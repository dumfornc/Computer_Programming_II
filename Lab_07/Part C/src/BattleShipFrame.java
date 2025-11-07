import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

public class BattleShipFrame extends JFrame
{
    // Constants for dimensions of battleship board
    public static final int ROWS = 10;
    public static final int COLS = 10;

    public static final Integer[] SHIP_SIZES = {5, 4, 3, 3, 2};

    BattleShipGame gameLogic;

    private JTextField strikesDisplay;
    private JTextField missStreakDisplay;
    private JTextField totalMissesDisplay;
    private JTextField hitsDisplay;

    private JPanel mainPanel;

    public BattleShipFrame()
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

        this.gameLogic = new BattleShipGame(ROWS, COLS, SHIP_SIZES);

        this.initializeUI();

//        startGame();
    }

    private void initializeUI()
    {
        setTitle("BattleShip");

        this.mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel statusPanel = initializeStatusPanel();
        mainPanel.add(statusPanel, BorderLayout.PAGE_START);

        JPanel battleShipBoardPanel = initializeBattleShipBoardPanel();
        mainPanel.add(battleShipBoardPanel, BorderLayout.CENTER);

        JPanel controlsPanel = initializeControlsPanel();
        mainPanel.add(controlsPanel, BorderLayout.PAGE_END);

        add(mainPanel);
    }

    private JPanel initializeStatusPanel()
    {
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayout(2, 4));

        JLabel strikeLabel = new JLabel("Strikes (3 and you loose):");
        statusPanel.add(strikeLabel);

        JLabel missLabel = new JLabel("Miss Streak (5 in a row is a strike):");
        statusPanel.add(missLabel);

        JLabel totalMissLabel = new JLabel("Total Misses:");
        statusPanel.add(totalMissLabel);

        JLabel hitsLabel = new JLabel("Total hits:");
        statusPanel.add(hitsLabel);

        this.strikesDisplay = new JTextField();
        this.strikesDisplay.setEditable(false);
        statusPanel.add(strikesDisplay);

        this.missStreakDisplay = new JTextField();
        this.missStreakDisplay.setEditable(false);
        statusPanel.add(missStreakDisplay);

        this.totalMissesDisplay = new JTextField();
        this.totalMissesDisplay.setEditable(false);
        statusPanel.add(totalMissesDisplay);

        this.hitsDisplay = new JTextField();
        this.hitsDisplay.setEditable(false);
        statusPanel.add(hitsDisplay);

        updateStatusPanel();

        return statusPanel;
    }

    private void updateStatusPanel()
    {
        this.strikesDisplay.setText(Integer.toString(this.gameLogic.getStrikes()));
        this.missStreakDisplay.setText(Integer.toString(this.gameLogic.getCurrentMissStreak()));
        this.totalMissesDisplay.setText(Integer.toString(this.gameLogic.getTotalMisses()));
        this.hitsDisplay.setText(Integer.toString(this.gameLogic.getTotalHits()));
    }

    private JPanel initializeBattleShipBoardPanel()
    {
        JPanel battleShipBoardPanel = new JPanel();
        battleShipBoardPanel.setLayout(new GridLayout(ROWS, COLS));

        for(int i = 0; i < ROWS; i++)
        {
            for(int j = 0; j < COLS; j++)
            {
                JButton newButton = new BattleShipTile(i, j);
                newButton.addActionListener(this::battleShipButtonListener);
                battleShipBoardPanel.add(newButton);
            }
        }

        return battleShipBoardPanel;
    }

    private void battleShipButtonListener(ActionEvent e)
    {
        if(e.getSource() instanceof BattleShipTile source)
        {
            int buttonRow = source.getRow();
            int buttonCol = source.getCol();

            int moveResult = this.gameLogic.makeMove(buttonRow, buttonCol);
            if(moveResult == BattleShipGame.NORMAL_MISS || moveResult == BattleShipGame.STRIKE_MISS)
            {
                source.setTileState(BattleShipTile.TILE_IS_MISS);
                updateStatusPanel();
            }
            else if(moveResult == BattleShipGame.LOOSE_MISS)
            {
                source.setTileState(BattleShipTile.TILE_IS_MISS);
                updateStatusPanel();

                int keepPlaying = JOptionPane.showConfirmDialog(
                        this,
                        "You lost, would you like to play again?",
                        "You Loose",
                        JOptionPane.YES_NO_OPTION);

                if(keepPlaying == JOptionPane.YES_OPTION)
                {
                    restartGame();
                }
                else
                {
                    dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                }
            }
            else if(moveResult == BattleShipGame.NORMAL_HIT)
            {
                source.setTileState(BattleShipTile.TILE_IS_HIT);
                updateStatusPanel();
            }
            else if(moveResult == BattleShipGame.WIN_HIT)
            {
                source.setTileState(BattleShipTile.TILE_IS_HIT);
                updateStatusPanel();

                int keepPlaying = JOptionPane.showConfirmDialog(
                        this,
                        "You won, would you like to play again?",
                        "You Win",
                        JOptionPane.YES_NO_OPTION);

                if(keepPlaying == JOptionPane.YES_OPTION)
                {
                    restartGame();
                }
                else
                {
                    dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                }
            }
        }
    }

    private void restartGame()
    {
        this.gameLogic.startNewGame();
        updateStatusPanel();

        BorderLayout layout = (BorderLayout) this.mainPanel.getLayout();
        Component centerComponent = layout.getLayoutComponent(BorderLayout.CENTER);

        this.mainPanel.remove(centerComponent);

        JPanel newBattleShipBoardPanel = initializeBattleShipBoardPanel();
        this.mainPanel.add(newBattleShipBoardPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private JPanel initializeControlsPanel()
    {
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new GridLayout(1, 2));

        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.addActionListener(this::handlePlayAgainButton);
        controlsPanel.add(playAgainButton);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(this::handleQuitButton);
        controlsPanel.add(quitButton);

        return controlsPanel;
    }

    private void handlePlayAgainButton(ActionEvent e)
    {
        int keepPlaying = JOptionPane.showConfirmDialog(
                this,
                "Do you want to discard the current game and start a new one?",
                "New Game?",
                JOptionPane.YES_NO_OPTION);

        if(keepPlaying == JOptionPane.YES_OPTION)
        {
            restartGame();
        }
    }

    private void handleQuitButton(ActionEvent e)
    {
        int keepPlaying = JOptionPane.showConfirmDialog(
                this,
                "Would you like to quit game?",
                "Quit game?",
                JOptionPane.YES_NO_OPTION);

        if(keepPlaying == JOptionPane.YES_OPTION)
        {
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }
}
