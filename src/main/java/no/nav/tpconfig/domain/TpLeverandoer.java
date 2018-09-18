package no.nav.tpconfig.domain;

public enum TpLeverandoer {
    GABLER("Gabler", "srvElsam_Gabler"),
    GFF("Garantikassen for fiskere", "srvElsam_GFF"),
    KLP("Kommunal Landspensjonskasse", "srvElsam_KLP"),
    OPF("Oslo Pensjonsforsikring", "srvElsam_OPF"),
    PTS("Pensjonstrygden for Sjømenn", "srvElsam_PTS"),
    SPK("Statens pensjonskasse", "srvElsam_SPK"),
    STOREBRAND("Storebrand", "srvElsam_Storebrand");

    private String serviceAccount;

    TpLeverandoer(String leverandoer, String serviceAccount) {
        this.serviceAccount = serviceAccount;
    }

    public String serviceAccount() { return this.serviceAccount; }
}