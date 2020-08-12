package calebcompass.calebcompass;

import calebcompass.calebcompass.events.CompassInstance;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CalebCompassCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >=1 && args[0].equalsIgnoreCase("help")) {
            sender.sendMessage("§7[§eCaleb Compass§7]§r: List of commands:");
            sender.sendMessage("§4/calebcompass track x y z§r Create a new waypoint to follow");
            sender.sendMessage("§4/calebcompass clear§r Clear your current waypoint");
            sender.sendMessage("§4/calebcompass hide§r Hide the compass");
            sender.sendMessage("§4/calebcompass show§r Show the compass");
            return true;
        } else if (args.length == 4 && args[0].equalsIgnoreCase("track")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if(CompassInstance.hasPerm( (Player) sender, "track.add.self")) {
                    try {
                        if (CompassInstance.getInstance().getPlayerLocOb(player) == null) CompassInstance.getInstance().addLocOb(player, player.getLocation(), new Location(player.getWorld(), Integer.valueOf(args[1]), Integer.valueOf(args[3]), Integer.valueOf(args[3])));
                        CompassInstance.getInstance().getPlayerLocOb(player).setOrigin(player.getLocation());
                        CompassInstance.getInstance().getPlayerLocOb(player).setCertainEnd(new Location(player.getWorld(), Integer.valueOf(args[1]), Integer.valueOf(args[2]), Integer.valueOf(args[3])));
                        CompassInstance.getInstance().getPlayerLocOb(player).setTracking(true);
                        CompassInstance.getInstance().saveData();
                        sender.sendMessage("§7[§eCaleb Compass§7]§r: Successfuly added track point");
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                } else {
                    sender.sendMessage("§7[§eCaleb Compass§7]§r: You do not have permission for this command!");
                    return true;
                }
            } else {
                sender.sendMessage("§7[§eCaleb Compass§7]§r: You must be a player to use this command, use /calebcompass track (player) x y z:");
            }
        } else if (args.length >=5 && args[0].equalsIgnoreCase("track")) {
            try {
                Player player = Bukkit.getPlayer(args[1]);
                if (!(sender instanceof Player) ||CompassInstance.hasPerm( (Player) sender, "track.add.other")) {
                    if (CompassInstance.getInstance().getPlayerLocOb(player) == null) CompassInstance.getInstance().addLocOb(player, player.getLocation(), new Location(player.getWorld(), Integer.valueOf(args[2]), Integer.valueOf(args[3]), Integer.valueOf(args[4])));
                    CompassInstance.getInstance().getPlayerLocOb(player).setOrigin(player.getLocation());
                    CompassInstance.getInstance().getPlayerLocOb(player).setCertainEnd(new Location(player.getWorld(), Integer.valueOf(args[2]), Integer.valueOf(args[3]), Integer.valueOf(args[4])));
                    CompassInstance.getInstance().getPlayerLocOb(player).setTracking(true);
                    CompassInstance.getInstance().saveData();
                    sender.sendMessage("§7[§eCaleb Compass§7]§r: Successfuly added track point for the player " + player.getDisplayName() );
                    return true;
                } else {
                    sender.sendMessage("§7[§eCaleb Compass§7]§r: You do not have permission for this command!");
                    return true;
                }
            } catch (Exception e) {
                sender.sendMessage("§7[§eCaleb Compass§7]§r: No player found with the name " + args[1]);
                return true;
            }
        } else if (args.length ==1 && args[0].equalsIgnoreCase("clear") && sender instanceof  Player) {
            Player player = (Player) sender;
            if (CompassInstance.hasPerm( (Player) sender, "track.remove.self")) {
                if (CompassInstance.getInstance().getPlayerLocOb(player) != null)
                    CompassInstance.getInstance().getPlayerLocOb(player).setTracking(false);
                    CompassInstance.getInstance().saveData();
                    sender.sendMessage("§7[§eCaleb Compass§7]§r: Successfuly removed track point");
            } else {
                sender.sendMessage("§7[§eCaleb Compass§7]§r: You do not have permission for this command!");
            }
            return true;
        } else if (args.length >=2 && args[0].equalsIgnoreCase("clear")) {
            try {
                if (!(sender instanceof Player) ||CompassInstance.hasPerm( (Player) sender, "track.remove.other")) {
                    Player player = Bukkit.getPlayer(args[1]);
                    if (CompassInstance.getInstance().getPlayerLocOb(player) != null) CompassInstance.getInstance().getPlayerLocOb(player).setTracking(false);
                    CompassInstance.getInstance().saveData();
                    sender.sendMessage("§7[§eCaleb Compass§7]§r: You successfuly cleared the track point for " + player.getDisplayName());
                    return true;
                } else {
                    sender.sendMessage("§7[§eCaleb Compass§7]§r: You do not have permission for this command!");
                    return true;
                }
            } catch (Exception e) {
                sender.sendMessage("§7[§eCaleb Compass§7]§r: Player not found");
                return true;
            }

        } else if (args.length ==1 && args[0].equalsIgnoreCase("reload")) {
            if (!(sender instanceof Player) ||CompassInstance.hasPerm( (Player) sender, "reload")) {
                Cache.getCache().update();
                CompassInstance.getInstance().load();
                sender.sendMessage("§7[§eCaleb Compass§7]§r: Config has been loaded into the game");
                return true;
            } else {
                sender.sendMessage("§7[§eCaleb Compass§7]§r: You do not have permission for this command!");
                return true;
            }
        } else if (args.length ==1 && args[0].equalsIgnoreCase("hide") && sender instanceof  Player) {
            if (CompassInstance.hasPerm( (Player) sender, "view.hide.self")) {
                CompassInstance.getInstance().setPlayerVisible((Player) sender, false);
                CompassInstance.getInstance().saveData();
                sender.sendMessage("§7[§eCaleb Compass§7]§r: Hid compass");
                return true;
            } else {
                sender.sendMessage("§7[§eCaleb Compass§7]§r: You do not have permission for this command!");
                return true;
            }
        } else if (args.length ==1 && args[0].equalsIgnoreCase("show") && sender instanceof  Player) {
            if (CompassInstance.hasPerm((Player) sender, "view.show.self")) {
                CompassInstance.getInstance().setPlayerVisible((Player) sender, true);
                sender.sendMessage("§7[§eCaleb Compass§7]§r: Showing compass");
                CompassInstance.getInstance().saveData();
                return true;
            } else {
                sender.sendMessage("§7[§eCaleb Compass§7]§r: You do not have permission for this command!");
                return true;
            }
        } else if (args.length >=2 && args[0].equalsIgnoreCase("hide")) {
            if (!(sender instanceof Player) ||  CompassInstance.hasPerm((Player) sender, "view.hide.other")) {
                try {
                    CompassInstance.getInstance().setPlayerVisible(Bukkit.getPlayer(args[1]), false);
                    sender.sendMessage("§7[§eCaleb Compass§7]§r: Hid compass for player " + args[1]);
                    CompassInstance.getInstance().saveData();
                    return true;
                } catch (Exception e) {
                    sender.sendMessage("§7[§eCaleb Compass§7]§r: Player not found!");
                    return true;
                }
            } else {
                sender.sendMessage("§7[§eCaleb Compass§7]§r: You do not have permission for this command!");
                return true;
            }
        } else if (args.length >=2 && args[0].equalsIgnoreCase("show")) {
            if (!(sender instanceof Player) ||  CompassInstance.hasPerm((Player) sender, "view.show.other")) {
                try {
                    CompassInstance.getInstance().setPlayerVisible(Bukkit.getPlayer(args[1]), true);
                    sender.sendMessage("§7[§eCaleb Compass§7]§r: Showing compass for player " + args[1]);
                    CompassInstance.getInstance().saveData();
                    return true;
                } catch (Exception e) {
                    sender.sendMessage("§7[§eCaleb Compass§7]§r: Player not found!");
                    return true;
                }
            } else {
                sender.sendMessage("§7[§eCaleb Compass§7]§r: You do not have permission for this command!");
                return true;
            }
        }

        return false;
    }
}
