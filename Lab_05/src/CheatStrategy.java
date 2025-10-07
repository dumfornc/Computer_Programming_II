public class CheatStrategy implements Strategy
{
    @Override
    public int getMove(int playerMove)
    {
        if(playerMove == RockPaperScissorsFrame.ROCK)
        {
            return RockPaperScissorsFrame.PAPER;
        }
        else if(playerMove == RockPaperScissorsFrame.PAPER)
        {
            return RockPaperScissorsFrame.SCISSORS;
        }
        else
        {
            return RockPaperScissorsFrame.ROCK;
        }
    }
}
