import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

public class PizzaGUIFrame extends JFrame
{
    private JTextArea resultsText;

    PizzaGUIFrame()
    {
        setTitle("Pizza Order Form");
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
        mainPanel.setLayout(new GridLayout(3, 1));

        JPanel topPanel = initializeOrderPanel();
        JPanel middlePanel = initializeResultsPanel();
        JPanel bottomPanel = initializeButtonsPanel();

        mainPanel.add(topPanel);
        mainPanel.add(middlePanel);
        mainPanel.add(bottomPanel);

        this.add(mainPanel);
    }

    private JPanel initializeOrderPanel()
    {
        JPanel orderPanel = new JPanel();
        orderPanel.setLayout(new GridLayout(1, 2));

        JPanel topLeftPanel = initializePizzaTypePanel();
        JPanel topRightPanel = initializePizzaToppingsPanel();

        orderPanel.add(topLeftPanel);
        orderPanel.add(topRightPanel);

        return orderPanel;
    }

    private JPanel initializePizzaTypePanel()
    {
        JPanel pizzaTypePanel = new JPanel();
        pizzaTypePanel.setLayout(new GridLayout(1, 2));
        pizzaTypePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLoweredBevelBorder(),
                "Choose Your Pizza Type:"
        ));


        JPanel leftPanel = initializePizzaSizePanel();
        JPanel rightPanel = initializeCrustTypePanel();

        pizzaTypePanel.add(leftPanel);
        pizzaTypePanel.add(rightPanel);

        return pizzaTypePanel;
    }

    private JPanel initializePizzaSizePanel()
    {
        JPanel pizzaSizePanel = new JPanel();
        pizzaSizePanel.setLayout(new BorderLayout());
        pizzaSizePanel.setBorder(new EmptyBorder(40, 10, 40, 10));

        String[] pizzaSizes = {"Small ($8)", "Medium ($12)", "Large ($16)", "Super ($20)"};
        JComboBox pizzaSizeDropdown = new JComboBox(pizzaSizes);

        pizzaSizePanel.add(pizzaSizeDropdown, BorderLayout.CENTER);

        return pizzaSizePanel;
    }

    private JPanel initializeCrustTypePanel()
    {
        JPanel crustTypePanel = new JPanel();
        crustTypePanel.setLayout(new GridLayout(3, 1));

        ButtonGroup crustTypeButtons = new ButtonGroup();

        JRadioButton thinCrustTypeButton = new JRadioButton("Thin Crust");
        crustTypeButtons.add(thinCrustTypeButton);

        JRadioButton regularCrustTypeButton = new JRadioButton("Regular Crust");
        regularCrustTypeButton.setSelected(true);
        crustTypeButtons.add(regularCrustTypeButton);

        JRadioButton deepDishCrustTypeButton = new JRadioButton("Deep Dish Crust");
        crustTypeButtons.add(deepDishCrustTypeButton);

        crustTypePanel.add(thinCrustTypeButton);
        crustTypePanel.add(regularCrustTypeButton);
        crustTypePanel.add(deepDishCrustTypeButton);

        return crustTypePanel;
    }

    private JPanel initializePizzaToppingsPanel()
    {
        JPanel pizzaToppingsPanel = new JPanel();
        pizzaToppingsPanel.setLayout(new GridLayout(3, 2));
        pizzaToppingsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLoweredBevelBorder(),
                "Choose Your Pizza Toppings (Each $1):"
        ));

        JCheckBox baconToppingBox = new JCheckBox("Bacon");
        pizzaToppingsPanel.add(baconToppingBox);

        JCheckBox sausageToppingBox = new JCheckBox("Sausage");
        pizzaToppingsPanel.add(sausageToppingBox);

        JCheckBox appleToppingBox = new JCheckBox("Apple Slices");
        pizzaToppingsPanel.add(appleToppingBox);

        JCheckBox dragonToppingBox = new JCheckBox("Dragon Teeth");
        pizzaToppingsPanel.add(dragonToppingBox);

        JCheckBox extraCheeseToppingBox = new JCheckBox("Extra Cheese");
        pizzaToppingsPanel.add(extraCheeseToppingBox);

        JCheckBox alfredoToppingBox = new JCheckBox("Alfredo Sauce");
        pizzaToppingsPanel.add(alfredoToppingBox);

        return pizzaToppingsPanel;
    }

    private JPanel initializeResultsPanel()
    {
        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new BorderLayout());
        resultsPanel.setBorder(BorderFactory.createLoweredBevelBorder());

        this.resultsText = new JTextArea();
        resultsPanel.add(this.resultsText, BorderLayout.CENTER);

        return resultsPanel;
    }

    private JPanel initializeButtonsPanel()
    {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1 ,3));
        buttonsPanel.setBorder(BorderFactory.createLoweredBevelBorder());

//        JButton orderButton = initializeOrderButton();
//        buttonsPanel.add(orderButton);
//
//        JButton clearButton = initializeClearButton();
//        buttonsPanel.add(clearButton);

        JButton quitButton = initializeQuitButton();
        buttonsPanel.add(quitButton);

        return buttonsPanel;
    }

//    private JButton initializeOrderButton()
//    {
//
//    }
//
//    private JButton initializeClearButton()
//    {
//
//    }

    private JButton initializeQuitButton()
    {
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(this::quitButtonHandler);

        return quitButton;
    }

    private void quitButtonHandler(ActionEvent e)
    {
        int quitConfirmation = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to quit?",
                "Quit Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if(quitConfirmation == JOptionPane.YES_OPTION) {dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));}
    }
}
