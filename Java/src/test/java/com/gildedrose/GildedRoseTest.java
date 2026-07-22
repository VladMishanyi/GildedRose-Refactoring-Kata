package com.gildedrose;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.gildedrose.GildedRoseTestFixture.AGED_BRIE;
import static com.gildedrose.GildedRoseTestFixture.BACKSTAGE_PASS;
import static com.gildedrose.GildedRoseTestFixture.CONJURED;
import static com.gildedrose.GildedRoseTestFixture.MAX_QUALITY;
import static com.gildedrose.GildedRoseTestFixture.MIN_QUALITY;
import static com.gildedrose.GildedRoseTestFixture.STANDARD_ITEM;
import static com.gildedrose.GildedRoseTestFixture.SULFURAS;
import static com.gildedrose.GildedRoseTestFixture.SULFURAS_QUALITY;
import static com.gildedrose.GildedRoseTestFixture.ageOneDay;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GildedRoseTest {

    @Nested
    @DisplayName("Standard items")
    class StandardItems {

        @Test
        void degradeInQualityAndSellInByOneEachDay() {
            Item item = ageOneDay(STANDARD_ITEM, 5, 7);
            assertEquals(4, item.sellIn);
            assertEquals(6, item.quality);
        }

        @Test
        void degradeTwiceAsFastOnceSellByDateHasPassed() {
            Item item = ageOneDay(STANDARD_ITEM, 0, 10);
            assertEquals(-1, item.sellIn);
            assertEquals(8, item.quality);
        }

        @Test
        void qualityNeverGoesNegativeBeforeSellByDate() {
            assertEquals(MIN_QUALITY, ageOneDay(STANDARD_ITEM, 5, 0).quality);
        }

        @Test
        void qualityNeverGoesNegativeAfterSellByDate() {
            assertEquals(MIN_QUALITY, ageOneDay(STANDARD_ITEM, -1, 1).quality);
        }
    }

    @Nested
    @DisplayName("Aged Brie")
    class AgedBrie {

        @Test
        void increasesInQualityAsItAges() {
            assertEquals(1, ageOneDay(AGED_BRIE, 2, 0).quality);
        }

        @Test
        void increasesTwiceAsFastOnceSellByDateHasPassed() {
            assertEquals(2, ageOneDay(AGED_BRIE, -1, 0).quality);
        }

        @Test
        void qualityIsCappedAtFifty() {
            assertEquals(MAX_QUALITY, ageOneDay(AGED_BRIE, -1, MAX_QUALITY).quality);
        }
    }

    @Nested
    @DisplayName("Sulfuras (legendary)")
    class Sulfuras {

        @Test
        void neverChangesQualityOrSellIn() {
            Item item = ageOneDay(SULFURAS, 0, SULFURAS_QUALITY);
            assertEquals(0, item.sellIn);
            assertEquals(SULFURAS_QUALITY, item.quality);
        }

        @Test
        void neverChangesEvenAfterSellByDate() {
            Item item = ageOneDay(SULFURAS, -1, SULFURAS_QUALITY);
            assertEquals(-1, item.sellIn);
            assertEquals(SULFURAS_QUALITY, item.quality);
        }
    }

    @Nested
    @DisplayName("Backstage passes")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class BackstagePasses {

        Stream<Arguments> valueGainTiers() {
            return Stream.of(
                arguments(15, 1),  // more than 10 days out
                arguments(11, 1),  // still more than 10 days out
                arguments(10, 2),  // 10 days or fewer
                arguments(6, 2),   // 6 days out
                arguments(5, 3),   // 5 days or fewer
                arguments(1, 3)    // last day before the concert
            );
        }

        @ParameterizedTest(name = "sellIn={0} gains {1}")
        @MethodSource("valueGainTiers")
        void gainsValueAsConcertApproaches(int sellIn, int expectedGain) {
            assertEquals(20 + expectedGain, ageOneDay(BACKSTAGE_PASS, sellIn, 20).quality);
        }

        @Test
        void qualityIsCappedAtFiftyEvenWithTripleGain() {
            assertEquals(MAX_QUALITY, ageOneDay(BACKSTAGE_PASS, 5, 49).quality);
        }

        @Test
        void qualityDropsToZeroAfterTheConcert() {
            assertEquals(MIN_QUALITY, ageOneDay(BACKSTAGE_PASS, 0, MAX_QUALITY).quality);
        }
    }

    @Nested
    @DisplayName("Conjured items")
    class ConjuredItems {

        @Test
        void degradeTwiceAsFastAsStandardItems() {
            assertEquals(4, ageOneDay(CONJURED, 3, 6).quality);
        }

        @Test
        void degradeFourTimesAsFastOnceSellByDateHasPassed() {
            assertEquals(2, ageOneDay(CONJURED, -1, 6).quality);
        }

        @Test
        void qualityNeverGoesNegative() {
            assertEquals(MIN_QUALITY, ageOneDay(CONJURED, -1, 1).quality);
        }
    }

    @Nested
    @DisplayName("Quality invariants")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class QualityInvariants {

        @ParameterizedTest
        @MethodSource("nonLegendaryItems")
        void nonLegendaryQualityNeverExceedsFifty(String name) {
            int quality = ageOneDay(name, 5, MAX_QUALITY).quality;
            assertTrue(quality <= MAX_QUALITY, () -> name + " quality should stay <= 50 but was " + quality);
        }

        Stream<Arguments> nonLegendaryItems() {
            return Stream.of(
                arguments(STANDARD_ITEM),
                arguments(AGED_BRIE),
                arguments(BACKSTAGE_PASS),
                arguments(CONJURED)
            );
        }
    }

    @Test
    @DisplayName("updateQuality ages every item in the inventory")
    void agesEveryItemInTheInventory() {
        Item[] items = new Item[] {
            new Item(STANDARD_ITEM, 5, 7),
            new Item(AGED_BRIE, 2, 0),
            new Item(CONJURED, 3, 6)
        };

        new GildedRose(items).updateQuality();

        assertEquals(6, items[0].quality);
        assertEquals(1, items[1].quality);
        assertEquals(4, items[2].quality);
    }
}
