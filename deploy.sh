#!/bin/bash

mvn clean package

docker build -f Dockerfile . -t lassulfi/starwars-api