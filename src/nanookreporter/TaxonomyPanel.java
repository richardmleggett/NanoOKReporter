package nanookreporter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class TaxonomyPanel extends JPanel {
    private NanoOKReporterOptions options = null;
    
    public TaxonomyPanel() {
        setBackground(Color.WHITE);
    }
    
    public void setOptions(NanoOKReporterOptions o) {
        options = o;
    }

    public Dimension getPreferredSize() {
        if (options == null) {
            return new Dimension(2000, 2000);
        } else {
            return new Dimension(options.getTaxonomy().getPlotWidth(), options.getTaxonomy().getPlotHeight());
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);       
        
        if (options != null) {
            options.getTaxonomy().drawTree(g);
        }
    }      
}
