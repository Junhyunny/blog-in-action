package blog.in.action;

import blog.in.action.algoritm.MergeSort;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MergeSortTests {

    @Test
    void ascending_merge_sort() {

        int[] array = new int[]{6, 2, 5, 7, 9, 1, 3, 4, 10, 11, 4};


        MergeSort sut = new MergeSort();
        sut.sort(array, 0, array.length - 1);


        assertThat(array[0], equalTo(1));
        assertThat(array[1], equalTo(2));
        assertThat(array[2], equalTo(3));
        assertThat(array[3], equalTo(4));
        assertThat(array[4], equalTo(4));
        assertThat(array[5], equalTo(5));
        assertThat(array[6], equalTo(6));
        assertThat(array[7], equalTo(7));
        assertThat(array[8], equalTo(9));
        assertThat(array[9], equalTo(10));
        assertThat(array[10], equalTo(11));
    }
}
