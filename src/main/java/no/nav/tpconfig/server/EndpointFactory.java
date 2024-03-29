package no.nav.tpconfig.server;

import no.nav.tpconfig.domain.TpConfig;
import org.json.JSONObject;

import static no.nav.tpconfig.server.MetricsBuilder.metricFor;


public class EndpointFactory {

    private static final String TPNR_URL_PARAMETER_NAME = "tpnr";
    private static final String TSSNR_URL_PARAMETER_NAME = "tssnr";
    private static final String TPNR_ORGNR_URL_PARAMETER_NAME = "tpnr_orgnr";
    private static final String SERVICE_ACCOUNT_PATH = String.format("serviceaccount/{%s}", TPNR_URL_PARAMETER_NAME);
    private static final String LEVERANDOR_BY_TPNR_PATH = String.format("tpleverandoer/{%s}", TPNR_URL_PARAMETER_NAME);
    private static final String VALIDATE_LEVERANDOR_BY_TPNR_AND_ORGNR_PATH =
            String.format("organisation/validate/{%s}", TPNR_ORGNR_URL_PARAMETER_NAME);
    private static final String LEVERANDOR_BY_TSSNR_PATH = String.format("tpleverandoer/tssnr/{%s}", TSSNR_URL_PARAMETER_NAME);
    private static final String TSSNR_BY_TPNR_PATH = String.format("tssnr/{%s}", TPNR_URL_PARAMETER_NAME);
    private static final String ORGANISATION_PATH = String.format("organisation/{%s}", TPNR_URL_PARAMETER_NAME);


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
    private static final Metrics METRICS_FOR_VALIDATE_LEVERANDOR_BY_TPNR_AND_ORGNR = metricFor("tp_config_validate_tpnr_orgnr")
            .withFoundCounter("Antall tpleverandoerer validert basert på tpnr og orgnr i request")
            .withNotFoundCounter("Antall tpleverandoerer ikke validert basert på tpnr og orgnr i request")
            .withReceivedCounter("Antall requests mottatt til validering basert på tpnr og orgnr endepunkt")
            .createMetrics();
    private static final Metrics METRICS_FOR_TSSNR_TO_TPLEVERANDOER = metricFor("tp_config_tpleverandoer_tssnr")
            .withFoundCounter("Antall tpleverandoerer funnet basert på tssnr i request")
            .withNotFoundCounter("Antall tpleverandoerer ikke funnet basert på tssnr i request")
            .withReceivedCounter("Antall requests mottatt til tpleverandoer for tssnr endepunkt")
            .createMetrics();
    private static final Metrics METRICS_FOR_TPNR_TO_TSSNR = metricFor("tp_config_tssnr_tpnr")
            .withFoundCounter("Antall tssnr funnet basert på tpnr i request")
            .withNotFoundCounter("Antall tssnr ikke funnet basert på tpnr i request")
            .withReceivedCounter("Antall requests mottatt til tssnr for tpnr endepunkt")
            .createMetrics();
    private static final Metrics METRICS_FOR_ORGANISATION_ENDPOINT = metricFor("tp_config_organisation")
            .withReceivedCounter("Antall requests mottatt til orgnaisation endepunkt")
            .createMetrics();

    public static Endpoint<String, String> createServiceAccountEndpoint(TpConfig tpConfig) {
        return new Endpoint<>(
                METRICS_FOR_SERVICEACCOUNT_ENDPOINT,
                Utlis.urlParamExtractor(TPNR_URL_PARAMETER_NAME),
                tpConfig::serviceaccount,
                SERVICE_ACCOUNT_PATH
        );
    }

    public static Endpoint<String, String> createTpNrToTPLeverandoerEndpoint(TpConfig tpConfig) {
        return new Endpoint<>(
                METRICS_FOR_TPNR_TO_TPLEVERANDOER,
                Utlis.urlParamExtractor(TPNR_URL_PARAMETER_NAME),
                tpConfig::leverandoerNameByTPNr,
                LEVERANDOR_BY_TPNR_PATH
        );
    }

    public static Endpoint<String, Boolean> createValidateLeverandorByTpnrAndOrgnrEndpoint(TpConfig tpConfig) {
        return new Endpoint<>(
                METRICS_FOR_VALIDATE_LEVERANDOR_BY_TPNR_AND_ORGNR,
                Utlis.urlParamExtractor(TPNR_ORGNR_URL_PARAMETER_NAME),
                tpConfig::validateTpLeverandorForTpOrdning,
                VALIDATE_LEVERANDOR_BY_TPNR_AND_ORGNR_PATH
        );
    }

    public static Endpoint<String, String> createTssNrToTPLeverandoerEndpoint(TpConfig tpConfig) {
        return new Endpoint<>(
                METRICS_FOR_TSSNR_TO_TPLEVERANDOER,
                Utlis.urlParamExtractor(TSSNR_URL_PARAMETER_NAME),
                tpConfig::leverandoerNameByTSSNr,
                LEVERANDOR_BY_TSSNR_PATH
        );
    }

    public static Endpoint<String, String> createTpNrToTssNrEndpoint(TpConfig tpConfig) {
        return new Endpoint<>(
                METRICS_FOR_TPNR_TO_TSSNR,
                Utlis.urlParamExtractor(TPNR_URL_PARAMETER_NAME),
                tpConfig::TSSNrByTPNr,
                TSSNR_BY_TPNR_PATH
        );
    }

    public static Endpoint<String, JSONObject> createOrganisationEndpoint(TpConfig tpConfig) {
        return new Endpoint<>(
                METRICS_FOR_ORGANISATION_ENDPOINT,
                Utlis.urlParamExtractor(TPNR_URL_PARAMETER_NAME),
                tpConfig::organisation,
                ORGANISATION_PATH
        );
    }
}
