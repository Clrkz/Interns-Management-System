/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panels;

import f.SQLCon;
import f.SelectedCompany;
import f.SystemInfo;
import f.UIManagers;
import home.Home;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
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
 * @author Clrkz
 */
public class pnlCompanyIntern extends javax.swing.JPanel {
public static Connection conn = null;
public static PreparedStatement pst =  null;
public static ResultSet rs = null; 
public static int startCompList = 0,endCompList = 20;
  public static boolean lastCompList = false;
   public static int listPage = 1;
   boolean isDept = false;
   boolean isCourse = false;
   boolean isLevel = false;
   boolean isHours = false;
  // static pnlCompanyIntern cmp = new pnlCompanyIntern(); 
   
       static pnlCompanyIntern cmp;
     public static void main(String[] args) {
        try {
            // The following line would throw ExceptionInInitializerError
           cmp = new pnlCompanyIntern();
        } catch (Throwable t) {
            System.out.println(t);
        }
        // The following line would cause NoClassDefFoundError
        cmp = new pnlCompanyIntern();
    }
     
     
    /**
     * Creates new form pnlHome
     */
    public pnlCompanyIntern() {
        initComponents();
        
f.ClassDisconnected.flag = false;
          home.Home.jTextField1.setText("Search..."); 
        Home.ShowSearch();
        Home.searchuserlist = false;
        Home.searchcompanylist = false;
        Home.searchstudentlist = false;
        Home.searchlogs = false;
        Home.searchintern = true;
        Home.searchemployed = false;
        Home.pnlFooterAction.show();
         
      Home.printcompanylist = false;
      Home.printhistorylist = false; 
      Home.printuserrecord = false;
      Home.printuserlist = false;
      Home.printuserprofile  = false; 
      Home.printloglist = false;
      Home.printinternlist = true;
      Home.printdeployedlist = false; 
     Home.printstudentlist = false;
    Home.printstudentlog = false;
     Home.printstudentact = false;
     Home.printstudentrecord = false; 
	 Home.printstatistics = false;
         
        
       CompanyInternList();
       jTextArea1.setEditable(false);
        jComboBox3.setBackground(Color.WHITE);
        jComboBox7.setBackground(Color.WHITE);
        jComboBox4.setBackground(Color.WHITE);
        jComboBox6.setBackground(Color.WHITE);
        
        CompanyListFunction();
        getDepartment();
        getLevels();
        getCourse();
        getHours();
         jLabel33.setText("Page: "+listPage); 
       getCompanyList(startCompList,endCompList);
       
       
       
       
       
       
         
           txtFromDate4.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() { 
             @Override
             public void propertyChange(PropertyChangeEvent e) {
                 if(txtFromDate4.getDate()!=null && txtFromDate3.getDate()!=null ){
                     startCompList = 0;
                        jLabel82.setText("<html> <font color='blue'><strong>"+((JTextField)txtFromDate4.getDateEditor().getUiComponent()).getText()+"</strong></font> and <font color='blue'><strong>"+((JTextField)txtFromDate3.getDateEditor().getUiComponent()).getText()+"</strong></font></html>");
                     lastCompList = false;
                     getCompanyList(startCompList,endCompList);   
                 listPage = 1;
            jLabel33.setText("Page: "+listPage); 
                 }else{ 
                     getCompanyList(startCompList,endCompList);  
                     jLabel82.setText("");
                 }
             }
         });
           
