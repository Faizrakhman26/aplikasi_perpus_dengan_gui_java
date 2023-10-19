import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Login.login();
                splash.Splash();
                // adminmenu.admin_menu();
                // visitorsmenu VisitorsMenu = new visitorsmenu();
                // VisitorsMenu.VisitorsMenu();
                // borrowermenu BorrowerMenu = new borrowermenu();
                // BorrowerMenu.BorrowerMenu();
            }
        });
    }
}
