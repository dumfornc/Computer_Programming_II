import javax.swing.*;
import java.awt.*;

public class BattleShipFrame extends JFrame
{
    BattleShipGame gameLogic;

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

        this.initializeUI();

        this.gameLogic = new BattleShipGame();

//        startGame();
    }

    public void initializeUI()
    {
        JPanel mainPanel = new JPanel();

        add(mainPanel);
    }
}
