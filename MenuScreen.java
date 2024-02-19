import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MenuScreen extends JFrame {
     private static int x, y, z;

     public MenuScreen() {
          setTitle("Game Menu");
          setSize(400, 300);
          setDefaultCloseOperation(EXIT_ON_CLOSE);
          setLayout(new BorderLayout());

          JPanel inputPanel = new JPanel(new FlowLayout());
          JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

          // Initialize the menu screen that will take the user input for initial values of x, y and z
          JLabel xLabel = new JLabel("Minimum no. of neighbours to live (x): ");
          JLabel yLabel = new JLabel("Maximum no. of neighbours to live (y): ");
          JLabel zLabel = new JLabel("No. of neighbours to come alive (z): ");

          JTextField xField = new JTextField(5);
          JTextField yField = new JTextField(5);
          JTextField zField = new JTextField(5);

          JButton enterButton = new JButton("Continue");

          enterButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent event) {
                    String xString = xField.getText();
                    String yString = yField.getText();
                    String zString = zField.getText();
                    if (ValidateInput.validateInput(xString, yString, zString)) {
                         x = Integer.parseInt(xString);
                         y = Integer.parseInt(yString);
                         z = Integer.parseInt(zString);

                         setVisible(false);
                         dispose();

                         new GameOfLife(x, y, z);
                    } else {
                         JOptionPane.showMessageDialog(
                              MenuScreen.this, 
                              "Please enter valid integers for x, y and z.", 
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
          buttonPanel.add(enterButton);
          add(inputPanel, BorderLayout.CENTER);
          add(buttonPanel, BorderLayout.SOUTH);
     }
}