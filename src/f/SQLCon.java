/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package f;

import home.Home;
import java.awt.Window;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import javax.swing.*;

/**
 *
 * @author Admin
 */
public class SQLCon {

    public static String server = "";
    public static String user = "";
    public static String pass = "";
    public static boolean connected = true;
    static int counter = 0;
    Connection conn = null;

    /*
    public static void main(String args[]){
ConnectDB();
    }
    
     */

    public static Connection ConnectDB() {

        String directory = System.getProperty("user.home");
        String defaultFolder = "IMS";
        String fileName0 = "cdpo-data-00";
        String fileName1 = "cdpo-data-01";
        String fileName2 = "cdpo-data-02";
        String absolutePath0 = directory + File.separator + defaultFolder + File.separator + fileName0;
        String absolutePath1 = directory + File.separator + defaultFolder + File.separator + fileName1;
        String absolutePath2 = directory + File.separator + defaultFolder + File.separator + fileName2;

        try (FileReader fileReader = new FileReader(absolutePath0)) {
            int ch = fileReader.read();
            server = "";
            while (ch != -1) {
                server = server + String.valueOf((char) ch);
                ch = fileReader.read();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
// exception handling 
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileReader fileReader = new FileReader(absolutePath1)) {
            int ch = fileReader.read();
            user = "";
            while (ch != -1) {
                user = user + String.valueOf((char) ch);
                ch = fileReader.read();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
// exception handling 
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileReader fileReader = new FileReader(absolutePath2)) {
            int ch = fileReader.read();
            pass = "";
            while (ch != -1) {
                pass = pass + String.valueOf((char) ch);
                ch = fileReader.read();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
// exception handling 
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    // Util.get("575865423944536b4c7a476e362b51714c3555464e7843744d377767536b6c356630736962335557797a343d"),
                    //Util.get("6a4f64385145666243635653335a4961673748307a773d3d"),
                    //Util.get("6872693737494f327155616744796b546977687a54673d3d")      
                    Util.get(server),
                    //"jdbc:mysql://192.168.137.1:3306/cdpo_db",
                    Util.get(user),
                    Util.get(pass)
            //Util.get("6872693737494f327155616744796b546977687a54673d3d")      
            );
            //JOptionPane.showMessageDialog(null, "Connected to Database");
            counter++;

            System.out.println(counter + ". Connected to DB");
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            if (connected){
                connected = false;
                if(ClassDisconnected.isOpen==false){ 
                SystemConnection sc = new SystemConnection();
                sc.show(); 
                }
            }
            //   UIManagers.getNewUI();
            //   //    System.out.println("Error: "+e);
            //.showMessageDialog(null, "Error has occured...\nContact your administrator","Can't Connect to the Server",JOptionPane.ERROR_MESSAGE);
            //System.exit(0);
            // UIManagers.applyOldUI();
            return null;
        }
    }
    
    
}
