/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home;

import f.AuditMessage;
import f.BCrypt;
import f.Zz;
import f.ForcedListSelectionModel;
import f.GetProfileInfo;
import f.GetUserInfo;
import f.SQLCon;
import f.SelectedUserAccount;
import f.SystemInfo;
import f.TableFromDatabase;
import f.UIManagers;
import f.UpdateAccount;
import f.UpdateAccountPassword;
import f.UpdateAccountStatus;
import f.getCompanyCount;
import f.getStudentCount;
import f.getUserCount;
import f.getEmployedCount;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import net.proteanit.sql.DbUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import panels.DynamicPanel;

/**
 *
 * @author Clrkz
 */
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import panels.Disconnected;
import panels.ImageReduce;
import panels.pnlCompanies;
import panels.pnlCompanyEmployed;
import panels.pnlCompanyIntern;
import panels.pnlStudentLogs;
import panels.pnlStudentProfile;
import panels.pnlStudents;
public class Home extends javax.swing.JFrame {
private boolean studentlist = true;
private boolean companylist = true;
////SYSTEM OPTIONS START
private boolean studentoptions = true;
private boolean internshipoptions = false;
private boolean companyoptions = false;
private boolean accessibilityoptions = false;
////SYSTEM OPTIONS END
public static Connection conn = null;
public static PreparedStatement pst =  null;
public static ResultSet rs = null;
boolean changeAvatar = false;
String s;
AuditMessage am = new AuditMessage();
boolean updateprofile=false,updateaccount = false;
int auditListStart = 0,profileAuditListStart=0,userProfileAuditListStart=0,auditlistEnd=20;
private final Border bordePressed = javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 0, new java.awt.Color(255,255,255));
 boolean staticStudentAddNavClick = false;
 boolean userProfileAuditLast = false;
 boolean profileAuditLast = false;
  boolean AuditListLast = false;
  /// Start Users List
  int startUserList = 0,endUserList = 20;
  boolean lastUserList = false;
  /// End Users List
    boolean profileFilterByDate = false;
    boolean auditFilterByDate = false;
    boolean UserProfileFilterByDate = false;
  int listPage = 1;
  int getCurrentUserListPage;
 public static   boolean searchuserlist = false;
 public static   boolean searchcompanylist = false;
