Product: Stripe Integrated Scenarios

Environment Set-up:

 - Download and initialize WSO2 ESB 4.9.0 - SNAPSHOT .
 
 - Upload the following connectors to the ESB.
 
			-> mailchimp-connector-2.0.0
			-> mandril-connector-1.0.0
			-> stripe-connector-1.0.0
			-> zohocrm-connector-1.0.0
			-> zohobooks-connector-2.0.0
			
 - Follow the instructions given in the developer guide of above connectors, and enable the ESB axis configurations accordingly. Developer guide locations of aforementioned connectors are listed below.
 
            MailChimp 	- 
			Mandril		- 	
			Stripe 		- 	https://docs.wso2.com/display/CONNECTORS/Stripe+Connector
			ZohoCrm 	- 	https://docs.wso2.com/display/CONNECTORS/Zoho+CRM+Connector
			ZohoBooks 	- 	https://docs.wso2.com/display/CONNECTORS/Zoho+Books+Connector
			
 - If required, add the corresponding website security certificates to the ESB for aforementioned connectors. 

 - Add the sequences and templates in the "common" directory and "messages" directory(inside <Stripe_Connector_Home>/stripe-integrated-scenarios/src ), to the ESB that are listed as below.
			- sequences - 	faultHandlerSeq.xml
							removeResponseHeaders.xml
			- templates - 	responseHandlerTemplate.xml		
				
			- messages/sequence - 	stripe_changeSubscriptionsWithUpdates_messages.xml
									stripe_retrieveChargesAndCreatePayments_messages.xml
									stripe_sendInvoiceDetailsToClients_messages.xml
 
 - Each scenario folder consists of sub-folders named as <Case-xxx>. Before executing each case, please read through the corresponding Case section in this document.			

 01. Marketing and Creating Subscriptions
	[i] Case-001
		- Purpose:	(a) Create a subscription plan in Stripe.
					(b) Create a draft campaign for the subscription plan in Mailchimp.
					(c) Send the campaign to a list of subscribers already registered in Mailchimp.
		
		- Files:	(a) Proxy - <Stripe_Connector_Home>/stripe-integrated-scenarios/src/scenarios/Marketing and Creating Subscriptions/Case 001/proxy/stripe_createSubscriptionPlanAndMarket.xml
		
		- Request Parameters:	(a) stripeMailchimpListName - User is expected to create a new subscribers' list (in Mailchimp) for every new subscription plan (created in Stripe) and provide the name of the list as value for the parameter.
								(b) mailchimpListName - Name of the default subscribers' list, to whose contacts the campaign is expected to be sent. It is necessary to have at least one contact in the list in order to send the campaign.
								(c) mailchimpTemplateId - ID of the template using which the campaign is to be created. Please refer to 'Special Notes - (d)' on how to create a template in Mailchimp.
									*The remaining parameters are specific to Stripe and Mailchimp API. Please refer to the respective API reference for parameter explanation.
		
		- Special Notes: (a) Before each execution of the scenario, a new subscribers' list has to be created in Mailchimp and the name of the list (case-sensitive) should be provided along with the request (stripeMailchimpListName).
							 (Each subscription plan in Stripe is supposed to have a subscribers' list in Mailchimp to which the subscribers of the plan will be added.)
						 (b) Request parameter 'stripePlanId' should be unique and has to be changed for each execution.
						 (c) Name of a subscribers' list (in Mailchimp) should be provided with the request (mailchimpListName). It is the list to whose subscribers the campaign will be sent. It is necessary to have at least one contact in the list.
						 (d) In Mailchimp, go to 'Templates' -> 'Create Template' -> 'Paste in Code' -> Copy and paste the contents of the following file to 'Edit Code' section:  <Stripe_Connector_Home>/stripe-integrated-scenarios/mailchimp_template.html -> Save the template and copy the 'tid' value in the URL. Retain the value to be passed in for the parameter 'mailchimpTemplateId'.
		
	[ii] Case-002
		- Purpose:	(a) Get the clickers for a particular campaign (created in Case-001) and create them as Leads in ZohoCRM.
							
		- Files:	(a) Proxy - <Stripe_Connector_Home>/stripe-integrated-scenarios/src/scenarios/Marketing and Creating Subscriptions/Case 002/proxy/stripe_createCampaignClickersAsLeads.xml
		
		- Request Parameters:	(a) mailchimpCampaignId - ID of the Mailchimp campaign created in Case-0001 (Please note that the ID is returned as part of the response of Case-0001).
								(b) mailchimpClickURL - The URL embedded in the whose clicks will be tracked. The URL should exactly match the URL provided in the 'href' tag following HTML template file (line no. 334): <Stripe_Connector_Home>/stripe-integrated-scenarios/mailchimp_template.html
								(c) stripePlanId - ID of the Stripe subscription plan for which the campaign was initially created.
		
		- Special Notes: (a)  As a prerequisite for the scenario, create the following custom field for Leads in ZohoCRM with the given type and length.
							  Select the 'Contacts' option in 'Also create for' section while creating the custom field.
									
									Name of the custom field		Field Type		Length
								
								-	Stripe Plan ID					Text			20
						 (b) There has to be at least one campaign click for the scenario to be effective.
		
	[iii] Case-003
		- Purpose:	(a) Retrieve contacts from the ZohoCRM API and create them as customers in the Stripe API.
					(b) Keeps track of the details of the Stripe plan to which the customer is associated to.
							
		- Files:	(a) Proxy - <Stripe_Connector_Home>/stripe-integrated-scenarios/src/scenarios/Marketing and Creating Subscriptions/Case 003/proxy/stripe_createContactsAsCustomers.xml
		
		- Request Parameters:	(a) No special parameters required.
		
		- Special Notes: (a)  There should be at least one Contact in the ZohoCrm account having set Contact Information, Address Information, Credit Card Information('Credit Card Number', 'Credit Card Expiry Year', 'Credit Card Expiry Month') and Stripe Information ('Stripe Plan ID', 'Stripe Subscription Quantity', 'Stripe Customer ID').
						 (b)  ZohoCrm Contact should have the following custom fields with the given field types and lengths. Please create the sections and the custom fields under the respective sections.
									
									Name of the custom field		Field Type		Length	Section
								
								-	Credit Card Number				Text			20		Credit Card Information
								- 	Credit Card Expiry Year			Text			2		Credit Card Information
								-	Credit Card Expiry Month		Text			2		Credit Card Information
								-	Stripe Subscription Quantity	Text			10		Stripe Information
								-	Stripe Customer ID				Text			20		Stripe Information
								
						 (c)  Under Credit Card information of the ZohoCrm Contact there must be a valid test 'Credit Card Number','Credit Card Expiry Year' and 'Credit Card Expiry Month'
		
	[iv] Case-004
		- Purpose:	(a) Retrieve customers from Stripe account and add them to the subscriber list in the MailChimp account. 
							
		- Files:	(a) Proxy - <Stripe_Connector_Home>/stripe-integrated-scenarios/src/scenarios/Marketing and Creating Subscriptions/Case 004/proxy/stripe_batchSubscribeCustomersToList.xml
		
		- Request Parameters:	(a) stripePlanId	  - a valid plan ID of stripe. Note that the customers will be added to the subscription list in mailchimp that is associated with this plan ID if and only if the the customer is associated with the same plan ID.

	[v] Case-005
		- Purpose:	(a) Create Subscription for each account in the Stripe API.
					(b) Send subscription details to customers via email using the Mailgun API.
							
		- Files:	(a) Proxy - <Stripe_Connector_Home>/stripe-integrated-scenarios/src/scenarios/Marketing and Creating Subscriptions/Case 005/proxy/stripe_createSubscriptionAndNotifyCustomers.xml
		
		- Request Parameters: (a) stripeTaxPercent - If provided, each invoice created by the subscription will apply the tax rate, increasing the amount billed to the customer.
		
 02. Payment Handling
 
	[i] Case-001
		- Purpose:	(a) Create invoices in ZohoBooks for the invoices created in Stripe within the day of execution of the scenario.
					(b) Create contacts and items in ZohoBooks related to the invoices (if they don't already exist).
		
		- Files:	(a) Proxy - <Stripe_Connector_Home>/stripe-integrated-scenarios/src/scenarios/Payment Handling/Case 001/proxy/stripe_sendInvoiceDetailsToClients.xml
		
		- Request Parameters:	All parameters are usual and self-explanatory.
		
		- Special Notes: In order to successfully execute the scenario, there has to be at least one invoice in Stripe created within the day of execution.
	
		
	[ii] Case-002
		- Purpose:	(a) Create payments in ZohoBooks for charges committed in Stripe within the day of execution of the scenario.
					(b) Acknowledge the customers for their payment via email.
					(c) Send a summary of all the processsed charges (during the scenario execution) to the organization via email.
		
		- Files:	(a) Proxy - <Stripe_Connector_Home>/stripe-integrated-scenarios/src/scenarios/Payment Handling/Case 002/proxy/stripe_retrieveChargesAndCreatePayments.xml
					(b) Sequence - <Stripe_Connector_Home>/stripe-integrated-scenarios/src/scenarios/Payment Handling/Case 002/sequence/sendPaymentSummaryToOrganization.xml
		
		- Request Parameters:	(a) stripeProcessChargesOfAllCustomers - Specify 'true' if charges of all the customers in Stripe should be processed. Specifying 'false' would only process the charges of customers given in array in (b).
								(b) stripeCustomerIds - Array of Stripe customer IDs whose charges need to be processed. Specifying 'true' for (a) would make this parameter ignored.
								
		- Special Notes:	In order to successfully execute the scenario, there has to be at least one charge in Stripe committed within the day of execution.
	
 03. Subscription Updates
	[i] Case-001
		- Purpose:	(a) Retrieve subscriptions which have been updated (moved from one subscription to another) from Stripe API.
					(b)	Unsubscribe them from the current list in the MailChimp API.		 
					(c) Add them to the new list in the MailChimp API.
		
		- Files:	(a) Proxy - <Stripe_Connector_Home>/stripe-integrated-scenarios/src/scenarios/Subscription Updates/Case 001/proxy/stripe_changeSubscriptionsWithUpdates.xml
		
		- Request Parameters: No special parameters required.
		
		- Special Notes: (a) In order to start this case, as a pre-requisite, one or more customers' current subscription plan should be changed (updated) to a different plan as an offline process to let this case track the subscription updates.
						 (b) Please note that for one customer there can only be one change/update to his subscription in any given day. The behaviour of the scenario is not guaranteed otherwise.
	