package nanookreporter;

import java.awt.List;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class BlastChunkSet extends AbstractTableModel {
    private int chunkCounter = 0;
    private String directory;
    private String prefix;
    private String midfix;
    private int numberOfRows = 0;
    private int numberOfCols = 5;
    private ArrayList<BlastChunk> chunks = new ArrayList<BlastChunk>();
    private HashMap<String, Integer> idCounts = new HashMap<String, Integer>();
    private HashMap<String, Integer> sortedCounts;
    private ArrayList<String> ids = new ArrayList<String>();
    private ArrayList<String> aros = new ArrayList<String>();
    private ArrayList<String> desc = new ArrayList<String>();
    private int counts[] = new int[100000];

    public BlastChunkSet(String d, String p, String m) {
        directory = d;
        prefix = p;
        midfix = m;
    }
    
    public void scanForChunks() {
        boolean found = false;
        int c = chunkCounter;
        
        do {
            String filename = directory + File.separator + prefix + "_" + c + "_" + midfix + ".txt";
            File f = new File(filename);
            if (f.exists()) {
                //System.out.println("Found " + filename);
                chunks.add(new BlastChunk(filename));
                found = true;
                chunkCounter = c;                
            } else {
                found = false;
                System.out.println("Can't find " + filename);
            }
            c++;
        } while (found);        
    }
    
    public int getNumberOfChunks() {
        return chunkCounter;
    }
    
    public void countHits(int endChunk) {
        if (endChunk > chunkCounter) {
            System.out.println("Warning: end chunk is greater than number of chunks!");
            endChunk = chunkCounter;
        }
        
        for (int i=0; i<endChunk; i++) {
            for (int j=0; j<chunks.get(i).getNumberOfAlignments(); j++) {
                BlastAlignment ba = chunks.get(i).getAlignment(j);
                String id = ba.getSubjectId();
                int count = 1;
                
                if (idCounts.containsKey(id)) {
                    count=idCounts.get(id) + 1;
                }
                
                idCounts.put(id, count+1);
            }
        }
    }
    
    public void updateTable(JTable table) {
        LinkedList list = new LinkedList(idCounts.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o2, Object o1) {
                return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        // Here I am copying the sorted list in HashMap
        // using LinkedHashMap to preserve the insertion order
        numberOfRows = 0;
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = entry.getKey().toString();
            int aroPos = key.indexOf("|ARO:");
            String aro = "";
            
            if (aroPos > 0) { 
                String aroStart = key.substring(aroPos+1);
                aro = aroStart.substring(0, aroStart.indexOf('|'));
            }
            
            ids.add(numberOfRows, (String) entry.getKey());
            aros.add(numberOfRows, AROMap.getNameFromAccession(aro));
            desc.add(numberOfRows, AROMap.getDescriptionFromAccession(aro));
            counts[numberOfRows] = (int) entry.getValue();
            numberOfRows++;
        }

        System.out.println("Table updated with "+numberOfRows+ " rows");
    }

    @Override
    public int getRowCount() {
       return numberOfRows;
    }

    @Override
    public int getColumnCount() {
        return numberOfCols;
    }

    @Override
    public String getColumnName(int columnIndex) {
        String name = "";
        
        switch(columnIndex) {
            case 0:
                name="Rank";
                break;
            case 1:
                name="Count";
                break;
            case 2:
                name="Id";
                break;
            case 3:
                name="Accession";
                break;
            case 4:
                name="Description";
                break;
        }
        
        return name;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0:
                return rowIndex+1;
            case 1:
                return Integer.toString(counts[rowIndex]);
            case 2:
                return ids.get(rowIndex);
            case 3:
                return aros.get(rowIndex);
            case 4:
                return desc.get(rowIndex);
        }
        
        return null;
    }
}
