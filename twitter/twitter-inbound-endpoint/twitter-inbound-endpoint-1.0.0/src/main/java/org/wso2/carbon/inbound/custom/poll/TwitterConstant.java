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

package org.wso2.carbon.inbound.custom.poll;

public class TwitterConstant {

    //property key in the InBoundEP properties file for the twitter api consumer key
    public static final String CONSUMER_KEY = "connection.twitter.consumerKey";

    //property key in the InBoundEP properties file for the twitter api consumer secret
    public static final String CONSUMER_SECRET = "connection.twitter.consumerSecret";

    //property key in the InBoundEP properties file for the twitter api access token
    public static final String ACCESS_TOKEN = "connection.twitter.accessToken";

    //property key in the InBoundEP properties file for the twitter api access secret
    public static final String ACCESS_SECRET = "connection.twitter.accessSecret";

    //property key in the InBoundEP properties file for the twitter api access secret
    public static final String TWITTER_OPERATION = "twitter.operation";

    //property key in the InBoundEP properties file for the twitter count
    public static final String TWITTER_COUNT = "twitter.count";

    //property key in the InBoundEP properties file for the twitter follow
    public static final String TWITTER_FOLLOW = "twitter.follow";

    //property key in the InBoundEP properties file for the twitter track
    public static final String TWITTER_TRACK = "twitter.track";

    //property key in the InBoundEP properties file for the twitter locations
    public static final String TWITTER_LOCATIONS = "twitter.locations";

    //property key in the InBoundEP properties file for the twitter language
    public static final String TWITTER_LANGUAGE = "twitter.language";

    //property key in the InBoundEP properties file for the twitter filter level
    public static final String TWITTER_FILTER_LEVEL = "twitter.filterLevel";

    //property key in the InBoundEP properties file for the twitter withFollowings
    public static final String TWITTER_WITH_FOLLOWINGS = "twitter.withFollowings";

    //content type of the message
    public static final String CONTENT_TYPE = "application/json";

    //property key in the InBoundEP properties file for the twitter firehose operation
    public static final String FILTER_STREAM_OPERATION = "filter";

    //property key in the InBoundEP properties file for the twitter firehose operation
    public static final String FIREHOSE_STREAM_OPERATION = "firehose";

    //property key in the InBoundEP properties file for the twitter link operation
    public static final String LINK_STREAM_OPERATION = "link";

    //property key in the InBoundEP properties file for the twitter sample operation
    public static final String SAMPLE_STREAM_OPERATION = "sample";

    //property key in the InBoundEP properties file for the twitter site operation
    public static final String SITE_STREAM_OPERATION = "site";

    //property key in the InBoundEP properties file for the twitter user operation
    public static final String USER_STREAM_OPERATION = "user";

    //property key in the InBoundEP properties file for the twitter retweet operation
    public static final String RETWEET_STREAM_OPERATION = "retweet";

}
