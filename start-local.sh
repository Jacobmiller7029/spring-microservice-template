#!/bin/bash
# Script for starting application locally.

echo "[SCRIPT] - Clean operation(Offline mode)"
sudo mvn -q clean -o

if [ $? -eq 1 ]; then
    echo "[SCRIPT] - Offline clean failed; attempting Clean in online mode."
    sudo mvn -q clean
fi

if [ $? -eq 0 ]; then
    echo "[SCRIPT] - Clean successful."
else
    echo "[SCRIPT] - Clean Failed. Exiting."
    exit 1;
fi

echo "[SCRIPT] - Installing, skipping tests..."
sudo mvn -q install -DskipTests

if [ $? -eq 0 ]; then
    echo "[SCRIPT] - Install successfully finished."
else
    echo "[SCRIPT] - Error installing. Exiting."
    exit 1;
fi

echo "[SCRIPT] - Initializing lane tracking application..."
sudo docker-compose -f ./docker/docker-compose.yml up

echo "[SCRIPT] - Shutting down application..."

if [ $# -eq 0  ] || [ $1 != "-c" ]; then
    sudo docker-compose -f ./docker/docker-compose.yml down
elif [ $1 = "-c" ]; then
    echo "[SCRIPT] - Docker cleaning up containers..."
    sudo docker-compose -f ./docker/docker-compose.yml down --rmi 'local'
fi

echo "[SCRIPT] - Shut down successful."

echo "-TERMINATED-"

exit 0