package no.nav.tpconfig.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TpConfig {

    private Map<String,String> tpconfig = new HashMap<>();

    private static final String SPK_SERVICEACCOUNT = "srvElsam_SPK";
    private static final String KLP_SERVICEACCOUNT = "srvElsam_KLP";
    private static final String STOREBRAND_SERVICEACCOUNT = "srvElsam_Storebrand";
    private static final String GABLER_SERVICEACCOUNT = "srvElsam_Gabler";
    private static final String PTS_SERVICEACCOUNT = "srvElsam_PTS";
    private static final String OPF_SERVICEACCOUNT = "srvElsam_OPF";
    private static final String GFF_SERVICEACCOUNT = "srvElsam_GFF";

    public TpConfig() {

        addConfig("3010", SPK_SERVICEACCOUNT);

        addConfig("3100", PTS_SERVICEACCOUNT);

        addConfig("3820", OPF_SERVICEACCOUNT);

        addConfig("4575", GFF_SERVICEACCOUNT);

        addConfig("3200", KLP_SERVICEACCOUNT);
        addConfig("3250", KLP_SERVICEACCOUNT);
        addConfig("4082", KLP_SERVICEACCOUNT);

        addConfig("3110", GABLER_SERVICEACCOUNT);
        addConfig("3410", GABLER_SERVICEACCOUNT);
        addConfig("3420", GABLER_SERVICEACCOUNT);
        addConfig("3440", GABLER_SERVICEACCOUNT);
        addConfig("3610", GABLER_SERVICEACCOUNT);
        addConfig("3620", GABLER_SERVICEACCOUNT);
        addConfig("3630", GABLER_SERVICEACCOUNT);
        addConfig("3660", GABLER_SERVICEACCOUNT);
        addConfig("3675", GABLER_SERVICEACCOUNT);
        addConfig("3700", GABLER_SERVICEACCOUNT);
        addConfig("3730", GABLER_SERVICEACCOUNT);
        addConfig("3736", GABLER_SERVICEACCOUNT);
        addConfig("3750", GABLER_SERVICEACCOUNT);
        addConfig("3780", GABLER_SERVICEACCOUNT);
        addConfig("3850", GABLER_SERVICEACCOUNT);
        addConfig("3860", GABLER_SERVICEACCOUNT);
        addConfig("3880", GABLER_SERVICEACCOUNT);
        addConfig("3910", GABLER_SERVICEACCOUNT);
        addConfig("3943", GABLER_SERVICEACCOUNT);
        addConfig("4099", GABLER_SERVICEACCOUNT);
        addConfig("4151", GABLER_SERVICEACCOUNT);
        addConfig("4152", GABLER_SERVICEACCOUNT);
        addConfig("4153", GABLER_SERVICEACCOUNT);
        addConfig("4154", GABLER_SERVICEACCOUNT);
        addConfig("4155", GABLER_SERVICEACCOUNT);
        addConfig("4156", GABLER_SERVICEACCOUNT);
        addConfig("4160", GABLER_SERVICEACCOUNT);
        addConfig("4161", GABLER_SERVICEACCOUNT);
        addConfig("4162", GABLER_SERVICEACCOUNT);
        addConfig("4163", GABLER_SERVICEACCOUNT);

        addConfig("3300", STOREBRAND_SERVICEACCOUNT);
        addConfig("3670", STOREBRAND_SERVICEACCOUNT);
        addConfig("3672", STOREBRAND_SERVICEACCOUNT);
        addConfig("4081", STOREBRAND_SERVICEACCOUNT);
        addConfig("4083", STOREBRAND_SERVICEACCOUNT);
        addConfig("4095", STOREBRAND_SERVICEACCOUNT);
        addConfig("4096", STOREBRAND_SERVICEACCOUNT);
        addConfig("4098", STOREBRAND_SERVICEACCOUNT);
        addConfig("4150", STOREBRAND_SERVICEACCOUNT);
    }

    void addConfig(String tpNumber, String serviceaccount){
        if(!Objects.isNull(tpconfig.get(tpNumber)))
            throw new IllegalTpConfig("TP-number " + tpNumber + " already exists");
        if(Objects.isNull(tpNumber))
            throw new IllegalTpConfig("TP-number can't be null");
        if(Objects.isNull(serviceaccount))
            throw new IllegalTpConfig("Serviceaccount can't be null");

        tpconfig.put(tpNumber, serviceaccount);
    }

    public String serviceaccount(String tpnr) {
        var serviceaccount = tpconfig.get(tpnr);
        if(Objects.isNull(serviceaccount)) {
            throw new NoTpOrdningFound("No serviceaccount found for TP-nr: " + tpnr);
        } else {
            return serviceaccount;
        }
    }
}
