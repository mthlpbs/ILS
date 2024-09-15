/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ILS;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
/**
 *
 * @author Mithila Prabashwara
 */

class InvalidEmailException extends Exception {
    public InvalidEmailException(String message) {
        super(message);
    }
}

class InvalidLoginCredentialsException extends Exception {
    public InvalidLoginCredentialsException(String message) {
        super(message);
    }
}

class InvalidPasswordException extends Exception {
    public InvalidPasswordException(String message) {
        super(message);
    }
}

public class Valid {
    //Used to check the format of the email addresses and if it is not in correct format throws a Exception
    public static void formatEmail(String email) throws InvalidEmailException {
        String format = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
        if (!email.matches(format)) {
            throw new InvalidEmailException("Please enter a valid email address.");
        }
    }
    
    //Used to check the login credentials and if the inputs are null or not in correct format throws a Exception
    public static void login(String id, String pass) throws InvalidLoginCredentialsException {
        if ((id == null || id.isEmpty()) && (pass == null || pass.isEmpty())) {throw new InvalidLoginCredentialsException("Please enter sid &  password.");}
        else if (id == null || id.isEmpty()) { throw new InvalidLoginCredentialsException("Please enter a sid.");}
        else if (pass == null || pass.isEmpty()) { throw new InvalidLoginCredentialsException("Please enter a password.");}
        else if (!(id.length()==5)) { throw new InvalidLoginCredentialsException("Please enter a valid sid.");}
        else if (checkSymbols(id) && checkSymbols(pass)) { throw new InvalidLoginCredentialsException("The login credentials must contain only alphanumeric characters. Symbols are not allowed. Try again.");}
        else if (checkSymbols(id)) { throw new InvalidLoginCredentialsException("The SID must contain only alphanumeric characters. Symbols are not allowed. Try again.");}
        else if (checkSymbols(pass)) { throw new InvalidLoginCredentialsException("The password must contain only alphanumeric characters. Symbols are not allowed. Try again.");}
    }
    
    
    /**
    public static void password(String pass) throws InvalidPasswordException {
        if (pass == null || pass.isEmpty()) {
            throw new InvalidPasswordException("Please enter a password.");
        }
    } **/

    private static boolean checkSymbols(String input) {
        String regex = "[^a-zA-Z0-9]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }
}


/**
    public static void main(String[] args) {
        String testString = "Hello@World!";
        boolean hasSymbols = containsSymbols(testString);
        System.out.println("Does the string contain symbols? " + hasSymbols);
    }
} **/