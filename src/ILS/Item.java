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
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;

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
        } catch (SQLException e) {
            Log.write("A Error occurred. \n"+" ".repeat(24)+"ERR Details-"+Item.class.getName()+" - "+e.getMessage());
            JOptionPane.showMessageDialog(null,"An unexpected Error. Try again or contact your administrator","Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void barrowItem(String id, String mid, String dueDate) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO borrow (ItemID, MemberID, BorrowDate, DueDate) VALUES ('" + id + "', '" + mid + "', CURDATE(), '" + dueDate + "')");
            statement.executeUpdate("UPDATE item SET Availability = '0' WHERE ItemId = '" + id + "'");
            String query = "SELECT BorrowID FROM borrow WHERE ItemID = '" + id + "' AND DueDate = '" + dueDate + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                JOptionPane.showMessageDialog(null, "The item is issued successfully. The borrow id is " + resultSet.getString("BorrowID"));
            }
        } catch (SQLException e) {
            Log.write("An error occurred. \n" + " ".repeat(24) + "ERR Details-" + Item.class.getName() + " - " + e.getMessage());
            JOptionPane.showMessageDialog(null, "An unexpected error occurred. Try again or contact your administrator", "Message", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
            } catch (SQLException e) {
                Log.write("An error occurred. \n" + " ".repeat(24) + "ERR Details-" + Item.class.getName() + " - " + e.getMessage());
                JOptionPane.showMessageDialog(null, "An unexpected error occurred. Try again or contact your administrator", "Message", JOptionPane.ERROR_MESSAGE);
            }
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
        } catch (SQLException e) {
            Log.write("A Error occurred. \n"+" ".repeat(24)+"ERR Details-"+Item.class.getName()+" - "+e.getMessage());
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
                return false;
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
                return null;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "An unexpected Error. Try again or contact your administrator", "Message", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public static String[] getBookBorrowDetails(String borrowId) throws SQLException {
        Connection cn = db.connect();
        String[] bookInfo = new String[10];
        String query = "SELECT i.Title, bk.Year, a.Name AS AuthorName, p.Name AS PublisherName, bk.ISBN, " +
                       "br.BorrowDate, br.DueDate " +
                       "FROM Borrow br " +
                       "JOIN Book bk ON bk.ItemId = br.ItemID " +
                       "JOIN Item i ON i.ItemId = br.ItemID " +
                       "JOIN Author a ON bk.AuthorId = a.AuthorId " +
                       "JOIN Publisher p ON bk.PublisherId = p.PublisherId " +
                       "WHERE br.BorrowID = ?";

        try (PreparedStatement stmt = cn.prepareStatement(query)) {
            stmt.setString(1, borrowId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                bookInfo[0] = "Book";
                bookInfo[1] = rs.getString("Title");
                bookInfo[2] = rs.getString("Year");
                bookInfo[3] = rs.getString("AuthorName");
                bookInfo[4] = rs.getString("PublisherName");
                bookInfo[5] = rs.getString("ISBN");
                bookInfo[6] = rs.getString("BorrowDate");
                bookInfo[7] = rs.getString("DueDate");

                return bookInfo;
            }
        }
        return null;
    }

    public static String[] getDVDBorrowDetails(String borrowId) throws SQLException {
        Connection cn = db.connect();
        String[] dvdInfo = new String[10];
        String query = "SELECT i.Title, d.Year, dr.Name AS DirectorName, dp.Name AS PublisherName, d.NumOfDVD, " +
                       "br.BorrowDate, br.DueDate " +
                       "FROM Borrow br " +
                       "JOIN DVD d ON d.ItemId = br.ItemID " +
                       "JOIN Item i ON i.ItemId = br.ItemID " +
                       "JOIN Director dr ON d.DirectorId = dr.DirectorId " +
                       "JOIN DVDPublisher dp ON d.PublisherId = dp.DPublisherId " +
                       "WHERE br.BorrowID = ?";

        try (PreparedStatement stmt = cn.prepareStatement(query)) {
            stmt.setString(1, borrowId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                dvdInfo[0] = "DVD";
                dvdInfo[1] = rs.getString("Title");
                dvdInfo[2] = rs.getString("Year");
                dvdInfo[3] = rs.getString("DirectorName");
                dvdInfo[4] = rs.getString("PublisherName");
                dvdInfo[5] = rs.getString("NumOfDVD");
                dvdInfo[6] = rs.getString("BorrowDate");
                dvdInfo[7] = rs.getString("DueDate");

                return dvdInfo;
            }
        }
        return null;
    }
    
    
    public static String getItemTypeByBorrowId(String borrowId) throws SQLException {
        Connection cn = db.connect();
        String query = "SELECT t.TypeName " +
                       "FROM Borrow br " +
                       "JOIN Item i ON br.ItemID = i.ItemId " +
                       "JOIN Type t ON i.TypeId = t.TypeId " +
                       "WHERE br.BorrowID = ?";
        try (PreparedStatement stmt = cn.prepareStatement(query)) {
            stmt.setString(1, borrowId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("TypeName");
            }
        }
        return null;
    }
    
    public static boolean isBorrowIdExists(String borrowId) throws SQLException {
        try {
            ResultSet rs = db.connect().createStatement().executeQuery("SELECT BorrowID FROM borrow WHERE BorrowID = '" + borrowId + "'");
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Enter a valid Borrow ID.", "Message", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return false;
    }

    public static boolean isItemIdExists(String itemId) throws SQLException {
        try {
            ResultSet rs = db.connect().createStatement().executeQuery("SELECT ItemID FROM item WHERE ItemID = '" + itemId + "'");
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Enter a valid Item ID.", "Message", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return false;
    }
    
    public static boolean isBook(String itemId) throws SQLException {
        boolean isBook = false;
        String query = "SELECT t.TypeName FROM item i JOIN type t ON i.TypeId = t.TypeId WHERE i.ItemId = ?";
        
        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, itemId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String typeName = rs.getString("TypeName");
                if ("Book".equalsIgnoreCase(typeName)) {
                    isBook = true;
                }
            }
        } catch (SQLException e) {
            Log.write("An error occurred. \n" + " ".repeat(24) + "ERR Details-" + Item.class.getName() + " - " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Something went wrong with your account. Try again or contact your administrator", "Message", JOptionPane.ERROR_MESSAGE);
        }
        
        return isBook;
    }
    
    public static String[] getBookDetailsByItemId(String itemId) throws SQLException {
        Connection cn = db.connect();
        String[] bookInfo = new String[10];
        String query = "SELECT i.Title, bk.Year, a.Name AS AuthorName, p.Name AS PublisherName, bk.ISBN " +
                       "FROM Book bk " +
                       "JOIN Item i ON i.ItemId = bk.ItemId " +
                       "JOIN Author a ON bk.AuthorId = a.AuthorId " +
                       "JOIN Publisher p ON bk.PublisherId = p.PublisherId " +
                       "WHERE bk.ItemId = '" + itemId + "'";

        try (Statement stmt = cn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                bookInfo[0] = "Book";
                bookInfo[1] = rs.getString("Title");
                bookInfo[2] = rs.getString("Year");
                bookInfo[3] = rs.getString("AuthorName");
                bookInfo[4] = rs.getString("PublisherName");
                bookInfo[5] = rs.getString("ISBN");

                return bookInfo;
            }
        }
        return null;
    }

    public static String[] getDVDDetailsByItemId(String itemId) throws SQLException {
        Connection cn = db.connect();
        String[] dvdInfo = new String[10];
        String query = "SELECT i.Title, d.Year, dr.Name AS DirectorName, dp.Name AS PublisherName, d.NumOfDVD " +
                       "FROM DVD d " +
                       "JOIN Item i ON i.ItemId = d.ItemId " +
                       "JOIN Director dr ON d.DirectorId = dr.DirectorId " +
                       "JOIN DVDPublisher dp ON d.PublisherId = dp.DPublisherId " +
                       "WHERE d.ItemId = '" + itemId + "'";

        try (Statement stmt = cn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                dvdInfo[0] = "DVD";
                dvdInfo[1] = rs.getString("Title");
                dvdInfo[2] = rs.getString("Year");
                dvdInfo[3] = rs.getString("DirectorName");
                dvdInfo[4] = rs.getString("PublisherName");
                dvdInfo[5] = rs.getString("NumOfDVD");

                return dvdInfo;
            }
        }
        return null;
    }
}
