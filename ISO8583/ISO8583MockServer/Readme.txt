Product: ISO8583MockServer for receiving the ISO8583 Messages from ISO8583 Connector and send the response back.

Pre-requisites:
 - Java 1.6 or above

STEPS:

1. Log4J is opensource logging library. We can download it’s library from http://logging.apache.org/log4j/1.2/download.html.

2. Add the Log4J jar as an External JAR into the java project to enable log.

3. Add the jpos-1.9.0 jar as an External JAR into the java project, Which can be downloaded from
    http://mvnrepository.com/artifact/org.jpos/jpos/1.9.0

4. Add the commons-cli-1.3.1 jar as an External JAR into the java project, Which can be downloaded from
    http://mvnrepository.com/artifact/commons-cli/commons-cli/1.3

5. Add the jdom-1.1.3 jar as an External JAR into the java project, Which can be downloaded from
    http://mvnrepository.com/artifact/org.jdom/jdom/1.1.3.

6. Create log4j.properties as logging configuration in default package.
	# Category Configuration
    log4j.rootLogger=INFO,Konsole,Roll
    # Console Appender Configuration
    log4j.appender.Konsole=org.apache.log4j.ConsoleAppender
    log4j.appender.Konsole.layout=org.apache.log4j.PatternLayout
    # Date Format based on ISO�8601 : %d
    log4j.appender.Konsole.layout.ConversionPattern=%d [%t] %5p %c � %m%n
    # Roll Appender Configuration
    log4j.appender.Roll=org.apache.log4j.RollingFileAppender
    log4j.appender.Roll.File=/home/Documents/socketConnections/ISO8583MockServer/log/myApp.log
    log4j.appender.Roll.MaxFileSize=10KB
    log4j.appender.Roll.MaxBackupIndex=2
    log4j.appender.Roll.layout=org.apache.log4j.PatternLayout
    # Date Format based on ISO�8601 : %d
    log4j.appender.Roll.layout.ConversionPattern=%d [%t] %p (%F:%L) � %m%n

