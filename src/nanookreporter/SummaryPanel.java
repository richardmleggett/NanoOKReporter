package nanookreporter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.PieSeries.PieSeriesRenderStyle;
import org.knowm.xchart.VectorGraphicsEncoder;
import org.knowm.xchart.style.Styler;

public class SummaryPanel extends JPanel {
    private NanoOKReporterOptions options = null;
    private BufferedImage pieImage = null;
    
    public SummaryPanel() {
        setBackground(Color.WHITE);
    }
    
    public void setOptions(NanoOKReporterOptions o) {
        options = o;
    }

    public Dimension getPreferredSize() {
        if (pieImage != null) {
            return new Dimension(pieImage.getWidth(), pieImage.getHeight());
        } else {
            return new Dimension(2000, 2000);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);   
        
        if (pieImage != null) {
            g.drawImage(pieImage, 0, 0, null);
        }      
    }        
    
    public void processTaxonomy(NanoOKSample s) {
        Map<String, Integer> leafNodes = options.getTaxonomy().getLeafNodes();
        String imageFilename = s.getSampleDir() + File.separator + "summary_taxon";
 
        try {
            PieChart speciesPie = new PieChartBuilder().width(800).height(600).title("Species").theme(Styler.ChartTheme.GGPlot2).build();
            Font f = speciesPie.getStyler().getLegendFont();
            speciesPie.getStyler().setLegendFont(f.deriveFont(Font.PLAIN, 12));
            speciesPie.getStyler().setDefaultSeriesRenderStyle(PieSeriesRenderStyle.Donut);
            //independentPie.getStyler().setDonutThickness(20);
            speciesPie.getStyler().setLegendPadding(4);

            int n = 0;
            for (Map.Entry<String, Integer> entry : leafNodes.entrySet())
            {
                String id = entry.getKey();
                int count = entry.getValue().intValue();
                speciesPie.addSeries(id, count);
                //System.out.println(id + " = " + count);
                n++;
                if (n == 20) {
                    break;
                }
            }

            BitmapEncoder.saveBitmap(speciesPie, imageFilename, BitmapEncoder.BitmapFormat.PNG);
            VectorGraphicsEncoder.saveVectorGraphic(speciesPie, imageFilename, VectorGraphicsEncoder.VectorGraphicsFormat.PDF);
            pieImage = ImageIO.read(new File(imageFilename + ".png"));
        } catch (Exception e) {
            System.out.println("Exception in processTaxonomy:");
            e.printStackTrace();
            System.exit(1);
        }        
    }
}
