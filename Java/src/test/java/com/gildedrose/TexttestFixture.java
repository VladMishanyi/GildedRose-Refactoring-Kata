package com.gildedrose;

/**
 * Command-line entry point that prints the {@link GildedRoseReport} for the
 * external TextTest approval tool (see {@code Java/README.md} and the
 * {@code texttests/} folder). The equivalent check runs automatically as a
 * JUnit test in {@code GildedRoseReportApprovalTest}.
 */
public class TexttestFixture {

    private static final int DEFAULT_DAYS = 30;

    public static void main(String[] args) {
        int days = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_DAYS;
        System.out.print(GildedRoseReport.render(GildedRoseReport.sampleInventory(), days));
    }

}
