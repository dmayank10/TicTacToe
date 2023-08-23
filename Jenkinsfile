pipeline {
    agent any
    environment {
        SCANNER_HOME = tool name: 'SonarQube', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
    }
    stages {
        stage('github') {
            steps {
                echo 'code check-in'
                git credentialsId: 'github-credentials', url: 'https://github.com/dmayank10/TicTacToe.git'
            }
        }
        stage('build') {
            steps {
                withMaven(globalMavenSettingsConfig: '', jdk: '', maven: 'Maven', mavenSettingsConfig: '', traceability: true) {
                    echo 'code build through maven'
                    sh 'mvn clean'
                    sh 'mvn package'
                    sh 'mvn compile'
                    sh 'mvn install'
                }
            }
        }    
        // stage('test') {
        //     steps {
        //         script {
        //             def sonarScannerCmd = "${SCANNER_HOME}/bin/sonar-scanner"
        //             withSonarQubeEnv('SonarQube') {
        //                 sh "${sonarScannerCmd}"
        //             }
        //         }
        //     }
        // }
        stage('deploy') {
            steps {
                echo 'code deploy into docker container'
                // sh 'docker rm -f $(docker ps -a -q)'
                sh 'docker build -t gitprac:${BUILD_NUMBER} .' 
                sh 'docker run -itd --name gitprac-container-${BUILD_NUMBER} -p 82:80 gitprac:${BUILD_NUMBER}'
            }
        }
    }
}
