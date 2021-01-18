/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package f;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Clrkz
 */
public class GetUserInfo {
public static Connection con = null;
public static PreparedStatement pst =  null;
public static ResultSet rs = null;
    public static void  getPofileInfo(String uid) {
        con = SQLCon.ConnectDB();
          String sql = "select * from users where u_information_id='"+uid+"' ";
         try{
               pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                 if(rs.next()){
                     SelectedUserAccount.ufirstname = rs.getString("u_firstname");
                     SelectedUserAccount.umiddlename = rs.getString("u_middlename");
                     SelectedUserAccount.ulastname = rs.getString("u_lastname");
                     SelectedUserAccount.usuffix = rs.getString("u_suffixname");
                     SelectedUserAccount.gender = rs.getString("u_gender");
                     SelectedUserAccount.ucontactnum = rs.getString("u_phone_number");
                     SelectedUserAccount.uemail = rs.getString("u_email");
                     SelectedUserAccount.urole = rs.getString("u_role");
                     SelectedUserAccount.uusername = rs.getString("u_username");
                     SelectedUserAccount.upassword = rs.getString("u_password");
                     SelectedUserAccount.uvalidate = rs.getString("u_validated");
                     SelectedUserAccount.ustatus = rs.getString("u_status");
                     SelectedUserAccount.udateadded = rs.getString("u_dateadded");
                     SelectedUserAccount.img = rs.getBytes("u_picture");
                 }
         }catch(Exception e){
                        //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e,"Error 1024",JOptionPane.ERROR_MESSAGE);
     }
    }
}
