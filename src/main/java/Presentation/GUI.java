package Presentation;

import Domain.CheckType;
import Domain.Checks.CheckFactory;
import Domain.Linter;
import Domain.PresentationInformation;
import Domain.UserOptions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GUI extends UI  {

    private List<JCheckBox> checkBoxes = new ArrayList<>();
    private JTextField directoryField = new JTextField(25);
    private JButton selectAll;
    private JButton confirmChecks;

    public GUI(Linter linter, Map<String, Object> config) {
        super(linter, config);
        this.linter = linter;
        this.config = config;
        setDirectoryFromConfig();
    }

    @Override
    public void UIMain() {
        JFrame frame = new JFrame("ASM Java Linter");
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        frame.setSize(new Dimension(600,450));
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(3, 1, 0, 8));
        createInitialText(textPanel);
        JPanel checksPanel = new JPanel();
        checksPanel.setLayout(new GridLayout(calculateNumRows(), 2));
        createChecksForUI(checksPanel);
        frame.add(textPanel);
        frame.add(checksPanel);
        frame.add(createButtonPanel());
        frame.add(createFileChooserPanel());
        frame.add(createRunLinterPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setChecksFromConfig();
        frame.setVisible(true);
    }

    private int calculateNumRows() {
        int numChecks = CheckType.values().length;
        return numChecks / 2;
    }

    private void setDirectoryFromConfig() {
        String directory = (String) config.get("directory");
        directoryField.setText(directory);
    }

    public void setChecksFromConfig() {
        if(config.get("checks") instanceof LinkedHashMap) {
            LinkedHashMap<String, Boolean> checkMap = (LinkedHashMap<String, Boolean>) config.get("checks");
            for (String check : checkMap.keySet()) {
                if(checkMap.get(check)) {
                    for (JCheckBox checkBox : checkBoxes) {
                        if (checkBox.getText().equals(check))
                            checkBox.setSelected(true);
                    }
                }
            }
        }
    }

    private JPanel createRunLinterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton runLinterButton = new JButton("Run Linter");
        panel.add(runLinterButton, BorderLayout.CENTER);
        runLinterButton.addActionListener(getRunLinterListener());
        return panel;
    }

    private void createInitialText(JPanel panel) {
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        JLabel welcomeLabel = new JLabel("Welcome to the ASM Java Linter!");
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel checksLabel = new JLabel("Please select the checks you would like to run", JLabel.CENTER);
        checksLabel.setHorizontalAlignment(JLabel.CENTER);

        panel.add(welcomeLabel);
        panel.add(separator);
        panel.add(checksLabel);
    }

    public void createChecksForUI(JPanel panel) {
        for(CheckType checkType : CheckType.values()) {
            JCheckBox checkBox = new JCheckBox(checkType.name());
            checkBoxes.add(checkBox);
            panel.add(checkBox);
        }
    }

    private JPanel createFileChooserPanel() {
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        JPanel panel = new JPanel(new BorderLayout());
        JPanel textPanel = new JPanel(new FlowLayout());
        JLabel label = new JLabel("Select directory containing .class files");
        JButton lookupButton = new JButton("Lookup a Directory");
        lookupButton.addActionListener(createFileChooserListener(directoryField));
        label.setHorizontalAlignment(JLabel.CENTER);
        panel.add(separator, BorderLayout.NORTH);
        panel.add(label, BorderLayout.CENTER);
        textPanel.add(directoryField);
        textPanel.add(lookupButton);
        panel.add(textPanel, BorderLayout.SOUTH);
        return panel;
    }

    public JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        selectAll = new JButton("Select All Checks");
        selectAll.addActionListener(getSelectAllListener());
        confirmChecks = new JButton("Confirm Selected Checks");
        confirmChecks.addActionListener(getConfirmChecksListener());
        panel.add(selectAll);
        panel.add(confirmChecks);
        return panel;
    }

    @Override
    protected void initializeAvailableChecks() {
        if(checkBoxes != null) {
            for (JCheckBox checkBox : checkBoxes) {
                if (checkBox.isSelected()) {
                    CheckType checkType = CheckType.valueOf(checkBox.getText());
                    registerCheck(checkType, CheckFactory.getCheck(checkType));
                }
            }
        }
        linter.defineAvailableChecks(checkComposition);
    }

    @Override
    public UserOptions getUserOptions(BufferedReader reader) throws IOException {
        UserOptions userOptions = new UserOptions();
        String[] options = {"Yes", "No"};

        for(JCheckBox checkBox : checkBoxes) {
            if (checkBox.getText().equals("SingleResponsibilityPrinciple") && checkBox.isSelected()){
                String response = JOptionPane.showInputDialog(null, "What would you like to set as your maximum amount of public methods?", "default");
                if(!response.equals("default"))
                    userOptions.defineMaxMethods(Integer.parseInt(response));
            }

            if(checkBox.getText().equals("PoorNamingConvention") && checkBox.isSelected()){
                int response = getOptionsDialog("Would you like to autocorrect the names of classes/fields/methods?", options);
                if(response == 0)
                    userOptions.doAutoCorrect();
            }
        }
        int response = getOptionsDialog("Would you like to generate output in a text file?", options);
        if(response == 0) {
            String dir = JOptionPane.showInputDialog(null, "What is the full path to the directory you would like the report to be placed in?");
            userOptions.saveOutput(dir);
        }

        response = getOptionsDialog("Would you like to generate a uml?", options);
        if(response == 0) {
            String dir = JOptionPane.showInputDialog(null, "What is the full path to the directory you would like the uml image and text to be placed in?");
            userOptions.doUMLParse(dir);
        }
        return userOptions;
    }

    private ActionListener getSelectAllListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(JCheckBox checkBox : checkBoxes) {
                    checkBox.setSelected(!checkBox.isSelected());
                }
            }
        };
    }

    private ActionListener createFileChooserListener(JTextField textField) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Choose a directory containing .class files to run the linter on: ");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = fileChooser.showDialog(null, "Select Directory");
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    textField.setText(fileChooser.getSelectedFile().getPath());
                }
            }
        };
    }

    private int getOptionsDialog(String message, String[] options) {
        return JOptionPane.showOptionDialog(null, message, "",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    }

    private ActionListener getConfirmChecksListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initializeAvailableChecks();
            }
        };
    }

    private ActionListener getRunLinterListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(availableChecks.size() > 0) {
                        UserOptions userOptions = getUserOptions(null);
                        List<PresentationInformation> results = linter.runChecks(availableChecks, directoryField.getText(), userOptions);
                        displayResults(results);
                    }else
                        JOptionPane.showMessageDialog(null, "ERROR: Please ensure that you have selected checks and pushed the 'Confirm Selected Checks' button!");

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    private void displayResults(List<PresentationInformation> results) {
        JFrame frame = new JFrame("Linter Results");
        frame.setSize(new Dimension(600,600));
        frame.setBounds(601, 0, 600, 600);
        JTextArea display = new JTextArea(1, 25);
        JScrollPane scrollPane = new JScrollPane(display);
        display.setLineWrap(true);
        display.setEditable(false);
        frame.add(scrollPane);
        display.setVisible(true);
        for(PresentationInformation info : results) {
            display.append(info.returnUIMessage() + "\n");
            display.append(info.printToDisplay() + "\n");
        }
        frame.setVisible(true);
    }

    public String getDirectoryTextField() {
        return directoryField.getText();
    }

    public List<JCheckBox> getCheckBoxes() {
        return checkBoxes;
    }

    public JButton getSelectAllButton() {
        return selectAll;
    }

    public JButton getConfirmChecksButton() {
        return confirmChecks;
    }

    public List<CheckType> getSelectedChecks() {
        return availableChecks;
    }
}
