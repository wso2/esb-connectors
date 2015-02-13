Product: Integration tests for WSO2 ESB Eventbright connector

Pre-requisites:

 - Maven 3.x
 - Java 1.6 or above
 - The org.wso2.esb.integration.integration-base project is required. The test suite has been configured to download this project automatically. If the automatic download fails, download the following project and compile it using the mvn clean install command to update your local repository:
   https://github.com/wso2-dev/esb-connectors/tree/master/integration-base

Tested Platform: 

 - Microsoft WINDOWS V-7
 - UBUNTU 13.04
 - WSO2 ESB 4.8.1
 
 Special Note :  It should be noted that following procedure is applicable to the methods that are implemented by Virtusa, that are,
	-createEvent
	-publishEvent
	-createTicketClass

Steps to follow in setting integration test.

 1. Download ESB 4.8.1 from official website.

 2. Deploy relevant patches, if applicable and the ESB should be configured as below.
	 Please make sure that the below mentioned Axis configurations are enabled (\repository\conf\axis2\axis2.xml).
		
	<messageFormatter contentType="application/json" 
		class="org.apache.synapse.commons.json.JsonStreamFormatter"/>
		
	<messageBuilder contentType="application/json" 
		class="org.apache.synapse.commons.json.JsonStreamBuilder"/>

 3. Compress modified ESB as wso2esb-4.8.1.zip and copy that zip file in to location "{Eventbrite_Connector_Home}/eventbrite-connector-1.0.0/org.wso2.carbon.connector/repository/".

 4. Create an Eventbright trial account and derive the OAuth token.
	i) 		Using the URL "https://www.eventbrite.com/signup/" create an Eventbright trial account.
	ii)		Login to the created Eventbright account and go to http://www.eventbrite.com/myaccount/apps/ link and create a new app to obtain your personal OAuth token.

 5. Update the eventbright properties file at location "{Eventbrite_Connector_Home}/eventbright-connector/eventbright-connector-1.0.0/org.wso2.carbon.connector/src/test/resources/artifacts/ESB/connector/config/eventbrite.properties" as below.
	
	i)		apiUrl 							- 	The API URL specific to the created account.
	ii) 	accessToken						-   the oauth token obtained in step 4 ii).
	iii)	name							- 	Valid string as the name of the new event.
	iv)		description						- 	Valid string to describe the new event.
	v)		timeZone						- 	A valid time zone.
	vi)		currency						-   Currency code of the ticket.
	vii)	logoId			    			-   Provide a unique identifier of the logo for the event.
	viii)	className			   			-   A valid string as the name of the ticket class.
	ix)	    quantityTotal 			        -   Total available number of the ticket class.
	x)	   	classDescription	    		-   A valid string to describe the ticket class.
	xi)	    costCurrency			    	-   Currency of the ticket.
	xii)	costValue			    		- 	Cost of the ticket.	
	
	
 6. Navigate to "{Eventbrite_Connector_Home}/eventbright-connector/eventbright-connector-1.0.0/org.wso2.carbon.connector/" and run the following command.
      $ mvn clean install
	  
	  Note:- eventbright trial account expires within 30 days.

		