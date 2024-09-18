/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ILS;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Mithila Prabashwara
 */
public class Book extends Item{
    private final Connection connection = db.connect();
    private String author;
    private String publisher;
    private String isbn;
    private String year;
    private String lang;
    
    public Book(String Ititle, String Iauthor, String Ipublisher, String Iisbn, String Iyear, String Ilang) {
        super(Ititle, "T001");
        this.author=Iauthor;
        this.publisher=Ipublisher;
        this.isbn=Iisbn;
        this.year=Iyear;
        this.lang=Ilang;
        try {
            Statement statement = connection.createStatement();
            ResultSet authorResultSet = statement.executeQuery("SELECT AuthorId FROM author WHERE Name = '" + author + "'");
            String authorId;
            if (authorResultSet.next()) {
                authorId = authorResultSet.getString("AuthorId");
            } else {
                statement.executeUpdate("INSERT INTO author (Name) VALUES ('" + author + "')");
                ResultSet ResultSet = statement.executeQuery("SELECT AuthorId FROM author WHERE Name = '" + author + "'");
                if (ResultSet.next()) {
                    authorId = ResultSet.getString(1);
                } else {
                    throw new SQLException("Failed to insert author, no ID obtained.");
                }
            }
            ResultSet publisherResultSet = statement.executeQuery("SELECT PublisherId FROM publisher WHERE Name = '" + publisher + "'");
            String publisherId;
            if (publisherResultSet.next()) {
                publisherId = publisherResultSet.getString("PublisherId");
            } else {
                statement.executeUpdate("INSERT INTO publisher (Name) VALUES ('" + publisher + "')");
                ResultSet ResultSet = statement.executeQuery("SELECT PublisherId FROM publisher WHERE Name = '" + publisher + "'");
                if (ResultSet.next()) {
                    publisherId = ResultSet.getString("PublisherId");
                } else {
                    throw new SQLException("Failed to insert publisher, no ID obtained.");
                }
            }
            statement.executeUpdate("INSERT INTO book (ItemId, AuthorId, PublisherId, ISBN, Year, Language) VALUES ('" + id + "', '" + authorId + "', '" + publisherId + "', '" + isbn + "', '" + year + "', '"+lang+"')");
        } catch (SQLException e) {
                Log.write("Error is occurred.\n"+" ".repeat(24)+"ERR MEG -"+e.getMessage());
                JOptionPane.showMessageDialog(null,e.getMessage()+". Try again or contact your administrator","Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static String[] getDetails(String id) {
        String[] details = new String[6];
        try {
            Connection connection = db.connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT AuthorId FROM book WHERE ItemId = '" + id + "'");
            String authorId;
            if (resultSet.next()) {
                authorId=resultSet.getString("AuthorId");
            } else {
                throw new SQLException("Failed to get author ID.");
            } ResultSet authorResultSet = statement.executeQuery("SELECT Name FROM author WHERE AuthorId = '" + authorId + "'");
            if (authorResultSet.next()) {
                details[0] = authorResultSet.getString("Name");
            } else {
                throw new SQLException("Failed to get author name.");
            } ResultSet yeaResultSet = statement.executeQuery("SELECT Year FROM book WHERE ItemId = '" + id + "'");
            if (yeaResultSet.next()) {
                details[1] = yeaResultSet.getString("Year");
            } else {
                throw new SQLException("Failed to get year.");
            } ResultSet isbnResultSet = statement.executeQuery("SELECT ISBN FROM book WHERE ItemId = '" + id + "'");
            if (isbnResultSet.next()) {
                details[2] = isbnResultSet.getString("ISBN");
            } else {
                throw new SQLException("Failed to get ISBN.");
            } ResultSet langResultSet = statement.executeQuery("SELECT Language FROM book WHERE ItemId = '" + id + "'");
            if (langResultSet.next()) {
                details[3] = langResultSet.getString("Language");
            } else {
                throw new SQLException("Failed to get language.");
            }
            return details;
        } catch (SQLException e) {
            Log.write("Error is occurred.\n"+" ".repeat(24)+"ERR MEG -"+e.getMessage());
            JOptionPane.showMessageDialog(null,e.getMessage()+". Try again or contact your administrator","Message", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

}