# PROYECTO - REPÓRTATE
----
Autor: 
*   Ing. Ruth Vargas 
*   Ing. Luis Carlos
*   Ing. Sergio Pilco 
*   Ing. Leonardo Bozo
*   Ing. Diana
*   Ing. Daniel Yugar
*   Vinx Junior Guzman Martinez
*   Ing. Ricardo Laredo 

#   DESCRICIÓN

El :
* Spring Boot
* Postgresql 9.6 sobre un contenedor docker

Este proyecto se puede desplegar como proyecto Spring Boot:

# BASE DE DATOS

Se debe crear una base de datos con nombre reportate y password 1234567 , o ejecute el siguiente script:

    CREATE USER reportate WITH
    LOGIN
    NOSUPERUSER
    INHERIT
    NOCREATEDB
    NOCREATEROLE
    NOREPLICATION;
    --Cambiar contrasenia del usuario
    ALTER USER reportate WITH PASSWORD '123456';
    --crear una base de datos con propietario especifico
    CREATE DATABASE reportate WITH OWNER reportate;
    --Asignar privilegios al usuario de de la base de datos
    GRANT ALL PRIVILEGES ON DATABASE reportate TO reportate;
