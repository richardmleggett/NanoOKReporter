
package nanookreporter;

import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.PieSeries.PieSeriesRenderStyle;
import org.knowm.xchart.VectorGraphicsEncoder;
import org.knowm.xchart.style.Styler.ChartTheme;

public class WalkOutResults {
    private NanoOKReporterOptions options;
    private HashMap<String, Integer> independentMatches = new HashMap<String, Integer>();
    private HashMap<String, Integer> notIndependentMatches = new HashMap<String, Integer>();

    public WalkOutResults(NanoOKReporterOptions o) {
        options = o;
    }        
    
    public void addHit(String hit, boolean isIndependent, int overlap) {
        if (isIndependent) {
            int count = 0;
            Integer c = independentMatches.get(hit);
            
            if (c != null) {
                count = c.intValue();
            }
            
            count ++;
            
            independentMatches.put(hit, count);
        } else {
            int count = 0;
            Integer c = notIndependentMatches.get(hit);
            
            if (c != null) {
                count = c.intValue();
            }
            
            count ++;
            
            notIndependentMatches.put(hit, count);
        }
    }
    
    public void writeResults(NanoOKReporter reporter, String walkoutDir) {
        Map<String, Integer> sortedIndependentMatches = sortByValues(independentMatches);
        Map<String, Integer> sortedNotIndependentMatches = sortByValues(notIndependentMatches);        
        
        try {
            //PieChart independentPie = new PieChart(600,600);
            PieChart independentPie = new PieChartBuilder().width(600).height(450).title("Independent hits (overlap >= 50)").theme(ChartTheme.GGPlot2).build();
            Font f = independentPie.getStyler().getLegendFont();
            independentPie.getStyler().setLegendFont(f.deriveFont(Font.PLAIN, 12));
            //independentPie.getStyler().setDefaultSeriesRenderStyle(PieSeriesRenderStyle.Donut);
            //independentPie.getStyler().setDonutThickness(20);
            independentPie.getStyler().setLegendPadding(4);
            PrintWriter pw = new PrintWriter(new FileWriter(walkoutDir + File.separator + "independent_matches.txt")); 
            System.out.println("Writing independent matches...");
            pw.println("Species\tCount");
            //for (HashMap.Entry<String, Integer> entry : independentMatches.entrySet())
            int n = 0;
            for (Map.Entry<String, Integer> entry : sortedIndependentMatches.entrySet())
            {
                String hit = entry.getKey();
                int count = entry.getValue().intValue();
                independentPie.addSeries(hit, count);
                pw.println(hit + "\t" + count);
                n++;
                if (n > 10) {
                    break;
                }
            }
            pw.close();
            System.out.println("Plotting independent matches...");
            BitmapEncoder.saveBitmap(independentPie, walkoutDir + File.separator + "independent_matches", BitmapEncoder.BitmapFormat.PNG);
            VectorGraphicsEncoder.saveVectorGraphic(independentPie, walkoutDir + File.separator + "independent_matches", VectorGraphicsEncoder.VectorGraphicsFormat.PDF);
            
            //PieChart notIndependentPie = new PieChart(600,600);
            PieChart notIndependentPie = new PieChartBuilder().width(600).height(450).title("overlap < 50").theme(ChartTheme.GGPlot2).build();
            notIndependentPie.getStyler().setLegendFont(f.deriveFont(Font.PLAIN, 12));
            notIndependentPie.getStyler().setLegendPadding(4);
            pw = new PrintWriter(new FileWriter(walkoutDir + File.separator + "not_independent_matches.txt")); 
            System.out.println("Writing non-independent matches...");
            pw.println("Species\tCount");
            //for (HashMap.Entry<String, Integer> entry : notIndependentMatches.entrySet())
            for (Map.Entry<String, Integer> entry : sortedNotIndependentMatches.entrySet())
            {
                String hit = entry.getKey();
                int count = entry.getValue().intValue();
                notIndependentPie.addSeries(hit, count);
                pw.println(hit + "\t" + count);
            }
            pw.close();
            System.out.println("Plotting non-independent matches...");
            BitmapEncoder.saveBitmap(notIndependentPie, walkoutDir + File.separator + "not_independent_matches", BitmapEncoder.BitmapFormat.PNG);
            VectorGraphicsEncoder.saveVectorGraphic(notIndependentPie, walkoutDir + File.separator + "not_independent_matches", VectorGraphicsEncoder.VectorGraphicsFormat.PDF);
            
            reporter.getWalkoutPanel().storeWalkoutImages(walkoutDir + File.separator + "independent_matches.png", walkoutDir + File.separator + "not_independent_matches.png");
        } catch (Exception e) {
            System.out.println("Exception in WalkOutAnalyser:");
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Done");
    }
    
    public static HashMap sortByValues(HashMap map) { 
         List list = new LinkedList(map.entrySet());

         // Defined Custom Comparator here
         Collections.sort(list, new Comparator() {
              public int compare(Object o1, Object o2) {
                 return ((Comparable) ((Map.Entry) (o2)).getValue())
                    .compareTo(((Map.Entry) (o1)).getValue());
              }
         });

         // Here I am copying the sorted list in HashMap
         // using LinkedHashMap to preserve the insertion order
         HashMap sortedHashMap = new LinkedHashMap();
         for (Iterator it = list.iterator(); it.hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();
                sortedHashMap.put(entry.getKey(), entry.getValue());
         } 

         return sortedHashMap;
    }    
}
