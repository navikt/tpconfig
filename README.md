# tpconfig
Konfigurasjon og ruting mellom tp-leverandører og tp-ordninger.

### /organisation/{tpnr}
Request/response eksempler:
```bash
$ curl -X GET -k https://tpconfig.nais.adeo.no/organisation/3010
Output: 
{
    "serviceaccount": "srvElsam_SPK",
    "orgnr": "12345678911"
}

$ curl -X GET -k https://tpconfig.nais.adeo.no/organisation/41604
Output: "No organisation found for TP-nr: 41604", status 404
```

### /serviceaccount/{tpnr}
Request/response eksempler:
```bash
$ curl -X GET -k https://tpconfig.nais.adeo.no/serviceaccount/3010
Output: 
{
    "serviceaccount": "srvElsam_SPK",
    "orgnr": "12345678911"
}

$ curl -X GET -k https://tpconfig.nais.adeo.no/serviceaccount/41604
Output: "No serviceaccount found for TP-nr: 41604", status 404
```

#### Metrikker
Grafana dashboards brukes for å f.eks. monitorere minne, cpu-bruk og andre metrikker. 
Se [tpconfig grafana dasboard](https://grafana.adeo.no/d/wcmOPO0ik/tpconfig?orgId=1)

#### Logging
[Kibana](https://logs.adeo.no/app/kibana) benyttes til logging. Søk på f.eks. ```application:tpconfig AND environment:q``` for logginnslag fra preprod.

#### Bygging
Github Actions benyttes til bygging.

Kontakt Team Samhandling dersom du har noen spørsmål. Vi finnes blant annet på Slack, i kanalen [#samhandling_pensjonsområdet](https://nav-it.slack.com/archives/CQ08JC3UG)
