import java.awt.*;

public class BigRectangleFilter implements InterfaceFilter
{
    @Override
    public boolean accept(Object x) {
        Rectangle rect = (Rectangle) x;
        boolean isBigRectangle = false;

        if ((2 * (rect.height + rect.width)) > 10)
        {
            isBigRectangle = true;
        }

        return isBigRectangle;
    }
}