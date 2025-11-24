import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class RandProductSearchFrame extends JFrame
{
    JTextArea matchingProductsTextArea;

    JTextField searchText;

    public RandProductSearchFrame()
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

        setTitle("Random Access Product Searcher");

        initializeUI();
    }

    private void initializeUI()
    {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel topPanel = initializeTopPanel();
        mainPanel.add(topPanel, BorderLayout.PAGE_START);

        this.matchingProductsTextArea = new JTextArea();
        this.matchingProductsTextArea.setEditable(false);
        JScrollPane productsPreviewScroller = new JScrollPane
        (
            this.matchingProductsTextArea,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        mainPanel.add(productsPreviewScroller, BorderLayout.CENTER);

        this.add(mainPanel);
    }

    private JPanel initializeTopPanel()
    {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 1));

        JPanel searchPanel = initializeSearchPanel();
        topPanel.add(searchPanel);

        JButton searchButton = new JButton("Search for matching product names");
        searchButton.addActionListener(this::searchForProducts);
        topPanel.add(searchButton);

        return topPanel;
    }

    private JPanel initializeSearchPanel()
    {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(1, 2));

        JLabel instructionsLabel = new JLabel("Find matching product names:");
        searchPanel.add(instructionsLabel);

        this.searchText = new JTextField();
        searchPanel.add(this.searchText);

        return searchPanel;
    }

    private void searchForProducts(ActionEvent e)
    {
        this.matchingProductsTextArea.setText("");

        try(RandomAccessFile randFile = new RandomAccessFile(RandProductMakerFrame.RAND_ACCESS_FILE_PATH, "r"))
        {
            ArrayList<Product> discoveredProducts;
            discoveredProducts = Product.retrieveRandomAccessProducts(randFile);

            ArrayList<Product> matchingProducts = new ArrayList<Product>();

            for(Product prod : discoveredProducts)
            {
                if(prod.getName().toLowerCase().contains(this.searchText.getText().toLowerCase()))
                {
                    matchingProducts.add(prod);
                }
            }

            if(matchingProducts.isEmpty())
            {
                JOptionPane.showMessageDialog(this, "No products match this search string");
            }
            else
            {
                for(Product prod : matchingProducts)
                {
                    this.matchingProductsTextArea.append(prod.toCSV() + "\n");
                }
            }
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(this, "There was an error reading the file: " + ex);
        }
    }
}
