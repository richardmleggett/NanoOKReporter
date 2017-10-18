package nanookreporter;

import java.util.List;

public class Node<T> {
    private T data;
    private Node<T> parent;
    private List<Node<T>> children;
    private Long taxonId;
    private Long parentId;
    private short rank;
    
    public static final short RANK_UNKNOWN = 0;
    public static final short RANK_CLASS = 1;
    public static final short RANK_COHORT = 2;
    public static final short RANK_FAMILY = 3;
    public static final short RANK_FORMA = 4;
    public static final short RANK_GENUS = 5;
    public static final short RANK_INFRACLASS = 6;
    public static final short RANK_INFRAORDER = 7;
    public static final short RANK_KINGDOM = 8;
    public static final short RANK_NO_RANK = 9;
    public static final short RANK_ORDER = 10;
    public static final short RANK_PARVORDER = 11;
    public static final short RANK_PHYLUM = 12;
    public static final short RANK_SPECIES = 13;
    public static final short RANK_SPECIES_GROUP = 14;
    public static final short RANK_SPECIES_SUBGROUP = 15;
    public static final short RANK_SUBCLASS = 16;
    public static final short RANK_SUBFAMILY = 17;
    public static final short RANK_SUBGENUS = 18;
    public static final short RANK_SUBKINGDOM = 19;
    public static final short RANK_SUBORDER = 20;
    public static final short RANK_SUBPHYLUM = 21;
    public static final short RANK_SUBSPECIES = 22;
    public static final short RANK_SUBTRIBE = 23;
    public static final short RANK_SUPERCLASS = 24;
    public static final short RANK_SUPERFAMILY = 25;
    public static final short RANK_SUPERKINGDOM = 26;
    public static final short RANK_SUPERORDER = 27;
    public static final short RANK_SUPERPHYLUM = 28;
    public static final short RANK_TRIBE = 29;
    public static final short RANK_VARIETAS = 30;
        
    public Node (Long id) {
        taxonId = id;
    }
    
    public Long getId() {
        return taxonId;
    }
    
    public void setParent(Long parent) {
        parentId = parent;
    }
    
    public Long getParent() {
        return parentId;
    }
    
    public void addChild(Node n) {
    }
    
    public void setRank(String s) {
        switch(s) {
            case "class": rank=RANK_CLASS; break;
            case "cohort": rank=RANK_COHORT; break;
            case "family": rank=RANK_FAMILY; break;
            case "forma": rank=RANK_FORMA; break;
            case "genus": rank=RANK_GENUS; break;
            case "infraclass": rank=RANK_INFRACLASS; break;
            case "infraorder": rank=RANK_INFRAORDER; break;
            case "kingdom": rank=RANK_KINGDOM; break;
            case "no rank": rank=RANK_NO_RANK; break;
            case "order": rank=RANK_ORDER; break;
            case "parvorder": rank=RANK_PARVORDER; break;
            case "phylum": rank=RANK_PHYLUM; break;
            case "species": rank=RANK_SPECIES; break;
            case "species group": rank=RANK_SPECIES_GROUP ; break;
            case "species subgroup": rank=RANK_SPECIES_SUBGROUP ; break;
            case "subclass": rank=RANK_SUBCLASS; break;
            case "subfamily": rank=RANK_SUBFAMILY; break;
            case "subgenus": rank=RANK_SUBGENUS; break;
            case "subkingdom": rank=RANK_SUBKINGDOM; break;
            case "suborder": rank=RANK_SUBORDER; break;
            case "subphylum": rank=RANK_SUBPHYLUM; break;
            case "subspecies": rank=RANK_SUBSPECIES; break;
            case "subtribe": rank=RANK_SUBTRIBE; break;
            case "superclass": rank=RANK_SUPERCLASS; break;
            case "superfamily": rank=RANK_SUPERFAMILY; break;
            case "superkingdom": rank=RANK_SUPERKINGDOM; break;
            case "superorder": rank=RANK_SUPERORDER; break;
            case "superphylum": rank=RANK_SUPERPHYLUM; break;
            case "tribe": rank=RANK_TRIBE; break;
            case "varietas": rank=RANK_VARIETAS; break;
            default: System.out.println("["+s+"]");rank=RANK_UNKNOWN; break;
        }
    }
    
    public short getRank() {
        return rank;
    }
    
    public String getRankString() {
        String r = "Unknown";
        switch(rank) {
            case RANK_CLASS: r="class"; break;
            case RANK_COHORT: r="cohort"; break;
            case RANK_FAMILY: r="family"; break;
            case RANK_FORMA: r="forma"; break;
            case RANK_GENUS: r="genus"; break;
            case RANK_INFRACLASS: r="infraclass"; break;
            case RANK_INFRAORDER: r="infraorder"; break;
            case RANK_KINGDOM: r="kingdom"; break;
            case RANK_NO_RANK: r="no rank"; break;
            case RANK_ORDER: r="order"; break;
            case RANK_PARVORDER: r="parvorder"; break;
            case RANK_PHYLUM: r="phylum"; break;
            case RANK_SPECIES: r="species"; break;
            case RANK_SPECIES_GROUP: r="species group"; break;
            case RANK_SPECIES_SUBGROUP: r="species subgroup"; break;
            case RANK_SUBCLASS: r="subclass"; break;
            case RANK_SUBFAMILY: r="subfamily"; break;
            case RANK_SUBGENUS: r="subgenus"; break;
            case RANK_SUBKINGDOM: r="subkingdom"; break;
            case RANK_SUBORDER: r="suborder"; break;
            case RANK_SUBPHYLUM: r="subphylum"; break;
            case RANK_SUBSPECIES: r="subspecies"; break;
            case RANK_SUBTRIBE: r="subtribe"; break;
            case RANK_SUPERCLASS: r="superclass"; break;
            case RANK_SUPERFAMILY: r="superfamily"; break;
            case RANK_SUPERKINGDOM: r="superkingdom"; break;
            case RANK_SUPERORDER: r="superorder"; break;
            case RANK_SUPERPHYLUM: r="superphylum"; break;
            case RANK_TRIBE: r="tribe"; break;
            case RANK_VARIETAS: r="varietas"; break;
        }
        
        return r;
    }
}