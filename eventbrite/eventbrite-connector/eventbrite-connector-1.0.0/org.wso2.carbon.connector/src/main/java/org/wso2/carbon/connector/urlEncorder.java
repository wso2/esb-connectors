package org.wso2.carbon.connector;

import org.apache.synapse.Mediator;
import org.apache.synapse.MessageContext;

/**
 * Created by zamly-PC on 9/4/14.
 */
public class urlEncorder implements Mediator {
    public boolean mediate(MessageContext messageContext) {
        System.out.println(messageContext.getProperty("id"));
        System.out.println(messageContext.getProperty("label"));

        return false;
    }

    public String getType() {
        return null;
    }

    public int getTraceState() {
        return 0;
    }

    public void setTraceState(int i) {

    }

    public boolean isContentAware() {
        return false;
    }

    public void setDescription(String s) {

    }

    public String getDescription() {
        return null;
    }
}