public static    boolean searchstudentlist = false; 
  public static boolean  searchlogs = false; 
    public static boolean searchintern = false;
    public static boolean   searchemployed = false;
  public static int startCompList = 0,endCompList = 20;
  public static boolean lastCompList = false; 
   boolean isDept = false;
   boolean isCourse = false;
   boolean isLevel = false;
   public static boolean printcompanylist = false;
   public static boolean printhistorylist = false;
   public static boolean printuserrecord = false;
   public static boolean printuserlist = false;
    public static boolean printuserprofile = false;
    public static boolean printloglist = false;
    public static boolean printinternlist = false;
    public static boolean printdeployedlist = false;
    public static boolean printstudentlist = false;
    public static boolean printstudentlog = false;
    public static boolean printstudentact = false;
    public static boolean printstudentrecord = false;
    public static boolean printstatistics = true;
    static Home cmp = new Home(); 
    /**
     * Creates new form Home
     */
    public Home() {
        initComponents();
        f.ClassDisconnected.flag = false;
        jLabel13.setVisible(false);
        jLabel12.setVisible(false);
        ImageIcon img = new ImageIcon("/images/logo_jmo_1_GvN_icon.ico");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/logo jmo_1.png")));
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setLocationRelativeTo(this);
        Components();
        Homepage();
        BarGraph();
        PieChart();
        UserProfileDateFilter();
       profileDateFilter();
       AuditDateFilter();
       
          LogsPanel();  
          UsersPanel();
          UserGender();
          UsersRecordLog();
          ProfileRecordLog();
          AuditTrail();
          ContactFilter();
          UserContactFilter();
          
      showTime();
      jLabel23.setText(getUserCount.StartUpdate());
      jLabel17.setText(getEmployedCount.StartUpdate());
      jLabel20.setText(getCompanyCount.StartUpdate());
      jLabel3.setText(getStudentCount.StartUpdate());
      jTable4.setSelectionModel(new ForcedListSelectionModel());
      
 
        
       OnSystemExit();
     
          jTable4.getTableHeader().setReorderingAllowed(false); 
           jPasswordField3.setEchoChar((char)0);
            jPasswordField1.setEchoChar((char)0);
            jPasswordField2.setEchoChar((char)0);
           
           
           
             txtFromDate7.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
                 
             @Override
             public void propertyChange(PropertyChangeEvent e) {
                 if(txtFromDate7.getDate()!=null && txtFromDate8.getDate()!=null ){
                     startCompList = 0;
                        jLabel112.setText("<html>Result between <font color='blue'><strong>"+((JTextField)txtFromDate7.getDateEditor().getUiComponent()).getText()+"</strong></font> and <font color='blue'><strong>"+((JTextField)txtFromDate8.getDateEditor().getUiComponent()).getText()+"</strong></font></html>");
                     lastCompList = false;
                     getCompanyList(startCompList,endCompList);   
                 listPage = 1;
            jLabel113.setText("Page: "+listPage); 
                 }else{ 
                     getCompanyList(startCompList,endCompList);  
                     jLabel112.setText("");
                 }
             }
         });
           
            txtFromDate8.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {  
             @Override
             public void propertyChange(PropertyChangeEvent e) {
                 if(txtFromDate7.getDate()!=null && txtFromDate8.getDate()!=null ){
                     startCompList = 0;
                       jLabel112.setText("<html>Result between <font color='blue'><strong>"+((JTextField)txtFromDate7.getDateEditor().getUiComponent()).getText()+"</strong></font> and <font color='blue'><strong>"+((JTextField)txtFromDate8.getDateEditor().getUiComponent()).getText()+"</strong></font></html>");
                     lastCompList = false;
                     getCompanyList(startCompList,endCompList);   
                 listPage = 1;
            jLabel113.setText("Page: "+listPage); 
                 } else{ 
                     getCompanyList(startCompList,endCompList);   
                     jLabel112.setText("");
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
    
   
    void SaveHome(){
        BufferedImage img = new BufferedImage(pnlHomePage.getWidth(),pnlHomePage.getHeight(),BufferedImage.TYPE_INT_RGB);
        pnlHomePage.printAll(img.getGraphics());
        try{
            ImageIO.write(img, "jpg", new File("user.jpg")); 
        }catch (Exception e) {
            e.printStackTrace();
          //  //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1018", JOptionPane.ERROR_MESSAGE);
        }
        try{
            conn = SQLCon.ConnectDB();
                Map <String,Object> parameters  = new HashMap<String,Object>();  
                BufferedImage image = ImageIO.read(new FileInputStream("user.jpg"));
                parameters.put("Image",  image);
           JasperDesign jd = JRXmlLoader.load("src\\reports\\statistics.jrxml");
             JRDesignQuery newQuery = new JRDesignQuery();
                    newQuery.setText("");
         jd.setQuery(newQuery);
          JasperReport jr = JasperCompileManager.compileReport(jd);
          JasperPrint jp = JasperFillManager.fillReport(jr, parameters, conn);
          JasperViewer jv = new JasperViewer(jp,false);
          jv.setTitle("Career Development and Placement Office");
          jv.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/logo jmo_1.png")));
          jv.setVisible(true); 
          jv.setExtendedState(MAXIMIZED_BOTH);
          conn.close();
        }catch(Exception e){
            e.printStackTrace();
        } 
    }
    
    void showTime(){
        new Timer(0, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                Date d = new Date();
                SimpleDateFormat s = new SimpleDateFormat("EEEE yyyy-MM-dd hh:mm:ss a");
                jLabel117.setText(s.format(d));
            }
        }).start();
    }
    public static void getCompanyList(int start,int end){
        try{
         String ssearch = "";
        String sdept ="";
        String scourse = "";
        String slevel  =  ""; 
        String getFromDate = "";
        String getToDate= "";
       if(txtFromDate7.getDate()==null || txtFromDate8.getDate()==null){
               getFromDate =  "0000-00-00";
               getToDate =  "9999-12-31";
        }else{
           getFromDate = ((JTextField)txtFromDate7.getDateEditor().getUiComponent()).getText();
           getToDate = ((JTextField)txtFromDate8.getDateEditor().getUiComponent()).getText();   
       }
        if(!home.Home.jTextField1.getText().equals("Search...")){
          ssearch = home.Home.jTextField1.getText();
       }else{
            ssearch = "";
        }
        if(jComboBox3.getSelectedItem().equals("Department (All)")){
              sdept ="";
        }else{
            sdept = jComboBox3.getSelectedItem().toString();
        }
          if(jComboBox4.getSelectedItem().equals("Course (All)")){
              scourse ="";
        }else{
            scourse = jComboBox4.getSelectedItem().toString();
        }
        
          if(jComboBox5.getSelectedItem().equals("Level (All)")){
              slevel ="";
        }else{
            slevel = jComboBox5.getSelectedItem().toString();
        }
         
         String sql = "SELECT sl.s_log_date_time as `Date & Time`,si.`s_student_no` as `Student No.`, \n" +
"concat( si.`s_lastname`,', ',si.`s_firstname`,' ',si.`s_middlename`,' ',si.`s_suffixname`) as Name,\n" +
"sc.s_course_title as Course,\n" +
"sls.s_level_title as Level,\n" +
"sl.s_log_title as Log\n" +
"FROM `student_information` si\n" +
"inner join student_records sr\n" +
"ON si.s_student_no=sr.s_information_id\n" +
"inner join student_logs sl\n" +
"ON sr.s_records_id=sl.s_records_id\n" +
"inner join student_levels sls\n" +
"on sr.s_year_level_id=sls.s_level_id\n" +
"inner join student_courses sc \n" +
"on sr.s_course_id=sc.s_course_id\n" +
"inner join  student_departments sd\n" +
"on sr.department_id=sd.department_id\n" +
"where concat(si.`s_student_no`,' ',si.`s_lastname`,' ',si.`s_firstname`,' ',si.`s_middlename`,' ',si.`s_suffixname`) LIKE '%"+ssearch+"%'\n" +
"and sc.s_course_title LIKE '%"+scourse+"%'\n" +
"and sls.s_level_title LIKE '%"+slevel+"%'\n" +
"and sd.department_title LIKE '%"+sdept+"%'\n" +
"and sl.s_log_date_time>='"+getFromDate+" 00:00:00' AND   sl.s_log_date_time < '"+getToDate+" 23:59:59'\n" +
"order by sl.s_log_date_time DESC\n" +
"LIMIT "+start+","+end+" "; 
           try{
               conn = SQLCon.ConnectDB();
              // System.out.println(sql);
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery();
            jTable2.setModel(DbUtils.resultSetToTableModel(rs));  
             LogsPanel();
             conn.close();
        }catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1059", JOptionPane.ERROR_MESSAGE);
        }
        }catch(Exception e){                  
        getDepartment();
        getLevels();
        getCourse(); 
            e.printStackTrace();
        
        }
    }
     public static void printLogs(){
        
         String ssearch = "";
        String sdept ="";
        String scourse = "";
        String slevel  =  ""; 
        String getFromDate = "";
        String getToDate= "";
       if(txtFromDate7.getDate()==null || txtFromDate8.getDate()==null){
               getFromDate =  "0000-00-00";
               getToDate =  "9999-12-31";
        }else{
           getFromDate = ((JTextField)txtFromDate7.getDateEditor().getUiComponent()).getText();
           getToDate = ((JTextField)txtFromDate8.getDateEditor().getUiComponent()).getText();   
       }
        if(!home.Home.jTextField1.getText().equals("Search...")){
          ssearch = home.Home.jTextField1.getText();
       }else{
            ssearch = "";
        }
        if(jComboBox3.getSelectedItem().equals("Department (All)")){
              sdept ="";
        }else{
            sdept = jComboBox3.getSelectedItem().toString();
        }
          if(jComboBox4.getSelectedItem().equals("Course (All)")){
              scourse ="";
        }else{
            scourse = jComboBox4.getSelectedItem().toString();
        }
        
          if(jComboBox5.getSelectedItem().equals("Level (All)")){
              slevel ="";
        }else{
            slevel = jComboBox5.getSelectedItem().toString();
        }
         
         String sql = "SELECT sl.s_log_date_time as `Date & Time`,si.`s_student_no` as `Student No.`, \n" +
"concat( si.`s_lastname`,', ',si.`s_firstname`,' ',si.`s_middlename`,' ',si.`s_suffixname`) as Name,\n" +
"sc.s_course_title as Course,\n" +
"sls.s_level_title as Level,\n" +
"sl.s_log_title as Log\n" +
"FROM `student_information` si\n" +
"inner join student_records sr\n" +
"ON si.s_student_no=sr.s_information_id\n" +
"inner join student_logs sl\n" +
"ON sr.s_records_id=sl.s_records_id\n" +
"inner join student_levels sls\n" +
"on sr.s_year_level_id=sls.s_level_id\n" +
"inner join student_courses sc \n" +
"on sr.s_course_id=sc.s_course_id\n" +
"inner join  student_departments sd\n" +
"on sr.department_id=sd.department_id\n" +
"where concat(si.`s_student_no`,' ',si.`s_lastname`,' ',si.`s_firstname`,' ',si.`s_middlename`,' ',si.`s_suffixname`) LIKE '%"+ssearch+"%'\n" +
"and sc.s_course_title LIKE '%"+scourse+"%'\n" +
"and sls.s_level_title LIKE '%"+slevel+"%'\n" +
"and sd.department_title LIKE '%"+sdept+"%'\n" +
"and sl.s_log_date_time>='"+getFromDate+" 00:00:00' AND   sl.s_log_date_time < '"+getToDate+" 23:59:59'\n" +
"order by sl.s_log_date_time DESC  "; 
           try{
               conn = SQLCon.ConnectDB();
                JasperDesign jd = JRXmlLoader.load("src\\reports\\loglist.jrxml");
             JRDesignQuery newQuery = new JRDesignQuery();
         newQuery.setText(sql);
         jd.setQuery(newQuery);
          JasperReport jr = JasperCompileManager.compileReport(jd);
          JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
           JasperViewer jv = new JasperViewer(jp,false);
          jv.setTitle("Career Development and Placement Office");
          jv.setIconImage(Toolkit.getDefaultToolkit().getImage(pnlCompanies.class.getResource("/images/logo jmo_1.png")));
          jv.setVisible(true);
          jv.setExtendedState(MAXIMIZED_BOTH); 
          conn.close();
        }catch(Exception e){       
            DC();
            e.printStackTrace(); 
        } 
    }
    
    
    
           public static void getDepartment(){
        String sql = "select department_title from student_departments where 1 ORDER by department_title ASC";
        try{
            conn = SQLCon.ConnectDB();
            jComboBox3.removeAllItems();
            jComboBox3.addItem("Department (All)");
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery();
            while(rs.next()){
      jComboBox3.addItem(rs.getString(1));
            }
            conn.close();
        }catch(Exception e){
            DC();
            e.printStackTrace();
          // //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1073", JOptionPane.ERROR_MESSAGE);
        } 
   }
      
       public static void getCourse(){  
        String sql = "SELECT  `s_course_title`  FROM `student_courses` WHERE 1 ";
        try{
            conn = SQLCon.ConnectDB();
            jComboBox4.removeAllItems();
            jComboBox4.addItem("Course (All)");
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery();
            while(rs.next()){
      jComboBox4.addItem(rs.getString(1));
            }
            conn.close();
        }catch(Exception e){
            DC();
            e.printStackTrace();
          //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1074", JOptionPane.ERROR_MESSAGE);
       } 
   }
        public static void getLevels(){ 
        String sql = "select s_level_title from student_levels where 1 ORDER by s_level_title ASC";
        try{
            conn = SQLCon.ConnectDB();
            jComboBox5.removeAllItems();
            jComboBox5.addItem("Level (All)");
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery();
            while(rs.next()){
      jComboBox5.addItem(rs.getString(1));
            }
            conn.close();
        }catch(Exception e){
            DC();
            e.printStackTrace();
             //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1075", JOptionPane.ERROR_MESSAGE);
      } 
   }
    
    
    public static void userRoleAccess(){
        if(!SystemInfo.urole.equals("1")){
            four.setVisible(false);
            seven.setVisible(false);
            eight.setVisible(false);
        }
    }
    
       public void ContactFilter() {
        txtLname10.addKeyListener(new KeyAdapter() {
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
       public void UserContactFilter() {
        txtContactNumber.addKeyListener(new KeyAdapter() {
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

     public void UserProfileDateFilter(){
         txtFromDate5.getDateEditor().addPropertyChangeListener(new PropertyChangeListener(){
         @Override
     public void propertyChange(PropertyChangeEvent e){
         if(txtFromDate5.getDate()==null || txtFromDate6.getDate()==null ){
             userProfileAuditListStart = 0;
             jLabel107.setText("");
             UserProfileFilterByDate = false;
             userProfileAuditLast = false;
       getUserProfileAuditList();
         }else{
             userProfileAuditListStart = 0;
             UserProfileFilterByDate = true;
             userProfileAuditLast = false;
             getUserProfileAuditListByDate();
         }
         listPage = 1;
            jLabel108.setText("Page: "+listPage);
     }
     });
         
          txtFromDate6.getDateEditor().addPropertyChangeListener(new PropertyChangeListener(){
         @Override
     public void propertyChange(PropertyChangeEvent e){
          if(txtFromDate5.getDate()==null || txtFromDate6.getDate()==null ){
               userProfileAuditListStart = 0;
             jLabel107.setText("");
             UserProfileFilterByDate = false;
             userProfileAuditLast = false;
                 getUserProfileAuditList();
         }else{
             userProfileAuditListStart = 0;
             UserProfileFilterByDate = true;
             userProfileAuditLast = false;
             getUserProfileAuditListByDate();
         }
            listPage = 1;
            jLabel108.setText("Page: "+listPage);
     }
     });
    }
    
    public void profileDateFilter(){
         txtFromDate1.getDateEditor().addPropertyChangeListener(new PropertyChangeListener(){
         @Override
     public void propertyChange(PropertyChangeEvent e){
         if(txtFromDate1.getDate()==null || txtFromDate2.getDate()==null ){
             profileAuditListStart = 0;
             jLabel81.setText("");
             profileFilterByDate = false;
             profileAuditLast = false;
       getProfileAuditList();
         }else{
             profileAuditListStart = 0;
             profileFilterByDate = true;
             profileAuditLast = false;
             getProfileAuditListByDate();
         }
         listPage = 1;
            jLabel104.setText("Page: "+listPage);
     }
     });
         
          txtFromDate2.getDateEditor().addPropertyChangeListener(new PropertyChangeListener(){
         @Override
     public void propertyChange(PropertyChangeEvent e){
          if(txtFromDate1.getDate()==null || txtFromDate2.getDate()==null ){
               profileAuditListStart = 0;
             jLabel81.setText("");
             profileFilterByDate = false;
             profileAuditLast = false;
                 getProfileAuditList();
         }else{
             profileAuditListStart = 0;
             profileFilterByDate = true;
             profileAuditLast = false;
             getProfileAuditListByDate();
         }
            listPage = 1;
            jLabel104.setText("Page: "+listPage);
     }
     });
    }
    
       public void AuditDateFilter(){
         txtFromDate4.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
             @Override
             public void propertyChange(PropertyChangeEvent e) {
                 if(txtFromDate4.getDate()==null || txtFromDate3.getDate()==null ){
                     auditListStart = 0;
                     jLabel82.setText("");
                     auditFilterByDate = false;
                     AuditListLast = false;
                     getAuditList();
                   
                 }else{
                     
                     auditListStart = 0;
                     auditFilterByDate = true;
                     AuditListLast = false;
                     getAuditListByDate();    
                 }  
                 listPage = 1;
            jLabel103.setText("Page: "+listPage);
             }
         });
         
          txtFromDate3.getDateEditor().addPropertyChangeListener(new PropertyChangeListener(){
         @Override
     public void propertyChange(PropertyChangeEvent e){
          if(txtFromDate4.getDate()==null || txtFromDate3.getDate()==null ){
               auditListStart = 0;
             jLabel82.setText("");
             auditFilterByDate = false;
             AuditListLast = false;
                getAuditList();   
         }else{
             auditListStart = 0;
             auditFilterByDate = true;
             AuditListLast = false;
             getAuditListByDate(); 
         } 
          listPage = 1;
            jLabel103.setText("Page: "+listPage);
     }
     });
    }
    
       public void OnSystemExit(){
         addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
               
          LookAndFeel old = UIManager.getLookAndFeel();
                try {
                    UIManager.setLookAndFeel(
                            UIManager.getSystemLookAndFeelClassName());
                } catch(ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException f) {
                    f.printStackTrace();
                }
        int selectedOption = JOptionPane.showConfirmDialog(null, "Are you sure?", "Exit",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
       try {
            UIManager.setLookAndFeel(old);
         }  catch(UnsupportedLookAndFeelException f) {
                    f.printStackTrace();
         }
        if (selectedOption==JOptionPane.YES_OPTION){
            insertExitAudit();
          
        }else{
           setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }
            }
            
        });
    }
    
    public void insertExitAudit(){
         String sql = "insert into audit (u_information_id,audit_activity,audit_description) values (?,?,?)";
        try {
            conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, SystemInfo.userid);
            pst.setString(2, am.exitSystem);
            pst.setString(3, SystemInfo.ufirstname+" "+SystemInfo.umiddlename+" "+SystemInfo.ulastname+" "+SystemInfo.usuffix);
            pst.executeUpdate();
            conn.close();
            System.exit(0);
        } catch (Exception e) {
                System.exit(0); //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1020", JOptionPane.ERROR_MESSAGE);
        }
    }
    
     
    
       public void AuditTrail(){
         
        jScrollPane6.getViewport().setBackground(Color.WHITE);
            JTableHeader Theader = jTable5.getTableHeader();
        Theader.setBackground(new Color(84,127,206)); // change the Background color
        Theader.setForeground(Color.WHITE); // change the Foreground
        Theader.setFont(new Font("Segoe UI", Font.BOLD, 14)); // font name style size
        ((DefaultTableCellRenderer)Theader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.LEFT); // center header text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
       jTable5.getColumnModel().getColumn(0).setPreferredWidth(10);
        jTable5.getColumnModel().getColumn(1).setPreferredWidth(80);
       jTable5.getColumnModel().getColumn(2).setPreferredWidth(80);
        jTable5.getColumnModel().getColumn(3).setPreferredWidth(300);
        jTable5.getColumnModel().getColumn(4).setPreferredWidth(300);
        jTable5.setDefaultEditor(Object.class, null);
    }
     
       
    
     public void ProfileRecordLog(){
         
        jScrollPane4.getViewport().setBackground(Color.WHITE);
            JTableHeader Theader = jTable3.getTableHeader();
        Theader.setBackground(new Color(84,127,206)); // change the Background color
        Theader.setForeground(Color.WHITE); // change the Foreground
        Theader.setFont(new Font("Segoe UI", Font.BOLD, 14)); // font name style size
        ((DefaultTableCellRenderer)Theader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.LEFT); // center header text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
       jTable3.getColumnModel().getColumn(0).setPreferredWidth(10);
        jTable3.getColumnModel().getColumn(1).setPreferredWidth(70);
       jTable3.getColumnModel().getColumn(2).setPreferredWidth(180);
        jTable3.getColumnModel().getColumn(3).setPreferredWidth(180);
        jTable3.setDefaultEditor(Object.class, null);
    }
     
     
    public void UsersRecordLog(){
           jComboBox2.setBackground(Color.WHITE);
            JTableHeader Theader = jTable1.getTableHeader();
        Theader.setBackground(new Color(84,127,206)); // change the Background color
        Theader.setForeground(Color.WHITE); // change the Foreground
        Theader.setFont(new Font("Segoe UI", Font.BOLD, 14)); // font name style size
        ((DefaultTableCellRenderer)Theader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.LEFT); // center header text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
       jTable1.getColumnModel().getColumn(0).setPreferredWidth(10);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(70);
       jTable1.getColumnModel().getColumn(2).setPreferredWidth(180);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(180);
        jTable1.setDefaultEditor(Object.class, null);
    }
    
    
    
    public static void LogsPanel(){
        jComboBox3.setBackground(Color.WHITE);
        jComboBox4.setBackground(Color.WHITE);
        jComboBox5.setBackground(Color.WHITE);
          jScrollPane2.getViewport().setBackground(Color.WHITE);
            JTableHeader Theader = jTable2.getTableHeader();
        Theader.setBackground(new Color(84,127,206)); // change the Background color
        Theader.setForeground(Color.WHITE); // change the Foreground
        Theader.setFont(new Font("Segoe UI", Font.BOLD, 14)); // font name style size
        ((DefaultTableCellRenderer)Theader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.LEFT); // center header text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
       jTable2.getColumnModel().getColumn(0).setPreferredWidth(30);
        jTable2.getColumnModel().getColumn(1).setPreferredWidth(10);
       jTable2.getColumnModel().getColumn(2).setPreferredWidth(100);
        jTable2.getColumnModel().getColumn(3).setPreferredWidth(150);
       jTable2.getColumnModel().getColumn(4).setPreferredWidth(10);
       jTable2.getColumnModel().getColumn(5).setPreferredWidth(100);
       
        jTable2.setDefaultEditor(Object.class, null);
    }
    
    public void UserGender(){
        jComboBox1.setBackground(Color.WHITE);
    jComboBox1.requestFocus(false);
        for (int i = 0; i < jComboBox1.getComponentCount(); i++) 
{
    if (jComboBox1.getComponent(i) instanceof JComponent) {
        ((JComponent) jComboBox1.getComponent(i)).setBorder(new EmptyBorder(0, 0,0,0));
    }


    if (jComboBox1.getComponent(i) instanceof AbstractButton) {
        ((AbstractButton) jComboBox1.getComponent(i)).setBorderPainted(false);
    }
}      
    
    }
     public void UsersPanel(){
        UserProfile.hide();
        jScrollPane3.getViewport().setBackground(Color.WHITE);
         jScrollPane5.getViewport().setBackground(Color.WHITE);
         JTableHeader Theader = jTable4.getTableHeader();
         Theader.setBackground(new Color(84, 127, 206)); // change the Background color
         Theader.setForeground(Color.WHITE); // change the Foreground
         Theader.setFont(new Font("Segoe UI", Font.BOLD, 14)); // font name style size
         ((DefaultTableCellRenderer) Theader.getDefaultRenderer())
                 .setHorizontalAlignment(JLabel.CENTER); // center header text
         DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
         centerRenderer.setHorizontalAlignment(JLabel.CENTER);
         TableColumnModel tcm = Theader.getColumnModel();
         TableColumn tc0 = tcm.getColumn(0);
         TableColumn tc2 = tcm.getColumn(2);
         TableColumn tc4 = tcm.getColumn(4);
         TableColumn tc5 = tcm.getColumn(5);
          tc0.setHeaderValue("User ID");
          tc2.setHeaderValue("Username");
          tc4.setHeaderValue("Contact no.");
          tc5.setHeaderValue("Status");
         jTable4.getColumnModel().getColumn(0).setPreferredWidth(70);
          jTable4.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
         jTable4.getColumnModel().getColumn(1).setPreferredWidth(300);
          jTable4.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
         jTable4.getColumnModel().getColumn(2).setPreferredWidth(100);
          jTable4.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
         jTable4.getColumnModel().getColumn(3).setPreferredWidth(80);
          jTable4.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
         jTable4.getColumnModel().getColumn(4).setPreferredWidth(120);
          jTable4.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
         jTable4.getColumnModel().getColumn(5).setPreferredWidth(50);
          jTable4.setDefaultEditor(Object.class, null);
          Theader.repaint();
          
    }
    
    
    public void Components(){
        pnlAddStudent1.hide();
        pnlAddStudent8.hide();
        pnlAddStudent5.hide();
        pnlAddStudent10.hide();
        pnlAddStudent14.hide();
        pnlAddStudent15.hide(); 
    }
    public void BarGraph(){
        DefaultCategoryDataset data = new DefaultCategoryDataset();
        /*
data.setValue(78, "Dept","CICT");
data.setValue(58, "Dept","CBM");
data.setValue(98, "Dept","CAS");
data.setValue(80, "Dept","PSYCH");
data.setValue(67, "Dept","CED");
data.setValue(79, "Dept","CHTM");
data.setValue(50, "Dept","CRIM");
        */
        try{
            conn = SQLCon.ConnectDB();
            String sql =    "SELECT `dept_abbr`,(select count(*) from student_records where department_id=sd.department_id and s_year_level_id=(SELECT s_level_id from student_levels where s_level_title='EMPLOYED')) as Data FROM `student_departments` sd";
        pst = conn.prepareStatement(sql);
        rs = pst.executeQuery();
        while(rs.next()){
          data.setValue(rs.getInt(2), rs.getString(1),"");
        }
        conn.close();
        }catch(Exception e){
            DC();
            e.printStackTrace();
        }
        
        
        /*
        
        data.setValue(78, "CICT","");
data.setValue(58, "CBM","");
data.setValue(98, "CAS","");
data.setValue(80, "PSYCH","");
data.setValue(67, "CED","");
data.setValue(79, "CHTM","");
data.setValue(50, "CRIM","");
*/
JFreeChart chart=ChartFactory.createBarChart("Employed/Deployed","","",data,PlotOrientation.VERTICAL, true, true, false);
chart.getTitle().setPaint(new Color(84,127,206));

CategoryPlot plot = chart.getCategoryPlot();
 //plot.getRenderer().setSeriesPaint(0,new Color(8,127,206));
 ((BarRenderer) plot.getRenderer()).setBarPainter(new StandardBarPainter());
 
chart.getPlot().setBackgroundPaint( Color.WHITE );  
plot.setOutlineVisible(false);
plot.setRangeGridlinePaint(Color.black);

  
ChartPanel panel=new ChartPanel(chart);
jPanel17.setLayout(new java.awt.GridLayout(0,1));
jPanel18.setBorder(BorderFactory.createTitledBorder(""));
jPanel18.setPreferredSize(new Dimension(2000, 450));    
jPanel18.add(panel);   
jPanel17.add(jPanel18);
		panel.setSize(2000,450);
		panel.setVisible(true);
                new DynamicPanel(jPanel17, panel);
    }
    public void PieChart(){
        DefaultPieDataset data=new DefaultPieDataset();
        
         try{
             conn = SQLCon.ConnectDB();
            String sql =    "SELECT `dept_abbr`,(select count(*) from student_information where s_department_id=sd.department_id) as Data FROM `student_departments` sd";
        pst = conn.prepareStatement(sql);
        rs = pst.executeQuery();
        while(rs.next()){
          data.setValue(rs.getString(1), rs.getInt(2));
        }
        conn.close();
        }catch(Exception e){
            DC();
            e.printStackTrace();
           // e.printStackTrace();
        }
        
        
		/*
		data.setValue("CICT", 309);
		data.setValue("CBM",290);
		data.setValue("CAS",405);
		data.setValue("PSYCH",265);
		data.setValue("CED",365);
                data.setValue("CHTM", 269);
                data.setValue("CRIM", 189);
		*/
		JFreeChart chart=ChartFactory.createPieChart("Students",data,true,true,false);
                chart.getPlot().setBackgroundPaint( Color.WHITE );   
                chart.getTitle().setPaint(new Color(84,127,206));
               
                PiePlot plot = (PiePlot) chart.getPlot();
       
        
                /*
                plot.setSectionPaint("CICT", new Color(128,34,158));
                plot.setSectionPaint("CBM", Color.YELLOW);
                plot.setSectionPaint("CHTM", Color.GREEN);
                plot.setSectionPaint("CRIM", Color.RED);
                */
                plot.setOutlineVisible(false);
                PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("{0} = {1}");
                plot.setLabelGenerator(labelGenerator);
                
                ChartPanel panel=new ChartPanel(chart);
                jPanel16.setLayout(new java.awt.GridLayout(0,1));
jPanel7.setBorder(BorderFactory.createTitledBorder(""));
jPanel7.setPreferredSize(new Dimension(300,300));    
jPanel7.add(panel);   
jPanel16.add(jPanel7);
		panel.setSize(300,300);
		panel.setVisible(true);
                new DynamicPanel(jPanel16, panel);
                
                
		/*panel.setSize(440,400);
		panel.setVisible(true);
		//frame.setLocationRelativeTo(null);
                new DynamicPanel(jPanel16, panel);
                */
    }
    
    
    public void getImage(){ 
          String sql = "select u_picture from users where u_information_id='"+SystemInfo.userid+"' ";
         try{
             conn = SQLCon.ConnectDB();
               pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                 if(rs.next()){
                     SystemInfo.img = rs.getBytes(1);
                    
         
                 }
                 conn.close();
         }catch(Exception e){
             
             DC();
             e.printStackTrace();
                        //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e,"Error 1011",JOptionPane.ERROR_MESSAGE);
     }
    }
    
    public void Homepage(){
        
        
         DynamicContent.hide();
        pnlHomePage.show();
        UserProfileInfo.hide();
        pnlLogs.hide();
        AuditTrail.hide();
        Developers.hide();
        pnlUsers.hide();
       HideSearch();
        pnlFooterAction.setVisible(true);
        
        
        
         
            this.one.setSelected(true);
            this.one.setColorNormal(new Color(41,57,80));
            this.one.setColorHover(new Color(41,57,80));
            this.one.setColorPressed(new Color(41,57,80));
            this.one.setBorder(bordePressed);
    }

     private void setColor(JPanel pane)
    {
        pane.setBackground(new Color(41,57,80));
    }
    
    private void resetColor(JPanel [] pane, JPanel [] indicators)
    {
        for(int i=0;i<pane.length;i++){
           pane[i].setBackground(new Color(23,35,51));
           
        } for(int i=0;i<indicators.length;i++){
           indicators[i].setOpaque(false);           
        }
        
        
    }
    
    public static void ShowSearch(){
        pnlSearch.show();
        pnlHeader.revalidate();
        pnlHeader.repaint();
    }
     public static void HideSearch(){
        pnlSearch.hide();
        pnlHeader.revalidate();
        pnlHeader.repaint();
    }
   
     public void insertLogoutAudit(){
       
        String sql = "insert into audit (u_information_id,audit_activity,audit_description) values (?,?,?)";
        try {
            conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, SystemInfo.userid);
            pst.setString(2, am.logoutAccount);
            pst.setString(3, SystemInfo.ufirstname+" "+SystemInfo.umiddlename+" "+SystemInfo.ulastname+" "+SystemInfo.usuffix);
            pst.executeUpdate(); 
            Zz s   = new Zz();
                s.login = false;
           s.Load();
           this.dispose();
           conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1013", JOptionPane.ERROR_MESSAGE);
        }
    
     }
     
     public void processUserProfileUpdate(){
            updateprofile = false;
         updateaccount = true;
         String upfname = txtFname.getText().replace("'", "''");
         String upmname =  txtMname.getText().replace("'", "''");
         String uplname =  txtLname.getText().replace("'", "''");
         String upsuffix =  txtSuffix.getText().replace("'", "''");
         String upuname =  txtUsername.getText().replace("'", "''");
         String upcnum =  txtContactNumber.getText().replace("'", "''");
         String upemail =  txtEmail.getText().replace("'", "''");
         String upgender = "";
         if(jComboBox1.getSelectedItem().equals("Male")){
             upgender = "0";
         }else if(jComboBox1.getSelectedItem().equals("Female")){
             upgender = "1";
         }
         String upvalid = "";
         if(jCheckBox1.isSelected()){
             upvalid = "1";
         }else{
              upvalid = "0";
         }
         String uprole = "";
         if(jComboBox2.getSelectedItem().equals("Admin")){
             uprole = "1";
         }else{
             uprole = "0";
         }
         
          UpdateAccount.StartUpdate(
                  SelectedUserAccount.userid,
                  upfname,upmname,uplname,upsuffix,upgender,upcnum,upemail,uprole,upuname,
                  SelectedUserAccount.upassword,
                  upvalid,
                  SelectedUserAccount.ustatus
          );
            if(changeAvatar){
           updateImage();
       }else{
           insertUpdateAudit();
       }
         
     }
     public void processProfileUpdate() {
     
        updateprofile = true;
         updateaccount = false;
       String updatecnum = txtLname10.getText().replace("'", "''");
       String updateemail  = txtLname11.getText().replace("'", "''");
       String updatepass = "";
       if(!jPasswordField1.getText().equals("Change Password") || !jPasswordField2.getText().equals("Repeat Password")){
             if (jPasswordField1.getText().equals("Change Password")) {
            JOptionPane.showMessageDialog(null, "Enter your password...");
            jPasswordField1.requestFocus();
        } else if (jPasswordField2.getText().equals("Repeat Password")) {
            JOptionPane.showMessageDialog(null, "Repeat your password...");
            jPasswordField2.requestFocus();
        } else if (!jPasswordField1.getText().equals(jPasswordField2.getText())) {
            JOptionPane.showMessageDialog(null, "Password is not matched...");
            jPasswordField2.requestFocus();
        } else if (jPasswordField1.getText().length() <= 5) {
            JOptionPane.showMessageDialog(null, "Password must 6 characters and above");
            jPasswordField1.requestFocus();
        }else{
              updatepass = BCrypt.hashpw(jPasswordField1.getText(), BCrypt.gensalt(12));
               UpdateAccount.StartUpdate(
                 SystemInfo.userid,
                 SystemInfo.ufirstname, 
                 SystemInfo.umiddlename, 
                 SystemInfo.ulastname, 
                 SystemInfo.usuffix,
                 SystemInfo.gender,
                 updatecnum,
                 updateemail,
                 SystemInfo.urole,
                 SystemInfo.uusername,
                 updatepass,
                 SystemInfo.uvalidate,
                 SystemInfo.ustatus);
       if(changeAvatar){
           updateImage();
       }else{
           insertUpdateAudit();
       }
          jPasswordField1.setText("Change Password"); 
   jPasswordField1.setEchoChar((char)0);
    jPasswordField1.setFont(new java.awt.Font("Segoe UI", 0, 14));
     jPasswordField2.setText("Repeat Password"); 
   jPasswordField2.setEchoChar((char)0);
    jPasswordField2.setFont(new java.awt.Font("Segoe UI", 0, 14));
    
    
        }
       }else{
           updatepass = SystemInfo.upassword;
            UpdateAccount.StartUpdate(
                 SystemInfo.userid,
                 SystemInfo.ufirstname, 
                 SystemInfo.umiddlename, 
                 SystemInfo.ulastname, 
                 SystemInfo.usuffix,
                 SystemInfo.gender,
                 updatecnum,
                 updateemail,
                 SystemInfo.urole,
                 SystemInfo.uusername,
                 updatepass,
                 SystemInfo.uvalidate,
                 SystemInfo.ustatus);
       if(changeAvatar){
           updateImage();
       }else{
           insertUpdateAudit();
       } 
       }
       
     
        
     }
     
     public void updateImage(){
         String updateid="";
         if(updateprofile){
             updateid = SystemInfo.userid;
         }else if(updateaccount){
             updateid = SelectedUserAccount.userid;
         }
         
         try{
             conn = SQLCon.ConnectDB();
            String sql = "UPDATE `users` SET u_picture=? WHERE u_information_id='" + updateid + "'";
          pst = conn.prepareStatement(sql);
          InputStream is = new FileInputStream(new File("user.png"));
          pst.setBlob(1, is);
          pst.executeUpdate();
         changeAvatar = false;
         insertUpdateAudit();
          jLabel1.setIcon(ResizeImage(s));
          conn.close();
         }catch (Exception e) {
             DC();
             e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1015", JOptionPane.ERROR_MESSAGE);
        }
        
     }
     
     public void insertUpdateAudit(){
             String updateid="";
             String updateMessage = "";
             String updateName = "";
         if(updateprofile){
             updateid = SystemInfo.userid;
             updateMessage = am.updateOwnAccount;
             updateName = SystemInfo.ufirstname+" "+SystemInfo.umiddlename+" "+SystemInfo.ulastname+" "+SystemInfo.usuffix;
         }else if(updateaccount){
             updateid = SystemInfo.userid;
             updateMessage = am.updateAccount;
             updateName = SelectedUserAccount.ufirstname+" "+SelectedUserAccount.umiddlename+" "+SelectedUserAccount.ulastname+" "+SelectedUserAccount.usuffix;
         }
         
        String sql = "insert into audit (u_information_id,audit_activity,audit_description) values (?,?,?)";
        try {
            conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, updateid);
            pst.setString(2, updateMessage);
            pst.setString(3, updateName);
            pst.executeUpdate();
      if(updateprofile){
           profileFilterByDate = false;
         txtFromDate1.setDate(null);
         txtFromDate2.setDate(null);
         jLabel81.setText(null); 
        getProfileAuditList();
      }else if(updateaccount){
         UserProfileFilterByDate = false;
         txtFromDate5.setDate(null);
         txtFromDate6.setDate(null);
         jLabel107.setText(null);
        getUserProfileAuditList();
      }
       
            updateprofile = false;
         updateaccount = false;
        UIManagers.getNewUI();
             JOptionPane.showMessageDialog(null, "Account successfully updated..."); 
        UIManagers.applyOldUI();
         conn.close();
        } catch (Exception e) {
            DC();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1016", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
        }
     
     }
        public void getAuditList(){
         String sql = "SELECT a.audit_id as `Log ID`,a.date_time as `Date and Time`,u.u_username as `Username`,a.audit_activity as `Activity`,a.audit_description as `Description` FROM audit a \n" +
"inner join users u\n" +
"on a.u_information_id=u.u_information_id\n" +
"where 1 ORDER BY `date_time` DESC LIMIT "+auditListStart+","+auditlistEnd+"";
            try{
                conn = SQLCon.ConnectDB();
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery();
            jTable5.setModel(DbUtils.resultSetToTableModel(rs)); 
            AuditTrail();
            conn.close();
        }catch (Exception e) { 
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1017", JOptionPane.ERROR_MESSAGE);
        }
     }
     public void printAuditList(){
             try{
                 conn = SQLCon.ConnectDB();
         String sql = "SELECT  a.date_time as `Date and Time`,u.u_username as `Username`,a.audit_activity as `Activity`,a.audit_description as `Description` FROM audit a \n" +
"inner join users u\n" +
"on a.u_information_id=u.u_information_id\n" +
"where 1 ORDER BY `date_time` DESC"; 
                  JasperDesign jd = JRXmlLoader.load("src\\reports\\historylist.jrxml");
             JRDesignQuery newQuery = new JRDesignQuery();
         newQuery.setText(sql);
         jd.setQuery(newQuery);
          JasperReport jr = JasperCompileManager.compileReport(jd);
          JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
          JasperViewer jv = new JasperViewer(jp,false);
          jv.setTitle("Career Development and Placement Office");
          jv.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/logo jmo_1.png")));
          jv.setVisible(true);
          jv.setExtendedState(MAXIMIZED_BOTH);
          conn.close();
        }catch (Exception e) {
          e.printStackTrace();
        }
     }
     
     public void printUserList(){
         String str = "";
         if(jTextField1.getText().equals("Search...")){
              str = "";
         }else{
         str = jTextField1.getText();
         }
          String sql = "SELECT `u_information_id` as `User ID`, concat(`u_firstname`,' ',`u_middlename`,' ',`u_lastname`,' ',`u_suffixname`) as Name, `u_username` as Username, \n" +
"(SELECT gender FROM gender where id=users.u_gender) as Gender, \n" +
"`u_phone_number` as `Contact no.`\n" +
"FROM users \n" +
"where `u_information_id` != 0 and concat_ws(`u_firstname`,' ',`u_middlename`,' ',`u_lastname`,' ',`u_suffixname`,' ',`u_username`) like '%"+str+"%'\n" +
"order by `u_information_id` DESC";
           try{
               conn = SQLCon.ConnectDB();
                 JasperDesign jd = JRXmlLoader.load("src\\reports\\userlist.jrxml");
             JRDesignQuery newQuery = new JRDesignQuery();
         newQuery.setText(sql);
         jd.setQuery(newQuery);
          JasperReport jr = JasperCompileManager.compileReport(jd);
          JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
          JasperViewer jv = new JasperViewer(jp,false);
          jv.setTitle("Career Development and Placement Office");
          jv.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/logo jmo_1.png")));
          jv.setVisible(true);
          jv.setExtendedState(MAXIMIZED_BOTH);
          conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
          
     }
        public void getAuditListByDate(){ 
             String getFromDate = ((JTextField)txtFromDate4.getDateEditor().getUiComponent()).getText();
          String getToDate = ((JTextField)txtFromDate3.getDateEditor().getUiComponent()).getText();  
         String sql = "SELECT a.audit_id as `Log ID`,a.date_time as `Date and Time`,u.u_username as `Username`,a.audit_activity as `Activity`,a.audit_description as `Description` FROM audit a \n" +
"inner join users u\n" +
"on a.u_information_id=u.u_information_id\n" +
"where 1 and date_time>='"+getFromDate+" 00:00:00' AND  date_time < '"+getToDate+" 23:59:59' ORDER BY `date_time` DESC LIMIT "+auditListStart+","+auditlistEnd+"";
            try{
                conn = SQLCon.ConnectDB();
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery();
            jTable5.setModel(DbUtils.resultSetToTableModel(rs)); 
            AuditTrail();
            jLabel82.setText("<html>Result between <font color='blue'><strong>"+getFromDate+"</strong></font> and <font color='blue'><strong>"+getToDate+"</strong></font></html>");
       conn.close();
            }catch (Exception e) {
                DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1017", JOptionPane.ERROR_MESSAGE);
        }
     }
      public void printAuditListByDate(){ 
             String getFromDate = ((JTextField)txtFromDate4.getDateEditor().getUiComponent()).getText();
          String getToDate = ((JTextField)txtFromDate3.getDateEditor().getUiComponent()).getText();  
         String sql = "SELECT a.date_time as `Date and Time`,u.u_username as `Username`,a.audit_activity as `Activity`,a.audit_description as `Description` FROM audit a \n" +
"inner join users u\n" +
"on a.u_information_id=u.u_information_id\n" +
"where 1 and date_time>='"+getFromDate+" 00:00:00' AND  date_time < '"+getToDate+" 23:59:59' ORDER BY `date_time` ";
            try{
                conn = SQLCon.ConnectDB();
                 JasperDesign jd = JRXmlLoader.load("src\\reports\\historylist.jrxml");
             JRDesignQuery newQuery = new JRDesignQuery();
         newQuery.setText(sql);
         jd.setQuery(newQuery);
          JasperReport jr = JasperCompileManager.compileReport(jd);
          JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
          JasperViewer jv = new JasperViewer(jp,false);
          jv.setTitle("Career Development and Placement Office");
          jv.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/logo jmo_1.png")));
          jv.setVisible(true);
          jv.setExtendedState(MAXIMIZED_BOTH);
          conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
     }
          public void getProfileAuditList(){
         String sql = "SELECT a.audit_id as `Log ID`,a.date_time as `Date and Time`,a.audit_activity as `Activity`,a.audit_description as `Description` FROM audit a \n" +
"inner join users u\n" +
"on a.u_information_id=u.u_information_id\n" +
"where u.u_information_id='"+SystemInfo.userid+"' ORDER BY `date_time` DESC LIMIT "+profileAuditListStart+","+auditlistEnd+"";
            try{
                conn = SQLCon.ConnectDB();
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery();
            jTable3.setModel(DbUtils.resultSetToTableModel(rs)); 
            ProfileRecordLog();
            conn.close();
        }catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1018", JOptionPane.ERROR_MESSAGE);
        }
     }
             public void printProfileAuditList(){
         String sql = "SELECT  a.date_time as `Date and Time`,a.audit_activity as `Activity`,a.audit_description as `Description` FROM audit a \n" +
"inner join users u\n" +
"on a.u_information_id=u.u_information_id\n" +
"where u.u_information_id='"+SystemInfo.userid+"' ORDER BY `date_time` DESC";
            try{
                conn = SQLCon.ConnectDB();
                try{
                 byte[] fileBytes;
        String query = "select u_picture from users where u_information_id='"+SystemInfo.userid+"'"; 
              pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            if (rs.next()) {
                String dir = "";
                String pdf = "user.jpg";  
                fileBytes = rs.getBytes(1);
                OutputStream targetFile = new FileOutputStream(dir+pdf);
                targetFile.write(fileBytes);
                targetFile.close();   
                }  
                }catch(Exception e){
                    e.printStackTrace();
                }
               
                Map <String,Object> parameters  = new HashMap<String,Object>(); 
                parameters.put("UserID", SystemInfo.userid);
                parameters.put("FullName", SystemInfo.ufirstname+" "+SystemInfo.ulastname);
                parameters.put("Username", SystemInfo.uusername);
                parameters.put("Contact", SystemInfo.ucontactnum);
                parameters.put("Email", SystemInfo.uemail);  
                BufferedImage image = ImageIO.read(new FileInputStream("user.jpg"));
                parameters.put("Image",  image);
           JasperDesign jd = JRXmlLoader.load("src\\reports\\userrecord.jrxml");
             JRDesignQuery newQuery = new JRDesignQuery();
                    newQuery.setText(sql);
         jd.setQuery(newQuery);
          JasperReport jr = JasperCompileManager.compileReport(jd);
          JasperPrint jp = JasperFillManager.fillReport(jr, parameters, conn);
          JasperViewer jv = new JasperViewer(jp,false);
          jv.setTitle("Career Development and Placement Office");
          jv.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/logo jmo_1.png")));
          jv.setVisible(true); 
          jv.setExtendedState(MAXIMIZED_BOTH);
          conn.close();
        }catch (Exception e) {
            e.printStackTrace();
          //  //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1018", JOptionPane.ERROR_MESSAGE);
        }
     } public void printProfileAuditListByDate(){
            String getFromDate = ((JTextField)txtFromDate1.getDateEditor().getUiComponent()).getText();
          String getToDate = ((JTextField)txtFromDate2.getDateEditor().getUiComponent()).getText();  
         String sql = "SELECT a.audit_id as `Log ID`,a.date_time as `Date and Time`,a.audit_activity as `Activity`,a.audit_description as `Description` FROM audit a \n" +
"inner join users u\n" +
"on a.u_information_id=u.u_information_id\n" +
"where u.u_information_id='"+SystemInfo.userid+"' and date_time>='"+getFromDate+" 00:00:00' AND  date_time < '"+getToDate+" 23:59:59' ORDER BY `date_time` DESC  ";
            try{
                conn = SQLCon.ConnectDB();
                 try{
                 byte[] fileBytes;
        String query = "select u_picture from users where u_information_id='"+SystemInfo.userid+"'"; 
              pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            if (rs.next()) {
                String dir = "";
                String pdf = "user.jpg";  
                fileBytes = rs.getBytes(1);
                OutputStream targetFile = new FileOutputStream(dir+pdf);
                targetFile.write(fileBytes);
                targetFile.close();   
                }  
                }catch(Exception e){
                    e.printStackTrace();
                }
               
                Map <String,Object> parameters  = new HashMap<String,Object>(); 
                parameters.put("UserID", SystemInfo.userid);
                parameters.put("FullName", SystemInfo.ufirstname+" "+SystemInfo.ulastname);
                parameters.put("Username", SystemInfo.uusername);
                parameters.put("Contact", SystemInfo.ucontactnum);
                parameters.put("Email", SystemInfo.uemail);  
                BufferedImage image = ImageIO.read(new FileInputStream("user.jpg"));
                parameters.put("Image",  image);
           JasperDesign jd = JRXmlLoader.load("src\\reports\\userrecord.jrxml");
             JRDesignQuery newQuery = new JRDesignQuery();
         newQuery.setText(sql);
         jd.setQuery(newQuery);
          JasperReport jr = JasperCompileManager.compileReport(jd);
          JasperPrint jp = JasperFillManager.fillReport(jr, parameters, conn);
          JasperViewer jv = new JasperViewer(jp,false); 
          jv.setTitle("Career Development and Placement Office");
          jv.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/logo jmo_1.png")));
          jv.setVisible(true);
          jv.setExtendedState(MAXIMIZED_BOTH);
         // JasperViewer.viewReport(jp,false);
         conn.close();
        }catch (Exception e) {
          e.printStackTrace();
        }
     }
            public void getProfileAuditListByDate(){
            String getFromDate = ((JTextField)txtFromDate1.getDateEditor().getUiComponent()).getText();
          String getToDate = ((JTextField)txtFromDate2.getDateEditor().getUiComponent()).getText();  
         String sql = "SELECT a.audit_id as `Log ID`,a.date_time as `Date and Time`,a.audit_activity as `Activity`,a.audit_description as `Description` FROM audit a \n" +
"inner join users u\n" +
"on a.u_information_id=u.u_information_id\n" +
"where u.u_information_id='"+SystemInfo.userid+"' and date_time>='"+getFromDate+" 00:00:00' AND  date_time < '"+getToDate+" 23:59:59' ORDER BY `date_time` DESC LIMIT "+profileAuditListStart+","+auditlistEnd+"";
            try{
                conn = SQLCon.ConnectDB();
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery();
            jTable3.setModel(DbUtils.resultSetToTableModel(rs)); 
            ProfileRecordLog();
            jLabel81.setText("<html>Result between <font color='blue'><strong>"+getFromDate+"</strong></font> and <font color='blue'><strong>"+getToDate+"</strong></font></html>");
       conn.close();
        }catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1023", JOptionPane.ERROR_MESSAGE);
        }
     }
              public void getUserProfileAuditList(){
         String sql = "SELECT a.audit_id as `Log ID`,a.date_time as `Date and Time`,a.audit_activity as `Activity`,a.audit_description as `Description` FROM audit a \n" +
"inner join users u\n" +
"on a.u_information_id=u.u_information_id\n" +
"where u.u_information_id='"+SelectedUserAccount.userid+"' ORDER BY `date_time` DESC LIMIT "+userProfileAuditListStart+","+auditlistEnd+"";
            try{
                conn = SQLCon.ConnectDB();
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery();
            jTable1.setModel(DbUtils.resultSetToTableModel(rs)); 
            UsersRecordLog();
            conn.close();
        }catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1025", JOptionPane.ERROR_MESSAGE);
        }
     }
           public void printUserProfileAuditList(){
         String sql = "SELECT a.audit_id as `Log ID`,a.date_time as `Date and Time`,a.audit_activity as `Activity`,a.audit_description as `Description` FROM audit a \n" +
"inner join users u\n" +
"on a.u_information_id=u.u_information_id\n" +
"where u.u_information_id='"+SelectedUserAccount.userid+"' ORDER BY `date_time` DESC ";
            try{
                conn = SQLCon.ConnectDB();
            try{
                 byte[] fileBytes;
        String query = "select u_picture from users where u_information_id='"+SelectedUserAccount.userid+"'"; 
              pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            if (rs.next()) {
                String dir = "";
                String pdf = "user.jpg";  
                fileBytes = rs.getBytes(1);
                OutputStream targetFile = new FileOutputStream(dir+pdf);
                targetFile.write(fileBytes);
                targetFile.close();   
                }  
                }catch(Exception e){
                    e.printStackTrace();
                }
               
                Map <String,Object> parameters  = new HashMap<String,Object>(); 
                parameters.put("UserID", SelectedUserAccount.userid);
                parameters.put("FullName", SelectedUserAccount.ufirstname+" "+SelectedUserAccount.ulastname);
                parameters.put("Username", SelectedUserAccount.uusername);
                parameters.put("Contact", SelectedUserAccount.ucontactnum);
                parameters.put("Email", SelectedUserAccount.uemail);  
                BufferedImage image = ImageIO.read(new FileInputStream("user.jpg"));
                parameters.put("Image",  image);
           JasperDesign jd = JRXmlLoader.load("src\\reports\\userrecord.jrxml");
             JRDesignQuery newQuery = new JRDesignQuery();
                    newQuery.setText(sql);
         jd.setQuery(newQuery);
          JasperReport jr = JasperCompileManager.compileReport(jd);
          JasperPrint jp = JasperFillManager.fillReport(jr, parameters, conn);
          JasperViewer jv = new JasperViewer(jp,false);
          jv.setTitle("Career Development and Placement Office");
          jv.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/logo jmo_1.png")));
          jv.setVisible(true); 
          jv.setExtendedState(MAXIMIZED_BOTH);
          conn.close();
        }catch (Exception e) {
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1025", JOptionPane.ERROR_MESSAGE);
        }
     }
           public void printUserProfileAuditListByDate(){
            String getFromDate = ((JTextField)txtFromDate5.getDateEditor().getUiComponent()).getText();
          String getToDate = ((JTextField)txtFromDate6.getDateEditor().getUiComponent()).getText();  
         String sql = "SELECT a.audit_id as `Log ID`,a.date_time as `Date and Time`,a.audit_activity as `Activity`,a.audit_description as `Description` FROM audit a \n" +
"inner join users u\n" +
"on a.u_information_id=u.u_information_id\n" +
"where u.u_information_id='"+SelectedUserAccount.userid+"' and date_time>='"+getFromDate+" 00:00:00' AND  date_time < '"+getToDate+" 23:59:59' ORDER BY `date_time` DESC";
            try{
                conn = SQLCon.ConnectDB();
            try{
                 byte[] fileBytes;
        String query = "select u_picture from users where u_information_id='"+SelectedUserAccount.userid+"'"; 
              pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            if (rs.next()) {
                String dir = "";
                String pdf = "user.jpg";  
                fileBytes = rs.getBytes(1);
                OutputStream targetFile = new FileOutputStream(dir+pdf);
                targetFile.write(fileBytes);
                targetFile.close();   
                }  
                }catch(Exception e){
                    e.printStackTrace();
                }
               
                Map <String,Object> parameters  = new HashMap<String,Object>(); 
                parameters.put("UserID", SelectedUserAccount.userid);
                parameters.put("FullName", SelectedUserAccount.ufirstname+" "+SelectedUserAccount.ulastname);
                parameters.put("Username", SelectedUserAccount.uusername);
                parameters.put("Contact", SelectedUserAccount.ucontactnum);
                parameters.put("Email", SelectedUserAccount.uemail);  
                BufferedImage image = ImageIO.read(new FileInputStream("user.jpg"));
                parameters.put("Image",  image);
           JasperDesign jd = JRXmlLoader.load("src\\reports\\userrecord.jrxml");
             JRDesignQuery newQuery = new JRDesignQuery();
                    newQuery.setText(sql);
         jd.setQuery(newQuery);
          JasperReport jr = JasperCompileManager.compileReport(jd);
          JasperPrint jp = JasperFillManager.fillReport(jr, parameters, conn);
          JasperViewer jv = new JasperViewer(jp,false);
          jv.setTitle("Career Development and Placement Office");
          jv.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/logo jmo_1.png")));
          jv.setVisible(true); 
          jv.setExtendedState(MAXIMIZED_BOTH);
          conn.close();
        }catch (Exception e) {
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1026", JOptionPane.ERROR_MESSAGE);
        }
     }
            public void getUserProfileAuditListByDate(){
            String getFromDate = ((JTextField)txtFromDate5.getDateEditor().getUiComponent()).getText();
          String getToDate = ((JTextField)txtFromDate6.getDateEditor().getUiComponent()).getText();  
         String sql = "SELECT a.audit_id as `Log ID`,a.date_time as `Date and Time`,a.audit_activity as `Activity`,a.audit_description as `Description` FROM audit a \n" +
"inner join users u\n" +
"on a.u_information_id=u.u_information_id\n" +
"where u.u_information_id='"+SelectedUserAccount.userid+"' and date_time>='"+getFromDate+" 00:00:00' AND  date_time < '"+getToDate+" 23:59:59' ORDER BY `date_time` DESC LIMIT "+userProfileAuditListStart+","+auditlistEnd+"";
            try{
                conn = SQLCon.ConnectDB();
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery();
            jTable1.setModel(DbUtils.resultSetToTableModel(rs)); 
            UsersRecordLog();
            jLabel107.setText("<html>Result between <font color='blue'><strong>"+getFromDate+"</strong></font> and <font color='blue'><strong>"+getToDate+"</strong></font></html>");
       conn.close();
        }catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1026", JOptionPane.ERROR_MESSAGE);
        }
     }
            
            
           public void UsersListFunction(){
                 int row =  jTable4.getSelectedRow(); 
        SelectedUserAccount.userid = jTable4.getModel().getValueAt(row, 0).toString();
        SelectedUserAccount.uusername = jTable4.getModel().getValueAt(row, 2).toString();
        String getGender = jTable4.getModel().getValueAt(row, 3).toString();
         SelectedUserAccount.ucontactnum =   jTable4.getModel().getValueAt(row, 4).toString();
        if(getGender.equals("Male")){
        SelectedUserAccount.gender = "0";
        }else{
        SelectedUserAccount.gender = "1";
        }
        
         Boolean checked = (Boolean) jTable4.getValueAt(row, 5);
         String userValidate = "0";
                if (checked) {
                    userValidate = "1";
                   // SelectedUserAccount.uvalidate = "1";  
                   jLabel102.setText("Activated");
                } else {
                    userValidate = "0";
                  //  SelectedUserAccount.uvalidate = "0";  
                  jLabel102.setText("Not Activated");
                }
                if(!userValidate.equals(SelectedUserAccount.uvalidate)){
                      UpdateAccountStatus.StartUpdate(SelectedUserAccount.userid, userValidate);
                }
               
                    GetUserInfo.getPofileInfo(SelectedUserAccount.userid);
           
                
                
                 
        
        jLabel9.setText("Date Registered: "+SelectedUserAccount.udateadded);
        jLabel35.setText(SelectedUserAccount.userid);
       // jLabel36.setText(SystemInfo.ufirstname+" "+SystemInfo.umiddlename+" "+SystemInfo.ulastname+" "+SystemInfo.usuffix);
        jLabel94.setText(SelectedUserAccount.ufirstname);
        jLabel95.setText(SelectedUserAccount.umiddlename);
        jLabel96.setText(SelectedUserAccount.ulastname);
        jLabel97.setText(SelectedUserAccount.usuffix);
        if(SelectedUserAccount.gender.equals("0")){
            jLabel98.setText("Male");
        }else{
             jLabel98.setText("Female");
        }
        jLabel99.setText(SelectedUserAccount.uusername);
        
        jLabel100.setText(SelectedUserAccount.ucontactnum);
        jLabel101.setText(SelectedUserAccount.uemail);
         
        
       try{
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(SelectedUserAccount.img).getImage().getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(), Image.SCALE_SMOOTH));
                jLabel1.setIcon(imageIcon); 
        }catch(Exception e){
            jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Name_125px.png")));
            System.out.println("No Image\n"+e);
        }  
       
             if(jTable4.getSelectionModel().isSelectionEmpty()){
              changePass.hide();
        }else{
              changePass.show();
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
        pnlFooter = new javax.swing.JPanel();
        pnlFooterAction = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        pnlHeader = new javax.swing.JPanel();
        jLabel114 = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        jLabel117 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        pnlSearch = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        pnlSideNav = new javax.swing.JPanel();
        one = new sidebutton.RSButtonMetro();
        two = new sidebutton.RSButtonMetro();
        three = new sidebutton.RSButtonMetro();
        four = new sidebutton.RSButtonMetro();
        five = new sidebutton.RSButtonMetro();
        six = new sidebutton.RSButtonMetro();
        seven = new sidebutton.RSButtonMetro();
        eight = new sidebutton.RSButtonMetro();
        jLabel2 = new javax.swing.JLabel();
        five1 = new sidebutton.RSButtonMetro();
        pnlContent = new javax.swing.JPanel();
        pnlUsers = new javax.swing.JPanel();
        UserList = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel109 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        changePass = new javax.swing.JPanel();
        jButton11 = new javax.swing.JButton();
        jPasswordField3 = new javax.swing.JPasswordField();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jLabel47 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        UserProfile = new javax.swing.JPanel();
        pnlStudentPersonalInfo1 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel39 = new javax.swing.JLabel();
        txtFname = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel40 = new javax.swing.JLabel();
        txtMname = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel42 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel43 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        txtLname = new javax.swing.JTextField();
        jSeparator11 = new javax.swing.JSeparator();
        txtSuffix = new javax.swing.JTextField();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel48 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jSeparator9 = new javax.swing.JSeparator();
        txtContactNumber = new javax.swing.JTextField();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel49 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton9 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel105 = new javax.swing.JLabel();
        txtFromDate5 = new com.toedter.calendar.JDateChooser();
        jLabel106 = new javax.swing.JLabel();
        txtFromDate6 = new com.toedter.calendar.JDateChooser();
        jLabel107 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        UserProfileInfo = new javax.swing.JPanel();
        pnlStudentPersonalInfo2 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel57 = new javax.swing.JLabel();
        jSeparator15 = new javax.swing.JSeparator();
        jLabel59 = new javax.swing.JLabel();
        jSeparator17 = new javax.swing.JSeparator();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jSeparator20 = new javax.swing.JSeparator();
        txtLname10 = new javax.swing.JTextField();
        jSeparator21 = new javax.swing.JSeparator();
        jLabel64 = new javax.swing.JLabel();
        txtLname11 = new javax.swing.JTextField();
        jSeparator22 = new javax.swing.JSeparator();
        jLabel65 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jSeparator23 = new javax.swing.JSeparator();
        jLabel66 = new javax.swing.JLabel();
        jPasswordField2 = new javax.swing.JPasswordField();
        jSeparator24 = new javax.swing.JSeparator();
        jLabel69 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        txtFromDate1 = new com.toedter.calendar.JDateChooser();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        txtFromDate2 = new com.toedter.calendar.JDateChooser();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        pnlLogs = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jComboBox5 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel110 = new javax.swing.JLabel();
        txtFromDate7 = new com.toedter.calendar.JDateChooser();
        jLabel111 = new javax.swing.JLabel();
        txtFromDate8 = new com.toedter.calendar.JDateChooser();
        jLabel112 = new javax.swing.JLabel();
        jLabel113 = new javax.swing.JLabel();
        pnlHomePage = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        DynamicContent = new javax.swing.JPanel();
        navHeaderHolder = new javax.swing.JPanel();
        StudentsNav = new javax.swing.JPanel();
        pnlListStudent = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        pnlAddStudent2 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        pnlAddStudent = new javax.swing.JPanel();
        pnlAddStudent1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        UsersNav = new javax.swing.JPanel();
        pnlListStudent1 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        pnlAddStudent3 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        pnlAddStudent4 = new javax.swing.JPanel();
        pnlAddStudent5 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        CompaniesNav = new javax.swing.JPanel();
        pnlListCompany = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        pnlAddStudent6 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        pnlAddCompany = new javax.swing.JPanel();
        pnlAddStudent8 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        OptionsNav = new javax.swing.JPanel();
        StudentOptions = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        pnlAddStudent7 = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        InternshipOptions = new javax.swing.JPanel();
        pnlAddStudent10 = new javax.swing.JPanel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        CompanyOptions = new javax.swing.JPanel();
        pnlAddStudent14 = new javax.swing.JPanel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        AccessibilityOptions = new javax.swing.JPanel();
        pnlAddStudent15 = new javax.swing.JPanel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnlDynamic = new javax.swing.JPanel();
        AuditTrail = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jLabel82 = new javax.swing.JLabel();
        txtFromDate3 = new com.toedter.calendar.JDateChooser();
        jLabel83 = new javax.swing.JLabel();
        txtFromDate4 = new com.toedter.calendar.JDateChooser();
        jLabel86 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        Developers = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel116 = new javax.swing.JLabel();
        jLabel122 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel128 = new javax.swing.JLabel();
        jLabel125 = new javax.swing.JLabel();
        jLabel126 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jLabel124 = new javax.swing.JLabel();
        jLabel132 = new javax.swing.JLabel();
        jLabel133 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jLabel141 = new javax.swing.JLabel();
        jLabel129 = new javax.swing.JLabel();
        jLabel149 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        jLabel146 = new javax.swing.JLabel();
        jLabel147 = new javax.swing.JLabel();
        jLabel148 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Internship Management System");
        setMinimumSize(new java.awt.Dimension(1300, 700));

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setMinimumSize(new java.awt.Dimension(1, 0));
        jPanel1.setPreferredSize(new java.awt.Dimension(1200, 656));

        pnlFooter.setBackground(new java.awt.Color(41, 57, 80));
        pnlFooter.setPreferredSize(new java.awt.Dimension(950, 50));

        pnlFooterAction.setBackground(new java.awt.Color(41, 57, 80));

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_PDF_32px.png"))); // NOI18N
        jLabel12.setToolTipText("Save as PDF");
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel12MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel12MouseExited(evt);
            }
        });

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Print_32px.png"))); // NOI18N
        jLabel11.setToolTipText("Print Record");
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel11MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel11MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel11MouseReleased(evt);
            }
        });

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Microsoft_Excel_32px.png"))); // NOI18N
        jLabel13.setToolTipText("Save as Excel");
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel13MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel13MouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnlFooterActionLayout = new javax.swing.GroupLayout(pnlFooterAction);
        pnlFooterAction.setLayout(pnlFooterActionLayout);
        pnlFooterActionLayout.setHorizontalGroup(
            pnlFooterActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFooterActionLayout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlFooterActionLayout.setVerticalGroup(
            pnlFooterActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel74.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(255, 255, 255));
        jLabel74.setText("ID:");

        javax.swing.GroupLayout pnlFooterLayout = new javax.swing.GroupLayout(pnlFooter);
        pnlFooter.setLayout(pnlFooterLayout);
        pnlFooterLayout.setHorizontalGroup(
            pnlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFooterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel74)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 806, Short.MAX_VALUE)
                .addComponent(pnlFooterAction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlFooterLayout.setVerticalGroup(
            pnlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlFooterAction, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlFooterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel74, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlHeader.setBackground(new java.awt.Color(84, 127, 206));
        pnlHeader.setPreferredSize(new java.awt.Dimension(950, 50));

        jLabel114.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        jLabel114.setForeground(new java.awt.Color(255, 255, 255));
        jLabel114.setText("Career Development and Placement Office");

        jLabel115.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo.png"))); // NOI18N

        jLabel117.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel117.setForeground(new java.awt.Color(255, 255, 255));
        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel117.setText("2019-12-12 04:04 AM");

        jPanel19.setBackground(new java.awt.Color(84, 127, 206));
        jPanel19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlSearch.setBackground(new java.awt.Color(84, 127, 206));

        jTextField1.setBackground(new java.awt.Color(123, 156, 225));
        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.setText("Search...");
        jTextField1.setBorder(null);
        jTextField1.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextField1.setPreferredSize(new java.awt.Dimension(2, 20));
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
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search_20px_1.png"))); // NOI18N
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel7MouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnlSearchLayout = new javax.swing.GroupLayout(pnlSearch);
        pnlSearch.setLayout(pnlSearchLayout);
        pnlSearchLayout.setHorizontalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSearchLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlSearchLayout.setVerticalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel19.add(pnlSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 0, -1, -1));

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel115, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel114, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 136, Short.MAX_VALUE)
                .addComponent(jLabel117)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 157, Short.MAX_VALUE)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel115, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel114)
                    .addComponent(jLabel117, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(13, 14, Short.MAX_VALUE))
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlSideNav.setBackground(new java.awt.Color(23, 35, 51));
        pnlSideNav.setPreferredSize(new java.awt.Dimension(140, 263));

        one.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sidehome.png"))); // NOI18N
        one.setText("Home");
        one.setColorHover(new java.awt.Color(41, 57, 80));
        one.setColorNormal(new java.awt.Color(23, 35, 51));
        one.setColorPressed(new java.awt.Color(41, 57, 80));
        one.setFocusPainted(false);
        one.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        one.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        one.setIconTextGap(25);
        one.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                oneMousePressed(evt);
            }
        });
        one.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oneActionPerformed(evt);
            }
        });

        two.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sidestudent.png"))); // NOI18N
        two.setText("Students");
        two.setColorHover(new java.awt.Color(41, 57, 80));
        two.setColorNormal(new java.awt.Color(23, 35, 51));
        two.setColorPressed(new java.awt.Color(41, 57, 80));
        two.setFocusPainted(false);
        two.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        two.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        two.setIconTextGap(25);
        two.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                twoMousePressed(evt);
            }
        });
        two.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                twoActionPerformed(evt);
            }
        });

        three.setBackground(new java.awt.Color(23, 35, 51));
        three.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sidecompanies.png"))); // NOI18N
        three.setText(" Companies");
        three.setColorHover(new java.awt.Color(41, 57, 80));
        three.setColorNormal(new java.awt.Color(23, 35, 51));
        three.setColorPressed(new java.awt.Color(41, 57, 80));
        three.setFocusPainted(false);
        three.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        three.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        three.setIconTextGap(20);
        three.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                threeMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                threeMouseReleased(evt);
            }
        });
        three.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                threeActionPerformed(evt);
            }
        });

        four.setBackground(new java.awt.Color(23, 35, 51));
        four.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sideusers.png"))); // NOI18N
        four.setText("Users");
        four.setColorHover(new java.awt.Color(41, 57, 80));
        four.setColorNormal(new java.awt.Color(23, 35, 51));
        four.setColorPressed(new java.awt.Color(41, 57, 80));
        four.setFocusPainted(false);
        four.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        four.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        four.setIconTextGap(25);
        four.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fourMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                fourMousePressed(evt);
            }
        });
        four.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fourActionPerformed(evt);
            }
        });

        five.setBackground(new java.awt.Color(23, 35, 51));
        five.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sideprofile.png"))); // NOI18N
        five.setText("Profile");
        five.setColorHover(new java.awt.Color(41, 57, 80));
        five.setColorNormal(new java.awt.Color(23, 35, 51));
        five.setColorPressed(new java.awt.Color(41, 57, 80));
        five.setFocusPainted(false);
        five.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        five.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        five.setIconTextGap(25);
        five.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fiveMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                fiveMousePressed(evt);
            }
        });
        five.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fiveActionPerformed(evt);
            }
        });

        six.setBackground(new java.awt.Color(23, 35, 51));
        six.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sidereport.png"))); // NOI18N
        six.setText("Logs");
        six.setColorHover(new java.awt.Color(41, 57, 80));
        six.setColorNormal(new java.awt.Color(23, 35, 51));
        six.setColorPressed(new java.awt.Color(41, 57, 80));
        six.setFocusPainted(false);
        six.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        six.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        six.setIconTextGap(25);
        six.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                sixMousePressed(evt);
            }
        });
        six.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sixActionPerformed(evt);
            }
        });

        seven.setBackground(new java.awt.Color(23, 35, 51));
        seven.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sidesettings.png"))); // NOI18N
        seven.setText("Options");
        seven.setColorHover(new java.awt.Color(41, 57, 80));
        seven.setColorNormal(new java.awt.Color(23, 35, 51));
        seven.setColorPressed(new java.awt.Color(41, 57, 80));
        seven.setFocusPainted(false);
        seven.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        seven.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        seven.setIconTextGap(25);
        seven.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                sevenMousePressed(evt);
            }
        });
        seven.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sevenActionPerformed(evt);
            }
        });

        eight.setBackground(new java.awt.Color(23, 35, 51));
        eight.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sideaudit.png"))); // NOI18N
        eight.setText("History");
        eight.setColorHover(new java.awt.Color(41, 57, 80));
        eight.setColorNormal(new java.awt.Color(23, 35, 51));
        eight.setColorPressed(new java.awt.Color(41, 57, 80));
        eight.setFocusPainted(false);
        eight.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        eight.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        eight.setIconTextGap(25);
        eight.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                eightMousePressed(evt);
            }
        });
        eight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eightActionPerformed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Logout_Rounded_Left_32px_1.png"))); // NOI18N
        jLabel2.setToolTipText("Logout");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel2MouseExited(evt);
            }
        });

        five1.setBackground(new java.awt.Color(23, 35, 51));
        five1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sidedevelopers.png"))); // NOI18N
        five1.setText("Developers");
        five1.setColorHover(new java.awt.Color(41, 57, 80));
        five1.setColorNormal(new java.awt.Color(23, 35, 51));
        five1.setColorPressed(new java.awt.Color(41, 57, 80));
        five1.setFocusPainted(false);
        five1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        five1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        five1.setIconTextGap(22);
        five1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                five1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                five1MousePressed(evt);
            }
        });
        five1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                five1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSideNavLayout = new javax.swing.GroupLayout(pnlSideNav);
        pnlSideNav.setLayout(pnlSideNavLayout);
        pnlSideNavLayout.setHorizontalGroup(
            pnlSideNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlSideNavLayout.createSequentialGroup()
                .addGroup(pnlSideNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(one, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(two, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(three, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(four, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(five, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(six, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seven, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(eight, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(five1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlSideNavLayout.setVerticalGroup(
            pnlSideNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSideNavLayout.createSequentialGroup()
                .addComponent(one, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(three, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(two, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(six, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(four, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(seven, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(eight, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(five, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(five1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlUsers.setBackground(new java.awt.Color(255, 255, 255));

        UserList.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel109.setBackground(new java.awt.Color(255, 255, 255));
        jLabel109.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel109.setForeground(new java.awt.Color(255, 51, 51));
        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "User Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Name_125px.png"))); // NOI18N
        jLabel1.setToolTipText("");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel87.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel87.setForeground(new java.awt.Color(102, 102, 102));
        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel87.setText("User ID :");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Firstname :");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Date Registered :");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Middlename :");

        jLabel88.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel88.setForeground(new java.awt.Color(102, 102, 102));
        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel88.setText("Lastname :");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Suffix :");

        jLabel89.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel89.setForeground(new java.awt.Color(102, 102, 102));
        jLabel89.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel89.setText("Gender :");

        jLabel90.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel90.setForeground(new java.awt.Color(102, 102, 102));
        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel90.setText("Contact No. :");

        jLabel91.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel91.setForeground(new java.awt.Color(102, 102, 102));
        jLabel91.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel91.setText("Email :");

        jLabel92.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel92.setForeground(new java.awt.Color(102, 102, 102));
        jLabel92.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel92.setText("Status :");

        jLabel93.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel93.setForeground(new java.awt.Color(102, 102, 102));
        jLabel93.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel93.setText("Username :");

        jLabel35.setBackground(new java.awt.Color(255, 255, 255));
        jLabel35.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(102, 102, 102));

        jLabel94.setBackground(new java.awt.Color(255, 255, 255));
        jLabel94.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel94.setForeground(new java.awt.Color(102, 102, 102));

        jLabel95.setBackground(new java.awt.Color(255, 255, 255));
        jLabel95.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel95.setForeground(new java.awt.Color(102, 102, 102));

        jLabel96.setBackground(new java.awt.Color(255, 255, 255));
        jLabel96.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel96.setForeground(new java.awt.Color(102, 102, 102));

        jLabel97.setBackground(new java.awt.Color(255, 255, 255));
        jLabel97.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel97.setForeground(new java.awt.Color(102, 102, 102));

        jLabel98.setBackground(new java.awt.Color(255, 255, 255));
        jLabel98.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel98.setForeground(new java.awt.Color(102, 102, 102));

        jLabel99.setBackground(new java.awt.Color(255, 255, 255));
        jLabel99.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel99.setForeground(new java.awt.Color(102, 102, 102));

        jLabel100.setBackground(new java.awt.Color(255, 255, 255));
        jLabel100.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel100.setForeground(new java.awt.Color(102, 102, 102));

        jLabel101.setBackground(new java.awt.Color(255, 255, 255));
        jLabel101.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel101.setForeground(new java.awt.Color(102, 102, 102));

        jLabel102.setBackground(new java.awt.Color(255, 255, 255));
        jLabel102.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel102.setForeground(new java.awt.Color(102, 102, 102));

        changePass.setBackground(new java.awt.Color(255, 255, 255));

        jButton11.setBackground(new java.awt.Color(84, 127, 206));
        jButton11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setText("Change");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jPasswordField3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPasswordField3.setForeground(new java.awt.Color(102, 102, 102));
        jPasswordField3.setText("Enter new password");
        jPasswordField3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPasswordField3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPasswordField3FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPasswordField3FocusLost(evt);
            }
        });
        jPasswordField3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPasswordField3MouseClicked(evt);
            }
        });
        jPasswordField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jPasswordField3KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout changePassLayout = new javax.swing.GroupLayout(changePass);
        changePass.setLayout(changePassLayout);
        changePassLayout.setHorizontalGroup(
            changePassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(changePassLayout.createSequentialGroup()
                .addComponent(jPasswordField3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton11))
        );
        changePassLayout.setVerticalGroup(
            changePassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(changePassLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(changePassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPasswordField3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(94, 94, 94))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel93, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel92, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel91, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel90, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel89, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel87, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                            .addComponent(jLabel88, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel94, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(jLabel95, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addComponent(jLabel96, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(jLabel97, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLabel98, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel99, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel100, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel101, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel102, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(changePass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel87)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel94, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel95, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel88)
                    .addComponent(jLabel96, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel97, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel89)
                    .addComponent(jLabel98, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel93)
                    .addComponent(jLabel99, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel90)
                    .addComponent(jLabel100, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel91)
                    .addComponent(jLabel101, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel92)
                    .addComponent(jLabel102, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(changePass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Users List", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        jScrollPane5.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jTable4.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jTable4.setForeground(new java.awt.Color(102, 102, 102));
        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "User ID", "Name", "Username", "Gender", "Contact no.", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable4.setToolTipText("Users List");
        jTable4.setGridColor(new java.awt.Color(255, 255, 255));
        jTable4.setRowHeight(25);
        jTable4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTable4FocusLost(evt);
            }
        });
        jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable4MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable4MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTable4MouseReleased(evt);
            }
        });
        jTable4.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jTable4InputMethodTextChanged(evt);
            }
        });
        jTable4.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTable4PropertyChange(evt);
            }
        });
        jTable4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable4KeyReleased(evt);
            }
        });
        jScrollPane5.setViewportView(jTable4);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Prev_35px.png"))); // NOI18N
        jLabel47.setToolTipText("Previous");
        jLabel47.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel47.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel47MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel47MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel47MouseExited(evt);
            }
        });

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Right_Button_35px.png"))); // NOI18N
        jLabel46.setToolTipText("Next");
        jLabel46.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel46.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel46MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel46MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel46MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel109, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel109, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout UserListLayout = new javax.swing.GroupLayout(UserList);
        UserList.setLayout(UserListLayout);
        UserListLayout.setHorizontalGroup(
            UserListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        UserListLayout.setVerticalGroup(
            UserListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        UserProfile.setBackground(new java.awt.Color(255, 255, 255));

        pnlStudentPersonalInfo1.setBackground(new java.awt.Color(255, 255, 255));
        pnlStudentPersonalInfo1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Personal Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Name_125px.png"))); // NOI18N
        jLabel37.setToolTipText("Update Photo");
        jLabel37.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel37.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel37MouseClicked(evt);
            }
        });

        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Hashtag_18px_3.png"))); // NOI18N

        jSeparator3.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator3.setForeground(new java.awt.Color(84, 127, 206));

        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/contacts_18px.png"))); // NOI18N

        txtFname.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtFname.setForeground(new java.awt.Color(102, 102, 102));
        txtFname.setText("Firstname");
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

        jSeparator7.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator7.setForeground(new java.awt.Color(84, 127, 206));

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/contacts_18px.png"))); // NOI18N

        txtMname.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtMname.setForeground(new java.awt.Color(102, 102, 102));
        txtMname.setText("Middlename");
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

        jSeparator8.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator8.setForeground(new java.awt.Color(84, 127, 206));

        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Gender_18px_1.png"))); // NOI18N

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(102, 102, 102));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));
        jComboBox1.setToolTipText("Gender");
        jComboBox1.setBorder(null);
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jSeparator10.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator10.setForeground(new java.awt.Color(84, 127, 206));

        jLabel43.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(102, 102, 102));
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("Date Registered: 01/01/2019");

        jLabel41.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(102, 102, 102));
        jLabel41.setText("User ID");

        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/contacts_18px.png"))); // NOI18N

        txtLname.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtLname.setForeground(new java.awt.Color(102, 102, 102));
        txtLname.setText("Lastname");
        txtLname.setToolTipText("Middlename");
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

        jSeparator11.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator11.setForeground(new java.awt.Color(84, 127, 206));

        txtSuffix.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtSuffix.setForeground(new java.awt.Color(102, 102, 102));
        txtSuffix.setText("Suffix");
        txtSuffix.setToolTipText("Middlename");
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

        jSeparator12.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator12.setForeground(new java.awt.Color(84, 127, 206));

        jLabel48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/contacts_18px.png"))); // NOI18N

        txtUsername.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtUsername.setForeground(new java.awt.Color(102, 102, 102));
        txtUsername.setText("Username");
        txtUsername.setToolTipText("Middlename");
        txtUsername.setBorder(null);
        txtUsername.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUsernameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUsernameFocusLost(evt);
            }
        });
        txtUsername.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtUsernameMouseClicked(evt);
            }
        });

        jSeparator9.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator9.setForeground(new java.awt.Color(84, 127, 206));

        txtContactNumber.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtContactNumber.setForeground(new java.awt.Color(102, 102, 102));
        txtContactNumber.setText("Contact Number");
        txtContactNumber.setToolTipText("Middlename");
        txtContactNumber.setBorder(null);
        txtContactNumber.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtContactNumberFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtContactNumberFocusLost(evt);
            }
        });
        txtContactNumber.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtContactNumberMouseClicked(evt);
            }
        });
        txtContactNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtContactNumberKeyReleased(evt);
            }
        });

        jSeparator13.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator13.setForeground(new java.awt.Color(84, 127, 206));

        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/contacts_18px.png"))); // NOI18N

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtEmail.setForeground(new java.awt.Color(102, 102, 102));
        txtEmail.setText("Email");
        txtEmail.setToolTipText("Middlename");
        txtEmail.setBorder(null);
        txtEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEmailFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEmailFocusLost(evt);
            }
        });
        txtEmail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtEmailMouseClicked(evt);
            }
        });
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        jSeparator14.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator14.setForeground(new java.awt.Color(84, 127, 206));

        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/contacts_18px.png"))); // NOI18N

        jLabel51.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(102, 102, 102));
        jLabel51.setText("Activate: ");

        jComboBox2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jComboBox2.setForeground(new java.awt.Color(102, 102, 102));
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Staff", "Admin" }));
        jComboBox2.setToolTipText("Gender");
        jComboBox2.setBorder(null);
        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });

        jCheckBox1.setBackground(new java.awt.Color(255, 255, 255));

        jButton9.setBackground(new java.awt.Color(84, 127, 206));
        jButton9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Update User Profile");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlStudentPersonalInfo1Layout = new javax.swing.GroupLayout(pnlStudentPersonalInfo1);
        pnlStudentPersonalInfo1.setLayout(pnlStudentPersonalInfo1Layout);
        pnlStudentPersonalInfo1Layout.setHorizontalGroup(
            pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfo1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlStudentPersonalInfo1Layout.createSequentialGroup()
                        .addGroup(pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlStudentPersonalInfo1Layout.createSequentialGroup()
                                .addComponent(jLabel37)
                                .addGap(26, 26, 26)
                                .addGroup(pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlStudentPersonalInfo1Layout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addComponent(jLabel51)
                                        .addGap(0, 0, 0)
                                        .addComponent(jCheckBox1))
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStudentPersonalInfo1Layout.createSequentialGroup()
                                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlStudentPersonalInfo1Layout.createSequentialGroup()
                                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtFname)
                                        .addComponent(txtMname)
                                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(pnlStudentPersonalInfo1Layout.createSequentialGroup()
                                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jSeparator3)
                                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlStudentPersonalInfo1Layout.createSequentialGroup()
                                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlStudentPersonalInfo1Layout.createSequentialGroup()
                                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jSeparator11)
                                    .addComponent(txtLname, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtSuffix, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlStudentPersonalInfo1Layout.createSequentialGroup()
                                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtUsername)
                                    .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlStudentPersonalInfo1Layout.createSequentialGroup()
                                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtContactNumber)
                                    .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlStudentPersonalInfo1Layout.createSequentialGroup()
                                .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtEmail)
                                    .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 20, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlStudentPersonalInfo1Layout.setVerticalGroup(
            pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfo1Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jLabel43)
                .addGroup(pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlStudentPersonalInfo1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel37))
                    .addGroup(pnlStudentPersonalInfo1Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(9, 9, 9)
                .addGroup(pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlStudentPersonalInfo1Layout.createSequentialGroup()
                        .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlStudentPersonalInfo1Layout.createSequentialGroup()
                        .addComponent(txtFname, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE))
                    .addGroup(pnlStudentPersonalInfo1Layout.createSequentialGroup()
                        .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(23, 23, 23)))
                .addGroup(pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlStudentPersonalInfo1Layout.createSequentialGroup()
                        .addComponent(txtMname, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlStudentPersonalInfo1Layout.createSequentialGroup()
                        .addComponent(txtSuffix, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 1, Short.MAX_VALUE))
                    .addComponent(txtLname, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(0, 0, 0)
                .addGroup(pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator11)
                    .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlStudentPersonalInfo1Layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlStudentPersonalInfo1Layout.createSequentialGroup()
                        .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlStudentPersonalInfo1Layout.createSequentialGroup()
                        .addComponent(txtContactNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlStudentPersonalInfo1Layout.createSequentialGroup()
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton9))
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Activity Logs", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        jScrollPane3.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jTable1.setForeground(new java.awt.Color(102, 102, 102));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1001", "03/12/2019 08:30 PM", "ADD STUDENT INFORMATION", "Claremce Andaya"},
                {"1002", "03/12/2019 08:30 PM", "UPDATE STUDENT RECORD", "Clarence Andaya"},
                {"1003", "03/12/2019 08:30 PM", "ADD COMPANY", "1002"},
                {"1004", "03/12/2019 08:30 PM", "ADD STUDENT RECORD ", null}
            },
            new String [] {
                "Log ID", "Date and Time", "Activity", "Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.setGridColor(new java.awt.Color(255, 255, 255));
        jTable1.setRowHeight(25);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTable1MouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(jTable1);

        jLabel105.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel105.setForeground(new java.awt.Color(102, 102, 102));
        jLabel105.setText("Filter by Date:");

        txtFromDate5.setBackground(new java.awt.Color(255, 255, 255));
        txtFromDate5.setForeground(new java.awt.Color(102, 102, 102));
        txtFromDate5.setDateFormatString("yyyy-MM-dd");
        txtFromDate5.setFocusable(false);
        txtFromDate5.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtFromDate5.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtFromDate5InputMethodTextChanged(evt);
            }
        });
        txtFromDate5.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtFromDate5PropertyChange(evt);
            }
        });
        txtFromDate5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFromDate5KeyReleased(evt);
            }
        });

        jLabel106.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel106.setForeground(new java.awt.Color(102, 102, 102));
        jLabel106.setText("to");

        txtFromDate6.setBackground(new java.awt.Color(255, 255, 255));
        txtFromDate6.setForeground(new java.awt.Color(102, 102, 102));
        txtFromDate6.setDateFormatString("yyyy-MM-dd");
        txtFromDate6.setFocusable(false);
        txtFromDate6.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtFromDate6.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtFromDate6InputMethodTextChanged(evt);
            }
        });
        txtFromDate6.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtFromDate6PropertyChange(evt);
            }
        });

        jLabel107.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel107.setForeground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel105)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtFromDate5, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel106)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtFromDate6, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel107, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 741, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtFromDate6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtFromDate5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel105, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel106, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel107, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                    .addGap(41, 41, 41)
                    .addComponent(jScrollPane3)
                    .addContainerGap()))
        );

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Right_Button_35px.png"))); // NOI18N
        jLabel53.setToolTipText("Next");
        jLabel53.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel53.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel53MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel53MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel53MouseExited(evt);
            }
        });

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Prev_35px.png"))); // NOI18N
        jLabel52.setToolTipText("Previous");
        jLabel52.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel52.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel52MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel52MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel52MouseExited(evt);
            }
        });

        jLabel108.setBackground(new java.awt.Color(255, 255, 255));
        jLabel108.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel108.setForeground(new java.awt.Color(255, 51, 51));
        jLabel108.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout UserProfileLayout = new javax.swing.GroupLayout(UserProfile);
        UserProfile.setLayout(UserProfileLayout);
        UserProfileLayout.setHorizontalGroup(
            UserProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UserProfileLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlStudentPersonalInfo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(UserProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(UserProfileLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel108, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        UserProfileLayout.setVerticalGroup(
            UserProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UserProfileLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(UserProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlStudentPersonalInfo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(UserProfileLayout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(UserProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                            .addComponent(jLabel108, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
        );

        javax.swing.GroupLayout pnlUsersLayout = new javax.swing.GroupLayout(pnlUsers);
        pnlUsers.setLayout(pnlUsersLayout);
        pnlUsersLayout.setHorizontalGroup(
            pnlUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(UserList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(UserProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlUsersLayout.setVerticalGroup(
            pnlUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(UserList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(UserProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        UserProfileInfo.setBackground(new java.awt.Color(255, 255, 255));
        UserProfileInfo.setPreferredSize(new java.awt.Dimension(1113, 565));

        pnlStudentPersonalInfo2.setBackground(new java.awt.Color(255, 255, 255));
        pnlStudentPersonalInfo2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "My Profile Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Name_125px.png"))); // NOI18N
        jLabel45.setToolTipText("Update Photo");
        jLabel45.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel45.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel45MouseClicked(evt);
            }
        });

        jLabel56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Hashtag_18px_3.png"))); // NOI18N

        jSeparator4.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator4.setForeground(new java.awt.Color(84, 127, 206));

        jLabel57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/contacts_18px.png"))); // NOI18N

        jSeparator15.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator15.setForeground(new java.awt.Color(84, 127, 206));

        jLabel59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Gender_18px_1.png"))); // NOI18N

        jSeparator17.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator17.setForeground(new java.awt.Color(84, 127, 206));

        jLabel60.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(102, 102, 102));
        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel60.setText("Date Registered: 01/01/2019");
        jLabel60.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel60MouseClicked(evt);
            }
        });

        jLabel61.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(102, 102, 102));
        jLabel61.setText("User ID");
        jLabel61.setToolTipText("User ID");

        jLabel63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/contacts_18px.png"))); // NOI18N

        jSeparator20.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator20.setForeground(new java.awt.Color(84, 127, 206));

        txtLname10.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtLname10.setForeground(new java.awt.Color(102, 102, 102));
        txtLname10.setText("Contact no.");
        txtLname10.setToolTipText("Contact number");
        txtLname10.setBorder(null);
        txtLname10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtLname10FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLname10FocusLost(evt);
            }
        });
        txtLname10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtLname10MouseClicked(evt);
            }
        });
        txtLname10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLname10KeyReleased(evt);
            }
        });

        jSeparator21.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator21.setForeground(new java.awt.Color(84, 127, 206));

        jLabel64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Phone_18px_1.png"))); // NOI18N

        txtLname11.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtLname11.setForeground(new java.awt.Color(102, 102, 102));
        txtLname11.setText("Email");
        txtLname11.setToolTipText("Email");
        txtLname11.setBorder(null);
        txtLname11.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtLname11FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLname11FocusLost(evt);
            }
        });
        txtLname11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtLname11MouseClicked(evt);
            }
        });
        txtLname11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLname11ActionPerformed(evt);
            }
        });

        jSeparator22.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator22.setForeground(new java.awt.Color(84, 127, 206));

        jLabel65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Secured_Letter_18px_3.png"))); // NOI18N

        jButton10.setBackground(new java.awt.Color(84, 127, 206));
        jButton10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("Update My Profile");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(102, 102, 102));
        jLabel36.setText("Fullname");
        jLabel36.setToolTipText("Fullname");

        jLabel72.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(102, 102, 102));
        jLabel72.setText("Gender");
        jLabel72.setToolTipText("Gender");

        jLabel73.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(102, 102, 102));
        jLabel73.setText("Username");
        jLabel73.setToolTipText("Username");

        jPasswordField1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jPasswordField1.setForeground(new java.awt.Color(102, 102, 102));
        jPasswordField1.setText("Change Password");
        jPasswordField1.setToolTipText("Password");
        jPasswordField1.setBorder(null);
        jPasswordField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPasswordField1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPasswordField1FocusLost(evt);
            }
        });
        jPasswordField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPasswordField1MouseClicked(evt);
            }
        });
        jPasswordField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordField1ActionPerformed(evt);
            }
        });

        jSeparator23.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator23.setForeground(new java.awt.Color(84, 127, 206));

        jLabel66.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/unlock_18px.png"))); // NOI18N

        jPasswordField2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jPasswordField2.setForeground(new java.awt.Color(102, 102, 102));
        jPasswordField2.setText("Repeat Password");
        jPasswordField2.setToolTipText("Repeat Password");
        jPasswordField2.setBorder(null);
        jPasswordField2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPasswordField2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPasswordField2FocusLost(evt);
            }
        });
        jPasswordField2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPasswordField2MouseClicked(evt);
            }
        });

        jSeparator24.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator24.setForeground(new java.awt.Color(84, 127, 206));

        jLabel69.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/unlock_18px.png"))); // NOI18N

        javax.swing.GroupLayout pnlStudentPersonalInfo2Layout = new javax.swing.GroupLayout(pnlStudentPersonalInfo2);
        pnlStudentPersonalInfo2.setLayout(pnlStudentPersonalInfo2Layout);
        pnlStudentPersonalInfo2Layout.setHorizontalGroup(
            pnlStudentPersonalInfo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStudentPersonalInfo2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel45)
                .addGap(94, 94, 94))
            .addGroup(pnlStudentPersonalInfo2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlStudentPersonalInfo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlStudentPersonalInfo2Layout.createSequentialGroup()
                        .addGroup(pnlStudentPersonalInfo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlStudentPersonalInfo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(pnlStudentPersonalInfo2Layout.createSequentialGroup()
                                    .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(pnlStudentPersonalInfo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtLname11, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jSeparator22)))
                                .addGroup(pnlStudentPersonalInfo2Layout.createSequentialGroup()
                                    .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(pnlStudentPersonalInfo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jSeparator4)))
                                .addGroup(pnlStudentPersonalInfo2Layout.createSequentialGroup()
                                    .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(pnlStudentPersonalInfo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jSeparator21)
                                        .addComponent(txtLname10)))
                                .addGroup(pnlStudentPersonalInfo2Layout.createSequentialGroup()
                                    .addGroup(pnlStudentPersonalInfo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(pnlStudentPersonalInfo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jSeparator17)
                                        .addComponent(jSeparator23)
                                        .addComponent(jPasswordField1)
                                        .addComponent(jSeparator24)
                                        .addComponent(jPasswordField2)
                                        .addComponent(jLabel72, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jSeparator15, javax.swing.GroupLayout.Alignment.TRAILING))))
                            .addGroup(pnlStudentPersonalInfo2Layout.createSequentialGroup()
                                .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlStudentPersonalInfo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jSeparator20)
                                    .addComponent(jLabel73, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))))
                        .addGap(0, 20, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlStudentPersonalInfo2Layout.setVerticalGroup(
            pnlStudentPersonalInfo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfo2Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jLabel60)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel45)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlStudentPersonalInfo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                    .addComponent(jLabel61, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlStudentPersonalInfo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel57, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlStudentPersonalInfo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel59, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                    .addComponent(jLabel72, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlStudentPersonalInfo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtLname10, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jSeparator21, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlStudentPersonalInfo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtLname11)
                    .addComponent(jLabel65, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jSeparator22, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(pnlStudentPersonalInfo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel63, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                    .addComponent(jLabel73, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jSeparator20, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(pnlStudentPersonalInfo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel66, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(jPasswordField1))
                .addGap(0, 0, 0)
                .addComponent(jSeparator23, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(pnlStudentPersonalInfo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jSeparator24, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton10)
                .addGap(1, 1, 1))
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "My Activity Logs", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        txtFromDate1.setBackground(new java.awt.Color(255, 255, 255));
        txtFromDate1.setForeground(new java.awt.Color(102, 102, 102));
        txtFromDate1.setDateFormatString("yyyy-MM-dd");
        txtFromDate1.setFocusable(false);
        txtFromDate1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtFromDate1.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtFromDate1InputMethodTextChanged(evt);
            }
        });
        txtFromDate1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtFromDate1PropertyChange(evt);
            }
        });
        txtFromDate1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFromDate1KeyReleased(evt);
            }
        });

        jScrollPane4.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jTable3.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jTable3.setForeground(new java.awt.Color(102, 102, 102));
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Log ID", "Date and Time", "Activity", "Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable3.setGridColor(new java.awt.Color(255, 255, 255));
        jTable3.setRowHeight(25);
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable3);

        txtFromDate2.setBackground(new java.awt.Color(255, 255, 255));
        txtFromDate2.setForeground(new java.awt.Color(102, 102, 102));
        txtFromDate2.setDateFormatString("yyyy-MM-dd");
        txtFromDate2.setFocusable(false);
        txtFromDate2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtFromDate2.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtFromDate2InputMethodTextChanged(evt);
            }
        });
        txtFromDate2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtFromDate2PropertyChange(evt);
            }
        });

        jLabel79.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(102, 102, 102));
        jLabel79.setText("Filter by Date:");

        jLabel80.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(102, 102, 102));
        jLabel80.setText("to");

        jLabel81.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel79)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtFromDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel80)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtFromDate2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel81, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 729, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtFromDate2, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                    .addComponent(txtFromDate1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel79, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel80, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel81, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                    .addGap(43, 43, 43)
                    .addComponent(jScrollPane4)
                    .addContainerGap()))
        );

        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel67.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Right_Button_35px.png"))); // NOI18N
        jLabel67.setToolTipText("Next");
        jLabel67.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel67.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel67MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel67MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel67MouseExited(evt);
            }
        });

        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Prev_35px.png"))); // NOI18N
        jLabel68.setToolTipText("Previous");
        jLabel68.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel68.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel68MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel68MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel68MouseExited(evt);
            }
        });

        jLabel104.setBackground(new java.awt.Color(255, 255, 255));
        jLabel104.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel104.setForeground(new java.awt.Color(255, 51, 51));
        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout UserProfileInfoLayout = new javax.swing.GroupLayout(UserProfileInfo);
        UserProfileInfo.setLayout(UserProfileInfoLayout);
        UserProfileInfoLayout.setHorizontalGroup(
            UserProfileInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UserProfileInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlStudentPersonalInfo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(UserProfileInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UserProfileInfoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel104, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        UserProfileInfoLayout.setVerticalGroup(
            UserProfileInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UserProfileInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(UserProfileInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlStudentPersonalInfo2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(UserProfileInfoLayout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(UserProfileInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel67, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel68, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                            .addComponent(jLabel104, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
        );

        pnlLogs.setBackground(new java.awt.Color(255, 255, 255));

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Right_Button_35px.png"))); // NOI18N
        jLabel54.setToolTipText("Next");
        jLabel54.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel54.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel54MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel54MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel54MouseReleased(evt);
            }
        });

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Prev_35px.png"))); // NOI18N
        jLabel55.setToolTipText("Previous");
        jLabel55.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel55.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel55MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel55MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel55MouseReleased(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Records Log", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        jComboBox3.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jComboBox3.setForeground(new java.awt.Color(102, 102, 102));
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Department (All)" }));
        jComboBox3.setBorder(null);
        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });
        jComboBox3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jComboBox3FocusGained(evt);
            }
        });
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
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
                "Date & Time", "Student no.", "Name", "Course", "Level", "Log"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable2.setGridColor(new java.awt.Color(255, 255, 255));
        jTable2.setRowHeight(25);
        jScrollPane2.setViewportView(jTable2);

        jComboBox5.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jComboBox5.setForeground(new java.awt.Color(102, 102, 102));
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Level (All)" }));
        jComboBox5.setBorder(null);
        jComboBox5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox5ItemStateChanged(evt);
            }
        });
        jComboBox5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jComboBox5FocusGained(evt);
            }
        });
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });

        jComboBox4.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jComboBox4.setForeground(new java.awt.Color(102, 102, 102));
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Course (All)" }));
        jComboBox4.setBorder(null);
        jComboBox4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox4ItemStateChanged(evt);
            }
        });
        jComboBox4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jComboBox4FocusGained(evt);
            }
        });

        jLabel110.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel110.setForeground(new java.awt.Color(102, 102, 102));
        jLabel110.setText("Filter by Date:");

        txtFromDate7.setBackground(new java.awt.Color(255, 255, 255));
        txtFromDate7.setForeground(new java.awt.Color(102, 102, 102));
        txtFromDate7.setDateFormatString("yyyy-MM-dd");
        txtFromDate7.setFocusable(false);
        txtFromDate7.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtFromDate7.setRequestFocusEnabled(false);
        txtFromDate7.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtFromDate7InputMethodTextChanged(evt);
            }
        });
        txtFromDate7.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtFromDate7PropertyChange(evt);
            }
        });
        txtFromDate7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFromDate7KeyReleased(evt);
            }
        });

        jLabel111.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel111.setForeground(new java.awt.Color(102, 102, 102));
        jLabel111.setText("to");

        txtFromDate8.setBackground(new java.awt.Color(255, 255, 255));
        txtFromDate8.setForeground(new java.awt.Color(102, 102, 102));
        txtFromDate8.setDateFormatString("yyyy-MM-dd");
        txtFromDate8.setFocusable(false);
        txtFromDate8.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtFromDate8.setRequestFocusEnabled(false);
        txtFromDate8.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtFromDate8InputMethodTextChanged(evt);
            }
        });
        txtFromDate8.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtFromDate8PropertyChange(evt);
            }
        });

        jLabel112.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel112.setForeground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel110)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtFromDate7, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel111)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtFromDate8, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel112, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtFromDate8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtFromDate7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel110, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel111, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel112, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE))
        );

        jLabel113.setBackground(new java.awt.Color(255, 255, 255));
        jLabel113.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel113.setForeground(new java.awt.Color(255, 51, 51));
        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout pnlLogsLayout = new javax.swing.GroupLayout(pnlLogs);
        pnlLogs.setLayout(pnlLogsLayout);
        pnlLogsLayout.setHorizontalGroup(
            pnlLogsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLogsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlLogsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLogsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel113, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlLogsLayout.setVerticalGroup(
            pnlLogsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLogsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlLogsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel55, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                    .addComponent(jLabel54, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                    .addComponent(jLabel113, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        pnlHomePage.setBackground(new java.awt.Color(255, 255, 255));

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1120, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 415, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel15.setBackground(new java.awt.Color(221, 75, 57));
        jPanel15.setToolTipText("Number of registered users");
        jPanel15.setPreferredSize(new java.awt.Dimension(428, 80));

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_User_Groups_52px.png"))); // NOI18N

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Users");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel24)))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(6, 6, 6))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel24)))
                .addContainerGap())
        );

        jPanel14.setBackground(new java.awt.Color(128, 34, 158));
        jPanel14.setToolTipText("Number of registered companies ");
        jPanel14.setPreferredSize(new java.awt.Dimension(428, 80));

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Company_52px.png"))); // NOI18N

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Companies");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel21)))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(6, 6, 6))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel21)))
                .addContainerGap())
        );

        jPanel13.setBackground(new java.awt.Color(79, 184, 115));
        jPanel13.setToolTipText("Number of deployed students ");
        jPanel13.setPreferredSize(new java.awt.Dimension(428, 80));
        jPanel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPanel13MouseReleased(evt);
            }
        });

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Permanent_Job_52px.png"))); // NOI18N

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Employed");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel18)))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(6, 6, 6))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18)))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(86, 165, 227));
        jPanel4.setToolTipText("Number of registered students");
        jPanel4.setPreferredSize(new java.awt.Dimension(428, 80));

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Students_52px_6.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Students");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 265, Short.MAX_VALUE)
                        .addComponent(jLabel15)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(6, 6, 6))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlHomePageLayout = new javax.swing.GroupLayout(pnlHomePage);
        pnlHomePage.setLayout(pnlHomePageLayout);
        pnlHomePageLayout.setHorizontalGroup(
            pnlHomePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHomePageLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlHomePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlHomePageLayout.createSequentialGroup()
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHomePageLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlHomePageLayout.setVerticalGroup(
            pnlHomePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHomePageLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnlHomePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26)
                .addGroup(pnlHomePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        navHeaderHolder.setLayout(null);

        pnlListStudent.setBackground(new java.awt.Color(255, 255, 255));
        pnlListStudent.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pnlListStudent.setPreferredSize(new java.awt.Dimension(100, 40));
        pnlListStudent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlListStudentMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlListStudentMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlListStudentMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pnlListStudentMouseReleased(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("List");

        pnlAddStudent2.setBackground(new java.awt.Color(23, 35, 51));
        pnlAddStudent2.setPreferredSize(new java.awt.Dimension(100, 40));
        pnlAddStudent2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlAddStudent2MouseClicked(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(51, 51, 51));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Add");

        javax.swing.GroupLayout pnlAddStudent2Layout = new javax.swing.GroupLayout(pnlAddStudent2);
        pnlAddStudent2.setLayout(pnlAddStudent2Layout);
        pnlAddStudent2Layout.setHorizontalGroup(
            pnlAddStudent2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddStudent2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel25)
                .addContainerGap(35, Short.MAX_VALUE))
        );
        pnlAddStudent2Layout.setVerticalGroup(
            pnlAddStudent2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAddStudent2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel25)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlListStudentLayout = new javax.swing.GroupLayout(pnlListStudent);
        pnlListStudent.setLayout(pnlListStudentLayout);
        pnlListStudentLayout.setHorizontalGroup(
            pnlListStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlListStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlListStudentLayout.createSequentialGroup()
                    .addGap(37, 37, 37)
                    .addComponent(jLabel8)
                    .addContainerGap(42, Short.MAX_VALUE)))
            .addGroup(pnlListStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlAddStudent2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlListStudentLayout.setVerticalGroup(
            pnlListStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(pnlListStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlListStudentLayout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addComponent(jLabel8)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(pnlListStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlListStudentLayout.createSequentialGroup()
                    .addComponent(pnlAddStudent2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 39, Short.MAX_VALUE)))
        );

        pnlAddStudent.setBackground(new java.awt.Color(220, 220, 220));
        pnlAddStudent.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pnlAddStudent.setPreferredSize(new java.awt.Dimension(100, 40));
        pnlAddStudent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlAddStudentMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pnlAddStudentMouseReleased(evt);
            }
        });

        pnlAddStudent1.setBackground(new java.awt.Color(23, 35, 51));
        pnlAddStudent1.setPreferredSize(new java.awt.Dimension(100, 40));
        pnlAddStudent1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlAddStudent1MouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Add");

        javax.swing.GroupLayout pnlAddStudent1Layout = new javax.swing.GroupLayout(pnlAddStudent1);
        pnlAddStudent1.setLayout(pnlAddStudent1Layout);
        pnlAddStudent1Layout.setHorizontalGroup(
            pnlAddStudent1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddStudent1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel6)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        pnlAddStudent1Layout.setVerticalGroup(
            pnlAddStudent1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAddStudent1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addContainerGap())
        );

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(51, 51, 51));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Add");

        javax.swing.GroupLayout pnlAddStudentLayout = new javax.swing.GroupLayout(pnlAddStudent);
        pnlAddStudent.setLayout(pnlAddStudentLayout);
        pnlAddStudentLayout.setHorizontalGroup(
            pnlAddStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddStudentLayout.createSequentialGroup()
                .addComponent(pnlAddStudent1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(pnlAddStudentLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel26)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlAddStudentLayout.setVerticalGroup(
            pnlAddStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddStudentLayout.createSequentialGroup()
                .addComponent(pnlAddStudent1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jLabel26)
                .addContainerGap())
        );

        javax.swing.GroupLayout StudentsNavLayout = new javax.swing.GroupLayout(StudentsNav);
        StudentsNav.setLayout(StudentsNavLayout);
        StudentsNavLayout.setHorizontalGroup(
            StudentsNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StudentsNavLayout.createSequentialGroup()
                .addComponent(pnlListStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlAddStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 830, Short.MAX_VALUE))
        );
        StudentsNavLayout.setVerticalGroup(
            StudentsNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, StudentsNavLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(StudentsNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlListStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlAddStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        navHeaderHolder.add(StudentsNav);
        StudentsNav.setBounds(0, 0, 1030, 40);

        pnlListStudent1.setBackground(new java.awt.Color(255, 255, 255));
        pnlListStudent1.setPreferredSize(new java.awt.Dimension(100, 40));
        pnlListStudent1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlListStudent1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlListStudent1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlListStudent1MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pnlListStudent1MouseReleased(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(51, 51, 51));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("List");

        pnlAddStudent3.setBackground(new java.awt.Color(23, 35, 51));
        pnlAddStudent3.setPreferredSize(new java.awt.Dimension(100, 40));
        pnlAddStudent3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlAddStudent3MouseClicked(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(51, 51, 51));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Add");

        javax.swing.GroupLayout pnlAddStudent3Layout = new javax.swing.GroupLayout(pnlAddStudent3);
        pnlAddStudent3.setLayout(pnlAddStudent3Layout);
        pnlAddStudent3Layout.setHorizontalGroup(
            pnlAddStudent3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddStudent3Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel28)
                .addContainerGap(35, Short.MAX_VALUE))
        );
        pnlAddStudent3Layout.setVerticalGroup(
            pnlAddStudent3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAddStudent3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel28)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlListStudent1Layout = new javax.swing.GroupLayout(pnlListStudent1);
        pnlListStudent1.setLayout(pnlListStudent1Layout);
        pnlListStudent1Layout.setHorizontalGroup(
            pnlListStudent1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlListStudent1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlListStudent1Layout.createSequentialGroup()
                    .addGap(37, 37, 37)
                    .addComponent(jLabel27)
                    .addContainerGap(42, Short.MAX_VALUE)))
            .addGroup(pnlListStudent1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlAddStudent3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlListStudent1Layout.setVerticalGroup(
            pnlListStudent1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(pnlListStudent1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlListStudent1Layout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addComponent(jLabel27)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(pnlListStudent1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlListStudent1Layout.createSequentialGroup()
                    .addComponent(pnlAddStudent3, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 39, Short.MAX_VALUE)))
        );

        pnlAddStudent4.setBackground(new java.awt.Color(220, 220, 220));
        pnlAddStudent4.setPreferredSize(new java.awt.Dimension(100, 40));
        pnlAddStudent4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlAddStudent4MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pnlAddStudent4MouseReleased(evt);
            }
        });

        pnlAddStudent5.setBackground(new java.awt.Color(23, 35, 51));
        pnlAddStudent5.setPreferredSize(new java.awt.Dimension(100, 40));
        pnlAddStudent5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlAddStudent5MouseClicked(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(51, 51, 51));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Add");

        javax.swing.GroupLayout pnlAddStudent5Layout = new javax.swing.GroupLayout(pnlAddStudent5);
        pnlAddStudent5.setLayout(pnlAddStudent5Layout);
        pnlAddStudent5Layout.setHorizontalGroup(
            pnlAddStudent5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddStudent5Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel29)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        pnlAddStudent5Layout.setVerticalGroup(
            pnlAddStudent5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAddStudent5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel29)
                .addContainerGap())
        );

        jLabel30.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(51, 51, 51));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Add");

        javax.swing.GroupLayout pnlAddStudent4Layout = new javax.swing.GroupLayout(pnlAddStudent4);
        pnlAddStudent4.setLayout(pnlAddStudent4Layout);
        pnlAddStudent4Layout.setHorizontalGroup(
            pnlAddStudent4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddStudent4Layout.createSequentialGroup()
                .addComponent(pnlAddStudent5, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(pnlAddStudent4Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel30)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlAddStudent4Layout.setVerticalGroup(
            pnlAddStudent4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddStudent4Layout.createSequentialGroup()
                .addComponent(pnlAddStudent5, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jLabel30)
                .addContainerGap())
        );

        javax.swing.GroupLayout UsersNavLayout = new javax.swing.GroupLayout(UsersNav);
        UsersNav.setLayout(UsersNavLayout);
        UsersNavLayout.setHorizontalGroup(
            UsersNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UsersNavLayout.createSequentialGroup()
                .addComponent(pnlListStudent1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlAddStudent4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 830, Short.MAX_VALUE))
        );
        UsersNavLayout.setVerticalGroup(
            UsersNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UsersNavLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(UsersNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlListStudent1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlAddStudent4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        navHeaderHolder.add(UsersNav);
        UsersNav.setBounds(0, 0, 1030, 40);

        pnlListCompany.setBackground(new java.awt.Color(255, 255, 255));
        pnlListCompany.setPreferredSize(new java.awt.Dimension(100, 40));
        pnlListCompany.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlListCompanyMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlListCompanyMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlListCompanyMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pnlListCompanyMouseReleased(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(51, 51, 51));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("List");

        pnlAddStudent6.setBackground(new java.awt.Color(23, 35, 51));
        pnlAddStudent6.setPreferredSize(new java.awt.Dimension(100, 40));
        pnlAddStudent6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlAddStudent6MouseClicked(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(51, 51, 51));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Add");

        javax.swing.GroupLayout pnlAddStudent6Layout = new javax.swing.GroupLayout(pnlAddStudent6);
        pnlAddStudent6.setLayout(pnlAddStudent6Layout);
        pnlAddStudent6Layout.setHorizontalGroup(
            pnlAddStudent6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddStudent6Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel32)
                .addContainerGap(35, Short.MAX_VALUE))
        );
        pnlAddStudent6Layout.setVerticalGroup(
            pnlAddStudent6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAddStudent6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel32)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlListCompanyLayout = new javax.swing.GroupLayout(pnlListCompany);
        pnlListCompany.setLayout(pnlListCompanyLayout);
        pnlListCompanyLayout.setHorizontalGroup(
            pnlListCompanyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(pnlListCompanyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlListCompanyLayout.createSequentialGroup()
                    .addGap(37, 37, 37)
                    .addComponent(jLabel31)
                    .addContainerGap(42, Short.MAX_VALUE)))
            .addGroup(pnlListCompanyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlAddStudent6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlListCompanyLayout.setVerticalGroup(
            pnlListCompanyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(pnlListCompanyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlListCompanyLayout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addComponent(jLabel31)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(pnlListCompanyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlListCompanyLayout.createSequentialGroup()
                    .addComponent(pnlAddStudent6, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 39, Short.MAX_VALUE)))
        );

        pnlAddCompany.setBackground(new java.awt.Color(220, 220, 220));
        pnlAddCompany.setPreferredSize(new java.awt.Dimension(100, 40));
        pnlAddCompany.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlAddCompanyMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pnlAddCompanyMouseReleased(evt);
            }
        });

        pnlAddStudent8.setBackground(new java.awt.Color(23, 35, 51));
        pnlAddStudent8.setPreferredSize(new java.awt.Dimension(100, 40));
        pnlAddStudent8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlAddStudent8MouseClicked(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(51, 51, 51));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("Add");

        javax.swing.GroupLayout pnlAddStudent8Layout = new javax.swing.GroupLayout(pnlAddStudent8);
        pnlAddStudent8.setLayout(pnlAddStudent8Layout);
        pnlAddStudent8Layout.setHorizontalGroup(
            pnlAddStudent8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddStudent8Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel33)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        pnlAddStudent8Layout.setVerticalGroup(
            pnlAddStudent8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAddStudent8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel33)
                .addContainerGap())
        );

        jLabel34.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(51, 51, 51));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("Add");

        javax.swing.GroupLayout pnlAddCompanyLayout = new javax.swing.GroupLayout(pnlAddCompany);
        pnlAddCompany.setLayout(pnlAddCompanyLayout);
        pnlAddCompanyLayout.setHorizontalGroup(
            pnlAddCompanyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddCompanyLayout.createSequentialGroup()
                .addComponent(pnlAddStudent8, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(pnlAddCompanyLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel34)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlAddCompanyLayout.setVerticalGroup(
            pnlAddCompanyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddCompanyLayout.createSequentialGroup()
                .addComponent(pnlAddStudent8, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jLabel34)
                .addContainerGap())
        );

        javax.swing.GroupLayout CompaniesNavLayout = new javax.swing.GroupLayout(CompaniesNav);
        CompaniesNav.setLayout(CompaniesNavLayout);
        CompaniesNavLayout.setHorizontalGroup(
            CompaniesNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CompaniesNavLayout.createSequentialGroup()
                .addComponent(pnlListCompany, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlAddCompany, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 830, Short.MAX_VALUE))
        );
        CompaniesNavLayout.setVerticalGroup(
            CompaniesNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CompaniesNavLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(CompaniesNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlListCompany, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlAddCompany, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        navHeaderHolder.add(CompaniesNav);
        CompaniesNav.setBounds(0, 0, 1030, 40);

        StudentOptions.setBackground(new java.awt.Color(255, 255, 255));
        StudentOptions.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        StudentOptions.setPreferredSize(new java.awt.Dimension(100, 40));
        StudentOptions.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                StudentOptionsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                StudentOptionsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                StudentOptionsMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                StudentOptionsMouseReleased(evt);
            }
        });

        jLabel58.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(51, 51, 51));
        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel58.setText("Student");

        pnlAddStudent7.setBackground(new java.awt.Color(23, 35, 51));
        pnlAddStudent7.setPreferredSize(new java.awt.Dimension(100, 40));
        pnlAddStudent7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlAddStudent7MouseClicked(evt);
            }
        });

        jLabel62.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(51, 51, 51));
        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel62.setText("Add");

        javax.swing.GroupLayout pnlAddStudent7Layout = new javax.swing.GroupLayout(pnlAddStudent7);
        pnlAddStudent7.setLayout(pnlAddStudent7Layout);
        pnlAddStudent7Layout.setHorizontalGroup(
            pnlAddStudent7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddStudent7Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel62)
                .addContainerGap(35, Short.MAX_VALUE))
        );
        pnlAddStudent7Layout.setVerticalGroup(
            pnlAddStudent7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAddStudent7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel62)
                .addContainerGap())
        );

        javax.swing.GroupLayout StudentOptionsLayout = new javax.swing.GroupLayout(StudentOptions);
        StudentOptions.setLayout(StudentOptionsLayout);
        StudentOptionsLayout.setHorizontalGroup(
            StudentOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StudentOptionsLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel58)
                .addContainerGap(28, Short.MAX_VALUE))
            .addGroup(StudentOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlAddStudent7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        StudentOptionsLayout.setVerticalGroup(
            StudentOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, StudentOptionsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel58)
                .addContainerGap())
            .addGroup(StudentOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(StudentOptionsLayout.createSequentialGroup()
                    .addComponent(pnlAddStudent7, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 40, Short.MAX_VALUE)))
        );

        InternshipOptions.setBackground(new java.awt.Color(220, 220, 220));
        InternshipOptions.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        InternshipOptions.setPreferredSize(new java.awt.Dimension(100, 40));
        InternshipOptions.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                InternshipOptionsMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                InternshipOptionsMouseReleased(evt);
            }
        });

        pnlAddStudent10.setBackground(new java.awt.Color(23, 35, 51));
        pnlAddStudent10.setPreferredSize(new java.awt.Dimension(100, 40));
        pnlAddStudent10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlAddStudent10MouseClicked(evt);
            }
        });

        jLabel70.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(51, 51, 51));
        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel70.setText("Add");

        javax.swing.GroupLayout pnlAddStudent10Layout = new javax.swing.GroupLayout(pnlAddStudent10);
        pnlAddStudent10.setLayout(pnlAddStudent10Layout);
        pnlAddStudent10Layout.setHorizontalGroup(
            pnlAddStudent10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddStudent10Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel70)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        pnlAddStudent10Layout.setVerticalGroup(
            pnlAddStudent10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAddStudent10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel70)
                .addContainerGap())
        );

        jLabel71.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(51, 51, 51));
        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel71.setText("Internship");

        javax.swing.GroupLayout InternshipOptionsLayout = new javax.swing.GroupLayout(InternshipOptions);
        InternshipOptions.setLayout(InternshipOptionsLayout);
        InternshipOptionsLayout.setHorizontalGroup(
            InternshipOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InternshipOptionsLayout.createSequentialGroup()
                .addComponent(pnlAddStudent10, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(InternshipOptionsLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel71)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        InternshipOptionsLayout.setVerticalGroup(
            InternshipOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InternshipOptionsLayout.createSequentialGroup()
                .addComponent(pnlAddStudent10, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jLabel71)
                .addContainerGap())
        );

        CompanyOptions.setBackground(new java.awt.Color(220, 220, 220));
        CompanyOptions.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        CompanyOptions.setPreferredSize(new java.awt.Dimension(100, 40));
        CompanyOptions.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CompanyOptionsMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CompanyOptionsMouseReleased(evt);
            }
        });

        pnlAddStudent14.setBackground(new java.awt.Color(23, 35, 51));
        pnlAddStudent14.setPreferredSize(new java.awt.Dimension(100, 40));
        pnlAddStudent14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlAddStudent14MouseClicked(evt);
            }
        });

        jLabel75.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(51, 51, 51));
        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel75.setText("Add");

        javax.swing.GroupLayout pnlAddStudent14Layout = new javax.swing.GroupLayout(pnlAddStudent14);
        pnlAddStudent14.setLayout(pnlAddStudent14Layout);
        pnlAddStudent14Layout.setHorizontalGroup(
            pnlAddStudent14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddStudent14Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel75)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        pnlAddStudent14Layout.setVerticalGroup(
            pnlAddStudent14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAddStudent14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel75)
                .addContainerGap())
        );

        jLabel76.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(51, 51, 51));
        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel76.setText("Company");

        javax.swing.GroupLayout CompanyOptionsLayout = new javax.swing.GroupLayout(CompanyOptions);
        CompanyOptions.setLayout(CompanyOptionsLayout);
        CompanyOptionsLayout.setHorizontalGroup(
            CompanyOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CompanyOptionsLayout.createSequentialGroup()
                .addComponent(pnlAddStudent14, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(CompanyOptionsLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel76)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        CompanyOptionsLayout.setVerticalGroup(
            CompanyOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CompanyOptionsLayout.createSequentialGroup()
                .addComponent(pnlAddStudent14, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jLabel76)
                .addContainerGap())
        );

        AccessibilityOptions.setBackground(new java.awt.Color(220, 220, 220));
        AccessibilityOptions.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        AccessibilityOptions.setPreferredSize(new java.awt.Dimension(100, 40));
        AccessibilityOptions.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AccessibilityOptionsMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                AccessibilityOptionsMouseReleased(evt);
            }
        });

        pnlAddStudent15.setBackground(new java.awt.Color(23, 35, 51));
        pnlAddStudent15.setPreferredSize(new java.awt.Dimension(100, 40));
        pnlAddStudent15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlAddStudent15MouseClicked(evt);
            }
        });

        jLabel77.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(51, 51, 51));
        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel77.setText("Add");

        javax.swing.GroupLayout pnlAddStudent15Layout = new javax.swing.GroupLayout(pnlAddStudent15);
        pnlAddStudent15.setLayout(pnlAddStudent15Layout);
        pnlAddStudent15Layout.setHorizontalGroup(
            pnlAddStudent15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddStudent15Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel77)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        pnlAddStudent15Layout.setVerticalGroup(
            pnlAddStudent15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAddStudent15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel77)
                .addContainerGap())
        );

        jLabel78.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(51, 51, 51));
        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel78.setText("Accessibilty");

        javax.swing.GroupLayout AccessibilityOptionsLayout = new javax.swing.GroupLayout(AccessibilityOptions);
        AccessibilityOptions.setLayout(AccessibilityOptionsLayout);
        AccessibilityOptionsLayout.setHorizontalGroup(
            AccessibilityOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AccessibilityOptionsLayout.createSequentialGroup()
                .addComponent(pnlAddStudent15, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(AccessibilityOptionsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        AccessibilityOptionsLayout.setVerticalGroup(
            AccessibilityOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AccessibilityOptionsLayout.createSequentialGroup()
                .addComponent(pnlAddStudent15, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jLabel78)
                .addContainerGap())
        );

        javax.swing.GroupLayout OptionsNavLayout = new javax.swing.GroupLayout(OptionsNav);
        OptionsNav.setLayout(OptionsNavLayout);
        OptionsNavLayout.setHorizontalGroup(
            OptionsNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OptionsNavLayout.createSequentialGroup()
                .addComponent(StudentOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(InternshipOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(CompanyOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(AccessibilityOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 630, Short.MAX_VALUE))
        );
        OptionsNavLayout.setVerticalGroup(
            OptionsNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, OptionsNavLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(OptionsNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AccessibilityOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CompanyOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(StudentOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(InternshipOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        navHeaderHolder.add(OptionsNav);
        OptionsNav.setBounds(0, 0, 1030, 40);

        jScrollPane1.setBorder(null);

        pnlDynamic.setBackground(new java.awt.Color(255, 255, 255));
        pnlDynamic.setLayout(new javax.swing.BoxLayout(pnlDynamic, javax.swing.BoxLayout.LINE_AXIS));
        jScrollPane1.setViewportView(pnlDynamic);

        javax.swing.GroupLayout DynamicContentLayout = new javax.swing.GroupLayout(DynamicContent);
        DynamicContent.setLayout(DynamicContentLayout);
        DynamicContentLayout.setHorizontalGroup(
            DynamicContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DynamicContentLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(DynamicContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1222, Short.MAX_VALUE)
                    .addComponent(navHeaderHolder, javax.swing.GroupLayout.DEFAULT_SIZE, 1222, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        DynamicContentLayout.setVerticalGroup(
            DynamicContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DynamicContentLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(navHeaderHolder, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        AuditTrail.setBackground(new java.awt.Color(255, 255, 255));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Audit Trail", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        jScrollPane6.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jTable5.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jTable5.setForeground(new java.awt.Color(102, 102, 102));
        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Log ID", "Date and Time", "Username", "Activity", "Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable5.setToolTipText("System Audit List");
        jTable5.setGridColor(new java.awt.Color(255, 255, 255));
        jTable5.setRowHeight(25);
        jTable5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable5MouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(jTable5);

        jLabel82.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(102, 102, 102));

        txtFromDate3.setBackground(new java.awt.Color(255, 255, 255));
        txtFromDate3.setForeground(new java.awt.Color(102, 102, 102));
        txtFromDate3.setDateFormatString("yyyy-MM-dd");
        txtFromDate3.setFocusable(false);
        txtFromDate3.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtFromDate3.setRequestFocusEnabled(false);
        txtFromDate3.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtFromDate3InputMethodTextChanged(evt);
            }
        });
        txtFromDate3.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtFromDate3PropertyChange(evt);
            }
        });

        jLabel83.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(102, 102, 102));
        jLabel83.setText("to");

        txtFromDate4.setBackground(new java.awt.Color(255, 255, 255));
        txtFromDate4.setForeground(new java.awt.Color(102, 102, 102));
        txtFromDate4.setDateFormatString("yyyy-MM-dd");
        txtFromDate4.setFocusable(false);
        txtFromDate4.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtFromDate4.setRequestFocusEnabled(false);
        txtFromDate4.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtFromDate4InputMethodTextChanged(evt);
            }
        });
        txtFromDate4.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtFromDate4PropertyChange(evt);
            }
        });
        txtFromDate4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFromDate4KeyReleased(evt);
            }
        });

        jLabel86.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel86.setForeground(new java.awt.Color(102, 102, 102));
        jLabel86.setText("Filter by Date:");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel86)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtFromDate4, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel83)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtFromDate3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel82, javax.swing.GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel10Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1061, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtFromDate3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtFromDate4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel86, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel83, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(448, Short.MAX_VALUE))
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                    .addGap(43, 43, 43)
                    .addComponent(jScrollPane6)
                    .addContainerGap()))
        );

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel84.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Right_Button_35px.png"))); // NOI18N
        jLabel84.setToolTipText("Next");
        jLabel84.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel84.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel84MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel84MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel84MouseExited(evt);
            }
        });

        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel85.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Prev_35px.png"))); // NOI18N
        jLabel85.setToolTipText("Previous");
        jLabel85.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel85.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel85MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel85MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel85MouseExited(evt);
            }
        });

        jLabel103.setBackground(new java.awt.Color(255, 255, 255));
        jLabel103.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel103.setForeground(new java.awt.Color(255, 51, 51));
        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout AuditTrailLayout = new javax.swing.GroupLayout(AuditTrail);
        AuditTrail.setLayout(AuditTrailLayout);
        AuditTrailLayout.setHorizontalGroup(
            AuditTrailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AuditTrailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AuditTrailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(AuditTrailLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel103, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel85, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel84, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        AuditTrailLayout.setVerticalGroup(
            AuditTrailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AuditTrailLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AuditTrailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel84, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel85, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                    .addComponent(jLabel103, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        Developers.setBackground(new java.awt.Color(255, 255, 255));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        jLabel116.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel116.setForeground(new java.awt.Color(102, 102, 102));
        jLabel116.setText("Name: Clarence A. Andaya");

        jLabel122.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel122.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/andaya.png"))); // NOI18N

        jLabel118.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel118.setForeground(new java.awt.Color(102, 102, 102));
        jLabel118.setText("Email: 143clarkz@gmail.com");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jLabel122, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel116)
                    .addComponent(jLabel118))
                .addGap(102, 102, 102))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel122, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel116)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel118)
                .addContainerGap())
        );

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));

        jLabel128.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel128.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/alba.png"))); // NOI18N

        jLabel125.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel125.setForeground(new java.awt.Color(102, 102, 102));
        jLabel125.setText("Email: albasherwin123@gmail.com");

        jLabel126.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel126.setForeground(new java.awt.Color(102, 102, 102));
        jLabel126.setText("Name: Sherwin L. Alba");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel126)
                    .addComponent(jLabel125))
                .addGap(45, 45, 45)
                .addComponent(jLabel128, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel128, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel126)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel125)
                .addGap(45, 45, 45))
        );

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));

        jLabel124.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel124.setForeground(new java.awt.Color(102, 102, 102));
        jLabel124.setText("Name: Ma. Danica D. Magtangob");

        jLabel132.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel132.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/danica.png"))); // NOI18N

        jLabel133.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel133.setForeground(new java.awt.Color(102, 102, 102));
        jLabel133.setText("Email: magtangobmadanica137@gmail.com");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(jLabel132, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel124)
                    .addComponent(jLabel133)))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(jLabel132, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel124)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel133)
                .addGap(44, 44, 44))
        );

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));

        jLabel141.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel141.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/nunez.png"))); // NOI18N

        jLabel129.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel129.setForeground(new java.awt.Color(102, 102, 102));
        jLabel129.setText("Name: John Lerry Nunez");

        jLabel149.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel149.setForeground(new java.awt.Color(102, 102, 102));
        jLabel149.setText("Email: johnlerrynunez23@gmail.com");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel129)
                    .addComponent(jLabel149))
                .addGap(36, 36, 36)
                .addComponent(jLabel141, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel141, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addComponent(jLabel129)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel149)
                .addGap(35, 35, 35))
        );

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));

        jLabel146.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel146.setForeground(new java.awt.Color(102, 102, 102));
        jLabel146.setText("Name: Noel Jaringa");

        jLabel147.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel147.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/noel.png"))); // NOI18N

        jLabel148.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel148.setForeground(new java.awt.Color(102, 102, 102));
        jLabel148.setText("Email: rayahj16@gmail.com");

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel147, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel146)
                    .addComponent(jLabel148))
                .addGap(111, 111, 111))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel147, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel146)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel148)
                .addGap(39, 39, 39))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 233, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout DevelopersLayout = new javax.swing.GroupLayout(Developers);
        Developers.setLayout(DevelopersLayout);
        DevelopersLayout.setHorizontalGroup(
            DevelopersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        DevelopersLayout.setVerticalGroup(
            DevelopersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DevelopersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(9, 9, 9))
        );

        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(DynamicContent, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlHomePage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlLogs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlUsers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(UserProfileInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 1030, Short.MAX_VALUE))
            .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(AuditTrail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Developers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlContentLayout.setVerticalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(DynamicContent, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlHomePage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlLogs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlUsers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(UserProfileInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE))
            .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(AuditTrail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Developers, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnlSideNav, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlFooter, javax.swing.GroupLayout.DEFAULT_SIZE, 1030, Short.MAX_VALUE)
                    .addComponent(pnlContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, 1200, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pnlContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(pnlFooter, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlSideNav, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void oneMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_oneMousePressed
        this.one.setSelected(true);
        this.two.setSelected(false);
        this.three.setSelected(false);
        this.four.setSelected(false);
        this.five.setSelected(false);
        this.five1.setSelected(false);
        this.six.setSelected(false);
        this.seven.setSelected(false);
        this.eight.setSelected(false);
    }//GEN-LAST:event_oneMousePressed

    private void oneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oneActionPerformed
f.ClassDisconnected.flag = false;
        DynamicContent.hide();
        pnlHomePage.show();
        UserProfileInfo.hide();
        pnlLogs.hide();
        AuditTrail.hide();
        Developers.hide();
        pnlUsers.hide();
       HideSearch();
        pnlFooterAction.setVisible(true);
        
          BarGraph();
        PieChart();
       
          jLabel23.setText(getUserCount.StartUpdate());
      jLabel17.setText(getEmployedCount.StartUpdate());
      jLabel20.setText(getCompanyCount.StartUpdate());
      jLabel3.setText(getStudentCount.StartUpdate());
        
                   
        printcompanylist = false;
        printhistorylist = false; 
      printuserrecord = false;
      printuserlist = false;
       printuserprofile = false;
       printloglist = false;
        printinternlist = false;
        printdeployedlist = false;
     printstudentlist = false;
    printstudentlog = false;
     printstudentact = false;
     printstudentrecord = false;
	 printstatistics = true;
        //new DynamicPanel(pnlDynamic, new panels.pnlHome());
        if(this.one.isSelected()){
            this.one.setColorNormal(new Color(41,57,80));
            this.one.setColorHover(new Color(41,57,80));
            this.one.setColorPressed(new Color(41,57,80));
            this.one.setBorder(bordePressed);

            this.two.setColorNormal(new Color(23,35,51));
            this.two.setColorHover(new Color(41,57,80));
            this.two.setColorPressed(new Color(41,57,80));
            this.two.setBorder(null);

             this.three.setColorNormal(new Color(23,35,51));
            this.three.setColorHover(new Color(41,57,80));
            this.three.setColorPressed(new Color(41,57,80));
            this.three.setBorder(null);
            
            this.four.setColorNormal(new Color(23,35,51));
            this.four.setColorHover(new Color(41,57,80));
            this.four.setColorPressed(new Color(41,57,80));
            this.four.setBorder(null);

             this.five.setColorNormal(new Color(23,35,51));
            this.five.setColorHover(new Color(41,57,80));
            this.five.setColorPressed(new Color(41,57,80));
            this.five.setBorder(null);
            
               this.five1.setColorNormal(new Color(23,35,51));
            this.five1.setColorHover(new Color(41,57,80));
            this.five1.setColorPressed(new Color(41,57,80));
            this.five1.setBorder(null);

             this.six.setColorNormal(new Color(23,35,51));
            this.six.setColorHover(new Color(41,57,80));
            this.six.setColorPressed(new Color(41,57,80));
            this.six.setBorder(null);

             this.seven.setColorNormal(new Color(23,35,51));
            this.seven.setColorHover(new Color(41,57,80));
            this.seven.setColorPressed(new Color(41,57,80));
            this.seven.setBorder(null);

             this.eight.setColorNormal(new Color(23,35,51));
            this.eight.setColorHover(new Color(41,57,80));
            this.eight.setColorPressed(new Color(41,57,80));
            this.eight.setBorder(null);
        }else{
           this.one.setColorNormal(new Color(23,35,51));
            this.one.setColorHover(new Color(41,57,80));
            this.one.setColorPressed(new Color(41,57,80));
            this.one.setBorder(bordePressed);
        }
    }//GEN-LAST:event_oneActionPerformed

    private void twoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_twoMousePressed
        this.one.setSelected(false);
        this.two.setSelected(true);
        this.three.setSelected(false);
        this.four.setSelected(false);
        this.five.setSelected(false);
        this.five1.setSelected(false);
        this.six.setSelected(false);
        this.seven.setSelected(false);
        this.eight.setSelected(false);
    }//GEN-LAST:event_twoMousePressed

    private void twoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_twoActionPerformed
