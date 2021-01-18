/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panels;

import f.AuditMessage;
import f.FilePath;
import f.SQLCon;
import f.SelectedCompany;
import f.SystemConnection;
import f.SystemInfo;
import f.UIManagers;
import home.Home;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.EventObject;
import java.util.HashMap;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
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
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author RojeruSan
 */
public class pnlCompanyProfile extends javax.swing.JPanel {
private static HashMap<Integer,String> hm = new HashMap<Integer,String>();
public static String selectedRowCompDocProfile;
public static Connection conn = null;
public static PreparedStatement pst =  null;
public static ResultSet rs = null;
AuditMessage am= new AuditMessage();
String s;
public static String currentDir = "user.home";
boolean changeAvatar = false; 
public boolean shiftPressed;
  DocumentFilter filter = new UppercaseDocumentFilter(); 
  
    //static pnlCompanyProfile cmp = new pnlCompanyProfile(); 
    
      static pnlCompanyProfile cmp;
     public static void main(String[] args) {
        try {
            // The following line would throw ExceptionInInitializerError
           cmp = new pnlCompanyProfile();
        } catch (Throwable t) {
            System.out.println(t);
        }
        // The following line would cause NoClassDefFoundError
        cmp = new pnlCompanyProfile();
    }
 

    /**
     * Creates new form pnlHome
     */
    public pnlCompanyProfile() {
        initComponents();
        f.ClassDisconnected.flag = false;
        Home.pnlFooterAction.hide();
        Home.HideSearch();
        DivisionBox();
        SectorBox();
        getDivision();
        getSector();
        CompanyReqList();
        reqListTable();
        
         jScrollPane5.getViewport().setBackground(Color.WHITE);
          
        hm.clear();  
       
      //  UIManager.getDefaults().put("TableHeader.cellBorder" , BorderFactory.createEmptyBorder(0,0,0,0));
        FilterCompanyContact(); 
        CompanyListFunction();
    ((AbstractDocument) txtCompanyName.getDocument()).setDocumentFilter(filter); 
    ((AbstractDocument) txtCompanyBranch.getDocument()).setDocumentFilter(filter); 
    add(txtCompanyName);
    add(txtCompanyBranch);
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
       
      public void reqListTable(){
        UIManagers.applyOldUI();
         getCompanyReqList();
        CompanyReqList();
        jTable5.getColumn("PDF Document").setCellRenderer(new ButtonCellRenderer1());
        jTable5.getColumn("PDF Document").setCellEditor(new ButtonCellEditor1()); 
    }
      
      class UppercaseDocumentFilter extends DocumentFilter {
  public void insertString(DocumentFilter.FilterBypass fb, int offset, String text,
      AttributeSet attr) throws BadLocationException {

    fb.insertString(offset, text.toUpperCase(), attr);
  }

  public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text,
      AttributeSet attrs) throws BadLocationException {

    fb.replace(offset, length, text.toUpperCase(), attrs);
  }
}
      
      
       public void FilterCompanyContact(){
        txtCompNo.addKeyListener(new KeyAdapter() {
    public void keyTyped(KeyEvent e) { 
      char c = e.getKeyChar();
      if (!((c >= '0') && (c <= '9') ||
         (c == KeyEvent.VK_MINUS) || 
         (c == KeyEvent.VK_ADD) || 
         (c == KeyEvent.VK_PLUS) || 
         (c == KeyEvent.VK_SPACE) || 
         (c == KeyEvent.VK_BACK_SPACE) ||
         (c == KeyEvent.VK_DELETE))) {
        getToolkit().beep();
        e.consume();
      }
    } 
  });  
    }
     public void getCompanyReqList(){  
          
         /*String sql = "SELECT  \n" +
"b.c_requirement_title,a.file_name\n" +
"FROM company_requirement_file a\n" +
"inner join company_requirements b\n" +
"on a.c_requirement_id=b.c_requirement_id\n" +
"inner join company_information c\n" +
"on a.c_information_id=c.c_information_id\n" +
"where c.c_information_id='"+SelectedCompany.compid+"'\n" +
"and b.status=1\n" + 
"order by b.c_requirement_id desc";*/
         String sql = "SELECT  \n" +
"b.c_requirement_title,a.file_name\n" +
"FROM company_requirement_file a\n" +
"inner join company_requirements b\n" +
"on a.c_requirement_id=b.c_requirement_id\n" +
"inner join company_information c\n" +
"on a.c_information_id=c.c_information_id\n" +
"where c.c_information_id='"+SelectedCompany.compid+"'\n" +
"and b.status=1\n" + 
"order by b.c_requirement_title asc";
            try{
                conn = SQLCon.ConnectDB();
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery();
            jTable5.setModel(DbUtils.resultSetToTableModel(rs));   
            conn.close();
        }catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1017", JOptionPane.ERROR_MESSAGE);
        }
     
    }
    
    public void CompanyListFunction(){
                  txtCompanyName.setText(SelectedCompany.cname);
                  txtCompNo.setText(SelectedCompany.ccontact);
                  txtCompanyBranch.setText(SelectedCompany.cbranch);
                  txtCompanyAddress.setText(SelectedCompany.caddress );
                  jComboBox7.setSelectedItem(SelectedCompany.cdivision);
                  jComboBox8.setSelectedItem(SelectedCompany.csector);   
             jLabel2.setText("Date Registered: "+SelectedCompany.cdateadded ); 
                jLabel13.setText(SelectedCompany.cIntern);
                jLabel5.setText(SelectedCompany.tIntern);
                jLabel7.setText(SelectedCompany.eIntern);
             
             
               new java.util.Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                
                      
        try{
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(SelectedCompany.img).getImage().getScaledInstance(jLabel14.getWidth(), jLabel14.getHeight(), Image.SCALE_SMOOTH));
                jLabel14.setIcon(imageIcon); 
        }catch(Exception e){
            jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Web_Camera_125px.png")));
            System.out.println("No Image\n"+e);
        }   
       
            
              
            }
        }, 500 * 1);
    }
       
     
    
     public void CompanyReqList(){
          JTableHeader Theader = jTable5.getTableHeader();
        Theader.setBackground(new Color(84,127,206)); // change the Background color
        Theader.setForeground(Color.WHITE); // change the Foreground
        Theader.setFont(new Font("Segoe UI", Font.BOLD, 14)); // font name style size
        ((DefaultTableCellRenderer)Theader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER); // center header text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
       jTable5.getColumnModel().getColumn(0).setPreferredWidth(300);
        jTable5.getColumnModel().getColumn(1).setPreferredWidth(5);
         TableColumnModel tcm = Theader.getColumnModel();
         TableColumn tc0 = tcm.getColumn(0);
         TableColumn tc1 = tcm.getColumn(1); 
          tc0.setHeaderValue("Requirements");
          tc1.setHeaderValue("PDF Document"); 
        jTable5.setDefaultEditor(Object.class, null);
        
    }

     public void DivisionBox(){
        jComboBox7.setBackground(Color.WHITE);
    jComboBox7.requestFocus(false);
        for (int i = 0; i < jComboBox7.getComponentCount(); i++) 
{
    if (jComboBox7.getComponent(i) instanceof JComponent) {
        ((JComponent) jComboBox7.getComponent(i)).setBorder(new EmptyBorder(0, 0,0,0));
    }


    if (jComboBox7.getComponent(i) instanceof AbstractButton) {
        ((AbstractButton) jComboBox7.getComponent(i)).setBorderPainted(false);
    }
}      
    }
    
 public void SectorBox(){
        jComboBox8.setBackground(Color.WHITE);
    jComboBox8.requestFocus(false);
        for (int i = 0; i < jComboBox8.getComponentCount(); i++) 
{
    if (jComboBox8.getComponent(i) instanceof JComponent) {
        ((JComponent) jComboBox8.getComponent(i)).setBorder(new EmptyBorder(0, 0,0,0));
    }


    if (jComboBox8.getComponent(i) instanceof AbstractButton) {
        ((AbstractButton) jComboBox8.getComponent(i)).setBorderPainted(false);
    }
}      
    }
 
  public static class ButtonCellRenderer1 extends JButton implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value != null) {
                setText("Update");
            } else {
                setText("Add" );
            }
            if (isSelected) {
                setForeground(Color.WHITE);
                setBackground(new Color(240, 173, 78));
            } else {
                setForeground(new Color(102,102,102));
                setBackground(new Color(240, 240, 240));
            }
            return this;
        }
    }
     
      public static class ButtonCellEditor1 extends AbstractCellEditor implements TableCellEditor {

        private JButton editor;
        private Object value;
        private int row;
        private JTable table;

        public ButtonCellEditor1() {
            editor = new JButton();
            editor.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (table != null) {
                        fireEditingStopped();
                        TableModel model = table.getModel();
                        if (model instanceof DefaultTableModel) {
                              SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LookAndFeel old = UIManager.getLookAndFeel();
                try {
                    UIManager.setLookAndFeel(
                            UIManager.getSystemLookAndFeelClassName());
                } catch(Exception e) {
                    e.printStackTrace();
                }
                if(hm.containsKey(row)){
                       JFileChooser fileChooser = new JFileChooser();
                 fileChooser.setCurrentDirectory(new File(FilePath.filePath));
     fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Document", "pdf"));
                //fileChooser.showOpenDialog(null);
                 int result=fileChooser.showOpenDialog(null);
                 if(result==JFileChooser.APPROVE_OPTION){
        File selectedFile = fileChooser.getSelectedFile();
        String path = selectedFile.getAbsolutePath();
        FilePath.filePath = path;
        hm.put(row,path);
        jTable5.setValueAt(Boolean.valueOf(true), row, 1); 
       Runnable r = new Runnable() {
            public void run() { 
                jTable5.setEnabled(false); 
               CheckFileExist();
            }
        };
        new Thread(r).start();
        jTable5.setEnabled(false); 
        //0. check if file exist  if exist goto 1 if not goto 2
        //1. insert into archive file
        //2. delete from file
        //3. insert file
                 }
                }else{
                JFileChooser fileChooser = new JFileChooser();
                 fileChooser.setCurrentDirectory(new File(FilePath.filePath));
     fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Document", "pdf"));
                //fileChooser.showOpenDialog(null);
                 int result=fileChooser.showOpenDialog(null);
                 if(result==JFileChooser.APPROVE_OPTION){
        File selectedFile = fileChooser.getSelectedFile();
        String path = selectedFile.getAbsolutePath();
        FilePath.filePath = path;
        hm.put(row,path);
        jTable5.setValueAt(Boolean.valueOf(true), row, 1); 
        
        Runnable r = new Runnable() {
            public void run() { 
                
                jTable5.setEnabled(false); 
               CheckFileExist();
            }
        };
        new Thread(r).start();
        jTable5.setEnabled(false);
        
         //0. check if file exist  if exist goto 1 if not goto 2
        //1. insert into archive file
        //2. delete from file
        //3. insert file
                 }
                }
                 
               try {
            UIManager.setLookAndFeel(old);
         }  catch(Exception e) {
                    e.printStackTrace();
                }
            }
           
        });
                          
                            
                        }
                        
                    }
                }
                
            });
        }

        @Override
        public boolean isCellEditable(EventObject e) {
            return true;
        }

        @Override
        public Object getCellEditorValue() {
            return value;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.table = table;
            this.row = row;
            this.value = value;
            if (value != null) {
                editor.setText("Update" );
            } else {
                editor.setText("Add" );
            }
            if (isSelected) {
                editor.setForeground(Color.WHITE);
                editor.setBackground(new Color(240, 173, 78));
            } else {
                editor.setForeground(new Color(102,102,102));
                editor.setBackground(new Color(240, 240, 240));
            }
            return editor;
        }
    }
   
      
      public static void CheckFileExist(){
          int row =  jTable5.getSelectedRow();
 String req = jTable5.getModel().getValueAt(row, 0).toString();
          String sql = "SELECT file_name FROM `company_requirement_file` \n" +
"WHERE c_information_id='"+SelectedCompany.compid+"'\n" +
"AND c_requirement_id=(SELECT c_requirement_id from company_requirements WHERE c_requirement_title='"+req+"')";
          try{
               conn = SQLCon.ConnectDB();
              pst = conn.prepareStatement(sql);
              rs = pst.executeQuery();
              if(rs.next()){
                  String isFile = rs.getString(1);
                  System.out.println(isFile);
                  if(isFile!=null){
                       //JOptionPane.showMessageDialog(null, "File Detected Moving to Archived...");
                       ArchiveFile();
                  }else if(isFile==null){
                 // JOptionPane.showMessageDialog(null, "File Not Detected Goto Delete Record...");
                  DeleteFile();
                  }
              } 
                  conn.close();
          }catch (Exception e) {
              DC();
              e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1061", JOptionPane.ERROR_MESSAGE);
        }
      }
      
      public static void ArchiveFile(){ 
           int row =  jTable5.getSelectedRow();
 String req = jTable5.getModel().getValueAt(row, 0).toString();
           String sql = "INSERT INTO `company_archive_file`(`c_requirement_file_id`, `c_requirement_id`, `c_information_id`, `pdf_doccument`, `file_name`) \n" +
"SELECT  `c_requirement_file_id`,`c_requirement_id`,`c_information_id`,`pdf_doccument`,`file_name`\n" +
"FROM company_requirement_file\n" +
"WHERE c_information_id='"+SelectedCompany.compid+"'\n" +
"AND c_requirement_id=(SELECT c_requirement_id from company_requirements WHERE c_requirement_title='"+req+"')";
          try{
               conn = SQLCon.ConnectDB();
             pst = conn.prepareStatement(sql);
             pst.executeUpdate();
             DeleteFile();
             conn.close();
          }catch (Exception e) {
              DC();
              e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1062", JOptionPane.ERROR_MESSAGE);
        }
      }
      
      public static void DeleteFile(){
           int row =  jTable5.getSelectedRow();
 String req = jTable5.getModel().getValueAt(row, 0).toString();
         String sql = "DELETE FROM company_requirement_file WHERE c_information_id='"+SelectedCompany.compid+"'\n" +
"AND c_requirement_id=(SELECT c_requirement_id from company_requirements WHERE c_requirement_title='"+req+"')";
         try{
              conn = SQLCon.ConnectDB();
             pst = conn.prepareStatement(sql);
             pst.executeUpdate();
              insertCompanyReq1();
              conn.close();
         }catch (Exception e) {
             DC();
             e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1063", JOptionPane.ERROR_MESSAGE);
        }
      }
      
       public static void insertCompanyReq1() { 
             int row =  jTable5.getSelectedRow();
 String req = jTable5.getModel().getValueAt(row, 0).toString();
             String sql = "INSERT INTO `company_requirement_file`(`c_requirement_id`, `c_information_id`, `pdf_doccument`) VALUES ((SELECT c_requirement_id from company_requirements where c_requirement_title = '"+req+"'),'"+SelectedCompany.compid+"',?)";
       try{
            conn = SQLCon.ConnectDB();
           pst = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
           if(hm.containsKey(row)){
               File pdfFile = new File(hm.get(row));
               byte[] pdfData = new byte[(int)pdfFile.length()]; 
               DataInputStream dis = new DataInputStream(new FileInputStream(pdfFile)); 
               dis.readFully(pdfData);  
               dis.close(); 
           pst.setBytes(1, pdfData);
       }else{
                 pst.setString(1, null);
               }
           pst.executeUpdate(); 
           rs = pst.getGeneratedKeys();
                        rs.next();
                        long pk = rs.getLong(1);
                        String returnFileID = String.valueOf(pk);
                        if(hm.containsKey(row)){ 
                        AddFileName(returnFileID);
                        }
                        conn.close();
    }catch(Exception e){
        DC();
        e.printStackTrace();
                        //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e,"Error 1055",JOptionPane.ERROR_MESSAGE);
     } 
        insertAudit();
    }

    public static void AddFileName(String str){
          String sql = "UPDATE company_requirement_file SET file_name=? where c_requirement_file_id='"+str+"'";
          try{
               conn = SQLCon.ConnectDB();
          pst = conn.prepareStatement(sql);
          pst.setString(1, "cdpo-c-"+str);
          pst.executeUpdate();
          conn.close();
          }catch(Exception e){
              DC();
              e.printStackTrace();
                        //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e,"Error 1058",JOptionPane.ERROR_MESSAGE);
     }
    }
    
    
     public static void insertAudit(){
         int row =  jTable5.getSelectedRow();
 String req = jTable5.getModel().getValueAt(row, 0).toString();
        String sql = "insert into audit (u_information_id,audit_activity,audit_description) values (?,?,?)";
        try {
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, SystemInfo.userid);
            pst.setString(2, AuditMessage.updateAddFileCompany); 
            pst.setString(3, req); 
            pst.executeUpdate(); 
            hm.clear(); 
           UIManagers.getNewUI();
           jTable5.setEnabled(true);
          JOptionPane.showMessageDialog(null, "Company requirement successfully updated...");
          UIManagers.applyOldUI();
          conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1065", JOptionPane.ERROR_MESSAGE);
        }
    }
      
      
      
  public void getDivision(){
        //String sql = "select c_division_title from company_division where status=1 ORDER by c_division_id DESC";
        String sql = "select c_division_title from company_division where status=1 ORDER by c_division_title ASC";
        try{
             conn = SQLCon.ConnectDB();
            jComboBox7.removeAllItems();
            jComboBox7.addItem("Company Division");
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery();
            while(rs.next()){
      jComboBox7.addItem(rs.getString(1));
            }
            conn.close();
        }catch(Exception e){
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
        } 
   }
        public void getSector(){
        //String sql = "select c_sector_title from company_sector where status=1 ORDER by c_sector_id DESC";
        String sql = "select c_sector_title from company_sector where status=1 ORDER by c_sector_title ASC";
        try{
             conn = SQLCon.ConnectDB();
            jComboBox8.removeAllItems();
            jComboBox8.addItem("Company Sector");
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery();
            while(rs.next()){
      jComboBox8.addItem(rs.getString(1));
            }
            conn.close();
        }catch(Exception e){
            DC();
            e.printStackTrace(); 
        } 
   }
    public void getCompanyReqPDF(){
        int row =  jTable5.getSelectedRow();
 String req = jTable5.getModel().getValueAt(row, 0).toString();
          byte[] fileBytes;
        String query;
        try {
             conn = SQLCon.ConnectDB();
            query = "SELECT pdf_doccument,file_name from company_requirement_file \n" +
"where c_information_id='"+SelectedCompany.compid+"'\n" +
"and pdf_doccument != ''\n" +         
"and c_requirement_id=(SELECT c_requirement_id from company_requirements WHERE c_requirement_title='"+req+"')";
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(query);
            if (rs.next()) {
                String dir = "company_files//";
                String pdf = rs.getString(2)+".pdf";  
                fileBytes = rs.getBytes(1);
                OutputStream targetFile = new FileOutputStream(dir+pdf);
                targetFile.write(fileBytes);
                targetFile.close();  
                    selectedRowCompDocProfile = dir+pdf;
                   PDFViewer pv =  new PDFViewer();
                    pv.companyadd = false;
                    pv.companyprofile = true;
                    pv.studentadd = false;
                    pv.studentprofile = false;
                    pv.setVisible(true); 
                   // JOptionPane.showMessageDialog(null, "PDF Downloaded"); 
                    System.out.println("PDF File Downloaded: "+pdf);
                }  
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
        }
    }
    
     public void checkDuplicate(){
          String cname = txtCompanyName.getText().toUpperCase().replace("'", "''");
          String cbranch = txtCompanyBranch.getText().toUpperCase().replace("'", "''") ;
          String sql = "SELECT a.c_name,b.c_branch FROM company_information a \n" +
"inner JOIN company_branch b  \n" +
"on a.c_information_id=b.c_information_id \n" +
"where a.c_name = '"+cname+"' and b.c_branch = '"+cbranch+"'";
          try{
               conn = SQLCon.ConnectDB();
              pst=conn.prepareStatement(sql);
              rs = pst.executeQuery();
              if(rs.next()){
                  UIManagers.getNewUI();
                  JOptionPane.showMessageDialog(null, "Company information already registered...");
                  UIManagers.applyOldUI();
              }else{
                  //JOptionPane.showMessageDialog(null, "update company Info...");
                  updateCompanyInfo();
              }
              conn.close();
          }catch (Exception e) {
              DC();
              e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1066", JOptionPane.ERROR_MESSAGE);
        }
      }
     
     public void updateCompanyInfo(){
         String sql = "UPDATE `company_information` \n" +
"SET `c_name`=?,`c_contact`=?,`c_sector_id`=(select c_sector_id from company_sector where c_sector_title='"+jComboBox8.getSelectedItem()+"'),`c_division_id`=(select c_division_id from company_division where c_division_title='"+jComboBox7.getSelectedItem()+"')\n" +
"WHERE `c_information_id`='"+SelectedCompany.compid+"'";
         try{
              conn = SQLCon.ConnectDB();
             pst = conn.prepareStatement(sql); 
             pst.setString(1, txtCompanyName.getText().toUpperCase().trim());
             pst.setString(2, txtCompNo.getText().trim()); 
              pst.executeUpdate();
              if (changeAvatar) {
                                updateCompanyImage();
                            } else {
                              UpdateBranch();   
                            }  
              conn.close();
         }catch (Exception e){
         DC();
         e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1067", JOptionPane.ERROR_MESSAGE);
        }
     }
     
      public void updateCompanyImage(){
         String sql = "UPDATE `company_information` \n" +
"SET `c_logo`=? WHERE `c_information_id`='"+SelectedCompany.compid+"'";
         try{
              conn = SQLCon.ConnectDB();
             pst = conn.prepareStatement(sql); 
                                InputStream is = new FileInputStream(new File("user.png"));
                                pst.setBlob(1, is); 
                             pst.executeUpdate();
                             UpdateBranch();
             conn.close();
         }catch (Exception e) {
             DC();
             e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1067", JOptionPane.ERROR_MESSAGE);
        }
     }
     
     public void UpdateBranch(){
         String sql = "UPDATE `company_branch` \n" +
"SET `c_branch`=?,`c_address`=?\n" +
"WHERE `c_information_id`='"+SelectedCompany.compid+"'";
         try{
              conn = SQLCon.ConnectDB();
             pst = conn.prepareStatement(sql);
             pst.setString(1, txtCompanyBranch.getText().toUpperCase());
             pst.setString(2, txtCompanyAddress.getText());
             pst.executeUpdate();
             insertUpdateAudit();
             conn.close();
         }catch (Exception e) {
             DC();
             e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1068", JOptionPane.ERROR_MESSAGE);
        }
     }
     
           public void insertUpdateAudit(){ 
        String sql = "insert into audit (u_information_id,audit_activity,audit_description) values (?,?,?)";
        try { conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, SystemInfo.userid);
            pst.setString(2, AuditMessage.updateCompany); 
            pst.setString(3, txtCompanyName.getText().toUpperCase() + " "+ txtCompanyBranch.getText().toUpperCase()); 
            pst.executeUpdate();  
           UIManagers.getNewUI(); 
          JOptionPane.showMessageDialog(null, "Company information successfully updated...");
          pnlCompanies.CompanyListFunction();
          UIManagers.applyOldUI();
          conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1065", JOptionPane.ERROR_MESSAGE);
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
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        pnlStudentPersonalInfo = new javax.swing.JPanel();
        txtCompanyName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        txtCompanyBranch = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtCompanyAddress = new javax.swing.JTextArea();
        jComboBox7 = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jSeparator17 = new javax.swing.JSeparator();
        jLabel19 = new javax.swing.JLabel();
        jComboBox8 = new javax.swing.JComboBox<>();
        jSeparator18 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtCompNo = new javax.swing.JTextField();
        jSeparator9 = new javax.swing.JSeparator();
        pnlStudentPersonalInfo4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jSeparator6 = new javax.swing.JSeparator();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1090, 520));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jButton6.setBackground(new java.awt.Color(84, 127, 206));
        jButton6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Update");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(84, 127, 206));
        jButton7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Deployed List");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(84, 127, 206));
        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Intern List");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        pnlStudentPersonalInfo.setBackground(new java.awt.Color(255, 255, 255));
        pnlStudentPersonalInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Company Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        txtCompanyName.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtCompanyName.setForeground(new java.awt.Color(102, 102, 102));
        txtCompanyName.setText("NAME");
        txtCompanyName.setToolTipText("Student number");
        txtCompanyName.setBorder(null);
        txtCompanyName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCompanyNameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCompanyNameFocusLost(evt);
            }
        });
        txtCompanyName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCompanyNameMouseClicked(evt);
            }
        });
        txtCompanyName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCompanyNameKeyReleased(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Business_18px_1.png"))); // NOI18N

        jSeparator3.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator3.setForeground(new java.awt.Color(84, 127, 206));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Location_18px.png"))); // NOI18N

        txtCompanyBranch.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtCompanyBranch.setForeground(new java.awt.Color(102, 102, 102));
        txtCompanyBranch.setText("BRANCH");
        txtCompanyBranch.setToolTipText("Firstname");
        txtCompanyBranch.setBorder(null);
        txtCompanyBranch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCompanyBranchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCompanyBranchFocusLost(evt);
            }
        });
        txtCompanyBranch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCompanyBranchMouseClicked(evt);
            }
        });
        txtCompanyBranch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCompanyBranchActionPerformed(evt);
            }
        });
        txtCompanyBranch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCompanyBranchKeyReleased(evt);
            }
        });

        jSeparator7.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator7.setForeground(new java.awt.Color(84, 127, 206));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Address_18px.png"))); // NOI18N

        jSeparator8.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator8.setForeground(new java.awt.Color(84, 127, 206));

        jScrollPane4.setBorder(null);

        txtCompanyAddress.setColumns(20);
        txtCompanyAddress.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtCompanyAddress.setForeground(new java.awt.Color(102, 102, 102));
        txtCompanyAddress.setRows(5);
        txtCompanyAddress.setText("ADDRESS");
        txtCompanyAddress.setBorder(null);
        txtCompanyAddress.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCompanyAddressFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCompanyAddressFocusLost(evt);
            }
        });
        txtCompanyAddress.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCompanyAddressMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(txtCompanyAddress);

        jComboBox7.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jComboBox7.setForeground(new java.awt.Color(102, 102, 102));
        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Company Division" }));
        jComboBox7.setToolTipText("Departments");
        jComboBox7.setBorder(null);
        jComboBox7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox7ItemStateChanged(evt);
            }
        });
        jComboBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox7ActionPerformed(evt);
            }
        });

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Grid_2_18px.png"))); // NOI18N

        jSeparator17.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator17.setForeground(new java.awt.Color(84, 127, 206));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Connect_Develop_18px.png"))); // NOI18N

        jComboBox8.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jComboBox8.setForeground(new java.awt.Color(102, 102, 102));
        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Company Sector" }));
        jComboBox8.setToolTipText("Courses");
        jComboBox8.setBorder(null);
        jComboBox8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox8ItemStateChanged(evt);
            }
        });

        jSeparator18.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator18.setForeground(new java.awt.Color(84, 127, 206));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Date Registered: 01/01/2019");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Intern: ");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Deployed:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(102, 102, 102));
        jLabel12.setText("Current Intern:");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 102, 102));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jSeparator12.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator12.setForeground(new java.awt.Color(84, 127, 206));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Web_Camera_125px.png"))); // NOI18N
        jLabel14.setToolTipText("Update Company Logo");
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Phone_18px_1.png"))); // NOI18N

        txtCompNo.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtCompNo.setForeground(new java.awt.Color(102, 102, 102));
        txtCompNo.setText("CONTACT NUMBER");
        txtCompNo.setToolTipText("Company Contact Number");
        txtCompNo.setBorder(null);
        txtCompNo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCompNoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCompNoFocusLost(evt);
            }
        });
        txtCompNo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCompNoMouseClicked(evt);
            }
        });
        txtCompNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCompNoActionPerformed(evt);
            }
        });
        txtCompNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCompNoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCompNoKeyReleased(evt);
            }
        });

        jSeparator9.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator9.setForeground(new java.awt.Color(84, 127, 206));

        javax.swing.GroupLayout pnlStudentPersonalInfoLayout = new javax.swing.GroupLayout(pnlStudentPersonalInfo);
        pnlStudentPersonalInfo.setLayout(pnlStudentPersonalInfoLayout);
        pnlStudentPersonalInfoLayout.setHorizontalGroup(
            pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addGap(0, 150, Short.MAX_VALUE)
                        .addComponent(jLabel14)
                        .addGap(0, 145, Short.MAX_VALUE))
                    .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox8, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator17, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator18, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jComboBox7, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator7)
                            .addComponent(txtCompanyBranch)))
                    .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane4)))
                    .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCompanyName)
                            .addComponent(jSeparator3)))
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator12)
                    .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator9)
                            .addComponent(txtCompNo))))
                .addContainerGap())
        );
        pnlStudentPersonalInfoLayout.setVerticalGroup(
            pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCompanyName, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addComponent(txtCompanyBranch, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCompNo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                                .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator18, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3))
        );

        pnlStudentPersonalInfo4.setBackground(new java.awt.Color(255, 255, 255));
        pnlStudentPersonalInfo4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Company Requirements", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N
        pnlStudentPersonalInfo4.setPreferredSize(new java.awt.Dimension(362, 500));

        jScrollPane5.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jTable5.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jTable5.setForeground(new java.awt.Color(102, 102, 102));
        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Requirements", "PDF Document"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable5.setGridColor(new java.awt.Color(255, 255, 255));
        jTable5.setRowHeight(25);
        jTable5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable5MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable5MousePressed(evt);
            }
        });
        jTable5.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTable5PropertyChange(evt);
            }
        });
        jTable5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable5KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable5KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTable5KeyTyped(evt);
            }
        });
        jScrollPane5.setViewportView(jTable5);

        jSeparator6.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator6.setForeground(new java.awt.Color(84, 127, 206));

        javax.swing.GroupLayout pnlStudentPersonalInfo4Layout = new javax.swing.GroupLayout(pnlStudentPersonalInfo4);
        pnlStudentPersonalInfo4.setLayout(pnlStudentPersonalInfo4Layout);
        pnlStudentPersonalInfo4Layout.setHorizontalGroup(
            pnlStudentPersonalInfo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfo4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlStudentPersonalInfo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        pnlStudentPersonalInfo4Layout.setVerticalGroup(
            pnlStudentPersonalInfo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfo4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlStudentPersonalInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlStudentPersonalInfo4, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlStudentPersonalInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pnlStudentPersonalInfo4, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                        .addGap(7, 7, 7)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4)
                            .addComponent(jButton6)
                            .addComponent(jButton7))))
                .addGap(10, 10, 10))
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

    private void txtCompanyNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCompanyNameFocusGained
        // TODO add your handling code here:
          if(txtCompanyName.getText().equals("NAME")){
            txtCompanyName.setText("");
        }
    }//GEN-LAST:event_txtCompanyNameFocusGained

    private void txtCompanyNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCompanyNameFocusLost
        if(txtCompanyName.getText().equals("")){
            txtCompanyName.setText("NAME");
        }         // TODO add your handling code here:
    }//GEN-LAST:event_txtCompanyNameFocusLost

    private void txtCompanyNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCompanyNameMouseClicked
         if(txtCompanyName.getText().equals("NAME")){
            txtCompanyName.setText("");
        }      // TODO add your handling code here:
    }//GEN-LAST:event_txtCompanyNameMouseClicked

    private void txtCompanyBranchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCompanyBranchFocusGained
        if(txtCompanyBranch.getText().equals("BRANCH")){
            txtCompanyBranch.setText("");
        }      // TODO add your handling code here:
    }//GEN-LAST:event_txtCompanyBranchFocusGained

    private void txtCompanyBranchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCompanyBranchFocusLost
      if(txtCompanyBranch.getText().equals("")){
            txtCompanyBranch.setText("BRANCH");
        }                  // TODO add your handling code here:
    }//GEN-LAST:event_txtCompanyBranchFocusLost

    private void txtCompanyBranchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCompanyBranchMouseClicked
       if(txtCompanyBranch.getText().equals("BRANCH")){
            txtCompanyBranch.setText("");
        }         // TODO add your handling code here:
    }//GEN-LAST:event_txtCompanyBranchMouseClicked

    private void txtCompanyBranchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCompanyBranchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCompanyBranchActionPerformed

    private void jTable5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable5MouseClicked
