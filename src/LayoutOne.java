import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.filechooser.*;

public class LayoutOne extends JFrame {
    private JButton[][] buttons;
    private JList<String> nameList;
    private static DefaultListModel<String> listModel;
    private static File selectedFile;
    private static AllStudents allStudents;

    private JButton clearButton;
    private JButton clearAllButton;
    private JButton exportButton; // Added export button

    public LayoutOne() {
        setTitle("Seating Plan Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        int rows = 4;
        int columns = 8;
        int buttonWidth = 150;
        int buttonHeight = 50;
        int horizontalGap = 20;
        int verticalGap = 20;

        buttons = new JButton[rows][columns];

        int startX = (int) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth() - columns * (buttonWidth + horizontalGap)) / 2);
        int startY = (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() - rows * (buttonHeight + verticalGap)) / 2);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                JButton button = new JButton("");
                button.setBackground(Color.white);
                button.setFocusable(false);
                buttons[i][j] = button;

                int x = startX + j * (buttonWidth + horizontalGap);
                int y = startY + i * (buttonHeight + verticalGap);

                buttons[i][j].setBounds(x, y, buttonWidth, buttonHeight);
                buttons[i][j].addActionListener(new ButtonClickListener(buttons[i][j]));
                add(buttons[i][j]);
            }
        }

        listModel = new DefaultListModel<>();
        nameList = new JList<>(listModel);
        JScrollPane listScrollPane = new JScrollPane(nameList);

        int listX = startX - 160;
        listScrollPane.setBounds(listX, startY, 120, rows * (buttonHeight + verticalGap) + 20);
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

        int frameWidth = columns * (buttonWidth + horizontalGap) + 180;
        int frameHeight = rows * (buttonHeight + verticalGap) + 40;

        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
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
            for (int i = 0; i < buttons.length; i++) {
                for (int j = 0; j < buttons[i].length; j++) {
                    if (buttons[i][j].getText().equals(selectedName)) {
                        buttons[i][j].setText("");
                        break;
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a name from the list.");
        }
    }

    private void clearAllNames() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j].setText("");
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