f.ClassDisconnected.flag = false;
ShowSearch();
        pnlHomePage.hide(); 
        UserProfileInfo.hide();
        pnlLogs.hide();
        AuditTrail.hide();
        Developers.hide();
        pnlUsers.hide();
        DynamicContent.show();
        UsersNav.hide();
        CompaniesNav.hide();
        StudentsNav.show();
        OptionsNav.hide();
        pnlFooterAction.setVisible(true);
        
        if(studentlist){
            new DynamicPanel(pnlDynamic, new panels.pnlStudents());
        }else{
            new DynamicPanel(pnlDynamic, new panels.pnlStudentsAdd());
            HideSearch();
             pnlFooterAction.setVisible(false);
        }
        searchuserlist = false;  
      searchcompanylist = false;
      searchstudentlist = true;
      searchlogs = false; 
      searchintern = false;
      searchemployed = false;
        if(this.two.isSelected()){
            this.one.setColorNormal(new Color(23,35,51));
            this.one.setColorHover(new Color(41,57,80));
            this.one.setColorPressed(new Color(41,57,80));
            this.one.setBorder(null);
            
            this.two.setColorNormal(new Color(41,57,80));
            this.two.setColorHover(new Color(41,57,80));
            this.two.setColorPressed(new Color(41,57,80));
            this.two.setBorder(bordePressed);

             this.three.setColorNormal(new Color(23,35,51));
            this.three.setColorHover(new Color(41,57,80));
            this.three.setColorPressed(new Color(41,57,80));
            this.three.setBorder(null);

            this.four.setColorNormal(new Color(23,35,51));
            this.four.setColorHover(new Color(41,57,80));
            this.four.setColorPressed(new Color(41,57,80));
            this.four.setBorder(null);

             this.five.setColorNormal(new Color(23,35,51));
            this.five.setColorHover(new Color(41,57,80));
            this.five.setColorPressed(new Color(41,57,80));
            this.five.setBorder(null);
            
                 this.five1.setColorNormal(new Color(23,35,51));
            this.five1.setColorHover(new Color(41,57,80));
            this.five1.setColorPressed(new Color(41,57,80));
            this.five1.setBorder(null);

             this.six.setColorNormal(new Color(23,35,51));
            this.six.setColorHover(new Color(41,57,80));
            this.six.setColorPressed(new Color(41,57,80));
            this.six.setBorder(null);

             this.seven.setColorNormal(new Color(23,35,51));
            this.seven.setColorHover(new Color(41,57,80));
            this.seven.setColorPressed(new Color(41,57,80));
            this.seven.setBorder(null);

             this.eight.setColorNormal(new Color(23,35,51));
            this.eight.setColorHover(new Color(41,57,80));
            this.eight.setColorPressed(new Color(41,57,80));
            this.eight.setBorder(null);
        }else{
           this.two.setColorNormal(new Color(23,35,51));
            this.two.setColorHover(new Color(41,57,80));
            this.two.setColorPressed(new Color(41,57,80));
            this.two.setBorder(bordePressed);
        }
    }//GEN-LAST:event_twoActionPerformed

    private void threeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_threeMousePressed
        this.one.setSelected(false);
        this.two.setSelected(false);
        this.three.setSelected(true);
        this.four.setSelected(false);
        this.five.setSelected(false);
        this.five1.setSelected(false);
        this.six.setSelected(false);
        this.seven.setSelected(false);
        this.eight.setSelected(false);
    }//GEN-LAST:event_threeMousePressed

    private void threeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_threeActionPerformed
