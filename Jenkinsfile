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
                    withSonarQubeEnv() {
                        sh "mvn clean verify sonar:sonar -Dsonar.projectKey=FantaSolver -Dsonar.projectName='FantaSolver' -Dsonar.login=squ_ba151bf4d23e8ab4211339f222912354aa6ab357"
                    }
                }
            }
        }

       stage('Building the Docker image') {
           steps{
               script {
                   dockerImage = docker.build(registry + ":$BUILD_NUMBER")
               }
           }
       }

       stage('Deploying the Docker image on DockerHub') {
           steps{
               script {
                   docker.withRegistry( '', registryCredential ) {
                       dockerImage.push()
                   }
               }
           }
       }

       stage('Removing unused Docker image from Jenkins area') {
           steps{
               sh "docker rmi $registry:$BUILD_NUMBER"
           }
       }

       stage('Removing old Docker images from staging area') {
           steps {
               script{
                   withCredentials([sshUserPrivateKey(credentialsId: 'staging-area-host', keyFileVariable: 'identity', passphraseVariable: '', usernameVariable: 'azureuser')]) {
                       remote.user = azureuser
                       remote.identityFile = identity

                       sshCommand remote: remote, command: "docker image prune -a -f"
                   }
               }
           }
       }

       stage("Pulling the Docker image on remote staging area") {
           steps {
               script{
                   withCredentials([sshUserPrivateKey(credentialsId: 'staging-area-host', keyFileVariable: 'identity', passphraseVariable: '', usernameVariable: 'azureuser')]) {
                       remote.user = azureuser
                       remote.identityFile = identity

                       sshCommand remote: remote, command: "docker image pull " + registry + ":$BUILD_NUMBER"
                   }
               }
           }
       }
    }
}