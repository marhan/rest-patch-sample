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
                sh "./gradlew sonarqube"
            }
        }
    }
}
