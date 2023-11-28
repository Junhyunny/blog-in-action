package inheritance;

import java.math.BigDecimal;

public abstract class Window {

    private final int width;
    private final int height;

    public Window(int width, int height) {
        this.width = width;
        this.height = height;
    }

    protected BigDecimal getArea() {
        return BigDecimal.valueOf((long) width * height);
    }

    public abstract void printArea();
}