package com.gildedrose;

/**
 * Shared test data and helpers for the Gilded Rose test suite.
 *
 * <p>Centralises the item names, quality bounds and the one-day ageing helper so
 * that individual test classes stay focused on behaviour rather than setup. This
 * class only holds constants and static utilities and is therefore not meant to
 * be instantiated.
 */
final class GildedRoseTestFixture {

    static final String STANDARD_ITEM = "Elixir of the Mongoose";
    static final String AGED_BRIE = "Aged Brie";
    static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    static final String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";
    static final String CONJURED = "Conjured Mana Cake";

    static final int MIN_QUALITY = 0;
    static final int MAX_QUALITY = 50;
    static final int SULFURAS_QUALITY = 80;

    /**
     * Builds a single item and ages it by one day.
     *
     * @param name    the item name
     * @param sellIn  the initial sell-by countdown
     * @param quality the initial quality
     * @return the same item after one {@link GildedRose#updateQuality()} cycle
     */
    static Item ageOneDay(String name, int sellIn, int quality) {
        Item item = new Item(name, sellIn, quality);
        new GildedRose(new Item[] { item }).updateQuality();
        return item;
    }
}
