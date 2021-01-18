/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import f.AuditMessage;
import f.BCrypt;
import f.Zz;
import f.SQLCon;
import f.SystemInfo;
import home.Home;
import loginsignup.LoginSignupPanel;
import static loginsignup.LoginSignupPanel.lblBack;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Clrkz
 */
public class LoginPanel1 extends javax.swing.JPanel {

    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    public static boolean firstOpen = false;
    public static boolean changeAvatar = false;
    public static boolean loginform = true;
    public static UserSignup us = new UserSignup();
    private JPanel container;
    private JPanel content;
   // Home home = new Home();
    boolean wImage = false;
    String s;
    String returnID = "";
   String newPass;
   AuditMessage am = new AuditMessage();
   boolean loginsuccess,loginfailed,deactivated,nostatus;
   
   
    /**
     * Creates new form LoginPanel
     */
    public LoginPanel1() {
        initComponents();
        UserGender();
        FillFields();
        //   SignupDefaultValues();
        ContactFilter();

        
        if (firstOpen) {
            NoAdmin();
        }
 txtPass.setEchoChar((char)0);
    }

    public static void NoAdmin() {
        SignupDefaultValues();
        LoginSignupPanel.dlogin = false;
        LoginSignupPanel.dsignup = true;
        LoginSignupPanel.dsignup1 = false;
        LoginSignupPanel.dforgot = false;
        LoginSignupPanel.dforgot1 = false;
        LoginSignupPanel.dforgot2 = false;
        lblBack.setVisible(false);
        login.setVisible(false);
        signup.setVisible(true);
        signup1.setVisible(false);
        forgot.setVisible(false);
        forgot1.setVisible(false);
        forgot2.setVisible(false);
        loader.setVisible(false);
        jLabel8.setVisible(false);
        jSeparator6.setVisible(false);
        noAdminMessage();
    }

