import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;


public class SaveScreen extends JFrame { // FRONTEND FOR SAVE SCREEN
    private static String fileName, description;

    public SaveScreen(int size, Cell[][] cellArray) {

        setTitle("Save Configuration");
        setSize(500, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Initialize the menu screen that will take the user input for filename and save accordingly
        JLabel inputLabel = new JLabel("Enter desired file name: ", JLabel.CENTER);

        JLabel descriptionLabel = new JLabel("Enter description of configuration", JLabel.CENTER);

        JTextField fileNameField = new JTextField(10);

        JTextArea descriptionField = new JTextArea(3,10);
        descriptionField.setLineWrap(true); 
        descriptionField.setWrapStyleWord(true); 

        JScrollPane descriptionScrollPane = new JScrollPane(descriptionField);
        descriptionField.setCaretPosition(descriptionField.getDocument().getLength());
        descriptionField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                descriptionField.setCaretPosition(descriptionField.getDocument().getLength());
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                descriptionField.setCaretPosition(descriptionField.getDocument().getLength());
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                descriptionField.setCaretPosition(descriptionField.getDocument().getLength());
            }
        });

        JButton enterButton = new JButton("Save");

        enterButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                String fileNameString = fileNameField.getText();
                String description = descriptionField.getText();

                if (FileSaver.validateInput(fileNameString)) {
                    fileName = fileNameString;
                    try{FileSaver.saveConfiguration(fileName, size, cellArray);}
                    catch (FileNotFoundException e){
                        JOptionPane.showMessageDialog(
                        SaveScreen.this, "File Could Not Be Saved", "Input Error", JOptionPane.ERROR_MESSAGE);
                        System.exit(0);
                    }

                    setVisible(false);
                    dispose();
                }
                    
                else{
                    JOptionPane.showMessageDialog(
                        SaveScreen.this, 
                        "Please ensure that your file name is valid", 
                        "Input Error", 
                        JOptionPane.ERROR_MESSAGE
                        );
                }
            }
        });

        // Add components to the frame
        inputPanel.add(inputLabel);
        inputPanel.add(fileNameField);
        inputPanel.add(descriptionLabel);
        inputPanel.add(descriptionScrollPane);

        buttonPanel.add(enterButton);
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}