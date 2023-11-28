package inheritance;

public class SquareWindow extends Window {

    public SquareWindow(int side) {
        super(side, side);
    }

    @Override
    public void printArea() {
        System.out.printf("Square window area is %s\n", getArea());
    }
}
