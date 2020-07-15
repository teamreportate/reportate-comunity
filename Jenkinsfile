#!groovy
pipeline {
    agent any
    tools {
        maven 'MVN3'
    }
    environment {
//         SERVER_DEV_SFE_CREDENTIAL = credentials('server.reportate.credential')
        PATH_BACKEND_API_WS = "${WORKSPACE}/source/backend/"
        PATH_FRONTEND = "${WORKSPACE}/source/back-office/"
        PATH_FRONTOFFICE = "${WORKSPACE}/source/frontoffice/"

        PATH_WEB_FACTURACION = "${WORKSPACE}/backend/web-facturacion/"
        PATH_WEB_BACKOFFICE_API = "${WORKSPACE}/backend/web-backoffice-api/"
        PATH_WEB_FRONTOFFICE_API = "${WORKSPACE}/backend/web-frontoffice-api/"
        PATH_BACKOFFICE_WAR = "${WORKSPACE}/backoffice/clic_backoffice"
        PATH_FRONTOFFICE_WAR = "${WORKSPACE}/frontoffice/clic_frontoffice"
    }
    stages {
        stage('Descargando de repositorio.') {
            steps {
                sh 'rm -rf *'
                checkout scm
            }
        }


        stage('Compilando..') {
            parallel {
                stage("Compilando backend"){
                    stages{
                        stage("Compilando Backend") {
                            steps {
                                ws(env.PATH_BACKEND_API_WS) {
                                    sh 'mvn clean compile'
                                }
                            }
                        }

                        stage('Junit') {
                            steps {
                                ws(env.PATH_BACKEND_API_WS) {
                                    sh 'mvn test'
                                }
                            }
                        }

                        stage('SonarQube') {
                            steps {
                                dir(env.PATH_BACKEND_API_WS) {
                                    withSonarQubeEnv('SonarQube') {
                                        sh 'mvn clean package sonar:sonar'
                                    }
                                }
                            }
                        }
                        stage("Verificando Calidad") {
                            steps {
                                timeout(time: 1, unit: 'HOURS') {
                                    waitForQualityGate abortPipeline: true
                                }
                            }
                        }

                    }
                }
                stage("Compilando aplicaciones web"){
                    stages{
                        stage("Compilando Backoffice") {
                            steps {
                                ws(env.PATH_FRONTEND) {
                                    sh 'mvn clean install'
                                }
                            }
                        }
//                         stage("Building Frontoffice") {
//                             steps {
//                                 ws(env.PATH_FRONTOFFICE) {
//                                     sh 'mvn clean install'
//                                 }
//                             }
//                         }
                    }

                }
            }
         }






//         stage('Desplegando artefactos') {
//           parallel {
//             stage("Desplegando en desarrollo"){
//                 when {
//                   branch 'master'  //only run these steps on the master branch
//                 }
//                 stages{
//                     stage('Compilando para despliegue') {
//                         steps {
//                             ws(env.PATH_BACKEND_API_WS) {
//                                 sh "mvn clean package -Dmaven.test.skip=true"
//                             }
//                         }
//                     }
//                     stage('Desplegando Core facturación') {
//                         steps {
//                             ws(env.PATH_WEB_FACTURACION) {
//                                 sh "mvn wildfly:deploy -Djboss-hostname=${env.SERVER_DEV_SFE_HOSTNAME} -Djboss-port=${env.SERVER_DEV_SFE_PORT_ADMIN} -Djboss-username=${env.SERVER_DEV_SFE_CREDENTIAL_USR} -Djboss-password=${env.SERVER_DEV_SFE_CREDENTIAL_PSW}"
//                             }
//                         }
//                     }
//
//                     stage('Desplegando Backoffice api') {
//                         steps {
//                             ws(env.PATH_WEB_BACKOFFICE_API) {
//                                 sh "mvn wildfly:deploy -Djboss-hostname=${env.SERVER_DEV_SFE_HOSTNAME} -Djboss-port=${env.SERVER_DEV_SFE_PORT_ADMIN} -Djboss-username=${env.SERVER_DEV_SFE_CREDENTIAL_USR} -Djboss-password=${env.SERVER_DEV_SFE_CREDENTIAL_PSW}"
//                             }
//                         }
//                     }
//
//                     stage('Desplegando Frontoffice api') {
//                         steps {
//                             ws(env.PATH_WEB_FRONTOFFICE_API) {
//                                 sh "mvn wildfly:deploy -Djboss-hostname=${env.SERVER_DEV_SFE_HOSTNAME} -Djboss-port=${env.SERVER_DEV_SFE_PORT_ADMIN} -Djboss-username=${env.SERVER_DEV_SFE_CREDENTIAL_USR} -Djboss-password=${env.SERVER_DEV_SFE_CREDENTIAL_PSW}"
//                             }
//                         }
//                     }
//                     stage('Desplegando Aplicación Web de Backoffice') {
//                         steps {
//                             ws(env.PATH_BACKOFFICE_WAR) {
//                                 sh "mvn wildfly:deploy -Djboss-hostname=${env.SERVER_DEV_SFE_HOSTNAME} -Djboss-port=${env.SERVER_DEV_SFE_PORT_ADMIN} -Djboss-username=${env.SERVER_DEV_SFE_CREDENTIAL_USR} -Djboss-password=${env.SERVER_DEV_SFE_CREDENTIAL_PSW}"
//                             }
//                         }
//                     }
//
//                     stage('Desplegando Aplicación Web de Frontoffice') {
//                         steps {
//                             ws(env.PATH_FRONTOFFICE_WAR) {
//                                 sh "mvn wildfly:deploy -Djboss-hostname=${env.SERVER_DEV_SFE_HOSTNAME} -Djboss-port=${env.SERVER_DEV_SFE_PORT_ADMIN} -Djboss-username=${env.SERVER_DEV_SFE_CREDENTIAL_USR} -Djboss-password=${env.SERVER_DEV_SFE_CREDENTIAL_PSW}"
//                             }
//                         }
//                     }
//                 }
//             }
//
//             stage("Desplegando en QA"){
//                 when {
//                   branch 'qa_branch'  //only run these steps on the master branch
//                 }
//                 stages{
//                     stage('Compilando para despliegue') {
//                         steps {
//                             ws(env.PATH_WEB_FACTURACION) {
//                                 sh "mvn wildfly:deploy -Djboss-hostname=${env.SERVER_DEV_SFE_HOSTNAME} -Djboss-port=${env.SERVER_DEV_SFE_PORT_ADMIN} -Djboss-username=${env.SERVER_DEV_SFE_CREDENTIAL_USR} -Djboss-password=${env.SERVER_DEV_SFE_CREDENTIAL_PSW}"
//                             }
//                         }
//                     }
//
//                     stage('Desplegando Core facturación') {
//                         steps {
//                             ws(env.PATH_WEB_FACTURACION) {
//                                 sh "mvn wildfly:deploy -Djboss-hostname=${env.SERVER_DEV_SFE_HOSTNAME} -Djboss-port=${env.SERVER_DEV_SFE_PORT_ADMIN} -Djboss-username=${env.SERVER_DEV_SFE_CREDENTIAL_USR} -Djboss-password=${env.SERVER_DEV_SFE_CREDENTIAL_PSW}"
//                             }
//                         }
//                     }
//
//                     stage('Desplegando Backoffice api') {
//                         steps {
//                             ws(env.PATH_WEB_BACKOFFICE_API) {
//                                 sh "mvn wildfly:deploy -Djboss-hostname=${env.SERVER_DEV_SFE_HOSTNAME} -Djboss-port=${env.SERVER_DEV_SFE_PORT_ADMIN} -Djboss-username=${env.SERVER_DEV_SFE_CREDENTIAL_USR} -Djboss-password=${env.SERVER_DEV_SFE_CREDENTIAL_PSW}"
//                             }
//                         }
//                     }
//
//                     stage('Desplegando Frontoffice api') {
//                         steps {
//                             ws(env.PATH_WEB_FRONTOFFICE_API) {
//                                 sh "mvn wildfly:deploy -Djboss-hostname=${env.SERVER_DEV_SFE_HOSTNAME} -Djboss-port=${env.SERVER_DEV_SFE_PORT_ADMIN} -Djboss-username=${env.SERVER_DEV_SFE_CREDENTIAL_USR} -Djboss-password=${env.SERVER_DEV_SFE_CREDENTIAL_PSW}"
//                             }
//                         }
//                     }
//                     stage('Desplegando Aplicación Web de Backoffice') {
//                         steps {
//                             ws(env.PATH_BACKOFFICE_WAR) {
//                                 sh "mvn wildfly:deploy -Djboss-hostname=${env.SERVER_DEV_SFE_HOSTNAME} -Djboss-port=${env.SERVER_DEV_SFE_PORT_ADMIN} -Djboss-username=${env.SERVER_DEV_SFE_CREDENTIAL_USR} -Djboss-password=${env.SERVER_DEV_SFE_CREDENTIAL_PSW}"
//                             }
//                         }
//                     }
//
//                     stage('Desplegando Aplicación Web de Frontoffice') {
//                         steps {
//                             ws(env.PATH_FRONTOFFICE_WAR) {
//                                 sh "mvn wildfly:deploy -Djboss-hostname=${env.SERVER_DEV_SFE_HOSTNAME} -Djboss-port=${env.SERVER_DEV_SFE_PORT_ADMIN} -Djboss-username=${env.SERVER_DEV_SFE_CREDENTIAL_USR} -Djboss-password=${env.SERVER_DEV_SFE_CREDENTIAL_PSW}"
//                             }
//                         }
//                     }
//                 }
//             }
//           }
//         }


        stage('Archivar resultados') {
            steps {
                step([$class: 'ArtifactArchiver', artifacts: '**/target/*.jar, **/target/*.war', fingerprint: true])
            }
        }
    }
    post {
        failure {
            mail to: 'rllayus@gmail.com', cc: "", charset: "UTF-8",
                    subject: ": ${currentBuild.fullDisplayName}",
                    body: "Se genero un error al ejecutor de tareas de jenkins. ${env.BUILD_URL}"
        }

        success {
            mail to: 'rllayus@gmail.com', cc: "", charset: "UTF-8",
                    subject: ": ${currentBuild.fullDisplayName}",
                    body: "La aplicación se ejecutó exitosamente" +
                            "" +
                            " ${env.BUILD_URL}"
        }

    }
}
