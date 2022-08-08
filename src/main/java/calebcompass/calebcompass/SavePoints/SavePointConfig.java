package calebcompass.calebcompass.SavePoints;

import calebcompass.calebcompass.CalebCompass;
import calebcompass.calebcompass.util.CompassInstance;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class SavePointConfig {

    private static SavePointConfig instance;

    private File savePointFile;

    private FileConfiguration savePointConfig;

    private Plugin server = Bukkit.getPluginManager().getPlugin("CalebCompass");

    private HashMap<String, SavePoint> currentPoints;

    public static SavePointConfig getInstance() {
        if (instance == null) instance = new SavePointConfig();
        return instance;
    }

    public SavePointConfig() {
        currentPoints = new HashMap<>();
        savePointFile = new File(server.getDataFolder(), "savepoints.yml");
        if (!savePointFile.exists()) server.saveResource("savepoints.yml", false);
        savePointConfig = YamlConfiguration.loadConfiguration(savePointFile);
        load();
    }

    public void togglePlayerPoint(UUID uuid, String point, boolean effect) {
        CompassInstance.getInstance().getCompassConfig().set("playerdata." + uuid.toString() + ".activepoints." + point, effect);
        CompassInstance.getInstance().saveData();
    }

    public void addSave(SavePoint newP) {
        this.currentPoints.put(newP.getName(), newP);
    }

    public ArrayList<SavePoint> getCurrentPoints() {
        return new ArrayList<SavePoint>(currentPoints.values());
    }

    public void removeSave(SavePoint save) {
        this.currentPoints.remove(save.getName());
        savePointConfig.set("points." + save.getName(), null);
    }

    public SavePoint getPointFromName(String name) {
        return currentPoints.get(name);
    }

    public boolean pointExists(String pointName) {
        return pointExistsExplicit(pointName);
    }

    public boolean pointExistsExplicit(String pointName) {
        return currentPoints.containsKey(pointName);
    }

    public void serialiseValues() {
        for (SavePoint p : currentPoints.values()) {
            savePointConfig.set("points." + p.getName() + ".world", p.getLoc1().getWorld().getName());
            savePointConfig.set("points." + p.getName() + ".x", p.getLoc1().getBlockX());
            savePointConfig.set("points." + p.getName() + ".y", p.getLoc1().getBlockY());
            savePointConfig.set("points." + p.getName() + ".z", p.getLoc1().getBlockZ());
            savePointConfig.set("points." + p.getName() + ".symbol_regular", p.getSymbol().replace("§", "&"));
            savePointConfig.set("points." + p.getName() + ".symbol_hovered", p.getSymbolHov().replace("§", "&"));
            savePointConfig.set("points." + p.getName() + ".global", p.isGlobal());
            savePointConfig.set("points." + p.getName() + ".range", p.getMaxRange());
        }
    }

    public void load() {
        this.currentPoints = new HashMap<>();
        savePointConfig = YamlConfiguration.loadConfiguration(savePointFile);
        if (savePointConfig.getConfigurationSection("points") == null) return;
        for (String load : savePointConfig.getConfigurationSection("points").getKeys(false)) {
            try {
                String curLoad = "points." + load +  ".";
                if (savePointConfig.getString(curLoad + "symbol_regular") == null) {
                    savePointConfig.set(curLoad + "symbol_regular" ,CalebCompass.getConfigManager().getString("regular.waypoint"));
                }
                if (savePointConfig.getString(curLoad + "symbol_hovered") == null) savePointConfig.set(curLoad + "symbol_hovered" ,CalebCompass.getConfigManager().getString("hovered.waypoint"));
                SavePoint point = new SavePoint(new Location(Bukkit.getWorld(savePointConfig.getString(curLoad + "world")),
                        Integer.valueOf(savePointConfig.getString(curLoad + "x")),
                        Integer.valueOf(savePointConfig.getString(curLoad + "y")),
                        Integer.valueOf(savePointConfig.getString(curLoad + "z"))),
                        load,
                        savePointConfig.getString(curLoad + "symbol_regular"),
                        savePointConfig.getString(curLoad + "symbol_hovered"));
                if (savePointConfig.isBoolean(curLoad + "global")) point.setGlobal(savePointConfig.getBoolean(curLoad + "global"));
                if(savePointConfig.isInt(curLoad + "range")) point.setMaxRange(savePointConfig.getInt(curLoad + "range"));
                this.currentPoints.put(load, point);
            } catch (Exception e) {
            }
        }
        saveData();
    }

    public void saveData() {
        serialiseValues();
        try {
            savePointConfig.save(savePointFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