int row =  jTable5.getSelectedRow();
 String req = jTable5.getModel().getValueAt(row, 0).toString();
   //    selectedRowCompDocProfile =  hm.get(row);
  if (evt.getClickCount() == 2) {
      /*
        if("".equals(selectedRowCompDocProfile) || selectedRowCompDocProfile == null){
         JOptionPane.showMessageDialog(null, "Add PDF Document First...");
      }else{
       PDFViewer pv =  new PDFViewer();
        pv.companyadd = false;
        pv.companyprofile = true;
       pv.studentadd = false;
       pv.studentprofile = false;
       pv.setVisible(true);
         }
*/   
        String query;
        try {
             conn = SQLCon.ConnectDB();
            query = "SELECT  file_name from company_requirement_file \n" +
"where c_information_id='"+SelectedCompany.compid+"'\n" +
"and pdf_doccument != ''\n" +         
"and c_requirement_id=(SELECT c_requirement_id from company_requirements WHERE c_requirement_title='"+req+"')";
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(query);
            if (rs.next()) {
                new File("company_files").mkdirs();
                String dir = "company_files//";
                String pdf = rs.getString(1)+".pdf";
                File f  = new File(dir+pdf);
                if(f.exists() && !f.isDirectory()){
                   // JOptionPane.showMessageDialog(null, "Already Downloaded"); 
                    selectedRowCompDocProfile = dir+pdf;
                   PDFViewer pv =  new PDFViewer();
                    pv.companyadd = false;
                    pv.companyprofile = true;
                    pv.studentadd = false;
                    pv.studentprofile = false;
                    pv.setVisible(true);
                    System.out.println("PDF File already Exist: "+pdf);
            }else{  
                    System.out.println("PDF File not Exist: "+pdf);
                getCompanyReqPDF();
                } 
            }else{
                UIManagers.getNewUI();
                JOptionPane.showMessageDialog(null, "Add the requirement file first...");
                UIManagers.applyOldUI();
            }
           conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
        } 
  }
   
    }//GEN-LAST:event_jTable5MouseClicked

    private void jTable5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable5MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable5MousePressed

    private void jTable5PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTable5PropertyChange

    }//GEN-LAST:event_jTable5PropertyChange

    private void jTable5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable5KeyPressed

    }//GEN-LAST:event_jTable5KeyPressed

    private void jTable5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable5KeyReleased

    }//GEN-LAST:event_jTable5KeyReleased

    private void jTable5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable5KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable5KeyTyped

    private void jComboBox7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox7ItemStateChanged
