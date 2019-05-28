pipeline {
  agent any
  environment {
        MYSONARIP = '172.17.0.2'
		MYSONARHOST    = 'http://localhost:9000/'
    }
  stages {
    stage('Build') {
      steps {
        configFileProvider([configFile(fileId: '83e8ff71-6618-47d6-a6ca-78038527066f', variable: 'MAVEN_SETTINGS')]) {
          sh 'mvn -s ${MAVEN_SETTINGS} clean install test'                                                                                           
        }
      }
    }
	stage('SonarQube analysis') {
      steps {
          sh "mvn sonar:sonar -Dsonar.host.url=${env.MYSONARHOST}"                                                                                         
      }
    }
  }
}
