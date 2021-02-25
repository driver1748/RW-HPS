## 语言  

[跳转中文](https://github.com/deng-rui/RW-HPS/blob/master/README.md)  
[TO EN](https://github.com/deng-rui/RW-HPS/blob/master/README-EN.md)  

# RW-HPS  
第三方铁锈战争服务器  
这是一个基于Netty的服务器  
旨在作为一个高性能 高可用的服务器 为玩家提供更好的游戏体验

### Licenses
本服务端遵守  
GNU General Public License v3.0

#### 不会支持的游戏协议
- 列表相关，如ADD List, Update List, Remove List  

**一切开发旨在学习，请勿用于非法用途**  

## 开始
- 开发文档: [docs](docs/README.md)
- 更新日志: [release](https://github.com/deng-rui/RWHPS/releases)
- 开发计划: [milestones](https://github.com/deng-rui/RWHPS/milestones)
- 讨论:
  > 在 GitHub Discussions 提出的问题会收到回复, 也欢迎分享你基于项目的新想法.  
  > 邮件联系 Der-DCT@pm.me  


## 构建
1.安装JDK 11+。如果你不知道怎么做，就去查.  
2.运行“gradlew jar”  
3.您的jar将位于'build/libs'目录中.  
4.运行您的Jar,体验高性能服务器 请注意 本服务端不支持Win下运行  

## 运行配置

| 配置 		| CPU             | 内存 	| 系统 			| 硬盘大小 	| Java      |
|:--- 		|:---             |:---     |:---           |:---       |:---       |
| 当前配置 	| BCM2711         | 4G      | Ubuntu 19.10  | 500 HDD  | Java 11   |
| 最低配置 	| ARMv7 Processor rev 5  | 64M      | Linux~  | 64M HDD  | Java 11   |

## 构建配置

| 配置 		| CPU             | 内存 	| 系统 			| 硬盘大小 	| Java      | Gradle    |
|:--- 		|:---             |:--- 	|:--- 			|:---      	|:---       |:---       |
| 当前配置 	| BCM2711         | 4G 		| Ubuntu 19.10 	| 500G HDD 	| Java 11    | 6.2.2     |

## 服务器命令列表
| 命令 					 | 参数 																						 | 信息 									 |
|:--- 					 |:--- 																						 |:--- 									 |
| help 		              |                                                  										 | 获取帮助 		 |
| start                  |                                                  										 | 开启服务器 						 |
| say 		            | &lt;文字&gt;                                                  								| 用Server的名义发消息 				 |
| giveadmin              | &lt;玩家位置&gt; 																            | 转移Admin       		         |
| restart 			      | 																						| 重启服务器 				  |
| gameover 				 |  	                                                                                    | 重新开始游戏               				 |
| clearbanip          		 |                                                  										 | 清理被ban的ip               	 |
| admin          		 |&lt;add/remove&gt; &lt;PlayerSite&gt;                                                  										 | 设置admin               			 |
| clearbanuuid          		 |                               	   											 | 清除被ban的uuid               			 |
| clearbanall          		 |                               	   											 | 清空ban               			 |
| ban          		 | &lt;PlayerSerialNumber&gt;                                 	   											 | 禁止某人               			 |
| mute          		 |  &lt;PlayerSerialNumber&gt;  &lt;Time/s&gt;                             	   											 | 清除被ban的uuid               			 |
| kick          		 |  &lt;PlayerSerialNumber&gt;  &lt;Time/s&gt;                             	   											 | 踢出               			 |
| isafk          		 |  &lt;off/on&gt;                             	   											 | 是否启用AFK               			 |
| plugins          		 |                               	   											 | 查看插件列表               			 |
| players          		 |                               	   											 | 查看玩家列表               			 |
| kill          		 | &lt;PlayerSerialNumber&gt;                              	   											 | 杀死玩家               			 |
| clearmuteall          		 |                               	   											 | 取消全部禁言               			 |
| maps          		 |                               	   											 | 查看Custom Map               			 |
| stop          		 |                               	   											 | 停止服务器               			 |


## 游戏命令列表

| 命令 			| 参数 												 | 信息 										 |
|:---           |:--- 												 |:--- 										 |
| help      |   | 获取帮助 									 |

### 鸣谢  
@Miku 的RUKKIT项目带来的启发  
@Tiexiu.xyz 提供计算支持  
@Aunken 的ARC/Mindustry项目提供底层设想  
@Apache 的org.apache.tools.zip
