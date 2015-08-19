Product: Integration tests for WSO2 ESB custom inbound endpoint

Pre-requisites:

 - Maven 3.x
 - Java 1.6 or above
 - The org.wso2.esb.integration.integration-base project is required. The test suite has been configured to download this project automatically. If the automatic download fails, download the following project and compile it using the mvn clean install command to update your local repository:
   https://github.com/wso2-dev/esb-connectors/tree/master/integration-base

Tested Platform:

 - Microsoft WINDOWS V-7
 - UBUNTU 14.04
 - WSO2 ESB 4.9.0

 Note:
	This test suite can be executed based on two scenarios.

    Set up a new twitter account and follow all the instruction given below in step 4.

Steps to follow in setting integration test.

 1. Download ESB 4.9.0 from official website.

 2. Deploy relevant patches, if applicable.

 3. Compress modified ESB as wso2esb-4.9.0.zip and copy that zip file in to location "{Twitter_Inbound_Endpoint_Home}/twitter-inbound-endpoint/twitter-inbound-endpoint-1.0.0/org.wso2.carbon.connector/repository/".

 4. Prerequisites for twitter inbound endpoint Integration Testing.

	i) 	 Create an twitter developer account using the URL "https://dev.twitter.com".
	ii)	 Get the consumerkey, consumersecret, accesstoken and accesssecret from the twitter Developer Account Home using the URL "https://dev.twitter.com"

 5. Update the twitter properties file at location "{Twitter_Inbound_Endpoint_Home}/twitter-inbound-endpoint/twitter-inbound-endpoint-1.0.0/org.wso2.carbon.inbound/src/test/resources/artifacts/ESB/inbound/config" as below.

	i)		consumerkey 						- Place the consumerkey that got from the twitter Developer Account(step 4[ii]).
	ii) 	consumersecret						- Place the consumersecret that got from the twitter Developer Account(step 4[ii]).
	iii)	accesstoken							- Place the accesstoken that got from the twitter Developer Account(step 4[ii]).
	iv)		accesssecret						- Place the accesssecret that got from the twitter Developer Account(step 4[ii]).

 6. Navigate to "{Twitter_Inbound_Endpoint_Home}/twitter-inbound-endpoint/twitter-inbound-endpoint-1.0.0/org.wso2.carbon.inbound/" and run the following command.
      $ mvn clean install
