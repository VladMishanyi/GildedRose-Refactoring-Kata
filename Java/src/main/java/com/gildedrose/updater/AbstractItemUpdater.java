package com.gildedrose.updater;

import com.gildedrose.Item;

/**
 * Base class providing the quality bounds and the small set of mutating
 * primitives shared by every {@link ItemUpdater}. Keeping these rules in one
 * place avoids duplicating the clamping logic across strategies (DRY).
 */
abstract class AbstractItemUpdater implements ItemUpdater {

    protected static final int MIN_QUALITY = 0;
    protected static final int MAX_QUALITY = 50;

    /**
     * An item is expired once its sell-by date has passed, i.e. when
     * {@code sellIn} has become negative after the day's decrement.
     */
    protected boolean isExpired(Item item) {
        return item.sellIn < 0;
    }

    protected void decreaseSellIn(Item item) {
        item.sellIn--;
    }

    protected void increaseQuality(Item item, int amount) {
        item.quality = Math.min(MAX_QUALITY, item.quality + amount);
    }

    protected void decreaseQuality(Item item, int amount) {
        item.quality = Math.max(MIN_QUALITY, item.quality - amount);
    }
}
