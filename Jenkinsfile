pipeline {
  agent any
  environment {
        MYSONARIP = '172.17.0.2'
		MYSONARHOST    = 'http://localhost:9000/'
       XMP_IMAGE = readMavenPom().getArtifactId()
       XMP_POM_VERSION = readMavenPom().getVersion()	
    }
  stages {
    stage('Variable Check') {
        steps {
               sh "printenv | sort"
            	}
				}
    stage('Build') {
      steps {
        configFileProvider([configFile(fileId: '83e8ff71-6618-47d6-a6ca-78038527066f', variable: 'MAVEN_SETTINGS')]) {
          sh 'mvn -s ${MAVEN_SETTINGS} clean install package'                                                                                           
        }
      }
    }
	stage('SonarQube analysis') {
      steps {
          sh "mvn sonar:sonar -Dsonar.host.url=${env.MYSONARHOST}"                                                                                         
      }
    }
   stage('Artifactory Repo') {
           steps {
		   withCredentials([string(credentialsId: 'JFrog_API_Key', variable: 'TOKEN')]) {
                sh '''
  			curl -u "Authorization: token ${TOKEN}" -X PUT "http://localhost:8081/artifactory/libs-release/xmp-gitflow-ci-demo-0.1.0-SNAPSHOT.jar" -T target/xmp-gitflow-ci-demo-0.1.0-SNAPSHOT.jar
                '''
            }
					
                }
}
}
}
