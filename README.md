# tpconfig
Konfigurasjon og ruting mellom tp-leverandører og tp-ordninger.

### /serviceaccount/{tpnr}
Request/response eksempler:
```bash
$ curl -X GET -k https://tpconfig.nais.adeo.no/serviceaccount/3010
Output: "srvElsam_SPK", status 200

$ curl -X GET -k https://tpconfig.nais.adeo.no/serviceaccount/41604
Output: "No serviceaccount found for TP-nr: 41604", status 404
```

#### Metrikker
Grafana dashboards brukes for å f.eks. monitorere minne, cpu-bruk og andre metrikker. 
Se [tpconfig grafana dasboard](https://grafana.adeo.no/d/wcmOPO0ik/tpconfig?orgId=1)

#### Logging
[Kibana](https://logs.adeo.no/app/kibana) benyttes til logging. Søk på f.eks. ```application:tpconfig AND environment:q``` for logginnslag fra preprod.

#### Bygging
Jenkins benyttes til bygging. Status på bygg finner du her: [tpconfig jenkins](https://jenkins-peon.adeo.no/job/tpconfig/)

Kontakt Team Peon dersom du har noen spørsmål. Vi finnes blant annet på Slack, i kanalen [#peon](https://nav-it.slack.com/messages/C6M80587R/)
