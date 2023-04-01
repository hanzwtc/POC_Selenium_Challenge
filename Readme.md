# BaseFramework : Distributed & Mainframe Apps.

This com.repository have a base framework to test distributed and Mainframe Apps.

### Pre-Requeriments

Install the next tools :
* Install Eclipse or Intellj IDE
* java 1.8 32 bits for Pcomm test
* Pcomm lastest version ( download a trial)
* Maven V3
* Configure settings.xm with artifactory url ( replace settings.xml file in your maven home eg : D:\Tools\maven\apache-maven-3.6.2\conf  check confiEnv folder).
* `<localRepository>D:/m2/com.repository</localRepository>` configure this path in settings.xml in order to download dependencies.
* Install cacerts in java path ( replace cacerts file in ..\jre1.8.x.x\lib\security\. check confiEnv folder).
* Install git and configure .gitconfig ( replace .gitconfig file in C:\Users\SXXXX check confiEnv folder) in order to clone , push and pull .


### Status
 
Working : 
* Webdriver from local instance
* Feature Override 
* Test data from Excel
* Extent Reports json Plugin
* thread local driver instance working 
* Maven Execution using cmd works
* Pcomm ran in jenkins sucessfully

Not Working : 
* log4j properties config 

to Add / Inquire : 
* jenkinsfile in order to executer the test automatically.
* API Code for Jira Xray test case Integration
* Cucumber reports point to "BeforeActions.beforeStep(Scenario)" for al the steps 
* Screenshot embed is not available for pcomm verify step 


