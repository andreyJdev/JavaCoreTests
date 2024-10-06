package ru.project.geometry;

/**
 * Hello world!
 *
 */
public class Utils
{
    public static boolean compareAreas(Circle circle, Rectangle rectangle) {
        return circle.getArea() > rectangle.getArea();
    }

    public static boolean comparePerimeters(Triangle triangle, Rectangle rectangle) {
        return triangle.getPerimeter() > rectangle.getPerimeter();
    }
}
