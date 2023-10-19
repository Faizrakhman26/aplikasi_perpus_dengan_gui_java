import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import net.proteanit.sql.DbUtils;
import java.awt.BorderLayout;
import java.awt.Color;

public class bookmenu {
    public Connection connection;
    public JTable bookListTable;

    public void Bookmenu() {
        connection = Connect.connect();

        JFrame vFrame = new JFrame("BOOKS");
        vFrame.getContentPane().setBackground(new Color(0, 191, 255));

        Connection connection = Connect.connect();
        String sql = "SELECT * FROM books";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            JTable bookListTable = new JTable();
            bookListTable.setModel(DbUtils.resultSetToTableModel(rs));
            JScrollPane scrollPane = new JScrollPane(bookListTable);

            JButton refreshButton = new JButton("Refresh");
            refreshButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        ResultSet updatedRs = stmt.executeQuery(sql);
                        bookListTable.setModel(DbUtils.resultSetToTableModel(updatedRs));
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    }
                }
            });

            // Button Add Book
            JButton addBookButton = new JButton("Add Book");
            addBookButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    JFrame aFrame = new JFrame("ADD NEW BOOK");
                    aFrame.getContentPane().setBackground(new Color(0, 191, 255));

                    JLabel labelBname, labelAuthor, labelYear;
                    labelBname = new JLabel("Book Name");
                    labelBname.setBounds(30, 15, 100, 30);

                    labelAuthor = new JLabel("Author");
                    labelAuthor.setBounds(30, 53, 100, 30);

                    labelYear = new JLabel("Year");
                    labelYear.setBounds(30, 90, 100, 30);

                    JTextField F_bname, F_author, F_year;
                    F_bname = new JTextField();
                    F_bname.setBounds(110, 15, 250, 30);

                    F_author = new JTextField();
                    F_author.setBounds(110, 53, 250, 30);

                    F_year = new JTextField();
                    F_year.setBounds(110, 90, 250, 30);

                    JButton createButton = new JButton("Submit");
                    createButton.setBounds(110, 130, 80, 25);
                    createButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {

                            String bname = F_bname.getText();
                            String author = F_author.getText();
                            String yearStr = F_year.getText();

                            try {
                                int year = Integer.parseInt(yearStr);

                                Connection connection = Connect.connect();

                                try {
                                    Statement stmt = connection.createStatement();
                                    stmt.executeUpdate(
                                            "INSERT INTO books(book_name, author, year) VALUES ('" + bname
                                                    + "','" + author
                                                    + "'," + year + ")");
                                    JOptionPane.showMessageDialog(null, "Book added!");
                                    aFrame.dispose();

                                    ResultSet updatedRs = stmt.executeQuery(sql);
                                    bookListTable.setModel(DbUtils.resultSetToTableModel(updatedRs));
                                } catch (SQLException ex) {
                                    JOptionPane.showMessageDialog(null, ex);
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "Year must be a valid number.");
                            }

                        }
                    });

                    aFrame.add(labelYear);
                    aFrame.add(createButton);
                    aFrame.add(labelBname);
                    aFrame.add(labelAuthor);
                    aFrame.add(F_bname);
                    aFrame.add(F_author);
                    aFrame.add(F_year);
                    aFrame.setSize(450, 300);
                    aFrame.setLayout(null);
                    aFrame.setVisible(true);
                    aFrame.setLocationRelativeTo(null);
                }
            });

            // Button Update Book
            JButton updateBookButton = new JButton("Update Book");
            updateBookButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = bookListTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        // Get the selected book's information from the table
                        String bookName = (String) bookListTable.getValueAt(selectedRow, 1);
                        String author = (String) bookListTable.getValueAt(selectedRow, 2);
                        int year = (int) bookListTable.getValueAt(selectedRow, 3);

                        JFrame updateFrame = new JFrame("UPDATE BOOK");
                        updateFrame.getContentPane().setBackground(new Color(0, 191, 255));
                        JLabel labelBname, labelAuthor, labelYear;
                        labelBname = new JLabel("Book Name");
                        labelBname.setBounds(30, 15, 100, 30);
                        labelAuthor = new JLabel("Author");
                        labelAuthor.setBounds(30, 53, 100, 30);
                        labelYear = new JLabel("Year");
                        labelYear.setBounds(30, 90, 100, 30);

                        JTextField F_bname, F_author, F_year;
                        F_bname = new JTextField();
                        F_bname.setBounds(110, 15, 250, 30);
                        F_bname.setText(bookName); // Pre-fill with existing data
                        F_author = new JTextField();
                        F_author.setBounds(110, 53, 250, 30);
                        F_author.setText(author); // Pre-fill with existing data
                        F_year = new JTextField();
                        F_year.setBounds(110, 90, 250, 30);
                        F_year.setText(String.valueOf(year)); // Pre-fill with existing data

                        JButton updateButton = new JButton("Update");
                        updateButton.setBounds(110, 130, 80, 25);
                        updateButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                String newBookName = F_bname.getText();
                                String newAuthor = F_author.getText();
                                String newYearStr = F_year.getText();

                                try {
                                    int newYear = Integer.parseInt(newYearStr);
                                    Connection connection = Connect.connect();
                                    try {
                                        Statement stmt = connection.createStatement();
                                        stmt.executeUpdate("UPDATE books SET book_name = '" + newBookName
                                                + "', author = '" + newAuthor + "', year = " + newYear
                                                + " WHERE book_name = '" + bookName + "'");
                                        JOptionPane.showMessageDialog(null, "Book updated!");
                                        updateFrame.dispose();

                                        ResultSet updatedRs = stmt.executeQuery(sql);
                                        bookListTable.setModel(DbUtils.resultSetToTableModel(updatedRs));
                                    } catch (SQLException e1) {
                                        JOptionPane.showMessageDialog(null, e1);
                                    }

                                } catch (NumberFormatException ex) {
                                    JOptionPane.showMessageDialog(null, "Year must be a valid number.");
                                }
                            }
                        });

                        updateFrame.add(labelYear);
                        updateFrame.add(updateButton);
                        updateFrame.add(labelBname);
                        updateFrame.add(labelAuthor);
                        updateFrame.add(F_bname);
                        updateFrame.add(F_author);
                        updateFrame.add(F_year);
                        updateFrame.setSize(450, 300);
                        updateFrame.setLayout(null);
                        updateFrame.setVisible(true);
                        updateFrame.setLocationRelativeTo(null);
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select a book to update.");
                    }

                }
            });

            // Button Delete Book
            JButton deleteBookButton = new JButton("Delete Book");
            deleteBookButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = bookListTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        int option = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to delete this book?", "Confirmation",
                                JOptionPane.YES_NO_OPTION);
                        if (option == JOptionPane.YES_OPTION) {
                            // Step 1: Get the unique identifier (e.g., book ID) from the table
                            int bookID = (int) bookListTable.getValueAt(selectedRow, 0);

                            // Step 2: Delete the selected book from the database
                            Connection connection = Connect.connect();
                            try {
                                Statement stmt = connection.createStatement();
                                String deleteSQL = "DELETE FROM books WHERE id = " + bookID;
                                stmt.executeUpdate(deleteSQL);
                                JOptionPane.showMessageDialog(null, "Book deleted!");
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null,
                                        "Error deleting the book: " + ex.getMessage());
                            }

                            // Step 3: Refresh the JTable to reflect the changes
                            try {
                                ResultSet updatedRs = stmt.executeQuery(sql);
                                bookListTable.setModel(DbUtils.resultSetToTableModel(updatedRs));
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, ex);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select a book to delete.");
                    }
                }
            });

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(refreshButton);
            buttonPanel.add(addBookButton);
            buttonPanel.add(updateBookButton);
            buttonPanel.add(deleteBookButton);

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
