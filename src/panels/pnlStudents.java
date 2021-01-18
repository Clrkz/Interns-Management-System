/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panels;

import f.GetStudentInfo;
import f.SQLCon;
import f.SelectedStudent;
import f.UIManagers;
import home.Home;
import java.awt.Color;
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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.AbstractButton;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
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
 * @author RojeruSan
 */
public class pnlStudents extends javax.swing.JPanel  implements ActionListener {
public static Connection conn = null;
public static PreparedStatement pst =  null;
public static ResultSet rs = null; 
public static int startCompList = 0,endCompList = 15;
  public static boolean lastCompList = false;
   public static int listPage = 1;
   boolean isDept = false;
   boolean isCourse = false;
   boolean isLevel = false;
   boolean isHours = false;
   public static boolean selected = false;
   	private JTable table;
	private DefaultTableModel tableModel; 
	private JPopupMenu popupMenu;
	private JMenuItem menuItemCopy;
	private JMenuItem menuItemAdd;
	private JMenuItem menuItemRemove;
	private JMenuItem menuItemRemoveAll;
        //static pnlStudents cmp = new pnlStudents();
        
         static pnlStudents cmp;
     public static void main(String[] args) {
        try {
            // The following line would throw ExceptionInInitializerError
           cmp = new pnlStudents();
        } catch (Throwable t) {
            System.out.println(t);
        }
        // The following line would cause NoClassDefFoundError
        cmp = new pnlStudents();
    }
     
     
    /**
     * Creates new form pnlHome
     */
    public pnlStudents() {
        initComponents(); 
        f.ClassDisconnected.flag = false;
                   Home.printcompanylist = false;
        Home.printhistorylist = false; 
      Home.printuserrecord = false;
      Home.printuserlist = false;
      Home.printuserprofile  = false; 
      Home.printloglist = false;
      Home.printinternlist = false;
      Home.printdeployedlist = false;
     Home.printstudentlist = true;
    Home.printstudentlog = false;
     Home.printstudentact = false;
         
          home.Home.jTextField1.setText("Search..."); 
        getDepartment();
        getLevels();
        getCourse();
        getHours();
        jLabel2.setText("Page: "+listPage); 
       getCompanyList(startCompList,endCompList);
         jScrollPane1.getViewport().setBackground(Color.WHITE);
         jScrollPane2.getViewport().setBackground(Color.WHITE);
       // DeptBox();CourseBox();YearBox();OjtHours();
        ChangeTableList();FilterCboColor();StudentContactList();
          jTable1.getTableHeader().setReorderingAllowed(false);
          jTable2.getTableHeader().setReorderingAllowed(false);
          
          
           txtFromDate4.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() { 
             @Override
             public void propertyChange(PropertyChangeEvent e) {
                 if(txtFromDate4.getDate()!=null && txtFromDate3.getDate()!=null ){
                     startCompList = 0;
                        jLabel82.setText("<html>Result between <font color='blue'><strong>"+((JTextField)txtFromDate4.getDateEditor().getUiComponent()).getText()+"</strong></font> and <font color='blue'><strong>"+((JTextField)txtFromDate3.getDateEditor().getUiComponent()).getText()+"</strong></font></html>");
                     lastCompList = false;
                     getCompanyList(startCompList,endCompList);   
                 listPage = 1;
            jLabel2.setText("Page: "+listPage); 
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
                       jLabel82.setText("<html>Result between <font color='blue'><strong>"+((JTextField)txtFromDate4.getDateEditor().getUiComponent()).getText()+"</strong></font> and <font color='blue'><strong>"+((JTextField)txtFromDate3.getDateEditor().getUiComponent()).getText()+"</strong></font></html>");
                     lastCompList = false;
                     getCompanyList(startCompList,endCompList);   
                 listPage = 1;
            jLabel2.setText("Page: "+listPage); 
                 } else{ 
                     getCompanyList(startCompList,endCompList);   
                     jLabel82.setText("");
                 }
             }
         });
          SelectedStudent.sid = "";
     
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
		jTable2.setComponentPopupMenu(popupMenu);
		