f.ClassDisconnected.flag = false;
ShowSearch();
        pnlHomePage.hide();   
        UserProfileInfo.hide();
        pnlLogs.hide();
        AuditTrail.hide();
        Developers.hide();
        pnlUsers.hide();
        DynamicContent.show();
        UsersNav.hide();
        CompaniesNav.show();
        StudentsNav.hide();
        OptionsNav.hide();
        pnlFooterAction.setVisible(true);
     
        if(companylist){
            jTextField1.setText("Search...");
            new DynamicPanel(pnlDynamic, new panels.pnlCompanies());
        }else{
            new DynamicPanel(pnlDynamic, new panels.pnlCompaniesAdd());
           HideSearch();
             pnlFooterAction.setVisible(false);
        }
        searchuserlist = false;  
      searchcompanylist = true;
      searchstudentlist = false; 
      searchlogs = false;
      searchintern = false;
      searchemployed = false;
        if(this.three.isSelected()){
            this.one.setBorder(null);
            this.two.setBorder(null);
            this.three.setBorder(bordePressed);
            this.four.setBorder(null);
            this.five.setBorder(null);
            this.five1.setBorder(null);
            this.six.setBorder(null);
            this.seven.setBorder(null);
            this.eight.setBorder(null);
            
            this.one.setColorNormal(new Color(23,35,51));
            this.one.setColorHover(new Color(41,57,80));
            this.one.setColorPressed(new Color(41,57,80));
            
            this.two.setColorNormal(new Color(23,35,51));
            this.two.setColorHover(new Color(41,57,80));
            this.two.setColorPressed(new Color(41,57,80));
            
            this.three.setColorNormal(new Color(41,57,80));
            this.three.setColorHover(new Color(41,57,80));
            this.three.setColorPressed(new Color(41,57,80));

            this.four.setColorNormal(new Color(23,35,51));
            this.four.setColorHover(new Color(41,57,80));
            this.four.setColorPressed(new Color(41,57,80));

             this.five.setColorNormal(new Color(23,35,51));
            this.five.setColorHover(new Color(41,57,80));
            this.five.setColorPressed(new Color(41,57,80));
            
             this.five1.setColorNormal(new Color(23,35,51));
            this.five1.setColorHover(new Color(41,57,80));
            this.five1.setColorPressed(new Color(41,57,80));

             this.six.setColorNormal(new Color(23,35,51));
            this.six.setColorHover(new Color(41,57,80));
            this.six.setColorPressed(new Color(41,57,80));

             this.seven.setColorNormal(new Color(23,35,51));
            this.seven.setColorHover(new Color(41,57,80));
            this.seven.setColorPressed(new Color(41,57,80));

             this.eight.setColorNormal(new Color(23,35,51));
            this.eight.setColorHover(new Color(41,57,80));
            this.eight.setColorPressed(new Color(41,57,80));
        }else{
           this.three.setColorNormal(new Color(23,35,51));
            this.three.setColorHover(new Color(41,57,80));
            this.three.setColorPressed(new Color(41,57,80));
        }
    }//GEN-LAST:event_threeActionPerformed

    private void fourMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fourMousePressed
        this.one.setSelected(false);
        this.two.setSelected(false);
        this.three.setSelected(false);
        this.four.setSelected(true);
        this.five.setSelected(false);
        this.five1.setSelected(false);
        this.six.setSelected(false);
        this.seven.setSelected(false);
        this.eight.setSelected(false);
    }//GEN-LAST:event_fourMousePressed

    private void fourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fourActionPerformed
