import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GameOfLife extends JFrame {
     private Cell[][] cells;
     private final JButton startStopButton = new JButton("Start/Stop");
     private final JButton stepButton = new JButton("Step");
     private final JButton speedButton = new JButton("Speed");
     private final JButton saveButton = new JButton("Save");
     private final JButton clearButton = new JButton("Clear");

     // Declare integers for x, y and z (conditions for cells to become live/dead)
     private int x, y, z;

     // Customization: Declare integers for gridSize 
     private int size;

     public GameOfLife(int x, int y, int z, int gridSize) {
          this.x = x;
          this.y = y;
          this.z = z;
          this.size = gridSize;

          cells = new Cell[size][size]; // Used to prepare a grid of Cell objects

          setTitle("Game of Life");
          setSize(800, 800);
          setDefaultCloseOperation(EXIT_ON_CLOSE);
          setLayout(new BorderLayout());

          // Initializing the JPanel with the N-by-N cell grid
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
          controlPanel.add(clearButton);

          stepButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   step();
               }
           });

          //archie continuous step progress

          //define action listener for timer
          ActionListener stepper = new ActionListener() {
               public void actionPerformed(ActionEvent e){
                    step();
               }
          };

          //instantiate new timer, with delay initially 300 TODO: speed button changes delay
          Timer timer = new Timer(300, stepper );
          timer.setRepeats(true);
          
          startStopButton.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e){
                    if (timer.isRunning()){timer.stop();} //if gol is running and button pressed, stop
                    else{timer.start();} //if gol not running and button pressed, start
               } 
          });


           

           clearButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                    clear();
               }
           });
           
          add(gridPanel, BorderLayout.CENTER);
          add(controlPanel, BorderLayout.SOUTH);

          
          setVisible(true);
     }

     private void step() {
          // Create a new 2D array to hold the next state of the cells
          boolean[][] nextGeneration = new boolean[size][size];

          // Iterate through each cell in the grid
          for (int i = 0; i < size; i++) {
               for (int j = 0; j < size; j++) {
                    int liveNeighbors = countLiveNeighbors(i, j);

                    // Apply the rules of the game
                    if (cells[i][j].isAlive()) {
                         // A live cell with 2 to 3 live neighbors remains live
                         if (liveNeighbors == x || liveNeighbors == y) {
                              nextGeneration[i][j] = true;
                         } else {
                              nextGeneration[i][j] = false;
                         }
                    } else {
                         // A dead cell with exactly 3 live neighbors becomes live
                         if (liveNeighbors == z) {
                              nextGeneration[i][j] = true;
                         } else {
                              nextGeneration[i][j] = false;
                         }
                    }
               }
          }

          // Update the state of each cell based on the next generation
          for (int i = 0; i < size; i++) {
               for (int j = 0; j < size; j++) {
                    cells[i][j].setAlive(nextGeneration[i][j]);
               }
          }
     }


     // Helper method to count the number of live neighbors for a given cell
     private int countLiveNeighbors(int x, int y) {
          int count = 0;
          for (int i = -1; i <= 1; i++) {
               for (int j = -1; j <= 1; j++) {
                    int neighborX = (x + i + size) % size;
                    int neighborY = (y + j + size) % size;

                    // Skip the current cell itself
                    if (i == 0 && j == 0) continue;
                    
                    if (cells[neighborX][neighborY].isAlive()) {
                         count++;
                    }
               }
          }
          return count;
     }


     private void clear() {
          for (int i = 0; i < size; i++) {
               for (int j = 0; j < size; j++) {
                    cells[i][j].setDead();
               }
          }
     }


     public static void main(String[] args) {
          // Used by convention to avoid potential threading issues
          SwingUtilities.invokeLater(() -> {
               new MenuScreen().setVisible(true);
          });
     }
}
