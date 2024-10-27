package com.glass.project;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;



public class Main {
    enum Screen {
        MAIN,
        EXECUTION,
        GRAMMAR
    }

    public static JFrame mainFrame = new JFrame("GLASS GUI");
    public static Screen state;
    public static HashSet<String> nonTerminalList = new HashSet<String>();
    public static HashSet<Terminal> terminalList = new HashSet<Terminal>(); 
    public static HashSet<String> terminalStringList = new HashSet<String>();
    public static List<Production> productionList = new ArrayList<Production>();
    public static JTextArea productionTreeWindow = new JTextArea("");
    public static List<JComboBox> rightSideList = new ArrayList<JComboBox>();
    public static ImageIcon originalIcon = new ImageIcon(Paths.get("").toAbsolutePath() + "/glass-gui/src/main/java/com/glass/project/glass-logo.png");
    public static Image scaledImage = originalIcon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH); // Increase size slightly
    public static ImageIcon glassIcon = new ImageIcon(scaledImage);


    public static void main(String[] args) {
        loadMainWindow();
        System.out.println(Paths.get("").toAbsolutePath());
        mainFrame.setSize(500, 500);
        mainFrame.setIconImage(originalIcon.getImage());
        state = Screen.MAIN;
        mainFrame.setVisible(true);
    }

    public static void loadMainWindow() {    
        JLabel mainMenuIcon = new JLabel(glassIcon);
        JLabel mainMenuLabel = new JLabel("GLASS", SwingConstants.LEFT);
        mainMenuLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainMenuLabel.setForeground(Color.WHITE);
    
        JPanel topLogoPanel = new JPanel();
        topLogoPanel.setLayout(new GridLayout(1, 2));
        topLogoPanel.setBackground(Color.BLACK);
        topLogoPanel.add(mainMenuIcon);
        topLogoPanel.add(mainMenuLabel);
    
        JButton executeButton = new JButton("Execution Window");
        executeButton.addActionListener(e -> changeState(Screen.EXECUTION));
        executeButton.setBackground(Color.BLACK);
        executeButton.setForeground(Color.WHITE);
    
        JButton grammarCreation = new JButton("Grammar Creation Window");
        grammarCreation.addActionListener(e -> changeState(Screen.GRAMMAR));
        grammarCreation.setBackground(Color.BLACK);
        grammarCreation.setForeground(Color.WHITE);
    
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        JPanel mainPanel = new JPanel(new GridLayout(2, 1));
        buttonPanel.add(executeButton);
        buttonPanel.add(grammarCreation);
    
        mainPanel.add(topLogoPanel);
        mainPanel.add(buttonPanel);
        mainFrame.add(mainPanel);
    }

    public static void loadExecutionWindow() {
    JPanel topPanel = new JPanel(new GridLayout(1, 2));
    JTextArea parseTreeArea = new JTextArea();
    parseTreeArea.setBackground(Color.BLACK);
    parseTreeArea.setForeground(Color.WHITE);
    topPanel.setBackground(Color.BLACK);
    JPanel mainButtonPanel = new JPanel(new GridLayout(3, 1));
    mainButtonPanel.setBackground(Color.BLACK);
    JPanel executionPanel = new JPanel(new GridLayout(3, 1));
    executionPanel.setBackground(Color.BLACK);

    JPanel inputPanel = new JPanel(new GridLayout(1, 3)); 
    inputPanel.setBackground(Color.BLACK);
    JLabel inputLabel = new JLabel("Enter Input File Path");
    inputLabel.setBackground(Color.BLACK);
    inputLabel.setForeground(Color.WHITE);
    JTextField inputField = new JTextField();
    inputField.setBackground(Color.BLACK);
    inputField.setForeground(Color.WHITE);
    JButton inputButton = new JButton("Select Input File");
    inputButton.setBackground(Color.BLACK);
    inputButton.setForeground(Color.WHITE);
    inputButton.addActionListener(e -> chooseFile(inputField));
    inputPanel.add(inputLabel);
    inputPanel.add(inputField);
    inputPanel.add(inputButton);

    JPanel grammarPanel = new JPanel(new GridLayout(1, 3)); 
    grammarPanel.setBackground(Color.BLACK);
    grammarPanel.setForeground(Color.WHITE);
    JLabel grammarLabel = new JLabel("Enter Grammar File Path");
    grammarLabel.setBackground(Color.BLACK);
    grammarLabel.setForeground(Color.WHITE);
    JTextField grammarField = new JTextField();
    grammarField.setBackground(Color.BLACK);
    grammarField.setForeground(Color.WHITE);
    JButton grammarButton = new JButton("Select Grammar File");
    grammarButton.setBackground(Color.BLACK);
    grammarButton.setForeground(Color.WHITE);
    grammarButton.addActionListener(e -> chooseFile(grammarField));
    grammarPanel.add(grammarLabel);
    grammarPanel.add(grammarField);
    grammarPanel.add(grammarButton);

    JPanel macroPanel = new JPanel(new GridLayout(1, 3)); 
    macroPanel.setBackground(Color.BLACK);
    JLabel macroLabel = new JLabel("Enter Macro File Path");
    macroLabel.setBackground(Color.BLACK);
    macroLabel.setForeground(Color.WHITE);
    JTextField macroField = new JTextField();
    macroField.setBackground(Color.BLACK);
    macroField.setForeground(Color.WHITE);
    JButton macroButton = new JButton("Select Macro File");
    macroButton.setBackground(Color.BLACK);
    macroButton.setForeground(Color.WHITE);
    JTextField outputTextField = new JTextField();
    outputTextField.setBackground(Color.BLACK);
    outputTextField.setForeground(Color.WHITE);
    JButton submitButton = new JButton("Submit");
    submitButton.setBackground(Color.BLACK);
    submitButton.setForeground(Color.WHITE);
    JButton backButton = new JButton("Back");
    backButton.setBackground(Color.BLACK);
    backButton.setForeground(Color.WHITE);
    backButton.addActionListener(e -> changeState(Screen.MAIN));
    macroButton.addActionListener(e -> chooseFile(macroField));
    submitButton.addActionListener(e -> execute(inputField, grammarField, macroField, parseTreeArea));
    macroPanel.add(macroLabel);
    macroPanel.add(macroField);
    macroPanel.add(macroButton);
    executionPanel.add(inputPanel);
    executionPanel.add(grammarPanel);
    executionPanel.add(macroPanel);
    mainButtonPanel.add(executionPanel);
    mainButtonPanel.add(submitButton);
    mainButtonPanel.add(backButton);
    topPanel.add(mainButtonPanel);
    topPanel.add(outputTextField);
    mainFrame.add(topPanel);
    mainFrame.revalidate();
    mainFrame.repaint();
}

