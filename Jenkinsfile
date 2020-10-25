pipeline {
  agent any
  stages {
    stage('Make Gradlew Executable') {
      steps {
        sh 'chmod +x ./gradlew'
      }
    }

    stage('Build') {
      steps {
        sh '''./gradlew build
./gradlew shadowJar'''
      }
    }

    stage('Archive') {
      steps {
        archiveArtifacts 'build/libs/*.jar'
      }
    }

  }
}