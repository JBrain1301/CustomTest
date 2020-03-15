package org.example.CustomTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Asserts {
    public static void assertTrue(boolean isEqual) {
        if (!isEqual) {
            System.out.println("Тест не пройден");
            throw new CustomTestException("Not True");
        } else {
            System.out.println("Тест пройден");
        }
    }

    public static void assertNotNull(Object o) {
        if (o == null) {
            System.out.println("Тест не пройден");
            throw new CustomTestException("Expected not null");
        } else {
            System.out.println("Тест пройден");
        }
    }

    public static void assertEquals(Object o1, Object o2) {
        if (o1.equals(o2)) {
            System.out.println("Тест пройден");
        }else {
            System.out.println("Тест не пройден");
            throw new CustomTestException("Not equals objects: Value 1 = " + o1 +", Value 2 = " + o2);
        }
    }
}
