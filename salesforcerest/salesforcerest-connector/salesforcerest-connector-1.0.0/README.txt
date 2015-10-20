Product: WSO2 ESB Connector for Salesforce REST API + Integration Tests

 Pre-requisites:

    - Maven 3.x
    - Java 1.6 or above
    - The org.wso2.esb.integration.integration-base project is required. The test suite has been configured to download this project automatically. If the automatic download fails, download the following project and compile it using the mvn clean install command to update your local repository:
                 https://github.com/wso2/esb-connectors/tree/master/integration-base-1.0.1

    Tested Platforms:

    - Microsoft WINDOWS V-7
    - Ubuntu 13.04
    - WSO2 ESB 4.9.0-BETA



Steps to follow in setting integration test.


 1. Download ESB 4.9.0-BETA-SNAPSHOT by navigating to the following URL: http://svn.wso2.org/repos/wso2/people/malaka/ESB/beta/


 2. Deploy relevant patches, if applicable. Place the patch files into location <ESB_HOME>/repository/components/patches.

 3. ESB should be configured as below.
    Please make sure that the below mentioned Axis configurations are enabled (\repository\conf\axis2\axis2.xml)

	    Message Formatters
            <messageFormatter contentType="application/json"
							  class="org.apache.synapse.commons.json.JsonStreamFormatter"/>
	    <messageFormatter contentType="text/html"
        						  class="org.wso2.carbon.relay.ExpandingMessageFormatter"/>

	    Message Builders
	    <messageBuilder contentType="application/json"
							  class="org.apache.synapse.commons.json.JsonStreamBuilder"/>
	    <messageBuilder contentType="text/html"
							  class="org.wso2.carbon.relay.BinaryRelayBuilder"/>



 4. Compress modified ESB as wso2esb-4.9.0-BETA.zip and copy that zip file in to location "<ESB_CONNECTORS_HOME>/repository/".


 5. Create a Salesforce account and create a connected app.
       i)  Using the URL "https://salesforce.com" create an account.
       ii) Create a connected app to get the clientId and clientSecret. Refer below link to create connected app.
            https://developer.salesforce.com/docs/atlas.en-us.api_rest.meta/api_rest/intro_defining_remote_access_applications.htm.

 Follow the below URL to 
 6.Before you start performing various operations with the connector, make sure to import the Salesforce certificate to your ESB client keystore.

   Follow the steps below to import the Salesforce certificate into the ESB client keystone:

       i.Go to your Salesforce account (e.g.,https://ap2.salesforce.com), and click the lock icon on the address bar to view the certificate.
       ii.Have a look at the certificate details and export the certificate to the file system.
       iii.Import the certificate into the ESB client keystone via the Management Console or by running the following command.

       keytool -importcert -file <certificate file> -keystore <ESB>/repository/resources/security/client-truststore.jks -alias "SFDCCertImport"

 7. Make sure that salesforcerest is specified as a module in ESB Connector Parent pom.
            <module>salesforcerest/salesforcerest-connector/salesforcerest-connector-1.0.0/org.wso2.carbon.connector</module>

 8. Update the salesforcerest properties file at location "<SALESFORCEREST_CONNECTOR_HOME>/salesforcerest-connector/salesforcerest-connector-1.0.0/org.wso2.carbon.connector/src/test/resources/artifacts/ESB/connector/config" as below.

        i)apiUrl 							- 	The URL of the salesforce connected app.
        ii)apiVersion                                                   -       The api version for the connected app(New version is better because some of the features are supported only on the new 											          version).
   	iii)refreshToken                                                -       The refresh token to access the API.
        iv)clientId                                                     -       The Consumer key of API to access the connected app.
        v)clientSecret                                                  -       The consumer key of API to access the connected app.
        vi)hostName                                                     -       Value of the hostname to authenticate salesforce(If you are using sandbox then the hostName is "https://test.salesforce.com" 											          instead of "https://login.salesforce.com").
	vii)accessToken                                                 -       The access Token to access the API.
        viii)sobjectType        				        -       The sObject type.
	ix)sobject						        -       The type of the sObject.
	x)startTime                                                     -       The start time,for that the date and time should be provided in ISO 8601 format.
        xi)endTime                                                      -       The end time , for that the date and time should be provided in ISO 8601 format.
	xii)userId                                                      -       The id of a user.
	xiii)Id								-       The id of the record.
	xiv)idToDelete          					-       The id of the record to be deleted.
	xv)actionName                    			        -       The action name.
	xvi)rowId                					-       The Id of a record to get the basic information.
	xvii)queryString                                                -       The SOQL query string.
	xviii)nextRecordsUrl 						-       The url is return at the query or queryAll response to retrieve additional query results. If the records 											           is too large only it will return at the query/queryAll response.
	xix)newPassword 					        -       The new password of saesforce to be set to access.
        xx)fields         						-       The comma separated fields of a particular sObject.
	xxi)searchString                                                -       The SOQL search string.
	xxii)sObjectList	                                        -       The comma separated sObject type.
	xxiii)listViewID                                                -       The id of the listview.
	xxiv)name                                                       -       The name of the newly created record/updated record.
	xxv)description                				        -       The description of the newly created/updated record.
     xxvi)apiStartTime                                  -       The start time for api, for that the date and time should be provided in ISO 8601 format.
       xxvii)apiEndTime                                 -       The end time for api, for that the date and time should be provided in ISO 8601 format.
Properties iii), vii) and xiv) needs to be changed before running the integration test each time.
You can put the value for nextRecordsUrl(xviii) property when the results/response of query or queryAll have too large of records only. If you have too large of records only you can get passed integration test at queryMore and queryAllMore.
Don't put the same value to xiv) and (xiii, xvi).


 9.  Navigate to "{ESB_Connector_Home}/" and run the following command.
             $ mvn clean install
