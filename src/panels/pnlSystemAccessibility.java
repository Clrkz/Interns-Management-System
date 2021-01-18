/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panels;

import f.AuditMessage;
import f.SQLCon;
import f.SystemInfo;
import f.UIManagers;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Frame;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.EventObject;
import java.util.HashMap;
import java.util.logging.Level;
import javax.swing.AbstractButton;
import javax.swing.AbstractCellEditor;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Clrkz
 */
public class pnlSystemAccessibility extends javax.swing.JPanel {        
public static Connection conn = null;
public static PreparedStatement pst =  null;
public static ResultSet rs = null;
AuditMessage am= new AuditMessage();
//static pnlSystemAccessibility cmp = new pnlSystemAccessibility(); 


static pnlSystemAccessibility cmp;
     public static void main(String[] args) {
        try {
            // The following line would throw ExceptionInInitializerError
           cmp = new pnlSystemAccessibility();
        } catch (Throwable t) {
            System.out.println(t);
        }
        // The following line would cause NoClassDefFoundError
        cmp = new pnlSystemAccessibility();
    }

    /**
     * Creates new form pnlHome
     */
    public pnlSystemAccessibility() {
        initComponents();
        
        f.ClassDisconnected.flag = false;
   //     getSysIDList();
        f.SystemIDList.TableFromDatabase();   
        jScrollPane2.getViewport().setBackground(Color.WHITE);
        SystemIDList();
    //    AddSystemID();
    
     SystemIDs.setSelectionModel(new ForcedListSelectionModel());
     
          SystemIDs.getTableHeader().setReorderingAllowed(false); 
    }
    
    
    
    public class ForcedListSelectionModel extends DefaultListSelectionModel {

