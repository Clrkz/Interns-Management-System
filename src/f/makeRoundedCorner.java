/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package f;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 *
 * @author Clrkz
 */
public class makeRoundedCorner {
    /*
    public static void main(String[] args) throws IOException {
BufferedImage icon = ImageIO.read(new File("icon.png"));
BufferedImage rounded = makeRoundedCorner(icon, 20);
ImageIO.write(rounded, "png", new File("icon.rounded.png"));
}
*/
    
    
    public static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) {
int w = image.getWidth();
int h = image.getHeight();
BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

Graphics2D g2 = output.createGraphics();

// This is what we want, but it only does hard-clipping, i.e. aliasing
// g2.setClip(new RoundRectangle2D ...)

// so instead fake soft-clipping by first drawing the desired clip shape
// in fully opaque white with antialiasing enabled...
g2.setComposite(AlphaComposite.Src);
g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
g2.setColor(Color.WHITE);
g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));

// ... then compositing the image on top,
// using the white shape from above as alpha source
g2.setComposite(AlphaComposite.SrcAtop);
g2.drawImage(image, 0, 0, null);

g2.dispose();

return output;
}
}
