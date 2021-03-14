package eu.thevelda.adventuremapmanager.events;

import eu.thevelda.adventuremapmanager.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Timer;
import java.util.TimerTask;

public class PlayerJoin implements Listener {

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent e) {
        int onlinePlayers = Bukkit.getOnlinePlayers().size();
        Player player = e.getPlayer();
        e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', "&a" + player.getDisplayName() + " se připojil na adventure mapu!&r &7(" + onlinePlayers + "/" + Bukkit.getServer().getMaxPlayers() + ")"));
        if (!main.getInstance().getConfig().getString("map.resource-pack-url").isEmpty()) {
            player.setResourcePack(main.getInstance().getConfig().getString("map.resource-pack-url"), main.getInstance().getConfig().getString("map.resource-pack-sha1"));
        }
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eVítej na Adventure mapě: " + main.getInstance().getConfig().getString("map.name") + "\n&r&eVytvořena pro verzi: " + main.getInstance().getConfig().getString("map.mc-version") + "\n&r&eMapu vytvořili: " + main.getInstance().getConfig().getString("map.author") + "\n&r&eOdkaz: " + main.getInstance().getConfig().getString("map.website")));
            }
        }, 1000);
    }
}
