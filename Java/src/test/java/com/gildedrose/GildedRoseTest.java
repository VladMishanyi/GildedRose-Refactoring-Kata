package com.gildedrose;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GildedRoseTest {

    private static final String AGED_BRIE = "Aged Brie";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";
    private static final String CONJURED = "Conjured Mana Cake";

    private static final int MAX_QUALITY = 50;
    private static final int MIN_QUALITY = 0;

    /** Ages a single item by one day and returns it for assertions. */
    private static Item age(String name, int sellIn, int quality) {
        Item item = new Item(name, sellIn, quality);
        new GildedRose(new Item[] { item }).updateQuality();
        return item;
    }

    @Nested
    @DisplayName("Standard items")
    class StandardItems {

        @Test
        void degradeInQualityAndSellInByOneEachDay() {
            Item item = age("Elixir of the Mongoose", 5, 7);
            assertEquals(4, item.sellIn);
            assertEquals(6, item.quality);
        }

        @Test
        void degradeTwiceAsFastOnceSellByDateHasPassed() {
            Item item = age("+5 Dexterity Vest", 0, 10);
            assertEquals(-1, item.sellIn);
            assertEquals(8, item.quality);
        }

        @Test
        void qualityNeverGoesNegativeBeforeSellByDate() {
            assertEquals(MIN_QUALITY, age("Elixir of the Mongoose", 5, 0).quality);
        }

        @Test
        void qualityNeverGoesNegativeAfterSellByDate() {
            assertEquals(MIN_QUALITY, age("Elixir of the Mongoose", -1, 1).quality);
        }
    }

    @Nested
    @DisplayName("Aged Brie")
    class AgedBrie {

        @Test
        void increasesInQualityAsItAges() {
            assertEquals(1, age(AGED_BRIE, 2, 0).quality);
        }

        @Test
        void increasesTwiceAsFastOnceSellByDateHasPassed() {
            assertEquals(2, age(AGED_BRIE, -1, 0).quality);
        }

        @Test
        void qualityIsCappedAtFifty() {
            assertEquals(MAX_QUALITY, age(AGED_BRIE, -1, MAX_QUALITY).quality);
        }
    }

    @Nested
    @DisplayName("Sulfuras (legendary)")
    class Sulfuras {

        @Test
        void neverChangesQualityOrSellIn() {
            Item item = age(SULFURAS, 0, 80);
            assertEquals(0, item.sellIn);
            assertEquals(80, item.quality);
        }

        @Test
        void neverChangesEvenAfterSellByDate() {
            Item item = age(SULFURAS, -1, 80);
            assertEquals(-1, item.sellIn);
            assertEquals(80, item.quality);
        }
    }

    @Nested
    @DisplayName("Backstage passes")
    class BackstagePasses {

        @ParameterizedTest(name = "sellIn={0} gains {1}")
        @CsvSource({
            "15, 1",  // more than 10 days out
            "11, 1",  // still more than 10 days out
            "10, 2",  // 10 days or fewer
            "6,  2",  // 6 days out
            "5,  3",  // 5 days or fewer
            "1,  3"   // last day before the concert
        })
        void gainsValueAsConcertApproaches(int sellIn, int expectedGain) {
            assertEquals(20 + expectedGain, age(BACKSTAGE_PASS, sellIn, 20).quality);
        }

        @Test
        void qualityIsCappedAtFiftyEvenWithTripleGain() {
            assertEquals(MAX_QUALITY, age(BACKSTAGE_PASS, 5, 49).quality);
        }

        @Test
        void qualityDropsToZeroAfterTheConcert() {
            assertEquals(MIN_QUALITY, age(BACKSTAGE_PASS, 0, MAX_QUALITY).quality);
        }
    }

    @Nested
    @DisplayName("Conjured items")
    class ConjuredItems {

        @Test
        void degradeTwiceAsFastAsStandardItems() {
            assertEquals(4, age(CONJURED, 3, 6).quality);
        }

        @Test
        void degradeFourTimesAsFastOnceSellByDateHasPassed() {
            assertEquals(2, age(CONJURED, -1, 6).quality);
        }

        @Test
        void qualityNeverGoesNegative() {
            assertEquals(MIN_QUALITY, age(CONJURED, -1, 1).quality);
        }
    }

    @Nested
    @DisplayName("Quality invariants")
    class QualityInvariants {

        @ParameterizedTest
        @ValueSource(strings = { "Elixir of the Mongoose", AGED_BRIE, BACKSTAGE_PASS, CONJURED })
        void nonLegendaryQualityNeverExceedsFifty(String name) {
            int quality = age(name, 5, MAX_QUALITY).quality;
            assertTrue(quality <= MAX_QUALITY, () -> name + " quality should stay <= 50 but was " + quality);
        }
    }

    @Test
    @DisplayName("updateQuality ages every item in the inventory")
    void agesEveryItemInTheInventory() {
        Item[] items = new Item[] {
            new Item("Elixir of the Mongoose", 5, 7),
            new Item(AGED_BRIE, 2, 0),
            new Item(CONJURED, 3, 6)
        };

        new GildedRose(items).updateQuality();

        assertEquals(6, items[0].quality);
        assertEquals(1, items[1].quality);
        assertEquals(4, items[2].quality);
    }
}
