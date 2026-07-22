package com.gildedrose;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Approval (characterisation) test for the whole system: it ages the
 * representative {@link GildedRoseReport#sampleInventory() sample inventory} for
 * thirty days and asserts the rendered report matches an approved snapshot.
 *
 * <p>This automates, inside the normal test run, the same end-to-end check that
 * the external TextTest tool performs against {@code texttests/ThirtyDays}.
 */
class GildedRoseReportApprovalTest {

    private static final int DAYS = 30;
    private static final String APPROVED_SNAPSHOT = "/com/gildedrose/thirtyDays.approved.txt";

    @Test
    @DisplayName("thirty-day report for the sample inventory matches the approved snapshot")
    void thirtyDayReportMatchesApprovedSnapshot() {
        String actual = GildedRoseReport.render(GildedRoseReport.sampleInventory(), DAYS);

        assertEquals(readApprovedSnapshot(), actual);
    }

    private String readApprovedSnapshot() {
        try (InputStream in = getClass().getResourceAsStream(APPROVED_SNAPSHOT)) {
            if (in == null) {
                throw new IllegalStateException("Approved snapshot not found: " + APPROVED_SNAPSHOT);
            }
            try (Scanner scanner = new Scanner(in, "UTF-8").useDelimiter("\\A")) {
                return scanner.hasNext() ? scanner.next() : "";
            }
        } catch (java.io.IOException e) {
            throw new IllegalStateException("Failed to read approved snapshot: " + APPROVED_SNAPSHOT, e);
        }
    }
}
