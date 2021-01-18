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
import java.sql.Statement;
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

/**
 *
 * @author RojeruSan
 */
public class pnlInternshipOptions extends javax.swing.JPanel {
            public static Connection conn = null;
public static PreparedStatement pst =  null;
public static ResultSet rs = null;
AuditMessage am= new AuditMessage(); 
HashMap<Integer,String> cids = new HashMap<Integer,String>();
String requirementID;
String requirement;
String shoursID;
String shours;
//static pnlInternshipOptions cmp = new pnlInternshipOptions(); 

static pnlInternshipOptions cmp;
     public static void main(String[] args) {
        try {
            // The following line would throw ExceptionInInitializerError
           cmp = new pnlInternshipOptions();
        } catch (Throwable t) {
            System.out.println(t);
        }
        // The following line would cause NoClassDefFoundError
        cmp = new pnlInternshipOptions();
    }

    /**
     * Creates new form pnlHome
     */
    public pnlInternshipOptions() {
        initComponents(); 
        f.ClassDisconnected.flag = false;
        f.InternReqList.TableFromDatabase(); 
        f.InternHourList.TableFromDatabase(); 
        jScrollPane2.getViewport().setBackground(Color.WHITE);
        jScrollPane3.getViewport().setBackground(Color.WHITE);
        RequirementList();LevelList();
     //   AddDepartment();AddLevel();
     InternReqList.setSelectionModel(new ForcedListSelectionModel());
     InternHourList.setSelectionModel(new ForcedListSelectionModel());
     
     ContactFilter();
     
          InternReqList.getTableHeader().setReorderingAllowed(false); 
          InternHourList.getTableHeader().setReorderingAllowed(false); 
    }
    
