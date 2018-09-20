package no.nav.tpconfig;

import no.nav.tpconfig.server.ConfigServer;

import java.net.InetAddress;
import java.net.UnknownHostException;

class Application {

    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }

    void run() {
        try {
            String hostName = InetAddress.getLocalHost().getHostName();
            ConfigServer configServer = new ConfigServer(hostName, 8080);
            configServer.run();
        } catch(UnknownHostException e) {
            //TODO: Log
        }
    }
}
