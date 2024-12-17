package net.felisgamerus.regius.genetics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class LocusMap {
    //A HashMap of Loci and their locusNames; used to contain the genes of a Snake
    public HashMap<String, Locus> loci = new HashMap<>();
    final ArrayList<String> LOCI = new ArrayList<>(Arrays.asList("albino", "pinstripe", "pastel"));
    final ArrayList<String> DOMINANT_LOCI = new ArrayList<>(Arrays.asList("pinstripe"));
    final ArrayList<String> CODOMINANT_LOCI = new ArrayList<>(Arrays.asList("pastel"));
    final ArrayList<String> RECESSIVE_LOCI = new ArrayList<>(Arrays.asList("albino"));

    public LocusMap() {
        this.fillDefaultLocusMap();
    }

    public void fillDefaultLocusMap() {
        //Initializes the LocusMap
        for (int i = 0; i < LOCI.size(); i++) {
            String locusName = LOCI.get(i);
            String locusType = "unknown";
            if (DOMINANT_LOCI.contains(locusName)) {
                locusType = "dominant";
            } else if (CODOMINANT_LOCI.contains(locusName)) {
                locusType = "codominant";
            } else if (RECESSIVE_LOCI.contains(locusName)) {
                locusType = "recessive";
            }
            Locus locus = new Locus(locusName, locusType);
            loci.put(locusName, locus);
        }
    }

    //Needed for the breeder
    public ArrayList getLociArray () {return LOCI;}
}
