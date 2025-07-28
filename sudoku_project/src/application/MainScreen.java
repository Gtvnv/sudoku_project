package application; 

import java.awt.BorderLayout;
import java.io.*;
import javax.swing.*;

import model.Difficulty;
import model.SudokuGenerator;
import model.Board;
import view.BoardPanel;

public class MainScreen {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String[] options = {"Easy", "Medium", "Hard"};
            int choice = JOptionPane.showOptionDialog(null,
                    "Choose difficulty level:", 
                    "Difficulty",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (choice == JOptionPane.CLOSED_OPTION) {
                System.exit(0);
            }

            Difficulty difficulty = Difficulty.EASY;
            if (choice == 1) {
                difficulty = Difficulty.MEDIUM;
            } else if (choice == 2) {
                difficulty = Difficulty.HARD;
            }

            int[][] generatedGrid = null;
            try {
                generatedGrid = SudokuGenerator.generate(difficulty);
            } catch (Exception e) {
                System.err.println("ERROR generating Sudoku grid: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Internal error generating Sudoku. Please restart.");
                System.exit(1);
            }

            Board board = new Board();
            try {
                board.setInitialPuzzle(generatedGrid);
            } catch (Exception e) {
                System.err.println("ERROR setting initial puzzle on Board: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Internal error configuring the board. Please restart.");
                System.exit(1);
            }

            BoardPanel boardPanel = new BoardPanel(board);
            try {
                boardPanel.updateFields();
            } catch (Exception e) {
                System.err.println("ERROR updating BoardPanel fields: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Visual error setting up the board. Please restart.");
                System.exit(1);
            }

            JFrame frame = new JFrame("Sudoku");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 500);

            JPanel buttonPanel = new JPanel();

            JButton saveButton = new JButton("Save");
            saveButton.addActionListener(e -> {
                try {
                    board.saveGame("saved_game.txt");
                    JOptionPane.showMessageDialog(frame, "Game saved successfully!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error saving game.");
                }
            });

            JButton loadButton = new JButton("Load");
            loadButton.addActionListener(e -> {
                try {
                    board.loadGame("saved_game.txt");
                    boardPanel.updateFields();
                    JOptionPane.showMessageDialog(frame, "Game loaded successfully!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error loading game.");
                }
            });

            buttonPanel.add(saveButton);
            buttonPanel.add(loadButton);

            frame.add(boardPanel, BorderLayout.CENTER);
            frame.add(buttonPanel, BorderLayout.SOUTH);
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
        });
    }
}
