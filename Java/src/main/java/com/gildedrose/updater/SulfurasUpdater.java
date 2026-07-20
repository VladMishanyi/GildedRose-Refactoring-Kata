package com.gildedrose.updater;

import com.gildedrose.Item;

/**
 * Sulfuras is a legendary item: it never has to be sold and never changes in
 * quality, so ageing it is a no-op.
 */
public class SulfurasUpdater extends AbstractItemUpdater {

    static final String NAME = "Sulfuras, Hand of Ragnaros";

    @Override
    public boolean handles(Item item) {
        return NAME.equals(item.name);
    }

    @Override
    public void update(Item item) {
        // Legendary: sellIn and quality are immutable.
    }
}
