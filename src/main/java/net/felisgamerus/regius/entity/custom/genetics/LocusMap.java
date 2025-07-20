package net.felisgamerus.regius.entity.custom.genetics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class LocusMap {
    //A HashMap of Loci and their locusNames; used to contain the genes of a Snake
    public HashMap<String, Locus> genes = new HashMap<>();

    //Lists of all possible morphs
    public final ArrayList<String> ALL_MORPHS = new ArrayList<>(Arrays.asList("albino", "pinstripe", "pastel"));
    public final ArrayList<String> DOMINANT_MORPHS = new ArrayList<>(Arrays.asList("pinstripe"));
    public final ArrayList<String> CODOMINANT_MORPHS = new ArrayList<>(Arrays.asList("pastel"));
    public final ArrayList<String> RECESSIVE_MORPHS = new ArrayList<>(Arrays.asList("albino"));

    public LocusMap() {
        this.fillDefaultLocusMap();
    }

    public void fillDefaultLocusMap() {
        //Initializes the LocusMap
        for (int i = 0; i < ALL_MORPHS.size(); i++) {
            String locusName = ALL_MORPHS.get(i);
            String locusType = "unknown";
            if (DOMINANT_MORPHS.contains(locusName)) {
                locusType = "dominant";
            } else if (CODOMINANT_MORPHS.contains(locusName)) {
                locusType = "codominant";
            } else if (RECESSIVE_MORPHS.contains(locusName)) {
                locusType = "recessive";
            }
            Locus locus = new Locus(locusName, locusType);
            genes.put(locusName, locus);
        }
    }

    public ArrayList getLociArray () {
        return ALL_MORPHS;
    }

    public String getLocusType(String locus) {return this.genes.get(locus).getLocusType();}
    public int getAllele0(String locus) {return this.genes.get(locus).getAllele0();}
    public int getAllele1(String locus) {return this.genes.get(locus).getAllele1();}
}
