package com.gildedrose.updater;

import com.gildedrose.Item;

import java.util.Arrays;
import java.util.List;

/**
 * Selects the {@link ItemUpdater} responsible for a given {@link Item}.
 *
 * <p>Specialised updaters are consulted in order and the first one that claims
 * the item wins; {@link StandardItemUpdater} sits last as the catch-all. To
 * support a new kind of item, add its updater to this list — no existing
 * strategy needs to change.
 */
public class ItemUpdaterFactory {

    private static final String NO_UPDATER_FOUND_MESSAGE = "No updater registered for item: %s";

    private final List<ItemUpdater> updaters;

    /**
     * Builds a factory with the default set of updaters. The specialised
     * updaters are consulted in registration order and {@link StandardItemUpdater}
     * sits last as the catch-all fallback.
     */
    public ItemUpdaterFactory() {
        this.updaters = Arrays.asList(
            new AgedBrieUpdater(),
            new BackstagePassUpdater(),
            new SulfurasUpdater(),
            new ConjuredItemUpdater(),
            new StandardItemUpdater()
        );
    }

    /**
     * Returns the updater responsible for ageing the given item.
     *
     * @param item the item whose ageing strategy is required
     * @return the first registered {@link ItemUpdater} that handles the item
     * @throws IllegalStateException if no registered updater handles the item
     */
    public ItemUpdater updaterFor(Item item) {
        return updaters.stream()
            .filter(updater -> updater.handles(item))
            .findFirst()
            .orElseThrow(() ->
                new IllegalStateException(String.format(NO_UPDATER_FOUND_MESSAGE, item.name)));
    }
}
