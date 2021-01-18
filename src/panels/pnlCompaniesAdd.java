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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
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
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
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
import net.proteanit.sql.DbUtils;  
import java.awt.AlphaComposite;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.io.File; 
import javax.imageio.ImageIO;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
/**
 *
 * @author RojeruSan
 */
public final class pnlCompaniesAdd extends javax.swing.JPanel {
private static HashMap<Integer,String> hm = new HashMap<Integer,String>();
public static String selectedRowCompDoc;
public static Connection conn = null;
public static PreparedStatement pst =  null;
public static ResultSet rs = null;
public boolean shiftPressed;
AuditMessage am= new AuditMessage();
boolean changeAvatar = false;
  DocumentFilter filter = new UppercaseDocumentFilter(); 
String s;
public static String currentDir = "user.home";
    String returnID = ""; 
    ArrayList<String> list = new ArrayList<String>();
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        
        // static pnlCompaniesAdd cmp = new pnlCompaniesAdd();  
         static pnlCompaniesAdd cmp;
     public static void main(String[] args) {
        try {
            // The following line would throw ExceptionInInitializerError
           cmp = new pnlCompaniesAdd();
        } catch (Throwable t) {
            System.out.println(t);
        }
        // The following line would cause NoClassDefFoundError
        cmp = new pnlCompaniesAdd();
    }
 

         
         
         
    /**
     * Creates new form pnlHome
     */
    public pnlCompaniesAdd() {
        initComponents();
         f.ClassDisconnected.flag = false;
        DivisionBox();
        SectorBox(); 
       reqListTable();
        getDivision();
        getSector();
        
       
        
         jScrollPane5.getViewport().setBackground(Color.WHITE);
          jScrollPane1.getViewport().setBackground(Color.WHITE);
          jTable1.getTableHeader().setReorderingAllowed(false);
          jTable5.getTableHeader().setReorderingAllowed(false);
         
        CompanyTableList();
        hm.clear();
       FilterCompanyContact(); 
       ((AbstractDocument) txtCompanyName.getDocument()).setDocumentFilter(filter); 
    ((AbstractDocument) txtCompanyBranch.getDocument()).setDocumentFilter(filter); 
    add(txtCompanyName);
    add(txtCompanyBranch);
      //  UIManager.getDefaults().put("TableHeader.cellBorder" , BorderFactory.createEmptyBorder(0,0,0,0));
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
    
    public void FilterCompanyContact(){
        txtCompNo.addKeyListener(new KeyAdapter() {
    public void keyTyped(KeyEvent e) { 
      char c = e.getKeyChar();
      if (!((c >= '0') && (c <= '9') ||
         (c == KeyEvent.VK_SHIFT) || 
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
    
    public void reqListTable(){
        UIManagers.applyOldUI();
         getCompanyReqList();
        CompanyReqList();
        jTable5.getColumn("PDF Document").setCellRenderer(new ButtonCellRenderer1());
        jTable5.getColumn("PDF Document").setCellEditor(new ButtonCellEditor1()); 
    }
    /*
      private static BufferedImage resizeImage(BufferedImage originalImage, int type) {
    int IMG_WIDTH = 512;
    int IMG_CLAHEIGHT = 512;
    BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_CLAHEIGHT,
        type);
    Graphics2D g = resizedImage.createGraphics();
    g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_CLAHEIGHT, null);
    g.dispose();
    return resizedImage;
  }
      */
    public void getCompanyReqList(){ 
        conn = SQLCon.ConnectDB();
         //String sql = "select c_requirement_title,file_handler from company_requirements where status=1 order by c_requirement_id desc";
         String sql = "select c_requirement_title,file_handler from company_requirements where status=1 order by c_requirement_title asc";
            try{
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery();
            jTable5.setModel(DbUtils.resultSetToTableModel(rs));  
            conn.close();
        }catch (Exception e) {
			DC();
                        e.printStackTrace();
            ////JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1017", JOptionPane.ERROR_MESSAGE);
        }
     
    }
    
       
     public void getDivision(){
         conn = SQLCon.ConnectDB();
        //String sql = "select c_division_title from company_division where status=1 ORDER by c_division_id DESC";
        String sql = "select c_division_title from company_division where status=1 ORDER by c_division_title ASC";
        try{
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
        } 
   }
        public void getSector(){
            conn = SQLCon.ConnectDB();
        //String sql = "select c_sector_title from company_sector where status=1 ORDER by c_sector_id DESC";
        String sql = "select c_sector_title from company_sector where status=1 ORDER by c_sector_title asc";
        try{
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
    
    
    
     public void CompanyTableList(){
          JTableHeader Theader = jTable1.getTableHeader();
        Theader.setBackground(new Color(84,127,206)); // change the Background color
        Theader.setForeground(Color.WHITE); // change the Foreground
        Theader.setFont(new Font("Segoe UI", Font.BOLD, 13)); // font name style size
        ((DefaultTableCellRenderer)Theader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER); // center header text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
       jTable1.getColumnModel().getColumn(0).setPreferredWidth(180);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(20);
         TableColumnModel tcm = Theader.getColumnModel();
         TableColumn tc0 = tcm.getColumn(0);
         TableColumn tc1 = tcm.getColumn(1); 
          tc0.setHeaderValue("Similar Company Name");
          tc1.setHeaderValue("BRANCH"); 
        jTable1.setDefaultEditor(Object.class, null);
        
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
                setText("Remove");
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
                       jTable5.setValueAt(null, row, 1); 
                      hm.remove(row);
                }else{
                JFileChooser fileChooser = new JFileChooser();
                 fileChooser.setCurrentDirectory(new File(currentDir));
     fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Document", "pdf"));
                //fileChooser.showOpenDialog(null);
                 int result=fileChooser.showSaveDialog(null);
                 if(result==JFileChooser.APPROVE_OPTION){
        File selectedFile = fileChooser.getSelectedFile();
        String path = selectedFile.getAbsolutePath();
        currentDir = path;
        hm.put(row,path);
        jTable5.setValueAt(Boolean.valueOf(true), row, 1); 
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
                editor.setText("Remove" );
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
   
      
      public void checkCompanyDuplicate(){
          conn = SQLCon.ConnectDB();
          String str = txtCompanyName.getText().replace("'", "''"); 
          String sql = "SELECT a.c_name,b.c_branch FROM company_information a\n" +
"inner JOIN company_branch b \n" +
"on a.c_information_id=b.c_information_id\n" +
"where a.c_name LIKE '%"+str+"%' LIMIT 5";
          try{
            pst = conn.prepareStatement(sql);
            rs=pst.executeQuery(); 
            jTable1.setModel(DbUtils.resultSetToTableModel(rs)); 
            if(!rs.next()){
               
            }
            CompanyTableList();
            conn.close();
          }catch (Exception e) {
              DC();
            e.printStackTrace();
            /////JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1050", JOptionPane.ERROR_MESSAGE);
        }
      }
 
      public void checkDuplicate(){
          conn = SQLCon.ConnectDB();
          String cname1 = txtCompanyName.getText().replace("\\", "");
          String cname = cname1.toUpperCase();
          String branch1 = txtCompanyBranch.getText().replace("\\", "");
          String cbranch = branch1.toUpperCase();
          String sql = "SELECT a.c_name,b.c_branch FROM company_information a \n" +
"inner JOIN company_branch b  \n" +
"on a.c_information_id=b.c_information_id \n" +
"where a.c_name = '"+cname.replace("'", "\\'")+"' and b.c_branch = '"+cbranch.replace("'", "\\'")+"'";
          try{
              pst=conn.prepareStatement(sql);
              rs = pst.executeQuery();
              if(rs.next()){
                  UIManagers.getNewUI();
                  JOptionPane.showMessageDialog(null, "Company information already registered...");
                  UIManagers.applyOldUI();
              }else{
                  //JOptionPane.showMessageDialog(null, "add company Info...");
                  insertCompanyInfo();
              }
              conn.close();
          }catch (Exception e) {
              DC();
            e.printStackTrace();
            ////JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1051", JOptionPane.ERROR_MESSAGE);
        }
      }
      
      public void insertCompanyInfo() {
          conn = SQLCon.ConnectDB();
         String sql = "INSERT INTO `company_information`(`c_logo`, `c_name`, `c_contact`, `c_division_id`, `c_sector_id`,  `dateadded`) \n" +
"VALUES (?,?,?,(SELECT c_division_id from company_division where c_division_title='"+jComboBox7.getSelectedItem()+"'),(SELECT c_sector_id from company_sector where c_sector_title='"+jComboBox8.getSelectedItem()+"'),now())";
                        try{
                            pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                            if (changeAvatar) {
                                InputStream is = new FileInputStream(new File("user.png"));
                                pst.setBlob(1, is);
                            } else {
                                pst.setString(1, null);
                            }
                            pst.setString(2, txtCompanyName.getText().toUpperCase().toString().replace("'", "\\'").replace("\\", ""));
                            pst.setString(3, txtCompNo.getText());  
                        pst.executeUpdate();
                        rs = pst.getGeneratedKeys();
                        rs.next();
                        long pk = rs.getLong(1);
                        returnID = String.valueOf(pk);
                       insertCompanyBranch();
                       conn.close();
                    }catch(Exception e){
                        DC();
            e.printStackTrace();
                     //   //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e,"Error 1052",JOptionPane.ERROR_MESSAGE);
     }
    }
      
         public void insertCompanyBranch() {
             conn = SQLCon.ConnectDB();
         String sql = "INSERT INTO `company_branch`(`c_information_id`, `c_branch`, `c_address`) VALUES (?,?,?)";
                        try{
                            pst = conn.prepareStatement(sql); 
                            pst.setString(1, returnID);  
                            pst.setString(2, txtCompanyBranch.getText().toUpperCase().toString().replace("'", "\\'").replace("\\", ""));  
                            pst.setString(3, txtCompanyAddress.getText());   
                        pst.executeUpdate();  
                       insertCompanyReq();
                       conn.close();
                    }catch(Exception e){
                        DC();
            e.printStackTrace();
                      //  //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e,"Error 1053",JOptionPane.ERROR_MESSAGE);
     }
    }
         
public void insertCompanyReq(){
    conn = SQLCon.ConnectDB();
    //String sql = "SELECT `c_requirement_title` FROM `company_requirements` where status=1 order by `c_requirement_id` desc";
    String sql = "SELECT `c_requirement_title` FROM `company_requirements` where status=1 order by `c_requirement_title` asc";
    try{
       pst = conn.prepareStatement(sql);
       rs = pst.executeQuery();
       while(rs.next()){
           list.add(rs.getString(1));
       } 
        insertCompanyReq1();
        conn.close();
    }catch(Exception e){
        DC();
            e.printStackTrace();
                   //     //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e,"Error 1054",JOptionPane.ERROR_MESSAGE);
     }
}

    public void insertCompanyReq1() {
        
          String[] reqList = new String[list.size()];
        reqList = list.toArray(reqList); 
        for(int i=0; i<reqList.length; i++){
            conn = SQLCon.ConnectDB();
             String sql = "INSERT INTO `company_requirement_file`(`c_requirement_id`, `c_information_id`, `pdf_doccument`) VALUES ((SELECT c_requirement_id from company_requirements where c_requirement_title = '"+reqList[i]+"'),'"+returnID+"',?)";
       try{
           pst = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
           if(hm.containsKey(i)){
               File pdfFile = new File(hm.get(i));
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
                        if(hm.containsKey(i)){ 
                        AddFileName(returnFileID);
                        }
                        if(i==(reqList.length-1)){
                            int cat = (reqList.length-1);
                            System.out.print(i+" - "+cat);
                            conn.close();
                        }
                       
    }catch(Exception e){
		DC();
            e.printStackTrace();
                       // //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e,"Error 1055",JOptionPane.ERROR_MESSAGE);
     }
        }
        
        insertAudit();
    }

    public void AddFileName(String str){
        conn = SQLCon.ConnectDB();
          String sql = "UPDATE company_requirement_file SET file_name=? where c_requirement_file_id='"+str+"'";
          try{
          pst = conn.prepareStatement(sql);
          pst.setString(1, "cdpo-c-"+str);
          pst.executeUpdate();
          conn.close();
          }catch(Exception e){
			  DC();
            e.printStackTrace();
                        ////JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e,"Error 1058",JOptionPane.ERROR_MESSAGE);
     }
    }
       public void insertAudit(){
           conn = SQLCon.ConnectDB();
        String sql = "insert into audit (u_information_id,audit_activity,audit_description) values (?,?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, SystemInfo.userid);
            pst.setString(2, am.addCompany); 
            pst.setString(3, txtCompanyName.getText().toUpperCase() + " "+ txtCompanyBranch.getText().toUpperCase()); 
            pst.executeUpdate();
            txtCompanyName.setText("NAME");
          DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0);
    txtCompanyBranch.setText("BRANCH");
    txtCompanyAddress.setText("ADDRESS");
    txtCompNo.setText("CONTACT NUMBER");
    jComboBox7.setSelectedIndex(0); 
    jComboBox8.setSelectedIndex(0);
    jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Web_Camera_125px.png")));
    list.clear();
    hm.clear();
    changeAvatar = false;
           reqListTable();
           UIManagers.getNewUI();
          JOptionPane.showMessageDialog(null, "Company successfully registered...");
          UIManagers.applyOldUI();
          conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
           // //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1056", JOptionPane.ERROR_MESSAGE);
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
        pnlStudentPersonalInfo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        txtCompNo = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        pnlStudentPersonalInfo4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jSeparator6 = new javax.swing.JSeparator();
        jButton4 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1086, 506));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        pnlStudentPersonalInfo.setBackground(new java.awt.Color(255, 255, 255));
        pnlStudentPersonalInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Company Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Web_Camera_125px.png"))); // NOI18N
        jLabel1.setToolTipText("Add Company Logo");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        txtCompanyName.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtCompanyName.setForeground(new java.awt.Color(102, 102, 102));
        txtCompanyName.setText("NAME");
        txtCompanyName.setToolTipText("Company Name");
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
        txtCompanyBranch.setToolTipText("Company Branch");
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
        txtCompanyAddress.setToolTipText("Company Address");
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
        jComboBox7.setToolTipText("Company Division");
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
        jComboBox8.setToolTipText("Company Sector");
        jComboBox8.setBorder(null);
        jComboBox8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox8ItemStateChanged(evt);
            }
        });

        jSeparator18.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator18.setForeground(new java.awt.Color(84, 127, 206));

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jTable1.setForeground(new java.awt.Color(102, 102, 102));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Similar Company Name", "Branch"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setToolTipText("Similar Company");
        jTable1.setFocusable(false);
        jTable1.setRequestFocusEnabled(false);
        jTable1.setRowHeight(20);
        jTable1.setRowSelectionAllowed(false);
        jTable1.setShowHorizontalLines(false);
        jTable1.setShowVerticalLines(false);
        jScrollPane1.setViewportView(jTable1);

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 51, 51));
        jLabel31.setText("*");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 51, 51));
        jLabel32.setText("*");

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

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Phone_18px_1.png"))); // NOI18N

        jSeparator9.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator9.setForeground(new java.awt.Color(84, 127, 206));

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 51, 51));
        jLabel33.setText("*");

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 51, 51));
        jLabel34.setText("*");

        javax.swing.GroupLayout pnlStudentPersonalInfoLayout = new javax.swing.GroupLayout(pnlStudentPersonalInfo);
        pnlStudentPersonalInfo.setLayout(pnlStudentPersonalInfoLayout);
        pnlStudentPersonalInfoLayout.setHorizontalGroup(
            pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
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
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator3)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                                .addComponent(txtCompanyName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel31))))
                    .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator9)
                            .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                                .addComponent(txtCompNo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel34))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel32)
                                    .addComponent(jLabel33)))
                            .addComponent(jSeparator8)))
                    .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                                .addComponent(txtCompanyBranch)
                                .addGap(22, 22, 22))
                            .addComponent(jSeparator7))))
                .addContainerGap())
        );
        pnlStudentPersonalInfoLayout.setVerticalGroup(
            pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCompanyName, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCompanyBranch)
                        .addComponent(jLabel32))
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCompNo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                                .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator18, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE)
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

        jButton4.setBackground(new java.awt.Color(84, 127, 206));
        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Add Record");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlStudentPersonalInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton4))
                    .addComponent(pnlStudentPersonalInfo4, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlStudentPersonalInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pnlStudentPersonalInfo4, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
                        .addGap(7, 7, 7)
                        .addComponent(jButton4)))
                .addContainerGap())
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
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtCompanyNameFocusLost

    private void txtCompanyNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCompanyNameMouseClicked
        if(txtCompanyName.getText().equals("NAME")){
            txtCompanyName.setText("");
        }         
    }//GEN-LAST:event_txtCompanyNameMouseClicked

    private void txtCompanyBranchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCompanyBranchFocusGained
        if(txtCompanyBranch.getText().equals("BRANCH")){
            txtCompanyBranch.setText("");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtCompanyBranchFocusGained

    private void txtCompanyBranchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCompanyBranchFocusLost
        if(txtCompanyBranch.getText().equals("")){
            txtCompanyBranch.setText("BRANCH");
        }                // TODO add your handling code here:
    }//GEN-LAST:event_txtCompanyBranchFocusLost

    private void txtCompanyBranchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCompanyBranchMouseClicked
        if(txtCompanyBranch.getText().equals("BRANCH")){
            txtCompanyBranch.setText("");
        }           // TODO add your handling code here:
    }//GEN-LAST:event_txtCompanyBranchMouseClicked

    private void txtCompanyBranchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCompanyBranchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCompanyBranchActionPerformed

    private void jTable5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable5MouseClicked
