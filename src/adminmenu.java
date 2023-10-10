import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import net.proteanit.sql.DbUtils;
import java.awt.BorderLayout;

public class adminmenu {
    public static void admin_menu() {

        JFrame f = new JFrame("Admin");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(700, 400);

        JButton viewBooksButton = new JButton("View Books");
        viewBooksButton.setBounds(20, 20, 150, 40);
        viewBooksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame f = new JFrame("Books");

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

                    JPanel buttonPanel = new JPanel();
                    buttonPanel.add(refreshButton);

                    f.add(buttonPanel, BorderLayout.NORTH);
                    f.add(scrollPane, BorderLayout.CENTER);
                    f.setSize(600, 300);
                    f.setVisible(true);
                    f.setLocationRelativeTo(null);
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, e1);
                }
            }
        });

        JButton viewUsersButton = new JButton("View Users");
        viewUsersButton.setBounds(20, 80, 150, 40);
        viewUsersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame f = new JFrame("Users List");

                Connection connection = Connect.connect();
                String sql = "SELECT * FROM users";
                try {
                    Statement stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    JTable userListTable = new JTable();
                    userListTable.setModel(DbUtils.resultSetToTableModel(rs));
                    JScrollPane scrollPane = new JScrollPane(userListTable);

                    f.add(scrollPane);
                    f.setSize(500, 250);
                    f.setVisible(true);
                    f.setLocationRelativeTo(null);
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, e1);
                }
            }
        });

        JButton updateBookButton = new JButton("Update Book");
        updateBookButton.setBounds(20, 140, 150, 40);
        updateBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame g = new JFrame("Update Book");

                JLabel labelId = new JLabel("Book ID(id)");
                labelId.setBounds(30, 15, 100, 30);
                JTextField F_bid = new JTextField();
                F_bid.setBounds(130, 15, 250, 30);

                JLabel labelBname = new JLabel("Book Name");
                labelBname.setBounds(30, 55, 100, 30);
                JTextField F_bname = new JTextField();
                F_bname.setBounds(130, 55, 250, 30);

                JLabel labelAuthor = new JLabel("Author");
                labelAuthor.setBounds(30, 95, 100, 30);
                JTextField F_genre = new JTextField();
                F_genre.setBounds(130, 95, 250, 30);

                JLabel labelYear = new JLabel("Year");
                labelYear.setBounds(30, 135, 100, 30);
                JTextField F_year = new JTextField();
                F_year.setBounds(130, 135, 250, 30);

                JButton updateButton = new JButton("Update");
                updateButton.setBounds(130, 170, 100, 25);

                updateButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String bid = F_bid.getText();
                        String bname = F_bname.getText();
                        String author = F_genre.getText();
                        String yearStr = F_year.getText();

                        try {
                            int year = Integer.parseInt(yearStr);

                            Connection connection = Connect.connect();

                            try {
                                Statement stmt = connection.createStatement();
                                stmt.executeUpdate("UPDATE books SET book_name='" + bname + "', author='" + author
                                        + "', year=" + year + " WHERE id=" + bid);
                                JOptionPane.showMessageDialog(null, "Book updated!");
                                g.dispose();
                            } catch (SQLException e1) {
                                JOptionPane.showMessageDialog(null, e1);
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Year must be a valid number.");
                        }

                    }
                });

                g.add(updateButton);
                g.add(labelId);
                g.add(F_bid);
                g.add(labelBname);
                g.add(F_bname);
                g.add(labelAuthor);
                g.add(F_genre);
                g.add(labelYear);
                g.add(F_year);

                g.setSize(450, 300);
                g.setLayout(null);
                g.setVisible(true);
                g.setLocationRelativeTo(null);
            }
        });

        JButton addBookButton = new JButton("Add Book");
        addBookButton.setBounds(20, 200, 150, 40);
        addBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JFrame g = new JFrame("Add New Book");

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
                                        "INSERT INTO books(book_name, author, year) VALUES ('" + bname + "','" + author
                                                + "'," + year + ")");
                                JOptionPane.showMessageDialog(null, "Book added!");
                                g.dispose();

                            } catch (SQLException e1) {
                                JOptionPane.showMessageDialog(null, e1);
                            }

                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Year must be a valid number.");
                        }

                    }
                });

                g.add(labelYear);
                g.add(createButton);
                g.add(labelBname);
                g.add(labelAuthor);
                g.add(F_bname);
                g.add(F_author);
                g.add(F_year);
                g.setSize(450, 300);
                g.setLayout(null);
                g.setVisible(true);
                g.setLocationRelativeTo(null);
            }
        });

        JButton deleteBookButton = new JButton("Delete Book");
        deleteBookButton.setBounds(20, 260, 150, 40);
        deleteBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame g = new JFrame("Delete Book");

                JLabel labelId;
                labelId = new JLabel("Book ID(id)");
                labelId.setBounds(30, 15, 100, 30);

                JTextField F_bid = new JTextField();
                F_bid.setBounds(130, 15, 200, 30);

                JButton deleteButton = new JButton("Delete");
                deleteButton.setBounds(130, 70, 80, 25);
                deleteButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String bid = F_bid.getText();

                        Connection connection = Connect.connect();

                        try {
                            Statement stmt = connection.createStatement();
                            stmt.executeUpdate("DELETE FROM books WHERE id=" + bid);
                            JOptionPane.showMessageDialog(null, "Book deleted!");
                            g.dispose();
                        } catch (SQLException e1) {
                            JOptionPane.showMessageDialog(null, e1);
                        }
                    }
                });

                g.add(deleteButton);
                g.add(labelId);
                g.add(F_bid);
                g.setSize(350, 150);
                g.setLayout(null);
                g.setVisible(true);
                g.setLocationRelativeTo(null);

            }
        });

        f.add(viewBooksButton);
        f.add(viewUsersButton);
        f.add(updateBookButton);
        f.add(addBookButton);
        f.add(deleteBookButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        f.setLayout(null);
        f.setVisible(true);
        f.setLocationRelativeTo(null);

    }
}
