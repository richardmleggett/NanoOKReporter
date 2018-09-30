package nanookreporter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import static nanookreporter.WalkOutResults.sortByValues;

public class Taxonomy {
    private int circleDiameter = 32;
    private int circleRadius = circleDiameter / 2;
    private final static int rowHeight = 32;
    private final static int colWidth = 50;
    private Hashtable<Long, Node> nodesById = new Hashtable();
    private Hashtable<Long, String> nameById = new Hashtable();
    private Hashtable<String, Long> idByName = new Hashtable();
    private Hashtable<String, Long> accessionToTaxon = new Hashtable();
    private Node unclassifiedNode = new Node(0L);
    private long humanId;
    private long bacteriaId;
    private long lambdaId;
    private long vectorsId;
    private long ecoliId;
    private int maxRow = 0;
    private int maxColumn = 0;
    private int plotWidth = 2000;
    private int plotHeight = 2000;
    private int nTrees = 0;
    private boolean warningId = false;
    private int totalAssignedReads = 0;
    private int assignedThreshold = 2;
    private int otherCount = 0;
    private int totalCountCheck = 0;
    private Rectangle bounds;
    
    public Taxonomy(String nodesFilename, String namesFilename) {
        try {
            System.out.println("Reading "+nodesFilename);
            BufferedReader br = new BufferedReader(new FileReader(nodesFilename));
            String line;                
            while ((line = br.readLine()) != null) {
                String[] fields = line.split("\t");                
                long id = Long.parseLong(fields[0]);
                long parentId = Integer.parseInt(fields[2]);
                String rank = fields[4];
                Node n = nodesById.get(id);
                
                if (n == null) {               
                    n = new Node(id);
                    nodesById.put(id, n);
                }
                
                n.setRank(rank);
                
                if (parentId != id) {
                    n.setParent(parentId);   
                    linkParent(n, parentId);                
                }
            }
            br.close();
            
            System.out.println("Reading "+namesFilename);
            br = new BufferedReader(new FileReader(namesFilename));
            while ((line = br.readLine()) != null) {
                String[] fields = line.split("\t");
                if (fields[6].equals("scientific name")) {
                    long id = Long.parseLong(fields[0]);
                    nameById.put(id, fields[2]);        
                    idByName.put(fields[2], id);

                    if (fields[2].equals("Homo sapiens")) {
                        humanId = id;
                        System.out.println("Got human ID");
                    }

                    if (fields[2].equals("Bacteria")) {
                        bacteriaId = id;
                        System.out.println("Got bacteria ID");
                    }
                    
                    if (fields[2].equals("Escherichia coli")) {
                        ecoliId = id;
                    }
                    
                    if (fields[2].equals("Escherichia virus Lambda")) {
                        lambdaId = id;
                        System.out.println("Warning: need to check classification to lambda");
                    }
                    
                    if (fields[2].equals("vectors")) {
                        vectorsId = id;
                        System.out.println("Warning: need to check classification to cloning vectors");
                    }                    
                } else if (fields[6].equals("synonym")) {
                    long id = Long.parseLong(fields[0]);
                    idByName.put(fields[2], id);                    
                }
            }
            br.close();
            System.out.println("Processed "+nameById.size()+" nodes");
            
            if ((lambdaId == 0) || (humanId == 0) || (vectorsId == 0) || (bacteriaId == 0) || (ecoliId == 0)) {
                System.out.println("Didn't get one of the Ids");
                System.exit(0);
            }
            
//            System.out.println("Reading "+mapFilename);
//            br = new BufferedReader(new FileReader(mapFilename));
//            br.readLine();
//            while ((line = br.readLine()) != null) {
//                String[] fields = line.split("\t");
//                String accession = fields[0];
//                long taxonId = Long.parseLong(fields[2]);
//                //long gi = Long.parseLong(fields[3]);
//                accessionToTaxon.put(accession, taxonId);
//            }
//            br.close();            
        } catch (Exception e) {
            System.out.println("Taxonomy exception");
            e.printStackTrace();
            System.exit(1);
        }
        
        nameById.put(0L, "unclassified");

        displayMemory();
        
        //System.out.println("Node 2 is "+getNameFromTaxonId(2L));
        //outputTaxonIdsFromNode(2L, "/Users/leggettr/Documents/Databases/taxonomy/bacteria_taxonids.txt");
        //System.out.println("Node 2157 is "+getNameFromTaxonId(2157L));
        //outputTaxonIdsFromNode(2157L, "/Users/leggettr/Documents/Databases/taxonomy/archea_taxonids.txt");
        //System.out.println("Node 10239 is "+getNameFromTaxonId(10239L));        
        //outputTaxonIdsFromNode(10239L, "/Users/leggettr/Documents/Databases/taxonomy/viruses_taxonids.txt");        
        //System.exit(1);
        
//        try {
//            FileOutputStream fos = new FileOutputStream("~/Desktop/acc2tax.ser");
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//            oos.writeObject(accessionToTaxon);
//            oos.close();
//        } catch (Exception e) {
//            System.out.println("Exception trying to write object:");
//            e.printStackTrace();
//        }
        
        //this.dumpTaxonomyFromName("Arabidopsis thaliana");
        //System.out.println("");
        //this.dumpTaxonomyFromName("Enterobacteriaceae bacterium strain FGI 57");
        
        //System.out.println("Enterobacter aerogenes = " + this.getTaxonIdFromName("Enterobacter aerogenes"));
        //System.out.println("" + );
    }
    
