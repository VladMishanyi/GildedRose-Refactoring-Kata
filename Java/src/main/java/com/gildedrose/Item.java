package com.gildedrose;

/**
 * A single item of stock in the inn's inventory.
 *
 * <p>An item is a mutable data holder describing what the goods are and where
 * they stand relative to their sell-by date. The rules for ageing an item are
 * intentionally kept out of this class and live in the
 * {@link com.gildedrose.updater.ItemUpdater} strategies instead.
 *
 * <p><strong>Note:</strong> the shape of this class (its fields and constructor)
 * must not be changed, as required by the Gilded Rose specification.
 */
public class Item {

    /** The display name of the item, e.g. {@code "Aged Brie"}. */
    public String name;

    /** The number of days left to sell the item; may become negative once overdue. */
    public int sellIn;

    /** How valuable the item is; normally bounded between 0 and 50. */
    public int quality;

    /**
     * Creates an item with the given name, sell-by countdown and quality.
     *
     * @param name    the display name of the item
     * @param sellIn  the number of days left to sell the item
     * @param quality how valuable the item is
     */
    public Item(String name, int sellIn, int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }

    /**
     * @return the item rendered as {@code "name, sellIn, quality"}
     */
   @Override
   public String toString() {
        return this.name + ", " + this.sellIn + ", " + this.quality;
    }
}
