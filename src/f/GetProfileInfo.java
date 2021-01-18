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
public class GetProfileInfo {
public static Connection con = null;
public static PreparedStatement pst =  null;
public static ResultSet rs = null;
    public static void  getPofileInfo() {
        con = SQLCon.ConnectDB();
          String sql = "select * from users where u_information_id='"+SystemInfo.userid+"' ";
         try{
               pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                 if(rs.next()){
                     SystemInfo.ufirstname = rs.getString("u_firstname");
                     SystemInfo.umiddlename = rs.getString("u_middlename");
                     SystemInfo.ulastname = rs.getString("u_lastname");
                     SystemInfo.usuffix = rs.getString("u_suffixname");
                     SystemInfo.gender = rs.getString("u_gender");
                     SystemInfo.ucontactnum = rs.getString("u_phone_number");
                     SystemInfo.uemail = rs.getString("u_email");
                     SystemInfo.urole = rs.getString("u_role");
                     SystemInfo.uusername = rs.getString("u_username");
                     SystemInfo.upassword = rs.getString("u_password");
                     SystemInfo.uvalidate = rs.getString("u_validated");
                     SystemInfo.ustatus = rs.getString("u_status");
                     SystemInfo.udateadded = rs.getString("u_dateadded");
                     SystemInfo.img = rs.getBytes("u_picture");
                     SystemInfo.imagereport = rs.getBlob("u_picture");
                 }
         }catch(Exception e){
                        //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e,"Error 1011",JOptionPane.ERROR_MESSAGE);
     }
    }
}
