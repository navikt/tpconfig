package no.nav.tpconfig.server;

import io.undertow.server.HttpServerExchange;

import java.util.function.Function;

public class Utlis {


    static String urlParamValue(HttpServerExchange exchange, String paramName) {
        return exchange.getQueryParameters().get(paramName).getFirst();
    }

    static Function<HttpServerExchange, String> urlParamExtractor(String paramName) {
        return exchange -> urlParamValue(exchange, paramName);
    }
}