    public static void noAdminMessage() {
        new java.util.Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                LookAndFeel old = UIManager.getLookAndFeel();
                try {
                    UIManager.setLookAndFeel(
                            UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "Create admin account first...", "No Admin Detected", JOptionPane.INFORMATION_MESSAGE);
                try {
                    UIManager.setLookAndFeel(old);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, 100 * 1);

    }
    
    ///START LOGIN
    public void processLogin(){
          Runnable r = new Runnable() {
            public void run() {
                con = SQLCon.ConnectDB();
              checkPass();
            }
        };
        new Thread(r).start();
        login.setVisible(false);
        signup.setVisible(false);
        signup1.setVisible(false);
        forgot.setVisible(false);
        forgot1.setVisible(false);
        forgot2.setVisible(false);
        loader.setVisible(true);
        lblBack.setVisible(false);
    }
    
    public void checkPass(){
         LookAndFeel old = UIManager.getLookAndFeel();
                try {
                    UIManager.setLookAndFeel(
                            UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(txtUname.getText().toLowerCase().equals("guest")){
                        insertNoAccountAudit();
                }else{
         String sql = "select * from users where u_username=? ";
         try{
               pst = con.prepareStatement(sql);
               pst.setString(1, txtUname.getText());
                rs = pst.executeQuery();
                 if(rs.next()){
                     SystemInfo.userid = rs.getString("u_information_id");
                     SystemInfo.ufirstname = rs.getString("u_firstname");
                     SystemInfo.umiddlename = rs.getString("u_middlename");
                     SystemInfo.ulastname = rs.getString("u_lastname");
                     SystemInfo.usuffix = rs.getString("u_suffixname");
                     SystemInfo.gender = rs.getString("u_gender");
                     SystemInfo.ucontactnum = rs.getString("u_phone_number");
                     SystemInfo.uemail = rs.getString("u_email");
                     SystemInfo.urole = rs.getString("u_role");
                     SystemInfo.uusername = rs.getString("u_username");
                     SystemInfo.upassword = rs.getString("u_password");
                     SystemInfo.uvalidate = rs.getString("u_validated");
                     SystemInfo.ustatus = rs.getString("u_status");
                     SystemInfo.udateadded = rs.getString("u_dateadded");
                    //SystemInfo.img = rs.getBytes("u_picture");
                     boolean matched = BCrypt.checkpw(txtPass.getText(), SystemInfo.upassword );
                     if(matched && SystemInfo.uvalidate.equals("1") &&  SystemInfo.ustatus.equals("1")){
                       
                         loginsuccess = true;
                         loginfailed = false;
                         deactivated= false;
                         nostatus=false;
                    insertLoginAudit();
                         Zz s = new Zz();
                         s.login = true;
                         s.Load();
                         Window window = SwingUtilities.windowForComponent(this);
                         window.dispose();
                     }else if(SystemInfo.ustatus.equals("0")){
                           loginsuccess = false;
                         loginfailed = false;
                         deactivated= false;
                         nostatus=true;
                          insertLoginAudit();
                     }else if(SystemInfo.uvalidate.equals("0")){
                           loginsuccess = false;
                         loginfailed = false;
                         deactivated= true;
                         nostatus=false;
                          insertLoginAudit();
                     }else if(matched==false){
                         loginsuccess = false;
                         loginfailed = true;
                         deactivated= false;
                         nostatus=false;
                          insertLoginAudit();
                     }
                 }else{
                     insertNoAccountAudit();
                 }
         }catch(Exception e){
                        //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e,"Error 1010",JOptionPane.ERROR_MESSAGE);
     }
                }
          try {
                    UIManager.setLookAndFeel(old);
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }
    ///END LOGIN
    
    public void insertLoginAudit(){
          LookAndFeel old = UIManager.getLookAndFeel();
                try {
                    UIManager.setLookAndFeel(
                            UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
         String sql = "insert into audit (u_information_id,audit_activity,audit_description) values (?,?,?)";
        try {
            pst = con.prepareStatement(sql);
            /*
                        loginsuccess = false;
                         loginfailed = true;
                         deactivated= false;
                         nostatus=false;
                         */
            pst.setString(1, SystemInfo.userid);
            if(loginsuccess){
                pst.setString(2, am.loginAccountSuccess);
                pst.setString(3, SystemInfo.ufirstname+" "+SystemInfo.umiddlename+" "+SystemInfo.ulastname+" "+SystemInfo.usuffix);
              pst.executeUpdate();
            }else if(nostatus){
                 pst.setString(2, am.loginAccountFailed);
                pst.setString(3, "Deactivated Account");
                  pst.executeUpdate();
                    LoginForm();
                   JOptionPane.showMessageDialog(null, "Login Failed.\nCheck your username and password...");
            }else if(deactivated){
                  pst.setString(2, am.loginAccountFailed);
                pst.setString(3, "Account not Activated");
                  pst.executeUpdate();
                    LoginForm();
                   JOptionPane.showMessageDialog(null, "Account not Activated.\nContact your administrator...");
            }else if(loginfailed){
                  pst.setString(2, am.loginAccountFailed);
                pst.setString(3, "Wrong Password");
                  pst.executeUpdate();
                    LoginForm();
                   JOptionPane.showMessageDialog(null, "Login Failed.\nCheck your password...");
            }
             try {
                    UIManager.setLookAndFeel(old);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1004", JOptionPane.ERROR_MESSAGE);
        }
    }
    
     
     public void insertNoAccountAudit(){
        String sql = "insert into audit (u_information_id,audit_activity,audit_description) values (?,?,?)";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, 0);
            pst.setString(2, am.loginNoAccount);
            pst.setString(3, "No account with username: "+txtUname.getText());
            pst.executeUpdate();
            LoginForm();
              LookAndFeel old = UIManager.getLookAndFeel();
                try {
                    UIManager.setLookAndFeel(
                            UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            JOptionPane.showMessageDialog(null, "Login Failed.\nCheck your username and password...");
              try {
                    UIManager.setLookAndFeel(old);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1004", JOptionPane.ERROR_MESSAGE);
        }
    }

    /////START REGISTRATION
    public void processRegistration() {
        Runnable r = new Runnable() {
            public void run() {
             newPass = BCrypt.hashpw(us.password, BCrypt.gensalt(12));
                con = SQLCon.ConnectDB();
                if (Zz.noAdmin) {
                    insertPersonalInfo();
                } else {
                    checkDuplicate1();
                }
            }
        };
        new Thread(r).start();
        login.setVisible(false);
        signup.setVisible(false);
        signup1.setVisible(false);
        forgot.setVisible(false);
        forgot1.setVisible(false);
        forgot2.setVisible(false);
        loader.setVisible(true);
        lblBack.setVisible(false);
    }

    public void checkDuplicate() {
         String sql = "select * from users where u_username='"+us.username+"'";
         try{
               pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                 if(rs.next()){
                      LookAndFeel old = UIManager.getLookAndFeel();
                try {
                    UIManager.setLookAndFeel(
                            UIManager.getSystemLookAndFeelClassName());
                } catch(Exception e) {
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "Username already exist...");
                 LoginSignupPanel.dlogin = false;
            LoginSignupPanel.dsignup = false;
            LoginSignupPanel.dsignup1 = true;
            LoginSignupPanel.dforgot = false;
            LoginSignupPanel.dforgot1 = false;
            LoginSignupPanel.dforgot2 = false;
            lblBack.setVisible(true);
            lblBack.setVisible(true);
            login.setVisible(false);
            signup.setVisible(false);
            signup1.setVisible(true);

            forgot.setVisible(false);
            forgot1.setVisible(false);
            forgot2.setVisible(false);
            loader.setVisible(false);
                  try {
            UIManager.setLookAndFeel(old);
         }  catch(Exception e) {
                    e.printStackTrace();
         }
                 }else{
                      insertPersonalInfo();
                 }
         }catch(Exception e){
                        //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator","Error 1006",JOptionPane.ERROR_MESSAGE);
     }
           
       
    }

    
       public void checkDuplicate1() {
            String sql = "";
            if(!us.middlename.equals("Middlename") && !us.suffix.equals("Suffix")){
                  sql = "select * from users where u_firstname='"+us.firstname+"' and u_middlename='"+us.middlename+"' and u_lastname='"+us.lastname+"' and u_suffixname='"+us.suffix+"'";
            }else if(!us.middlename.equals("Middlename")){
                 sql = "select * from users where u_firstname='"+us.firstname+"' and u_middlename='"+us.middlename+"' and u_lastname='"+us.lastname+"'  and u_suffixname=''";
            }else if(!us.suffix.equals("Suffix")){
                 sql = "select * from users where u_firstname='"+us.firstname+"' and u_middlename='' and u_lastname='"+us.lastname+"' and u_suffixname='"+us.suffix+"'";
       }else{
                checkDuplicate();
            }
            if(!sql.equals("")){
         try{
               pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                 if(rs.next()){
                      LookAndFeel old = UIManager.getLookAndFeel();
                try {
                    UIManager.setLookAndFeel(
                            UIManager.getSystemLookAndFeelClassName());
                } catch(Exception e) {
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "User already registered...");
               LoginSignupPanel.dlogin = false;
        LoginSignupPanel.dsignup = true;
        LoginSignupPanel.dsignup1 = false;
        LoginSignupPanel.dforgot = false;
        LoginSignupPanel.dforgot1 = false;
        LoginSignupPanel.dforgot2 = false;
        lblBack.setVisible(true);
        login.setVisible(false);
        signup.setVisible(true);
        signup1.setVisible(false);
        forgot.setVisible(false);
        forgot1.setVisible(false);
        forgot2.setVisible(false);
        loader.setVisible(false);
                  try {
            UIManager.setLookAndFeel(old);
         }  catch(Exception e) {
                    e.printStackTrace();
         }
                 }else{
                      checkDuplicate();
                 }
         }catch(Exception e){
                        //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e,"Error 1007",JOptionPane.ERROR_MESSAGE);
     }
            }
       
    }

       
       
    public void insertPersonalInfo() {
         String sql = "INSERT INTO users (u_firstname,u_middlename,u_lastname,u_suffixname,u_gender,u_phone_number,u_email,u_role,u_username,u_password,u_validated,u_status,u_picture,u_dateadded) values (?,?,?,?,?,?,?,?,?,?,?,?,?,now())";
                        try{
                        pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                        pst.setString(1, us.firstname);
                        if(us.middlename.equals("Middlename")){
                            pst.setString(2, "");
                        }else{
                             pst.setString(2, us.middlename);
                        }
                        pst.setString(3, us.lastname);
                         if(us.suffix.equals("Suffix")){
                            pst.setString(4, "");
                        }else{
                        pst.setString(4, us.suffix);
                        }
                      
                        if(us.gender.equals("Male")){
                              pst.setInt(5, 0);
                        }else{
                              pst.setInt(5, 1);
                        }
                      
                        pst.setString(6, us.contactNumber);
                        
                          if(us.email.equals("Email")){
                            pst.setString(7, "");
                        }else{
                        pst.setString(7, us.email);
                        }
                      
                        pst.setString(9, us.username);
                        pst.setString(10, newPass);
                         if(Zz.noAdmin){
                        pst.setInt(8, 1); //is admin
                        pst.setInt(11, 1); //is validated
                        pst.setInt(12, 1);  //status
                         }else{
                        pst.setInt(8, 0);
                        pst.setInt(11, 0);
                        pst.setInt(12, 1); 
                         }
                          if(changeAvatar){
                             InputStream is = new FileInputStream(new File(us.image));
            pst.setBlob(13, is);
            }else{
            pst.setString(13, null);
            }
                       
                        pst.executeUpdate();
                        rs = pst.getGeneratedKeys();
                        rs.next();
                        long pk = rs.getLong(1);
                        returnID = String.valueOf(pk);
                       insertAudit();
                    }catch(Exception e){
                        //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e,"Error 1003",JOptionPane.ERROR_MESSAGE);
     }
    }

    
    public void insertAudit(){
        String sql = "insert into audit (u_information_id,audit_activity,audit_description) values (?,?,?)";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, returnID);
            pst.setString(2, am.createAccount);
             if(!us.middlename.equals("Middlename") && !us.suffix.equals("Suffix")){
                 pst.setString(3, us.firstname + " " + us.middlename + " " + us.lastname+" "+us.suffix);
            }else if(!us.middlename.equals("Middlename")){
                   pst.setString(3, us.firstname + " " + us.middlename + " " + us.lastname);
            }else if(!us.suffix.equals("Suffix")){
                pst.setString(3, us.firstname + " " + us.lastname+" "+us.suffix);
            }else{
                pst.setString(3, us.firstname + " " + us.lastname);
            }
           
            pst.executeUpdate();
           
            if(Zz.noAdmin){
                checkExistingSystemID();
            }else{
            finishMessage();
            }
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1004", JOptionPane.ERROR_MESSAGE);
        }
    }
    
        
    public void checkExistingSystemID(){
         String sql = "select * from system where sn='"+SystemInfo.systemid+"'";
        try{
             pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                 if(rs.next()){
        updateSystemID();
              }else{
                     insertSystemID();
                 }
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1005", JOptionPane.ERROR_MESSAGE);
        }
    }
public void updateSystemID(){
     String sql = "update system set status=1 where sn='"+SystemInfo.systemid+"'";
        try{
             pst = con.prepareStatement(sql);
             pst.executeUpdate();
             finishMessage();
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 10012", JOptionPane.ERROR_MESSAGE);
        }
}
    
    public void insertSystemID(){
        String sql = "insert into system (sn,status) values (?,?)";
        try{
             pst = con.prepareStatement(sql);
              pst.setString(1, SystemInfo.systemid);
            pst.setInt(2, 1);
            pst.executeUpdate();
             finishMessage();
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator\n\n"+e, "Error 1005", JOptionPane.ERROR_MESSAGE);
        }
       
    }

    public void finishMessage() {
        changeAvatar = false;
         Zz s = new Zz();
            s.login = false;
            s.Load();
            Window window = SwingUtilities.windowForComponent(this);
            window.dispose();
        LookAndFeel old = UIManager.getLookAndFeel();
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Zz.noAdmin) {
            Zz.noAdmin = false;
            JOptionPane.showMessageDialog(null, "Successfully registered...");
        } else {
             JOptionPane.showMessageDialog(null, "Successfully registered...\nContact the admin for confirmation...");
        }

        try {
            UIManager.setLookAndFeel(old);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public void ContactFilter() {
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

    ////START USER REGISTRATION///
    public static void changeImage() {
        new java.util.Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                if (changeAvatar) {
                    lblImage.setIcon(ResizeImage(us.image));
                }
                //home.show();

                //cool stuff....
                //Added some Jfree chart so let's see how it goes.
                //it will include on Github
            }
        }, 100 * 1);

    }

    public static void SignupDefaultValues() {
        us.firstname = "Firstname";
        us.middlename = "Middlename";
        us.lastname = "Lastname";
        us.suffix = "Suffix";
        us.gender = "Male";
        us.contactNumber = "Contact Number";
        us.email = "Email";
        us.username = "Username";
        us.password = "jPasswordField1";
        us.password2 = "jPasswordField1";
        us.image = "/images/icons8_Name_125px.png";

        txtFname.setText(us.firstname);
        txtMname.setText(us.middlename);
        txtLname.setText(us.lastname);
        txtSuffix.setText(us.suffix);
        jComboBox1.setSelectedItem(us.gender);
        txtContactNumber.setText(us.contactNumber);
        txtEmail.setText(us.email);
        txtUsername.setText(us.username);
        txtPass1.setText(us.password);
        txtPass4.setText(us.password2);
        lblImage.setIcon(new javax.swing.ImageIcon(LoginPanel1.class.getResource(us.image)));
    }

    public void FillFields() {
        txtFname.setText(us.firstname);
        txtMname.setText(us.middlename);
        txtLname.setText(us.lastname);
        txtSuffix.setText(us.suffix);
        jComboBox1.setSelectedItem(us.gender);
        txtContactNumber.setText(us.contactNumber);
        txtEmail.setText(us.email);
        txtUsername.setText(us.username);
        txtPass1.setText(us.password);
        txtPass4.setText(us.password2);
    }

    public void UserGender() {
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

    
    public void LoginForm(){
         login.setVisible(true);
        signup.setVisible(false);
        signup1.setVisible(false);
        forgot.setVisible(false);
        forgot1.setVisible(false);
        forgot2.setVisible(false);
        loader.setVisible(false);
        lblBack.setVisible(false);
    }
    
    
    public void pressLogin(){
         LookAndFeel old = UIManager.getLookAndFeel();
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            System.out.println(e);
        }
        if(txtUname.getText().equals("Username") || txtUname.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Enter the username...");
            txtUname.requestFocus();
        }else if(txtPass.getText().equals("jPasswordField1") || txtPass.getText().equals("")){
             JOptionPane.showMessageDialog(null, "Enter the password...");
            txtPass.requestFocus();
        }else{ 
            
            btn_login.setEnabled(false);
           processLogin();
              new java.util.Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                
                btn_login.setEnabled(true); 
              
            }
        }, 1000 * 2);
        }
        
        
         try {
            UIManager.setLookAndFeel(old);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    
    /*
    public void ChangeTable(){
          jTable1.setShowGrid(false);
        ChangeTable();
        JTableHeader Theader = jTable1.getTableHeader();
        
        Theader.setBackground(Color.WHITE); // change the Background color
        //Theader.setForeground(Color.WHITE); // change the Foreground
        
        /*Theader.setFont(new Font("Tahome", Font.BOLD, 20)); // font name style size
        ((DefaultTableCellRenderer)Theader.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER); // center header text
        
    }
     */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlBackground = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        login = new javax.swing.JPanel();
        txtUname = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btn_login = new java.awt.Button();
        jLabel5 = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        signup = new javax.swing.JPanel();
        txtFname = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        txtMname = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtLname = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        lblImage = new javax.swing.JLabel();
        txtSuffix = new javax.swing.JTextField();
        jSeparator15 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jSeparator18 = new javax.swing.JSeparator();
        jLabel42 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        signup1 = new javax.swing.JPanel();
        txtEmail = new javax.swing.JTextField();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        txtUsername = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        txtPass1 = new javax.swing.JPasswordField();
        jLabel17 = new javax.swing.JLabel();
        txtContactNumber = new javax.swing.JTextField();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txtPass4 = new javax.swing.JPasswordField();
        jLabel27 = new javax.swing.JLabel();
        jSeparator17 = new javax.swing.JSeparator();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        forgot = new javax.swing.JPanel();
        txtFname3 = new javax.swing.JTextField();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        forgot1 = new javax.swing.JPanel();
        txtFname4 = new javax.swing.JTextField();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        forgot2 = new javax.swing.JPanel();
        jSeparator16 = new javax.swing.JSeparator();
        jLabel21 = new javax.swing.JLabel();
        txtPass2 = new javax.swing.JPasswordField();
        jLabel26 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtPass3 = new javax.swing.JPasswordField();
        jSeparator19 = new javax.swing.JSeparator();
        jButton5 = new javax.swing.JButton();
        loader = new javax.swing.JPanel();
        img_loader = new javax.swing.JLabel();
        lbl_loader = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(499, 569));
        setMinimumSize(new java.awt.Dimension(499, 569));
        setPreferredSize(new java.awt.Dimension(499, 569));

        pnlBackground.setBackground(new java.awt.Color(255, 255, 255));
        pnlBackground.setLayout(new javax.swing.BoxLayout(pnlBackground, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.CardLayout());

        login.setBackground(new java.awt.Color(255, 255, 255));
        login.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                loginMouseDragged(evt);
            }
        });
        login.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                loginMousePressed(evt);
            }
        });

        txtUname.setForeground(new java.awt.Color(102, 102, 102));
        txtUname.setText("Username");
        txtUname.setToolTipText("Username");
        txtUname.setBorder(null);
        txtUname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUnameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUnameFocusLost(evt);
            }
        });
        txtUname.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtUnameMouseClicked(evt);
            }
        });
        txtUname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUnameKeyReleased(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator1.setForeground(new java.awt.Color(84, 127, 206));

        jSeparator2.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator2.setForeground(new java.awt.Color(84, 127, 206));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/unlock_18px.png"))); // NOI18N
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/contacts_18px.png"))); // NOI18N

        btn_login.setBackground(new java.awt.Color(84, 127, 206));
        btn_login.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_login.setForeground(new java.awt.Color(255, 255, 255));
        btn_login.setLabel("Login");
        btn_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_loginActionPerformed(evt);
            }
        });

        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Forgot password?");
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel5MouseEntered(evt);
            }
        });

        txtPass.setForeground(new java.awt.Color(102, 102, 102));
        txtPass.setText("jPasswordField1");
        txtPass.setToolTipText("Password");
        txtPass.setBorder(null);
        txtPass.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPassFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPassFocusLost(evt);
            }
        });
        txtPass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPassMouseClicked(evt);
            }
        });
        txtPass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPassKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI Semilight", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Login or Signup");

        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Create Account");
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout loginLayout = new javax.swing.GroupLayout(login);
        login.setLayout(loginLayout);
        loginLayout.setHorizontalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginLayout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loginLayout.createSequentialGroup()
                        .addGroup(loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6)
                            .addGroup(loginLayout.createSequentialGroup()
                                .addGroup(loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtUname)
                                    .addComponent(jSeparator1)
                                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtPass, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loginLayout.createSequentialGroup()
                                .addGroup(loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jSeparator4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_login, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(76, 76, 76))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loginLayout.createSequentialGroup()
                        .addGroup(loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator5))
                        .addGap(205, 205, 205))))
        );
        loginLayout.setVerticalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginLayout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jLabel6)
                .addGap(111, 111, 111)
                .addGroup(loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(loginLayout.createSequentialGroup()
                        .addComponent(txtUname, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(loginLayout.createSequentialGroup()
                        .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(103, 103, 103)
                .addGroup(loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_login, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(loginLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57))
        );

        jPanel1.add(login, "card2");

        signup.setBackground(new java.awt.Color(255, 255, 255));
        signup.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                signupMouseDragged(evt);
            }
        });
        signup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                signupMousePressed(evt);
            }
        });

        txtFname.setForeground(new java.awt.Color(102, 102, 102));
        txtFname.setText("Firstname");
        txtFname.setToolTipText("Enter Firstname");
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
        txtFname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFnameKeyReleased(evt);
            }
        });

        jSeparator3.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator3.setForeground(new java.awt.Color(84, 127, 206));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/contacts_18px.png"))); // NOI18N

        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("Already a member");
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI Semilight", 0, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("Signup");

        jSeparator7.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator7.setForeground(new java.awt.Color(84, 127, 206));

        txtMname.setForeground(new java.awt.Color(102, 102, 102));
        txtMname.setText("Middlename");
        txtMname.setToolTipText("Enter Middlename");
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

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/contacts_18px.png"))); // NOI18N

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/contacts_18px.png"))); // NOI18N

        txtLname.setForeground(new java.awt.Color(102, 102, 102));
        txtLname.setText("Lastname");
        txtLname.setToolTipText("Enter Lastname");
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

        jSeparator8.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator8.setForeground(new java.awt.Color(84, 127, 206));

        lblImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Name_125px.png"))); // NOI18N
        lblImage.setToolTipText("Add photo");
        lblImage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblImageMouseClicked(evt);
            }
        });
        lblImage.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                lblImagePropertyChange(evt);
            }
        });

        txtSuffix.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtSuffix.setForeground(new java.awt.Color(102, 102, 102));
        txtSuffix.setText("Suffix");
        txtSuffix.setToolTipText("Enter Suffix");
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

        jSeparator15.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator15.setForeground(new java.awt.Color(84, 127, 206));

        jButton2.setBackground(new java.awt.Color(84, 127, 206));
        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Next");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setText("*");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 51, 51));
        jLabel24.setText("*");

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));
        jComboBox1.setToolTipText("Select Gender");
        jComboBox1.setBorder(null);
        jComboBox1.setFocusable(false);
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jComboBox1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboBox1KeyReleased(evt);
            }
        });

        jSeparator18.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator18.setForeground(new java.awt.Color(84, 127, 206));

        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Gender_18px_1.png"))); // NOI18N

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 51, 51));
        jLabel25.setText("*");

        javax.swing.GroupLayout signupLayout = new javax.swing.GroupLayout(signup);
        signup.setLayout(signupLayout);
        signupLayout.setHorizontalGroup(
            signupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(signupLayout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(signupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, signupLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblImage)
                        .addGap(187, 187, 187))
                    .addGroup(signupLayout.createSequentialGroup()
                        .addGroup(signupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, signupLayout.createSequentialGroup()
                                .addGroup(signupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jSeparator6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(signupLayout.createSequentialGroup()
                                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(signupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(signupLayout.createSequentialGroup()
                                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jSeparator18)))
                            .addGroup(signupLayout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(signupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator7)
                                    .addComponent(txtMname)))
                            .addGroup(signupLayout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(signupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(signupLayout.createSequentialGroup()
                                        .addComponent(txtFname, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel1))
                                    .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)))
                            .addGroup(signupLayout.createSequentialGroup()
                                .addGroup(signupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addGroup(signupLayout.createSequentialGroup()
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(signupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(signupLayout.createSequentialGroup()
                                                .addComponent(txtLname, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel24))
                                            .addComponent(jSeparator8))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(signupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtSuffix, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                                            .addComponent(jSeparator15))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(76, 76, 76))))
        );
        signupLayout.setVerticalGroup(
            signupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(signupLayout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblImage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(signupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(signupLayout.createSequentialGroup()
                        .addGroup(signupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtFname, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(signupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(signupLayout.createSequentialGroup()
                        .addComponent(txtMname, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(signupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(signupLayout.createSequentialGroup()
                        .addGroup(signupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSuffix, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(signupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtLname, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel24)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(signupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(signupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(signupLayout.createSequentialGroup()
                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(signupLayout.createSequentialGroup()
                        .addGroup(signupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator18, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)))
                .addGroup(signupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, signupLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        jPanel1.add(signup, "card2");

        signup1.setBackground(new java.awt.Color(255, 255, 255));
        signup1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                signup1MouseDragged(evt);
            }
        });
        signup1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                signup1MousePressed(evt);
            }
        });

        txtEmail.setForeground(new java.awt.Color(102, 102, 102));
        txtEmail.setText("Email");
        txtEmail.setToolTipText("Enter Email");
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
        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEmailKeyReleased(evt);
            }
        });

        jSeparator9.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator9.setForeground(new java.awt.Color(84, 127, 206));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Secured_Letter_18px_3.png"))); // NOI18N

        jLabel14.setFont(new java.awt.Font("Segoe UI Semilight", 0, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setText("Signup");

        jSeparator11.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator11.setForeground(new java.awt.Color(84, 127, 206));

        txtUsername.setForeground(new java.awt.Color(102, 102, 102));
        txtUsername.setText("Username");
        txtUsername.setToolTipText("Enter Username");
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
        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });
        txtUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUsernameKeyReleased(evt);
            }
        });

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/contacts_18px.png"))); // NOI18N

        jSeparator12.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator12.setForeground(new java.awt.Color(84, 127, 206));

        txtPass1.setForeground(new java.awt.Color(102, 102, 102));
        txtPass1.setText("jPasswordField1");
        txtPass1.setToolTipText("Enter Password");
        txtPass1.setBorder(null);
        txtPass1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPass1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPass1FocusLost(evt);
            }
        });
        txtPass1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPass1MouseClicked(evt);
            }
        });
        txtPass1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPass1KeyReleased(evt);
            }
        });

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/unlock_18px.png"))); // NOI18N
        jLabel17.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        txtContactNumber.setForeground(new java.awt.Color(102, 102, 102));
        txtContactNumber.setText("Contact Number");
        txtContactNumber.setToolTipText("Enter Contact Number");
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

        jSeparator10.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator10.setForeground(new java.awt.Color(84, 127, 206));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Phone_18px_1.png"))); // NOI18N

        jButton1.setBackground(new java.awt.Color(84, 127, 206));
        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Signup");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtPass4.setForeground(new java.awt.Color(102, 102, 102));
        txtPass4.setText("jPasswordField1");
        txtPass4.setToolTipText("Repeat Password");
        txtPass4.setBorder(null);
        txtPass4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPass4FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPass4FocusLost(evt);
            }
        });
        txtPass4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPass4MouseClicked(evt);
            }
        });
        txtPass4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPass4KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPass4KeyTyped(evt);
            }
        });

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/unlock_18px.png"))); // NOI18N
        jLabel27.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jSeparator17.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator17.setForeground(new java.awt.Color(84, 127, 206));

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 51, 51));
        jLabel30.setText("*");

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 51, 51));
        jLabel31.setText("*");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 51, 51));
        jLabel32.setText("*");

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 51, 51));
        jLabel33.setText("*");

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 51, 51));
        jLabel34.setText("*");

        javax.swing.GroupLayout signup1Layout = new javax.swing.GroupLayout(signup1);
        signup1.setLayout(signup1Layout);
        signup1Layout.setHorizontalGroup(
            signup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, signup1Layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(signup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addGroup(signup1Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(signup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, signup1Layout.createSequentialGroup()
                                .addComponent(txtUsername)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel31))))
                    .addGroup(signup1Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(signup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, signup1Layout.createSequentialGroup()
                                .addComponent(txtEmail)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel34))))
                    .addGroup(signup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(signup1Layout.createSequentialGroup()
                            .addComponent(jLabel17)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(signup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, signup1Layout.createSequentialGroup()
                                    .addComponent(txtPass1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel32))))
                        .addGroup(signup1Layout.createSequentialGroup()
                            .addComponent(jLabel27)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(signup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, signup1Layout.createSequentialGroup()
                                    .addComponent(txtPass4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel33)))))
                    .addGroup(signup1Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(signup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(signup1Layout.createSequentialGroup()
                                .addComponent(txtContactNumber)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel30)))))
                .addContainerGap(76, Short.MAX_VALUE))
        );
        signup1Layout.setVerticalGroup(
            signup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, signup1Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jLabel14)
                .addGap(56, 56, 56)
                .addGroup(signup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(signup1Layout.createSequentialGroup()
                        .addGroup(signup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtContactNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(signup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(signup1Layout.createSequentialGroup()
                        .addGroup(signup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(signup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(signup1Layout.createSequentialGroup()
                        .addGroup(signup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(signup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(signup1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(signup1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(signup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPass1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(signup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(signup1Layout.createSequentialGroup()
                        .addGroup(signup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPass4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel33))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
        );

        jPanel1.add(signup1, "card2");

        forgot.setBackground(new java.awt.Color(255, 255, 255));
        forgot.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                forgotMouseDragged(evt);
            }
        });
        forgot.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                forgotMousePressed(evt);
            }
        });

        txtFname3.setForeground(new java.awt.Color(102, 102, 102));
        txtFname3.setText("Email");
        txtFname3.setBorder(null);
        txtFname3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFname3FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFname3FocusLost(evt);
            }
        });
        txtFname3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtFname3MouseClicked(evt);
            }
        });
        txtFname3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFname3ActionPerformed(evt);
            }
        });

        jSeparator13.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator13.setForeground(new java.awt.Color(84, 127, 206));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Secured_Letter_18px_3.png"))); // NOI18N

        jLabel18.setFont(new java.awt.Font("Segoe UI Semilight", 0, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 51, 51));
        jLabel18.setText("Forgot Password");

        jLabel22.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(51, 51, 51));
        jLabel22.setText("Send code via email");

        jButton3.setBackground(new java.awt.Color(84, 127, 206));
        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Continue");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout forgotLayout = new javax.swing.GroupLayout(forgot);
        forgot.setLayout(forgotLayout);
        forgotLayout.setHorizontalGroup(
            forgotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, forgotLayout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(forgotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jLabel18)
                    .addGroup(forgotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(forgotLayout.createSequentialGroup()
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(forgotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtFname3, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                                .addComponent(jSeparator13)))))
                .addContainerGap(76, Short.MAX_VALUE))
        );
        forgotLayout.setVerticalGroup(
            forgotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, forgotLayout.createSequentialGroup()
                .addGroup(forgotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(forgotLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(forgotLayout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22)
                        .addGap(121, 121, 121)
                        .addGroup(forgotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(forgotLayout.createSequentialGroup()
                                .addComponent(txtFname3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 235, Short.MAX_VALUE)))
                .addGap(52, 52, 52))
        );

        jPanel1.add(forgot, "card2");

        forgot1.setBackground(new java.awt.Color(255, 255, 255));
        forgot1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                forgot1MouseDragged(evt);
            }
        });
        forgot1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                forgot1MousePressed(evt);
            }
        });

        txtFname4.setForeground(new java.awt.Color(102, 102, 102));
        txtFname4.setText("Confirmation Code");
        txtFname4.setBorder(null);
        txtFname4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFname4FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFname4FocusLost(evt);
            }
        });
        txtFname4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtFname4MouseClicked(evt);
            }
        });
        txtFname4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFname4ActionPerformed(evt);
            }
        });

        jSeparator14.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator14.setForeground(new java.awt.Color(84, 127, 206));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Checked_18px_1.png"))); // NOI18N

        jLabel20.setFont(new java.awt.Font("Segoe UI Semilight", 0, 24)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(51, 51, 51));
        jLabel20.setText("Forgot Password");

        jLabel23.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(51, 51, 51));
        jLabel23.setText("Enter confirmation code");

        jButton4.setBackground(new java.awt.Color(84, 127, 206));
        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Submit");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout forgot1Layout = new javax.swing.GroupLayout(forgot1);
        forgot1.setLayout(forgot1Layout);
        forgot1Layout.setHorizontalGroup(
            forgot1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, forgot1Layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(forgot1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addComponent(jLabel20)
                    .addGroup(forgot1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(forgot1Layout.createSequentialGroup()
                            .addGap(109, 109, 109)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, forgot1Layout.createSequentialGroup()
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(forgot1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtFname4, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                                .addComponent(jSeparator14)))))
                .addGap(76, 76, 76))
        );
        forgot1Layout.setVerticalGroup(
            forgot1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, forgot1Layout.createSequentialGroup()
                .addGroup(forgot1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(forgot1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(forgot1Layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23)
                        .addGap(121, 121, 121)
                        .addGroup(forgot1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(forgot1Layout.createSequentialGroup()
                                .addComponent(txtFname4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 235, Short.MAX_VALUE)))
                .addGap(52, 52, 52))
        );

        jPanel1.add(forgot1, "card2");

        forgot2.setBackground(new java.awt.Color(255, 255, 255));
        forgot2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                forgot2MouseDragged(evt);
            }
        });
        forgot2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                forgot2MousePressed(evt);
            }
        });

        jSeparator16.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator16.setForeground(new java.awt.Color(84, 127, 206));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/unlock_18px.png"))); // NOI18N
        jLabel21.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        txtPass2.setForeground(new java.awt.Color(102, 102, 102));
        txtPass2.setText("jPasswordField1");
        txtPass2.setBorder(null);
        txtPass2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPass2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPass2FocusLost(evt);
            }
        });
        txtPass2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPass2MouseClicked(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Segoe UI Semilight", 0, 24)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(51, 51, 51));
        jLabel26.setText("Forgot Password");

        jLabel28.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(51, 51, 51));
        jLabel28.setText("Enter your new password");

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/unlock_18px.png"))); // NOI18N
        jLabel29.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        txtPass3.setForeground(new java.awt.Color(102, 102, 102));
        txtPass3.setText("jPasswordField1");
        txtPass3.setBorder(null);
        txtPass3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPass3FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPass3FocusLost(evt);
            }
        });
        txtPass3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPass3MouseClicked(evt);
            }
        });

        jSeparator19.setBackground(new java.awt.Color(84, 127, 206));
        jSeparator19.setForeground(new java.awt.Color(84, 127, 206));

        jButton5.setBackground(new java.awt.Color(84, 127, 206));
        jButton5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Confirm");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout forgot2Layout = new javax.swing.GroupLayout(forgot2);
        forgot2.setLayout(forgot2Layout);
        forgot2Layout.setHorizontalGroup(
            forgot2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, forgot2Layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(forgot2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(forgot2Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(forgot2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator19, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtPass3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel28)
                    .addComponent(jLabel26)
                    .addGroup(forgot2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(forgot2Layout.createSequentialGroup()
                            .addComponent(jLabel21)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(forgot2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jSeparator16, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtPass2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        forgot2Layout.setVerticalGroup(
            forgot2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, forgot2Layout.createSequentialGroup()
                .addGroup(forgot2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(forgot2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(forgot2Layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel28)
                        .addGap(92, 92, 92)
                        .addGroup(forgot2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(forgot2Layout.createSequentialGroup()
                                .addComponent(txtPass3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator19, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(forgot2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(forgot2Layout.createSequentialGroup()
                                .addComponent(txtPass2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 207, Short.MAX_VALUE)))
                .addGap(52, 52, 52))
        );

        jPanel1.add(forgot2, "card2");

        loader.setBackground(new java.awt.Color(255, 255, 255));

        img_loader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ring.gif"))); // NOI18N

        lbl_loader.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_loader.setForeground(new java.awt.Color(84, 127, 206));
        lbl_loader.setText("Please Wait...");

        javax.swing.GroupLayout loaderLayout = new javax.swing.GroupLayout(loader);
        loader.setLayout(loaderLayout);
        loaderLayout.setHorizontalGroup(
            loaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loaderLayout.createSequentialGroup()
                .addGroup(loaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(loaderLayout.createSequentialGroup()
                        .addGap(180, 180, 180)
                        .addComponent(img_loader, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(loaderLayout.createSequentialGroup()
                        .addGap(205, 205, 205)
                        .addComponent(lbl_loader)))
                .addContainerGap(181, Short.MAX_VALUE))
        );
        loaderLayout.setVerticalGroup(
            loaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loaderLayout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addComponent(img_loader, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addComponent(lbl_loader)
                .addContainerGap(216, Short.MAX_VALUE))
        );

        jPanel1.add(loader, "card3");

        pnlBackground.add(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(pnlBackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 582, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(pnlBackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtUnameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUnameFocusGained
        // TODO add your handling code here:
        if (txtUname.getText().equals("Username")) {
            txtUname.setText("");
        }
    }//GEN-LAST:event_txtUnameFocusGained

    private void btn_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_loginActionPerformed
   LookAndFeel old = UIManager.getLookAndFeel();
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            System.out.println(e);
        }
        if(txtUname.getText().equals("Username") || txtUname.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Enter the username...");
            txtUname.requestFocus();
        }else if(txtPass.getText().equals("jPasswordField1") || txtPass.getText().equals("")){
             JOptionPane.showMessageDialog(null, "Enter the password...");
            txtPass.requestFocus();
        }else{ 
            
            btn_login.setEnabled(false);
           processLogin();
              new java.util.Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                
                btn_login.setEnabled(true); 
              
            }
        }, 1000 * 2);
        }
        
        
         try {
            UIManager.setLookAndFeel(old);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        
        
        /*
        
        login.setVisible(false);
        signup.setVisible(false);
        signup1.setVisible(false);
        forgot.setVisible(false);
        forgot1.setVisible(false);
        forgot2.setVisible(false);
        loader.setVisible(true);
        // lets add timeout
        new java.util.Timer().schedule(new TimerTask() {
            @Override
            public void run() {
             
                CDPOSystem s = new CDPOSystem();
                s.login = true;
                Component button = (Component) evt.getSource();
                Window window = SwingUtilities.windowForComponent(button);
                s.Load();
                window.dispose();
              
            }
        }, 1000 * 3);
         
         */

    }//GEN-LAST:event_btn_loginActionPerformed

    private void txtPassFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassFocusGained
/*        if (txtPass.getText().equals("jPasswordField1")) {
            txtPass.setText("");
        }
        */
if( txtPass.getText().equals("Enter new password")){
   txtPass.setText(""); 
   txtPass.setEchoChar('*');
 txtPass.setFont(new java.awt.Font("Segoe UI", 1, 14));
}
    }//GEN-LAST:event_txtPassFocusGained

    private void loginMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginMouseDragged
        // TODO add your handling code here:

    }//GEN-LAST:event_loginMouseDragged

    private void loginMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginMousePressed
        // TODO add your handling code here:


    }//GEN-LAST:event_loginMousePressed

    private void txtUnameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUnameFocusLost
        if (txtUname.getText().equals("")) {
            txtUname.setText("Username");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtUnameFocusLost

    private void txtUnameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUnameMouseClicked
        if (txtUname.getText().equals("Username")) {
            txtUname.setText("");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtUnameMouseClicked

    private void txtPassFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassFocusLost
        if (txtPass.getText().equals("")) {
            txtPass.setText("jPasswordField1");
        }           // TODO add your handling code here:
    }//GEN-LAST:event_txtPassFocusLost

    private void txtPassMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPassMouseClicked
        if (txtPass.getText().equals("jPasswordField1")) {
            txtPass.setText("");
        }             // TODO add your handling code here:
    }//GEN-LAST:event_txtPassMouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        SignupDefaultValues();
        LoginSignupPanel.dlogin = false;
        LoginSignupPanel.dsignup = true;
        LoginSignupPanel.dsignup1 = false;
        LoginSignupPanel.dforgot = false;
        LoginSignupPanel.dforgot1 = false;
        LoginSignupPanel.dforgot2 = false;
        lblBack.setVisible(true);
        login.setVisible(false);
        signup.setVisible(true);
        signup1.setVisible(false);
        forgot.setVisible(false);
        forgot1.setVisible(false);
        forgot2.setVisible(false);
        loader.setVisible(false);

    }//GEN-LAST:event_jLabel7MouseClicked

    private void txtFnameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFnameFocusGained
        // TODO add your handling code here:
        if (txtFname.getText().equals("Firstname")) {
            txtFname.setText("");
        }
    }//GEN-LAST:event_txtFnameFocusGained

    private void txtFnameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFnameFocusLost
        if (txtFname.getText().equals("")) {
            txtFname.setText("Firstname");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtFnameFocusLost

    private void txtFnameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtFnameMouseClicked
        if (txtFname.getText().equals("Firstname")) {
            txtFname.setText("");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtFnameMouseClicked

    private void txtMnameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMnameFocusGained
        if (txtMname.getText().equals("Middlename")) {
            txtMname.setText("");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtMnameFocusGained

    private void txtMnameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMnameFocusLost
        if (txtMname.getText().equals("")) {
            txtMname.setText("Middlename");
        }                // TODO add your handling code here:
    }//GEN-LAST:event_txtMnameFocusLost

    private void txtMnameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMnameMouseClicked
        if (txtMname.getText().equals("Middlename")) {
            txtMname.setText("");
        }           // TODO add your handling code here:
    }//GEN-LAST:event_txtMnameMouseClicked

    private void txtMnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMnameActionPerformed

    private void txtLnameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLnameFocusGained
        if (txtLname.getText().equals("Lastname")) {
            txtLname.setText("");
        }
    }//GEN-LAST:event_txtLnameFocusGained

    private void txtLnameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLnameFocusLost
        // TODO add your handling code here:
        if (txtLname.getText().equals("")) {
            txtLname.setText("Lastname");
        }
    }//GEN-LAST:event_txtLnameFocusLost

    private void txtLnameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtLnameMouseClicked
        if (txtLname.getText().equals("Middlename")) {
            txtLname.setText("");
        }
    }//GEN-LAST:event_txtLnameMouseClicked

    private void signupMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signupMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_signupMouseDragged

    private void signupMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signupMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_signupMousePressed

    private void txtEmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusGained
        if (txtEmail.getText().equals("Email")) {
            txtEmail.setText("");
        }
    }//GEN-LAST:event_txtEmailFocusGained

    private void txtEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusLost
        if (txtEmail.getText().equals("")) {
            txtEmail.setText("Email");
        }
    }//GEN-LAST:event_txtEmailFocusLost

    private void txtEmailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtEmailMouseClicked
        if (txtEmail.getText().equals("Email")) {
            txtEmail.setText("");
        }           // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailMouseClicked

    private void txtUsernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsernameFocusGained
        if (txtUsername.getText().equals("Username")) {
            txtUsername.setText("");
        }
    }//GEN-LAST:event_txtUsernameFocusGained

    private void txtUsernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsernameFocusLost
        if (txtUsername.getText().equals("")) {
            txtUsername.setText("Username");
        }
    }//GEN-LAST:event_txtUsernameFocusLost

    private void txtUsernameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUsernameMouseClicked
        if (txtUsername.getText().equals("Username")) {
            txtUsername.setText("");
        }
    }//GEN-LAST:event_txtUsernameMouseClicked

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    private void signup1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signup1MouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_signup1MouseDragged

    private void signup1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signup1MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_signup1MousePressed

    private void txtPass1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPass1FocusGained
        if (txtPass1.getText().equals("jPasswordField1")) {
            txtPass1.setText("");
        }             // TODO add your handling code here:
    }//GEN-LAST:event_txtPass1FocusGained

    private void txtPass1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPass1FocusLost
        if (txtPass1.getText().equals("")) {
            txtPass1.setText("jPasswordField1");
        }
    }//GEN-LAST:event_txtPass1FocusLost

    private void txtPass1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPass1MouseClicked
        if (txtPass1.getText().equals("jPasswordField1")) {
            txtPass1.setText("");
        }
    }//GEN-LAST:event_txtPass1MouseClicked

    private void txtContactNumberFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtContactNumberFocusGained
        if (txtContactNumber.getText().equals("Contact Number")) {
            txtContactNumber.setText("");
        }             // TODO add your handling code here:
    }//GEN-LAST:event_txtContactNumberFocusGained

    private void txtContactNumberFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtContactNumberFocusLost
        if (txtContactNumber.getText().equals("")) {
            txtContactNumber.setText("Contact Number");
        }
    }//GEN-LAST:event_txtContactNumberFocusLost

    private void txtContactNumberMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtContactNumberMouseClicked
        if (txtContactNumber.getText().equals("Contact Number")) {
            txtContactNumber.setText("");
        }          // TODO add your handling code here:
    }//GEN-LAST:event_txtContactNumberMouseClicked

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtFname3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFname3FocusGained
        if (txtFname3.getText().equals("Email")) {
            txtFname3.setText("");
        }
    }//GEN-LAST:event_txtFname3FocusGained

    private void txtFname3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFname3FocusLost
        if (txtFname3.getText().equals("")) {
            txtFname3.setText("Email");
        }           // TODO add your handling code here:
    }//GEN-LAST:event_txtFname3FocusLost

    private void txtFname3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtFname3MouseClicked
        if (txtFname3.getText().equals("Email")) {
            txtFname3.setText("");
        }
    }//GEN-LAST:event_txtFname3MouseClicked

    private void txtFname3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFname3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFname3ActionPerformed

    private void forgotMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forgotMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_forgotMouseDragged

    private void forgotMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forgotMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_forgotMousePressed

    private void txtFname4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFname4FocusGained
        if (txtFname4.getText().equals("Confirmation Code")) {
            txtFname4.setText("");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtFname4FocusGained

    private void txtFname4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFname4FocusLost
        if (txtFname4.getText().equals("")) {
            txtFname4.setText("Confirmation Code");
        }
    }//GEN-LAST:event_txtFname4FocusLost

    private void txtFname4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtFname4MouseClicked
        if (txtFname4.getText().equals("Confirmation Code")) {
            txtFname4.setText("");
        }         // TODO add your handling code here:
    }//GEN-LAST:event_txtFname4MouseClicked

    private void txtFname4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFname4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFname4ActionPerformed

    private void forgot1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forgot1MouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_forgot1MouseDragged

    private void forgot1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forgot1MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_forgot1MousePressed

    private void txtPass2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPass2FocusGained
        if (txtPass2.getText().equals("jPasswordField1")) {
            txtPass2.setText("");
        }
    }//GEN-LAST:event_txtPass2FocusGained

    private void txtPass2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPass2FocusLost
        if (txtPass2.getText().equals("")) {
            txtPass2.setText("jPasswordField1");
        }
    }//GEN-LAST:event_txtPass2FocusLost

    private void txtPass2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPass2MouseClicked
        if (txtPass2.getText().equals("jPasswordField1")) {
            txtPass2.setText("");
        }
    }//GEN-LAST:event_txtPass2MouseClicked

    private void forgot2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forgot2MouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_forgot2MouseDragged

    private void forgot2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forgot2MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_forgot2MousePressed

    private void txtPass3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPass3FocusGained
        if (txtPass3.getText().equals("jPasswordField1")) {
            txtPass3.setText("");
        }
    }//GEN-LAST:event_txtPass3FocusGained

    private void txtPass3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPass3FocusLost
        if (txtPass3.getText().equals("")) {
            txtPass3.setText("jPasswordField1");
        }
    }//GEN-LAST:event_txtPass3FocusLost

    private void txtPass3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPass3MouseClicked
        if (txtPass3.getText().equals("jPasswordField1")) {
            txtPass3.setText("");
        }
    }//GEN-LAST:event_txtPass3MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
