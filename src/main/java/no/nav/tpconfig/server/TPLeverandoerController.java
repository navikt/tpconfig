package no.nav.tpconfig.server;

import io.prometheus.client.Counter;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;
import no.nav.tpconfig.domain.NoTpOrdningFound;
import no.nav.tpconfig.domain.TpConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TPLeverandoerController {

    private static final Counter sumByTPNRRequestsRecieved = Counter.build()
            .name("tp_config_tpleverandoer_tpnr_endpoint_requests_recieved")
            .help("Antall requests mottatt til tpleverandoer for tpnr endepunkt").register();

    private static final Counter sumByTSSNRRequestsRecieved = Counter.build()
            .name("tp_config_tpleverandoer_tssnr_endpoint_requests_recieved")
            .help("Antall requests mottatt til tpleverandoer for tssnr endepunkt").register();

    private static final Counter tPLeverandoerFoundForTPNR = Counter.build()
            .name("tpconfig_tpleverandoer_tpnr_found")
            .help("Antall tpleverandoerer funnet basert på tpnr i request").register();

    private static final Counter tPLeverandoerFoundForTSSNR = Counter.build()
            .name("tpconfig_tpleverandoer_tssnr_found")
            .help("Antall tpleverandoerer funnet basert på tssnr i request").register();

    private static final Counter tPLeverandoerNotFoundForTPNR = Counter.build()
            .name("tpconfig_tpleverandoer_tpnr_not_found")
            .help("Antall tpleverandoerer ikke funnet basert på tpnr i request").register();

    private static final Counter tPLeverandoerNotFoundForTSSNR = Counter.build()
            .name("tpconfig_tpleverandoer_tssnr_not_found")
            .help("Antall tpleverandoerer ikke funnet basert på tssnr i request").register();

    private final TpConfig tpConfig;
    private static final String TPNR_URL_PARAMETER_NAME = "tpnr";
    private static final String TSSNR_URL_PARAMETER_NAME = "tssnr";

    private static final Logger LOG = LoggerFactory.getLogger(TPLeverandoerController.class);

    public TPLeverandoerController(TpConfig tpConfig) {
        this.tpConfig = tpConfig;
    }

    public void tpNrToTPLeverandoer(HttpServerExchange exchange) {
        sumByTPNRRequestsRecieved.inc();
        LOG.info("Request url: " + exchange.getRequestURL());

        try {
            var tpnr = urlParamValue(exchange, TPNR_URL_PARAMETER_NAME);
            var tpleverandoerName = tpConfig.leverandoerNameByTPNr(tpnr);
            tPLeverandoerFoundForTPNR.inc();
            exchange.setStatusCode(StatusCodes.OK);
            exchange.getResponseSender().send(tpleverandoerName);
        } catch (NoTpOrdningFound e) {
            LOG.warn(e.getMessage());
            tPLeverandoerNotFoundForTPNR.inc();
            exchange.setStatusCode(StatusCodes.NOT_FOUND);
            exchange.getResponseSender().send(e.getMessage());
        }

        throw new UnsupportedOperationException();
    }

    public void tssNrToTPLeverandoer(HttpServerExchange exchange) {
        sumByTSSNRRequestsRecieved.inc();
        LOG.info("Request url: " + exchange.getRequestURL());

        try {
            var tssnr = urlParamValue(exchange, TSSNR_URL_PARAMETER_NAME);
            var tpleverandoerName = tpConfig.leverandoerNameByTSSNr(tssnr);
            tPLeverandoerFoundForTSSNR.inc();
            exchange.setStatusCode(StatusCodes.OK);
            exchange.getResponseSender().send(tpleverandoerName);
        } catch (NoTpOrdningFound e) {
            LOG.warn(e.getMessage());
            tPLeverandoerNotFoundForTSSNR.inc();
            exchange.setStatusCode(StatusCodes.NOT_FOUND);
            exchange.getResponseSender().send(e.getMessage());
        }

        throw new UnsupportedOperationException();
    }


    private static String urlParamValue(HttpServerExchange exchange, String paramName) {
        return exchange.getQueryParameters().get(paramName).getFirst();
    }
}
