package no.nav.tpconfig.server;

import io.undertow.Undertow;
import io.undertow.server.RoutingHandler;
import io.undertow.util.StatusCodes;

import javax.servlet.ServletException;
import java.util.Arrays;

public class Server {

    private static final String ROOT_URL_PATH = "/";
    private static final String METRICS_URL_PATH = "metrics";
    private static final String IS_ALIVE_URL_PATH = "isAlive";
    private static final String IS_READY_URL_PATH = "isReady";

    private Undertow server;

    public Server(String hostName, int port, Endpoint... endpoints) throws ServletException {

        var metricsEndpoint = new MetricsEndpoint();

        var handler = new RoutingHandler()
                .get(IS_ALIVE_URL_PATH, NaisReadiness.status(StatusCodes.OK))
                .get(IS_READY_URL_PATH, NaisReadiness.status(StatusCodes.OK))
                .get(METRICS_URL_PATH, metricsEndpoint.metrics(ROOT_URL_PATH, METRICS_URL_PATH));
        Arrays.stream(endpoints).forEach(endpoint -> handler.get(endpoint.getPath(), endpoint));

        this.server = Undertow.builder()
                .addHttpListener(port, hostName)
                .setHandler(handler)
                .build();
    }

    public void run() {
        server.start();
    }

    void stop() {
        this.server.stop();
    }
}
