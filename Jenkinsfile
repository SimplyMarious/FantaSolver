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
    //    SONAR_HOST_URL = 'http://172.191.97.235:9000'
    //    SONAR_AUTH_TOKEN = 'squ_ba151bf4d23e8ab4211339f222912354aa6ab357'
    }

    agent any
    stages {
        stage('Cloning repository') {
            steps{
                checkout scm
            }
        }

         stage('Compiling'){
             steps{
                 sh 'mvn clean compile'
             }
         }

         stage('Testing'){
              steps{
                  sh 'mvn integration-test'
              }

              post{
                  always{
                      junit '**/target/surefire-reports/TEST-*.xml'
                  }
              }
         }

        stage('SonarQube analyzing') {
            steps {
                script {
                    withSonarQubeEnv('sonarqube') {
                        sh "mvn clean verify sonar:sonar"

                        echo "SQA ${env.SONAR_HOST_URL}"
                        echo "SQA ${env.SONAR_AUTH_TOKEN}"
                    }
                }
            }
        }
        stage("Quality Gate"){
            steps{
                script{
                    echo "QG ${env.SONAR_HOST_URL}"
                    echo "QG ${env.SONAR_AUTH_TOKEN}"

                    def qg = waitForQualityGate()
                    if (qg.status != 'OK') {
                        error "Pipeline aborted due to quality gate failure: ${qg.status}"
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