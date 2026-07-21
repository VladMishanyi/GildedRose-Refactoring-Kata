package com.gildedrose.updater;

import com.gildedrose.Item;

/**
 * Sulfuras is a legendary item: it never has to be sold and never changes in
 * quality, so ageing it is a no-op.
 */
public class SulfurasUpdater extends AbstractItemUpdater {

    static final String NAME = "Sulfuras, Hand of Ragnaros";

    /**
     * {@inheritDoc}
     *
     * <p>Handles the legendary "Sulfuras, Hand of Ragnaros" item.
     */
    @Override
    public boolean handles(Item item) {
        return NAME.equals(item.name);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Intentionally a no-op: Sulfuras is legendary, so its {@code sellIn} and
     * {@code quality} never change. An explicit updater is still required so
     * that Sulfuras does not fall through to {@link StandardItemUpdater} and get
     * degraded.
     */
    @Override
    public void update(Item item) {
        // Intentionally empty: legendary items never age.
    }
}
