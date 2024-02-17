import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.filechooser.*;
import java.util.List;
import java.util.ArrayList;


public class LayoutTwo extends JFrame {
    private JButton[][] buttons;
    private JList<String> nameList;
    private static DefaultListModel<String> listModel;
    private static File selectedFile;
    private static AllStudents allStudents;

    private JButton clearButton;
    private JButton clearAllButton;
    private JButton exportButton; // Added export button
    int columns = 1; // Only one column for each set of buttons
    private List<JButton[]> buttonsList;

    public LayoutTwo() {
        setTitle("Seating Plan Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        buttonsList = new ArrayList<>();


        int rows = 10; // Number of rows for the first and last columns
        int middleRows = 6; // Number of rows for the middle columns
        int buttonWidth = 150;
        int buttonHeight = 50;
        int horizontalGap = 20;
        int verticalGap = 20;
        int startXOffset = 250; // Adjusted starting X offset to move away from the edge

        // First column with ten buttons
        createColumn(rows, startXOffset, 20, buttonWidth, buttonHeight, horizontalGap);

        // Second column with six buttons
        createColumn(middleRows, startXOffset + 200, 20, buttonWidth, buttonHeight, horizontalGap);

        // Third column with six buttons
        createColumn(middleRows, startXOffset + 400, 20, buttonWidth, buttonHeight, horizontalGap);

        // Fourth column with ten buttons
        createColumn(rows, startXOffset + 600, 20, buttonWidth, buttonHeight, horizontalGap);

        listModel = new DefaultListModel<>();
        nameList = new JList<>(listModel);
        JScrollPane listScrollPane = new JScrollPane(nameList);

        listScrollPane.setBounds(10,200,120,200);
        add(listScrollPane);

        JButton importNamesButton = importNamesButton(this);
        add(importNamesButton);

        clearButton = new JButton("Clear Name");
        clearButton.setFocusable(false);
        clearButton.setBounds(10, 45, 150, 25);
        clearButton.addActionListener(e -> clearSelectedName());
        add(clearButton);

        clearAllButton = new JButton("Clear All");
        clearAllButton.setFocusable(false);
        clearAllButton.setBounds(10, 80, 150, 25);
        clearAllButton.addActionListener(e -> clearAllNames());
        add(clearAllButton);

        exportButton = new JButton("Export as PNG");
        exportButton.setFocusable(false);
        exportButton.setBounds(10, 115, 150, 25);
        exportButton.addActionListener(e -> exportAsPNG());
        add(exportButton);

        setLocationRelativeTo(null);
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    private void createColumn(int rows, int startX, int startY, int buttonWidth, int buttonHeight, int horizontalGap) {
        JButton[] columnButtons = new JButton[rows]; // Create an array of JButton
        for (int i = 0; i < rows; i++) {
            JButton button = new JButton("");
            button.setBackground(Color.white);
            button.setFocusable(false);
            columnButtons[i] = button; // Add button to the array
            int y = startY + i * (buttonHeight + horizontalGap);
            button.setBounds(startX, y, buttonWidth, buttonHeight);
            button.addActionListener(new ButtonClickListener(button));
            add(button);
        }
        buttonsList.add(columnButtons); // Add the array to buttonsList
    }




    private class ButtonClickListener implements ActionListener {
        private JButton button;

        public ButtonClickListener(JButton button) {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = nameList.getSelectedIndex();
            if (selectedIndex != -1) {
                String selectedName = nameList.getModel().getElementAt(selectedIndex);
                button.setText(selectedName);
            } else {
                JOptionPane.showMessageDialog(null, "Please select a name from the list.");
            }
        }
    }

    public static JButton importNamesButton(JFrame frame) {
        JButton importNames = new JButton("Import Names");
        importNames.setFocusable(false);
        importNames.setBounds(10, 10, 150, 25);
        importNames.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                allStudents = new AllStudents(selectedFile.getAbsolutePath());

                listModel.clear();

                for (String name : allStudents.getNameTags()) {
                    listModel.addElement(name);
                }
            } else {
                System.out.println("File selection cancelled.");
            }
        });
        return importNames;
    }

    private void clearSelectedName() {
        int selectedIndex = nameList.getSelectedIndex();
        if (selectedIndex != -1) {
            String selectedName = nameList.getModel().getElementAt(selectedIndex);
            for (JButton[] columnButtons : buttonsList) {
                for (JButton button : columnButtons) {
                    if (button.getText().equals(selectedName)) {
                        button.setText(""); // Clear the text of the button
                        return; // Exit the method after clearing the name
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a name from the list.");
        }
    }
    private void clearAllNames() {
        for (JButton[] columnButtons : buttonsList) {
            for (JButton button : columnButtons) {
                button.setText(""); // Clear the text of each button
            }
        }
    }

    private void exportAsPNG() {
        try {
            Robot robot = new Robot();
            BufferedImage screenshot = robot.createScreenCapture(new Rectangle(getLocationOnScreen(), getSize()));
            File file = new File("seating_plan.png");
            ImageIO.write(screenshot, "png", file);
            JOptionPane.showMessageDialog(null, "Seating plan exported as PNG: " + file.getAbsolutePath());
        } catch (AWTException | IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error exporting seating plan as PNG.");
        }
    }
}