            txtFromDate3.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {  
             @Override
             public void propertyChange(PropertyChangeEvent e) {
                 if(txtFromDate4.getDate()!=null && txtFromDate3.getDate()!=null ){
                     startCompList = 0;
                       jLabel82.setText("<html> <font color='blue'><strong>"+((JTextField)txtFromDate4.getDateEditor().getUiComponent()).getText()+"</strong></font> and <font color='blue'><strong>"+((JTextField)txtFromDate3.getDateEditor().getUiComponent()).getText()+"</strong></font></html>");
                     lastCompList = false;
                     getCompanyList(startCompList,endCompList);   
                 listPage = 1;
            jLabel33.setText("Page: "+listPage); 
                 } else{ 
                     getCompanyList(startCompList,endCompList);   
                     jLabel82.setText("");
                 }
             }
         });
    }
    
    public void getHours(){ 
        String sql = "select ojt_hours_title from ojt_hours where ojt_hours_title!='0' ORDER by ojt_hours_title ASC";
        try{
          conn = SQLCon.ConnectDB();
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery();
            while(rs.next()){
      jComboBox6.addItem(rs.getString(1));
            }
            conn.close();
        }catch(Exception e){
            
            DC();
            e.printStackTrace();
          //   //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1076", JOptionPane.ERROR_MESSAGE);
      } 
   }
        
    
     public void getLevels(){ 
        String sql = "select s_level_title from student_levels where s_level_title!='EMPLOYED' ORDER by s_level_title ASC";
        try{
           conn = SQLCon.ConnectDB();
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery();
            while(rs.next()){
      jComboBox7.addItem(rs.getString(1));
            }
            conn.close();
        }catch(Exception e){
            DC();
            e.printStackTrace();
            // //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1075", JOptionPane.ERROR_MESSAGE);
      } 
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
    public void getCourse(){  
        String sql = "SELECT  `s_course_title`  FROM `student_courses` WHERE  1 ";
        try{
           conn = SQLCon.ConnectDB();
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery();
            while(rs.next()){
      jComboBox4.addItem(rs.getString(1));
            }
            conn.close();
        }catch(Exception e){
            DC();
            e.printStackTrace();
          ////JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1074", JOptionPane.ERROR_MESSAGE);
       } 
   }
     public void getDepartment(){  
        String sql = "select dept_abbr from student_departments where 1 ORDER by dept_abbr ASC";
        try{
         conn = SQLCon.ConnectDB();
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
    
   public void CompanyListFunction(){
                  jLabel8.setText(SelectedCompany.cname);
                  txtCompNo.setText(SelectedCompany.ccontact);
                  jLabel9.setText(SelectedCompany.cbranch);
                  jTextArea1.setText(SelectedCompany.caddress );
                  jLabel14.setText(SelectedCompany.cdivision);
                  jLabel15.setText(SelectedCompany.csector);   
             jLabel2.setText("Date Registered: "+SelectedCompany.cdateadded ); 
                
                jLabel28.setText(SelectedCompany.cIntern);
                jLabel30.setText(SelectedCompany.tIntern);
                jLabel32.setText(SelectedCompany.eIntern);
             
             
               new java.util.Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                
                      
        try{
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(SelectedCompany.img).getImage().getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(), Image.SCALE_SMOOTH));
                jLabel1.setIcon(imageIcon); 
        }catch(Exception e){
            jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Web_Camera_125px.png")));
            System.out.println("No Image\n"+e);
        }   
       
            
              
            }
        }, 500 * 1);
    }
       
     public static void getCompanyList(int start,int end){
         String ssearch = "";
        String sdept ="";
        String scourse = "";
        String slevel  =  "";
        String shours = "";
        String getFromDate = "";
        String getToDate= "";
        
       if(txtFromDate4.getDate()==null || txtFromDate3.getDate()==null){
               getFromDate =  "0000-00-00";
               getToDate =  "9999-12-31";
        }else{
           getFromDate = ((JTextField)txtFromDate4.getDateEditor().getUiComponent()).getText();
           getToDate = ((JTextField)txtFromDate3.getDateEditor().getUiComponent()).getText();   
       }
         
        if(!home.Home.jTextField1.getText().equals("Search...")){
          ssearch = home.Home.jTextField1.getText();
       }else{
            ssearch = "";
        }
        if(jComboBox3.getSelectedItem().equals("Department")){
              sdept ="";
        }else{
            sdept = jComboBox3.getSelectedItem().toString();
        }
          if(jComboBox4.getSelectedItem().equals("Course")){
              scourse ="";
        }else{
            scourse = jComboBox4.getSelectedItem().toString();
        }
        
          if(jComboBox7.getSelectedItem().equals("Level")){
              slevel ="";
        }else{
            slevel = jComboBox7.getSelectedItem().toString();
        }
        
          if(jComboBox6.getSelectedItem().equals("Hours")){
              shours ="";
        }else{
            shours = jComboBox6.getSelectedItem().toString();
        }
        
         String sql = "SELECT sr.dateadded as Date,concat( si.`s_lastname`,', ',si.`s_firstname`,' ',si.`s_middlename`,' ',si.`s_suffixname`) as Name,\n" +
"sc.s_course_title as Course,\n" +
"sl.s_level_title as Level,\n" +
"oh.ojt_hours_title as Hours\n" +
"FROM `student_records` sr\n" +
"inner join student_information si\n" +
"on sr.`s_information_id`=si.s_student_no\n" +
"inner join student_courses sc ON\n" +
"sr.`s_course_id`=sc.s_course_id\n" +
"inner join student_levels sl ON\n" +
"sr.`s_year_level_id`=sl.s_level_id\n" +
"INNER JOIN ojt_information oi ON\n" +
"sr.`s_ojt_information_id`=oi.ojt_information_id\n" +
"inner JOIN ojt_hours oh ON\n" +
"oi.ojt_hours_id=oh.ojt_hours_id\n" +
"INNER JOIN student_departments sd ON\n" +
"sr.`department_id`=sd.department_id\n" +
"where sl.s_level_title!='EMPLOYED'\n" +
"and oi.c_information_id='"+SelectedCompany.compid+"'\n" +
"and concat(si.`s_student_no`,' ',si.`s_lastname`,' ',si.`s_firstname`,' ',si.`s_middlename`,' ',si.`s_suffixname`) LIKE '%"+ssearch+"%'\n" +
"AND sd.dept_abbr LIKE '%"+sdept+"%'\n" +
"AND sc.s_course_title LIKE '%"+scourse+"%'\n" +    
"AND sl.s_level_title LIKE '%"+slevel+"%'\n" +
"AND oh.ojt_hours_title LIKE '%"+shours+"%'\n" +
"AND si.record_date>='"+getFromDate+" 00:00:00' AND   si.record_date < '"+getToDate+" 23:59:59'  \n" +
"ORDER BY sr.dateadded DESC\n" +
"LIMIT "+start+","+end+""; 
           try{
               conn = SQLCon.ConnectDB();
              // System.out.println(sql);
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery();
            jTable5.setModel(DbUtils.resultSetToTableModel(rs));  
            CompanyInternList();
            conn.close();
        }catch (Exception e) {
            DC();
            e.printStackTrace();
            ////JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1059", JOptionPane.ERROR_MESSAGE);
        }
     
    }
    public static void printCompanyList(){
         String ssearch = "";
        String sdept ="";
        String scourse = "";
        String slevel  =  "";
        String shours = "";
        String getFromDate = "";
        String getToDate= "";
        
       if(txtFromDate4.getDate()==null || txtFromDate3.getDate()==null){
               getFromDate =  "0000-00-00";
               getToDate =  "9999-12-31";
        }else{
           getFromDate = ((JTextField)txtFromDate4.getDateEditor().getUiComponent()).getText();
           getToDate = ((JTextField)txtFromDate3.getDateEditor().getUiComponent()).getText();   
       }
         
        if(!home.Home.jTextField1.getText().equals("Search...")){
          ssearch = home.Home.jTextField1.getText();
       }else{
            ssearch = "";
        }
        if(jComboBox3.getSelectedItem().equals("Department")){
              sdept ="";
        }else{
            sdept = jComboBox3.getSelectedItem().toString();
        }
          if(jComboBox4.getSelectedItem().equals("Course")){
              scourse ="";
        }else{
            scourse = jComboBox4.getSelectedItem().toString();
        }
        
          if(jComboBox7.getSelectedItem().equals("Level")){
              slevel ="";
        }else{
            slevel = jComboBox7.getSelectedItem().toString();
        }
        
          if(jComboBox6.getSelectedItem().equals("Hours")){
              shours ="";
        }else{
            shours = jComboBox6.getSelectedItem().toString();
        }
        
         String sql = "SELECT sr.s_information_id as `Student No`,sr.dateadded as Date,concat( si.`s_lastname`,', ',si.`s_firstname`,' ',si.`s_middlename`,' ',si.`s_suffixname`) as Name,\n" +
"sc.s_course_title as Course,\n" +
"sl.s_level_title as Level,\n" +
"oh.ojt_hours_title as Hours\n" +
"FROM `student_records` sr\n" +
"inner join student_information si\n" +
"on sr.`s_information_id`=si.s_student_no\n" +
"inner join student_courses sc ON\n" +
"sr.`s_course_id`=sc.s_course_id\n" +
"inner join student_levels sl ON\n" +
"sr.`s_year_level_id`=sl.s_level_id\n" +
"INNER JOIN ojt_information oi ON\n" +
"sr.`s_ojt_information_id`=oi.ojt_information_id\n" +
"inner JOIN ojt_hours oh ON\n" +
"oi.ojt_hours_id=oh.ojt_hours_id\n" +
"INNER JOIN student_departments sd ON\n" +
"sr.`department_id`=sd.department_id\n" +
"where sl.s_level_title!='EMPLOYED'\n" +
"and oi.c_information_id='"+SelectedCompany.compid+"'\n" +
"and concat(si.`s_student_no`,' ',si.`s_lastname`,' ',si.`s_firstname`,' ',si.`s_middlename`,' ',si.`s_suffixname`) LIKE '%"+ssearch+"%'\n" +
"AND sd.dept_abbr LIKE '%"+sdept+"%'\n" +
"AND sc.s_course_title LIKE '%"+scourse+"%'\n" +    
"AND sl.s_level_title LIKE '%"+slevel+"%'\n" +
"AND oh.ojt_hours_title LIKE '%"+shours+"%'\n" +
"AND si.record_date>='"+getFromDate+" 00:00:00' AND   si.record_date < '"+getToDate+" 23:59:59'  \n" +
"ORDER BY sr.dateadded DESC "; 
           try{
               conn = SQLCon.ConnectDB();
            try{
                 byte[] fileBytes;
        String query = "SELECT  c_logo  FROM company_information WHERE c_information_id='"+SelectedCompany.compid+"'"; 
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
                parameters.put("Name", SelectedCompany.cname);
                parameters.put("Branch", SelectedCompany.cbranch);
                parameters.put("Address", SelectedCompany.caddress);
                parameters.put("Contact", SelectedCompany.ccontact);
                parameters.put("Division", SelectedCompany.cdivision);  
                parameters.put("Sector", SelectedCompany.csector);
                parameters.put("cIntern", SelectedCompany.cIntern);  
                parameters.put("tIntern", SelectedCompany.tIntern);
                parameters.put("Employed", SelectedCompany.eIntern);  
                BufferedImage image = ImageIO.read(new FileInputStream("user.jpg"));
                parameters.put("Image",  image);
           JasperDesign jd = JRXmlLoader.load("src\\reports\\hinternlist.jrxml");
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
            DC();
            e.printStackTrace();
         //   //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1059", JOptionPane.ERROR_MESSAGE);
        }
     
    }
    
    
     public static void CompanyInternList(){
         jScrollPane5.getViewport().setBackground(Color.WHITE);
          JTableHeader Theader = jTable5.getTableHeader();
        Theader.setBackground(new Color(84,127,206)); // change the Background color
        Theader.setForeground(Color.WHITE); // change the Foreground
        Theader.setFont(new Font("Segoe UI", Font.BOLD, 14)); // font name style size
        ((DefaultTableCellRenderer)Theader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER); // center header text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
       jTable5.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable5.getColumnModel().getColumn(1).setPreferredWidth(120);
        jTable5.getColumnModel().getColumn(2).setPreferredWidth(230);
        jTable5.getColumnModel().getColumn(3).setPreferredWidth(50);
        jTable5.getColumnModel().getColumn(4).setPreferredWidth(5);
        jTable5.setDefaultEditor(Object.class, null);
        
    }

 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        pnlStudentPersonalInfo4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jSeparator6 = new javax.swing.JSeparator();
        jComboBox4 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox6 = new javax.swing.JComboBox<>();
        jComboBox7 = new javax.swing.JComboBox<>();
        jLabel86 = new javax.swing.JLabel();
        txtFromDate4 = new com.toedter.calendar.JDateChooser();
        txtFromDate3 = new com.toedter.calendar.JDateChooser();
        jLabel83 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        pnlStudentPersonalInfo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel18 = new javax.swing.JLabel();
        jSeparator17 = new javax.swing.JSeparator();
        jLabel19 = new javax.swing.JLabel();
        jSeparator18 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        txtCompNo = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(102, 102, 102));
        jLabel12.setText("Current Intern:");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 102, 102));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("324");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Intern: ");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("1021");

        jSeparator12.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator12.setForeground(new java.awt.Color(84, 127, 206));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Deployed:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("324");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(102, 102, 102));
        jLabel21.setText("Current Intern:");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(102, 102, 102));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("324");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(102, 102, 102));
        jLabel23.setText("Intern: ");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(102, 102, 102));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("1021");

        jSeparator13.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator13.setForeground(new java.awt.Color(84, 127, 206));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(102, 102, 102));
        jLabel25.setText("Deployed:");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(102, 102, 102));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel26.setText("324");

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1089, 520));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Right_Button_35px.png"))); // NOI18N
        jLabel17.setToolTipText("Next");
        jLabel17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel17MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel17MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel17MouseReleased(evt);
            }
        });

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Prev_35px.png"))); // NOI18N
        jLabel16.setToolTipText("Previous");
        jLabel16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel16MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel16MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel16MouseReleased(evt);
            }
        });

        pnlStudentPersonalInfo4.setBackground(new java.awt.Color(255, 255, 255));
        pnlStudentPersonalInfo4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Intern List", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N
        pnlStudentPersonalInfo4.setPreferredSize(new java.awt.Dimension(362, 500));

        jScrollPane5.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jTable5.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jTable5.setForeground(new java.awt.Color(102, 102, 102));
        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Name", "Course", "Level", "Hours"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
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

        jComboBox4.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jComboBox4.setForeground(new java.awt.Color(102, 102, 102));
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Course" }));
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

        jComboBox3.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jComboBox3.setForeground(new java.awt.Color(102, 102, 102));
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Department" }));
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

        jComboBox6.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jComboBox6.setForeground(new java.awt.Color(102, 102, 102));
        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hours" }));
        jComboBox6.setBorder(null);
        jComboBox6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox6ItemStateChanged(evt);
            }
        });
        jComboBox6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jComboBox6FocusGained(evt);
            }
        });

        jComboBox7.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jComboBox7.setForeground(new java.awt.Color(102, 102, 102));
        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Level" }));
        jComboBox7.setBorder(null);
        jComboBox7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox7ItemStateChanged(evt);
            }
        });
        jComboBox7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jComboBox7FocusGained(evt);
            }
        });

        jLabel86.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel86.setForeground(new java.awt.Color(102, 102, 102));
        jLabel86.setText("Filter by Date:");

        txtFromDate4.setBackground(new java.awt.Color(255, 255, 255));
        txtFromDate4.setForeground(new java.awt.Color(102, 102, 102));
        txtFromDate4.setDateFormatString("yyyy-MM-dd");
        txtFromDate4.setFocusable(false);
        txtFromDate4.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtFromDate4.setRequestFocusEnabled(false);
        txtFromDate4.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtFromDate4InputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
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

        txtFromDate3.setBackground(new java.awt.Color(255, 255, 255));
        txtFromDate3.setForeground(new java.awt.Color(102, 102, 102));
        txtFromDate3.setDateFormatString("yyyy-MM-dd");
        txtFromDate3.setFocusable(false);
        txtFromDate3.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtFromDate3.setRequestFocusEnabled(false);
        txtFromDate3.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtFromDate3InputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
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

        jLabel82.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout pnlStudentPersonalInfo4Layout = new javax.swing.GroupLayout(pnlStudentPersonalInfo4);
        pnlStudentPersonalInfo4.setLayout(pnlStudentPersonalInfo4Layout);
        pnlStudentPersonalInfo4Layout.setHorizontalGroup(
            pnlStudentPersonalInfo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfo4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlStudentPersonalInfo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlStudentPersonalInfo4Layout.createSequentialGroup()
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlStudentPersonalInfo4Layout.createSequentialGroup()
                        .addComponent(jLabel86)
                        .addGap(18, 18, 18)
                        .addComponent(txtFromDate4, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel83)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFromDate3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel82, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlStudentPersonalInfo4Layout.setVerticalGroup(
            pnlStudentPersonalInfo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfo4Layout.createSequentialGroup()
                .addGroup(pnlStudentPersonalInfo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtFromDate3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtFromDate4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel86, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel83, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlStudentPersonalInfo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlStudentPersonalInfo.setBackground(new java.awt.Color(255, 255, 255));
        pnlStudentPersonalInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Company Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Web_Camera_125px.png"))); // NOI18N
        jLabel1.setToolTipText("");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Business_18px_1.png"))); // NOI18N

        jSeparator3.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator3.setForeground(new java.awt.Color(84, 127, 206));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Location_18px.png"))); // NOI18N

        jSeparator7.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator7.setForeground(new java.awt.Color(84, 127, 206));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Address_18px.png"))); // NOI18N

        jSeparator8.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator8.setForeground(new java.awt.Color(84, 127, 206));

        jScrollPane4.setBorder(null);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(102, 102, 102));
        jTextArea1.setRows(5);
        jTextArea1.setText("Address");
        jTextArea1.setBorder(null);
        jScrollPane4.setViewportView(jTextArea1);

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Grid_2_18px.png"))); // NOI18N

        jSeparator17.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator17.setForeground(new java.awt.Color(84, 127, 206));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Connect_Develop_18px.png"))); // NOI18N

        jSeparator18.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator18.setForeground(new java.awt.Color(84, 127, 206));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Date Added: 01/01/2019");

        jButton3.setBackground(new java.awt.Color(84, 127, 206));
        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("BACK TO COMPANY PROFILE");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Name");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("Branch");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(102, 102, 102));
        jLabel14.setText("Division");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(102, 102, 102));
        jLabel15.setText("Sector");

        jSeparator9.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator9.setForeground(new java.awt.Color(84, 127, 206));

        txtCompNo.setEditable(false);
        txtCompNo.setBackground(new java.awt.Color(255, 255, 255));
        txtCompNo.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtCompNo.setForeground(new java.awt.Color(102, 102, 102));
        txtCompNo.setText("Contact Number");
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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCompNoKeyReleased(evt);
            }
        });

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Phone_18px_1.png"))); // NOI18N

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(102, 102, 102));
        jLabel27.setText("Current Intern:");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(102, 102, 102));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel28.setText("324");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(102, 102, 102));
        jLabel29.setText("Intern: ");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(102, 102, 102));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText("1021");

        jSeparator14.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator14.setForeground(new java.awt.Color(84, 127, 206));

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(102, 102, 102));
        jLabel31.setText("Deployed:");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(102, 102, 102));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel32.setText("324");

        javax.swing.GroupLayout pnlStudentPersonalInfoLayout = new javax.swing.GroupLayout(pnlStudentPersonalInfo);
        pnlStudentPersonalInfo.setLayout(pnlStudentPersonalInfoLayout);
        pnlStudentPersonalInfoLayout.setHorizontalGroup(
            pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addGap(0, 150, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(0, 145, Short.MAX_VALUE))
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator17, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator18, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator7)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                            .addComponent(jSeparator3)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator9)
                            .addComponent(txtCompNo)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59)
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator14))
                .addContainerGap())
        );
        pnlStudentPersonalInfoLayout.setVerticalGroup(
            pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCompNo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator18, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jButton3))
        );

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 51, 51));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlStudentPersonalInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlStudentPersonalInfo4, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pnlStudentPersonalInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pnlStudentPersonalInfo4, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
                            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
