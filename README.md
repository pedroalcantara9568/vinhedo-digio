# Vinhedo

- #### Docker

````
docker -v

Docker version 27.0.3, build 7d4bcd8
  ````

- #### docker-compose

````
docker-compose -v

Docker Compose version v2.28.1-desktop.1
  ````


- #### Java 11
````
java -version

java version "17.0.4"
````

## Ambiente Linux

### Publicar imagem da aplicação:

````
./gradlew bootBuildImage
````

### Executar container
```
docker-compose -f docker-compose.yml up -d
``` 

## Ambiente Windows


````
./gradlew bootBuildImage
````

### Executar container
```
docker-compose -f docker-compose.yml up -d
``` 

# Swagger
http://localhost:8080/swagger-ui.html

