/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package f;

import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import panels.pnlStudentsOptions;

public class CoursesList  {

    public static Connection con = null;
    public static PreparedStatement pst = null;
    public static ResultSet rs = null;
    
       
        public static void TableFromDatabase(String dept) {
        Vector<Object> columnNames = new Vector<Object>();
        Vector<Object> data = new Vector<Object>();

        try {
con = SQLCon.ConnectDB();

// Read data from a table
            //String sql = "SELECT `s_course_title`,`status` FROM `student_courses` where `department_id`=(select department_id from student_departments where department_title='"+dept+"') order by `s_course_id` DESC";
           String sql = "SELECT `s_course_title`,`status` FROM `student_courses` where `department_id`=(select department_id from student_departments where department_title='"+dept+"') order by `s_course_title` ASC";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();

// Get column names
            for (int i = 1; i <= columns; i++) {
                columnNames.addElement(md.getColumnName(i));
            }

// Get row data
            while (rs.next()) {
                Vector<Object> row = new Vector<Object>(columns);

                for (int i = 1; i <= columns; i++) {
                     if (i == 2) // convert checked column
                    {
                        int value = rs.getInt(i);
                        row.addElement(value == 0 ? Boolean.FALSE : Boolean.TRUE);
                    } else {
                       row.addElement(rs.getObject(i));
                    }
                }

                data.addElement(row);
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
             pnlStudentsOptions.DC();
            System.out.println(e);
        }

// Create table with database data
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            int col  = 0;
            @Override
 public boolean isCellEditable(int row,int column){
     switch(column){
         case 0:
             return false;
         case 1: 
              return true;
         default:
              return false;
             
     }
    
 }    
 
            @Override
            public Class getColumnClass(int column) {
                for (int row = 0; row < getRowCount(); row++) {
                    Object o = getValueAt(row, column);

                    if (o != null) {
                        return o.getClass();
                    }
                }

                return Object.class;
                
            }
            
        };
        
        
       panels.pnlStudentsOptions.CourseList.setModel(model);
    }
}