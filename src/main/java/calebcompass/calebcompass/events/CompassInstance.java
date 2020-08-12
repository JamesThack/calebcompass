package calebcompass.calebcompass.events;

import calebcompass.calebcompass.CalebCompass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Boss;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class CompassInstance {

    private static CompassInstance instance;
    private ArrayList<BossBar> activeBars;
    private ArrayList<LocObs> locObs;
    private File playerFile = new File(Bukkit.getPluginManager().getPlugin("CalebCompass").getDataFolder(), "players.yml");
    private FileConfiguration compassConfig;

    public static CompassInstance getInstance() {
        if (instance ==  null) instance = new CompassInstance();
        return instance;
    }

    private CompassInstance() {
        this.activeBars = new ArrayList<BossBar>();
        this.locObs = new ArrayList<LocObs>();
        if (!playerFile.exists()) {
            Bukkit.getPluginManager().getPlugin("CalebCompass").saveResource("players.yml", false);
        }
        this.compassConfig = YamlConfiguration.loadConfiguration(playerFile);
        load();
    }


    public void addBar(BossBar bar) {
        this.activeBars.add(bar);
    }

    public void clearPlayer(Player player) {
        BossBar yeet = null;
        for (BossBar current : activeBars) {
            if (current.getPlayers().get(0).getUniqueId().equals(player.getUniqueId())) {
                yeet = current;
                yeet.setVisible(false);
            }
        }
        if (yeet != null) activeBars.remove(yeet);
    }

    public void setPlayerVisible(Player player, boolean Tracking) {
        LocObs add = getPlayerLocOb(player);
        if (add == null) {
            addLocOb(player, player.getLocation(), player.getLocation());
            add = getPlayerLocOb(player);
            add.setTracking(false);
        }
        add.setShowing(Tracking);
    }

    public boolean isCompassVisible(Player player) {
        LocObs add = getPlayerLocOb(player);
        if (add == null) return true;
        return add.isShowing();
    }

    public BossBar getBarFromPlayer(Player player) {
        for (BossBar current : activeBars) {
            if (current.getPlayers().contains(player)) return current;
        } return null;
    }

    public LocObs getPlayerLocOb(Player player) {
        for (LocObs cur: locObs) {
            if (cur.getPlayer().equals(player.getUniqueId().toString())) return cur;
        }
        return null;
    }

    public LocObs addLocOb(String player, Location loc1, Location loc2) {
        LocObs cache = null;
        for (LocObs cur : locObs) {
            if (cur.getPlayer().equals(player)) cache = cur;
        }
        if (cache != null) locObs.remove(cache);
        LocObs locObs = new LocObs(player, loc1, loc2);
        this.locObs.add(locObs);
        return locObs;

    }

    public LocObs addLocOb(LocObs loc) {
        LocObs cache = null;
        for (LocObs cur : locObs) {
            if (cur.getPlayer().equals(loc.getPlayer())) cache = cur;
        }
        if (cache != null) locObs.remove(cache);
        this.locObs.add(loc);
        return loc;
    }

    public void addLocOb(Player player, Location loc1, Location loc2) {
        addLocOb(player.getUniqueId().toString(), loc1, loc2);
    }

    public void removeLocOb(LocObs locOb) {
        this.locObs.remove(locOb);
    }

    public void saveData() {
        for (LocObs loc : this.locObs) {
            String uuid = loc.getPlayer();
            compassConfig.set("playerdata." + uuid+".istracking", loc.isTracking());
            compassConfig.set("playerdata." + uuid+".world", loc.getOrigin().getWorld().getName());
            compassConfig.set("playerdata." + uuid+".furthestdistance.x", loc.getOrigin().getBlockX());
            compassConfig.set("playerdata." + uuid+".furthestdistance.y", loc.getOrigin().getBlockY());
            compassConfig.set("playerdata." + uuid+".furthestdistance.z", loc.getOrigin().getBlockZ());
            compassConfig.set("playerdata." + uuid+".endpoint.x", loc.getCertainEnd().getBlockX());
            compassConfig.set("playerdata." + uuid+".endpoint.y", loc.getCertainEnd().getBlockY());
            compassConfig.set("playerdata." + uuid+".endpoint.z", loc.getCertainEnd().getBlockZ());
            compassConfig.set("playerdata." + uuid+".viewing", loc.isShowing());
            try {
                compassConfig.save(this.playerFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void load() {
        this.locObs.clear();
        if (!playerFile.exists()) {
            Bukkit.getPluginManager().getPlugin("CalebCompass").saveResource("players.yml", false);
        }
        compassConfig = YamlConfiguration.loadConfiguration(playerFile);
        if (compassConfig.getString("playerdata") == null) return;
        for (String cur : compassConfig.getConfigurationSection("playerdata").getKeys(false)) {
            String curPath = "playerdata." + cur;
            try {
                if (compassConfig.getBoolean(curPath + ".istracking")) {
                    Location origin = new Location(Bukkit.getWorld(compassConfig.getString(curPath + ".world")),
                            Integer.valueOf(compassConfig.getString(curPath + ".furthestdistance.x")),
                            Integer.valueOf(compassConfig.getString(curPath + ".furthestdistance.y")),
                            Integer.valueOf(compassConfig.getString(curPath + ".furthestdistance.z")));
                    Location endPoint = new Location(Bukkit.getWorld(compassConfig.getString(curPath + ".world")),
                            Integer.valueOf(compassConfig.getString(curPath + ".endpoint.x")),
                            Integer.valueOf(compassConfig.getString(curPath + ".endpoint.y")),
                            Integer.valueOf(compassConfig.getString(curPath + ".endpoint.z")));
                    LocObs newLoc = new LocObs(cur, origin, endPoint);
                    if (compassConfig.isBoolean(curPath + ".viewing")) newLoc.setShowing(compassConfig.getBoolean(curPath + ".viewing"));
                    addLocOb(newLoc);
                } else {
                    if (compassConfig.isBoolean(curPath + ".viewing")) setPlayerVisible(Bukkit.getPlayer(UUID.fromString(cur)), compassConfig.getBoolean(curPath + ".viewing"));
                }
            } catch (Exception e) {
                System.out.println("CalebCompass: Error found in player data for player " + cur + " skipping");
            }

        }
    }

    public static boolean hasPerm(Player player, String perm) {
        return (player.isOp() || player.hasPermission("calebcompass." + perm));
    }



}
