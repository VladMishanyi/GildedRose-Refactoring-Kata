package com.gildedrose.updater;

import com.gildedrose.Item;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ItemUpdaterFactoryTest {

    private final ItemUpdaterFactory factory = new ItemUpdaterFactory();

    static Stream<Arguments> updaterSelectionCases() {
        return Stream.of(
            arguments("Aged Brie", AgedBrieUpdater.class),
            arguments("Sulfuras, Hand of Ragnaros", SulfurasUpdater.class),
            arguments("Backstage passes to a TAFKAL80ETC concert", BackstagePassUpdater.class),
            arguments("Conjured Mana Cake", ConjuredItemUpdater.class),
            arguments("Conjured", ConjuredItemUpdater.class),
            arguments("Elixir of the Mongoose", StandardItemUpdater.class),
            arguments("Sulfuras Hand of Ragnaros", StandardItemUpdater.class), // not the exact legendary name
            arguments("", StandardItemUpdater.class),
            arguments(null, StandardItemUpdater.class)
        );
    }

    @ParameterizedTest(name = "\"{0}\" -> {1}")
    @MethodSource("updaterSelectionCases")
    void selectsTheUpdaterMatchingTheItemName(String name, Class<? extends ItemUpdater> expectedUpdater) {
        ItemUpdater updater = factory.updaterFor(new Item(name, 0, 0));
        assertEquals(expectedUpdater, updater.getClass());
    }

}