		jTable2.addMouseListener(new TableMouseListener(jTable2));
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
        
          if(jComboBox6.getSelectedItem().equals("Hours (All)")){
              shours ="";
        }else{
            shours = jComboBox6.getSelectedItem().toString();
        }
        
         String sql = "SELECT si.`s_student_no` as `Student No.`, \n" +
"concat( si.`s_lastname`,', ',si.`s_firstname`,' ',si.`s_middlename`,' ',si.`s_suffixname`) as Name,\n" +
"sc.s_course_title as Course, \n" +
"si.`s_dateadded` as `Date Reg.`\n" +
"FROM `student_information` si \n" +
"INNER JOIN student_departments sd\n" +
"on si.s_department_id=sd.department_id\n" +
"INNER JOIN student_courses sc \n" +
"on si.s_course_id=sc.s_course_id\n" +
"INNER JOIN student_levels sl \n" +
"on si.s_level_id=sl.s_level_id\n" +
"INNER JOIN ojt_hours oh \n" +
"on si.ojt_hours_id=oh.ojt_hours_id\n" +
"WHERE concat(si.`s_student_no`,' ',si.`s_lastname`,' ',si.`s_firstname`,' ',si.`s_middlename`,' ',si.`s_suffixname`) LIKE '%"+ssearch+"%'\n" +
"AND sd.dept_abbr LIKE '%"+sdept+"%'\n" +
"AND sc.s_course_title LIKE '%"+scourse+"%'\n" +
"AND sl.s_level_title LIKE '%"+slevel+"%'\n" +
"AND oh.ojt_hours_title LIKE '%"+shours+"%'\n" +
"AND si.record_date>='"+getFromDate+" 00:00:00' AND   si.record_date < '"+getToDate+" 23:59:59'  \n" +
"ORDER BY si.`s_lastname` ASC\n" +
"LIMIT "+start+","+end+""; 
           try{
           conn = SQLCon.ConnectDB();
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery();
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));  
            ChangeTableList();
            conn.close();
        }catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1059", JOptionPane.ERROR_MESSAGE);
        }
     
    }
    
      public static void printCompanyList(){
           conn = SQLCon.ConnectDB();
         String ssearch = "";
        String sdept ="";
        String scourse = "";
        String slevel  =  "";
        String shours = "";
        String getFromDate = "";
        String getToDate= "";
       
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
        
          if(jComboBox6.getSelectedItem().equals("Hours (All)")){
              shours ="";
        }else{
            shours = jComboBox6.getSelectedItem().toString();
        }
          
          String sql="";
          if(txtFromDate4.getDate()==null || txtFromDate3.getDate()==null){
               getFromDate =  "0000-00-00";
               getToDate =  "9999-12-31";
                sql = "SELECT si.`s_student_no` as `Student No.`, \n" +
"concat( si.`s_lastname`,', ',si.`s_firstname`,' ',si.`s_middlename`,' ',si.`s_suffixname`) as Name,\n" +
"sc.s_course_title as Course, \n" +     
"sl.s_level_title as level, \n" +
"si.`s_dateadded` as `Date Reg.`\n" +
"FROM `student_information` si \n" +
"INNER JOIN student_departments sd\n" +
"on si.s_department_id=sd.department_id\n" +
"INNER JOIN student_courses sc \n" +
"on si.s_course_id=sc.s_course_id\n" +
"INNER JOIN student_levels sl \n" +
"on si.s_level_id=sl.s_level_id\n" +
"INNER JOIN ojt_hours oh \n" +
"on si.ojt_hours_id=oh.ojt_hours_id\n" +
"WHERE concat(si.`s_student_no`,' ',si.`s_lastname`,' ',si.`s_firstname`,' ',si.`s_middlename`,' ',si.`s_suffixname`) LIKE '%"+ssearch+"%'\n" +
"AND sd.dept_abbr LIKE '%"+sdept+"%'\n" +
"AND sc.s_course_title LIKE '%"+scourse+"%'\n" +
"AND sl.s_level_title LIKE '%"+slevel+"%'\n" +
"AND oh.ojt_hours_title LIKE '%"+shours+"%'\n" +
"AND si.record_date>='"+getFromDate+" 00:00:00' AND   si.record_date < '"+getToDate+" 23:59:59'  \n" +
"ORDER BY si.`s_lastname` ASC "; 
        }else{
           getFromDate = ((JTextField)txtFromDate4.getDateEditor().getUiComponent()).getText();
           getToDate = ((JTextField)txtFromDate3.getDateEditor().getUiComponent()).getText();   
            sql = "SELECT sr.`s_information_id` as `Student No.`,\n" +
"(SELECT  concat( si.`s_lastname`,', ',si.`s_firstname`,' ',si.`s_middlename`,' ',si.`s_suffixname`) from student_information si where s_student_no=sr.`s_information_id`)  as Name,\n" +
"(select s_course_title from student_courses where s_course_id=sr.`s_course_id`) as Course,\n" +
"(select s_level_title from student_levels where s_level_id=sr.`s_year_level_id`) as Level,\n" +
"`dateadded`  as `Date Reg.`\n" +
"FROM `student_records` sr\n" +
"INNER join student_information si\n" +
"on sr.`s_information_id`=si.s_student_no\n" +
"INNER JOIN student_departments sd\n" +
"on sr.`department_id`=sd.department_id\n" +
"INNER JOIN student_courses sc \n" +
"on sr.`s_course_id`=sc.s_course_id\n" +
"INNER JOIN student_levels sl \n" +
"on sr.`s_year_level_id`=sl.s_level_id\n" +
"inner join ojt_information oi\n" +
"on sr.`s_ojt_information_id`=oi.ojt_information_id\n" +
"inner join ojt_hours oh\n" +
"on oi.ojt_hours_id=oh.ojt_hours_id\n" +
"WHERE\n" +
"concat(si.`s_student_no`,' ',si.`s_lastname`,' ',si.`s_firstname`,' ',si.`s_middlename`,' ',si.`s_suffixname`) LIKE '%%'\n" +
"AND sd.dept_abbr LIKE '%"+sdept+"%'\n" +
"AND sc.s_course_title LIKE '%"+scourse+"%'\n" +
"AND sl.s_level_title LIKE '%"+slevel+"%'\n" +
"AND oh.ojt_hours_title LIKE '%"+shours+"%'\n" +
"AND sr.`dateadded`>='"+getFromDate+" 00:00:00' AND   sr.`dateadded` < '"+getToDate+" 23:59:59'  \n" +
"ORDER BY si.`s_lastname` ASC"; 
       }
         
           try{
           JasperDesign jd = JRXmlLoader.load("src\\reports\\studentlist.jrxml");
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
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1059", JOptionPane.ERROR_MESSAGE);
        }
     
    }
    
    
        
            public void getHours(){ 
        String sql = "select ojt_hours_title from ojt_hours where status=1 ORDER by ojt_hours_title ASC";
        try{
             conn = SQLCon.ConnectDB();
            jComboBox6.removeAllItems();
            jComboBox6.addItem("Hours (All)");
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery();
            while(rs.next()){
      jComboBox6.addItem(rs.getString(1));
            }
            conn.close();
        }catch(Exception e){
            DC();
            e.printStackTrace();
             //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1076", JOptionPane.ERROR_MESSAGE);
      } 
   }
        
    
    
       public void getDepartment(){  
        String sql = "select dept_abbr from student_departments where status=1 ORDER by dept_abbr ASC";
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
           //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1073", JOptionPane.ERROR_MESSAGE);
        } 
   }
      
       public void getCourse(){  
        String sql = "SELECT  `s_course_title`  FROM `student_courses` WHERE  `status`=1 ";
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
        public void getLevels(){ 
        String sql = "select s_level_title from student_levels where status=1 ORDER by s_level_title ASC";
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
    
    
     public void FilterCboColor(){
        jComboBox3.setBackground(Color.WHITE);
        jComboBox4.setBackground(Color.WHITE);
        jComboBox5.setBackground(Color.WHITE);
        jComboBox6.setBackground(Color.WHITE);
    }
    public static void StudentContactList(){
          JTableHeader Theader = jTable2.getTableHeader();
        Theader.setBackground(new Color(255,255,255)); // change the Background color
        Theader.setForeground(Color.WHITE); // change the Foreground
        Theader.setFont(new Font("Tahome", Font.BOLD, 14)); // font name style size
        ((DefaultTableCellRenderer)Theader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER); // center header text
      
       jTable2.getColumnModel().getColumn(0).setPreferredWidth(10);
        jTable2.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTable2.setDefaultEditor(Object.class, null);
        jTable2.setSelectionModel(new ForcedListSelectionModel());
        jTable2.getTableHeader().setUI(null);
         
        
    }
    public static void ChangeTableList(){
          JTableHeader Theader = jTable1.getTableHeader();
        Theader.setBackground(new Color(84,127,206)); // change the Background color
        Theader.setForeground(Color.WHITE); // change the Foreground
        Theader.setFont(new Font("Tahome", Font.BOLD, 14)); // font name style size
        ((DefaultTableCellRenderer)Theader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER); // center header text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER); 
             jTable1.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
       jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(250); 
            jTable1.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(15);
        jTable1.setDefaultEditor(Object.class, null);
        jTable1.setSelectionModel(new ForcedListSelectionModel());
        
    }

    @Override
    public void actionPerformed(ActionEvent event) {
     JMenuItem menu = (JMenuItem) event.getSource();
		if (menu == menuItemAdd) {
			 
		} else if(menu == menuItemCopy){
                         int row = jTable2.getSelectedRow();
        String contact = jTable2.getModel().getValueAt(row, 1).toString();
        StringSelection stringSelection = new StringSelection(contact);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
                }else if (menu == menuItemRemove) {
			 
		} else if (menu == menuItemRemoveAll) {
		 
		}
    }
    
    
    
    public static class ForcedListSelectionModel extends DefaultListSelectionModel {

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
    
     public void DeptBox(){
        jComboBox3.setBackground(Color.WHITE);
    jComboBox3.requestFocus(false);
        for (int i = 0; i < jComboBox3.getComponentCount(); i++) 
{
    if (jComboBox3.getComponent(i) instanceof JComponent) {
        ((JComponent) jComboBox3.getComponent(i)).setBorder(new EmptyBorder(0, 0,0,0));
    }


    if (jComboBox3.getComponent(i) instanceof AbstractButton) {
        ((AbstractButton) jComboBox3.getComponent(i)).setBorderPainted(false);
    }
}      
    }
     public void CourseBox(){
        jComboBox4.setBackground(Color.WHITE);
    jComboBox4.requestFocus(false);
        for (int i = 0; i < jComboBox4.getComponentCount(); i++) 
{
    if (jComboBox4.getComponent(i) instanceof JComponent) {
        ((JComponent) jComboBox4.getComponent(i)).setBorder(new EmptyBorder(0, 0,0,0));
    }


    if (jComboBox4.getComponent(i) instanceof AbstractButton) {
        ((AbstractButton) jComboBox4.getComponent(i)).setBorderPainted(false);
    }
}      
    }
     
     public void YearBox(){
        jComboBox5.setBackground(Color.WHITE);
    jComboBox5.requestFocus(false);
        for (int i = 0; i < jComboBox5.getComponentCount(); i++) 
{
    if (jComboBox5.getComponent(i) instanceof JComponent) {
        ((JComponent) jComboBox5.getComponent(i)).setBorder(new EmptyBorder(0, 0,0,0));
    }


    if (jComboBox5.getComponent(i) instanceof AbstractButton) {
        ((AbstractButton) jComboBox5.getComponent(i)).setBorderPainted(false);
    }
}      
    }
    
 public void OjtHours(){
        jComboBox6.setBackground(Color.WHITE);
    jComboBox6.requestFocus(false);
        for (int i = 0; i < jComboBox6.getComponentCount(); i++) 
{
    if (jComboBox6.getComponent(i) instanceof JComponent) {
        ((JComponent) jComboBox6.getComponent(i)).setBorder(new EmptyBorder(0, 0,0,0));
    }


    if (jComboBox6.getComponent(i) instanceof AbstractButton) {
        ((AbstractButton) jComboBox6.getComponent(i)).setBorderPainted(false);
    }
}      
    }
    
  public static void CompanyListFunction(){
                 int row =  jTable1.getSelectedRow(); 
       SelectedStudent.sid = jTable1.getModel().getValueAt(row, 0).toString(); 
       SelectedStudent.sdateadd = jTable1.getModel().getValueAt(row, 3).toString(); 
       
         GetStudentInfo.getPofileInfo(SelectedStudent.sid);
         
             jLabel9.setText("Date Registered: "+SelectedStudent.sdateadd); 
                  jLabel13.setText(SelectedStudent.sid );
                  jLabel14.setText(SelectedStudent.fname);
                  jLabel15.setText(SelectedStudent.mname);
                  jLabel16.setText(SelectedStudent.lname );
                  jLabel17.setText(SelectedStudent.sname);
                  jLabel18.setText(SelectedStudent.sgender); 
                  
                  
       try{
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(SelectedStudent.img).getImage().getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(), Image.SCALE_SMOOTH));
                jLabel1.setIcon(imageIcon); 
             //   pnlCompanyProfile.lblImage.setIcon(imageIcon);
        }catch(Exception e){
            jLabel1.setIcon(new javax.swing.ImageIcon(pnlStudents.class.getResource("/images/icons8_Name_125px.png")));
//             System.out.println("No Image\n"+e);
        }   
       getContact(SelectedStudent.sid);
       selected = true;
            }
 
   public static void getContact(String sid){
        String sql = "SELECT ct.s_contact_type_title,sc.s_contact_info FROM student_contact sc \n" +
"INNER JOIN student_contact_type ct\n" +
"ON sc.s_contact_type_id=ct.s_contact_type_id\n" +
"WHERE s_information_id='"+sid+"' ORDER BY ct.s_contact_type_title ASC"; 
           try{ 
                conn = SQLCon.ConnectDB();
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery();
            jTable2.setModel(DbUtils.resultSetToTableModel(rs));  
           StudentContactList();
           conn.close();
        }catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1059", JOptionPane.ERROR_MESSAGE);
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
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jButton7 = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jComboBox5 = new javax.swing.JComboBox<>();
        jComboBox6 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        txtFromDate4 = new com.toedter.calendar.JDateChooser();
        jLabel83 = new javax.swing.JLabel();
        txtFromDate3 = new com.toedter.calendar.JDateChooser();
        jLabel82 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1090, 520));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Prev_35px.png"))); // NOI18N
        jLabel7.setToolTipText("Previous");
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel7MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel7MouseReleased(evt);
            }
        });

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Right_Button_35px.png"))); // NOI18N
        jLabel8.setToolTipText("Next");
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel8MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel8MouseReleased(evt);
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
                "Student No.", "Name", "Course", "Date Reg"
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
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable1KeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Name_125px.png"))); // NOI18N
        jLabel1.setToolTipText("Add photo");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Student No. :");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Firstname :");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Middlename :");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Lastname :");

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jTable2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTable2.setForeground(new java.awt.Color(102, 102, 102));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setGridColor(new java.awt.Color(255, 255, 255));
        jTable2.setRowHeight(25);
        jTable2.setRowSelectionAllowed(false);
        jTable2.setShowHorizontalLines(false);
        jTable2.setShowVerticalLines(false);
        jScrollPane2.setViewportView(jTable2);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Date Registered");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Suffix :");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("Contact Information:");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(102, 102, 102));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Gender :");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 102, 102));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(102, 102, 102));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(102, 102, 102));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(102, 102, 102));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(102, 102, 102));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(102, 102, 102));

        jButton7.setBackground(new java.awt.Color(84, 127, 206));
        jButton7.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("VIEW STUDENT RECORDS");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jSeparator1)
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 96, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(92, 92, 92))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7))
        );

        jComboBox3.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jComboBox3.setForeground(new java.awt.Color(102, 102, 102));
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Department (All)" }));
        jComboBox3.setBorder(null);
        jComboBox3.setMinimumSize(new java.awt.Dimension(77, 20));
        jComboBox3.setPreferredSize(new java.awt.Dimension(77, 20));
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
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

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

        jComboBox6.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jComboBox6.setForeground(new java.awt.Color(102, 102, 102));
        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hours (All)" }));
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
        jComboBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox6ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 51, 51));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel86.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel86.setForeground(new java.awt.Color(102, 102, 102));
        jLabel86.setText("Filter by Date of Record:");

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

        jLabel83.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(102, 102, 102));
        jLabel83.setText("to");

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

        jLabel82.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 729, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jComboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel86)
                        .addGap(18, 18, 18)
                        .addComponent(txtFromDate4, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel83)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFromDate3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel82, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtFromDate3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtFromDate4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel86, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel83, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
