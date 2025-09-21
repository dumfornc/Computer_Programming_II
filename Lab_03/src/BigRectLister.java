import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class BigRectLister
{
    public static void main(String[] args) {
        BigRectangleFilter rectFilter = new BigRectangleFilter();
        ArrayList<Rectangle> rects = new ArrayList<Rectangle>();

        rects.add(new Rectangle(1, 1));   // Perimeter of 4
        rects.add(new Rectangle(1, 2));   // Perimeter of 6
        rects.add(new Rectangle(2, 2));   // Perimeter of 8
        rects.add(new Rectangle(2, 3));   // Perimeter of 10
        rects.add(new Rectangle(3, 3));   // Perimeter of 12
        rects.add(new Rectangle(3, 4));   // Perimeter of 14
        rects.add(new Rectangle(4, 4));   // Perimeter of 16
        rects.add(new Rectangle(4, 5));   // Perimeter of 18
        rects.add(new Rectangle(5, 5));   // Perimeter of 20
        rects.add(new Rectangle(49, 49)); // Perimeter of 196

        System.out.println("These rectangles have perimeters larger than 10:");
        for (Rectangle rect : rects)
        {
            if (rectFilter.accept(rect))
            {
                System.out.println(rect);
            }
        }
    }
}
