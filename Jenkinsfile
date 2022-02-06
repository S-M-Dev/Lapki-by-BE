pipeline {
    agent any

    stages {
        stage('Build application') {
            steps {
                sh "mvn clean package"
            }
        }

        stage('Build container'){
            steps {
                sh 'sudo docker rm --force lapki'
                sh 'sudo docker build --no-cache --tag=lapki-be:latest .'
            }
        }
        
        stage('Deploy') {
            steps {
                sh 'sudo docker run -d -p 8081:8080 -p 3306:3306 --name lapki lapki-be:latest'
            }
        }
        
    }
}
