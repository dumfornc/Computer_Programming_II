import java.util.Random;

public class RandomStrategy implements Strategy
{
    public static Random moveGenerator = new Random();

    @Override
    public int getMove(int playerMove)
    {
        return moveGenerator.nextInt(0,3);
    }
}
