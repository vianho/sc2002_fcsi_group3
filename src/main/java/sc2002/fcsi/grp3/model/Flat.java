package sc2002.fcsi.grp3.model;

import sc2002.fcsi.grp3.model.enums.FlatType;

/**
 * The Flat class represents a flat in a housing project.
 * It contains details such as the flat type, available units, and selling price.
 */
public class Flat {

    private final FlatType type;
    private int unitsAvailable;
    private final float sellingPrice;

    /**
     * Constructs a Flat with the specified type, available units, and selling price.
     *
     * @param type           the type of the flat
     * @param unitsAvailable the number of units available
     * @param sellingPrice   the selling price of the flat
     */
    public Flat(FlatType type, int unitsAvailable, float sellingPrice) {
        this.type = type;
        this.unitsAvailable = unitsAvailable;
        this.sellingPrice = sellingPrice;
    }

    /**
     * Gets the type of the flat.
     *
     * @return the flat type
     */
    public FlatType getType() {
        return type;
    }

    /**
     * Gets the number of units available for the flat.
     *
     * @return the number of available units
     */
    public int getUnitsAvailable() {
        return unitsAvailable;
    }

    /**
     * Gets the selling price of the flat.
     *
     * @return the selling price
     */
    public float getSellingPrice() {
        return sellingPrice;
    }

    /**
     * Reduces the number of available units by one if units are available.
     *
     * @return true if the operation was successful, false otherwise
     */
    public boolean reduceUnitsAvailable() {
        if (unitsAvailable > 0) {
            unitsAvailable--;
            return true;
        }
        return false;
    }
}