public static void execute(JTextField inputField, JTextField grammarField, JTextField macroField, JTextArea outputField) {
    if ((inputField.getText() != "") && (grammarField.getText() != "")) {
        String inputPath = inputField.getText();
        String grammarPath = grammarField.getText();
        LanguageParser grammarParser = null;
        ParserNode rootNode = null;
        try {
            grammarParser = API.buildParserFromFile(grammarPath);
        } catch (IOException e) {
            outputField.setText("Invalid Path: " + grammarPath);
        }

        if (grammarParser != null) {
            try {
                rootNode = API.parseFile(inputPath, grammarParser);
                if (macroField.getText() == "") {
                    outputField.setText(writeParseTree(rootNode));
                } else {
                    String macroPath = macroField.getText();
                    try {
                        ParserNode macroRootNode = API.applyMacroFromPath(rootNode, macroPath);
                        outputField.setText(writeParseTree(macroRootNode));
                    } catch (IOException e) {
                        outputField.setText("Invalid Path: " + macroPath);
                    }
                }
            } catch (IOException e) {
                outputField.setText("Invalid Path: " + inputPath);
            }
        }
    }
}

public static String writeParseTree(ParserNode rootNode) {
    return rootNode.toString();
}

public static void chooseFile(JTextField textField) {
    JFileChooser fileChooser = new JFileChooser();
    int result = fileChooser.showOpenDialog(null);
    if (result == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        textField.setText(selectedFile.getAbsolutePath());
    }
}