if(isDept){
    startCompList = 0;
        getCompanyList(startCompList,endCompList); 
}   
        //  DeptBox();     // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ItemStateChanged

    private void jComboBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox4ItemStateChanged
if(isCourse){
    startCompList = 0;
        getCompanyList(startCompList,endCompList); 
}            //   CourseBox();  // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4ItemStateChanged

    private void jTable5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable5KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable5KeyTyped

    private void jTable5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable5KeyReleased

    }//GEN-LAST:event_jTable5KeyReleased

    private void jTable5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable5KeyPressed

    }//GEN-LAST:event_jTable5KeyPressed

    private void jTable5PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTable5PropertyChange

    }//GEN-LAST:event_jTable5PropertyChange

    private void jTable5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable5MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable5MousePressed

    private void jTable5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable5MouseClicked

    }//GEN-LAST:event_jTable5MouseClicked

    private void jLabel17MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseEntered
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Right_Button_37px_1.png")));        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel17MouseEntered

    private void jLabel17MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseExited
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Right_Button_35px.png")));
    }//GEN-LAST:event_jLabel17MouseExited

    private void jLabel16MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseEntered
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Prev_37px.png")));        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel16MouseEntered

    private void jLabel16MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseExited
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Prev_35px.png")));
    }//GEN-LAST:event_jLabel16MouseExited

    private void txtCompNoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCompNoKeyReleased

                  // TODO add your handling code here:
    }//GEN-LAST:event_txtCompNoKeyReleased

    private void txtCompNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCompNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCompNoActionPerformed

    private void txtCompNoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCompNoMouseClicked
        
               // TODO add your handling code here:
    }//GEN-LAST:event_txtCompNoMouseClicked

    private void txtCompNoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCompNoFocusLost
      // TODO add your handling code here:
    }//GEN-LAST:event_txtCompNoFocusLost

    private void txtCompNoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCompNoFocusGained
             // TODO add your handling code here:
    }//GEN-LAST:event_txtCompNoFocusGained

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
f.ClassDisconnected.flag = false;
        new DynamicPanel(Home.pnlDynamic, new panels.pnlCompanyProfile());        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtFromDate4InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtFromDate4InputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFromDate4InputMethodTextChanged

    private void txtFromDate4PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtFromDate4PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFromDate4PropertyChange

    private void txtFromDate4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFromDate4KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFromDate4KeyReleased

    private void txtFromDate3InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtFromDate3InputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFromDate3InputMethodTextChanged

    private void txtFromDate3PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtFromDate3PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFromDate3PropertyChange

    private void jComboBox3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBox3FocusGained
