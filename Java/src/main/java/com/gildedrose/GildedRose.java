package com.gildedrose;

import com.gildedrose.updater.ItemUpdaterFactory;

/**
 * Ages the inn's inventory by one day. The per-category ageing rules live in
 * dedicated {@link com.gildedrose.updater.ItemUpdater} strategies, keeping this
 * class focused on iterating over the inventory (Single Responsibility).
 */
class GildedRose {

    Item[] items;

    private final ItemUpdaterFactory updaterFactory = new ItemUpdaterFactory();

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            updaterFactory.updaterFor(item).update(item);
        }
    }
}
