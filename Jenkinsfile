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
	stage('JFrog Artifactory') {
      steps {
          sh '''
             //          cd ${XMP_IMAGE}/
		//       curl -X PUT "https://artifacts.daimler.com/artifactory/mfepace-main-maven-snapshots/xmp-common-0.1.0-SNAPSHOT.jar" || curl --proxy "http://security-proxy.emea.svc.corpintra.net:3128" -T target/xmp-common-0.1.0-SNAPSHOT.jar
                    '''  
  }
}
