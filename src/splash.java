import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class splash {

    public static void Splash() {
        // Create the splash form frame
        JFrame splashFrame = new JFrame("Welcome");
        splashFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        splashFrame.setSize(640, 360);
        splashFrame.setLayout(null);

        // Load the background image
        ImageIcon backgroundImage = new ImageIcon("src/img/splash.jpg"); // Replace with your image file

        // Create a JLabel to display the background
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 640, 360);

        // Create a JLabel for the title
        JLabel titleLabel = new JLabel("PERPUSTAKAANKU");
        titleLabel.setForeground(Color.black);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36)); // Set font and size
        Dimension labelSize = titleLabel.getPreferredSize();
        titleLabel.setBounds((640 - labelSize.width) / 2, 160, labelSize.width, labelSize.height);

        // Create a JButton to navigate to the admin menu
        JButton adminMenuButton = new JButton("Login");
        adminMenuButton.setBounds(250, 250, 100, 30);
        adminMenuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the admin menu when the button is clicked
                Login.login();
                splashFrame.dispose(); // Close the splash form
            }
        });

        // Add components to the splash form
        backgroundLabel.add(titleLabel);
        splashFrame.add(backgroundLabel);
        splashFrame.add(adminMenuButton);

        // Make the splash form visible
        splashFrame.setVisible(true);
        splashFrame.setLocationRelativeTo(null); // Center the form on the screen
    }
}
