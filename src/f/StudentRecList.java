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

public class StudentRecList  {

    public static Connection con = null;
    public static PreparedStatement pst = null;
    public static ResultSet rs = null;
    
       
        public static void TableFromDatabase(String str) {
        Vector<Object> columnNames = new Vector<Object>();
        Vector<Object> data = new Vector<Object>();

        try {
con = SQLCon.ConnectDB(); 
String sql = "SELECT sl.s_level_title as Records,oh.ojt_hours_title as Hours,oi.ojt_completed_status as Complete FROM `student_records` sr\n" +
"INNER JOIN student_levels sl on\n" +
"sr.s_year_level_id=sl.s_level_id\n" +
"inner join ojt_information oi\n" +
"on sr.s_ojt_information_id=oi.ojt_information_id\n" +
"inner join ojt_hours oh on\n" +
"oi.ojt_hours_id=oh.ojt_hours_id\n" +
"WHERE sr.s_information_id='"+str+"'\n" +
"ORDER BY sr.s_records_id ASC";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount(); 
            for (int i = 1; i <= columns; i++) {
                columnNames.addElement(md.getColumnName(i));
            } 
            while (rs.next()) {
                Vector<Object> row = new Vector<Object>(columns);

                for (int i = 1; i <= columns; i++) {
                     if (i == 3) // convert checked column
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
            System.out.println(e);
        } 
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            int col  = 0;
            @Override
 public boolean isCellEditable(int row,int column){
     switch(column){
         case 0:
             return false;
         case 1: 
              return false;
         case 2: 
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
        
        
       panels.pnlStudentProfile.jTable4.setModel(model);
    }
}
