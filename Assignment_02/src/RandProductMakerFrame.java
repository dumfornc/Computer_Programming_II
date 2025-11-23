import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

public class RandProductMakerFrame extends JFrame
{
    private JTextArea productsPreviewTextArea;

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

        JLabel idLabel = new JLabel("Product ID:");
        productEntryPanel.add(idLabel);

        JLabel nameLabel = new JLabel("Product Name:");
        productEntryPanel.add(nameLabel);

        JLabel descriptionLabel = new JLabel("Product Description:");
        productEntryPanel.add(descriptionLabel);

        JLabel costLabel = new JLabel("Product Cost:");
        productEntryPanel.add(costLabel);

        JLabel countLabel = new JLabel("Num Products Entered:");
        productEntryPanel.add(countLabel);

        //Add all fields as class variables and add tooltips to all fields with max character nums

        return productEntryPanel;
    }

    private JPanel initializeButtonsPanel()
    {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2));

        JButton saveButton = new JButton("Save product to file");
        saveButton.addActionListener(this::saveResultsToFile);
        buttonsPanel.add(saveButton);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(this::quitButtonHandler);
        buttonsPanel.add(quitButton);

        return buttonsPanel;
    }

    private void saveResultsToFile(ActionEvent e)
    {
        JFileChooser saveFileChooser = new JFileChooser();


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
