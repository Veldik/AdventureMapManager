# AdventureMapManager
[![Current Release](https://img.shields.io/github/release/Veldik/AdventureMapManager.svg)](https://github.com/Veldik/AdventureMapManager/releases/latest)
[![SpigotMC Downloads](https://img.shields.io/spiget/downloads/90119.svg?label=Downloads&color=fcba03)](https://www.spigotmc.org/resources/adventuremapmanager.90119/)
[![SpigotMC Rating](https://img.shields.io/spiget/stars/90119.svg?label=Rating)](https://www.spigotmc.org/resources/adventuremapmanager.90119/)  
Plugin for manage Minecraft adventure maps on Minecraft server.  
Main function is when everybody leave server, map will be restarted (re-downloaded) to factory phase.  
You can configure your map in [config.yml](https://github.com/Veldik/AdventureMapManager/blob/main/src/main/resources/config.yml)

[Czech version of README](README_CZ.md)

## Features:
- Shutdown server (it's recommended to set-up auto start, if shutdown), when everybody leave
- Re-download map from link, what is configured in config, on every start.
- Announce about playing map on join
- Set player-limit by value, what is in config. 
- You can allow automatically download [PaperMC](https://papermc.io/) by map version what is configured in config
- You can set to force download (When player don't allow it, he can't join to server) map resource pack

## Commands:
- `/map` Shows basic info about map, what are you playing

## Placeholders:
- `%AdventureMapManager_map_name%` Returns map name
- `%AdventureMapManager_map_mc_version%` Returns Minecraft version of map
- `%AdventureMapManager_map_world_name%` Returns world name, where is map played
- `%AdventureMapManager_map_world_url%` Returns link for download map world
- `%AdventureMapManager_map_resource_pack_url%` Returns link for download map resource pack
- `%AdventureMapManager_map_website%` Returns link for map website
- `%AdventureMapManager_map_author%` Returns map author name
- `%AdventureMapManager_map_limit%` Returns maximum of players for map

## config.yml
```yaml
# Server jar name, it is used for replacing by correct version of PaperMC, when is force-map-version set to true
server-file-name: server.jar
# When is set to true, is tries to download correct Minecraft version by map version
force-map-version: false
# This is example, how can be map configured
map:
  # Map name, must be configured, you can use colors (example: &a&lOMG &c&nMaRiO &eSUPERMAP)
  name: "&b&lLast Breath"
  # Map Minecraft version, must be configured
  mc-version: 1.16.3
  # Minecraft world name, when will be map placed
  world-name: world
  # Link for download map zip
  world-url: https://upload.hicoria.com/files/qxEd13NF.zip
  # Link of zip resourcepack, when you don't want to use resourcepack set quotation marks only (Default Minecraft launcher java don't support Let's encrypt certificates)
  resource-pack-url: "https://upload.hicoria.com/files/rbAhKexY.zip"
  # SHA1 of resource pack
  resource-pack-sha1: 3980d40646934e544163a359fa0387eaa4cbe017
  # When is set to true, players what don't allow to download resource pack, will be kicked
  deny-join-without-resource-pack: true
  # Link for map website, must be configured
  website: https://bit.ly/35gWAkX
  # Map author, must be configured
  author: Command Builders
  # Player-limit of map, must be configured
  limit: 4
```
