package blog.in.action;

public class MySet {

    private int[] array = new int[2];
    private int position = 0;

    public int size() {
        return position;
    }

    public void add(int number) {
        if (contains(number)) {
            return;
        }
        if (position >= array.length) {
            resize();
        }
        array[position] = number;
        position++;
    }

    public void remove(int number) {
        for (int index = 0; index < position; index++) {
            if (array[index] == number) {
                position--;
                array[index] = array[position];
                break;
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

    private void resize() {
        int[] temp = new int[array.length * 2];
        for (int index = 0; index < array.length; index++) {
            temp[index] = array[index];
        }
        array = temp;
    }
}
