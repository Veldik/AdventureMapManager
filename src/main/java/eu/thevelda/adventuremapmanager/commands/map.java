package eu.thevelda.adventuremapmanager.commands;

import eu.thevelda.adventuremapmanager.main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class map implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        String message = ChatColor.translateAlternateColorCodes('&', "&eNázev mapy: " + main.getInstance().getConfig().getString("map.name") + "\n&r&eVytvořena pro verzi: " + main.getInstance().getConfig().getString("map.mc-version") + "\n&r&eMapu vytvořili: " + main.getInstance().getConfig().getString("map.author") + "\n&r&eOdkaz: " + main.getInstance().getConfig().getString("map.website") + "\n&r&eLimit hráčů: " + main.getInstance().getConfig().getString("map.limit"));
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage(message);
        } else {
            System.out.println(message);
        }
        return false;
    }
}
