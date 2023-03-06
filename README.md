# Spring Boot Open Telemetry Demo
This is a POC for demonstrating how to use Open Telemetry in SpringBoot applications using Jaeger.

## How to run?

```shell
$ ./run.sh start
```

* Access http://localhost:8081/api/products
* Access http://localhost:8082/api/promotions
* Go to Jaeger UI http://localhost:16686, select the service and Find Traces.