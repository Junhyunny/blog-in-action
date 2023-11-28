package inheritance;

import java.math.BigDecimal;

public class CircleWindow extends Window {

    private final int radius;

    public CircleWindow(int radius) {
        super(radius, radius);
        this.radius = radius;
    }

    @Override
    public BigDecimal getArea() {
        var pi = new BigDecimal(String.valueOf(Math.PI));
        return pi.multiply(
                BigDecimal.valueOf((long) radius * radius)
        );
    }

    @Override
    public void printArea() {
        System.out.printf("Circle window area is %s\n", getArea());
    }
}
