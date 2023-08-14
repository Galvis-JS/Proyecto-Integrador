#!/bin/bash

ruta="/home/ubuntu"
nombre_usuario="cinthyarondon"
nombre_repositorio="digitalbooking"
etiqueta="latest"
container_id=$(sudo docker ps --filter "publish=80" --format "{{.ID}}")

# Verificar máquina virtual
echo "Host: $(hostname)"
echo "OS: $(uname -a)"

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
  sudo usermod -aG docker $USER
else
  echo "Docker ya está instalado!"
  docker --version
  sudo systemctl enable docker
fi

# Verificar si se encontró un contenedor
if [ -n "$container_id" ]; then
  echo "Se encontró un contenedor en ejecución con el puerto 80 asignado. Deteniendo y eliminando el contenedor..."

  # Detener el contenedor
  sudo docker stop "$container_id"

  # Eliminar el contenedor
  sudo docker rm "$container_id"

  echo "El contenedor ha sido detenido y eliminado correctamente."
else
  echo "No se encontró ningún contenedor en ejecución que utilice el puerto 80."
fi

# Imagen y contendor de Docker
if [ -f "$ruta/Dockerfile" ]; then
  echo "El archivo Dockerfile existe en la ruta especificada."

  if [ -d "$ruta/backend/" ]; then
    echo "La carpeta 'backend' existe en la ruta especificada."

    docker system prune --force

    # Construir la imagen
    sudo docker build -t "$nombre_usuario/$nombre_repositorio:$etiqueta" .

    # Verificar si la construcción de la imagen fue exitosa
    if [ $? -eq 0 ]; then
      echo "La imagen se ha construido correctamente."

      # Ejecutar el contenedor
      sudo docker run -d -p 80:8080 "$nombre_usuario/$nombre_repositorio:$etiqueta"
    else
      echo "La construcción de la imagen ha fallado."
    fi

  else
    echo "La carpeta 'backend' no existe en la ruta especificada."
  fi

else
  echo "El archivo Dockerfile no existe en la ruta especificada."
fi