if(isDept){
    
     lastCompList = false; 
     listPage = 1;
     jLabel2.setText("Page: "+listPage);
    
    startCompList = 0;
        getCompanyList(startCompList,endCompList); 
}      
    }//GEN-LAST:event_jComboBox3ItemStateChanged

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox4ItemStateChanged
if(isCourse){
     lastCompList = false; 
     listPage = 1;
     jLabel2.setText("Page: "+listPage);
    startCompList = 0;
        getCompanyList(startCompList,endCompList); 
}         
    }//GEN-LAST:event_jComboBox4ItemStateChanged

    private void jComboBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox6ActionPerformed

    private void jComboBox6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox6ItemStateChanged
if(isHours){
     lastCompList = false; 
     listPage = 1;
     jLabel2.setText("Page: "+listPage);
    startCompList = 0;
        getCompanyList(startCompList,endCompList); 
}        
    }//GEN-LAST:event_jComboBox6ItemStateChanged

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jComboBox5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox5ItemStateChanged
if(isLevel){
     lastCompList = false; 
     listPage = 1;
     jLabel2.setText("Page: "+listPage);
    startCompList = 0;
        getCompanyList(startCompList,endCompList); 
}      
    }//GEN-LAST:event_jComboBox5ItemStateChanged

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
  
    }//GEN-LAST:event_jTable1MouseClicked

    private void jLabel7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseEntered
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Prev_37px.png")));        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel7MouseEntered

    private void jLabel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseExited
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Prev_35px.png")));
    }//GEN-LAST:event_jLabel7MouseExited

    private void jLabel8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseEntered
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Right_Button_37px_1.png")));        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel8MouseEntered

    private void jLabel8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseExited
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Right_Button_35px.png")));
    }//GEN-LAST:event_jLabel8MouseExited

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4ActionPerformed

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

    private void jLabel8MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseReleased
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
        
          if(jComboBox6.getSelectedItem().equals("Hours (All)")){
              shours ="";
        }else{
            shours = jComboBox6.getSelectedItem().toString();
        }
        
         String sql = "SELECT si.`s_student_no` as `Student No.`, \n" +
