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
     *
     * @param item the item to inspect
     * @return {@code true} if the item's sell-by date has passed
     */
    protected boolean isExpired(Item item) {
        return item.sellIn < 0;
    }

    /**
     * Advances the item one day closer to (or past) its sell-by date.
     *
     * @param item the item to age
     */
    protected void decreaseSellIn(Item item) {
        item.sellIn--;
    }

    /**
     * Raises the item's quality by {@code amount}, capped at {@link #MAX_QUALITY}.
     *
     * @param item   the item to improve
     * @param amount the number of quality points to add
     */
    protected void increaseQuality(Item item, int amount) {
        item.quality = Math.min(MAX_QUALITY, item.quality + amount);
    }

    /**
     * Lowers the item's quality by {@code amount}, floored at {@link #MIN_QUALITY}.
     *
     * @param item   the item to degrade
     * @param amount the number of quality points to remove
     */
    protected void decreaseQuality(Item item, int amount) {
        item.quality = Math.max(MIN_QUALITY, item.quality - amount);
    }
}
