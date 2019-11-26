package no.nav.tpconfig.domain;

public class TestTpConfig extends TpConfig {

    @Override
    void addConfig(String tpNumber, TPLeverandoerData tpLeverandoerData, String tssNumber) {
        //Prevents prodconfig in super constructor from being added
    }

    public void addTestConfig(String tpnr, String serviceAccount, String tPLeverandoerName, String tssNumber, String orgNr) {
        super.addConfig(tpnr, new TPLeverandoerData(serviceAccount, tPLeverandoerName, orgNr), tssNumber);
    }
}
