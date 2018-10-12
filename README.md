# tpconfig
Configuration and routing between TP-providers and TP-ordninger

###/serviceaccount/{tpnr}
Request/response examples
```bash
$ curl -X GET -k https://tpconfig.nais.adeo.no/serviceaccount/3010
Output: "srvElsam_SPK", status 200

$ curl -X GET -k https://tpconfig.nais.adeo.no/serviceaccount/41604
Output: "No serviceaccount found for TP-nr: 41604", status 404
```
