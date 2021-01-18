/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panels;

import f.AuditMessage;
import f.GetStudentInfo;
import f.SQLCon;
import f.SelectedStudent;
import f.SystemInfo;
import f.UIManagers;
import home.Home;
import static home.Home.pnlDynamic;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
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
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.AbstractCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
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
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author RojeruSan
 */
public class pnlStudentProfile extends javax.swing.JPanel  implements ActionListener {
   //static pnlStudentProfile cmp = new pnlStudentProfile();
   
   static pnlStudentProfile cmp;
     public static void main(String[] args) {
        try {
            // The following line would throw ExceptionInInitializerError
           cmp = new pnlStudentProfile();
        } catch (Throwable t) {
            System.out.println(t);
        }
        // The following line would cause NoClassDefFoundError
        cmp = new pnlStudentProfile();
    }

   
   
    public static Connection conn= null;
    public static PreparedStatement pst = null;
    public static ResultSet rs = null;
    private static HashMap<Integer, String> hm = new HashMap<Integer, String>();
    public static String selectedRowDocSProfile;
    boolean update;
    
public boolean shiftPressed;
    boolean changeAvatar = false;
    String ojtInformationID = "";
    ArrayList<String> list = new ArrayList<String>();
    Timestamp ts = new Timestamp(System.currentTimeMillis());
    String s;
    public static String currentDir = "user.home";
    public static boolean addNewRecord = false;
    public static String selectedStudentRecord = "";
    public static String selectedOjtRecord = "";
    boolean recordSelected = false;
    
  DocumentFilter filter = new UppercaseDocumentFilter(); 
boolean deptAction = true;
int selectedRecord; 
   	private JTable table;
	private DefaultTableModel tableModel; 
	private JPopupMenu popupMenu;
	private JMenuItem menuItemCopy;
	private JMenuItem menuItemAdd;
	private JMenuItem menuItemRemove;
	private JMenuItem menuItemRemoveAll;
        public static String selectedCompany,selectedBranch,scDepartment,sSupervisor,sContactNum,sAllowance,sHour,ssLevel;
      

         
        