public static void saveFile(JTextArea grammar) {
    JFileChooser fileChooser = new JFileChooser();
    int result = fileChooser.showSaveDialog(null);
    if (result == JFileChooser.APPROVE_OPTION) {
        File newFile = fileChooser.getSelectedFile();
        try {
            FileWriter fileWriter = new FileWriter(newFile);
            fileWriter.write("tokens {\n    active {");
            for (Terminal terminal: terminalList) {
                fileWriter.write(String.format("\n      %s : %s", terminal.getIdentifier(), terminal.getRegex()));
            }
            fileWriter.write("\n    }\n}");
            fileWriter.write("\nproductions {\n");
            fileWriter.write(grammar.getText());
            fileWriter.write("\n}");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error saving file");
        }
    }
}

public static void loadGrammarWindow() {
    JPanel topPanel = new JPanel(new GridLayout(2, 1));
    JPanel lowerButtonPanel = new JPanel(new GridLayout(1, 2));
    lowerButtonPanel.setBackground(Color.BLACK);
    lowerButtonPanel.setForeground(Color.WHITE);
    JButton writeFileButton = new JButton("Write Grammar to File");
    writeFileButton.setBackground(Color.BLACK);
    writeFileButton.setForeground(Color.WHITE);
    topPanel.setBackground(Color.BLACK);

    JPanel topGrammarPanel = new JPanel(new GridLayout(1, 2));
    topGrammarPanel.setBackground(Color.BLACK);

    JButton createProductionButton = new JButton("Create Production");
    createProductionButton.setBackground(Color.BLACK);
    createProductionButton.setForeground(Color.WHITE);
    createProductionButton.addActionListener(e -> createProduction());

    JButton createNonTerminalButton = new JButton("Create Non Terminal");
    createNonTerminalButton.setBackground(Color.BLACK);
    createNonTerminalButton.setForeground(Color.WHITE);
    createNonTerminalButton.addActionListener(e -> createNonTerminal());

    JButton createTerminalButton = new JButton("Create Terminal");
    createTerminalButton.setBackground(Color.BLACK);
    createTerminalButton.setForeground(Color.WHITE);
    createTerminalButton.addActionListener(e -> createTerminal());

    JPanel grammarButtonPanel = new JPanel(new GridLayout(3, 1));
    grammarButtonPanel.setBackground(Color.BLACK);
    grammarButtonPanel.add(createNonTerminalButton);
    grammarButtonPanel.add(createTerminalButton);
    grammarButtonPanel.add(createProductionButton);

    topGrammarPanel.add(grammarButtonPanel);

    productionTreeWindow.setBackground(Color.DARK_GRAY);
    productionTreeWindow.setForeground(Color.WHITE);
    topGrammarPanel.add(productionTreeWindow);
    writeFileButton.addActionListener(e -> saveFile(productionTreeWindow));

    JButton backButton = new JButton("Back");
    backButton.setBackground(Color.BLACK);
    backButton.setForeground(Color.WHITE);
    backButton.addActionListener(e -> changeState(Screen.MAIN));

    topPanel.add(topGrammarPanel);
    lowerButtonPanel.add(backButton);
    lowerButtonPanel.add(writeFileButton);
    topPanel.add(lowerButtonPanel);

    mainFrame.getContentPane().removeAll();
    mainFrame.add(topPanel);
    mainFrame.revalidate();
    mainFrame.repaint();
}

    public static void createTerminal() {
        JFrame createTerminalFormFrame = new JFrame("Create Terminal");
        createTerminalFormFrame.setIconImage(originalIcon.getImage());
    
        JPanel mainPanel = new JPanel(new GridLayout(2, 1));
        mainPanel.setBackground(Color.BLACK);
    
        JPanel lowerButtonPanel = new JPanel(new GridLayout(1, 2));
        lowerButtonPanel.setBackground(Color.BLACK);
    
        JButton submitButton = new JButton("Create");
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.WHITE);
    
        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> closeWindow(createTerminalFormFrame));
    
        lowerButtonPanel.add(submitButton);
        lowerButtonPanel.add(backButton);
    
        JPanel inputPanel = new JPanel(new GridLayout(1, 4));
        inputPanel.setBackground(Color.BLACK);
    
        JTextField inputField = new JTextField();
        inputField.setBackground(Color.DARK_GRAY);
        inputField.setForeground(Color.WHITE);

        JLabel regexInputLabel = new JLabel("Regex: ");
        regexInputLabel.setForeground(Color.WHITE);

        JTextField regexField = new JTextField();
        regexField.setBackground(Color.DARK_GRAY);
        regexField.setForeground(Color.WHITE);
    
        JLabel inputLabel = new JLabel("Name:");
        inputLabel.setForeground(Color.WHITE);
    
        submitButton.addActionListener(e -> addSymbolToTerminalList(inputField.getText(), regexField.getText(), createTerminalFormFrame));
    
        inputPanel.add(inputLabel);
        inputPanel.add(inputField);
        inputPanel.add(regexInputLabel);
        inputPanel.add(regexField);
        mainPanel.add(inputPanel);
        mainPanel.add(lowerButtonPanel);
    
        createTerminalFormFrame.add(mainPanel);
        createTerminalFormFrame.setSize(500, 500);
        createTerminalFormFrame.getContentPane().setBackground(Color.BLACK);  // Set frame background to black
        createTerminalFormFrame.setVisible(true);
    }
    

    public static void closeWindow(JFrame window) {
        window.setVisible(false);
        window.dispose();
    }

    public static void addSymbolToTerminalList(String identifier, String regex, JFrame formFrame) {
        if ((identifier != "") && (regex != "") && (!terminalStringList.contains(identifier))) {
            if (!regex.startsWith("/") && !regex.endsWith("/")) {
                regex = String.format("/%s/", regex);
            }
            terminalList.add(new Terminal(identifier, regex));
            terminalStringList.add(identifier);
        }
        closeWindow(formFrame);
    }

    public static void addSymbolToNonTerminalList(String text, JFrame formFrame) {
        if (text != "") {
            nonTerminalList.add(text);
        }
        closeWindow(formFrame);
    }

    public static void addToProductionList(String leftSide, JFrame formFrame) {
        List<String> rightSideListStr = new ArrayList<>();
        for (JComboBox comboBox: rightSideList) {
            Object boxItem = comboBox.getSelectedItem();
            if (boxItem instanceof String s) {
                rightSideListStr.add(s);
            }
        }

        productionList.add(new Production(leftSide, rightSideListStr));
        writeProductionTree();
        rightSideList = new ArrayList<JComboBox>();
        closeWindow(formFrame);
    }

    public static void createNonTerminal() {
        JFrame createNonTerminalFormFrame = new JFrame("Create Non Terminal");
        createNonTerminalFormFrame.setIconImage(originalIcon.getImage());
    
        JPanel mainPanel = new JPanel(new GridLayout(2, 1));
        mainPanel.setBackground(Color.BLACK);
        
        JPanel lowerButtonPanel = new JPanel(new GridLayout(1, 2));
        lowerButtonPanel.setBackground(Color.BLACK);
    
        JButton submitButton = new JButton("Create");
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.WHITE);
    
        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> closeWindow(createNonTerminalFormFrame));
    
        lowerButtonPanel.add(submitButton);
        lowerButtonPanel.add(backButton);
    
        JPanel inputPanel = new JPanel(new GridLayout(1, 2));
        inputPanel.setBackground(Color.BLACK);
    
        JTextField inputField = new JTextField();
        inputField.setBackground(Color.DARK_GRAY);
        inputField.setForeground(Color.WHITE);
        
        JLabel inputLabel = new JLabel("Name:");
        inputLabel.setForeground(Color.WHITE);
    
        submitButton.addActionListener(e -> addSymbolToNonTerminalList(inputField.getText(), createNonTerminalFormFrame));
    
        inputPanel.add(inputLabel);
        inputPanel.add(inputField);
        mainPanel.add(inputPanel);
        mainPanel.add(lowerButtonPanel);
    
        createNonTerminalFormFrame.add(mainPanel);
        createNonTerminalFormFrame.setSize(500, 500);
        createNonTerminalFormFrame.getContentPane().setBackground(Color.BLACK);  // Set frame background to black
        createNonTerminalFormFrame.setVisible(true);
    }
    

    public static void createProduction() {
    JFrame createProductionFormFrame = new JFrame("Create Production");
    createProductionFormFrame.setIconImage(originalIcon.getImage());
    
    JPanel mainPanel = new JPanel(new GridLayout(2, 1));
    mainPanel.setBackground(Color.BLACK);
    
    JPanel lowerButtonPanel = new JPanel(new GridLayout(1, 2));
    lowerButtonPanel.setBackground(Color.BLACK);
    
    JButton submitButton = new JButton("Create");
    submitButton.setBackground(Color.BLACK);
    submitButton.setForeground(Color.WHITE);
    
    JButton backButton = new JButton("Back");
    backButton.setBackground(Color.BLACK);
    backButton.setForeground(Color.WHITE);
    backButton.addActionListener(e -> closeWindow(createProductionFormFrame));
    
    lowerButtonPanel.add(submitButton);
    lowerButtonPanel.add(backButton);
    
    JPanel upperCreationPanel = new JPanel(new GridLayout(1, 0));
    upperCreationPanel.setBackground(Color.BLACK);
    
    JComboBox<String> leftSideDropDownBox = new JComboBox<>();
    leftSideDropDownBox.setBackground(Color.BLACK);
    leftSideDropDownBox.setForeground(Color.WHITE);
    
    JLabel separatorLabel = new JLabel("   ->   ");
    separatorLabel.setFont(new Font("Arial", Font.BOLD, 20));
    separatorLabel.setForeground(Color.WHITE);
    separatorLabel.setBackground(Color.BLACK);
    
    JComboBox<String> rightSideDropDownBox = createRightSideComboBox();
    rightSideDropDownBox.setBackground(Color.BLACK);
    rightSideDropDownBox.setForeground(Color.WHITE);
    
    for (String symbol : nonTerminalList) {
        leftSideDropDownBox.addItem(symbol);
    }
    
    JButton addButton = new JButton("Add Symbol");
    addButton.setBackground(Color.BLACK);
    addButton.setForeground(Color.WHITE);
    
    upperCreationPanel.add(leftSideDropDownBox);
    upperCreationPanel.add(separatorLabel);
    upperCreationPanel.add(rightSideDropDownBox);
    addButton.addActionListener(e -> addRightSideSymbol(upperCreationPanel, createProductionFormFrame));
    upperCreationPanel.add(addButton);
    
    mainPanel.add(upperCreationPanel);
    mainPanel.add(lowerButtonPanel);
    
    submitButton.addActionListener(e -> addToProductionList((String) leftSideDropDownBox.getSelectedItem(), createProductionFormFrame));
    
    createProductionFormFrame.add(mainPanel);
    createProductionFormFrame.setSize(500, 500);
    createProductionFormFrame.getContentPane().setBackground(Color.BLACK);
    createProductionFormFrame.setVisible(true);
}


    public static void writeProductionTree() {
        StringBuilder result = new StringBuilder();
        for (Production prod: productionList) {
            result.append(String.format("   %s.", prod));
            result.append("\n");
        }
        productionTreeWindow.setText(result.toString());
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public static void addRightSideSymbol(JPanel panel, JFrame panelFrame) {
        panel.add(createRightSideComboBox(), panel.getComponentCount() - 1);
        panelFrame.revalidate();
        panelFrame.repaint();
    }

    public static JComboBox createRightSideComboBox() {
        JComboBox box = new JComboBox<String>();
        box.setBackground(Color.BLACK);
        box.setForeground(Color.WHITE);
        for (String symbol: nonTerminalList) {
            box.addItem(symbol);
        }
        for (String symbol: terminalStringList) {
            box.addItem(symbol);
        }
        rightSideList.add(box);
        return box;
    }

    public static void changeState(Screen screenState) {
        if (state != screenState) {
            loadScreen(screenState);
        }
    }

    public static void loadScreen(Screen screenState) {
        mainFrame.getContentPane().removeAll();
        if (screenState == Screen.EXECUTION) {
            loadExecutionWindow();
        } else if (screenState == Screen.GRAMMAR) {
            loadGrammarWindow();
        } else {
            loadMainWindow();
        }
        state = screenState;
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}