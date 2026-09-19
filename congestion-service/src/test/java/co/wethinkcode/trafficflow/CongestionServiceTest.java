package co.wethinkcode.trafficflow;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CongestionServiceTest {

    @Test
    void congestionLevelStartsAtZero() {
        CongestionServiceApp service = new CongestionServiceApp();

        assertEquals(0, service.getCongestionLevel());
    }

    @Test
    void canSetValidCongestionLevel() {
        CongestionServiceApp service = new CongestionServiceApp();

        service.setCongestionLevel(5);

        assertEquals(5, service.getCongestionLevel());
    }

     @Test
    void levelEightIsValid() {
        CongestionServiceApp service = new CongestionServiceApp();

        service.setCongestionLevel(8);

        assertEquals(8, service.getCongestionLevel());
    }

    @Test
    void negativeLevelIsRejected() {
        CongestionServiceApp service = new CongestionServiceApp();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.setCongestionLevel(-1)
        );
    }

    @Test
    void levelAboveEightIsRejected() {
        CongestionServiceApp service = new CongestionServiceApp();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.setCongestionLevel(9)
        );
    }
    @Test
    void levelZeroIsValid() {

        CongestionServiceApp service = new CongestionServiceApp();

        service.setCongestionLevel(0);

        assertEquals(0,service.getCongestionLevel());
    }


}
