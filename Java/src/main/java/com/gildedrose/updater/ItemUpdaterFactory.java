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

    private final List<ItemUpdater> updaters = Arrays.asList(
        new AgedBrieUpdater(),
        new BackstagePassUpdater(),
        new SulfurasUpdater(),
        new ConjuredItemUpdater(),
        new StandardItemUpdater()
    );

    public ItemUpdater updaterFor(Item item) {
        return updaters.stream()
            .filter(updater -> updater.handles(item))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException(
                "No updater registered for item: " + item.name));
    }
}
