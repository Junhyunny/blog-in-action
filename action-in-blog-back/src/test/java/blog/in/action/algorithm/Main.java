package blog.in.action.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static class Point {

        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Vector {

        final int dx;
        final int dy;

        public Vector(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        public Vector(Point p1, Point p2) {
            this.dx = p2.x - p1.x;
            this.dy = p2.y - p1.y;
        }

        public double getScalar() {
            return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        }

        public Vector reverse() {
            return new Vector(-1 * dx, -1 * dy);
        }

        public Vector addVector(Vector v) {
            return new Vector(this.dx + v.dx, this.dy + v.dy);
        }

        @Override
        public String toString() {
            return "dx: " + dx + ", dy: " + dy;
        }
    }

    public static List<Vector> getAllVectorList(List<Point> pointList) {
        List<Vector> result = new ArrayList<>();
        if (pointList.size() == 2) {
            result.add(new Vector(0, 0));
        }
        int size = pointList.size();
        for (int index = 0; index < size; index++) {
            for (int subIndex = index + 1; subIndex < size; subIndex++) {
                Vector vector = new Vector(pointList.get(index), pointList.get(subIndex));
                result.add(vector);
            }
        }
        return result;
    }

    public static double getMinSumOfVector(List<Vector> vectorList) {
        double result = Double.MAX_VALUE;
        int size = vectorList.size();
        for (int index = 0; index < size; index++) {
            for (int subIndex = index + 1; subIndex < size; subIndex++) {
                Vector vector1 = vectorList.get(index);
                Vector vector2 = vectorList.get(subIndex);
                double scalar1 = vector1.addVector(vector2).getScalar();
                double scalar2 = vector1.reverse().addVector(vector2).getScalar();
                double scalar3 = vector1.addVector(vector2.reverse()).getScalar();
                double scalar4 = vector1.reverse().addVector(vector2.reverse()).getScalar();
                double temp = Math.min(scalar1, scalar2);
                temp = Math.min(temp, scalar3);
                temp = Math.min(temp, scalar4);
                if (result > temp) {
                    result = temp;
                }
            }
        }
        return result;
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int times = sc.nextInt();
        for (int t = 0; t < times; t++) {
            int N = sc.nextInt();
            List<Point> pointList = new ArrayList<>();
            for (int n = 0; n < N; n++) {
                int x = sc.nextInt();
                int y = sc.nextInt();
                pointList.add(new Point(x, y));
            }
            List<Vector> vectorList = getAllVectorList(pointList);
            double answer = getMinSumOfVector(vectorList);
            System.out.println(answer);
        }
        sc.close();
    }
}
