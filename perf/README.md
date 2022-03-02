To run the performance script:

docker run -i --network host grafana/k6 run - <./k6perf.js

*NOTE*: the above only works in Linux because docker "--network host" does not work on Windows and Mac.

== Mac OS X

1. Install k6 (brew install k6)
2. Run the performance with k6

```shell
$ k6 run k6perf.js
```