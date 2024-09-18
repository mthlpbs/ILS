/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ILS;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Mithila Prabashwara
 */
public class Item {
    private static final Connection connection = db.connect();
    protected String title;
    protected String typeId;
    protected String availability;
    protected String id;
    
    public Item(String Ititle, String ItypeId) {
        title=Ititle;
        typeId=ItypeId;
        availability="1";
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO item (Title, TypeId, Availability) VALUES ('"+title+"', '"+typeId+"', '1')");
            ResultSet resultSet = statement.executeQuery("SELECT ItemId FROM item WHERE title = '"+title+"'");
            if(resultSet.next()) {
                id= resultSet.getString("ItemId");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null,"An unexpected Error. Try again or contact your administrator","Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static String barrowItem(String id, String mid, String dueDate) {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO borrow (ItemId, MemberId, BorrowDate, DueDate) VALUES ('" + id + "', '" + mid + "', CURDATE(), '" + dueDate + "')");
            statement.executeUpdate("UPDATE item SET Availability = '0' WHERE ItemId = '" + id + "'");
            return "Item borrowed successfully";
        } catch (SQLException ex) {
            Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, ex);
            return "An unexpected Error. Try again or contact your administrator";
        }
    }

    public static double returnItem(String borrowId) {
        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT ItemId, MemberId, DueDate FROM borrow WHERE BorrowID = '" + borrowId + "'");
            if (rs.next()) {
                String itemId = rs.getString("ItemId");
                String memberId = rs.getString("MemberId");
                LocalDate dueDate = rs.getDate("DueDate").toLocalDate();
                LocalDate returnDate = LocalDate.now();
                long daysExceeded = ChronoUnit.DAYS.between(dueDate, returnDate);
                double fine = daysExceeded > 0 ? daysExceeded * 20 : 0;

                statement.executeUpdate("INSERT INTO returnTable (ItemId, MemberId, ReturnDate, FineID) VALUES ('" + itemId + "', '" + memberId + "', CURDATE(), NULL)", Statement.RETURN_GENERATED_KEYS);
                ResultSet rsReturn = statement.getGeneratedKeys();
                String returnId = null;
                if (rsReturn.next()) {
                    returnId = rsReturn.getString(1);
                }

                if (fine > 0) {
                    statement.executeUpdate("INSERT INTO fine (Amount, FineDate, MemberID) VALUES (" + fine + ", CURDATE(), '" + memberId + "')", Statement.RETURN_GENERATED_KEYS);
                    ResultSet rsFine = statement.getGeneratedKeys();
                    String fineId = null;
                    if (rsFine.next()) {
                        fineId = rsFine.getString(1);
                    }
                    statement.executeUpdate("UPDATE returnTable SET FineID = '" + fineId + "' WHERE ReturnID = '" + returnId + "'");
                }
                statement.executeUpdate("UPDATE item SET Availability = '1' WHERE ItemId = '" + itemId + "'");
                return fine;
            } else {
                JOptionPane.showMessageDialog(null, "Borrow record not found", "Message", JOptionPane.ERROR_MESSAGE);
                return 0;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "An unexpected Error. Try again or contact your administrator", "Message", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }

    public static boolean checkItem(String itemId) {
        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT Availability FROM Item WHERE ItemId = '" + itemId + "'");
            if (rs.next()) {
                boolean availability = rs.getBoolean("Availability");
                return availability;
            } else {
                return false; // Item not found
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Enter a valid Item ID.", "Message", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void showItemId() {
        JOptionPane.showMessageDialog(null,"The item is added successfully. The id of "+title+" is "+id);
    }
    
    public static String[] getDetails(String itemId) {
        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM item WHERE ItemId = '" + itemId + "'");
            if (rs.next()) {
                String[] details = new String[4];
                details[0] = rs.getString("ItemId");
                details[1] = rs.getString("Title");
                details[2] = rs.getString("TypeId");
                details[3] = rs.getString("Availability");
                return details;
            } else {
                return null; // Item not found
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "An unexpected Error. Try again or contact your administrator", "Message", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
}
