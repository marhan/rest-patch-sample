#!/usr/bin/env groovy

node {
    stage('checkout') {
        checkout scm
    }

    stage('clean') {
        sh "chmod +x gradlew"
        sh "./gradlew clean"
    }

    stage('test') {
        try {
            sh "./gradlew test"
        } catch (err) {
            throw err
        } finally {
            junit '**/build/**/TEST-*.xml'
        }
    }
}
