/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ILS;

import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Mithila Prabashwara
 */
public class Search {
    public static String[] algorithmOfHomeBox(String searchKey) {
        try {
            if (searchKey == null) {
                JOptionPane.showMessageDialog(null, "Search key cannot be null", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } catch (Exception e) {
                    Log.write("An error occurred. \n" + " ".repeat(24) + "ERR Details-" + Home.class.getName() + " - " + e.getMessage());
                    JOptionPane.showMessageDialog(null, "Search key cannot be null", "Message", JOptionPane.ERROR_MESSAGE);
        }

        String keywd = searchKey.trim().toUpperCase();
        String[] rn = new String[4];
        rn[0] = "";
        rn[1] = "";
        rn[2] = "";
        rn[3] = "";
        if (keywd.length() == 6) {
            if (keywd.startsWith("UG")) {
                try {
                    if (Item.isItemIdExists(keywd)) {
                        rn[1] = "1";
                        rn[0] = keywd;
                        return rn;
                    } else {
                        JOptionPane.showMessageDialog(null, "Please enter a valid ID");
                    }
                } catch (SQLException e) {
                    Log.write("An error occurred. \n" + " ".repeat(24) + "ERR Details-" + Home.class.getName() + " - " + e.getMessage());
                    JOptionPane.showMessageDialog(null, "Something went wrong with your account. Try again or contact your administrator", "Message", JOptionPane.ERROR_MESSAGE);
                }
            } else if (keywd.startsWith("ME")) {
                try {
                    if (Member.isMemIdExists(keywd)) {
                        rn[2] = "1";
                        rn[0] = keywd;
                        return rn;
                    } else {
                        JOptionPane.showMessageDialog(null, "Please enter a valid ID");
                    }
                } catch (SQLException e) {
                    Log.write("An error occurred. \n" + " ".repeat(24) + "ERR Details-" + Home.class.getName() + " - " + e.getMessage());
                    JOptionPane.showMessageDialog(null, "Something went wrong with your account. Try again or contact your administrator", "Message", JOptionPane.ERROR_MESSAGE);
                }
            } else if (keywd.startsWith("S")) {
                try {
                    if (Staff.isStaffIdExists(keywd)) {
                        rn[3] = "1";
                        rn[0] = keywd;
                        return rn;
                    } else {
                        JOptionPane.showMessageDialog(null, "Please enter a valid ID");
                    }
                } catch (SQLException e) {
                    Log.write("An error occurred. \n" + " ".repeat(24) + "ERR Details-" + Home.class.getName() + " - " + e.getMessage());
                    JOptionPane.showMessageDialog(null, "Something went wrong with your account. Try again or contact your administrator", "Message", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a valid ID");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a valid ID");
        }
        return rn;
    }
}