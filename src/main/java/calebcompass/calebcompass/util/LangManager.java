package calebcompass.calebcompass.util;

import calebcompass.calebcompass.CalebCompass;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class LangManager {

    private File langFile;

    private FileConfiguration langConfig;

    private static LangManager instance;

    public static String PREFIX;

    public static LangManager getInstance() {
        if (instance == null) instance = new LangManager();
        return instance;
    }

    public LangManager() {
        init();
        setup();
    }

    public void init() {
        CalebCompass instancel = (CalebCompass) Bukkit.getPluginManager().getPlugin("CalebCompass");
        langFile = new File(instancel.getDataFolder(), "lang.yml");
        if (!langFile.exists()) instancel.saveResource("lang.yml", false);
        langConfig = YamlConfiguration.loadConfiguration(langFile);
    }

    public void setup() {
        init();
        setupDefault();
        PREFIX = MessageUtil.colourise(getString("prefix")); // update the prefix
    }

    public String getString(String path) {
        return langConfig.getString(path);
    }


    private void setupDefault() {
        setDefaultValue("prefix", "&7[&eCaleb Compass&7]&r: ");
        setDefaultValue("no-permission", "You do not have permission for this command!");
        setDefaultValue("player-not-found", "Player not found!");
        setDefaultValue("config-reload", "Config has been loaded into the game");
        setDefaultValue("page-not-found", "Please enter a valid page number!");
        setDefaultValue("help", "Type '/calebcompass help' for more info");
        setDefaultValue("missing-toggle", "Enter either enable/disable");
        setDefaultValue("waypoint-no-valid", "Enter a valid waypoint");
        setDefaultValue("help-command-header-1", "Commands, page 1/2:");
        setDefaultValue("help-command-track", "&4/calebcompass track O:(player) x y z&r Track a set of coordinates on the compass");
        setDefaultValue("help-command-clear", "&4/calebcompass clear O:(player)&r Clear current track");
        setDefaultValue("help-command-hide", "&4/calebcompass hide O:(player)&r Hide the compass");
        setDefaultValue("help-command-show", "&4/calebcompass show O:(player)&r Show the compass");
        setDefaultValue("help-command-save", "&4/calebcompass save (name)&r Save a new waypoint for the compass");
        setDefaultValue("help-command-header-2", "Commands, page 2/2:");
        setDefaultValue("help-command-remove", "&4/calebcompass remove (waypoint)&r Remove a waypoint");
        setDefaultValue("help-command-toggle", "&4/calebcompass toggle O:(player) (waypoint) (enable/disable)&r Toggle viewing a waypoint");
        setDefaultValue("help-command-focus", "&4/calebcompass focus&r Focus your quest marker on a specific waypoint");
        setDefaultValue("help-command-waypoints", "&4/calebcompass waypoints (page)&r List all active waypoints enabled for you");
        setDefaultValue("help-command-footer", "&4Any arguments marked with O: are optional");
        setDefaultValue("help-page-not-found", "Page not found, try /calebcompass help");
        setDefaultValue("track-from-player", "You must be a player to use this command, use /calebcompass track (player) x y z:");
        setDefaultValue("track-point-added", "Successfully added track point");
        setDefaultValue("track-point-other-added", "Successfully added track point for the player %player%");
        setDefaultValue("track-point-removed", "Successfully removed track point");
        setDefaultValue("track-point-other-removed", "Successfully removed track point for the player %player%");
        setDefaultValue("compass-hide", "Hid compass");
        setDefaultValue("compass-other-hide", "Hid compass for player %player%");
        setDefaultValue("compass-show", "Showing compass");
        setDefaultValue("compass-other-show", "Showing compass for player %player%");
        setDefaultValue("point-already-exist", "A point with this name already exists");
        setDefaultValue("point-saved", "Saved point");
        setDefaultValue("point-not-found", "No point found with this name");
        setDefaultValue("point-removed", "Removed point");
        setDefaultValue("point-toggled", "Toggled point %point% %toggle%");
        setDefaultValue("point-other-toggled", "Toggled point %point% for player %player% %toggle%");
        setDefaultValue("point-not-looked", "No point found, please look at a waypoint!");
        setDefaultValue("focus-changed", "Changed focus");
        setDefaultValue("active-points-list", "Current active points page %page% :");
        setDefaultValue("active-point", "&7&l%name% &r&eX: %loc-x% &eY: %loc-y% &eZ: %loc-z% &eSymbol: %symbol%");

        save();
    }


    private void setDefaultValue(String path, String value) {
        if (langConfig.getString(path) != null) return;
        langConfig.set(path, value);
    }


    public void save() {
        try {
            langConfig.save(langFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
