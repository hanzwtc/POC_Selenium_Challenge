@Library('shared-test@master')
import sharedlibTest.JenkinsUtils
def utils = new JenkinsUtils(steps,this)
def enviroment = "dev"
try{
    node(){
    stage("Initial"){
        cleanWs()
        checkout scm 
    }
    stage("Execute Test"){
        utils.executeMavenIT()
    }
    stage("Jira-Xray"){
        utils.deployToJiraXray()
    }
    }
}
catch (Exception e){
    node(){
        throw e
    }
}