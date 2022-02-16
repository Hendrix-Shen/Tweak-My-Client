# TweakMyClient

[![Minecraft](http://cf.way2muchnoise.eu/versions/Minecraft_478757_all.svg?badge_style=flat)](https://www.curseforge.com/minecraft/mc-mods/tweakmyclient/files)
[![License](https://img.shields.io/github/license/Hendrix-Shen/Tweak-My-Client?label=License&style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/blob/master/LICENSE)
![Languages](https://img.shields.io/github/languages/top/Hendrix-Shen/Tweak-My-Client?style=flat-square)
![Java-8~16](https://img.shields.io/badge/Java-8%20%7C%209%20%7C%2010%20%7C%2011%20%7C%2012%20%7C%2013%20%7C%2014%20%7C%2015%20%7C%2016-orange?style=flat-square)
[![Issues](https://img.shields.io/github/issues/Hendrix-Shen/Tweak-My-Client?label=Issuess&style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/issues)
[![Pull Requests](https://img.shields.io/github/issues-pr/Hendrix-Shen/Tweak-My-Client?label=Pull%20Requests&style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/pulls)
[![Last build](https://img.shields.io/github/workflow/status/Hendrix-Shen/Tweak-My-Client/CI/dev?label=Last%20build&style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/actions/workflows/CI.yml)
[![Github Release](https://img.shields.io/github/v/release/Hendrix-Shen/Tweak-My-Client?label=Github%20Release&include_prereleases&style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/releases)
[![Github Release Downloads](https://img.shields.io/github/downloads/Hendrix-Shen/Tweak-My-Client/total?label=Github%20Release%20Downloads&style=flat-square)](https://github.com/Hendrix-Shen/Tweak-My-Client/releases)
[![CurseForge Downloads](http://cf.way2muchnoise.eu/478757.svg?badge_style=flat)](https://www.curseforge.com/minecraft/mc-mods/tweakmyclient)

[中文](./README_ZH_CN.md)

The default hotkey to open the in-game config GUI is **T + C**.

## Dependencies

| Dependency               | Type     | 1.16.5                       | 1.17.1                       | 1.18.1         | Download                                                                                                                                           |
|--------------------------|----------|------------------------------|------------------------------|----------------|----------------------------------------------------------------------------------------------------------------------------------------------------|
| Auth Me                  | Optional | \>=1.4.0                     | \>=1.5.0                     | \>=2.2.0       | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/auth-me) &#124; [Github](https://github.com/axieum/authme)                               |
| In-Game Account Switcher | Optional | \>=7.1.0-pre3                | \>=7.1.2                     | \>=7.1.3       | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/in-game-account-switcher)                                                                |
| MagicLib                 | Required | \>=0.1.13 <0.2               | \>=0.1.13 <0.2               | \>=0.1.13 <0.2 | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/magiclib) &#124; [Github](https://github.com/Hendrix-Shen/MagicLib)                      |
| MaliLib                  | Required | \>=0.10.0-dev.21             | \>=0.10.0-dev.26             | \>=0.11.5      | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/malilib) &#124; [Masa WebSite](https://masa.dy.fi/mcmods/client_mods/?mod=malilib)       |
| Litematica               | Optional | \>=0.0.0-dev.20210612.170003 | \>=0.0.0-dev.20210906.183617 | \>=0.10.1      | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/litematica) &#124; [Masa WebSite](https://masa.dy.fi/mcmods/client_mods/?mod=litematica) |
| ReAuth (Fabric)          | Optional | \>=3.9.2                     | \>=3.9.2                     | \>=3.9.2       | [CurseForge](https://www.curseforge.com/minecraft/mc-mods/reauth-fabric)                                                                           |

## Config List

### Color

### colorBlockOutside

Custom block outside color.

### colorGuiStart

Custom gui gradient start color.

### colorGuiStop

Custom gui gradient stop color.

### colorSidebarContent

Custom sidebar content color.

### colorSidebarTitle

Custom sidebar title color.

### colorWaterOpen

openWaterHelper open water outline color.

### colorWaterShallow

openWaterHelper open shallow outline color.

### Disable

### disableAttackEntity

Disable attacks on entities in the list.

### disableClientBlockEvents

Disable block event rendering (eg pistons animations).

### disableClientEntityInListUpdates

Disable entity updates in the list on the client.

The priority of this setting is higher than the following:

- `disableClientEntityTNTUpdates`
- `disableClientEntityWitherUpdates`
- `disableClientEntityZombieVillagerUpdates`

### disableClientEntityTNTUpdates

Disable TNT entity updates on the client.

Maybe useful for TNT related machines.

### disableClientEntityWitherUpdates

Disable wither entity updates on the client.

Maybe it's useful for fake peace.

### disableClientEntityZombieVillagerUpdates

Disable zombie villager entity updates on the client.

Maybe it's useful for fake peace.

### disableClientEntityInListRendering

Disable rendering of entities in the list.

The priority of this setting is higher than the following:

- `disableEntityTNTRendering`
- `disableEntityWitherRendering`
- `disableEntityZombieVillagerRendering`

### disableFovAffectedBySpeed

Disable speed multiplier effect fov transform.

### disableGuiShadowLayer

Disable gui shadow overlay rendering when gui is opening.

### disableItemGlowing

Disable items with enchanted glowing effects.

### disableRenderBossBar

Disable boss bar rendering when player is near boss.

You will gain a broader view.

### disableRenderOverlayFire

Disable fire overlay rendering when player is on fire.

You will gain a broader view.

### disableRenderOverlayPumpkin

Disable mask overlay rendering when player is wearing pumpkin.

You will gain a broader view.

### disableRenderScoreboard

Disable Scoreboard rendering.

You will gain a broader view.

### disableRenderToast

Disable toast rendering.

### disableSlowdown

Cancels slowness effects caused by honey, soul sand and using items.

### Generic

### antiGhostBlocksAutoTriggerInterval

How many seconds does antiGhostBlocks trigger automatically.

### antiGhostBlocksManualTrigger

Manually trigger antiGhostBlocks once.

### antiGhostBlocksMode

antiGhostBlocksMode working mode.

### antiGhostItemsAutoTriggerInterval

How many seconds does antiGhostItems trigger automatically.

### antiGhostItemsManualTrigger

Manually trigger antiGhostItems once.

### antiGhostItemsMode

antiGhostItemsMode working mode.

### autoDropInterval

How many ticks per interval to perform auto drop.

### autoReconnectTimer

How many seconds to wait for auto reconnection.

### daylightOverrideTime

The time set when overriding the client's world time.

### customWindowTitle

Modify current window title.

The available placeholders are as follows:

- {fabric_loader_asm_version}
- {fabric_loader_version}
- {fabric_mod_ver:<modid>}
- {mc_fps}
- {mc_protocol_version}
- {mc_username}
- {mc_version}
- {tmc_version}
- {tmc_version_type}

### customWindowTitleWithActivity

Modify the current window title with the current activity.

The available placeholders are as follows:

- {fabric_loader_asm_version}
- {fabric_loader_version}
- {fabric_mod_ver:<modid>}
- {mc_activity}
- {mc_fps}
- {mc_protocol_version}
- {mc_username}
- {mc_version}
- {tmc_version}
- {tmc_version_type}

### enderPortalRenderMode

If `endPortalRendererFix` is enabled, the ender portal renders in the specified mode.

The available modes are as follows:

- Actual - Rendering the ender portal using its hit box.
- Full - Rendering the ender portal as a full block.
- Legacy - Rendering the ender portal with Minecraft 21w13a below.
- Modern - Rendering the ender portal with Minecraft 21w13a and above.

### getTargetBlockPosition

Gets the position of the looking block.

This feature is used to get the position of the farthest block you point to.

### lowHealthThreshold

Trigger threshold of low health warning.

### memoryCleaner

Force release memory.

### openConfigGui

A hotkey to open the in-game Config GUI.

### targetBlockMaxTraceDistance

Maximum line tracing distance of target block position.

### targetBlockPositionFormat

Position tracing format sent to public chat.

### targetBlockPositionMode

The block tracing position will be sent in this mode.

- private - Only you can see
- public - Send to public chat

### Feature

### featureAntiGhostBlocks

send mining abort packets to allow the server to reply with the correct data.

### featureAntiGhostItems

Try to send false data to the server in order to get the correct player inventory data from the server.

### featureAutoClimb

When you are approaching the ladder / vine and raises your head, you will automatically climb.

### featureAutoDrop

Automatically drops unwanted items.

### featureAutoReconnect

Automatically reconnects when you disconnect from the server.

Adaptive AuthMe, ReAuth and In-Game Account Switcher session validation.

### featureAutoRespawn

Automatically respawns you whenever you die.

### featureCustomBlockOutsideColor

Enable block outside color override.

### featureCustomGuiBackgroundColor

Enable gui background color override.

### featureCustomSidebarBackgroundColor

Enable sidebar background color override.

### featureCustomWindowIcon

Enable window icon override.

You can define your own icons using the following resource pack namespace files:

- tweakmyclient:icons/icon_16x16.png
- tweakmyclient:icons/icon_32x32.png

Note: You must reload the resource pack to apply the file changes.

### featureCustomWindowTitle

Enable window title override.

### featureGlobalEventListener

Print global event trigger coordinates.

### featureDaylightOverride

Override the client's world time to the daytime you want.

### featureGetTargetBlockPosition

Allow you get the position of the farthest block you point to.

### featureLowHealthWarning

When your health is too low, use action bar to send a warning message.

### featureOpenWaterHelper

Shows whether you are fishing in 'open water' and draws a box around the area used for the open water calculation.

### featureUnfocusedCPU

Will not render anything when your Minecraft window is not focused.

### List

### listAutoDropBlackList

Auto drop item black list.

All items in this list won't be auto dropped.

### listAutoDropType

Auto drop item list type.

### listAutoDropWhiteList

Auto drop item white list.

All items in this list will be auto dropped.

### listDisableAttackEntity

Disable attack entity list.

All entities in this list will not be attacked.

### listDisableClientEntityUpdates

Disable client entity updates list.

All entities in this list will not be updated.

### listDisableClientEntityRendering

Disable client entity rendering list.

All entities in this list will not be rendered.

### listItemGlowing

Disable items glowing list.

All items in this list will not be glowed.

### Patch

### disableLitematicaEasyPlaceFailTip

Disable easyPlace failure that annoying prompt window.

Note: Takes effect only when litematica is loaded.

### endPortalRendererFix

Fixing the endPortal to only render materials at the top.

### forcePistonWithoutAffectByTool

The pickaxe will no longer be used as an effective mining tool for pistons.

## Development

### Mappings

I am using the **Mojang official** mappings to de-obfuscate Minecraft and insert patches.

## License

This project is available under the GPLv3 license. Feel free to learn from it and incorporate it in your own projects.