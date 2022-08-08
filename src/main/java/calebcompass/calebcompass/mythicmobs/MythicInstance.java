package calebcompass.calebcompass.mythicmobs;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class MythicInstance {

    private static MythicInstance instance;
    private static Plugin plugin = Bukkit.getPluginManager().getPlugin("CalebCompass");
    public static boolean isPluginInstalled = false;

    private File subfolder = new File(plugin.getDataFolder(), "mythicmobs");
    private File mythicFile = new File(subfolder, "mobpoints.yml");
    private YamlConfiguration config;

    private int maxRange;
    private boolean defaultMobShow;
    private String defaultRegular;
    private String defaultHovered;
    private HashMap<String, String> regularOverrides;
    private HashMap<String, String> hoveredOverride;

    public static MythicInstance getInstance() {
        if (instance == null) instance = new MythicInstance();
        return instance;
    }

    public MythicInstance() {
        if (!mythicFile.exists())plugin.saveResource("mythicmobs" + File.separator + "mobpoints.yml", false);
        config = YamlConfiguration.loadConfiguration(mythicFile);
        isPluginInstalled = true;
        load();
    }

    public int getMaxRange() {
        return maxRange;
    }

    public void load() {
        if (!mythicFile.exists())plugin.saveResource("mythicmobs" + File.separator + "mobpoints.yml", false);
        config = YamlConfiguration.loadConfiguration(mythicFile);

        setupDefaults();
        loadRegularOverrides();
        loadHoveredOverrides();

        maxRange = config.getInt("default-settings.mob-detection-range");
        defaultMobShow = config.getBoolean("default-settings.show-all-mobs-by-default");
        defaultRegular = config.getString("default-settings.default-symbol-regular");
        defaultHovered = config.getString("default-settings.default-symbol-hovered");

    }
    public void save() {
        try {
            config.save(mythicFile);
        } catch (Exception e) {}
    }

    private void loadRegularOverrides() {
        regularOverrides = new HashMap<>();
        if (config.getConfigurationSection("custom-overrides.regular") == null) return;
            for (String str : config.getConfigurationSection("custom-overrides.regular").getKeys(false)) {
                String curPath = "custom-overrides.regular." + str;
                regularOverrides.put(str, config.getString(curPath));
            }
    }

    private void loadHoveredOverrides() {
        hoveredOverride = new HashMap<>();
        if (config.getConfigurationSection("custom-overrides.hovered") == null) return;
        for (String str : config.getConfigurationSection("custom-overrides.hovered").getKeys(false)) {
            String curPath = "custom-overrides.hovered." + str;
            hoveredOverride.put(str,  config.getString(curPath));
        }
    }
    private void setupDefaults() {
        String curPath = "default-settings.";
        setDefaultValue(curPath + "show-all-mobs-by-default", false);
        setDefaultValue(curPath + "default-symbol-regular", "&4&l !! ");
        setDefaultValue(curPath + "default-symbol-hovered", "&b&l !! ");
        setDefaultValue(curPath + "mob-detection-range", 11);

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

    public YamlConfiguration getConfig() {
        return config;
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

    public String getRegularOverride(String mythicMob) {
        if (regularOverrides.containsKey(mythicMob)) return regularOverrides.get(mythicMob);
        return defaultRegular;
    }

    public String getHoveredOverride(String mythicMob) {
        if (hoveredOverride.containsKey(mythicMob)) return hoveredOverride.get(mythicMob);
        return defaultHovered;
    }

    public boolean hasCustomOverride(String mobName) {
        if (regularOverrides.containsKey(mobName) || hoveredOverride.containsKey(mobName)) return true;
        return false;
    }
}
