package no.nav.tpconfig.server;

import io.undertow.Undertow;
import io.undertow.server.RoutingHandler;
import io.undertow.util.StatusCodes;
import no.nav.tpconfig.domain.TpConfig;

public class Server {

    private final String IS_ALIVE_URL_PATH = "isAlive";
    private final String IS_READY_URL_PATH = "isReady";
    private final String SERVICE_ACCOUNT_PATH = "serviceaccount/{tpnr}";

    private Undertow server;

    public Server(String hostName, int port, ServiceAccount serviceAccount) {

        this.server = Undertow.builder()
                .addHttpListener(port, hostName)
                .setHandler(new RoutingHandler()
                        .get(IS_ALIVE_URL_PATH, NaisReadiness.status(StatusCodes.OK))
                        .get(IS_READY_URL_PATH, NaisReadiness.status(StatusCodes.OK))
                        .get(SERVICE_ACCOUNT_PATH, serviceAccount::tpNrToServiceAccount))
                .build();
    }

    public void run() {
        server.start();
    }

    public void stop() {
        this.server.stop();
    }
}