DivisionBox();        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox7ItemStateChanged

    private void jComboBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox7ActionPerformed

    private void jComboBox8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox8ItemStateChanged
SectorBox();        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox8ItemStateChanged

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

        int cname_length = txtCompanyName.getText().length();
 for(int i=0; i<cname_length; i++){
      if(txtCompanyName.getText().charAt(0)==' '){
   txtCompanyName.setText(txtCompanyName.getText().replaceFirst(" ", ""));
 }
 }
  int cbranch_length = txtCompanyBranch.getText().length();
 for(int i=0; i<cbranch_length; i++){
      if(txtCompanyBranch.getText().charAt(0)==' '){
   txtCompanyBranch.setText(txtCompanyBranch.getText().replaceFirst(" ", ""));
 }
 }
       
 
        UIManagers.getNewUI();
  txtCompanyName.setText(txtCompanyName.getText().replace("\\", ""));
  txtCompanyBranch.setText(txtCompanyBranch.getText().replace("\\", ""));
        if(txtCompanyName.getText().equals("") || txtCompanyName.getText().equals("NAME")){
    JOptionPane.showMessageDialog(null, "Company name is required");
    txtCompanyName.requestFocus();
}else if(txtCompanyBranch.getText().equals("") || txtCompanyBranch.getText().equals("BRANCH")){
      JOptionPane.showMessageDialog(null, "Company branch is required");
    txtCompanyBranch.requestFocus();
}else if(txtCompanyAddress.getText().equals("") || txtCompanyAddress.getText().equals("ADDRESS")){
      JOptionPane.showMessageDialog(null, "Company address is required");
    txtCompanyAddress.requestFocus();
}else if(txtCompNo.getText().equals("") || txtCompNo.getText().equals("CONTACT NUMBER")){
      JOptionPane.showMessageDialog(null, "Company contact is required");
    txtCompNo.requestFocus();
}else if(jComboBox7.getSelectedItem().equals("Company Division")){
     JOptionPane.showMessageDialog(null, "Company division is required");
    jComboBox7.requestFocus();
}else if(jComboBox8.getSelectedItem().equals("Company Sector")){
     JOptionPane.showMessageDialog(null, "Company sector is required");
    jComboBox8.requestFocus();
}else{
     f.ClassDisconnected.flag = false;
   // JOptionPane.showMessageDialog(null, "Niceaaa");
   if(txtCompanyName.getText().equals(SelectedCompany.cname) && txtCompanyBranch.getText().equals(SelectedCompany.cbranch)){
   //  JOptionPane.showMessageDialog(null, "Update company info");
        updateCompanyInfo();
   }else{
       checkDuplicate();
   }
    
    
}
        UIManagers.applyOldUI();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
 new DynamicPanel(Home.pnlDynamic, new panels.pnlCompanyEmployed());    //     new DynamicPanel(pnlDynamic, new panels.pnlStudentLogs());
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
 
