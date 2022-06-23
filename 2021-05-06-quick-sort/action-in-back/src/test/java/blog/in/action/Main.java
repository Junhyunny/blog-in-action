package blog.in.action;

import java.util.Arrays;

public class Main {

    static void swap(int[] array, int from, int to) {
        int temp = array[from];
        array[from] = array[to];
        array[to] = temp;
    }

    static int partition(int[] array, int left, int right) {
        int pivotIndex = left;
        int lowIndex = left;
        int highIndex = right + 1;
        do {
            // pivotIndex 위치의 값보다 작으면 skip
            do {
                lowIndex++;
            } while (lowIndex <= right && array[lowIndex] < array[pivotIndex]);
            // pivotIndex 위치의 값보다 크면 skip
            do {
                highIndex--;
            } while (left <= highIndex && array[highIndex] > array[pivotIndex]);
            // lowIndex와 highIndex 값이 교차되지 않았다면 값 변경
            if (lowIndex < highIndex) {
                swap(array, lowIndex, highIndex);
            }
        } while (lowIndex < highIndex);

        // pivotIndex 위치의 값과 highIndex 위치의 값을 변경
        swap(array, pivotIndex, highIndex);

        return highIndex;
    }

    static void quickSort(int[] array, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(array, left, right);
            quickSort(array, left, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, right);
        }
    }

    public static void main(String args[]) {
        int[] array = new int[]{6, 2, 5, 7, 9, 1, 3, 4, 10, 11, 4, 0, -1, 20, 5, 7, 5, 1, 6,};
        quickSort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }
}
