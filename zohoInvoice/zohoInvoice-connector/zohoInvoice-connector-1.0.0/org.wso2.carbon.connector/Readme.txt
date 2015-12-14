Product: Integration tests for WSO2 ESB ZohoInvoice connector through REST API

Pre-requisites:

 - Maven 3.x
 - Java 1.6 or above

Tested Platform: 

 -  MAC OS
 - WSO2 ESB 4.8.1

STEPS:

1. Download ESB 4.9.0 from the official website and add the certificate of Zoho Invoice.

2. Compress the modified ESB as wso2esb-4.9.0.zip and copy this zip file to <ZOHOINVOICE_CONNECTOR_HOME>/zohoInvoice-connector/zohoInvoice-connector-1.0.0/org.wso2.carbon.connector/repository/.
	
3.Create an zohoInvoice  account and derive the API key:
Go to https://www.zoho.com, create an zohoInvoice  account, and log in.
Follow the instruction given in https://www.zoho.com/invoice/api/v3/ to get the API token.

4.Update the zohoInvoice properties file at the location <ZOHOINVOICE_CONNECTOR_HOME>/zohoInvoice-connector/zohoInvoice-connector-1.0.0/org.wso2.carbon.connector/src/test/resources/artifacts/ESB/connector/config with required parameters.

5.Navigate to <ZOHOINVOICE_CONNECTOR_HOME>/zohoInvoice-connector/zohoInvoice-connector-1.0.0/org.wso2.carbon.connector/ and run the following command.
      $ mvn clean install

	
