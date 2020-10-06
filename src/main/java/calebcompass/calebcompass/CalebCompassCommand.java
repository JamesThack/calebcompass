package calebcompass.calebcompass;

import calebcompass.calebcompass.SavePoints.SavePoint;
import calebcompass.calebcompass.SavePoints.SavePointConfig;
import calebcompass.calebcompass.util.CompassInstance;
import calebcompass.calebcompass.util.CompassLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class CalebCompassCommand implements CommandExecutor {

	private final static String PREFIX = "§7[§eCaleb Compass§7]§r: ";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// help
        if (args.length >= 1 && args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(PREFIX + "List of commands:");
            sender.sendMessage("§4/calebcompass track x y z§r Create a new waypoint to follow");
            sender.sendMessage("§4/calebcompass clear§r Clear your current waypoint");
            sender.sendMessage("§4/calebcompass hide§r Hide the compass");
            sender.sendMessage("§4/calebcompass show§r Show the compass");
			sender.sendMessage("§4/calebcompass save (name)§r Save a new waypoint for the compass");
			sender.sendMessage("§4/calebcompass toggle (waypoint) (true/false)§r Toggle viewing a waypoint");
			sender.sendMessage("§4/calebcompass focus§r Focus your quest marker on a specific waypoint");
            return true;
        }

        // track
        if (args.length == 4 && args[0].equalsIgnoreCase("track")) {
            if (!(sender instanceof Player)) {
	            sender.sendMessage(PREFIX + "You must be a player to use this command, use /calebcompass track (player) x y z:");
	            return true;
            }

            Player player = (Player) sender;
            if (!CompassInstance.hasPerm((Player) sender, "track.add.self")) {
	            sender.sendMessage(PREFIX + "You do not have permission for this command!");
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
            	sender.sendMessage(PREFIX + "Successfully added track point");
            	return true;
            } catch (Exception e) {
            	return true;
            }
        }

        // track
        if (args.length >= 5 && args[0].equalsIgnoreCase("track")) {
        	Player player = Bukkit.getPlayer(args[1]);
        	if (player == null) {
        		sender.sendMessage(PREFIX + "Player not found!");
        		return true;
        	}

        	if (sender instanceof Player && !CompassInstance.hasPerm((Player) sender, "track.add.other")) {
        		sender.sendMessage(PREFIX + "You do not have permission for this command!");
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
		        sender.sendMessage(PREFIX + "Successfully added track point for the player " + player.getDisplayName());
		        return true;
	        } catch (Exception e) {
	        	return true;
	        }
        }

        // clear
        if (args.length == 1 && args[0].equalsIgnoreCase("clear") && sender instanceof Player) {
            Player player = (Player) sender;
            if (!CompassInstance.hasPerm((Player) sender, "track.remove.self")) {
	            sender.sendMessage(PREFIX + "You do not have permission for this command!");
	            return true;
            }

            if (CompassInstance.getInstance().getCompassLocation(player) != null) CompassInstance.getInstance().getCompassLocation(player).setTracking(false);
            CompassInstance.getInstance().saveData();
            sender.sendMessage(PREFIX + "Successfully removed track point");
            return true;
        }

        // clear
        if (args.length >= 2 && args[0].equalsIgnoreCase("clear")) {
	        Player player = Bukkit.getPlayer(args[1]);
	        if (player == null) {
		        sender.sendMessage(PREFIX + "Player not found");
		        return true;
	        }

	        if (sender instanceof Player && !CompassInstance.hasPerm((Player) sender, "track.remove.other")) {
		        sender.sendMessage(PREFIX + "You do not have permission for this command!");
		        return true;
	        }

	        if (CompassInstance.getInstance().getCompassLocation(player) != null) CompassInstance.getInstance().getCompassLocation(player).setTracking(false);
	        CompassInstance.getInstance().saveData();
	        sender.sendMessage(PREFIX + "You successfully cleared the track point for " + player.getDisplayName());
	        return true;
        }

        // reload
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
        	if (sender instanceof Player && !CompassInstance.hasPerm((Player) sender, "reload")) {
		        sender.sendMessage(PREFIX + "You do not have permission for this command!");
		        return true;
	        }

        	CalebCompass.getConfigManager().setup();
        	SavePointConfig.getInstance().load();
        	CompassInstance.getInstance().load();
        	sender.sendMessage(PREFIX + "Config has been loaded into the game");
        	return true;
        }

        // hide
        if (args.length == 1 && args[0].equalsIgnoreCase("hide") && sender instanceof Player) {
            if (!CompassInstance.hasPerm((Player) sender, "view.hide.self")) {
	            sender.sendMessage(PREFIX + "You do not have permission for this command!");
	            return true;
            }

            CompassInstance.getInstance().setPlayerVisible((Player) sender, false);
            CompassInstance.getInstance().saveData();
            sender.sendMessage(PREFIX + "Hid compass");
            return true;
        }

	    // hide
	    if (args.length >= 2 && args[0].equalsIgnoreCase("hide")) {
		    if (sender instanceof Player && !CompassInstance.hasPerm((Player) sender, "view.hide.other")) {
			    sender.sendMessage(PREFIX + "You do not have permission for this command!");
			    return true;
		    }

		    Player player = Bukkit.getPlayer(args[1]);
		    if (player == null) {
			    sender.sendMessage(PREFIX + "Player not found!");
			    return true;
		    }

		    CompassInstance.getInstance().setPlayerVisible(player, false);
		    sender.sendMessage(PREFIX + "Hid compass for player " + args[1]);
		    CompassInstance.getInstance().saveData();
		    return true;
	    }

	    // show
        if (args.length == 1 && args[0].equalsIgnoreCase("show") && sender instanceof Player) {
            if (!CompassInstance.hasPerm((Player) sender, "view.show.self")) {
	            sender.sendMessage(PREFIX + "You do not have permission for this command!");
	            return true;
            }

            CompassInstance.getInstance().setPlayerVisible((Player) sender, true);
            sender.sendMessage(PREFIX + "Showing compass");
            CompassInstance.getInstance().saveData();
            return true;
        }

        // show
        if (args.length >= 2 && args[0].equalsIgnoreCase("show")) {
	        if (sender instanceof Player && !CompassInstance.hasPerm((Player) sender, "view.show.other")) {
		        sender.sendMessage(PREFIX + "You do not have permission for this command!");
		        return true;
	        }

	        Player player = Bukkit.getPlayer(args[1]);
	        if (player == null) {
		        sender.sendMessage(PREFIX + "Player not found!");
		        return true;
	        }

	        CompassInstance.getInstance().setPlayerVisible(Bukkit.getPlayer(args[1]), true);
	        sender.sendMessage(PREFIX + "Showing compass for player " + args[1]);
	        CompassInstance.getInstance().saveData();
	        return true;
        }

		// save point
		if (args.length >= 2 && args[0].equalsIgnoreCase("save") && sender instanceof Player) {
			if (!CompassInstance.hasPerm((Player) sender, "point.save")) {
				sender.sendMessage(PREFIX + "You do not have permission for this command!");
				return true;
			}

			if (SavePointConfig.getInstance().pointExists(args[1])) {
				sender.sendMessage(PREFIX + "A point with this name already exists");
				return true;
			}
			SavePoint newSave = new SavePoint(((Player) sender).getLocation(), args[1], "&c&l !!! ", "&b&l !!! ");
			newSave.savePoint();
			sender.sendMessage(PREFIX + "Saved point");
			SavePointConfig.getInstance().saveData();
			return true;
		}

		// remove point
		if (args.length >= 2 && args[0].equalsIgnoreCase("remove") && sender instanceof Player) {
			if (!CompassInstance.hasPerm((Player) sender, "point.remove")) {
				sender.sendMessage(PREFIX + "You do not have permission for this command!");
				return true;
			}

			if (!SavePointConfig.getInstance().pointExists(args[1])) {
				sender.sendMessage(PREFIX + "No point found with this name");
				return true;
			}
			SavePointConfig.getInstance().removeSave(SavePointConfig.getInstance().getPointFromName(args[1]));
			sender.sendMessage(PREFIX + "Removed point");
			SavePointConfig.getInstance().saveData();
			return true;
		}

		// toggle point
		if (args.length == 3 && args[0].equalsIgnoreCase("toggle") && sender instanceof  Player) {
			if (!CompassInstance.hasPerm((Player) sender, "point.toggle.self")) {
				sender.sendMessage(PREFIX + "You do not have permission for this command!");
				return true;
			}

			Player player = (Player) sender;

			boolean toggleTo;
			if (args[2].equalsIgnoreCase("enable") || args[2].equalsIgnoreCase("on")) {
				toggleTo = true;
			} else if  (args[2].equalsIgnoreCase("disable") || args[2].equalsIgnoreCase("off")) {
				toggleTo = false;
			} else {
				sender.sendMessage(PREFIX + "Please enter either disable/enable");
				return true;
			}

			if (!SavePointConfig.getInstance().pointExistsExplicit(args[1])) {
				sender.sendMessage(PREFIX + "Point not found");
				return true;
			}

			SavePointConfig.getInstance().togglePlayerPoint(player.getUniqueId(), args[1], toggleTo);
			if (toggleTo) CompassInstance.getInstance().addSavePoint(player.getUniqueId(), SavePointConfig.getInstance().getPointFromName(args[2]));
			if (!toggleTo) CompassInstance.getInstance().removeSavePoint(player.getUniqueId(), SavePointConfig.getInstance().getPointFromName(args[2]));
			sender.sendMessage(PREFIX + "Toggled point " + args[1]);
			CompassInstance.getInstance().saveData();
			SavePointConfig.getInstance().saveData();
			CompassInstance.getInstance().load();
			return true;
		}

		// toggle point
		if (args.length >= 4 && args[0].equalsIgnoreCase("toggle")) {
			if (sender instanceof Player && !CompassInstance.hasPerm((Player) sender, "point.toggle.other")) {
				sender.sendMessage(PREFIX + "You do not have permission for this command!");
				return true;
			}

			Player player = Bukkit.getPlayer(args[1]);
			if (player == null) {
				sender.sendMessage(PREFIX + "Player not found!");
				return true;
			}

			boolean toggleTo;
			if (args[3].equalsIgnoreCase("enable") || args[3].equalsIgnoreCase("on")) {
				toggleTo = true;
			} else if  (args[3].equalsIgnoreCase("disable") || args[3].equalsIgnoreCase("off")) {
				toggleTo = false;
			} else {
				sender.sendMessage(PREFIX + "Please enter either disable/enable");
				return true;
			}

			if (!SavePointConfig.getInstance().pointExistsExplicit(args[2])) {
				sender.sendMessage(PREFIX + "Point not found");
				return true;
			}

			SavePointConfig.getInstance().togglePlayerPoint(player.getUniqueId(), args[2], toggleTo);
			if (toggleTo) CompassInstance.getInstance().addSavePoint(player.getUniqueId(), SavePointConfig.getInstance().getPointFromName(args[2]));
			if (!toggleTo) CompassInstance.getInstance().removeSavePoint(player.getUniqueId(), SavePointConfig.getInstance().getPointFromName(args[2]));
			sender.sendMessage(PREFIX + "Toggled point " + args[2] + " for player " + args[1]);
			CompassInstance.getInstance().saveData();
			SavePointConfig.getInstance().saveData();
			CompassInstance.getInstance().load();
			return true;
		}

		// focus point
		if (args.length == 1 && args[0].equalsIgnoreCase("focus") && sender instanceof Player) {

			SavePointConfig.getInstance().load();
			if (!CompassInstance.hasPerm((Player) sender, "point.focus")) {
				sender.sendMessage(PREFIX + "You do not have permission for this command!");
				return true;
			}
			Player player = (Player) sender;

			SavePoint point = getSavePointAtLoc(player);
			if (point == null)  {
				player.sendMessage(PREFIX + "No point found, please look at a waypoint!");
				return true;
			}

			CompassInstance.getInstance().getCompassLocation(player).setTarget(point.getLoc1());
			CompassInstance.getInstance().getCompassLocation(player).setOrigin(player.getLocation());
			CompassInstance.getInstance().getCompassLocation(player).setTracking(true);
			sender.sendMessage(PREFIX + "Changed focus");
			CompassInstance.getInstance().saveData();
			return true;
		}



        sender.sendMessage(PREFIX + "Type '/calebcompass help' for more info");
        return true;
    }

    public SavePoint getSavePointAtLoc(Player player) {
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

}
