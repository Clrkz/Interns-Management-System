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
import javax.swing.JOptionPane;

/**
 *
 * @author Clrkz
 */
public class UpdateAccount {
    public static Connection con = null;
    public static PreparedStatement pst = null;
    public static ResultSet rs = null;

    public static void StartUpdate(
                                   String uid,
                                   String fname, 
                                   String mname, 
                                   String lname, 
                                   String suffix, 
                                   String gender, 
                                   String contact, 
                                   String email, 
                                   String role, 
                                   String uname, 
                                   String pass,
                                   String validate, 
                                   String status
                                   ) 
    {
        con = SQLCon.ConnectDB();
        String sql = "update users set u_firstname='"+fname+"',"
                + "u_middlename='"+mname+"',"
                + "u_lastname='"+lname+"',"
                + "u_suffixname= '"+suffix+"',"
                + "u_gender='"+gender+"',"
                + "u_phone_number='"+contact+"',"
                + "u_email='"+email+"',"
                + "u_role='"+role+"',"
                + "u_username='"+uname+"',"
                + "u_password='"+pass+"',"
                + "u_validated='"+validate+"',"
                + "u_status='"+status+"'"
                + "where u_information_id='"+uid+"'";
        try{
            pst = con.prepareStatement(sql);
            pst.executeUpdate();
            System.out.println("Successfully Updated!");
        }catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1014", JOptionPane.ERROR_MESSAGE);
        }
        
    }
}
