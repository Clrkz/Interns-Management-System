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
public class GetStudentInfo {
public static Connection con = null;
public static PreparedStatement pst =  null;
public static ResultSet rs = null;
    public static void  getPofileInfo(String cid) {
        con = SQLCon.ConnectDB();
          String sql = "SELECT `s_student_no`,`s_firstname`,`s_middlename`,`s_lastname`,`s_suffixname`,(SELECT gender from gender WHERE id = si.s_gender),(SELECT department_title from student_departments WHERE department_id = si.s_department_id),(SELECT s_course_title from student_courses WHERE s_course_id = si.s_course_id),(SELECT s_level_title from student_levels WHERE s_level_id = si.s_level_id),`s_dateadded`,`s_picture` FROM `student_information` si WHERE `s_student_no`='"+cid+"'";
         try{
               pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                 if(rs.next()){
                     SelectedStudent.sid = rs.getString(1);
                     SelectedStudent.fname = rs.getString(2);
                     SelectedStudent.mname = rs.getString(3);
                     SelectedStudent.lname = rs.getString(4);
                     SelectedStudent.sname = rs.getString(5);
                     SelectedStudent.sgender = rs.getString(6);
                     SelectedStudent.sdept = rs.getString(7);
                     SelectedStudent.scourse = rs.getString(8);
                     SelectedStudent.slevel = rs.getString(9);
                     SelectedStudent.sdateadd = rs.getString(10);
                     SelectedStudent.img = rs.getBytes(11);   
                 }
         }catch(Exception e){
                        //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e,"Error 1060",JOptionPane.ERROR_MESSAGE);
     }
    }
}
