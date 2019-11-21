package no.nav.tpconfig.domain;

final class TPLeverandoerData {
    private final String serviceAccount;
    private final String orgNr;
    private final String tPLeverandoerName;

    TPLeverandoerData(String tPLeverandoerName, String serviceAccount,  String orgNr) {
        this.serviceAccount = serviceAccount;
        this.orgNr = orgNr;
        this.tPLeverandoerName = tPLeverandoerName;
    }

    String getTpLeverandoerName() {
        return tPLeverandoerName;
    }

    String getServiceAccount() {
        return serviceAccount;
    }

    String getOrgNr() {
        return orgNr;
    }
}
