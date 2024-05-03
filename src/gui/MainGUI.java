package gui;

import util.*;
import javax.swing.*;

import algorithm.AStarSolver;
import algorithm.GreedyBestFirstSolver;
import algorithm.UCSSolver;
import algorithm.WordLadderSolver;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainGUI extends JFrame {
    private JTextField startWordField;
    private JTextField endWordField;
    private JComboBox<String> algorithmComboBox;
    private JButton solveButton;
    private JTextArea resultArea;

    private JLabel stepsLabel;
    private JLabel visitedNodesLabel;
    private JLabel executionTimeLabel;

    public MainGUI() {
        setTitle("Word Ladder Solver");
        setSize(990, 704);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setResizable(false);

        ImagePanel backgroundPanel = new ImagePanel("src/gui/background.png");
        backgroundPanel.setLayout(null);

        // FONT
        Font labelFont = new Font("Arial", Font.PLAIN, 16);

        // KOMPONEN
        startWordField = new JTextField();
        endWordField = new JTextField();
        algorithmComboBox = new JComboBox<>(new String[]{"UCS", "Greedy Best First Search", "A*"});
        solveButton = new JButton("S O L V E");
        resultArea = new JTextArea();
        resultArea.setEditable(false); JScrollPane scrollPane = new JScrollPane(resultArea); scrollPane.setBorder(null);
        stepsLabel = new JLabel("");
        visitedNodesLabel = new JLabel("");
        executionTimeLabel = new JLabel("");

        // SETTING KOMPONEN
        // Set warna resultArea
        resultArea.setBackground(Color.decode("#E5FDFF"));
        // Set font resultArea
        Font resultFont = new Font("Arial", Font.PLAIN, 20);
        resultArea.setFont(resultFont);
        
        // Set warna background untuk startWordField dan endWordField
        Color backgroundColor = Color.decode("#1C2A41");
        startWordField.setBackground(backgroundColor);
        endWordField.setBackground(backgroundColor);
        // Hapus border 
        startWordField.setBorder(BorderFactory.createEmptyBorder());
        endWordField.setBorder(BorderFactory.createEmptyBorder());

        // Set warna text dan font startWordField dan endWordField
        startWordField.setForeground(Color.WHITE);
        endWordField.setForeground(Color.WHITE);
        startWordField.setFont(labelFont);
        endWordField.setFont(labelFont);
        // Center text 
        startWordField.setHorizontalAlignment(SwingConstants.CENTER);
        endWordField.setHorizontalAlignment(SwingConstants.CENTER);

        // Set warna text dan font untuk stepsLabel, visitedNodesLabel, dan executionTimeLabel
        stepsLabel.setForeground(Color.WHITE);
        visitedNodesLabel.setForeground(Color.WHITE);
        executionTimeLabel.setForeground(Color.WHITE);
        stepsLabel.setFont(labelFont);
        visitedNodesLabel.setFont(labelFont);
        executionTimeLabel.setFont(labelFont);
        // Center text
        stepsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        visitedNodesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        executionTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Set algorithmComboBox 
        algorithmComboBox.setFont(labelFont);
        algorithmComboBox.setForeground(Color.WHITE);
        algorithmComboBox.setBackground(Color.decode("#1C2A41"));
        algorithmComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel renderer = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                renderer.setHorizontalAlignment(SwingConstants.CENTER); // Center align text
                return renderer;
            }
        });

        // Set solveButton
        solveButton.setBackground(Color.decode("#F0A0F9"));
        solveButton.setForeground(Color.BLACK); 


        // SET POSISI KOMPONEN
        startWordField.setBounds(90, 199, 150, 25);
        endWordField.setBounds(90, 300, 150, 25);
        algorithmComboBox.setBounds(63, 400, 210, 25);
        solveButton.setBounds(95, 565, 150, 40);
        resultArea.setBounds(340, 163, 550, 370);
        stepsLabel.setBounds(328, 608, 120, 20);
        visitedNodesLabel.setBounds(508, 608, 120, 20);
        executionTimeLabel.setBounds(730, 608, 120, 20);
        scrollPane.setBounds(340, 163, 550, 370);
        

        // aksi solveButton
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runSolver();
            }
        });

        // ADD KOMPONEN KE PANEL
        backgroundPanel.add(startWordField);
        backgroundPanel.add(endWordField);
        backgroundPanel.add(algorithmComboBox);
        backgroundPanel.add(solveButton);
        // backgroundPanel.add(resultArea);
        backgroundPanel.add(stepsLabel);
        backgroundPanel.add(visitedNodesLabel);
        backgroundPanel.add(executionTimeLabel);
        backgroundPanel.add(scrollPane);

        setContentPane(backgroundPanel);
        setVisible(true);
    }

    // Method untuk menjalankan solver
    private void runSolver() {
        String startWord = startWordField.getText().toUpperCase();
        String endWord = endWordField.getText().toUpperCase();

        // Validasi input
        if (startWord.isEmpty() || endWord.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both start and end words.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int algorithmChoice = algorithmComboBox.getSelectedIndex() + 1;

        Dictionary dictionary = new Dictionary("src/util/Collins_Scrabble_Words_2019.txt");

        // Validasi kata awal dan akhir
        if (!dictionary.isWord(startWord) || !dictionary.isWord(endWord)) {
            JOptionPane.showMessageDialog(this, "Invalid start or end word.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // validasi panjang kata awal dan akhir
        if (startWord.length() != endWord.length()) {
            JOptionPane.showMessageDialog(this, "Start and end words must have the same length.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        WordLadderSolver solver;
        switch (algorithmChoice) {
            case 1:
                solver = new UCSSolver(dictionary, startWord, endWord);
                break;
            case 2:
                solver = new GreedyBestFirstSolver(dictionary, startWord, endWord);
                break;
            case 3:
                solver = new AStarSolver(dictionary, startWord, endWord);
                break;
            default:
                resultArea.setText("Invalid choice. Exiting...");
                return;
        }

        long startTime = System.nanoTime();
        List<String> path = solver.solve();
        long endTime = System.nanoTime();
        long executionTime = (endTime - startTime) / 1000000; // Ubah ke milliseconds

        if (path != null) {
            if (path.isEmpty()) {
                resultArea.setText("No path found.");
                return;
            }
            StringBuilder result = new StringBuilder();
            for (String word : path) {
                result.append(word).append("\n");
            }
            stepsLabel.setText("" + (path.size() - 1));
            visitedNodesLabel.setText("" + solver.getVisitedNodes());
            executionTimeLabel.setText("" + executionTime + " ms");
            resultArea.setText(result.toString());
        } else {
            resultArea.setText("No path found.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}
