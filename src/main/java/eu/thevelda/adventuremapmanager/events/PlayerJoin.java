package eu.thevelda.adventuremapmanager.events;

import eu.thevelda.adventuremapmanager.Main;
import org.apache.commons.text.StringSubstitutor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerJoin implements Listener {

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent e) {
        int onlinePlayers = Bukkit.getOnlinePlayers().size();
        Player player = e.getPlayer();
        Map<String, String> replacements = new HashMap<>();
        replacements.put("player_name", player.getDisplayName());
        replacements.put("online_players", String.valueOf(onlinePlayers));
        StringSubstitutor sub = new StringSubstitutor(replacements);
        String joinMessage = ChatColor.translateAlternateColorCodes('&', sub.replace(Main.getInstance().messageReplacement(Main.getInstance().getMessagesConfig().getString("global-join-message"))));
        e.setJoinMessage(joinMessage);
        if (!Main.getInstance().getConfig().getString("map.resource-pack-url").isEmpty()) {
            player.setResourcePack(Main.getInstance().getConfig().getString("map.resource-pack-url"), Main.getInstance().getConfig().getString("map.resource-pack-sha1"));
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().messageReplacement(Main.getInstance().getMessagesConfig().getString("welcome-message"))));
        }, 20L);
    }
}
