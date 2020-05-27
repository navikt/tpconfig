FROM navikt/java:11-appdynamics
COPY target/app.jar /app/app.jar
ENV APPD_ENABLED=true
