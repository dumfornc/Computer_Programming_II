import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowEvent;

public class RockPaperScissorsFrame extends JFrame
{
    //Instance counts of how many times a player has used each option
    private int playerRockUses = 0;
    private int playerPaperUses = 0;
    private int playerScissorsUses = 0;

    //Constants used internally to represent the different options
    public static final int ROCK = 1;
    public static final int PAPER = 2;
    public static final int SCISSORS = 3;

    //Declaring text fields as instance variables so that they can easily be updated
    private JTextField playerWinsField = new JTextField();
    private JTextField tiesField = new JTextField();
    private JTextField computerWinsField = new JTextField();

    private JTextArea historyText = new JTextArea();

    public RockPaperScissorsFrame()
    {
        setTitle("Rock Paper Scissors");
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
    }

    private void initializeUI()
    {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel statsPanel = initializeStatsPanel();
        mainPanel.add(statsPanel, BorderLayout.PAGE_START);

        JPanel historyPanel = initializeHistoryPanel();
        mainPanel.add(historyPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = initializeButtonPanel();
        mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);

        this.add(mainPanel);
    }

    private JPanel initializeStatsPanel()
    {
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(2,3));

        JLabel playerWinsLabel = new JLabel("Player Wins");
        statsPanel.add(playerWinsLabel);

        JLabel tiesLabel = new JLabel("Ties");
        statsPanel.add(tiesLabel);

        JLabel computerWinsLabel = new JLabel("Computer Wins");
        statsPanel.add(computerWinsLabel);

        this.playerWinsField.setEditable(false);
        statsPanel.add(this.playerWinsField);

        this.tiesField.setEditable(false);
        statsPanel.add(this.tiesField);

        this.computerWinsField.setEditable(false);
        statsPanel.add(this.computerWinsField);

        return statsPanel;
    }

    private JPanel initializeHistoryPanel()
    {
        JPanel historyPanel = new JPanel(new BorderLayout());

        Font middlePanelFont = new Font("Calibri", Font.PLAIN, 16);
        this.historyText.setFont(middlePanelFont);
        this.historyText.setEditable(false);

        JScrollPane scroller = new JScrollPane(this.historyText, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        historyPanel.add(scroller, BorderLayout.CENTER);

        return historyPanel;
    }

    private JPanel initializeButtonPanel()
    {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,4));

        JButton rockButton = createRPSButton("imgs\\Rock.png", "Rock");
        buttonPanel.add(rockButton);

        JButton paperButton = createRPSButton("imgs\\Paper.png", "Paper");
        buttonPanel.add(paperButton);

        JButton scissorsButton = createRPSButton("imgs\\Scissors.png", "Scissors");
        buttonPanel.add(scissorsButton);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener((e) -> {dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));});
        buttonPanel.add(quitButton);

        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        return buttonPanel;
    }

    private JButton createRPSButton(String imagePath, String buttonText)
    {
        ImageIcon buttonImage = new ImageIcon(imagePath);
        JButton newButton = new JButton(buttonText, buttonImage);
        newButton.setHorizontalTextPosition(JButton.CENTER);
        newButton.setVerticalTextPosition(JButton.BOTTOM);

        return newButton;
    }
}
