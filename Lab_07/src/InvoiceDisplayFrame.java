import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class InvoiceDisplayFrame extends JFrame
{
    private Customer customer;
    private Map<Product, Integer> products;

    InvoiceDisplayFrame()
    {
        setTitle("Invoice Creator");
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

        JPanel topPanel = initializeCustomerInputPanel();
        mainPanel.add(topPanel, BorderLayout.PAGE_START);

        JPanel middlePanel = initializeProductCreationPanel();
        mainPanel.add(middlePanel, BorderLayout.CENTER);

        JPanel bottomPanel = initializeButtonsPanel(); //Create invoice button & quit button (move customer buttons here too?)
        mainPanel.add(bottomPanel);

        this.add(mainPanel);
    }

    private JPanel initializeCustomerInputPanel()
    {
        JPanel customerInputPanel = new JPanel();
        customerInputPanel.setLayout(new GridLayout(2,1));

        JPanel customerDataInputsPanel = initializeCustomerDataInputPanel();
        customerInputPanel.add(customerDataInputsPanel);

        JPanel customerButtonsPanel = initializeCustomerButtonsPanel();
        customerInputPanel.add(customerButtonsPanel);

        return customerInputPanel;
    }

    private JPanel initializeCustomerDataInputPanel()
    {
        JPanel customerDataInputPanel = new JPanel();
        customerDataInputPanel.setLayout(new GridLayout(2, 5));

        // 5 x 2 grid with label above input for name, address, city, state, & zip
    }

    private JPanel initializeCustomerButtonsPanel()
    {
        JPanel customerButtonsPanel = new JPanel();
        customerButtonsPanel.setLayout(new GridLayout(1, 2));

        JButton createCustomerButton = new JButton("Set Customer Data");
        createCustomerButton.addActionListener(); //Make fields uneditable
        customerButtonsPanel.add(createCustomerButton);

        JButton clearCustomerButton = new JButton("Clear Customer Data");
        createCustomerButton.addActionListener(); //Clear fields and make them editable again
        customerButtonsPanel.add(clearCustomerButton);

        return customerButtonsPanel;
    }
}