f.ClassDisconnected.flag = false;
         DynamicContent.hide();
        pnlHomePage.hide();
        pnlLogs.hide();
        pnlUsers.show();
        UserProfile.hide();
        UserProfileInfo.hide();
        AuditTrail.hide();
        Developers.hide();
        UserList.show();
         ShowSearch();
        pnlFooterAction.setVisible(true);
        //getUsersList.get();
         changeAvatar = false;
          lastUserList = false;
        startUserList = 0;
         listPage = 1;    
          jLabel109.setText("Page: "+listPage);
       
          
           if(getCurrentUserListPage==listPage){
                 jTable4.setSelectionBackground(new Color(184, 207, 229));
            }else{
                 jTable4.setSelectionBackground(Color.white);
            }
         jTextField1.setText("Search...");
            String str = "";
        TableFromDatabase.TableFromDatabase(str,startUserList,endUserList);
        UsersPanel();
        if(jTable4.getSelectionModel().isSelectionEmpty()){
              changePass.setVisible(false);
        }else{
              changePass.setVisible(true);
        }
      
      searchuserlist = true;  
      searchcompanylist = false;
      searchstudentlist = false; 
      searchlogs = false;
      searchintern = false;
      searchemployed = false;
      
          printcompanylist = false;
        printhistorylist = false; 
      printuserrecord = false;
      printuserlist = true;
       printuserprofile = false;
       printloglist = false;
        printinternlist = false;
        printdeployedlist = false; 
     printstudentlist = false;
    printstudentlog = false;
     printstudentact = false;
     printstudentrecord = false;
	 printstatistics = false;
        if(this.four.isSelected()){
            this.one.setBorder(null);
            this.two.setBorder(null);
            this.three.setBorder(null);
            this.four.setBorder(bordePressed);
            this.five.setBorder(null);
            this.five1.setBorder(null);
            this.six.setBorder(null);
            this.seven.setBorder(null);
            this.eight.setBorder(null);
            
            this.one.setColorNormal(new Color(23,35,51));
            this.one.setColorHover(new Color(41,57,80));
            this.one.setColorPressed(new Color(41,57,80));
            
            this.two.setColorNormal(new Color(23,35,51));
            this.two.setColorHover(new Color(41,57,80));
            this.two.setColorPressed(new Color(41,57,80));
            
            this.three.setColorNormal(new Color(23,35,51));
            this.three.setColorHover(new Color(41,57,80));
            this.three.setColorPressed(new Color(41,57,80));

            this.four.setColorNormal(new Color(41,57,80));
            this.four.setColorHover(new Color(41,57,80));
            this.four.setColorPressed(new Color(41,57,80));

             this.five.setColorNormal(new Color(23,35,51));
            this.five.setColorHover(new Color(41,57,80));
            this.five.setColorPressed(new Color(41,57,80));
            
             this.five1.setColorNormal(new Color(23,35,51));
            this.five1.setColorHover(new Color(41,57,80));
            this.five1.setColorPressed(new Color(41,57,80));

             this.six.setColorNormal(new Color(23,35,51));
            this.six.setColorHover(new Color(41,57,80));
            this.six.setColorPressed(new Color(41,57,80));

             this.seven.setColorNormal(new Color(23,35,51));
            this.seven.setColorHover(new Color(41,57,80));
            this.seven.setColorPressed(new Color(41,57,80));

             this.eight.setColorNormal(new Color(23,35,51));
            this.eight.setColorHover(new Color(41,57,80));
            this.eight.setColorPressed(new Color(41,57,80));
        }else{
           this.four.setColorNormal(new Color(23,35,51));
            this.four.setColorHover(new Color(41,57,80));
            this.four.setColorPressed(new Color(41,57,80));
        }
    }//GEN-LAST:event_fourActionPerformed

    private void fiveMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fiveMousePressed
        this.one.setSelected(false);
        this.two.setSelected(false);
        this.three.setSelected(false);
        this.four.setSelected(false);
        this.five.setSelected(true);
        this.five1.setSelected(false);
        this.six.setSelected(false);
        this.seven.setSelected(false);
        this.eight.setSelected(false);
    }//GEN-LAST:event_fiveMousePressed

    private void fiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fiveActionPerformed