new DynamicPanel(Home.pnlDynamic, new panels.pnlCompanyIntern());   //     new DynamicPanel(pnlDynamic, new panels.pnlStudentActivities());
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txtCompanyAddressFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCompanyAddressFocusGained
 if(txtCompanyAddress.getText().equals("ADDRESS")){
            txtCompanyAddress.setText("");
        }          // TODO add your handling code here:
    }//GEN-LAST:event_txtCompanyAddressFocusGained

    private void txtCompanyAddressMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCompanyAddressMouseClicked
 if(txtCompanyAddress.getText().equals("ADDRESS")){
            txtCompanyAddress.setText("");
        }          // TODO add your handling code here:
    }//GEN-LAST:event_txtCompanyAddressMouseClicked

    private void txtCompanyAddressFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCompanyAddressFocusLost
 if(txtCompanyAddress.getText().equals("")){
            txtCompanyAddress.setText("ADDRESS");
        }             // TODO add your handling code here:
    }//GEN-LAST:event_txtCompanyAddressFocusLost

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
  UIManagers.getNewUI();
        JLabel img;
        JButton open;
        JFileChooser jf = new JFileChooser();
        // Create label
        img = new JLabel();

        // Let label come fatty!!
        img.setPreferredSize(new Dimension(175, 175));

        // Set label as accessory
        jf.setAccessory(img);

        // Accept only image files
        jf.setAcceptAllFileFilterUsed(false);

        // Create filter for image files
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif");

        // Set it as current filter
        jf.setFileFilter(filter);

        // Add property change listener
        jf.addPropertyChangeListener(new PropertyChangeListener() {

            // When any JFileChooser property changes, this handler
            // is executed
            public void propertyChange(final PropertyChangeEvent pe) {
                // Create SwingWorker for smooth experience
                SwingWorker<Image, Void> worker = new SwingWorker<Image, Void>() {

                    // The image processing method
                    protected Image doInBackground() {
                        // If selected file changes..
                        if (pe.getPropertyName().equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)) {
                            // Get selected file
                            File f = jf.getSelectedFile();

                            try {
                                // Create FileInputStream for file
                                FileInputStream fin = new FileInputStream(f);

                                // Read image from fin
                                BufferedImage bim = ImageIO.read(fin);

                                // Return the scaled version of image
                                return bim.getScaledInstance(178, 170, BufferedImage.SCALE_FAST);

                            } catch (Exception e) {
                                // If there is a problem reading image,
                                // it might not be a valid image or unable
                                // to read
                                img.setText(" Not valid image/Unable to read");
                            }
                        }

                        return null;
                    }

                    protected void done() {
                        try {
                            // Get the image
                            Image i = get(1L, TimeUnit.NANOSECONDS);

                            // If i is null, go back!
                            if (i == null) {
                                return;
                            }

                            // Set icon otherwise
                            img.setIcon(new ImageIcon(i));
                        } catch (Exception e) {
                            // Print error occured
                            img.setText(" Error occured.");
                        }
                    }
                };

                // Start worker thread
                worker.execute();
            }

        });
        //jf.showOpenDialog(null);
        int result = jf.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jf.getSelectedFile();
            if (selectedFile != null) {
                String path = selectedFile.getAbsolutePath();
                File file = new File(path);
                if (file.exists() && !file.isDirectory()) {
                    jLabel14.setIcon(ResizeImage(path));
                    s = path;
                    changeAvatar = true;
                     ///START RESIZE IMAGE///
                    try{
                      BufferedImage originalImage = ImageIO.read(new File(s));
    int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB
        : originalImage.getType(); 
    BufferedImage resizeImageBmp = ImageReduce.resizeImage(originalImage, type);
    ImageIO.write(resizeImageBmp, "png", new File("user.png"));
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    ///END RESIZE IMAGE//
                } else {
                    JOptionPane.showMessageDialog(null, "Image not found...");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Error opening image...");
            }
        }

      UIManagers.applyOldUI();
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel14MouseClicked

    private void txtCompNoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCompNoFocusGained
        if(txtCompNo.getText().equals("CONTACT NUMBER")){
            txtCompNo.setText("");
        }          // TODO add your handling code here:
    }//GEN-LAST:event_txtCompNoFocusGained

    private void txtCompNoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCompNoFocusLost
        if(txtCompNo.getText().equals("")){
            txtCompNo.setText("CONTACT NUMBER");
        }     // TODO add your handling code here:
    }//GEN-LAST:event_txtCompNoFocusLost

    private void txtCompNoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCompNoMouseClicked
        if(txtCompNo.getText().equals("CONTACT NUMBER")){
            txtCompNo.setText("");
        }          // TODO add your handling code here:
    }//GEN-LAST:event_txtCompNoMouseClicked

    private void txtCompNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCompNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCompNoActionPerformed

    private void txtCompNoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCompNoKeyReleased
