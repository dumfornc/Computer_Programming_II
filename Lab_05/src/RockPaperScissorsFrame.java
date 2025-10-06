import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class RockPaperScissorsFrame extends JFrame
{
    private int playerRockUses = 0;
    private int playerPaperUses = 0;
    private int playerScissorsUses = 0;

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

        JPanel buttonsPanel = initializeButtonPanel();
        mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
    }

    private JPanel initializeButtonPanel()
    {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,4));

        ImageIcon rockImage = new ImageIcon("..\\imgs\\Rock.png");
        JButton rockButton = new JButton("Rock", rockImage);

        buttonPanel.add(rockButton);

        ImageIcon paperImage = new ImageIcon("..\\imgs\\Paper.png");
        JButton paperButton = new JButton("Paper", paperImage);

        buttonPanel.add(paperButton);

        ImageIcon scissorsImage = new ImageIcon("..\\imgs\\Scissors.png");
        JButton scissorsButton = new JButton("Scissors", scissorsImage);

        buttonPanel.add(scissorsButton);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener((e) -> {dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));});
        buttonPanel.add(quitButton);

        return buttonPanel;
    }
}
