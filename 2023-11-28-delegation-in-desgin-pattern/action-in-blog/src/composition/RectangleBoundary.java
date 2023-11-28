package composition;

import java.math.BigDecimal;

public class RectangleBoundary implements Boundary {

    private final int width;
    private final int height;

    public RectangleBoundary(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public String getName() {
        return "Rectangle";
    }

    @Override
    public BigDecimal getArea() {
        return BigDecimal.valueOf((long) width * height);
    }
}
