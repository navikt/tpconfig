package no.nav.tpconfig.domain;

final class TPLeverandoerData {
    private final String serviceAccount;
    private final String tPLeverandoerName;

    TPLeverandoerData(String serviceAccount, String tPLeverandoerName) {
        this.serviceAccount = serviceAccount;
        this.tPLeverandoerName = tPLeverandoerName;
    }

    String gettPLeverandoerName() {
        return tPLeverandoerName;
    }

    String getServiceAccount() {
        return serviceAccount;
    }
}
