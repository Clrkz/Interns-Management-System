/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package f;

import javax.swing.LookAndFeel;

/**
 *
 * @author Clrkz
 */
public class UIManagers {
       static LookAndFeel old = javax.swing.UIManager.getLookAndFeel();
    public static void getNewUI(){
                try {
                    javax.swing.UIManager.setLookAndFeel(
                            javax.swing.UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }
 public static void applyOldUI(){
            try {
                    javax.swing.UIManager.setLookAndFeel(old);
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }
}