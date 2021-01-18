/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package f;

import home.Home;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader; 
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.channels.FileLock;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import login.LoginPanel;
import loginsignup.LoginSignupPanel;



/**
 *
 * @author Clrkz
 */
public class Zz {
public static boolean login = false;
public static String key;
public static Connection con = null;
public static PreparedStatement pst = null;
public static ResultSet rs = null;
public static boolean noAdmin = false;

    /**
     * @param args the command line arguments
     */
 
    public static void main(String args[]) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String directory = System.getProperty("user.home"); 
String defaultFolder = "IMS"; 
        new File(directory+File.separator+defaultFolder).mkdirs();
        new File("company_files").mkdirs(); 
        new File("intern_files").mkdirs(); 
        
        if(!isFileshipAlreadyRunning()){
            UIManagers.getNewUI();
            JOptionPane.showMessageDialog(null, "System already running...");
            UIManagers.applyOldUI();
            System.out.println("System already running");
    System.exit(0);
    }
        

        
SystemConnection sc = new SystemConnection();
String fileName0 = "cdpo-data-00"; 
String fileName1 = "cdpo-data-01"; 
String fileName2 = "cdpo-data-02";  
String absolutePath0 = directory +File.separator +defaultFolder + File.separator + fileName0;    
String absolutePath1 = directory +File.separator +defaultFolder + File.separator + fileName1;   
String absolutePath2 = directory +File.separator +defaultFolder + File.separator + fileName2;   


File f = new File(absolutePath0);
if(f.exists() && f.isFile()){ 
    f = new File(absolutePath1);
    if(f.exists() && f.isFile()){
           f = new File(absolutePath2);
            if(f.exists() && f.isFile()){
                
                SystemWarning w = new SystemWarning();
                String sysID = Hardware4Win.getSerialNumber();
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.reset();
                md.update(sysID.getBytes());
                byte[] digest = md.digest();
                BigInteger bi = new BigInteger(1,digest);
                key = bi.toString(16);
                SystemInfo.systemid = key;
                con = SQLCon.ConnectDB();
                ////check admin start
                try{
                    String sql = "select * from users where u_role=1";
                    pst = con.prepareStatement(sql);
                    rs = pst.executeQuery();
                    if(rs.next()) {
                        noAdmin = false;
                    } else {
                        noAdmin = true;
                        
                        LoginPanel lp = new LoginPanel();
                        lp.firstOpen = true;
                        LoginSignupPanel l = new LoginSignupPanel();
                        l.show();
                    }
                }catch(Exception e){
                 //   //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator","Error 1002",JOptionPane.ERROR_MESSAGE);
                    
                }
                ///check admin stop
                if(noAdmin==false){
                    try{
                        
                        String sql = "select * from system where sn='"+key+"' and status=1";
                        pst = con.prepareStatement(sql);
                        rs = pst.executeQuery();
                        if (rs.next()) {
                            Load();
                        } else {
                            w.show();
                            w.jTextField1.setText(key);
                        }
                        
                    }catch(Exception e){
                        e.printStackTrace();
                        ////JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator","Error 1001",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }else{
     sc.show();
}
    }else{
     sc.show();
} 
}else{
     sc.show();
}
 
 
    


    }
    public static void Load(){
      UIManagers.applyOldUI();
        LoginSignupPanel l = new LoginSignupPanel();
        Home m = new Home();
        if(login){
           m.userRoleAccess();
           m.show();  
           m.jLabel74.setText("System ID: "+key);
        }else{
           l.show();     
        }
       
    }
    
     
    private static boolean isFileshipAlreadyRunning() {
// socket concept is shown at http://www.rbgrn.net/content/43-java-single-application-instance
// but this one is really great
try {
    String directory = System.getProperty("user.home");  
    String defaultFolder = "IMS";  
final File file = new File(directory+File.separator+defaultFolder+File.separator+"Clrkz.txt");
final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
final FileLock fileLock = randomAccessFile.getChannel().tryLock();
if (fileLock != null) {
Runtime.getRuntime().addShutdownHook(new Thread() {
public void run() {
try {
fileLock.release();
randomAccessFile.close();
file.delete();
} catch (Exception e) {
//log.error("Unable to remove lock file: " + lockFile, e);
}
}
});
return true;
}
} catch (Exception e) {
// log.error("Unable to create and/or lock file: " + lockFile, e);
}
return false;
}
    
    public static void ClrkzAlgo()  throws UnsupportedEncodingException, NoSuchAlgorithmException {
                        
                SystemWarning w = new SystemWarning();
                String sysID = Hardware4Win.getSerialNumber();
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.reset();
                md.update(sysID.getBytes());
                byte[] digest = md.digest();
                BigInteger bi = new BigInteger(1,digest);
                key = bi.toString(16);
                SystemInfo.systemid = key;
                con = SQLCon.ConnectDB();
                ////check admin start
                try{
                    String sql = "select * from users where u_role=1";
                    pst = con.prepareStatement(sql);
                    rs = pst.executeQuery();
                    if(rs.next()) {
                        noAdmin = false;
                    } else {
                        noAdmin = true;
                        
                        LoginPanel lp = new LoginPanel();
                        lp.firstOpen = true;
                        LoginSignupPanel l = new LoginSignupPanel();
                        l.show();
                    }
                }catch(Exception e){
                 //JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator","Error 1002",JOptionPane.ERROR_MESSAGE);
                     SystemConnection sc = new SystemConnection();
          sc.show();
                }
                ///check admin stop
                if(noAdmin==false){
                    try{
                        
                        String sql = "select * from system where sn='"+key+"' and status=1";
                        pst = con.prepareStatement(sql);
                        rs = pst.executeQuery();
                        if (rs.next()) {
                            Load();
                        } else {
                            w.show();
                            w.jTextField1.setText(key);
                        }
                        
                    }catch(Exception e){
                        e.printStackTrace();
                        ////JOptionPane.showMessageDialog(null, "Error has occured...\nContact your administrator","Error 1001",JOptionPane.ERROR_MESSAGE);
                    }
                }
    }
}
