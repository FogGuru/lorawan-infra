#!/bin/bash
#Living lab lorawan infrastructure

ERROR_MSG="Please chose one of the two modes: \\n a) Create: sh start_infra.sh create \\n b) Delete: sh start_infra.sh delete"

system_initialize() {

  echo "Starting the creation of Volumes and Deployment"

  kubectl apply -f chirpstack-volumes.yaml 
  kubectl apply -f mosquitto/mosquitto.yml
  kubectl apply -f postgres/postgresql.yml
  kubectl apply -f postgres/postgres-configmap.yaml
  kubectl apply -f proxy/proxy-deployment.yml 
  kubectl apply -k redis/.

  echo "Setting up external services"
  kubectl apply -f services.yaml

  echo "Applying chirpstack configurations into Postgresql"
  kubectl exec -i service/postgresql  -- psql -v ON_ERROR_STOP=1 postgresdb --username postgresadmin <<-EOSQL
    create role chirpstack_ns with login password 'chirpstack_ns';
    create database chirpstack_ns with owner chirpstack_ns;
EOSQL

  kubectl exec -i service/postgresql -- psql -v ON_ERROR_STOP=1 postgresdb --username postgresadmin <<-EOSQL
    create role chirpstack_as with login password 'chirpstack_as';
    create database chirpstack_as with owner chirpstack_as;
EOSQL

  kubectl exec -i service/postgresql -- psql -v ON_ERROR_STOP=1 --username postgresadmin --dbname="chirpstack_as" <<-EOSQL
    create extension pg_trgm;
EOSQL

  kubectl exec -i service/postgresql -- psql -v ON_ERROR_STOP=1 --username postgresadmin --dbname="chirpstack_as" <<-EOSQL
     create extension hstore;
EOSQL

  echo "Loading configuration files of chirpstack"
  kubectl create configmap example --from-file=docker/configuration

  echo "Starting chirpstack deployment"
  kubectl apply -f chirpstack.yml

  echo "Initialization done"
}

system_delete(){
  echo "Deleting deployments, volumes and services"
  kubectl delete deployment.apps/proxy deployment.apps/postgresql deployment.apps/nodered deployment.apps/mosquitto deployment.apps/live-demo
  kubectl delete pvc mosquitto postgres-pv-claim postgresinit-pv-claim
  kubectl delete pv mosquitto-pv-volume postgres-pv-volume postgresinit-pv-volume
  kubectl delete -f services.yaml
  echo "Done"
}

if [ $# -eq 1 ]; then
  if [ "$1" == "create" ]; then
    system_initialize
  elif [ "$1" == "delete" ]; then
    system_delete
  else
    echo "$ERROR_MSG"
  fi
else
  echo "$ERROR_MSG"
fi


