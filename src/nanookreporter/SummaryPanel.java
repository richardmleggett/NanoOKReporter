package nanookreporter;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.bg.RectangleBackground;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.ColorPalette;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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
         
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(300);
        frequencyAnalyzer.setMinWordLength(4);
        //frequencyAnalyzer.setStopWords(loadStopWords());        

        final List<WordFrequency> wordFrequencies = new ArrayList(); //frequencyAnalyzer.load("text/datarank.txt");
        
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
              
                if (n <20) {
                    speciesPie.addSeries(id, count);
                }
                
                wordFrequencies.add(new WordFrequency(id, count));

                n++;
                //if (n == 20) {
                //    break;
                //}
            }

            BitmapEncoder.saveBitmap(speciesPie, imageFilename, BitmapEncoder.BitmapFormat.PNG);
            VectorGraphicsEncoder.saveVectorGraphic(speciesPie, imageFilename, VectorGraphicsEncoder.VectorGraphicsFormat.PDF);
            pieImage = ImageIO.read(new File(imageFilename + ".png"));


            final Dimension dimension = new Dimension(800, 800);
            WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
            wordCloud.setPadding(2);
            wordCloud.setBackground(new CircleBackground(400));
            wordCloud.setBackgroundColor(Color.WHITE);
            wordCloud.setKumoFont(new KumoFont(new Font("Arial", Font.PLAIN, 20)));
            wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
            //wordCloud.setFontScalar(new LinearFontScalar(10, 40));
            
            wordCloud.setFontScalar(new SqrtFontScalar(10, 40));
            wordCloud.build(wordFrequencies);
            wordCloud.writeToFile(imageFilename + "_wordcloud.png");
        } catch (Exception e) {
            System.out.println("Exception in processTaxonomy:");
            e.printStackTrace();
            System.exit(1);
        }        
    }
}
