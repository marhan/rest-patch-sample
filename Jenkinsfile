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

        stage ('SonarQube Default') {            
            when {
                expression { BRANCH_NAME ==~ /(master)/ }                    
            }    
            steps {                       
                echo "${BRANCH_NAME} is in (master|develop)"
                sh "./gradlew sonarqube -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.login=${SONAR_LOGIN}"
            }            
        }

        stage ('SonarQube Feature') {            
            when {
                expression { BRANCH_NAME !=~ /(master)/ }                    
            }           
            steps {                
                echo "${BRANCH_NAME} is NOT in (master|develop)"
                sh "./gradlew sonarqube -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.login=${SONAR_LOGIN} -Dsonar.projectKey=${CUSTOM_PROJECT_KEY}"
            }
        }
    }
}
