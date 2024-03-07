import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MenuScreen extends JFrame {
     private static int x, y, z, gridSize;

     public MenuScreen() {
          setTitle("Game Menu");
          setSize(500, 200);
          setDefaultCloseOperation(EXIT_ON_CLOSE);
          setLayout(new BorderLayout());

          JPanel inputPanel = new JPanel(new FlowLayout());
          JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

          // Initialize the menu screen that will take the user input for initial values of x, y and z
          JLabel xLabel = new JLabel("Minimum no. of neighbours to live, x (1 \u2264 x \u2264 y): ");
          JLabel yLabel = new JLabel("Maximum no. of neighbours to live, y (x \u2264 y \u2264 8): ");
          JLabel zLabel = new JLabel("No. of neighbours to come alive, z (x \u2264 z \u2264 y): ");

          // User input for custom grid size
          JLabel gridSizeLabel = new JLabel("Grid dimensions, N (10 \u2264 N \u2264 50): ");

          JTextField xField = new JTextField(5);
          JTextField yField = new JTextField(5);
          JTextField zField = new JTextField(5);

          JTextField gridSizeTextField = new JTextField(5);

          JButton enterButton = new JButton("Continue");

          enterButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent event) {
                    String xString = xField.getText();
                    String yString = yField.getText();
                    String zString = zField.getText();
                    String gridSizeString = gridSizeTextField.getText();
                    if (ValidateInput.validateInput(xString, yString, zString, gridSizeString)) {
                         x = Integer.parseInt(xString);
                         y = Integer.parseInt(yString);
                         z = Integer.parseInt(zString);

                         gridSize = Integer.parseInt(gridSizeString);

                         setVisible(false);
                         dispose();

                         new GameOfLife(x, y, z, gridSize);
                    } else {
                         JOptionPane.showMessageDialog(
                              MenuScreen.this, 
                              "Please ensure that your inputs for x, y, z and N are valid.", 
                              "Input Error", 
                              JOptionPane.ERROR_MESSAGE
                         );
                    }
               }
          });

          // Add components to the frame
          inputPanel.add(xLabel);
          inputPanel.add(xField);
          inputPanel.add(yLabel);
          inputPanel.add(yField);
          inputPanel.add(zLabel);
          inputPanel.add(zField);
          inputPanel.add(gridSizeLabel);
          inputPanel.add(gridSizeTextField);

          buttonPanel.add(enterButton);
          add(inputPanel, BorderLayout.CENTER);
          add(buttonPanel, BorderLayout.SOUTH);
     }
}