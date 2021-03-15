package eu.thevelda.adventuremapmanager;

import eu.thevelda.adventuremapmanager.commands.MapCommand;
import eu.thevelda.adventuremapmanager.events.PlayerJoin;
import eu.thevelda.adventuremapmanager.events.PlayerLeave;
import eu.thevelda.adventuremapmanager.events.PlayerResourcepackStatus;
import org.apache.commons.io.IOUtils;
import org.apache.commons.text.StringSubstitutor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public final class Main extends JavaPlugin {
    private static Main instance;

    private File messagesConfigFile;
    private FileConfiguration messagesConfig;

    private boolean delete(File file) {
        if (file.isDirectory())
            for (File subfile : file.listFiles())
                if (!delete(subfile))
                    return false;
        if (!file.delete())
            return false;
        return true;
    }

    public static void copyInputStream(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0) {
            out.write(buffer, 0, len);
        }
        in.close();
        out.close();
    }

    public void downloadFile(String fileUrl, String fileName) {
        try {
            URL url = new URL(fileUrl);

            url.openConnection();
            InputStream reader = url.openStream();

            FileOutputStream writer = new FileOutputStream(fileName);
            byte[] buffer = new byte[102400];
            int bytesRead = 0;

            while ((bytesRead = reader.read(buffer)) > 0) {
                writer.write(buffer, 0, bytesRead);
                buffer = new byte[102400];
            }
            writer.close();
            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unZipIt(String worldName) {
        try {
            String zipFolder = worldName + ".zip";
            ZipFile zipFile = new ZipFile(zipFolder);
            Enumeration zipEntries = zipFile.entries();

            File OUTFILEFOLD = new File(worldName);
            if (!OUTFILEFOLD.exists()) {
                OUTFILEFOLD.mkdir();
            }
            String OUTDIR = "./" + worldName + File.separator;
            while (zipEntries.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) zipEntries.nextElement();

                if (zipEntry.isDirectory()) {
                    new File(OUTDIR + zipEntry.getName()).mkdir();
                    continue;
                }
                copyInputStream(zipFile.getInputStream(zipEntry), new BufferedOutputStream(new FileOutputStream(OUTDIR + zipEntry.getName())));
            }
            zipFile.close();
        } catch (IOException ioe) {
            System.err.println("Unhandled exception:");
            ioe.printStackTrace();
            return;
        }
    }

    private void changeSlots(int slots) throws ReflectiveOperationException {
        Method serverGetHandle = getServer().getClass().getDeclaredMethod("getHandle");

        Object playerList = serverGetHandle.invoke(getServer());
        Field maxPlayersField = playerList.getClass().getSuperclass().getDeclaredField("maxPlayers");

        maxPlayersField.setAccessible(true);
        maxPlayersField.set(playerList, slots);

        Properties properties = new Properties();
        File propertiesFile = new File("server.properties");

        try {
            try (InputStream is = new FileInputStream(propertiesFile)) {
                properties.load(is);
            }

            String maxPlayers = Integer.toString(getServer().getMaxPlayers());

            if (properties.getProperty("max-players").equals(maxPlayers)) {
                return;
            }

            getLogger().info("I am saving max player limit into server.properties...");
            properties.setProperty("max-players", maxPlayers);

            try (OutputStream os = new FileOutputStream(propertiesFile)) {
                properties.store(os, "Minecraft server properties");
            }
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "There was an issue with saving max player limit into server.properties", e);
        }
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        URLConnection urlConnection = new URL(url).openConnection();
        urlConnection.addRequestProperty("User-Agent", "Mozilla/5.0");
        InputStream is = urlConnection.getInputStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = IOUtils.toString(rd);
            return new JSONObject(jsonText);
        } finally {
            is.close();
        }
    }

    @Override
    public void onEnable() {
        // Instance
        instance = this;

        // Downloading correct version of PaperMC for map
        String fullPlatformVersion = getServer().getVersion();
        int start = fullPlatformVersion.indexOf("(MC:");
        String mcversion = fullPlatformVersion.substring(start + 5, fullPlatformVersion.length() - 1);

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lAdventureMapManager&r &8&l>&r &aPlugin is enabled. Minecraft server version is: " + mcversion));

        if (mcversion.equals(getConfig().getString("map.mc-version"))) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lAdventureMapManager&r &8&l>&r &aMinecraft server version is same as Minecraft map version. Everything is ok!"));
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lAdventureMapManager&r &8&l>&r &cMinecraft server version is not same as Minecraft map version."));
            if (getConfig().getBoolean("force-map-version")) {
                try {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lAdventureMapManager&r &8&l>&r &aI will replace your server to PaperMC Minecraft version: " + getConfig().getString("map.mc-version")));
                    JSONObject latestJson = readJsonFromUrl("https://papermc.io/api/v1/paper/" + getConfig().getString("map.mc-version"));
                    Integer latestBuild = latestJson.getJSONObject("builds").getInt("latest");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lAdventureMapManager&r &8&l>&r &aPaperMC build number: " + latestBuild + " on Minecraft version: " + getConfig().getString("map.mc-version") + " was found. After downloading server will be stopped. (it's recommended to set-up auto start, if shutdown)"));
                    String latestPaperUrl = "https://papermc.io/api/v1/paper/" + getConfig().getString("map.mc-version") + "/" + latestBuild + "/download";
                    downloadFile(latestPaperUrl, getConfig().getString("server-file-name"));
                    getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                        public void run() {
                            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "stop");
                        }
                    });
                } catch (Exception e) {
                    System.out.println(e);
                    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lAdventureMapManager&r &8&l>&r &cThere was problem with searching PaperMC on Minecraft version: " + getConfig().getString("map.mc-version")));
                }
            } else {
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lAdventureMapManager&r &8&l>&r &cForce set Minecraft PaperMC same as map Minecraft version is disabled."));
            }
        }

        // Register config
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        createMessagesConfig();
        // Loading messages


        // Register events
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerJoin(), this);
        pm.registerEvents(new PlayerLeave(), this);
        pm.registerEvents(new PlayerResourcepackStatus(), this);

        // Register placeholders
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Placeholders().register();
        }

        // Delete old map and download new
        String worldName = getConfig().getString("map.world-name");
        delete(new File(getServer().getWorldContainer(), worldName));
        String worldFileName = worldName + ".zip";
        String worldUrl = getConfig().getString("map.world-url");
        downloadFile(worldUrl, worldFileName);
        unZipIt(worldName);

        // Nastavení limitu hráčů
        try {
            changeSlots(getConfig().getInt("map.limit"));
        } catch (ReflectiveOperationException e) {
            getLogger().log(Level.SEVERE, "There was issue with setting-up player max limit.", e);
        }

        // Register commands
        getCommand("map").setExecutor(new MapCommand());
    }

    public String messageReplacement(String stringToReplace) {
        Map<String, String> replacements = new HashMap<>();
        replacements.put("map_name", this.getConfig().getString("map.name"));
        replacements.put("map_mc_version", this.getConfig().getString("map.mc-version"));
        replacements.put("map_world_name", this.getConfig().getString("map.world-name"));
        replacements.put("map_world_url", this.getConfig().getString("map.world-url"));
        replacements.put("map_resource_pack_url", this.getConfig().getString("map.resource-pack-url"));
        replacements.put("map_website", this.getConfig().getString("map.website"));
        replacements.put("map_author", this.getConfig().getString("map.author"));
        replacements.put("map_limit", this.getConfig().getString("map.limit"));
        StringSubstitutor sub = new StringSubstitutor(replacements);
        return sub.replace(stringToReplace);
    }

    public FileConfiguration getMessagesConfig() {
        return this.messagesConfig;
    }

    private void createMessagesConfig() {
        messagesConfigFile = new File(getDataFolder(), "messages.yml");
        if (!messagesConfigFile.exists()) {
            messagesConfigFile.getParentFile().mkdirs();
            saveResource("messages.yml", false);
        }

        messagesConfig = new YamlConfiguration();
        try {
            messagesConfig.load(messagesConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(
                ChatColor.translateAlternateColorCodes('&', "&5&lAdventureMapManager&r &8&l>&r &cPlugin is disabled."));
        instance = null;
    }

    public static Main getInstance() {
        return instance;
    }
}
