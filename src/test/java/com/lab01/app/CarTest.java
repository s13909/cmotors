package com.lab01.app;

import junit.framework.Test;
import static org.junit.Assert.assertNotNull;


public class CarTest {
    @Test
    public void createTest() {
        Car fiat = new Car();
        assertNotNull(fiat);
    }
}