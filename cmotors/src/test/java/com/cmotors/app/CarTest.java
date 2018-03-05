package com.cmotors.app;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;

public class CarTest {
    @Test
    public void createTest() {
        Car fiat = new Car();
        assertNotNull(fiat);
    }
}
