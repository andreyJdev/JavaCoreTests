package ru.project.geometry;

import static ru.project.geometry.Utils.compareAreas;
import static ru.project.geometry.Utils.comparePerimeters;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        Circle circle = new Circle(10);
        Rectangle rectangle = new Rectangle(3, 6);
        Triangle triangle = new Triangle(4, 4, 4);

        System.out.println("Circle area: " + circle.getArea());
        System.out.println("Circle perimeter: " + circle.getPerimeter());

        System.out.println("Rectangle area: " + rectangle.getArea());
        System.out.println("Rectangle perimeter: " + rectangle.getPerimeter());

        System.out.println("Triangle area: " + triangle.getArea());
        System.out.println("Triangle perimeter: " + triangle.getPerimeter());

        boolean isCircleAreaLarger = compareAreas(circle, rectangle);
        System.out.println("Is circle area larger than rectangle area? " + isCircleAreaLarger);

        boolean isTrianglePerimeterLarger = comparePerimeters(triangle, rectangle);
        System.out.println("Is triangle perimeter larger than rectangle perimeter? " + isTrianglePerimeterLarger);

        Cube cube = new Cube(3);
        Cylinder cylinder = new Cylinder(4, 7);

        System.out.println("Cube side: " + cube.getSide());
        System.out.println("Cube volume: " + cube.getVolume());
        System.out.println("Cube surface area: " + cube.getSurfaceArea());

        System.out.println("Cylinder radius: " + cylinder.getRadius());
        System.out.println("Cylinder height: " + cylinder.getHeight());
        System.out.println("Cylinder volume: " + cylinder.getVolume());
        System.out.println("Cylinder surface area: " + cylinder.getSurfaceArea());
    }
}
