package inheritance;

public class RectangleWindow extends Window {

    public RectangleWindow(int width, int height) {
        super(width, height);
    }

    @Override
    public void printArea() {
        System.out.printf("Rectangle window area is %s\n", getArea());
    }
}
