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

   public static boolean checkMemberBorrowStatus(String memberId) throws SQLException {
        Connection conn = db.connect();
        String queryBorrow = "SELECT b.ItemID, b.DueDate " +
                             "FROM Borrow b " +
                             "WHERE b.MemberID = ? " +
                             "AND b.ItemID NOT IN (SELECT r.ItemID FROM ReturnTable r WHERE r.MemberID = ?)";
        String queryReturn = "SELECT r.ReturnDate, f.Amount " +
                             "FROM ReturnTable r " +
                             "LEFT JOIN Fine f ON r.FineID = f.FineID " +
                             "WHERE r.MemberID = ? AND r.ItemID = ?";

        try (PreparedStatement borrowStmt = conn.prepareStatement(queryBorrow)) {
            borrowStmt.setString(1, memberId);
            borrowStmt.setString(2, memberId);

            ResultSet rsBorrow = borrowStmt.executeQuery();
            if (!rsBorrow.next()) {
                return true;
            } else {
                String itemId = rsBorrow.getString("ItemID");
                java.sql.Date dueDate = rsBorrow.getDate("DueDate");

                try (PreparedStatement returnStmt = conn.prepareStatement(queryReturn)) {
                    returnStmt.setString(1, memberId);
                    returnStmt.setString(2, itemId);

                    ResultSet rsReturn = returnStmt.executeQuery();

                    if (rsReturn.next()) {
                        java.sql.Date returnDate = rsReturn.getDate("ReturnDate");
                        double fineAmount = rsReturn.getDouble("Amount");
                        if (returnDate.after(dueDate)) {
                            return fineAmount == 0;
                        } else {
                            return true;
                        }
                    } else {
                        return false;
                    }
                }
            }
        }
    }
}
