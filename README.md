# TweakMyClient

[![Minecraft](http://cf.way2muchnoise.eu/versions/Minecraft_478757_all.svg?badge_style=flat)](https://www.curseforge.com/minecraft/mc-mods/tweakmyclient/files)
[![License](https://img.shields.io/github/license/Hendrix-Shen/Tweak-My-Client?label=License&style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/blob/master/LICENSE)
![Languages](https://img.shields.io/github/languages/top/Hendrix-Shen/Tweak-My-Client?style=flat-square)
![Java-8~18](https://img.shields.io/badge/Java-8%20%7C%209%20%7C%2010%20%7C%2011%20%7C%2012%20%7C%2013%20%7C%2014%20%7C%2015%20%7C%2016%20%7C%2017%20%7C%2018-orange?style=flat-square)
[![Issues](https://img.shields.io/github/issues/Hendrix-Shen/Tweak-My-Client?label=Issuess&style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/issues)
[![Pull Requests](https://img.shields.io/github/issues-pr/Hendrix-Shen/Tweak-My-Client?label=Pull%20Requests&style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/pulls)
[![Last build](https://img.shields.io/github/actions/workflow/status/Hendrix-Shen/Tweak-My-Client/CI.yml?label=Last%20build&style=flat-square&branch=dev)](https://github.com/Hendrix-Shen/Tweak-My-Client/actions/workflows/CI.yml)
[![Github Release](https://img.shields.io/github/v/release/Hendrix-Shen/Tweak-My-Client?label=Github%20Release&style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/releases)
[![Github Release Downloads](https://img.shields.io/github/downloads/Hendrix-Shen/Tweak-My-Client/total?label=Github%20Release%20Downloads&style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/releases)
[![CurseForge Downloads](http://cf.way2muchnoise.eu/478757.svg?badge_style=flat)](https://www.curseforge.com/minecraft/mc-mods/tweakmyclient)

[中文](./README_ZH_CN.md)

The default hotkey to open the in-game config GUI is **T + C**.

## Dependencies (WIP)

| Dependency               | Type     | Version   | Download                                                                                                                                           |
|--------------------------|----------|-----------|----------------------------------------------------------------------------------------------------------------------------------------------------|
| Auth Me                  | Optional | Any       | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/auth-me) &#124; [Github](https://github.com/axieum/authme)                               |
| Hwyla                    | Optional | Latest    | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/hwyla)                                                                                   |
| In-Game Account Switcher | Optional | Any       | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/in-game-account-switcher)                                                                |
| Jade                     | Optional | Any       | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/jade)                                                                                    |
| MagicLib                 | Required | \>=0.5.37 | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/magiclib) &#124; [Github](https://github.com/Hendrix-Shen/MagicLib)                      |
| MaliLib                  | Required | Any       | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/malilib) &#124; [Masa WebSite](https://masa.dy.fi/mcmods/client_mods/?mod=malilib)       |
| Litematica               | Optional | Any       | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/litematica) &#124; [Masa WebSite](https://masa.dy.fi/mcmods/client_mods/?mod=litematica) |
| ReAuth (Fabric)          | Optional | Any       | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/reauth-fabric)                                                                           |
| WTHIT                    | Optional | Latest    | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/wthit)                                                                                   |

## Development

### Support

Current main development for Minecraft version: 1.19.2

And use `preprocess` to be compatible with all versions.

**Note: I only accept the following versions of issues, which are the last updates of each MC major version. Please note that this information is time-sensitive and any version of the issue not listed here will be closed**

- Minecraft 1.14.4
- Minecraft 1.15.2
- Minecraft 1.16.5
- Minecraft 1.17.1
- Minecraft 1.18.2
- Minecraft 1.19.3

### Mappings

I am using the **Mojang official** mappings to de-obfuscate Minecraft and insert patches.

## License

This project is available under the GPLv3 license. Feel free to learn from it and incorporate it in your own projects.