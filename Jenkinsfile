def remote = [:]
    remote.name = "azureuser"
    remote.host = "172.191.227.85"
    remote.allowAnyHosts = true

pipeline {

    tools{
        maven 'maven'
    }

    environment {
        registry = 'spme2023/fantasolver'
        dockerImage = ''
        marioGitHubToken = credentials('MarioGithubToken')
        registryCredential = 'spme2023_dockerhub'
    }

    agent any
    stages {
        stage('Cloning repository') {
            steps{
                checkout scm
            }
        }

//         stage('Compiling'){
//             steps{
//                 sh 'mvn clean compile'
//             }
//         }
//
//         stage('Testing'){
//              steps{
//                  sh 'mvn integration-test'
//              }
//
//              post{
//                  always{
//                      junit '**/target/surefire-reports/TEST-*.xml'
//                  }
//              }
//         }

        stage('SonarQube analyzing') {
            steps {
                script {
                    withSonarQubeEnv() {
                        sh "mvn clean verify sonar:sonar -Dsonar.projectKey=FantaSolver -Dsonar.projectName='FantaSolver' -Dsonar.token=squ_ba151bf4d23e8ab4211339f222912354aa6ab357"
                    }
                }
            }
        }

        stage("Quality Gate"){
          steps{
            script{
                timeout(time: 1, unit: 'HOURS') { // Just in case something goes wrong, pipeline will be killed after a timeout
                    def qg = waitForQualityGate() // Reuse taskId previously collected by withSonarQubeEnv
                    if (qg.status != 'OK') {
                      error "Pipeline aborted due to quality gate failure: ${qg.status}"
                    }
                }
            }
          }
        }

        stage('Packaging') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Delivering to staging area') {
            steps {
                sh 'scp -i ~/.ssh/id_rsa_staging target/FantaSolver.jar azureuser@172.191.227.85:~/Desktop/FantaSolver'
            }
        }
    }
}