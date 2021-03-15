package eu.thevelda.adventuremapmanager.events;

import eu.thevelda.adventuremapmanager.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

public class PlayerResourcepackStatus implements Listener {
    @EventHandler
    public void onResourcepackStatusEvent(PlayerResourcePackStatusEvent e) {
        Player player = e.getPlayer();
        if (Main.getInstance().getConfig().getBoolean("map.deny-join-without-resource-pack")) {
            if (e.getStatus() == PlayerResourcePackStatusEvent.Status.DECLINED) {
                String message = ChatColor.translateAlternateColorCodes('&', Main.getInstance().messageReplacement(Main.getInstance().getMessagesConfig().getString("kick-without-resourcepack")));
                player.sendMessage(message);
                player.kickPlayer(message);
            }
        }
    }
}
