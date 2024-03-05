import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LayoutSelection {

    private JFrame frame;
    private JComboBox<String> layoutDropdown;
    private JButton okButton;

    public LayoutSelection() {
        frame = new JFrame("Layout Selection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new FlowLayout());

        String[] layoutOptions = {"LayoutOne", "LayoutTwo"}; // Add more layout options
        layoutDropdown = new JComboBox<>(layoutOptions);
        frame.add(new JLabel("Please select a layout:")); // Add label
        frame.add(layoutDropdown);

        okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected layout from the dropdown
                String selectedLayout = (String) layoutDropdown.getSelectedItem();

                // Instantiate the selected layout class
                instantiateLayout(selectedLayout);
            }
        });

        frame.add(okButton);

        frame.setVisible(true);
    }

    private void instantiateLayout(String selectedLayout) {
        try {
            // Dynamically instantiate the selected layout class
            Class<?> layoutClass = Class.forName(selectedLayout);
            Object layoutInstance = layoutClass.getDeclaredConstructor().newInstance();
            frame.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new LayoutSelection();
    }
}
