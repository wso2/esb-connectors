Product: EventBrite Integrated Scenarios

Environment Set-up:

 - Download and initialize ESB 4.8.1.
 
 - Upload the following connectors to the ESB.
 
			-> eventbrtie-connector-1.0.0
			-> surveymonkey-connector-2.0.0
			-> gmail-connector-1.0.0
			-> nexmo-connector-1.0.0
			-> gotowebinar-connector-1.0.0
			-> constantcontact-connector-1.0.0
			-> facebook-connector-1.0.0
			
 - Follow the instructions given in the developer guide of the above connectors, and enable the ESB axis configurations accordingly. Developer guide locations of the aforementioned connectors are listed below.
			
			Eventbrite - https://docs.wso2.com/display/CONNECTORS/Eventbrite+Connector
			SurveyMonkey  - https://docs.wso2.com/display/CONNECTORS/SurveyMonkey+Connector
			GMail - https://docs.wso2.com/display/CONNECTORS/Gmail+Connector
			Nexmo - https://docs.wso2.com/display/CONNECTORS/Nexmo+Connector
			GoToWebinar - https://docs.wso2.com/display/CONNECTORS/GoToWebinar+Connector
			ConstantContact - https://docs.wso2.com/display/CONNECTORS/Constant+Contact+Connector
			Facebook - https://docs.wso2.com/display/CONNECTORS/Facebook+Connector

 - If required, add the corresponding website security certificates to the ESB for aforementioned connectors. 

 - Add the sequences and templates in the "common" directory (<EVENTBRITE_CONNECTOR_HOME>/eventbrite-integrated-scenarios/src/common ), to the ESB that are listed as below.
			- sequences - faultHandlerSeq.xml
						  removeResponseHeaders.xml
			- templates - responseHandlerTemplate.xml
 
 - Each scenario folder consists of sub-folders named as <Case-xxx>. Before executing each case, please read through the corresponding Case section in this document.			

 01. Initiate Event
	[i] Case -001
		- Purpose : (a) Retrieve selected set of webinars from GoToWebinar and create and publish those as Events in Eventbrite.
		
		- Files:	(a) Proxy - <EVENTBRITE_CONNECTOR_HOME>\eventbrite-integrated-scenarios\src\scenarios\Initiate Event\Case-001\proxy\eventbrite_createWebinarAsEventAndPublish.xml
					(b) Template - <EVENTBRITE_CONNECTOR_HOME>\eventbrite-integrated-scenarios\src\scenarios\Initiate Event\Case-001\templates\createEventAndTraining.xml
		- Request Parameters:	(a) webinar - Data object for the Webinar and Event related data. 
													- webinarKey - Key of the Webinar.
													- price - Price of the Webinar. If the price value is empty, it will create the event as a free event in EventBrite. 
													- currency - Currency value of the event (e.g. USD).
													- timeZone - Time zone of the event (e.g. Asia/Colombo).
													- ticketQty - No. of the tickets for the event.
 
 02. Marketing Events
	[i] Case-001
		- Purpose:	(a) Retrieve selected set of eventbrite published events.
					(b) If (a) is successful -> Create a mail campaign and schedule it in ConstantContact.
					(c) If (a) is successful -> Add events as posts in the Facebook.
					
		- Files:	(a) Proxy -		<EVENTBRITE_CONNECTOR_HOME>\eventbrite-integrated-scenarios\src\scenarios\Marketing Events\Case-001\proxy\eventbrite_retrieveEventsAndMarketing.xml
					(b) Template -	<EVENTBRITE_CONNECTOR_HOME>\eventbrite-integrated-scenarios\src\scenarios\Marketing Events\Case-001\templates\createPostsAndCampaigns.xml
					
		- Request Parameters:	(a) eventBriteEventIds - An array of EventBrite event IDs.
								(b) constantContactFromEmail - The ConstantContact campaign sender's email address.
								(c) constantContactSentToContactLists - An array containing contact list ID objects to which the campaigns should be sent.
								(d) constantContactreplyToEmail- The email address to which the subscribers can reply for the ConstantContact mail campaign.
								(e) constantContactFromName - Name of the sender of the ConstantContact mail campaign.
								
		- Special Notes: 	Time zone of ConstantContact should always be 'London, United Kingdom' in order to get the exact same scheduled time of the ConstantContact mail campaign.
								
	[ii] Case-002
		- Purpose:	(a) Retrieve a list of contacts from ConstantContact who have clicked on the link in the sent mail campaign, and create a contact list in ConstantContact.
		
		- Files:		(a) Proxy-	<EVENTBRITE_CONNECTOR_HOME>\eventbrite-integrated-scenarios\src\scenarios\Marketing Events\Case-002\proxy\eventbrite_retrieveClickersAndCreateContactList.xml

		- Request Parameters:	(a) constantContactCampaignId -  ID	of the sent campaign in ConstantContact from which the clickers will be retrieved.
								(b) constantContactContactListId - ID of the contact list in ConstantContact to which the clickers will be added.
								
 03. Manage Attendees
	[i] Case -001
		- Purpose : (a) Retrieve selected set of Event attendees from Eventbrite and create registrants for specified Webinar in GoToWebinar.
		
		- Files:	(a) Proxy - <EVENTBRITE_CONNECTOR_HOME>\eventbrite-integrated-scenarios\src\scenarios\Manage Attendees\Case-001\proxy\eventbrite_retreiveAttendeesAndCreateRegistrants.xml
					
		- Request Parameters:	(a) events - Mapping between an Event and a Webinar as a JSON object array. 
													- webinarKey - Key of the Webinar.
													- eventId - Eventbrite event ID.
 04. Event Reminders
	[i] Case -001
		- Purpose : (a) Fetch events which are due tomorrow from Eventbrite and send reminders to the event attendees via Nexmo and Gmail.
		
		- Files:	(a) Proxy - <EVENTBRITE_CONNECTOR_HOME>\eventbrite-integrated-scenarios\src\scenarios\Event Reminders\Case-001\proxy\eventbrite_sendEventReminders.xml
					(b) Template - <EVENTBRITE_CONNECTOR_HOME>\eventbrite-integrated-scenarios\src\scenarios\Event Reminders\Case-001\templates\sendReminders.xml

		- Special Notes:	To send SMS reminders, an attendee should be registered with a cell phone number. In order to add the cell phone number to an attendee, update the event order form through the Event Dashboard in Eventbrite.
 													
 05. Event Surveys
	[i] Case-001
		- Purpose:	(a) Retrieve attendees of past events from Eventbrite and send surveys to them using SurveyMonkey.
		
		- Files:	(a) Proxy - 	<EVENTBRITE_CONNECTOR_HOME>\eventbrite-integrated-scenarios\src\scenarios\Event Surveys\Case-001\proxy\eventbrite_retrievePastEventAttendeesAndSendSurveys.xml

		
		-Request Parameters:	(a) pastEventData - Mapping between an Event and a Survey as a JSON object array.  				
								(b) surveyMonkeySurveyTitle - Title for the survey.
								(c) surveyMonkeyCollectorName - The name of survey collector.
								(d) surveyMonkeyReplyEmail - The email address to which the reply could be sent.
								(e) surveyMonkeyMessageSubject - Subject message for the survey email.

	
	[ii] Case-002
		- Purpose:	(a) Retrieve Survey respondents from SurveyMonkey and send an email to the Organizer as a notification, using Gmail.
		
		- Files:	(a) Proxy - 	<EVENTBRITE_CONNECTOR_HOME>\eventbrite-integrated-scenarios\src\scenarios\Event Surveys\Case-001\proxy\eventbrite_retrievePastEventAttendeesAndSendSurveys.xml

		
		-Request Parameters:	(a) surveyMonkeyCollectiorId - Collector ID of the survey.
								(b) gmailSubject - Subject of the survey response email. 				
								(c) eventOrganizerEmail - Email address of the event organizer.

			
	