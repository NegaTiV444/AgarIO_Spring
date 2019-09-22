#!/bin/bash

docker build --force-rm -t negativ444/agario-maven .
sudo docker-compose up
