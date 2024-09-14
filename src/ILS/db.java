package ILS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class db {
    public static Connection connect() {
            Connection conn = null;
            try {
                conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/ils", "root", "");
            } catch (SQLException e) {
                    Log.write("Error is occurred while establish the database connection.\n"+" ".repeat(24)+"ERR MEG -"+e.getMessage());
                    JOptionPane.showMessageDialog(null, "Failed to establish the database connection successfully. Try again or contact your administrator.","Message", JOptionPane.ERROR_MESSAGE);
            }
            return conn;
        }
}
