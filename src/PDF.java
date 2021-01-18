
import java.util.ResourceBundle;
import javax.swing.JSplitPane;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.PropertiesManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Clrkz
 */
public class PDF {
    public static void main(String args[]){
        pdfviewerICE("1.pdf");
    }
   public static void pdfviewerICE(String path) {
    String filePath = path;

    // build a controller
    SwingController controller = new SwingController();

    // Build a SwingViewFactory configured with the controller
    SwingViewBuilder factory = new SwingViewBuilder(controller);
    PropertiesManager properties = new PropertiesManager(
        System.getProperties(),
        ResourceBundle.getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE));

    properties.set(PropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, "1.75");

    JSplitPane jSplitPane_PDF = factory.buildUtilityAndDocumentSplitPane(true);
    controller.openDocument(filePath);
} 
}
