import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

public class SortedListMakerFrame extends JFrame
{
    private final SortedList sortedList = new SortedList();

    private final JTextArea listDisplayTextArea = new JTextArea();

    private final JTextField addItemInput = new JTextField();
    private final JTextField searchItemsInput = new JTextField();

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

        initializeUI();
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

//        JPanel searchingItemsPanel = initializeSearchingItemsPanel();
//        controlsPanel.add(searchingItemsPanel);

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

        JLabel addingItemsLabel = new JLabel("Enter a string to add to a sorted list");
        addingItemsInputPanel.add(addingItemsLabel);

        addingItemsInputPanel.add(this.addItemInput);

        return addingItemsInputPanel;
    }

    private void addItemHandler(ActionEvent e)
    {
        String addedItem = this.addItemInput.getText();
        if(!addedItem.isBlank())
        {
            this.addItemInput.setText("");

            this.sortedList.add(addedItem);

            StringBuilder addedItemMessage = new StringBuilder("Item '%s' added to list:\n[".formatted(addedItem));

            for(String item : this.sortedList.getSortedList())
            {
                addedItemMessage.append(item).append(", ");
            }

            // Removes last ", "
            addedItemMessage = new StringBuilder(addedItemMessage.substring(0, addedItemMessage.length() - 2));
            addedItemMessage.append("]\n\n");

            this.listDisplayTextArea.append(addedItemMessage.toString());
        }
    }

    private JScrollPane initializeResultsScrollArea()
    {
        this.listDisplayTextArea.setEditable(false);

        return new JScrollPane
        (
            this.listDisplayTextArea,
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
