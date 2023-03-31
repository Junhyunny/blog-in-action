package blog.in.action;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

@Slf4j
public class CloneMaterialExample {

    @Test
    void clone_material() throws CloneNotSupportedException {

        Shape originShape = new Shape();
        originShape.addLine(new Line(new Point(0, 0), new Point(0, 1)));
        originShape.addLine(new Line(new Point(0, 1), new Point(1, 1)));
        originShape.addLine(new Line(new Point(1, 1), new Point(1, 0)));
        originShape.addLine(new Line(new Point(1, 0), new Point(0, 0)));


        Shape clonedShape = originShape.clone();


        Line line = clonedShape.getLineAtIndex(0);
        Point secondPoint = line.getSecondPoint();
        secondPoint.setX(-1);
        secondPoint.setY(-1);
        assertThat(originShape.getLineAtIndex(0).getSecondPoint().getX(), not(-1));
        assertThat(originShape.getLineAtIndex(0).getSecondPoint().getY(), not(-1));
        log.info("origin shape -\n{}", originShape);
        log.info("cloned shape -\n{}", clonedShape);
    }
}

@Getter
@Setter
class Point implements Cloneable {

    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    protected Point clone() throws CloneNotSupportedException {
        return new Point(x, y);
    }

    @Override
    public String toString() {
        return "[x: " + x + ", y: " + y + "]";
    }
}

class Line implements Cloneable {

    private final Point point1;
    private final Point point2;

    public Line(Point point1, Point point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    public Point getFirstPoint() {
        return point1;
    }

    public Point getSecondPoint() {
        return point2;
    }

    @Override
    protected Line clone() throws CloneNotSupportedException {
        return new Line(point1.clone(), point2.clone());
    }

    @Override
    public String toString() {
        return "[point1: " + point1 + ", point2: " + point2 + "]";
    }
}

class Shape implements Cloneable {

    private final List<Line> lines;

    public Shape() {
        this.lines = new ArrayList<>();
    }

    public Shape(List<Line> lines) {
        this.lines = lines;
    }

    public void addLine(Line line) {
        this.lines.add(line);
    }

    public Line getLineAtIndex(int index) {
        return this.lines.get(index);
    }

    @Override
    protected Shape clone() throws CloneNotSupportedException {
        List<Line> lineList = new ArrayList<>();
        for (Line line : this.lines) {
            lineList.add(line.clone());
        }
        return new Shape(lineList);
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Line line : lines) {
            builder.append("line: ").append(line).append("\n");
        }
        return builder.toString();
    }
}