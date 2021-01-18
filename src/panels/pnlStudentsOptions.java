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
import java.awt.Font;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author RojeruSan
 */
public class pnlStudentsOptions extends javax.swing.JPanel {
    public static Connection conn = null;
public static PreparedStatement pst =  null;
public static ResultSet rs = null;
AuditMessage am= new AuditMessage();
boolean DeptListClick = false; 
String selectedDeptID;
String selectedDeptName;
String selectedDeptAbbr;
String selectedCourseID;
String selectedCourseDept;
String selectedCourse;
String selectedLevelID;
String selectedLevel; 
//static pnlStudentsOptions cmp = new pnlStudentsOptions(); 

static pnlStudentsOptions cmp;
     public static void main(String[] args) {
        try {
            // The following line would throw ExceptionInInitializerError
           cmp = new pnlStudentsOptions();
        } catch (Throwable t) {
            System.out.println(t);
        }
        // The following line would cause NoClassDefFoundError
        cmp = new pnlStudentsOptions();
    }
          
     
     
    /**
     * Creates new form pnlHome
     */
    public pnlStudentsOptions() {
        initComponents();
f.ClassDisconnected.flag = false;
         f.DepartmentList.TableFromDatabase();
            f.LevelList.TableFromDatabase();    
            getDepartments();
       jScrollPane1.getViewport().setBackground(Color.WHITE);
        jScrollPane2.getViewport().setBackground(Color.WHITE);
        jScrollPane3.getViewport().setBackground(Color.WHITE); 
        DepartmentList();CoursesList();LevelList();
        jComboBox2.setBackground(Color.WHITE); //Courses CBOx
    //   AddCourses();AddDepartment();AddLevel();
     CourseList.setSelectionModel(new ForcedListSelectionModel());
     DeptList.setSelectionModel(new ForcedListSelectionModel());
     LevelList.setSelectionModel(new ForcedListSelectionModel()); 
          CourseList.getTableHeader().setReorderingAllowed(false); 
          DeptList.getTableHeader().setReorderingAllowed(false); 
          LevelList.getTableHeader().setReorderingAllowed(false); 
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
      
     public void getDepartments(){
        //String sql = "select department_title from student_departments where status=1 ORDER by department_id DESC";
        String sql = "select department_title from student_departments where status=1 ORDER by department_title ASC";
        try{
            conn = SQLCon.ConnectDB();
            jComboBox2.removeAllItems();
            jComboBox2.addItem("Select Department");
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery();
            while(rs.next()){
      jComboBox2.addItem(rs.getString(1));
            }
            conn.close();
        }catch(Exception e){
            DC();
            e.printStackTrace(); 
        } 
   }
    
    
    public void AddCourses(){
        //ADD Contact Start
         DefaultTableModel model = (DefaultTableModel) CourseList.getModel();
          jButton1.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             if(jComboBox2.getSelectedItem().toString().equals("Select Department")){
    JOptionPane.showMessageDialog(null, "Select Department");
    jComboBox2.requestFocus();
             } else if(!jTextField1.getText().equals("Course") && !jTextField1.getText().equals("")){
              model.addRow(new Object[]{
                               jComboBox2.getSelectedItem(),jTextField1.getText()
                                       });
        jTextField1.setText("Course");
         }else{
    JOptionPane.showMessageDialog(null, "Enter Course");
    jTextField1.requestFocus();
          }
        
        }
 });
       //ADD Contact Finish
    }
    
    public void AddDepartment(){
         DefaultTableModel model = (DefaultTableModel) DeptList.getModel();
          jButton2.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
if(!jTextField2.getText().equals("Department") && !jTextField2.getText().equals("")){
              model.addRow(new Object[]{
                              jTextField2.getText()
                                       });
        jTextField2.setText("Department");
         }else{
    JOptionPane.showMessageDialog(null, "Enter Department");
    jTextField2.requestFocus();
          }
        
        }
 });
   
    }
       public void AddLevel(){
         DefaultTableModel model = (DefaultTableModel) LevelList.getModel();
          jButton3.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
if(!jTextField3.getText().equals("Level") && !jTextField3.getText().equals("")){
              model.addRow(new Object[]{
                              jTextField3.getText()
                                       });
        jTextField3.setText("Level");
         }else{
    JOptionPane.showMessageDialog(null, "Enter Level");
    jTextField3.requestFocus();
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
    
     public void CoursesList(){
          JTableHeader Theader = CourseList.getTableHeader();
        Theader.setBackground(new Color(84,127,206)); // change the Background color
        Theader.setForeground(Color.WHITE); // change the Foreground
        Theader.setFont(new Font("Segoe UI", Font.BOLD, 14)); // font name style size
        ((DefaultTableCellRenderer)Theader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER); // center header text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
       CourseList.getColumnModel().getColumn(0).setPreferredWidth(250);
       CourseList.getColumnModel().getColumn(1).setPreferredWidth(25);
        TableColumnModel tcm = Theader.getColumnModel();
         TableColumn tc0 = tcm.getColumn(0);
         TableColumn tc1 = tcm.getColumn(1); 
          tc0.setHeaderValue("Course");
          tc1.setHeaderValue("Active"); 
        CourseList.setDefaultEditor(Object.class, null);
        
    }
     
         public void DepartmentList(){
          JTableHeader Theader = DeptList.getTableHeader();
        Theader.setBackground(new Color(84,127,206)); // change the Background color
        Theader.setForeground(Color.WHITE); // change the Foreground
        Theader.setFont(new Font("Segoe UI", Font.BOLD, 14)); // font name style size
        ((DefaultTableCellRenderer)Theader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER); // center header text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
      DeptList.getColumnModel().getColumn(0).setPreferredWidth(250);
      DeptList.getColumnModel().getColumn(1).setPreferredWidth(25);
         TableColumnModel tcm = Theader.getColumnModel();
         TableColumn tc0 = tcm.getColumn(0);
         TableColumn tc2 = tcm.getColumn(1); 
          tc0.setHeaderValue("Deparment");
          tc2.setHeaderValue("Active"); 
        DeptList.setDefaultEditor(Object.class, null);
        
        
    }
    
         public void LevelList(){
          JTableHeader Theader = LevelList.getTableHeader();
        Theader.setBackground(new Color(84,127,206)); // change the Background color
        Theader.setForeground(Color.WHITE); // change the Foreground
        Theader.setFont(new Font("Segoe UI", Font.BOLD, 14)); // font name style size
        ((DefaultTableCellRenderer)Theader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER); // center header text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
      LevelList.getColumnModel().getColumn(0).setPreferredWidth(250);
      LevelList.getColumnModel().getColumn(1).setPreferredWidth(45);
      TableColumnModel tcm = Theader.getColumnModel();
         TableColumn tc0 = tcm.getColumn(0);
         TableColumn tc2 = tcm.getColumn(1); 
          tc0.setHeaderValue("Level");
          tc2.setHeaderValue("Active"); 
        LevelList.setDefaultEditor(Object.class, null);
        
    }
    
         public void  insertDept(){
             String sql = "insert into student_departments (department_title,dept_abbr,status) values (?,?,?)";
             try{
                 conn = SQLCon.ConnectDB();
                 pst = conn.prepareStatement(sql);
                 pst.setString(1, jTextField2.getText().toUpperCase());
                 pst.setString(2, jTextField4.getText().toUpperCase());
                  pst.setInt(3, 1);
                 pst.executeUpdate(); 
                 insertAudit();
                    f.DepartmentList.TableFromDatabase();
        DepartmentList();
        JOptionPane.showMessageDialog(null, "Department successfully added...");
        jTextField2.setText("Department");
        jTextField4.setText("Abbreviation");
        getDepartments();
        conn.close();
             }catch (Exception e) {
                 DC();
                 e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1029", JOptionPane.ERROR_MESSAGE);
        }
         }
         
         public void  updateDept(){
             String sql = "update student_departments SET department_title=?,dept_abbr=? where department_id='"+selectedDeptID+"'";
             try{
                 conn = SQLCon.ConnectDB();
                 pst = conn.prepareStatement(sql);
                 pst.setString(1, jTextField2.getText().toUpperCase());
                 pst.setString(2, jTextField4.getText().toUpperCase()); 
                 pst.executeUpdate(); 
                 insertUpdateDeptAudit();
                 DefaultTableModel model = (DefaultTableModel) DeptList.getModel();
            model.setRowCount(0);
                    f.DepartmentList.TableFromDatabase();
        DepartmentList(); 
       
        jTextField2.setText("Department");
        jTextField4.setText("Abbreviation");
        jButton2.setText("Add");
         selectedDeptID="";
 selectedDeptName="";
 selectedDeptAbbr=""; 
        getDepartments();
          txtInfo.setText("");
         JOptionPane.showMessageDialog(null, "Department successfully updated...");
                      UIManagers.applyOldUI();
                      conn.close();
             }catch (Exception e) {
                 DC();
                 e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1029", JOptionPane.ERROR_MESSAGE);
        }
         }
             
    public void insertAudit(){
         String sql = "insert into audit (u_information_id,audit_activity,audit_description) values (?,?,?)";
        try {
            conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, SystemInfo.userid);
            pst.setString(2, am.addDepartment);
            pst.setString(3, jTextField2.getText().toUpperCase());
            pst.executeUpdate(); 
           conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1030", JOptionPane.ERROR_MESSAGE);
        }
    }
      public void insertUpdateDeptAudit(){
         String sql = "insert into audit (u_information_id,audit_activity,audit_description) values (?,?,?)";
        try {
            conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, SystemInfo.userid);
            pst.setString(2, am.updateDepartment);
            pst.setString(3, jTextField2.getText().toUpperCase());
            pst.executeUpdate(); 
           conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1030", JOptionPane.ERROR_MESSAGE);
        }
    }
      public void  insertCourse(){
             String sql = "INSERT INTO `student_courses`( `department_id`, `s_course_title`,`status`) VALUES ((SELECT department_id from student_departments where department_title='"+jComboBox2.getSelectedItem().toString().replace("'", "''")+"'),?,?)";
             try{
                 conn = SQLCon.ConnectDB();
                 pst = conn.prepareStatement(sql);
                 pst.setString(1, jTextField1.getText().toUpperCase());
                  pst.setInt(2, 1);
                 pst.executeUpdate(); 
                 insertAddCourseAudit();
                    f.CoursesList.TableFromDatabase((String) jComboBox2.getSelectedItem().toString().replace("'", "''"));
        CoursesList();
        JOptionPane.showMessageDialog(null, "Course successfully added...");
        jTextField1.setText("Course");
      conn.close();
             }catch (Exception e) {
                 DC();
                 e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1029", JOptionPane.ERROR_MESSAGE);
        }
         }
    public void  updateCourse(){
             String sql = "UPDATE `student_courses` SET  `s_course_title`=? WHERE s_course_id='"+selectedCourseID+"'";
             try{
                 conn = SQLCon.ConnectDB();
                 pst = conn.prepareStatement(sql);
                 pst.setString(1, jTextField1.getText().toUpperCase()); 
                 pst.executeUpdate(); 
                 insertUpdateCourseAudit();
                  DefaultTableModel model = (DefaultTableModel) CourseList.getModel();
            model.setRowCount(0);
                    f.CoursesList.TableFromDatabase((String) jComboBox2.getSelectedItem().toString().replace("'", "''"));
        CoursesList(); 
        //jTextField1.setText("Course"); 
                      txtInfo.setText("");
                      jButton1.setText("Add");
                      jTextField1.setText("Course");
                      
 selectedCourseID="";
 selectedCourseDept="";
 selectedCourse="";
                      
                      
                      
       JOptionPane.showMessageDialog(null, "Course successfully updated...");
       conn.close();
             }catch (Exception e) {
                 DC();
                 e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1029", JOptionPane.ERROR_MESSAGE);
        }
         }
  
      
        public void insertAddCourseAudit(){
         String sql = "insert into audit (u_information_id,audit_activity,audit_description) values (?,?,?)";
        try {
            conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, SystemInfo.userid);
            pst.setString(2, am.addCourse);
            pst.setString(3, jTextField1.getText().toUpperCase()+" on " + jComboBox2.getSelectedItem());
            pst.executeUpdate(); 
           conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1033", JOptionPane.ERROR_MESSAGE);
        }
    }
    
        
        public void insertUpdateCourseAudit(){
         String sql = "insert into audit (u_information_id,audit_activity,audit_description) values (?,?,?)";
        try {
            conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, SystemInfo.userid);
            pst.setString(2, am.updateCourse);
            pst.setString(3, jTextField1.getText().toUpperCase()+" on " + jComboBox2.getSelectedItem());
            pst.executeUpdate(); 
           conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1033", JOptionPane.ERROR_MESSAGE);
        }
    }
    
        
         public void  insertLevel(){
             String sql = "insert into student_levels (s_level_title,status) values (?,?)";
             try{
                 conn = SQLCon.ConnectDB();
                 pst = conn.prepareStatement(sql);
                 pst.setString(1, jTextField3.getText().toUpperCase());
                  pst.setInt(2, 1);
                 pst.executeUpdate(); 
                 insertAddLevelAudit();
                    f.LevelList.TableFromDatabase();
        LevelList();
        JOptionPane.showMessageDialog(null, "Level successfully added...");
        jTextField3.setText("Level"); 
        txtInfo.setText("");
        conn.close();
             }catch (Exception e) {
                 DC();
                 e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1036", JOptionPane.ERROR_MESSAGE);
        }
         }
         
            public void  updateLevel(){
             String sql = "UPDATE student_levels SET s_level_title=? WHERE s_level_id='"+selectedLevelID+"'";
             try{
                 conn = SQLCon.ConnectDB();
                 pst = conn.prepareStatement(sql);
                 pst.setString(1, jTextField3.getText().toUpperCase()); 
                 pst.executeUpdate(); 
                 insertUpdateLevelAudit();
                   DefaultTableModel model = (DefaultTableModel) LevelList.getModel();
            model.setRowCount(0);
                    f.LevelList.TableFromDatabase();
        LevelList();
        jTextField3.setText("Level"); 
        txtInfo.setText(""); 
 selectedLevelID="";
 selectedLevel=""; 
 jButton3.setText("Add");
        JOptionPane.showMessageDialog(null, "Level successfully updated...");
        conn.close();
             }catch (Exception e) {
                 DC();
                 e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1036", JOptionPane.ERROR_MESSAGE);
        }
         }
         
             
    public void insertAddLevelAudit(){
         String sql = "insert into audit (u_information_id,audit_activity,audit_description) values (?,?,?)";
        try {
            conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, SystemInfo.userid);
            pst.setString(2, am.addLevel);
            pst.setString(3, jTextField3.getText().toUpperCase());
            pst.executeUpdate(); 
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1030", JOptionPane.ERROR_MESSAGE);
        }
    }
     public void insertUpdateLevelAudit(){
         String sql = "insert into audit (u_information_id,audit_activity,audit_description) values (?,?,?)";
        try {
            conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, SystemInfo.userid);
            pst.setString(2, am.updateLevel);
            pst.setString(3, jTextField3.getText().toUpperCase());
            pst.executeUpdate();  
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1030", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void AddDept(){
        UIManagers.getNewUI();
        if (jTextField2.equals("") || jTextField2.getText().equals("Department")) {
            txtInfo.setText("Enter department name");
           // JOptionPane.showMessageDialog(null, "Enter department name");
            jTextField2.requestFocus();
        } else if (jTextField2.getText().contains("'")) {
            txtInfo.setText("Invalid department name");
            //JOptionPane.showMessageDialog(null, "Invalid department name");
            jTextField2.requestFocus();
        } else if (jTextField4.equals("") || jTextField4.getText().equals("Abbreviation")) {
            txtInfo.setText("Enter Abbreviation (Acronym)");
           // JOptionPane.showMessageDialog(null, "Enter Abbreviation (Acronym)");
            jTextField4.requestFocus();
        } else if (jTextField4.getText().contains("'")) {
            txtInfo.setText("Invalid Abbreviation (Acronym)");
            //JOptionPane.showMessageDialog(null, "Invalid Abbreviation (Acronym)");
            jTextField4.requestFocus();
        } else {
            String dept = jTextField2.getText().toUpperCase();
            String abbr = jTextField4.getText().toUpperCase();
            if(jButton2.getText().equals("Update")){
                if(jTextField2.getText().equals(selectedDeptName)){
                   if(jTextField4.getText().equals(selectedDeptAbbr)){
                        insertUpdateDeptAudit();
                 DefaultTableModel model = (DefaultTableModel) DeptList.getModel();
            model.setRowCount(0);
                        f.DepartmentList.TableFromDatabase();
        DepartmentList();  
        jTextField2.setText("Department");
        jTextField4.setText("Abbreviation");
        jButton2.setText("Add");
         selectedDeptID="";
 selectedDeptName="";
 selectedDeptAbbr="";  txtInfo.setText("");
                       JOptionPane.showMessageDialog(null, "Department successfully updated...");  
                      UIManagers.applyOldUI();
                   }else{
                         try{
                             conn = SQLCon.ConnectDB();
                        String sql = "select dept_abbr from student_departments where dept_abbr=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, abbr);
                rs = pst.executeQuery();
                if (rs.next()) {
                     txtInfo.setText("Abbreviation already exist...");
                    //JOptionPane.showMessageDialog(null, "Abbreviation already exist...");
                } else {
                    //  txtInfo.setText("UPDATE DEPT...");
                     updateDept();
                }
                conn.close();
                    }catch(Exception e){
                        DC();
                        e.printStackTrace();
                         //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1028", JOptionPane.ERROR_MESSAGE);
       
                    }
                   }
                }else{
                     try {
                         conn = SQLCon.ConnectDB();
                String sql = "select department_title from student_departments where department_title=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, dept);
                rs = pst.executeQuery();
                if (rs.next()) {
            txtInfo.setText("Department already exist...");
                    //JOptionPane.showMessageDialog(null, "Department already exist...");
                } else {
                     
                           if(jTextField4.getText().equals(selectedDeptAbbr)){
                    updateDept();
                   }else{
                         try{
                             conn = SQLCon.ConnectDB();
                         sql = "select dept_abbr from student_departments where dept_abbr=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, abbr);
                rs = pst.executeQuery();
                if (rs.next()) {
                     txtInfo.setText("Abbreviation already exist...");
                    //JOptionPane.showMessageDialog(null, "Abbreviation already exist...");
                } else {
                    //  txtInfo.setText("UPDATE DEPT...");
                     updateDept();
                }
                conn.close();
                         }catch(Exception e){
                             DC();
                          //JOptionPane.showMessageDialog(null, e);  
			e.printStackTrace(); 
                         }
                    
                           }
                     
                }
                conn.close();
            } catch (Exception e) {
                DC();
                e.printStackTrace();
                
                //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1028", JOptionPane.ERROR_MESSAGE);
            }
                } 
            }else{
                ///ADDD DEPT
            try {
                conn = SQLCon.ConnectDB();
                String sql = "select department_title from student_departments where department_title=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, dept);
                rs = pst.executeQuery();
                if (rs.next()) {
            txtInfo.setText("Department already exist...");
                    //JOptionPane.showMessageDialog(null, "Department already exist...");
                } else { 
                sql = "select dept_abbr from student_departments where dept_abbr=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, abbr);
                rs = pst.executeQuery();
                if (rs.next()) {
                     txtInfo.setText("Abbreviation already exist...");
                    //JOptionPane.showMessageDialog(null, "Abbreviation already exist...");
                } else {
                     insertDept();
                }
                    
                   
                }
                conn.close();
            } catch (Exception e) {
                DC();
                e.printStackTrace();
                //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1028", JOptionPane.ERROR_MESSAGE);
            }
            }
        }//end else
        
        
        
      //  filter
//convert to uppercase
     //   checkDuplicate();
     //  insert();
     //    call table();
     //    callcombobox course();
   // clear add dept text
   //add checkbox function
        UIManagers.applyOldUI();
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
        pnlStudentPersonalInfo1 = new javax.swing.JPanel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        CourseList = new javax.swing.JTable();
        pnlStudentPersonalInfo2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        DeptList = new javax.swing.JTable();
        jTextField4 = new javax.swing.JTextField();
        pnlStudentPersonalInfo4 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        LevelList = new javax.swing.JTable();
        txtInfo = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        pnlStudentPersonalInfo1.setBackground(new java.awt.Color(255, 255, 255));
        pnlStudentPersonalInfo1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Course List", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N
        pnlStudentPersonalInfo1.setPreferredSize(new java.awt.Dimension(362, 500));

        jComboBox2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jComboBox2.setForeground(new java.awt.Color(102, 102, 102));
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Department" }));
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
        jTextField1.setText("Course");
        jTextField1.setToolTipText("Course");
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

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        CourseList.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        CourseList.setForeground(new java.awt.Color(102, 102, 102));
        CourseList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Course", "Active"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        CourseList.setGridColor(new java.awt.Color(255, 255, 255));
        CourseList.setRowHeight(25);
        CourseList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CourseListMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(CourseList);

        javax.swing.GroupLayout pnlStudentPersonalInfo1Layout = new javax.swing.GroupLayout(pnlStudentPersonalInfo1);
        pnlStudentPersonalInfo1.setLayout(pnlStudentPersonalInfo1Layout);
        pnlStudentPersonalInfo1Layout.setHorizontalGroup(
            pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfo1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(pnlStudentPersonalInfo1Layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlStudentPersonalInfo1Layout.setVerticalGroup(
            pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfo1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlStudentPersonalInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlStudentPersonalInfo2.setBackground(new java.awt.Color(255, 255, 255));
        pnlStudentPersonalInfo2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Department List", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N
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
        jTextField2.setText("Department");
        jTextField2.setToolTipText("Department");
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
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField2KeyTyped(evt);
            }
        });

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        DeptList.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        DeptList.setForeground(new java.awt.Color(102, 102, 102));
        DeptList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Department", "Active"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        DeptList.setGridColor(new java.awt.Color(255, 255, 255));
        DeptList.setRowHeight(25);
        DeptList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                DeptListMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(DeptList);

        jTextField4.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jTextField4.setForeground(new java.awt.Color(102, 102, 102));
        jTextField4.setText("Abbreviation");
        jTextField4.setToolTipText("Abbreviation");
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
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout pnlStudentPersonalInfo2Layout = new javax.swing.GroupLayout(pnlStudentPersonalInfo2);
        pnlStudentPersonalInfo2.setLayout(pnlStudentPersonalInfo2Layout);
        pnlStudentPersonalInfo2Layout.setHorizontalGroup(
            pnlStudentPersonalInfo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfo2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlStudentPersonalInfo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(pnlStudentPersonalInfo2Layout.createSequentialGroup()
                        .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField2))
                .addContainerGap())
        );
        pnlStudentPersonalInfo2Layout.setVerticalGroup(
            pnlStudentPersonalInfo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfo2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlStudentPersonalInfo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlStudentPersonalInfo4.setBackground(new java.awt.Color(255, 255, 255));
        pnlStudentPersonalInfo4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Levels", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N
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
        jTextField3.setText("Level");
        jTextField3.setToolTipText("Level");
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

        LevelList.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        LevelList.setForeground(new java.awt.Color(102, 102, 102));
        LevelList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Level", "Active"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        LevelList.setGridColor(new java.awt.Color(255, 255, 255));
        LevelList.setRowHeight(25);
        LevelList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                LevelListMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(LevelList);

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
                        .addComponent(pnlStudentPersonalInfo2, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlStudentPersonalInfo1, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlStudentPersonalInfo4, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pnlStudentPersonalInfo4, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                    .addComponent(pnlStudentPersonalInfo1, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                    .addComponent(pnlStudentPersonalInfo2, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(82, Short.MAX_VALUE))
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
        if(jTextField2.getText().equals("Department")){
            jTextField2.setText("");
        }
    }//GEN-LAST:event_jTextField2MouseClicked

    private void jTextField2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2FocusLost
        if(jTextField2.getText().equals("")){
            jTextField2.setText("Department");
        }              // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2FocusLost

    private void jTextField2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2FocusGained
        if(jTextField2.getText().equals("Department")){
            jTextField2.setText("");
        }
    }//GEN-LAST:event_jTextField2FocusGained

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

f.ClassDisconnected.flag = false;
        jTextField2.setText(jTextField2.getText().replace("\\","").replaceAll("'", ""));
        AddDept();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
  if(evt.getKeyCode()==10){
     UIManagers.getNewUI();
     UIManagers.getNewUI();
if(jComboBox2.getSelectedItem().equals("Select Department")){
    txtInfo.setText("Select department...");
     //  JOptionPane.showMessageDialog(null, "Select department...");
    jComboBox2.requestFocus();
}else if(jTextField1.getText().equals("") || jTextField1.getText().equals("Course")){
    txtInfo.setText("Enter course name...");
      //      JOptionPane.showMessageDialog(null, "Enter course name...");
    jTextField1.requestFocus(); 
}else if(jTextField1.getText().contains("'") || jTextField1.getText().contains("\\")){ 
    txtInfo.setText("Invalid course name...");
           // JOptionPane.showMessageDialog(null, "Invalid course name");
    jTextField1.requestFocus();
}else{
           String course = jTextField1.getText().toUpperCase();
             if(jButton1.getText().equals("Update")){
                 if(jTextField1.getText().equals(selectedCourse)){
                     updateCourse();
                 }else{
                  try{
                      conn = SQLCon.ConnectDB();
                String sql = "select * from student_courses where s_course_title=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, course);
                rs = pst.executeQuery();
                if(rs.next()){
    txtInfo.setText("Course already exist...");
                 //   JOptionPane.showMessageDialog(null, "Course already exist...");
                }else{
                    updateCourse();
                }
                conn.close();
            }catch (Exception e) {
                DC();
                e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1032", JOptionPane.ERROR_MESSAGE);
        }    
                 }
             }else{
            try{
                conn = SQLCon.ConnectDB();
                String sql = "select * from student_courses where s_course_title=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, course);
                rs = pst.executeQuery();
                if(rs.next()){
    txtInfo.setText("Course already exist...");
                 //   JOptionPane.showMessageDialog(null, "Course already exist...");
                }else{
                    insertCourse();
                }
                conn.close();
            }catch (Exception e) {
                DC();
                e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1032", JOptionPane.ERROR_MESSAGE);
        }
        }
}
        UIManagers.applyOldUI();

   }    
     

    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTextField1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseClicked
        if(jTextField1.getText().equals("Course")){
            jTextField1.setText("");
        }           // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1MouseClicked

    private void jTextField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusLost
        if(jTextField1.getText().equals("")){
            jTextField1.setText("Course");
        }           // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1FocusLost

    private void jTextField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusGained
        if(jTextField1.getText().equals("Course")){
            jTextField1.setText("");
        }
    }//GEN-LAST:event_jTextField1FocusGained

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

