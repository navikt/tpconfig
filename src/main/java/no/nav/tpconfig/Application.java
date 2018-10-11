package no.nav.tpconfig;

import no.nav.tpconfig.domain.TpConfig;
import no.nav.tpconfig.server.Server;
import no.nav.tpconfig.server.ServiceAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);
    private static final int PORT = 8080;

    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }

    void run() {
        try {
            final String hostName = InetAddress.getLocalHost().getHostName();
            final TpConfig tpConfig = new TpConfig();
            final ServiceAccount serviceAccount = new ServiceAccount(tpConfig);
            Server server = new Server(hostName, PORT, serviceAccount);
            LOG.info(String.format("Starting server on host: %s:%s", hostName, PORT));
            server.run();
        } catch(UnknownHostException e) {
            LOG.error("Could not resolve host.", e);
        }
    }
}
