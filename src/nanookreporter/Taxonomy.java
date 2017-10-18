package nanookreporter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Hashtable;

public class Taxonomy {
    private Hashtable<Long, Node> nodesById = new Hashtable();
    private Hashtable<Long, String> nameById = new Hashtable();
    private Hashtable<String, Long> idByName = new Hashtable();
    
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
                n.setParent(parentId);   
                n.setRank(rank);
                linkParent(n, parentId);                
            }
            br.close();
            
            System.out.println("Reading "+namesFilename);
            br = new BufferedReader(new FileReader(namesFilename));
            while ((line = br.readLine()) != null) {
                String[] fields = line.split("\t");
                if (fields[6].equals("scientific name")) {
                    long id = Long.parseLong(fields[0]);
                    associateNameWithTaxonId(fields[2], id);
                }                
            }
            br.close();            
        } catch (Exception e) {
            System.out.println("Taxonomy exception");
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Processed "+nameById.size()+" nodes");
        
        this.dumpTaxonomyFromName("Arabidopsis thaliana");
        System.out.println("");
        this.dumpTaxonomyFromName("Enterobacteriaceae bacterium strain FGI 57");
    }
    
    private void associateNameWithTaxonId(String name, Long id) {
        nameById.put(id, name);        
        idByName.put(name, id);
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
                    if (parentId != n.getId()) {
                        n = getNodeFromTaxonId(n.getParent());
                    } else {
                        n = null;
                    }
                }
            }
        }
        
        return taxonString;
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
                    if (parentId != n.getId()) {
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
}
