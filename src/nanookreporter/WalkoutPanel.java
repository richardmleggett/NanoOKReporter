package nanookreporter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class WalkoutPanel extends JPanel {
    private NanoOKReporterOptions options = null;
    private BufferedImage independentPieImage = null;
    private BufferedImage nonIndependentPieImage = null;

    
    public WalkoutPanel() {
        setBackground(Color.WHITE);
    }
    
    public void setOptions(NanoOKReporterOptions o) {
        options = o;
    }

    public Dimension getPreferredSize() {
        if (independentPieImage != null) {
            return new Dimension(independentPieImage.getWidth() + nonIndependentPieImage.getWidth(), independentPieImage.getHeight());
        } else {
            return new Dimension(2000, 2000);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);   
        
        if (independentPieImage != null) {
            g.drawImage(independentPieImage, 0, 0, null);
            g.drawImage(nonIndependentPieImage, independentPieImage.getWidth(), 0, null);
        }      
    }        

    public void storeWalkoutImages(String independentImage, String nonIndependentImage) {
        try {
            independentPieImage = ImageIO.read(new File(independentImage));
            nonIndependentPieImage = ImageIO.read(new File(nonIndependentImage));
        } catch (Exception e) {
            System.out.println("storeWalkoutImages exception::");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
