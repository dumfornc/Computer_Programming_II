import javax.swing.*;
import java.awt.*;

public class RockPaperScissorsFrame extends JFrame
{
    private int playerRockUses = 0;
    private int playerPaperUses = 0;
    private int playerScissorsUses = 0;

    public RockPaperScissorsFrame()
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
    }

    private void initializeUI()
    {

    }
}
