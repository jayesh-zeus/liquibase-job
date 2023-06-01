pipeline {
    agent any

        stages {

            stage('Clone Application Repo') {
                steps {
                    script {
                        def secretProperties = readProperties file: 'lb-properties';
                        env.URL= secretProperties.URL
                        env.driver = secretProperties.driver
                        env.classpath = secretProperties.classpath
                        env.changeLog = secretProperties.changeLog
                        echo "${env.driver} ${env.classpath} ${env.URL}"
                        git branch: 'master', url: 'https://github.com/jayesh-zeus/liquibase-job.git'

                        withCredentials([usernamePassword(credentialsId: 'local-db', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                            env.username = USERNAME
                                env.password = PASSWORD
                                echo "Username: ${USERNAME}"
                        }
                    }
                }

            }

            stage('Run Liquibase') {
                steps {
                    script {
                        def serviceDirectories = findFiles(glob: 'services/*')
                        for (def service in serviceDirectories) {
                           def serviceName = service.name
                           echo "${serviceName}"
                        }
                        /* dir("liquibase") { */
                        /*     sh "./liquibase updateSQL --changelogFile=${env.changeLog} --classpath=${env.classpath} --driver=${env.driver} --url=${env.URL}  --username=${env.USERNAME} --password=${env.PASSWORD}" */
                        /* } */
                    }
                }
            }
        }
}
