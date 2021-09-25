pipeline {
    agent any
    stages {
        stage('compile and clean') {
            steps {
		sh "mvn clean compile"
            }
        }
                stage('Deploy') {
            steps {
                sh "mvn clean install"
            }
	}
		stage('Execution') {
            steps {
                sh "java -jar target/SportyShoes-0.0.1-SNAPSHOT.jar"
            }
	}

    }
}
