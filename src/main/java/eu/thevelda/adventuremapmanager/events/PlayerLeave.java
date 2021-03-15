package eu.thevelda.adventuremapmanager.events;

import eu.thevelda.adventuremapmanager.Main;
import org.apache.commons.text.StringSubstitutor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerLeave implements Listener {
    @EventHandler
    void onPlayerLeave(PlayerQuitEvent e) {
        int onlinePlayers = Bukkit.getOnlinePlayers().size();
        int realOnlinePlayers = onlinePlayers - 1;
        Player player = e.getPlayer();
        Map<String, String> replacements = new HashMap<>();
        replacements.put("player_name", player.getDisplayName());
        replacements.put("online_players", String.valueOf(realOnlinePlayers));
        StringSubstitutor sub = new StringSubstitutor(replacements);
        String leaveMessage = ChatColor.translateAlternateColorCodes('&', sub.replace(Main.getInstance().messageReplacement(Main.getInstance().getMessagesConfig().getString("global-leave-message"))));
        e.setQuitMessage(leaveMessage);
        if (realOnlinePlayers == 0) {
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),"stop");
        }
    }
}
