/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ILS;
/**
 *
 * @author Mithila Prabashwara
 */
public class Main {

    public static void main(String[] args) {
        if (Cache.checkFile()==true) {
            LogIn.autoLog();
        } else {
            LogIn login = new LogIn();
            login.setVisible(true);
        }
    }
}