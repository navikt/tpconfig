package no.nav.tpconfig.domain;

final class TPConfigData {
    private final String tssNumber;
    private final TPLeverandoerData tpLeverandoerData;

    TPConfigData(TPLeverandoerData tpLeverandoerData, String tssNumber) {
        this.tpLeverandoerData = tpLeverandoerData;
        this.tssNumber = tssNumber;
    }

    TPLeverandoerData getTpLeverandoerData() {
        return tpLeverandoerData;
    }

    String getTssNumber() {
        return tssNumber;
    }
}
