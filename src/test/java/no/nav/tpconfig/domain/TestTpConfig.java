package no.nav.tpconfig.domain;

public class TestTpConfig extends TpConfig {

    @Override
    void addConfig(String tpnr, String serviceaccount) {
        //Prevents prodconfig in super constructor from being added
    }

    public void addTestConfig(String tpnr, String serviceaccount) {
        super.addConfig(tpnr, serviceaccount);
    }
}
