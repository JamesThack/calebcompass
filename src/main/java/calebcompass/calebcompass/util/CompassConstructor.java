package calebcompass.calebcompass.util;

import calebcompass.calebcompass.SavePoints.SavePoint;
import calebcompass.calebcompass.towny.TownyEvents;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class CompassConstructor {

    private Player player;
    private BossBar bar;
    private CompassLocation location;
    private ArrayList<String> cachedInputs;
    private int startLoc;
    private static final String FORMAT = "▘⬟⬟⬟⬟▙⬟⬟⬟⬟▚⬟⬟⬟⬟▛⬟⬟⬟⬟▜⬟⬟⬟⬟▝⬟⬟⬟⬟▞⬟⬟⬟⬟▟⬟⬟⬟⬟▘⬟⬟⬟⬟▙⬟⬟⬟⬟▚⬟⬟⬟⬟▛⬟⬟⬟⬟▜⬟⬟⬟⬟▝⬟⬟⬟⬟▞⬟⬟⬟⬟▟⬟⬟⬟⬟▘⬟⬟⬟⬟▙⬟⬟⬟⬟▚⬟⬟⬟⬟▛⬟⬟⬟⬟▜⬟⬟⬟⬟▝⬟⬟⬟⬟▞⬟⬟⬟⬟▟⬟⬟⬟⬟";

    public CompassConstructor(Player player) {
        this.player = player;
        this.cachedInputs = new ArrayList<>();
        this.location = CompassInstance.getInstance().getCompassLocation(player);
        BossBar existing = CompassInstance.getInstance().getBarFromPlayer(player);
        if (existing == null) {
            int yaw = Math.round((player.getLocation().getYaw() + 360) / 9);
            existing = Bukkit.createBossBar(makeBasicCompass(yaw), Util.getBarColor(), Util.getBarStyle());
            existing.addPlayer(player);
            CompassInstance.getInstance().addBar(existing);
        }
        this.bar = existing;
        updateTrackingDistance();
        bar.setColor(Util.getBarColor());
		bar.setStyle(Util.getBarStyle());
        if(this.location != null) bar.setVisible(location.isShowing());
    }

    public static String replaceHovered(String insert) {
        insert = insert.replace("▚", Util.getHovered(Symbol.SOUTH))
                .replace("▛", Util.getHovered(Symbol.SOUTH_WEST))
                .replace("▜", Util.getHovered(Symbol.WEST))
                .replace("▝", Util.getHovered(Symbol.NORTH_WEST))
                .replace("▞", Util.getHovered(Symbol.NORTH))
                .replace("▟", Util.getHovered(Symbol.NORTH_EAST))
                .replace("▘", Util.getHovered(Symbol.EAST))
                .replace("▙", Util.getHovered(Symbol.SOUTH_EAST))
                .replace("⬟", Util.getHovered(Symbol.FILLED))
                .replace("▨", Util.getHovered(Symbol.TRACKER));
        return insert;
    }

    public static String replaceRegular(String insert) {
        insert = insert.replace("▚", Util.getRegular(Symbol.SOUTH))
                .replace("▛", Util.getRegular(Symbol.SOUTH_WEST))
                .replace("▜", Util.getRegular(Symbol.WEST))
                .replace("▝", Util.getRegular(Symbol.NORTH_WEST))
                .replace("▞", Util.getRegular(Symbol.NORTH))
                .replace("▟", Util.getRegular(Symbol.NORTH_EAST))
                .replace("▘", Util.getRegular(Symbol.EAST))
                .replace("▙", Util.getRegular(Symbol.SOUTH_EAST))
                .replace("⬟", Util.getRegular(Symbol.FILLED))
                .replace("▨", Util.getRegular(Symbol.TRACKER));
        return insert;
    }

    private String makeBasicCompass(int dir) {
        if (dir >= 40) dir -= 40;
        startLoc = dir;
        String overallMessage = FORMAT;
        String over = addInWaypoints(overallMessage);
        over = addInTracker(over);
        over = over.substring(dir, dir + 21);
        cacheWaypointSymbols(over);
        over = finalSymbols(over);
        over = addInCachedWaypoints(over);
        return over;
    }

    private String addInWaypoints(String str) {
        if (Bukkit.getPluginManager().getPlugin("Towny") != null) TownyEvents.addTownyPoints(player);
        if (location == null || location.getActivePoints() == null) return str;
        ArrayList<SavePoint> extraPoints = location.getActivePoints();
        Location playerLoc = player.getLocation();
        for (SavePoint cur : extraPoints) {
            if (!cur.getLoc1().getWorld().equals(playerLoc.getWorld())) continue;
            Vector target = cur.getLoc1().toVector();
            playerLoc.setDirection(target.subtract(playerLoc.toVector()));
            float playerYaw = playerLoc.getYaw();
            int goDir2 = Math.round(playerYaw / 9);
            StringBuilder build = new StringBuilder(str);
            if (goDir2 - 30  <= 10 && goDir2 - 30 >= 0) build.setCharAt(goDir2 - 30, '▓');
            build.setCharAt(goDir2 + 10, '▓');
            build.setCharAt(goDir2 + 50, '▓');
            str = build.toString();
        }
        return str;
    }

    private String addInTracker(String str) {
        if(location == null || !location.isTracking() || !location.getTarget().getWorld().equals(player.getWorld())) {
            return str;
        }
        double dist = location.getOrigin().distanceSquared(location.getTarget());
        double newDist = player.getLocation().distanceSquared(location.getTarget());
        if (newDist > dist) location.setOrigin(player.getLocation());

        if (checkCoords(player.getLocation(), location.getTarget())) return str;

        Location playerLoc = player.getLocation();
        Vector target = location.getTarget().toVector();
        playerLoc.setDirection(target.subtract(playerLoc.toVector()));
        float playerYaw = playerLoc.getYaw();
        int goDir = Math.round(playerYaw / 9);

        StringBuilder newView = new StringBuilder(str);
        if (goDir - 30  <= 10 && goDir - 30 >= 0) newView.setCharAt(goDir - 30, '▨');
        newView.setCharAt(goDir + 10, '▨');
        newView.setCharAt(goDir + 50, '▨');
        str = newView.toString();

        return str;
    }

    private String finalSymbols(String input) {
        String util = Util.getSymbolStart() + replaceRegular(input.substring(0, 10)) + replaceHovered(String.valueOf(input.charAt(10))) + replaceRegular(input.substring(11)) + Util.getSymbolEnd();
        return util;
    }

    private void cacheWaypointSymbols(String sr) {
        for (int i = 0; i < sr.toCharArray().length; i++) {
            char current = sr.charAt(i);
            int curLocation = startLoc + i - 10;
            if (curLocation <1) curLocation += 40;
            if(curLocation >=41) curLocation -=40;
            if (current == '▓') {
                SavePoint point = getSavePointAt(curLocation);
                if (point == null && curLocation == 40) {
                    point = getSavePointAt(0);
                }
                if (point == null) {
                    cachedInputs.add(Util.getRegular(Symbol.WAYPOINT));
                    continue;
                }
                if (startLoc == curLocation || (startLoc == 0 && curLocation == 40)) {
                    cachedInputs.add(point.getSymbolHov());
                    continue;
                }
                cachedInputs.add(point.getSymbol());
            }
        }
    }

    private String addInCachedWaypoints(String str) {
        int counter = 0;
        for(int i = 0; i < str.length(); i++) {
            char current = str.charAt(i);
            if (current == '▓')  {
                str = str.replaceFirst("▓", cachedInputs.get(counter));
                counter +=1;
            }
        }
        return str;
    }

    public void updateCompass() {
        int yaw = Math.round((player.getLocation().getYaw() + 360) / 9);
        bar.setTitle(makeBasicCompass(yaw));
        if (location == null || location.getActivePoints() == null) return;
        ArrayList<SavePoint> newPoints = new ArrayList<>();
        for (SavePoint cur : location.getTrueActivePoints()) {
            if (cur == null) continue;
            if (!cur.isMythic() && !cur.isNPC() && !cur.isTowny()) newPoints.add(cur);
        }
        location.setActivePoints(newPoints);
    }



    private void updateTrackingDistance() {
        if(location == null || location.getTarget() == null || location.getTarget().getWorld() == null ||  !location.getTarget().getWorld().equals(player.getWorld()) || !location.isTracking()) {
            bar.setProgress(1F);
            return;
        }
        double dist = location.getOrigin().distanceSquared(location.getTarget());
        double newDist = player.getLocation().distanceSquared(location.getTarget());
        double travel = newDist / dist;

        if (travel >= 1F) travel = 1F;

        if (checkCoords(player.getLocation(), location.getTarget())) {
            bar.setProgress(1F);
            return;
        } else {
            bar.setProgress(1F - travel);
        }
    }

    private boolean checkCoords(Location loc1, Location loc2) {
        return loc1.getX() == loc2.getX() && loc1.getZ() == loc2.getZ();
    }

    private SavePoint getSavePointAt(int dir) {
        Location playerLoc = player.getLocation();
        for (SavePoint cur : location.getActivePoints()) {
            Vector target = cur.getLoc1().toVector();
            playerLoc.setDirection(target.subtract(playerLoc.toVector()));
            float playerYaw = playerLoc.getYaw();
            int goDir2 = Math.round(playerYaw / 9);
            if(goDir2 == dir) return cur;
        }
        return null;
    }



    //All methods below here are external plugin integrations

    private String addMythicPoints(String string) {
        return string;
    }


}
