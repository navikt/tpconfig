package no.nav.tpconfig.server;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;
import no.nav.tpconfig.domain.NoTpOrdningFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;


public class Endpoint<E> implements HttpHandler {

    private static final Logger LOG = LoggerFactory.getLogger(EndpointFactory.class);
    private Metrics metrics;
    private Function<HttpServerExchange, E> extractInputFromExcange;
    private Function<E, String> createResultFromInput;
    private String path;

    Endpoint(Metrics metrics, Function<HttpServerExchange, E> extractInputFromExcange, Function<E, String> createResultFromInput, String path) {
        this.metrics = metrics;
        this.extractInputFromExcange = extractInputFromExcange;
        this.createResultFromInput = createResultFromInput;
        this.path = path;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        metrics.requestReceived();
        LOG.info("Request url: " + exchange.getRequestURL());
        try {
            E state = extractInputFromExcange.apply(exchange);
            var responseString = createResultFromInput.apply(state);
            metrics.found();
            exchange.setStatusCode(StatusCodes.OK);
            exchange.getResponseSender().send(responseString);
        } catch (NoTpOrdningFound e) {
            LOG.warn(e.getMessage());
            metrics.notFound();
            exchange.setStatusCode(StatusCodes.NOT_FOUND);
            exchange.getResponseSender().send(e.getMessage());
        }

    }

    public String getPath() {
        return path;
    }
}
