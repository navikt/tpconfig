package no.nav.tpconfig.server;

import io.prometheus.client.Counter;

import java.util.Objects;

public class Metrics {


    private final Runnable foundCounter;
    private final Runnable notFoundCounter;
    private final Runnable receivedCounter;

    private static final Runnable COUNTER_THAT_DOES_NOTHING = () -> { };

    public Metrics(String metricFor, String foundHelp, String notFoundHelp, String receivedHelp) {
        this.foundCounter = createCounter(metricFor + "_found", foundHelp);
        this.notFoundCounter = createCounter(metricFor + "_not_found", notFoundHelp);
        this.receivedCounter = createCounter(metricFor + "_endpoint_requests_recieved", receivedHelp);
    }

    void requestReceived() { receivedCounter.run(); }

    void found() { foundCounter.run(); }

    void notFound() { notFoundCounter.run(); }

    private Runnable createCounter(String name, String help) {
        return Objects.isNull(help) ?
                COUNTER_THAT_DOES_NOTHING
                : Counter.build()
                .name(name)
                .help(help)
                .register()
                ::inc;
    }
}
