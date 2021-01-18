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
public class UpdateAccountStatus {
    public static Connection con = null;
    public static PreparedStatement pst = null;
    public static ResultSet rs = null;

    public static void StartUpdate(
                                   String uid,
                                   String validate
                                   ) 
    {
        con = SQLCon.ConnectDB();
        String sql = "update users set u_validated='"+validate+"'"
                + "where u_information_id='"+uid+"'";
        try{
            pst = con.prepareStatement(sql);
            pst.executeUpdate();
            System.out.println("Status Successfully Updated!");
        }catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1014", JOptionPane.ERROR_MESSAGE);
        }
        
    }
}
