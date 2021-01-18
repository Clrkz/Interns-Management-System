

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.jfree.chart.*;
import org.jfree.data.general.DefaultPieDataset;
import com.jtattoo.plaf.mcwin.*;

//Anugrah Bagus Susilo
public class demoPieChart {

  public static void main(String[] args) throws UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(new McWinLookAndFeel());
		// TODO Auto-generated method stub
		DefaultPieDataset data=new DefaultPieDataset();
		
		data.setValue("Java", 40);
		data.setValue("C++",30);
		data.setValue("PHP",15);
		data.setValue("Python",10);
		data.setValue("Others",5);
		
		JFreeChart chart=ChartFactory.createPieChart("Daftar Bahasa Pemrograman Terpopuler",data,true,true,false);
		ChartFrame frame=new ChartFrame("demo Pie Chart",chart);

		frame.setSize(450,300);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

	}

}
