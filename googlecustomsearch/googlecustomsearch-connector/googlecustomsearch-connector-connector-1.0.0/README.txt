Product: Integration tests for WSO2 ESB Google Custom Search connector

Pre-requisites:

 - Maven 3.x
 - Java 1.6 or above

Tested Platform:

 - Linux 3.11.0-19-generic (Ubuntu 14.04LTS)
 - WSO2 ESB 4.8.1

STEPS:

1. Make sure the ESB 4.8.1 zip file with latest patches available at "Integration_Test/products/esb/4.8.1/modules/distribution/target/"

2. This ESB should be configured as below;
    In Axis configurations (\repository\conf\axis2\axis2.xml).

   i) Enable message formatter for "text/html" in messageFormatters tag
            <messageFormatter contentType="text/html" class="org.wso2.carbon.relay.ExpandingMessageFormatter"/>

   ii) Enable message builder for "text/html" in messageBuilders tag
            <messageBuilder contentType="text/html" class="org.wso2.carbon.relay.BinaryRelayBuilder"/>

   iii) Enable message formatter for "application/json" in messageFormatters tag
            <messageFormatter contentType="application/json" class="org.apache.synapse.commons.json.JsonStreamFormatter"/>

   iv) Enable message builder for "application/json" in messageBuilders tag
            <messageBuilder contentType="application/json" class="org.apache.synapse.commons.json.JsonStreamBuilder"/>

   V) Install HTTP PATCH request enabling patch and Json patch to ESB 4.8.1
        patch0804 - http PATCH request patch
        patch0800 - Json string escape ("\") character patch

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
