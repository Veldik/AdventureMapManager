package eu.thevelda.adventuremapmanager.events;

import eu.thevelda.adventuremapmanager.main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

public class PlayerResourcepackStatus implements Listener {
    @EventHandler
    public void onResourcepackStatusEvent(PlayerResourcePackStatusEvent e) {
        Player player = e.getPlayer();
        if (main.getInstance().getConfig().getBoolean("map.deny-join-without-resource-pack")) {
            if (e.getStatus() == PlayerResourcePackStatusEvent.Status.DECLINED) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cMusíš přijmout stáhnutí resourcepacku, jinak si tuto mapu nezahraješ."));
                player.kickPlayer("Musíš přijmout stáhnutí resourcepacku, jinak si tuto mapu nezahraješ.");
            }
        }
    }
}
