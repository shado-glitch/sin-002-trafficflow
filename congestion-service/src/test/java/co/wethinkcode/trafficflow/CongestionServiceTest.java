package co.wethinkcode.trafficflow;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CongestionServiceTest {

    @Test
    void congestionLevelStartsAtZero() {
        CongestionServiceApp service = new CongestionServiceApp();

        assertEquals(0, service.getCongestionLevel());
    }
}
