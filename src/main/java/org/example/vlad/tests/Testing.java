package org.example.vlad.tests;

import org.example.Calculator.Calculator;
import org.example.CustomTest.Before;
import org.example.CustomTest.Test;


import static org.example.CustomTest.Asserts.assertEquals;
import static org.example.CustomTest.Asserts.assertTrue;


public class Testing {
    private Calculator calc;

    @Before
    public void before() {
        calc = new Calculator();
    }

    @Test
    public void sumTest() {
        assertEquals(calc.sum("5 5"," "),11);
        assertEquals(calc.sum("3 5 6", " "), 14);
        assertEquals(calc.sum("5 9 6", " "), 20);
        assertEquals(calc.sum("5 6", " "), 12);
    }

    @Test
    public void minusTest() {
        assertEquals(calc.minus("100", "3 7 10", " "), 80);
    }

   @Test
    public void trueTest() {
        assertTrue(calc.sum("3 5", " ").equals(calc.sum("2 2 2 2", " ")));
    }
}
