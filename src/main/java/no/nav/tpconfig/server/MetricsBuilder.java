package no.nav.tpconfig.server;

class MetricsBuilder {
    private String metricFor;
    private String foundHelp;
    private String notFoundHelp;
    private String receivedHelp;

    private MetricsBuilder(String metricFor) {
        this.metricFor = metricFor;
    }

    static MetricsBuilder metricFor(String metricFor) {
        return new MetricsBuilder(metricFor);
    }

    MetricsBuilder withFoundCounter(String foundHelp) {
        this.foundHelp = foundHelp;
        return this;
    }

    MetricsBuilder withNotFoundCounter(String notFoundHelp) {
        this.notFoundHelp = notFoundHelp;
        return this;
    }

    MetricsBuilder withReceivedCounter(String receivedHelp) {
        this.receivedHelp = receivedHelp;
        return this;
    }

    Metrics createMetrics() {
        return new Metrics(metricFor, foundHelp, notFoundHelp, receivedHelp);
    }
}