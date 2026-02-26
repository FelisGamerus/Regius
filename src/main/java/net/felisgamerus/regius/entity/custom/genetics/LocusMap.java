package net.felisgamerus.regius.entity.custom.genetics;

import net.felisgamerus.regius.entity.custom.BallPythonEntity;
import org.checkerframework.checker.units.qual.A;

import java.util.*;

public class LocusMap {
    //A HashMap of Loci and their locusNames; used to contain the genes of a Snake
    private HashMap<String, Locus> genes = new HashMap<>();

    //Lists of all possible morphs
    //Note to self: To add new morphs, just add the textures and edit these lists. That's it.
    private static final ArrayList<String> ALL_MORPHS = new ArrayList<>(Arrays.asList("albino", "cinnamon", "pinstripe", "pastel"));
    private static final HashSet<String> DOMINANT_MORPHS = new HashSet<>(Arrays.asList("pinstripe"));
    private static final HashSet<String> INCOMPLETE_DOMINANT_MORPHS = new HashSet<>(Arrays.asList("cinnamon", "pastel"));
    private static final HashSet<String> RECESSIVE_MORPHS = new HashSet<>(Arrays.asList("albino"));

    //List of morph combos with no assigned texture, couldn't think of anywhere better to put it
    private static final ArrayList<String> INVALID_TEXTURES = new ArrayList<>(Arrays.asList(
            "albino_cinnamon-super_pastel",
            "albino_cinnamon_pastel-super",
            "albino_cinnamon-super_pastel-super",
            "albino_cinnamon-super_pinstripe",
            "cinnamon-super_pastel-super_pinstripe",
            "albino_cinnamon-super_pastel_pinstripe",
            "albino_cinnamon_pastel-super_pinstripe",
            "albino_cinnamon-super_pastel-super_pinstripe",
            "albino_pastel-super_pinstripe"
    ));

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

    public static boolean isInvalid(String morphCombo) {
        return INVALID_TEXTURES.contains(morphCombo);
    }
}
