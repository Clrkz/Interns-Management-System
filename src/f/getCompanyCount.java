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
public class getCompanyCount {
    public static Connection con = null;
    public static PreparedStatement pst = null;
    public static ResultSet rs = null;
    public static String StartUpdate() 
    {
        String userCount = "";
        con = SQLCon.ConnectDB();
        String sql = "SELECT count(*)  FROM `company_information`";
        try{
              pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                 if(rs.next()){
                    userCount = rs.getString(1);
                 }
            
        }catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1057", JOptionPane.ERROR_MESSAGE);
        }
        return userCount;
        
    }
}
