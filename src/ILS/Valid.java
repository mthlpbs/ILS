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

class InvalidMemRegCredentialsException extends Exception {
    public InvalidMemRegCredentialsException(String message) {
        super(message);
    }
}

class InvalidStaffRegCredentialsException extends Exception {
    public InvalidStaffRegCredentialsException(String message) {
        super(message);
    }
}

class InvalidBookCredentialsException extends Exception {
    public InvalidBookCredentialsException(String message) {
        super(message);
    }
}

class InvalidDVDCredentialsException extends Exception {
    public InvalidDVDCredentialsException(String message) {
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
    
    private static boolean checkNull(String input) {
        return input == null || input.isEmpty();
    }
    
    private static boolean checkSymbols(String input) {
        String regex = "[^a-zA-Z0-9]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }
    
    private static boolean checkName(String input) {
        String regex = "[^a-zA-Z0-9. ]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }
    
    private static boolean checkNum(String input) {
        String regex = "[^0-9]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }
    
    private static boolean checkPwd(String Pwd, String Cpwd) {
       return Pwd.matches(Cpwd);
    }

    //Used to check the login credentials and if the inputs are null or not in correct format throws a Exception
    public static void login(String id, String pass) throws InvalidLoginCredentialsException {
        if (checkNull(id) && checkNull(pass)) {throw new InvalidLoginCredentialsException("Please enter sid &  password.");}
        else if (checkNull(id)) { throw new InvalidLoginCredentialsException("Please enter a sid.");}
        else if (checkNull(pass)) { throw new InvalidLoginCredentialsException("Please enter a password.");}
        else if (!(id.length()==5)) { throw new InvalidLoginCredentialsException("Please enter a valid sid.");}
        else if (checkSymbols(id) && checkSymbols(pass)) { throw new InvalidLoginCredentialsException("The login credentials must contain only alphanumeric characters. Symbols are not allowed. Try again.");}
        else if (checkSymbols(id)) { throw new InvalidLoginCredentialsException("The SID must contain only alphanumeric characters. Symbols are not allowed. Try again.");}
        else if (checkSymbols(pass)) { throw new InvalidLoginCredentialsException("The password must contain only alphanumeric characters. Symbols are not allowed. Try again.");}
    }

    //Used to check member registration Details
    public static void member(String Name, String Email, String NIC, String Age, String TeleNum, String Addr) throws InvalidMemRegCredentialsException {
        String format = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
        if (checkNull(Name)) { throw new InvalidMemRegCredentialsException("Please enter a name."); }
        else if (checkNull(Email)) { throw new InvalidMemRegCredentialsException("Please enter an email address."); }
        else if (checkNull(NIC)) { throw new InvalidMemRegCredentialsException("Please enter a NIC."); }
        else if (checkNull(Age)) { throw new InvalidMemRegCredentialsException("Please enter an age."); }
        else if (checkNull(TeleNum)) { throw new InvalidMemRegCredentialsException("Please enter a telephone number."); }
        else if (checkNull(Addr)) { throw new InvalidMemRegCredentialsException("Please enter a address.");}
        else if (checkName(Name)) { throw new InvalidMemRegCredentialsException("The name must contain only alphanumeric characters. Symbols are not allowed. Try again."); }
        else if (checkNum(Age)) { throw new InvalidMemRegCredentialsException("The age must contain only numeric characters. Symbols are not allowed. Try again."); }
        else if (checkNum(NIC)) { throw new InvalidMemRegCredentialsException("The NIC must contain only alphanumeric characters. Symbols are not allowed. Try again."); }
        else if (checkNum(TeleNum)) { throw new InvalidMemRegCredentialsException("The telephone number must contain only numeric characters. Symbols are not allowed. Try again."); }
        else if (!Email.matches(format)) { throw new InvalidMemRegCredentialsException("Please enter a valid email address."); }
        else if (NIC.length()!=12) { throw new InvalidMemRegCredentialsException("Please enter a NIC."); }
        else if (TeleNum.length()!=10) { throw new InvalidMemRegCredentialsException("Please enter a phone number"); }
        else if (Integer.parseInt(Age)>0 && Integer.parseInt(Age)>=120) { throw new InvalidMemRegCredentialsException("Please enter a valid age. Try again."); }
    }

    //Used to check staff registration Details
    public static void staff(String Name, String Email, String NIC, String TeleNum, String Pwd, String CPwd) throws InvalidStaffRegCredentialsException {
        String format = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
        if (checkNull(Name)) { throw new InvalidStaffRegCredentialsException("Please enter a name."); }
        else if (checkNull(Email)) { throw new InvalidStaffRegCredentialsException("Please enter an email address."); }
        else if (checkNull(NIC)) { throw new InvalidStaffRegCredentialsException("Please enter a NIC."); }
        else if (checkNull(Pwd) || checkNull(CPwd)) { throw new InvalidStaffRegCredentialsException("Please enter an password."); }
        else if (checkNull(TeleNum)) { throw new InvalidStaffRegCredentialsException("Please enter a telephone number."); }
        else if (checkName(Name)) { throw new InvalidStaffRegCredentialsException("The name must contain only alphanumeric characters. Symbols are not allowed. Try again."); }
        else if (checkNum(NIC)) { throw new InvalidStaffRegCredentialsException("The NIC must contain only alphanumeric characters. Symbols are not allowed. Try again."); }
        else if (checkNum(TeleNum)) { throw new InvalidStaffRegCredentialsException("The telephone number must contain only numeric characters. Symbols are not allowed. Try again."); }
        else if (!Email.matches(format)) { throw new InvalidStaffRegCredentialsException("Please enter a valid email address."); }
        else if (NIC.length()!=12) { throw new InvalidStaffRegCredentialsException("Please enter a valid NIC."); }
        else if (TeleNum.length()!=10) { throw new InvalidStaffRegCredentialsException("Please enter a valid phone number"); }
        else if (!checkPwd(Pwd, CPwd)) { throw new InvalidStaffRegCredentialsException("The passwords do not match. Try again."); }
    }
    
    
    public static void book(String Ititle, String Iauthor, String Ipublisher, String Iisbn, String Iyear, String Ilang) throws InvalidBookCredentialsException {
        if (checkNull(Ititle)) { throw new InvalidBookCredentialsException("Please enter a title."); }
        if (checkNull(Iauthor)) { throw new InvalidBookCredentialsException("Please enter an author."); }
        if (checkNull(Ipublisher)) { throw new InvalidBookCredentialsException("Please enter a publisher."); }
        if (checkNull(Iisbn)) { throw new InvalidBookCredentialsException("Please enter an ISBN."); }
        if (checkNull(Iyear)) { throw new InvalidBookCredentialsException("Please enter a year."); }
        if (checkNull(Ilang)) { throw new InvalidBookCredentialsException("Please enter a language."); }
        String isbnFormat = "^(97(8|9))?\\d{9}(\\d|X)$";
        if (!Iisbn.matches(isbnFormat)) {
            throw new InvalidBookCredentialsException("Please enter a valid ISBN.");
        }
        
        // Check if year is a valid number
        try {
            int year = Integer.parseInt(Iyear);
            if (year <= 0) {
                throw new InvalidBookCredentialsException("Please enter a valid year.");
            }
        } catch (NumberFormatException e) {
            throw new InvalidBookCredentialsException("Please enter a valid year.");
        }
    }

    public static void dvd(String Ititle, String Idirector, String Iproducer, String Iyear, String IdvdNum) throws InvalidDVDCredentialsException {
        if (checkNull(Ititle)) { throw new InvalidDVDCredentialsException("Please enter a title."); }
        if (checkNull(Idirector)) { throw new InvalidDVDCredentialsException("Please enter a director."); }
        if (checkNull(Iproducer)) { throw new InvalidDVDCredentialsException("Please enter a producer."); }
        if (checkNull(Iyear)) { throw new InvalidDVDCredentialsException("Please enter a year."); }
        if (checkNull(IdvdNum)) { throw new InvalidDVDCredentialsException("Please enter a DVD number."); }
        if (checkName(Idirector)) { throw new InvalidDVDCredentialsException("The director must contain only alphanumeric characters. Symbols are not allowed. Try again."); }
        if (checkName(Iproducer)) { throw new InvalidDVDCredentialsException("The producer must contain only alphanumeric characters. Symbols are not allowed. Try again."); }
        if (checkNum(IdvdNum)) { throw new InvalidDVDCredentialsException("The DVD number must contain only numeric characters. Symbols are not allowed. Try again."); }
        if (checkNum(IdvdNum)) { throw new InvalidDVDCredentialsException("The DVD number must contain only numeric characters. Symbols are not allowed. Try again."); }
        try {
            int year = Integer.parseInt(Iyear);
            if (year <= 0) {
                throw new InvalidDVDCredentialsException("Please enter a valid year.");
            }
        } catch (NumberFormatException e) {
            throw new InvalidDVDCredentialsException("Please enter a valid year.");
        }
    }
    
    
}
