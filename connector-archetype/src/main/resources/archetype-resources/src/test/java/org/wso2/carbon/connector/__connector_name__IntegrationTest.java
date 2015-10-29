#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/*
*  Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package ${package};

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.carbon.esb.ESBIntegrationTest;

public class ${connector_name}IntegrationTest extends ESBIntegrationTest {

//    @BeforeClass(alwaysRun = true)
//    public void setEnvironment() throws Exception {
//        super.init();
//    }
//
//    @Override
//    protected void cleanup() {
//        axis2Client.destroy();
//    }

@Test(enabled = true, groups = {"wso2.esb"}, description = "Sample test case")
public void testSample() throws Exception {
        log.info("IntegrationTest Success ${connector_name}");
        }
        }