//new DynamicPanel(pnlPrincipal, this);
        LoginSignupPanel.dlogin = false;
        LoginSignupPanel.dsignup = false;
        LoginSignupPanel.dsignup1 = false;
        LoginSignupPanel.dforgot = true;
        LoginSignupPanel.dforgot1 = false;
        LoginSignupPanel.dforgot2 = false;
        lblBack.setVisible(true);
        login.setVisible(false);
        signup.setVisible(false);
        signup1.setVisible(false);
        forgot.setVisible(true);
        forgot1.setVisible(false);
        forgot2.setVisible(false);
        loader.setVisible(false);
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
       LoginForm();
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel5MouseEntered

    private void lblImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImageMouseClicked

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
                    lblImage.setIcon(ResizeImage(path));
                    us.image = path;
                    changeAvatar = true;
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

    }//GEN-LAST:event_lblImageMouseClicked

    private void txtSuffixFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSuffixFocusGained
        if (txtSuffix.getText().equals("Suffix")) {
            txtSuffix.setText("");
        }             // TODO add your handling code here:
    }//GEN-LAST:event_txtSuffixFocusGained

    private void txtSuffixFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSuffixFocusLost
        if (txtSuffix.getText().equals("")) {
            txtSuffix.setText("Suffix");
        }
    }//GEN-LAST:event_txtSuffixFocusLost

    private void txtSuffixMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSuffixMouseClicked
        if (txtSuffix.getText().equals("Suffix")) {
            txtSuffix.setText("");
        }             // TODO add your handling code here:
    }//GEN-LAST:event_txtSuffixMouseClicked

    private void txtSuffixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSuffixActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSuffixActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        LookAndFeel old = UIManager.getLookAndFeel();
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            System.out.println(e);
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

        if (txtContactNumber.getText().equals("Contact Number")) {

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
        } else if (txtPass1.getText().equals("jPasswordField1")) {

            JOptionPane.showMessageDialog(null, "Enter your password...");

            txtPass1.requestFocus();
        } else if (txtPass4.getText().equals("jPasswordField1")) {

            JOptionPane.showMessageDialog(null, "Repeat your password...");

            txtPass4.requestFocus();
        } else if (!txtPass1.getText().equals(txtPass4.getText())) {

            JOptionPane.showMessageDialog(null, "Password is not matched...");

            txtPass4.requestFocus();
        } else if (txtPass1.getText().length() <= 5) {
            JOptionPane.showMessageDialog(null, "Password must 6 characters and above");
            txtPass1.requestFocus();
        } else {
            
            
            processRegistration();
            /*
  // checkAdmin();
  //checkDuplicate();   
  // insertPersonalInfo();
  // insertImage();
  // insertContact();
  // insertAccount();
       login.setVisible(true);
        signup.setVisible(false);
        signup1.setVisible(false);
        forgot.setVisible(false);
        forgot1.setVisible(false);
        forgot2.setVisible(false);
        loader.setVisible(false);
        JOptionPane.showMessageDialog(null, "Successfully registered...\nContact the admin for account approval...");
 
   
             */
        }
        try {
            UIManager.setLookAndFeel(old);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        LookAndFeel old = UIManager.getLookAndFeel();
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        LoginPanel1.firstOpen = false;
        if (txtFname.getText().equals("Firstname")) {

            JOptionPane.showMessageDialog(null, "Firstname is required...");

            txtFname.requestFocus();
        } else if (txtLname.getText().equals("Lastname")) {

            JOptionPane.showMessageDialog(null, "Lastname is required...");

            txtLname.requestFocus();
        } else {
            LoginSignupPanel.dlogin = false;
            LoginSignupPanel.dsignup = false;
            LoginSignupPanel.dsignup1 = true;
            LoginSignupPanel.dforgot = false;
            LoginSignupPanel.dforgot1 = false;
            LoginSignupPanel.dforgot2 = false;
            lblBack.setVisible(true);
            lblBack.setVisible(true);
            login.setVisible(false);
            signup.setVisible(false);
            signup1.setVisible(true);

            forgot.setVisible(false);
            forgot1.setVisible(false);
            forgot2.setVisible(false);
            loader.setVisible(false);

        }
        try {
            UIManager.setLookAndFeel(old);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        if (!jComboBox1.getSelectedItem().equals("Male")) {
            us.gender = jComboBox1.getSelectedItem().toString();
        } else {
            us.gender = "Male";
        }
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void txtPass4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPass4FocusGained
        if (txtPass4.getText().equals("jPasswordField1")) {
            txtPass4.setText("");
        }          // TODO add your handling code here:
    }//GEN-LAST:event_txtPass4FocusGained

    private void txtPass4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPass4FocusLost
        if (txtPass4.getText().equals("")) {
            txtPass4.setText("jPasswordField1");
        }                 // TODO add your handling code here:
    }//GEN-LAST:event_txtPass4FocusLost

    private void txtPass4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPass4MouseClicked
        if (txtPass4.getText().equals("jPasswordField1")) {
            txtPass4.setText("");
        }         // TODO add your handling code here:
    }//GEN-LAST:event_txtPass4MouseClicked

    private void txtFnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFnameKeyReleased
        if (!txtFname.getText().equals("")) {
            us.firstname = txtFname.getText();
        } else {
            us.firstname = "Firstname";
        }

    }//GEN-LAST:event_txtFnameKeyReleased

    private void txtMnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMnameKeyReleased
        if (!txtMname.getText().equals("")) {
            us.middlename = txtMname.getText();
        } else {
            us.middlename = "Middlename";
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtMnameKeyReleased

    private void txtLnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLnameKeyReleased
        if (!txtLname.getText().equals("")) {
            us.lastname = txtLname.getText();
        } else {
            us.lastname = "Lastname";
        }
    }//GEN-LAST:event_txtLnameKeyReleased

    private void txtSuffixKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSuffixKeyReleased
        if (!txtSuffix.getText().equals("")) {
            us.suffix = txtSuffix.getText();
        } else {
            us.suffix = "Suffix";
        }
    }//GEN-LAST:event_txtSuffixKeyReleased

    private void txtContactNumberKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContactNumberKeyReleased
        ContactFilter();
        if (!txtContactNumber.getText().equals("")) {
            us.contactNumber = txtContactNumber.getText();
        } else {
            us.contactNumber = "Contact Number";
        }
    }//GEN-LAST:event_txtContactNumberKeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        LoginSignupPanel.dlogin = false;
        LoginSignupPanel.dsignup = false;
        LoginSignupPanel.dsignup1 = false;
        LoginSignupPanel.dforgot = false;
        LoginSignupPanel.dforgot1 = true;
        LoginSignupPanel.dforgot2 = false;
        lblBack.setVisible(true);
        login.setVisible(false);
        signup.setVisible(false);
        signup1.setVisible(false);
        forgot.setVisible(false);
        forgot1.setVisible(true);
        forgot2.setVisible(false);
        loader.setVisible(false);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        LoginSignupPanel.dlogin = false;
        LoginSignupPanel.dsignup = false;
        LoginSignupPanel.dsignup1 = false;
        LoginSignupPanel.dforgot = false;
        LoginSignupPanel.dforgot1 = false;
        LoginSignupPanel.dforgot2 = true;
        lblBack.setVisible(false);
        login.setVisible(false);
        signup.setVisible(false);
        signup1.setVisible(false);
        forgot.setVisible(false);
        forgot1.setVisible(false);
        forgot2.setVisible(true);
        loader.setVisible(false);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (txtPass3.getText().equals("jPasswordField1") || txtPass3.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Enter password", "", JOptionPane.INFORMATION_MESSAGE);
            txtPass3.requestFocus();
        } else if (txtPass3.getText().length() <= 5) {
            JOptionPane.showMessageDialog(null, "Password must 6 characters and above", "", JOptionPane.INFORMATION_MESSAGE);
            txtPass3.requestFocus();
        } else if (txtPass2.getText().length() <= 5) {
            JOptionPane.showMessageDialog(null, "Password must 6 characters and above", "", JOptionPane.INFORMATION_MESSAGE);
            txtPass2.requestFocus();
        } else if (txtPass2.getText().equals("jPasswordField1") || txtPass2.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Repeat password", "", JOptionPane.INFORMATION_MESSAGE);
            txtPass2.requestFocus();
        } else if (!txtPass3.getText().equals(txtPass2.getText())) {
            JOptionPane.showMessageDialog(null, "Password not match", "", JOptionPane.ERROR_MESSAGE);
            txtPass3.requestFocus();
        } else {
            
            login.setVisible(true);
            signup.setVisible(false);
            signup1.setVisible(false);
            forgot.setVisible(false);
            forgot1.setVisible(false);
            forgot2.setVisible(false);
            loader.setVisible(false);

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void lblImagePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_lblImagePropertyChange

    }//GEN-LAST:event_lblImagePropertyChange

    private void txtEmailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyReleased
        if (!txtEmail.getText().equals("")) {
            us.email = txtEmail.getText();
        } else {
            us.email = "Email";
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailKeyReleased

    private void txtUsernameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsernameKeyReleased
        if (!txtUsername.getText().equals("")) {
            us.username = txtUsername.getText();
        } else {
            us.username = "Username";
        }            // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameKeyReleased

    private void txtPass1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPass1KeyReleased
        if (!txtPass1.getText().equals("")) {
            us.password = txtPass1.getText();
        } else {
            us.password = "jPasswordField1";
        }         // TODO add your handling code here:
    }//GEN-LAST:event_txtPass1KeyReleased

    private void txtPass4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPass4KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPass4KeyTyped

    private void txtPass4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPass4KeyReleased
        if (!txtPass4.getText().equals("")) {
            us.password2 = txtPass4.getText();
        } else {
            us.password2 = "jPasswordField1";
        }         // TODO add your handling code here:
    }//GEN-LAST:event_txtPass4KeyReleased

    private void jComboBox1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox1KeyReleased

    }//GEN-LAST:event_jComboBox1KeyReleased

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed

    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void txtUnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUnameKeyReleased
 if(evt.getKeyCode()==10){
        pressLogin();
        
 btn_login.requestFocus();
   }      
    }//GEN-LAST:event_txtUnameKeyReleased

    private void txtPassKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPassKeyReleased
 
        if(evt.getKeyCode()==10){
        pressLogin();
        
 btn_login.requestFocus();
        } 
    }//GEN-LAST:event_txtPassKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button btn_login;
    public static javax.swing.JPanel forgot;
    public static javax.swing.JPanel forgot1;
    public static javax.swing.JPanel forgot2;
    private javax.swing.JLabel img_loader;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    public static javax.swing.JComboBox<String> jComboBox1;
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
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    public static javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    public static javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    public static javax.swing.JLabel lblImage;
    private javax.swing.JLabel lbl_loader;
    public static javax.swing.JPanel loader;
    public static javax.swing.JPanel login;
    private javax.swing.JPanel pnlBackground;
    public static javax.swing.JPanel signup;
    public static javax.swing.JPanel signup1;
    public static javax.swing.JTextField txtContactNumber;
    public static javax.swing.JTextField txtEmail;
    public static javax.swing.JTextField txtFname;
    private javax.swing.JTextField txtFname3;
    private javax.swing.JTextField txtFname4;
    public static javax.swing.JTextField txtLname;
    public static javax.swing.JTextField txtMname;
    public static javax.swing.JPasswordField txtPass;
    public static javax.swing.JPasswordField txtPass1;
    private javax.swing.JPasswordField txtPass2;
    private javax.swing.JPasswordField txtPass3;
    public static javax.swing.JPasswordField txtPass4;
    public static javax.swing.JTextField txtSuffix;
    private javax.swing.JTextField txtUname;
    public static javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
 public static ImageIcon ResizeImage(String imgPath) {
        ImageIcon MyImage = new ImageIcon(imgPath);
        Image img = MyImage.getImage();
        Image newImage = img.getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImage);
        return image;
    }
}
