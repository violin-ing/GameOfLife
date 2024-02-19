import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


class Cell extends JPanel {
     private boolean alive = false;

     public Cell(int x, int y) {
          setBorder(BorderFactory.createLineBorder(Color.BLACK));
          addMouseListener(new MouseAdapter() {
               @Override
               public void mouseClicked(MouseEvent e) {
                    toggleState(); // Change cell state upon mouse click
               }
          });
     }

     private void toggleState() {
          alive = !alive; // If the cell is 'alive', it will be set to 'dead', and vice versa
          if (alive) {
               setBackground(Color.BLACK);
          } else {
               setBackground(Color.WHITE);
          }
     }
 }
 