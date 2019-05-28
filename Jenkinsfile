ppipeline {
  agent any
  environment {
        MYSONARIP = '172.17.0.2'
		MYSONARHOST    = 'http://localhost:9000/'
    }
  stages {
    stage('Build') {
      steps {
        configFileProvider([configFile(fileId: 'a60df1f1-0bb2-4afc-ad7f-e9680bb92425', variable: 'MAVEN_SETTINGS')]) {
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
