/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ILS;

import java.util.Base64;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author Mithila Prabashwara
 */
public class Cache {
    
    public static boolean checkFile() {
        String usrDir = System.getProperty("user.home");
        File logFile = new File(usrDir + "\\.ExtersILS\\config.txt");
        return logFile.exists();
    }
    
    public static void saveUserLog(String id, String pwd) {
        String usrDir = System.getProperty("user.home");
        File logFile = new File(usrDir + "\\.ExtersILS\\config.txt");
        try {
            try (FileWriter Editor = new FileWriter(logFile, false)) {
                Editor.write(encrypt(id, pwd));
            }
        } catch (IOException e) {
            Log.write("Error is occurred.\n"+" ".repeat(24)+"ERR MEG -"+e.getMessage());
            JOptionPane.showMessageDialog(null, "An unexpected error occurred. Try again or contact your administrator","Message", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        
    }

    public static String[] retrieveUserData() {
        String usrDir = System.getProperty("user.home");
        File logFile = new File(usrDir + "\\.ExtersILS\\config.txt");
        try {
            String encodedData = new String(Files.readAllBytes(Paths.get(logFile.getPath())));
            return decrypt(encodedData);
        } catch (IOException e) {
            Log.write("Error is occurred.\n"+" ".repeat(24)+"ERR MEG -"+e.getMessage());
            JOptionPane.showMessageDialog(null, "Failed to retrive user data. Please sign in again.","Message", JOptionPane.ERROR_MESSAGE);
            Log.write("Auto login session is failed.");
            LogIn login = new LogIn();            
            login.setVisible(true);
            Log.write("The login page is started successfully.");
        }
        return null;
    }
    
    public static void clearUserCache() {
        String usrDir = System.getProperty("user.home");
        File logFile = new File(usrDir + "\\.ExtersILS\\config.txt");
        if (logFile.exists()) logFile.delete();
    }
    
    // Use Base64 as a encrypton method : LOL
    // Below method is used to encode the Base64 
    private static String encrypt(String id, String pwd) {
        String encodedId = Base64.getEncoder().encodeToString(id.getBytes());
        String encodedPwd = Base64.getEncoder().encodeToString(pwd.getBytes());
        String encryptedLol = encodedId + ":" + encodedPwd;
        return encryptedLol;
    }
    
    // Use to decode the Base64
    private static String[] decrypt(String encoded) {
        try {
            String[] parts = encoded.split(":"); // Split the encoded string using the delimiter
            String decodedId = new String(Base64.getDecoder().decode(parts[0]));
            String decodedPwd = new String(Base64.getDecoder().decode(parts[1]));
            return new String[]{decodedId, decodedPwd};
        } catch (Exception e) {
            Log.write("Error is occurred.\n"+" ".repeat(24)+"ERR MEG -"+e.getMessage());
            JOptionPane.showMessageDialog(null, "Failed to retrive user data. Please sign in again.","Message", JOptionPane.ERROR_MESSAGE);
            Log.write("Auto login session is failed.");
            LogIn login = new LogIn();            
            login.setVisible(true);
            Log.write("The login page is started successfully.");
        }
        return null;
    }

        // Use Base64 as a encrypton method : LOL
    // Below method is used to encode the Base64 
    public static String encryptPwd(String pwd) {
        return Base64.getEncoder().encodeToString(pwd.getBytes());
    }
    
    // Use to decode the Base64
    public static byte[] decryptPwd(String encoded) {
        return Base64.getDecoder().decode(encoded);
        }
}

