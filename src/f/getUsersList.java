/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package f;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Clrkz
 */
public class getUsersList {
    public static Connection con = null;
    public static PreparedStatement pst = null;
    public static ResultSet rs = null;
    public static void get() 
    {
        con = SQLCon.ConnectDB();
        String sql = "SELECT `u_information_id` as `User ID`,\n" +
"concat(`u_firstname`,' ',`u_middlename`,' ',`u_lastname`,' ',`u_suffixname`) as Name,\n" +
"`u_username` as Username,\n" +
"(SELECT gender FROM gender where id=users.u_gender) as Gender,\n" +
"`u_phone_number` as `Contact no.`,\n" +
"`u_validated` as Status\n" +
"FROM users\n" +
"where `u_information_id` != 0\n" +
"order by `u_information_id` DESC";
        try{
              pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
               // home.Home.jTable4.setModel(DbUtils.resultSetToTableModel(rs));
          
        }catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1021", JOptionPane.ERROR_MESSAGE);
        }
        
    }
}
