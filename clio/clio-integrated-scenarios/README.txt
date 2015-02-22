Product: Clio Integrated Scenarios

Environment Set-up:

 - Download and initialize ESB 4.8.1 .
 
 - Upload the following connectors to the ESB.
 
			-> clio-connector-1.0.0
			-> simplenote-connector-1.0.0
			-> zohobooks-connector-1.0.0
			-> gmail-connector-1.0.0
			-> zohocrm-connector-1.0.0
			
 - Follow the instructions given in the developer guide of above connectors, and enable the ESB axis configurations accordingly. Developer guide locations of aforementioned connectors are listed below.
 
            Clio - https://docs.wso2.com/display/CONNECTORS/Clio+Connector
			SimpleNote - https://docs.wso2.com/display/CONNECTORS/SimpleNote+Connector
			ZohoBooks - https://docs.wso2.com/display/CONNECTORS/ZohoBooks+Connector
			Gmail - https://docs.wso2.com/display/CONNECTORS/Gmail+Connector
			ZohoCRM - https://docs.wso2.com/display/CONNECTORS/ZohoCRM+Connector
			
 - If required, add the corresponding website security certificates to the ESB for aforementioned connectors. 

 - Add the sequences and templates in the "common" directory (<Clio_Connector_Home>/clio-integrated-scenarios/src/common ), to the ESB that are listed as below.
			- sequences - 	faultHandlerSeq.xml
							removeResponseHeaders.xml
							scenarioConstants.xml
			- templates - 	sendNotifications.xml
							responseHandlerTemplate.xml
							getCustomFieldId.xml
 
 - Each scenario folder consists of sub-folders named as <Case-xxx>. Before executing each case, please read through the corresponding Case section in this document.			

 01. Matter Initiation
	[i] Case-001
		- Purpose:	(a) Create contacts either directly in Clio API or by retrieving selected contacts (that need legal assistance) from ZohoCRM API. 
						In the latter case, duplicate contacts from ZohoCRM will be avoided.
					(b) For each contact created in Clio in (a), a Tag is created in SimpleNote API.
					(c) For each contact created in Clio in (a), a note will be created under the respective Tag in SimpleNote.
						Name of the Tag would be in the format 'Active note for <first-name> <last-name>...'
		
		- Files:	(a) Proxy - <Clio_Connector_Home>\clio-integrated-scenarios\src\scenarios\Matter Initiation\Case 001\proxy\clio_createContactAndTag.xml
					(b) Template - <Clio_Connector_Home>\clio-integrated-scenarios\src\scenarios\Matter Initiation\Case 001\templates\createContactAndTag.xml
		
		- Request Parameters:	(a) zohoCrmContactIds - An array of contact Ids from ZohoCrm. 		
								(b) clioContact 	  - Contact details of a client in the below given format. Note that this is not required when zohoCrmContactIds is given.
								
										{
											"prefix": "Mr",
											"firstName": "Suresh", (Mandatory)
											"lastName": "Perera", (Mandatory)
											"title": "Senior Consultant",
											"addresses": [
												{
													"name": "Billing",
													"street": "4148 Kuhlman Islands",
													"city": "Hirthemouth",
													"postal_code": "75745-8618",
													"province": "Ohio",
													"country": "United States"
												}
											],
											"emailAddresses": [
												{
													"name": "Work",
													"address": "binsandsons@kofeest.net",
													"default_email": "binsandsons@kofeest.net"
												}
											],
											"instantMessengers": [
												{
													"name": "Work",
													"address": "wso2@gmail.com"
												}
											],
											"phoneNumbers": [
												{
													"name": "Work",
													"number": "872-913-0768"
												}
											]
										}
		
		- Special Notes: (a) If the user decides to fetch contacts from ZohoCrm then initially there should be few records in the user's ZohoCrm account.
							 This is not required if the user directly gives the contact's details in the request.
						 (b) A custom field named 'Clio Contact ID' should be created in ZohoCRM API for contacts.
						 (c) A custom field named 'Current SimpleNote Key' should be created in Clio for contacts as a prerequisite.
		
	[ii] Case-002
		- Purpose:	(a) Retrieve the contents of selected notes in SimpleNote and create them as matters in Clio under the contact identified by the name of the tag to which the note belongs.
					(b) Add selected notes to a new Tag called 'Selected-Notes'
					(c) For each given contact, a note will be created under the respective Tag in SimpleNote. That note would then be the active note for the contact and will have the default content 'Active note for <first-name> <last-name>...'
							
		- Files:	(a) Proxy - <Clio_Connector_Home>\clio-integrated-scenarios\src\scenarios\Matter Initiation\Case-002\proxy\clio_selectNoteAndCreateMatter.xml
		
		- Request Parameters:	(a) clioContacts - An array of contact IDs along with a boolean value "createMatter" which specifies whether the active note of the contact (in SimlpeNote) needs to be selected to create matter out of it or not.
											{"clioContactID":"889056955", "createMatter":true}
		
		- Special Notes: (a) A tag has to be created under the name 'Selected-Notes' in SimpleNote API as a prerequisite.
						 (b) Contacts whos IDs are passed in for this case, should have been created using Case-001 of the scenario (not in any other way).
						 (b) The active note of each contact has to be modified (from default content) before it can be created as a Matter.
						 (e) It is advisable to consider this case as a continuation of Case-001 of Matter Initiation scenario and follow the special notes of Case 001 before executing Case-002.
	
 02. Task Handling
 
	[i] Case-001
		- Purpose:	(a) Create a Task in Clio.
					(b) Email the details of the created Task to the assignee via GMail.
		
		- Files:	(a) Proxy - 	<Clio_Connector_Home>\clio-integrated-scenarios\src\scenarios\Task Handling\Case-001\proxy\clio_sendTaskNotificationsToAssignees.xml

		- Request Parameters:	No special parameters required.
		
		- Special Notes: Assignee of the Task should have an email address in Clio. That email should be made primary for the contact.
							The API behavior when providing INVALID view Ids is not predictable. Therefore please make sure only valid view Ids are used while executing the scenarios.
	
		
	[ii] Case-002
		- Purpose:	(a) Close the given task (identified by the ID) in Clio.
					(b) Notify the Assigner via an email about the completion of the task.
		
		- Files:	(a) Proxy - <Clio_Connector_Home>\clio-integrated-scenarios\src\scenarios\Task Handling\Case-002\proxy\clio_sendNotificationsToAssignersOnTasksCompletion.xml
		
		-Request Parameters:	(a) clioTaskIds - An array of VALID task IDs to close.
		
		- Special Notes:	(a) Task IDs provided for 'clioTaskIds' needs to be VALID and open.
	
 03. Invoicing
	[i] Case-001
		- Purpose:	(a) Create a contact in ZohoBooks for the owner of the Bill (in Clio).
					(b) Create an item in ZohoBooks for the Matter of the Bill (in Clio).
					(c) Create an invoice in ZohoBooks for a Bill (in Clio).
					(d) Email the invoice to the primary email of the contact.
		
		- Files:	(a) Proxy - <Clio_Connector_Home>\clio-integrated-scenarios\src\scenarios\Invoicing\Case 001\proxy\clio_sendInvoiceDetailsToClients.xml
					(b) Template - <Clio_Connector_Home>\clio-integrated-scenarios\src\scenarios\Invoicing\Case 001\templates\createInvoice.xml
		
		-Request Parameters:	(a) clioBillIds - An array of VALID bill IDs.
		
		- Special Notes: (a) A custom field named 'ZohoBooks Contact ID' and 'ZohoBooks Contact Person ID' should be created in Clio for contacts as a prerequisite.
						 (b) When creating bills to pass to the scenario the following should be adhered to.
								- Bills should be created for a contact and a matter.
								- Multiple bills can be created for the same contact but different matters should be used for each bill.
								- Add an expense entry to the Bill
						 (c) Please make sure the timezone and currency of the organization in ZohoBooks and Clio account are the same.
						 (d) Please make sure that the contacts in Clio under whom the bills are created should have an email address for the invoice to be sent.
						 (e) Mapping between Clio and ZohoBooks are as follows.
								(Clio, ZohoBooks) => (Contact, Contact), (Matter, Item), (Bill, Invoice)
	