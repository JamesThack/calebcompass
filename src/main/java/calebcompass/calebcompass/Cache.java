package calebcompass.calebcompass;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;

import java.io.File;

public class Cache {

    private static Cache cache;
    private String north;
    private String northEast;
    private String east;
    private String southEast;
    private String south;
    private String southWest;
    private String west;
    private String northWest;
    private String pointer;
    private String middleSymbol;
    private String questColour;
    private String questSymbol;
    private String mid;
    private String start;
    private String end;
    private String barCol;
    private String style;
    private File compassFile = new File(Bukkit.getPluginManager().getPlugin("CalebCompass").getDataFolder(), "compass.yml");
    private FileConfiguration compassConfig;

    public Cache () {
        if(!compassFile.exists()) {
            Bukkit.getPluginManager().getPlugin("CalebCompass").saveResource("compass.yml", false);
        }
        this.compassConfig = YamlConfiguration.loadConfiguration(compassFile);
        update();

    }
    public static Cache getCache() {
        if (cache == null) cache = new Cache();
        return cache;
    }

    public void update() {
        if(!compassFile.exists()) {
            Bukkit.getPluginManager().getPlugin("CalebCompass").saveResource("compass.yml", false);
        }
        compassConfig = YamlConfiguration.loadConfiguration(compassFile);
        validate();
        this.north = compassConfig.getString("directions.north").replace("&","§") + "N";
        this.northEast = compassConfig.getString("directions.northeast").replace("&","§") + "NE";
        this.east = compassConfig.getString("directions.east").replace("&","§") + "E";
        this.southEast = compassConfig.getString("directions.southeast").replace("&","§") + "SE";
        this.south = compassConfig.getString("directions.south").replace("&","§") + "S";
        this.southWest = compassConfig.getString("directions.southwest").replace("&","§") + "SW";
        this.west = compassConfig.getString("directions.west").replace("&","§") + "W";
        this.northWest = compassConfig.getString("directions.northwest").replace("&","§") + "NW";
        this.pointer = compassConfig.getString("gencompass.pointercolour").replace("&", "§");
        this.middleSymbol = compassConfig.getString("gencompass.emptysymbol").replace("&", "§");
        this.questColour = compassConfig.getString("gencompass.questcolour").replace("&", "§");
        this.questSymbol = compassConfig.getString("gencompass.questsymbol").replace("&", "§");
        this.mid = compassConfig.getString("gencompass.emptycolour").replace("&", "§");
        this.start = compassConfig.getString("gencompass.start").replace("&", "§");
        this.end = compassConfig.getString("gencompass.end").replace("&", "§");
        this.barCol = compassConfig.getString("gencompass.barcolour");
        this.style = compassConfig.getString("gencompass.barstyle");

    }

    private void validate() {
        if (compassConfig.getString("directions.north") == null) compassConfig.set("directions.north", "&3&l");
        if (compassConfig.getString("directions.northeast") == null) compassConfig.set("directions.northeast", "&7&l");
        if (compassConfig.getString("directions.east") == null) compassConfig.set("directions.east", "&3&l");
        if (compassConfig.getString("directions.southeast") == null) compassConfig.set("directions.southeast", "&7&l");
        if (compassConfig.getString("directions.south") == null) compassConfig.set("directions.south", "&3&l");
        if (compassConfig.getString("directions.southwest") == null) compassConfig.set("directions.southwest", "&7&l");
        if (compassConfig.getString("directions.west") == null) compassConfig.set("directions.west", "&3&l");
        if (compassConfig.getString("directions.northwest") == null) compassConfig.set("directions.northwest", "&7&l");
        if (compassConfig.getString("gencompass.pointercolour") == null) compassConfig.set("gencompass.pointercolour", "&5&l");
        if (compassConfig.getString("gencompass.emptysymbol") == null) compassConfig.set("gencompass.emptysymbol", "⬟");
        if (compassConfig.getString("gencompass.questcolour") == null) compassConfig.set("gencompass.questcolour", "&4&l");
        if (compassConfig.getString("gencompass.questsymbol") == null) compassConfig.set("gencompass.questsymbol", " !!! ");
        if (compassConfig.getString("gencompass.emptycolour") == null) compassConfig.set("gencompass.emptycolour", "&f&l");
        if (compassConfig.getString("gencompass.start") == null) compassConfig.set("gencompass.start", "&5&l≪─ ");
        if (compassConfig.getString("gencompass.end") == null) compassConfig.set("gencompass.end", " &5&l─≫");
        if (compassConfig.getString("gencompass.barcolour") == null) compassConfig.set("gencompass.barcolour", "Purple");
        if (compassConfig.getString("gencompass.barstyle") == null) compassConfig.set("gencompass.barstyle", "Solid");

    }

    public String getNorth() {
        return north;
    }

    public String getNorthEast() {
        return northEast;
    }

    public String getEast() {
        return east;
    }

    public String getSouthEast() {
        return southEast;
    }

    public String getSouth() {
        return south;
    }

    public String getSouthWest() {
        return southWest;
    }

    public String getWest() {
        return west;
    }

    public String getNorthWest() {
        return northWest;
    }

    public String getPointer() {
        return pointer;
    }

    public String getMid() {
        return mid;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getBarCol() {
        return barCol;
    }

    public String getStyle() {
        return style;
    }

    public String getMiddleSymbol() {
        return middleSymbol;
    }

    public String getQuestColour() {
        return questColour;
    }

    public String getQuestSymbol() {
        return questSymbol;
    }

    public void save() {
        try {
            compassConfig.save(compassFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