    /**
     * Creates new form pnlHome
     */
    public pnlStudentProfile() {
        initComponents();
        f.ClassDisconnected.flag = false; 
            Home.pnlFooterAction.show();
  Home.printcompanylist = false;
        Home.printhistorylist = false; 
      Home.printuserrecord = false;
      Home.printuserlist = false;
      Home.printuserprofile  = false; 
      Home.printloglist = false;
      Home.printinternlist = false;
      Home.printdeployedlist = false; 
     Home.printstudentlist = false;
    Home.printstudentlog = false;
     Home.printstudentact = false;
     Home.printstudentrecord = true; 
	 Home.printstatistics = false;
        
        
        Home.HideSearch();
        getDepartment();
        getLevels();
        getHours();
        getContact();
        getAwards();
        f.StudentRecList.TableFromDatabase(SelectedStudent.sid);
        CompanyListFunction();
        GenderBox();
        DeptBox();
        CourseBox();
        YearBox();
        OjtHours();
        jTable1.getColumn("Action").setCellRenderer(new ButtonCellRenderer());
        jTable1.getColumn("Action").setCellEditor(new ButtonCellEditor());
        jTable2.getColumn("Action").setCellRenderer(new ButtonCellRenderer2());
        jTable2.getColumn("Action").setCellEditor(new ButtonCellEditor2());
        reqListTable();

        jScrollPane1.getViewport().setBackground(Color.WHITE);
        jScrollPane2.getViewport().setBackground(Color.WHITE);
        jScrollPane3.getViewport().setBackground(Color.WHITE);
        jScrollPane4.getViewport().setBackground(Color.WHITE);
        jScrollPane5.getViewport().setBackground(Color.WHITE);
        ContactTableList();
        AwardsTableList();
        OjtTableList();
        CompanyTableList();
        jComboBox2.setBackground(Color.WHITE); //Contact Combo Box
        AddContact();
        AddAward();
        ContactFilter();
        RecordsTableList();
        //hm.clear();
        jTable3.setSelectionModel(new ForcedListSelectionModel());
        jTable4.getTableHeader().setReorderingAllowed(false);
        addNewRecord = false;
       selectedStudentRecord = "";
     selectedOjtRecord = "";
   recordSelected = false;
   deptAction = true; 
   
  txtCompInfo.setEditable(false); 
  txtDept.setEditable(false);
  txtSupervisor.setEditable(false);
  txtCompNo.setEditable(false);
  txtAllowance.setEditable(false);
  cboHours.setEnabled(false); 
   
 // constructs the popup menu
		popupMenu = new JPopupMenu();
		menuItemCopy = new JMenuItem("Copy");
		menuItemAdd = new JMenuItem("Add New Row");
		menuItemRemove = new JMenuItem("Remove Current Row");
		menuItemRemoveAll = new JMenuItem("Remove All Rows");
		
                menuItemCopy.addActionListener(this);
		menuItemAdd.addActionListener(this);
		menuItemRemove.addActionListener(this);
		menuItemRemoveAll.addActionListener(this);
		
		popupMenu.add(menuItemCopy);
                //popupMenu.add(menuItemAdd);
		//popupMenu.add(menuItemRemove);
		//popupMenu.add(menuItemRemoveAll);
		
		// sets the popup menu for the table
		jTable1.setComponentPopupMenu(popupMenu);
		
		jTable1.addMouseListener(new TableMouseListener(jTable1));
 
                DefaultTableModel model = (DefaultTableModel) jTable5.getModel();
            model.setRowCount(0); 
   UIManagers.applyOldUI();
   
   
   
      ((AbstractDocument) txtStudentNo.getDocument()).setDocumentFilter(filter); 
    ((AbstractDocument) txtFname.getDocument()).setDocumentFilter(filter); 
     ((AbstractDocument) txtMname.getDocument()).setDocumentFilter(filter); 
    ((AbstractDocument) txtLname.getDocument()).setDocumentFilter(filter); 
     ((AbstractDocument) txtSuffix.getDocument()).setDocumentFilter(filter); 
    ((AbstractDocument) txtDept.getDocument()).setDocumentFilter(filter); 
     ((AbstractDocument) txtSupervisor.getDocument()).setDocumentFilter(filter); 
    ((AbstractDocument) txtCompNo.getDocument()).setDocumentFilter(filter); 
     ((AbstractDocument) txtAllowance.getDocument()).setDocumentFilter(filter);  
    add(txtStudentNo);
    add(txtFname);
    add(txtMname);
    add(txtLname);
    add(txtSuffix);
    add(txtDept);
    add(txtSupervisor);
    add(txtCompNo);
    add(txtAllowance); 
    
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
      
     

    public static void CompanyListFunction() {

        jLabel2.setText("Date Registered: " + SelectedStudent.sdateadd);
        txtStudentNo.setText(SelectedStudent.sid);
        txtFname.setText(SelectedStudent.fname);
 if (SelectedStudent.mname.equals("")) {
            txtMname.setText("MIDDLENAME");
        } else {
            txtMname.setText(SelectedStudent.mname);
        }
        txtLname.setText(SelectedStudent.lname);
        if (SelectedStudent.sname.equals("")) {
            txtSuffix.setText("SUFFIX");
        } else {
            txtSuffix.setText(SelectedStudent.sname);
        }
 jComboBox1.setSelectedItem(SelectedStudent.sgender);
        
         
          DefaultComboBoxModel model;
          model = (DefaultComboBoxModel) cboDept.getModel();
        String dept = SelectedStudent.sdept;
        if(model.getIndexOf(dept)==-1){
          cboDept.addItem(SelectedStudent.sdept);
                cboDept.setSelectedItem(SelectedStudent.sdept); 
        }else{
             cboDept.setSelectedItem(SelectedStudent.sdept);
        }
        
        
         model = (DefaultComboBoxModel) cboCourse.getModel();
        String course = SelectedStudent.scourse;
        if(model.getIndexOf(course)==-1){
          cboCourse.addItem(SelectedStudent.scourse);
                cboCourse.setSelectedItem(SelectedStudent.scourse); 
        }else{
             cboCourse.setSelectedItem(SelectedStudent.scourse);
        }
        
        
        
         model = (DefaultComboBoxModel) cboLevel.getModel();
        String level = SelectedStudent.slevel;
        if(model.getIndexOf(level)==-1){
          cboLevel.addItem(SelectedStudent.slevel);
                cboLevel.setSelectedItem(SelectedStudent.slevel); 
        }else{
            getLevels();
             cboLevel.setSelectedItem(SelectedStudent.slevel);
        }
        

      
 
         

        new java.util.Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                try {
                    ImageIcon imageIcon = new ImageIcon(new ImageIcon(SelectedStudent.img).getImage().getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH));
                    lblImage.setIcon(imageIcon);
                    //   pnlCompanyProfile.lblImage.setIcon(imageIcon);
                } catch (Exception e) {
                    lblImage.setIcon(new javax.swing.ImageIcon(pnlStudents.class.getResource("/images/icons8_Name_125px.png")));
//             System.out.println("No Image\n"+e);
                }
            }
        }, 500 * 1);
    }

    public static void getContact() {
        String sql = "SELECT ct.s_contact_type_title as Contact,sc.s_contact_info as Information,sc.s_contact_id as Action FROM student_contact sc INNER JOIN student_contact_type ct ON sc.s_contact_type_id=ct.s_contact_type_id WHERE s_information_id='" + SelectedStudent.sid + "' ORDER BY ct.s_contact_type_title ASC";
        try {
            conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            ContactTableList();
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1059", JOptionPane.ERROR_MESSAGE);
        }

    }

    public static void getAwards() {
        String sql = "SELECT `s_achievement_title` as Information,`s_achievement_id` as Action FROM `student_achievements` where `s_information_id`='" + SelectedStudent.sid + "'";
        try {
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            jTable2.setModel(DbUtils.resultSetToTableModel(rs));
            AwardsTableList();
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1059", JOptionPane.ERROR_MESSAGE);
        }

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
        conn.close();
         }catch(Exception e){
         e.printStackTrace();
     }
    }
	
	
    
    
            public static void printCompanyList(){  
          
               
         String sql = "SELECT sl.s_activity_date_time as `Date & Time`,  \n" +
"sl.s_activity_title as Activity\n" +
"FROM `student_information` si\n" +
"inner join student_records sr\n" +
"ON si.s_student_no=sr.s_information_id\n" +
"inner join student_activities sl\n" +
"ON sr.s_records_id=sl.s_records_id\n" +
"inner join student_levels sls\n" +
"on sr.s_year_level_id=sls.s_level_id\n" +
"inner join student_courses sc \n" +
"on sr.s_course_id=sc.s_course_id\n" +
"inner join  student_departments sd\n" +
"on sr.department_id=sd.department_id\n" +
"where sr.s_year_level_id=(select s_level_id FROM student_levels where s_level_title='"+ssLevel+"')\n" +
"and  si.`s_student_no`='"+SelectedStudent.sid+"' \n" +
"order by sl.s_activity_date_time DESC "; 
      //   System.out.println("QUERY "+sql);
           try{
                conn = SQLCon.ConnectDB();
                String recordDate = "";
                String contactNum = "";
            try{
                  int row = jTable4.getSelectedRow();
                 String compReq = jTable4.getModel().getValueAt(row, 0).toString(); 
                 byte[] fileBytes;
        String query = "SELECT s_picture,sr.dateadded,sc.s_contact_info FROM student_information si inner join student_contact sc ON si.s_student_no=sc.s_information_id inner join student_records sr on si.s_student_no=sr.s_information_id where sr.s_year_level_id=(select s_level_id from student_levels where s_level_title='"+compReq+"') and si.s_student_no='"+SelectedStudent.sid+"' LIMIt 1"; 
              pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            if (rs.next()) {
                String dir = "";
                String pdf = "user.jpg";  
                fileBytes = rs.getBytes(1);
                recordDate = rs.getString(2);
                contactNum = rs.getString(3);
                OutputStream targetFile = new FileOutputStream(dir+pdf);
                targetFile.write(fileBytes);
                targetFile.close();   
                }  
                }catch(Exception e){
                    //DC();
                    e.printStackTrace();
                } 
                Map <String,Object> parameters  = new HashMap<String,Object>(); 
                parameters.put("StudentNo", SelectedStudent.sid);
                parameters.put("Name", SelectedStudent.fname+ " "+SelectedStudent.mname+" "+SelectedStudent.lname);
                parameters.put("Gender", SelectedStudent.sgender);
                parameters.put("Contact", contactNum);
                parameters.put("Department", SelectedStudent.sdept);  
                parameters.put("Course", SelectedStudent.scourse);
                parameters.put("Year", ssLevel);   
                parameters.put("Company", selectedCompany);
                parameters.put("Branch", selectedBranch);  
                parameters.put("Supervisor", sSupervisor);  
                parameters.put("Allowance", sAllowance); 
                parameters.put("CDepartment", scDepartment); 
                parameters.put("SupervisorContact", sContactNum); 
                parameters.put("Hours", sHour);  
                parameters.put("Date", recordDate);   
                BufferedImage image = ImageIO.read(new FileInputStream("user.jpg"));
                parameters.put("Image",  image);
           JasperDesign jd = JRXmlLoader.load("src\\reports\\studentrecord.jrxml");
             JRDesignQuery newQuery = new JRDesignQuery();
                    newQuery.setText(sql);
         jd.setQuery(newQuery);
          JasperReport jr = JasperCompileManager.compileReport(jd);
          JasperPrint jp = JasperFillManager.fillReport(jr, parameters, conn);
          JasperViewer jv = new JasperViewer(jp,false);
          jv.setTitle("Career Development and Placement Office");
          jv.setIconImage(Toolkit.getDefaultToolkit().getImage(pnlCompanyIntern.class.getResource("/images/logo jmo_1.png")));
          jv.setVisible(true); 
          jv.setExtendedState(MAXIMIZED_BOTH);
          conn.close();
        }catch (Exception e) {
          //  DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1059", JOptionPane.ERROR_MESSAGE);
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
 
    public void reqListTable() {
        UIManagers.applyOldUI();
        //  getCompanyReqList();
        OjtTableList();
        jTable5.getColumn("PDF Document").setCellRenderer(new ButtonCellRenderer1());
        jTable5.getColumn("PDF Document").setCellEditor(new ButtonCellEditor1());
    }

    public void getCompanyReqList() {
        //String sql = "select c_requirement_title,file_handler from company_requirements where status=1 order by c_requirement_id desc";
        String sql = "select ojt_requirement_title,file_handler from ojt_requirements where status=1 order by ojt_requirement_title asc";
        try {
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
           // if(!cboLevel.getSelectedItem().toString().equals("EMPLOYED") ){
                jTable5.setModel(DbUtils.resultSetToTableModel(rs));
          //  }
          //  conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1017", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void FindCompany() {
        String str = txtCompInfo.getText().replace("'", "''");
        String sql = "SELECT a.c_name,b.c_branch FROM company_information a\n"
                + "inner JOIN company_branch b \n"
                + "on a.c_information_id=b.c_information_id\n"
                + "where a.c_name LIKE '%" + str + "%' LIMIT 10";
        try {
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            jTable3.setModel(DbUtils.resultSetToTableModel(rs));
            CompanyTableList();
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1075", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
         JMenuItem menu = (JMenuItem) event.getSource();
		if (menu == menuItemAdd) {
			 
		} else if(menu == menuItemCopy){
                         int row = jTable1.getSelectedRow();
        String contact = jTable1.getModel().getValueAt(row, 1).toString();
        StringSelection stringSelection = new StringSelection(contact);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
                }else if (menu == menuItemRemove) {
			 
		} else if (menu == menuItemRemoveAll) {
		 
		}
         }

    public class ForcedListSelectionModel extends DefaultListSelectionModel {

        public ForcedListSelectionModel() {
            setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }

        @Override
        public void clearSelection() {
        }

        @Override
        public void removeSelectionInterval(int index0, int index1) {
        }

    }

    public void getDepartment() {
        String sql = "select department_title from student_departments where status=1 ORDER by department_title ASC";
        try {
            // cboDept.removeAllItems();
            //  cboDept.addItem("Select Department");
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                cboDept.addItem(rs.getString(1));
            }
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1073", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void getCourse() {
        String dept = cboDept.getSelectedItem().toString();
        String sql = "select s_course_title from student_courses \n"
                + "where department_id=(SELECT department_id FROM student_departments WHERE department_title='" + dept + "' ) \n"
                + "AND status=1 \n"
                + "ORDER by s_course_title ASC";
        try {
             conn = SQLCon.ConnectDB();
            cboCourse.removeAllItems();
            cboCourse.addItem("Select Course");
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                cboCourse.addItem(rs.getString(1));
            }
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace(); 
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1074", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void getLevels(){
        String sql = "select s_level_title from student_levels where status=1 ORDER by s_level_title ASC";
        try {
             conn = SQLCon.ConnectDB();
            cboLevel.removeAllItems();
            cboLevel.addItem("Select Level");
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                cboLevel.addItem(rs.getString(1));
            }
            conn.close();
        }catch(Exception e){
            DC();
            e.printStackTrace();
             }
    }

    public void getHours() {
        String sql = "select ojt_hours_title from ojt_hours where status=1 ORDER by ojt_hours_title ASC";
        try {
             conn = SQLCon.ConnectDB();
            cboHours.removeAllItems();
            cboHours.addItem("Select Hours");
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                cboHours.addItem(rs.getString(1));
            }
           // conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1076", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void ContactFilter() {
        jTextField1.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (jComboBox2.getSelectedItem().equals("Number")) {
                    char c = e.getKeyChar();
                    if (!((c >= '0') && (c <= '9')
                            || (c == KeyEvent.VK_BACK_SPACE)
                            || (c == KeyEvent.VK_DELETE))) {
                        getToolkit().beep();
                        e.consume();
                    }
                }
            }
        });
    }

    public void AddContact() {
        //ADD Contact Start
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!jTextField1.getText().equals("Contact Information") && !jTextField1.getText().equals("")) {
                   jTextField1.setText(jTextField1.getText().trim().replaceAll(" ", ""));
                    String contactType = jComboBox2.getSelectedItem().toString();
                    String sql = "INSERT INTO `student_contact`( `s_information_id`, `s_contact_type_id`, `s_contact_info`) VALUES (?,(SELECT s_contact_type_id FROM student_contact_type WHERE s_contact_type_title='" + contactType + "'),?)";
                    
                     if(jComboBox2.getSelectedItem().equals("Email")){
        if(emailValidator(jTextField1.getText())){
             
                    try {
                         conn = SQLCon.ConnectDB();
                        pst = conn.prepareStatement(sql);
                        pst.setString(1, SelectedStudent.sid);
                        pst.setString(2, jTextField1.getText());
                        pst.executeUpdate();
                        conn.close();
                    } catch (SQLException ex) {
                        DC();
                        ex.printStackTrace();
                        //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1079", JOptionPane.ERROR_MESSAGE);
                    } 
                    model.addRow(new Object[]{
                        jComboBox2.getSelectedItem(), jTextField1.getText()
                    });
                    jTextField1.setText("Contact Information");
        }else{
            UIManagers.getNewUI();
           JOptionPane.showMessageDialog(null, "Invalid Email Address");
            UIManagers.applyOldUI();
        } 
                }else{
                    try {
                         conn = SQLCon.ConnectDB();
                        pst = conn.prepareStatement(sql);
                        pst.setString(1, SelectedStudent.sid);
                        pst.setString(2, jTextField1.getText());
                        pst.executeUpdate();
                        conn.close();
                    } catch (SQLException ex) {
                        DC();
                        ex.printStackTrace();
                        //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1079", JOptionPane.ERROR_MESSAGE);
                    } 
                    model.addRow(new Object[]{
                        jComboBox2.getSelectedItem(), jTextField1.getText()
                    });
                    jTextField1.setText("Contact Information");
                            }
                } else {
                    UIManagers.getNewUI();
                    JOptionPane.showMessageDialog(null, "Enter Contact Information");
                    jTextField1.requestFocus();
                    UIManagers.applyOldUI();
                }

            }
        });
        //ADD Contact Finish
    }

         
         public boolean emailValidator(String email){
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
         
    public void AddAward() {
        //ADD Award Start
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!jTextField2.getText().equals("Achievements and awards recieved") && !jTextField2.getText().equals("")) {
                    String sql = "INSERT INTO `student_achievements`( `s_information_id`, `s_achievement_title`) VALUES (?,?)";
                    try {
                         conn = SQLCon.ConnectDB();
                        pst = conn.prepareStatement(sql);
                        pst.setString(1, SelectedStudent.sid);
                        pst.setString(2, jTextField2.getText());
                        pst.executeUpdate();
                        
                        conn.close();
                    } catch (SQLException ex) {
                        DC();
                        ex.printStackTrace();
                        //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1080", JOptionPane.ERROR_MESSAGE);
                    }
                    model.addRow(new Object[]{
                        jTextField2.getText()
                    });
                    jTextField2.setText("Achievements and awards recieved");

                } else {
                    UIManagers.getNewUI();
                    JOptionPane.showMessageDialog(null, "Enter achievements and awards recieved");
                    jTextField2.requestFocus();
                    UIManagers.applyOldUI();
                }

            }
        });
        //ADD Award Finish
    }

    public static void ContactTableList() {
        JTableHeader Theader = jTable1.getTableHeader();
        Theader.setBackground(new Color(84, 127, 206)); // change the Background color
        Theader.setForeground(Color.WHITE); // change the Foreground
        Theader.setFont(new Font("Segoe UI", Font.BOLD, 14)); // font name style size
        ((DefaultTableCellRenderer) Theader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER); // center header text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(30);
        jTable1.setDefaultEditor(Object.class, null);
    }

    public static void AwardsTableList() {
        JTableHeader Theader = jTable2.getTableHeader();
        Theader.setBackground(new Color(84, 127, 206)); // change the Background color
        Theader.setForeground(Color.WHITE); // change the Foreground
        Theader.setFont(new Font("Segoe UI", Font.BOLD, 14)); // font name style size
        ((DefaultTableCellRenderer) Theader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER); // center header text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        jTable2.getColumnModel().getColumn(0).setPreferredWidth(190);
        jTable2.getColumnModel().getColumn(1).setPreferredWidth(10);
        jTable2.setDefaultEditor(Object.class, null);
    }

    public void RecordsTableList() {
        JTableHeader Theader = jTable4.getTableHeader();
        Theader.setBackground(new Color(84, 127, 206)); // change the Background color
        Theader.setForeground(Color.WHITE); // change the Foreground
        Theader.setFont(new Font("Segoe UI", Font.BOLD, 14)); // font name style size
        ((DefaultTableCellRenderer) Theader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER); // center header text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        jTable4.setDefaultRenderer(String.class, centerRenderer);
        jTable4.getColumnModel().getColumn(0).setPreferredWidth(70);
        jTable4.getColumnModel().getColumn(1).setPreferredWidth(10);
        jTable4.getColumnModel().getColumn(2).setPreferredWidth(10);
        TableColumnModel tcm = Theader.getColumnModel();
        TableColumn tc0 = tcm.getColumn(0);
        TableColumn tc1 = tcm.getColumn(1);
        TableColumn tc2 = tcm.getColumn(2);
        tc0.setHeaderValue("Records");
        tc1.setHeaderValue("Hours");
        tc2.setHeaderValue("Complete");
        jTable4.setDefaultEditor(Object.class, null);
    }

    public void OjtTableList() {
        JTableHeader Theader = jTable5.getTableHeader();
        Theader.setBackground(new Color(84, 127, 206)); // change the Background color
        Theader.setForeground(Color.WHITE); // change the Foreground
        Theader.setFont(new Font("Segoe UI", Font.BOLD, 14)); // font name style size
        ((DefaultTableCellRenderer) Theader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER); // center header text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        jTable5.getColumnModel().getColumn(0).setPreferredWidth(130);
        jTable5.getColumnModel().getColumn(1).setPreferredWidth(30);
        TableColumnModel tcm = Theader.getColumnModel();
        TableColumn tc0 = tcm.getColumn(0);
        TableColumn tc1 = tcm.getColumn(1);
        tc0.setHeaderValue("Requirements");
        tc1.setHeaderValue("PDF Document");
        jTable5.setDefaultEditor(Object.class, null);

    }

    public void CompanyTableList() {
        JTableHeader Theader = jTable3.getTableHeader();
        Theader.setBackground(new Color(84, 127, 206)); // change the Background color
        Theader.setForeground(Color.WHITE); // change the Foreground
        Theader.setFont(new Font("Segoe UI", Font.BOLD, 14)); // font name style size
        ((DefaultTableCellRenderer) Theader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER); // center header text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        jTable3.getColumnModel().getColumn(0).setPreferredWidth(190);
        jTable3.getColumnModel().getColumn(1).setPreferredWidth(20);
        TableColumnModel tcm = Theader.getColumnModel();
        TableColumn tc0 = tcm.getColumn(0);
        TableColumn tc1 = tcm.getColumn(1);
        tc0.setHeaderValue("Company");
        tc1.setHeaderValue("Branch");
        jTable3.setDefaultEditor(Object.class, null);

    }

    public static class ButtonCellRenderer extends JButton implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value != null) {
                setText("Remove");
            } else {
                setText("Remove");
            }
            if (isSelected) {
                setForeground(Color.WHITE);
                setBackground(new Color(240, 173, 78));
            } else {
                setForeground(new Color(102, 102, 102));
                setBackground(new Color(240, 240, 240));
            }
            return this;
        }
    }

    public static class ButtonCellEditor extends AbstractCellEditor implements TableCellEditor {

        private JButton editor;
        private Object value;
        private int row;
        private JTable table;

        public ButtonCellEditor() {
            editor = new JButton();
            editor.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (table != null) {
                        fireEditingStopped();
                        TableModel model = table.getModel();
                        if (model instanceof DefaultTableModel) {
                            String sql = "DELETE FROM `student_contact` WHERE `s_information_id`=? and s_contact_info=? LIMIT 1";
                            try {
                                
                                 conn = SQLCon.ConnectDB();
                                pst = conn.prepareStatement(sql);
                                pst.setString(1, SelectedStudent.sid);
                                pst.setString(2, model.getValueAt(row, 1).toString());
                                pst.executeUpdate();
                                ((DefaultTableModel) model).removeRow(row);
                                conn.close();
                            } catch (SQLException ex) {
                                DC();
                                ex.printStackTrace();
                                //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1079", JOptionPane.ERROR_MESSAGE);
                            }
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
                editor.setText("Remove");
            } else {
                editor.setText("Remove");
            }
            if (isSelected) {
                editor.setForeground(Color.WHITE);
                editor.setBackground(new Color(240, 173, 78));
            } else {
                editor.setForeground(new Color(102, 102, 102));
                editor.setBackground(new Color(240, 240, 240));
            }
            return editor;
        }
    }

    public void GenderBox() {
        jComboBox1.setBackground(Color.WHITE);
        jComboBox1.requestFocus(false);
        for (int i = 0; i < jComboBox1.getComponentCount(); i++) {
            if (jComboBox1.getComponent(i) instanceof JComponent) {
                ((JComponent) jComboBox1.getComponent(i)).setBorder(new EmptyBorder(0, 0, 0, 0));
            }

            if (jComboBox1.getComponent(i) instanceof AbstractButton) {
                ((AbstractButton) jComboBox1.getComponent(i)).setBorderPainted(false);
            }
        }
    }

    //////////////////AWARD START
    public static class ButtonCellRenderer2 extends JButton implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value != null) {
                setText("Remove");
            } else {
                setText("Remove");
            }
            if (isSelected) {
                setForeground(Color.WHITE);
                setBackground(new Color(240, 173, 78));
            } else {
                setForeground(new Color(102, 102, 102));
                setBackground(new Color(240, 240, 240));
            }
            return this;
        }
    }

    public static class ButtonCellEditor2 extends AbstractCellEditor implements TableCellEditor {

        private JButton editor;
        private Object value;
        private int row;
        private JTable table;

        public ButtonCellEditor2() {
            editor = new JButton();
            editor.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (table != null) {
                        fireEditingStopped();
                        TableModel model = table.getModel();
                        if (model instanceof DefaultTableModel) {
                            String sql = "DELETE FROM `student_achievements` WHERE `s_information_id`=? and s_achievement_title=? LIMIT 1";
                            try {
                                 conn = SQLCon.ConnectDB();
                                pst = conn.prepareStatement(sql);
                                pst.setString(1, SelectedStudent.sid);
                                pst.setString(2, model.getValueAt(row, 0).toString());
                                pst.executeUpdate();
                                ((DefaultTableModel) model).removeRow(row);
                                conn.close();
                            } catch (SQLException ex) {
                                DC();
                                ex.printStackTrace();
                                //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1079", JOptionPane.ERROR_MESSAGE);
                            }
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
                editor.setText("Remove");
            } else {
                editor.setText("Remove");
            }
            if (isSelected) {
                editor.setForeground(Color.WHITE);
                editor.setBackground(new Color(240, 173, 78));
            } else {
                editor.setForeground(new Color(102, 102, 102));
                editor.setBackground(new Color(240, 240, 240));
            }
            return editor;
        }
    }

    ///////////////////AWARD STOP
    public void DeptBox() {
        cboDept.setBackground(Color.WHITE);
        cboDept.requestFocus(false);
        for (int i = 0; i < cboDept.getComponentCount(); i++) {
            if (cboDept.getComponent(i) instanceof JComponent) {
                ((JComponent) cboDept.getComponent(i)).setBorder(new EmptyBorder(0, 0, 0, 0));
            }

            if (cboDept.getComponent(i) instanceof AbstractButton) {
                ((AbstractButton) cboDept.getComponent(i)).setBorderPainted(false);
            }
        }
    }

    public void CourseBox() {
        cboCourse.setBackground(Color.WHITE);
        cboCourse.requestFocus(false);
        for (int i = 0; i < cboCourse.getComponentCount(); i++) {
            if (cboCourse.getComponent(i) instanceof JComponent) {
                ((JComponent) cboCourse.getComponent(i)).setBorder(new EmptyBorder(0, 0, 0, 0));
            }

            if (cboCourse.getComponent(i) instanceof AbstractButton) {
                ((AbstractButton) cboCourse.getComponent(i)).setBorderPainted(false);
            }
        }
    }

    public void YearBox() {
        cboLevel.setBackground(Color.WHITE);
        cboLevel.requestFocus(false);
        for (int i = 0; i < cboLevel.getComponentCount(); i++) {
            if (cboLevel.getComponent(i) instanceof JComponent) {
                ((JComponent) cboLevel.getComponent(i)).setBorder(new EmptyBorder(0, 0, 0, 0));
            }

            if (cboLevel.getComponent(i) instanceof AbstractButton) {
                ((AbstractButton) cboLevel.getComponent(i)).setBorderPainted(false);
            }
        }
    }

    public void OjtHours() {
        cboHours.setBackground(Color.WHITE);
        cboHours.requestFocus(false);
        for (int i = 0; i < cboHours.getComponentCount(); i++) {
            if (cboHours.getComponent(i) instanceof JComponent) {
                ((JComponent) cboHours.getComponent(i)).setBorder(new EmptyBorder(0, 0, 0, 0));
            }

            if (cboHours.getComponent(i) instanceof AbstractButton) {
                ((AbstractButton) cboHours.getComponent(i)).setBorderPainted(false);
            }
        }
    }

    public static class ButtonCellRenderer1 extends JButton implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value != null) {
                  if(addNewRecord){ 
                setText("Remove");
                  }else{ 
                setText("Update");
                  }
            } else {
                setText("Add");
            }
            if (isSelected) {
                setForeground(Color.WHITE);
                setBackground(new Color(240, 173, 78));
            } else {
                setForeground(new Color(102, 102, 102));
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
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    if (hm.containsKey(row)){ 
                                                 if(addNewRecord){
                                                      jTable5.setValueAt(null, row, 1); 
                      hm.remove(row);
                                                 }else{
                                        JFileChooser fileChooser = new JFileChooser();
                                        fileChooser.setCurrentDirectory(new File(currentDir));
                                        fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Document", "pdf"));
                                        //fileChooser.showOpenDialog(null);
                                        int result = fileChooser.showOpenDialog(null);
                                        if (result == JFileChooser.APPROVE_OPTION) {
                                            File selectedFile = fileChooser.getSelectedFile();
                                            String path = selectedFile.getAbsolutePath();
                                            currentDir = path;
                                            hm.put(row, path);
                                            jTable5.setValueAt(Boolean.valueOf(true), row, 1);
                                   
                                            Runnable r = new Runnable() {
                                                public void run() {
                                                   // con = SQLCon.ConnectDB();
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
                                    }else{
                                        JFileChooser fileChooser = new JFileChooser();
                                        fileChooser.setCurrentDirectory(new File(currentDir));
                                        fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Document", "pdf"));
                                        //fileChooser.showOpenDialog(null);
                                        int result = fileChooser.showOpenDialog(null);
                                        if (result == JFileChooser.APPROVE_OPTION) {
                                            File selectedFile = fileChooser.getSelectedFile();
                                            String path = selectedFile.getAbsolutePath();
                                            currentDir = path;
                                            hm.put(row, path);
                                            jTable5.setValueAt(Boolean.valueOf(true), row, 1);
    if(!addNewRecord){
                                            Runnable r = new Runnable() {
                                                public void run() {
                                                 //   con = SQLCon.ConnectDB();

                                                    jTable5.setEnabled(false);
                                                    CheckFileExist();
                                                }
                                            };
                                            new Thread(r).start();
                                            jTable5.setEnabled(false);
    }
                                            //0. check if file exist  if exist goto 1 if not goto 2
                                            //1. insert into archive file
                                            //2. delete from file
                                            //3. insert file
                                        }
                                    }

                                    try {
                                        UIManager.setLookAndFeel(old);
                                    } catch (Exception e) {
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
                  if(addNewRecord){  
                editor.setText("Remove");
                  }else{  
                editor.setText("Update");
                  }
            } else {
                editor.setText("Add");
            }
            if (isSelected) {
                editor.setForeground(Color.WHITE);
                editor.setBackground(new Color(240, 173, 78));
            } else {
                editor.setForeground(new Color(102, 102, 102));
                editor.setBackground(new Color(240, 240, 240));
            }
            return editor;
        }
    }

    public static void CheckFileExist() {
        int row = jTable5.getSelectedRow();
        String req = jTable5.getModel().getValueAt(row, 0).toString();
        String sql = "SELECT file_name FROM `ojt_requirement_file` \n"
                + "WHERE ojt_information_id='" + selectedOjtRecord + "'\n"
                + "AND ojt_requirement_id=(SELECT ojt_requirement_id from ojt_requirements WHERE ojt_requirement_title='" + req + "')";
        try {
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                String isFile = rs.getString(1);
                System.out.println(isFile);
                if (isFile != null) {
                    //JOptionPane.showMessageDialog(null, "File Detected Moving to Archived...");
                    ArchiveFile();
                } else if (isFile == null) {
                    // JOptionPane.showMessageDialog(null, "File Not Detected Goto Delete Record...");
                    DeleteFile();
                }
            }
conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1061", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void ArchiveFile() {
        int row = jTable5.getSelectedRow();
        String req = jTable5.getModel().getValueAt(row, 0).toString();
        String sql = "INSERT INTO `ojt_archive_file`(`ojt_company_req_id`, `ojt_requirement_id`, `ojt_information_id`, `pdf_document`, `file_name`) \n"
                + "SELECT  `ojt_company_req_id`,`ojt_requirement_id`,`ojt_information_id`,`pdf_document`,`file_name`\n"
                + "FROM ojt_requirement_file\n"
                + "WHERE ojt_information_id='" + selectedOjtRecord + "'\n"
                + "AND ojt_requirement_id=(SELECT ojt_requirement_id from ojt_requirements WHERE ojt_requirement_title='" + req + "')";
        try {
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            DeleteFile();
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1062", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void DeleteFile() {
        int row = jTable5.getSelectedRow();
        String req = jTable5.getModel().getValueAt(row, 0).toString();
        String sql = "DELETE FROM ojt_requirement_file WHERE ojt_information_id='" + selectedOjtRecord + "'\n"
                + "AND ojt_requirement_id=(SELECT ojt_requirement_id from ojt_requirements WHERE ojt_requirement_title='" + req + "')";
        try {
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            insertCompanyReq1();
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1063", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void insertCompanyReq1() {
        int row = jTable5.getSelectedRow();
        String req = jTable5.getModel().getValueAt(row, 0).toString();
        String sql = "INSERT INTO `ojt_requirement_file`(`ojt_requirement_id`, `ojt_information_id`, `pdf_document`) VALUES ((SELECT ojt_requirement_id from ojt_requirements where ojt_requirement_title = '" + req + "'),'" + selectedOjtRecord + "',?)";
        try {
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            if (hm.containsKey(row)) {
                File pdfFile = new File(hm.get(row));
                byte[] pdfData = new byte[(int) pdfFile.length()];
                DataInputStream dis = new DataInputStream(new FileInputStream(pdfFile));
                dis.readFully(pdfData);
                dis.close();
                pst.setBytes(1, pdfData);
            } else {
                pst.setString(1, null);
            }
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            rs.next();
            long pk = rs.getLong(1);
            String returnFileID = String.valueOf(pk);
            if (hm.containsKey(row)) {
                AddFileName(returnFileID);
            }
            conn.close();
        } catch (Exception e) {
            
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1055", JOptionPane.ERROR_MESSAGE);
        }
        insertAuditFile();
    }

    public static void insertAuditFile() {
        int row = jTable5.getSelectedRow();
        String req = jTable5.getModel().getValueAt(row, 0).toString();
        String sql = "insert into audit (u_information_id,audit_activity,audit_description) values (?,?,?)";
        try {
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, SystemInfo.userid);
            pst.setString(2, AuditMessage.updateAddFileIntern);
            pst.setString(3, SelectedStudent.sid + " " + req);
            pst.executeUpdate();
            hm.clear();
            UIManagers.getNewUI();
            jTable5.setEnabled(true);
            JOptionPane.showMessageDialog(null, "Student requirement successfully updated...");
            UIManagers.applyOldUI();
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1065", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void AddFileName(String str) {
        String sql = "UPDATE ojt_requirement_file SET file_name=? where ojt_company_req_id='" + str + "'";
        try {
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, "cdpo-i-" + str);
            pst.executeUpdate();
            conn.close();
        } catch (Exception e) {
        DC();
        e.printStackTrace();
        }
    }

    public void checkDuplicate() {
        String studentNo = txtStudentNo.getText().toUpperCase().replace("'", "''");
        String sql = "SELECT s_student_no FROM student_information where s_student_no='" + studentNo + "'";
        try {
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                UIManagers.getNewUI();
                JOptionPane.showMessageDialog(null, "Student is already registered...");
                UIManagers.applyOldUI();
            } else {
                updateStudentInfo();
            }
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1051", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateStudentInfo() {
        int selectedGender;
        if (jComboBox1.getSelectedItem().toString().equalsIgnoreCase("Male")) {
            selectedGender = 0;
        } else {
            selectedGender = 1;
        }
        String slevel = cboLevel.getSelectedItem().toString();
        String sdepartment = cboDept.getSelectedItem().toString();
        String scourse = cboCourse.getSelectedItem().toString();
        String sql = "UPDATE `student_information` SET `s_student_no`=?, `s_firstname`=?, `s_middlename`=?, `s_lastname`=?, `s_suffixname`=?, `s_gender`=?, `s_department_id`=(SELECT `department_id` FROM `student_departments` WHERE  `department_title`='" + sdepartment + "'), `s_course_id`=(SELECT `s_course_id` FROM `student_courses` WHERE `s_course_title`='" + scourse + "'), `s_level_id`=(SELECT `s_level_id` FROM `student_levels` WHERE `s_level_title`='" + slevel + "') WHERE `s_student_no`='" + SelectedStudent.sid + "'";
        try {
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, txtStudentNo.getText().toUpperCase());
            pst.setString(2, txtFname.getText());
            if (txtMname.getText().equalsIgnoreCase("MIDDLENAME")) {
                pst.setString(3, "");
            } else {
                pst.setString(3, txtMname.getText());
            }
            pst.setString(4, txtLname.getText());

            if (txtSuffix.getText().equalsIgnoreCase("SUFFIX")) {
                pst.setString(5, "");
            } else {
                pst.setString(5, txtSuffix.getText());
            }
            pst.setInt(6, selectedGender);
            pst.executeUpdate();
            if (changeAvatar) {
                updateStudentImage();
            } else {
                GetStudentInfo.getPofileInfo(txtStudentNo.getText());
                  if (jTable4.getSelectedRowCount() > 0) {
                updateOjtRecord();
                    f.StudentRecList.TableFromDatabase(SelectedStudent.sid);
  RecordsTableList();
  jTable4.setRowSelectionInterval(selectedRecord, selectedRecord);
  getOjtSchoolInfo();
            }
                insertUpdateAudit();
            }
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1078", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateStudentImage() {
        String sql = "UPDATE `student_information` \n"
                + "SET `s_picture`=? WHERE `s_student_no`='" + SelectedStudent.sid + "'";
        try {
            
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            InputStream is = new FileInputStream(new File("user.png"));
            pst.setBlob(1, is);
            pst.executeUpdate();
            GetStudentInfo.getPofileInfo(txtStudentNo.getText());
            if (jTable4.getSelectedRowCount() > 0) {
                updateOjtRecord();
                 f.StudentRecList.TableFromDatabase(SelectedStudent.sid);
  RecordsTableList();
  jTable4.setRowSelectionInterval(selectedRecord, selectedRecord);
  getOjtSchoolInfo();
            }

            insertUpdateAudit();
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1067", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    
    public void updateOjtRecord(){
          String slevel = cboLevel.getSelectedItem().toString();
        String sdepartment = cboDept.getSelectedItem().toString();
        String scourse = cboCourse.getSelectedItem().toString();
     String sql = " UPDATE `student_records` SET  `department_id`=(SELECT `department_id` FROM `student_departments` WHERE  `department_title`='" + sdepartment + "'), `s_course_id`=(SELECT `s_course_id` FROM `student_courses` WHERE `s_course_title`='" + scourse + "'), `s_year_level_id`=(SELECT `s_level_id` FROM `student_levels` WHERE `s_level_title`='" + slevel + "') WHERE  `s_records_id`='"+selectedStudentRecord+"'";
     try{
          conn = SQLCon.ConnectDB();
         pst = conn.prepareStatement(sql);
         pst.executeUpdate();
         updateOjtRecComp();
         conn.close();
     }catch(Exception e){
         DC();
         e.printStackTrace();
     }
    }
    
    public void updateOjtRecComp(){
          int row = jTable3.getSelectedRow(); 
        String company = jTable3.getModel().getValueAt(row, 0).toString().replace("'", "\\'");
         String branch = jTable3.getModel().getValueAt(row, 1).toString().replace("'", "\\'");
         String hours = cboHours.getSelectedItem().toString();
           
               String sql = "UPDATE `ojt_information` SET `c_information_id`=(SELECT ci.c_information_id FROM company_information ci INNER JOIN company_branch cb ON ci.c_information_id=cb.c_information_id WHERE ci.c_name='"+company+"' AND cb.c_branch='"+branch+"'), `ojt_department`=?, `ojt_supervisor`=?, `ojt_contact_number`=?, `ojt_allowance`=?, `ojt_hours_id`=(SELECT ojt_hours_id FROM ojt_hours WHERE ojt_hours_title='"+hours+"') WHERE ojt_information_id='"+selectedOjtRecord+"'";
               try{
                    conn = SQLCon.ConnectDB();
                   pst = conn.prepareStatement(sql);
                   pst.setString(1, txtDept.getText());
                   pst.setString(2, txtSupervisor.getText());
                   pst.setString(3, txtCompNo.getText());
                   pst.setString(4, txtAllowance.getText());
                   pst.executeUpdate();
                     if(cboLevel.getSelectedItem().equals("EMPLOYED")){
                         updateOjtCompleteStatus1();
                     }
                     conn.close();
               }catch(Exception e){
                   DC();
                        e.printStackTrace();
                        //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e,"Error 1081",JOptionPane.ERROR_MESSAGE);
                }
    }
        

    public void insertUpdateAudit() {
        String sql = "insert into audit (u_information_id,audit_activity,audit_description) values (?,?,?)";
        try {
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, SystemInfo.userid);
            pst.setString(2, AuditMessage.updateStudent);
            pst.setString(3, txtStudentNo.getText().toUpperCase() + " " + txtFname.getText() + " " + txtLname.getText());
            pst.executeUpdate();
            UIManagers.getNewUI();
            JOptionPane.showMessageDialog(null, "Student information successfully updated...");
            UIManagers.applyOldUI();
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1056", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void insertStudentInfo() {
        int selectedGender;
        if (jComboBox1.getSelectedItem().toString().equalsIgnoreCase("Male")) {
            selectedGender = 0;
        } else {
            selectedGender = 1;
        }

        String slevel = cboLevel.getSelectedItem().toString();
        String sdepartment = cboDept.getSelectedItem().toString();
        String scourse = cboCourse.getSelectedItem().toString();
        String hours = cboHours.getSelectedItem().toString();
        String sql = "INSERT INTO `student_information`(`s_student_no`, `u_information_id`, `s_firstname`, `s_middlename`, `s_lastname`, `s_suffixname`, `s_gender`, `s_picture`, `s_dateadded`, `s_department_id`, `s_course_id`, `ojt_hours_id`, `s_level_id`,`record_date`) VALUES (?,?,?,?,?,?,?,?,now(),(SELECT `department_id` FROM `student_departments` WHERE  `department_title`='" + sdepartment + "'),(SELECT `s_course_id` FROM `student_courses` WHERE `s_course_title`='" + scourse + "'),(SELECT ojt_hours_id FROM ojt_hours WHERE ojt_hours_title='" + hours + "'),(SELECT `s_level_id` FROM `student_levels` WHERE `s_level_title`='" + slevel + "'),now())";
        try {
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, txtStudentNo.getText().toUpperCase());
            pst.setString(2, SystemInfo.userid);
            pst.setString(3, txtFname.getText());
            if (txtMname.getText().equalsIgnoreCase("MIDDLENAME")) {
                pst.setString(4, "");
            } else {
                pst.setString(4, txtMname.getText());
            }
            pst.setString(5, txtLname.getText());

            if (txtSuffix.getText().equalsIgnoreCase("SUFFIX")) {
                pst.setString(6, "");
            } else {
                pst.setString(6, txtSuffix.getText());
            }
            pst.setInt(7, selectedGender);
            if (changeAvatar) {
                InputStream is = new FileInputStream(new File(s));
                pst.setBlob(8, is);
            } else {
                pst.setString(8, null);
            }
            pst.executeUpdate();
            insertStudentContact();
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1078", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void insertStudentContact() {
        int rowCount = jTable1.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String contactType = jTable1.getModel().getValueAt(i, 0).toString();
            String sql = "INSERT INTO `student_contact`( `s_information_id`, `s_contact_type_id`, `s_contact_info`) VALUES (?,(SELECT s_contact_type_id FROM student_contact_type WHERE s_contact_type_title='" + contactType + "'),?)";
            try {
                 conn = SQLCon.ConnectDB();
                pst = conn.prepareStatement(sql);
                pst.setString(1, txtStudentNo.getText().toUpperCase());
                pst.setString(2, jTable1.getModel().getValueAt(i, 1).toString());
                pst.executeUpdate();
                conn.close();
            } catch (Exception e) {
                DC();
                e.printStackTrace();
                //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1079", JOptionPane.ERROR_MESSAGE);
            }
        }
        insertStudentAchievements();
    }

    public void insertStudentAchievements() {
        int rowCount = jTable2.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String sql = "INSERT INTO `student_achievements`( `s_information_id`, `s_achievement_title`) VALUES (?,?)";
            try {
                 conn = SQLCon.ConnectDB();
                pst = conn.prepareStatement(sql);
                pst.setString(1, txtStudentNo.getText().toUpperCase());
                pst.setString(2, jTable2.getModel().getValueAt(i, 0).toString());
                pst.executeUpdate();
                conn.close();
            } catch (Exception e) {
                DC();
                e.printStackTrace();
                //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1080", JOptionPane.ERROR_MESSAGE);
            }
        }
        insertOjtInformation();
    }

    ///////START OF INSERTION
    
    
    
       
 
    
    
     public void checkEmployed() {
       
        String sql = "SELECT `s_information_id` FROM `student_records` WHERE `s_year_level_id`=(SELECT s_level_id FROM `student_levels` WHERE `s_level_title` = 'EMPLOYED' ) and `s_information_id` = '"+SelectedStudent.sid+"'";
        try {
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                UIManagers.getNewUI();
                JOptionPane.showMessageDialog(null, "Student is already employed...");
                UIManagers.applyOldUI();
            }else{
                 insertOjtInformation(); 
                   
                addNewRecord = false;
                jButton6.setText("ADD");
                jButton7.setEnabled(true);
                jButton4.setEnabled(true);
                jButton8.setText("UPDATE");
                jButton8.setBackground(new java.awt.Color(84, 127, 206));
                jTable4.setEnabled(true);
                txtStudentNo.setEditable(true);
                txtFname.setEditable(true);
                txtMname.setEditable(true);
                txtLname.setEditable(true);
                txtSuffix.setEditable(true);
                 DefaultTableModel model = (DefaultTableModel) jTable5.getModel();
            model.setRowCount(0);
             CompanyListFunction(); 
                f.StudentRecList.TableFromDatabase(SelectedStudent.sid);
RecordsTableList(); 
            }
       conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1081", JOptionPane.ERROR_MESSAGE);
        }

    }

    
    
        public void insertOjtInformation() {
        int row = jTable3.getSelectedRow();
        String company = jTable3.getModel().getValueAt(row, 0).toString().replace("'", "\\'");
         String branch = jTable3.getModel().getValueAt(row, 1).toString().replace("'", "\\'");
        String hours = cboHours.getSelectedItem().toString();
        int status;
        if(cboLevel.getSelectedItem().equals("EMPLOYED")){
            status = 1;
        }else{
            status = 0;
        }
        String sql = "INSERT INTO `ojt_information`(`c_information_id`, `ojt_department`, `ojt_supervisor`, `ojt_contact_number`, `ojt_allowance`, `ojt_hours_id`, `ojt_completed_status`) VALUES ((SELECT ci.c_information_id FROM company_information ci INNER JOIN company_branch cb ON ci.c_information_id=cb.c_information_id WHERE ci.c_name='" + company + "' AND cb.c_branch='" + branch + "'),?,?,?,?,(SELECT ojt_hours_id FROM ojt_hours WHERE ojt_hours_title='" + hours + "'),? )";
        try {
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtDept.getText());
            pst.setString(2, txtSupervisor.getText());
            pst.setString(3, txtCompNo.getText());
            pst.setString(4, txtAllowance.getText());
            pst.setInt(5, status);
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            rs.next();
            long pk = rs.getLong(1);
            ojtInformationID = String.valueOf(pk);
            insertOjtReq();
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1081", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void insertOjtReq() {
        String sql = "SELECT `ojt_requirement_title` FROM `ojt_requirements` where status=1 order by `ojt_requirement_title` asc";
        try {
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
            insertOjtReq1();
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1054", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void insertOjtReq1(){
        String[] reqList = new String[list.size()];
        reqList = list.toArray(reqList);
        for (int i = 0; i < reqList.length; i++) {
            String sql = "INSERT INTO `ojt_requirement_file`(`ojt_requirement_id`, `ojt_information_id`, `pdf_document`) VALUES ((SELECT ojt_requirement_id from ojt_requirements where ojt_requirement_title = '" + reqList[i] + "'),'" + ojtInformationID + "',? )";
            try {
                 conn = SQLCon.ConnectDB();
                pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                if (hm.containsKey(i)) {
                    File pdfFile = new File(hm.get(i));
                    byte[] pdfData = new byte[(int) pdfFile.length()];
                    DataInputStream dis = new DataInputStream(new FileInputStream(pdfFile));
                    dis.readFully(pdfData);
                    dis.close();
                    pst.setBytes(1, pdfData);
                } else {
                    pst.setString(1, null);
                }
                pst.executeUpdate();
                rs = pst.getGeneratedKeys();
                rs.next();
                long pk = rs.getLong(1);
                String returnFileID = String.valueOf(pk);
                if (hm.containsKey(i)) {
                    AddFileName1(returnFileID);
                }
                conn.close();
            } catch (Exception e) {
                
                DC();
                e.printStackTrace();
                //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1055", JOptionPane.ERROR_MESSAGE);
            }
        }
        insertStudentRecord();
    }

    public void AddFileName1(String str) {
        String sql = "UPDATE ojt_requirement_file SET file_name=? where ojt_company_req_id='" + str + "'";
        try {
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, "cdpo-i-" + str);
            pst.executeUpdate();
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1058", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void insertStudentRecord() {
        String slevel = cboLevel.getSelectedItem().toString();
        String sdepartment = cboDept.getSelectedItem().toString();
        String scourse = cboCourse.getSelectedItem().toString();
        String sql = "INSERT INTO `student_records`(`s_information_id`, `s_year_level_id`, `department_id`, `s_course_id`, `s_ojt_information_id`, `dateadded`) VALUES (?,(SELECT `s_level_id` FROM `student_levels` WHERE `s_level_title`='" + slevel + "'),(SELECT `department_id` FROM `student_departments` WHERE  `department_title`='" + sdepartment + "'),(SELECT `s_course_id` FROM `student_courses` WHERE `s_course_title`='" + scourse + "'),?,now() )";
        try {
            
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, SelectedStudent.sid);
            pst.setString(2, ojtInformationID);
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            rs.next();
            long pk = rs.getLong(1);
            String studentRecord = String.valueOf(pk);
            AddRecordLog(studentRecord);
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1058", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void AddRecordLog(String str) {
        String sql = "INSERT INTO `student_logs`( `s_records_id`, `s_log_date_time`, `s_log_title`) VALUES (?,now(),? )";
        try {
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, str);
            pst.setString(2, AuditMessage.logRegisterStudent);
            pst.executeUpdate();
            //update dept
            //update couirse
            //update level
            //update record_added instudent info 
            //update ojt hoiurs id
            updateStudentRecordInfo();
conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1058", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateStudentRecordInfo() {
        String slevel = cboLevel.getSelectedItem().toString();
        String sdepartment = cboDept.getSelectedItem().toString();
        String scourse = cboCourse.getSelectedItem().toString();
        String hours = cboHours.getSelectedItem().toString();
        String sql = "UPDATE `student_information` SET  `s_department_id`=(SELECT `department_id` FROM `student_departments` WHERE  `department_title`='" + sdepartment + "'), `s_course_id`=(SELECT `s_course_id` FROM `student_courses` WHERE `s_course_title`='" + scourse + "'), `s_level_id`=(SELECT `s_level_id` FROM `student_levels` WHERE `s_level_title`='" + slevel + "'),`ojt_hours_id`=(SELECT ojt_hours_id FROM ojt_hours WHERE ojt_hours_title='" + hours + "'),`record_date`=now()  WHERE `s_student_no`='" + SelectedStudent.sid + "' ";
        try {
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            GetStudentInfo.getPofileInfo(SelectedStudent.sid);
            insertAudit();
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1078", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void insertAudit() {
        String sql = "insert into audit (u_information_id,audit_activity,audit_description) values (?,?,?)";
        try {
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, SystemInfo.userid);
            pst.setString(2, AuditMessage.addStudent);
            pst.setString(3, SelectedStudent.sid + " " + txtFname.getText() + " " + txtLname.getText());
            pst.executeUpdate();
            f.StudentRecList.TableFromDatabase(SelectedStudent.sid);
            RecordsTableList();
            //lblImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Name_125px.png")));
            //  txtStudentNo.setText("STUDENT NO.");
            //  txtFname.setText("FIRSTNAME");
            // txtMname.setText("MIDDLENAME");
            //   txtLname.setText("LASTNAME");
            //   txtSuffix.setText("SUFFIX"); 
            // jComboBox1.setSelectedIndex(0); 
            //  cboDept.setSelectedIndex(0); 
            //  cboCourse.setSelectedIndex(0); 
            //  cboLevel.setSelectedIndex(0);  
            DefaultTableModel model;
            //       DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            //  model.setRowCount(0); 
            //    model = (DefaultTableModel) jTable2.getModel();
            //  model.setRowCount(0);
            list.clear();
            hm.clear();
            reqListTable();
            txtCompInfo.setText("Search Company and Branch name or ID");
            model = (DefaultTableModel) jTable3.getModel();
            model.setRowCount(0);
            txtDept.setText("DEPARTMENT");
            txtSupervisor.setText("SUPERVISOR");
            txtCompNo.setText("SUPERVISOR CONTACT NUMBER");
            //txtAllowance.setText("ALLOWANCE");
            //cboHours.setSelectedIndex(0);
            //changeAvatar = false;  
            UIManagers.getNewUI();
            JOptionPane.showMessageDialog(null, "Student record successfully added...");
            UIManagers.applyOldUI();
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1056", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateOjtCompleteStatus(String str) {
        String sql = "UPDATE `ojt_information` SET  `ojt_completed_status`='" + str + "' WHERE `ojt_information_id`='" + selectedOjtRecord + "'";
        try {
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            System.out.println("OJT Completion Status Updated");

            getOjtSchoolInfo();
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
        }
    }
 public   void updateOjtCompleteStatus1() {
        String sql = "UPDATE `ojt_information` SET  `ojt_completed_status`=1 WHERE `ojt_information_id`='" + selectedOjtRecord + "'";
        try {
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            System.out.println("OJT Completion Status Updated FROM UPDATE"); 
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
        }
    }
    public void getOjtSchoolInfo() {
        String sql = " SELECT sd.department_title,sc.s_course_title,sl.s_level_title,sd.status,sc.status,sl.status FROM `student_records` sr\n"
                + "INNER join student_departments sd ON\n"
                + "sr.department_id=sd.department_id\n"
                + "INNER JOIN student_courses sc ON\n"
                + "sr.s_course_id=sc.s_course_id\n"
                + "INNER JOIN student_levels sl ON\n"
                + "sr.s_year_level_id=sl.s_level_id\n"
                + "WHERE s_records_id='" + selectedStudentRecord + "' ";
        String dept = "";
        String course = "";
        String level = "";
        String sdept = "";
        String scourse = "";
        String slevel = "";
        try {
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                dept = rs.getString(1);
                course = rs.getString(2);
                level = rs.getString(3);
                ssLevel = level;
               // JOptionPane.showMessageDialog(null, ssLevel);
                sdept = rs.getString(4);
                scourse = rs.getString(5);
                slevel = rs.getString(6);
            }
            if (sdept.equals("1")) {
                cboDept.setSelectedItem(dept);
            } else {
                cboDept.removeItem(dept);
                cboDept.addItem(dept);
                cboDept.setSelectedItem(dept);
                getCourse1();
            }

            if (scourse.equals("1")) {
                cboCourse.setSelectedItem(course);
            } else {
                getCourse1();
                cboCourse.addItem(course);
                cboCourse.setSelectedItem(course);
            }

            if (slevel.equals("1")) {
                cboLevel.setSelectedItem(level);
            } else {
                 getLevels();
              //  cboLevel.removeItem(level);
                cboLevel.addItem(level);
                cboLevel.setSelectedItem(level);
            }
            hm.clear();
            list.clear();
            getOjtRecordFiles();
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
        }
    }

    public void getCourse1() {
        String sql = "select s_course_title from student_courses \n"
                + "where status=1 \n"
                + "ORDER by s_course_title ASC";
        try {
             conn = SQLCon.ConnectDB();
            cboCourse.removeAllItems();
            cboCourse.addItem("Select Course");
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                cboCourse.addItem(rs.getString(1));
            }
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1074", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void getOjtRecordFiles() {
        UIManagers.applyOldUI();
        getOjtReqList();
        OjtTableList();
        jTable5.getColumn("PDF Document").setCellRenderer(new ButtonCellRenderer1());
        jTable5.getColumn("PDF Document").setCellEditor(new ButtonCellEditor1());
    }

    public void getOjtReqList() {
        String sql = "SELECT b.ojt_requirement_title,a.file_name  FROM ojt_requirement_file a\n"
                + "INNER join ojt_requirements b \n"
                + "on a.ojt_requirement_id=b.ojt_requirement_id\n"
                + "INNER JOIN ojt_information c \n"
                + "ON a.ojt_information_id=c.ojt_information_id  \n"
                + "WHERE c.ojt_information_id='" + selectedOjtRecord + "'\n"
                + "AND status=1\n"
                + "ORDER by b.ojt_requirement_title";
        try {
            
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
             String recordLevel = jTable4.getModel().getValueAt(selectedRecord, 0).toString();
            System.out.println(""+recordLevel);
            if(recordLevel.equals("EMPLOYED") && cboLevel.getSelectedItem().equals("EMPLOYED")){
                 DefaultTableModel model = (DefaultTableModel) jTable5.getModel();
            model.setRowCount(0);
            }else{
            jTable5.setModel(DbUtils.resultSetToTableModel(rs)); 
            }
            getOjtRecordCompany();
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1017", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void getOjtRecordCompany() {
        String sql = "SELECT  ci.c_name,cb.c_branch FROM ojt_information oi\n"
                + "INNER JOIN company_information ci\n"
                + "on oi.c_information_id=ci.c_information_id\n"
                + "INNER JOIN company_branch cb\n"
                + "on ci.c_information_id=cb.c_information_id\n"
                + "WHERE ojt_information_id='" + selectedOjtRecord + "'";
        try {
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            jTable3.setModel(DbUtils.resultSetToTableModel(rs));
            CompanyTableList();
            jTable3.setRowSelectionInterval(0, 0); 
                selectedCompany=jTable3.getModel().getValueAt(0, 0).toString();
                selectedBranch=jTable3.getModel().getValueAt(0, 1).toString(); 
            getOjtRecordCompInfo();
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
        }
    }

    public void getOjtRecordCompInfo() {
        String sql = "SELECT   `ojt_department`, `ojt_supervisor`, `ojt_contact_number`, `ojt_allowance`, (SELECT ojt_hours_title from ojt_hours where ojt_hours_id=ojt_information.ojt_hours_id),(SELECT status from ojt_hours where ojt_hours_id=ojt_information.ojt_hours_id)  \n"
                + "FROM `ojt_information` \n"
                + "WHERE `ojt_information_id`='" + selectedOjtRecord + "'";
        try {
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            String hour = "";
            String shour = "";
            while (rs.next()) {
                txtDept.setText(rs.getString(1));
                scDepartment  =  rs.getString(1);
                txtSupervisor.setText(rs.getString(2));
                sSupervisor = rs.getString(2);
                txtCompNo.setText(rs.getString(3));
                sContactNum = rs.getString(3);
                txtAllowance.setText(rs.getString(4));
                sAllowance = rs.getString(4);
                sHour = rs.getString(5);
               hour=rs.getString(5);
               shour=rs.getString(6);
               
            } 
               if (shour.equals("1")){
                cboHours.setSelectedItem(hour);
            }else{
                //cboHours.removeItem(hour);
                getHours();
                cboHours.addItem(hour);
                cboHours.setSelectedItem(hour);
            }
            
conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
        }
    }
    
      public void getHours1() {
        String sql = "select ojt_hours_title from ojt_hours \n"
                + "where status=1 \n"
                + "ORDER by ojt_hours_title ASC";
        try {
             conn = SQLCon.ConnectDB();
            cboHours.removeAllItems();
            cboHours.addItem("Select Hours");
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                cboHours.addItem(rs.getString(1));
            }
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1074", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void getCompanyReqPDF() {
        int row = jTable5.getSelectedRow();
        String req = jTable5.getModel().getValueAt(row, 0).toString();
        byte[] fileBytes;
        String query;
        try {
             conn = SQLCon.ConnectDB();
            query = "SELECT pdf_document,file_name from ojt_requirement_file \n"
                    + "where ojt_information_id='" + selectedOjtRecord + "'\n"
                    + "and pdf_document != ''\n"
                    + "and ojt_requirement_id=(SELECT ojt_requirement_id from ojt_requirements WHERE ojt_requirement_title='" + req + "')";
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(query);
            if (rs.next()) {
                String dir = "intern_files//";
                String pdf = rs.getString(2) + ".pdf";
                fileBytes = rs.getBytes(1);
                OutputStream targetFile = new FileOutputStream(dir + pdf);
                targetFile.write(fileBytes);
                targetFile.close();
                selectedRowDocSProfile = dir + pdf;
                PDFViewer pv = new PDFViewer();
                pv.companyadd = false;
                pv.companyprofile = false;
                pv.studentadd = false;
                pv.studentprofile = true;
                pv.setVisible(true);
                // JOptionPane.showMessageDialog(null, "PDF Downloaded"); 
                System.out.println("PDF File Downloaded: " + pdf);
            }
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
        }
    }
    
    public void filterUpdate(){
         if (txtStudentNo.getText().isEmpty() || txtStudentNo.getText().equals("STUDENT NO.")) {
                JOptionPane.showMessageDialog(null, "Student number is required");
                txtStudentNo.requestFocus();
            } else if (txtFname.getText().isEmpty() || txtFname.getText().equals("FIRSTNAME")) {
                JOptionPane.showMessageDialog(null, "Student first name is required");
                txtFname.requestFocus();
            } else if (txtLname.getText().isEmpty() || txtLname.getText().equals("LASTNAME")) {
                JOptionPane.showMessageDialog(null, "Student last name is required");
                txtLname.requestFocus();
            } else if (jComboBox1.getSelectedItem().equals("Select Gender")) {
                JOptionPane.showMessageDialog(null, "Select gender");
                jComboBox1.requestFocus();
            } else if (cboDept.getSelectedItem().equals("Select Department")) {
                JOptionPane.showMessageDialog(null, "Select Department");
                cboDept.requestFocus();
            } else if (cboCourse.getSelectedItem().equals("Select Course")) {
                JOptionPane.showMessageDialog(null, "Select Course");
                cboCourse.requestFocus();
            } else if (cboLevel.getSelectedItem().equals("Select Level")) {
                JOptionPane.showMessageDialog(null, "Select Level");
                cboLevel.requestFocus();
            } else if (jTable1.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Add student contact information");
                jTextField1.requestFocus();
            } else {
                if (txtStudentNo.getText().equals(SelectedStudent.sid)) {
                    updateStudentInfo();
                } else {
                    checkDuplicate();
                }
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
        lblImage = new javax.swing.JLabel();
        txtStudentNo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        txtFname = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        txtMname = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        txtLname = new javax.swing.JTextField();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jSeparator10 = new javax.swing.JSeparator();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        txtSuffix = new javax.swing.JTextField();
        pnlStudentPersonalInfo1 = new javax.swing.JPanel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        pnlStudentPersonalInfo2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        pnlStudentPersonalInfo4 = new javax.swing.JPanel();
        txtCompInfo = new javax.swing.JTextField();
        txtSupervisor = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        txtAllowance = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        cboHours = new javax.swing.JComboBox<>();
        jSeparator14 = new javax.swing.JSeparator();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jSeparator6 = new javax.swing.JSeparator();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jSeparator15 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        txtDept = new javax.swing.JTextField();
        jSeparator16 = new javax.swing.JSeparator();
        txtCompNo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        jButton4 = new javax.swing.JButton();
        pnlStudentPersonalInfo3 = new javax.swing.JPanel();
        cboDept = new javax.swing.JComboBox<>();
        jSeparator17 = new javax.swing.JSeparator();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        cboCourse = new javax.swing.JComboBox<>();
        jSeparator18 = new javax.swing.JSeparator();
        cboLevel = new javax.swing.JComboBox<>();
        jSeparator19 = new javax.swing.JSeparator();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        pnlStudentPersonalInfo.setBackground(new java.awt.Color(255, 255, 255));
        pnlStudentPersonalInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Personal Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        lblImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Name_125px.png"))); // NOI18N
        lblImage.setToolTipText("Add photo");
        lblImage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblImageMouseClicked(evt);
            }
        });

        txtStudentNo.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtStudentNo.setForeground(new java.awt.Color(102, 102, 102));
        txtStudentNo.setText("STUDENT NO.");
        txtStudentNo.setToolTipText("Student number");
        txtStudentNo.setBorder(null);
        txtStudentNo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtStudentNoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtStudentNoFocusLost(evt);
            }
        });
        txtStudentNo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtStudentNoMouseClicked(evt);
            }
        });
        txtStudentNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtStudentNoKeyReleased(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Hashtag_18px_3.png"))); // NOI18N

        jSeparator3.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator3.setForeground(new java.awt.Color(84, 127, 206));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/contacts_18px.png"))); // NOI18N

        txtFname.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtFname.setForeground(new java.awt.Color(102, 102, 102));
        txtFname.setText("FIRSTNAME");
        txtFname.setToolTipText("Firstname");
        txtFname.setBorder(null);
        txtFname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFnameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFnameFocusLost(evt);
            }
        });
        txtFname.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtFnameMouseClicked(evt);
            }
        });
        txtFname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFnameActionPerformed(evt);
            }
        });
        txtFname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFnameKeyReleased(evt);
            }
        });

        jSeparator7.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator7.setForeground(new java.awt.Color(84, 127, 206));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/contacts_18px.png"))); // NOI18N

        txtMname.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtMname.setForeground(new java.awt.Color(102, 102, 102));
        txtMname.setText("MIDDLENAME");
        txtMname.setToolTipText("Middlename");
        txtMname.setBorder(null);
        txtMname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMnameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMnameFocusLost(evt);
            }
        });
        txtMname.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMnameMouseClicked(evt);
            }
        });
        txtMname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMnameActionPerformed(evt);
            }
        });
        txtMname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMnameKeyReleased(evt);
            }
        });

        jSeparator8.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator8.setForeground(new java.awt.Color(84, 127, 206));

        txtLname.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtLname.setForeground(new java.awt.Color(102, 102, 102));
        txtLname.setText("LASTNAME");
        txtLname.setToolTipText("Lastname");
        txtLname.setBorder(null);
        txtLname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtLnameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLnameFocusLost(evt);
            }
        });
        txtLname.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtLnameMouseClicked(evt);
            }
        });
        txtLname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLnameKeyReleased(evt);
            }
        });

        jSeparator9.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator9.setForeground(new java.awt.Color(84, 127, 206));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/contacts_18px.png"))); // NOI18N

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Gender_18px_1.png"))); // NOI18N

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(102, 102, 102));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Gender", "Male", "Female" }));
        jComboBox1.setToolTipText("Gender");
        jComboBox1.setBorder(null);
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jSeparator10.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator10.setForeground(new java.awt.Color(84, 127, 206));

        jScrollPane4.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jTable4.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jTable4.setForeground(new java.awt.Color(102, 102, 102));
        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Records", "Hours", "Complete"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable4.setGridColor(new java.awt.Color(255, 255, 255));
        jTable4.setRowHeight(25);
        jTable4.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable4MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTable4MouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(jTable4);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Date Registered");

        jSeparator12.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator12.setForeground(new java.awt.Color(84, 127, 206));

        txtSuffix.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtSuffix.setForeground(new java.awt.Color(102, 102, 102));
        txtSuffix.setText("SUFFIX");
        txtSuffix.setToolTipText("Suffix");
        txtSuffix.setBorder(null);
        txtSuffix.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSuffixFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSuffixFocusLost(evt);
            }
        });
        txtSuffix.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSuffixMouseClicked(evt);
            }
        });
        txtSuffix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSuffixActionPerformed(evt);
            }
        });
        txtSuffix.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSuffixKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout pnlStudentPersonalInfoLayout = new javax.swing.GroupLayout(pnlStudentPersonalInfo);
        pnlStudentPersonalInfo.setLayout(pnlStudentPersonalInfoLayout);
        pnlStudentPersonalInfoLayout.setHorizontalGroup(
            pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStudentPersonalInfoLayout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(lblImage)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStudentPersonalInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jSeparator8)
                                .addComponent(txtMname, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)))
                        .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jSeparator7)
                                .addComponent(txtFname)))
                        .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtStudentNo, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                .addComponent(jSeparator3))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator10)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                                .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtLname, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                                    .addComponent(jSeparator9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSuffix)
                                    .addComponent(jSeparator12))))))
                .addGap(40, 40, 40))
        );
        pnlStudentPersonalInfoLayout.setVerticalGroup(
            pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblImage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addComponent(txtStudentNo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addComponent(txtFname, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addComponent(txtMname, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                                .addComponent(txtLname, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                                .addComponent(txtSuffix, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pnlStudentPersonalInfo1.setBackground(new java.awt.Color(255, 255, 255));
        pnlStudentPersonalInfo1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Contact Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N
        pnlStudentPersonalInfo1.setPreferredSize(new java.awt.Dimension(362, 500));

        jComboBox2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jComboBox2.setForeground(new java.awt.Color(102, 102, 102));
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Number", "Email", "Social" }));
        jComboBox2.setBorder(null);
        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(84, 127, 206));
        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(102, 102, 102));
        jTextField1.setText("Contact Information");
        jTextField1.setToolTipText("Contact Information");
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField1FocusLost(evt);
            }
        });
        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField1MouseClicked(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jTable1.setForeground(new java.awt.Color(102, 102, 102));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Contact", "Informaton", "Action"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.setGridColor(new java.awt.Color(255, 255, 255));
        jTable1.setRowHeight(25);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout pnlStudentPersonalInfo1Layout = new javax.swing.GroupLayout(pnlStudentPersonalInfo1);
        pnlStudentPersonalInfo1.setLayout(pnlStudentPersonalInfo1Layout);
        pnlStudentPersonalInfo1Layout.setHorizontalGroup(
            pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfo1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStudentPersonalInfo1Layout.createSequentialGroup()
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlStudentPersonalInfo1Layout.setVerticalGroup(
            pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfo1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pnlStudentPersonalInfo2.setBackground(new java.awt.Color(255, 255, 255));
        pnlStudentPersonalInfo2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Awards Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N
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
        jTextField2.setText("Achievements and awards recieved");
        jTextField2.setToolTipText("Achievements and awards recieved");
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

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jTable2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jTable2.setForeground(new java.awt.Color(102, 102, 102));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Informaton", "Action"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable2.setGridColor(new java.awt.Color(255, 255, 255));
        jTable2.setRowHeight(25);
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout pnlStudentPersonalInfo2Layout = new javax.swing.GroupLayout(pnlStudentPersonalInfo2);
        pnlStudentPersonalInfo2.setLayout(pnlStudentPersonalInfo2Layout);
        pnlStudentPersonalInfo2Layout.setHorizontalGroup(
            pnlStudentPersonalInfo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfo2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlStudentPersonalInfo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStudentPersonalInfo2Layout.createSequentialGroup()
                        .addComponent(jTextField2)
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pnlStudentPersonalInfo4.setBackground(new java.awt.Color(255, 255, 255));
        pnlStudentPersonalInfo4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "OJT Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N
        pnlStudentPersonalInfo4.setPreferredSize(new java.awt.Dimension(362, 500));

        txtCompInfo.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtCompInfo.setForeground(new java.awt.Color(102, 102, 102));
        txtCompInfo.setText("Search Company and Branch name or ID");
        txtCompInfo.setToolTipText("Search Company and Branch name or ID");
        txtCompInfo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCompInfoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCompInfoFocusLost(evt);
            }
        });
        txtCompInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCompInfoMouseClicked(evt);
            }
        });
        txtCompInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCompInfoActionPerformed(evt);
            }
        });
        txtCompInfo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCompInfoKeyReleased(evt);
            }
        });

        txtSupervisor.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtSupervisor.setForeground(new java.awt.Color(102, 102, 102));
        txtSupervisor.setText("SUPERVISOR");
        txtSupervisor.setToolTipText("Supervisor");
        txtSupervisor.setBorder(null);
        txtSupervisor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSupervisorFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSupervisorFocusLost(evt);
            }
        });
        txtSupervisor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSupervisorMouseClicked(evt);
            }
        });
        txtSupervisor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSupervisorActionPerformed(evt);
            }
        });
        txtSupervisor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSupervisorKeyReleased(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Administrator_Male_18px.png"))); // NOI18N

        jSeparator4.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator4.setForeground(new java.awt.Color(84, 127, 206));

        txtAllowance.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtAllowance.setForeground(new java.awt.Color(102, 102, 102));
        txtAllowance.setText("ALLOWANCE");
        txtAllowance.setToolTipText("Allowance");
        txtAllowance.setBorder(null);
        txtAllowance.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtAllowanceFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAllowanceFocusLost(evt);
            }
        });
        txtAllowance.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtAllowanceMouseClicked(evt);
            }
        });
        txtAllowance.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAllowanceKeyReleased(evt);
            }
        });

        jSeparator5.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator5.setForeground(new java.awt.Color(84, 127, 206));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Receive_Cash_18px.png"))); // NOI18N

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Time_Machine_18px.png"))); // NOI18N

        cboHours.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        cboHours.setForeground(new java.awt.Color(102, 102, 102));
        cboHours.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Hours" }));
        cboHours.setToolTipText("Hours");
        cboHours.setBorder(null);
        cboHours.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboHoursItemStateChanged(evt);
            }
        });
        cboHours.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHoursActionPerformed(evt);
            }
        });

        jSeparator14.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator14.setForeground(new java.awt.Color(84, 127, 206));

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

        jScrollPane3.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jTable3.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jTable3.setForeground(new java.awt.Color(102, 102, 102));
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Company", "Branch"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable3.setGridColor(new java.awt.Color(255, 255, 255));
        jTable3.setRowHeight(25);
        jScrollPane3.setViewportView(jTable3);

        jSeparator15.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator15.setForeground(new java.awt.Color(84, 127, 206));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Department_18px.png"))); // NOI18N

        txtDept.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtDept.setForeground(new java.awt.Color(102, 102, 102));
        txtDept.setText("DEPARTMENT");
        txtDept.setToolTipText("Department");
        txtDept.setBorder(null);
        txtDept.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDeptFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDeptFocusLost(evt);
            }
        });
        txtDept.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDeptMouseClicked(evt);
            }
        });
        txtDept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDeptActionPerformed(evt);
            }
        });
        txtDept.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDeptKeyReleased(evt);
            }
        });

        jSeparator16.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator16.setForeground(new java.awt.Color(84, 127, 206));

        txtCompNo.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtCompNo.setForeground(new java.awt.Color(102, 102, 102));
        txtCompNo.setText("SUPERVISOR CONTACT NUMBER");
        txtCompNo.setToolTipText("Supervisor Contact Number");
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
        txtCompNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCompNoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCompNoKeyReleased(evt);
            }
        });

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Phone_18px_1.png"))); // NOI18N

        jSeparator11.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator11.setForeground(new java.awt.Color(84, 127, 206));

        javax.swing.GroupLayout pnlStudentPersonalInfo4Layout = new javax.swing.GroupLayout(pnlStudentPersonalInfo4);
        pnlStudentPersonalInfo4.setLayout(pnlStudentPersonalInfo4Layout);
        pnlStudentPersonalInfo4Layout.setHorizontalGroup(
            pnlStudentPersonalInfo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfo4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlStudentPersonalInfo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator15, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator6)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStudentPersonalInfo4Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlStudentPersonalInfo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSupervisor)
                            .addComponent(jSeparator4)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStudentPersonalInfo4Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlStudentPersonalInfo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtAllowance)
                            .addComponent(jSeparator5)))
                    .addGroup(pnlStudentPersonalInfo4Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlStudentPersonalInfo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboHours, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator14)))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(txtCompInfo, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStudentPersonalInfo4Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlStudentPersonalInfo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDept)
                            .addComponent(jSeparator16)))
                    .addGroup(pnlStudentPersonalInfo4Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlStudentPersonalInfo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCompNo)
                            .addComponent(jSeparator11))))
                .addContainerGap())
        );
        pnlStudentPersonalInfo4Layout.setVerticalGroup(
            pnlStudentPersonalInfo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfo4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCompInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlStudentPersonalInfo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlStudentPersonalInfo4Layout.createSequentialGroup()
                        .addComponent(txtDept, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlStudentPersonalInfo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlStudentPersonalInfo4Layout.createSequentialGroup()
                        .addComponent(txtSupervisor, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlStudentPersonalInfo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlStudentPersonalInfo4Layout.createSequentialGroup()
                        .addComponent(txtCompNo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlStudentPersonalInfo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlStudentPersonalInfo4Layout.createSequentialGroup()
                        .addComponent(txtAllowance, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlStudentPersonalInfo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlStudentPersonalInfo4Layout.createSequentialGroup()
                        .addComponent(cboHours, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jButton4.setBackground(new java.awt.Color(84, 127, 206));
        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("ACTIVITIES");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        pnlStudentPersonalInfo3.setBackground(new java.awt.Color(255, 255, 255));
        pnlStudentPersonalInfo3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "School Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N
        pnlStudentPersonalInfo3.setPreferredSize(new java.awt.Dimension(362, 500));

        cboDept.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        cboDept.setForeground(new java.awt.Color(102, 102, 102));
        cboDept.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Department" }));
        cboDept.setToolTipText("Departments");
        cboDept.setBorder(null);
        cboDept.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboDeptItemStateChanged(evt);
            }
        });
        cboDept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboDeptActionPerformed(evt);
            }
        });

        jSeparator17.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator17.setForeground(new java.awt.Color(84, 127, 206));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Department_18px.png"))); // NOI18N

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Course_18px.png"))); // NOI18N

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Stairs_Up_18px.png"))); // NOI18N

        cboCourse.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        cboCourse.setForeground(new java.awt.Color(102, 102, 102));
        cboCourse.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Course" }));
        cboCourse.setToolTipText("Courses");
        cboCourse.setBorder(null);
        cboCourse.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboCourseItemStateChanged(evt);
            }
        });

        jSeparator18.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator18.setForeground(new java.awt.Color(84, 127, 206));

        cboLevel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        cboLevel.setForeground(new java.awt.Color(102, 102, 102));
        cboLevel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Level" }));
        cboLevel.setToolTipText("Levels");
        cboLevel.setBorder(null);
        cboLevel.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboLevelItemStateChanged(evt);
            }
        });
        cboLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLevelActionPerformed(evt);
            }
        });

        jSeparator19.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator19.setForeground(new java.awt.Color(84, 127, 206));

        javax.swing.GroupLayout pnlStudentPersonalInfo3Layout = new javax.swing.GroupLayout(pnlStudentPersonalInfo3);
        pnlStudentPersonalInfo3.setLayout(pnlStudentPersonalInfo3Layout);
        pnlStudentPersonalInfo3Layout.setHorizontalGroup(
            pnlStudentPersonalInfo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfo3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlStudentPersonalInfo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlStudentPersonalInfo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboDept, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboCourse, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboLevel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator17, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator18, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator19))
                .addContainerGap())
        );
        pnlStudentPersonalInfo3Layout.setVerticalGroup(
            pnlStudentPersonalInfo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfo3Layout.createSequentialGroup()
                .addGroup(pnlStudentPersonalInfo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlStudentPersonalInfo3Layout.createSequentialGroup()
                        .addGroup(pnlStudentPersonalInfo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlStudentPersonalInfo3Layout.createSequentialGroup()
                                .addGroup(pnlStudentPersonalInfo3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(pnlStudentPersonalInfo3Layout.createSequentialGroup()
                                        .addComponent(cboDept, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboCourse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jSeparator18, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator19, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jButton6.setBackground(new java.awt.Color(84, 127, 206));
        jButton6.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("ADD");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(84, 127, 206));
        jButton7.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("LOGS");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(84, 127, 206));
        jButton8.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("UPDATE");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlStudentPersonalInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlStudentPersonalInfo1, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                    .addComponent(pnlStudentPersonalInfo2, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                    .addComponent(pnlStudentPersonalInfo3, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlStudentPersonalInfo4, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlStudentPersonalInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(pnlStudentPersonalInfo3, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlStudentPersonalInfo1, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlStudentPersonalInfo2, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pnlStudentPersonalInfo4, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4)
                            .addComponent(jButton6)
                            .addComponent(jButton7)
                            .addComponent(jButton8))))
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

    private void txtStudentNoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStudentNoFocusGained
        // TODO add your handling code here:
        if (txtStudentNo.getText().equals("STUDENT NO.")) {
            txtStudentNo.setText("");
        }
    }//GEN-LAST:event_txtStudentNoFocusGained

    private void txtStudentNoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStudentNoFocusLost
        if (txtStudentNo.getText().equals("")) {
            txtStudentNo.setText("STUDENT NO.");
        }       // TODO add your handling code here:
    }//GEN-LAST:event_txtStudentNoFocusLost

    private void txtStudentNoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtStudentNoMouseClicked
        if (txtStudentNo.getText().equals("STUDENT NO.")) {
            txtStudentNo.setText("");
        }
    }//GEN-LAST:event_txtStudentNoMouseClicked

    private void txtFnameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFnameFocusGained
        if (txtFname.getText().equals("FIRSTNAME")) {
            txtFname.setText("");
        }
    }//GEN-LAST:event_txtFnameFocusGained

    private void txtFnameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFnameFocusLost
        if (txtFname.getText().equals("")) {
            txtFname.setText("FIRSTNAME");
        }                // T
    }//GEN-LAST:event_txtFnameFocusLost

    private void txtFnameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtFnameMouseClicked
        if (txtFname.getText().equals("FIRSTNAME")) {
            txtFname.setText("");
        }
    }//GEN-LAST:event_txtFnameMouseClicked

    private void txtFnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFnameActionPerformed

    private void txtMnameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMnameFocusGained
        if (txtMname.getText().equals("MIDDLENAME")) {
            txtMname.setText("");
        }
    }//GEN-LAST:event_txtMnameFocusGained

    private void txtMnameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMnameFocusLost
        // TODO add your handling code here:
        if (txtMname.getText().equals("")) {
            txtMname.setText("MIDDLENAME");
        }
    }//GEN-LAST:event_txtMnameFocusLost

    private void txtMnameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMnameMouseClicked
        if (txtMname.getText().equals("MIDDLENAME")) {
            txtMname.setText("");
        }
    }//GEN-LAST:event_txtMnameMouseClicked

    private void txtLnameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLnameFocusGained
        if (txtLname.getText().equals("LASTNAME")) {
            txtLname.setText("");
        }
    }//GEN-LAST:event_txtLnameFocusGained

    private void txtLnameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLnameFocusLost
        if (txtLname.getText().equals("")) {
            txtLname.setText("LASTNAME");
        }
    }//GEN-LAST:event_txtLnameFocusLost

    private void txtLnameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtLnameMouseClicked
        if (txtLname.getText().equals("LASTNAME")) {
            txtLname.setText("");
        }
    }//GEN-LAST:event_txtLnameMouseClicked

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        GenderBox();  // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
        jTextField1.setText("Contact Information");
        jTextField1.requestFocus();
    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
