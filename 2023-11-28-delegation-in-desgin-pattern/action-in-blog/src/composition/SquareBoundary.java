package composition;

import java.math.BigDecimal;

public class SquareBoundary implements Boundary {
    private final int side;

    public SquareBoundary(int side) {
        this.side = side;
    }

    @Override
    public void printArea() {
        System.out.printf("Square window area is %s\n", getArea());
    }

    private BigDecimal getArea() {
        return BigDecimal.valueOf((long) side * side);
    }
}
