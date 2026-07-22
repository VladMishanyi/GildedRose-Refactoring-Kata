package com.gildedrose.updater;

import com.gildedrose.Item;

/**
 * Strategy that knows how to age a single {@link Item} by one day.
 *
 * <p>Each concrete updater encapsulates the rules for one category of item, so
 * adding a new category means adding a new implementation rather than changing
 * existing behaviour (Open/Closed Principle).
 */
public interface ItemUpdater {

    /**
     * @return {@code true} if this updater is responsible for the given item.
     */
    boolean handles(Item item);

    /**
     * Applies one day of ageing to the given item, mutating its
     * {@code sellIn} and {@code quality} in place.
     */
    void update(Item item);
}