f.ClassDisconnected.flag = false;        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
f.ClassDisconnected.flag = false;

    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtSupervisorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSupervisorFocusGained
  if(addNewRecord || jTable4.getSelectedRowCount() > 0 || cboLevel.getSelectedItem().equals("EMPLOYED")){
        if (txtSupervisor.getText().equals("SUPERVISOR")) {
            txtSupervisor.setText("");
        }   }       // TODO add your handling code here:
    }//GEN-LAST:event_txtSupervisorFocusGained

    private void txtSupervisorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSupervisorFocusLost
        if (txtSupervisor.getText().equals("")) {
            txtSupervisor.setText("SUPERVISOR");
        }    // TODO add your handling code here:
    }//GEN-LAST:event_txtSupervisorFocusLost

    private void txtSupervisorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSupervisorMouseClicked
        if(addNewRecord || jTable4.getSelectedRowCount() > 0 || cboLevel.getSelectedItem().equals("EMPLOYED")){
        if (txtSupervisor.getText().equals("SUPERVISOR")) {
            txtSupervisor.setText("");
        }  }    // TODO add your handling code here:
    }//GEN-LAST:event_txtSupervisorMouseClicked

    private void txtAllowanceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAllowanceFocusGained
if(addNewRecord || jTable4.getSelectedRowCount() > 0 || cboLevel.getSelectedItem().equals("EMPLOYED")){
    if (txtAllowance.getText().equals("ALLOWANCE")) {
            txtAllowance.setText("");
        }   }       // TODO add your handling code here:
    }//GEN-LAST:event_txtAllowanceFocusGained

    private void txtAllowanceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAllowanceFocusLost
        if (txtAllowance.getText().equals("")) {
            txtAllowance.setText("ALLOWANCE");
        }      // TODO add your handling code here:
    }//GEN-LAST:event_txtAllowanceFocusLost

    private void txtAllowanceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAllowanceMouseClicked
     if(addNewRecord || jTable4.getSelectedRowCount() > 0 || cboLevel.getSelectedItem().equals("EMPLOYED")){
         if (txtAllowance.getText().equals("ALLOWANCE")) {
            txtAllowance.setText("");
        }  }     // TODO add your handling code here:
    }//GEN-LAST:event_txtAllowanceMouseClicked

    private void txtSupervisorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSupervisorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSupervisorActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
