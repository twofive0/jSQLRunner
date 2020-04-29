/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsqlrunner;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.UIManager;

/**
 *
 * @author Devin
 */
public class JSQLRunner {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
   
        try {
                if (OSValidator.isWindows())
                {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); 
                }
                else
                {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel"); 
                }
                
            } catch (Exception ex) { 
              ex.printStackTrace(); 
            }
       frmMain mainWin = new frmMain(); 
       java.net.URL url = ClassLoader.getSystemResource("jsqlrunner/images/system-log-out.png");
       Toolkit kit = Toolkit.getDefaultToolkit();
       Image img = kit.createImage(url);
       mainWin.setIconImage(img);
       mainWin.setVisible(true);
    }
}
