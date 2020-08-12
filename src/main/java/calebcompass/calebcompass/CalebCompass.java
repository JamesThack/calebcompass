package calebcompass.calebcompass;

import calebcompass.calebcompass.betonquest.CompassClear;
import calebcompass.calebcompass.betonquest.TrackEvent;
import calebcompass.calebcompass.events.CompassInstance;
import calebcompass.calebcompass.events.Events;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import pl.betoncraft.betonquest.BetonQuest;

import java.io.File;

public final class CalebCompass extends JavaPlugin {


    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Events(), this);
        getServer().getPluginCommand("calebcompass").setExecutor(new CalebCompassCommand());

        if (Bukkit.getPluginManager().getPlugin("BetonQuest") != null) {
            System.out.println("[CalebCompass]: Plugin hooked: BetonQuest");
            BetonQuest.getInstance().registerEvents("compasstrack", TrackEvent.class);
            BetonQuest.getInstance().registerEvents("clearcompass", CompassClear.class);
        }

    }

    @Override
    public void onDisable() {
        CompassInstance.getInstance().saveData();
    }
}
