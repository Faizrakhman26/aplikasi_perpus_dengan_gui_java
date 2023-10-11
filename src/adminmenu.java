import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import net.proteanit.sql.DbUtils;
import java.awt.BorderLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Color;
import java.awt.Image;

public class adminmenu {
    public static void admin_menu() {

        // form Admin Menu
        JFrame f = new JFrame("MENU");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(700, 400);
        f.setLayout(null);

        // background
        ImageIcon backgroundImage = new ImageIcon("src/img/pxfuel.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 700, 400);

        // date and time
        JLabel dateTimeLabel = new JLabel();
        dateTimeLabel.setBounds(520, 10, 170, 20);
        dateTimeLabel.setForeground(Color.WHITE);
        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE , dd-MMM-yyyy | HH:mm:ss");
                Date now = new Date();
                dateTimeLabel.setText(dateFormat.format(now));
            }
        });
        timer.start();

        // Button View Book
        Icon viewBooksIcon = new ImageIcon(new ImageIcon("src/img/viewbook.png").getImage()
                .getScaledInstance(32, 32, Image.SCALE_DEFAULT));
        JButton viewBooksButton = new JButton(" VIEW BOOK", viewBooksIcon);
        viewBooksButton.setBounds(20, 10, 160, 40);
        viewBooksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame vFrame = new JFrame("BOOKS");

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

                    vFrame.add(buttonPanel, BorderLayout.NORTH);
                    vFrame.add(scrollPane, BorderLayout.CENTER);
                    vFrame.setSize(650, 350);
                    vFrame.setVisible(true);
                    vFrame.setLocationRelativeTo(null);
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, e1);
                }
            }
        });

        // Button View User
        Icon viewUserIcon = new ImageIcon(new ImageIcon("src/img/viewuser.png").getImage()
                .getScaledInstance(32, 32, Image.SCALE_DEFAULT));
        JButton viewUsersButton = new JButton(" VIEW USER  ", viewUserIcon);
        viewUsersButton.setBounds(20, 70, 160, 40);
        viewUsersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame f = new JFrame("USER LIST");

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

        // Button Update Book
        Icon updateBooksIcon = new ImageIcon(new ImageIcon("src/img/updatebook.png").getImage()
                .getScaledInstance(32, 32, Image.SCALE_DEFAULT));
        JButton updateBookButton = new JButton("UPDATE BOOK", updateBooksIcon);
        updateBookButton.setBounds(20, 130, 160, 40);
        updateBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame uFrame = new JFrame("UPDATE BOOK");
                uFrame.getContentPane().setBackground(new Color(0, 191, 255));

                JLabel labelId = new JLabel("Book Id");
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
                updateButton.setBounds(130, 175, 100, 30);

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
                                uFrame.dispose();
                            } catch (SQLException e1) {
                                JOptionPane.showMessageDialog(null, e1);
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Year must be a valid number.");
                        }

                    }
                });

                uFrame.add(updateButton);
                uFrame.add(labelId);
                uFrame.add(F_bid);
                uFrame.add(labelBname);
                uFrame.add(F_bname);
                uFrame.add(labelAuthor);
                uFrame.add(F_genre);
                uFrame.add(labelYear);
                uFrame.add(F_year);

                uFrame.setSize(450, 300);
                uFrame.setLayout(null);
                uFrame.setVisible(true);
                uFrame.setLocationRelativeTo(null);
            }
        });

        // Button Add Book
        Icon addBooksIcon = new ImageIcon(new ImageIcon("src/img/addbook.png").getImage()
                .getScaledInstance(32, 32, Image.SCALE_DEFAULT));
        JButton addBookButton = new JButton("  ADD BOOK      ", addBooksIcon);
        addBookButton.setBounds(20, 190, 160, 40);
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
                                        "INSERT INTO books(book_name, author, year) VALUES ('" + bname + "','" + author
                                                + "'," + year + ")");
                                JOptionPane.showMessageDialog(null, "Book added!");
                                aFrame.dispose();

                            } catch (SQLException e1) {
                                JOptionPane.showMessageDialog(null, e1);
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

        // Button Delete Book
        Icon dleteBooksIcon = new ImageIcon(new ImageIcon("src/img/deletebook.png").getImage()
                .getScaledInstance(32, 32, Image.SCALE_DEFAULT));
        JButton deleteBookButton = new JButton(" DELETE BOOK", dleteBooksIcon);
        deleteBookButton.setBounds(20, 250, 160, 40);
        deleteBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame g = new JFrame("DELETE");

                g.getContentPane().setBackground(new Color(0, 191, 255));
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
                g.setSize(380, 150);
                g.setLayout(null);
                g.setVisible(true);
                g.setLocationRelativeTo(null);

            }
        });

        // Button Logout
        Icon logoutIcon = new ImageIcon(new ImageIcon("src/img/logout.png").getImage()
                .getScaledInstance(32, 32, Image.SCALE_DEFAULT));
        JButton logoutButton = new JButton("  LOGOUT    ", logoutIcon);
        logoutButton.setBounds(20, 310, 160, 40);
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.dispose(); // Close the admin menu
                splash.Splash();
            }
        });

        f.add(dateTimeLabel);
        f.add(backgroundLabel);
        f.add(viewBooksButton);
        f.add(viewUsersButton);
        f.add(updateBookButton);
        f.add(addBookButton);
        f.add(deleteBookButton);
        f.add(logoutButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        f.setVisible(true);
        f.setLocationRelativeTo(null);

    }

}