f.ClassDisconnected.flag = false;

        DynamicContent.hide();
        pnlHomePage.hide();
        pnlLogs.hide();
        UserProfileInfo.show();
        pnlUsers.hide();
         AuditTrail.hide();
        UsersNav.hide();
        CompaniesNav.hide();
        StudentsNav.hide();
        OptionsNav.show();
         //ShowSearch();
         HideSearch();
        pnlFooterAction.setVisible(true);
        
        GetProfileInfo.getPofileInfo();
        
        
        jLabel60.setText("Date Registered: "+SystemInfo.udateadded);
        jLabel61.setText(SystemInfo.userid);
        jLabel36.setText(SystemInfo.ufirstname+" "+SystemInfo.umiddlename+" "+SystemInfo.ulastname+" "+SystemInfo.usuffix);
        if(SystemInfo.gender.equals("0")){
            jLabel72.setText("Male");
        }else{
             jLabel72.setText("Female");
        }
        txtLname10.setText(SystemInfo.ucontactnum);
        txtLname11.setText(SystemInfo.uemail);
        jLabel73.setText(SystemInfo.uusername);
        
       try{
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(SystemInfo.img).getImage().getScaledInstance(jLabel45.getWidth(), jLabel45.getHeight(), Image.SCALE_SMOOTH));
                jLabel45.setIcon(imageIcon); 
        }catch(Exception e){
            jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Name_125px.png")));
            System.out.println("No Image\n"+e);
        }
       
         changeAvatar = false;
         profileAuditListStart = 0;
       profileFilterByDate = false;
         txtFromDate1.setDate(null); 
         txtFromDate2.setDate(null);
         jLabel81.setText(null);
          listPage = 1;
          jLabel104.setText("Page: "+listPage);
       getProfileAuditList();
       
   //    ((JTextField)txtFromDate1.getDateEditor().getUiComponent()).setText("From Date");
   //    ((JTextField)txtFromDate2.getDateEditor().getUiComponent()).setText("To Date");
    printcompanylist = false;
        printhistorylist = false; 
      printuserrecord = true;
      printuserlist = false;
       printuserprofile = false;
       printloglist = false;
        printinternlist = false;
        printdeployedlist = false;
     printstudentlist = false;
    printstudentlog = false;
     printstudentact = false;
     printstudentrecord = false;
	 printstatistics = false;
        if(this.five.isSelected()){
            this.one.setBorder(null);
            this.two.setBorder(null);
            this.three.setBorder(null);
            this.four.setBorder(null);
            this.five.setBorder(bordePressed);
            this.five1.setBorder(null);
            this.six.setBorder(null);
            this.seven.setBorder(null);
            this.eight.setBorder(null);
            
            this.one.setColorNormal(new Color(23,35,51));
            this.one.setColorHover(new Color(41,57,80));
            this.one.setColorPressed(new Color(41,57,80));
            
            this.two.setColorNormal(new Color(23,35,51));
            this.two.setColorHover(new Color(41,57,80));
            this.two.setColorPressed(new Color(41,57,80));
            
            this.three.setColorNormal(new Color(23,35,51));
            this.three.setColorHover(new Color(41,57,80));
            this.three.setColorPressed(new Color(41,57,80));

            this.four.setColorNormal(new Color(23,35,51));
            this.four.setColorHover(new Color(41,57,80));
            this.four.setColorPressed(new Color(41,57,80));
            
            this.five.setColorNormal(new Color(41,57,80));
            this.five.setColorHover(new Color(41,57,80));
            this.five.setColorPressed(new Color(41,57,80));

             this.five1.setColorNormal(new Color(23,35,51));
            this.five1.setColorHover(new Color(41,57,80));
            this.five1.setColorPressed(new Color(41,57,80));

             this.six.setColorNormal(new Color(23,35,51));
            this.six.setColorHover(new Color(41,57,80));
            this.six.setColorPressed(new Color(41,57,80));

             this.seven.setColorNormal(new Color(23,35,51));
            this.seven.setColorHover(new Color(41,57,80));
            this.seven.setColorPressed(new Color(41,57,80));

             this.eight.setColorNormal(new Color(23,35,51));
            this.eight.setColorHover(new Color(41,57,80));
            this.eight.setColorPressed(new Color(41,57,80));
        }else{
           this.five.setColorNormal(new Color(23,35,51));
            this.five.setColorHover(new Color(41,57,80));
            this.five.setColorPressed(new Color(41,57,80));
        }
    }//GEN-LAST:event_fiveActionPerformed

    private void sixMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sixMousePressed
        this.one.setSelected(false);
        this.two.setSelected(false);
        this.three.setSelected(false);
        this.four.setSelected(false);
        this.five.setSelected(false);
        this.five1.setSelected(false);
        this.six.setSelected(true);
        this.seven.setSelected(false);
        this.eight.setSelected(false);
    }//GEN-LAST:event_sixMousePressed

    private void sixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sixActionPerformed
f.ClassDisconnected.flag = false;
DynamicContent.hide();
        pnlHomePage.hide();
        pnlLogs.show();
        UserProfileInfo.hide();
        pnlUsers.hide();
        AuditTrail.hide();
        Developers.hide();
        ShowSearch();
        pnlFooterAction.setVisible(true);
        
           lastCompList = false; 
   isDept = false;
     isCourse = false;
    isLevel = false;
        startCompList = 0;
                endCompList = 20;
                        listPage = 1;
        getDepartment();
        getLevels();
        getCourse(); 
       getCompanyList(startCompList,endCompList);
       jLabel113.setText("Page: "+listPage); 
       jTextField1.setText("Search..."); 
        searchuserlist = false;  
      searchcompanylist = false;
      searchstudentlist = false;
      searchlogs = true;
      searchintern = false;
      searchemployed = false;
        printcompanylist = false;
        printhistorylist = false; 
      printuserrecord = false;
      printuserlist = false;
       printuserprofile = false;
       printloglist = true;
        printinternlist = false;
        printdeployedlist = false;
     printstudentlist = false;
    printstudentlog = false;
     printstudentact = false; 
     printstudentrecord = false;
	 printstatistics = false;
        if(this.six.isSelected()){
            this.one.setBorder(null);
            this.two.setBorder(null);
            this.three.setBorder(null);
            this.four.setBorder(null);
            this.five.setBorder(null);
            this.five1.setBorder(null);
            this.six.setBorder(bordePressed);
            this.seven.setBorder(null);
            this.eight.setBorder(null);
            
            this.one.setColorNormal(new Color(23,35,51));
            this.one.setColorHover(new Color(41,57,80));
            this.one.setColorPressed(new Color(41,57,80));
            
            this.two.setColorNormal(new Color(23,35,51));
            this.two.setColorHover(new Color(41,57,80));
            this.two.setColorPressed(new Color(41,57,80));
            
            this.three.setColorNormal(new Color(23,35,51));
            this.three.setColorHover(new Color(41,57,80));
            this.three.setColorPressed(new Color(41,57,80));

            this.four.setColorNormal(new Color(23,35,51));
            this.four.setColorHover(new Color(41,57,80));
            this.four.setColorPressed(new Color(41,57,80));
            
            this.five.setColorNormal(new Color(23,35,51));
            this.five.setColorHover(new Color(41,57,80));
            this.five.setColorPressed(new Color(41,57,80));
            
              this.five1.setColorNormal(new Color(23,35,51));
            this.five1.setColorHover(new Color(41,57,80));
            this.five1.setColorPressed(new Color(41,57,80));
            
            this.six.setColorNormal(new Color(41,57,80));
            this.six.setColorHover(new Color(41,57,80));
            this.six.setColorPressed(new Color(41,57,80));

             this.seven.setColorNormal(new Color(23,35,51));
            this.seven.setColorHover(new Color(41,57,80));
            this.seven.setColorPressed(new Color(41,57,80));

             this.eight.setColorNormal(new Color(23,35,51));
            this.eight.setColorHover(new Color(41,57,80));
            this.eight.setColorPressed(new Color(41,57,80));
        }else{
           this.six.setColorNormal(new Color(23,35,51));
            this.six.setColorHover(new Color(41,57,80));
            this.six.setColorPressed(new Color(41,57,80));
        }
    }//GEN-LAST:event_sixActionPerformed

    private void sevenMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sevenMousePressed
        this.one.setSelected(false);
        this.two.setSelected(false);
        this.three.setSelected(false);
        this.four.setSelected(false);
        this.five.setSelected(false);
        this.five1.setSelected(false);
        this.six.setSelected(false);
        this.seven.setSelected(true);
        this.eight.setSelected(false);
    }//GEN-LAST:event_sevenMousePressed

    private void sevenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sevenActionPerformed
f.ClassDisconnected.flag = false;
HideSearch();
        pnlHomePage.hide(); 
        UserProfileInfo.hide();
        pnlLogs.hide();
        AuditTrail.hide();
        Developers.hide();
        pnlUsers.hide();
        DynamicContent.show();
        UsersNav.hide();
        CompaniesNav.hide();
        StudentsNav.hide();
        OptionsNav.show();
        pnlFooterAction.setVisible(false);
        
    
        
        if (studentoptions) {
            new DynamicPanel(pnlDynamic, new panels.pnlStudentsOptions());
        } else if(internshipoptions){
            new DynamicPanel(pnlDynamic, new panels.pnlInternshipOptions());
        } else if(companyoptions){
             new DynamicPanel(pnlDynamic, new panels.pnlCompanyOptions());
        } else if(accessibilityoptions){
             new DynamicPanel(pnlDynamic, new panels.pnlSystemAccessibility());
        }

        if(this.seven.isSelected()){
            this.one.setBorder(null);
            this.two.setBorder(null);
            this.three.setBorder(null);
            this.four.setBorder(null);
            this.five.setBorder(null);
            this.five1.setBorder(null);
            this.six.setBorder(null);
            this.seven.setBorder(bordePressed);
            this.eight.setBorder(null);
            
            this.one.setColorNormal(new Color(23,35,51));
            this.one.setColorHover(new Color(41,57,80));
            this.one.setColorPressed(new Color(41,57,80));
            
            this.two.setColorNormal(new Color(23,35,51));
            this.two.setColorHover(new Color(41,57,80));
            this.two.setColorPressed(new Color(41,57,80));
            
            this.three.setColorNormal(new Color(23,35,51));
            this.three.setColorHover(new Color(41,57,80));
            this.three.setColorPressed(new Color(41,57,80));

            this.four.setColorNormal(new Color(23,35,51));
            this.four.setColorHover(new Color(41,57,80));
            this.four.setColorPressed(new Color(41,57,80));
            
            this.five.setColorNormal(new Color(23,35,51));
            this.five.setColorHover(new Color(41,57,80));
            this.five.setColorPressed(new Color(41,57,80));
            
            this.five1.setColorNormal(new Color(23,35,51));
            this.five1.setColorHover(new Color(41,57,80));
            this.five1.setColorPressed(new Color(41,57,80));
            
            
             this.six.setColorNormal(new Color(23,35,51));
            this.six.setColorHover(new Color(41,57,80));
            this.six.setColorPressed(new Color(41,57,80));

            this.seven.setColorNormal(new Color(41,57,80));
            this.seven.setColorHover(new Color(41,57,80));
            this.seven.setColorPressed(new Color(41,57,80));
            
             this.eight.setColorNormal(new Color(23,35,51));
            this.eight.setColorHover(new Color(41,57,80));
            this.eight.setColorPressed(new Color(41,57,80));
        }else{
           this.seven.setColorNormal(new Color(23,35,51));
            this.seven.setColorHover(new Color(41,57,80));
            this.seven.setColorPressed(new Color(41,57,80));
        }
    }//GEN-LAST:event_sevenActionPerformed

    private void eightMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eightMousePressed
        this.one.setSelected(false);
        this.two.setSelected(false);
        this.three.setSelected(false);
        this.four.setSelected(false);
        this.five.setSelected(false);
        this.five1.setSelected(false);
        this.six.setSelected(false);
        this.seven.setSelected(false);
        this.eight.setSelected(true);
    }//GEN-LAST:event_eightMousePressed

    private void eightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eightActionPerformed
f.ClassDisconnected.flag = false;
DynamicContent.hide();
        pnlHomePage.hide();
        pnlLogs.hide();
        UserProfileInfo.hide();
        pnlUsers.hide();
        AuditTrail.show(); 
        Developers.hide();
         //ShowSearch();
         HideSearch();
        pnlFooterAction.setVisible(true);
        auditListStart = 0;
         auditFilterByDate = false;
         txtFromDate4.setDate(null);
         txtFromDate3.setDate(null);
         jLabel82.setText(null);
          listPage = 1;
                     jLabel103.setText("Page: "+listPage);
                getAuditList();
      
                
        printcompanylist = false;
        printhistorylist = true; 
      printuserrecord = false;
      printuserlist = false;
       printuserprofile = false;
       printloglist = false;
        printinternlist = false;
        printdeployedlist = false;
     printstudentlist = false;
    printstudentlog = false;
     printstudentact = false;
     printstudentrecord = false;
	 printstatistics = false;
        if(this.eight.isSelected()){
            this.one.setBorder(null);
            this.two.setBorder(null);
            this.three.setBorder(null);
            this.four.setBorder(null);
            this.five.setBorder(null);
            this.five1.setBorder(null);
            this.six.setBorder(null);
            this.seven.setBorder(null);
            this.eight.setBorder(bordePressed);
            
            this.one.setColorNormal(new Color(23,35,51));
            this.one.setColorHover(new Color(41,57,80));
            this.one.setColorPressed(new Color(41,57,80));
            
            this.two.setColorNormal(new Color(23,35,51));
            this.two.setColorHover(new Color(41,57,80));
            this.two.setColorPressed(new Color(41,57,80));
            
            this.three.setColorNormal(new Color(23,35,51));
            this.three.setColorHover(new Color(41,57,80));
            this.three.setColorPressed(new Color(41,57,80));

            this.four.setColorNormal(new Color(23,35,51));
            this.four.setColorHover(new Color(41,57,80));
            this.four.setColorPressed(new Color(41,57,80));
            
            this.five.setColorNormal(new Color(23,35,51));
            this.five.setColorHover(new Color(41,57,80));
            this.five.setColorPressed(new Color(41,57,80));
            
               
            this.five1.setColorNormal(new Color(23,35,51));
            this.five1.setColorHover(new Color(41,57,80));
            this.five1.setColorPressed(new Color(41,57,80));
            
             this.six.setColorNormal(new Color(23,35,51));
            this.six.setColorHover(new Color(41,57,80));
            this.six.setColorPressed(new Color(41,57,80));
            
            this.seven.setColorNormal(new Color(23,35,51));
            this.seven.setColorHover(new Color(41,57,80));
            this.seven.setColorPressed(new Color(41,57,80));
            
            this.eight.setColorNormal(new Color(41,57,80));
            this.eight.setColorHover(new Color(41,57,80));
            this.eight.setColorPressed(new Color(41,57,80));
            
             
        }else{
           this.eight.setColorNormal(new Color(23,35,51));
            this.eight.setColorHover(new Color(41,57,80));
            this.eight.setColorPressed(new Color(41,57,80));
        }
    }//GEN-LAST:event_eightActionPerformed

    private void jLabel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseEntered
 jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Logout_Rounded_Left_40px_2.png")));        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel2MouseEntered

    private void jLabel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseExited
jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Logout_Rounded_Left_32px_1.png")));        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel2MouseExited

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked

          LookAndFeel old = UIManager.getLookAndFeel();
                try {
                    UIManager.setLookAndFeel(
                            UIManager.getSystemLookAndFeelClassName());
                } catch(Exception e) {
                    e.printStackTrace();
                }
        int selectedOption = JOptionPane.showConfirmDialog(null, "Are you sure?", "Logout",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
       try {
            UIManager.setLookAndFeel(old);
         }  catch(Exception e) {
                    e.printStackTrace();
         }
        if (selectedOption==JOptionPane.YES_OPTION){
            insertLogoutAudit();
            /*
             CDPOSystem s   = new CDPOSystem();
                s.login = false;
           s.Load();
           this.dispose();
            */
        }
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jTextField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusGained
 if(jTextField1.getText().equals("Search...")){
       jTextField1.setText("");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1FocusGained

    private void jTextField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusLost
if(jTextField1.getText().equals("")){
        jTextField1.setText("Search...");
        }            // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1FocusLost

    private void jTextField1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseClicked
if(jTextField1.getText().equals("Search...")){
        jTextField1.setText("");
        }           // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1MouseClicked

    private void jLabel7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseEntered
 jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Search_25px.png")));        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel7MouseEntered

    private void jLabel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseExited
 jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search_20px_1.png")));          // TODO add your handling code here:
    }//GEN-LAST:event_jLabel7MouseExited

    private void jLabel11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseEntered
  jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Print_40px.png")));      // TODO add your handling code here:
    }//GEN-LAST:event_jLabel11MouseEntered

    private void jLabel11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseExited
    jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Print_32px.png")));    // TODO add your handling code here:
    }//GEN-LAST:event_jLabel11MouseExited

    private void pnlListStudentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlListStudentMouseClicked
/*studentlist = true;
        new DynamicPanel(pnlDynamic, new panels.pnlStudents());
 ShowSearch();
pnlFooterAction.show();
        pnlListStudent.setBackground(new Color(255,255,255));
      pnlAddStudent2.show();
      pnlAddStudent.setBackground(new Color(220,220,220));
      pnlAddStudent1.hide();
        */
    }//GEN-LAST:event_pnlListStudentMouseClicked

    private void pnlListStudentMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlListStudentMouseExited

    }//GEN-LAST:event_pnlListStudentMouseExited

    private void pnlListStudentMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlListStudentMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlListStudentMouseEntered

    private void pnlAddStudentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAddStudentMouseClicked
/*studentlist = false;
//if(staticStudentAddNavClick==false){
new DynamicPanel(pnlDynamic, new panels.pnlStudentsAdd());
HideSearch();
pnlFooterAction.hide();
// staticStudentAddNavClick = true;
//}
      pnlListStudent.setBackground(new Color(220,220,220));
      pnlAddStudent2.hide();
      pnlAddStudent.setBackground(new Color(255,255,255));
      pnlAddStudent1.show();
        */
    }//GEN-LAST:event_pnlAddStudentMouseClicked

    private void pnlAddStudent1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAddStudent1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlAddStudent1MouseClicked

    private void pnlAddStudent2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAddStudent2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlAddStudent2MouseClicked

    private void pnlAddStudent3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAddStudent3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlAddStudent3MouseClicked

    private void pnlListStudent1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlListStudent1MouseClicked
    
    }//GEN-LAST:event_pnlListStudent1MouseClicked

    private void pnlListStudent1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlListStudent1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlListStudent1MouseEntered

    private void pnlListStudent1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlListStudent1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlListStudent1MouseExited

    private void pnlAddStudent5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAddStudent5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlAddStudent5MouseClicked

    private void pnlAddStudent4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAddStudent4MouseClicked
  // TODO add your handling code here:
    }//GEN-LAST:event_pnlAddStudent4MouseClicked

    private void pnlAddStudent6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAddStudent6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlAddStudent6MouseClicked

    private void pnlListCompanyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlListCompanyMouseClicked
       
    }//GEN-LAST:event_pnlListCompanyMouseClicked

    private void pnlListCompanyMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlListCompanyMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlListCompanyMouseEntered

    private void pnlListCompanyMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlListCompanyMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlListCompanyMouseExited

    private void pnlAddStudent8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAddStudent8MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlAddStudent8MouseClicked

    private void pnlAddCompanyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAddCompanyMouseClicked
       // TODO add your handling code here:
    }//GEN-LAST:event_pnlAddCompanyMouseClicked

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
if(isDept){
    startCompList = 0;
        getCompanyList(startCompList,endCompList); 
}   
        //  DeptBox();     // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ItemStateChanged

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox4ItemStateChanged
if(isCourse){
    startCompList = 0;
        getCompanyList(startCompList,endCompList); 
}          //   CourseBox();  // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4ItemStateChanged

    private void jComboBox5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox5ItemStateChanged
