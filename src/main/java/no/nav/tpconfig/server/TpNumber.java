package no.nav.tpconfig.server;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;
import no.nav.tpconfig.domain.NoTpOrdningFound;
import no.nav.tpconfig.domain.TpOrdning;
import org.apache.log4j.Logger;

class TpNumber {

    private static final Logger LOG = Logger.getLogger(TpNumber.class);

    static void serviceAccount(HttpServerExchange exchange) {

        LOG.info(exchange.getRequestURL());

        try {
            String tpnr = UrlParam.getParamValue(exchange, "tpnr");
            String serviceAccount = TpOrdning.tpOrdningFromTpNumber(tpnr).tpLeverandoer().serviceAccount();
            exchange.setStatusCode(StatusCodes.OK);
            exchange.getResponseSender().send(serviceAccount);
        } catch (NoTpOrdningFound e) {
            LOG.warn(e.getMessage());
            exchange.setStatusCode(StatusCodes.NOT_FOUND);
            exchange.getResponseSender().send(e.getMessage());
        }
    }
}
