package co.wethinkcode.trafficflow;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class IntersectionServiceTest {
    @Test
    void canFindKnownIntersection() {

        IntersectionServiceApp service =new IntersectionServiceApp();
        service.addIntersection(new Intersection("INT-1001","Downtown","4-way",true));
        Intersection result = service.findIntersection("INT-1001");

        assertEquals("INT-1001", result.getId());
        assertEquals("Downtown", result.getDistrict());
    }

    @Test
    void unknownIntersectionReturnsNull() {

        IntersectionServiceApp service = new IntersectionServiceApp();
        Intersection result =service.findIntersection("INT-9999");
        assertNull(result);
    }

    
}
