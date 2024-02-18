import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class GameOfLife extends JFrame {
     private final int size = 50; // size of the cell grid
     private final Cell[][] cells = new Cell[size][size]; // grid of cells
     private final JButton startStopButton = new JButton("Start/Stop");
     private final JButton stepButton = new JButton("Step");
     private final JButton speedButton = new JButton("Speed");
     private final JButton saveButton = new JButton("Save");
     private Timer timer;
     private boolean running = false;
     private int speed = 500; // speed of the simulation in milliseconds

     public GameOfLife() {
          setTitle("Game of Life");
          setSize(800, 600);
          setDefaultCloseOperation(EXIT_ON_CLOSE);
          setLayout(new BorderLayout());

          // Initializing the JPanel with the 50-by-50 cell grid
          JPanel gridPanel = new JPanel(new GridLayout(size, size));
          for (int i = 0; i < size; i++) {
               for (int j = 0; j < size; j++) {
                    cells[i][j] = new Cell(i, j);
                    gridPanel.add(cells[i][j]);
               }
          }

          // Initializing the control JPanel which will include all the user control buttons
          JPanel controlPanel = new JPanel();
          controlPanel.add(startStopButton);
          controlPanel.add(stepButton);
          controlPanel.add(speedButton);
          controlPanel.add(saveButton);

          add(gridPanel, BorderLayout.CENTER);
          add(controlPanel, BorderLayout.SOUTH);

          initializeListeners();
     }

     private void initializeListeners() {
          
     }

     public static void main(String[] args) {
          // Used by convention to avoid potential threading issues
          SwingUtilities.invokeLater(() -> {
               new GameOfLife().setVisible(true);
          });
     }
}