if(isLevel){
    startCompList = 0;
        getCompanyList(startCompList,endCompList); 
}             //     YearBox();                 // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox5ItemStateChanged

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jTable4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MouseClicked
       
          
    }//GEN-LAST:event_jTable4MouseClicked

    private void txtFnameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFnameFocusGained
        if(txtFname.getText().equals("Firstname")){
            txtFname.setText("");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtFnameFocusGained

    private void txtFnameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFnameFocusLost
        if(txtFname.getText().equals("")){
            txtFname.setText("Firstname");
        }                // TODO add your handling code here:
    }//GEN-LAST:event_txtFnameFocusLost

    private void txtFnameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtFnameMouseClicked
        if(txtFname.getText().equals("Firstname")){
            txtFname.setText("");
        }         
    }//GEN-LAST:event_txtFnameMouseClicked

    private void txtFnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFnameActionPerformed

    private void txtMnameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMnameFocusGained
        if(txtMname.getText().equals("Middlename")){
            txtMname.setText("");
        }
    }//GEN-LAST:event_txtMnameFocusGained

    private void txtMnameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMnameFocusLost
        // TODO add your handling code here:
        if(txtMname.getText().equals("")){
            txtMname.setText("Middlename");
        }
    }//GEN-LAST:event_txtMnameFocusLost

    private void txtMnameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMnameMouseClicked
        if(txtMname.getText().equals("Middlename")){
            txtMname.setText("");
        }
    }//GEN-LAST:event_txtMnameMouseClicked

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
      
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void txtLnameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLnameFocusGained
       if(txtLname.getText().equals("Lastname")){
            txtLname.setText("");
        }       // TODO add your handling code here:
    }//GEN-LAST:event_txtLnameFocusGained

    private void txtLnameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLnameFocusLost
  if (txtLname.getText().equals("")) {
            txtLname.setText("Lastname");
        }               // TODO add your handling code here:
    }//GEN-LAST:event_txtLnameFocusLost

    private void txtLnameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtLnameMouseClicked
  if(txtLname.getText().equals("Lastname")){
            txtLname.setText("");
        }     
    }//GEN-LAST:event_txtLnameMouseClicked

    private void jLabel46MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel46MouseEntered
  jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Right_Button_37px_1.png")));        // TODO add your handling code here:
              // TODO add your handling code here:
    }//GEN-LAST:event_jLabel46MouseEntered

    private void jLabel46MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel46MouseExited
       jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Right_Button_35px.png")));
    }//GEN-LAST:event_jLabel46MouseExited

    private void jLabel47MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel47MouseEntered
   jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Prev_37px.png")));        // TODO add your handling code here:
            // TODO add your handling code here:
    }//GEN-LAST:event_jLabel47MouseEntered

    private void jLabel47MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel47MouseExited
     jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Prev_35px.png")));
    }//GEN-LAST:event_jLabel47MouseExited

    private void txtSuffixFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSuffixFocusGained
 if (txtSuffix.getText().equals("Suffix")) {
            txtSuffix.setText("");
        }            // TODO add your handling code here:
    }//GEN-LAST:event_txtSuffixFocusGained

    private void txtSuffixFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSuffixFocusLost
     if (txtSuffix.getText().equals("")) {
            txtSuffix.setText("Suffix");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtSuffixFocusLost

    private void txtSuffixMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSuffixMouseClicked
 if (txtSuffix.getText().equals("Suffix")) {
            txtSuffix.setText("");
        }            // TODO add your handling code here:
    }//GEN-LAST:event_txtSuffixMouseClicked

    private void txtSuffixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSuffixActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSuffixActionPerformed

    private void txtUsernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsernameFocusGained
   if (txtUsername.getText().equals("Username")) {
            txtUsername.setText("");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameFocusGained

    private void txtUsernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsernameFocusLost
    if (txtUsername.getText().equals("")) {
            txtUsername.setText("Username");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameFocusLost

    private void txtUsernameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUsernameMouseClicked
   if (txtUsername.getText().equals("Username")) {
            txtUsername.setText("");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameMouseClicked

    private void txtContactNumberFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtContactNumberFocusGained
   if (txtContactNumber.getText().equals("Contact Number")) {
            txtContactNumber.setText("");
        }             // TODO add your handling code here:
    }//GEN-LAST:event_txtContactNumberFocusGained

    private void txtContactNumberFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtContactNumberFocusLost
    if (txtContactNumber.getText().equals("")) {
            txtContactNumber.setText("Contact Number");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtContactNumberFocusLost

    private void txtContactNumberMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtContactNumberMouseClicked
   if (txtContactNumber.getText().equals("Contact Number")) {
            txtContactNumber.setText("");
        }             // TODO add your handling code here:
    }//GEN-LAST:event_txtContactNumberMouseClicked

    private void txtEmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusGained
if (txtEmail.getText().equals("Email")) {
            txtEmail.setText("");
        }          // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailFocusGained

    private void txtEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusLost
            if (txtEmail.getText().equals("")) {
            txtEmail.setText("Email");
        }
    }//GEN-LAST:event_txtEmailFocusLost

    private void txtEmailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtEmailMouseClicked
if (txtEmail.getText().equals("Email")) {
            txtEmail.setText("");
        }            // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailMouseClicked

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

   
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
          LookAndFeel old = UIManager.getLookAndFeel();
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
        
        
        boolean noDot = false, noAt = false;
        if (!txtEmail.getText().equals("Email")) {
            noDot = true;
            noAt = true;
            String email = txtEmail.getText();
            int emailCount = email.length();
            for (int i = 0; i < emailCount; i++) {
                if (email.charAt(i) == '.') {
                    noDot = false;
                }
                if (email.charAt(i) == '@') {
                    noAt = false;
                }
            }
        }
      
        if (txtFname.getText().equals("Firstname")) {

            JOptionPane.showMessageDialog(null, "Firstname is required...");

            txtFname.requestFocus();
        } else if (txtLname.getText().equals("Lastname")) {

            JOptionPane.showMessageDialog(null, "Lastname is required...");

            txtLname.requestFocus();
        } else   if (txtContactNumber.getText().equals("Contact Number")) {

            JOptionPane.showMessageDialog(null, "Contact Number is required...");

            txtContactNumber.requestFocus();
        }else if (txtContactNumber.getText().length() <= 3) {

            JOptionPane.showMessageDialog(null, "Invalid Contact Number");

            txtContactNumber.requestFocus();
        } else if(txtEmail.getText().equals("Email")){
            JOptionPane.showMessageDialog(null, "Email is required...");

            txtEmail.requestFocus();
            
        }else if (noDot || noAt) {
            JOptionPane.showMessageDialog(null, "Email is not valid...");
            txtEmail.requestFocus();
        } else if (!txtEmail.getText().equals("Email") && txtEmail.getText().length() <= 8) {
            JOptionPane.showMessageDialog(null, "Email is not valid...");
            txtEmail.requestFocus();
        } else if (txtUsername.getText().equals("Username")) {

            JOptionPane.showMessageDialog(null, "Username is required...");

            txtUsername.requestFocus();
        } else if (txtUsername.getText().length() <= 4) {

            JOptionPane.showMessageDialog(null, "Username must 5 characters and above");

            txtUsername.requestFocus();
        }  else { 
           //  JOptionPane.showMessageDialog(null, "Nice");
          processUserProfileUpdate();
          

        }
        try {
            UIManager.setLookAndFeel(old);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jLabel52MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel52MouseEntered
        jLabel52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Prev_37px.png")));        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel52MouseEntered

    private void jLabel52MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel52MouseExited
        jLabel52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Prev_35px.png")));
    }//GEN-LAST:event_jLabel52MouseExited

    private void jLabel53MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel53MouseEntered
        jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Right_Button_37px_1.png")));        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel53MouseEntered

    private void jLabel53MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel53MouseExited
        jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Right_Button_35px.png")));
    }//GEN-LAST:event_jLabel53MouseExited

    private void jLabel54MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel54MouseEntered
          jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Right_Button_37px_1.png")));        // TODO add your handling code here:
     
    }//GEN-LAST:event_jLabel54MouseEntered

    private void jLabel54MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel54MouseExited
        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Right_Button_35px.png")));
   
    }//GEN-LAST:event_jLabel54MouseExited

    private void jLabel55MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel55MouseEntered
   jLabel55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Prev_37px.png")));        // TODO add your handling code here:
              // TODO add your handling code here:
    }//GEN-LAST:event_jLabel55MouseEntered

    private void jLabel55MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel55MouseExited
     jLabel55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Prev_35px.png")));    // TODO add your handling code here:
    }//GEN-LAST:event_jLabel55MouseExited

    private void txtLname10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLname10FocusGained
if (txtLname10.getText().equals("Contact Number")) {
            txtLname10.setText("");
        }         // TODO add your handling code here:
    }//GEN-LAST:event_txtLname10FocusGained

    private void txtLname10FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLname10FocusLost
   if (txtLname10.getText().equals("")) {
            txtLname10.setText("Contact Number");
        }         // TODO add your handling code here:
    }//GEN-LAST:event_txtLname10FocusLost

    private void txtLname10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtLname10MouseClicked
if (txtLname10.getText().equals("Contact Number")) {
            txtLname10.setText("");
        } 
    }//GEN-LAST:event_txtLname10MouseClicked

    private void txtLname11FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLname11FocusGained
if (txtLname11.getText().equals("Email")) {
            txtLname11.setText("");
        }         // TODO add your handling code here:
    }//GEN-LAST:event_txtLname11FocusGained

    private void txtLname11FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLname11FocusLost
  if (txtLname11.getText().equals("")) {
            txtLname11.setText("Email");
        }             // TODO add your handling code here:
    }//GEN-LAST:event_txtLname11FocusLost

    private void txtLname11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtLname11MouseClicked
if (txtLname11.getText().equals("Email")) {
            txtLname11.setText("");
        }         // TODO add your handling code here:
    }//GEN-LAST:event_txtLname11MouseClicked

    private void txtLname11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLname11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLname11ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
  UIManagers.getNewUI(); 
        boolean noDot = false, noAt = false;
        if (!txtLname11.getText().equals("Email")) {
            noDot = true;
            noAt = true;
            String email = txtLname11.getText();
            int emailCount = email.length();
            for (int i = 0; i < emailCount; i++) {
                if (email.charAt(i) == '.') {
                    noDot = false;
                }
                if (email.charAt(i) == '@') {
                    noAt = false;
                }
            }
        } 
        if (txtLname10.getText().equals("Contact Number")) {
            JOptionPane.showMessageDialog(null, "Contact Number is required...");
            txtLname10.requestFocus();
        }else if (txtLname10.getText().length() <= 3) {
            JOptionPane.showMessageDialog(null, "Invalid Contact Number");
            txtLname10.requestFocus();
        } else if(txtLname11.getText().equals("Email")){
            JOptionPane.showMessageDialog(null, "Email is required...");
            txtLname11.requestFocus();
        }else if (noDot || noAt) {
            JOptionPane.showMessageDialog(null, "Email is not valid...");
            txtLname11.requestFocus();
        } else if (!txtLname11.getText().equals("Email") && txtLname11.getText().length() <= 8) {
            JOptionPane.showMessageDialog(null, "Email is not valid...");
            txtLname11.requestFocus();
        }  else {
            
            
            processProfileUpdate();
    
        }
       UIManagers.applyOldUI();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable3MouseClicked

    private void jLabel67MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel67MouseEntered
    jLabel67.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Right_Button_37px_1.png")));   
    }//GEN-LAST:event_jLabel67MouseEntered

    private void jLabel67MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel67MouseExited
        jLabel67.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Right_Button_35px.png")));
    }//GEN-LAST:event_jLabel67MouseExited

    private void jLabel68MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel68MouseEntered
  jLabel68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Prev_37px.png")));          // TODO add your handling code here:
    }//GEN-LAST:event_jLabel68MouseEntered

    private void jLabel68MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel68MouseExited
      jLabel68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Prev_35px.png")));
    }//GEN-LAST:event_jLabel68MouseExited

    private void jTable5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable5MouseClicked

    private void jLabel84MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel84MouseEntered
      jLabel84.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Right_Button_37px_1.png")));      
    }//GEN-LAST:event_jLabel84MouseEntered

    private void jLabel84MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel84MouseExited
        jLabel84.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Right_Button_35px.png")));
    }//GEN-LAST:event_jLabel84MouseExited

    private void jLabel85MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel85MouseEntered
 jLabel85.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Prev_37px.png")));        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel85MouseEntered

    private void jLabel85MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel85MouseExited
      jLabel85.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Prev_35px.png")));
    }//GEN-LAST:event_jLabel85MouseExited

    private void pnlAddStudent7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAddStudent7MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlAddStudent7MouseClicked

    private void StudentOptionsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StudentOptionsMouseClicked

      
    }//GEN-LAST:event_StudentOptionsMouseClicked

    private void StudentOptionsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StudentOptionsMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_StudentOptionsMouseEntered

    private void StudentOptionsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StudentOptionsMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_StudentOptionsMouseExited

    private void pnlAddStudent10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAddStudent10MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlAddStudent10MouseClicked

    private void InternshipOptionsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_InternshipOptionsMouseClicked
 
    }//GEN-LAST:event_InternshipOptionsMouseClicked

    private void pnlAddStudent14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAddStudent14MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlAddStudent14MouseClicked

    private void CompanyOptionsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CompanyOptionsMouseClicked
 
    }//GEN-LAST:event_CompanyOptionsMouseClicked

    private void txtMnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMnameActionPerformed

    private void pnlAddStudent15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAddStudent15MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlAddStudent15MouseClicked

    private void AccessibilityOptionsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AccessibilityOptionsMouseClicked
       // TODO add your handling code here:
    }//GEN-LAST:event_AccessibilityOptionsMouseClicked

    private void jPasswordField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordField1ActionPerformed

    private void jLabel45MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel45MouseClicked
   LookAndFeel old = UIManager.getLookAndFeel();
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
                JFileChooser fileChooser = new JFileChooser();
                 fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
     fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpeg","jpg","png"));
                //fileChooser.showOpenDialog(null);
                 int result=fileChooser.showSaveDialog(null);
                 if(result==JFileChooser.APPROVE_OPTION){
        File selectedFile = fileChooser.getSelectedFile();
        String path = selectedFile.getAbsolutePath();
        lblImage.setIcon(ResizeImage(path));
                 }
         */

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
                    jLabel45.setIcon(ResizeImage(path));
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

        try {
            UIManager.setLookAndFeel(old);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel45MouseClicked

    private void jPasswordField1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPasswordField1MouseClicked
    if( jPasswordField1.getText().equals("Change Password")){
   jPasswordField1.setText(""); 
   jPasswordField1.setEchoChar('*');
 jPasswordField1.setFont(new java.awt.Font("Segoe UI", 1, 14));
}
    }//GEN-LAST:event_jPasswordField1MouseClicked

    private void jPasswordField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordField1FocusGained
  
 if( jPasswordField1.getText().equals("Change Password")){
   jPasswordField1.setText(""); 
   jPasswordField1.setEchoChar('*');
 jPasswordField1.setFont(new java.awt.Font("Segoe UI", 1, 14));
}
    }//GEN-LAST:event_jPasswordField1FocusGained

    private void jPasswordField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordField1FocusLost
 if( jPasswordField1.getText().equals("")){
   jPasswordField1.setText("Change Password");
   jPasswordField1.setEchoChar((char)0);
    jPasswordField1.setFont(new java.awt.Font("Segoe UI", 0, 14));
    }  
    }//GEN-LAST:event_jPasswordField1FocusLost

    private void jPasswordField2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordField2FocusLost
 if( jPasswordField2.getText().equals("")){
   jPasswordField2.setText("Repeat Password");
   jPasswordField2.setEchoChar((char)0);
    jPasswordField2.setFont(new java.awt.Font("Segoe UI", 0, 14));
    }  
    }//GEN-LAST:event_jPasswordField2FocusLost

    private void jPasswordField2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordField2FocusGained
 
 if( jPasswordField2.getText().equals("Repeat Password")){
   jPasswordField2.setText(""); 
   jPasswordField2.setEchoChar('*');
 jPasswordField2.setFont(new java.awt.Font("Segoe UI", 1, 14));
 }
    }//GEN-LAST:event_jPasswordField2FocusGained

    private void jPasswordField2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPasswordField2MouseClicked
 if( jPasswordField2.getText().equals("Repeat Password")){
   jPasswordField2.setText(""); 
   jPasswordField2.setEchoChar('*');
 jPasswordField2.setFont(new java.awt.Font("Segoe UI", 1, 14));
 }
    }//GEN-LAST:event_jPasswordField2MouseClicked

    private void fiveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fiveMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_fiveMouseClicked

    private void jLabel84MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel84MouseClicked
        if(!AuditListLast){
            auditListStart+=auditlistEnd;
            String sql = "";
         if(auditFilterByDate){
             String getFromDate = ((JTextField)txtFromDate4.getDateEditor().getUiComponent()).getText();
          String getToDate = ((JTextField)txtFromDate3.getDateEditor().getUiComponent()).getText();  
             sql = "SELECT * FROM `audit` where  date_time>='"+getFromDate+" 00:00:00' AND  date_time < '"+getToDate+" 23:59:59' LIMIT "+auditListStart+","+auditlistEnd+"";
         }else{
             sql = "SELECT * FROM `audit` LIMIT "+auditListStart+","+auditlistEnd+"";
         }
            try{
                conn = SQLCon.ConnectDB();
                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();
                if(rs.next()){
                    listPage++;
                    AuditListLast = false;
                    String get  = "";
                    if(auditFilterByDate){
                        get = "ByDate";
                        getAuditListByDate();
                    }else{
                         get = "ByList";
                    getAuditList();
                    }
            jLabel103.setText("Page: "+listPage);
            System.out.println(AuditListLast+ " "+get+" "+auditListStart);
                }else{
                    LookAndFeel old = UIManager.getLookAndFeel();
                    try {
                        UIManager.setLookAndFeel(
                            UIManager.getSystemLookAndFeelClassName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    AuditListLast = true;
                    auditListStart-=auditlistEnd;
                    
                   // JOptionPane.showMessageDialog(null, "This is the last page...");
                   jLabel103.setText("Already in last page... "+listPage);
                    try {
                        UIManager.setLookAndFeel(old);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                conn.close();
            }catch (Exception e) {
                DC();
            e.printStackTrace();
                //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1017", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            LookAndFeel old = UIManager.getLookAndFeel();
            try {
                UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        //    JOptionPane.showMessageDialog(null, "This is the last page....");
                   jLabel103.setText("Already in last page... "+listPage);

            try {
                UIManager.setLookAndFeel(old);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel84MouseClicked

    private void jLabel85MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel85MouseClicked
        LookAndFeel old = UIManager.getLookAndFeel();
        try {
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(auditListStart<0 || auditListStart==0){
          //  JOptionPane.showMessageDialog(null, "This is the first page...");
            jLabel103.setText("Already in first page... "+listPage);
        }else{
            listPage--;
            AuditListLast = false;
            auditListStart-=auditlistEnd;
            if(auditFilterByDate){
            getAuditListByDate();
            }else{
            getAuditList();
            }
            jLabel103.setText("Page: "+listPage);

        }

        try {
            UIManager.setLookAndFeel(old);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jLabel85MouseClicked

    private void jLabel68MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel68MouseClicked
        LookAndFeel old = UIManager.getLookAndFeel();
        try {
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(profileAuditListStart<0 || profileAuditListStart==0){
           // JOptionPane.showMessageDialog(null, "This is the first page...");
              jLabel104.setText("Already in first page... "+listPage);
        }else{
            listPage--;
            profileAuditLast = false;
            profileAuditListStart-=auditlistEnd;
            if(profileFilterByDate){
                getProfileAuditListByDate();
            }else{
                 getProfileAuditList();
            }
           
            jLabel104.setText("Page: "+listPage);
        }

        try {
            UIManager.setLookAndFeel(old);
        } catch (Exception e) {
            e.printStackTrace();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel68MouseClicked

    private void jTable4InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTable4InputMethodTextChanged
  
                // TODO add your handling code here:
    }//GEN-LAST:event_jTable4InputMethodTextChanged

    private void jTable4PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTable4PropertyChange
   // TODO add your handling code here:
    }//GEN-LAST:event_jTable4PropertyChange

    private void jLabel67MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel67MouseClicked
if(!profileAuditLast){
        profileAuditListStart+=auditlistEnd;
        String sql = "";
         if(profileFilterByDate){
             String getFromDate = ((JTextField)txtFromDate1.getDateEditor().getUiComponent()).getText();
          String getToDate = ((JTextField)txtFromDate2.getDateEditor().getUiComponent()).getText();  
          sql = "SELECT * FROM audit where u_information_id='"+SystemInfo.userid+"' and date_time>='"+getFromDate+" 00:00:00' AND  date_time < '"+getToDate+" 23:59:59' LIMIT "+profileAuditListStart+","+auditlistEnd+"";
           }else{ 
             sql = "SELECT * FROM `audit` where u_information_id='"+SystemInfo.userid+"' LIMIT "+profileAuditListStart+","+auditlistEnd+"";
          }
            try{
                conn = SQLCon.ConnectDB();
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery();
            if(rs.next()){
                    listPage++;
              profileAuditLast = false;
               if(profileFilterByDate){
                getProfileAuditListByDate();
            }else{
        getProfileAuditList();   
               }
            jLabel104.setText("Page: "+listPage);
            }else{
                LookAndFeel old = UIManager.getLookAndFeel();
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
              profileAuditLast = true;
              profileAuditListStart-=auditlistEnd;
        //    JOptionPane.showMessageDialog(null, "This is the last page...");
              jLabel104.setText("Already in last page... "+listPage);
        try {
            UIManager.setLookAndFeel(old);
        } catch (Exception e) {
            e.printStackTrace();
        }
            }
            conn.close();
        }catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1019", JOptionPane.ERROR_MESSAGE);
        }
        
}else{
      LookAndFeel old = UIManager.getLookAndFeel();
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
            //JOptionPane.showMessageDialog(null, "This is the last page....");
              jLabel104.setText("Already in last page... "+listPage);
        try {
            UIManager.setLookAndFeel(old);
        } catch (Exception e) {
            e.printStackTrace();
        }
}        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel67MouseClicked

    private void jLabel46MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel46MouseClicked
 
        if(!lastUserList){
        startUserList+=endUserList;
        String str = jTextField1.getText();
         String sql = "SELECT * FROM `users` where `u_information_id` != 0  and concat_ws(`u_firstname`,' ',`u_middlename`,' ',`u_lastname`,' ',`u_suffixname`,' ',`u_username`) like '%"+str+"%' LIMIT "+startUserList+","+endUserList+"";
            try{
                conn = SQLCon.ConnectDB();
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery();
            if(rs.next()){
                    listPage++;
              lastUserList = false; 
        TableFromDatabase.TableFromDatabase(str,startUserList,endUserList);
            UsersPanel(); 
            
            jLabel109.setText("Page: "+listPage);
            
            if(getCurrentUserListPage==listPage){
                 jTable4.setSelectionBackground(new Color(184, 207, 229));
            }else{
                 jTable4.setSelectionBackground(Color.white);
            } 
            }else{ 
              lastUserList = true;
              startUserList-=endUserList; 
       jLabel109.setText("Already in last page... "+listPage); 
            }
            conn.close();
        }catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1022", JOptionPane.ERROR_MESSAGE);
        }
        
}else{ 
         jLabel109.setText("Already in last page... "+listPage); 
}      
    }//GEN-LAST:event_jLabel46MouseClicked

    private void jLabel47MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel47MouseClicked
 
LookAndFeel old = UIManager.getLookAndFeel();
        try {
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(startUserList<0 || startUserList==0){
         //   JOptionPane.showMessageDialog(null, "This is the first page...");
             jLabel109.setText("Already in first page... "+listPage);
        }else{
            listPage--;
            lastUserList = false;
            startUserList -= endUserList;
                  String str = jTextField1.getText();
        TableFromDatabase.TableFromDatabase(str,startUserList,endUserList);
            UsersPanel(); 
            jLabel109.setText("Page: "+listPage);
 
            if(getCurrentUserListPage==listPage){
                 jTable4.setSelectionBackground(new Color(184, 207, 229));
            }else{
                 jTable4.setSelectionBackground(Color.white);
            }
            
           
        }

        try {
            UIManager.setLookAndFeel(old);
        } catch (Exception e) {
            e.printStackTrace();
        }            // TODO add your handling code here:
    }//GEN-LAST:event_jLabel47MouseClicked

    private void txtFromDate1InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtFromDate1InputMethodTextChanged
     // TODO add your handling code here:
    }//GEN-LAST:event_txtFromDate1InputMethodTextChanged

    private void txtFromDate1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtFromDate1PropertyChange
        // TODO add your handling code here:
        //   if(txtFromDate1.getDate()!=null){
            //   cboRecords.setSelectedItem("ALL BETWEEN DATES");
            //   }
    }//GEN-LAST:event_txtFromDate1PropertyChange

    private void txtFromDate2InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtFromDate2InputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFromDate2InputMethodTextChanged

    private void txtFromDate2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtFromDate2PropertyChange
      // TODO add your handling code here:
    }//GEN-LAST:event_txtFromDate2PropertyChange

    private void txtFromDate1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFromDate1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFromDate1KeyReleased

    private void txtFromDate3InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtFromDate3InputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFromDate3InputMethodTextChanged

    private void txtFromDate3PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtFromDate3PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFromDate3PropertyChange

    private void txtFromDate4InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtFromDate4InputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFromDate4InputMethodTextChanged

    private void txtFromDate4PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtFromDate4PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFromDate4PropertyChange

    private void txtFromDate4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFromDate4KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFromDate4KeyReleased

    private void txtLname10KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLname10KeyReleased
ContactFilter();        // TODO add your handling code here:
    }//GEN-LAST:event_txtLname10KeyReleased

    private void jTable4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MouseReleased
getCurrentUserListPage = listPage;
jTable4.setSelectionBackground(new Color(184, 207, 229));
        /*
        jTable4.getModel().addTableModelListener(new TableModelListener(){
         public void tableChanged(TableModelEvent e) {
            int row =  jTable4.getSelectedRow(); 
            int column = e.getColumn();
            if (column == 5) {
                TableModel model = (TableModel) e.getSource();
                Boolean checked = (Boolean) model.getValueAt(row, 5);
                if (checked) {
                    System.out.println( ": " + true);
                    JOptionPane.showMessageDialog(null,"True");
                } else {
                    System.out.println( ": " + false);
                    JOptionPane.showMessageDialog(null,"False");
                }
            }
        }
      
      });      
        */
 UsersListFunction();
         if (evt.getClickCount() == 2) {
                 printcompanylist = false;
        printhistorylist = false; 
      printuserrecord = false;
      printuserlist = false;
       printuserprofile = true;
       printloglist = false;
        printinternlist = false;
        printdeployedlist = false;
     printstudentlist = false;
    printstudentlog = false;
     printstudentact = false;
     printstudentrecord = false;
	 printstatistics = false;
          UserList.hide();
          UserProfile.show();
           
        jLabel43.setText("Date Registered: "+SelectedUserAccount.udateadded);
        jLabel41.setText(SelectedUserAccount.userid);
       // jLabel36.setText(SystemInfo.ufirstname+" "+SystemInfo.umiddlename+" "+SystemInfo.ulastname+" "+SystemInfo.usuffix);
        txtFname.setText(SelectedUserAccount.ufirstname);
        txtMname.setText(SelectedUserAccount.umiddlename);
        txtLname.setText(SelectedUserAccount.ulastname);
        txtSuffix.setText(SelectedUserAccount.usuffix);
        if(SelectedUserAccount.gender.equals("0")){
            jComboBox1.setSelectedItem("Male");
        }else{
           jComboBox1.setSelectedItem("Female");
        }
        txtUsername.setText(SelectedUserAccount.uusername);
        
        txtContactNumber.setText(SelectedUserAccount.ucontactnum);
        txtEmail.setText(SelectedUserAccount.uemail);
         if(SelectedUserAccount.uvalidate.equals("1")){
             jCheckBox1.setSelected(true);
         }else{
              jCheckBox1.setSelected(false);
         }
         if(SelectedUserAccount.urole.equals("1")){
             jComboBox2.setSelectedItem("Admin");
         }else{
               jComboBox2.setSelectedItem("Staff");
         }
        
        
       try{
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(SelectedUserAccount.img).getImage().getScaledInstance(jLabel37.getWidth(), jLabel37.getHeight(), Image.SCALE_SMOOTH));
                jLabel37.setIcon(imageIcon); 
        }catch(Exception e){
            jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Name_125px.png")));
            System.out.println("No Image\n"+e);
        } 
       
        changeAvatar = false;
         userProfileAuditListStart = 0;
       UserProfileFilterByDate = false;
         txtFromDate5.setDate(null);
         txtFromDate6.setDate(null);
         jLabel107.setText(null);
          listPage = 1;
          jLabel108.setText("Page: "+listPage);
     
       getUserProfileAuditList();
       //ShowSearch();
         HideSearch();
  }
       
      
// UsersListFunction();        // TODO add your handling code here:
    }//GEN-LAST:event_jTable4MouseReleased

    private void jTable4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable4KeyReleased
 
   if(evt.getKeyCode()==38  || evt.getKeyCode()==40){
          getCurrentUserListPage = listPage;
jTable4.setSelectionBackground(new Color(184, 207, 229));  
  UsersListFunction();  
   }
    }//GEN-LAST:event_jTable4KeyReleased

    private void jLabel37MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel37MouseClicked
  LookAndFeel old = UIManager.getLookAndFeel();
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
                JFileChooser fileChooser = new JFileChooser();
                 fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
     fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpeg","jpg","png"));
                //fileChooser.showOpenDialog(null);
                 int result=fileChooser.showSaveDialog(null);
                 if(result==JFileChooser.APPROVE_OPTION){
        File selectedFile = fileChooser.getSelectedFile();
        String path = selectedFile.getAbsolutePath();
        lblImage.setIcon(ResizeImage(path));
                 }
         */

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
                    jLabel37.setIcon(ResizeImage(path));
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

        try {
            UIManager.setLookAndFeel(old);
        } catch (Exception e) {
            e.printStackTrace();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel37MouseClicked

    private void txtFromDate5InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtFromDate5InputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFromDate5InputMethodTextChanged

    private void txtFromDate5PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtFromDate5PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFromDate5PropertyChange

    private void txtFromDate5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFromDate5KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFromDate5KeyReleased

    private void txtFromDate6InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtFromDate6InputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFromDate6InputMethodTextChanged

    private void txtFromDate6PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtFromDate6PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFromDate6PropertyChange

    private void jLabel52MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel52MouseClicked
  LookAndFeel old = UIManager.getLookAndFeel();
        try {
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(userProfileAuditListStart<0 || userProfileAuditListStart==0){
           // JOptionPane.showMessageDialog(null, "This is the first page...");
              jLabel108.setText("Already in first page... "+listPage);
        }else{
            listPage--;
            userProfileAuditLast = false;
            userProfileAuditListStart-=auditlistEnd;
            if(UserProfileFilterByDate){
                getUserProfileAuditListByDate();
            }else{
                 getUserProfileAuditList();
            } 
            jLabel108.setText("Page: "+listPage);
        }

        try {
            UIManager.setLookAndFeel(old);
        } catch (Exception e) {
            e.printStackTrace();
        }          // TODO add your handling code here:
    }//GEN-LAST:event_jLabel52MouseClicked

    private void jLabel53MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel53MouseClicked
if(!userProfileAuditLast){
        userProfileAuditListStart+=auditlistEnd;
        String sql = "";
         if(UserProfileFilterByDate){
             String getFromDate = ((JTextField)txtFromDate5.getDateEditor().getUiComponent()).getText();
          String getToDate = ((JTextField)txtFromDate6.getDateEditor().getUiComponent()).getText();  
          sql = "SELECT * FROM audit where u_information_id='"+SelectedUserAccount.userid+"' and date_time>='"+getFromDate+" 00:00:00' AND  date_time < '"+getToDate+" 23:59:59' LIMIT "+userProfileAuditListStart+","+auditlistEnd+"";
           }else{ 
             sql = "SELECT * FROM `audit` where u_information_id='"+SelectedUserAccount.userid+"' LIMIT "+userProfileAuditListStart+","+auditlistEnd+"";
          }
            try{
                conn = SQLCon.ConnectDB();
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery();
            if(rs.next()){
                    listPage++;
              userProfileAuditLast = false;
               if(UserProfileFilterByDate){
                getUserProfileAuditListByDate();
            }else{
        getUserProfileAuditList();   
               }
            jLabel108.setText("Page: "+listPage);
            }else{
                LookAndFeel old = UIManager.getLookAndFeel();
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
              userProfileAuditLast = true;
              userProfileAuditListStart-=auditlistEnd;
        //    JOptionPane.showMessageDialog(null, "This is the last page...");
              jLabel108.setText("Already in last page... "+listPage);
        try {
            UIManager.setLookAndFeel(old);
        } catch (Exception e) {
            e.printStackTrace();
        }
            }
            conn.close();
        }catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1027", JOptionPane.ERROR_MESSAGE);
        }
        
}else{
      LookAndFeel old = UIManager.getLookAndFeel();
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
            //JOptionPane.showMessageDialog(null, "This is the last page....");
              jLabel108.setText("Already in last page... "+listPage);
        try {
            UIManager.setLookAndFeel(old);
        } catch (Exception e) {
            e.printStackTrace();
        }
}        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel53MouseClicked

    private void txtContactNumberKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContactNumberKeyReleased
 UserContactFilter();        // TODO add your handling code here:
    }//GEN-LAST:event_txtContactNumberKeyReleased

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
UIManagers.getNewUI();
        String updatepass = ""; 
             if (jPasswordField3.getText().equals("Enter new password")) {
            JOptionPane.showMessageDialog(null, "Enter the password...");
            jPasswordField3.requestFocus();
        }   else if (jPasswordField3.getText().length() <= 5) {
            JOptionPane.showMessageDialog(null, "Password must 6 characters and above");
            jPasswordField3.requestFocus();
        }else{
              updatepass = BCrypt.hashpw(jPasswordField3.getText(), BCrypt.gensalt(12));
               UpdateAccountPassword.StartUpdate(SelectedUserAccount.userid,updatepass);
                jPasswordField3.setText("Enter new password");
   jPasswordField3.setEchoChar((char)0);
    jPasswordField3.setFont(new java.awt.Font("Segoe UI", 0, 14));
        } 
             UIManagers.applyOldUI();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jPasswordField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordField3KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordField3KeyReleased

    private void jPasswordField3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordField3FocusGained