f.ClassDisconnected.flag = false;
     UIManagers.getNewUI();
     
     jTextField1.setText(jTextField1.getText().replace("\\","").replaceAll("'", ""));
if(jComboBox2.getSelectedItem().equals("Select Department")){
    txtInfo.setText("Select department...");
     //  JOptionPane.showMessageDialog(null, "Select department...");
    jComboBox2.requestFocus();
}else if(jTextField1.getText().equals("") || jTextField1.getText().equals("Course")){
    txtInfo.setText("Enter course name...");
      //      JOptionPane.showMessageDialog(null, "Enter course name...");
    jTextField1.requestFocus(); 
}else if(jTextField1.getText().contains("'") || jTextField1.getText().contains("\\")){ 
    txtInfo.setText("Invalid course name...");
           // JOptionPane.showMessageDialog(null, "Invalid course name");
    jTextField1.requestFocus();
}else{
           String course = jTextField1.getText().toUpperCase();
             if(jButton1.getText().equals("Update")){
                 if(jTextField1.getText().equals(selectedCourse)){
                     updateCourse();
                 }else{
                  try{
                      conn = SQLCon.ConnectDB();
                String sql = "select * from student_courses where s_course_title=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, course);
                rs = pst.executeQuery();
                if(rs.next()){
    txtInfo.setText("Course already exist...");
                 //   JOptionPane.showMessageDialog(null, "Course already exist...");
                }else{
                    updateCourse();
                }
                conn.close();
            }catch (Exception e) {
                DC();
                e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1032", JOptionPane.ERROR_MESSAGE);
        }    
                 }
             }else{
            try{
                conn = SQLCon.ConnectDB();
                String sql = "select * from student_courses where s_course_title=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, course);
                rs = pst.executeQuery();
                if(rs.next()){
    txtInfo.setText("Course already exist...");
                 //   JOptionPane.showMessageDialog(null, "Course already exist...");
                }else{
                    insertCourse();
                }
                conn.close();
            }catch (Exception e) {
                DC();
                e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1032", JOptionPane.ERROR_MESSAGE);
        }
        }
}
        UIManagers.applyOldUI();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
        jTextField1.setText("Course");
        jTextField1.requestFocus(); 
         jButton1.setText("Add");
 selectedCourseID="";
 selectedCourseDept="";
 selectedCourse="";
             f.CoursesList.TableFromDatabase((String) jComboBox2.getSelectedItem());
               CoursesList(); 
    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

