pipeline {
    agent any
    
    stages {
        /* stage('Fetch AWS Secret') { */
        /*     steps { */
        /*         sh 'aws secretsmanager get-secret-value --secret-id liquibase-properties --query SecretString --output text > liquibase-properties.txt' */
        /*     } */
        /* } */
        
        /* stage('Populate Environment Variables') { */
        /*     steps { */
        /*         script { */
                    def secretProperties = readProperties file: 'lb-properties'
                      env.URL= secretProperties.URL
                      env.driver = secretProperties.driver
                      env.classpath = secretProperties.classpath
                      env.changeLog = secretProperties.changeLog
                      echo "${env.driver} ${env.classpath} ${env.URL}"

        /*             env.AUTH_URL = secretProperties.AUTH_URL */
        /*             env.CMS_URL = secretProperties.CMS_URL */
        /*             env.FSAPI_URL = secretProperties.FSAPI_URL */
        /*             env.LMS_URL = secretProperties.LMS_URL */
        /*             env.LTI_URL = secretProperties.LTI_URL */
        /*             env.ARCHIVE_URL = secretProperties.ARCHIVE_URL */
        /*             // Add more environment variables for other properties as needed */
        /*         } */
        /*     } */
        /* } */
        
        stage('Clone Application Repo') {
            steps {
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

