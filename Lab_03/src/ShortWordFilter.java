public class ShortWordFilter implements InterfaceFilter
{
    @Override
    public boolean accept(Object x) {
        boolean acceptString = false;
        int length = x.toString().length();

        if (length < 5 && length > 0)
        {
            acceptString = true;
        }

        return acceptString;
    }
}
