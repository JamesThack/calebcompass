package calebcompass.calebcompass.towny;

public class TownObject {

    private String hoveredOverride;
    private String normalOverride;
    private int range;

    public TownObject(String hoveredOverride, String normalOverride, int range) {
        this.hoveredOverride = hoveredOverride;
        this.normalOverride = normalOverride;
        this.range = range;
    }

    public String getHoveredOverride() {
        return hoveredOverride;
    }

    public String getNormalOverride() {
        return normalOverride;
    }

    public int getRange() {
        return range;
    }
}