if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
            shiftPressed = false;
        }
        FilterCompanyContact();           // TODO add your handling code here:
    }//GEN-LAST:event_txtCompNoKeyReleased

    private void txtCompNoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCompNoKeyPressed
       if(shiftPressed && evt.getKeyCode()==KeyEvent.VK_EQUALS){
            txtCompNo.setText(txtCompNo.getText()+"+");
        }else if(shiftPressed && evt.getKeyCode()==KeyEvent.VK_9){
            txtCompNo.setText(txtCompNo.getText()+"(");
        }else if(shiftPressed && evt.getKeyCode()==KeyEvent.VK_0){
            txtCompNo.setText(txtCompNo.getText()+")");
        }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
            shiftPressed = true;
        }
    }//GEN-LAST:event_txtCompNoKeyPressed

    private void txtCompanyBranchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCompanyBranchKeyReleased
//     txtCompanyBranch.setText(txtCompanyBranch.getText().toUpperCase());         // TODO add your handling code here:
    }//GEN-LAST:event_txtCompanyBranchKeyReleased

    private void txtCompanyNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCompanyNameKeyReleased
//      txtCompanyName.setText(txtCompanyName.getText().toUpperCase());  // TODO add your handling code here:
    }//GEN-LAST:event_txtCompanyNameKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    public static javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    public static javax.swing.JTable jTable5;
    private javax.swing.JPanel pnlStudentPersonalInfo;
    private javax.swing.JPanel pnlStudentPersonalInfo4;
    private javax.swing.JTextField txtCompNo;
    private javax.swing.JTextArea txtCompanyAddress;
    private javax.swing.JTextField txtCompanyBranch;
    private javax.swing.JTextField txtCompanyName;
    // End of variables declaration//GEN-END:variables
 public static ImageIcon ResizeImage(String imgPath) {
        ImageIcon MyImage = new ImageIcon(imgPath);
        Image img = MyImage.getImage();
        Image newImage = img.getScaledInstance(jLabel14.getWidth(), jLabel14.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImage);
        return image;
    }
}
