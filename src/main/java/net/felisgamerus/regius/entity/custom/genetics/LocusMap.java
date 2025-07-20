package net.felisgamerus.regius.entity.custom.genetics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class LocusMap {
    //A HashMap of Loci and their locusNames; used to contain the genes of a Snake
    public HashMap<String, Locus> genes = new HashMap<>();
    public final ArrayList<String> ALL_LOCI = new ArrayList<>(Arrays.asList("albino", "pinstripe", "pastel"));
    final ArrayList<String> DOMINANT_LOCI = new ArrayList<>(Arrays.asList("pinstripe"));
    final ArrayList<String> CODOMINANT_LOCI = new ArrayList<>(Arrays.asList("pastel"));
    final ArrayList<String> RECESSIVE_LOCI = new ArrayList<>(Arrays.asList("albino"));

    public LocusMap() {
        this.fillDefaultLocusMap();
    }

    public void fillDefaultLocusMap() {
        //Initializes the LocusMap
        for (int i = 0; i < ALL_LOCI.size(); i++) {
            String locusName = ALL_LOCI.get(i);
            String locusType = "unknown";
            if (DOMINANT_LOCI.contains(locusName)) {
                locusType = "dominant";
            } else if (CODOMINANT_LOCI.contains(locusName)) {
                locusType = "codominant";
            } else if (RECESSIVE_LOCI.contains(locusName)) {
                locusType = "recessive";
            }
            Locus locus = new Locus(locusName, locusType);
            genes.put(locusName, locus);
        }
    }

    public ArrayList getLociArray () {
        return ALL_LOCI;
    }
    public HashMap getGenes () {
        return genes;
    }

    public String getLocusType(String locus) {return this.genes.get(locus).getLocusType();}

    public void setAllele0(String locus, int newAllele) {this.genes.get(locus).setAllele0(newAllele);}
    public int getAllele0(String locus) {return this.genes.get(locus).getAllele0();}

    public void setAllele1(String locus, int newAllele) {this.genes.get(locus).setAllele1(newAllele);}
    public int getAllele1(String locus) {return this.genes.get(locus).getAllele1();}
}
