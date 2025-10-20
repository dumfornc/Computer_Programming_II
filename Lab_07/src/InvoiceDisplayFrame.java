import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map;

public class InvoiceDisplayFrame extends JFrame
{
    private Customer customer;
    private Map<Product, Integer> products;

    private final JTextField customerNameField = new JTextField();
    private final JTextField customerAddressField = new JTextField();
    private final JTextField customerCityField = new JTextField();
    private final JTextField customerStateField = new JTextField();
    private final JTextField customerZipField = new JTextField();

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

//        JPanel middlePanel = initializeProductCreationPanel();
//        mainPanel.add(middlePanel, BorderLayout.CENTER);

//        JPanel bottomPanel = initializeButtonsPanel(); //Create invoice button & quit button (move customer buttons here too?)
//        mainPanel.add(bottomPanel);

        this.add(mainPanel);
    }

    private JPanel initializeCustomerInputPanel()
    {
        JPanel customerInputPanel = new JPanel();
        customerInputPanel.setLayout(new GridLayout(2,1));
        customerInputPanel.setBorder(new TitledBorder("Customer Details"));

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
        JLabel customerNameLabel = new JLabel("Name:");
        JLabel customerAddressLabel = new JLabel("Address:");
        JLabel customerCityLabel = new JLabel("City:");
        JLabel customerStateLabel = new JLabel("State:");
        JLabel customerZipLabel = new JLabel("Zip Code:");

        customerDataInputPanel.add(customerNameLabel);
        customerDataInputPanel.add(customerAddressLabel);
        customerDataInputPanel.add(customerCityLabel);
        customerDataInputPanel.add(customerStateLabel);
        customerDataInputPanel.add(customerZipLabel);

        customerDataInputPanel.add(this.customerNameField);
        customerDataInputPanel.add(this.customerAddressField);
        customerDataInputPanel.add(this.customerCityField);
        customerDataInputPanel.add(this.customerStateField);
        customerDataInputPanel.add(this.customerZipField);

        return customerDataInputPanel;
    }

    private JPanel initializeCustomerButtonsPanel()
    {
        JPanel customerButtonsPanel = new JPanel();
        customerButtonsPanel.setLayout(new GridLayout(1, 2));

        JButton createCustomerButton = new JButton("Set Customer Data");
        createCustomerButton.addActionListener(this::createCustomerListener); //Make fields uneditable
        customerButtonsPanel.add(createCustomerButton);

        JButton clearCustomerButton = new JButton("Clear Customer Data");
        clearCustomerButton.addActionListener(this::clearCustomerListener); //Clear fields and make them editable again
        customerButtonsPanel.add(clearCustomerButton);

        return customerButtonsPanel;
    }

    private void createCustomerListener(ActionEvent e)
    {
        if(
            customerNameField.getText().isBlank() ||
            customerAddressField.getText().isBlank() ||
            customerCityField.getText().isBlank() ||
            customerStateField.getText().isBlank() ||
            customerZipField.getText().isBlank()
        )
        {
            JOptionPane.showMessageDialog(
                this,
                "All customer data fields must be filled out to set customer data.",
                "Incomplete customer data",
                JOptionPane.WARNING_MESSAGE
            );
        }
        else
        {
            this.customer = new Customer(
                customerNameField.getText(),
                customerAddressField.getText(),
                customerCityField.getText(),
                customerStateField.getText(),
                customerZipField.getText()
            );

            //Sets all customer fields to uneditable until customer data is cleared
            customerNameField.setEditable(false);
            customerAddressField.setEditable(false);
            customerCityField.setEditable(false);
            customerStateField.setEditable(false);
            customerZipField.setEditable(false);
        }
    }

    private void clearCustomerListener(ActionEvent e)
    {
        this.customer = null;

        customerNameField.setText("");
        customerAddressField.setText("");
        customerCityField.setText("");
        customerStateField.setText("");
        customerZipField.setText("");

        customerNameField.setEditable(true);
        customerAddressField.setEditable(true);
        customerCityField.setEditable(true);
        customerStateField.setEditable(true);
        customerZipField.setEditable(true);
    }
}
