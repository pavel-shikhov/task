package com.tsystems.javaschool.tasks.pyramid;

import java.util.*;

public class PyramidBuilder {
    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        if (isPyramidBuildable(inputNumbers.size())) {
            int[] dimensions = getDimensions(inputNumbers.size());
            int rows = dimensions[0];
            int columns = dimensions[1];
            try {
                int[][] pyramid = new int[rows][columns];
                int period = columns / 2;
                Collections.sort(inputNumbers);
                Iterator<Integer> iterator = inputNumbers.iterator();
                for (int i = 0; i < rows; i++) {
                    int index = period;
                    for (int j = 0; j < i+1; j++) {
                        pyramid[i][index] = iterator.next();
                        index += 2;
                    }
                    period--;
                }
                return pyramid;
            } catch (OutOfMemoryError | NullPointerException | NoSuchElementException e) {
                throw new CannotBuildPyramidException();
            }
        } else {
            throw new CannotBuildPyramidException();
        }
    }

    private int getNumberDigitSum(int number) {
        int sum = 0;
        int positiveNumber = Math.abs(number);
        while (positiveNumber > 0) {
            sum += positiveNumber % 10;
            positiveNumber /= 10;
        }
        return sum;
    }

    private boolean isPyramidBuildable(int inputListSize){
        return getNumberDigitSum(inputListSize) % 3 == 0 || getNumberDigitSum(inputListSize) == 10 || inputListSize == 10;
    }

    private static int[] getDimensions(int inputListSize){
        int rows = 2;
        int columns = 3;
        double currentListSize = 3;

        while (currentListSize < inputListSize){
            rows++;
            columns += 2;
            currentListSize += rows;
        }
        return new int[]{rows, columns};
    }
}