     public static void DC(){
        if(f.ClassDisconnected.flag==false){
               f.ClassDisconnected.flag = true;
        Window window = SwingUtilities.windowForComponent(cmp); 
        Disconnected addCourse = new Disconnected((Frame) window, true);
        addCourse.setLocationRelativeTo(cmp); 
        addCourse.setVisible(true); 
        System.out.print(f.ClassDisconnected.flag);
        } 
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
      
    
    
    
   
    
    public void AddDepartment(){
         DefaultTableModel model = (DefaultTableModel) InternReqList.getModel();
          jButton2.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
if(!jTextField2.getText().equals("Requirement") && !jTextField2.getText().equals("")){
              model.addRow(new Object[]{
                              jTextField2.getText()
                                       });
        jTextField2.setText("Requirement");
         }else{
    JOptionPane.showMessageDialog(null, "Enter Requirement");
    jTextField2.requestFocus();
          }
        
        }
 });
   
    }
       public void AddLevel(){
         DefaultTableModel model = (DefaultTableModel) InternHourList.getModel();
          jButton3.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
if(!jTextField4.getText().equals("Internship Hour") && !jTextField4.getText().equals("")){
              model.addRow(new Object[]{
                              jTextField4.getText()
                                       });
        jTextField4.setText("Internship Hour");
         }else{
    JOptionPane.showMessageDialog(null, "Enter Internship Hour");
    jTextField4.requestFocus();
          }
        
        }
 });
   
    }
    
   
  
     
         public void RequirementList(){
          JTableHeader Theader = InternReqList.getTableHeader();
        Theader.setBackground(new Color(84,127,206)); // change the Background color
        Theader.setForeground(Color.WHITE); // change the Foreground
        Theader.setFont(new Font("Segoe UI", Font.BOLD, 14)); // font name style size
        ((DefaultTableCellRenderer)Theader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER); // center header text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
       InternReqList.getColumnModel().getColumn(0).setPreferredWidth(250);
        InternReqList.getColumnModel().getColumn(1).setPreferredWidth(25);
         TableColumnModel tcm = Theader.getColumnModel();
         TableColumn tc0 = tcm.getColumn(0);
         TableColumn tc1 = tcm.getColumn(1); 
          tc0.setHeaderValue("Requirement");
          tc1.setHeaderValue("Active"); 
        InternReqList.setDefaultEditor(Object.class, null);
        
    }
    
         public void LevelList(){
          JTableHeader Theader = InternHourList.getTableHeader();
        Theader.setBackground(new Color(84,127,206)); // change the Background color
        Theader.setForeground(Color.WHITE); // change the Foreground
        Theader.setFont(new Font("Segoe UI", Font.BOLD, 14)); // font name style size
        ((DefaultTableCellRenderer)Theader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER); // center header text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
       InternHourList.getColumnModel().getColumn(0).setPreferredWidth(250);
        InternHourList.getColumnModel().getColumn(1).setPreferredWidth(25);
           TableColumnModel tcm = Theader.getColumnModel();
         TableColumn tc0 = tcm.getColumn(0);
         TableColumn tc1 = tcm.getColumn(1); 
          tc0.setHeaderValue("Hours");
          tc1.setHeaderValue("Active"); 
        InternHourList.setDefaultEditor(Object.class, null);
        
    }
      public void getOJTsIDs(){
                int counter = 0;
                cids.clear();
                String sql = "select ojt_information_id from ojt_information";
                   try{
                       conn = SQLCon.ConnectDB();
                       pst = conn.prepareStatement(sql);
                       rs  = pst.executeQuery();
                       while(rs.next()){
                           cids.put(counter,rs.getString(1));
                           counter++;
                       }   
                        insertReq();
                        conn.close();
                   } catch (Exception e) {
                       DC();
                       e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1072", JOptionPane.ERROR_MESSAGE);
        }   
            }
         
         public void insertReq(){
              String sql = "insert into ojt_requirements (ojt_requirement_title,status) values (?,?)";
             try{
                 conn = SQLCon.ConnectDB();
                 pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS );
                 pst.setString(1, jTextField2.getText().toUpperCase());
                 pst.setInt(2, 1);
                 pst.executeUpdate();  
                    rs = pst.getGeneratedKeys();
                        rs.next();
                        long pk = rs.getLong(1);
                        String returnID = String.valueOf(pk);
                        insertCompanyReq1(returnID);
                 insertCompReqAudit();
                    f.InternReqList.TableFromDatabase(); 
                    RequirementList();
                    UIManagers.getNewUI();
        JOptionPane.showMessageDialog(null, "Requirement successfully added...");
        UIManagers.applyOldUI();
        jTextField2.setText("Requirement");
        txtInfo.setText("");
        conn.close();
             }catch (Exception e) {
                 DC();
                 e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1038", JOptionPane.ERROR_MESSAGE);
        }
         }
         
            public void updateReq(){
              String sql = "UPDATE  ojt_requirements SET ojt_requirement_title=? where ojt_requirement_id='"+requirementID+"'";
             try{
                 conn = SQLCon.ConnectDB();
                 pst = conn.prepareStatement(sql);
                 pst.setString(1, jTextField2.getText().toUpperCase()); 
                 pst.executeUpdate();   
                 insertUpdateCompReqAudit();
                  DefaultTableModel model = (DefaultTableModel) InternReqList.getModel();
            model.setRowCount(0);
                    f.InternReqList.TableFromDatabase(); 
                    RequirementList();
                    UIManagers.getNewUI();
        UIManagers.applyOldUI();
        jTextField2.setText("Requirement");
         requirementID  ="";
 requirement  ="" ;
 jButton2.setText("Add"); 
        txtInfo.setText("");
        UIManagers.getNewUI();
        JOptionPane.showMessageDialog(null, "Requirement successfully updated...");
        UIManagers.applyOldUI();
        conn.close();
             }catch (Exception e) {
                 DC();
                 e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1038", JOptionPane.ERROR_MESSAGE);
        }
         }
         
         
            
              public void insertCompanyReq1(String str) { 
        for(int i=0; i<cids.size(); i++){
             String sql = "INSERT INTO `ojt_requirement_file`(`ojt_requirement_id`, `ojt_information_id`, `pdf_document`,`file_name`) VALUES ('"+str+"','"+cids.get(i)+"',?,?)";
       try{
           conn = SQLCon.ConnectDB();
           pst = conn.prepareStatement(sql ); 
                 pst.setString(1, null); 
                 pst.setString(2, null); 
           pst.executeUpdate(); 
           if(i==(cids.size()-1)){
               System.out.println("Disconnect from loop");
               conn.close();
           }
    }catch (Exception e) {
        DC();
        e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1070", JOptionPane.ERROR_MESSAGE);
        }
        }   
    }

                   public void insertCompReqAudit(){
         String sql = "insert into audit (u_information_id,audit_activity,audit_description) values (?,?,?)";
        try {
            conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, SystemInfo.userid);
            pst.setString(2, am.addInternReq);
            pst.setString(3, jTextField2.getText().toUpperCase());
            pst.executeUpdate(); 
           jButton2.setEnabled(true);
           conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1039", JOptionPane.ERROR_MESSAGE);
        }
    } 
                   
                   public void insertUpdateCompReqAudit(){
         String sql = "insert into audit (u_information_id,audit_activity,audit_description) values (?,?,?)";
        try {
            conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, SystemInfo.userid);
            pst.setString(2, am.updateInternReq);
            pst.setString(3, jTextField2.getText().toUpperCase());
            pst.executeUpdate(); 
           jButton2.setEnabled(true);
           conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1039", JOptionPane.ERROR_MESSAGE);
        }
    } 
                   
                     public void ContactFilter(){
         jTextField4.addKeyListener(new KeyAdapter() {
    public void keyTyped(KeyEvent e) { 
      char c = e.getKeyChar();
      if (!((c >= '0') && (c <= '9') ||
         (c == KeyEvent.VK_BACK_SPACE) ||
         (c == KeyEvent.VK_DELETE))) {
        getToolkit().beep();
        e.consume();
      }
    } 
  }); 
    }
              
                       public void  insertHour(){
               String sql = "insert into ojt_hours (ojt_hours_title,status) values (?,?)";
             try{
                 conn = SQLCon.ConnectDB();
                 pst = conn.prepareStatement(sql);
                pst.setString(1, jTextField4.getText().toUpperCase());
                  pst.setInt(2, 1);
                 pst.executeUpdate(); 
                 insertCompDivAudit(); 
                    f.InternHourList.TableFromDatabase(); 
                    LevelList();
                    UIManagers.getNewUI();
        JOptionPane.showMessageDialog(null, "Internship hour successfully added...");
        UIManagers.applyOldUI();
        jTextField4.setText("Internship Hour"); 
     
 txtInfo.setText("");
 conn.close();
             }catch (Exception e) {
                 DC();
                 e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1043", JOptionPane.ERROR_MESSAGE);
        }
         }
                    public void  updateHour(){
             String sql = "update ojt_hours set ojt_hours_title=? where ojt_hours_id='"+shoursID+"'" ;
             try{
                 conn = SQLCon.ConnectDB();
                 pst = conn.prepareStatement(sql);
                 pst.setString(1, jTextField4.getText().toUpperCase()); 
                 pst.executeUpdate(); 
                 insertUpdateCompDivAudit();  
                 DefaultTableModel model = (DefaultTableModel) InternHourList.getModel();
            model.setRowCount(0);
                    f.InternHourList.TableFromDatabase(); 
                    LevelList();
                    UIManagers.getNewUI();
        UIManagers.applyOldUI();
        jTextField4.setText("Internship Hour");   
        jButton3.setText("Add");
 shoursID="";
 shours="";
 txtInfo.setText("");
 UIManagers.getNewUI();
        JOptionPane.showMessageDialog(null, "Internship hour successfully updated...");
        UIManagers.applyOldUI();
        conn.close();
             }catch (Exception e) {
                 DC();
                 e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1043", JOptionPane.ERROR_MESSAGE);
        }
         }
              
                    
              public void insertCompDivAudit(){
         String sql = "insert into audit (u_information_id,audit_activity,audit_description) values (?,?,?)";
        try {
            conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, SystemInfo.userid);
            pst.setString(2, am.addInternHour);
            pst.setString(3, jTextField4.getText().toUpperCase());
            pst.executeUpdate(); 
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1042", JOptionPane.ERROR_MESSAGE);
        }
    }          
              public void insertUpdateCompDivAudit(){
         String sql = "insert into audit (u_information_id,audit_activity,audit_description) values (?,?,?)";
        try {
            
            conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, SystemInfo.userid);
            pst.setString(2, am.addInternHour);
            pst.setString(3, jTextField4.getText().toUpperCase());
            pst.executeUpdate(); 
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1042", JOptionPane.ERROR_MESSAGE);
        }
    }
              
    /**
     * 
     * 
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
        InternReqList = new javax.swing.JTable();
        pnlStudentPersonalInfo4 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jTextField4 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        InternHourList = new javax.swing.JTable();
        txtInfo = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        pnlStudentPersonalInfo2.setBackground(new java.awt.Color(255, 255, 255));
        pnlStudentPersonalInfo2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Internship Requirement List", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N
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
        jTextField2.setText("Requirement");
        jTextField2.setToolTipText("Requirement");
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
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        InternReqList.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        InternReqList.setForeground(new java.awt.Color(102, 102, 102));
        InternReqList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Requirement", "Active"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        InternReqList.setGridColor(new java.awt.Color(255, 255, 255));
        InternReqList.setRowHeight(25);
        InternReqList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                InternReqListMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                InternReqListMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(InternReqList);

        javax.swing.GroupLayout pnlStudentPersonalInfo2Layout = new javax.swing.GroupLayout(pnlStudentPersonalInfo2);
        pnlStudentPersonalInfo2.setLayout(pnlStudentPersonalInfo2Layout);
        pnlStudentPersonalInfo2Layout.setHorizontalGroup(
            pnlStudentPersonalInfo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfo2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlStudentPersonalInfo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStudentPersonalInfo2Layout.createSequentialGroup()
                        .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlStudentPersonalInfo4.setBackground(new java.awt.Color(255, 255, 255));
        pnlStudentPersonalInfo4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Internship Hours", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N
        pnlStudentPersonalInfo4.setPreferredSize(new java.awt.Dimension(362, 500));

        jButton3.setBackground(new java.awt.Color(84, 127, 206));
        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Add");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextField4.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jTextField4.setForeground(new java.awt.Color(102, 102, 102));
        jTextField4.setText("Internship Hour");
        jTextField4.setToolTipText("Internship Hour");
        jTextField4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField4FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField4FocusLost(evt);
            }
        });
        jTextField4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField4MouseClicked(evt);
            }
        });
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });

        jScrollPane3.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        InternHourList.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        InternHourList.setForeground(new java.awt.Color(102, 102, 102));
        InternHourList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Hours", "Active"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        InternHourList.setGridColor(new java.awt.Color(255, 255, 255));
        InternHourList.setRowHeight(25);
        InternHourList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                InternHourListMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(InternHourList);

        javax.swing.GroupLayout pnlStudentPersonalInfo4Layout = new javax.swing.GroupLayout(pnlStudentPersonalInfo4);
        pnlStudentPersonalInfo4.setLayout(pnlStudentPersonalInfo4Layout);
        pnlStudentPersonalInfo4Layout.setHorizontalGroup(
            pnlStudentPersonalInfo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfo4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlStudentPersonalInfo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStudentPersonalInfo4Layout.createSequentialGroup()
                        .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlStudentPersonalInfo4Layout.setVerticalGroup(
            pnlStudentPersonalInfo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfo4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlStudentPersonalInfo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        txtInfo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtInfo.setForeground(new java.awt.Color(84, 127, 206));
        txtInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(192, 192, 192)
                .addComponent(pnlStudentPersonalInfo2, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(pnlStudentPersonalInfo4, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                .addGap(191, 191, 191))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlStudentPersonalInfo2, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(pnlStudentPersonalInfo4, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                .addGap(141, 141, 141))
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
        if(jTextField2.getText().equals("Requirement")){
            jTextField2.setText("");
        }
    }//GEN-LAST:event_jTextField2MouseClicked

    private void jTextField2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2FocusLost
        if(jTextField2.getText().equals("")){
            jTextField2.setText("Requirement");
        }              // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2FocusLost

    private void jTextField2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2FocusGained
        if(jTextField2.getText().equals("Requirement")){
            jTextField2.setText("");
        }
    }//GEN-LAST:event_jTextField2FocusGained

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
UIManagers.getNewUI();
  jTextField2.setText(jTextField2.getText().replace("\\","").replaceAll("'", ""));
        if(jTextField2.getText().equals("") || jTextField2.getText().equals("Requirement")){
            txtInfo.setText("Enter requirement name...");
            //JOptionPane.showMessageDialog(null, "Enter requirement name");
    jTextField2.requestFocus();
}else if(jTextField2.getText().contains("'") || jTextField2.getText().contains("\\")){ 
     txtInfo.setText("Invalid requirement name...");
           //JOptionPane.showMessageDialog(null, "Invalid requirement name");
    jTextField2.requestFocus();
}else{
     f.ClassDisconnected.flag = false;
            String req = jTextField2.getText().toUpperCase();
            if(jButton2.getText().equals("Update")){
               if(requirement.equals(req)){
                    updateReq();
               }else{
                     try{
                         
                         conn = SQLCon.ConnectDB();
                String sql = "select * from ojt_requirements where ojt_requirement_title=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, req);
                rs = pst.executeQuery();
                if(rs.next()){ 
                    txtInfo.setText("Requirement already exist..."); 
                }else{
                     updateReq();
                } 
                conn.close();
                     }catch (Exception e) {
                         DC();
                         e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1037", JOptionPane.ERROR_MESSAGE);
        }
               }
            }else{
            try{
                conn = SQLCon.ConnectDB();
                String sql = "select * from ojt_requirements where ojt_requirement_title=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, req);
                rs = pst.executeQuery();
                if(rs.next()){ 
                    txtInfo.setText("Requirement already exist...");
                  //  JOptionPane.showMessageDialog(null, "Requirement already exist...");
                }else{
                
                    Runnable r = new Runnable() {
                        public void run() {
                            getOJTsIDs();
                            //insertReq();
                        }
                    };
                    new Thread(r).start();
                    jButton2.setEnabled(false);

                }
                conn.close();
            }catch (Exception e) {
                DC();
                e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1037", JOptionPane.ERROR_MESSAGE);
        }
            }
        }//end else 
        UIManagers.applyOldUI();

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
UIManagers.getNewUI();
  jTextField4.setText(jTextField4.getText().replace("\\","").replaceAll("'", ""));
        if(jTextField4.getText().equals("") || jTextField4.getText().equals("Internship Hour")){
             txtInfo.setText("Enter internship hour...");
            //JOptionPane.showMessageDialog(null, "Enter internship hour");
    jTextField4.requestFocus();
}else if(jTextField4.getText().contains("'") || jTextField4.getText().contains("\\")){ 
             txtInfo.setText("Invalid internship hour...");
           // JOptionPane.showMessageDialog(null, "Invalid internship hour");
    jTextField4.requestFocus();
}else{
     f.ClassDisconnected.flag = false;
            String div = jTextField4.getText().toUpperCase(); 
             if(jButton3.getText().equals("Update")){
                   if(div.equals(shours)){
                updateHour();
            }else{
                 try{
                     conn = SQLCon.ConnectDB();
                String sql = "select ojt_hours_title from ojt_hours where ojt_hours_title=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, div);
                rs = pst.executeQuery();
                if(rs.next()){
     txtInfo.setText("Internship hour already exist..."); 
                }else{
                    updateHour();
                }
                conn.close();
            }catch (Exception e) {
                DC();
                e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1035", JOptionPane.ERROR_MESSAGE);
        }
                   }      
             }else{
            try{
                conn = SQLCon.ConnectDB();
                String sql = "select ojt_hours_title from ojt_hours where ojt_hours_title=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, div);
                rs = pst.executeQuery();
                if(rs.next()){
     txtInfo.setText("Internship hour already exist...");
                    //JOptionPane.showMessageDialog(null, "Internship hour already exist...");
                }else{
                    insertHour();
                }
                conn.close();
            }catch (Exception e) {
                DC();
                e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1041", JOptionPane.ERROR_MESSAGE);
        }
             }
        }//end else 
        UIManagers.applyOldUI();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField4FocusGained
 if(jTextField4.getText().equals("Internship Hour")){
            jTextField4.setText("");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4FocusGained

    private void jTextField4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField4FocusLost
   if(jTextField4.getText().equals("")){
            jTextField4.setText("Internship Hour");
        }              // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4FocusLost

    private void jTextField4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField4MouseClicked
 if(jTextField4.getText().equals("Internship Hour")){
            jTextField4.setText("");
        }              // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4MouseClicked

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
  ContactFilter();
  if(evt.getKeyCode()==10){
      UIManagers.getNewUI();
        if(jTextField4.getText().equals("") || jTextField4.getText().equals("Internship Hour")){
             txtInfo.setText("Enter internship hour...");
            //JOptionPane.showMessageDialog(null, "Enter internship hour");
    jTextField4.requestFocus();
}else if(jTextField4.getText().contains("'")){ 
             txtInfo.setText("Invalid internship hour...");
           // JOptionPane.showMessageDialog(null, "Invalid internship hour");
    jTextField4.requestFocus();
}else{
            String div = jTextField4.getText().toUpperCase(); 
             if(jButton3.getText().equals("Update")){
                   if(div.equals(shours)){
                updateHour();
            }else{
                 try{
                     
                     conn = SQLCon.ConnectDB();
                String sql = "select ojt_hours_title from ojt_hours where ojt_hours_title=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, div);
                rs = pst.executeQuery();
                if(rs.next()){
     txtInfo.setText("Internship hour already exist..."); 
                }else{
                    updateHour();
                }
                conn.close();
            }catch (Exception e) {
                DC();
                e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1035", JOptionPane.ERROR_MESSAGE);
        }
                   }      
             }else{
            try{
                conn = SQLCon.ConnectDB();
                String sql = "select ojt_hours_title from ojt_hours where ojt_hours_title=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, div);
                rs = pst.executeQuery();
                if(rs.next()){
     txtInfo.setText("Internship hour already exist...");
                    //JOptionPane.showMessageDialog(null, "Internship hour already exist...");
                }else{
                    insertHour();
                }
                conn.close();
            }catch (Exception e) {
                DC();
                e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1041", JOptionPane.ERROR_MESSAGE);
        }
             }
        }//end else 
        UIManagers.applyOldUI();

  }
        
              
    }//GEN-LAST:event_jTextField4KeyReleased

    private void InternReqListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_InternReqListMouseReleased
 f.ClassDisconnected.flag = false;
        int row =  InternReqList.getSelectedRow(); 
        String compReq = InternReqList.getModel().getValueAt(row, 0).toString();
      
         Boolean checked = (Boolean) InternReqList.getValueAt(row, 1);
         String compReqStatus = "0";
                if (checked) {
                    compReqStatus = "1"; 
                } else {
                    compReqStatus = "0"; 
                }
       try{
           conn = SQLCon.ConnectDB();
           String sql = "update ojt_requirements set status=? where ojt_requirement_title='"+compReq+"'";
           pst = conn.prepareStatement(sql);
           pst.setString(1, compReqStatus);
           pst.executeUpdate(); 
           System.out.println("Intern Rquirement Status Updated");
           conn.close();
       }catch (Exception e) {
           DC();
           e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1040", JOptionPane.ERROR_MESSAGE);
        } 
       
         try{
             conn = SQLCon.ConnectDB();
           String sql = "SELECT  ojt_requirement_id,ojt_requirement_title from ojt_requirements  where ojt_requirement_title='"+compReq+"'";
           pst = conn.prepareStatement(sql); 
          rs =  pst.executeQuery();  
          while(rs.next()){  
 requirementID  = rs.getString(1);
 requirement = rs.getString(2);
 jTextField2.setText(requirement);
 jButton2.setText("Update");
          }
          
          conn.close();
       }catch (Exception e) {
           DC();
           e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1040", JOptionPane.ERROR_MESSAGE);
        } 
       
       
       
    }//GEN-LAST:event_InternReqListMouseReleased

    private void InternHourListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_InternHourListMouseReleased
 f.ClassDisconnected.flag = false;
        int row =  InternHourList.getSelectedRow(); 
        String compReq = InternHourList.getModel().getValueAt(row, 0).toString();
      
         Boolean checked = (Boolean) InternHourList.getValueAt(row, 1);
         String compReqStatus = "0";
                if (checked) {
                    compReqStatus = "1"; 
                } else {
                    compReqStatus = "0"; 
                }
       try{
           conn = SQLCon.ConnectDB();
           String sql = "update ojt_hours set status=? where ojt_hours_title='"+compReq+"'";
           pst = conn.prepareStatement(sql);
           pst.setString(1, compReqStatus);
           pst.executeUpdate(); 
           System.out.println("Intern hours Status Updated");
           conn.close();
       }catch (Exception e) {
           DC();
           e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1040", JOptionPane.ERROR_MESSAGE);
        }         
       
       
       try{
           conn = SQLCon.ConnectDB();
           String sql = "select ojt_hours_id,ojt_hours_title from ojt_hours where ojt_hours_title='"+compReq+"'";
           pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                 shoursID = rs.getString(1);
                shours = rs.getString(2);
               jTextField4.setText(shours); 
                jButton3.setText("Update");
            } 
            conn.close();
       }catch (Exception e) {
           DC();
           e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1040", JOptionPane.ERROR_MESSAGE);
        }         // TODO add your handling code here:
    }//GEN-LAST:event_InternHourListMouseReleased

    private void InternReqListMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_InternReqListMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_InternReqListMousePressed

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
  if(evt.getKeyCode()==10){
      UIManagers.getNewUI();
        if(jTextField2.getText().equals("") || jTextField2.getText().equals("Requirement")){
            txtInfo.setText("Enter requirement name...");
            //JOptionPane.showMessageDialog(null, "Enter requirement name");
    jTextField2.requestFocus();
}else if(jTextField2.getText().contains("'")){ 
     txtInfo.setText("Invalid requirement name...");
           //JOptionPane.showMessageDialog(null, "Invalid requirement name");
    jTextField2.requestFocus();
}else{
            String req = jTextField2.getText().toUpperCase();
            if(jButton2.getText().equals("Update")){
               if(requirement.equals(req)){
                    updateReq();
               }else{
                     try{
                         conn = SQLCon.ConnectDB();
                String sql = "select * from ojt_requirements where ojt_requirement_title=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, req);
                rs = pst.executeQuery();
                if(rs.next()){ 
                    txtInfo.setText("Requirement already exist..."); 
                }else{
                     updateReq();
                } 
                conn.close();
                     }catch (Exception e) {
                         
                         DC();
                         e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1037", JOptionPane.ERROR_MESSAGE);
        }
               }
            }else{
            try{
                conn = SQLCon.ConnectDB();
                String sql = "select * from ojt_requirements where ojt_requirement_title=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, req);
                rs = pst.executeQuery();
                if(rs.next()){ 
                    txtInfo.setText("Requirement already exist...");
                  //  JOptionPane.showMessageDialog(null, "Requirement already exist...");
                }else{
                
                    Runnable r = new Runnable() {
                        public void run() {
                            getOJTsIDs();
                            //insertReq();
                        }
                    };
                    new Thread(r).start();
                    jButton2.setEnabled(false);

                }
                conn.close();
            }catch (Exception e) {
                DC();
                e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1037", JOptionPane.ERROR_MESSAGE);
        }
            }
        }//end else 
        UIManagers.applyOldUI();


  }


// TODO add your handling code here:
    }//GEN-LAST:event_jTextField2KeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTable InternHourList;
    public static javax.swing.JTable InternReqList;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JPanel pnlStudentPersonalInfo2;
    private javax.swing.JPanel pnlStudentPersonalInfo4;
    private javax.swing.JLabel txtInfo;
    // End of variables declaration//GEN-END:variables
}
