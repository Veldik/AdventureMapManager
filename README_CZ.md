# AdventureMapManager
Plugin pro správu adventure map na Minecraft serveru.  
Nastavení mapy probíhá v config.yml  
Český překlad pluginu, na spodu tohoto README.
## Funkce:
- Vypnutí serveru, když jej všichni opustí
- Znovu stáhnutí mapy z odkazu nastaveném v configu na server po znovu spuštění
- Oznámení o momentální mapě při připojení
- Nastaví limit hráčů podle limitu nastaveném v configu
- Nastavení automatického stažení paper serveru podle verze, na kterou je vytvořena mapa
- Stažení resourcepacku, možnost zakázat v configu připojení bez resourcepacku

## Commands:
- `/map` Vypíše základní informace o aktuální mapě

## Placeholders:
- `%AdventureMapManager_map_name%` Vrátí název aktuální mapy
- `%AdventureMapManager_map_mc_version%` Vrátí verzi Minecraftu, pro kterou je mapa vytvořena
- `%AdventureMapManager_map_world_name%` Vrátí název světa, ve kterém je mapa nahrána
- `%AdventureMapManager_map_world_url%` Vrátí odkaz pro stažení světa mapy
- `%AdventureMapManager_map_resource_pack_url%` Vrátí odkaz pro resource packu mapy
- `%AdventureMapManager_map_website%` Vrátí odkaz na web mapy
- `%AdventureMapManager_map_author%` Vrátí autora mapy
- `%AdventureMapManager_map_limit%` Vrátí maximum hráčů pro mapu

## config.yml
```yaml
# Název jar souboru, při spouštění. Používá se pro nahrazení při automatickém stažení paperu podle verez mapy. Musí být definován pokud je force-map-version nastaveno na true.
server-file-name: server.jar
# Když je nastaveno na true, automaticky se pokusí stáhnout server podle verze mapy
force-map-version: false
# Tohle je příklad, jak může být mapa nastavena
map:
  # Název mapy, musí být nastaven, mohou být použity barvy
  name: "&b&lLast Breath"
  # Verze mapy, musí být nastavena
  mc-version: 1.16.3
  # Název světa mapy, používá se pro umístění nově stažené mapy
  world-name: world
  # Odkaz pro stažení zipu mapy
  world-url: https://upload.hicoria.com/files/qxEd13NF.zip
  # Odkaz na zip resourcepacku, pokud nechcete použít resourcepack, nechte pouze prázdné uvozovky
  resource-pack-url: "https://upload.hicoria.com/files/rbAhKexY.zip"
  # SHA1 resourcepacku
  resource-pack-sha1: 3980d40646934e544163a359fa0387eaa4cbe017
  # Pokud je nastaveno na true hráči, kteří zakážou stáhnutí resourcepacku budou vyhozeni.
  deny-join-without-resource-pack: true
  # Odkaz na web mapy, musí být nastaven
  website: https://bit.ly/35gWAkX
  # Autor mapy, musí být nastaven
  author: Command Builders
  # limit hráčů mapy, musí být nastaven
  limit: 4
```
## messages.yml
```yaml
# V tomto configu můžeš použít tyto proměnné:
# ${map_name} = Název mapy
# ${map_mc_version} = Minecraft verze mapy
# ${map_world_name} = Svět, ve kterém je mapa
# ${map_world_url} = Odkaz na svět mapy
# ${map_resource_pack_url} = Odkaz na resource pack mapy
# ${map_website} = Odkaz na web mapy
# ${map_author} = Autor mapy
# ${map_limit} = Maximum hráčů pro tuto mapu

# Zpráva o připojení hráče, kterou všichni vidí
# Dodatečné proměnné:
# ${player_name} = Jméno hráče
# ${online_players} = Počet online hráčů
global-join-message: "&a${player_name} se připojil na adventure mapu!&r &7(${online_players}/${map_limit})"
# Zpráva o odpojení hráče, kterou všichni vidí
# Dodatečné proměnné:
# ${player_name} = Jméno hráče
# ${online_players} = Počet online hráčů
global-leave-message: "&c${player_name} se odpojil z adventure mapy!&r &7(${online_players}/${map_limit})"
# Zpráva, kterou vidí hráč po připojení na server
welcome-message: "&eVítej na Adventure mapě: ${map_name}\n&r&eVytvořena pro verzi: ${map_mc_version}\n&r&eMapu vytvořili:${map_author}\n&r&eOdkaz: ${map_website}"
# Zpráva, která se zobrazí pro napsaní /map
map-command-message: "&eNázev mapy: ${map_name}\n&r&eVytvořena pro verzi: ${map_mc_version}\n&r&eMapu vytvořili: ${map_author}\n&r&eOdkaz: ${map_website}\n&r&eLimit hráčů: ${map_limit}"
# Zpráva, která se zobrazí, když nemá resourcepack
kick-without-resourcepack: "&cMusíš přijmout stáhnutí resourcepacku, jinak si tuto mapu nezahraješ."
```