package no.nav.tpconfig.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TpConfigTest {

    private static TpConfig tpConfig;
    private final static String NON_EXISTING_TPNR = "90000000";
    private final static String TPNR_FOR_SPK = "3010";
    private final static String NON_EXISTING_TSSNR = "00";
    private final static String TSSNR_FOR_SPK = "80000470761";
    private final static String FICTIONAL_SERVICE_ACCOUNT = "srvElsam_Test";
    private final static String FICTIONAL_TPLEVERANDOERNAME = "TEST";
    private final static String SPK_SERVICE_ACCOUNT = "srvElsam_SPK";
    private final static TPLeverandoerData FICTIONALTPLEVERANDOERDATA = new TPLeverandoerData(FICTIONAL_SERVICE_ACCOUNT, FICTIONAL_TPLEVERANDOERNAME);

    @Before
    public void setUp() {
        tpConfig = new TpConfig();
    }

    @Test
    public void serviceaccount_returns_SPK_SERVICE_ACCOUNT_when_tpnr_is_3010() {
        assertEquals(SPK_SERVICE_ACCOUNT, tpConfig.serviceaccount(TPNR_FOR_SPK));
    }

    @Test(expected = NoTpOrdningFound.class)
    public void serviceaccount_returns_error_message_given_nonexisting_tpnr() {
        tpConfig.serviceaccount(NON_EXISTING_TPNR);
    }

    @Test
    public void addConfig_adds_tpnr_and_serviceaccount_when_key_does_not_exist_in_tpconfig() {
        tpConfig.addConfig(NON_EXISTING_TPNR, FICTIONALTPLEVERANDOERDATA, NON_EXISTING_TSSNR);
        assertEquals(FICTIONAL_SERVICE_ACCOUNT, tpConfig.serviceaccount(NON_EXISTING_TPNR));
    }

    @Test
    public void hent_tssnr_by_tpnr() {
        assertEquals(TSSNR_FOR_SPK, tpConfig.tssNummerByTpNr(TPNR_FOR_SPK));
    }

    @Test(expected = IllegalTpConfig.class)
    public void addConfig_throws_IllegalTpConfig_when_key_already_exists_in_map() {
        tpConfig.addConfig(TPNR_FOR_SPK, FICTIONALTPLEVERANDOERDATA, TSSNR_FOR_SPK);
    }

    @Test(expected = IllegalTpConfig.class)
    public void addConfig_throws_IllegalTpConfig_when_tpnr_is_null() {
        tpConfig.addConfig(null, FICTIONALTPLEVERANDOERDATA, NON_EXISTING_TSSNR);
    }

    @Test(expected = IllegalTpConfig.class)
    public void addConfig_throws_IllegalTpConfig_when_serviceaccount_is_null() {
        tpConfig.addConfig(NON_EXISTING_TPNR, new TPLeverandoerData(null, FICTIONAL_TPLEVERANDOERNAME), NON_EXISTING_TSSNR);
    }

    @Test(expected = IllegalTpConfig.class)
    public void addConfig_throws_IllegalTpConfig_when_tpleverandoername_is_null() {
        tpConfig.addConfig(NON_EXISTING_TPNR, new TPLeverandoerData(FICTIONAL_SERVICE_ACCOUNT, null), NON_EXISTING_TSSNR);
    }

    @Test(expected = IllegalTpConfig.class)
    public void addConfig_throws_IllegalTpConfig_when_tplevernandoerdata_is_null() {
        tpConfig.addConfig(NON_EXISTING_TPNR, null, NON_EXISTING_TSSNR);
    }

    @Test(expected = IllegalTpConfig.class)
    public void addConfig_throws_IllegalTpConfig_when_tssnumber_is_null() {
        tpConfig.addConfig(NON_EXISTING_TPNR, FICTIONALTPLEVERANDOERDATA, null);
    }

    @Test(expected = IllegalTpConfig.class)
    public void addConfig_throws_IllegalTpConfig_when_tpnr_and_serviceaccount_and_tssnumber_is_null() {
        tpConfig.addConfig(null, null, null);
    }
}