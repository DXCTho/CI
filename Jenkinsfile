pipeline {
    agent { label 'master' }
    environment {
        MYSONARNAME    = 'mysonar distracted_lumiere'
		MYSONARHOST    = 'http://localhost:9000/'
		scannerHome = tool 'SonarQubeScanner'
		MY_ENV_VAR = "$BUILD_ID"
		BRANCH = "$BRANCH_NAME"
    }
	
    stages {
        stage('Clean workspace') {
            steps {
                deleteDir()
      //          echo "Running SonarQube ${env.MYSONARNAME} on ${env.MYSONARIP}"
                echo " Build ID '${env.BUILD_ID}'"
                echo " JOB NAME'${env.JOB_NAME.replaceFirst('.+/', '')}'"
                echo " Branch '${env.BRANCH_NAME}'"
                
            }
        }
        stage('Clone repository Dai Sandbox') {
            steps {          
			   dir('ci-sandbox') {
                    git(
			    branch: '"${BRANCH_NAME}"',
                        url: 'https://github.com/DXCTho/CI.git',
                        credentialsId: 'NonDXCGitHubPW'
                    )
                     echo " Branch '${env.BRANCH_NAME}'"
                }
            }
        }	  
        stage('Maven package') {
            steps {
                configFileProvider([configFile(fileId: '83e8ff71-6618-47d6-a6ca-78038527066f', variable: 'MAVEN_SETTINGS')]) {
                    sh 'cd ci-sandbox/; mvn -s ${MAVEN_SETTINGS} clean package'
                }
            }
        } 
		stage('SonarQube analysis') {
            steps {	
				withSonarQubeEnv('sonarqube') {
					sh '''
					cd ci-sandbox/; mvn sonar:sonar \
					-Dsonar.host.url="${MYSONARHOST}" \
					-Dsonar.projectName=ePACE_MY_PROJECT_KEY \
					-Dsonar.projectVersion="${BUILD_ID}"
					'''
				}
			 }
	    }	
        stage("Quality Gate") {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    // Parameter indicates whether to set pipeline to UNSTABLE if Quality Gate fails
                    // true = set pipeline to UNSTABLE, false = don't
                    waitForQualityGate abortPipeline: true
                }
            }
        }
		
		stage('Artifactory Repo') {
            steps {
					withCredentials([string(credentialsId: 'AF_STR', variable: 'TOKEN')]) {
                sh '''
                    cd ci-sandbox/
					curl -u "Authorization: token ${TOKEN}" -X PUT "http://localhost:8081/artifactory/libs-release/javax" -T "/var/lib/jenkins/workspace/Public-GitHub-Pipeline/ci-sandbox/src/main/java"
                '''
            }
					
                }
            }
	}
    post {
        success {
            withCredentials([string(credentialsId: 'NonDXCGitHubST', variable: 'TOKEN')]) {
                sh '''
                    cd ci-sandbox/
                    curl -XPOST -H "Authorization: token ${TOKEN}"  https://api.github.com/repos/DXCTho/CI/statuses/$(git rev-parse HEAD) -d '{"state": "success" , "target_url": "'${BUILD_URL}'" , "description": "build '${BUILD_ID}'succeeded"}'
                '''
            }
        }
        failure {
            withCredentials([string(credentialsId: 'NonDXCGitHubST', variable: 'TOKEN')]) {
                sh '''
                    cd ci-sandbox/
                    curl -XPOST -H "Authorization: token ${TOKEN}"  https://api.github.com/repos/DXCTho/CI/statuses/$(git rev-parse HEAD) -d '{"state": "failure" , "target_url": "'${BUILD_URL}'" , "description": "build '${BUILD_ID}' failed"}'
                '''
            }
        }
    }
}
