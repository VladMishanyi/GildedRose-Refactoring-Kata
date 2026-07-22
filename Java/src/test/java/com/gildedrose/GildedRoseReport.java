package com.gildedrose;

/**
 * Renders a day-by-day report of how a fixed sample inventory ages, in the
 * exact text format used for approval testing.
 *
 * <p>This is the single source of truth shared by {@link TexttestFixture} (which
 * prints it for the external TextTest tool) and the JUnit approval test (which
 * asserts it against an approved snapshot), so the two can never drift apart.
 */
final class GildedRoseReport {

    private static final String HEADER = "OMGHAI!";
    private static final String LINE = "\n";

    /**
     * @return a fresh copy of the representative inventory, covering one item of
     *         each category plus the notable edge cases (Sulfuras before and
     *         after its sell date, backstage passes at each pricing tier and an
     *         item near the quality cap)
     */
    static Item[] sampleInventory() {
        return new Item[] {
            new Item("+5 Dexterity Vest", 10, 20),
            new Item("Aged Brie", 2, 0),
            new Item("Elixir of the Mongoose", 5, 7),
            new Item("Sulfuras, Hand of Ragnaros", 0, 80),
            new Item("Sulfuras, Hand of Ragnaros", -1, 80),
            new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
            new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
            new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
            new Item("Conjured Mana Cake", 3, 6)
        };
    }

    /**
     * Renders the inventory's state for day {@code 0} through day {@code days}
     * inclusive, ageing it one day between each printed block.
     *
     * @param items the inventory to age; mutated in place
     * @param days  the number of days to simulate (day 0 is the initial state)
     * @return the full report as a single string
     */
    static String render(Item[] items, int days) {
        StringBuilder report = new StringBuilder();
        report.append(HEADER).append(LINE);

        GildedRose app = new GildedRose(items);
        for (int day = 0; day <= days; day++) {
            report.append("-------- day ").append(day).append(" --------").append(LINE);
            report.append("name, sellIn, quality").append(LINE);
            for (Item item : items) {
                report.append(item).append(LINE);
            }
            report.append(LINE);
            app.updateQuality();
        }
        return report.toString();
    }
}
