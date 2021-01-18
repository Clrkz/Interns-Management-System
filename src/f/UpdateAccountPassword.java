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
public class UpdateAccountPassword {
    public static Connection con = null;
    public static PreparedStatement pst = null;
    public static ResultSet rs = null;

    public static void StartUpdate(
                                   String uid,
                                   String password
                                   ) 
    {
        con = SQLCon.ConnectDB();
        String sql = "update users set u_password='"+password+"'"
                + "where u_information_id='"+uid+"'";
        try{
            pst = con.prepareStatement(sql);
            pst.executeUpdate();
            insertUpdateAudit();
            System.out.println("Password Successfully Updated!");
        }catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1014", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    
     public static void insertUpdateAudit(){
         AuditMessage am = new AuditMessage();
        String sql = "insert into audit (u_information_id,audit_activity,audit_description) values (?,?,?)";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, SystemInfo.userid);
            pst.setString(2, am.updateAccount);
            pst.setString(3, SelectedUserAccount.ufirstname+" "+SelectedUserAccount.umiddlename+" "+SelectedUserAccount.ulastname+" "+SelectedUserAccount.usuffix);
            pst.executeUpdate(); 
        UIManagers.getNewUI();
             JOptionPane.showMessageDialog(null, "Account password successfully updated..."); 
        UIManagers.applyOldUI();
         
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1016", JOptionPane.ERROR_MESSAGE);
        }
     
     }
     
    
    
}
