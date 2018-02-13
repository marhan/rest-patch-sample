pipeline {
    agent any
    stages {
        stage('checkout') {
            steps {
            checkout scm
            }
        }

        stage('assemble') {
            steps {
                sh "chmod +x gradlew"
                sh "./gradlew clean assemble"    
            }            
        }

        stage('test') {
            steps {
                sh "./gradlew test"
            }            
        }
    }
}
