package net.felisgamerus.regius.entity.custom.genetics;

public class Locus {
    //Each individual locus where the alleles are stored; used for determining what a Snake looks like
    private String locusName;
    private String locusType;
    private int allele0 = 0;
    private int allele1 = 0;

    Locus(String locusName, String locusType) {
        this.locusName = locusName;
        this.locusType = locusType;
    }

    //Control methods for locusType and both alleles
    public String getLocusType() {
        return this.locusType;
    }

    public int getAllele0() {
        return this.allele0;
    }

    public int getAllele1() {
        return this.allele1;
    }

    public void setAllele0(int newAllele) {
        this.allele0 = newAllele;
    }

    public void setAllele1(int newAllele) {
        this.allele1 = newAllele;
    }
}
