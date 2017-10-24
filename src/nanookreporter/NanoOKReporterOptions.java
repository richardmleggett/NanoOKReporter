
package nanookreporter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class NanoOKReporterOptions {
    private Taxonomy taxonomy;
    private String prefsFile = System.getProperty("user.home") + File.separator + ".nanook_reporter.prefs";
    private String lastSample = "";
    
    public NanoOKReporterOptions() {
        taxonomy = new Taxonomy("/Users/leggettr/Documents/Projects/BAMBI/taxdump/nodes.dmp",
                                "/Users/leggettr/Documents/Projects/BAMBI/taxdump/names.dmp",
                                "/Users/leggettr/Documents/Projects/BAMBI/accession2taxid/nucl_gb.accession2taxid");
        this.readPrefs();
    }
    
    public Taxonomy getTaxonomy() {
        return taxonomy;
    }
    
    public void readPrefs() {
        try {
            File f = new File(prefsFile );
            
            if (f.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(f ));            
                String line;

                while ((line = br.readLine()) != null) {
                    if (line.startsWith("LastSample:")) {
                        lastSample = line.substring(11);
                    }
                }
                br.close();
            }
        } catch (Exception e) {
            System.out.println("BlastChunk exception");
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public String getLastSample() {
        return lastSample;
    }
    
    public void storeLastLoadedSample(String s) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(prefsFile)); 
            pw.write("LastSample:"+s);
            pw.close();
        } catch (IOException e) {
            System.out.println("writeCardSummaries exception:");
            e.printStackTrace();
            System.exit(1);
        }
        
    }
}
