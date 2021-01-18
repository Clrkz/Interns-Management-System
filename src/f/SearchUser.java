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

public class SearchUser  {

    public static Connection con = SQLCon.ConnectDB();
    public static PreparedStatement pst = null;
    public static ResultSet rs = null;
    
       
        public static void TableFromDatabase(int a,String str, int start, int end) {
        Vector<Object> columnNames = new Vector<Object>();
        Vector<Object> data = new Vector<Object>();

        try {
//con = SQLCon.ConnectDB();

// Read data from a table
            String sql = "SELECT `u_information_id` as `User ID`, concat(`u_firstname`,' ',`u_middlename`,' ',`u_lastname`,' ',`u_suffixname`) as Name, `u_username` as Username, \n" +
"(SELECT gender FROM gender where id=users.u_gender) as Gender, \n" +
"`u_phone_number` as `Contact no.`,\n" +
"`u_validated` as Status \n" +
"FROM users \n" +
"where `u_information_id` != 0 and concat_ws(`u_firstname`,' ',`u_middlename`,' ',`u_lastname`,' ',`u_suffixname`,' ',`u_username`) like '%"+str+"%'\n" +
"order by `u_information_id` DESC LIMIT "+start+","+end+"";
             
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
                     if (i == 6) // convert checked column
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

// Create table with database data
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
              return false;
         case 3:
              return false;
         case 4:
              return false;
         case 5:
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
        
        
        home.Home.jTable4.setModel(model);
    }
}
