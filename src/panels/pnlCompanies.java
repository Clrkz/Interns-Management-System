/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panels;

import f.AuditMessage;
import f.ClassDisconnected;
import f.GetCompanyInfo;
import f.SQLCon;
import f.SelectedCompany;
import f.UIManagers;
import home.Home;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
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
public final class pnlCompanies extends javax.swing.JPanel {
public static Connection conn = null;
public static PreparedStatement pst =  null;
public static ResultSet rs = null;
AuditMessage am= new AuditMessage();
 public static int startCompList = 0,endCompList = 20;
  public static boolean lastCompList = false;
   public static int listPage = 1;
   boolean isDiv = false;
   boolean isSect = false;
   public static boolean  selected = false;
   //  static pnlCompanies cmp = new pnlCompanies();
   
   static pnlCompanies cmp;
     public static void main(String[] args) {
        try {
            // The following line would throw ExceptionInInitializerError
           cmp = new pnlCompanies();
        } catch (Throwable t) {
            System.out.println(t);
        }
        // The following line would cause NoClassDefFoundError
        cmp = new pnlCompanies();
    }
 

    
   boolean flagDC = false;
    /**
     * Creates new form pnlHome
     */
    public pnlCompanies() {
        initComponents(); 
         ClassDisconnected.flag = false;
        Home.searchuserlist = false;
        Home.searchcompanylist = true;
        Home.searchstudentlist = false;
        Home.searchlogs = false;
        Home.searchintern = false;
        Home.searchemployed = false;
        /////print function
        Home.printcompanylist = true;
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
     Home.printstudentrecord = false; 
	 Home.printstatistics = false;
         
         ChangeTableList();FilterCboColor();
        /////
          jScrollPane4.getViewport().setBackground(Color.WHITE);
       // DeptBox();CourseBox();YearBox();OjtHours();
         jLabel1.setText("Page: "+listPage); 
       getCompanyList(startCompList,endCompList);
          getDivision();
        getSector();
        
     //   DivisionBox();SectorBox();
     jTable4.setSelectionModel(new ForcedListSelectionModel());
          jTable4.getTableHeader().setReorderingAllowed(false); 
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
    
     public static void getDivision(){
          conn = SQLCon.ConnectDB();
        //String sql = "select c_division_title from company_division where status=1 ORDER by c_division_id DESC";
        String sql = "select c_division_title from company_division where status=1 ORDER by c_division_title ASC";
        try{
            jComboBox7.removeAllItems();
            jComboBox7.addItem("Division");
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
        public static void getSector(){
             conn = SQLCon.ConnectDB();
        //String sql = "select c_sector_title from company_sector where status=1 ORDER by c_sector_id DESC";
        String sql = "select c_sector_title from company_sector where status=1 ORDER by c_sector_title ASC";
        try{
            jComboBox8.removeAllItems();
            jComboBox8.addItem("Sector");
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery();
            while(rs.next()){
      jComboBox8.addItem(rs.getString(1));
  
            }    conn.close();
        }catch(Exception e){
          DC();
          e.printStackTrace();
        } 
   }
     public static void getCompanyCurrentIntern(){
          conn = SQLCon.ConnectDB();
         String sql = "SELECT count(*) FROM `student_records` sr\n" +
"inner join ojt_information oi\n" +
"on sr.s_ojt_information_id=oi.ojt_information_id\n" +
"WHERE s_year_level_id!=(SELECT s_level_id FROM `student_levels` where s_level_title='EMPLOYED')\n" +
"and ojt_completed_status=0 \n" +
"and c_information_id='"+SelectedCompany.compid+"'";
        try{ 
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery();
            while(rs.next()){
            jLabel26.setText(rs.getString(1));
             SelectedCompany.cIntern = rs.getString(1);
             
            }conn.close();
        }catch(Exception e){
            DC();
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
        } 
   }
    
     public static void getCompanyTotalIntern(){
         conn = SQLCon.ConnectDB();
         String sql = "SELECT count(*) FROM `student_records` sr\n" +
"inner join ojt_information oi\n" +
"on sr.s_ojt_information_id=oi.ojt_information_id\n" +
"WHERE s_year_level_id!=(SELECT s_level_id FROM `student_levels` where s_level_title='EMPLOYED')\n" +
"and c_information_id='"+SelectedCompany.compid+"'";
        try{ 
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery();
            while(rs.next()){
            jLabel27.setText(rs.getString(1));
             SelectedCompany.tIntern = rs.getString(1);
           
            }  conn.close();
        }catch(Exception e){
            DC(); 
			e.printStackTrace();
        } 
   }
       public static void getCompanyEmployed(){
            conn = SQLCon.ConnectDB();
         String sql = "SELECT count(*) FROM `student_records` sr\n" +
"inner join ojt_information oi\n" +
"on sr.s_ojt_information_id=oi.ojt_information_id\n" +
"WHERE s_year_level_id=(SELECT s_level_id FROM `student_levels` where s_level_title='EMPLOYED')\n" +
"and c_information_id='"+SelectedCompany.compid+"'";
        
        try{ 
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery();
            while(rs.next()){
            jLabel28.setText(rs.getString(1));
             SelectedCompany.eIntern = rs.getString(1);
           
            }  conn.close();
        }catch(Exception e){
            DC();
            //JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
        } 
   }
    public static void getCompanyList(int start,int end){
        conn = SQLCon.ConnectDB();
        String sdiv ="";
        String ssect = "";
        String ssearch  =  "";
        if(!home.Home.jTextField1.getText().equals("Search...")){
          ssearch = home.Home.jTextField1.getText();
       }
        if(jComboBox7.getSelectedItem().equals("Division")){
              sdiv ="";
        }else{
            sdiv = jComboBox7.getSelectedItem().toString();
        }
        if(jComboBox8.getSelectedItem().equals("Sector")){
             ssect = "";
        }else{
            ssect = (String) jComboBox8.getSelectedItem().toString();
        }
        /*
        String sqloriginal = "SELECT c_information_id as ID,c_name as Name,(SELECT c_branch from company_branch where c_information_id=ci.c_information_id) as Branch,(SELECT c_division_title from company_division where c_division_id=ci.c_division_id) as Division,(select c_sector_title from company_sector where c_sector_id=ci.c_sector_id) as Sector\n" +
"FROM company_information ci\n" +
"WHERE 1 order by c_information_id DESC limit "+start+","+end+" ";
        
        String sql = "SELECT c_information_id as ID,c_name as Name,\n" +
"(SELECT c_branch from company_branch where c_information_id=ci.c_information_id) as Branch,\n" +
"(SELECT c_division_title from company_division where c_division_id=ci.c_division_id) as Division,\n" +
"(select c_sector_title from company_sector where c_sector_id=ci.c_sector_id) as Sector\n" +
"FROM company_information ci \n" +
"inner join company_division cd\n" +
"on ci.c_division_id=cd.c_division_id\n" +
"INNER JOIN company_sector cs\n" +
"on ci.c_sector_id=cs.c_sector_id\n" +
"WHERE `c_name` LIKE '%"+ssearch+"%'\n" +  String sql = "SELECT c_information_id as ID,c_name as Name,\n" +
"(SELECT c_branch from company_branch where c_information_id=ci.c_information_id) as Branch,\n" +
"(SELECT c_division_title from company_division where c_division_id=ci.c_division_id) as Division,\n" +
"(select c_sector_title from company_sector where c_sector_id=ci.c_sector_id) as Sector\n" +
"FROM company_information ci \n" +
"inner join company_division cd\n" +
"on ci.c_division_id=cd.c_division_id\n" +
"INNER JOIN company_sector cs\n" +
"on ci.c_sector_id=cs.c_sector_id\n" +
"WHERE `c_name` LIKE '%"+ssearch+"%'\n" +
"and cs.c_sector_title LIKE '%"+ssect+"%' \n" +
"and cd.c_division_title  LIKE '%"+sdiv+"%' \n" +
"order by c_name ASC LIMIT "+start+","+end+"";
"and cs.c_sector_title LIKE '%"+ssect+"%' \n" +
"and cd.c_division_title  LIKE '%"+sdiv+"%' \n" +
"order by c_information_id DESC LIMIT "+start+","+end+"";
        */
        
        
         String sql = "SELECT c_information_id as ID,c_name as Name,\n" +
"(SELECT c_branch from company_branch where c_information_id=ci.c_information_id) as Branch,\n" +
"(SELECT c_division_title from company_division where c_division_id=ci.c_division_id) as Division,\n" +
"(select c_sector_title from company_sector where c_sector_id=ci.c_sector_id) as Sector\n" +
"FROM company_information ci \n" +
"inner join company_division cd\n" +
"on ci.c_division_id=cd.c_division_id\n" +
"INNER JOIN company_sector cs\n" +
"on ci.c_sector_id=cs.c_sector_id\n" +
"WHERE concat(`c_information_id` , `c_name`) LIKE '%"+ssearch+"%'\n" +
"and cs.c_sector_title LIKE '%"+ssect+"%' \n" +
"and cd.c_division_title  LIKE '%"+sdiv+"%' \n" +
"order by c_name ASC LIMIT "+start+","+end+"";
           try{
            pst=conn.prepareStatement(sql); 
            rs=pst.executeQuery(); 
            jTable4.setModel(DbUtils.resultSetToTableModel(rs));   
            ChangeTableList();
            conn.close();
        }catch (Exception e) { 
                DC(); 
                e.printStackTrace();
               }
     
    }
    
       public static void PrintCompanyList(){
            conn = SQLCon.ConnectDB();
           UIManagers.getNewUI();
        String sdiv ="";
        String ssect = "";
        String ssearch  =  "";
        if(!home.Home.jTextField1.getText().equals("Search...")){
          ssearch = home.Home.jTextField1.getText();
       } 
        if(jComboBox7.getSelectedItem().equals("Division")){
              sdiv ="";
        }else{
            sdiv = jComboBox7.getSelectedItem().toString();
        }
        if(jComboBox8.getSelectedItem().equals("Sector")){
             ssect = "";
        }else{ 
            ssect = (String) jComboBox8.getSelectedItem().toString();
        }  
        try{
        String sql = "SELECT c_information_id as ID,c_name as Name,\n" +
"(SELECT c_branch from company_branch where c_information_id=ci.c_information_id) as Branch,\n" +
"(SELECT c_division_title from company_division where c_division_id=ci.c_division_id) as Division,\n" +
"(select c_sector_title from company_sector where c_sector_id=ci.c_sector_id) as Sector\n" +
"FROM company_information ci \n" +
"inner join company_division cd\n" +
"on ci.c_division_id=cd.c_division_id\n" +
"INNER JOIN company_sector cs\n" +
"on ci.c_sector_id=cs.c_sector_id\n" +
"WHERE concat(`c_information_id` , `c_name`) LIKE '%"+ssearch+"%'\n" +
"and cs.c_sector_title LIKE '%"+ssect+"%' \n" +
"and cd.c_division_title  LIKE '%"+sdiv+"%'";
           JasperDesign jd = JRXmlLoader.load("src\\reports\\companylist.jrxml");
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
        }catch (Exception e) { 
            DC();
            e.printStackTrace();
                }
     
           UIManagers.applyOldUI();
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
      
 public void FilterCboColor(){
        jComboBox7.setBackground(Color.WHITE);
        jComboBox8.setBackground(Color.WHITE);
    }
    public static void ChangeTableList(){
          JTableHeader Theader = jTable4.getTableHeader();
        Theader.setBackground(new Color(84,127,206)); // change the Background color
        Theader.setForeground(Color.WHITE); // change the Foreground
        Theader.setFont(new Font("Tahome", Font.BOLD, 14)); // font name style size
        ((DefaultTableCellRenderer)Theader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER); // center header text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
       jTable4.getColumnModel().getColumn(0).setPreferredWidth(10);
       jTable4.getColumnModel().getColumn(1).setPreferredWidth(330);
        jTable4.getColumnModel().getColumn(2).setPreferredWidth(40);
        jTable4.getColumnModel().getColumn(3).setPreferredWidth(40);
        jTable4.getColumnModel().getColumn(4).setPreferredWidth(40);
        jTable4.setDefaultEditor(Object.class, null);
        
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
 
 
  public static void CompanyListFunction()  {
                 int row =  jTable4.getSelectedRow(); 
       SelectedCompany.compid = jTable4.getModel().getValueAt(row, 0).toString();
       SelectedCompany.cname = jTable4.getModel().getValueAt(row, 1).toString();
       SelectedCompany.cbranch = jTable4.getModel().getValueAt(row, 2).toString();
       SelectedCompany.cdivision = jTable4.getModel().getValueAt(row, 3).toString();
       SelectedCompany.csector = jTable4.getModel().getValueAt(row, 4).toString();
       
         GetCompanyInfo.getPofileInfo(SelectedCompany.compid);
         
             jLabel4.setText("Date Registered: "+SelectedCompany.cdateadded ); 
                  jLabel30.setText(SelectedCompany.compid );
                  jLabel22.setText(SelectedCompany.cname);
                  jLabel23.setText(SelectedCompany.cbranch);
                  jLabel24.setText(SelectedCompany.cdivision );
                  jLabel25.setText(SelectedCompany.csector);
                  jLabel32.setText(SelectedCompany.ccontact);
                  jLabel26.setText("");
                  jLabel27.setText("");
                  jLabel28.setText(""); 
                  
                  
       try{
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(SelectedCompany.img).getImage().getScaledInstance(jLabel13.getWidth(), jLabel13.getHeight(), Image.SCALE_SMOOTH));
                jLabel13.setIcon(imageIcon); 
             //   pnlCompanyProfile.lblImage.setIcon(imageIcon);
        }catch(Exception e){
            jLabel13.setIcon(new javax.swing.ImageIcon(pnlCompanies.class.getResource("/images/icons8_Web_Camera_125px.png")));
//           pnlCompanyProfile.lblImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Web_Camera_125px.png")));
            System.out.println("No Image\n"+e);
        }   
        selected = true;
            }
 
     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jButton7 = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox<>();
        jComboBox8 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane4.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jTable4.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jTable4.setForeground(new java.awt.Color(102, 102, 102));
        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Branch", "Division", "Sector"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable4.setGridColor(new java.awt.Color(255, 255, 255));
        jTable4.setRowHeight(25);
        jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable4MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTable4MouseReleased(evt);
            }
        });
        jTable4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable4KeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(jTable4);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Web_Camera_125px.png"))); // NOI18N
        jLabel13.setToolTipText("Company Logo");
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(102, 102, 102));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Name:");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(102, 102, 102));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Branch:");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(102, 102, 102));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Division:");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(102, 102, 102));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Sector:");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(102, 102, 102));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Current Intern:");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(102, 102, 102));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Total Intern:");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(102, 102, 102));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("Deployed:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Date Registered:");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(102, 102, 102));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText(" ");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(102, 102, 102));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText(" ");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(102, 102, 102));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText(" ");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(102, 102, 102));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText(" ");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(102, 102, 102));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel26.setText(" ");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(102, 102, 102));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText(" ");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(102, 102, 102));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel28.setText(" ");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(102, 102, 102));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel29.setText("ID:");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(102, 102, 102));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText(" ");

        jButton7.setBackground(new java.awt.Color(84, 127, 206));
        jButton7.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("VIEW COMPANY INFORMATION");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(102, 102, 102));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel31.setText("Contact:");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(102, 102, 102));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel32.setText(" ");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addGap(92, 92, 92))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton7))
        );

        jComboBox7.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jComboBox7.setForeground(new java.awt.Color(102, 102, 102));
        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Division" }));
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
        jComboBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox7ActionPerformed(evt);
            }
        });

        jComboBox8.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jComboBox8.setForeground(new java.awt.Color(102, 102, 102));
        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sector" }));
        jComboBox8.setBorder(null);
        jComboBox8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox8ItemStateChanged(evt);
            }
        });
        jComboBox8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jComboBox8FocusGained(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Prev_35px.png"))); // NOI18N
        jLabel2.setToolTipText("Previous");
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

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Right_Button_35px.png"))); // NOI18N
        jLabel3.setToolTipText("Next");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel3MouseExited(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 729, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox7ItemStateChanged
if(isDiv){
    listPage = 1; 
    jLabel1.setText("Page: "+listPage); 
    lastCompList = false; 
    startCompList = 0; 
        getCompanyList(startCompList,endCompList); 
}             
        
        
    }//GEN-LAST:event_jComboBox7ItemStateChanged

    private void jComboBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox7ActionPerformed

    private void jComboBox8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox8ItemStateChanged
if(isSect){
     listPage = 1; 
    jLabel1.setText("Page: "+listPage); 
    lastCompList = false; 
    startCompList = 0;
    getCompanyList(startCompList,endCompList);
} 
    }//GEN-LAST:event_jComboBox8ItemStateChanged

    private void jTable4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MouseClicked
