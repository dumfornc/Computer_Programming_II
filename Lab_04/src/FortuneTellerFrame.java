import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

public class FortuneTellerFrame extends JFrame
{
    private ArrayList<String> fortunes = new ArrayList<String>();
    private JTextArea fortuneTextArea = new JTextArea(24, 50);
    private Random randomFortuneIndex = new Random();
    private int lastFortuneIndex = -1;

    public FortuneTellerFrame() throws HeadlessException
    {
        this.setFortunes();

        setTitle("Fortune Teller");
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

    private void setFortunes()
    {
        this.fortunes.add("One day when you are older you will get hit by a boulder");
        this.fortunes.add("At some point in the future you will grow older");
        this.fortunes.add("Do or do not, there is no try");
        this.fortunes.add("Your lucky number is the last 2 digits of pi");
        this.fortunes.add("If you never try you will never succeed");
        this.fortunes.add("Honesty is the best policy");
        this.fortunes.add("You should go pet a penguin");
        this.fortunes.add("When the dark comes a rising, 6 shall turn it back");
        this.fortunes.add("Fire on the mountain shall find the harp of gold");
        this.fortunes.add("All shall find the light at last");
        this.fortunes.add("If you fail to hydrate today bad things will happen");
        this.fortunes.add("You will pass a class in the next 2 years");
    }

    private void initializeUI()
    {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel topPanel = initializeTopPanel();
        JPanel middlePanel = initializeMiddlePanel();
        JPanel bottomPanel = initializeBottomPanel();

        mainPanel.add(topPanel, BorderLayout.PAGE_START);
        mainPanel.add(middlePanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.PAGE_END);

        add(mainPanel);
    }

    private JPanel initializeTopPanel()
    {
        JPanel topPanel = new JPanel();

        ImageIcon readImagee = new ImageIcon("Images\\FortuneTeller.png");
        Image originalImage = readImagee.getImage();
        Image resizedImage = originalImage.getScaledInstance(360, 240, Image.SCALE_SMOOTH);
        ImageIcon fortuneTellerImage = new ImageIcon(resizedImage);

        JLabel title = new JLabel("Are You Ready to Learn the Future?", fortuneTellerImage, JLabel.CENTER);
        title.setHorizontalTextPosition(JLabel.CENTER);
        title.setVerticalTextPosition(JLabel.BOTTOM);

        Font topPanelFont = new Font("Gabriola", Font.PLAIN, 50);
        title.setFont(topPanelFont);

        topPanel.add(title);
        return topPanel;
    }

    private JPanel initializeMiddlePanel()
    {
        JPanel middlePanel = new JPanel(new BorderLayout());

        Font middlePanelFont = new Font("Calibri", Font.PLAIN, 16);
        this.fortuneTextArea.setFont(middlePanelFont);

        JScrollPane scroller = new JScrollPane(this.fortuneTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        middlePanel.add(scroller, BorderLayout.CENTER);

        return middlePanel;
    }

    private JPanel initializeBottomPanel()
    {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        JButton fortuneButton = new JButton("Read My Fortune!");
        fortuneButton.addActionListener((x) -> {this.fortuneTextArea.append(this.getFortune() + "\n");});
        bottomPanel.add(fortuneButton, BorderLayout.LINE_START);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener((x) -> {dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));});
        bottomPanel.add(quitButton, BorderLayout.LINE_END);

        return bottomPanel;
    }

    public String getFortune()
    {
        String newFortune = "";

        while (newFortune.isEmpty())
        {
            int newIndex = this.randomFortuneIndex.nextInt(12);

            if (newIndex != this.lastFortuneIndex)
            {
                newFortune = this.fortunes.get(newIndex);
                this.lastFortuneIndex = newIndex;
            }
        }

        return newFortune;
    }
}
