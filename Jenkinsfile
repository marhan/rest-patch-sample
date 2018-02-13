pipeline {
    agent any
    stages {
        stage('checkout') {
            steps {
            checkout scm
            }
        }

        stage('clean') {
            steps {
                sh "chmod +x gradlew"
                sh "./gradlew clean"    
            }            
        }

        stage('test') {
            steps {
                sh "./gradlew test"
            }            
        }
    }
}
