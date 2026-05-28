# TweakMyClient

[![Minecraft](http://cf.way2muchnoise.eu/versions/Minecraft_478757_all.svg?badge_style=flat)](https://www.curseforge.com/minecraft/mc-mods/tweakmyclient/files)
[![License](https://img.shields.io/github/license/Hendrix-Shen/Tweak-My-Client?label=License&style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/blob/master/LICENSE)
![Languages](https://img.shields.io/github/languages/top/Hendrix-Shen/Tweak-My-Client?style=flat-square)
[![Issues](https://img.shields.io/github/issues/Hendrix-Shen/Tweak-My-Client?label=Issuess&style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/issues)
[![Pull Requests](https://img.shields.io/github/issues-pr/Hendrix-Shen/Tweak-My-Client?label=Pull%20Requests&style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/pulls)
[![Last Building](https://img.shields.io/github/actions/workflow/status/Hendrix-Shen/Tweak-My-Client/CI.yml?label=Last%20build&style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/actions/workflows/CI.yml)
[![Stable Release](https://img.shields.io/github/v/release/Hendrix-Shen/Tweak-My-Client?label=Stable%20Release&style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/releases)
[![Development Release Downloads](https://img.shields.io/github/v/release/Hendrix-Shen/Tweak-My-Client?include_prereleases&label=Development%20Release&style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/releases)
[![Github Release Downloads](https://img.shields.io/github/downloads/Hendrix-Shen/Tweak-My-Client/total?label=Github%20Release%20Downloads&style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/releases)
[![Modrinth Downloads](https://img.shields.io/modrinth/dt/HMEK2HpH?label=Modrinth%20Downloads&logo=Modrinth%20Downloads&style=flat-square)](https://modrinth.com/mod/tweakmyclient)
[![CurseForge Downloads](http://cf.way2muchnoise.eu/478757.svg?badge_style=flat)](https://www.curseforge.com/minecraft/mc-mods/tweakmyclient)

[English](./README.md)

打开游戏内配置界面的默认热键是 **T + C**。

❗在报告问题前，请务必尝试最新[测试版](https://github.com/Hendrix-Shen/Tweak-My-Client/releases)，检查问题是否依然存在。

## 依赖项

| 依赖       | 类型 | 版本         | 下载                                                                                                                                           |
|----------|----|------------|----------------------------------------------------------------------------------------------------------------------------------------------|
| MagicLib | 必须 | \>=0.7.721 | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/magiclib) &#124; [Github](https://github.com/Hendrix-Shen/MagicLib)                |
| MaliLib  | 必须 | 任意         | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/malilib) &#124; [Masa WebSite](https://masa.dy.fi/mcmods/client_mods/?mod=malilib) | |

## 开发

### 支持

当前主开发版本：1.16.5

并且使用 `预处理` 来兼容各版本。

**注意: 我们仅接受以下版本的议题。请注意该信息的时效性，任何不在此列出的版本议题均会被关闭。**

- Minecraft 1.14.4
- Minecraft 1.15.2
- Minecraft 1.16.5
- Minecraft 1.17.1
- Minecraft 1.18.2
- Minecraft 1.19.2
- Minecraft 1.19.3
- Minecraft 1.19.4
- Minecraft 1.20.1
- Minecraft 1.20.2
- Minecraft 1.20.4
- Minecraft 1.20.6
- Minecraft 1.21.1
- Minecraft 1.21.3
- Minecraft 1.21.4
- Minecraft 1.21.5
- Minecraft 1.21.8
- Minecraft 1.21.10
- Minecraft 1.21.11
- Minecraft 26.1.2

### 混淆映射表

对于 Minecraft 1.14 - 1.21.11，我们使用 **Mojang 官方** 混淆映射表来反混淆 Minecraft 并插入补丁程序。

### 文档

英文文档与中文文档是逐行对应的。

## 免责声明

本开源模组的使用完全基于使用者的自主决定，所有资源使用过程中可能产生的任何风险、损失或后果均由使用者自行承担，我们不对因使用本项目而导致的任何直接或间接损失负责。

我们郑重承诺，在本项目的使用过程中，我们不会以任何形式收集、存储、传输或上传使用者的任何个人信息、使用数据或其他敏感信息，所有数据处理均在本地完成，确保使用者的隐私安全得到最大程度的保护。

对于任何第三方基于本项目创建的分支、修改版本或通过其他渠道进行的分发，我们不提供任何形式的保证、支持或维护服务，使用者在使用此类第三方版本时应自行评估相关风险。我们提供的官方发行版严格基于开源代码原样编译生成二进制文件，在此过程中仅会通过占位符等技术手段在编译时替换版本号等必要信息，以确保项目的正确标识和追踪，除此之外不会对源代码进行任何实质性修改。

同时，我们需要明确告知使用者，我们无法保证能够完全修复所有已知或未知的安全漏洞，也无法承诺及时响应所有功能请求或提供定期更新，项目的维护和更新将根据项目发展情况和资源可用性进行安排，使用者应理解开源项目的这一特性并据此做出合理的使用决策。

需要特别说明的是，本项目库与 Mojang Studios、Microsoft 及其相关产品、服务或商标没有任何关联、授权、赞助或合作关系，本项目为完全独立的开源项目。

若您不接受上述任何条款和免责声明，请不要使用、安装、引入或集成本模组到您的任何项目中，并请立即删除您设备或系统中已存储的任何相关副本，停止一切使用行为。

## 许可

此项目在 LGPL-3.0许可证 下可用。 从中学习，并将其融入到您自己的项目中。
