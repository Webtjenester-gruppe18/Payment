pipeline {
  agent any
  stages {
    stage('Test') {
      steps {
        sh 'mvn clean install'
      }
    }
     stage('Build') {
          steps {
            sh 'docker build --tag paymentmanagerimage:latest . '
          }
        }
     stage('Deploy') {
          steps {
            sh '''docker stop paymentmanager || true && docker rm paymentmanager || true;
            docker run -d -p 7474:8080 --name paymentmanager paymentmanagerimage:latest
            '''
          }
     }
  }
}