// Instruments
node {
 
  properties([pipelineTriggers([[$class: 'GitHubPushTrigger']])])
  def mvnHome = tool 'maven3'

  // Use JDK8 oracle
  env.JAVA_HOME="${tool 'oracle-jdk8'}"
  env.PATH="${env.JAVA_HOME}/bin:${env.PATH}"

  stage('Clean') {
    checkout scm
    sh "${mvnHome}/bin/mvn -B clean"
  }

  stage('Build') {
    sh "${mvnHome}/bin/mvn -B  compile test-compile"
  }

  stage('Test') {

    if (env.BRANCH_NAME == 'master') {
      sh "${mvnHome}/bin/mvn -B  test"
    } else {
      sh "${mvnHome}/bin/mvn -B -Dmaven.test.failure.ignore test"
    }
    //junit '**/target/surefire-reports/TEST-*.xml'
  }

  if (env.BRANCH_NAME == 'dev' || env.BRANCH_NAME == 'master') {

    stage('Deploy') {
        sh "${mvnHome}/bin/mvn -B -DskipTests=true -Dmaven.test.failure.ignore deploy"
        step([$class: 'ArtifactArchiver', artifacts: '**/target/*.jar', fingerprint: true])
    }

    stage("Deploy Documentation") {

        sh "make -C doc html"
        sh "mkdir -p /data/cd/org.odfi.instruments/instruments-core/${env.BRANCH_NAME}/doc/"
        sh "cp -Rf doc/build/html/* /data/cd/org.odfi.instruments/instruments-core/${env.BRANCH_NAME}/doc/"
        
    }

  } else {
    stage('Package') {
        sh "${mvnHome}/bin/mvn -B -DskipTests=true -Dmaven.test.failure.ignore package"
        step([$class: 'ArtifactArchiver', artifacts: '**/target/*.jar', fingerprint: true])
    }
  }

 


}
