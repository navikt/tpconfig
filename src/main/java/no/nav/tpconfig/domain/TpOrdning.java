package no.nav.tpconfig.domain;

import java.util.Arrays;

public enum TpOrdning {

    TPORDNING_SPK_DEFAULT("3010", "80000470761", TpLeverandoer.SPK),
    TPORDNING1_KLP_DEFAULT("3200", "80000470767", TpLeverandoer.KLP),
    TPORDNING2_KLP_DEFAULT("3250", "80000470769", TpLeverandoer.KLP),
    TPORDNING_PTS_DEFAULT("3100", "80000470763", TpLeverandoer.PTS),
    TPORDNING_OPF_DEFAULT("3820", "80000470802", TpLeverandoer.OPF),
    TPORDNING_GFF_DEFAULT("4575", "80000470829", TpLeverandoer.GFF),
    TPORDNING_GABLER_DEFAULT("4160", "80000619163", TpLeverandoer.GABLER),
    STOREBRAND_TPORDNING1_DEFAULT("4095", "80000470818", TpLeverandoer.STOREBRAND),
    STOREBRAND_TPORDNING2_DEFAULT("4096", "80000619165", TpLeverandoer.STOREBRAND),
    TPORDNING_ASKER("4082", "80000750000", TpLeverandoer.KLP),
    TPORDNING_AKERSHUS("3410", "80000470772", TpLeverandoer.GABLER),
    TPORDNING_ARENDAL("3610", "80000470780", TpLeverandoer.GABLER),
    TPORDNING_BERGEN("3620", "80000470782", TpLeverandoer.GABLER),
    TPORDNING_FLEKKEFJORD("3675", "80000470792", TpLeverandoer.GABLER),
    TPORDNING_HALDEN("3700", "80000470794", TpLeverandoer.GABLER),
    TPORDNING_HAUGESUND("3730", "80000470796", TpLeverandoer.GABLER),
    TPORDNING_KRISTIANSAND("3750", "80000470798", TpLeverandoer.GABLER),
    TPORDNING_SANDEFJORD("3850", "80000470804", TpLeverandoer.GABLER),
    TPORDNING_SANDNES("3860", "80000470806", TpLeverandoer.GABLER),
    TPORDNING_SKIEN("3880", "80000470808", TpLeverandoer.GABLER),
    TPORDNING_TRONDHEIM("3910", "80000470810", TpLeverandoer.GABLER),
    TPORDNING_DRAMMEN("3660", "80000470786", TpLeverandoer.GABLER),
    TPORDNING_BUSKERUD("3420", "80000470774", TpLeverandoer.GABLER),
    TPORDNING_MOSS("3780", "80000470800", TpLeverandoer.GABLER),
    TPORDNING_BODO("3630", "80000470784", TpLeverandoer.GABLER),
    TPORDNING_HAUGALAND_KRAFT("4154", "80000470826", TpLeverandoer.GABLER),
    TPORDNING_LOERENSKOG("4099", "80000556586", TpLeverandoer.GABLER),
    TPORDNING_AGDER_ENERGI("3440", "80000470776", TpLeverandoer.GABLER),
    TPORDNING_BKK("4152", "80000470823", TpLeverandoer.GABLER),
    TPORDNING_ENERGISELSKAPENE("4153", "80000470824", TpLeverandoer.GABLER),
    TPORDNING_HAFSLUND_INFRATEK("4151", "80000470822", TpLeverandoer.GABLER),
    TPORDNING_HARSTAD("4156", "80000613513", TpLeverandoer.GABLER),
    TPORDNING_HAALOGALAND_KRAFT("3736", "80000748050", TpLeverandoer.GABLER),
    TPORDNING_LOFOTKRAFT("4162", "80000748052", TpLeverandoer.GABLER),
    TPORDNING_NETTBUSS("4161", "80000748054", TpLeverandoer.GABLER),
    TPORDNING_NORDKRAFT("4163", "80000748056", TpLeverandoer.GABLER),
    TPORDNING_SKAGERAK_ENERGI("3943", "80000748058", TpLeverandoer.GABLER),
    TPORDNING_TEKNOLOGISK_INSTITUTT("4155", "80000470828", TpLeverandoer.GABLER),
    TPORDNING_ELVERUM("3670", "80000470788", TpLeverandoer.STOREBRAND),
    TPORDNING_NORGESBANK("3110", "80000470765", TpLeverandoer.GABLER),
    TPORDNING_MOLDE("3300", "80000470771", TpLeverandoer.STOREBRAND),
    TPORDNING_FJELL("3672", "80000470790", TpLeverandoer.STOREBRAND),
    TPORDNING_TROMSOE("4081", "80000749994", TpLeverandoer.STOREBRAND),
    TPORDNING_BAERUM("4083", "80000749998", TpLeverandoer.STOREBRAND),
    TPORDNING_HELSEFORETAKENE_HOVEDSTADSOMRAADET("4098", "80000515880", TpLeverandoer.STOREBRAND),
    TPORDNING_ECO_ENERGI("4150", "80000470820", TpLeverandoer.STOREBRAND);

    private String tpNumber;
    private String tssNumber;
    private TpLeverandoer tpLeverandoer;

    TpOrdning(String tpNumber, String tssNumber, TpLeverandoer tpLeverandoer) {
        this.tpNumber = tpNumber;
        this.tssNumber = tssNumber;
        this.tpLeverandoer = tpLeverandoer;
    }

    public static TpLeverandoer tpLeverandoerFromTpNumber(String tpNumber) {
        return Arrays.stream(values()).filter(
                (ordning) -> ordning.tpNumber.equals(tpNumber)).findAny().map(TpOrdning::tpLeverandoer)
                .orElseThrow(() -> new NoTpOrdningFound("No TP-leverandoer found for TP-nr: " + tpNumber));
    }

    public static TpOrdning tpOrdningFromTpNumber(String tpNumber) {
        return Arrays.stream(values()).filter(
                (ordning) -> ordning.tpNumber.equals(tpNumber)).findAny()
                .orElseThrow(() -> new NoTpOrdningFound("No TP-ordning found for TP-nr: " + tpNumber));
    }

    public static TpOrdning tpOrdningFromTssNumber(String tssNumber) {
        return Arrays.stream(values()).filter(
                (ordning) -> ordning.tssNumber.equals(tssNumber)).findAny()
                .orElseThrow(() -> new NoTpOrdningFound("No TP-ordning found for TSS-nr: " + tssNumber));
    }

    public String tpNumber() {
        return this.tpNumber;
    }

    public String tssNumber() {
        return this.tssNumber;
    }

    public TpLeverandoer tpLeverandoer() {
        return this.tpLeverandoer;
    }


}
