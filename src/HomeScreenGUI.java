import javax.swing.*;
import java.awt.*;

public class HomeScreenGUI {

    public static void homeScreen() {
        JPanel panel = new JPanel();
        JFrame frame = new JFrame("Home");
        frame.setSize(800,650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setResizable(false);
        panel.setLayout(null);

        JLabel mainMenuLabel = new JLabel("Main Menu");
        mainMenuLabel.setBounds(315, 50, 350, 300);
        mainMenuLabel.setFont(new Font("Arial", Font.BOLD, 35));
        panel.add(mainMenuLabel);

        JButton createProject = createProjectButton(frame);
        createProject.setFocusable(false);
        panel.add(createProject);
        panel.setBackground(Color.white);

        frame.setVisible(true);

    }

    public static JButton createProjectButton(JFrame frame) {
        JButton createProject = new JButton("Create New Project");
        createProject.setBounds(250,250,300,75);
        createProject.addActionListener(e -> {
            //frame.dispose();
            LayoutSelection selectlayout = new LayoutSelection();
        });
        return createProject;
    }

}
