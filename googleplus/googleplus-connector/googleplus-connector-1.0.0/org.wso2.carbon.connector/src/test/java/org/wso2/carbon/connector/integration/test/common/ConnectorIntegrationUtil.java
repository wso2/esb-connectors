/**
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 **/
package org.wso2.carbon.connector.integration.test.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.carbon.automation.core.ProductConstant;
import org.wso2.carbon.mediation.library.stub.upload.MediationLibraryUploaderStub;
import org.wso2.carbon.mediation.library.stub.upload.types.carbon.LibraryFileItem;

import javax.activation.DataHandler;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class ConnectorIntegrationUtil {

    private static final Log log = LogFactory.getLog(ConnectorIntegrationUtil.class);

    /**
     * To upload connector to ESB
     *
     * @param repoLocation           The file location of ESB
     * @param mediationLibUploadStub The stub of mediation library uploader
     * @param strFileName            FileName
     * @throws MalformedURLException
     * @throws RemoteException
     */
    public static void uploadConnector(String repoLocation,
                                       MediationLibraryUploaderStub mediationLibUploadStub,
                                       String strFileName)
            throws MalformedURLException, RemoteException {

        List<LibraryFileItem> uploadLibraryInfoList = new ArrayList<LibraryFileItem>();
        LibraryFileItem uploadedFileItem = new LibraryFileItem();
        uploadedFileItem.setDataHandler(
                new DataHandler(new URL("file:" + "///" + repoLocation + "/" + strFileName)));
        uploadedFileItem.setFileName(strFileName);
        uploadedFileItem.setFileType("zip");
        uploadLibraryInfoList.add(uploadedFileItem);
        LibraryFileItem[] uploadServiceTypes = new LibraryFileItem[uploadLibraryInfoList.size()];
        uploadServiceTypes = uploadLibraryInfoList.toArray(uploadServiceTypes);
        mediationLibUploadStub.uploadLibrary(uploadServiceTypes);

    }

    /**
     * To retrieve the headers
     *
     * @param addUrl URL
     * @param query  Query
     * @return response code
     * @throws IOException
     */
    public static int sendRequestToRetrieveHeaders(String addUrl, String query) throws IOException {

        String charset = "UTF-8";
        URLConnection connection = new URL(addUrl).openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Accept-Charset", charset);
        connection.setRequestProperty("Content-Type", "application/json;charset=" + charset);

        OutputStream output = null;
        try {
            output = connection.getOutputStream();
            output.write(query.getBytes(charset));
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException logOrIgnore) {
                    log.error("Error while closing the connection");
                }
            }
        }

        HttpURLConnection httpConn = (HttpURLConnection) connection;

        return httpConn.getResponseCode();
    }

    /**
     * To get the JSON object
     *
     * @param addUrl URL
     * @param query  Query
     * @return The requested JSON object
     * @throws IOException
     * @throws JSONException
     */
    public static JSONObject sendRequest(String addUrl, String query)
            throws IOException, JSONException {

        String charset = "UTF-8";
        URLConnection connection = new URL(addUrl).openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Accept-Charset", charset);
        connection.setRequestProperty("Content-Type", "application/json;charset=" + charset);
        OutputStream output = null;
        try {
            output = connection.getOutputStream();
            output.write(query.getBytes(charset));
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException logOrIgnore) {
                    log.error("Error while closing the connection");
                }
            }
        }

        HttpURLConnection httpConn = (HttpURLConnection) connection;
        InputStream response;

        if (httpConn.getResponseCode() >= 400) {
            response = httpConn.getErrorStream();
        } else {
            response = connection.getInputStream();
        }

        String out = "{}";
        if (response != null) {
            StringBuilder sb = new StringBuilder();
            byte[] bytes = new byte[1024];
            int len;
            while ((len = response.read(bytes)) != -1) {
                sb.append(new String(bytes, 0, len));
            }

            if (!sb.toString().trim().isEmpty()) {
                out = sb.toString();
            }
        }

        return new JSONObject(out);
    }

    /**
     * To get the connector configuration
     *
     * @param connectorName Name of the connector
     * @return property file
     */
    public static Properties getConnectorConfigProperties(String connectorName) {
        ProductConstant.init();
        try {
            String connectorConfigFile =
                    ProductConstant.SYSTEM_TEST_SETTINGS_LOCATION + File.separator + "artifacts" +
                            File.separator
                            + "ESB" + File.separator + "connector" + File.separator + "config" +
                            File.separator
                            + connectorName + ".properties";
            File connectorPropertyFile = new File(connectorConfigFile);
            InputStream inputStream = null;
            if (connectorPropertyFile.exists()) {
                inputStream = new FileInputStream(connectorPropertyFile);
            }

            if (inputStream != null) {
                Properties prop = new Properties();
                prop.load(inputStream);
                inputStream.close();
                return prop;
            }

        } catch (IOException ignored) {
            log.error("automation.properties file not found, please check your configuration");
        }

        return null;
    }

    /**
     * Method to read in contents of a file as String
     *
     * @param path Path to the file
     * @return String contents of file
     * @throws java.io.IOException
     */
    public static String getFileContent(String path) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(path));
            String line;

            String ls = System.getProperty("line.separator");

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

        } catch (IOException ioe) {
            log.error("Error reading request from file.", ioe);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Method to Create Required json Payload as a String according to the required optional parameters
     *
     * @param requestJsonString          json string to be modified
     * @param unneededOptionalParameters parameters to remove
     * @return modified json string
     * @throws JSONException
     */
    public static String getRequiredJsonString(String requestJsonString,
                                               String[] unneededOptionalParameters) throws JSONException {
        JSONObject requestJson = new JSONObject(requestJsonString);
        for (String keys : unneededOptionalParameters) {
            requestJson.remove(keys);
        }
        return requestJson.toString();
    }

}
