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
public class GetCompanyInfo {
public static Connection con = null;
public static PreparedStatement pst =  null;
public static ResultSet rs = null;
    public static void  getPofileInfo(String cid){
        con = SQLCon.ConnectDB();
          String sql = "SELECT c_name as Name,\n" +
"(SELECT c_branch from company_branch where c_information_id=ci.c_information_id) as Branch,\n" +
"(SELECT c_address from company_branch where c_information_id=ci.c_information_id) as Address,\n" +
"(SELECT c_division_title from company_division where c_division_id=ci.c_division_id) as Division,\n" +
"(select c_sector_title from company_sector where c_sector_id=ci.c_sector_id) as Sector,\n" +
"c_logo,\n" +
"dateadded,\n" +  
"c_contact\n" +                  
"FROM company_information ci\n" +
"WHERE c_information_id = '"+cid+"'";
         try{
               pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                 if(rs.next()){
                     SelectedCompany.cname = rs.getString(1);
                     SelectedCompany.cbranch = rs.getString(2);
                     SelectedCompany.caddress = rs.getString(3);
                     SelectedCompany.cdivision = rs.getString(4);
                     SelectedCompany.csector = rs.getString(5);
                     SelectedCompany.img = rs.getBytes(6);  
                     SelectedCompany.cdateadded = rs.getString(7);
                     SelectedCompany.ccontact = rs.getString(8);
                 }
                 
         }catch(Exception e){
                        //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e,"Error 1060",JOptionPane.ERROR_MESSAGE);
     }
    }
}
