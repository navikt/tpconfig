package no.nav.tpconfig;

import no.nav.tpconfig.domain.TpConfig;
import no.nav.tpconfig.server.Server;
import no.nav.tpconfig.server.ServiceAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.prometheus.client.hotspot.DefaultExports;

import javax.servlet.ServletException;
import java.net.InetAddress;
import java.net.UnknownHostException;

class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);
    private static final int PORT = 8080;

    public static void main(String[] args) {
        var app = new Application();
        app.run();
    }

    void run() {
        try {
            initializeJvmMetrics();
            final var hostName = InetAddress.getLocalHost().getHostName();
            final var tpConfig = new TpConfig();
            final var serviceAccount = new ServiceAccount(tpConfig);
            var server = new Server(hostName, PORT, serviceAccount);
            LOG.info(String.format("Starting server on host: %s:%s", hostName, PORT));
            server.run();
        } catch(UnknownHostException e) {
            LOG.error("Could not resolve host.", e);
            System.exit(1);
        } catch (ServletException e) {
            LOG.error("Could not start metrics servlet.", e);
            System.exit(1);
        }
    }

    private void initializeJvmMetrics() {
        DefaultExports.initialize();
    }
}
