package co.wethinkcode.trafficflow;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntersectionCleanerTest {

    @Test
    void shouldCleanIntersectionId() {

        String result =IntersectionCleaner.cleanId(" int-1002 ");
        assertEquals("INT-1002", result);
    }

    @Test
    void shouldReturnNullForBlankId() {

        String result =IntersectionCleaner.cleanId("   ");
        assertEquals(null, result);
    }

    @Test
    void shouldCleanDistrict() {

        String result =IntersectionCleaner.cleanDistrict(" downtown ");
        assertEquals("Downtown", result);
    }

    @Test
    void shouldCollapseDoubleSpaces() {

        String result =IntersectionCleaner.cleanDistrict(" East  Side ");
        assertEquals("East Side", result);
    }

    @Test
    void shouldCleanSignalType() {

        String result =IntersectionCleaner.cleanSignalType(" ROUNDABOUT ");
        assertEquals("roundabout", result);
    }

    @Test
void shouldConvertMissingSignalTypeToNull() {

    assertEquals(null,IntersectionCleaner.cleanSignalType(""));
    assertEquals(null,IntersectionCleaner.cleanSignalType("unknown"));
    assertEquals(null,IntersectionCleaner.cleanSignalType("N/A"));
    assertEquals(null,IntersectionCleaner.cleanSignalType("TBD"));
    assertEquals(null,IntersectionCleaner.cleanSignalType("-"));
}



}