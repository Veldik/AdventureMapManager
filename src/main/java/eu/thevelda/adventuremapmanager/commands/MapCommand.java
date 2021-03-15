package eu.thevelda.adventuremapmanager.commands;

import eu.thevelda.adventuremapmanager.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MapCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        String message = ChatColor.translateAlternateColorCodes('&', Main.getInstance().messageReplacement(Main.getInstance().getMessagesConfig().getString("map-command-message")));
        sender.sendMessage(message);
        return false;
    }
}