isDept = true;        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3FocusGained

    private void jComboBox4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBox4FocusGained
isCourse = true;         // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4FocusGained

    private void jComboBox7FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBox7FocusGained
isLevel = true;        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox7FocusGained

    private void jComboBox6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBox6FocusGained
isHours = true;         // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox6FocusGained

    private void jComboBox6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox6ItemStateChanged
if(isHours){
    startCompList = 0;
        getCompanyList(startCompList,endCompList); 
}                // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox6ItemStateChanged

    private void jComboBox7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox7ItemStateChanged
if(isLevel){
    startCompList = 0;
        getCompanyList(startCompList,endCompList); 
}            // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox7ItemStateChanged

    private void jLabel16MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseReleased
f.ClassDisconnected.flag = false;
        UIManagers.getNewUI();
        if(startCompList<0 || startCompList==0 || listPage<0 || listPage==0 ){ 
            jLabel33.setText("Already in first page... "+listPage);
        }else{
            listPage--;
            lastCompList = false;
            startCompList-=endCompList; 
             getCompanyList(startCompList,endCompList); 
            jLabel33.setText("Page: "+listPage); 
        } 
     UIManagers.applyOldUI();          // TODO add your handling code here:
    }//GEN-LAST:event_jLabel16MouseReleased

    private void jLabel17MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseReleased
