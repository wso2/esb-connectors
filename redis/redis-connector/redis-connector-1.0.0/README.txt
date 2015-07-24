Product: Integration tests for WSO2 ESB redis connector

Pre-requisites:

 - Maven 3.x
 - Java 1.6 or above
 - The org.wso2.esb.integration.integration-base project is required. The test suite has been configured to download this project automatically. If the automatic download fails, download the following project and compile it using the mvn clean install command to update your local repository:
      https://github.com/wso2-dev/esb-connectors/tree/master/integration-base

Tested Platform: 

 - Mac OSx 10.9
 - WSO2 ESB 4.8.1

STEPS:

 1. Make sure the ESB 4.8.1 zip file available at "{REDIS_CONNECTOR_HOME}/redis-connector/redis-connector-1.0.0/org
 .wso2.carbon.connector/repository/"

 2. Download Redis server from http://redis.io/download
 
 3. Start the Redis server
	 
 4. Navigate to "{REDIS_CONNECTOR_HOME}/redis-connector/redis-connector-1.0.0/org.wso2.carbon.connector/" and
 run the following command.
      $ mvn clean install


