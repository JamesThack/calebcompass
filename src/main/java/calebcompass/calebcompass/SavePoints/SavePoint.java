package calebcompass.calebcompass.SavePoints;

import calebcompass.calebcompass.CalebCompass;
import calebcompass.calebcompass.util.MessageUtil;
import org.bukkit.Location;

public class SavePoint {

    private Location loc1;
    private String symbol;
    private String symbolHov;
    private String name;
    private boolean isMythic;
    private boolean isNPC;
    private boolean isGlobal;
    private int maxRange;

    public SavePoint(Location loc1, String name,  String symbol, String hov) {
        this.loc1 = loc1;
        this.symbol = CalebCompass.getConfigManager().getString("regular.waypoint");
        this.symbolHov = CalebCompass.getConfigManager().getString("hovered.waypoint");
        this.name = name;
        if (symbol != null)this.symbol = symbol;
        if (hov != null) this.symbolHov = hov;
        isMythic = false;
        isNPC = false;
        isGlobal = false;
        maxRange = 0;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public void setMaxRange(int maxRange) {
        this.maxRange = maxRange;
    }

    public boolean isGlobal() {
        return isGlobal;
    }

    public void setGlobal(boolean global) {
        isGlobal = global;
    }

    public boolean isMythic() {
        return isMythic;
    }

    public void setMythic(boolean mythic) {
        isMythic = mythic;
    }

    public boolean isNPC() {
        return isNPC;
    }

    public void setNPC(boolean NPC) {
        isNPC = NPC;
    }

    public Location getLoc1() {
        return loc1;
    }

    public void setLoc1(Location loc1) {
        this.loc1 = loc1;
    }

    public String getSymbol() {
        return MessageUtil.colourise(symbol);
    }

    public String getSymbolHov() {
        return MessageUtil.colourise(symbolHov);
    }

    public void savePoint() {
        SavePointConfig.getInstance().addSave(this);
    }

    public String getName() {
        return name;
    }
}
