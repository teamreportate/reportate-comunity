# PROYECTO - REPÓRTATE
----
Autor: 
* Ing. Ruth Vargas 
* Ing. Luis Carlos
* Ing. Sergio Pilco 
* Ing. Leonardo Bozo
* Ing. Diana
* Ing. Daniel Yugar
* Vinx Junior Guzman Martinez
* Ing. Ricardo Laredo 

##   DESCRICIÓN
Repórtate, es una aplicación de alerta temprana y control de riesgo de las personas más propensas a contraer COVID-19.
Se desarrollo en un contexto en el que la pandemia **CODIV-19** está afectando a Bolivia y no se tienen herramientas 
tecnológicas que nos permitan mejorar el manejo de las personas sospechosas.
##   OBJETIVOS
El objetivo es desarrollar un sistema que permita a la población Boliviana reportar los síntomas que presenta y que el 
sistema de acuerdo a una matriz de diagnóstico pueda determinar si el paciente tiene la probabilidad de estar infectado con
COVID-19.
###  OBJETIVOS ESPECIFICOS
*   Desarrollar una aplicación web para seguimieinto de síntomas, administración y configuración del sistema.
*   Desarrollar una aplicación web para que la población Boliviana pueda reportar los síntomas que presenta.

##   TECNOLOGIA
Se ha utilizado en lo posible tecnologías Open Source para no incurrir en gastos de licencia que puedan poner en riesgo 
la puesta en producción del sistema entre los cuales tenemos:
* Spring Boot 2.1.1.RELEASE para desarrollo del backend.
* Postgresql 9.6 el mismo puede ser modificado puesto que se usa usa JPA como intermedio entre la base de datos y el backend
* Angular 8  para el desarrollo de la aplicación web de administración y seguimiento.
* MapBox para visualización del mapa. pero es posible cambiar a google maps u otro.
* React para el desarrollo de la aplicación de uso del paciente.

Este proyecto se puede desplegar como proyecto Spring Boot ó compilar la aplicación para que se despliegue en servidor
de aplicaciones como wildfly ó Jboss EAP.

# BASE DE DATOS

Se debe crear una base de datos con nombre reportate y password 1234567, a continuación se presenta el scrip para crear 
la base de datos, tomar en cuenta que es una base de datos de desarrollo.

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
