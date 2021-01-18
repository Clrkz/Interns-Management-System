import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class Main extends JFrame {
  public Main() throws HeadlessException {
    setSize(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new FlowLayout(FlowLayout.LEFT));

    DocumentFilter filter = new UppercaseDocumentFilter();

    JTextField firstName = new JTextField();
    firstName.setPreferredSize(new Dimension(100, 20));
    ((AbstractDocument) firstName.getDocument()).setDocumentFilter(filter);

    JTextField lastName = new JTextField();
    lastName.setPreferredSize(new Dimension(100, 20));
    ((AbstractDocument) lastName.getDocument()).setDocumentFilter(filter);

    add(firstName);
    add(lastName);
  }

  public static void main(String[] args) {
    new Main().setVisible(true);
  }

}

class UppercaseDocumentFilter extends DocumentFilter {
  public void insertString(DocumentFilter.FilterBypass fb, int offset, String text,
      AttributeSet attr) throws BadLocationException {

    fb.insertString(offset, text.toUpperCase(), attr);
  }

  public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text,
      AttributeSet attrs) throws BadLocationException {

    fb.replace(offset, length, text.toUpperCase(), attrs);
  }
}