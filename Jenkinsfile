pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Assemble') {
            steps {
                sh "chmod +x gradlew"
                sh "./gradlew clean assemble"    
            }            
        }

        stage('Test') {
            steps {
                sh "./gradlew test"
            }            
        }

        stage ('SonarQube') {
            var mainBranch = ${BRANCH_NAME} in ['master', 'develop']
            steps {
                if (mainBranch) {
                    echo "Is main brnach ${mainBranch}"
                }
                sh "./gradlew sonarqube -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.login=${SONAR_LOGIN}"
            }
        }
    }
}
