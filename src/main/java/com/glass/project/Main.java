package com.glass.project;

import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Main {
    private JFrame mainFrame;
    private JTextField syntaxFileField;
    private JTextField inputFileField;
    private JTextField macroFileField;
    private JTextArea resultArea;

    private Color backgroundColor = new Color(20, 20, 20);
    private Color accentColor = new Color(64, 224, 208);
    private Color textColor = Color.WHITE;

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.prepareGUI();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Language Parser");
        mainFrame.setSize(500, 400);
        mainFrame.setLayout(new GridLayout(6, 2, 10, 10));
        mainFrame.getContentPane().setBackground(backgroundColor);

        JLabel syntaxFileLabel = createCustomLabel("Syntax Definition File: ");
        syntaxFileField = createCustomTextField();
        JButton syntaxFileButton = createCustomButton("Select File");

        JLabel inputFileLabel = createCustomLabel("Input File: ");
        inputFileField = createCustomTextField();
        JButton inputFileButton = createCustomButton("Select File");

        JLabel macroFileLabel = createCustomLabel("Macro File: ");
        macroFileField = createCustomTextField();
        JButton macroFileButton = createCustomButton("Select File");

        JButton parseButton = createCustomButton("Run Parser");
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setBackground(backgroundColor);
        resultArea.setForeground(textColor);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 14));
        resultArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        mainFrame.add(syntaxFileLabel);
        mainFrame.add(syntaxFileField);
        mainFrame.add(syntaxFileButton);
        mainFrame.add(inputFileLabel);
        mainFrame.add(inputFileField);
        mainFrame.add(inputFileButton);
        mainFrame.add(macroFileLabel);
        mainFrame.add(macroFileField);
        mainFrame.add(macroFileButton);
        mainFrame.add(parseButton);
        mainFrame.add(new JLabel());
        mainFrame.add(new JScrollPane(resultArea));

        syntaxFileButton.addActionListener(e -> {
            String filePath = selectFile();
            if (filePath != null) {
                syntaxFileField.setText(filePath);
            }
        });

        inputFileButton.addActionListener(e -> {
            String filePath = selectFile();
            if (filePath != null) {
                inputFileField.setText(filePath);
            }
        });

        macroFileButton.addActionListener(e -> {
            String filePath = selectFile();
            if (filePath != null) {
                macroFileField.setText(filePath);
            }
        });

        parseButton.addActionListener(e -> {
            try {
                runParser();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }

    private String selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        return null;
    }

    private void runParser() throws IOException {
        String syntaxFilePath = syntaxFileField.getText();
        String inputFilePath = inputFileField.getText();
        String macroFilePath = macroFileField.getText();

        if (syntaxFilePath.isEmpty() || inputFilePath.isEmpty() || macroFilePath.isEmpty()) {
            resultArea.setText("Please fill in all fields.");
            return;
        }

        try {
            LanguageParser parser = API.buildParserFromFile(syntaxFilePath);
            ParserNode rootNode = API.parseFile(inputFilePath, parser);
            ParserNode modifiedRoot = API.applyMacroFromPath(rootNode, macroFilePath);
            resultArea.setText(modifiedRoot.getRawString());
        } catch (Exception ex) {
            resultArea.setText("Error: " + ex.getMessage());
        }
    }

    private JLabel createCustomLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(textColor);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    private JTextField createCustomTextField() {
        JTextField textField = new JTextField(20);
        textField.setBackground(new Color(40, 40, 40));
        textField.setForeground(textColor);
        textField.setBorder(BorderFactory.createLineBorder(accentColor));
        return textField;
    }

    private JButton createCustomButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(accentColor);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createLineBorder(accentColor));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(72, 209, 204));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(accentColor);
            }
        });

        return button;
    }
}
