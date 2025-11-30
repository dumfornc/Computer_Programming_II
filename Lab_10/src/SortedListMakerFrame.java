import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

public class SortedListMakerFrame extends JFrame
{
    private final SortedList sortedList = new SortedList();

    private final JTextPane listDisplayTextPane = new JTextPane();

    private SimpleAttributeSet attentionGrabbing;
    private SimpleAttributeSet normalText;

    private final JTextField addItemInput = new JTextField();
    private final JTextField searchItemInput = new JTextField();

    // This is an instance variable so it can be disabled until there is something in the list
    private JButton searchItemButton;

    public SortedListMakerFrame()
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

        setTitle("Sorted List Maker");

        initializeTextAreaStyles();
        initializeUI();
    }

    private void initializeTextAreaStyles()
    {
        this.attentionGrabbing = new SimpleAttributeSet();
        StyleConstants.setBold(this.attentionGrabbing, true);
        StyleConstants.setForeground(this.attentionGrabbing, Color.RED);

        this.normalText = new SimpleAttributeSet();
    }

    private void initializeUI()
    {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel controlsPanel = initializeControlsPanel();
        mainPanel.add(controlsPanel, BorderLayout.PAGE_START);

        JScrollPane resultsScrollArea = initializeResultsScrollArea();
        mainPanel.add(resultsScrollArea, BorderLayout.CENTER);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(this::quitButtonHandler);
        mainPanel.add(quitButton, BorderLayout.PAGE_END);

        add(mainPanel);
    }

    private JPanel initializeControlsPanel()
    {
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new GridLayout(1, 2));

        JPanel addingItemsPanel = initializeAddingItemsPanel();
        controlsPanel.add(addingItemsPanel);

        JPanel searchingItemsPanel = initializeSearchingItemsPanel();
        controlsPanel.add(searchingItemsPanel);

        return controlsPanel;
    }

    private JPanel initializeAddingItemsPanel()
    {
        JPanel addingItemsPanel = new JPanel();
        addingItemsPanel.setLayout(new BorderLayout());

        JPanel addingItemsInputPanel = initializeAddingItemsInputPanel();
        addingItemsPanel.add(addingItemsInputPanel, BorderLayout.CENTER);

        JButton addItemButton = new JButton("Add item");
        addItemButton.addActionListener(this::addItemHandler);
        addingItemsPanel.add(addItemButton, BorderLayout.PAGE_END);

        return addingItemsPanel;
    }

    private JPanel initializeAddingItemsInputPanel()
    {
        JPanel addingItemsInputPanel = new JPanel();
        addingItemsInputPanel.setLayout(new GridLayout(1, 2));

        JLabel addingItemsLabel = new JLabel("Enter a string to add to a sorted list:");
        addingItemsInputPanel.add(addingItemsLabel);

        addingItemsInputPanel.add(this.addItemInput);

        return addingItemsInputPanel;
    }

    private void addItemHandler(ActionEvent e)
    {
        String addedItem = this.addItemInput.getText();
        if(!addedItem.isBlank())
        {
            // Now that there is an item the list can be searched
            this.searchItemButton.setEnabled(true);

            this.addItemInput.setText("");

            this.sortedList.add(addedItem);

            StyledDocument doc = listDisplayTextPane.getStyledDocument();

            try
            {
                doc.insertString(doc.getLength(), "Item '" + addedItem + "' added to list:\n[", this.normalText);

                for(String item : sortedList.getSortedList())
                {
                    if(item.equalsIgnoreCase(addedItem))
                    {
                        doc.insertString(doc.getLength(), item, this.attentionGrabbing);
                    }
                    else
                    {
                        doc.insertString(doc.getLength(), item, this.normalText);
                    }
                    doc.insertString(doc.getLength(), ", ", this.normalText);
                }

                // remove trailing ", "
                doc.remove(doc.getLength() - 2, 2);
                doc.insertString(doc.getLength(), "]\n\n", this.normalText);
            }
            catch(BadLocationException _)
            {
                // This shouldn't be an issue since all the inserts are based on the length of the document
            }
        }
    }

    private JPanel initializeSearchingItemsPanel()
    {
        JPanel searchingItemsPanel = new JPanel();
        searchingItemsPanel.setLayout(new BorderLayout());

        JPanel searchingItemsInputPanel = initializeSearchingItemsInputPanel();
        searchingItemsPanel.add(searchingItemsInputPanel, BorderLayout.CENTER);

        this.searchItemButton = new JButton("Search for item");
        this.searchItemButton.addActionListener(this::searchItemHandler);
        this.searchItemButton.setEnabled(false);
        this.searchItemButton.setToolTipText("Not available till at least one item has been added to the list");
        searchingItemsPanel.add(this.searchItemButton, BorderLayout.PAGE_END);

        return searchingItemsPanel;
    }

    private JPanel initializeSearchingItemsInputPanel()
    {
        JPanel searchingItemsInputPanel = new JPanel();
        searchingItemsInputPanel.setLayout(new GridLayout(1, 2));

        JLabel searchingItemsLabel = new JLabel("Enter a string to search for its placement:");
        searchingItemsInputPanel.add(searchingItemsLabel);

        searchingItemsInputPanel.add(this.searchItemInput);

        return searchingItemsInputPanel;
    }

    private void searchItemHandler(ActionEvent e)
    {
        String searchedItem = this.searchItemInput.getText();
        if(!searchedItem.isBlank())
        {
            this.searchItemInput.setText("");

            int itemIndex = this.sortedList.binarySearchToPlaceString(searchedItem);
            String currentItemAtIndex = this.sortedList.getIndex(itemIndex);

            StyledDocument doc = listDisplayTextPane.getStyledDocument();

            try {
                // Item is already in the list
                if(currentItemAtIndex.equals(searchedItem))
                {
                    doc.insertString(doc.getLength(), "Item '" + searchedItem + "' was in list at index " + itemIndex + ":\n[", this.normalText);

                    for(String item : sortedList.getSortedList())
                    {
                        if(item.equalsIgnoreCase(searchedItem))
                        {
                            doc.insertString(doc.getLength(), item, this.attentionGrabbing);
                        }
                        else
                        {
                            doc.insertString(doc.getLength(), item, this.normalText);
                        }
                        doc.insertString(doc.getLength(), ", ", this.normalText);
                    }
                }
                else
                {
                    String msgStart = "Item '" + searchedItem + "' was not in list, if it were added it would be at index  " + itemIndex + ":\n[";
                    doc.insertString(doc.getLength(), msgStart, this.normalText);

                    int i = 0;
                    for(String item : sortedList.getSortedList())
                    {
                        if(i == itemIndex)
                        {
                            doc.insertString(doc.getLength(), "<Here>", this.attentionGrabbing);
                            doc.insertString(doc.getLength(), ", ", this.normalText);
                        }
                        doc.insertString(doc.getLength(), item + ", ", this.normalText);
                        i++;
                    }
                }

                // remove trailing ", "
                doc.remove(doc.getLength() - 2, 2);
                doc.insertString(doc.getLength(), "]\n\n", this.normalText);
            }
            catch(BadLocationException _)
            {
                // This shouldn't be an issue since all the inserts are based on the length of the document
            }
        }
    }

    private JScrollPane initializeResultsScrollArea()
    {
        this.listDisplayTextPane.setEditable(false);

        return new JScrollPane
        (
            this.listDisplayTextPane,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
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
