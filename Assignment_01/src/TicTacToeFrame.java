import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

public class TicTacToeFrame extends JFrame
{
    private static final int ROW = 3;
    private static final int COL = 3;
    private TicTacToeTile[][] board = new TicTacToeTile[ROW][COL];

    String player = "X";
    int moveCnt = 0;
    final int MOVES_FOR_WIN = 5;
    final int MOVES_FOR_TIE = 7;

    public TicTacToeFrame()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // get screen dimensions
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        // center frame in screen
        setSize(screenWidth / 2, screenHeight / 2);
        setLocation(screenWidth / 4, screenHeight / 4);

        this.initializeUI();

        startGame();
    }

    public void initializeUI()
    {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 3));

        for(int row = 0; row < ROW; row++)
        {
            for(int col = 0; col < COL; col++)
            {
                TicTacToeTile newButton = new TicTacToeTile(row, col);
                newButton.addActionListener(this::buttonPressListener);

                Font buttonFont = new Font("Times New Roman", Font.PLAIN, 80);
                newButton.setFont(buttonFont);

                this.board[row][col] = newButton;
                mainPanel.add(newButton);
            }
        }

        add(mainPanel);
    }

    private void buttonPressListener(ActionEvent e)
    {
        if(e.getSource() instanceof TicTacToeTile source)
        {
            moveCnt++;

            int buttonRow = source.getRow();
            int buttonCol = source.getCol();

            this.board[buttonRow][buttonCol].setText(this.player);
            this.board[buttonRow][buttonCol].setEnabled(false);

            if(isWin())
            {
                onWin();
            }
            else if (isTie())
            {
                onTie();
            }
            else
            {
                switchToOtherPlayer();
            }
        }
    }

    private void startGame()
    {
        switchPlayer("X");
        moveCnt = 0;

        for(int row = 0; row < ROW; row++)
        {
            for(int col = 0; col < COL; col++)
            {
                this.board[row][col].setText("");
                this.board[row][col].setEnabled(true);
            }
        }

        JOptionPane.showMessageDialog(this, "Let's play Tic-Tac-Toe! Player " + player + " starts!");
    }

    private void switchPlayer(String newPlayer)
    {
        player = newPlayer;
        setTitle("Tic-Tac-Toe (" + player + "'s Turn)");
    }

    private void switchToOtherPlayer()
    {
        if(player.equals("X"))
        {
            switchPlayer("O");
        } else
        {
            switchPlayer("X");
        }
    }

    private void onWin()
    {
        int keepPlaying = JOptionPane.showConfirmDialog(
                this,
                player + " won! Do you want to play again?",
                "Play again",
                JOptionPane.YES_NO_OPTION);

        if(keepPlaying == JOptionPane.YES_OPTION)
        {
            this.startGame();
        } else
        {
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    private void onTie()
    {
        int keepPlaying = JOptionPane.showConfirmDialog(
                this,
                " There was a tie. Do you want to play again?",
                "Play again",
                JOptionPane.YES_NO_OPTION);

        if(keepPlaying == JOptionPane.YES_OPTION)
        {
            this.startGame();
        } else
        {
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    private boolean isWin()
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

    private boolean isColWin(String player)
    {
        // checks for a col win for specified player
        for(int col=0; col < COL; col++)
        {
            if(board[0][col].getText().equals(player) &&
                    board[1][col].getText().equals(player) &&
                    board[2][col].getText().equals(player))
            {
                return true;
            }
        }
        return false; // no col win
    }

    private boolean isRowWin(String player)
    {
        // checks for a row win for the specified player
        for(int row=0; row < ROW; row++)
        {
            if(board[row][0].getText().equals(player) &&
                    board[row][1].getText().equals(player) &&
                    board[row][2].getText().equals(player))
            {
                return true;
            }
        }
        return false; // no row win
    }

    private boolean isDiagonalWin(String player)
    {
        // checks for a diagonal win for the specified player

        if(board[0][0].getText().equals(player) &&
                board[1][1].getText().equals(player) &&
                board[2][2].getText().equals(player) )
        {
            return true;
        }
        if(board[0][2].getText().equals(player) &&
                board[1][1].getText().equals(player) &&
                board[2][0].getText().equals(player) )
        {
            return true;
        }
        return false;
    }

    // checks for a tie before board is filled.
    // check for the win first to be efficient
    private boolean isTie()
    {
        if(moveCnt >= MOVES_FOR_TIE)
        {
            boolean xFlag = false;
            boolean oFlag = false;
            // Check all 8 win vectors for an X and O so
            // no win is possible
            // Check for row ties
            for (int row = 0; row < ROW; row++) {
                if (board[row][0].getText().equals("X") ||
                        board[row][1].getText().equals("X") ||
                        board[row][2].getText().equals("X")) {
                    xFlag = true; // there is an X in this row
                }
                if (board[row][0].getText().equals("O") ||
                        board[row][1].getText().equals("O") ||
                        board[row][2].getText().equals("O")) {
                    oFlag = true; // there is an O in this row
                }

                if (!(xFlag && oFlag)) {
                    return false; // No tie can still have a row win
                }

                xFlag = oFlag = false;

            }
            // Now scan the columns
            for (int col = 0; col < COL; col++) {
                if (board[0][col].getText().equals("X") ||
                        board[1][col].getText().equals("X") ||
                        board[2][col].getText().equals("X")) {
                    xFlag = true; // there is an X in this col
                }
                if (board[0][col].getText().equals("O") ||
                        board[1][col].getText().equals("O") ||
                        board[2][col].getText().equals("O")) {
                    oFlag = true; // there is an O in this col
                }

                if (!(xFlag && oFlag)) {
                    return false; // No tie can still have a col win
                }
            }
            // Now check for the diagonals
            xFlag = oFlag = false;

            if (board[0][0].getText().equals("X") ||
                    board[1][1].getText().equals("X") ||
                    board[2][2].getText().equals("X")) {
                xFlag = true;
            }
            if (board[0][0].getText().equals("O") ||
                    board[1][1].getText().equals("O") ||
                    board[2][2].getText().equals("O")) {
                oFlag = true;
            }
            if (!(xFlag && oFlag)) {
                return false; // No tie can still have a diagonal win
            }
            xFlag = oFlag = false;

            if (board[0][2].getText().equals("X") ||
                    board[1][1].getText().equals("X") ||
                    board[2][0].getText().equals("X")) {
                xFlag = true;
            }
            if (board[0][2].getText().equals("O") ||
                    board[1][1].getText().equals("O") ||
                    board[2][0].getText().equals("O")) {
                oFlag = true;
            }
            if (!(xFlag && oFlag)) {
                return false; // No tie can still have a diagonal win
            }

            // Checked every vector so I know I have a tie
            return true;
        }
        else
        {
            return false;
        }
    }
}
