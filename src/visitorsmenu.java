import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import net.proteanit.sql.DbUtils;
import java.awt.BorderLayout;
import java.awt.Color;
import com.toedter.calendar.JDateChooser;
import java.awt.FlowLayout;

public class visitorsmenu {
    public Connection connection;
    public JTable bookListTable;

    public void VisitorsMenu() {
        connection = Connect.connect();

        JFrame vFrame = new JFrame("VISITORS");

        Connection connection = Connect.connect();
        String sql = "SELECT * FROM visitors";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            JTable visitorListTable = new JTable();
            visitorListTable.setModel(DbUtils.resultSetToTableModel(rs));
            JScrollPane scrollPane = new JScrollPane(visitorListTable);

            JButton refreshButton = new JButton("Refresh");
            refreshButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        ResultSet updatedRs = stmt.executeQuery(sql);
                        visitorListTable.setModel(DbUtils.resultSetToTableModel(updatedRs));
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    }
                }
            });

            // Button Add Visitor
            JButton addVisitorButton = new JButton("Add Visitor");
            addVisitorButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JFrame aFrame = new JFrame("ADD NEW VISITOR");
                    aFrame.getContentPane().setBackground(new Color(0, 191, 255));

                    JLabel labelName, labelBirthdate, labelAddress, labelPhone;
                    labelName = new JLabel("Name");
                    labelName.setBounds(30, 15, 100, 30);

                    labelBirthdate = new JLabel("Birthdate");
                    labelBirthdate.setBounds(30, 53, 100, 30);

                    labelAddress = new JLabel("Address");
                    labelAddress.setBounds(30, 90, 100, 30);

                    labelPhone = new JLabel("Phone");
                    labelPhone.setBounds(30, 127, 100, 30);

                    JTextField F_name, F_address, F_phone;
                    F_name = new JTextField();
                    F_name.setBounds(110, 15, 250, 30);

                    F_address = new JTextField();
                    F_address.setBounds(110, 90, 250, 30);

                    F_phone = new JTextField();
                    F_phone.setBounds(110, 127, 250, 30);

                    // Create a date picker using JDateChooser
                    JDateChooser birthdatePicker = new JDateChooser();
                    birthdatePicker.setBounds(110, 53, 250, 30);

                    JButton createButton = new JButton("Submit");
                    createButton.setBounds(110, 165, 80, 25);
                    createButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            String name = F_name.getText();
                            String birthdate = new SimpleDateFormat("yyyy-MM-dd").format(birthdatePicker.getDate());
                            String address = F_address.getText();
                            String phone = F_phone.getText();

                            try {
                                Connection connection = Connect.connect();

                                try {
                                    Statement stmt = connection.createStatement();
                                    stmt.executeUpdate(
                                            "INSERT INTO visitors(name, bdate, address, phone) VALUES ('" + name
                                                    + "','" + birthdate
                                                    + "','" + address
                                                    + "','" + phone + "')");
                                    JOptionPane.showMessageDialog(null, "Visitor added!");
                                    aFrame.dispose();

                                    ResultSet updatedRs = stmt.executeQuery(sql);
                                    visitorListTable.setModel(DbUtils.resultSetToTableModel(updatedRs));
                                } catch (SQLException ex) {
                                    JOptionPane.showMessageDialog(null, ex);
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "Phone must be a valid number.");
                            }
                        }
                    });

                    aFrame.add(labelPhone);
                    aFrame.add(createButton);
                    aFrame.add(labelName);
                    aFrame.add(labelBirthdate);
                    aFrame.add(labelAddress);
                    aFrame.add(F_name);
                    aFrame.add(F_address);
                    aFrame.add(F_phone);
                    aFrame.add(birthdatePicker); // Add the date picker
                    aFrame.setSize(450, 300);
                    aFrame.setLayout(null);
                    aFrame.setVisible(true);
                    aFrame.setLocationRelativeTo(null);
                }
            });

            // Button Delete Visitor
            JButton deleteVisitorButton = new JButton("Delete Visitor");
            deleteVisitorButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = visitorListTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        int option = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to delete this visitor?", "Confirmation",
                                JOptionPane.YES_NO_OPTION);
                        if (option == JOptionPane.YES_OPTION) {
                            int visitorID = (int) visitorListTable.getValueAt(selectedRow, 0);
                            Connection connection = Connect.connect();
                            try {
                                Statement stmt = connection.createStatement();
                                String deleteSQL = "DELETE FROM visitors WHERE id = " + visitorID;
                                stmt.executeUpdate(deleteSQL);
                                JOptionPane.showMessageDialog(null, "Visitor deleted!");
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null,
                                        "Error deleting the visitor: " + ex.getMessage());
                            }

                            try {
                                ResultSet updatedRs = stmt.executeQuery(sql);
                                visitorListTable.setModel(DbUtils.resultSetToTableModel(updatedRs));
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, ex);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select a visitor to delete.");
                    }
                }
            });

            // Tombol Pencarian
            JTextField searchIDField = new JTextField();
            searchIDField.setBounds(400, 5, 100, 27);
            JTextArea searchidaArea = new JTextArea("ID : ");
            searchidaArea.setBounds(380, 6, 80, 26);
            JButton searchButton = new JButton("Search");
            searchButton.setBounds(500, 5, 80, 26);
            searchButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String searchID = searchIDField.getText();
                    try {
                        String searchSql = "SELECT * FROM visitors WHERE id = ?";
                        PreparedStatement pstmt = connection.prepareStatement(searchSql);
                        pstmt.setInt(1, Integer.parseInt(searchID));
                        ResultSet searchResult = pstmt.executeQuery();
                        visitorListTable.setModel(DbUtils.resultSetToTableModel(searchResult));
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid ID Visitor.");
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error searching for visitor: " + ex.getMessage());
                    }
                }
            });

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            // Add search field and button
            vFrame.add(searchIDField);
            vFrame.add(searchButton);
            vFrame.add(searchidaArea);

            // Add the rest of the buttons
            buttonPanel.add(refreshButton);
            buttonPanel.add(addVisitorButton);
            buttonPanel.add(deleteVisitorButton);
            vFrame.add(buttonPanel, BorderLayout.NORTH);
            vFrame.add(scrollPane, BorderLayout.CENTER);
            vFrame.setSize(650, 350);
            vFrame.setVisible(true);
            vFrame.setLocationRelativeTo(null);
        } catch (SQLException e1) {
            JOptionPane.showMessageDialog(null, e1);
        }
    }

}
