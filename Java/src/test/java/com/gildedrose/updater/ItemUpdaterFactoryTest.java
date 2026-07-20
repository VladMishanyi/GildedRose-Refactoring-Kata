package com.gildedrose.updater;

import com.gildedrose.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemUpdaterFactoryTest {

    private final ItemUpdaterFactory factory = new ItemUpdaterFactory();

    @ParameterizedTest(name = "\"{0}\" -> {1}")
    @CsvSource({
        "Aged Brie,                                  AgedBrieUpdater",
        "Sulfuras Hand of Ragnaros,                  StandardItemUpdater",
        "'Sulfuras, Hand of Ragnaros',               SulfurasUpdater",
        "'Backstage passes to a TAFKAL80ETC concert',BackstagePassUpdater",
        "Conjured Mana Cake,                         ConjuredItemUpdater",
        "Conjured,                                   ConjuredItemUpdater",
        "Elixir of the Mongoose,                     StandardItemUpdater",
        "'',                                         StandardItemUpdater"
    })
    void selectsTheUpdaterMatchingTheItemName(String name, String expectedUpdater) {
        ItemUpdater updater = factory.updaterFor(new Item(name, 0, 0));
        assertEquals(expectedUpdater, updater.getClass().getSimpleName());
    }

    @Test
    void fallsBackToStandardUpdaterForNullName() {
        ItemUpdater updater = factory.updaterFor(new Item(null, 0, 0));
        assertEquals("StandardItemUpdater", updater.getClass().getSimpleName());
    }
}
