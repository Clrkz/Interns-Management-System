/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panels;

import f.AuditMessage;
import f.SQLCon;
import f.SelectedStudent;
import f.SystemInfo;
import f.UIManagers;
import home.Home;
import static home.Home.pnlDynamic;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Frame;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.BufferedImage;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
 * @author RojeruSan
 */
public class pnlStudentLogs extends javax.swing.JPanel {
    public static Connection conn=null;
public static PreparedStatement pst =  null;
public static ResultSet rs = null; 
public static int startCompList = 0,endCompList = 20;
  public static boolean lastCompList = false;
   public static int listPage = 1;
private static HashMap<Integer,String> hm = new HashMap<Integer,String>();
public static String selectedRowDocSProfile;
boolean update;
    // static pnlStudentLogs cmp = new pnlStudentLogs(); 
     
     static pnlStudentLogs cmp;
     public static void main(String[] args) {
        try {
            // The following line would throw ExceptionInInitializerError
           cmp = new pnlStudentLogs();
        } catch (Throwable t) {
            System.out.println(t);
        }
        // The following line would cause NoClassDefFoundError
        cmp = new pnlStudentLogs();
    }

 
    /**
     * Creates new form pnlHome
     */
    public pnlStudentLogs() {
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
     Home.printstudentlist = false;
    Home.printstudentlog = true;
     Home.printstudentact = false;
     Home.printstudentrecord = false; 
	 Home.printstatistics = false;
        
        
        
        
       CompanyListFunction();
        jScrollPane2.getViewport().setBackground(Color.WHITE);
         jLabel3.setText("Page: "+listPage); 
       getCompanyList(startCompList,endCompList);
        LogTableList();
        
    }
    
       public static void CompanyListFunction() {

        jLabel2.setText("Date Registered: " + SelectedStudent.sdateadd);
        txtStudentNo.setText(SelectedStudent.sid);
        txtFname.setText(SelectedStudent.fname);
 if (SelectedStudent.mname.equals("")) {
            txtMname.setText("Middlename");
        } else {
            txtMname.setText(SelectedStudent.mname);
        }
        txtLname.setText(SelectedStudent.lname);
        if (SelectedStudent.sname.equals("")) {
            txtSuffix.setText("Suffix");
        } else {
            txtSuffix.setText(SelectedStudent.sname);
        }
 jLabel8.setText(SelectedStudent.sgender);
        
          
        
        
       
            
 
         

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
    
    
    
     public static void LogTableList(){
          JTableHeader Theader = jTable2.getTableHeader();
        Theader.setBackground(new Color(84,127,206)); // change the Background color
        Theader.setForeground(Color.WHITE); // change the Foreground
        Theader.setFont(new Font("Segoe UI", Font.BOLD, 14)); // font name style size
        ((DefaultTableCellRenderer)Theader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.LEFT); // center header text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
       jTable2.getColumnModel().getColumn(0).setPreferredWidth(5);
        jTable2.getColumnModel().getColumn(1).setPreferredWidth(400);
        jTable2.setDefaultEditor(Object.class, null);
     
        
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
        
         String sql = "SELECT sl.s_log_date_time as `Date & Time`,  \n" +
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
"where si.`s_student_no`='"+SelectedStudent.sid+"'\n" +
"order by sl.s_log_date_time DESC\n" +
"LIMIT "+start+","+end+""; 
           try{
               conn = SQLCon.ConnectDB();
              // System.out.println(sql);
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery();
            jTable2.setModel(DbUtils.resultSetToTableModel(rs));  
            LogTableList();
            conn.close();
        }catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1059", JOptionPane.ERROR_MESSAGE);
        }
     
    }
      
