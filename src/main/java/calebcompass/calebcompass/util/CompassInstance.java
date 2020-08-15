package calebcompass.calebcompass.util;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

import org.bukkit.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import calebcompass.calebcompass.CalebCompass;

public class CompassInstance {

	private static CompassInstance instance;

	private List<BossBar> activeBars;
	private List<CompassLocation> compassLocations;

	private File playerFile = new File(CalebCompass.getInstance().getDataFolder(), "players.yml");
	private FileConfiguration compassConfig;

	public static CompassInstance getInstance() {
		if (instance ==  null) instance = new CompassInstance();
		return instance;
	}

	private CompassInstance() {
		activeBars = new ArrayList<>();
		compassLocations = new ArrayList<>();

		if (!playerFile.exists()) CalebCompass.getInstance().saveResource("players.yml", false);
		compassConfig = YamlConfiguration.loadConfiguration(playerFile);

		load();
	}

	public void addBar(BossBar bar) {
		activeBars.add(bar);
	}

	public void clearPlayer(Player player) {
		BossBar bar = null;
		for (BossBar current : activeBars) {
			if (!current.getPlayers().get(0).getUniqueId().equals(player.getUniqueId())) continue;
			bar = current;
			bar.setVisible(false);
		}
		if (bar != null) activeBars.remove(bar);
	}

	public void setPlayerVisible(Player player, boolean Tracking) {
		CompassLocation add = getCompassLocation(player);
		if (add == null) {
			addCompassLocation(player, player.getLocation(), player.getLocation());
			add = getCompassLocation(player);
			add.setTracking(false);
		}
		add.setShowing(Tracking);
	}

	public boolean isCompassVisible(Player player) {
		CompassLocation add = getCompassLocation(player);
		if (add == null) return true;
		return add.isShowing();
	}

	public BossBar getBarFromPlayer(Player player) {
		for (BossBar current : activeBars) {
			if (current.getPlayers().contains(player)) return current;
		}
		return null;
	}

	public CompassLocation getCompassLocation(Player player) {
		for (CompassLocation cur : compassLocations) {
			if (cur.getUUID().equals(player.getUniqueId())) return cur;
		}
		return null;
	}

	public CompassLocation addCompassLocation(UUID uuid, Location loc1, Location loc2) {
		CompassLocation cache = null;
		for (CompassLocation cur : compassLocations) {
			if (cur.getUUID().equals(uuid)) cache = cur;
		}
		if (cache != null) compassLocations.remove(cache);

		CompassLocation locObs = new CompassLocation(uuid, loc1, loc2);
		compassLocations.add(locObs);
		return locObs;
	}

	public CompassLocation addCompassLocation(CompassLocation loc) {
		CompassLocation cache = null;
		for (CompassLocation cur : compassLocations) {
			if (cur.getUUID().equals(loc.getUUID())) cache = cur;
		}
		if (cache != null) compassLocations.remove(cache);

		compassLocations.add(loc);
		return loc;
	}

	public void addCompassLocation(Player player, Location loc1, Location loc2) {
		addCompassLocation(player.getUniqueId(), loc1, loc2);
	}

	public void removeCompassLocation(CompassLocation locOb) {
		compassLocations.remove(locOb);
	}

	public void saveData() {
		for (CompassLocation loc : compassLocations) {
			String uuid = loc.getUUID().toString();
			String path = "playerdata.";

			compassConfig.set(path + uuid + ".istracking", loc.isTracking());
			compassConfig.set(path + uuid + ".world", loc.getOrigin().getWorld().getName());
			compassConfig.set(path + uuid + ".origin.x", loc.getOrigin().getBlockX() + 0.5);
			compassConfig.set(path + uuid + ".origin.y", loc.getOrigin().getBlockY());
			compassConfig.set(path + uuid + ".origin.z", loc.getOrigin().getBlockZ() + 0.5);
			compassConfig.set(path + uuid + ".target.x", loc.getTarget().getX());
			compassConfig.set(path + uuid + ".target.y", loc.getTarget().getY());
			compassConfig.set(path + uuid + ".target.z", loc.getTarget().getZ());
			compassConfig.set(path + uuid + ".viewing", loc.isShowing());

			try {
				compassConfig.save(playerFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void load() {
		compassLocations.clear();
		if (!playerFile.exists()) CalebCompass.getInstance().saveResource("players.yml", false);
		compassConfig = YamlConfiguration.loadConfiguration(playerFile);

		if (compassConfig.getString("playerdata") == null) return;
		for (String uuidStr : compassConfig.getConfigurationSection("playerdata").getKeys(false)) {
			UUID uuid = UUID.fromString(uuidStr);
			String curPath = "playerdata." + uuid + ".";
			try {
				if (compassConfig.getBoolean(curPath + "istracking")) {
					World world = Bukkit.getWorld(compassConfig.getString(curPath + ".world"));
					double originX = getCoord(curPath + "origin.x");
					double originY = getCoord(curPath + "origin.y");
					double originZ = getCoord(curPath + "origin.z");

					double targetX = getCoord(curPath + "target.x");
					double targetY = getCoord(curPath + "target.y");
					double targetZ = getCoord(curPath + "target.z");

					Location origin = new Location(world, originX, originY, originZ);
					Location target = new Location(world, targetX, targetY, targetZ);

					CompassLocation newLoc = new CompassLocation(uuid, origin, target);
					if (compassConfig.isBoolean(curPath + "viewing")) newLoc.setShowing(compassConfig.getBoolean(curPath + "viewing"));
					addCompassLocation(newLoc);
					continue;
				}

				if (compassConfig.isBoolean(curPath + "viewing")) setPlayerVisible(Bukkit.getPlayer(uuid), compassConfig.getBoolean(curPath + "viewing"));
			} catch (Exception e) {
				CalebCompass.log("Error found in player data for player with uuid '" + uuid + "' - skipping");
			}
		}
	}

	private double getCoord(String path) {
		return Double.parseDouble(compassConfig.getString(path));
	}

	public static boolean hasPerm(Player player, String perm) {
		return (player.isOp() || player.hasPermission("calebcompass." + perm));
	}

}
