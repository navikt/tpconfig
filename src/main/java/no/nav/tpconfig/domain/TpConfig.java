package no.nav.tpconfig.domain;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TpConfig {

    private Map<String, TPConfigData> tpconfig = new HashMap<>();

    private static final String SPK_SERVICEACCOUNT = "srvElsam_SPK";
    private static final String KLP_SERVICEACCOUNT = "srvElsam_KLP";
    private static final String STOREBRAND_SERVICEACCOUNT = "srvElsam_Storebrand";
    private static final String GABLER_SERVICEACCOUNT = "srvElsam_Gabler";
    private static final String PTS_SERVICEACCOUNT = "srvElsam_PTS";
    private static final String OPF_SERVICEACCOUNT = "srvElsam_OPF";
    private static final String GFF_SERVICEACCOUNT = "srvElsam_GFF";

    private static final String SPK_ORGNR = "982583462";
    private static final String KLP_ORGNR = "938708606";
    private static final String STOREBRAND_ORGNR = "958995369";
    private static final String GABLER_ORGNR = "950842393";
    private static final String PTS_ORGNR = "974707055";
    private static final String OPF_ORGNR = "982759412";
    private static final String GFF_ORGNR = "974652382";

    private static final String SPK = "SPK";
    private static final String KLP = "KLP";
    private static final String STOREBRAND = "STOREBRAND";
    private static final String GABLER = "GABLER";
    private static final String PTS = "PTS";
    private static final String OPF = "OPF";
    private static final String GFF = "GFF";

    public TpConfig() {

        final TPLeverandoerData spk = new TPLeverandoerData(SPK_SERVICEACCOUNT, SPK, SPK_ORGNR);
        final TPLeverandoerData klp = new TPLeverandoerData(KLP_SERVICEACCOUNT, KLP, KLP_ORGNR);
        final TPLeverandoerData storebrand = new TPLeverandoerData(STOREBRAND_SERVICEACCOUNT, STOREBRAND, STOREBRAND_ORGNR);
        final TPLeverandoerData gabler = new TPLeverandoerData(GABLER_SERVICEACCOUNT, GABLER, GABLER_ORGNR);
        final TPLeverandoerData pts = new TPLeverandoerData(PTS_SERVICEACCOUNT, PTS, PTS_ORGNR);
        final TPLeverandoerData opf = new TPLeverandoerData(OPF_SERVICEACCOUNT, OPF, OPF_ORGNR);
        final TPLeverandoerData gff = new TPLeverandoerData(GFF_SERVICEACCOUNT, GFF, GFF_ORGNR);

        addConfig("3010", spk, "80000470761");

        addConfig("3100", pts, "80000470763");

        addConfig("3820", opf, "80000470802");

        addConfig("4575", gff, "80000470829");

        addConfig("3200", klp, "80000470767");
        addConfig("3250", klp, "80000470769");
        addConfig("4082", klp, "80000750000");

        addConfig("3110", gabler, "80000470765");
        addConfig("3410", gabler, "80000470772");
        addConfig("3420", gabler, "80000470774");
        addConfig("3440", gabler, "80000470776");
        addConfig("3610", gabler, "80000470780");
        addConfig("3620", gabler, "80000470782");
        addConfig("3630", gabler, "80000470784");
        addConfig("3660", gabler, "80000470786");
        addConfig("3675", gabler, "80000470792");
        addConfig("3700", gabler, "80000470794");
        addConfig("3730", gabler, "80000470796");
        addConfig("3736", gabler, "80000748050");
        addConfig("3750", gabler, "80000470798");
        addConfig("3780", gabler, "80000470800");
        addConfig("3850", gabler, "80000470804");
        addConfig("3860", gabler, "80000470806");
        addConfig("3880", gabler, "80000470808");
        addConfig("3910", gabler, "80000470810");
        addConfig("3943", gabler, "80000748058");
        addConfig("4099", gabler, "80000556586");
        addConfig("4151", gabler, "80000470822");
        addConfig("4152", gabler, "80000470823");
        addConfig("4153", gabler, "80000470824");
        addConfig("4154", gabler, "80000470826");
        addConfig("4155", gabler, "80000470828");
        addConfig("4156", gabler, "80000613513");
        addConfig("4160", gabler, "80000619163");
        addConfig("4161", gabler, "80000748054");
        addConfig("4162", gabler, "80000748052");
        addConfig("4163", gabler, "80000748056");
        addConfig("4164", gabler, "80000795700");

        addConfig("3300", storebrand, "80000470771");
        addConfig("3670", storebrand, "80000470788");
        addConfig("3672", storebrand, "80000470790");
        addConfig("4081", storebrand, "80000749994");
        addConfig("4083", storebrand, "80000749998");
        addConfig("4095", storebrand, "80000470818");
        addConfig("4096", storebrand, "80000619165");
        addConfig("4098", storebrand, "80000515880");
        addConfig("4150", storebrand, "80000470820");
        addConfig("4157", storebrand, "80000783960");
        addConfig("4158", storebrand, "80000783962");
    }

    void addConfig(String tpNumber, TPLeverandoerData tpLeverandoerData, String tssNumber) {
        if (!Objects.isNull(tpconfig.get(tpNumber)))
            throw new IllegalTpConfig("TP-number " + tpNumber + " already exists");
        if (Objects.isNull(tpNumber))
            throw new IllegalTpConfig("TP-number can't be null");
        if (Objects.isNull(tpLeverandoerData))
            throw new IllegalTpConfig("TPLeverandoerdata can't be null");
        if (Objects.isNull(tpLeverandoerData.getServiceAccount()))
            throw new IllegalTpConfig("Serviceaccount can't be null");
        if (Objects.isNull(tpLeverandoerData.getTpLeverandoerName()))
            throw new IllegalTpConfig("TP-leverandoer-name can't be null");
        if (Objects.isNull(tssNumber))
            throw new IllegalTpConfig("TSS-number can't be null");

        tpconfig.put(tpNumber, new TPConfigData(tpLeverandoerData, tssNumber));
    }

    public String serviceaccount(String tpnr) {
        var tpData = tpconfig.get(tpnr);
        var tpLeverandoerData = tpData == null ? null : tpData.getTpLeverandoerData();
        var serviceaccount = tpLeverandoerData == null ? null : tpLeverandoerData.getServiceAccount();
        if (Objects.isNull(serviceaccount)) {
            throw new NoTpOrdningFound("No serviceaccount found for TP-nr: " + tpnr);
        } else {
            return serviceaccount;
        }
    }

    public String leverandoerNameByTPNr(String tpnr) {
        var tpData = tpconfig.get(tpnr);
        var tpLeverandoerData = tpData == null ? null : tpData.getTpLeverandoerData();
        var name = tpLeverandoerData == null ? null : tpLeverandoerData.getTpLeverandoerName();
        if (Objects.isNull(name)) {
            throw new NoTpOrdningFound("No TP-account found for TP-nr: " + tpnr);
        } else {
            return name;
        }
    }

    public String leverandoerNameByTSSNr(String tssnr) {
        var name = tpconfig.values().stream().filter((e) -> e.getTssNumber().equals(tssnr)).
                map(TPConfigData::getTpLeverandoerData).
                map(TPLeverandoerData::getTpLeverandoerName).
                findFirst().orElse(null);
        if (Objects.isNull(name)) {
            throw new NoTpOrdningFound("No TP-account found for TSS-nr: " + tssnr);
        } else {
            return name;
        }
    }

    public JSONObject organisation(String tpnr) {
        var tpData = tpconfig.get(tpnr);
        var tpLeverandoerData = tpData == null ? null : tpData.getTpLeverandoerData();
        String serviceaccount = null;
        String orgnr = null;
        if (tpLeverandoerData != null){
            serviceaccount = tpLeverandoerData.getServiceAccount();
            orgnr = tpLeverandoerData.getOrgNr();
        }
        if (Objects.isNull(serviceaccount)) {
            throw new NoTpOrdningFound("No organisation found for TP-nr: " + tpnr);
        } else {
            try {
                return new JSONObject().put("serviceaccount", serviceaccount).put("orgnr", orgnr);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
