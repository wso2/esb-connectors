Product: Integration tests for WSO2 ESB Google Custom Search connector

Pre-requisites:

 - Maven 3.x
 - Java 1.6 or above

Tested Platform:

 - Linux 3.11.0-19-generic (Ubuntu 14.04LTS)
 - WSO2 ESB 4.8.1

STEPS:

1. Make sure the ESB 4.8.1 zip file with latest patches available at "{PATH_TO_SOURCE_BUNDLE}/googlecustomsearch-connector/googlecustomsearch-connector-1.0.0/org.wso2.carbon.connector/repository/"

2. This ESB should be configured as below;
	Please make sure that the below mentioned Axis configurations are enabled (\repository\conf\axis2\axis2.xml).

   <messageFormatter contentType="text/html" class="org.wso2.carbon.relay.ExpandingMessageFormatter"/>
   
   <messageFormatter contentType="application/x-www-form-urlencoded" class="org.apache.axis2.transport.http.XFormURLEncodedFormatter"/>
   
   <messageFormatter contentType="text/javascript" class="org.wso2.carbon.relay.ExpandingMessageFormatter"/>	
   
   <messageFormatter contentType="application/octet-stream" class="org.wso2.carbon.relay.ExpandingMessageFormatter"/>	
   
   <messageBuilder contentType="text/html" class="org.wso2.carbon.relay.BinaryRelayBuilder"/>
   
   <messageBuilder contentType="application/x-www-form-urlencoded" class="org.apache.synapse.commons.builders.XFormURLEncodedBuilder"/>
   
   <messageBuilder contentType="text/javascript" class="org.wso2.carbon.relay.BinaryRelayBuilder"/>
   
   <messageBuilder contentType="application/octet-stream" class="org.wso2.carbon.relay.BinaryRelayBuilder"/>
   
   Enable the relevant message builders and formatters in axis2 configuration file when testing file upload methods.
   
		Eg: Below mentioned message formatter and the builder should be enabled when uploading ".png" files to test file upload methods.
		
		<messageFormatter contentType="image/png" class="org.wso2.carbon.relay.ExpandingMessageFormatter"/>

		<messageBuilder contentType="image/png" class="org.wso2.carbon.relay.BinaryRelayBuilder"/>

3. Make sure "integration-base" project is placed at "{basedir}/../"

4. Navigate to "integration-base" and run the following command.
      $ mvn clean install

5. Add following dependancy to the file "${basedir}/pom.xml"

    <dependency>
        <groupId>org.wso2.esb</groupId>
        <artifactId>org.wso2.connector.integration.test.base</artifactId>
        <version>4.8.1</version>
        <scope>system</scope>
        <systemPath>${basedir}/../integration-base/target/org.wso2.connector.integration.test.base-4.8.1.jar</systemPath>
    </dependency>

7. Make sure the Google Custom Search test suite is enabled (as given below) and all other test suites are commented in the following file "{basedir}/src/test/resources/testng.xml"

     <test name="Google Custom Search-Connector-Test" preserve-order="true" verbose="2">
             <packages>
                 <package name="org.wso2.carbon.connector.integration.test.googlecustomsearch"/>
             </packages>
     </test>

8. Creating a Google Cloud Console account: 
	- Go to https://console.developers.google.com/
	- Create a new Google Cloud Console project
	- Go to your newly created project and go to APIs and Auth
	- Enable the Custom Search API
	- Go to Credentials and find Public API access
	- Generate a new API key

9. Creating a Custom Search Engine

	- Go to https://www.google.com/cse/all
	- Create a new search engine and go to control panel and find Search engine ID

9. Copy the connector properties file at "googlecustomsearch/src/test/resources/artifacts/ESB/connector/config/googlecustomsearch.properties".

    i)  apiKey - Use the API key you got from step 8.
    ii) cseID - Use the Search engine ID you got from step 9.
   

10. Navigate to "${basedir}/" and run the following command.
      $ mvn clean install

NOTE => The Custom Search API allows only 100 queries per day for free,
	if you want to search more than 100, you have to paid for it 
	or create a new API key and use it.
        apiKey:AIzaSyBAj-H1k2IGT19ZTqp_UwZzJmzvzQvV4tw
	cscID:014869045608377880101:18pywcgrwls