f.ClassDisconnected.flag = false;
        if(!lastCompList){
            startCompList+=endCompList;
             String ssearch = "";
        String sdept ="";
        String scourse = "";
        String slevel  =  "";
        String shours = "";
        String getFromDate = "";
        String getToDate= "";
        
       if(txtFromDate4.getDate()==null || txtFromDate3.getDate()==null){
               getFromDate =  "0000-00-00";
               getToDate =  "9999-12-31";
        }else{
           getFromDate = ((JTextField)txtFromDate4.getDateEditor().getUiComponent()).getText();
           getToDate = ((JTextField)txtFromDate3.getDateEditor().getUiComponent()).getText();   
       }
         
        if(!home.Home.jTextField1.getText().equals("Search...")){
          ssearch = home.Home.jTextField1.getText();
       }else{
            ssearch = "";
        }
        if(jComboBox3.getSelectedItem().equals("Department")){
              sdept ="";
        }else{
            sdept = jComboBox3.getSelectedItem().toString();
        }
          if(jComboBox4.getSelectedItem().equals("Course")){
              scourse ="";
        }else{
            scourse = jComboBox4.getSelectedItem().toString();
        }
        
          if(jComboBox7.getSelectedItem().equals("Level")){
              slevel ="";
        }else{
            slevel = jComboBox7.getSelectedItem().toString();
        }
        
          if(jComboBox6.getSelectedItem().equals("Hours")){
              shours ="";
        }else{
            shours = jComboBox6.getSelectedItem().toString();
        }
         
          String sql = "SELECT sr.dateadded as Date,concat( si.`s_lastname`,', ',si.`s_firstname`,' ',si.`s_middlename`,' ',si.`s_suffixname`) as Name,\n" +
"sc.s_course_title as Course,\n" +
"sl.s_level_title as Level,\n" +
"oh.ojt_hours_title as Hours\n" +
"FROM `student_records` sr\n" +
"inner join student_information si\n" +
"on sr.`s_information_id`=si.s_student_no\n" +
"inner join student_courses sc ON\n" +
"sr.`s_course_id`=sc.s_course_id\n" +
"inner join student_levels sl ON\n" +
"sr.`s_year_level_id`=sl.s_level_id\n" +
"INNER JOIN ojt_information oi ON\n" +
"sr.`s_ojt_information_id`=oi.ojt_information_id\n" +
"inner JOIN ojt_hours oh ON\n" +
"oi.ojt_hours_id=oh.ojt_hours_id\n" +
"INNER JOIN student_departments sd ON\n" +
"sr.`department_id`=sd.department_id\n" +
"where sl.s_level_title!='EMPLOYED'\n" +
"and oi.c_information_id='"+SelectedCompany.compid+"'\n" +
"and concat(si.`s_student_no`,' ',si.`s_lastname`,' ',si.`s_firstname`,' ',si.`s_middlename`,' ',si.`s_suffixname`) LIKE '%"+ssearch+"%'\n" +
"AND sd.dept_abbr LIKE '%"+sdept+"%'\n" +
"AND sc.s_course_title LIKE '%"+scourse+"%'\n" +    
"AND sl.s_level_title LIKE '%"+slevel+"%'\n" +
"AND oh.ojt_hours_title LIKE '%"+shours+"%'\n" +
"AND si.record_date>='"+getFromDate+" 00:00:00' AND   si.record_date < '"+getToDate+" 23:59:59'  \n" +
"ORDER BY sr.dateadded DESC\n" +
"LIMIT "+startCompList+","+endCompList+""; 
            try{
                conn = SQLCon.ConnectDB();
                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();
                if(rs.next()){
                    listPage++;
                    lastCompList = false; 
                     getCompanyList(startCompList,endCompList);
            jLabel33.setText("Page: "+listPage);
                }else{
                   UIManagers.getNewUI(); 
                    lastCompList = true;
                    startCompList-=endCompList;
                    
                   // JOptionPane.showMessageDialog(null, "This is the last page...");
                   jLabel33.setText("Already in last page... "+listPage);
                  UIManagers.applyOldUI();
                }
                conn.close();
            }catch (Exception e) {
                 startCompList-=endCompList;
                 DC();
            e.printStackTrace();
             //   //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1017", JOptionPane.ERROR_MESSAGE);
            }
        }else{
          UIManagers.getNewUI(); 
                   jLabel33.setText("Already in last page... "+listPage); 
          UIManagers.applyOldUI();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel17MouseReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton3;
    public static javax.swing.JComboBox<String> jComboBox3;
    public static javax.swing.JComboBox<String> jComboBox4;
    public static javax.swing.JComboBox<String> jComboBox6;
    public static javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
    public static javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane4;
    public static javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    public static javax.swing.JTable jTable5;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JPanel pnlStudentPersonalInfo;
    private javax.swing.JPanel pnlStudentPersonalInfo4;
    private javax.swing.JTextField txtCompNo;
    public static com.toedter.calendar.JDateChooser txtFromDate3;
    public static com.toedter.calendar.JDateChooser txtFromDate4;
    // End of variables declaration//GEN-END:variables
}
