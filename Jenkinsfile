pipeline {
  agent any
  stages {
    stage('Initialize') {
      steps {
        sh 'mvn clean'
      }
    }
    stage('Build') {
      steps {
        sh 'mvn compile'
      }
    }
    stage('Execution') {
      steps {
        sh 'mvn test'
      }
    }
  }
}