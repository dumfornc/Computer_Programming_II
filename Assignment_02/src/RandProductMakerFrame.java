import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class RandProductMakerFrame extends JFrame
{
    private static final File workingDirectory = new File(System.getProperty("user.dir"));
    private static final String RAND_ACCESS_FILE_PATH = workingDirectory.getPath() + "\\src\\RandAccessProductData.rand";

    private JTextArea productsPreviewTextArea;

    private JTextField productIdEntryField;
    private JTextField productNameEntryField;
    private JTextField productDescEntryField;
    private JSpinner productCostEntryField;
    private JTextField productNumEnteredField;

    private final ArrayList<Product> productsToCreate = new ArrayList<Product>();

    public RandProductMakerFrame()
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

        setTitle("Random Access Product Maker");

        initializeUI();
    }

    private void initializeUI()
    {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel formPanel = initializeFormPanel();
        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = initializeButtonsPanel();
        mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);

        this.add(mainPanel);
    }

    private JPanel initializeFormPanel()
    {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(1, 2));

        JPanel productEntryPanel = initializeProductEntryPanel();

        this.productsPreviewTextArea = new JTextArea();
        this.productsPreviewTextArea.setEditable(false);

        JScrollPane productsPreviewScroller = new JScrollPane
        (
            this.productsPreviewTextArea,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        formPanel.add(productEntryPanel);
        formPanel.add(productsPreviewScroller, BorderLayout.CENTER);

        return formPanel;
    }

    private JPanel initializeProductEntryPanel()
    {
        JPanel productEntryPanel = new JPanel();
        productEntryPanel.setLayout(new GridLayout(5, 2));

        initializeProductEntryFields();

        JLabel idLabel = new JLabel("Product ID:");
        productEntryPanel.add(idLabel);
        productEntryPanel.add(this.productIdEntryField);

        JLabel nameLabel = new JLabel("Product Name:");
        productEntryPanel.add(nameLabel);
        productEntryPanel.add(this.productNameEntryField);

        JLabel descriptionLabel = new JLabel("Product Description:");
        productEntryPanel.add(descriptionLabel);
        productEntryPanel.add(this.productDescEntryField);

        JLabel costLabel = new JLabel("Product Cost:");
        productEntryPanel.add(costLabel);
        productEntryPanel.add(this.productCostEntryField);

        JLabel countLabel = new JLabel("Entry Number:");
        productEntryPanel.add(countLabel);
        productEntryPanel.add(this.productNumEnteredField);

        return productEntryPanel;
    }

    private void initializeProductEntryFields()
    {
        this.productIdEntryField = new JTextField();
        this.productIdEntryField.setToolTipText("A unique ID " + Product.randomAccessIdLength + " characters long.");

        this.productNameEntryField = new JTextField();
        this.productNameEntryField.setToolTipText("A name no longer than " + Product.randomAccessNameLength + " characters.");

        this.productDescEntryField = new JTextField();
        this.productDescEntryField.setToolTipText("A description no longer than " + Product.randomAccessDescLength + " characters.");

        this.productCostEntryField = new JSpinner(new SpinnerNumberModel(100, 1, 99999, 100));
        this.productCostEntryField.setEditor(new JSpinner.NumberEditor(this.productCostEntryField, "#,##0.00"));
        this.productCostEntryField.setToolTipText("The cost of one unit of the product.");

        this.productNumEnteredField = new JTextField();
        this.productNumEnteredField.setToolTipText("The number of this product, incrementing each time a new product is saved");
        this.productNumEnteredField.setEditable(false);
        this.productNumEnteredField.setText("1");
    }

    private JPanel initializeButtonsPanel()
    {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2));

        JButton saveButton = new JButton("Save product to file");
        saveButton.addActionListener(this::saveProductsToFile);
        buttonsPanel.add(saveButton);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(this::quitButtonHandler);
        buttonsPanel.add(quitButton);

        return buttonsPanel;
    }

    private void saveProductsToFile(ActionEvent e)
    {
        String newProductId = this.productIdEntryField.getText();
        int newIdLength = newProductId.length();

        String newProductName = this.productNameEntryField.getText();
        int newNameLength = newProductName.length();

        String newProductDesc = this.productDescEntryField.getText();
        int newDescLength = newProductDesc.length();

        Integer newProductCost = (Integer) this.productCostEntryField.getValue();

        if(newProductId.isBlank() || newIdLength != Product.randomAccessIdLength)
        {
            JOptionPane.showMessageDialog(this, "Please enter an ID that is " + Product.randomAccessIdLength + " characters long.");
        }
        else if(newProductName.isBlank() || newNameLength > 35)
        {
            JOptionPane.showMessageDialog(this, "Please enter a name that is less than" + Product.randomAccessNameLength + " characters long.");
        }
        else if(newProductDesc.isBlank() || newDescLength > 75)
        {
            JOptionPane.showMessageDialog(this, "Please enter a description that is less than " + Product.randomAccessIdLength + " characters long.");
        }
        else {
            Product newProduct = new Product(newProductId, newProductName, newProductDesc, newProductCost);

            try(RandomAccessFile randFile = new RandomAccessFile(RAND_ACCESS_FILE_PATH, "rw"))
            {
                Integer numEntries = Integer.parseInt(this.productNumEnteredField.getText());
                newProduct.saveToRandAccessFile(randFile, numEntries);

                numEntries++;
                this.productNumEnteredField.setText(String.valueOf(numEntries));
            }
            catch(Exception ex)
            {
                JOptionPane.showMessageDialog(this, "There was an error when trying to save the file: " + ex);
            }

            this.productsPreviewTextArea.append(newProductId + ", " + newProductName + ", " + newProductDesc + ", " + newProductCost + "\n");

            clearInputFields();
        }
    }

    private void addProduct(ActionEvent e)
    {

    }

    private void clearInputFields()
    {
        this.productIdEntryField.setText("");
        this.productNameEntryField.setText("");
        this.productDescEntryField.setText("");
        this.productCostEntryField.setValue(100);
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
