package no.nav.tpconfig.server;

import io.prometheus.client.exporter.MetricsServlet;
import io.undertow.Handlers;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;

import javax.servlet.ServletException;

class MetricsEndpoint {

    private final String DEPLOYMENT_NAME = "metricsServlet";

    PathHandler metrics(String rootUrlPath, String metricsUrlPath) throws ServletException {
        DeploymentInfo servletBuilder = Servlets.deployment()
                .setClassLoader(Server.class.getClassLoader())
                .setDeploymentName(DEPLOYMENT_NAME)
                .setContextPath(rootUrlPath)
                .addServlets(Servlets.servlet(MetricsServlet.class).addMapping(metricsUrlPath));

        var manager = Servlets.defaultContainer().addDeployment(servletBuilder);
        manager.deploy();
        return Handlers.path().addPrefixPath(rootUrlPath, manager.start());
    }
}
