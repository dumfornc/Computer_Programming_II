import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

public class TicTacToeFrame extends JFrame
{
    private final TicTacToeTile[][] board = new TicTacToeTile[TicTacToeGame.ROW][TicTacToeGame.COL];

    String player = "X";
    TicTacToeGame game;

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

        this.game = new TicTacToeGame();

        startGame();
    }

    public void initializeUI()
    {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 3));

        for(int row = 0; row < TicTacToeGame.ROW; row++)
        {
            for(int col = 0; col < TicTacToeGame.COL; col++)
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
            int buttonRow = source.getRow();
            int buttonCol = source.getCol();

            this.board[buttonRow][buttonCol].setText(this.player);
            this.board[buttonRow][buttonCol].setEnabled(false);

            int gamePlayer;
            if(player.equals("X")) {gamePlayer = TicTacToeGame.PLAYER_X;}
            else {gamePlayer = TicTacToeGame.PLAYER_O;} //if(player.equals("O")

            int moveResult = this.game.makeMove(gamePlayer, buttonCol, buttonRow);
            if(moveResult == TicTacToeGame.X_WIN || moveResult == TicTacToeGame.O_WIN)
            {
                onWin();
            }
            else if(moveResult == TicTacToeGame.TIE)
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
        this.game.startNewGame();

        for(int row = 0; row < TicTacToeGame.ROW; row++)
        {
            for(int col = 0; col < TicTacToeGame.COL; col++)
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
}
