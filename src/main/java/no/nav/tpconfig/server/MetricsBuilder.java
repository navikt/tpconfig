package no.nav.tpconfig.server;

public class MetricsBuilder {
    private String metricFor;
    private String foundHelp;
    private String notFoundHelp;
    private String receivedHelp;

    public MetricsBuilder(String metricFor) {
        this.metricFor = metricFor;
    }

    public static MetricsBuilder metricFor(String metricFor) {
        return new MetricsBuilder(metricFor);
    }

    public MetricsBuilder withFoundCounter(String foundHelp) {
        this.foundHelp = foundHelp;
        return this;
    }

    public MetricsBuilder withNotFoundCounter(String notFoundHelp) {
        this.notFoundHelp = notFoundHelp;
        return this;
    }

    public MetricsBuilder withReceivedCounter(String receivedHelp) {
        this.receivedHelp = receivedHelp;
        return this;
    }

    public Metrics createMetrics() {
        return new Metrics(metricFor, foundHelp, notFoundHelp, receivedHelp);
    }
}