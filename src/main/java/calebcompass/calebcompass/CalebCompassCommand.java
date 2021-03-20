package calebcompass.calebcompass;

import calebcompass.calebcompass.SavePoints.SavePoint;
import calebcompass.calebcompass.SavePoints.SavePointConfig;
import calebcompass.calebcompass.citizens.CitizensInstance;
import calebcompass.calebcompass.mythicmobs.MythicInstance;
import calebcompass.calebcompass.util.CompassInstance;
import calebcompass.calebcompass.util.CompassLocation;
import calebcompass.calebcompass.util.LangManager;
import calebcompass.calebcompass.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class CalebCompassCommand implements CommandExecutor {

	private static String PREFIX = MessageUtil.colourise(LangManager.getInstance().getString("prefix"));

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// help
		if ((args.length == 1 || (args.length >=1 && args[1].equals("1"))) && args[0].equalsIgnoreCase("help")) {
			sender.sendMessage(PREFIX +MessageUtil.colourise(LangManager.getInstance().getString("help-command-header-1")));
			sender.sendMessage(MessageUtil.colourise(LangManager.getInstance().getString("help-command-track")));
			sender.sendMessage(MessageUtil.colourise(LangManager.getInstance().getString("help-command-clear")));
			sender.sendMessage(MessageUtil.colourise(LangManager.getInstance().getString("help-command-hide")));
			sender.sendMessage(MessageUtil.colourise(LangManager.getInstance().getString("help-command-show")));
			sender.sendMessage(MessageUtil.colourise(LangManager.getInstance().getString("help-command-save")));
			sender.sendMessage(MessageUtil.colourise(LangManager.getInstance().getString("help-command-footer")));
			return true;
		}

		if (args.length >=1 && args[0].equalsIgnoreCase("help")) {
			try {
				switch (Integer.parseInt(args[1])) {
					case(2):
						sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("help-command-header-2")));
						sender.sendMessage(MessageUtil.colourise(LangManager.getInstance().getString("help-command-remove")));
						sender.sendMessage(MessageUtil.colourise(LangManager.getInstance().getString("help-command-toggle")));
						sender.sendMessage(MessageUtil.colourise(LangManager.getInstance().getString("help-command-focus")));
						sender.sendMessage(MessageUtil.colourise(LangManager.getInstance().getString("help-command-waypoints")));
						sender.sendMessage(MessageUtil.colourise(LangManager.getInstance().getString("help-command-footer")));
						return true;
				}
			} catch (Exception e) {
			}
			sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("help-page-not-found")));
			return true;
		}

		// track
		if (args.length == 4 && args[0].equalsIgnoreCase("track")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("track-from-player")));
				return true;
			}

			Player player = (Player) sender;
			if (!CompassInstance.hasPerm((Player) sender, "track.add.self")) {
				sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("no-permission")));
				return true;
			}


			try {
				CompassLocation location = CompassInstance.getInstance().getCompassLocation(player);
				if (location == null) CompassInstance.getInstance().addCompassLocation(player, player.getLocation(), new Location(player.getWorld(), Integer.parseInt(args[1]), Integer.parseInt(args[3]), Integer.parseInt(args[3])));
				location = CompassInstance.getInstance().getCompassLocation(player);

				location.setOrigin(player.getLocation());
				location.setTarget(new Location(player.getWorld(), Double.parseDouble(args[1]) + 0.5, Double.parseDouble(args[2]), Double.parseDouble(args[3]) + 0.5));
				location.setTracking(true);
				CompassInstance.getInstance().saveData();
				sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("track-point-added")));
				return true;
			} catch (Exception e) {
				return true;
			}
		}

		// track
		if (args.length >= 5 && args[0].equalsIgnoreCase("track")) {
			Player player = Bukkit.getPlayer(args[1]);
			if (player == null) {
				sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("player-not-found")));
				return true;
			}

			if (sender instanceof Player && !CompassInstance.hasPerm((Player) sender, "track.add.other")) {
				sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("no-permission")));
				return true;
			}

			try {
				CompassLocation location = CompassInstance.getInstance().getCompassLocation(player);
				if (location == null) CompassInstance.getInstance().addCompassLocation(player, player.getLocation(), new Location(player.getWorld(), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4])));
				location = CompassInstance.getInstance().getCompassLocation(player);

				location.setOrigin(player.getLocation());
				location.setTarget(new Location(player.getWorld(), Double.parseDouble(args[2]) + 0.5, Double.parseDouble(args[3]), Double.parseDouble(args[4]) + 0.5));
				location.setTracking(true);
				CompassInstance.getInstance().saveData();
				sender.sendMessage(PREFIX + MessageUtil.colourise(MessageUtil.replaceVariable(LangManager.getInstance().getString("track-point-other-added"),"%player%", player.getDisplayName())));
				return true;
			} catch (Exception e) {
				return true;
			}
		}

		// clear
		if (args.length == 1 && args[0].equalsIgnoreCase("clear") && sender instanceof Player) {
			Player player = (Player) sender;
			if (!CompassInstance.hasPerm((Player) sender, "track.remove.self")) {
				sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("no-permission")));
				return true;
			}

			if (CompassInstance.getInstance().getCompassLocation(player) != null) CompassInstance.getInstance().getCompassLocation(player).setTracking(false);
			CompassInstance.getInstance().saveData();
			sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("track-point-removed")));
			return true;
		}

		// clear
		if (args.length >= 2 && args[0].equalsIgnoreCase("clear")) {
			Player player = Bukkit.getPlayer(args[1]);
			if (player == null) {
				sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("player-not-found")));
				return true;
			}

			if (sender instanceof Player && !CompassInstance.hasPerm((Player) sender, "track.remove.other")) {
				sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("no-permission")));
				return true;
			}

			if (CompassInstance.getInstance().getCompassLocation(player) != null) CompassInstance.getInstance().getCompassLocation(player).setTracking(false);
			CompassInstance.getInstance().saveData();
			sender.sendMessage(PREFIX + MessageUtil.colourise(MessageUtil.replaceVariable(LangManager.getInstance().getString("track-point-other-removed"), "%player%", player.getDisplayName())));
			return true;
		}

		// reload
		if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
			if (sender instanceof Player && !CompassInstance.hasPerm((Player) sender, "reload")) {
				sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("no-permission")));
				return true;
			}
            CalebCompass.getLangManager().setup();
        	CalebCompass.getConfigManager().setup();

        	SavePointConfig.getInstance().load();
        	CompassInstance.getInstance().load();

        	PREFIX = MessageUtil.colourise(LangManager.getInstance().getString("prefix"));
        	if (MythicInstance.isPluginInstalled) MythicInstance.getInstance().load();
        	if (CitizensInstance.isPluginInstalled) CitizensInstance.getInstance().load();
        	sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("config-reload")));
        	return true;
        }

		// hide

		if (args.length == 1 && args[0].equalsIgnoreCase("hide") && sender instanceof Player) {
			if (!CompassInstance.hasPerm((Player) sender, "view.hide.self")) {
				sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("no-permission")));
				return true;
			}

			CompassInstance.getInstance().setPlayerVisible((Player) sender, false);
			CompassInstance.getInstance().saveData();
			sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("compass-hide")));
			return true;
		}

		// hide
		if (args.length >= 2 && args[0].equalsIgnoreCase("hide")) {
			if (sender instanceof Player && !CompassInstance.hasPerm((Player) sender, "view.hide.other")) {
				sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("no-permission")));
				return true;
			}

			Player player = Bukkit.getPlayer(args[1]);
			if (player == null) {
				sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("player-not-found")));
				return true;
			}

			CompassInstance.getInstance().setPlayerVisible(player, false);
			sender.sendMessage(PREFIX + MessageUtil.colourise(MessageUtil.replaceVariable(LangManager.getInstance().getString("compass-other-hide"), "%player%", player.getDisplayName())));
			CompassInstance.getInstance().saveData();
			return true;
		}

		// show
		if (args.length == 1 && args[0].equalsIgnoreCase("show") && sender instanceof Player) {
			if (!CompassInstance.hasPerm((Player) sender, "view.show.self")) {
				sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("no-permission")));
				return true;
			}

			CompassInstance.getInstance().setPlayerVisible((Player) sender, true);
			sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("compass-show")));
			CompassInstance.getInstance().saveData();
			return true;
		}

		// show
		if (args.length >= 2 && args[0].equalsIgnoreCase("show")) {
			if (sender instanceof Player && !CompassInstance.hasPerm((Player) sender, "view.show.other")) {
				sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("no-permission")));
				return true;
			}

			Player player = Bukkit.getPlayer(args[1]);
			if (player == null) {
				sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("player-not-found")));
				return true;
			}

			CompassInstance.getInstance().setPlayerVisible(Bukkit.getPlayer(args[1]), true);
			sender.sendMessage(PREFIX + MessageUtil.colourise(MessageUtil.replaceVariable(LangManager.getInstance().getString("compass-other-show"), "%player%", player.getDisplayName())));
			CompassInstance.getInstance().saveData();
			return true;
		}

		// save point
		if (args.length >= 2 && args[0].equalsIgnoreCase("save") && sender instanceof Player) {
			if (!CompassInstance.hasPerm((Player) sender, "point.save")) {
				sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("no-permission")));
				return true;
			}

			if (SavePointConfig.getInstance().pointExists(args[1])) {
				sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("point-already-exist")));
				return true;
			}
			SavePoint newSave = new SavePoint(((Player) sender).getLocation(), args[1], CalebCompass.getConfigManager().getString("regular.waypoint"), CalebCompass.getConfigManager().getString("hovered.waypoint"));
			newSave.savePoint();
			sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("point-saved")));
			SavePointConfig.getInstance().saveData();
			return true;
		}

		// remove point
		if (args.length >= 2 && args[0].equalsIgnoreCase("remove") && sender instanceof Player) {
			if (!CompassInstance.hasPerm((Player) sender, "point.remove")) {
				sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("no-permission")));
				return true;
			}

			if (!SavePointConfig.getInstance().pointExists(args[1])) {
				sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("point-not-found")));
				return true;
			}
			SavePointConfig.getInstance().removeSave(SavePointConfig.getInstance().getPointFromName(args[1]));
			sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("point-removed")));
			SavePointConfig.getInstance().saveData();
			CompassInstance.getInstance().load();
			return true;
		}

		// toggle point
		if (args.length == 3 && args[0].equalsIgnoreCase("toggle") && sender instanceof  Player) {
			if (!CompassInstance.hasPerm((Player) sender, "point.toggle.self")) {
				sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("no-permission")));
				return true;
			}

			Player player = (Player) sender;

			boolean toggleTo;
			if (args[2].equalsIgnoreCase("enable") || args[2].equalsIgnoreCase("on")) {
				toggleTo = true;
			} else if  (args[2].equalsIgnoreCase("disable") || args[2].equalsIgnoreCase("off")) {
				toggleTo = false;
			} else {
				sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("missing-toggle")));
				return true;
			}

			if (!SavePointConfig.getInstance().pointExistsExplicit(args[1])) {
				sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("point-not-found")));
				return true;
			}

			SavePointConfig.getInstance().togglePlayerPoint(player.getUniqueId(), args[1], toggleTo);
			if (toggleTo) CompassInstance.getInstance().addSavePoint(player.getUniqueId(), SavePointConfig.getInstance().getPointFromName(args[2]));
			if (!toggleTo) CompassInstance.getInstance().removeSavePoint(player.getUniqueId(), SavePointConfig.getInstance().getPointFromName(args[2]));
			String status = "on";
			if (!toggleTo) status = "off";
			sender.sendMessage(PREFIX + MessageUtil.colourise(MessageUtil.replaceVariable(MessageUtil.replaceVariable(LangManager.getInstance().getString("point-toggled"), "%point%", args[1]), "%toggle%",status)));
			CompassInstance.getInstance().saveData();
			SavePointConfig.getInstance().saveData();
			CompassInstance.getInstance().load();
			return true;
		}

		// toggle point
		if (args.length >= 4 && args[0].equalsIgnoreCase("toggle")) {
			if (sender instanceof Player && !CompassInstance.hasPerm((Player) sender, "point.toggle.other")) {
				sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("no-permission")));
				return true;
			}

			Player player = Bukkit.getPlayer(args[1]);
			if (player == null) {
				sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("player-not-found")));
				return true;
			}

			boolean toggleTo;
			if (args[3].equalsIgnoreCase("enable") || args[3].equalsIgnoreCase("on")) {
				toggleTo = true;
			} else if  (args[3].equalsIgnoreCase("disable") || args[3].equalsIgnoreCase("off")) {
				toggleTo = false;
			} else {
				sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("missing-toggle")));
				return true;
			}

			if (!SavePointConfig.getInstance().pointExistsExplicit(args[2])) {
				sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("point-not-found")));
				return true;
			}

			SavePointConfig.getInstance().togglePlayerPoint(player.getUniqueId(), args[2], toggleTo);
			if (toggleTo) CompassInstance.getInstance().addSavePoint(player.getUniqueId(), SavePointConfig.getInstance().getPointFromName(args[2]));
			if (!toggleTo) CompassInstance.getInstance().removeSavePoint(player.getUniqueId(), SavePointConfig.getInstance().getPointFromName(args[2]));
			sender.sendMessage(PREFIX + MessageUtil.colourise(MessageUtil.replaceVariable(MessageUtil.replaceVariable(MessageUtil.replaceVariable(LangManager.getInstance().getString("point-toggled"), "%point%",args[1]), "%toggle%", String.valueOf(toggleTo)), "%player%", player.getDisplayName())));
			CompassInstance.getInstance().saveData();
			SavePointConfig.getInstance().saveData();
			CompassInstance.getInstance().load();
			return true;
		}

		// focus point
		if (args.length == 1 && args[0].equalsIgnoreCase("focus") && sender instanceof Player) {

			SavePointConfig.getInstance().load();
			if (!CompassInstance.hasPerm((Player) sender, "point.focus")) {
				sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("no-permission")));
				return true;
			}
			Player player = (Player) sender;

			SavePoint point = getSavePointAtLoc(player);
			if (point == null)  {
				player.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("point-not-looked")));
				return true;
			}

			CompassInstance.getInstance().getCompassLocation(player).setTarget(point.getLoc1());
			CompassInstance.getInstance().getCompassLocation(player).setOrigin(player.getLocation());
			CompassInstance.getInstance().getCompassLocation(player).setTracking(true);
			sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("focus-changed")));
			CompassInstance.getInstance().saveData();
			return true;
		}


		// focus point namepoint
		if (args.length == 2 && args[0].equalsIgnoreCase("focus") && sender instanceof Player) {

			SavePointConfig.getInstance().load();
			if (!CompassInstance.hasPerm((Player) sender, "point.focus")) {
				sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("no-permission")));
				return true;
			}
			Player player = (Player) sender;

			SavePoint point = getSavePointFromName(player, args[1]);
			if (point == null) {
				player.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("point-not-found")));
				return true;
			}


			CompassInstance.getInstance().getCompassLocation(player).setTarget(point.getLoc1());
			CompassInstance.getInstance().getCompassLocation(player).setOrigin(player.getLocation());
			CompassInstance.getInstance().getCompassLocation(player).setTracking(true);
			sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("focus-changed")));
			CompassInstance.getInstance().saveData();
			return true;
		}

		//list waypoints
		if (args.length>=1 && args[0].equalsIgnoreCase("waypoints") && sender instanceof Player) {
			if (!CompassInstance.hasPerm((Player) sender, "point.list")) {
				sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("no-permission")));
				return true;
			}
			if (args.length == 1 || args[1].equals("1")) {
				sender.sendMessage(PREFIX + MessageUtil.colourise(MessageUtil.replaceVariable(LangManager.getInstance().getString("active-points-list"), "%page%", "1")));
				CompassLocation loc = CompassInstance.getInstance().getCompassLocation((Player) sender);
				if (loc == null || loc.getActivePoints() == null) return true;
				for (int i = 0; i < 5; i++) {
					try {
						SavePoint currentPoint = loc.getActivePoints().get(i);
						if (currentPoint != null) sender.sendMessage(MessageUtil.colourise(MessageUtil.replaceVariable(MessageUtil.replaceVariable(MessageUtil.replaceVariable(MessageUtil.replaceVariable(MessageUtil.replaceVariable(LangManager.getInstance().getString("active-point"), "%symbol%", currentPoint.getSymbol()), "%loc-z%", String.valueOf(currentPoint.getLoc1().getBlockZ())), "%loc-y%", String.valueOf(currentPoint.getLoc1().getBlockY())), "%loc-x%", String.valueOf(currentPoint.getLoc1().getBlockX())), "%name%",currentPoint.getName())));
					} catch(Exception e) { }
				}
				return true;
			}
			try {
				int page = Integer.parseInt(args[1]);
				if (page >=2) {
					sender.sendMessage(PREFIX + MessageUtil.colourise(MessageUtil.replaceVariable(LangManager.getInstance().getString("active-points-list"), "%page%", args[1])));
					CompassLocation loc = CompassInstance.getInstance().getCompassLocation((Player) sender);
					if (loc == null || loc.getActivePoints() == null) return true;
					for (int i = (page * 5) - 5; i < (page * 5); i++) {
						try {
							SavePoint currentPoint = loc.getActivePoints().get(i);
							if (currentPoint != null) sender.sendMessage(MessageUtil.colourise(MessageUtil.replaceVariable(MessageUtil.replaceVariable(MessageUtil.replaceVariable(MessageUtil.replaceVariable(MessageUtil.replaceVariable(LangManager.getInstance().getString("active-point"), "%symbol%", currentPoint.getSymbol()), "%loc-z%", String.valueOf(currentPoint.getLoc1().getBlockZ())), "%loc-y%", String.valueOf(currentPoint.getLoc1().getBlockY())), "%loc-x%", String.valueOf(currentPoint.getLoc1().getBlockX())), "%name%",currentPoint.getName())));
						} catch(Exception e) { }
					}
					return true;
				}
			} catch(Exception e) { }
			sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("page-not-found")));
			return true;
		}



		sender.sendMessage(PREFIX + MessageUtil.colourise(LangManager.getInstance().getString("help")));
		return true;
	}

	public static SavePoint getSavePointAtLoc(Player player) {
		Location playerLoc = player.getLocation();
		int yaw = Math.round((playerLoc.getYaw() + 360) / 9);

		if (yaw >= 40) yaw -=40;


		if (CompassInstance.getInstance().getCompassLocation(player) == null) {
			return null;
		}

		ArrayList<SavePoint> extraPoints = CompassInstance.getInstance().getCompassLocation(player).getActivePoints();
		for (SavePoint cur : extraPoints) {
			if (!cur.getLoc1().getWorld().equals(player.getLocation().getWorld())) continue;
			Vector target = cur.getLoc1().toVector();
			playerLoc.setDirection(target.subtract(playerLoc.toVector()));
			float playerYaw = playerLoc.getYaw();
			int goDir2 = Math.round(playerYaw / 9);
			if (goDir2 == yaw) {
				return cur;
			}
		}
		return null;
	}


	public SavePoint getSavePointFromName(Player player, String name) {

	    // check if the point exist and toggle it to true to player before focus it
        if (CompassInstance.getInstance().getCompassConfig().getConfigurationSection("playerdata." + player.getUniqueId().toString() + ".activepoints") != null) {
                if(CompassInstance.getInstance().getCompassConfig().getConfigurationSection("playerdata." + player.getUniqueId().toString() + ".activepoints").contains(name)) { // check if player has the point into his list
                    SavePointConfig.getInstance().togglePlayerPoint(player.getUniqueId(), name, true); // toggle the waypoint to true
                    CompassInstance.getInstance().addSavePoint(player.getUniqueId(), SavePointConfig.getInstance().getPointFromName(name)); // save it
                    CompassInstance.getInstance().saveData();
                    SavePointConfig.getInstance().saveData();
                    CompassInstance.getInstance().load();
                }
        }
        else return null;

		ArrayList<SavePoint> extraPoints = CompassInstance.getInstance().getCompassLocation(player).getActivePoints();
		for (SavePoint cur : extraPoints) {
			if (cur.getName().equalsIgnoreCase(name)) return cur;
		}
		return null;
	}


}