"concat( si.`s_lastname`,', ',si.`s_firstname`,' ',si.`s_middlename`,' ',si.`s_suffixname`) as Name,\n" +
"sc.s_course_title as Course, \n" +
"si.`s_dateadded` as `Date Reg.`\n" +
"FROM `student_information` si \n" +
"INNER JOIN student_departments sd\n" +
"on si.s_department_id=sd.department_id\n" +
"INNER JOIN student_courses sc \n" +
"on si.s_course_id=sc.s_course_id\n" +
"INNER JOIN student_levels sl \n" +
"on si.s_level_id=sl.s_level_id\n" +
"INNER JOIN ojt_hours oh \n" +
"on si.ojt_hours_id=oh.ojt_hours_id\n" +
"WHERE concat(si.`s_student_no`,' ',si.`s_lastname`,' ',si.`s_firstname`,' ',si.`s_middlename`,' ',si.`s_suffixname`) LIKE '%"+ssearch+"%'\n" +
"AND sd.dept_abbr LIKE '%"+sdept+"%'\n" +
"AND sc.s_course_title LIKE '%"+scourse+"%'\n" +
"AND sl.s_level_title LIKE '%"+slevel+"%'\n" +
"AND oh.ojt_hours_title LIKE '%"+shours+"%'\n" +
"AND si.record_date>='"+getFromDate+" 00:00:00' AND   si.record_date < '"+getToDate+" 23:59:59'  \n" +
"ORDER BY si.`s_lastname` ASC\n" +
"LIMIT "+startCompList+","+endCompList+""; 
            try{
                conn = SQLCon.ConnectDB();
                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();
                if(rs.next()){
                    listPage++;
                    lastCompList = false; 
                     getCompanyList(startCompList,endCompList);
            jLabel2.setText("Page: "+listPage);
                }else{
                   UIManagers.getNewUI(); 
                    lastCompList = true;
                    startCompList-=endCompList;
                    
                   // JOptionPane.showMessageDialog(null, "This is the last page...");
                   jLabel2.setText("Already in last page... "+listPage);
                  UIManagers.applyOldUI();
                }
                conn.close();
            }catch (Exception e) {
                 startCompList-=endCompList;
                 DC();
                 e.printStackTrace();
                //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1017", JOptionPane.ERROR_MESSAGE);
            }
        }else{
          UIManagers.getNewUI(); 
                   jLabel2.setText("Already in last page... "+listPage); 
          UIManagers.applyOldUI();
        }
    }//GEN-LAST:event_jLabel8MouseReleased

    private void jLabel7MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseReleased
