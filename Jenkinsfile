pipeline {
    agent any

        stages {
            /* stage('Fetch AWS Secret') { */
            /*     steps { */
            /*         sh 'aws secretsmanager get-secret-value --secret-id liquibase-properties --query SecretString --output text > liquibase-properties.txt' */
            /*     } */
            /* } */

            stage('Clone Application Repo') {
                steps {
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

            stage('Run Liquibase') {
                steps {
                    script {
                        /* def folders = ["auth", "cms", "fsapi", "lms", "lti", "archive"] */
                        /* for (folder in folders) { */
                        dir("liquibase") {
                            sh "./liquibase update-sql --changelog-file=${env.changeLog} --classpath=${env.classpath} --driver=${env.driver} --url=${env.URL}  --username=${env.USERNAME} --password=${env.PASSWORD}"
                                /* } */
                            /* } */
                    }
                }
            }
        }
}
