import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RepairWorkGUI extends JFrame {

    private RepairWorkDatabase database;
    private JTextArea repairWorksTextArea;
    private JLabel statusLabel;

    public RepairWorkGUI() {
        try {
            database = new RepairWorkDatabase("repair_works.dat");
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Repair Works");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 400));

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");

        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement logic for opening files
                statusLabel.setText("Opened file...");
            }
        });

        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement logic for saving files
                statusLabel.setText("Saved file...");
            }
        });

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        statusLabel = new JLabel("Status: Ready");
        statusLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        contentPane.add(statusLabel, BorderLayout.SOUTH);

        repairWorksTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(repairWorksTextArea);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("Add New Repair Work");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAddDialog();
            }
        });
        contentPane.add(addButton, BorderLayout.NORTH);

        refreshRepairWorks();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showAddDialog() {
        JFrame addDialog = new JFrame("Add New Repair Work");
        addDialog.setLayout(new GridLayout(0, 2));

        JLabel firmLabel = new JLabel("Firm: ");
        JTextField firmField = new JTextField();
        JLabel workTypeLabel = new JLabel("Work Type: ");
        JTextField workTypeField = new JTextField();
        JLabel unitLabel = new JLabel("Unit: ");
        JTextField unitField = new JTextField();
        JLabel costLabel = new JLabel("Cost per Unit: ");
        JTextField costField = new JTextField();
        JLabel dateLabel = new JLabel("Execution Date (yyyy-MM-dd): ");
        JTextField dateField = new JTextField();
        JLabel volumeLabel = new JLabel("Volume: ");
        JTextField volumeField = new JTextField();

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String firm = firmField.getText();
                    String workType = workTypeField.getText();
                    String unit = unitField.getText();
                    String cost = costField.getText();
                    Date executionDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateField.getText());
                    double volume = Double.parseDouble(volumeField.getText());

                    RepairWork repairWork = new RepairWork(firm, workType, unit, cost, executionDate, volume);
                    database.addRepairWork(repairWork);

                    refreshRepairWorks();
                    addDialog.dispose();
                } catch (ParseException | NumberFormatException | IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Invalid input. Please check your entries.");
                }
            }
        });

        addDialog.add(firmLabel);
        addDialog.add(firmField);
        addDialog.add(workTypeLabel);
        addDialog.add(workTypeField);
        addDialog.add(unitLabel);
        addDialog.add(unitField);
        addDialog.add(costLabel);
        addDialog.add(costField);
        addDialog.add(dateLabel);
        addDialog.add(dateField);
        addDialog.add(volumeLabel);
        addDialog.add(volumeField);
        addDialog.add(addButton);

        addDialog.pack();
        addDialog.setLocationRelativeTo(null);
        addDialog.setVisible(true);
    }

    private void refreshRepairWorks() {
        try {
            List<RepairWork> repairWorks = database.getAllRepairWorks();
            StringBuilder sb = new StringBuilder();

            if (repairWorks.isEmpty()) {
                sb.append("No repair works found.");
            } else {
                sb.append("Repair Works:\n");
                for (int i = 0; i < repairWorks.size(); i++) {
                    sb.append(i).append(". ").append(repairWorks.get(i)).append("\n");
                }
            }
            repairWorksTextArea.setText(sb.toString());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            repairWorksTextArea.setText("Error fetching repair works.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new RepairWorkGUI();
            }
        });
    }
}



