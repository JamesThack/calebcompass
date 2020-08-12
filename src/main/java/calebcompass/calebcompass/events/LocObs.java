package calebcompass.calebcompass.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LocObs {

    public LocObs (Player player, Location loc1, Location loc2) {
        this.player = player.getUniqueId().toString();
        origin = loc1;
        certainEnd = loc2;

    }

    public LocObs(String player, Location loc1, Location loc2) {
        this.player = player;
        origin = loc1;
        certainEnd = loc2;
        this.isShowing = true;
        this.isTracking = true;
    }

    private String player;
    private Location origin;
    private Location certainEnd;
    private boolean isTracking;
    private boolean isShowing;

    public String getPlayer() {
        return player;
    }

    public Location getOrigin() {
        return origin;
    }

    public void setOrigin(Location origin) {
        this.origin = origin;
    }

    public Location getCertainEnd() {
        return certainEnd;
    }

    public void setCertainEnd(Location certainEnd) {
        this.certainEnd = certainEnd;
    }

    public boolean isTracking() {
        return isTracking;
    }

    public void setTracking(boolean tracking) {
        isTracking = tracking;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public boolean isShowing() {
        return isShowing;
    }

    public void setShowing(boolean showing) {
        isShowing = showing;
    }
}
