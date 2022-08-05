package calebcompass.calebcompass;

import calebcompass.calebcompass.betonquest.CompassClear;
import calebcompass.calebcompass.betonquest.Focus;
import calebcompass.calebcompass.betonquest.TogglePoint;
import calebcompass.calebcompass.betonquest.TrackEvent;
import calebcompass.calebcompass.citizens.CitizensEvents;
import calebcompass.calebcompass.citizens.CitizensInstance;
import calebcompass.calebcompass.miscevents.ItemFocus;
import calebcompass.calebcompass.mythicmobs.MythicEvents;
import calebcompass.calebcompass.mythicmobs.MythicInstance;
import calebcompass.calebcompass.util.CompassInstance;
import calebcompass.calebcompass.util.ConfigManager;
import org.betonquest.betonquest.BetonQuest;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class CalebCompass extends JavaPlugin {

	private static CalebCompass instance;

	private static ConfigManager configManager;

	@Override
	public void onEnable() {
		instance = this;

		configManager = ConfigManager.getInstance();

		CompassMoveEvent moveEvent = new CompassMoveEvent();
		moveEvent.runTaskTimer(this, 0, 1);

		getServer().getPluginManager().registerEvents(new ItemFocus(), this);

		getServer().getPluginCommand("calebcompass").setExecutor(new CalebCompassCommand());

		if (Bukkit.getPluginManager().getPlugin("BetonQuest") != null) {
			try {
				BetonQuest.getInstance().registerEvents("compasstrack", TrackEvent.class);
				BetonQuest.getInstance().registerEvents("clearcompass", CompassClear.class);
				BetonQuest.getInstance().registerEvents("togglewaypoint", TogglePoint.class);
				BetonQuest.getInstance().registerEvents("focuspoint", Focus.class);
				log("Plugin hooked: BetonQuest");
			} catch (Exception e) {
				log("Unable to hook into BetonQuest! Disabling features");
				e.printStackTrace();
			}
		}

		if (Bukkit.getPluginManager().getPlugin("MythicMobs") != null) {
			log("Plugin hooked: MythicMobs");
			MythicInstance instance = MythicInstance.getInstance();
			MythicEvents mythEvents = new MythicEvents();
			mythEvents.runTaskTimer(this, 0, 1);
		}

		if (Bukkit.getPluginManager().getPlugin("Citizens") != null) {
			log("Plugin hooked: Citizens");
			CitizensInstance instance = CitizensInstance.getInstance();
			CitizensEvents npcEvents = new CitizensEvents();
			npcEvents.runTaskTimer(this, 0, 1);
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
