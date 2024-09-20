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

public class Fine {
    public double fineFee = 50.00;

    public void createFineRecord(String borrowId) {
        Connection conn = db.connect();
        if (conn != null) {
            try {
                Statement stmt = conn.createStatement();
                String insertFineSQL = "INSERT INTO fine (Amount) VALUES (0.00)";
                stmt.executeUpdate(insertFineSQL, Statement.RETURN_GENERATED_KEYS);
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    String fineId = generatedKeys.getString(1);
                    String updateBorrowSQL = "UPDATE borrow SET FineID = '" + fineId + "' WHERE BorrowID = '" + borrowId + "'";
                    stmt.executeUpdate(updateBorrowSQL);
                }
            } catch (SQLException e) {
                Log.write("A Error occurred. \n"+" ".repeat(24)+"ERR Details-"+Item.class.getName()+" - "+e.getMessage());
                JOptionPane.showMessageDialog(null, "Something is went wrong with your account. Try again or contact your administrator","Message", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    Log.write("A Error occurred. \n"+" ".repeat(24)+"ERR Details-"+Item.class.getName()+" - "+e.getMessage());
                    JOptionPane.showMessageDialog(null, "Something is went wrong with your account. Try again or contact your administrator","Message", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void updateFineRecord(String fineId) {
        Connection conn = db.connect();
        if (conn != null) {
            try {
                Statement stmt = conn.createStatement();
                String updateFineSQL = "UPDATE fine SET Amount = Amount + " + fineFee + " WHERE FineID = '" + fineId + "'";
                stmt.executeUpdate(updateFineSQL);
            } catch (SQLException e) {
                Log.write("A Error occurred. \n"+" ".repeat(24)+"ERR Details-"+Item.class.getName()+" - "+e.getMessage());
                JOptionPane.showMessageDialog(null, "Something is went wrong with your account. Try again or contact your administrator","Message", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    Log.write("A Error occurred. \n"+" ".repeat(24)+"ERR Details-"+Item.class.getName()+" - "+e.getMessage());
                    JOptionPane.showMessageDialog(null, "Something is went wrong with your account. Try again or contact your administrator","Message", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void checkAndUpdateFines() {
        Connection conn = db.connect();
        if (conn != null) {
            Statement stmt = null;
            Statement stmtReturn = null;
            ResultSet rs = null;
            ResultSet rsReturn = null;
            try {
                stmt = conn.createStatement();
                stmtReturn = conn.createStatement();
                String query = "SELECT BorrowID, BorrowDate, DueDate, FineID FROM borrow";
                rs = stmt.executeQuery(query);
                while (rs.next()) {
                    String borrowId = rs.getString("BorrowID");
                    LocalDate borrowDate = rs.getDate("BorrowDate").toLocalDate();
                    LocalDate dueDate = rs.getDate("DueDate").toLocalDate();
                    String fineId = rs.getString("FineID");
                    String returnQuery = "SELECT BorrowID FROM returntable WHERE BorrowID = '" + borrowId + "'";
                    rsReturn = stmtReturn.executeQuery(returnQuery);
                    if (!rsReturn.next()) {
                        long daysDifference = ChronoUnit.DAYS.between(dueDate, LocalDate.now());

                        if (daysDifference > 0) {
                            createFineRecord(borrowId);
                        } else if (daysDifference <= 0 && fineId != null) {
                            updateFineRecord(fineId);
                        }
                    }
                    rsReturn.close();
                }
            } catch (SQLException e) {
                Log.write("An error occurred. \n" + " ".repeat(24) + "ERR Details-" + Item.class.getName() + " - " + e.getMessage());
                JOptionPane.showMessageDialog(null, "Something went wrong with your account. Try again or contact your administrator", "Message", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (rsReturn != null) rsReturn.close();
                    if (rs != null) rs.close();
                    if (stmtReturn != null) stmtReturn.close();
                    if (stmt != null) stmt.close();
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    Log.write("An error occurred. \n" + " ".repeat(24) + "ERR Details-" + Item.class.getName() + " - " + e.getMessage());
                    JOptionPane.showMessageDialog(null, "Something went wrong with your account. Try again or contact your administrator", "Message", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    public static String[] checkMemberFineStatus(String memberId) throws SQLException {
        Connection connection = db.connect();
        String queryBorrow = "SELECT b.FineID " +
                             "FROM Borrow b " +
                             "WHERE b.MemberID = '" + memberId + "' " +
                             "AND b.ItemID NOT IN (SELECT r.ItemID FROM ReturnTable r WHERE r.MemberID = '" + memberId + "')";
        String queryFineTemplate = "SELECT f.Amount " +
                                   "FROM Fine f " +
                                   "WHERE f.FineID = '%s'";
        Statement statement = null;
        ResultSet rsBorrow = null;
        ResultSet rsFine = null;
        try {
            statement = connection.createStatement();
            rsBorrow = statement.executeQuery(queryBorrow);

            if (!rsBorrow.next()) {
                return new String[]{"no", "0.00"};
            } else {
                String fineId = rsBorrow.getString("FineID");

                if (fineId != null) {
                    String queryFine = String.format(queryFineTemplate, fineId);
                    rsFine = statement.executeQuery(queryFine);

                    if (rsFine.next()) {
                        double fineAmount = rsFine.getDouble("Amount");
                        if (fineAmount > 0) {
                            return new String[]{"yes", String.valueOf(fineAmount)};
                        }
                    }
                }
                return new String[]{"no", "0.00"};
            }
        } finally {
            if (rsFine != null) {
                rsFine.close();
            }
            if (rsBorrow != null) {
                rsBorrow.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}