    public ForcedListSelectionModel () {
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    @Override
    public void clearSelection() {
    }

    @Override
    public void removeSelectionInterval(int index0, int index1) {
    }

}
      
    
    
    
   
    
    public void AddSystemID(){
         DefaultTableModel model = (DefaultTableModel) SystemIDs.getModel();
          jButton2.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
if(!jTextField2.getText().equals("System ID") && !jTextField2.getText().equals("")){
              model.addRow(new Object[]{
                              jTextField2.getText()
                                       });
        jTextField2.setText("System ID");
         }else{
    JOptionPane.showMessageDialog(null, "Enter System ID");
    jTextField2.requestFocus();
          }
        
        }
 });
   
    }
    
     public static void DC(){
	 try{
        if(f.ClassDisconnected.flag==false){
               f.ClassDisconnected.flag = true;
        Window window = SwingUtilities.windowForComponent(cmp); 
        Disconnected addCourse = new Disconnected((Frame) window, true);
        addCourse.setLocationRelativeTo(cmp); 
        addCourse.setVisible(true); 
        System.out.print(f.ClassDisconnected.flag);
        } 
	 }catch(Exception e){
		 e.printStackTrace();
	 }
    }
   
  
     
         public void SystemIDList(){
          JTableHeader Theader = SystemIDs.getTableHeader();
        Theader.setBackground(new Color(84,127,206)); // change the Background color
        Theader.setForeground(Color.WHITE); // change the Foreground
        Theader.setFont(new Font("Segoe UI", Font.BOLD, 14)); // font name style size
        ((DefaultTableCellRenderer)Theader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER); // center header text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
       SystemIDs.getColumnModel().getColumn(0).setPreferredWidth(225); 
       SystemIDs.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); 
       SystemIDs.getColumnModel().getColumn(1).setPreferredWidth(25); 
       TableColumnModel tcm = Theader.getColumnModel();
         TableColumn tc0 = tcm.getColumn(0); 
          TableColumn tc1 = tcm.getColumn(1); 
          tc0.setHeaderValue("System ID"); 
          tc1.setHeaderValue("Status"); 
        SystemIDs.setDefaultEditor(Object.class, null);
        
    }
    
            public void  insertSysID(){
             String sql = "insert into system (sn,status) values (?,?)";
             try{
                     conn = SQLCon.ConnectDB();
                 pst = conn.prepareStatement(sql);
                 pst.setString(1, jTextField2.getText());
                  pst.setInt(2, 1);
                 pst.executeUpdate(); 
                 insertSysIDAudit();  
            f.SystemIDList.TableFromDatabase();   
                    SystemIDList();
        JOptionPane.showMessageDialog(null, "System ID successfully added...");
        jTextField2.setText("System ID"); 
        conn.close();
             }catch (Exception e) {
                 DC();
                 e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1047", JOptionPane.ERROR_MESSAGE);
        }
         }
              
                public void insertSysIDAudit(){
         String sql = "insert into audit (u_information_id,audit_activity,audit_description) values (?,?,?)";
        try {
                conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, SystemInfo.userid);
            pst.setString(2, am.addSystemID);
            pst.setString(3, jTextField2.getText());
            pst.executeUpdate();  
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1048", JOptionPane.ERROR_MESSAGE);
        }
    }
                
                  public void getSysIDLists(){
         String sql = "select sn,date_added from system";
            try{
                    conn = SQLCon.ConnectDB();
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery();
            SystemIDs.setModel(DbUtils.resultSetToTableModel(rs)); 
       SystemIDList();
       conn.close();
        }catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1017", JOptionPane.ERROR_MESSAGE);
        }
     }
      
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        pnlStudentPersonalInfo2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        SystemIDs = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        pnlStudentPersonalInfo2.setBackground(new java.awt.Color(255, 255, 255));
        pnlStudentPersonalInfo2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "System Accessibility ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N
        pnlStudentPersonalInfo2.setPreferredSize(new java.awt.Dimension(362, 500));

        jButton2.setBackground(new java.awt.Color(84, 127, 206));
        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Add");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(102, 102, 102));
        jTextField2.setText("System ID");
        jTextField2.setToolTipText("System ID");
        jTextField2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField2FocusLost(evt);
            }
        });
        jTextField2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField2MouseClicked(evt);
            }
        });
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        SystemIDs.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        SystemIDs.setForeground(new java.awt.Color(102, 102, 102));
        SystemIDs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "System ID", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        SystemIDs.setGridColor(new java.awt.Color(255, 255, 255));
        SystemIDs.setRowHeight(25);
        SystemIDs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                SystemIDsMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(SystemIDs);

        javax.swing.GroupLayout pnlStudentPersonalInfo2Layout = new javax.swing.GroupLayout(pnlStudentPersonalInfo2);
        pnlStudentPersonalInfo2.setLayout(pnlStudentPersonalInfo2Layout);
        pnlStudentPersonalInfo2Layout.setHorizontalGroup(
            pnlStudentPersonalInfo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfo2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlStudentPersonalInfo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStudentPersonalInfo2Layout.createSequentialGroup()
                        .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlStudentPersonalInfo2Layout.setVerticalGroup(
            pnlStudentPersonalInfo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfo2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlStudentPersonalInfo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(358, 358, 358)
                .addComponent(pnlStudentPersonalInfo2, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                .addGap(363, 363, 363))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addComponent(pnlStudentPersonalInfo2, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                .addGap(97, 97, 97))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField2MouseClicked
        if(jTextField2.getText().equals("System ID")){
            jTextField2.setText("");
        }
    }//GEN-LAST:event_jTextField2MouseClicked

    private void jTextField2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2FocusLost
        if(jTextField2.getText().equals("")){
            jTextField2.setText("System ID");
        }              // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2FocusLost

    private void jTextField2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2FocusGained
        if(jTextField2.getText().equals("System ID")){
            jTextField2.setText("");
        }
    }//GEN-LAST:event_jTextField2FocusGained

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
f.ClassDisconnected.flag = false;
        UIManagers.getNewUI();
        if(jTextField2.equals("") || jTextField2.getText().equals("System ID")){
            JOptionPane.showMessageDialog(null, "Enter System ID");
    jTextField2.requestFocus();
}else if(jTextField2.getText().length() < 32 || jTextField2.getText().length() > 32){
    JOptionPane.showMessageDialog(null, "Invalid System ID","Message",JOptionPane.ERROR_MESSAGE);
    jTextField2.requestFocus();
}else if(jTextField2.getText().contains("'") || jTextField2.getText().contains("\\")){ 
            JOptionPane.showMessageDialog(null, "Invalid System ID","Message",JOptionPane.ERROR_MESSAGE);
    jTextField2.requestFocus();
}else{
            String sysID = jTextField2.getText();
            try{
                conn = SQLCon.ConnectDB();
      String sql = "select * from system where sn=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, sysID);
                rs = pst.executeQuery();
                if(rs.next()){
                    JOptionPane.showMessageDialog(null, "System ID already exist...");
                }else{
                    insertSysID();
                }
                conn.close();
            }catch (Exception e) {
                DC();
                e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1046", JOptionPane.ERROR_MESSAGE);
        }
        }//end else 
        UIManagers.applyOldUI(); 
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void SystemIDsMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SystemIDsMouseReleased
f.ClassDisconnected.flag = false;
        int row =  SystemIDs.getSelectedRow(); 
        String sysID = SystemIDs.getModel().getValueAt(row, 0).toString();
      
         Boolean checked = (Boolean) SystemIDs.getValueAt(row, 1);
         String sysIDStatus = "0";
                if (checked) {
                    sysIDStatus = "1"; 
                } else {
                    sysIDStatus = "0"; 
                }
       try{
           conn = SQLCon.ConnectDB();
           String sql = "update system set status=? where sn='"+sysID+"'";
           pst = conn.prepareStatement(sql);
           pst.setString(1, sysIDStatus);
           pst.executeUpdate(); 
           System.out.println("System ID Status Updated");
           conn.close();
       }catch (Exception e) {
           DC();
           e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1049", JOptionPane.ERROR_MESSAGE);
        }    
        // TODO add your handling code here:
    }//GEN-LAST:event_SystemIDsMouseReleased
      

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTable SystemIDs;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JPanel pnlStudentPersonalInfo2;
    // End of variables declaration//GEN-END:variables
}