f.ClassDisconnected.flag = false;
        new DynamicPanel(pnlDynamic, new panels.pnlStudentActivities());
    }//GEN-LAST:event_jButton4ActionPerformed

    private void cboHoursItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboHoursItemStateChanged
        OjtHours();        // TODO add your handling code here:
    }//GEN-LAST:event_cboHoursItemStateChanged

    private void cboHoursActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHoursActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboHoursActionPerformed

    private void txtCompInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCompInfoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCompInfoActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased

        jTextField1.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (jComboBox2.getSelectedItem().equals("Number")) {
                    char c = e.getKeyChar();
                    if (!((c >= '0') && (c <= '9')
                            || (c == KeyEvent.VK_BACK_SPACE)
                            || (c == KeyEvent.VK_DELETE))) {
                        getToolkit().beep();
                        e.consume();
                    }
                }
            }
        });


    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTextField1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseClicked
        if (jTextField1.getText().equals("Contact Information")) {
            jTextField1.setText("");
        }           // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1MouseClicked

    private void jTextField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusLost
        if (jTextField1.getText().equals("")) {
            jTextField1.setText("Contact Information");
        }           // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1FocusLost

    private void jTextField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusGained
        if (jTextField1.getText().equals("Contact Information")) {
            jTextField1.setText("");
        }
    }//GEN-LAST:event_jTextField1FocusGained

    private void jTextField2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField2MouseClicked
        if (jTextField2.getText().equals("Achievements and awards recieved")) {
            jTextField2.setText("");
        }
    }//GEN-LAST:event_jTextField2MouseClicked

    private void jTextField2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2FocusGained
        if (jTextField2.getText().equals("Achievements and awards recieved")) {
            jTextField2.setText("");
        }
    }//GEN-LAST:event_jTextField2FocusGained

    private void jTextField2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2FocusLost
        if (jTextField2.getText().equals("")) {
            jTextField2.setText("Achievements and awards recieved");
        }              // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2FocusLost

    private void jTable5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable5MouseClicked
