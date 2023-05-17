## Start

### Run Docker for setting up local service

```
$ docker-compose up
```

### Run the app

```
// -X will turn on debug mode
$  ./mvnw spring-boot:run -X
```

### Usage

Get the most recent created device from the api

```
$ curl -X GET -H "Content-type: application/json" "http://localhost:8080/load"
```

Get all the targets

```
$ curl -X GET -H "Content-type: application/json" "http://localhost:8080/targets"
```

Create a target

```
$ curl -X POST -H "Content-type: application/json" -d '{"deviceId": "<given_device_id>", "targetNumber": "<given_target_number>"}' "http://localhost:8080/targets"
```

Update a target

```
$ curl -X PUT -H "Content-type: application/json" -d '{"deviceId": "<given_device_id>", "targetNumber": "<given_target_number>"}' "http://localhost:8080/target/<target_id>"
```

### Admin page for database

```
# detail in docker-compose.yml
http://localhost:9000
```

### Third party APIs
[mockAPI](https://mockapi.io/projects/646337aa4dca1a6613574ca8)