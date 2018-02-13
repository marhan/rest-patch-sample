pipeline {
    agent any
    stages {
        stage('checkout') {
            checkout scm
        }

        stage('clean') {
            sh "chmod +x gradlew"
            sh "./gradlew clean"
        }

        stage('test') {
            sh "./gradlew test"
        }
    }
}
