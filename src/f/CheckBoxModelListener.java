/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package f;

import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Clrkz
 */
   
    public class CheckBoxModelListener implements TableModelListener { 
        @Override
        public void tableChanged(TableModelEvent e) {
            int row = e.getFirstRow();
            int column = e.getColumn();
            if (column == 1) {
                TableModel model = (TableModel) e.getSource();
                String columnName = model.getColumnName(column);
                Boolean checked = (Boolean) model.getValueAt(row, column); 
                
                if (checked) { 
                    JOptionPane.showMessageDialog(null, columnName + ": " + true); 
                } else {
                    JOptionPane.showMessageDialog(null, columnName + ": " + false);
                } 
            }
        }
    }