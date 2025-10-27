import javax.swing.*;
import java.awt.event.WindowEvent;

public class TicTacToeGame
{
    // Constants representing players for inputting who performed a move and use in the board
    public static final int PLAYER_O = 0;
    public static final int PLAYER_X = 1;
    public static final int PLAYER_EMPTY = 2;

    // Constants for representing the result of makeMove method and allowing it to be interpreted by other classes
    public static final int O_WIN = 0;
    public static final int X_WIN = 1;
    public static final int TIE = 2;
    public static final int PLAY_ON = 3;

    // Constants tracking how long it takes to win
    private static final int MOVES_FOR_WIN = 5;
    private static final int MOVES_FOR_TIE = 7;

    // Constants tracking number of rows and columns
    public static final int ROW = 3;
    public static final int COL = 3;

    private final Integer[][] board = new Integer[ROW][COL];
    public int moveCnt;

    public TicTacToeGame()
    {
        this.startNewGame();
    }

    public void startNewGame()
    {
        moveCnt = 0;

        for(int row = 0; row < ROW; row++)
        {
            for(int col = 0; col < COL; col++)
            {
                this.board[row][col] = PLAYER_EMPTY;
            }
        }
    }

    public int makeMove(int player, int col, int row)
    {
        moveCnt++;

        this.board[row][col] = player;

        if(isWin(player))
        {
            if(player == PLAYER_X)
            {
                return X_WIN;
            }
            else //if(player == PLAYER_O)
            {
                return O_WIN;
            }
        }
        else if (isTie())
        {
            return TIE;
        }
        else
        {
            return PLAY_ON;
        }
    }

    private boolean isWin(int player)
    {
        boolean isWin = false;

        if(moveCnt >= MOVES_FOR_WIN)
        {
            if(isColWin(player) || isRowWin(player) || isDiagonalWin(player))
            {
                isWin = true;
            }
        }

        return isWin;
    }

    private boolean isColWin(int player)
    {
        // checks for a col win for specified player
        for(int col=0; col < COL; col++)
        {
            if(board[0][col] == player &&
               board[1][col] == player &&
               board[2][col] == player )
            {
                return true;
            }
        }
        return false; // no col win
    }

    private boolean isRowWin(int player)
    {
        // checks for a row win for the specified player
        for(int row=0; row < ROW; row++)
        {
            if(board[row][0] == player &&
               board[row][1] == player &&
               board[row][2] == player )
            {
                return true;
            }
        }
        return false; // no row win
    }

    private boolean isDiagonalWin(int player)
    {
        // checks for a diagonal win for the specified player

        if(board[0][0] == player &&
           board[1][1] == player &&
           board[2][2] == player )
        {
            return true;
        }
        if(board[0][2] == player &&
           board[1][1] == player &&
           board[2][0] == player )
        {
            return true;
        }
        return false;
    }

    // checks for a tie before board is filled.
    // check for the win first to be efficient
    private boolean isTie()
    {
        if (moveCnt >= MOVES_FOR_TIE)
        {
            boolean xFlag = false;
            boolean oFlag = false;
            // Check all 8 win vectors for an X and O so
            // no win is possible
            // Check for row ties
            for (int row = 0; row < ROW; row++)
            {
                if (board[row][0] == PLAYER_X ||
                    board[row][1] == PLAYER_X ||
                    board[row][2] == PLAYER_X )
                {
                    xFlag = true; // there is an X in this row
                }
                if (board[row][0] == PLAYER_O ||
                    board[row][1] == PLAYER_O ||
                    board[row][2] == PLAYER_O )
                {
                    oFlag = true; // there is an O in this row
                }

                if (!(xFlag && oFlag))
                {
                    return false; // No tie can still have a row win
                }

                xFlag = oFlag = false;

            }
            // Now scan the columns
            for (int col = 0; col < COL; col++)
            {
                if (board[0][col] == PLAYER_X ||
                    board[1][col] == PLAYER_X ||
                    board[2][col] == PLAYER_X )
                {
                    xFlag = true; // there is an X in this col
                }
                if (board[0][col] == PLAYER_O ||
                    board[1][col] == PLAYER_O ||
                    board[2][col] == PLAYER_O )
                {
                    oFlag = true; // there is an O in this col
                }

                if (!(xFlag && oFlag))
                {
                    return false; // No tie can still have a col win
                }
            }
            // Now check for the diagonals
            xFlag = oFlag = false;

            if (board[0][0] == PLAYER_X ||
                board[1][1] == PLAYER_X ||
                board[2][2] == PLAYER_X )
            {
                xFlag = true;
            }
            if (board[0][0] == PLAYER_O ||
                board[1][1] == PLAYER_O ||
                board[2][2] == PLAYER_O )
            {
                oFlag = true;
            }
            if (!(xFlag && oFlag))
            {
                return false; // No tie can still have a diagonal win
            }
            xFlag = oFlag = false;

            if (board[0][2] == PLAYER_X ||
                board[1][1] == PLAYER_X ||
                board[2][0] == PLAYER_X )
            {
                xFlag = true;
            }
            if (board[0][2] == PLAYER_O ||
                board[1][1] == PLAYER_O ||
                board[2][0] == PLAYER_O )
            {
                oFlag = true;
            }
            if (!(xFlag && oFlag))
            {
                return false; // No tie can still have a diagonal win
            }

            // Checked every vector so I know I have a tie
            return true;
        } else {
            return false;
        }
    }
}
