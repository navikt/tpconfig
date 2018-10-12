package no.nav.tpconfig.server;

import io.prometheus.client.Counter;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;
import no.nav.tpconfig.domain.NoTpOrdningFound;
import no.nav.tpconfig.domain.TpConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceAccount {

    private static final Counter sumRequestsRecieved = Counter.build()
            .name("sum_requests_recieved")
            .help("Antall requests mottatt til serviceaccount endepunkt").register();

    private static final Counter serviceAccountFound = Counter.build()
            .name("sum_service_accounts_found")
            .help("Antall serviceaccounts funnet basert på tpnr i request").register();

    private static final Counter serviceAccountNotFound = Counter.build()
            .name("sum_service_accounts_not_found")
            .help("Antall serviceaccounts ikke funnet basert på tpnr i request").register();

    private static final Logger LOG = LoggerFactory.getLogger(ServiceAccount.class);
    private static final String TPNR_URL_PARAMETER_NAME = "tpnr";
    private final TpConfig tpConfig;

    public ServiceAccount(TpConfig tpConfig) {
        this.tpConfig = tpConfig;
    }

    void tpNrToServiceAccount(HttpServerExchange exchange) {

        sumRequestsRecieved.inc();
        LOG.info("Request url: " + exchange.getRequestURL());

        try {
            String tpnr = urlParamValue(exchange, TPNR_URL_PARAMETER_NAME);
            String serviceAccount = tpConfig.serviceaccount(tpnr);
            exchange.setStatusCode(StatusCodes.OK);
            exchange.getResponseSender().send(serviceAccount);
            serviceAccountFound.inc();
        } catch (NoTpOrdningFound e) {
            LOG.warn(e.getMessage());
            exchange.setStatusCode(StatusCodes.NOT_FOUND);
            exchange.getResponseSender().send(e.getMessage());
            serviceAccountNotFound.inc();
        }
    }

    private static String urlParamValue(HttpServerExchange exchange, String paramName) {
        return exchange.getQueryParameters().get(paramName).getFirst();
    }
}
