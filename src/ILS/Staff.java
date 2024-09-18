/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ILS;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author Mithila Prabashwara
 */
public class Staff {
    private static final Connection connection = db.connect();
    private static String sid;

    public static void setSid(String id) {
        sid = id;
    }

    public static String getSid() {
        return sid;
    }

    public static String getName() {
        try {
            db.connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Name FROM staff WHERE SID = '"+sid+"'");
            if(resultSet.next()) {
                return resultSet.getString("Name");
            }
        } catch (SQLException e) {
            Log.write("Error is occurred while fetching the staff name.\n"+" ".repeat(24)+"ERR MEG -"+e.getMessage());
            JOptionPane.showMessageDialog(null, "Failed to fetch the staff name. Try again or contact your administrator.","Message", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    
    public static String addUser(String Name, String Email, String NIC, String TeleNum, String Pwd) {
        try {
            db.connect();
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO staff (Name, NIC, Tel, Email, Password) VALUES ('"+Name+"', '"+NIC+"', '"+TeleNum+"', '"+Email+"', '"+Pwd+"')");
            ResultSet resultSet = statement.executeQuery("SELECT SID FROM staff WHERE NIC = '"+NIC+"'");
            if(resultSet.next()) {
                return resultSet.getString("SID");
            }
        } catch (SQLException e) {
            Log.write("Error is occurred while adding the user.\n"+" ".repeat(24)+"ERR MEG -"+e.getMessage());
            return "Failed to add the user. Try again or contact your administrator.";
        }
        return null;
    }
}