f.ClassDisconnected.flag = false;
        UIManagers.getNewUI();
        if(startCompList<0 || startCompList==0 || listPage<0 || listPage==0){ 
            jLabel2.setText("Already in first page... "+listPage);
        }else{
            listPage--;
            lastCompList = false;
            startCompList-=endCompList; 
             getCompanyList(startCompList,endCompList); 
            jLabel2.setText("Page: "+listPage); 
        } 
     UIManagers.applyOldUI();        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel7MouseReleased

    private void jComboBox3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBox3FocusGained
isDept = true;        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3FocusGained

    private void jComboBox4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBox4FocusGained
isCourse = true;        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4FocusGained

    private void jComboBox5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBox5FocusGained
isLevel = true;        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox5FocusGained

    private void jComboBox6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBox6FocusGained
isHours = true;        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox6FocusGained

    private void jTable1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseReleased
        f.ClassDisconnected.flag = false;
        CompanyListFunction();  
        
        int row =  jTable1.getSelectedRow();
  if (evt.getClickCount() == 2) {
          new DynamicPanel(Home.pnlDynamic, new panels.pnlStudentProfile());
  }
    }//GEN-LAST:event_jTable1MouseReleased

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
f.ClassDisconnected.flag = false;
CompanyListFunction();
    }//GEN-LAST:event_jTable1KeyReleased

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
f.ClassDisconnected.flag = false;
        if(selected){
   if(jTable1.getSelectedRow()<0 && SelectedStudent.sid.equals("")){
        UIManagers.getNewUI();
    JOptionPane.showMessageDialog(null, "Select Student from list");
    UIManagers.applyOldUI();
   }else{
        new DynamicPanel(Home.pnlDynamic, new panels.pnlStudentProfile()); 
   }
}else{
    UIManagers.getNewUI();
    JOptionPane.showMessageDialog(null, "Select Student from list");
    UIManagers.applyOldUI();
    
}
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton7;
    public static javax.swing.JComboBox<String> jComboBox3;
    public static javax.swing.JComboBox<String> jComboBox4;
    public static javax.swing.JComboBox<String> jComboBox5;
    public static javax.swing.JComboBox<String> jComboBox6;
    public static javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    public static javax.swing.JLabel jLabel13;
    public static javax.swing.JLabel jLabel14;
    public static javax.swing.JLabel jLabel15;
    public static javax.swing.JLabel jLabel16;
    public static javax.swing.JLabel jLabel17;
    public static javax.swing.JLabel jLabel18;
    public static javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel86;
    public static javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    public static javax.swing.JTable jTable1;
    public static javax.swing.JTable jTable2;
    public static com.toedter.calendar.JDateChooser txtFromDate3;
    public static com.toedter.calendar.JDateChooser txtFromDate4;
    // End of variables declaration//GEN-END:variables
}
