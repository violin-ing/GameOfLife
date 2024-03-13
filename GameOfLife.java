import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;


public class GameOfLife extends JFrame {
     private static Cell[][] cells;
     public static final JButton startStopButton = new JButton("Start/Stop");
     public static final JButton stepButton = new JButton("Step");
     public static final JButton speedUpButton = new JButton("Speed Up");
     public static final JButton speedDownButton = new JButton("Speed Down");
     public static final JButton saveButton = new JButton("Save");
     public static final JButton clearButton = new JButton("Clear");
     public static final JButton loadButton = new JButton("Load");
     public static boolean gameRunning = false;
     private JPanel gridPanel;


     // Declare integers for x, y and z (conditions for cells to become live/dead)
     private static int x, y, z;

     // Customization: Declare integers for gridSize and add description
     private static int size;
     private String description; 

     public GameOfLife(int x, int y, int z, int gridSize) {
          GameOfLife.x = x;
          GameOfLife.y = y;
          GameOfLife.z = z;
          GameOfLife.size = gridSize;

          cells = new Cell[size][size]; // Used to prepare a grid of Cell objects

          setTitle("Game of Life");
          setSize(800, 800);
          setDefaultCloseOperation(EXIT_ON_CLOSE);
          setLayout(new BorderLayout());

          // Initializing the JPanel with the N-by-N cell grid
          gridPanel = new JPanel(new GridLayout(size, size));
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
          controlPanel.add(speedUpButton);
          controlPanel.add(speedDownButton);
          controlPanel.add(saveButton);
          controlPanel.add(loadButton);
          controlPanel.add(clearButton);

          stepButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   GameFunctions.step(cells, size, x, y, z);
               }
           });
          
          startStopButton.addActionListener(new ActionListener() { // if gol is running and button pressed, stop. also set clickable to false
               public void actionPerformed(ActionEvent e) {
                    GameFunctions.startStop(cells); 
               } 
          });

          speedUpButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                    GameFunctions.speedUp();
               }
           });

           speedDownButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                    GameFunctions.speedDown();
               }
           });

           clearButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                    GameFunctions.clear(cells, size);
               }
           });

          saveButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    dispose();
                    new SaveScreen(size, cells);
               }
          });

          loadButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e){
                    JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    int r = fileChooser.showOpenDialog(null);
                    if (r == JFileChooser.APPROVE_OPTION) {
                         String filePath = fileChooser.getSelectedFile().getAbsolutePath();

                         try {
                              GameFunctions.loadGol(filePath, cells, size);
                         } catch (FileNotFoundException ex) {
                              JOptionPane.showMessageDialog(
                                   GameOfLife.this, 
                                   "No gol file currently saved.", 
                                   "File Not Found", 
                                   JOptionPane.ERROR_MESSAGE
                              );
                         } catch (IOException ex) {
                              JOptionPane.showMessageDialog(
                                   GameOfLife.this, 
                                   "Error while reading file.", 
                                   "File I/O Error", 
                                   JOptionPane.ERROR_MESSAGE
                              );
                         } catch (StringIndexOutOfBoundsException ex) {
                              JOptionPane.showMessageDialog(
                                   GameOfLife.this, 
                                   "Grid size of saved gol file not compatible with selected grid size.", 
                                   "Grid Size Incompatibility", 
                                   JOptionPane.ERROR_MESSAGE
                              );
                         }
                    } else {}

                    /*remove(gridPanel);
                    size = 20;
                    cells = new Cell[size][size];
                    gridPanel = new JPanel(new GridLayout(size, size));
                    for (int i = 0; i < size; i++) {
                         for (int j = 0; j < size; j++){
                              cells[i][j] = new Cell(i, j);
                              gridPanel.add(cells[i][j]);
                         }
                    }
                    add(gridPanel, BorderLayout.CENTER);
                    setVisible(true);

                    try {
                         loadGol();
                    } catch (FileNotFoundException ex) {
                         JOptionPane.showMessageDialog(
                              GameOfLife.this, 
                              "No gol file currently saved.", 
                              "File Not Found", 
                              JOptionPane.ERROR_MESSAGE
                         );
                    } catch (IOException ex) {
                         JOptionPane.showMessageDialog(
                              GameOfLife.this, 
                              "Error while reading file.", 
                              "File I/O Error", 
                              JOptionPane.ERROR_MESSAGE
                         );
                    } catch (StringIndexOutOfBoundsException ex) {
                         JOptionPane.showMessageDialog(
                              GameOfLife.this, 
                              "Grid size of saved gol file not compatible with selected grid size.", 
                              "Grid Size Incompatibility", 
                              JOptionPane.ERROR_MESSAGE
                         );
                    }*/
               }
          });
           
          add(gridPanel, BorderLayout.CENTER);
          add(controlPanel, BorderLayout.SOUTH);
          
          setVisible(true);
     }

     /*public void saveConfiguration() throws FileNotFoundException {
          PrintWriter writer = new PrintWriter("mostRecentSave.gol");
               
               for (int i=0; i<size; i++) {
                    for (int j=0; j<size; j++) {
                        if (cells[i][j].isAlive()) {
                            writer.print("o");
                        }
                        else {
                            writer.print(".");
                        }
        
                         if (j%(size-1) == 0 && j!=0) {
                              writer.print("\n");
                         }
        
                    }
               }

          writer.close();
          System.exit(0);
     }

     private void loadGol2() throws FileNotFoundException, IOException {
          BufferedReader reader = new BufferedReader(new FileReader("mostRecentSave.gol2"));

          String line;
          while ((line = reader.readLine()) != null && !line.isEmpty()) {
               String[] parts = line.split("=");
               if (parts.length == 2) {
                    String key = parts[0];
                    String value = parts[1];
                    if (key.equals("x")) {
                         x = Integer.parseInt(value);
                    } else if (key.equals("y")) {
                         y = Integer.parseInt(value);
                    } else if (key.equals("z")) {
                         z = Integer.parseInt(value);
                    } else if (key.equals("size")) {
                         size = Integer.parseInt(value);
                    } else if (key.equals("description")) {
                         description = value;
                    }
               }
          }
          reader.close();
     }*/

     // Getter methods:
     public static Cell[][] getCells() {return cells;}
     public static int getGridSize() {return size;}
     public static int getXVal() {return x;}
     public static int getYVal() {return y;}
     public static int getZVal() {return z;}

     public static void main(String[] args) {
          // Used by convention to avoid potential threading issues
          SwingUtilities.invokeLater(() -> {
               new MenuScreen().setVisible(true);
          });
     }
}
