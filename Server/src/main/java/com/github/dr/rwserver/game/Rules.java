package com.github.dr.rwserver.game;

import com.github.dr.rwserver.data.Player;
import com.github.dr.rwserver.data.global.Data;
import com.github.dr.rwserver.io.Packet;
import com.github.dr.rwserver.net.AbstractNetConnect;
import com.github.dr.rwserver.net.AbstractNetPacket;
import com.github.dr.rwserver.net.Administration;
import com.github.dr.rwserver.net.Net;
import com.github.dr.rwserver.struct.OrderedMap;
import com.github.dr.rwserver.util.encryption.Base64;
import com.github.dr.rwserver.util.file.FileUtil;
import com.github.dr.rwserver.util.file.LoadConfig;
import com.github.dr.rwserver.util.log.Log;
import com.github.dr.rwserver.util.zip.zip.ZipDecoder;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledFuture;

/**
 * @author Dr
 */
public class Rules {
    /** 端口 */
    public int port = 5123;
    /** 支持版本 */
    public final int version;
    /** 协议 */
    public final AbstractNetConnect connectNet;
    public final AbstractNetPacket connectPacket;
    /** 是否已启动游戏 */
    public boolean isStartGame = false;
    /** 倍数 */
    public float income = 1f;
    /** 初始钱 */
    public int credits = 0;
    /** 最大玩家 */
    public int maxPlayer;
    /** 地图数据 */
    public final GameMaps maps = new GameMaps();
    /** nukes */
    public boolean noNukes = false;
    /** 初始单位 */
    public int initUnit = 1;
    /** 迷雾 */
    public int mist = 2;
    /** 共享控制 */
    public boolean sharedControl = false;
    /** 密码 */
    public String passwd;
    /** 按键包缓存 */
    public final LinkedBlockingQueue<GameCommand> gameCommandCache = new LinkedBlockingQueue<>();
    /** 混战分配 */
    public boolean amTeam = false;
    /** 队伍数据 */
    public volatile Player[] playerData;
    /** ？ */
    public Net.NetStartGame natStartGame = null;
    /** 最大发言长度 */
    public int maxMessageLen;
    /** 最大单位数 */
    public final int maxUnit;
    /** AFK */
    public boolean isAfk = true;
    /** only Admin */
    public boolean oneAdmin;
    /** TICK */
    public final int tickTimeA;
    public final int tickTimeB;
    /** ip多语言支持 */
    public final boolean ipCheckMultiLanguageSupport;
    /** 重连暂停 */
    public boolean reConnectBreak = false;
    /** 重连缓存 GameSave */
    public volatile Packet gameSaveCache = null;
    /** 是否启用重连 */
    public final boolean reConnect;
    /** 是否启用胜负判定 */
    public final boolean winOrLose;
    /** 胜负判定时间 */
    public final int winOrLoseTime;
    /** 是否第一次启动读取MOD */
    public boolean oneReadUnitList;
    /** 共享控制 */
    public int sharedControlPlayer = 0;

    /** AD */
    public final String enterAd;
    public final String startAd;

    /* */
    public final boolean webApi;
    public final String webUrl;
    public final int webApiPort;
    public final boolean webApiSsl;
    public final String webApiSslKetPath;
    public final String webApiSslPasswd;
    /* */
    public final boolean deleteLib;

    public ScheduledFuture afk = null;
    public ScheduledFuture gameOver = null;
    public ScheduledFuture gameTask = null;
    public ScheduledFuture ping = null;
    public ScheduledFuture team = null;
    public ScheduledFuture winOrLoseCheck = null;

    public final String subtitle;

    public OrderedMap<String,GameMaps.MapData> mapsData = new OrderedMap<>(8);

    public Rules(LoadConfig config) {

        subtitle = config.readString("subtitle","");


        int port = config.readInt("port",5123);
        init(config.readInt("maxPlayer",10),port);

        enterAd = config.readString("enterServerAd","");
        passwd = config.readString("passwd","");
        startAd = config.readString("startAd","");
        if (config.readBoolean("readMap",false)) {
            checkMaps(FileUtil.File(Data.Plugin_Maps_Path));
            Log.clog(Data.localeUtil.getinput("server.load.maps"));
        }
        maxMessageLen = config.readInt("maxMessageLen",40);
        maxUnit = config.readInt("maxUnit",200);
        int tick = config.readInt("tickSpeed",0);
        if (maxPlayer > 10) {
            tick = 0;
        }
        Data.core.defIncome = config.readFloat("defIncome",1f);
        tickTimeA = (tick > 0) ? 100 : 200;
        tickTimeB = (tick > 0) ? 100 : 150;
        Data.core.serverName = config.readString("serverName","RW-HPS");
        oneAdmin = config.readBoolean("oneAdmin",true);
        ipCheckMultiLanguageSupport = config.readBoolean("iPCheckMultiLanguageSupport",false);

        reConnect = config.readBoolean("reConnect",false);
        winOrLose = config.readBoolean("winOrLose",false);
        winOrLoseTime = config.readInt("winOrLoseTime",30000);



        webApi = config.readBoolean("webApi",false);
        webUrl = config.readString("webUrl","");
        webApiPort = config.readInt("webApiPort",0);
        webApiSsl = config.readBoolean("webApiSsl",false);
        webApiSslKetPath = config.readString("webApiSslKetPath",null);
        webApiSslPasswd = config.readString("webApiSslPasswd",null);

        deleteLib = config.readBoolean("deleteLib","");
        oneReadUnitList = config.readBoolean("oneReadUnitList",false);


        /* RWHPS Core */
        Administration.NetConnectProtocolData protocol = Data.core.admin.getNetConnectProtocol();
        connectNet = protocol.protocol;
        Administration.NetConnectPacketData packet = Data.core.admin.getNetConnectPacket();
        connectPacket = packet.packet;
        version = protocol.version;
        
    }

    public void init(int maxPlayer,int port) {
        this.maxPlayer = maxPlayer;
        playerData = new Player[maxPlayer];
        this.port = port;
        income = Data.core.defIncome;
    }

    public void re() {
        gameCommandCache.clear();
        playerData = null;
        playerData = new Player[maxPlayer];
        income = Data.core.defIncome;
        initUnit = 1;
        mist = 2;
        sharedControl = false;
        System.gc();
    }

    private void checkMaps(FileUtil fileutil) {
        List<File> list = fileutil.getFileList();
        list.forEach(e -> {
            final String original = Base64.isBase64(e.getName()) ? Base64.decodeString(e.getName()) : e.getName();
            final String postpone = original.substring(original.lastIndexOf("."));
            final String name = original.substring(0, original.length()-postpone.length());
            switch (postpone) {
                case ".tmx":
                    try {
                        mapsData.put(name,new GameMaps.MapData(GameMaps.MapType.customMap,new FileUtil(e).readFileByte()));
                    } catch (Exception exception) {
                        Log.error("read tmx Maps",e);
                    }
                    break;
                case ".save":
                    try {
                        mapsData.put(name,new GameMaps.MapData(GameMaps.MapType.savedGames,new FileUtil(e).readFileByte()));
                    } catch (Exception exception) {
                        Log.error("read save Maps",e);
                    }
                    break;
                case ".zip":
                    try {
                        OrderedMap<String,byte[]> data = new ZipDecoder(e).getSpecifiedSuffixInThePackage("tmx");
                        data.each((k,v) -> mapsData.put(k,new GameMaps.MapData(GameMaps.MapType.customMap,v)));
                    } catch (IOException ioException) {
                        Log.error("ZIP READ",ioException);
                    }
                    break;
                default:
                    break;
            }
        });
    }
}
