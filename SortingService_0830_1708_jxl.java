// 代码生成时间: 2025-08-30 17:08:15
package com.example.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import java.util.Collections;
import java.util.List;

/**
 * A service class to perform sorting operations using different algorithms.
 */
public class SortingService {

    private final SqlSessionFactory sqlSessionFactory;

    public SortingService(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     * Performs bubble sort on a given list of integers.
     *
      * @param numbers List of integers to be sorted.
      * @return Sorted list of integers.
      */
    public List<Integer> bubbleSort(List<Integer> numbers) {
        if (numbers == null) {
            throw new IllegalArgumentException("The input list cannot be null.");
        }

        boolean swapped;
        for (int i = 0; i < numbers.size() - 1; i++) {
            swapped = false;
            for (int j = 0; j < numbers.size() - i - 1; j++) {
                if (numbers.get(j) > numbers.get(j + 1)) {
                    // Swap elements
                    int temp = numbers.get(j);
                    numbers.set(j, numbers.get(j + 1));
                    numbers.set(j + 1, temp);
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
        return numbers;
    }

    /**
     * Performs quick sort on a given list of integers.
     *
      * @param numbers List of integers to be sorted.
      * @return Sorted list of integers.
      */
    public List<Integer> quickSort(List<Integer> numbers) {
        if (numbers == null) {
            throw new IllegalArgumentException("The input list cannot be null.");
        }

        return quickSort(numbers, 0, numbers.size() - 1);
    }

    private List<Integer> quickSort(List<Integer> numbers, int low, int high) {
        if (low < high) {
            int pi = partition(numbers, low, high);
            quickSort(numbers, low, pi - 1);
            quickSort(numbers, pi + 1, high);
        }
        return numbers;
    }

    private int partition(List<Integer> numbers, int low, int high) {
        int pivot = numbers.get(high);
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (numbers.get(j) < pivot) {
                i++;
                swap(numbers, i, j);
            }
        }
        swap(numbers, i + 1, high);
        return i + 1;
    }

    private void swap(List<Integer> numbers, int i, int j) {
        int temp = numbers.get(i);
        numbers.set(i, numbers.get(j));
        numbers.set(j, temp);
    }

    // Additional sorting algorithms can be added here following the same pattern.
}
