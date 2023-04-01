package blog.in.action;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Slf4j
public class QuickSortTests {

    void swap(int[] array, int from, int to) {
        int temp = array[from];
        array[from] = array[to];
        array[to] = temp;
    }

    int divideAndConquer(int[] array, int left, int right) {
        int pivot = left;
        int leftIndex = left;
        int rightIndex = right + 1;
        do {
            // pivot 위치의 값보다 작으면 skip
            do {
                leftIndex++;
            } while (leftIndex <= right && array[leftIndex] < array[pivot]);

            // pivot 위치의 값보다 크면 skip
            do {
                rightIndex--;
            } while (left <= rightIndex && array[rightIndex] > array[pivot]);

            // leftIndex, rightIndex 값이 교차되지 않았다면 값 변경
            if (leftIndex < rightIndex) {
                swap(array, leftIndex, rightIndex);
            }
        } while (leftIndex < rightIndex);

        // pivot 위치의 값과 rightIndex 위치의 값을 변경
        swap(array, pivot, rightIndex);

        return rightIndex;
    }

    void sort(int[] array, int left, int right) {
        if (left < right) {
            int pivotIndex = divideAndConquer(array, left, right);
            sort(array, left, pivotIndex - 1);
            sort(array, pivotIndex + 1, right);
        }
    }

    @Test
    void quick_sort_ascending_integer_array() {
        int[] array = new int[]{6, 2, 5, 9, 1, 3, 10, 11, 4, 0, -1, 20, 7, 5, 1};


        sort(array, 0, array.length - 1);


        assertThat(array[0], equalTo(-1));
        assertThat(array[1], equalTo(0));
        assertThat(array[2], equalTo(1));
        assertThat(array[3], equalTo(1));
        assertThat(array[4], equalTo(2));
        assertThat(array[5], equalTo(3));
        assertThat(array[6], equalTo(4));
        assertThat(array[7], equalTo(5));
        assertThat(array[8], equalTo(5));
        assertThat(array[9], equalTo(6));
        assertThat(array[10], equalTo(7));
        assertThat(array[11], equalTo(9));
        assertThat(array[12], equalTo(10));
        assertThat(array[13], equalTo(11));
        assertThat(array[14], equalTo(20));
        log.info(Arrays.toString(array));
    }
}
