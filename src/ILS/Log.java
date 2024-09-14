package ILS;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

/**
 *
 * @author Mithila Prabashwara
 */
public class Log {
    
    private static File logFile;

    // Use to initialize the log file
    public static void iniLogFile() {
        String usrDir = System.getProperty("user.home");
        String appDir = usrDir + "\\.ExtersILS";
        File dir = new File(appDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        LocalDate dateD = LocalDate.now();
        LocalTime timeD = LocalTime.now();
        DateTimeFormatter timeFormatted = DateTimeFormatter.ofPattern("HH-mm-ss");
        String formattedTime = timeD.format(timeFormatted);
        logFile = new File(appDir + "\\" + "session " + dateD +"@" + formattedTime + ".txt");
    }

    // Use to write log statements
    public static void write(String statement) {
        LocalDate dateD = LocalDate.now();
        String date = dateD.toString();
        LocalTime timeD = LocalTime.now();
        DateTimeFormatter timeFormatted = DateTimeFormatter.ofPattern("HH:mm:ss");
        String time = timeD.format(timeFormatted);
        try {
            if (logFile == null) {
                iniLogFile();
            }
            try (FileWriter txtEditor = new FileWriter(logFile, true)) {
                txtEditor.write("["+date +"|"+ time +"]"+ " - " + statement + "\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, """
                                                An unexpected error occurred. Contact your administrator.
                                                ADMINREF:An IO error occurred while creating/updating the log file.
                                                ERR MEG -"""+e.getMessage());
            System.exit(0);
        }
    }

    // Main method
    public static void main(String[] args) {
        iniLogFile();
    }
}