f.ClassDisconnected.flag = false;
        UIManagers.getNewUI();
  jTextField3.setText(jTextField3.getText().replace("\\","").replaceAll("'", ""));
        if(jTextField3.getText().equals("") || jTextField3.getText().equals("Level")){
            txtInfo.setText("Enter level name...");
            //JOptionPane.showMessageDialog(null, "Enter level name");
    jTextField3.requestFocus();
}else if(jTextField1.getText().contains("'") || jTextField1.getText().contains("\\")){ 
     txtInfo.setText("Invalid level name...");
         //   JOptionPane.showMessageDialog(null, "Invalid level name");
    jTextField3.requestFocus();
}else{
            String level = jTextField3.getText().toUpperCase();
             if(jButton3.getText().equals("Update")){
            if(level.equals(selectedLevel)){
                updateLevel();
            }else{
                 try{
                     conn = SQLCon.ConnectDB();
                String sql = "select * from student_levels where s_level_title=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, level);
                rs = pst.executeQuery();
                if(rs.next()){ 
     txtInfo.setText("Level already exist...");
                  //  JOptionPane.showMessageDialog(null, "Level already exist...");
                }else{
                    updateLevel();
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
                String sql = "select * from student_levels where s_level_title=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, level);
                rs = pst.executeQuery();
                if(rs.next()){ 
     txtInfo.setText("Level already exist...");
                  //  JOptionPane.showMessageDialog(null, "Level already exist...");
                }else{
                    insertLevel();
                }
                conn.close();
            }catch (Exception e) {
                DC();
                e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1035", JOptionPane.ERROR_MESSAGE);
        }
        }//end else 
}
        UIManagers.applyOldUI();   
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField3FocusGained
 if(jTextField3.getText().equals("Level")){
            jTextField3.setText("");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3FocusGained

    private void jTextField3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField3FocusLost
   if(jTextField3.getText().equals("")){
            jTextField3.setText("Level");
        }              // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3FocusLost

    private void jTextField3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField3MouseClicked
 if(jTextField3.getText().equals("Level")){
            jTextField3.setText("");
        }              // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3MouseClicked

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void DeptListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DeptListMouseReleased

f.ClassDisconnected.flag = false;
        DeptListClick = true;
         int row =  DeptList.getSelectedRow(); 
        String dept = DeptList.getModel().getValueAt(row, 0).toString().replace("'", "''");
      
         Boolean checked = (Boolean) DeptList.getValueAt(row, 1);
         String deptStatus = "0";
                if (checked) {
                    deptStatus = "1"; 
                } else {
                    deptStatus = "0"; 
                }
       try{
           conn = SQLCon.ConnectDB();
           String sql = "update student_departments set status=? where department_title='"+dept+"'";
           pst = conn.prepareStatement(sql);
           pst.setString(1, deptStatus);
           pst.executeUpdate();
           getDepartments();
           System.out.println("Dept Status Updated");
           conn.close();
       }catch (Exception e) {
           DC();
           e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1031", JOptionPane.ERROR_MESSAGE);
        }
          try{
              conn = SQLCon.ConnectDB();
           String sql = "SELECT   `department_id`,`department_title`, `dept_abbr`, `status` FROM `student_departments` WHERE `department_title`='"+dept+"'";
           pst = conn.prepareStatement(sql);
          rs = pst.executeQuery();
            while(rs.next()){
                selectedDeptID = rs.getString(1);
                System.out.println("Selected Dept ID: "+selectedDeptID);
                jTextField2.setText(rs.getString(2));
               selectedDeptName =  rs.getString(2);
                jTextField4.setText(rs.getString(3)); 
                selectedDeptAbbr = rs.getString(3);
                jButton2.setText("Update");
            }
            conn.close();
       }catch (Exception e) {
           DC();
           e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1031", JOptionPane.ERROR_MESSAGE);
        }
           
         jButton1.setText("Add");
 selectedCourseID="";
 selectedCourseDept="";
 selectedCourse="";
        /*     

            */ 
 
          
    }//GEN-LAST:event_DeptListMouseReleased

    private void CourseListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CourseListMouseReleased

f.ClassDisconnected.flag = false;
        int row =  CourseList.getSelectedRow(); 
        String course = CourseList.getModel().getValueAt(row, 0).toString().replace("'", "''"); 
         Boolean checked = (Boolean) CourseList.getValueAt(row, 1);
         String courseStatus = "0";
                if (checked) {
                    courseStatus = "1"; 
                } else {
                    courseStatus = "0"; 
                }
       try{
           conn = SQLCon.ConnectDB();
           String sql = "update student_courses set status=? where s_course_title='"+course+"'";
           pst = conn.prepareStatement(sql);
           pst.setString(1, courseStatus);
           pst.executeUpdate(); 
           System.out.println("Course Status Updated");
           conn.close();
       }catch (Exception e) {
           DC();
           e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1031", JOptionPane.ERROR_MESSAGE);
        }   
       

 try{
     conn = SQLCon.ConnectDB();
           String sql = "SELECT   `s_course_id`,`department_id`, `s_course_title` FROM `student_courses` WHERE `s_course_title`='"+course+"'";
           pst = conn.prepareStatement(sql);
          rs = pst.executeQuery();
            while(rs.next()){ 
                selectedCourseID = rs.getString(1); 
               // jTextField2.setText(rs.getString(2));
               selectedCourseDept =  rs.getString(2);
                jTextField1.setText(rs.getString(3)); 
                selectedCourse = rs.getString(3);
                jButton1.setText("Update");
            }
            conn.close();
       }catch (Exception e) {
           DC();
           e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1031", JOptionPane.ERROR_MESSAGE);
        }
         



// TODO add your handling code here:
    }//GEN-LAST:event_CourseListMouseReleased

    private void LevelListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LevelListMouseReleased

f.ClassDisconnected.flag = false;
        int row =  LevelList.getSelectedRow(); 
        String level = LevelList.getModel().getValueAt(row, 0).toString();
      
         Boolean checked = (Boolean) LevelList.getValueAt(row, 1);
         String levelStatus = "0";
                if (checked) {
                    levelStatus = "1"; 
                } else {
                    levelStatus = "0"; 
                }
       try{
           conn = SQLCon.ConnectDB();
           String sql = "update student_levels set status=? where s_level_title='"+level+"'";
           pst = conn.prepareStatement(sql);
           pst.setString(1, levelStatus);
           pst.executeUpdate(); 
           System.out.println("Level Status Updated");
           conn.close();
       }catch (Exception e) {
           DC();
           e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1036", JOptionPane.ERROR_MESSAGE);
        }    
       
       
 try{
     conn = SQLCon.ConnectDB();
           String sql = "SELECT `s_level_id`,`s_level_title` FROM `student_levels` WHERE `s_level_title`='"+level+"'";
           pst = conn.prepareStatement(sql);
          rs = pst.executeQuery();
            while(rs.next()){ 
                selectedLevelID = rs.getString(1);  
               selectedLevel =  rs.getString(2);
               jTextField3.setText(selectedLevel); 
                jButton3.setText("Update");
            }
            conn.close();
       }catch (Exception e) {
           DC();
           e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1031", JOptionPane.ERROR_MESSAGE);
        }
       
       
       
       
       
       
               // TODO add your handling code here:
    }//GEN-LAST:event_LevelListMouseReleased

    private void jTextField4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField4FocusGained
  if(jTextField4.getText().equals("Abbreviation")){
            jTextField4.setText("");
        }       
    }//GEN-LAST:event_jTextField4FocusGained

    private void jTextField4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField4FocusLost
 if(jTextField4.getText().equals("")){
            jTextField4.setText("Abbreviation");
        }           // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4FocusLost

    private void jTextField4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField4MouseClicked
  if(jTextField4.getText().equals("Abbreviation")){
            jTextField4.setText("");
        }              // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4MouseClicked

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
 
        if(evt.getKeyCode()==10){
       AddDept();
   }    
    }//GEN-LAST:event_jTextField2KeyReleased

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
 
    if(evt.getKeyCode()==10){
       AddDept();
    }  
    }//GEN-LAST:event_jTextField4KeyReleased

    private void jTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2KeyTyped

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
                                            
  if(evt.getKeyCode()==10){
      UIManagers.getNewUI();
        if(jTextField3.getText().equals("") || jTextField3.getText().equals("Level")){
            txtInfo.setText("Enter level name...");
            //JOptionPane.showMessageDialog(null, "Enter level name");
    jTextField3.requestFocus();
}else if(jTextField3.getText().contains("'")){ 
     txtInfo.setText("Invalid level name...");
         //   JOptionPane.showMessageDialog(null, "Invalid level name");
    jTextField3.requestFocus();
}else{
            String level = jTextField3.getText().toUpperCase();
             if(jButton3.getText().equals("Update")){
            if(level.equals(selectedLevel)){
                updateLevel();
            }else{
                 try{
                     conn = SQLCon.ConnectDB();
                String sql = "select * from student_levels where s_level_title=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, level);
                rs = pst.executeQuery();
                if(rs.next()){ 
     txtInfo.setText("Level already exist...");
                  //  JOptionPane.showMessageDialog(null, "Level already exist...");
                }else{
                    updateLevel();
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
                String sql = "select * from student_levels where s_level_title=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, level);
                rs = pst.executeQuery();
                if(rs.next()){ 
     txtInfo.setText("Level already exist...");
                  //  JOptionPane.showMessageDialog(null, "Level already exist...");
                }else{
                    insertLevel();
                }
                conn.close();
            }catch (Exception e) {
                DC();
                e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1035", JOptionPane.ERROR_MESSAGE);
        }
        }//end else 
}
        UIManagers.applyOldUI();   

  }
    }//GEN-LAST:event_jTextField3KeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTable CourseList;
    public static javax.swing.JTable DeptList;
    public static javax.swing.JTable LevelList;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JPanel pnlStudentPersonalInfo1;
    private javax.swing.JPanel pnlStudentPersonalInfo2;
    private javax.swing.JPanel pnlStudentPersonalInfo4;
    private javax.swing.JLabel txtInfo;
    // End of variables declaration//GEN-END:variables
}
