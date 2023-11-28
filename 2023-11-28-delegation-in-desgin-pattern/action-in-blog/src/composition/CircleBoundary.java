package composition;

import java.math.BigDecimal;

public class CircleBoundary implements Boundary {
    private final int radius;

    public CircleBoundary(int radius) {
        this.radius = radius;
    }

    @Override
    public void printArea() {
        System.out.printf("Circle window area is %s\n", getArea());
    }

    private BigDecimal getArea() {
        var pi = new BigDecimal(String.valueOf(Math.PI));
        return pi.multiply(
                BigDecimal.valueOf((long) radius * radius)
        );
    }
}
