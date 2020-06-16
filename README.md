# LoraWAN infrastructure 

Repository containing the required files to deploy on Kubernetes a LoraWAN ready infrastructure.

It uses chirpstack as our LoRaWAN server running in arm architecture.

- [Chirpstack](https://www.chirpstack.io/)
- Postgres
- Redis
- Mosquitto
- Flink

## Images

All the images are built multi-arch.  More info in: [fogguru images](https://hub.docker.com/u/fogguru) 

## How to

### Setup 

To install and deploy the services use:

```
 $ sh start_infra.sh create
```

To remove use: 

```
 $ sh start_infra.sh delete
```

### Kubernetes

If you want to setup Kubernetes in a raspberrypi follow this [quick guide](kuberetes/install.md)

### Flink deployment

Deployment: 

```
 $ kubectl apply -f flink/.
```

Remove deployment:

```
 $ kubectl delete -f flink/.
```

## Configuration

### Service

In [services.yml](services.yaml), the services are exposed. 

We use externalIP to expose some services to communicate with the gateway, you have to use your external ip to connect with your gateway.

### Chirpstack configuration

All the configuration for chirpstack are loaded using Kubernetes configmap, the configs are loaded from [docker/configuration](docker/configuration)

To change the configuration parameters, please refer to [chirpstack project](https://www.chirpstack.io/overview/).

### Monitoring

Currently we use a separated deployment of Prometheus in our centralized server. We indicate [this monitoring project](https://github.com/uschtwill/docker_monitoring_logging_alerting)

Data is scrapped from chirpstack-application-server, chirpstack-simulator and Kubernetes ([cadvisor](https://github.com/google/cadvisor/tree/master/deploy/kubernetes)) with a centralized deployment of prometheus.

## Tests

### Simulator

We use the chirpstack simulator to test the infrastructure, more in [simulator](https://github.com/FogGuru/lorawan-infra/blob/master/simulator/README.md)


