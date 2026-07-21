package com.gildedrose.updater;

import com.gildedrose.Item;

/**
 * Default strategy for ordinary goods: quality drops by one each day and twice
 * as fast once the sell-by date has passed, never falling below zero.
 *
 * <p>This updater is the catch-all fallback and therefore handles any item.
 */
public class StandardItemUpdater extends AbstractItemUpdater {

    private static final int NORMAL_DEGRADATION = 1;
    private static final int EXPIRED_DEGRADATION = 2;

    /**
     * {@inheritDoc}
     *
     * <p>As the catch-all fallback, this updater handles every item.
     */
    @Override
    public boolean handles(Item item) {
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Lowers quality by one per day, or by two once the sell-by date has passed.
     */
    @Override
    public void update(Item item) {
        decreaseSellIn(item);
        decreaseQuality(item, isExpired(item) ? EXPIRED_DEGRADATION : NORMAL_DEGRADATION);
    }
}
