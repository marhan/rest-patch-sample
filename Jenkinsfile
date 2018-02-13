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
            steps {
                when {
                    expression { BRANCH_NAME ==~ /(master|develop)/ }                    
                }           
                echo "${BRANCH_NAME} is in (master|develop)"
                sh "./gradlew sonarqube -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.login=${SONAR_LOGIN}"
            }           
        }

         stage ('SonarQube for feature') {                        
            steps {
                when {
                    not {
                        expression { BRANCH_NAME ==~ /(master|develop)/ }
                    }                                 
                }           
                echo "${BRANCH_NAME} is NOT in (master|develop)"
                sh "./gradlew sonarqube -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.login=${SONAR_LOGIN}"
            }
        }
    }
}
