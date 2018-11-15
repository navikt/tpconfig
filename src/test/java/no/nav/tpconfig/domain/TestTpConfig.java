package no.nav.tpconfig.domain;

public class TestTpConfig extends TpConfig {

    @Override
    void addConfig(String tpNumber, TPLeverandoerData tpLeverandoerData, String tssNumber) {
        //Prevents prodconfig in super constructor from being added
    }

    public void addTestConfig(String tpnr, TPLeverandoerData tpLeverandoerData, String tssNumber) {
        super.addConfig(tpnr, tpLeverandoerData, tssNumber);
    }

    public void addTestConfig(String tpnr, String serviceAccount, String tPLeverandoerName, String tssNumber) {
        super.addConfig(tpnr, new TPLeverandoerData(serviceAccount, tPLeverandoerName), tssNumber);
    }
}
