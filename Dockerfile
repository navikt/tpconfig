FROM navikt/java:11
COPY target/app.jar /app/app.jar
ENV APPD_ENABLED=true
