package blog.in.action.algoritm;

public class MergeSort {

    public void sort(int[] array, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            sort(array, left, mid);
            sort(array, mid + 1, right);
            conquerAndCombine(array, left, right);
        }
    }

    private void conquerAndCombine(int[] array, int left, int right) {

        int mid = (left + right) / 2;
        int[] tempArray = new int[right - left + 1];

        int tempIndex = 0;
        int leftIndex = left;
        int rightIndex = mid + 1;

        while (leftIndex <= mid && rightIndex <= right) {
            if (array[leftIndex] < array[rightIndex]) {
                tempArray[tempIndex++] = array[leftIndex++];
            } else {
                tempArray[tempIndex++] = array[rightIndex++];
            }
        }
        while (leftIndex <= mid) {
            tempArray[tempIndex++] = array[leftIndex++];
        }
        while (rightIndex <= right) {
            tempArray[tempIndex++] = array[rightIndex++];
        }

        System.arraycopy(tempArray, 0, array, left, tempArray.length);
    }
}
