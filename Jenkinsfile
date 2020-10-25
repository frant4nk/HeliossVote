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
    //stage ('Deploy') {
    //  steps {
    //    nexusPublisher nexusInstanceId: 'heliossnexus', nexusRepositoryId: 'maven-releases', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: 'jar', filePath: 'build/libs/heliossvote-1.0-all.jar']], mavenCoordinate: [artifactId: 'heliossvote', groupId: 'io.github.frant4nk', packaging: 'jar', version: '1.0']]]
    //  }
    }
  }
}
