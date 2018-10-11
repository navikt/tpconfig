package no.nav.tpconfig.server;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;
import no.nav.tpconfig.domain.IllegalTpConfig;
import no.nav.tpconfig.domain.NoTpOrdningFound;
import no.nav.tpconfig.domain.TpConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceAccount {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceAccount.class);
    private static final String TPNR_URL_PARAMETER_NAME = "tpnr";
    private final TpConfig tpConfig;

    public ServiceAccount(TpConfig tpConfig) {
        this.tpConfig = tpConfig;
    }

    void tpNrToServiceAccount(HttpServerExchange exchange) {

        LOG.info("Request url: " + exchange.getRequestURL());

        try {
            String tpnr = urlParamValue(exchange, TPNR_URL_PARAMETER_NAME);
            String serviceAccount = tpConfig.serviceaccount(tpnr);
            exchange.setStatusCode(StatusCodes.OK);
            exchange.getResponseSender().send(serviceAccount);
        } catch (NoTpOrdningFound e) {
            LOG.warn(e.getMessage());
            exchange.setStatusCode(StatusCodes.NOT_FOUND);
            exchange.getResponseSender().send(e.getMessage());
        }
    }

    private static String urlParamValue(HttpServerExchange exchange, String paramName) {
        return exchange.getQueryParameters().get(paramName).getFirst();
    }
}
