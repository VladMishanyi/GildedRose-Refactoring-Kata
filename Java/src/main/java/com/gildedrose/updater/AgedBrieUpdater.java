package com.gildedrose.updater;

import com.gildedrose.Item;

/**
 * Aged Brie improves with age: its quality rises by one each day and twice as
 * fast once the sell-by date has passed, capped at the maximum quality.
 */
public class AgedBrieUpdater extends AbstractItemUpdater {

    static final String NAME = "Aged Brie";

    private static final int NORMAL_IMPROVEMENT = 1;
    private static final int EXPIRED_IMPROVEMENT = 2;

    /**
     * {@inheritDoc}
     *
     * <p>Handles the "Aged Brie" item.
     */
    @Override
    public boolean handles(Item item) {
        return NAME.equals(item.name);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Raises quality by one per day, or by two once the sell-by date has passed.
     */
    @Override
    public void update(Item item) {
        decreaseSellIn(item);
        increaseQuality(item, isExpired(item) ? EXPIRED_IMPROVEMENT : NORMAL_IMPROVEMENT);
    }
}
