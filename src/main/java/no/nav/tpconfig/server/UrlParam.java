package no.nav.tpconfig.server;

import io.undertow.server.HttpServerExchange;

class UrlParam {
    static String value(HttpServerExchange exchange, String paramName) {
        return exchange.getQueryParameters().get(paramName).getFirst();
    }
}
