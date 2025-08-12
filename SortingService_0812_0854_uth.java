// 代码生成时间: 2025-08-12 08:54:18
package com.example.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;
import java.util.Collections;

/**
 * Service class to handle sorting operations.
 * This class demonstrates a simple sorting algorithm implementation.
 */
public class SortingService {

    private SqlSessionFactory sqlSessionFactory;

    public SortingService(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     * Sorts a list of integers using an insertion sort algorithm.
     * 
     * @param numbers List of integers to be sorted.
     * @return A sorted list of integers.
     */
    public List<Integer> insertionSort(List<Integer> numbers) {
        if (numbers == null) {
            throw new IllegalArgumentException("Input list cannot be null.");
        }
        // Perform insertion sort
        for (int i = 1; i < numbers.size(); i++) {
            int key = numbers.get(i);
            int j = i - 1;
            while (j >= 0 && numbers.get(j) > key) {
                numbers.set(j + 1, numbers.get(j));
                j = j - 1;
            }
            numbers.set(j + 1, key);
        }
        return numbers;
    }

    /**
     * Main method for testing the sorting service.
     * 
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        // Mock SqlSessionFactory for demonstration purposes
        SqlSessionFactory sqlSessionFactory = null; // Initialize with actual SqlSessionFactory instance

        SortingService sortingService = new SortingService(sqlSessionFactory);

        List<Integer> numbers = List.of(3, 6, 2, 8, 4, 1);
        try {
            List<Integer> sortedNumbers = sortingService.insertionSort(numbers);
            System.out.println("Sorted numbers: " + sortedNumbers);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
