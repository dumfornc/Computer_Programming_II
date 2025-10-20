import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class InvoiceDisplayFrame extends JFrame
{
    private Customer customer;
    // This holds a map of the product objects created and the numeric input tracking how many of that product to order
    private LinkedHashMap<Product, JSpinner> orderContents = new LinkedHashMap<>();

    private JPanel mainPanel;

    private final JTextField customerNameField = new JTextField();
    private final JTextField customerAddressField = new JTextField();
    private final JTextField customerCityField = new JTextField();
    private final JTextField customerStateField = new JTextField();
    private final JTextField customerZipField = new JTextField();

    // This holds the JPanels that each form a line in the order contents of the invoice builder.
    // Each time a new product is added the order contents panel is rebuilt using the existing products from this array
    private ArrayList<JPanel> orderContentsLines = new ArrayList<>();

    // Fields on the product builder that need to get referenced outside the method that initializes them
    JTextField productNameField;
    JSpinner productCostField;

    // This variable holds the field tracking how many of a product to order and allows it to be created in one
    // method and added to orderContents in another method
    JSpinner numProductToOrderField;

    public InvoiceDisplayFrame()
    {
        setTitle("Invoice Creator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Get screen dimensions
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        // Center frame in screen
        setSize(screenWidth / 2, screenHeight / 2);
        setLocation(screenWidth / 4, screenHeight / 4);

        this.initializeUI();
    }

    private void initializeUI()
    {
        this.mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel topPanel = initializeCustomerInputPanel();
        mainPanel.add(topPanel, BorderLayout.PAGE_START);

        JPanel middlePanel = initializeOrderContentsPanel();
        mainPanel.add(middlePanel, BorderLayout.CENTER);

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
        createCustomerButton.addActionListener(this::createCustomerHandler); //Make fields uneditable
        customerButtonsPanel.add(createCustomerButton);

        JButton clearCustomerButton = new JButton("Clear Customer Data");
        clearCustomerButton.addActionListener(this::clearCustomerHandler); //Clear fields and make them editable again
        customerButtonsPanel.add(clearCustomerButton);

        return customerButtonsPanel;
    }

    private void createCustomerHandler(ActionEvent e)
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

    private void clearCustomerHandler(ActionEvent e)
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

    private JPanel initializeOrderContentsPanel()
    {
        JPanel orderContentsPanel = new JPanel();

        Integer numOrderContentsLines = orderContentsLines.size() + 2;

        orderContentsPanel.setLayout(new GridLayout(numOrderContentsLines, 1));
        orderContentsPanel.setBorder(BorderFactory.createLoweredBevelBorder());

        orderContentsPanel.add(initializeOrderContentsHeadersPanel());

        for(JPanel product : orderContentsLines)
        {
            orderContentsPanel.add(product);
        }

        JButton addProductButton = new JButton("+ Add a new product");
        addProductButton.addActionListener(this::addProductHandler);
        orderContentsPanel.add(addProductButton);

        return orderContentsPanel;
    }

    private JPanel initializeOrderContentsHeadersPanel()
    {
        JPanel orderContentsHeadersPanel = new JPanel();
        orderContentsHeadersPanel.setLayout(new GridLayout(1, 3));

        JLabel nameHeaderLabel = new JLabel("Product Name:");
        nameHeaderLabel.setBorder(new LineBorder(Color.black));
        orderContentsHeadersPanel.add(nameHeaderLabel);

        JLabel costHeaderLabel = new JLabel("Product Cost:");
        costHeaderLabel.setBorder(new LineBorder(Color.black));
        orderContentsHeadersPanel.add(costHeaderLabel);

        JLabel quantityHeaderLabel = new JLabel("Product Quantity:");
        quantityHeaderLabel.setBorder(new LineBorder(Color.black));
        orderContentsHeadersPanel.add(quantityHeaderLabel);

        return orderContentsHeadersPanel;
    }

    private void addProductHandler(ActionEvent e)
    {
        JPanel createProductPanel = initializeCreateProductPanel();

        Integer productCreationResult = JOptionPane.showConfirmDialog(
                this,
                createProductPanel,
                "Enter Product Details",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if(productCreationResult == JOptionPane.OK_OPTION)
        {
            orderContentsLines.add(this.createProductLine());

            this.orderContents.put(new Product(productNameField.getText(), (Double) productCostField.getValue()), numProductToOrderField);

            this.recreateOrderContentsPanel();
        }
    }

    private JPanel initializeCreateProductPanel()
    {
        JPanel createProductPanel = new JPanel();
        createProductPanel.setLayout(new GridLayout(2, 2));

        this.productNameField = new JTextField();
        this.productCostField = new JSpinner(new SpinnerNumberModel(1.99, 0.01, 999.99, 1));

        createProductPanel.add(new JLabel("Product Name:"));
        createProductPanel.add(productNameField);

        createProductPanel.add(new JLabel("Cost of One Unit ($):"));
        createProductPanel.add(productCostField);

        return createProductPanel;
    }

    private JPanel createProductLine()
    {
        JPanel newProductLine = new JPanel();
        newProductLine.setLayout(new GridLayout(1, 3));
        newProductLine.setBorder(BorderFactory.createLoweredBevelBorder());

        numProductToOrderField = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));

        newProductLine.add(new JLabel(productNameField.getText()));
        newProductLine.add(new JLabel("$" + productCostField.getValue().toString()));
        newProductLine.add(numProductToOrderField);

        return newProductLine;
    }

    private void recreateOrderContentsPanel()
    {
        BorderLayout mainLayout = (BorderLayout) mainPanel.getLayout();
        Component oldOrderContentsPanel = mainLayout.getLayoutComponent(BorderLayout.CENTER);
        mainPanel.remove(oldOrderContentsPanel);

        JPanel newOrderContentsPanel = initializeOrderContentsPanel();
        mainPanel.add(newOrderContentsPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}
