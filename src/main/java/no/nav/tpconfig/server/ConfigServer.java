package no.nav.tpconfig.server;

import io.undertow.Undertow;
import io.undertow.server.RoutingHandler;
import io.undertow.util.StatusCodes;

public class ConfigServer {

    private final String IS_ALIVE_URL_PATH = "isAlive";
    private final String IS_READY_URL_PATH = "isReady";
    private final String TP_NR_FOR_LEVERANDOER_PATH = "serviceaccount/{tpnr}";
    private final String TSS_NR_FOR_LEVERANDOER_PATH = "tpleverandoer/{tssnr}"; //TODO: Implement

    private Undertow server;
    private String hostName;
    private int port;

    public ConfigServer(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    public void runServer() {
        this.server = Undertow.builder()
                .addHttpListener(port, hostName)
                .setHandler(new RoutingHandler()
                        .get(IS_ALIVE_URL_PATH, NaisReadiness.status(StatusCodes.OK))
                        .get(IS_READY_URL_PATH, NaisReadiness.status(StatusCodes.OK))
                        .get(TP_NR_FOR_LEVERANDOER_PATH, TpNumber::serviceAccount))
                .build();
        server.start();
    }

    public void stopServer() {
        this.server.stop();
    }
}
