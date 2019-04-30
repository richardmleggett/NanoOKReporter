package nanookreporter;

import java.awt.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
    public final static int TYPE_CARD = 1;
    public final static int TYPE_NT = 2;
    private int chunkCounter = 0;
    private int selectedChunk = 0;
    private String directory;
    private String prefix;
    private String midfix;
    private int numberOfRows = 0;
    private int numberOfCols = 5;
    private ArrayList<BlastChunk> chunks = new ArrayList<BlastChunk>();
    private HashMap<String, Integer> idCounts = new HashMap<String, Integer>();
    private HashMap<String, String> titles = new HashMap<String, String>();
    private HashMap<String, Integer> sortedCounts;
    private ArrayList<String> ids = new ArrayList<String>();
    private ArrayList<String> aros = new ArrayList<String>();
    private ArrayList<String> desc = new ArrayList<String>();
    private int counts[] = new int[100000];
    private int chunkType = TYPE_CARD;
    private String[] columnNames = {"1", "2", "3", "4", "5"};
    private boolean doneScan = false;
    private NanoOKReporterOptions options;
    private int lastScan = 0;
    private int taxonomyTreeId;

    public BlastChunkSet(NanoOKReporterOptions o, String d, String p, String m, int i) {
        options = o;
        directory = d;
        prefix = p;
        midfix = m;
        taxonomyTreeId = i;
        
        if (midfix.equals("blastn_card")) {
            numberOfCols = 5;
            chunkType = TYPE_CARD;
            columnNames[0] = "Rank";
            columnNames[1] = "Count";
            columnNames[2] = "Id";
            columnNames[3] = "Name";
            columnNames[4] = "Description";
        } else {
            numberOfCols = 4;
            chunkType = TYPE_NT;
            columnNames[0] = "Rank";
            columnNames[1] = "Count";
            columnNames[2] = "Id";
            columnNames[3] = "Description";
        }
    }
    
    public void scanForChunks(NanoOKReporter nor) {
        boolean found = false;
        int c = chunkCounter;
        
        // We always re-scan the last chunk
        if (chunks.size() > 0) {
            System.out.println("Removing " + chunkCounter);
            chunks.remove(chunkCounter);
        }
        
        do {
            String filename = directory + File.separator + prefix + "_" + c + "_" + midfix + ".txt";
            File f = new File(filename);
            if (f.exists()) {
                System.out.println("Found " + filename);
                nor.setStatus("File " + filename);
                chunks.add(new BlastChunk(options, filename, taxonomyTreeId));
                found = true;
                chunkCounter = c;                
            } else {
                System.out.println("Can't find "+filename);
                found = false;
            }
            c++;
            
            if (options.getChunksToLoad() != 0) {
                if (c >= (lastScan + options.getChunksToLoad())) {
                    lastScan = c;
                    break;
                }
            }
            
            // DEBUG
            //if (c >= (lastScan + 10)) {
            //    lastScan = c;
            //    break;
            //}
            //if ((doneScan == false) && (c == 10)) {
            //    break;
            //}
        } while (found);        
        lastScan = c;
        
        selectedChunk = chunkCounter;
        doneScan = true;
        //System.out.println("chunkCounter "+chunkCounter);
    }
    
    public String getPrefix() {
        return prefix;
    }

    public int getNumberOfChunks() {
        return chunkCounter+1;
    }
    
    public int getLastChunkNumber() {
        return chunkCounter;
    }
    
    public int getSelectedChunk() {
        return selectedChunk;
    }
    
    public void setSelectedChunk(int s) {
        selectedChunk = s;
    }
    
    public BlastChunk getChunk(int i) {
        return chunks.get(i);
    }
    
    public void countHits(int endChunk, BlastMatchCriteria bmc) {
        if (endChunk > chunkCounter) {
            System.out.println("Warning: end chunk is greater than number of chunks!");
            endChunk = chunkCounter;
        }
        
        if (chunks.size() == 0) {
            //System.out.println("Error: No chunks");
            return;
        }
        
        if (selectedChunk < endChunk) {
            endChunk = selectedChunk;
        }
        
        idCounts.clear();
        
        //System.out.println("Counting hits...");
        for (int i=0; i<=endChunk; i++) {
            for (int j=0; j<chunks.get(i).getNumberOfAlignments(); j++) {
                BlastAlignment ba = chunks.get(i).getTopHit(j);
                
                if ((ba.getPercentIdentity() >= bmc.getMinId()) && 
                    (ba.getLength() >= bmc.getMinLength()))
                {
                    String id = ba.getSubjectId();
                    String title = ba.getSubjectTitle();
                    int count = 0;

                    if (idCounts.containsKey(id)) {
                        count=idCounts.get(id);
                    } else {
                        titles.put(id, title);
                    }

                    idCounts.put(id, count+1);
                }
            }
        }
        //System.out.println("Done " + endChunk);
    }

    public void countHitsCARD(int endChunk) {
        double minId = options.getLCAMinID();
        int minLength = options.getLCAMinLength();
        
        System.out.println("Counting...");
        
        if (endChunk > chunkCounter) {
            System.out.println("Warning: end chunk is greater than number of chunks!");
            endChunk = chunkCounter;
        }
        
        if (chunks.size() == 0) {
            //System.out.println("Error: No chunks");
            return;
        }
        
        if (selectedChunk < endChunk) {
            endChunk = selectedChunk;
        }
        
        idCounts.clear();
        
        //System.out.println("Counting hits...");
        for (int i=0; i<=endChunk; i++) {
            for (int j=0; j<chunks.get(i).getNumberOfAlignments(); j++) {
                BlastAlignment ba = chunks.get(i).getTopHit(j);
                if ((ba.getPercentIdentity() >= minId) && (ba.getLength() >= minLength)) {
                    String id = ba.getSubjectId();
                    String title = ba.getSubjectTitle();
                    int count = 0;

                    if (idCounts.containsKey(id)) {
                        count=idCounts.get(id);
                    } else {
                        titles.put(id, title);
                    }

                    idCounts.put(id, count+1);
                }
            }
        }
        //System.out.println("Done " + endChunk);
    }
    
    public void writeSummaryFile(String filename) {
        LinkedList list = new LinkedList(idCounts.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o2, Object o1) {
                return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
            }
        });
        
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(filename)); 

            pw.println("Rank,Count,ID,Name,Description");
            
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
                
                pw.println((numberOfRows+1) + "," +
                           entry.getValue() + "," +
                           entry.getKey() + "," +
                           AROMap.getNameFromAccession(aro) + "," + 
                           AROMap.getDescriptionFromAccession(aro));

                numberOfRows++;
            }

            System.out.println("File " + filename + " written with "+numberOfRows+ " rows");

            pw.close();
        } catch (IOException e) {
            System.out.println("writeSummaryFile exception:");
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public void updateTableCard(JTable table) {
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

        System.out.println("CARD Table updated with "+numberOfRows+ " rows");
    }
    
    public void updateTableNt(JTable table) {
        //System.out.println("Updating...");
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
            ids.add(numberOfRows, (String) entry.getKey());
            counts[numberOfRows] = (int) entry.getValue();
            numberOfRows++;
            if (numberOfRows > 1000) {
                break;
            }
        }

        System.out.println("Table updated with "+numberOfRows+ " rows");
    }
    
    public void updateTable(JTable table) {
        if (chunkType == TYPE_CARD) {
            updateTableCard(table);
        } else {
            updateTableNt(table);
       }
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
        return columnNames[columnIndex];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (chunkType == TYPE_CARD) {
            return getValueAtCard(rowIndex, columnIndex);
        }
        
        return getValueAtNt(rowIndex, columnIndex);
    }
    
    public Object getValueAtCard(int rowIndex, int columnIndex) {
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

    public Object getValueAtNt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0:
                return rowIndex+1;
            case 1:
                return Integer.toString(counts[rowIndex]);
            case 2:
                return ids.get(rowIndex);
            case 3:
                return titles.get(ids.get(rowIndex));
        }
        
        return null;
    }
    
    public long getChunkLastModified(int c) {
        if (chunks.size() == 0) {
            return 0;
        }
        return chunks.get(c).getLastModified();
    }

}
