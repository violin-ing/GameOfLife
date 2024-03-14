import javax.swing.*;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class GameFunctions {
     // define action listener for timer
     private static ActionListener stepper = new ActionListener() {
          public void actionPerformed(ActionEvent e) {
               GameFunctions.step(GameOfLife.getCells(), GameOfLife.getGridSize(), GameOfLife.getXVal(), GameOfLife.getYVal(), GameOfLife.getZVal());
          }
     };

     private static Timer timer = new Timer(300, stepper);
     static {
          timer.setRepeats(true);
     }

     public static void step(Cell[][] cells, int size, int x, int y, int z) {
          // Create a new 2D array to hold the next state of the cells
          boolean[][] nextGeneration = new boolean[size][size];

          // Iterate through each cell in the grid
          for (int i = 0; i < size; i++) {
               for (int j = 0; j < size; j++) {
                    int liveNeighbors = countLiveNeighbors(cells, size, i, j);

                    // Apply the rules of the game
                    if (cells[i][j].isAlive()) {
                         // A live cell with x to y live neighbors remains live
                         if (liveNeighbors >= x && liveNeighbors <= y) {
                              nextGeneration[i][j] = true;
                         } else {
                              nextGeneration[i][j] = false;
                         }
                    } else {
                         // A dead cell with exactly z live neighbors becomes live
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

     public static void startStop(Cell[][] cells) {
          if (timer.isRunning()) {
               timer.stop();
               GameOfLife.stepButton.setEnabled(true);
               GameOfLife.saveButton.setEnabled(true);
               GameOfLife.clearButton.setEnabled(true);
               GameOfLife.gameRunning = false;
               for (Cell[] c1:cells) {
                    for (Cell c2:c1) {
                         c2.setClickable(true);
                    }
               }
          } else { // if gol not running and button pressed, start. also set clickable to false
               timer.start(); 
               GameOfLife.gameRunning = true; 
               GameOfLife.stepButton.setEnabled(false);
               GameOfLife.saveButton.setEnabled(false);
               GameOfLife.clearButton.setEnabled(false);
               for (Cell[] c1:cells) {
                    for (Cell c2:c1) {
                         c2.setClickable(false);
                         GameOfLife.stepButton.setEnabled(false);
                    }
               } 
          }
     }

     public static void speedUp() {
          if (timer.getDelay() >= 100) {
               timer.setDelay(timer.getDelay() - 25);
          }
          if (timer.getDelay() == 100) {
               GameOfLife.speedUpButton.setEnabled(false);
          }
          if (timer.getDelay() < 400) {
               GameOfLife.speedDownButton.setEnabled(true);
          }
     }

     public static void speedDown() {
          if (timer.getDelay() <= 400) {
               timer.setDelay(timer.getDelay() + 25);
          }
          if (timer.getDelay() == 400) {
               GameOfLife.speedDownButton.setEnabled(false);
          }
          if (timer.getDelay() > 100) {
               GameOfLife.speedUpButton.setEnabled(true);
          }
     }

     public static void clear(Cell[][] cells, int size) {
          for (int i = 0; i < size; i++) {
               for (int j = 0; j < size; j++) {
                    cells[i][j].setDead();
               }
          }
     }

     public static Cell[][] loadGol(String path) throws IOException {
          BufferedReader reader = new BufferedReader(new FileReader(path));
          String line;

          line = reader.readLine();
          int size = line.length();
          Cell[][] cells = new Cell[size][size];
          for(int x = 0; x < size; x++) {
               cells[0][x] = new Cell(0, x);
               if ("o".compareTo(Character.toString(line.charAt(x))) == 0) {
                    cells[0][x].setAlive(true);
               } 
               else{
               cells[0][x].setAlive(false);
               }    
          }

          for (int i = 1; i < size; i++) {
               line = reader.readLine();
               for(int j = 0; j < size; j++) {

                    cells[i][j] = new Cell(i, j);

                    if ("o".compareTo(Character.toString(line.charAt(j))) == 0) {
                         cells[i][j].setAlive(true);
                    } 
                    else{
                    cells[i][j].setAlive(false);
                    }    
               }
          }

          while ((line = reader.readLine()) != null && !line.isEmpty()){
               String[] parts = line.split("=");
                    if (parts.length == 2) {
                         String key = parts[0];
                         String value = parts[1];
                         if (key.equals("x")) {
                              GameOfLife.setXVal(Integer.parseInt(value));
                         } else if (key.equals("y")) {
                              GameOfLife.setYVal(Integer.parseInt(value));
                         } else if (key.equals("z")) {
                              GameOfLife.setZVal(Integer.parseInt(value));
                         } else if (key.equals("size")) {
                              //GameOfLife.size = Integer.parseInt(value);
                         } else if (key.equals("description")) {
                              GameOfLife.setDescription(value);
                         }
                    }


          }

          reader.close();
          return cells;
     }

     // Counts the number of live neighbors for a given cell
     private static int countLiveNeighbors(Cell[][] cells, int size, int x, int y) {
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
}
