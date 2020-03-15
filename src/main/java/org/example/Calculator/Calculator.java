package org.example.Calculator;

import com.google.common.base.Splitter;

public class Calculator {

    public Integer sum(String numbers, String splitter) {
        if (numbers.isEmpty()) {
            return 0;
        }
        Iterable<String> iterable = Splitter.on(splitter).split(numbers);
        return getSumResult(iterable);
    }

    public Integer minus(String startNumber,String numbers, String splitter) {
        if (numbers.isEmpty()) {
            return 0;
        }
        Iterable<String> iterable = Splitter.on(splitter).split(numbers);
        return getMinusResult(iterable,startNumber);
    }

    private int getSumResult(Iterable<String> iterable) {
        try {
            int result = 0;
            for (String number : iterable) {
                int a = Integer.parseInt(number);
                if (a > 0) {
                    result += a;
                }
            }
            return result;
        } catch (NumberFormatException e) {
            throw new RuntimeException();
        }
    }

    private int getMinusResult(Iterable<String> iterable,String startNumber) {
        try {
            int result = Integer.parseInt(startNumber);
            for (String number : iterable) {
                int a = Integer.parseInt(number);
                result -= a;
            }
            return result;
        } catch (NumberFormatException e) {
            throw new RuntimeException();
        }
    }
}
