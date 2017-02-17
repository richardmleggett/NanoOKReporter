package nanookreporter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class AROMap {
    private static HashMap<String, String> names = new HashMap<String, String>();
    private static HashMap<String, String> descriptions = new HashMap<String, String>();
    
    public static void readMapFile(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = br.readLine();
            
            if (!line.startsWith("Accession")) {
                System.out.println("Error: ARO file not understood");
                System.exit(1);
            }
            
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length == 3) {
                    names.put(columns[0], columns[1]);
                    descriptions.put(columns[0], columns[2]);
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("BlastChunk exception");
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public static String getNameFromAccession(String a) {
        String n="";
        String name = names.get(a);
        if (name != null) {
            n = name;
        }
        return n;
    }

    public static String getDescriptionFromAccession(String a) {
        String d="";
        String desc = descriptions.get(a);
        if (desc != null) {
            d = desc;
        }
        return d;
    }
}
