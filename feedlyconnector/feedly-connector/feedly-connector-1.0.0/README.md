Product: Integration tests for WSO2 ESB feedlyESBconnector


     Pre-requisites:

         - Maven 3.x
         - Java 1.6 or above

    Integration testing of verified platforms
        - Ubuntu 14.04
        - WSO2 ESB 4.8.1
        - Java 1.7

    STEPS:
    1.Download ESB 4.8.1 from the official website.
    2.Compress the ESB as wso2esb-4.8.1.zip and copy this zip file into this directory "feedly-connector/feedly-connector-1.0.0/org.wso2.carbon.connector/repository/” .
    3.Create an feedly  account and get the access token.
      a.Go tohttp://feedly.com/i/welcome, and log in with existing account.

    Update following fields in Feedly.properties
       - clientId
       - clientSecret
       - accessToken
       - accessTokenNegative
       - userId
       - redirectUri
       - topicId
       - interest
       - updatedInterest
       - entryIdOne
       - entryIdTwo
       - entryIdThree
       - entryIdFour
       - feedId
       - feedIdTwo
       - feedIdThree
       - categoryId
       - categoryIdTwo
       - categoryIdThree
       - categoryIdFour
       - locale
       - q
       - tagId
       - tagIdTwo


    5.Navigate to "feedly-connector/feedly-connector-1.0.0/org.wso2.carbon.connector” and run the following command.
       $ mvn clean install