f.ClassDisconnected.flag = false;
        int row =  jTable4.getSelectedRow(); 
       SelectedCompany.compid = jTable4.getModel().getValueAt(row, 0).toString();
  if (evt.getClickCount() == 2) {
          new DynamicPanel(Home.pnlDynamic, new panels.pnlCompanyProfile());
  }
    }//GEN-LAST:event_jTable4MouseClicked

    private void jLabel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseEntered
jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Right_Button_37px_1.png")));        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel3MouseEntered

    private void jLabel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseEntered
jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Prev_37px.png")));        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel2MouseEntered

    private void jLabel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseExited
      jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Right_Button_35px.png"))); 
    }//GEN-LAST:event_jLabel3MouseExited

    private void jLabel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseExited
 jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Prev_35px.png"))); 
    }//GEN-LAST:event_jLabel2MouseExited

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
f.ClassDisconnected.flag = false;
        UIManagers.getNewUI();
        if(startCompList<0 || startCompList==0 || listPage<1 || listPage==1){
          //  JOptionPane.showMessageDialog(null, "This is the first page...");
            jLabel1.setText("Already in first page... "+listPage);
            System.out.println(listPage);
            System.out.println(lastCompList);
            System.out.println(startCompList);
            System.out.println(endCompList);  
        }else{
            listPage--;
            lastCompList = false;
            startCompList-=endCompList;
             getCompanyList(startCompList,endCompList); 
            jLabel1.setText("Page: "+listPage); 
        }
     UIManagers.applyOldUI();
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
f.ClassDisconnected.flag = false;
        if(!lastCompList){
         conn = SQLCon.ConnectDB();
            startCompList+=endCompList; 
        String sdiv ="";
        String ssect = "";
        String ssearch  =  "";
        if(!home.Home.jTextField1.getText().equals("Search...")){
          ssearch = home.Home.jTextField1.getText();
       }
        if(jComboBox7.getSelectedItem().equals("Division")){
              sdiv ="";
        }else{
            sdiv = jComboBox7.getSelectedItem().toString();
        }
        if(jComboBox8.getSelectedItem().equals("Sector")){
             ssect = "";
        }else{
            ssect = (String) jComboBox8.getSelectedItem().toString();
        }  
         String sql = "SELECT c_information_id as ID,c_name as Name,\n" +
"(SELECT c_branch from company_branch where c_information_id=ci.c_information_id) as Branch,\n" +
"(SELECT c_division_title from company_division where c_division_id=ci.c_division_id) as Division,\n" +
"(select c_sector_title from company_sector where c_sector_id=ci.c_sector_id) as Sector\n" +
"FROM company_information ci \n" +
"inner join company_division cd\n" +
"on ci.c_division_id=cd.c_division_id\n" +
"INNER JOIN company_sector cs\n" +
"on ci.c_sector_id=cs.c_sector_id\n" +
"WHERE concat(`c_information_id` , `c_name`) LIKE '%"+ssearch+"%'\n" +
"and cs.c_sector_title LIKE '%"+ssect+"%' \n" +
"and cd.c_division_title  LIKE '%"+sdiv+"%' \n" +
"order by c_name ASC LIMIT "+startCompList+","+endCompList+"";
            try{
                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();
                if(rs.next()){
                    listPage++;
                    lastCompList = false; 
                     getCompanyList(startCompList,endCompList);
            jLabel1.setText("Page: "+listPage);
            
                }else{
                   UIManagers.getNewUI();

                    lastCompList = true;
                    startCompList-=endCompList;
                    
                   // JOptionPane.showMessageDialog(null, "This is the last page...");
                   jLabel1.setText("Already in last page... "+listPage);
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
        //    JOptionPane.showMessageDialog(null, "This is the last page....");
                   jLabel1.setText("Already in last page... "+listPage);

          UIManagers.applyOldUI();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jTable4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MouseReleased
f.ClassDisconnected.flag = false;  
        CompanyListFunction();
        getCompanyCurrentIntern();
        getCompanyTotalIntern();
        getCompanyEmployed();
 
    }//GEN-LAST:event_jTable4MouseReleased

    private void jComboBox7FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBox7FocusGained
isDiv = true;        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox7FocusGained

    private void jComboBox8FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBox8FocusGained
isSect = true;        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox8FocusGained

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
f.ClassDisconnected.flag = false;  
        if(selected){
            if(jTable4.getSelectedRow()<0){
                UIManagers.getNewUI();
                JOptionPane.showMessageDialog(null, "Select company from list");
                UIManagers.applyOldUI();
            }else{
              ///
              int row =  jTable4.getSelectedRow(); 
       SelectedCompany.compid = jTable4.getModel().getValueAt(row, 0).toString(); 
          new DynamicPanel(Home.pnlDynamic, new panels.pnlCompanyProfile());
              ///
            }
        }else{
            UIManagers.getNewUI();
            JOptionPane.showMessageDialog(null, "Select company from list");
            UIManagers.applyOldUI();

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jTable4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable4KeyReleased
f.ClassDisconnected.flag = false;
        CompanyListFunction();
        getCompanyCurrentIntern();
        getCompanyTotalIntern();
        getCompanyEmployed();        // TODO add your handling code here:
    }//GEN-LAST:event_jTable4KeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton7;
    public static javax.swing.JComboBox<String> jComboBox7;
    public static javax.swing.JComboBox<String> jComboBox8;
    public static javax.swing.JLabel jLabel1;
    public static javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    public static javax.swing.JLabel jLabel22;
    public static javax.swing.JLabel jLabel23;
    public static javax.swing.JLabel jLabel24;
    public static javax.swing.JLabel jLabel25;
    public static javax.swing.JLabel jLabel26;
    public static javax.swing.JLabel jLabel27;
    public static javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    public static javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    public static javax.swing.JLabel jLabel32;
    public static javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    public static javax.swing.JTable jTable4;
    // End of variables declaration//GEN-END:variables
}
