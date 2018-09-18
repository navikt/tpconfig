package no.nav.tpconfig;

import no.nav.tpconfig.server.ConfigServer;

class Application {

    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }

    void run() {
        ConfigServer configServer = new ConfigServer("localhost", 8080);
        configServer.runServer();
        Runtime.getRuntime().addShutdownHook(new Thread(configServer::stopServer));
    }
}
