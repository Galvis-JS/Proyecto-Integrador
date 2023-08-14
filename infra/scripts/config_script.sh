#!/bin/bash

# Actualizar el sistema
sudo apt-get update
sudo apt-get upgrade -y

# Instalar Docker
if ! command -v docker &>/dev/null; then
  echo "Instalación de Docker"
  curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
  sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu focal stable"
  sudo apt update
  sudo apt install -y docker-ce
else
  echo "Docker ya está instalado"
  docker --version
fi

# Instalar Nginx
if ! command -v nginx &>/dev/null; then
  echo "Instalación de Nginx"
  sudo apt update
  sudo apt upgrade
  sudo apt install -y nginx
else
  echo "Nginx ya está instalado"
  nginx -v
fi

# Instalar Java 17
if ! command -v java &>/dev/null; then
  echo "Instalación de Java 17"
  sudo apt update
  sudo apt upgrade
  sudo apt install -y openjdk-17-jdk
else
  echo "Java ya está instalado"
  java -version
fi


if ! command -v mvn &>/dev/null; then
  echo "Instalación de Maven 3.6.3"
  sudo apt update
  wget https://mirrors.estointernet.in/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz
  tar -xvf apache-maven-3.6.3-bin.tar.gz
  sudo mv apache-maven-3.6.3 /opt/
  sudo apt install maven
else
  echo "Maven ya está instalado"
  mvn -version
fi