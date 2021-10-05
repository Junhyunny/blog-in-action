package blog.in.action;

public class MySet {

    private int[] array = new int[100];
    private int position = 0;

    public int size() {
        return position;
    }

    public void add(int number) {
        if (contains(number)) {
            return;
        }
        array[position] = number;
        position++;
    }

    public void remove(int number) {
        for (int index = 0; index < position; index++) {
            if (array[index] == number) {
                position--;
                int itemOfLastIndex = array[position];
                array[index] = itemOfLastIndex;
            }
        }
    }

    public int get(int index) {
        return array[index];
    }

    public boolean contains(int number) {
        for (int index = 0; index < position; index++) {
            if (array[index] == number) {
                return true;
            }
        }
        return false;
    }
}