if(addNewRecord){
        UIManagers.getNewUI();
        int row =  jTable5.getSelectedRow();
       selectedRowDocSProfile =  hm.get(row);
  if (evt.getClickCount() == 2) {
        if("".equals(selectedRowDocSProfile) || selectedRowDocSProfile == null){
         JOptionPane.showMessageDialog(null, "Add PDF Document First...");
      }else{
       PDFViewer pv =  new PDFViewer();
       pv.companyadd = false;
       pv.companyprofile = false;
       pv.studentadd = false;
       pv.studentprofile = true;
       pv.setVisible(true);
         } 
  }  
}else{
        int row = jTable5.getSelectedRow();
        String req = jTable5.getModel().getValueAt(row, 0).toString();
        if (evt.getClickCount() == 2) {
            String query;
            try {
                 conn = SQLCon.ConnectDB();
                query = "SELECT  file_name from ojt_requirement_file \n"
                        + "where ojt_information_id='" + selectedOjtRecord + "'\n"
                        + "and pdf_document != ''\n"
                        + "and ojt_requirement_id=(SELECT ojt_requirement_id from ojt_requirements WHERE ojt_requirement_title='" + req + "')";
                Statement state = conn.createStatement();
                ResultSet rs = state.executeQuery(query);
                if (rs.next()) {
                    new File("intern_files").mkdirs();
                    String dir = "intern_files//";
                    String pdf = rs.getString(1) + ".pdf";
                    File f = new File(dir + pdf);
                    if (f.exists() && !f.isDirectory()) {
                        selectedRowDocSProfile = dir + pdf;
                        PDFViewer pv = new PDFViewer();
                        pv.companyadd = false;
                        pv.companyprofile = false;
                        pv.studentadd = false;
                        pv.studentprofile = true;
                        pv.setVisible(true);
                        System.out.println("PDF File already Exist: " + pdf);
                    } else {
                        System.out.println("PDF File not Exist: " + pdf);
                        getCompanyReqPDF();
                    }
                } else {
                    UIManagers.getNewUI();
                    JOptionPane.showMessageDialog(null, "Add PDF Document First...");
                    UIManagers.applyOldUI();
                }
conn.close();
            } catch (Exception e) {
                DC();
                e.printStackTrace();
            }
        }
}
      UIManagers.applyOldUI();  
    }//GEN-LAST:event_jTable5MouseClicked

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jTable5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable5KeyReleased


    }//GEN-LAST:event_jTable5KeyReleased

    private void jTable5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable5KeyPressed

    }//GEN-LAST:event_jTable5KeyPressed

    private void jTable5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable5KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable5KeyTyped

    private void jTable5PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTable5PropertyChange

    }//GEN-LAST:event_jTable5PropertyChange

    private void jTable5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable5MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable5MousePressed

    private void txtDeptFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDeptFocusGained
         if(addNewRecord || jTable4.getSelectedRowCount() > 0 || cboLevel.getSelectedItem().equals("EMPLOYED")){
        if (txtDept.getText().equals("DEPARTMENT")) {
            txtDept.setText("");
        } }        // TODO add your handling code here:
    }//GEN-LAST:event_txtDeptFocusGained

    private void txtDeptFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDeptFocusLost
        if (txtDept.getText().equals("")) {
            txtDept.setText("DEPARTMENT");
        }    // TODO add your handling code here:
    }//GEN-LAST:event_txtDeptFocusLost

    private void txtDeptMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDeptMouseClicked
  if(addNewRecord || jTable4.getSelectedRowCount() > 0 || cboLevel.getSelectedItem().equals("EMPLOYED")){
        if (txtDept.getText().equals("DEPARTMENT")) {
            txtDept.setText("");
        }  }     // TODO add your handling code here:
    }//GEN-LAST:event_txtDeptMouseClicked

    private void txtDeptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDeptActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDeptActionPerformed

    private void txtCompNoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCompNoFocusGained
  if(addNewRecord || jTable4.getSelectedRowCount() > 0 || cboLevel.getSelectedItem().equals("EMPLOYED")){
        if (txtCompNo.getText().equals("SUPERVISOR CONTACT NUMBER")) {
            txtCompNo.setText("");
        }
        txtCompNo.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9')
                        || (c == KeyEvent.VK_BACK_SPACE)
                        || (c == KeyEvent.VK_DELETE))) {
                    getToolkit().beep();
                    e.consume();
                }
            }
        });
  }
    }//GEN-LAST:event_txtCompNoFocusGained

    private void txtCompNoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCompNoFocusLost
        if (txtCompNo.getText().equals("")) {
            txtCompNo.setText("SUPERVISOR CONTACT NUMBER");
        }      // TODO add your handling code here:
    }//GEN-LAST:event_txtCompNoFocusLost

    private void txtCompNoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCompNoMouseClicked
  if(addNewRecord || jTable4.getSelectedRowCount() > 0 || cboLevel.getSelectedItem().equals("EMPLOYED")){
        if (txtCompNo.getText().equals("SUPERVISOR CONTACT NUMBER")) {
            txtCompNo.setText("");
        }   }  // TODO add your handling code here:
    }//GEN-LAST:event_txtCompNoMouseClicked

    private void cboDeptItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboDeptItemStateChanged
 if(deptAction){
     getCourse(); 
 }    
        // TODO add your handling code here:
    }//GEN-LAST:event_cboDeptItemStateChanged

    private void cboDeptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboDeptActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboDeptActionPerformed

    private void cboCourseItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboCourseItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cboCourseItemStateChanged

    private void cboLevelItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboLevelItemStateChanged
   int n = cboLevel.getItemCount();
   if(n>0){
        if(cboLevel.getSelectedItem().equals("EMPLOYED")){
            System.out.println("IF");
        cboHours.addItem("0");
        cboHours.setSelectedItem("0");
        cboHours.setFocusable(false); 
        cboHours.setEnabled(false);
        txtAllowance.setEnabled(false);
        txtAllowance.setText("0");
        txtAllowance.setFocusable(false);
           DefaultTableModel model = (DefaultTableModel) jTable5.getModel();
            model.setRowCount(0);
            
         if(!addNewRecord && jTable4.getSelectedRowCount() <= 0){
            txtCompInfo.setEditable(true); 
  txtDept.setEditable(true);
  txtSupervisor.setEditable(true);
  txtCompNo.setEditable(true);  
         }
         
          if(!addNewRecord && jTable4.getSelectedRowCount() <= 0 && cboLevel.getSelectedItem().equals(SelectedStudent.slevel) ){
            txtCompInfo.setEditable(false); 
  txtDept.setEditable(false);
  txtSupervisor.setEditable(false);
  txtCompNo.setEditable(false);  
         }
    }else{
            System.out.println("ELSE");
        getHours(); 
        cboHours.setEnabled(true);
        cboHours.setFocusable(true); 
         txtAllowance.setEnabled(true);
        txtAllowance.setText("ALLOWANCE");
        txtAllowance.setFocusable(true);
        
        if(addNewRecord){
         getCompanyReqList();
            reqListTable();
             list.clear();
            hm.clear();
        }
         if (jTable4.getSelectedRowCount() > 0) { 
            String compReq = jTable4.getModel().getValueAt(selectedRecord, 0).toString();
            getOjtRecordFiles(); 
             if(compReq.equals("EMPLOYED")){
                  getHours();
             } 
         }
        
    }
   }
    }//GEN-LAST:event_cboLevelItemStateChanged

    private void cboLevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLevelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboLevelActionPerformed

    private void jTable4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MouseClicked

    }//GEN-LAST:event_jTable4MouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

