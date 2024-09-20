/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ILS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author Mithila Prabashwara
 */
public class Member {

    public static String addUser(String Name, String Email, String NIC, String Age, String TeleNum, String Addr) {
        try {
            Connection connection = db.connect();
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO member (Name, Tel, Email, NIC, Age, Address) VALUES ('"+Name+"', '"+TeleNum+"', '"+Email+"', '"+NIC+"', '"+Age+"', '"+Addr+"')");
            ResultSet resultSet = statement.executeQuery("SELECT MId FROM member WHERE NIC = '"+NIC+"'");
            if(resultSet.next()) {
                return resultSet.getString("MID");
            }
        } catch (SQLException e) {
            Log.write("Error is occurred while adding the user.\n"+" ".repeat(24)+"ERR MEG -"+e.getMessage());
            return "Failed to add the user. Try again or contact your administrator.";
        }
        return null;
    }
   
    public static boolean isMemIdExists(String id) throws SQLException {
        try {
            ResultSet rs = db.connect().createStatement().executeQuery("SELECT * FROM member WHERE MID = '"+id+"'");
            if (rs.next()) {
                return true;}
        } catch (SQLException e) {
                Log.write("A Error occurred. \n"+" ".repeat(24)+"ERR Details-"+Item.class.getName()+" - "+e.getMessage());
                JOptionPane.showMessageDialog(null, "Something is went wrong with your account. Try again or contact your administrator","Message", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return false;
    }
}
