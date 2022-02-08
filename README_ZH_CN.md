# TweakMyClient

[![Minecraft](http://cf.way2muchnoise.eu/versions/Minecraft_513524_all.svg?badge_style=flat)](https://www.curseforge.com/minecraft/mc-mods/tweakmyclient/files)
[![License](https://img.shields.io/github/license/Hendrix-Shen/Tweak-My-Client?style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/blob/main/LICENSE)
![Languages](https://img.shields.io/github/languages/top/Hendrix-Shen/Tweak-My-Client?style=flat-square)
![Java-8\~16](https://img.shields.io/badge/Java-8%20%7C%209%20%7C%2010%20%7C%2011%20%7C%2012%20%7C%2013%20%7C%2014%20%7C%2015%20%7C%2016-orange?style=flat-square)
[![Issues](https://img.shields.io/github/issues/Hendrix-Shen/Tweak-My-Client?style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/issues)
[![Pull Requests](https://img.shields.io/github/issues-pr/Hendrix-Shen/Tweak-My-Client?style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/pulls)
[![Public Beta](https://img.shields.io/github/workflow/status/Hendrix-Shen/Tweak-My-Client/CI/dev?label=Public%20Beta\&style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/actions/workflows/CI.yml?query=branch%3Adev)
[![Public Release](https://img.shields.io/github/workflow/status/Hendrix-Shen/Tweak-My-Client/CI/master?label=Public%20Release\&style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/actions/workflows/CI.yml?query=branch%3Amaster)
[![Github Release](https://img.shields.io/github/v/release/Hendrix-Shen/Tweak-My-Client?include_prereleases\&style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/releases)
[![Github Release Downloads](https://img.shields.io/github/downloads/Hendrix-Shen/Tweak-My-Client/total?label=Github%20Release%20Downloads\&style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/releases)
[![CurseForge Downloads](http://cf.way2muchnoise.eu/478757.svg?badge_style=flat)](https://www.curseforge.com/minecraft/mc-mods/tweakmyclient)

[English](./README.md)

打开游戏内配置界面的默认热键是 **T + C**。

## 依赖项

| 依赖                       | 类型 | 下载                                                                                                                                             |
| ------------------------ | -- | ---------------------------------------------------------------------------------------------------------------------------------------------- |
| Auth Me                  | 可选 | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/auth-me) \| [Github](https://github.com/axieum/authme)                               |
| In-Game Account Switcher | 可选 | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/in-game-account-switcher)                                                            |
| MagicLib                 | 内置 | [Github](https://github.com/Hendrix-Shen/MagicLib)                                                                                             |
| MaliLib                  | 必须 | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/malilib) \| [Masa WebSite](https://masa.dy.fi/mcmods/client_mods/?mod=malilib)       |
| Litematica               | 可选 | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/litematica) \| [Masa WebSite](https://masa.dy.fi/mcmods/client_mods/?mod=litematica) |
| ReAuth (Fabric)          | 可选 | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/reauth-fabric)                                                                       |

## 配置列表

### 颜色

### 方块轮廓颜色
自定义方块轮廓颜色。

### 界面渐变背景起始颜色
自定义界面渐变起始颜色。

### 界面渐变背景终止颜色
自定义界面渐变终止颜色。

### 计分板内容颜色
自定义计分板内容颜色。

### 计分板标题颜色
自定义计分板标题颜色。

### 开阔水域颜色
开阔水域助手开阔水域轮廓颜色。

### 潜水域颜色
开阔水域助手潜水域轮廓颜色。

### 禁用

### 禁用实体攻击
禁用攻击在列表中的实体。

### 禁用客户端侧方块事件
关闭客户端侧方块事件渲染 (例如活塞动画)。

### 禁用客户端侧列表内实体运算
关闭客户端对列表中实体的运算。<br>
此设置的优先级高于以下: <br>
`禁用客户端侧 TNT 运算`, `禁用客户端侧凋零实体运算`, `禁用客户端侧僵尸村民实体运算`

### 禁用客户端侧 TNT 实体运算
关闭客户端对 TNT 实体的运算。<br>
也许对与 TNT 有关的机器有帮助。

### 禁用客户端侧凋零实体运算
关闭客户端对凋零实体的运算。<br>
也许对伪和平有帮助。

### 禁用客户端侧僵尸村民实体运算
关闭客户端对僵尸村民实体的运算。<br>
也许对伪和平有帮助。

### 禁用列表内实体渲染
关闭游戏对列表中实体的渲染。<br>
此设置的优先级高于以下: <br>
`禁用 TNT 实体渲染`, `禁用凋零实体渲染`, `禁用僵尸村民实体渲染`

### 禁用速度倍率影响视角
关闭速度倍率对视角变换的影响。

### 禁用 GUI 阴影层
关闭打开 GUI 时的阴影层渲染。

### 禁用物品发光
禁用物品的附魔发光效果。

### 禁用 Boss 血条渲染
关闭在玩家在Boss附近渲染Boss血条。<br>
你将获得更广阔的视野。

### 禁用火焰渲染层
关闭在玩家着火时的渲染覆盖。<br>
你将获得更广阔的视野。

### 禁用南瓜头渲染层
关闭在玩家在穿戴南瓜时的渲染覆盖。<br>
你将获得更广阔的视野。

### 禁用计分板渲染
关闭游戏对计分板的 UI 渲染。<br>
你将获得更广阔的视野。

### 禁用 Toast 提醒消息
禁用 Toast 提醒消息渲染。

### 禁用减速
取消蜂蜜块, 灵魂沙和使用物品时的减速效果。

### 全局

### 自动清理幽灵方块间隔
多少秒自动触发一次清除幽灵方块。

### 手动清除幽灵方块
手动触发一次清除幽灵方块。

### 清除幽灵方块模式
清除幽灵方块工作模式。

### 自动清理幽灵物品间隔
多少秒自动触发一次清除幽灵物品。

### 手动清除幽灵物品
手动触发一次清除幽灵物品。

### 清除幽灵物品模式
清除幽灵物品工作模式。

### 自动丢弃间隔
每隔多少刻执行一次自动丢弃。

### 自动重连计时器
等待多少秒后自动重连。

### 客户端时间
覆盖客户端的世界时间时设置的时间。

### 自定义窗口标题
修改当前窗口标题。<br>
可用的占位符如下:<br>
{fabric_loader_asm_version}<br>
{fabric_loader_version}<br>
{fabric_mod_ver:<modid>}<br>
{mc_fps}<br>
{mc_protocol_version}<br>
{mc_username}<br>
{mc_version}<br>
{tmc_version}<br>
{tmc_version_type}

### 自定义带当前活动的窗口标题
修改带有当前活动的窗口标题。<br>
可用的占位符如下:<br>
{fabric_loader_asm_version}<br>
{fabric_loader_version}<br>
{fabric_mod_ver:<modid>}<br>
{mc_fps}<br>
{mc_protocol_version}<br>
{mc_username}<br>
{mc_version}<br>
{tmc_version}<br>
{tmc_version_type}

### 末路之地传送门渲染模式
若启用 末路之地传送门渲染修复, 末路之地传送门将以指定模式渲染。<br>
可用的模式如下：<br>
实际的 - 渲染末路之地传送门使用其自身碰撞箱<br>
完整的 - 渲染末路之地传送门为一个完整的方块<br>
新版的 - 渲染末路之地传送门使用 Minecraft 21w13a 及以上版本的行为<br>
旧版的 - 渲染末路之地传送门使用 Minecraft 21w13a 以下版本的行为

### 获取方块坐标
获取你看向方块的坐标。<br>
此特性用于获取你指向方向最远端的方块坐标。

### 低生命值阈值
触发低生命值警告的阈值。

### 内存清理
强制释放内存。

### 打开设置界面
打开设置界面的快捷键。

### 获取方块坐标最大追踪距离
获取方块的最大直线追踪距离。

### 获取方块坐标格式
发送至聊天窗的位置追踪格式。

### 获取方块坐标发送模式
获取到的追踪位置将以该模式发送。<br>
私有 - 仅你自己看到<br>
公共 - 发送至公屏聊天

### 特性开关

### 清除幽灵方块
发送挖掘终止数据包以让服务端回复正确的数据。

### 清除幽灵物品
尝试向服务器发送虚假数据, 以便从服务端获取正确的玩家背包数据。

### 自动攀爬
当你靠近梯子 / 藤蔓并抬起头时, 你将会自动攀爬。

### 自动丢弃
丢掉不想要的东西。

### 自动重连
从服务器断开连接时自动重新连接服务器。<br>
适配 AuthMe, ReAuth 和 In-Game Account Switcher 会话验证。

### 自动重生
死亡时自动重生。

### 自定义方块轮廓
启用方块轮廓颜色覆写。

### 自定义界面背景颜色
启用计分板界面背景色覆写。

### 自定义计分板背景颜色
启用计分板背景色覆写。

### 自定义窗口图标
启用窗口图标覆写。<br>

### 自定义带当前活动的窗口标题
修改带有当前活动的窗口标题。<br>
可用的占位符如下:<br>
{fabric_loader_asm_version}<br>
{fabric_loader_version}<br>
{fabric_mod_ver:<modid>}<br>
{mc_fps}<br>
{mc_protocol_version}<br>
{mc_username}<br>
{mc_version}<br>
{tmc_version}<br>
{tmc_version_type}

### 全局事件监听
打印全局事件触发坐标。

### 覆盖客户端世界时间
覆盖客户端的世界时间为你想要的时间。

### 获取方块坐标
允许获取你指向方向最远端的方块坐标。

### 低血量警告
当你的血量过低时, 将使用动作条发送一条警告信息。

### 开阔水域助手
展示是否在'开放水域'钓鱼, 并在用于开阔水域计算的区域绘制一个方框。

### 失去焦点释放 CPU
当焦点未处于 Minecraft 窗口时, 客户端将不会执行渲染。

### 列表

### 自动丢弃黑名单
自动丢弃物品黑名单。<br>
所有在此列表的物品将不会被丢弃。

### 自动丢弃列表类型
自动丢弃物品列表类型。

### 自动丢弃白名单
自动丢弃物品白名单。<b>
所有在此列表的物品将会被丢弃。

### 禁用实体攻击列表
禁用实体攻击的列表。<br>
所有在此列表内的实体将不会被攻击。

### 禁用客户端侧实体运算列表
禁用客户端侧运算实体的列表。<br>
所有在此列表内的实体将不会被运算。

### 禁用客户端侧实体渲染列表
禁用客户端侧渲染实体的列表。<br>
所有在此列表内的实体将不会被渲染。

### 禁用物品发光列表
禁用发光物品列表。<br>
所有在此列表内的物品将不会被发光效果装饰。

### 修补

### 禁用投影轻松放置失败提示
禁用轻松放置失败时那烦人的提示窗。<br>
注意: 仅当 Litematica 加载时生效。

### 末路之地传送门渲染修复
修复末路之地传送门只会在顶部渲染材质。

### 强制活塞不受镐子影响
镐子将不再作为活塞的有效开采工具。

## 开发

### 混淆映射表

我使用 **Mojang 官方** 混淆映射表来反混淆 Minecraft 并插入补丁程序。

## 许可

此项目在 GPLv3许可证 下可用。 从中学习，并将其融入到您自己的项目中。
