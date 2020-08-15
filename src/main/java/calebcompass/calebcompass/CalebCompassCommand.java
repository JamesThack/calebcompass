package calebcompass.calebcompass;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

import calebcompass.calebcompass.util.CompassInstance;
import calebcompass.calebcompass.util.CompassLocation;

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

        sender.sendMessage(PREFIX + "Type '/calebcompass help' for more info");
        return true;
    }

}
