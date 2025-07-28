package view; 

import javax.swing.*;

import model.Board;

import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class BoardPanel extends JPanel {
    private JTextField[][] fields = new JTextField[9][9];
    private Board board;

    public BoardPanel(Board board) {
        this.board = board;
        setLayout(new GridLayout(9, 9));
        initializeFields(); 
    }

    public void updateFields() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int value = board.getCellValue(i, j); // Gets the current cell value

                // Sets the field text
                if (value != 0) {
                    fields[i][j].setText(String.valueOf(value));
                } else {
                    fields[i][j].setText(""); // Ensures empty fields don't show '0'
                }

                // Applies fixed status and colors
                if (board.isCellFixed(i, j)) { // If the cell is fixed (consulting the Board)
                    fields[i][j].setEditable(false); // DISABLES EDITING
                    fields[i][j].setBackground(Color.LIGHT_GRAY); // Gray background
                    fields[i][j].setForeground(Color.BLUE); // Blue text color
                } else { // If the cell is not fixed (editable by the user)
                    fields[i][j].setEditable(true); // ENABLES EDITING
                    fields[i][j].setBackground(Color.WHITE); // White background
                    fields[i][j].setForeground(Color.BLACK); // Black text color
                }
            }
        }
    }

    private void initializeFields() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JTextField field = new JTextField();
                field.setHorizontalAlignment(JTextField.CENTER);
                field.setFont(new Font("Arial", Font.BOLD, 24));
                field.setBorder(javax.swing.BorderFactory.createLineBorder(Color.DARK_GRAY));
                
                int row = i;
                int col = j; 
                field.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        if (!field.isEditable()) { // Important: only acts if editable
                            return;
                        }
                        String text = field.getText(); 
                        if (text.matches("[1-9]")) {
                            int value = Integer.parseInt(text); 
                            if (board.isValidMove(row, col, value)) { 
                                board.setCellValue(row, col, value); 
                                if (board.isSolved()) {
                                    JOptionPane.showMessageDialog(null, "Congratulations! You completed the Sudoku!");
                                }
                            } else {
                                field.setBackground(Color.RED); // Visual feedback for error
                                Timer timer = new Timer(300, new ActionListener() { // Returns to normal after 300ms
                                    public void actionPerformed(ActionEvent evt) {
                                        field.setBackground(Color.WHITE);
                                    }
                                });
                                timer.setRepeats(false);
                                timer.start();
                                field.setText(""); // Clears the field for invalid value
                                JOptionPane.showMessageDialog(null, "Invalid value!");
                            }
                        } else if (!text.isEmpty()) {
                            field.setText(""); // Clears if the input is not a valid number
                        }
                    }
                });
                fields[i][j] = field;
                add(field);
            }
        }
    }
}