f.ClassDisconnected.flag = false;
if (addNewRecord) {
            ///////
            UIManagers.getNewUI();
            if (txtStudentNo.getText().isEmpty() || txtStudentNo.getText().equals("STUDENT NO.")) {
                JOptionPane.showMessageDialog(null, "Student number is required");
                txtStudentNo.requestFocus();
            } else if (txtFname.getText().isEmpty() || txtFname.getText().equals("FIRSTNAME")) {
                JOptionPane.showMessageDialog(null, "Student first name is required");
                txtFname.requestFocus();
            } else if (txtLname.getText().isEmpty() || txtLname.getText().equals("LASTNAME")) {
                JOptionPane.showMessageDialog(null, "Student last name is required");
                txtLname.requestFocus();
            } else if (jComboBox1.getSelectedItem().equals("Select Gender")) {
                JOptionPane.showMessageDialog(null, "Select gender");
                jComboBox1.requestFocus();
            } else if (cboDept.getSelectedItem().equals("Select Department")) {
                JOptionPane.showMessageDialog(null, "Select Department");
                cboDept.requestFocus();
            } else if (cboCourse.getSelectedItem().equals("Select Course")) {
                JOptionPane.showMessageDialog(null, "Select Course");
                cboCourse.requestFocus();
            } else if (cboLevel.getSelectedItem().equals("Select Level")) {
                JOptionPane.showMessageDialog(null, "Select Level");
                cboLevel.requestFocus();
            } else if (jTable1.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Add student contact information");
                jTextField1.requestFocus();
            } else if (jTable3.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(null, "Select Company information");
                txtCompInfo.requestFocus();
            } else if (txtDept.getText().isEmpty() || txtDept.getText().equals("DEPARTMENT")) {
                JOptionPane.showMessageDialog(null, "Department is required");
                txtDept.requestFocus();
            } else if (txtSupervisor.getText().isEmpty() || txtSupervisor.getText().equals("SUPERVISOR")) {
                JOptionPane.showMessageDialog(null, "Company Supervisor is required");
                txtSupervisor.requestFocus();
            } else if (txtCompNo.getText().isEmpty() || txtCompNo.getText().equals("SUPERVISOR CONTACT NUMBER")) {
                JOptionPane.showMessageDialog(null, "Supervisor Contact number is required");
                txtCompNo.requestFocus();
            } else if (txtAllowance.getText().isEmpty() || txtAllowance.getText().equals("ALLOWANCE")) {
                JOptionPane.showMessageDialog(null, "Allowance is required, put zero if none");
                txtAllowance.requestFocus();
            } else if (cboHours.getSelectedItem().equals("Select Hours")) {
                JOptionPane.showMessageDialog(null, "Select Internship Hours");
                cboHours.requestFocus();
            } else {
                
                if(cboLevel.getSelectedItem().toString().equals("EMPLOYED")){
                      checkEmployed();
                }else{
                       String sql = "SELECT `s_information_id` FROM `student_records` WHERE `s_year_level_id`=(SELECT s_level_id FROM `student_levels` WHERE `s_level_title` = '"+cboLevel.getSelectedItem().toString()+"' ) and `s_information_id` = '"+SelectedStudent.sid+"'";
        try {
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                UIManagers.getNewUI();
                JOptionPane.showMessageDialog(null, "Same record detected...");
                UIManagers.applyOldUI();
            }else{
                   insertOjtInformation();  
                addNewRecord = false;
                jButton6.setText("ADD");
                jButton7.setEnabled(true);
                jButton4.setEnabled(true);
                jButton8.setText("UPDATE");
                jButton8.setBackground(new java.awt.Color(84, 127, 206));
                jTable4.setEnabled(true);
                txtStudentNo.setEditable(true);
                txtFname.setEditable(true);
                txtMname.setEditable(true);
                txtLname.setEditable(true);
                txtSuffix.setEditable(true);
                 DefaultTableModel model = (DefaultTableModel) jTable5.getModel();
            model.setRowCount(0);
             CompanyListFunction(); 
                            f.StudentRecList.TableFromDatabase(SelectedStudent.sid);
RecordsTableList(); 
 // jTable4.setRowSelectionInterval(selectedRecord, selectedRecord);  
           } 
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1081", JOptionPane.ERROR_MESSAGE);
        }

                   

            }
                 } 
        } else {
            
              txtCompInfo.setEditable(true); 
  txtDept.setEditable(true);
  txtSupervisor.setEditable(true);
  txtCompNo.setEditable(true);
  txtAllowance.setEditable(true);
  cboHours.setEnabled(true); 
            
            addNewRecord = true;
            deptAction = false;
            jButton6.setText("SAVE");
            jButton4.setEnabled(false);
            jButton7.setEnabled(false);
            jButton8.setText("CANCEL");
            jButton8.setBackground(new java.awt.Color(255, 102, 102));
            jTable4.setEnabled(false);
            //remove selection jtable4
            txtStudentNo.setEditable(false);
            txtFname.setEditable(false);
            txtMname.setEditable(false);
            txtLname.setEditable(false);
            txtSuffix.setEditable(false); 
             jComboBox1.setEnabled(false);
            cboDept.setSelectedIndex(0);
            cboCourse.setSelectedIndex(0);
            cboLevel.setSelectedIndex(0);
            jTable4.getSelectionModel().clearSelection();
            getCompanyReqList();
            reqListTable();
            list.clear();
            hm.clear();
            cboDept.removeAllItems();
            cboDept.addItem("Select Department");
            getDepartment();
getLevels();
//getCourse(); 
            /////
            txtCompInfo.setText("Search Company and Branch name or ID");
            DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
            model.setRowCount(0);
            txtDept.setText("DEPARTMENT");
            txtSupervisor.setText("SUPERVISOR");
            txtCompNo.setText("SUPERVISOR CONTACT NUMBER");
            txtAllowance.setText("ALLOWANCE");
            cboHours.setSelectedIndex(0);
            /////

            deptAction = true;
        }

        UIManagers.applyOldUI();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
