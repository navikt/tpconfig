package no.nav.tpconfig.server;

import io.undertow.Undertow;
import io.undertow.server.RoutingHandler;
import io.undertow.util.StatusCodes;

import javax.servlet.ServletException;

public class Server {

    private final String ROOT_URL_PATH = "/";
    private final String METRICS_URL_PATH =  "metrics";
    private final String IS_ALIVE_URL_PATH = "isAlive";
    private final String IS_READY_URL_PATH = "isReady";
    private final String SERVICE_ACCOUNT_PATH = "serviceaccount/{tpnr}";

    private Undertow server;

    public Server(String hostName, int port, ServiceAccount serviceAccount) throws ServletException {

        MetricsEndpoint metricsEndpoint = new MetricsEndpoint();

        this.server = Undertow.builder()
                .addHttpListener(port, hostName)
                .setHandler(new RoutingHandler()
                        .get(IS_ALIVE_URL_PATH, NaisReadiness.status(StatusCodes.OK))
                        .get(IS_READY_URL_PATH, NaisReadiness.status(StatusCodes.OK))
                        .get(SERVICE_ACCOUNT_PATH, serviceAccount::tpNrToServiceAccount)
                        .get(METRICS_URL_PATH, metricsEndpoint.metrics(ROOT_URL_PATH, METRICS_URL_PATH)))
                .build();
    }

    public void run() {
        server.start();
    }

    void stop() {
        this.server.stop();
    }
}
