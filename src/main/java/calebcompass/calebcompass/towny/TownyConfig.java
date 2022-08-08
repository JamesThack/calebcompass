package calebcompass.calebcompass.towny;

import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyObject;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class TownyConfig {

    private static TownyConfig instance;
    private static Plugin plugin = Bukkit.getPluginManager().getPlugin("CalebCompass");
    public static boolean isPluginInstalled = false;

    private File subfolder = new File(plugin.getDataFolder(), "towny");
    private File mythicFile = new File(subfolder, "towny.yml");
    private YamlConfiguration config;

    private int maxRange;
    private boolean defaultMobShow;
    private String defaultRegular;
    private String defaultHovered;
    private HashMap<String, TownObject> regularOverrides;

    public static TownyConfig getInstance() {
        if (instance == null) instance = new TownyConfig();
        return instance;
    }

    public TownyConfig() {
        if (!mythicFile.exists())plugin.saveResource("towny" + File.separator + "towny.yml", false);
        config = YamlConfiguration.loadConfiguration(mythicFile);
        isPluginInstalled = true;
        load();
    }

    public TownObject getTown(String townName) {
        return regularOverrides.get(townName);
    }

    public void load() {
        if (!mythicFile.exists())plugin.saveResource("towny" + File.separator + "towny.yml", false);
        config = YamlConfiguration.loadConfiguration(mythicFile);

        setupDefaults();
        loadRegularOverrides();

        maxRange = config.getInt("default-town-range");
        defaultMobShow = config.getBoolean("show-towns-by-default");
        defaultRegular = config.getString("default-town-symbol");
        defaultHovered = config.getString("default-town-symbol-hovered");

    }
    public void save() {
        try {
            config.save(mythicFile);
        } catch (Exception e) {}
    }

    private void loadRegularOverrides() {
        regularOverrides = new HashMap<>();
        if (config.getConfigurationSection("custom-town-overrides") == null) return;
        for (String str : config.getConfigurationSection("custom-town-overrides").getKeys(false)) {
            try {
                String curPath = "custom-town-overrides." + str;
                regularOverrides.put(str, new TownObject(
                        config.getString(curPath + ".town-symbol"),
                        config.getString(curPath + ".town-symbol-hovered"),
                        config.getInt(curPath + ".town-range")));
            } catch (Exception e) {
                System.out.println("[CalebCompass]: Error loading town! " + str  + " Skipping over");
                e.printStackTrace();
            }
        }
    }

    private void setupDefaults() {
        setDefaultValue( "show-towns-by-default", false);
        setDefaultValue("default-town-symbol-hovered", " §b§lT ");
        setDefaultValue("default-town-symbol", " §a§lT ");
        setDefaultValue("default-town-range", 50);

        save();
    }

    private void setDefaultValue(String loc, String val) {
        if (config.getString(loc) != null) return;
        config.set(loc, val);
    }

    private void setDefaultValue(String loc, int va) {
        if (config.getInt(loc) >0 ) return;
        config.set(loc, va);
    }

    private void setDefaultValue(String loc, boolean value) {
        if (config.isBoolean(loc)) return;
        config.set(loc, value);
    }

    public boolean isDefaultMobShow() {
        return defaultMobShow;
    }

    public String getDefaultRegular() {
        return defaultRegular;
    }

    public String getDefaultHovered() {
        return defaultHovered;
    }

    public int getMaxRange() {
        return maxRange;
    }
}

