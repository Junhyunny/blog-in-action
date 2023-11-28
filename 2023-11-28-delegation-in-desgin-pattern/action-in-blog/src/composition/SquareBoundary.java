package composition;

import java.math.BigDecimal;

public class SquareBoundary implements Boundary {

    private final int side;

    public SquareBoundary(int side) {
        this.side = side;
    }

    @Override
    public String getName() {
        return "Square";
    }

    @Override
    public BigDecimal getArea() {
        return BigDecimal.valueOf((long) side * side);
    }
}
