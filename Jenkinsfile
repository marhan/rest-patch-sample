pipeline {
    agent any
    stage('checkout') {
        checkout scm
    }

    stage('clean') {
        sh "chmod +x gradlew"
        sh "./gradlew clean"
    }

    stage('test') {
        sh "./gradlew test -Dspring.profiles.active=test"
    }
}