       public static void printCompanyList(){  
               
         String sql = "SELECT sl.s_log_date_time as `Date & Time`,  \n" +
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
"where si.`s_student_no`='"+SelectedStudent.sid+"'\n" +
"order by sl.s_log_date_time DESC ";  
           try{
               conn = SQLCon.ConnectDB();
                String recordDate = "";
                String contactNum = "";
            try{
                 byte[] fileBytes;
        String query = "SELECT s_picture,si.s_dateadded,sc.s_contact_info FROM student_information si inner join student_contact sc ON si.s_student_no=sc.s_information_id  \n" +
" where si.s_student_no='"+SelectedStudent.sid+"' LIMIt 1"; 
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
                parameters.put("Year", SelectedStudent.slevel);   
                parameters.put("Date", recordDate);   
                BufferedImage image = ImageIO.read(new FileInputStream("user.jpg"));
                parameters.put("Image",  image);
           JasperDesign jd = JRXmlLoader.load("src\\reports\\studentrecordlog.jrxml");
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
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1059", JOptionPane.ERROR_MESSAGE);
        }
     
    }
    
    
    public static void insertAudit() { 
        String sql = "insert into audit (u_information_id,audit_activity,audit_description) values (?,?,?)";
        try {
            conn = SQLCon.ConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, SystemInfo.userid);
            pst.setString(2, AuditMessage.addStudentLog);
            pst.setString(3, jTextField2.getText().toUpperCase());
            pst.executeUpdate(); 
            conn.close();
        } catch (Exception e) {
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n" + e, "Error 1065", JOptionPane.ERROR_MESSAGE);
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
        jLabel4 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        txtStudentNo = new javax.swing.JLabel();
        txtFname = new javax.swing.JLabel();
        txtMname = new javax.swing.JLabel();
        txtLname = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtSuffix = new javax.swing.JTextField();
        jSeparator12 = new javax.swing.JSeparator();
        pnlStudentPersonalInfo4 = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        pnlStudentPersonalInfo.setBackground(new java.awt.Color(255, 255, 255));
        pnlStudentPersonalInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Personal Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        lblImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Name_125px.png"))); // NOI18N
        lblImage.setToolTipText("");
        lblImage.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Hashtag_18px_3.png"))); // NOI18N

        jSeparator3.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator3.setForeground(new java.awt.Color(84, 127, 206));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/contacts_18px.png"))); // NOI18N

        jSeparator7.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator7.setForeground(new java.awt.Color(84, 127, 206));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/contacts_18px.png"))); // NOI18N

        jSeparator8.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator8.setForeground(new java.awt.Color(84, 127, 206));

        jSeparator9.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator9.setForeground(new java.awt.Color(84, 127, 206));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/contacts_18px.png"))); // NOI18N

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Gender_18px_1.png"))); // NOI18N

        jSeparator10.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator10.setForeground(new java.awt.Color(84, 127, 206));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Date Registered: 01/01/2019");

        jButton3.setBackground(new java.awt.Color(84, 127, 206));
        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("BACK TO STUDENT PROFILE");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        txtStudentNo.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtStudentNo.setForeground(new java.awt.Color(102, 102, 102));
        txtStudentNo.setText("Student no.");

        txtFname.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtFname.setForeground(new java.awt.Color(102, 102, 102));
        txtFname.setText("Firstname");

        txtMname.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtMname.setForeground(new java.awt.Color(102, 102, 102));
        txtMname.setText("Middlename");

        txtLname.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtLname.setForeground(new java.awt.Color(102, 102, 102));
        txtLname.setText("Lastname");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Gender");

        txtSuffix.setEditable(false);
        txtSuffix.setBackground(new java.awt.Color(255, 255, 255));
        txtSuffix.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtSuffix.setForeground(new java.awt.Color(102, 102, 102));
        txtSuffix.setText("Suffix");
        txtSuffix.setToolTipText("Lastname");
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
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                                .addComponent(txtFname, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)))
                        .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                .addComponent(txtStudentNo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator10)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addGap(18, 18, 18)
                .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                                .addComponent(txtStudentNo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                                .addComponent(txtFname, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                                .addComponent(txtMname, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlStudentPersonalInfoLayout.createSequentialGroup()
                        .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSuffix, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtLname, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlStudentPersonalInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(0, 0, 0)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addComponent(jButton3))
        );

        pnlStudentPersonalInfo4.setBackground(new java.awt.Color(255, 255, 255));
        pnlStudentPersonalInfo4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Logs", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N
        pnlStudentPersonalInfo4.setPreferredSize(new java.awt.Dimension(362, 500));

        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(102, 102, 102));
        jTextField2.setText("Enter Log information");
        jTextField2.setToolTipText("Log information");
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
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTextField2MouseReleased(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(84, 127, 206));
        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Add");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
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
                "Date & Time", "Log"
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

        javax.swing.GroupLayout pnlStudentPersonalInfo4Layout = new javax.swing.GroupLayout(pnlStudentPersonalInfo4);
        pnlStudentPersonalInfo4.setLayout(pnlStudentPersonalInfo4Layout);
        pnlStudentPersonalInfo4Layout.setHorizontalGroup(
            pnlStudentPersonalInfo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfo4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlStudentPersonalInfo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStudentPersonalInfo4Layout.createSequentialGroup()
                        .addComponent(jTextField2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 712, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlStudentPersonalInfo4Layout.setVerticalGroup(
            pnlStudentPersonalInfo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentPersonalInfo4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlStudentPersonalInfo4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Right_Button_35px.png"))); // NOI18N
        jLabel9.setToolTipText("Next");
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel9MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel9MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel9MouseReleased(evt);
            }
        });

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Prev_35px.png"))); // NOI18N
        jLabel14.setToolTipText("Previous");
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel14MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel14MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel14MouseReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 51, 51));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlStudentPersonalInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlStudentPersonalInfo4, javax.swing.GroupLayout.DEFAULT_SIZE, 744, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlStudentPersonalInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pnlStudentPersonalInfo4, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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

    private void jTextField2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2FocusGained
        if(jTextField2.getText().equals("Enter Log information")){
            jTextField2.setText("");
        }
    }//GEN-LAST:event_jTextField2FocusGained

    private void jTextField2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2FocusLost
        if(jTextField2.getText().equals("")){
            jTextField2.setText("Enter Log information");
        }              // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2FocusLost

    private void jTextField2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField2MouseClicked
        if(jTextField2.getText().equals("Enter Log information")){
            jTextField2.setText("");
        }
    }//GEN-LAST:event_jTextField2MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
