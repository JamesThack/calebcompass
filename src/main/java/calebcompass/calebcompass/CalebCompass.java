package calebcompass.calebcompass;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import pl.betoncraft.betonquest.BetonQuest;

import calebcompass.calebcompass.util.ConfigManager;
import calebcompass.calebcompass.util.CompassInstance;
import calebcompass.calebcompass.betonquest.TrackEvent;
import calebcompass.calebcompass.betonquest.CompassClear;

public final class CalebCompass extends JavaPlugin {

	private static CalebCompass instance;

	private static ConfigManager configManager;

	@Override
	public void onEnable() {
		instance = this;

		configManager = new ConfigManager(instance);

		getServer().getPluginManager().registerEvents(new CompassMoveEvent(), this);
		getServer().getPluginCommand("calebcompass").setExecutor(new CalebCompassCommand());

		if (Bukkit.getPluginManager().getPlugin("BetonQuest") != null) {
			log("Plugin hooked: BetonQuest");
			BetonQuest.getInstance().registerEvents("compasstrack", TrackEvent.class);
			BetonQuest.getInstance().registerEvents("clearcompass", CompassClear.class);
		}
	}

	@Override
	public void onDisable() {
		CompassInstance.getInstance().saveData();
	}

	public static CalebCompass getInstance() {
		return instance;
	}

	public static ConfigManager getConfigManager() {
		return configManager;
	}

	public static void log(String log) {
		Bukkit.getLogger().log(Level.INFO, "[CalebCompass]: " + log);
	}

}