if( jPasswordField3.getText().equals("Enter new password")){
   jPasswordField3.setText(""); 
   jPasswordField3.setEchoChar('*');
 jPasswordField3.setFont(new java.awt.Font("Segoe UI", 1, 14));
}
    }//GEN-LAST:event_jPasswordField3FocusGained

    private void jPasswordField3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPasswordField3MouseClicked
if(jPasswordField3.getText().equals("Enter new password")){
   jPasswordField3.setText("");
   jPasswordField3.setEchoChar('*'); 
   jPasswordField3.setFont(new java.awt.Font("Segoe UI", 1, 14));
    }                
    }//GEN-LAST:event_jPasswordField3MouseClicked

    private void jPasswordField3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordField3FocusLost
if( jPasswordField3.getText().equals("")){
   jPasswordField3.setText("Enter new password");
   jPasswordField3.setEchoChar((char)0);
    jPasswordField3.setFont(new java.awt.Font("Segoe UI", 0, 14));
    }                
    }//GEN-LAST:event_jPasswordField3FocusLost

    private void jLabel60MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel60MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel60MouseClicked

    private void jTable4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable4FocusLost

    }//GEN-LAST:event_jTable4FocusLost

    private void fourMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fourMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_fourMouseClicked

    private void jTable4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable4MousePressed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
String str = jTextField1.getText();
        if(searchuserlist){
              lastUserList = false;
        startUserList = 0;
         listPage = 1;    
          jLabel109.setText("Page: "+listPage); 
           if(getCurrentUserListPage==listPage){
                 jTable4.setSelectionBackground(new Color(184, 207, 229));
            }else{
                 jTable4.setSelectionBackground(Color.white);
            }
        TableFromDatabase.TableFromDatabase(str,startUserList,endUserList);
        UsersPanel();
        }else if(searchcompanylist){
            pnlCompanies.lastCompList = false;
            pnlCompanies.startCompList = 0;
            pnlCompanies.listPage = 1;
            pnlCompanies.jLabel1.setText("Page: "+listPage);  
            pnlCompanies.getCompanyList(pnlCompanies.startCompList,pnlCompanies.endCompList); 
            pnlCompanies.ChangeTableList();
        }else if(searchstudentlist){
            pnlStudents.lastCompList = false;
            pnlStudents.startCompList = 0;
            pnlStudents.listPage = 1;
            pnlStudents.jLabel2.setText("Page: "+listPage);  
            pnlStudents.getCompanyList(pnlCompanies.startCompList,pnlCompanies.endCompList); 
            pnlStudents.ChangeTableList();
        }else if(searchlogs){
         lastCompList = false;
            startCompList = 0;
            listPage = 1;
             jLabel113.setText("Page: "+listPage);  
            getCompanyList(startCompList,endCompList); 
             LogsPanel();
        }else if(searchintern){
         pnlCompanyIntern.lastCompList = false;
            pnlCompanyIntern.startCompList = 0;
            pnlCompanyIntern.listPage = 1;
             pnlCompanyIntern.jLabel33.setText("Page: "+listPage);  
            pnlCompanyIntern.getCompanyList(startCompList,endCompList); 
             pnlCompanyIntern.CompanyInternList();
        }else if(searchemployed){
         pnlCompanyEmployed.lastCompList = false;
            pnlCompanyEmployed.startCompList = 0;
            pnlCompanyEmployed.listPage = 1;
             pnlCompanyEmployed.jLabel33.setText("Page: "+listPage);  
            pnlCompanyEmployed.getCompanyList(startCompList,endCompList); 
             pnlCompanyEmployed.CompanyInternList();
        }
        
    }//GEN-LAST:event_jTextField1KeyReleased

    private void pnlListStudentMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlListStudentMouseReleased
studentlist = true;
        new DynamicPanel(pnlDynamic, new panels.pnlStudents());
 ShowSearch();
pnlFooterAction.show();
        pnlListStudent.setBackground(new Color(255,255,255));
      pnlAddStudent2.show();
      pnlAddStudent.setBackground(new Color(220,220,220));
      pnlAddStudent1.hide();        // TODO add your handling code here:
    }//GEN-LAST:event_pnlListStudentMouseReleased

    private void pnlAddStudentMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAddStudentMouseReleased
studentlist = false;
//if(staticStudentAddNavClick==false){
new DynamicPanel(pnlDynamic, new panels.pnlStudentsAdd());
HideSearch();
pnlFooterAction.hide();
// staticStudentAddNavClick = true;
//}
      pnlListStudent.setBackground(new Color(220,220,220));
      pnlAddStudent2.hide();
      pnlAddStudent.setBackground(new Color(255,255,255));
      pnlAddStudent1.show();        // TODO add your handling code here:
    }//GEN-LAST:event_pnlAddStudentMouseReleased

    private void pnlListStudent1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlListStudent1MouseReleased
   pnlListStudent1.setBackground(new Color(255,255,255));
      pnlAddStudent3.show();
      pnlAddStudent4.setBackground(new Color(220,220,220));
      pnlAddStudent5.hide();        // TODO add your handling code here:
    }//GEN-LAST:event_pnlListStudent1MouseReleased

    private void pnlAddStudent4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAddStudent4MouseReleased
 pnlListStudent1.setBackground(new Color(220,220,220));
      pnlAddStudent3.hide();
      pnlAddStudent4.setBackground(new Color(255,255,255));
      pnlAddStudent5.show();              // TODO add your handling code here:
    }//GEN-LAST:event_pnlAddStudent4MouseReleased

    private void pnlListCompanyMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlListCompanyMouseReleased
 companylist = true;
        new DynamicPanel(pnlDynamic, new panels.pnlCompanies());
       ShowSearch();
        pnlFooterAction.show();
        pnlListCompany.setBackground(new Color(255, 255, 255));
        pnlAddStudent6.show();
        pnlAddCompany.setBackground(new Color(220, 220, 220));
        pnlAddStudent8.hide();        // TODO add your handling code here:
    }//GEN-LAST:event_pnlListCompanyMouseReleased

    private void pnlAddCompanyMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAddCompanyMouseReleased
companylist = false;
new DynamicPanel(pnlDynamic, new panels.pnlCompaniesAdd());
HideSearch();
pnlFooterAction.hide();
        pnlListCompany.setBackground(new Color(220,220,220));
      pnlAddStudent6.hide();
      pnlAddCompany.setBackground(new Color(255,255,255));
      pnlAddStudent8.show();          // TODO add your handling code here:
    }//GEN-LAST:event_pnlAddCompanyMouseReleased

    private void StudentOptionsMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StudentOptionsMouseReleased
 studentoptions = true;
 internshipoptions = false;
 companyoptions = false;
 accessibilityoptions = false;
      new DynamicPanel(pnlDynamic, new panels.pnlStudentsOptions());
      StudentOptions.setBackground(new Color(255,255,255));
      pnlAddStudent7.show();
      InternshipOptions.setBackground(new Color(220,220,220));
      pnlAddStudent10.hide();  
      CompanyOptions.setBackground(new Color(220,220,220));
      pnlAddStudent14.hide();
      AccessibilityOptions.setBackground(new Color(220,220,220));
      pnlAddStudent15.hide();        // TODO add your handling code here:
    }//GEN-LAST:event_StudentOptionsMouseReleased

    private void InternshipOptionsMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_InternshipOptionsMouseReleased
studentoptions = false;
 internshipoptions = true;
 companyoptions = false;
 accessibilityoptions = false;
 
 new DynamicPanel(pnlDynamic, new panels.pnlInternshipOptions());
       StudentOptions.setBackground(new Color(220,220,220));
      pnlAddStudent7.hide();
      InternshipOptions.setBackground(new Color(255,255,255));
      pnlAddStudent10.show();  
      CompanyOptions.setBackground(new Color(220,220,220));
      pnlAddStudent14.hide();
      AccessibilityOptions.setBackground(new Color(220,220,220));
      pnlAddStudent15.hide();        // TODO add your handling code here:
    }//GEN-LAST:event_InternshipOptionsMouseReleased

    private void CompanyOptionsMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CompanyOptionsMouseReleased
studentoptions = false;
 internshipoptions = false;
 companyoptions = true; 
 accessibilityoptions = false;
 new DynamicPanel(pnlDynamic, new panels.pnlCompanyOptions());
       StudentOptions.setBackground(new Color(220,220,220));
      pnlAddStudent7.hide();
      InternshipOptions.setBackground(new Color(220,220,220));
      pnlAddStudent10.hide();  
      CompanyOptions.setBackground(new Color(255,255,255));
      pnlAddStudent14.show();
      AccessibilityOptions.setBackground(new Color(220,220,220));
      pnlAddStudent15.hide();        // TODO add your handling code here:
    }//GEN-LAST:event_CompanyOptionsMouseReleased

    private void AccessibilityOptionsMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AccessibilityOptionsMouseReleased
studentoptions = false;
 internshipoptions = false;
 companyoptions = false; 
 accessibilityoptions = true;
 new DynamicPanel(pnlDynamic, new panels.pnlSystemAccessibility());
       StudentOptions.setBackground(new Color(220,220,220));
      pnlAddStudent7.hide();
      InternshipOptions.setBackground(new Color(220,220,220));
      pnlAddStudent10.hide();  
      CompanyOptions.setBackground(new Color(220,220,220));
      pnlAddStudent14.hide();
      AccessibilityOptions.setBackground(new Color(255,255,255));
      pnlAddStudent15.show();          // TODO add your handling code here:
    }//GEN-LAST:event_AccessibilityOptionsMouseReleased

    private void txtFromDate7InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtFromDate7InputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFromDate7InputMethodTextChanged

    private void txtFromDate7PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtFromDate7PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFromDate7PropertyChange

    private void txtFromDate7KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFromDate7KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFromDate7KeyReleased

    private void txtFromDate8InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtFromDate8InputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFromDate8InputMethodTextChanged

    private void txtFromDate8PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtFromDate8PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFromDate8PropertyChange

    private void jLabel55MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel55MouseReleased
UIManagers.getNewUI();
        if(startCompList<0 || startCompList==0){ 
            jLabel113.setText("Already in first page... "+listPage);
        }else{
            listPage--;
            lastCompList = false;
            startCompList-=endCompList; 
             getCompanyList(startCompList,endCompList); 
            jLabel113.setText("Page: "+listPage); 
        } 
     UIManagers.applyOldUI();          // TODO add your handling code here:
    }//GEN-LAST:event_jLabel55MouseReleased

    private void jLabel54MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel54MouseReleased
if(!lastCompList){
            startCompList+=endCompList;
            String sql = ""; 
             sql = "SELECT * FROM `student_records` LIMIT "+startCompList+","+endCompList+""; 
            try{
                conn = SQLCon.ConnectDB();
                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();
                if(rs.next()){
                    listPage++;
                    lastCompList = false; 
                     getCompanyList(startCompList,endCompList);
            jLabel113.setText("Page: "+listPage);
                }else{
                   UIManagers.getNewUI(); 
                    lastCompList = true;
                    startCompList-=endCompList;
                    
                   // JOptionPane.showMessageDialog(null, "This is the last page...");
                   jLabel113.setText("Already in last page... "+listPage);
                  UIManagers.applyOldUI();
                }
                conn.close();
            }catch (Exception e) {
                DC();
            e.printStackTrace();
                //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1017", JOptionPane.ERROR_MESSAGE);
            }
        }else{
          UIManagers.getNewUI(); 
                   jLabel113.setText("Already in last page... "+listPage); 
          UIManagers.applyOldUI();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel54MouseReleased

    private void jComboBox3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBox3FocusGained
 isDept = true;        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3FocusGained

    private void jComboBox4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBox4FocusGained
 
    isCourse = true;       // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4FocusGained

    private void jComboBox5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBox5FocusGained

    isLevel = true;        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox5FocusGained

    private void jLabel11MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseReleased
     Runnable r = new Runnable() {
            public void run() {
            if(printcompanylist){
            pnlCompanies.PrintCompanyList();
        }else if(printhistorylist){
             if(auditFilterByDate){ 
                 printAuditListByDate(); 
                    }else{ 
                    printAuditList();
                    } 
        }else if(printuserrecord){
             if(profileFilterByDate){
                printProfileAuditListByDate();
            }else{
               printProfileAuditList();
            } 
        }else if(printuserlist){
            printUserList();
        }else if(printuserprofile){
             if(UserProfileFilterByDate){
               printUserProfileAuditListByDate();
            }else{
                 printUserProfileAuditList();
            } 
        }else if(printloglist){
            printLogs();
        }else if(printinternlist){
             pnlCompanyIntern.printCompanyList();
        }else if(printdeployedlist ){
             pnlCompanyEmployed.printCompanyList();
        }else if(printstudentlist){
             pnlStudents.printCompanyList();
        }else if(printstudentrecord){
              if (pnlStudentProfile.jTable4.getSelectedRowCount() > 0) {
               pnlStudentProfile.printCompanyList();
            }else{
                UIManagers.getNewUI();
                JOptionPane.showMessageDialog(null, "Select record you want to print data...");
                UIManagers.applyOldUI(); 
            }
        }else if(printstudentact){
               pnlStudentProfile.printCompanyList();
        }else if(printstudentlog){
            pnlStudentLogs.printCompanyList();
        }else if(printstatistics){
     SaveHome();
        }else {
            JOptionPane.showMessageDialog(null, "Error has occured, try again...");
        }
            
              Home.this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
              jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }
        };
      new Thread(r).start();
      this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
      jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        
        
        /*
        
        
     printstudentlist = false;
    printstudentlog = false;
     printstudentact = false;
     printstudentrecord = false;
        */ 

    }//GEN-LAST:event_jLabel11MouseReleased

    private void jLabel12MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseExited
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_PDF_32px.png")));    // TODO add your handling code here:
    }//GEN-LAST:event_jLabel12MouseExited

    private void jLabel12MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseEntered
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_PDF_40px.png")));     // TODO add your handling code here:
    }//GEN-LAST:event_jLabel12MouseEntered

    private void jLabel13MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseExited
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Microsoft_Excel_32px.png")));      // TODO add your handling code here:
    }//GEN-LAST:event_jLabel13MouseExited

    private void jLabel13MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseEntered
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Microsoft_Excel_40px.png")));          // TODO add your handling code here:
    }//GEN-LAST:event_jLabel13MouseEntered

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTable1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseReleased

    private void jPanel13MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel13MouseReleased

    private void five1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_five1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_five1MouseClicked

    private void five1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_five1MousePressed
        this.one.setSelected(false);
        this.two.setSelected(false);
        this.three.setSelected(false);
        this.four.setSelected(false);
        this.five.setSelected(false);
        this.five1.setSelected(true);
        this.six.setSelected(false);
        this.seven.setSelected(false);
        this.eight.setSelected(false);        // TODO add your handling code here:
    }//GEN-LAST:event_five1MousePressed

    private void five1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_five1ActionPerformed

         HideSearch();
        pnlFooterAction.setVisible(false);  
        
        
        
        
          DynamicContent.hide();
        pnlHomePage.hide();
        pnlLogs.hide();
        UserProfileInfo.hide();
        pnlUsers.hide();
        AuditTrail.hide();
        Developers.show();
         
       
        
        if(this.five1.isSelected()){
            this.one.setBorder(null);
            this.two.setBorder(null);
            this.three.setBorder(null);
            this.four.setBorder(null);
            this.five.setBorder(null);
            this.five1.setBorder(bordePressed);
            this.six.setBorder(null);
            this.seven.setBorder(null);
            this.eight.setBorder(null);
            
            this.one.setColorNormal(new Color(23,35,51));
            this.one.setColorHover(new Color(41,57,80));
            this.one.setColorPressed(new Color(41,57,80));
            
            this.two.setColorNormal(new Color(23,35,51));
            this.two.setColorHover(new Color(41,57,80));
            this.two.setColorPressed(new Color(41,57,80));
            
            this.three.setColorNormal(new Color(23,35,51));
            this.three.setColorHover(new Color(41,57,80));
            this.three.setColorPressed(new Color(41,57,80));

            this.four.setColorNormal(new Color(23,35,51));
            this.four.setColorHover(new Color(41,57,80));
            this.four.setColorPressed(new Color(41,57,80));
            
           this.five.setColorNormal(new Color(23,35,51));
            this.five.setColorHover(new Color(41,57,80));
            this.five.setColorPressed(new Color(41,57,80));
            
            this.five1.setColorNormal(new Color(41,57,80));
            this.five1.setColorHover(new Color(41,57,80));
            this.five1.setColorPressed(new Color(41,57,80));

             this.six.setColorNormal(new Color(23,35,51));
            this.six.setColorHover(new Color(41,57,80));
            this.six.setColorPressed(new Color(41,57,80));

             this.seven.setColorNormal(new Color(23,35,51));
            this.seven.setColorHover(new Color(41,57,80));
            this.seven.setColorPressed(new Color(41,57,80));

             this.eight.setColorNormal(new Color(23,35,51));
            this.eight.setColorHover(new Color(41,57,80));
            this.eight.setColorPressed(new Color(41,57,80));
        }else{
           this.five1.setColorNormal(new Color(23,35,51));
            this.five1.setColorHover(new Color(41,57,80));
            this.five1.setColorPressed(new Color(41,57,80));
        }        // TODO add your handling code here:
    }//GEN-LAST:event_five1ActionPerformed

    private void threeMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_threeMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_threeMouseReleased

    /**
     * @param args the command line arguments
     */
    
    //START MAIN MECTHOD public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        
        /*
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
*/
        /* Create and display the form 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new Home().setVisible(true);
                CDPOSystem m = new CDPOSystem();
                m.Load();
            }
        });
        */
    //END MAIN METHOD }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AccessibilityOptions;
    private javax.swing.JPanel AuditTrail;
    private javax.swing.JPanel CompaniesNav;
    private javax.swing.JPanel CompanyOptions;
    private javax.swing.JPanel Developers;
    private javax.swing.JPanel DynamicContent;
    private javax.swing.JPanel InternshipOptions;
    private javax.swing.JPanel OptionsNav;
    private javax.swing.JPanel StudentOptions;
    private javax.swing.JPanel StudentsNav;
    private javax.swing.JPanel UserList;
    private javax.swing.JPanel UserProfile;
    private javax.swing.JPanel UserProfileInfo;
    private javax.swing.JPanel UsersNav;
    private javax.swing.JPanel changePass;
    public static sidebutton.RSButtonMetro eight;
    private sidebutton.RSButtonMetro five;
    private sidebutton.RSButtonMetro five1;
    public static sidebutton.RSButtonMetro four;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    public static javax.swing.JComboBox<String> jComboBox3;
    public static javax.swing.JComboBox<String> jComboBox4;
    public static javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel148;
    private javax.swing.JLabel jLabel149;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    public static javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    public static javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    public static javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JPasswordField jPasswordField3;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator22;
    private javax.swing.JSeparator jSeparator23;
    private javax.swing.JSeparator jSeparator24;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTable jTable1;
    public static javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    public static javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    public static javax.swing.JTextField jTextField1;
    private javax.swing.JPanel navHeaderHolder;
    private sidebutton.RSButtonMetro one;
    private javax.swing.JPanel pnlAddCompany;
    private javax.swing.JPanel pnlAddStudent;
    private javax.swing.JPanel pnlAddStudent1;
    private javax.swing.JPanel pnlAddStudent10;
    private javax.swing.JPanel pnlAddStudent14;
    private javax.swing.JPanel pnlAddStudent15;
    private javax.swing.JPanel pnlAddStudent2;
    private javax.swing.JPanel pnlAddStudent3;
    private javax.swing.JPanel pnlAddStudent4;
    private javax.swing.JPanel pnlAddStudent5;
    private javax.swing.JPanel pnlAddStudent6;
    private javax.swing.JPanel pnlAddStudent7;
    private javax.swing.JPanel pnlAddStudent8;
    private javax.swing.JPanel pnlContent;
    public static javax.swing.JPanel pnlDynamic;
    private javax.swing.JPanel pnlFooter;
    public static javax.swing.JPanel pnlFooterAction;
    public static javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlHomePage;
    private javax.swing.JPanel pnlListCompany;
    private javax.swing.JPanel pnlListStudent;
    private javax.swing.JPanel pnlListStudent1;
    private javax.swing.JPanel pnlLogs;
    public static javax.swing.JPanel pnlSearch;
    private javax.swing.JPanel pnlSideNav;
    private javax.swing.JPanel pnlStudentPersonalInfo1;
    private javax.swing.JPanel pnlStudentPersonalInfo2;
    private javax.swing.JPanel pnlUsers;
    public static sidebutton.RSButtonMetro seven;
    private sidebutton.RSButtonMetro six;
    private sidebutton.RSButtonMetro three;
    private sidebutton.RSButtonMetro two;
    private javax.swing.JTextField txtContactNumber;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFname;
    private com.toedter.calendar.JDateChooser txtFromDate1;
    private com.toedter.calendar.JDateChooser txtFromDate2;
    private com.toedter.calendar.JDateChooser txtFromDate3;
    private com.toedter.calendar.JDateChooser txtFromDate4;
    private com.toedter.calendar.JDateChooser txtFromDate5;
    private com.toedter.calendar.JDateChooser txtFromDate6;
    public static com.toedter.calendar.JDateChooser txtFromDate7;
    public static com.toedter.calendar.JDateChooser txtFromDate8;
    private javax.swing.JTextField txtLname;
    private javax.swing.JTextField txtLname10;
    private javax.swing.JTextField txtLname11;
    private javax.swing.JTextField txtMname;
    private javax.swing.JTextField txtSuffix;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
 public static ImageIcon ResizeImage(String imgPath) {
        ImageIcon MyImage = new ImageIcon(imgPath);
        Image img = MyImage.getImage();
        Image newImage = img.getScaledInstance(jLabel45.getWidth(), jLabel45.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImage);
        return image;
    }
}
