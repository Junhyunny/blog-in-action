package blog.in.action.algorithm;

import java.util.Arrays;

public class Main {

    static int partition(int[] array, int left, int right) {
        return 0;
    }

    static void quickSort(int[] array, int left, int right) {
        if (left < right) {
            int pivot = partition(array, left, right);
            partition(array, left, pivot);
            partition(array, pivot + 1, right);
        }
    }

    public static void main(String args[]) {
        int[] array = new int[]{6, 2, 5, 7, 9, 1, 3, 4, 10, 11, 4};
        partition(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }
}
