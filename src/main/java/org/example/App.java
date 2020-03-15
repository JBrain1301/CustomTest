package org.example;

import org.example.CustomTest.CustomUnitTest;
import java.lang.reflect.InvocationTargetException;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        CustomUnitTest.runTest("org.example.vlad.tests");
    }
}