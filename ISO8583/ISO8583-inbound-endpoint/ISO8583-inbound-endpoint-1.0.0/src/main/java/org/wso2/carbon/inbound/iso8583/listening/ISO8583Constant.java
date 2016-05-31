/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wso2.carbon.inbound.iso8583.listening;

public class ISO8583Constant {
    public final static String TAG_FIELD = "field";
    public final static String TAG_MSG = "ISOMessage";
    public final static String TAG_DATA = "data";
    public final static String TAG_ID = "id";
    public static final String INBOUND_SEQUENTIAL = "sequential";
    public static final String PORT = "port";
    public static final String PACKAGER = "jposdef.xml";
    public static final String CORE_THREADS  = "1";
    public static final String MAX_THREADS   = "3";
    public static final String KEEP_ALIVE    = "1";
    public static final String THREAD_QLEN   = "1";
    public static final String INBOUND_CORE_THREADS = "coreThreads";
    public static final String INBOUND_MAX_THREADS = "maxThreads";
    public static final String INBOUND_KEEP_ALIVE = "keepAlive";
    public static final String INBOUND_THREAD_QLEN = "queueLength";
}
