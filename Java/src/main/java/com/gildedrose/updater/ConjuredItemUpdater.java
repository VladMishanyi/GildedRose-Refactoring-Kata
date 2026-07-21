package com.gildedrose.updater;

import com.gildedrose.Item;

/**
 * Conjured items degrade in quality twice as fast as ordinary goods: two per
 * day before the sell-by date and four per day once it has passed.
 */
public class ConjuredItemUpdater extends AbstractItemUpdater {

    static final String NAME_PREFIX = "Conjured";

    private static final int NORMAL_DEGRADATION = 2;
    private static final int EXPIRED_DEGRADATION = 4;

    /**
     * {@inheritDoc}
     *
     * <p>Handles any item whose name begins with "Conjured".
     */
    @Override
    public boolean handles(Item item) {
        return item.name != null && item.name.startsWith(NAME_PREFIX);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Lowers quality by two per day, or by four once the sell-by date has passed.
     */
    @Override
    public void update(Item item) {
        decreaseSellIn(item);
        decreaseQuality(item, isExpired(item) ? EXPIRED_DEGRADATION : NORMAL_DEGRADATION);
    }
}
