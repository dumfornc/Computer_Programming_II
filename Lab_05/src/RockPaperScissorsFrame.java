import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

public class RockPaperScissorsFrame extends JFrame
{
    //Instance counts of how many times a player has used each option
    private int playerRockUses = 0;
    private int playerPaperUses = 0;
    private int playerScissorsUses = 0;

    private int playerWins = 0;
    private int ties = 0;
    private int computerWins = 0;

    //Constants used internally to represent the different options
    public static final int ROCK = 0;
    public static final int PAPER = 1;
    public static final int SCISSORS = 2;

    public static final String[] optionNames = {"Rock", "Paper", "Scissors"};

    //Array indexed by the constant values that holds the words used to describe that option winning
    public static final String[] victoryWords = {" crushes ", " covers ", " cuts "};

    //Declaring text fields as instance variables so that they can easily be updated
    private JTextField playerWinsField = new JTextField();
    private JTextField tiesField = new JTextField();
    private JTextField computerWinsField = new JTextField();

    private JTextArea historyText = new JTextArea();

    //A random number generator to generate the random response of the program
    private Random programResponseGenerator = new Random();

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

        JButton rockButton = createRPSButton("imgs\\Rock.png", "Rock", ROCK);
        buttonPanel.add(rockButton);

        JButton paperButton = createRPSButton("imgs\\Paper.png", "Paper", PAPER);
        buttonPanel.add(paperButton);

        JButton scissorsButton = createRPSButton("imgs\\Scissors.png", "Scissors", SCISSORS);
        buttonPanel.add(scissorsButton);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener((e) -> {dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));});
        buttonPanel.add(quitButton);

        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        return buttonPanel;
    }

    private JButton createRPSButton(String imagePath, String buttonText, int actionEnum)
    {
        ImageIcon buttonImage = new ImageIcon(imagePath);
        JButton newButton = new JButton(buttonText, buttonImage);

        newButton.setHorizontalTextPosition(JButton.CENTER);
        newButton.setVerticalTextPosition(JButton.BOTTOM);

        newButton.addActionListener((e) -> {this.playGame(e, actionEnum);});

        return newButton;
    }

    private void playGame(ActionEvent e, int playerChoice)
    {
        this.incrementPlayerChoice(playerChoice);

        this.determineOutcome(playerChoice, this.determineComputerChoice());
    }

    private void incrementPlayerChoice(int playerChoice)
    {
        if(playerChoice == ROCK)
        {
            this.playerRockUses++;
        }
        else if(playerChoice == PAPER)
        {
            this.playerPaperUses++;
        }
        else if(playerChoice == SCISSORS)
        {
            this.playerScissorsUses++;
        }
    }

    private int determineComputerChoice()
    {
        return this.programResponseGenerator.nextInt(0, 3);
    }

    private void determineOutcome(int playerChoice, int computerChoice)
    {
        if(playerChoice == computerChoice)
        {
            handleTie(playerChoice, computerChoice);
        }
        else if(playerChoice == ROCK)
        {
            if(computerChoice == PAPER)
            {
                handleComputerWin(PAPER);
            }
            else if(computerChoice == SCISSORS)
            {
                handlePlayerWin(ROCK);
            }
        }
        else if(playerChoice == PAPER)
        {
            if(computerChoice == SCISSORS)
            {
                handleComputerWin(SCISSORS);
            }
            else if(computerChoice == ROCK)
            {
                handlePlayerWin(PAPER);
            }
        }
        else if(playerChoice == SCISSORS)
        {
            if(computerChoice == ROCK)
            {
                handleComputerWin(ROCK);
            }
            else if(computerChoice == PAPER)
            {
                handlePlayerWin(SCISSORS);
            }
        }
    }

    private void handleTie(int playerChoice, int computerChoice)
    {
        String tieMessage = optionNames[playerChoice] + " ties " + optionNames[computerChoice] + "(No Winner)\n";
        this.historyText.append(tieMessage);

        this.ties++;
        this.tiesField.setText(String.valueOf(this.ties));
    }

    private void handleComputerWin(int computerChoice)
    {
        String computerWinMessage = createVictoryMessage(computerChoice) + " (Computer Wins)\n";
        this.historyText.append(computerWinMessage);

        this.computerWins++;
        this.computerWinsField.setText(String.valueOf(this.computerWins));
    }

    private void handlePlayerWin(int playerChoice)
    {
        String playerWinMessage = createVictoryMessage(playerChoice) + " (Player Wins)\n";
        this.historyText.append(playerWinMessage);

        this.playerWins++;
        this.playerWinsField.setText(String.valueOf(this.playerWins));
    }

    private String createVictoryMessage(int victorChoice)
    {
        String winMessage = "";

        if(victorChoice == ROCK)
        {
            winMessage = optionNames[ROCK] + victoryWords[ROCK] + optionNames[SCISSORS];
        }
        else if(victorChoice == PAPER)
        {
            winMessage = optionNames[PAPER] + victoryWords[PAPER] + optionNames[ROCK];
        }
        else if(victorChoice == SCISSORS)
        {
            winMessage =optionNames[SCISSORS] + victoryWords[SCISSORS] + optionNames[PAPER];
        }

        return winMessage;
    }
}
