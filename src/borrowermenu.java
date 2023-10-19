import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import net.proteanit.sql.DbUtils;
import java.awt.BorderLayout;
import java.awt.Color;

import com.toedter.calendar.JDateChooser;

public class borrowermenu {
    public Connection connection;
    public JTable borrowerListTable;

    public void BorrowerMenu() {
        connection = Connect.connect();

        JFrame bFrame = new JFrame("BORROWER");
        Connection connection = Connect.connect();
        String sql = "SELECT * FROM borrower";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            borrowerListTable = new JTable();
            borrowerListTable.setModel(DbUtils.resultSetToTableModel(rs));
            JScrollPane scrollPane = new JScrollPane(borrowerListTable);

            JButton refreshButton = new JButton("Refresh");
            refreshButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        ResultSet updatedRs = stmt.executeQuery(sql);
                        borrowerListTable.setModel(DbUtils.resultSetToTableModel(updatedRs));
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    }
                }
            });

            // Add Borrower
            JButton addBorrowerButton = new JButton("Add Borrower");
            addBorrowerButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    addBorrowerForm();
                }
            });

            // delete borrower
            JButton deleteBorrowerButton = new JButton("Delete Borrower");
            deleteBorrowerButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = borrowerListTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        int option = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to delete this borrower?", "Confirmation",
                                JOptionPane.YES_NO_OPTION);
                        if (option == JOptionPane.YES_OPTION) {
                            int borrowerID = (int) borrowerListTable.getValueAt(selectedRow, 0);
                            Connection connection = Connect.connect();
                            try {
                                Statement stmt = connection.createStatement();
                                String deleteSQL = "DELETE FROM borrower WHERE id_borrower = " + borrowerID;
                                stmt.executeUpdate(deleteSQL);
                                JOptionPane.showMessageDialog(null, "Borrower deleted!");
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null,
                                        "Error deleting the borrower: " + ex.getMessage());
                            }

                            try {
                                ResultSet updatedRs = stmt.executeQuery(sql);
                                borrowerListTable.setModel(DbUtils.resultSetToTableModel(updatedRs));
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, ex);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select a borrower to delete.");
                    }
                }
            });

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(refreshButton);
            buttonPanel.add(addBorrowerButton);
            buttonPanel.add(deleteBorrowerButton);

            bFrame.add(buttonPanel, BorderLayout.NORTH);
            bFrame.add(scrollPane, BorderLayout.CENTER);
            bFrame.setSize(650, 350);
            bFrame.setVisible(true);
            bFrame.setLocationRelativeTo(null);
        } catch (SQLException e1) {
            JOptionPane.showMessageDialog(null, e1);
        }
    }

    private void addBorrowerForm() {
        JFrame addBorrowerFrame = new JFrame("Add Borrower");
        addBorrowerFrame.setSize(500, 400);
        addBorrowerFrame.setLocationRelativeTo(null);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBackground(new Color(0, 191, 255));// Set the layout to null

        // Dropdown menu untuk ID Visitor
        JLabel idVisitorLabel = new JLabel("ID Visitor");
        idVisitorLabel.setBounds(30, 15, 100, 30);
        JComboBox<String> idVisitorDropdown = new JComboBox<>();
        fillVisitorDropdown(idVisitorDropdown); // Isi dropdown dengan ID Visitor dari database
        idVisitorDropdown.setBounds(140, 15, 250, 30);
        JLabel visitorNameLabel = new JLabel("Visitor Name");
        visitorNameLabel.setBounds(30, 53, 100, 30);
        JTextField visitorNameField = new JTextField();
        visitorNameField.setBounds(140, 53, 250, 30);
        visitorNameField.setEditable(false);

        // Dropdown menu untuk ID Buku
        JLabel idBookLabel = new JLabel("ID Buku");
        idBookLabel.setBounds(30, 90, 100, 30);
        JComboBox<String> idBookDropdown = new JComboBox<>();
        fillBookDropdown(idBookDropdown); // Isi dropdown dengan ID Buku dari database
        idBookDropdown.setBounds(140, 90, 250, 30);
        JLabel bookTitleLabel = new JLabel("Book Name");
        bookTitleLabel.setBounds(30, 127, 100, 30);
        JTextField bookTitleField = new JTextField();
        bookTitleField.setBounds(140, 127, 250, 30);
        bookTitleField.setEditable(false);

        // Tambahkan tanggal peminjaman dan tanggal pengembalian
        JLabel borrowDateLabel = new JLabel("Borrow Date");
        borrowDateLabel.setBounds(30, 165, 100, 30);
        JDateChooser borrowDateChooser = new JDateChooser();
        borrowDateChooser.setBounds(140, 165, 250, 30);

        JLabel returnDateLabel = new JLabel("Return Date");
        returnDateLabel.setBounds(30, 202, 100, 30);
        JDateChooser returnDateChooser = new JDateChooser();
        returnDateChooser.setBounds(140, 202, 250, 30);

        // Listener saat memilih ID Visitor
        idVisitorDropdown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedVisitorID = Integer.parseInt(idVisitorDropdown.getSelectedItem().toString());
                    String visitorName = getVisitorNameFromDatabase(selectedVisitorID);
                    visitorNameField.setText(visitorName);
                } catch (NumberFormatException ex) {
                    // Handle jika konversi ke integer gagal
                    visitorNameField.setText("Invalid ID");
                }
            }
        });

        // Listener saat memilih ID Buku
        idBookDropdown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedBookID = Integer.parseInt(idBookDropdown.getSelectedItem().toString());
                    String bookTitle = getBookTitleFromDatabase(selectedBookID);
                    bookTitleField.setText(bookTitle);
                } catch (NumberFormatException ex) {
                    // Handle jika konversi ke integer gagal
                    bookTitleField.setText("Invalid ID");
                }
            }
        });

        formPanel.add(idVisitorLabel);
        formPanel.add(idVisitorDropdown);
        formPanel.add(visitorNameLabel);
        formPanel.add(visitorNameField);
        formPanel.add(idBookLabel);
        formPanel.add(idBookDropdown);
        formPanel.add(bookTitleLabel);
        formPanel.add(bookTitleField);
        formPanel.add(borrowDateLabel);
        formPanel.add(borrowDateChooser);
        formPanel.add(returnDateLabel);
        formPanel.add(returnDateChooser);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedVisitorID = Integer.parseInt(idVisitorDropdown.getSelectedItem().toString());
                int selectedBookID = Integer.parseInt(idBookDropdown.getSelectedItem().toString());
                java.sql.Date borrowDate = new java.sql.Date(borrowDateChooser.getDate().getTime());
                java.sql.Date returnDate = new java.sql.Date(returnDateChooser.getDate().getTime());

                addBorrowerToDatabase(selectedVisitorID, selectedBookID, borrowDate, returnDate);
                idVisitorDropdown.setSelectedIndex(0);
                idBookDropdown.setSelectedIndex(0);
                borrowDateChooser.setDate(null);
                returnDateChooser.setDate(null);

                JOptionPane.showMessageDialog(null, "Data Borrower berhasil ditambahkan!");
            }
        });

        addButton.setBounds(200, 240, 100, 30); // Set the position and size of the "Add" button
        formPanel.add(addButton);

        addBorrowerFrame.add(formPanel);
        addBorrowerFrame.setVisible(true);
    }

    private void addBorrowerToDatabase(int selectedVisitorID, int selectedBookID, Date borrowDate,
            Date returnDate) {
        try {
            // Buka koneksi ke database
            Connection connection = Connect.connect();

            // Siapkan query SQL untuk menambahkan data borrower
            String sql = "INSERT INTO borrower (id_visitor, id_book, borrower_date, return_date) VALUES (?, ?, ?, ?)";

            // Persiapkan statement SQL
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, selectedVisitorID);
            pstmt.setInt(2, selectedBookID);
            pstmt.setDate(3, borrowDate);
            pstmt.setDate(4, returnDate);

            // Jalankan perintah SQL untuk menambahkan data
            pstmt.executeUpdate();

            // Tutup statement dan koneksi
            pstmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal menambahkan borrower: " + e.getMessage());
        }
    }

    // Method untuk mengisi dropdown dengan ID Visitor dari database
    private void fillVisitorDropdown(JComboBox<String> dropdown) {
        try {
            Connection connection = Connect.connect();
            String sql = "SELECT id FROM visitors"; // Ganti sesuai dengan nama tabel yang sesuai

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String visitorID = rs.getString("id");
                dropdown.addItem(visitorID);
            }

            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mengisi dropdown ID Visitor: " + e.getMessage());
        }

    }

    // Method untuk mengisi dropdown dengan ID Buku dari database
    private void fillBookDropdown(JComboBox<String> dropdown) {
        try {
            Connection connection = Connect.connect();
            String sql = "SELECT id FROM books"; // Ganti sesuai dengan nama tabel yang sesuai

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String bookID = rs.getString("id");
                dropdown.addItem(bookID);
            }

            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mengisi dropdown ID Buku: " + e.getMessage());
        }
    }

    // Method untuk mendapatkan nama Visitor dari database berdasarkan ID Visitor
    private String getVisitorNameFromDatabase(int visitorID) {
        try {
            Connection connection = Connect.connect();
            String sql = "SELECT name FROM visitors WHERE id = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, visitorID);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("name");
            } else {
                return "Visitor not found"; // Atau nilai default sesuai kebutuhan
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mendapatkan nama Visitor: " + e.getMessage());
            return "Error";
        }
    }

    // Method untuk mendapatkan judul buku dari database berdasarkan ID Buku
    private String getBookTitleFromDatabase(int bookID) {
        try {
            Connection connection = Connect.connect();
            String sql = "SELECT book_name FROM books WHERE id = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, bookID);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("book_name");
            } else {
                return "Book not found"; // Atau nilai default sesuai kebutuhan
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mendapatkan judul buku: " + e.getMessage());
            return "Error";
        }
    }
}
