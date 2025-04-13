package sc2002.fcsi.grp3.model;

import sc2002.fcsi.grp3.model.enums.FlatType;

public class Flat {
    private final FlatType type;
    private int unitsAvailable;
    private final float sellingPrice;

    public Flat(FlatType type, int unitsAvailable, float sellingPrice) {
        this.type = type;
        this.unitsAvailable = unitsAvailable;
        this.sellingPrice = sellingPrice;
    }

    public FlatType getType() {
        return type;
    }

    public int getUnitsAvailable() {
        return unitsAvailable;
    }

    public float getSellingPrice() {
        return sellingPrice;
    }

    public boolean reduceUnitsAvailable() {
        if (unitsAvailable > 0) {
            unitsAvailable--;
            return true;
        }
        return false;
    }
}
