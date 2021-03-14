package eu.thevelda.adventuremapmanager.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener {
    @EventHandler
    void onPlayerLeave(PlayerQuitEvent e) {
        int onlinePlayers = Bukkit.getOnlinePlayers().size();
        int realOnlinePlayers = onlinePlayers - 1;
        Player player = e.getPlayer();
        e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', "&c" + player.getDisplayName() + " se odpojil z adventure mapy!&r &7(" + realOnlinePlayers + "/" + Bukkit.getServer().getMaxPlayers() + ")"));
        if (realOnlinePlayers == 0) {
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),"stop");
        }
    }
}