f.ClassDisconnected.flag = false;
        if(jTextField2.getText().equals("Enter Log information")){
    UIManagers.getNewUI();
    JOptionPane.showMessageDialog(null, "Enter Log information");
    UIManagers.applyOldUI();
}else{
    try{
        conn = SQLCon.ConnectDB();
    String data = jTextField2.getText().toUpperCase();
    String sql = "INSERT INTO `student_logs`( `s_records_id`, `s_log_date_time`, `s_log_title`) VALUES ((SELECT `s_records_id` FROM `student_records` where `s_information_id`='"+SelectedStudent.sid+"' ORDER BY `s_records_id` DESC limit 1),NOW(),?)";
    pst = conn.prepareStatement(sql);
    pst.setString(1, data);
    pst.executeUpdate();
    insertAudit();
   startCompList = 0;
   endCompList = 20;
   lastCompList = false;
   listPage = 1;
     jLabel3.setText("Page: "+listPage); 
       getCompanyList(startCompList,endCompList);
        LogTableList();
        jTextField2.setText("Enter Log information");
       
    UIManagers.getNewUI();
    JOptionPane.showMessageDialog(null, "Log successfully added..."); 
    UIManagers.applyOldUI();
    conn.close();
}catch(Exception e){
    DC();
    e.printStackTrace();
}
}
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
   f.ClassDisconnected.flag = false; 
        new DynamicPanel(pnlDynamic, new panels.pnlStudentProfile());          // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtSuffixFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSuffixFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSuffixFocusGained

    private void txtSuffixFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSuffixFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSuffixFocusLost

    private void txtSuffixMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSuffixMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSuffixMouseClicked

    private void txtSuffixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSuffixActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSuffixActionPerformed

    private void jLabel9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseEntered
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Right_Button_37px_1.png")));        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel9MouseEntered

    private void jLabel9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseExited
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Right_Button_35px.png")));
    }//GEN-LAST:event_jLabel9MouseExited

    private void jLabel14MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseEntered
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Prev_37px.png")));        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel14MouseEntered

    private void jLabel14MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseExited
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Prev_35px.png")));
    }//GEN-LAST:event_jLabel14MouseExited

    private void jLabel14MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseReleased
   f.ClassDisconnected.flag = false; 
        UIManagers.getNewUI();
        if(startCompList<0 || startCompList==0 || listPage<0 || listPage==0){ 
            jLabel3.setText("Already in first page... "+listPage);
        }else{
            listPage--;
            lastCompList = false;
            startCompList-=endCompList; 
             getCompanyList(startCompList,endCompList); 
            jLabel3.setText("Page: "+listPage); 
        } 
     UIManagers.applyOldUI();         // TODO add your handling code here:
    }//GEN-LAST:event_jLabel14MouseReleased

    private void jLabel9MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseReleased
   f.ClassDisconnected.flag = false; 
        if(!lastCompList){
            startCompList+=endCompList;
            String sql = "SELECT sl.s_log_date_time as `Date & Time`,  \n" +
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
"where si.`s_student_no`='"+SelectedStudent.sid+"'\n" +
"order by sl.s_log_date_time DESC\n" +
"LIMIT "+startCompList+","+endCompList+""; 
        
            try{
                conn = SQLCon.ConnectDB();
                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();
                if(rs.next()){
                    listPage++;
                    lastCompList = false; 
                     getCompanyList(startCompList,endCompList);
            jLabel3.setText("Page: "+listPage);
                }else{
                   UIManagers.getNewUI(); 
                    lastCompList = true;
                    startCompList-=endCompList;
                    
                   // JOptionPane.showMessageDialog(null, "This is the last page...");
                   jLabel3.setText("Already in last page... "+listPage);
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
                   jLabel3.setText("Already in last page... "+listPage); 
          UIManagers.applyOldUI();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel9MouseReleased

    private void jTextField2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField2MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2MouseReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    public static javax.swing.JLabel jLabel2;
    public static javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    public static javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    public static javax.swing.JTable jTable2;
    public static javax.swing.JTextField jTextField2;
    public static javax.swing.JLabel lblImage;
    private javax.swing.JPanel pnlStudentPersonalInfo;
    private javax.swing.JPanel pnlStudentPersonalInfo4;
    public static javax.swing.JLabel txtFname;
    public static javax.swing.JLabel txtLname;
    public static javax.swing.JLabel txtMname;
    public static javax.swing.JLabel txtStudentNo;
    public static javax.swing.JTextField txtSuffix;
    // End of variables declaration//GEN-END:variables
}