f.ClassDisconnected.flag = false;
        new DynamicPanel(pnlDynamic, new panels.pnlStudentLogs());

    }//GEN-LAST:event_jButton7ActionPerformed

    private void txtSuffixFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSuffixFocusGained
        if (txtSuffix.getText().equals("SUFFIX")) {
            txtSuffix.setText("");
        }          // TODO add your handling code here:
    }//GEN-LAST:event_txtSuffixFocusGained

    private void txtSuffixFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSuffixFocusLost
        if (txtSuffix.getText().equals("")) {
            txtSuffix.setText("SUFFIX");
        }      // TODO add your handling code here:
    }//GEN-LAST:event_txtSuffixFocusLost

    private void txtSuffixMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSuffixMouseClicked
        if (txtSuffix.getText().equals("SUFFIX")) {
            txtSuffix.setText("");
        }      // TODO add your handling code here:
    }//GEN-LAST:event_txtSuffixMouseClicked

    private void txtSuffixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSuffixActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSuffixActionPerformed

    private void txtCompInfoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCompInfoFocusGained
     if(addNewRecord || jTable4.getSelectedRowCount() > 0 || cboLevel.getSelectedItem().equals("EMPLOYED")){
          if (txtCompInfo.getText().equals("Search Company and Branch name or ID")) {
            txtCompInfo.setText("");
        } 
        }  
    }//GEN-LAST:event_txtCompInfoFocusGained

    private void txtCompInfoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCompInfoMouseClicked
 if(addNewRecord || jTable4.getSelectedRowCount() > 0 || cboLevel.getSelectedItem().equals("EMPLOYED")){
          if (txtCompInfo.getText().equals("Search Company and Branch name or ID")) {
            txtCompInfo.setText("");
        } 
        }  
    }//GEN-LAST:event_txtCompInfoMouseClicked

    private void txtCompInfoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCompInfoFocusLost
        if (txtCompInfo.getText().equals("")) {
            txtCompInfo.setText("Search Company and Branch name or ID");
        }            // TODO add your handling code here:
    }//GEN-LAST:event_txtCompInfoFocusLost

    private void lblImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImageMouseClicked
        if (!addNewRecord) {
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

            // Set path
            //jf.setCurrentDirectory(new File (System.getProperty("user.home")+System.getProperty("file.separator")+"Documents"));
            jf.setCurrentDirectory(new File(currentDir));

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
                System.out.println("Selected File: " + selectedFile);
                if (selectedFile != null) {
                    String path = selectedFile.getAbsolutePath();
                    System.out.println("Selected File Path: " + path);
                    File file = new File(path);
                    if (file.exists() && !file.isDirectory()) {
                        lblImage.setIcon(ResizeImage(path));
                        s = path;
                        currentDir = s;
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
        }// TODO add your handling code here:
    }//GEN-LAST:event_lblImageMouseClicked

    private void txtCompInfoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCompInfoKeyReleased
        DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
        if (txtCompInfo.getText().equals("") || txtCompInfo.getText().equals("Search Company and Branch name or ID")) {
            model.setRowCount(0);
            System.out.println("SELECTED ROW COUNT: " + jTable4.getSelectedRow());
            System.out.println("SELECTED ROW COUNT2: " + jTable4.getSelectedRowCount());
            if (jTable4.getSelectedRowCount() > 0) {
                getOjtRecordCompany();
            }
        } else {
            model.setRowCount(0);
            FindCompany();
            jTable3.getSelectionModel().clearSelection();
        }            // TODO add your handling code here:
    }//GEN-LAST:event_txtCompInfoKeyReleased

    private void txtCompNoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCompNoKeyReleased
     if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
            shiftPressed = false;
        }
        FilterCompanyContact(); 
        //txtCompNo.setText(txtCompNo.getText().toUpperCase());
    }//GEN-LAST:event_txtCompNoKeyReleased

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed

        f.ClassDisconnected.flag = false;
        int stdno_lenth = txtStudentNo.getText().length();
 for(int i=0; i<stdno_lenth; i++){
      if(txtStudentNo.getText().charAt(0)==' '){
   txtStudentNo.setText(txtStudentNo.getText().replaceFirst(" ", ""));
 }
 }
 
  int fname_lenth = txtFname.getText().length();
 for(int i=0; i<fname_lenth; i++){
      if(txtFname.getText().charAt(0)==' '){
   txtFname.setText(txtFname.getText().replaceFirst(" ", ""));
 }
 }
 
   int mname_lenth = txtMname.getText().length();
 for(int i=0; i<mname_lenth; i++){
      if(txtMname.getText().charAt(0)==' '){
   txtMname.setText(txtMname.getText().replaceFirst(" ", ""));
 }
 }
 
    int lname_lenth = txtLname.getText().length();
 for(int i=0; i<lname_lenth; i++){
      if(txtLname.getText().charAt(0)==' '){
   txtLname.setText(txtLname.getText().replaceFirst(" ", ""));
 }
 }
 
 
    int suffix_lenth = txtSuffix.getText().length();
 for(int i=0; i<suffix_lenth; i++){
      if(txtSuffix.getText().charAt(0)==' '){
   txtSuffix.setText(txtSuffix.getText().replaceFirst(" ", ""));
 }
 }
       
        UIManagers.getNewUI();
        if (addNewRecord) {
            
            
              txtCompInfo.setEditable(false); 
  txtDept.setEditable(false);
  txtSupervisor.setEditable(false);
  txtCompNo.setEditable(false);
  txtAllowance.setEditable(false);
  //cboHours.setEnabled(false); 
            
            addNewRecord = false;
            jButton6.setText("ADD");
            jButton7.setEnabled(true);
            jButton4.setEnabled(true);
            jButton8.setText("UPDATE");
            jButton8.setBackground(new java.awt.Color(84, 127, 206));
            jTable4.setEnabled(true);
            txtStudentNo.setEditable(true);
            txtFname.setEditable(true);
            txtMname.setEditable(true);
            txtLname.setEditable(true);
            txtSuffix.setEditable(true);
           jComboBox1.setEnabled(true);
            ////
            txtCompInfo.setText("Search Company and Branch name or ID");
            DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
            model.setRowCount(0);
            model = (DefaultTableModel) jTable5.getModel();
            model.setRowCount(0);
            txtDept.setText("DEPARTMENT");
            txtSupervisor.setText("SUPERVISOR");
            txtCompNo.setText("SUPERVISOR CONTACT NUMBER");
            txtAllowance.setText("ALLOWANCE");
            cboHours.setSelectedIndex(0);
             CompanyListFunction();
            reqListTable();
            //

        } else { 
         
  if (jTable4.getSelectedRowCount() > 0) {
            String record = jTable4.getModel().getValueAt(selectedRecord, 0).toString(); 
      if(!record.equals(cboLevel.getSelectedItem().toString())){
           String sql = "SELECT `s_information_id` FROM `student_records` WHERE `s_year_level_id`=(SELECT s_level_id FROM `student_levels` WHERE `s_level_title` = '"+cboLevel.getSelectedItem().toString()+"' ) and `s_information_id` = '"+SelectedStudent.sid+"'";
        try {
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                UIManagers.getNewUI();
                JOptionPane.showMessageDialog(null, "Same record detected...");
                UIManagers.applyOldUI();
            }else{
                    String recordList = jTable4.getModel().getValueAt(selectedRecord, 0).toString();
            if(!recordList.equals("EMPLOYED") && cboLevel.getSelectedItem().toString().equals("EMPLOYED")){
                    sql = "SELECT `s_information_id` FROM `student_records` WHERE `s_year_level_id`=(SELECT s_level_id FROM `student_levels` WHERE `s_level_title` = 'EMPLOYED' ) and `s_information_id` = '"+SelectedStudent.sid+"'";
        try {
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                UIManagers.getNewUI();
                JOptionPane.showMessageDialog(null, "Student is already employed...");
                UIManagers.applyOldUI();
            }else{
                  if(jTable3.getSelectedRowCount()<=0){
           JOptionPane.showMessageDialog(null, "Select Company information");
                txtCompInfo.requestFocus();
      }else if (txtDept.getText().isEmpty() || txtDept.getText().equals("DEPARTMENT")) {
                JOptionPane.showMessageDialog(null, "Department is required");
                txtDept.requestFocus();
            }else if (txtSupervisor.getText().isEmpty() || txtSupervisor.getText().equals("SUPERVISOR")) {
                JOptionPane.showMessageDialog(null, "Company Supervisor is required");
                txtSupervisor.requestFocus();
            } else if (txtCompNo.getText().isEmpty() || txtCompNo.getText().equals("SUPERVISOR CONTACT NUMBER")) {
                JOptionPane.showMessageDialog(null, "Supervisor Contact number is required");
                txtCompNo.requestFocus();
            } else if (txtAllowance.getText().isEmpty() || txtAllowance.getText().equals("ALLOWANCE")) {
                JOptionPane.showMessageDialog(null, "Allowance is required, put zero if none");
                txtAllowance.requestFocus();
            }else if(cboHours.getSelectedItem().equals("Select Hours")){
           JOptionPane.showMessageDialog(null, "Select Internship Hours");
                cboHours.requestFocus();
      }else{
          filterUpdate();
      }
            }
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1081", JOptionPane.ERROR_MESSAGE);
        }
            }else{
      
      
      
      if(jTable3.getSelectedRowCount()<=0){
           JOptionPane.showMessageDialog(null, "Select Company information");
                txtCompInfo.requestFocus();
      }else if (txtDept.getText().isEmpty() || txtDept.getText().equals("DEPARTMENT")) {
                JOptionPane.showMessageDialog(null, "Department is required");
                txtDept.requestFocus();
            }else if (txtSupervisor.getText().isEmpty() || txtSupervisor.getText().equals("SUPERVISOR")) {
                JOptionPane.showMessageDialog(null, "Company Supervisor is required");
                txtSupervisor.requestFocus();
            } else if (txtCompNo.getText().isEmpty() || txtCompNo.getText().equals("SUPERVISOR CONTACT NUMBER")) {
                JOptionPane.showMessageDialog(null, "Supervisor Contact number is required");
                txtCompNo.requestFocus();
            } else if (txtAllowance.getText().isEmpty() || txtAllowance.getText().equals("ALLOWANCE")) {
                JOptionPane.showMessageDialog(null, "Allowance is required, put zero if none");
                txtAllowance.requestFocus();
            }else if(cboHours.getSelectedItem().equals("Select Hours")){
           JOptionPane.showMessageDialog(null, "Select Internship Hours");
                cboHours.requestFocus();
      }else{
          filterUpdate();
      }
  }
           } 
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1081", JOptionPane.ERROR_MESSAGE);
        }
      }else{///
         String recordList = jTable4.getModel().getValueAt(selectedRecord, 0).toString();
            if(!recordList.equals("EMPLOYED") && cboLevel.getSelectedItem().toString().equals("EMPLOYED")){
                   String sql = "SELECT `s_information_id` FROM `student_records` WHERE `s_year_level_id`=(SELECT s_level_id FROM `student_levels` WHERE `s_level_title` = 'EMPLOYED' ) and `s_information_id` = '"+SelectedStudent.sid+"'";
        try {
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                UIManagers.getNewUI();
                JOptionPane.showMessageDialog(null, "Student is already employed...");
                UIManagers.applyOldUI();
            }else{
                  if(jTable3.getSelectedRowCount()<=0){
           JOptionPane.showMessageDialog(null, "Select Company information");
                txtCompInfo.requestFocus();
      }else if (txtDept.getText().isEmpty() || txtDept.getText().equals("DEPARTMENT")) {
                JOptionPane.showMessageDialog(null, "Department is required");
                txtDept.requestFocus();
            }else if (txtSupervisor.getText().isEmpty() || txtSupervisor.getText().equals("SUPERVISOR")) {
                JOptionPane.showMessageDialog(null, "Company Supervisor is required");
                txtSupervisor.requestFocus();
            } else if (txtCompNo.getText().isEmpty() || txtCompNo.getText().equals("SUPERVISOR CONTACT NUMBER")) {
                JOptionPane.showMessageDialog(null, "Supervisor Contact number is required");
                txtCompNo.requestFocus();
            } else if (txtAllowance.getText().isEmpty() || txtAllowance.getText().equals("ALLOWANCE")) {
                JOptionPane.showMessageDialog(null, "Allowance is required, put zero if none");
                txtAllowance.requestFocus();
            }else if(cboHours.getSelectedItem().equals("Select Hours")){
           JOptionPane.showMessageDialog(null, "Select Internship Hours");
                cboHours.requestFocus();
      }else{
          filterUpdate();
      }
            }
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1081", JOptionPane.ERROR_MESSAGE);
        }
            }else{
      
      
      
      if(jTable3.getSelectedRowCount()<=0){
           JOptionPane.showMessageDialog(null, "Select Company information");
                txtCompInfo.requestFocus();
      }else if (txtDept.getText().isEmpty() || txtDept.getText().equals("DEPARTMENT")) {
                JOptionPane.showMessageDialog(null, "Department is required");
                txtDept.requestFocus();
            }else if (txtSupervisor.getText().isEmpty() || txtSupervisor.getText().equals("SUPERVISOR")) {
                JOptionPane.showMessageDialog(null, "Company Supervisor is required");
                txtSupervisor.requestFocus();
            } else if (txtCompNo.getText().isEmpty() || txtCompNo.getText().equals("SUPERVISOR CONTACT NUMBER")) {
                JOptionPane.showMessageDialog(null, "Supervisor Contact number is required");
                txtCompNo.requestFocus();
            } else if (txtAllowance.getText().isEmpty() || txtAllowance.getText().equals("ALLOWANCE")) {
                JOptionPane.showMessageDialog(null, "Allowance is required, put zero if none");
                txtAllowance.requestFocus();
            }else if(cboHours.getSelectedItem().equals("Select Hours")){
           JOptionPane.showMessageDialog(null, "Select Internship Hours");
                cboHours.requestFocus();
      }else{
          filterUpdate();
      }
  }
  }
  }else if(!SelectedStudent.slevel.equals(cboLevel.getSelectedItem().toString())){
     
        if(cboLevel.getSelectedItem().toString().equals("EMPLOYED")){
            if(jTable3.getSelectedRowCount()<=0){
           JOptionPane.showMessageDialog(null, "Select Company information");
                txtCompInfo.requestFocus();
      }else if (txtDept.getText().isEmpty() || txtDept.getText().equals("DEPARTMENT")) {
                JOptionPane.showMessageDialog(null, "Department is required");
                txtDept.requestFocus();
            }else if (txtSupervisor.getText().isEmpty() || txtSupervisor.getText().equals("SUPERVISOR")) {
                JOptionPane.showMessageDialog(null, "Company Supervisor is required");
                txtSupervisor.requestFocus();
            } else if (txtCompNo.getText().isEmpty() || txtCompNo.getText().equals("SUPERVISOR CONTACT NUMBER")) {
                JOptionPane.showMessageDialog(null, "Supervisor Contact number is required");
                txtCompNo.requestFocus();
            } else if (txtAllowance.getText().isEmpty() || txtAllowance.getText().equals("ALLOWANCE")) {
                JOptionPane.showMessageDialog(null, "Allowance is required, put zero if none");
                txtAllowance.requestFocus();
            }else if(cboHours.getSelectedItem().equals("Select Hours")){
           JOptionPane.showMessageDialog(null, "Select Internship Hours");
                cboHours.requestFocus();
      }else{ 
                   String sql = "SELECT `s_information_id` FROM `student_records` WHERE `s_year_level_id`=(SELECT s_level_id FROM `student_levels` WHERE `s_level_title` = 'EMPLOYED' ) and `s_information_id` = '"+SelectedStudent.sid+"'";
        try {
             conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                UIManagers.getNewUI();
                JOptionPane.showMessageDialog(null, "Student is already employed...");
                UIManagers.applyOldUI();
            }else{ 
                 System.out.println("1st");
                insertOjtInformation();
             //JOptionPane.showMessageDialog(null, "Insert Update Add Employed Record");    
         // filterUpdate();///make insert employed record
            }
            conn.close();
        }catch(Exception e){
            DC();
            e.printStackTrace();
        }
                
                
            
      }
        }else{
             System.out.println("3rd");
            filterUpdate(); 
        } 
  }else{
       System.out.println("2nd");
            ////START UPDATE PERSONAL INFO     
           filterUpdate();
            ////END UPDATE PERSONAL INFO 
  }
  
            }
        
        UIManagers.applyOldUI();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jTable4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MouseReleased
