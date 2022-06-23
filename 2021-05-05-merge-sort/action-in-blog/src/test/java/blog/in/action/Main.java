package blog.in.action;

import java.util.Arrays;

public class Main {

    static void merge(int[] array, int left, int right) {

        int index = 0;
        int mid = (left + right) / 2;
        int[] temp = new int[right - left + 1];

        int leftIndex = left;
        int rightIndex = mid + 1;
        while (leftIndex <= mid && rightIndex <= right) {
            if (array[leftIndex] < array[rightIndex]) {
                temp[index++] = array[leftIndex++];
            } else {
                temp[index++] = array[rightIndex++];
            }
        }

        while (leftIndex <= mid) {
            temp[index++] = array[leftIndex++];
        }

        while (rightIndex <= right) {
            temp[index++] = array[rightIndex++];
        }

        for (int subIndex = 0; subIndex < temp.length; subIndex++) {
            array[subIndex + left] = temp[subIndex];
        }
    }

    static void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);
            merge(array, left, right);
        }
    }

    public static void main(String args[]) {
        int[] array = new int[]{6, 2, 5, 7, 9, 1, 3, 4, 10, 11, 4};
        mergeSort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }
}
