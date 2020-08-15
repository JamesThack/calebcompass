package calebcompass.calebcompass.util;

import java.io.File;

import calebcompass.calebcompass.CalebCompass;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {

	private File compassFile;

	private FileConfiguration compassConfig;

	public ConfigManager(CalebCompass instance) {
		init(instance);
		setup();
	}

	public void init(CalebCompass instance) {
		compassFile = new File(instance.getDataFolder(), "compass.yml");
		if (!compassFile.exists()) instance.saveResource("compass.yml", false);
		compassConfig = YamlConfiguration.loadConfiguration(compassFile);
	}

	public void setup() {
		init(CalebCompass.getInstance());
		setupDefault();

		for (Symbol symbol : Symbol.values()) {
			setupValues(symbol);
		}

		Util.setSymbolStart(getString("symbol-start"));
		Util.setSymbolEnd(getString("symbol-end"));

		Util.setBarColor(getString("bar-color"));
		Util.setBarStyle(getString("bar-style"));
	}

	private void setupValues(Symbol symbol) {
		Util.getRegular().put(symbol, getString("regular." + symbol.getName()));
		Util.getHovered().put(symbol, getString("hovered." + symbol.getName()));
	}

	private String getString(String path) {
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
		setDefaultValue(regPath + Symbol.FILLED.getName(), "&f&l" + "⬟");
		setDefaultValue(regPath + Symbol.TRACKER.getName(), "&c&l" + "!!!");

		setDefaultValue(hovPath + Symbol.NORTH.getName(), "&b&l" + "N");
		setDefaultValue(hovPath + Symbol.NORTH_EAST.getName(), "&b&l" + "NE");
		setDefaultValue(hovPath + Symbol.EAST.getName(), "&b&l" + "E");
		setDefaultValue(hovPath + Symbol.SOUTH_EAST.getName(), "&b&l" + "SE");
		setDefaultValue(hovPath + Symbol.SOUTH.getName(), "&b&l" + "S");
		setDefaultValue(hovPath + Symbol.SOUTH_WEST.getName(), "&b&l" + "SW");
		setDefaultValue(hovPath + Symbol.WEST.getName(), "&b&l" + "W");
		setDefaultValue(hovPath + Symbol.NORTH_WEST.getName(), "&b&l" + "NW");
		setDefaultValue(hovPath + Symbol.FILLED.getName(), "&b&l" + "⬟");
		setDefaultValue(hovPath + Symbol.TRACKER.getName(), "&b&l" + "!!!");

		setDefaultValue("symbol-start", "&e&l" + "≪─");
		setDefaultValue("symbol-end", "&e&l" + "─≫");

		setDefaultValue("bar-color", "purple");
		setDefaultValue("bar-style", "solid");

		save();
	}

	private void setDefaultValue(String path, String value) {
		if (compassConfig.getString(path) != null) return;
		compassConfig.set(path, value);
	}

	public void save() {
		try {
			compassConfig.save(compassFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