UIManagers.getNewUI();
        int row =  jTable5.getSelectedRow();
       selectedRowCompDoc =  hm.get(row);
  if (evt.getClickCount() == 2) {
        if("".equals(selectedRowCompDoc) || selectedRowCompDoc == null){
         JOptionPane.showMessageDialog(null, "Add PDF Document First...");
      }else{
       PDFViewer pv =  new PDFViewer();
        pv.companyadd = true;
       pv.companyprofile = false;
       pv.studentadd = false;
       pv.studentprofile = false;
       pv.setVisible(true);
         } 
  }
   UIManagers.applyOldUI();
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

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
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
   // JOptionPane.showMessageDialog(null, "Niceaaa");
    checkDuplicate();
    
}
        UIManagers.applyOldUI();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jComboBox7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox7ItemStateChanged
DivisionBox();        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox7ItemStateChanged

    private void jComboBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox7ActionPerformed

    private void jComboBox8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox8ItemStateChanged
SectorBox();        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox8ItemStateChanged

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
        }          // TODO add your handling code here:
    }//GEN-LAST:event_txtCompanyAddressFocusLost

    private void txtCompanyNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCompanyNameKeyReleased
if(txtCompanyName.getText().equals("")|| txtCompanyName.getText().equals("NAME")){
     DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0);
} else{
   // txtCompanyName.setText(txtCompanyName.getText().toUpperCase());
   checkCompanyDuplicate();     
}
    }//GEN-LAST:event_txtCompanyNameKeyReleased

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
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
                    jLabel1.setIcon(ResizeImage(path));
                    s = path;
                    //JOptionPane.showMessageDialog(null, s);
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
                    
                    
                    changeAvatar = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Image not found...");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Error opening image...");
            }
        }

      UIManagers.applyOldUI();
    }//GEN-LAST:event_jLabel1MouseClicked

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
//  txtCompanyBranch.setText(txtCompanyBranch.getText().toUpperCase());        // TODO add your handling code here:
    }//GEN-LAST:event_txtCompanyBranchKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    public static javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTable jTable1;
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
        Image newImage = img.getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImage);
        return image;
    }
}
