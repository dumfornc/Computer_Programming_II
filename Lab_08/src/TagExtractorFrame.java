import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.TreeMap;
import java.util.Map;

public class TagExtractorFrame extends JFrame
{
    private TagExtractor tagExtractor;

    private JFileChooser tagFileChooser;
    private JFileChooser stopWordFileChooser;

    private Boolean validTagFile = false;
    private Boolean validStopWordFile = false;

    private JButton tagFileChooserButton;
    private JButton stopWordFileChooserButton;

    private JButton saveButton;

    private JTextArea tagsDisplayTextArea;

    private JSpinner minimumTagAppearanceThresholdSpinner;

    public TagExtractorFrame()
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

        setTitle("Tag Extractor");

        initializeFilePickers();
        initializeUI();
    }

    private void initializeFilePickers()
    {
        FileNameExtensionFilter textFileFilter = new FileNameExtensionFilter("Text files", "txt");

        this.tagFileChooser = new JFileChooser();
        this.tagFileChooser.addChoosableFileFilter(textFileFilter);

        this.stopWordFileChooser = new JFileChooser();
        this.stopWordFileChooser.addChoosableFileFilter(textFileFilter);
    }

    private void initializeUI()
    {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel filePickersPanel = initializeFilePickersPanel();
        mainPanel.add(filePickersPanel, BorderLayout.PAGE_START);

        JPanel tagsDisplayPanel = initializeTagsDisplayPanel();
        mainPanel.add(tagsDisplayPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = initializeButtonsPanel();
        mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);

        this.add(mainPanel);
    }

    private JPanel initializeFilePickersPanel()
    {
        JPanel filePickersPanel= new JPanel();
        filePickersPanel.setLayout(new GridLayout(2, 2));

        this.tagFileChooserButton = new JButton("Select a file to extract tags from");
        this.tagFileChooserButton.addActionListener(this::selectTagExtractionFile);
        filePickersPanel.add(this.tagFileChooserButton);

        JPanel tagFrequencyThresholdPanel = initializeTagFrequencyThresholdPanel();
        filePickersPanel.add(tagFrequencyThresholdPanel);

        this.stopWordFileChooserButton = new JButton("Select a stop words file");
        this.stopWordFileChooserButton.addActionListener(this::selectStopWordFile);
        filePickersPanel.add(this.stopWordFileChooserButton);

        JButton getTagsButton = new JButton("Extract tags");
        getTagsButton.addActionListener(this::extractTags);
        filePickersPanel.add(getTagsButton);

        return filePickersPanel;
    }

    private JPanel initializeTagFrequencyThresholdPanel()
    {
        JPanel tagFrequencyThresholdPanel = new JPanel();
        tagFrequencyThresholdPanel.setLayout(new GridLayout(1, 2));

        JLabel tagFrequencyThresholdLabel = new JLabel(" Minimum word appearances threshold: ");
        tagFrequencyThresholdPanel.add(tagFrequencyThresholdLabel);

        minimumTagAppearanceThresholdSpinner = new JSpinner(new SpinnerNumberModel(5 ,1, 999, 1));
        minimumTagAppearanceThresholdSpinner.setEditor(new JSpinner.NumberEditor(minimumTagAppearanceThresholdSpinner, "###"));
        minimumTagAppearanceThresholdSpinner.setToolTipText("Words that appear less times than the value set will not be counted as tags");
        tagFrequencyThresholdPanel.add(minimumTagAppearanceThresholdSpinner);

        return tagFrequencyThresholdPanel;
    }

    private void selectTagExtractionFile(ActionEvent e)
    {
        int fileSelectionResult = this.tagFileChooser.showOpenDialog(this);

        if(fileSelectionResult == JFileChooser.APPROVE_OPTION)
        {
            validTagFile = true;
            String newTagFileText =
                    "Selected tag extraction file: <" +
                    this.tagFileChooser.getSelectedFile().getName() +
                    ">";
            tagFileChooserButton.setText(newTagFileText);
        }
    }

    private void selectStopWordFile(ActionEvent e)
    {
        int fileSelectionResult = this.stopWordFileChooser.showOpenDialog(this);

        if(fileSelectionResult == JFileChooser.APPROVE_OPTION)
        {
            validStopWordFile = true;
            String newStopWordFileText =
                    "Selected stop word file: <" +
                    this.stopWordFileChooser.getSelectedFile().getName() +
                    ">";
            stopWordFileChooserButton.setText(newStopWordFileText);

            try
            {
                this.tagExtractor = new TagExtractor(this.stopWordFileChooser.getSelectedFile());
            } catch (FileNotFoundException ex) {
                this.tagExtractor = null;
                this.validStopWordFile = false;

                JOptionPane.showMessageDialog(this, "Stop word file was invalid");
            }
        }
    }

    private void extractTags(ActionEvent e)
    {
        if(validTagFile && validStopWordFile)
        {
            try
            {
                TreeMap<String, Integer> tagResults = this.tagExtractor.getHighFrequencyTags
                (
                    this.tagFileChooser.getSelectedFile(),
                    (Integer) this.minimumTagAppearanceThresholdSpinner.getValue()
                );

                // Clears the tag results before looping and adding all the tags
                this.tagsDisplayTextArea.setText("");

                for(Map.Entry<String, Integer> entry : tagResults.entrySet())
                {
                    this.tagsDisplayTextArea.append(entry.getKey() + ": " + entry.getValue() + "\n");
                }

                this.saveButton.setEnabled(true);
            }
            catch (java.io.FileNotFoundException ex)
            {
                this.validTagFile = false;
                JOptionPane.showMessageDialog(this, "Tag extraction file was invalid");
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Please select a tag extraction file and a stop word file to extract tags.");
        }
    }

    private JPanel initializeTagsDisplayPanel()
    {
        JPanel tagsDisplayPanel = new JPanel();
        tagsDisplayPanel.setLayout(new BorderLayout());

        this.tagsDisplayTextArea = new JTextArea();
        this.tagsDisplayTextArea.setEditable(false);

        JScrollPane tagDisplayScroller = new JScrollPane(this.tagsDisplayTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tagsDisplayPanel.add(tagDisplayScroller, BorderLayout.CENTER);

        return tagsDisplayPanel;
    }

    private JPanel initializeButtonsPanel()
    {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2));

        this.saveButton = new JButton("Save tags to file");
        this.saveButton.setEnabled(false);
        this.saveButton.addActionListener(this::saveResultsToFile);
        buttonsPanel.add(this.saveButton);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(this::quitButtonHandler);
        buttonsPanel.add(quitButton);

        return buttonsPanel;
    }

    private void saveResultsToFile(ActionEvent e)
    {
        JFileChooser saveFileChooser = new JFileChooser();

        int fileSaveResult = saveFileChooser.showSaveDialog(this);
        if(fileSaveResult == JFileChooser.APPROVE_OPTION)
        {
            try
            {
                Files.writeString(saveFileChooser.getSelectedFile().toPath(), this.tagsDisplayTextArea.getText());
            } catch (IOException ex)
            {
                JOptionPane.showMessageDialog(this, "An error occurred while saving: " + ex.toString());
            }
        }
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
