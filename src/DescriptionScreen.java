import javax.swing.*;
import java.awt.*;

public class DescriptionScreen extends JFrame {

    public DescriptionScreen(String description) {

        setTitle("Load Description");
        setSize(500, 200);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new BorderLayout());
        JTextArea descriptionText = new JTextArea();
        
        if (description != null) {
            descriptionText.setText("Description: " + description);
        } else {
            descriptionText.setText("No description added yet.");
        }
        descriptionText.setEditable(false);
        descriptionText.setLineWrap(true);
        descriptionText.setWrapStyleWord(true);
        
        inputPanel.add(descriptionText);
        add(inputPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}