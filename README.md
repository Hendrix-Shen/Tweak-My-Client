# TweakMyClient
[![For MC](http://cf.way2muchnoise.eu/versions/For%20MC_tweakmyclient_all.svg?badge_style=flat)](https://www.curseforge.com/minecraft/mc-mods/tweakmyclient/files/all?filter-game-version=1738749986%3a70886)
[![License](https://img.shields.io/github/license/Hendrix-Shen/Tweak-My-Client?style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/blob/main/LICENSE)
[![Issues](https://img.shields.io/github/issues/Hendrix-Shen/Tweak-My-Client?style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/issues)
[![Pull Requests](https://img.shields.io/github/issues-pr/Hendrix-Shen/Tweak-My-Client?style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/pulls)
[![Java CI with gradle](https://img.shields.io/github/workflow/status/Hendrix-Shen/Tweak-My-Client/Java%20CI%20with%20Gradle?label=Java%20CI%20with%20Gradle&style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/actions/workflows/build.yml)
[![Publish Release](https://img.shields.io/github/workflow/status/Hendrix-Shen/Tweak-My-Client/Publish%20Release?label=Publish%20Release&style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/actions/workflows/publish.yml)
[![Release](https://img.shields.io/github/v/release/Hendrix-Shen/Tweak-My-Client?include_prereleases&style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/releases)
[![Github Release Downloads](https://img.shields.io/github/downloads/Hendrix-Shen/Tweak-My-Client/total?label=Github%20Release%20Downloads&style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/releases)
[![CurseForge Downloads](http://cf.way2muchnoise.eu/tweakmyclient.svg?badge_style=flat)](https://www.curseforge.com/minecraft/mc-mods/tweakmyclient)
#### Warning: The project is still in the early development stage.
The default hotkey to open the in-game config GUI is **T + C**.
## Dependencies
| Dependency      | Type     | Version                        | Download                                                                 |
| --------------- | -------- | ------------------------------ | ------------------------------------------------------------------------ |
| Auth Me         | Optional | \>=1.4.0                       | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/auth-me)       |
| MaliLib         | Required | \>=3.9.2                       | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/malilib)       |
| ReAuth (Fabric) | Optional | \>=1.16.4-0.10.0-dev.21+arne.1 | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/reauth-fabric) |
## It includes the following features.
### Generic
+ daylightOverrideTime
  - *The time set when overriding the client's world time.*
  - *覆盖客户端的世界时间时设置的时间.*
+ getTargetBlockPosition
  - *Gets the position of the looking block. This feature is used to get the coordinates of the farthest block you point to.*
  - *获取你看向方块的坐标. 此特性用于获取你指向方向最远端的方块坐标.*
+ openConfigGui
  - *A hotkey to open the in-game Config GUI.*
  - *打开设置界面的快捷键.*
+ targetBlockMaxTraceDistance
  - *Maximum line tracking distance of target block position.*
  - *获取方块的最大直线追踪距离.*
+ targetBlockPositionFormat
  - *Position tracing format sent to public chat.*
  - *发送至聊天窗的位置追踪格式.*
+ targetBlockPositionMode
  - *The block tracing position will be sent in this mode.*
  - *获取到的追踪位置将该模式发送.*

### Disable Toggle
+ disableClientBlockEvents
  - *Disables block event rendering (eg pistons animations).*
  - *关闭客户端侧方块事件渲染 (例如活塞动画).*
+ disableClientEntityTNTUpadates
  - *Disables TNT entity updates on the client. Maybe useful for TNT related machines.*
  - *关闭客户端对TNT实体的运算.也许对与TNT有关的机器有帮助.*
+ disableClientEntityWitherUpdates
  - *Disables wither entity updates on the client. Maybe it's useful for fake peace.*
  - *关闭客户端对凋零实体的运算. 也许对伪和平有帮助.*
+ disableClientEntityZombieVillagerUpdates
  - *Disables zombie villager entity updates on the client. Maybe it's useful for fake peace.*
  - *关闭客户端对僵尸村民实体的运算. 也许对伪和平有帮助.*
+ disableEntityTNTRendering
  - *Disables TNT entity rendering. Maybe it's useful for fake peace.*
  - *关闭游戏对TNT实体的渲染. 也许对与TNT有关的机器有帮助.*
+ disableEntityWitherRendering
  - *Disables wither entity rendering. Maybe it's useful for fake peace.*
  - *关闭游戏对凋零实体的渲染. 也许对伪和平有帮助.*
+ disableEntityZombieVillagerRendering
  - *Disables zombie villager entity rendering. Maybe it's useful for fake peace.*
  - *关闭游戏对僵尸村民实体的渲染. 也许对伪和平有帮助.*
+ disableRenderOverlayFire
  - *Disables fire overlay rendering when player is on fire. You will gain a broader view.*
  - *关闭在玩家着火时的渲染覆盖. 你将获得更广阔的视野.*
+ disableRenderOverlayPumpkin
  - *Disables mask overlay rendering when player is wearing pumpkin. You will gain a broader view.*
  - *关闭在玩家在穿戴南瓜时的渲染覆盖. 你将获得更广阔的视野.*
+ disableScoreboardRendering
  - *Disables Scoreboard rendering. You will gain a broader view.*
  - *关闭游戏对计分板的UI渲染. 你将获得更广阔的视野.*
+ disableSlowdown
  - *Cancels slowness effects caused by honey, soul sand and using items.*
  - *取消蜂蜜块, 灵魂沙和使用物品时的减速效果.*
### Feature Toggle
+ featureAutoReconnect
  - *Automatically reconnects when you disconnect from the server.*
  - *从服务器断开连接时自动重新连接服务器.*
+ featureAutoRespawn
  - *Automatically respawns you whenever you die.*
  - *死亡时自动重生.*
+ featureDaylightOverride
  - *Override the client's world time to the day time you want.*
  - *覆盖客户端的世界时间为你想要的时间.*
+ featureGetTargetBlockPosition
  - *Allow you get the position of the farthest block you point to.*
  - *允许获取你指向方向最远端的方块坐标.*