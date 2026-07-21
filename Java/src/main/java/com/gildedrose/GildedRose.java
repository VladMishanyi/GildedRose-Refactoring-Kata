package com.gildedrose;

import com.gildedrose.updater.ItemUpdaterFactory;

/**
 * Ages the inn's inventory by one day. The per-category ageing rules live in
 * dedicated {@link com.gildedrose.updater.ItemUpdater} strategies, keeping this
 * class focused on iterating over the inventory (Single Responsibility).
 */
class GildedRose {

    Item[] items;

    private final ItemUpdaterFactory updaterFactory;

    /**
     * Creates an inventory manager for the given items.
     *
     * @param items the inventory to manage; mutated in place by {@link #updateQuality()}
     */
    public GildedRose(Item[] items) {
        this.items = items;
        this.updaterFactory = new ItemUpdaterFactory();
    }

    /**
     * Ages every item in the inventory by one day, delegating each item to the
     * {@link com.gildedrose.updater.ItemUpdater} that owns its category-specific
     * rules.
     */
    public void updateQuality() {
        for (Item item : items) {
            updaterFactory.updaterFor(item).update(item);
        }
    }
}