f.ClassDisconnected.flag = false;
        if (!addNewRecord) {
            int row = jTable4.getSelectedRow();
            selectedRecord = row;
            String compReq = jTable4.getModel().getValueAt(row, 0).toString();

            Boolean checked = (Boolean) jTable4.getValueAt(row, 2);
            String compReqStatus = "0";
            if (checked) {
                compReqStatus = "1";
            } else {
                compReqStatus = "0";
            }
            try {
                 conn = SQLCon.ConnectDB();
                String sql = "SELECT sr.s_records_id,sr.s_ojt_information_id FROM `student_records` sr INNER JOIN student_levels sl on sr.s_year_level_id=sl.s_level_id inner join ojt_information oi on sr.s_ojt_information_id=oi.ojt_information_id inner join ojt_hours oh on oi.ojt_hours_id=oh.ojt_hours_id WHERE sr.s_information_id='" + SelectedStudent.sid + "' ORDER BY sr.s_records_id ASC\n"
                        + "LIMIT " + row + ",1";
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                while (rs.next()) {
                    selectedStudentRecord = rs.getString(1);
                    selectedOjtRecord = rs.getString(2);
                }
                System.out.println("STUDENT RECORD ID: " + selectedStudentRecord);
                System.out.println("STUDENT OJT RECORD ID: " + selectedOjtRecord);
                updateOjtCompleteStatus(compReqStatus);
                txtCompInfo.setText("Search Company and Branch name or ID");
                conn.close();
            } catch (Exception e) {
                DC();
                e.printStackTrace();
                //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1040", JOptionPane.ERROR_MESSAGE);
            }
                 txtCompInfo.setEditable(true); 
  txtDept.setEditable(true);
  txtSupervisor.setEditable(true);
  txtCompNo.setEditable(true);
  txtAllowance.setEditable(true);
  if(!cboLevel.getSelectedItem().equals("EMPLOYED")){
     cboHours.setEnabled(true);  
  }
  
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jTable4MouseReleased

    private void txtStudentNoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStudentNoKeyReleased
//txtStudentNo.setText(txtStudentNo.getText().toUpperCase());        // TODO add your handling code here:
    }//GEN-LAST:event_txtStudentNoKeyReleased

    private void txtFnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFnameKeyReleased
//txtFname.setText(txtFname.getText().toUpperCase());        // TODO add your handling code here:
    }//GEN-LAST:event_txtFnameKeyReleased

    private void txtMnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMnameKeyReleased
//txtMname.setText(txtMname.getText().toUpperCase());        // TODO add your handling code here:
    }//GEN-LAST:event_txtMnameKeyReleased

    private void txtLnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLnameKeyReleased
//txtLname.setText(txtLname.getText().toUpperCase());        // TODO add your handling code here:
    }//GEN-LAST:event_txtLnameKeyReleased

    private void txtSuffixKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSuffixKeyReleased
//txtSuffix.setText(txtSuffix.getText().toUpperCase());        // TODO add your handling code here:
    }//GEN-LAST:event_txtSuffixKeyReleased

    private void txtDeptKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDeptKeyReleased
//txtDept.setText(txtDept.getText().toUpperCase());        // TODO add your handling code here:
    }//GEN-LAST:event_txtDeptKeyReleased

    private void txtSupervisorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSupervisorKeyReleased
//txtSupervisor.setText(txtSupervisor.getText().toUpperCase());        // TODO add your handling code here:
    }//GEN-LAST:event_txtSupervisorKeyReleased

    private void txtAllowanceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAllowanceKeyReleased
//txtAllowance.setText(txtAllowance.getText().toUpperCase());        // TODO add your handling code here:
    }//GEN-LAST:event_txtAllowanceKeyReleased

    private void txtCompNoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCompNoKeyPressed
  if(shiftPressed && evt.getKeyCode()==KeyEvent.VK_EQUALS){
            txtCompNo.setText(txtCompNo.getText()+"+");
        }else if(shiftPressed && evt.getKeyCode()==KeyEvent.VK_9){
            txtCompNo.setText(txtCompNo.getText()+"(");
        }else if(shiftPressed && evt.getKeyCode()==KeyEvent.VK_0){
            txtCompNo.setText(txtCompNo.getText()+")");
        }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
            shiftPressed = true;
        }           // TODO add your handling code here:
    }//GEN-LAST:event_txtCompNoKeyPressed

    private void txtMnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMnameActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JComboBox<String> cboCourse;
    public static javax.swing.JComboBox<String> cboDept;
    private javax.swing.JComboBox<String> cboHours;
    public static javax.swing.JComboBox<String> cboLevel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    public static javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    public static javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    public static javax.swing.JTable jTable1;
    public static javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    public static javax.swing.JTable jTable4;
    public static javax.swing.JTable jTable5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    public static javax.swing.JLabel lblImage;
    private javax.swing.JPanel pnlStudentPersonalInfo;
    private javax.swing.JPanel pnlStudentPersonalInfo1;
    private javax.swing.JPanel pnlStudentPersonalInfo2;
    private javax.swing.JPanel pnlStudentPersonalInfo3;
    private javax.swing.JPanel pnlStudentPersonalInfo4;
    private javax.swing.JTextField txtAllowance;
    private javax.swing.JTextField txtCompInfo;
    private javax.swing.JTextField txtCompNo;
    private javax.swing.JTextField txtDept;
    public static javax.swing.JTextField txtFname;
    public static javax.swing.JTextField txtLname;
    public static javax.swing.JTextField txtMname;
    public static javax.swing.JTextField txtStudentNo;
    public static javax.swing.JTextField txtSuffix;
    private javax.swing.JTextField txtSupervisor;
    // End of variables declaration//GEN-END:variables
 public static ImageIcon ResizeImage(String imgPath) {
        ImageIcon MyImage = new ImageIcon(imgPath);
        Image img = MyImage.getImage();
        Image newImage = img.getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImage);
        return image;
    }
}
