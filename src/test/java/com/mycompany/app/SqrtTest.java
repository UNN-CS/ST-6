package com.mycompany.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SqrtTest {

    private static final double DELTA = 1e-9;

    @Test
    void testAverageTrue() {
        Sqrt sqrt = new Sqrt(-1.0);
        assertEquals(8.0, sqrt.average(6.0, 10.0), DELTA);
        assertEquals(0.0, sqrt.average(-5.0, 5.0), DELTA);
        assertEquals(0.0, sqrt.average(0.0, 0.0), DELTA);
    }

    @Test
    void testAverageFalse() {
        Sqrt sqrt = new Sqrt(-1.0);
        assertNotEquals(8.0, sqrt.average(69.0, 67.0), DELTA);
        assertNotEquals(0.0, sqrt.average(-54.0, 11.0), DELTA);
        assertNotEquals(0.0, sqrt.average(98.0, 100.0), DELTA);
    }

    @Test
    void testGoodTrue() {
        Sqrt sqrt = new Sqrt(-1.0);
        assertTrue(sqrt.good(2.0, 4.0));
        assertTrue(sqrt.good(5.0, 25.0));
        assertTrue(sqrt.good(6.0, 36.0));
    }

    @Test
    void testGoodFalse() {
        Sqrt sqrt = new Sqrt(-1.0);
        assertFalse(sqrt.good(1.0, 4.0));
        assertFalse(sqrt.good(2.0, 25.0));
        assertFalse(sqrt.good(3.0, 36.0));
    }

    @Test
    void testImprove() {
        Sqrt sqrt = new Sqrt(-1.0);
        double improved1 = sqrt.improve(1.0, 2.0);
        assertEquals(1.5, improved1, DELTA);

        double improved2 = sqrt.improve(10.0, 100.0);
        assertEquals(10.0, improved2, DELTA);

        double improved3 = sqrt.improve(9.0, 81.0);
        assertEquals(9.0, improved3, DELTA);
    }

    @Test
    void testIter() {
        Sqrt sqrt = new Sqrt(-1.0);
        double result = sqrt.iter(1.0, 2.0);
        assertEquals(Math.sqrt(2), result, 1e-8);

        double result3 = sqrt.iter(9.0, 81.0);
        assertEquals(9.0, result3, 1e-8);

        double result4 = sqrt.iter(10.0, 100.0);
        assertEquals(10.0, result4, 1e-8);
    }

    @Test
    void testCalcSqrt4() {
        Sqrt sqrt = new Sqrt(4);
        assertEquals(2.0, sqrt.calc(), DELTA);
    }

    @Test
    void testCalcSqrt2() {
        Sqrt sqrt = new Sqrt(2);
        assertEquals(Math.sqrt(2), sqrt.calc(), 1e-8);
    }

    @Test
    void testCalcSqrt0() {
        Sqrt sqrt = new Sqrt(0);
        assertEquals(0.0, sqrt.calc(), DELTA);
    }

    @Test
    void testCalcSqrtSmall() {
        Sqrt sqrt = new Sqrt(0.0001);
        assertEquals(0.01, sqrt.calc(), 1e-8);
    }

    @Test
    void testCalcSqrtLarge() {
        Sqrt sqrt = new Sqrt(1000000);
        assertEquals(1000.0, sqrt.calc(), 1e-8);
    }
}