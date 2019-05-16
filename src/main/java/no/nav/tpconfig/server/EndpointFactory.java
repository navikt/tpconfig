package no.nav.tpconfig.server;

import no.nav.tpconfig.domain.TpConfig;
import static no.nav.tpconfig.server.MetricsBuilder.metricFor;


public class EndpointFactory {

    private static final String TPNR_URL_PARAMETER_NAME = "tpnr";
    private static final String TSSNR_URL_PARAMETER_NAME = "tssnr";
    private static final String SERVICE_ACCOUNT_PATH = String.format("serviceaccount/{%s}", TPNR_URL_PARAMETER_NAME);
    private static final String LEVERANDOR_BY_TPNR_PATH = String.format("tpleverandoer/{%s}", TPNR_URL_PARAMETER_NAME);
    private static final String LEVERANDOR_BY_TSSNR_PATH = String.format("tpleverandoer/tssnr/{%s}", TSSNR_URL_PARAMETER_NAME);


    private final static Metrics METRICS_FOR_SERVICEACCOUNT_ENDPOINT = metricFor("tp_config_serviceaccount")
            .withFoundCounter("Antall serviceaccounts funnet basert på tpnr i request")
            .withNotFoundCounter("Antall serviceaccounts ikke funnet basert på tpnr i request")
            .withReceivedCounter("Antall requests mottatt til serviceaccount endepunkt")
            .createMetrics();
    private static final Metrics METRICS_FOR_TPNR_TO_TPLEVERANDOER = metricFor("tp_config_tpleverandoer_tpnr")
            .withFoundCounter("Antall tpleverandoerer funnet basert på tpnr i request")
            .withNotFoundCounter("Antall serviceaccounts ikke funnet basert på tpnr i request")
            .withReceivedCounter("Antall requests mottatt til tpleverandoer for tpnr endepunkt")
            .createMetrics();
    private static final Metrics METRICS_FOR_TSSNR_TO_TPLEVERANDOER = metricFor("tp_config_tpleverandoer_tssnr")
            .withFoundCounter("Antall tpleverandoerer funnet basert på tssnr i request")
            .withNotFoundCounter("Antall tpleverandoerer ikke funnet basert på tssnr i request")
            .withReceivedCounter("Antall requests mottatt til tpleverandoer for tssnr endepunkt")
            .createMetrics();

    public static Endpoint<String> createServiceAccountEndpoint(TpConfig tpConfig){
        return new Endpoint<>(
                METRICS_FOR_SERVICEACCOUNT_ENDPOINT,
                Utlis.urlParamExtractor(TPNR_URL_PARAMETER_NAME),
                tpConfig::serviceaccount,
                SERVICE_ACCOUNT_PATH
        );
    }

    public static Endpoint<String> createTpNrToTPLeverandoerEndpoint(TpConfig tpConfig){
        return new Endpoint<>(
                METRICS_FOR_TPNR_TO_TPLEVERANDOER,
                Utlis.urlParamExtractor(TPNR_URL_PARAMETER_NAME),
                tpConfig::leverandoerNameByTPNr,
                LEVERANDOR_BY_TPNR_PATH
        );
    }

    public static Endpoint<String> createTssNrToTPLeverandoerEndpoint(TpConfig tpConfig){
        return new Endpoint<>(
                METRICS_FOR_TSSNR_TO_TPLEVERANDOER,
                Utlis.urlParamExtractor(TSSNR_URL_PARAMETER_NAME),
                tpConfig::leverandoerNameByTSSNr,
                LEVERANDOR_BY_TSSNR_PATH
        );
    }

}
