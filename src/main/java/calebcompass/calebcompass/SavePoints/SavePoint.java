package calebcompass.calebcompass.SavePoints;

import calebcompass.calebcompass.util.CompassInstance;
import calebcompass.calebcompass.util.ConfigManager;
import org.bukkit.Location;

public class SavePoint {

    private Location loc1;
    private String symbol;
    private String name;

    public SavePoint(Location loc1, String name,  String symbol) {
        this.loc1 = loc1;
        this.symbol = "§c !!! ";
        this.name = name;
        if (symbol != null)this.symbol = symbol;
    }

    public Location getLoc1() {
        return loc1;
    }

    public void setLoc1(Location loc1) {
        this.loc1 = loc1;
    }

    public String getSymbol() {
        return symbol;
    }

    public void savePoint() {
        SavePointConfig.getInstance().addSave(this);
    }

    public String getName() {
        return name;
    }
}
