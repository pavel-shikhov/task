package com.tsystems.javaschool.tasks.subsequence;

import java.util.*;

public class Subsequence {
    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public static boolean find(List x, List y) {
        boolean yContainsX = false;
        if (Objects.equals(null, x) || Objects.equals(null, y)){
            throw new IllegalArgumentException();
        }
        if ((x.isEmpty() && !y.isEmpty()) || (x.isEmpty() && y.isEmpty())){
            return true;
        }
        int i = 0;
        for (Object o : y) {
            if (o == x.get(i)) {
                if (i < x.size() - 1) {
                    i++;
                } else if (i == x.size() - 1) {
                    yContainsX = true;
                    break;
                }
            }
        }
        return yContainsX;
    }
}
