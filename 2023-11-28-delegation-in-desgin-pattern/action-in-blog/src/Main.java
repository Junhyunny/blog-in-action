import composition.Window;

public class Main {

//    public static void main(String[] args) {
//
//        Window rectangleWindow = new RectangleWindow(10, 5);
//        Window squareWindow = new SquareWindow(5);
//        Window circleWindow = new CircleWindow(10);
//
//        rectangleWindow.printArea();
//        squareWindow.printArea();
//        circleWindow.printArea();
//    }

    public static void main(String[] args) {

        Window rectangleWindow = Window.createRectangle(5, 10);
        Window squareWindow = Window.createSquare(5);
        Window circleWindow = Window.createCircle(10);

        rectangleWindow.printArea();
        squareWindow.printArea();
        circleWindow.printArea();
    }
}