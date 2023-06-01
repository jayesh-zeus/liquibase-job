pipeline {
    agent any

        stages {

            stage('Clone Application Repo') {
                steps {
                    script {
                        // read from AWS secrets
                        def secretProperties = readProperties file: 'lb-properties';

                        env.URL= secretProperties.URL
                        env.driver = secretProperties.driver
                        env.classpath = secretProperties.classpath
                        env.changeLog = secretProperties.changeLog

                        git branch: 'master', url: 'https://github.com/jayesh-zeus/liquibase-job.git'

                        withCredentials([usernamePassword(credentialsId: 'local-db', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                            env.username = USERNAME
                            env.password = PASSWORD
                        }
                    }
                }

            }

            stage('Run Liquibase') {
                steps {
                    script {
                        dir('services') {
                            def serviceDirectories = sh(returnStdout: true, script: 'ls -d */').trim().split()
                                for (def service in serviceDirectories) {
                                    // run  liquibase update for each service
                                    echo "${service}"
                                }
                        }
                        /* dir("liquibase") { */
                        /*     sh "./liquibase updateSQL --changeLogFile=${env.changeLog} --classpath=${env.classpath} --driver=${env.driver} --url=${env.URL}  --username=${env.USERNAME} --password=${env.PASSWORD}" */
                        /* } */
                    }
                }
            }
        }
}
