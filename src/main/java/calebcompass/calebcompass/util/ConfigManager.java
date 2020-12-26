package calebcompass.calebcompass.util;

import calebcompass.calebcompass.CalebCompass;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;

public class ConfigManager {

	private File compassFile;

	private FileConfiguration compassConfig;

	private static ConfigManager instance;

	private Material focusItem;

	private ArrayList<String> bannedWorlds;

	public static ConfigManager getInstance() {
		if (instance == null) instance = new ConfigManager();
		return instance;
	}

	public ConfigManager() {
		init();
		setup();
	}

	public void init() {
		CalebCompass instancec = (CalebCompass) Bukkit.getPluginManager().getPlugin("CalebCompass");
		compassFile = new File(instancec.getDataFolder(), "compass.yml");
		if (!compassFile.exists()) instancec.saveResource("compass.yml", false);
		compassConfig = YamlConfiguration.loadConfiguration(compassFile);
	}

	public void setup() {
		init();
		setupDefault();

		for (Symbol symbol : Symbol.values()) {
			setupValues(symbol);
		}

		Util.setSymbolStart(getString("symbol-start"));
		Util.setSymbolEnd(getString("symbol-end"));

		Util.setBarColor(getString("bar-color"));
		Util.setBarStyle(getString("bar-style"));

		try {
			if (compassConfig.getString("focus-item").equalsIgnoreCase("NOITEM")) focusItem = null;
			else focusItem = Material.valueOf(compassConfig.getString("focus-item").toUpperCase());
		} catch (Exception e) {
			System.out.println("Error, item " + compassConfig.getString("focus-item") + " is not recognised! Use NOITEM if you want this feature disabled");
			focusItem = null;
		}

		bannedWorlds = new ArrayList<>();
		for (String cur : compassConfig.getConfigurationSection("banned-worlds").getKeys(false)) {
			if (compassConfig.getBoolean("banned-worlds." + cur)) {
				bannedWorlds.add(cur);
			}
		}
	}

	private void setupValues(Symbol symbol) {
		Util.getRegular().put(symbol, getString("regular." + symbol.getName()));
		Util.getHovered().put(symbol, getString("hovered." + symbol.getName()));
	}

	public boolean isWorldBanned(String world) {
		return bannedWorlds.contains(world);
	}

	public String getString(String path) {
		return compassConfig.getString(path);
	}

	private void setupDefault() {
		String regPath = "regular.";
		String hovPath = "hovered.";

		setDefaultValue(regPath + Symbol.NORTH.getName(), "&e&l" + "N");
		setDefaultValue(regPath + Symbol.NORTH_EAST.getName(), "&6&l" + "NE");
		setDefaultValue(regPath + Symbol.EAST.getName(), "&e&l" + "E");
		setDefaultValue(regPath + Symbol.SOUTH_EAST.getName(), "&6&l" + "SE");
		setDefaultValue(regPath + Symbol.SOUTH.getName(), "&e&l" + "S");
		setDefaultValue(regPath + Symbol.SOUTH_WEST.getName(), "&6&l" + "SW");
		setDefaultValue(regPath + Symbol.WEST.getName(), "&e&l" + "W");
		setDefaultValue(regPath + Symbol.NORTH_WEST.getName(), "&6&l" + "NW");
		setDefaultValue(regPath + Symbol.TRACKER.getName(), "&a&l" + " !!! ");
		setDefaultValue(regPath + Symbol.WAYPOINT.getName(), "&c&l" + " !!! ");
		setDefaultValue(regPath + Symbol.FILLED.getName(), "&#ffffff&l" + "⬟");

		setDefaultValue(hovPath + Symbol.NORTH.getName(), "&b&l" + "N");
		setDefaultValue(hovPath + Symbol.NORTH_EAST.getName(), "&b&l" + "NE");
		setDefaultValue(hovPath + Symbol.EAST.getName(), "&b&l" + "E");
		setDefaultValue(hovPath + Symbol.SOUTH_EAST.getName(), "&b&l" + "SE");
		setDefaultValue(hovPath + Symbol.SOUTH.getName(), "&b&l" + "S");
		setDefaultValue(hovPath + Symbol.SOUTH_WEST.getName(), "&b&l" + "SW");
		setDefaultValue(hovPath + Symbol.WEST.getName(), "&b&l" + "W");
		setDefaultValue(hovPath + Symbol.NORTH_WEST.getName(), "&b&l" + "NW");
		setDefaultValue(hovPath + Symbol.TRACKER.getName(), "&b&l" + " !!! ");
		setDefaultValue(hovPath + Symbol.WAYPOINT.getName(), "&b&l" + " !!! ");
		setDefaultValue(hovPath + Symbol.FILLED.getName(), "&b&l" + "⬟");

		setDefaultValue("symbol-start", "&e&l" + "≪─");
		setDefaultValue("symbol-end", "&e&l" + "─≫");

		setDefaultValue("bar-color", "purple");
		setDefaultValue("bar-style", "solid");

		setDefaultValue("focus-item", "NOITEM");

		setDefaultValues("banned-worlds", new String[]{"world","world_nether","world_the_end"});

		save();
	}

	private void setDefaultValue(String path, String value) {
		if (compassConfig.getString(path) != null) return;
		compassConfig.set(path, value);
	}

	private void setDefaultValues(String path, String[] values) {
		if (compassConfig.getString(path )!= null) return;
		for (String str : values) {
			compassConfig.set(path + "." + str, false);
		}

	}

	public Material getFocusItem() {
		return focusItem;
	}

	public void save() {
		try {
			compassConfig.save(compassFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
