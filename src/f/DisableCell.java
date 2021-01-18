/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package f;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Clrkz
 */
public abstract class DisableCell extends AbstractTableModel {
    @Override
 public boolean isCellEditable(int row,int column){
     return false;
 }    
}
