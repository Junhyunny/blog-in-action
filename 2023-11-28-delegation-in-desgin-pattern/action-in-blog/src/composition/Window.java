package composition;

public class Window {

    private final Boundary windowBoundary;

    private Window(Boundary windowBoundary) {
        this.windowBoundary = windowBoundary;
    }

    public static Window createCircle(int radius) {
        return new Window(new CircleBoundary(radius));
    }

    public static Window createRectangle(int width, int height) {
        return new Window(new RectangleBoundary(width, height));
    }

    public static Window createSquare(int side) {
        return new Window(new SquareBoundary(side));
    }

    public void printArea() {
        System.out.printf("%s window area is %s\n", windowBoundary.getName(), windowBoundary.getArea());
    }
}
