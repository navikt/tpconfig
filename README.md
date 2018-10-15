# tpconfig
Konfigurasjon og ruting mellom tp-leverandører og tp-ordninger.

###/serviceaccount/{tpnr}
Request/response eksempler:
```bash
$ curl -X GET -k https://tpconfig.nais.adeo.no/serviceaccount/3010
Output: "srvElsam_SPK", status 200

$ curl -X GET -k https://tpconfig.nais.adeo.no/serviceaccount/41604
Output: "No serviceaccount found for TP-nr: 41604", status 404
```

Kontakt Team Peon dersom du har noen spørsmål. Vi finnes blant annet på Slack, i kanalen #peon.