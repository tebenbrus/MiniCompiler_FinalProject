import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;

public class MiniCompiler extends JFrame {
    private JTextArea codeTextArea, resultTextArea;
    private JButton openFileBtn, lexicalBtn, syntaxBtn, semanticBtn, clearBtn;
    private boolean lexicalPassed = false;
    private JLabel lblNewLabel, lblNewLabel_1;
    static JProgressBar progressBar;
    static JLabel label_1;

    public MiniCompiler() {
        setLookAndFeel();
        initializeUI();
        setupActions();
        runSplash();
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeUI() {
        setTitle("Combined Compiler");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        codeTextArea = new JTextArea();
        codeTextArea.setBackground(SystemColor.controlHighlight);
        codeTextArea.setTabSize(4);
        resultTextArea = new JTextArea();
        resultTextArea.setBackground(SystemColor.controlHighlight);
        resultTextArea.setEditable(false);

        openFileBtn = new JButton("Open File");
        openFileBtn.addMouseListener(new MouseAdapter() {


        });
        openFileBtn.setForeground(new Color(0, 0, 0));
        openFileBtn.setBackground(SystemColor.activeCaption);
        lexicalBtn = new JButton("Lexical Analysis");
        lexicalBtn.setForeground(SystemColor.desktop);
        lexicalBtn.setBackground(SystemColor.activeCaption);
        syntaxBtn = new JButton("Syntax Analysis");
        syntaxBtn.setForeground(SystemColor.desktop);
        syntaxBtn.setBackground(SystemColor.activeCaption);
        semanticBtn = new JButton("Semantic Analysis");
        semanticBtn.setForeground(SystemColor.desktop);
        semanticBtn.setBackground(SystemColor.activeCaption);
        clearBtn = new JButton("Clear");
        clearBtn.setForeground(SystemColor.desktop);
        clearBtn.setBackground(SystemColor.activeCaption);


        setAnalysisButtonsEnabled(false);

        getContentPane().setLayout(new BorderLayout(40, 0));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(openFileBtn);
        buttonPanel.add(lexicalBtn);
        buttonPanel.add(syntaxBtn);
        buttonPanel.add(semanticBtn);
        buttonPanel.add(clearBtn);

        getContentPane().add(buttonPanel, BorderLayout.NORTH);

        JPanel textAreasPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JScrollPane scrollPane = new JScrollPane(codeTextArea);
        textAreasPanel.add(scrollPane);
        lblNewLabel = new JLabel("Source Code");
        scrollPane.setColumnHeaderView(lblNewLabel);
        textAreasPanel.add(createResultPanel());

        getContentPane().add(textAreasPanel, BorderLayout.CENTER);
    }

    private JPanel createResultPanel() {
        JPanel resultPanel = new JPanel(new BorderLayout(5, 5));
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        resultPanel.add(scrollPane, BorderLayout.CENTER);
        lblNewLabel_1 = new JLabel("Result");
        scrollPane.setColumnHeaderView(lblNewLabel_1);
        return resultPanel;
    }

    private void setupActions() {
        openFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFileBtn.setBackground(Color.GRAY);
                openFile();
                openFileBtn.setBackground(SystemColor.activeCaption);
            }
        });
        lexicalBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFileBtn.setBackground(Color.GRAY);
                performLexicalAnalysis();
                openFileBtn.setBackground(SystemColor.activeCaption);
            }
        });

        syntaxBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFileBtn.setBackground(Color.GRAY);
                performSyntaxAnalysis();
                openFileBtn.setBackground(SystemColor.activeCaption);
            }
        });

        semanticBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFileBtn.setBackground(Color.GRAY);
                performSemanticAnalysis();
                openFileBtn.setBackground(SystemColor.activeCaption);
            }
        });


        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFileBtn.setBackground(Color.GRAY);
                clear();
                openFileBtn.setBackground(SystemColor.activeCaption);
            }
        });
        clearBtn.setEnabled(false);
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);



        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                reader.close();

                codeTextArea.setText(content.toString());
                setAnalysisButtonsEnabled(true);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            syntaxBtn.setEnabled(false);
            semanticBtn.setEnabled(false);
        }
    }


    private void setAnalysisButtonsEnabled(boolean enabled) {
        lexicalBtn.setEnabled(enabled);
        syntaxBtn.setEnabled(enabled);
        semanticBtn.setEnabled(enabled);
    }

    private void performLexicalAnalysis() {String code = codeTextArea.getText();

        if (code.isEmpty()) {
            resultTextArea.setText("Result: Failed - No input or file opened");
            clearBtn.setEnabled(false);
            syntaxBtn.setEnabled(false);
            semanticBtn.setEnabled(false);
            lexicalBtn.setEnabled(false);
        } else {

            resultTextArea.setText("Result: Lexical analysis phase passed");
            lexicalPassed = true;

            clearBtn.setEnabled(true);
            syntaxBtn.setEnabled(true);
            semanticBtn.setEnabled(false);
            lexicalBtn.setEnabled(false);
        }
    }

    private void performSyntaxAnalysis() {
        if (!lexicalPassed) {
            resultTextArea.setText("Result: Error - Lexical analysis phase must pass first.");
            return;
        }

        String code = codeTextArea.getText();


        String[] lines = code.split("\\n");

        for (String line : lines) {
            boolean validSyntax = line.matches("\\s*(String|int|double)\\s+[a-zA-Z][a-zA-Z0-9_]*\\s*=\\s*[^;]+\\s*;\\s*");

            if (!validSyntax) {
                resultTextArea.setText("Result: Syntax analysis failed - Invalid variable declaration or assignment.");
                return;
            }
        }

        resultTextArea.setText("Result: Syntax analysis phase passed");

        setAnalysisButtonsEnabled(false);
        semanticBtn.setEnabled(true);
    }

    private void performSemanticAnalysis() {
        String input = codeTextArea.getText().trim();

        if (input.isEmpty()) {
            resultTextArea.setText("Result: Semantically Incorrect! No input provided.");
            return;
        }

        String[] lines = input.split("\\n");

        for (String line : lines) {
            if (!line.endsWith(";")) {
                resultTextArea.setText("Result: Semantically Incorrect! Statement does not end with ';'");
                return;
            }

            line = line.substring(0, line.length() - 1).trim();
            String[] parts = line.split("=");

            if (parts.length != 2) {
                resultTextArea.setText("Result: Semantically Incorrect!");
                return;
            }

            String declare = parts[0].trim();
            String value = parts[1].trim();
            String[] variable = declare.split("\\s+");

            if (variable.length < 2) {
                resultTextArea.setText("Result: Semantically Incorrect!");
                return;
            }

            String dataType = variable[0];


            if (!isValidAssignment(dataType, value)) {
                resultTextArea.setText("Result: Semantically Incorrect!");
                return;
            }
        }

        resultTextArea.setText("Result: Semantically Correct!");
    }

    private void clear() {
        codeTextArea.setText("");
        resultTextArea.setText("");
        setAnalysisButtonsEnabled(false);
        lexicalPassed = false;
    }


    public static boolean isValidAssignment(String dataType, String value) {
        switch (dataType) {
            case "int":
                try {
                    Integer.parseInt(value);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            case "double":

                try {
                    double doubleValue = Double.parseDouble(value);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            case "String":
                return value.startsWith("\"") && value.endsWith("\"");
        }
        return false;
    }


    public static void main(String[] args) {
        new MiniCompiler().setVisible(true);
    }

    private void runSplash() {
        int x;
        splash frame = new splash();
        frame.setVisible(true);
        try {
            for (x = 0; x <= 100; x++) {
                splash.progressBar.setValue(x);
                Thread.sleep(50);
                splash.label_1.setText(Integer.toString(x) + " %");
                if (x == 100) {
                    frame.dispose();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


