package no.nav.tpconfig.server;

import io.undertow.server.HttpHandler;

class NaisReadiness {

    static HttpHandler status(int status){ return exchange -> exchange.setStatusCode(status); }
}