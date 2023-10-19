import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import net.proteanit.sql.DbUtils;
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
        JButton viewBooksButton = new JButton(" BOOK MENU", viewBooksIcon);
        viewBooksButton.setBounds(20, 10, 180, 40);
        viewBooksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bookmenu bookMenu = new bookmenu();
                bookMenu.Bookmenu();
            }
        });

        // Button View User
        Icon viewUserIcon = new ImageIcon(new ImageIcon("src/img/viewuser.png").getImage()
                .getScaledInstance(32, 32, Image.SCALE_DEFAULT));
        JButton viewUsersButton = new JButton(" VIEW USER  ", viewUserIcon);
        viewUsersButton.setBounds(20, 70, 180, 40);
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

        // Button Logout
        Icon logoutIcon = new ImageIcon(new ImageIcon("src/img/logout.png").getImage()
                .getScaledInstance(32, 32, Image.SCALE_DEFAULT));
        JButton logoutButton = new JButton("  LOGOUT    ", logoutIcon);
        logoutButton.setBounds(20, 250, 180, 40);
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.dispose(); // Close the admin menu
                splash.Splash();
            }
        });

        // Button View Visitors
        Icon viewVisitorsIcon = new ImageIcon(new ImageIcon("src/img/visitor.png").getImage()
                .getScaledInstance(32, 32, Image.SCALE_DEFAULT));
        JButton viewVisitorsButton = new JButton("VISITORS MENU", viewVisitorsIcon);
        viewVisitorsButton.setBounds(20, 130, 180, 40);
        viewVisitorsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                visitorsmenu VisitorsMenu = new visitorsmenu();
                VisitorsMenu.VisitorsMenu();
            }
        });

        // Button Add Borrower
        Icon addBorrowerIcon = new ImageIcon(new ImageIcon("src/img/borrow.png").getImage()
                .getScaledInstance(32, 32, Image.SCALE_DEFAULT));
        JButton addBorrowerButton = new JButton("BORROWER MENU", addBorrowerIcon);
        addBorrowerButton.setBounds(20, 190, 180, 40);
        addBorrowerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                borrowermenu BorrowerMenu = new borrowermenu();
                BorrowerMenu.BorrowerMenu();
            }
        });

        f.add(dateTimeLabel);
        f.add(backgroundLabel);
        f.add(viewBooksButton);
        f.add(viewUsersButton);
        f.add(logoutButton);
        f.add(viewVisitorsButton);
        f.add(addBorrowerButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        f.setVisible(true);
        f.setLocationRelativeTo(null);

    }

}
