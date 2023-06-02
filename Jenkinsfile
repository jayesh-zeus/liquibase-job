project = params.project

basePath = ''

cmsDirectory = 'cms'
cmsRepoPath = basePath + cmsDirectory

lmsDirectory = 'lms'
lmsRepoPath = basePath + lmsDirectory

projectEnvironment = params.environment
// branch as parameter
dockerBuildNode = 'quantum-docker-build'

def repoData = [
    "awana-integration": [
        "cms-git-url": "repo-url",
        "cms-git-branch": "*/integration",
    ],
    "awana-qa": [
        "cms-git-url": "repo-url",
        "cms-git-branch": "*/bil-dev",
    ],
    "awana-load-testing": [
        "cms-git-url": "repo-url",
        "cms-git-branch": "*/load-testing",
    ],
    "awana-staging": [
        "cms-git-url": "repo-url",
        "cms-git-branch": "*/staging",
    ]
]

    def gitDetails = repoData[projectEnvironment]

    pipeline {
        agent any

            stages {
                stage('') {
                    steps {
                        script {
                            basePath = pwd() + '/'
                                cmsRepoPath = basePath + cmsDirectory
                                lmsRepoPath = basePath + lmsDirectory
                        }
                    }
                }
                stage('Git Checkout') {
                    parallel {
                        stage('CMS Checkout') {
                            steps {
                                dir(basePath) {
                                   echo "Checking out branch ${gitDetails["cms-git-branch"]} from repo ${gitDetails["lms-git-url"]}"
                                    /* checkout changelog: false, poll: false, scm: [$class: 'GitSCM', branches: [[name: gitDetails["cms-git-branch"]]], extensions: [[$class: 'CheckoutOption', timeout: 30], [$class: 'CloneOption', depth: 1, noTags: false, reference: '', shallow: true, timeout: 30], [$class: 'RelativeTargetDirectory', relativeTargetDir: cmsDirectory]], userRemoteConfigs: [[credentialsId: gitCredentials, url: gitDetails["cms-git-url"]]]] */
                                }
                            }
                        }
                    }
                }
                stage('Clone Application Repo') {
                    steps {
                        script {
                            // read from AWS secrets as JSON
                            def secretProperties = readProperties file: 'lb-properties';
                            secretProperties.each { key, value -> 
                                env."${key}" = value
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
                                        echo "ChangeLogFile = src/${service}db.changelog-master.xml"
                                        service = service.replaceAll('/', '')
                                        echo "URL = ${env["${service}_url"]}"
                                    }
                            }
                            dir("liquibase") {
                                def serviceDirectores = sh(returnStdout: true, script: 'ls -d */').trim().split()
                                echo "$serviceDirectores"
                                sh "./liquibase --changeLogFile=${env.changeLog} --classpath=${env.classpath} --driver=${env.driver} --url=${env.URL}  --username=${env.USERNAME} --password=${env.PASSWORD} updateSQL"
                            }
                        }
                    }
                }
            }
    }
