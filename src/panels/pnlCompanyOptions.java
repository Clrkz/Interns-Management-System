/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panels;

import f.AuditMessage;
import f.ClassDisconnected;
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
import java.util.ArrayList;
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
public class pnlCompanyOptions extends javax.swing.JPanel {
        public static Connection conn = null;
public static PreparedStatement pst =  null;
public static ResultSet rs = null;
AuditMessage am= new AuditMessage(); 
HashMap<Integer,String> cids = new HashMap<Integer,String>();
String scomReqID;
String scomReq;

String sdivID;
String sdiv;

String ssecID;
String ssec;
//static pnlCompanyOptions cmp = new pnlCompanyOptions();

static pnlCompanyOptions cmp;
     public static void main(String[] args) {
        try {
            // The following line would throw ExceptionInInitializerError
           cmp = new pnlCompanyOptions();
        } catch (Throwable t) {
            System.out.println(t);
        }
        // The following line would cause NoClassDefFoundError
        cmp = new pnlCompanyOptions();
    }
     
     
     
    /**
     * Creates new form pnlHome
     */
    public pnlCompanyOptions() {
        initComponents();
        ClassDisconnected.flag = false;
                    f.CompanyReqList.TableFromDatabase(); 
                    f.CompanyDivList.TableFromDatabase(); 
                    f.CompanySectList.TableFromDatabase(); 
        jScrollPane2.getViewport().setBackground(Color.WHITE);
        jScrollPane3.getViewport().setBackground(Color.WHITE);
        jScrollPane4.getViewport().setBackground(Color.WHITE);
        RequirementList();SectorList();DivisionList();
      //  AddDepartment();AddSector();AddDivision();
      
     CompReqList.setSelectionModel(new ForcedListSelectionModel());
     CompSectList.setSelectionModel(new ForcedListSelectionModel());
     CompDivList.setSelectionModel(new ForcedListSelectionModel());
     
          CompReqList.getTableHeader().setReorderingAllowed(false); 
          CompSectList.getTableHeader().setReorderingAllowed(false); 
          CompDivList.getTableHeader().setReorderingAllowed(false); 
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
      
    
       public static void DC(){
        if(ClassDisconnected.flag==false){
               ClassDisconnected.flag = true;
        Window window = SwingUtilities.windowForComponent(cmp); 
        Disconnected addCourse = new Disconnected((Frame) window, true);
        addCourse.setLocationRelativeTo(cmp); 
        addCourse.setVisible(true); 
        System.out.print(ClassDisconnected.flag);
        } 
    }
       
    public String escapeMetaCharacters(String inputString){ 
        final String[] metaCharacters = {"\\","^","$","{","}","[","]","(",")",".","*","+","?","|","<",">","-","&","%"}; 
        for (int i = 0 ; i < metaCharacters.length ; i++){
            if(inputString.contains(metaCharacters[i])){
                inputString = inputString.replace(metaCharacters[i],"\\"+metaCharacters[i]);
            }
        }
        return inputString; 
    }
   
    
    public void AddDepartment(){
         DefaultTableModel model = (DefaultTableModel) CompReqList.getModel();
          jButton2.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
if(!jTextField2.getText().equals("Requirement") && !jTextField2.getText().equals("")){
              model.addRow(new Object[]{
                              jTextField2.getText()
                                       });
        jTextField2.setText("Requirement");
         }else{   UIManagers.getNewUI();
    JOptionPane.showMessageDialog(null, "Enter Requirement");
                    UIManagers.applyOldUI();
    jTextField2.requestFocus();
          }
        
        }
 });
   
    }
       public void AddSector(){
         DefaultTableModel model = (DefaultTableModel) CompSectList.getModel();
          jButton3.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
if(!jTextField3.getText().equals("Sector") && !jTextField3.getText().equals("")){
              model.addRow(new Object[]{
                              jTextField3.getText()
                                       });
        jTextField3.setText("Sector");
         }else{   UIManagers.getNewUI();
    JOptionPane.showMessageDialog(null, "Enter Sector");
                    UIManagers.applyOldUI();
    jTextField3.requestFocus();
          }
        
        }
 });
   
    }
       
          public void AddDivision(){
         DefaultTableModel model = (DefaultTableModel) CompDivList.getModel();
          jButton4.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
if(!jTextField4.getText().equals("Division") && !jTextField4.getText().equals("")){
              model.addRow(new Object[]{
                              jTextField4.getText()
                                       });
        jTextField4.setText("Division");
         }else{   UIManagers.getNewUI();
    JOptionPane.showMessageDialog(null, "Enter Division");
                    UIManagers.applyOldUI();
    jTextField4.requestFocus();
          }
        
        }
 });
   
    }
    
   
  
     
         public void RequirementList(){
          JTableHeader Theader = CompReqList.getTableHeader();
        Theader.setBackground(new Color(84,127,206)); // change the Background color
        Theader.setForeground(Color.WHITE); // change the Foreground
        Theader.setFont(new Font("Segoe UI", Font.BOLD, 14)); // font name style size
        ((DefaultTableCellRenderer)Theader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER); // center header text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
       CompReqList.getColumnModel().getColumn(0).setPreferredWidth(250);
        CompReqList.getColumnModel().getColumn(1).setPreferredWidth(25);
         TableColumnModel tcm = Theader.getColumnModel();
         TableColumn tc0 = tcm.getColumn(0);
         TableColumn tc1 = tcm.getColumn(1); 
          tc0.setHeaderValue("Requirement");
          tc1.setHeaderValue("Active"); 
        CompReqList.setDefaultEditor(Object.class, null);
        
    }
    
         public void SectorList(){
          JTableHeader Theader = CompSectList.getTableHeader();
        Theader.setBackground(new Color(84,127,206)); // change the Background color
        Theader.setForeground(Color.WHITE); // change the Foreground
        Theader.setFont(new Font("Segoe UI", Font.BOLD, 14)); // font name style size
        ((DefaultTableCellRenderer)Theader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER); // center header text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
       CompSectList.getColumnModel().getColumn(0).setPreferredWidth(250);
        CompSectList.getColumnModel().getColumn(1).setPreferredWidth(25);
           TableColumnModel tcm = Theader.getColumnModel();
         TableColumn tc0 = tcm.getColumn(0);
         TableColumn tc1 = tcm.getColumn(1); 
          tc0.setHeaderValue("Sector");
          tc1.setHeaderValue("Active"); 
        CompSectList.setDefaultEditor(Object.class, null);
        
    }
    
            public void DivisionList(){
          JTableHeader Theader = CompDivList.getTableHeader();
        Theader.setBackground(new Color(84,127,206)); // change the Background color
        Theader.setForeground(Color.WHITE); // change the Foreground
        Theader.setFont(new Font("Segoe UI", Font.BOLD, 14)); // font name style size
        ((DefaultTableCellRenderer)Theader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER); // center header text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
       CompDivList.getColumnModel().getColumn(0).setPreferredWidth(250);
        CompDivList.getColumnModel().getColumn(1).setPreferredWidth(25);
           TableColumnModel tcm = Theader.getColumnModel();
         TableColumn tc0 = tcm.getColumn(0);
         TableColumn tc1 = tcm.getColumn(1); 
          tc0.setHeaderValue("Division");
          tc1.setHeaderValue("Active"); 
        CompDivList.setDefaultEditor(Object.class, null);
        
    }
            
            public void getCompaniesIDs(){
                int counter = 0;
                cids.clear();
                String sql = "select c_information_id from company_information";
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
           // //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1069", JOptionPane.ERROR_MESSAGE);
        }   
            }
            
           
            
             public void  insertReq(){
             String sql = "insert into company_requirements (c_requirement_title,status) values (?,?)";
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
                    f.CompanyReqList.TableFromDatabase(); 
                    RequirementList();
                    UIManagers.getNewUI();
                       UIManagers.getNewUI();
        JOptionPane.showMessageDialog(null, "Requirement successfully added...");UIManagers.applyOldUI();
        jTextField2.setText("Requirement"); 
        conn.close();
             }catch (Exception e) {
                 DC();
            e.printStackTrace();
           // //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1038", JOptionPane.ERROR_MESSAGE);
        }
         }
                 
             public void  updateReq(){
             String sql = "update company_requirements set c_requirement_title=? WHERE  c_requirement_id='"+scomReqID+"'";
             try{
                 conn = SQLCon.ConnectDB();
                 pst = conn.prepareStatement(sql);
                 pst.setString(1, jTextField2.getText().toUpperCase()); 
                 pst.executeUpdate();  
                       DefaultTableModel model = (DefaultTableModel) CompReqList.getModel();
            model.setRowCount(0);
                 insertCompUpdateReqAudit();
                    f.CompanyReqList.TableFromDatabase(); 
                    RequirementList();
                    UIManagers.getNewUI();
                       UIManagers.getNewUI(); 
                       jTextField2.setText("Requirement"); 
                       txtInfo.setText("");
                       scomReq = "";
                       scomReqID = "";
                       jButton2.setText("Add");
        JOptionPane.showMessageDialog(null, "Requirement successfully updated...");UIManagers.applyOldUI();
       conn.close();
             }catch (Exception e) {
                 DC();
                 e.printStackTrace();
           // //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1038", JOptionPane.ERROR_MESSAGE);
        }
         }
             
              public void insertCompanyReq1(String str) { 
        for(int i=0; i<cids.size(); i++){
             String sql = "INSERT INTO `company_requirement_file`(`c_requirement_id`, `c_information_id`, `pdf_doccument`,`file_name`) VALUES ('"+str+"','"+cids.get(i)+"',?,?)";
       try{
           conn = SQLCon.ConnectDB();
           pst = conn.prepareStatement(sql ); 
                 pst.setString(1, null); 
           pst.setString(2, null); 
           pst.executeUpdate(); 
           
           if(i==(cids.size()-1)){
                conn.close();
           }
          
    }catch (Exception e) {
        DC();
        e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1070", JOptionPane.ERROR_MESSAGE);
        }
        }   
    }

              
             
                       
                public void insertCompUpdateReqAudit(){
         String sql = "insert into audit (u_information_id,audit_activity,audit_description) values (?,?,?)";
        try {
            conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, SystemInfo.userid);
            pst.setString(2, am.updateCompReq);
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
              
                public void insertCompReqAudit(){
         String sql = "insert into audit (u_information_id,audit_activity,audit_description) values (?,?,?)";
        try {
            conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, SystemInfo.userid);
            pst.setString(2, am.addCompReq);
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
    
           public void  insertDivision(){
             String sql = "insert into company_division (c_division_title,status) values (?,?)";
             try{
                 conn = SQLCon.ConnectDB();
                 pst = conn.prepareStatement(sql);
                 pst.setString(1, jTextField4.getText().toUpperCase());
                  pst.setInt(2, 1);
                 pst.executeUpdate(); 
                 insertCompDivAudit();
                    f.CompanyDivList.TableFromDatabase(); 
                    DivisionList();   UIManagers.getNewUI();
        JOptionPane.showMessageDialog(null, "Division successfully added...");
                    UIManagers.applyOldUI();
        jTextField4.setText("Division"); 
        txtInfo.setText("");
        conn.close();
             }catch (Exception e) {
                 DC();
                 e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1043", JOptionPane.ERROR_MESSAGE);
        }
         }
                public void  updateDivision(){
             String sql = "update company_division set c_division_title=? where c_division_id='"+sdivID+"'";
             try{
                 conn = SQLCon.ConnectDB();
                 pst = conn.prepareStatement(sql);
                 pst.setString(1, jTextField4.getText().toUpperCase()); 
                 pst.executeUpdate(); 
                 insertUpdateCompDivAudit();
                     DefaultTableModel model = (DefaultTableModel) CompDivList.getModel();
            model.setRowCount(0);
                    f.CompanyDivList.TableFromDatabase(); 
                    DivisionList();   UIManagers.getNewUI();
                    UIManagers.applyOldUI();
        jTextField4.setText("Division"); 
        sdiv = "";
        sdivID = "";
        jButton4.setText("Add");
        
        txtInfo.setText("");
        UIManagers.getNewUI();
        JOptionPane.showMessageDialog(null, "Division successfully updated...");
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
            pst.setString(2, am.addCompDiv);
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
            pst.setString(2, am.updateCompDiv);
            pst.setString(3, jTextField4.getText().toUpperCase());
            pst.executeUpdate(); 
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1042", JOptionPane.ERROR_MESSAGE);
        }
    }
                public void  insertSector(){
             String sql = "insert into company_sector (c_sector_title,status) values (?,?)";
             try{
                 conn = SQLCon.ConnectDB();
                 pst = conn.prepareStatement(sql);
                 pst.setString(1, jTextField3.getText().toUpperCase());
                  pst.setInt(2, 1);
                 pst.executeUpdate(); 
                 insertCompSectAudit();
                    f.CompanySectList.TableFromDatabase(); 
                    SectorList();   UIManagers.getNewUI();
        JOptionPane.showMessageDialog(null, "Sector successfully added...");
                    UIManagers.applyOldUI();
        jTextField3.setText("Sector"); 
        conn.close();
             }catch (Exception e) {
                 DC();
                 e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1043", JOptionPane.ERROR_MESSAGE);
        }
         }
                public void  updateSector(){
             String sql = "update company_sector set c_sector_title=? where c_sector_id='"+ssecID+"'";
             try{
                 
                 conn = SQLCon.ConnectDB();
                 pst = conn.prepareStatement(sql);
                 pst.setString(1, jTextField3.getText().toUpperCase()); 
                 pst.executeUpdate(); 
                 insertUpdateCompSectAudit();
                   DefaultTableModel model = (DefaultTableModel) CompSectList.getModel();
            model.setRowCount(0);
                    f.CompanySectList.TableFromDatabase(); 
                    SectorList();   UIManagers.getNewUI();
                    UIManagers.applyOldUI();
        jTextField3.setText("Sector");
        ssec = "";
        ssecID = "";
        txtInfo.setText("");
        jButton3.setText("Add");
        UIManagers.getNewUI();
        JOptionPane.showMessageDialog(null, "Sector successfully updated...");
        UIManagers.applyOldUI();
        conn.close();
             }catch (Exception e) {
                 DC();
                 e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1043", JOptionPane.ERROR_MESSAGE);
        }
         }
              public void insertUpdateCompSectAudit(){
         String sql = "insert into audit (u_information_id,audit_activity,audit_description) values (?,?,?)";
        try { 
            conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, SystemInfo.userid);
            pst.setString(2, am.updateCompSect);
            pst.setString(3, jTextField3.getText().toUpperCase());
            pst.executeUpdate(); 
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1045", JOptionPane.ERROR_MESSAGE);
        }
    }
    
            public void insertCompSectAudit(){
         String sql = "insert into audit (u_information_id,audit_activity,audit_description) values (?,?,?)";
        try {
            conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, SystemInfo.userid);
            pst.setString(2, am.addCompSect);
            pst.setString(3, jTextField3.getText().toUpperCase());
            pst.executeUpdate(); 
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1045", JOptionPane.ERROR_MESSAGE);
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
        CompReqList = new javax.swing.JTable();
        pnlStudentPersonalInfo4 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        CompSectList = new javax.swing.JTable();
        pnlStudentPersonalInfo3 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jTextField4 = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        CompDivList = new javax.swing.JTable();
        txtInfo = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        pnlStudentPersonalInfo2.setBackground(new java.awt.Color(255, 255, 255));
        pnlStudentPersonalInfo2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Company Requirement List", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N
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

        CompReqList.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        CompReqList.setForeground(new java.awt.Color(102, 102, 102));
        CompReqList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Requirement", "Active"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        CompReqList.setGridColor(new java.awt.Color(255, 255, 255));
        CompReqList.setRowHeight(25);
        CompReqList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                CompReqListMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CompReqListMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(CompReqList);

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
        pnlStudentPersonalInfo4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Company Sector", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N
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

        jTextField3.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(102, 102, 102));
        jTextField3.setText("Sector");
        jTextField3.setToolTipText("Sector");
        jTextField3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField3FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField3FocusLost(evt);
            }
        });
        jTextField3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField3MouseClicked(evt);
            }
        });
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
        });

        jScrollPane3.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        CompSectList.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        CompSectList.setForeground(new java.awt.Color(102, 102, 102));
        CompSectList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sector", "Active"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        CompSectList.setGridColor(new java.awt.Color(255, 255, 255));
        CompSectList.setRowHeight(25);
        CompSectList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CompSectListMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(CompSectList);

        javax.swing.GroupLayout pnlStudentPersonalInfo4Layout = new javax.swing.GroupLayout(pnlStudentPersonalInfo4);
        pnlStudentPersonalInfo4.setLayout(pnlStudentPersonalInfo4Layout);
        pnlStudentPersonalInfo4Layout.setHorizontalGroup(
            pnlStudentPersonalInfo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfo4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlStudentPersonalInfo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStudentPersonalInfo4Layout.createSequentialGroup()
                        .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
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
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlStudentPersonalInfo3.setBackground(new java.awt.Color(255, 255, 255));
        pnlStudentPersonalInfo3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Company Division", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N
        pnlStudentPersonalInfo3.setPreferredSize(new java.awt.Dimension(362, 500));

        jButton4.setBackground(new java.awt.Color(84, 127, 206));
        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Add");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jTextField4.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jTextField4.setForeground(new java.awt.Color(102, 102, 102));
        jTextField4.setText("Division");
        jTextField4.setToolTipText("Division");
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

        jScrollPane4.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        CompDivList.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        CompDivList.setForeground(new java.awt.Color(102, 102, 102));
        CompDivList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Division", "Active"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        CompDivList.setGridColor(new java.awt.Color(255, 255, 255));
        CompDivList.setRowHeight(25);
        CompDivList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CompDivListMouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(CompDivList);

        javax.swing.GroupLayout pnlStudentPersonalInfo3Layout = new javax.swing.GroupLayout(pnlStudentPersonalInfo3);
        pnlStudentPersonalInfo3.setLayout(pnlStudentPersonalInfo3Layout);
        pnlStudentPersonalInfo3Layout.setHorizontalGroup(
            pnlStudentPersonalInfo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfo3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlStudentPersonalInfo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStudentPersonalInfo3Layout.createSequentialGroup()
                        .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlStudentPersonalInfo3Layout.setVerticalGroup(
            pnlStudentPersonalInfo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfo3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlStudentPersonalInfo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
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
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pnlStudentPersonalInfo2, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlStudentPersonalInfo3, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlStudentPersonalInfo4, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE))
                    .addComponent(txtInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(pnlStudentPersonalInfo4, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(pnlStudentPersonalInfo3, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
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
           // JOptionPane.showMessageDialog(null, "Enter requirement name");
    jTextField2.requestFocus();
}else if(jTextField2.getText().contains("'") || jTextField2.getText().contains("\\")){ 
            txtInfo.setText("Invalid requirement name...");
            //JOptionPane.showMessageDialog(null, "Invalid requirement name");
    jTextField2.requestFocus();
}else{
     ClassDisconnected.flag = false;
            String req = jTextField2.getText().toUpperCase();
            if(jButton2.getText().equals("Update")){
                if(req.equals(scomReq)){
                    updateReq();
                }else{
                    try{
                        conn = SQLCon.ConnectDB();
                String sql = "select * from company_requirements where c_requirement_title=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, req);
                rs = pst.executeQuery();
                if(rs.next()){
                     txtInfo.setText("Requirement already exist...");
                   // JOptionPane.showMessageDialog(null, "Requirement already exist...");
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
                String sql = "select * from company_requirements where c_requirement_title=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, req);
                rs = pst.executeQuery();
                if(rs.next()){
                     txtInfo.setText("Requirement already exist...");
                   // JOptionPane.showMessageDialog(null, "Requirement already exist...");
                }else{ 
                    Runnable r = new Runnable() {
                        public void run() {
                            getCompaniesIDs();
                           // insertReq();
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
  jTextField3.setText(jTextField3.getText().replace("\\","").replaceAll("'", ""));
        if(jTextField3.getText().equals("") || jTextField3.getText().equals("Sector")){
            txtInfo.setText("Enter sector name...");
            //JOptionPane.showMessageDialog(null, "Enter sector name");
    jTextField3.requestFocus();
}else if(jTextField3.getText().contains("'") || jTextField3.getText().contains("\\")){ 
               txtInfo.setText("Invalid sector name...");
            //JOptionPane.showMessageDialog(null, "Invalid sector name");
    jTextField3.requestFocus();
}else{
     ClassDisconnected.flag = false;
            String sect = jTextField3.getText().toUpperCase();
            if(jButton3.getText().equals("Update")){
                if(sect.equals(ssec)){
                    updateSector();
                }else{
                      try{
                          conn = SQLCon.ConnectDB();
                String sql = "select * from company_sector where c_sector_title=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, sect);
                rs = pst.executeQuery();
                if(rs.next()){
                    txtInfo.setText("Sector already exist...");
                  //  JOptionPane.showMessageDialog(null, "Sector already exist...");
                }else{
                     updateSector();
                }
                conn.close();
            }catch (Exception e) {
                DC();
                e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1044", JOptionPane.ERROR_MESSAGE);
        }
                }
            }else{
            try{
                conn = SQLCon.ConnectDB();
                String sql = "select * from company_sector where c_sector_title=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, sect);
                rs = pst.executeQuery();
                if(rs.next()){
                    txtInfo.setText("Sector already exist...");
                   // JOptionPane.showMessageDialog(null, "Sector already exist...");
                }else{
                    insertSector();
                }
                conn.close();
            }catch (Exception e) {
                DC();
                e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1044", JOptionPane.ERROR_MESSAGE);
        }
            }
        }//end else 
        UIManagers.applyOldUI();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField3FocusGained
 if(jTextField3.getText().equals("Sector")){
            jTextField3.setText("");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3FocusGained

    private void jTextField3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField3FocusLost
   if(jTextField3.getText().equals("")){
            jTextField3.setText("Sector");
        }              // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3FocusLost

    private void jTextField3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField3MouseClicked
 if(jTextField3.getText().equals("Sector")){
            jTextField3.setText("");
        }              // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3MouseClicked

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
UIManagers.getNewUI();
  jTextField4.setText(jTextField4.getText().replace("\\","").replaceAll("'", ""));
        if(jTextField4.getText().equals("") || jTextField4.getText().equals("Division")){
             txtInfo.setText("Enter division name...");
           /// JOptionPane.showMessageDialog(null, "Enter division name");
    jTextField4.requestFocus();
}else if(jTextField4.getText().contains("'") || jTextField4.getText().contains("\\")){ 
             txtInfo.setText("Invalid division name...");
           // JOptionPane.showMessageDialog(null, "Invalid division name");
    jTextField4.requestFocus();
}else{
     ClassDisconnected.flag = false;
            String div = jTextField4.getText().toUpperCase();
            if(jButton4.getText().equals("Update")){
                if(div.equals(sdiv)){
                    updateDivision();
                }else{
                     try{
                         conn = SQLCon.ConnectDB();
                String sql = "select * from company_division where c_division_title=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, div);
                rs = pst.executeQuery();
                if(rs.next()){
             txtInfo.setText("Division already exist...");
                   // JOptionPane.showMessageDialog(null, "Division already exist...");
                }else{
                   updateDivision();
                }
                conn.close();
            }catch (Exception e) {
                DC();
                e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1041", JOptionPane.ERROR_MESSAGE);
        }
                }
            }else{
            try{
                conn = SQLCon.ConnectDB();
                String sql = "select * from company_division where c_division_title=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, div);
                rs = pst.executeQuery();
                if(rs.next()){
             txtInfo.setText("Division already exist...");
                   // JOptionPane.showMessageDialog(null, "Division already exist...");
                }else{
                    insertDivision();
                }
                conn.close();
            }catch (Exception e){
                DC();
                e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1041", JOptionPane.ERROR_MESSAGE);
        }
            }
        }//end else 
        UIManagers.applyOldUI();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField4FocusGained
if(jTextField4.getText().equals("Division")){
            jTextField4.setText("");
        }        
    }//GEN-LAST:event_jTextField4FocusGained

    private void jTextField4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField4FocusLost
 if(jTextField4.getText().equals("")){
            jTextField4.setText("Division");
        }          // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4FocusLost

    private void jTextField4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField4MouseClicked
if(jTextField4.getText().equals("Division")){
            jTextField4.setText("");
        }                // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4MouseClicked

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void CompReqListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CompReqListMouseReleased
 ClassDisconnected.flag = false;
        int row =  CompReqList.getSelectedRow(); 
        String compReq = CompReqList.getModel().getValueAt(row, 0).toString();
        
      
         Boolean checked = (Boolean) CompReqList.getValueAt(row, 1);
         String compReqStatus = "0";
                if (checked) {
                    compReqStatus = "1"; 
                } else {
                    compReqStatus = "0"; 
                }
       try{
           conn = SQLCon.ConnectDB();
           String sql = "update company_requirements set status=? where c_requirement_title='"+compReq+"'";
           pst = conn.prepareStatement(sql);
           pst.setString(1, compReqStatus);
           pst.executeUpdate();
           System.out.println("Company Rquirement Status Updated");
           conn.close();
       }catch (Exception e) {
           DC();
           e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1040", JOptionPane.ERROR_MESSAGE);
        }   
         try{
             conn = SQLCon.ConnectDB();
           String sql = "SELECT c_requirement_id,c_requirement_title from company_requirements where c_requirement_title='"+compReq+"'";
           pst = conn.prepareStatement(sql);
           rs = pst.executeQuery(); 
           while(rs.next()){
               scomReqID = rs.getString(1);
               scomReq = rs.getString(2);
               jTextField2.setText(scomReq);
               jButton2.setText("Update");
           }
           conn.close();
       }catch (Exception e) {
           DC();
           e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1040", JOptionPane.ERROR_MESSAGE);
        }   



// TODO add your handling code here:
    }//GEN-LAST:event_CompReqListMouseReleased

    private void CompDivListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CompDivListMouseReleased
 ClassDisconnected.flag = false;
        int row =  CompDivList.getSelectedRow(); 
        String compDiv = CompDivList.getModel().getValueAt(row, 0).toString();
      
         Boolean checked = (Boolean) CompDivList.getValueAt(row, 1);
         String compDivStatus = "0";
                if (checked) {
                    compDivStatus = "1"; 
                } else {
                    compDivStatus = "0"; 
                }
       try{
           conn = SQLCon.ConnectDB();
           String sql = "update company_division set status=? where c_division_title='"+compDiv+"'";
           pst = conn.prepareStatement(sql);
           pst.setString(1, compDivStatus);
           pst.executeUpdate(); 
           System.out.println("Company Division Status Updated");
           conn.close();
       }catch (Exception e) {
           DC();
           e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1043", JOptionPane.ERROR_MESSAGE);
        }   
       

 try{
     conn = SQLCon.ConnectDB();
           String sql = "SELECT c_division_id,c_division_title FROM company_division where c_division_title='"+compDiv+"'";
           pst = conn.prepareStatement(sql); 
          rs = pst.executeQuery(); 
         while(rs.next()){
             sdivID = rs.getString(1);
             sdiv = rs.getString(2);
             jButton4.setText("Update");
             jTextField4.setText(sdiv);
         }
         conn.close();
       }catch (Exception e) {
           DC();
           e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1043", JOptionPane.ERROR_MESSAGE);
        }   

//        // TODO add your handling code here:
    }//GEN-LAST:event_CompDivListMouseReleased

    private void CompSectListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CompSectListMouseReleased

         ClassDisconnected.flag = false;
         int row =  CompSectList.getSelectedRow(); 
        String compSect= CompSectList.getModel().getValueAt(row, 0).toString();
      
         Boolean checked = (Boolean) CompSectList.getValueAt(row, 1);
         String compSectStatus = "0";
                if (checked) {
                    compSectStatus = "1"; 
                } else {
                    compSectStatus = "0"; 
                }
       try{
           conn = SQLCon.ConnectDB();
           String sql = "update company_sector set status=? where c_sector_title='"+compSect+"'";
           pst = conn.prepareStatement(sql);
           pst.setString(1, compSectStatus);
           pst.executeUpdate(); 
           System.out.println("Company Sector Status Updated");
           conn.close();
       }catch (Exception e) {
           DC();
           e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1043", JOptionPane.ERROR_MESSAGE);
        }     

   try{
       conn = SQLCon.ConnectDB();
           String sql = "SELECT c_sector_id,c_sector_title from company_sector  where c_sector_title='"+compSect+"'";
           pst = conn.prepareStatement(sql); 
         rs =   pst.executeQuery(); 
         while(rs.next()){
             ssecID = rs.getString(1);
             ssec = rs.getString(2);
             jTextField3.setText(ssec);
             jButton3.setText("Update");
         }
         conn.close();
       }catch (Exception e) {
           DC();
           e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1043", JOptionPane.ERROR_MESSAGE);
        }     
       //        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_CompSectListMouseReleased

    private void CompReqListMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CompReqListMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CompReqListMousePressed

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
  if(evt.getKeyCode()==10){
      UIManagers.getNewUI();
        if(jTextField2.getText().equals("") || jTextField2.getText().equals("Requirement")){
            txtInfo.setText("Enter requirement name...");
           // JOptionPane.showMessageDialog(null, "Enter requirement name");
    jTextField2.requestFocus();
}else if(jTextField2.getText().contains("'")){ 
            txtInfo.setText("Invalid requirement name...");
            //JOptionPane.showMessageDialog(null, "Invalid requirement name");
    jTextField2.requestFocus();
}else{
            String req = jTextField2.getText().toUpperCase();
            if(jButton2.getText().equals("Update")){
                if(req.equals(scomReq)){
                    updateReq();
                }else{
                    try{
                        conn = SQLCon.ConnectDB();
                String sql = "select * from company_requirements where c_requirement_title=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, req);
                rs = pst.executeQuery();
                if(rs.next()){
                     txtInfo.setText("Requirement already exist...");
                   // JOptionPane.showMessageDialog(null, "Requirement already exist...");
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
                String sql = "select * from company_requirements where c_requirement_title=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, req);
                rs = pst.executeQuery();
                if(rs.next()){
                     txtInfo.setText("Requirement already exist...");
                   // JOptionPane.showMessageDialog(null, "Requirement already exist...");
                }else{
                
                    Runnable r = new Runnable() {
                        public void run() {
                            getCompaniesIDs();
                           // insertReq();
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

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
  if(evt.getKeyCode()==10){
      
UIManagers.getNewUI();
        if(jTextField4.getText().equals("") || jTextField4.getText().equals("Division")){
             txtInfo.setText("Enter division name...");
           /// JOptionPane.showMessageDialog(null, "Enter division name");
    jTextField4.requestFocus();
}else if(jTextField4.getText().contains("'")){ 
             txtInfo.setText("Invalid division name...");
           // JOptionPane.showMessageDialog(null, "Invalid division name");
    jTextField4.requestFocus();
}else{
            String div = jTextField4.getText().toUpperCase();
            if(jButton4.getText().equals("Update")){
                if(div.equals(sdiv)){
                    updateDivision();
                }else{
                     try{
                         conn = SQLCon.ConnectDB();
                String sql = "select * from company_division where c_division_title=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, div);
                rs = pst.executeQuery();
                if(rs.next()){
             txtInfo.setText("Division already exist...");
                   // JOptionPane.showMessageDialog(null, "Division already exist...");
                }else{
                   updateDivision();
                }
                conn.close();
            }catch (Exception e) {
                DC();
                e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1041", JOptionPane.ERROR_MESSAGE);
        }
                }
            }else{
            try{
                conn = SQLCon.ConnectDB();
                String sql = "select * from company_division where c_division_title=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, div);
                rs = pst.executeQuery();
                if(rs.next()){
             txtInfo.setText("Division already exist...");
                   // JOptionPane.showMessageDialog(null, "Division already exist...");
                }else{
                    insertDivision();
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
// TODO add your handling code here:
    }//GEN-LAST:event_jTextField4KeyReleased

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
  if(evt.getKeyCode()==10){
UIManagers.getNewUI();
        if(jTextField3.getText().equals("") || jTextField3.getText().equals("Sector")){
            txtInfo.setText("Enter sector name...");
            //JOptionPane.showMessageDialog(null, "Enter sector name");
    jTextField3.requestFocus();
}else if(jTextField3.getText().contains("'")){ 
               txtInfo.setText("Invalid sector name...");
            //JOptionPane.showMessageDialog(null, "Invalid sector name");
    jTextField3.requestFocus();
}else{
            String sect = jTextField3.getText().toUpperCase();
            if(jButton3.getText().equals("Update")){
                if(sect.equals(ssec)){
                    updateSector();
                }else{
                      try{
                          conn = SQLCon.ConnectDB();
                String sql = "select * from company_sector where c_sector_title=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, sect);
                rs = pst.executeQuery();
                if(rs.next()){
                    txtInfo.setText("Sector already exist...");
                  //  JOptionPane.showMessageDialog(null, "Sector already exist...");
                }else{
                     updateSector();
                }
                conn.close();
            }catch (Exception e) {
                DC();
                e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1044", JOptionPane.ERROR_MESSAGE);
        }
                }
            }else{
            try{
                conn = SQLCon.ConnectDB();
                String sql = "select * from company_sector where c_sector_title=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, sect);
                rs = pst.executeQuery();
                if(rs.next()){
                    txtInfo.setText("Sector already exist...");
                   // JOptionPane.showMessageDialog(null, "Sector already exist...");
                }else{
                    insertSector();
                }
                conn.close();
            }catch (Exception e) {
                DC();
                e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1044", JOptionPane.ERROR_MESSAGE);
        }
            }
        }//end else 
        UIManagers.applyOldUI();        // TODO add your handling code here:

      

  }// TODO add your handling code here:
    }//GEN-LAST:event_jTextField3KeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTable CompDivList;
    public static javax.swing.JTable CompReqList;
    public static javax.swing.JTable CompSectList;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JPanel pnlStudentPersonalInfo2;
    private javax.swing.JPanel pnlStudentPersonalInfo3;
    private javax.swing.JPanel pnlStudentPersonalInfo4;
    private javax.swing.JLabel txtInfo;
    // End of variables declaration//GEN-END:variables
}
