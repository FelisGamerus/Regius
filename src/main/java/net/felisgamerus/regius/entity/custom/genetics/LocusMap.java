package net.felisgamerus.regius.entity.custom.genetics;

import net.felisgamerus.regius.entity.custom.BallPythonEntity;
import org.checkerframework.checker.units.qual.A;

import java.util.*;

public class LocusMap {
    //A HashMap of Loci and their locusNames; used to contain the genes of a Snake
    private HashMap<String, Locus> genes = new HashMap<>();

    //Lists of all possible morphs
    private static final ArrayList<String> ALL_MORPHS = new ArrayList<>(Arrays.asList("albino", "pinstripe", "pastel"));
    private static final HashSet<String> DOMINANT_MORPHS = new HashSet<>(Arrays.asList("pinstripe"));
    private static final HashSet<String> INCOMPLETE_DOMINANT_MORPHS = new HashSet<>(Arrays.asList("pastel"));
    private static final HashSet<String> RECESSIVE_MORPHS = new HashSet<>(Arrays.asList("albino"));

    public LocusMap() {
        this.fillDefaultLocusMap();
    }

    //TODO: Can I get rid of this?
    public void fillDefaultLocusMap() {
        //Initializes the LocusMap
        for (int i = 0; i < ALL_MORPHS.size(); i++) {
            String locusName = ALL_MORPHS.get(i);
            String locusType = "unknown";
            if (DOMINANT_MORPHS.contains(locusName)) {
                locusType = "dominant";
            } else if (INCOMPLETE_DOMINANT_MORPHS.contains(locusName)) {
                locusType = "incompleteDominant";
            } else if (RECESSIVE_MORPHS.contains(locusName)) {
                locusType = "recessive";
            }
            Locus locus = new Locus(locusName, locusType);
            genes.put(locusName, locus);
        }
    }

    //type must equal "recessive", "incompleteDominant", or "dominant"
    public static Boolean checkType (String locus, String type) {
        return getLocusType(locus).equals(type);
    }

    public int getAllele0(String locus) {
        return this.genes.get(locus).getAllele0();
    }

    public int getAllele1(String locus) {
        return this.genes.get(locus).getAllele1();
    }

    public HashMap<String, Locus> getGenes() {
        return genes;
    }

    public static ArrayList getLociArray() {
        return ALL_MORPHS;
    }

    public static String getLocusType(String locus) {
        if (DOMINANT_MORPHS.contains(locus)) {
            return "dominant";
        } else if (INCOMPLETE_DOMINANT_MORPHS.contains(locus)) {
            return "incompleteDominant";
        } else if (RECESSIVE_MORPHS.contains(locus)) {
            return "recessive";
        } else return "unknown";
    }
}
