package net.felisgamerus.regius.entity.custom.genetics;

public class Locus {
    //Each individual locus where the alleles are stored; used for determining what a Snake looks like
    String locusName;
    String locusType;
    int allele0 = 0;
    int allele1 = 0;

    Locus(String givenLocusid, String givenLocusType) {
        locusName = givenLocusid;
        locusType = givenLocusType;
    }

    //Control methods for locusType and both alleles
    public String getLocusType() {return this.locusType;}

    public void setAllele0(int newAllele) {this.allele0 = newAllele;}
    public int getAllele0() {return this.allele0;}

    public void setAllele1(int newAllele) {this.allele1 = newAllele;}
    public int getAllele1() {return this.allele1;}
}
