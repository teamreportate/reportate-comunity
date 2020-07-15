# PROYECTO - REPÓRTATE
----
Autores: 

Equipo Técnico
* Ing. Ruth Vargas 
* Ing. Luis Suaznabar
* Ing. Sergio Pilco 
* Ing. Leonardo Bozo
* Ing. Diana Mejia
* Ing. Daniel Yugar
* Ing. Junior Guzman
* Ing. Alvaro Merlo
* Ing. André Ticona
* Ing. Victor Gutierrez
* Ing. Ricardo Laredo

Equipo Médico
* Dra. Marcia Andrea Gonzáles 
* Dra. Ariani Arrázola Mendivil
* Dr. Mijail Lenin Quintanilla

Equipo de Coordinación
* Lic. Annelissie Arrázola Mendivil
* Ing. Sinchy Díaz
* Lic. Jessica Olivares Cerruto
* Lic. Xiomara Zambrana


##   DESCRIPCIÓN
Repórtate, es una aplicación de alerta temprana y control de riesgo de las personas más propensas a contraer COVID-19.
Se desarrollo en un contexto en el que la pandemia está afectando a Bolivia y no se tienen herramientas 
tecnológicas que permitan mejorar el control y seguimiento de los pacientes.
##   OBJETIVOS
El objetivo es desarrollar un sistema para control y seguimiento  de síntomas
###  OBJETIVOS ESPECIFICOS
*   Diagnosticar COVID-19 y otras enfermedades endémicas de acuerdo a una matriz de diagnóstico
*   Permitir a los pacientes reportar síntomas y enfermedades base.
*   Notificar los casos sospechosos a los médicos de su centro de salud.
*   Permitir gestionar las fichas epidemiológicas de cada paciente.

##   TECNOLOGIA
Se ha utilizado en lo posible tecnologías Open Source para no incurrir en gastos de licencia que puedan poner en riesgo 
la puesta en producción del sistema, entre los cuales tenemos:
* Spring Boot 2.1.1.RELEASE para desarrollo del backend.
* Postgresql 9.6 + el mismo puede ser modificado por otro motor de base de datos.
* Angular 8  para el desarrollo de la aplicación back-office.
* MapBox para visualización del mapa. pero es posible cambiar a google maps u otro.
* React para el desarrollo de la aplicación de uso del paciente.

Este proyecto se puede desplegar como proyecto Spring Boot ó compilar la aplicación para que se despliegue en servidor
de aplicaciones como wildfly ó JBoss EAP.
## Requerimientos de sistemas para ambiente de desarrollo
El presente proyecto se ejecuta en las siguientes versiones.

* Node: 12.13.1
* OS: win32 x64
* NPM versión 6.14.4
* Angular CLI versión 9.1.1
* Apache Maven 3.5.4
* Java version: 1.8.0_181
## Comandos de instalación y ejecución

### Organización del proyecto
    reportate-covid-19
    |__ source
        |__back-office
        |__backend
        |__frontoffice
### Dependendencias y compilación
Instalar dependencias ejecutando el siguiente comando al nivel del pom.xml principal: `mvn clean install`.

### Ejecutar en servidor desarrollo
Para desplegar el proyecto configurar el acceso a la Base de datos en el archivo `application.properties`, que se encuentra en el directorio: `..\source\backend\web-backoffice-api\src\main\resources`.

### Preparación para la aplicación versión movil
* Instalar dependencias ejecutando yarn install en la terminal.
* Actualizar archivo /src/service/ServiceBase.js linea 3, cambiar URL por la dirección al servidor backend, y PORT por el puerto configurado.
* Generar Release ejecutando yarn build en la terminal el release se encuentra en el directorio /build
* Implementar en servidor de aplicaciones JBOSS

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
    
Copyright (C) 2020, Repórtate

Este programa es software libre: puede redistribuirlo y / o modificarlo
bajo los términos de la Licencia Pública General GNU publicada por
Free Software Foundation, ya sea la versión 3 de la Licencia, cualquier versión posterior.

Este software se distribuye con el propósito de que cualquier personas, natural o jurídicas,
pueda hacer uso pero SIN NINGUNA GARANTÍA; sin siquiera la garantía implícita de
COMERCIABILIDAD o APTITUD PARA UN PROPÓSITO EN PARTICULAR. Ver el
GNU General Public License para más detalles.

Debería haber recibido una copia de la Licencia Pública General de GNU
junto con este programa. Si no, vea [GNU LICENSES](http://www.gnu.org/licenses/)
