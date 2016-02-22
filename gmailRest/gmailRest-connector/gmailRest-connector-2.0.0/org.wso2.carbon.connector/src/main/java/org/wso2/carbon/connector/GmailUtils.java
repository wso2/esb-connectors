/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *   WSO2 Inc. licenses this file to you under the Apache License,
 *   Version 2.0 (the "License"); you may not use this file except
 *   in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.wso2.carbon.connector;

import com.google.code.com.sun.mail.imap.IMAPFolder;
import com.google.code.com.sun.mail.imap.IMAPMessage;
import com.google.code.com.sun.mail.imap.IMAPStore;
import com.google.code.com.sun.mail.smtp.SMTPTransport;
import com.google.code.javax.activation.DataHandler;
import com.google.code.javax.mail.*;
import com.google.code.javax.mail.Flags.Flag;
import com.google.code.javax.mail.Part;
import com.google.code.javax.mail.internet.InternetAddress;
import com.google.code.javax.mail.internet.MimeBodyPart;
import com.google.code.javax.mail.internet.MimeMessage;
import com.google.code.javax.mail.internet.MimeMultipart;
import com.google.code.javax.mail.search.SearchTerm;
import com.google.code.javax.mail.util.ByteArrayDataSource;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.context.OperationContext;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.SynapseConstants;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.wso2.carbon.connector.core.ConnectException;
import org.wso2.carbon.connector.core.util.ConnectorUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public final class GmailUtils {

    /**
     * Making the default constructor private since Utility classes should not
     * have a public constructors
     */
    private GmailUtils() {
    }

    /**
     * Log instance.
     */
    private static Log log = LogFactory.getLog(GmailUtils.class);
    public static int batchNumber;

    /**
     * Extracts a given parameter from message context
     *
     * @param messageContext Input message context
     * @param paramName      Name of the parameter to extract from the message context
     * @return extracted parameter as a {@link String}
     */
    public static String lookupFunctionParam(MessageContext messageContext, String paramName) {
        return (String) ConnectorUtils.lookupTemplateParamater(messageContext, paramName);
    }

    /**
     * Stores error response in the message context
     *
     * @param messageContext Message Context where the error response should be stored
     * @param e              Exception
     */
    public static void storeErrorResponseStatus(MessageContext messageContext, final Throwable e,
                                                int errorCode) {
        messageContext.setProperty(SynapseConstants.ERROR_EXCEPTION, e);
        messageContext.setProperty(SynapseConstants.ERROR_MESSAGE, e.getMessage());

        if (messageContext.getEnvelope().getBody().getFirstElement() != null) {
            messageContext.getEnvelope().getBody().getFirstElement().detach();
        }
        OMFactory factory = OMAbstractFactory.getOMFactory();
        OMNamespace ns = factory.createOMNamespace("http://org.wso2.esbconnectors.gmail", "ns");
        OMElement result = factory.createOMElement("ErrorResponse", ns);
        OMElement errorMessageElement = factory.createOMElement("ErrorMessage", ns);
        result.addChild(errorMessageElement);
        errorMessageElement.setText(e.getMessage());
        messageContext.getEnvelope().getBody().addChild(result);

        messageContext.setProperty(SynapseConstants.ERROR_CODE, errorCode);
        messageContext.setFaultResponse(true);
        log.info("Stored the error response");
    }

    /**
     * Process message body.
     *
     * @param message              Message to be processed.
     * @param messageContext       Message Context
     * @param attachmentContentIDs file names as the content IDs of the attachments
     * @param messageID            ID of the message.
     * @return
     * @throws java.io.IOException
     * @throws com.google.code.javax.mail.MessagingException.
     */
    private static String processMessageBody(Message message, MessageContext messageContext,
                                             StringBuilder attachmentContentIDs, String messageID)
            throws IOException,
            MessagingException {
        Object content = message.getContent();
        if (content instanceof Multipart) {
            Multipart multiPart = (Multipart) content;
            StringBuilder builder = new StringBuilder();
            return procesMultiPart(builder, multiPart, messageContext, attachmentContentIDs,
                    messageID);
        } else if (content instanceof String) {
            return content.toString();
        } else if (content instanceof InputStream) {
            InputStream inStream = (InputStream) content;
            BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            br.close();
            inStream.close();
            return builder.toString();
        }
        log.error("invalid message content");
        return null;
    }

    /**
     * Close and remove the already stored IMAP and SMTP connections
     *
     * @param operationContext where the connections are stored
     * @throws com.google.code.javax.mail.MessagingException.
     */
    public static void closeConnection(org.apache.axis2.context.MessageContext axis2MessageContext)
            throws MessagingException {
        if (axis2MessageContext.getProperty(GmailConstants.GMAIL_LOGIN_MODE) == null) {
            return;
        }
        OperationContext operationContext = axis2MessageContext.getOperationContext();
        if (operationContext.getProperty(GmailConstants.GMAIL_IMAP_STORE_INSTANCE) != null) {
            log.info("Closing the previously opened IMAP Store");
            ((IMAPStore) operationContext.getProperty(GmailConstants.GMAIL_IMAP_STORE_INSTANCE)).close();
            operationContext.removeProperty(GmailConstants.GMAIL_IMAP_STORE_INSTANCE);
        }
        if (operationContext.getProperty(GmailConstants.GMAIL_SMTP_CONNECTION_INSTANCE) != null) {
            log.info("Closing the previously opened SMTP transport");
            ((GmailSMTPConnectionObject) operationContext.getProperty(GmailConstants.GMAIL_SMTP_CONNECTION_INSTANCE))
                    .getTransport().close();
            operationContext.removeProperty(GmailConstants.GMAIL_SMTP_CONNECTION_INSTANCE);
        }
        axis2MessageContext.removeProperty((String) axis2MessageContext.getProperty(GmailConstants.GMAIL_LOGIN_MODE));
    }

    /**
     * Store the response for send mail operations.
     *
     * @param responseElementName Response element's name
     * @param subject             Subject of the mail
     * @param textContent         Text content of the mail
     * @param recipients          A comma separated list of recipients
     * @param attachmentIDs       A comma separated list of attachmentIDs
     * @param messageContext      Message context where the response should be stored.
     */
    public static void storeSentMailResponse(String responseElementName, String subject,
                                             String textContent, String recipients,
                                             String attachmentIDs, MessageContext messageContext) {
        log.info("Storing the response in the message context");
        if (messageContext.getEnvelope().getBody().getFirstElement() != null) {
            messageContext.getEnvelope().getBody().getFirstElement().detach();
        }
        OMFactory factory = OMAbstractFactory.getOMFactory();
        OMNamespace ns = factory.createOMNamespace("http://org.wso2.esbconnectors.gmail", "ns");
        OMElement result = factory.createOMElement(responseElementName, ns);

        OMElement messageElement = factory.createOMElement("message", ns);
        result.addChild(messageElement);
        OMElement subjectElement = factory.createOMElement("subject", ns);
        subjectElement.setText(subject);
        messageElement.addChild(subjectElement);
        OMElement contentElement = factory.createOMElement("content", ns);
        contentElement.setText(textContent);
        messageElement.addChild(contentElement);
        OMElement attachmentElement = factory.createOMElement("attachments", ns);
        attachmentElement.setText(attachmentIDs);
        messageElement.addChild(attachmentElement);
        OMElement recipientsElement = factory.createOMElement("recipients", ns);
        recipientsElement.setText(recipients);
        messageElement.addChild(recipientsElement);
        messageContext.getEnvelope().getBody().addChild(result);
    }

    /**
     * Creates a new {@link com.google.code.javax.mail.Message}.
     *
     * @param session        Mail {@link com.google.code.javax.mail.Session}.
     * @param subject        Subject of the mail.
     * @param textContent    Text content of the mail message.
     * @param toRecipients   'To' recipients of the mail message.
     * @param ccRecipients   'CC' recipients of the mail message.
     * @param bccRecipients  'BCC' recipients of the mail message.
     * @param attachmentList Array of attachment file names.
     * @param axis2MsgCtx    Axis2 message context where the attachment files are stored.
     * @return returns the created {@link #}
     * @throws org.wso2.carbon.connector.core.ConnectException if invalid attachment IDs are provided.
     * @throws com.google.code.javax.mail.MessagingException
     * @throws java.io.IOException.
     */
    public static Message createNewMessage(Session session, String subject, String textContent,
                                           String toRecipients, String ccRecipients,
                                           String bccRecipients, String[] attachmentList,
                                           org.apache.axis2.context.MessageContext axis2MsgCtx)
            throws ConnectException,
            MessagingException,
            IOException {
        log.info("Creating the mail message");
        MimeMessage message = new MimeMessage(session);
        if (StringUtils.isNotEmpty(toRecipients)) {
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toRecipients));
        }
        if (StringUtils.isNotEmpty(ccRecipients)) {
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccRecipients));
        }
        if (StringUtils.isNotEmpty(bccRecipients)) {
            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bccRecipients));
        }
        message.setSubject(subject);
        MimeMultipart content = new MimeMultipart();
        MimeBodyPart mainPart = new MimeBodyPart();
        mainPart.setText(textContent);
        content.addBodyPart(mainPart);

        for (String attachment : attachmentList) {
            javax.activation.DataHandler handler = axis2MsgCtx.getAttachment(attachment);
            if (handler != null) {
                InputStream inStream = handler.getInputStream();
                byte[] bytes = IOUtils.toByteArray(inStream);
                ByteArrayDataSource source =
                        new ByteArrayDataSource(bytes,
                                handler.getContentType());
                MimeBodyPart bodyPart = new MimeBodyPart();
                bodyPart.setDataHandler(new DataHandler(source));
                bodyPart.setFileName(attachment);
                content.addBodyPart(bodyPart);
            } else {
                String errorLog = "Invalid attachemnt ID, " + attachment;
                log.error(errorLog);
                ConnectException connectException = new ConnectException(errorLog);
                throw (connectException);
            }
        }
        message.setContent(content);
        return message;
    }

    /**
     * Sends the given {@link com.google.code.javax.mail.Message} through the given {@link com.google.code.com.sun.mail.smtp.SMTPTransport}
     *
     * @param message   The message to be sent
     * @param transport The {@link com.google.code.com.sun.mail.smtp.SMTPTransport} through which the message should be
     *                  sent
     * @throws com.google.code.javax.mail.MessagingException as a result of failures in message transportation.
     */
    public static void sendMessage(Message message, SMTPTransport transport)
            throws MessagingException {
        log.info("Sending the mail...");
        transport.sendMessage(message, message.getAllRecipients());
        log.info("The mail is succesfully sent");
    }

    private static Message[] getBatch(Message[] messages, int batchNumber) {
        Message[] newMessages;
        if (messages.length > GmailConstants.GMAIL_BATCH_SIZE * batchNumber) {
            newMessages = Arrays.copyOfRange(messages, messages.length - GmailConstants.GMAIL_BATCH_SIZE * batchNumber,
                            messages.length - GmailConstants.GMAIL_BATCH_SIZE * (batchNumber - 1));
        } else if (messages.length > GmailConstants.GMAIL_BATCH_SIZE * (batchNumber - 1)) {
            newMessages = Arrays.copyOfRange(messages, 0, messages.length - GmailConstants.GMAIL_BATCH_SIZE *
                    (batchNumber - 1));
        } else {
            newMessages = new Message[0];
        }
        return newMessages;
    }

    /**
     * Process {@link com.google.code.javax.mail.Multipart} content.
     *
     * @param contentBuilder       String builder to store the content
     * @param multipart            input {@link com.google.code.javax.mail.Multipart} to process
     * @param messageContext       Message context from where the attachments should be taken
     * @param attachmentContentIDs String builder to store content IDs of the attachments
     * @param messageID            ID of the message
     * @return the {@link com.google.code.javax.mail.Multipart} content as a {@link String}
     * @throws com.google.code.javax.mail.MessagingException
     * @throws java.io.IOException.
     */
    private static String procesMultiPart(StringBuilder contentBuilder, Multipart multipart,
                                          MessageContext messageContext,
                                          StringBuilder attachmentContentIDs, String messageID)
            throws MessagingException,
            IOException {
        int multiPartCount = multipart.getCount();
        for (int i:multiPartCount) {
            BodyPart bodyPart = multipart.getBodyPart(i);

            if (bodyPart.isMimeType("text/plain")) {
                contentBuilder.append("Text:\n" + bodyPart.getContent() + "\n");
            } else if (bodyPart.getContent() instanceof Multipart) {
                procesMultiPart(contentBuilder, (Multipart) bodyPart.getContent(), messageContext,
                        attachmentContentIDs, messageID);
            } else if (null != bodyPart.getDisposition() &&
                    bodyPart.getDisposition().equalsIgnoreCase(Part.ATTACHMENT)) {
                String fileName = bodyPart.getFileName();
                String attachmentID = messageID + fileName;
                contentBuilder.append("Attachment:" + fileName + "\n");
                attachmentContentIDs.append(attachmentID);
                attachmentContentIDs.append(',');
                addAttachmentToMessageContext(attachmentID, bodyPart.getInputStream(), bodyPart.getContentType(), messageContext);
            } else if (null != bodyPart.getDisposition() && bodyPart.getDisposition().equalsIgnoreCase(Part.INLINE)) {
                String fileName = bodyPart.getFileName();
                contentBuilder.append("INLINE:" + fileName + "\n");
            }

        }
        return contentBuilder.toString();
    }

    /**
     * Add attachments to the message context.
     *
     * @param attachmentContentID Content ID (file name) of the attachment
     * @param inputStream         Input stream to attach
     * @param type                Content type of the attachment
     * @param messageContext      Message context to where the attachments should be added
     * @throws java.io.IOException as a result of the failures occur while getting the byte
     *                             array from the input stream.
     */
    private static void addAttachmentToMessageContext(String attachmentContentID,
                                                      InputStream inputStream, String type,
                                                      MessageContext messageContext)
            throws IOException {
        org.apache.axis2.context.MessageContext axis2mc = ((Axis2MessageContext) messageContext).getAxis2MessageContext();
        byte[] bytes = IOUtils.toByteArray(inputStream);
        javax.mail.util.ByteArrayDataSource source = new javax.mail.util.ByteArrayDataSource(bytes, type);
        javax.activation.DataHandler handler = new javax.activation.DataHandler((javax.activation.DataSource) source);
        axis2mc.addAttachment(attachmentContentID, handler);
        log.info("Added an attachemnt named \"" + attachmentContentID + "\" to message context");
    }
}
