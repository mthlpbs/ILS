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
public class DVD extends Item {
    private final Connection connection = db.connect();
    private String director;
    private String publisher;
    private String num;
    private String releaseYear;
    
    public DVD(String title, String director, String publisher, String releaseYear, String NumOfDVD) {
        super(title, "T002");
        this.director = director;
        this.publisher = publisher;
        this.releaseYear = releaseYear;
        this.num=NumOfDVD;
        try {
            Statement statement = connection.createStatement();
            ResultSet directorResultSet = statement.executeQuery("SELECT DirectorId FROM director WHERE Name = '" + director + "'");
            String directorId;
            if (directorResultSet.next()) {
                directorId = directorResultSet.getString("DirectorId");
            } else {
                statement.executeUpdate("INSERT INTO director (Name) VALUES ('" + director + "')");
                ResultSet resultSet = statement.executeQuery("SELECT DirectorId FROM director WHERE Name = '" + director + "'");
                if (resultSet.next()) {
                    directorId = resultSet.getString("DirectorId");
                } else {
                    throw new SQLException("Failed to insert director, no ID obtained.");
                }
            }

            // Check if the DVD publisher exists
            ResultSet publisherResultSet = statement.executeQuery("SELECT DPublisherId FROM dvdpublisher WHERE Name = '" + publisher + "'");
            String publisherId;
            if (publisherResultSet.next()) {
                publisherId = publisherResultSet.getString("DPublisherId");
            } else {
                statement.executeUpdate("INSERT INTO dvdpublisher (Name) VALUES ('" + publisher + "')");
                ResultSet resultSet = statement.executeQuery("SELECT DPublisherId FROM dvdpublisher WHERE Name = '" + publisher + "'");
                if (resultSet.next()) {
                    publisherId = resultSet.getString("DPublisherId");
                } else {
                    throw new SQLException("Failed to insert DVD publisher, no ID obtained.");
                }
            }

            statement.executeUpdate("INSERT INTO dvd (ItemId, DirectorId, PublisherId, NumOfDVD, Year) VALUES ('" + super.id + "', '" + directorId + "', '" + publisherId + "', '" + num + "', '" + releaseYear + "')");
            ResultSet resultSet = statement.executeQuery("SELECT DRN FROM dvd WHERE ItemId = '" + super.id + "'");
            if (resultSet.next()) {
                String drn = resultSet.getString("DRN");
                System.out.println("DVD added with DRN: " + drn);
            }
        } catch (SQLException e) {
            Log.write("Error occurred while adding the DVD.\n" + " ".repeat(24) + "ERR MEG -" + e.getMessage());
            System.err.println("Failed to add the DVD. Try again or contact your administrator.");
        }
    }

    public static String[] getDetails(String id) {
        String[] details = new String[5];
        try {
            Connection connection = db.connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT DirectorId, PublisherId, NumOfDVD, Year FROM DVD WHERE ItemId = '" + id + "'");
            String directorId, publisherId;
            if (resultSet.next()) {
                directorId = resultSet.getString("DirectorId");
                publisherId = resultSet.getString("PublisherId");
                details[2] = resultSet.getString("NumOfDVD");
                details[3] = resultSet.getString("Year");
            } else {
                throw new SQLException("Failed to get DVD details.");
            }
            ResultSet directorResultSet = statement.executeQuery("SELECT Name FROM Director WHERE DirectorId = '" + directorId + "'");
            if (directorResultSet.next()) {
                details[0] = directorResultSet.getString("Name");
            } else {
                throw new SQLException("Failed to get director name.");
            }
            ResultSet publisherResultSet = statement.executeQuery("SELECT Name FROM DVDPublisher WHERE DPublisherId = '" + publisherId + "'");
            if (publisherResultSet.next()) {
                details[1] = publisherResultSet.getString("Name");
            } else {
                throw new SQLException("Failed to get publisher name.");
            }
            
            return details;
        } catch (SQLException e) {
            Log.write("Error occurred while retrieving DVD details.\n" + " ".repeat(24) + "ERR MEG -" + e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage() + ". Try again or contact your administrator", "Message", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    
}
