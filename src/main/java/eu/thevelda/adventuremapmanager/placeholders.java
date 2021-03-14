package eu.thevelda.adventuremapmanager;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class placeholders extends PlaceholderExpansion {

    private main plugin;

    @Override
    public boolean canRegister() {
        return (plugin = (main) Bukkit.getPluginManager().getPlugin(getRequiredPlugin())) != null;
    }

    @Override
    public String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public String getIdentifier() {
        return "AdventureMapManager";
    }

    @Override
    public String getRequiredPlugin() {
        return "AdventureMapManager";
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    // Variables
    @Override
    public String onPlaceholderRequest(Player p, String identifier) {

        if (identifier.equals("map_name")) {
            return plugin.getConfig().getString("map_name", plugin.getConfig().getString("map.name"));
        }
        if (identifier.equals("map_mc_version")) {
            return plugin.getConfig().getString("map_mc_version", plugin.getConfig().getString("map.mc-version"));
        }
        if (identifier.equals("map_world_name")) {
            return plugin.getConfig().getString("map_world_name", plugin.getConfig().getString("map.world-name"));
        }
        if (identifier.equals("map_world_url")) {
            return plugin.getConfig().getString("map_world_url", plugin.getConfig().getString("map.world-url"));
        }
        if (identifier.equals("map_resource_pack_url")) {
            return plugin.getConfig().getString("map_resource_pack_url", plugin.getConfig().getString("map.resource-pack-url"));
        }
        if (identifier.equals("map_website")) {
            return plugin.getConfig().getString("map_website", plugin.getConfig().getString("map.website"));
        }
        if (identifier.equals("map_author")) {
            return plugin.getConfig().getString("map_author", plugin.getConfig().getString("map.author"));
        }
        if (identifier.equals("map_limit")) {
            return plugin.getConfig().getString("map_limit", plugin.getConfig().getString("map.limit"));
        }
        return null;
    }
}