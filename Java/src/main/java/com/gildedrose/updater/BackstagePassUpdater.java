package com.gildedrose.updater;

import com.gildedrose.Item;

/**
 * Backstage passes gain value as the concert approaches: by one normally, by
 * two with ten days or fewer left and by three with five days or fewer left.
 * Their quality drops to zero once the concert has passed.
 *
 * <p>The thresholds are evaluated against the sell-by value <em>before</em> the
 * day's decrement, matching the original behaviour.
 */
public class BackstagePassUpdater extends AbstractItemUpdater {

    static final String NAME = "Backstage passes to a TAFKAL80ETC concert";

    private static final int SECOND_TIER_DAYS = 10;
    private static final int THIRD_TIER_DAYS = 5;

    /**
     * {@inheritDoc}
     *
     * <p>Handles the backstage-pass item.
     */
    @Override
    public boolean handles(Item item) {
        return NAME.equals(item.name);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Raises quality as the concert approaches, then drops it to zero once
     * the concert has passed.
     */
    @Override
    public void update(Item item) {
        increaseQuality(item, valueGainFor(item.sellIn));
        decreaseSellIn(item);
        if (isExpired(item)) {
            item.quality = MIN_QUALITY;
        }
    }

    /**
     * Computes the quality gained for one day based on the days remaining
     * before the concert (evaluated before the day's decrement).
     *
     * @param sellIn the days remaining before the concert
     * @return {@code 3} with five days or fewer left, {@code 2} with ten days
     *         or fewer left, otherwise {@code 1}
     */
    private int valueGainFor(int sellIn) {
        if (sellIn <= THIRD_TIER_DAYS) {
            return 3;
        }
        if (sellIn <= SECOND_TIER_DAYS) {
            return 2;
        }
        return 1;
    }
}