    public void outputTaxonIdsFromNode(long id, String filename) {
        System.out.println("Writing "+filename);
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(filename)); 
            outputNodeIdAndChildrenToFile(getNodeFromTaxonId(id), pw);
            pw.close();
        } catch (IOException e) {
            System.out.println("outputTaxonIdsFromNode exception:");
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public void outputNodeIdAndChildrenToFile(Node n, PrintWriter pw) {
        pw.println(n.getId());
        ArrayList<Node> children = n.getChildren();
        for (int i=0; i<children.size(); i++) {
            Node c = children.get(i);
            outputNodeIdAndChildrenToFile(c, pw);
        }
    }

    public void displayMemory() {
        System.out.println("Total memory: "+ (Runtime.getRuntime().totalMemory() / (1024*1024)) + " Mb");
        System.out.println(" Free memory: "+ (Runtime.getRuntime().freeMemory() / (1024*1024)) + " Mb");
        System.out.println(" Used memory: "+ ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024*1024)) + " Mb");
    }
        
    private void linkParent(Node n, long parentId) {
        Node parentNode = nodesById.get(parentId);
        if (parentNode == null) {
            parentNode = new Node(parentId);
            nodesById.put(parentId, parentNode);
        }
        parentNode.addChild(n);        
    }
    
    public String getNameFromTaxonId(Long id) {
        return nameById.get(id);
    }
    
    public Long getTaxonIdFromName(String name) {
        Long id = idByName.get(name);
        return id;
    }
    
    public Node getNodeFromTaxonId(Long id) {
        return nodesById.get(id);
    }
    
    public Node getNodeFromName(String name) {
        Long id = getTaxonIdFromName(name);
        Node n = null;
        if (id != null) {
            n = getNodeFromTaxonId(id);
        }
        return n;
    }
    
    public String getTaxonomyStringFromId(Long id) {
        String taxonString = "";

        if (id != null) {
            Node n = getNodeFromTaxonId(id);

            if (n != null) {
                while (n != null) {
                    String t = getNameFromTaxonId(n.getId());
                    if (t != null) {
                        if (taxonString.length() > 0) {
                            taxonString = t + "," + taxonString;
                        } else {
                            taxonString = t;
                        }
                    }
                    Long parentId = n.getParent();
                    if ((parentId != null) && (parentId != n.getId())) {
                        n = getNodeFromTaxonId(parentId);
                    } else {
                        n = null;
                    }
                }
            }
        }
        
        return taxonString;
    }

    public ArrayList<Long> getTaxonIdPathFromId(Long id) {
        ArrayList<Long> nodes = new ArrayList<Long>();
        
        if (id == 0) {
            nodes.add(0L);
        } else if (id != null) {
            Node n = getNodeFromTaxonId(id);

            while (n != null) {
                nodes.add(n.getId());
                Long parentId = n.getParent();
                if ((parentId != null) && (parentId != n.getId())) {
                    n = getNodeFromTaxonId(parentId);
                } else {
                    n = null;
                }
            }
        }
        
        return nodes;
    }    
    
    public String getTaxonomyStringFromName(String s) {
        Long id = getTaxonIdFromName(s);
        return getTaxonomyStringFromId(id);
    }
    
    public String dumpTaxonomyFromId(Long id) {
        String taxonString = "";

        if (id != null) {
            Node n = getNodeFromTaxonId(id);

            if (n != null) {
                while (n != null) {
                    String t = getNameFromTaxonId(n.getId());
                    if (t != null) {
                        System.out.println(t + "\t" + n.getRankString());
                    }
                    Long parentId = n.getParent();
                    if ((parentId != null) && (parentId != n.getId())) {
                        n = getNodeFromTaxonId(n.getParent());
                    } else {
                        n = null;
                    }
                }
            }
        }
        
        return taxonString;
    }

    public String dumpTaxonomyFromName(String s) {
        Long id = getTaxonIdFromName(s);
        return dumpTaxonomyFromId(id);
    }
    
    private void countRead(Long id) {
        Node n = getNodeFromTaxonId(id);
        
        totalCountCheck++;
        
        if (n == null) {
            unclassifiedNode.incrementAssigned();
        } else {
            n.incrementAssigned();

            do {
                Long parent = n.getParent();
                n.incrementSummarised();

                if (id != parent) {
                    id = parent;
                } else {
                    id = null;                
                }

                if (id != null) {
                    n = getNodeFromTaxonId(id);
                }
            } while (id != null);
        }
    }
    
    public Long parseTaxonomyToId(String s) {
        Long id = null;
        String[] parts = s.split("(,|\\s)");       
        String species = s;
        String genus = s;
        String triplet = s;
        
        if (parts[0].equals("PREDICTED:") || (parts[0].equals("Uncultured")) || (parts[0].equals("Synthetic"))) {
            if (parts.length >= 4) {
                species = parts[1] + " " + parts[2];
                genus = parts[1];
                triplet = parts[1] + " " + parts[2] + " " + parts[3];
            }
        } else {
            if (parts.length >= 3) {
                species = parts[0] + " " + parts[1];
                genus = parts[0];
                triplet = parts[0] + " " + parts[1] + " " + parts[2];
            }
        }
              
        id = getTaxonIdFromName(species);
        if (id == null) {
            id = getTaxonIdFromName(genus);
            if (id == null) {
                id = getTaxonIdFromName(triplet);
                if (id == null) {
                    if (species.startsWith("Human")) {
                        id = humanId;
                    } else if (species.equals("Artificial cloning")) {
                        id = vectorsId;
                    } else if (species.startsWith("Cloning vectors") || (species.startsWith("Cloning vector"))) {
                        id = vectorsId;
                    } else if (species.equalsIgnoreCase("Unidentified bacterium")) {
                        id = bacteriaId;
                    } else if (s.contains("vector lambda") || (species.startsWith("Lambda genome"))) {
                        id = lambdaId;
                    } else if (s.startsWith("E.coli")) {
                        id = ecoliId;
                    } else {
                        //System.out.println("Couldn't parse "+s+" ("+species+")");
                        //unclassifiedNode.incrementAssigned();
                    }
                }
            }
        }       
        
        //System.out.println("Taxon "+s + " to id " + id);
        
        return id;
    }
    
    public void parseTaxonomyAndCount(String s) {
        System.out.println("ERROR: Shouldn't get to parseTaxonomyAndCount");
        System.exit(1);
        String[] parts = s.split("(,|\\s)");
        String species;
        String genus;
        String triplet;
        //System.out.println(species);
        
        if (parts[0].equals("PREDICTED:") || (parts[0].equals("Uncultured")) || (parts[0].equals("Synthetic"))) {
            species = parts[1] + " " + parts[2];
            genus = parts[1];
            triplet = parts[1] + " " + parts[2] + " " + parts[3];
        } else {
            species = parts[0] + " " + parts[1];
            genus = parts[0];
            triplet = parts[0] + " " + parts[1] + " " + parts[2];
        }
        
        // Note: Could look for 'plasmid' keyword 
        if (s.contains("plasmid")) {
            //System.out.println("Plasmid found");
        }
        
        Long id = getTaxonIdFromName(species);
        if (id != null) {
            countRead(id);            
        } else {
            id = getTaxonIdFromName(genus);
            if (id != null) {
                countRead(id);
            } else {
                id = getTaxonIdFromName(triplet);
                if (id != null) {
                    countRead(id);
                } else {                
                    if (species.startsWith("Human")) {
                        countRead(humanId);
                    } else if (species.equals("Artificial cloning")) {
                        id = vectorsId;
                    } else if (species.startsWith("Cloning vectors") || (species.startsWith("Cloning vector"))) {
                        id = vectorsId;
                    } else if (species.equalsIgnoreCase("Unidentified bacterium")) {
                        id = bacteriaId;
                    } else if (s.contains("vector lambda") || (species.startsWith("Lambda genome"))) {
                        countRead(lambdaId);
                    } else if (s.startsWith("E.coli")) {
                        countRead(ecoliId);
                    } else {
                        //System.out.println("Couldn't parse and count "+s+" ("+species+")");
                        unclassifiedNode.incrementAssigned();
                    }
                }
            }
        }
        
        //System.out.println("["+getTaxonomyStringFromName(species)+"]");
    }
    
    public void findAncestorAndStore(BlastHitSet bhs, int treeId) {
        if (treeId == 0) {
            //bhs.displayInfo();
            long ancestor = 0;
            boolean same = true;
            int level = 1;
            int maxLevel = 1000;
            int maxToParse = 5;
            int loopTo = bhs.getNumberOfAlignments() < maxToParse ? bhs.getNumberOfAlignments():maxToParse;

             //System.out.println("------------ New match -----------");
            
            for (int i=0; i<loopTo; i++) {
                //System.out.print(this.getTaxonomyStringFromId(bhs.getAlignment(i).getLeafNode()));
                //System.out.println(" "+bhs.getAlignment(i).getTaxonLevel());
                if (bhs.getAlignment(i).getTaxonLevel() < maxLevel) {
                    maxLevel = bhs.getAlignment(i).getTaxonLevel();
                }
                //ArrayList tip = bhs.getAlignment(i).getTaxonIdPath();
                //for (int j=0; j<tip.size(); j++) {
                //    if (j > 0) {
                //        System.out.print(",");
                //    }
                //    System.out.print(tip.get(j));
                //}
                //System.out.println("");
            }

            while ((same == true) && (level <= maxLevel)) {
                long common = -1;
                for (int i=0; i<loopTo; i++) {
                    if (common == -1) {
                        common = bhs.getAlignment(i).getTaxonNode(level);
                    } else if (bhs.getAlignment(i).getTaxonNode(level) != common) {
                        same = false;
                    }
                }

                if (same == true) {
                    ancestor = common;
                    //System.out.println("Match on " + this.getNameFromTaxonId(common));
                    level++;
                }         
            }

            //System.out.println("Ancestor " + this.getNameFromTaxonId(ancestor));
            countRead(ancestor);
        } else {
            if (!warningId) {
                System.out.println("Not storing taxonomy for tree ID other than 0");
                warningId = true;
            }
        }
    }
    
    private void displayLevel(Node n, int l) {
        String taxonString = getNameFromTaxonId(n.getId());
        for (int i=0; i<l; i++) {
            System.out.print("  ");
        }
        System.out.println(taxonString + " ("+n.getDisplayCol() + " , "+n.getDisplayRow()+")");
        ArrayList<Node> children = n.getChildren();
        for (int i=0; i<children.size(); i++) {
            Node c = children.get(i);
            
            if (c.getSummarised() > 0) {
                displayLevel(children.get(i), l+1);
            }
        }
    }
    
    public void displayTaxonomy() {
        Node n = getNodeFromTaxonId(1L);
        displayLevel(n, 0);
    }
    
    private int getMaxColumn() {
        return maxColumn;
    }
    
    private int getMaxRow() {
        return maxRow;
    }
    
    private int prepareNode(Node n, int col, int row) {
        n.setDisplayPosition(col, row);
        
        if (col > maxColumn) {
            maxColumn = col;
        }
        
        ArrayList<Node> children = n.getChildren();
        int childrenIncluded = 0;
        for (int i=0; i<children.size(); i++) {
            Node c = children.get(i);
                        
            if (c.getSummarised() > 0) {
                childrenIncluded++;
                if (childrenIncluded > 1) {
                    row++;
                }
                row = prepareNode(c, col+1, row);
            }
        }
        return row;
    }
    
    public void prepareTreePlot() {
        Node n = getNodeFromTaxonId(1L);
        maxRow = prepareNode(n, 1, 1);
        unclassifiedNode.setDisplayPosition(1, maxRow+1);
        plotWidth = ((maxColumn+1)*colWidth) + 200;
        plotHeight = (maxRow+2)*rowHeight;
        System.out.println("plotWidth = "+plotWidth + " plotHeight = " + plotHeight);
    }
    
    public int getUnclassifiedCount() {
        return unclassifiedNode.getSummarised();
    }
    
    private void connectNode(Graphics g, Node n, Node c) {
        if (n.getDisplayRow() == c.getDisplayRow()) {
            //g.drawLine(colToX(n.getDisplayCol()) + circleRadius, rowToY(n.getDisplayRow()), colToX(c.getDisplayCol()) - circleRadius, rowToY(c.getDisplayRow()));
            g.drawLine(colToX(n.getDisplayCol()), rowToY(n.getDisplayRow()), colToX(c.getDisplayCol()), rowToY(c.getDisplayRow()));
        } else {
            //g.drawLine(colToX(n.getDisplayCol()) + circleRadius, rowToY(n.getDisplayRow()), colToX(n.getDisplayCol()) + circleDiameter, rowToY(n.getDisplayRow()));
            //g.drawLine(colToX(n.getDisplayCol()) + circleDiameter, rowToY(n.getDisplayRow()), colToX(n.getDisplayCol()) + circleDiameter, rowToY(c.getDisplayRow()));
            //g.drawLine(colToX(n.getDisplayCol()) + circleDiameter, rowToY(c.getDisplayRow()), colToX(c.getDisplayCol()) - circleRadius, rowToY(c.getDisplayRow()));
            g.drawLine(colToX(n.getDisplayCol()), rowToY(n.getDisplayRow()), colToX(n.getDisplayCol()), rowToY(n.getDisplayRow()));
            g.drawLine(colToX(n.getDisplayCol()), rowToY(n.getDisplayRow()), colToX(n.getDisplayCol()), rowToY(c.getDisplayRow()));
            g.drawLine(colToX(n.getDisplayCol()), rowToY(c.getDisplayRow()), colToX(c.getDisplayCol()), rowToY(c.getDisplayRow()));
        }
    }
    
    private int colToX(int c) {
        return ((c-1) * colWidth) + circleDiameter;
    }
    
    private int rowToY(int r) {
        return ((r-1) * rowHeight) + circleDiameter;
    }

    public int getPlotWidth() {
        return plotWidth;
    }

    public int getPlotHeight() {
        return plotHeight;
    }
    
    private int setGraphicColourAndGetSizeForCount(Graphics g, Node n) {
        Double v = Math.log((double)n.getAssigned()+1);
        Double m = Math.log((double)Node.getMaxAssigned()+1);
        //Double value = (double)n.getAssigned() / (double)Node.getMaxAssigned();
        Double value = v / m;
        double radius = value * circleDiameter;
        
        int aR = 255;   int aG = 255; int aB=255;  // RGB for our 1st color (blue in this case).
        int bR = 0x31; int bG = 0x82; int bB=0xbd;    // RGB for our 2nd color (red in this case).
        
        int red   = (int)((double)(bR - aR) * value + aR);      // Evaluated as -255*value + 255.
        int green = (int)((double)(bG - aG) * value + aG);      // Evaluates as 0.
        int blue  = (int)((double)(bB - aB) * value + aB);      // Evaluates as 255*value + 0.
    
        g.setColor(new Color(red, green, blue));
        
        return (int)radius;       
    }
        
    
    private void drawConnections(Graphics g, Node n) {
        ArrayList<Node> children = n.getChildren();
        int childrenIncluded = 0;
        
        for (int i=0; i<children.size(); i++) {
            Node c = children.get(i);
            if (c.getSummarised() > 0) {
                childrenIncluded++;
                drawConnections(g, c);
                connectNode(g, n, c);
            }
        }        
    }
    
    private void drawNode(Graphics g, Node n) {
        int diameter = setGraphicColourAndGetSizeForCount(g, n);
        int x = colToX(n.getDisplayCol()) - (diameter/2);
        int y = rowToY(n.getDisplayRow()) - (diameter/2);

        if ((x >= (bounds.getX() - 32)) &&
            (y >= (bounds.getY() - 32)) &&
            (x <= (bounds.getX() + bounds.getWidth())) &&
            (y <= (bounds.getY() + bounds.getHeight()))) {        
            g.fillOval(x, y, diameter, diameter);
            g.setColor(Color.BLACK);
            g.drawOval(x, y, diameter, diameter);
        }
                
        ArrayList<Node> children = n.getChildren();
        int childrenIncluded = 0;
        
        for (int i=0; i<children.size(); i++) {
            Node c = children.get(i);
            if (c.getSummarised() > 0) {
                childrenIncluded++;
                drawNode(g, c);
                //connectNode(g, n, c);
            }
        }
        
        if (childrenIncluded == 0) {
            String taxonString = getNameFromTaxonId(n.getId());
            g.drawString(taxonString, colToX(n.getDisplayCol()) + (diameter/2) + 4, rowToY(n.getDisplayRow()) + 4);
            //System.out.println("Label "+taxonString + " " + n.getDisplayCol() + " " + n.getDisplayRow());
        }
    }
    
    public void drawTree(Graphics g) {
        //System.out.println("Drawing tree...");
        Node n = getNodeFromTaxonId(1L);
        bounds = g.getClipBounds();
        g.setFont(new Font("Arial", Font.PLAIN, 12)); 
        drawConnections(g, n);
        drawNode(g, n);
        drawNode(g, unclassifiedNode);
    }
    
    private void walkNode(Node n, HashMap counts) {
        ArrayList<Node> children = n.getChildren();
        int childrenIncluded = 0;

        for (int i=0; i<children.size(); i++) {
            Node c = children.get(i);
            if (c.getSummarised() > 0) {
                childrenIncluded++;
                walkNode(c, counts);
            }
        }

        //if (childrenIncluded == 0) {
            if (n.getAssigned() >= assignedThreshold) {
                counts.put(getNameFromTaxonId(n.getId())+ " ("+n.getAssigned()+")", n.getAssigned());
                totalAssignedReads += n.getAssigned();
            } else {
                otherCount++;
            }
        //}
    }
    
    public int getTotalAssignedReads() {
        return totalAssignedReads;
    }
    
    public Map<String, Integer> getLeafNodes() {
        HashMap<String, Integer> counts = new HashMap<String, Integer>();
        Node n = getNodeFromTaxonId(1L);
        totalAssignedReads = 0;
        otherCount = 0;
        walkNode(n, counts);
        System.out.println("otherCount="+otherCount);
        System.out.println("unclassified assigned="+unclassifiedNode.getAssigned());
        System.out.println("total count "+totalCountCheck);
        counts.put("Other ("+otherCount+")", otherCount);
        counts.put("Unclassified ("+unclassifiedNode.getAssigned()+")", unclassifiedNode.getAssigned());
                
        Map<String, Integer> sortedLeafCounts = WalkOutResults.sortByValues(counts);

        return sortedLeafCounts;
    }
    
    public int registerTree() {
        return nTrees++;
    }
}
