package org.wso2.handler.synapse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.AbstractSynapseHandler;
import org.apache.synapse.SynapseConstants;
import org.apache.synapse.config.SynapsePropertiesLoader;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.MessageContext;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Properties;

public class RequestEncodingHandler extends AbstractSynapseHandler {
    private static final Log log = LogFactory.getLog(RequestEncodingHandler.class);

    private static Properties synapseProperties = SynapsePropertiesLoader.loadSynapseProperties();

    private static String escape_encode_chars_property = SynapsePropertiesLoader.loadSynapseProperties().
            getProperty("escape_encode_chars");

    private static char[] escapedChars = escape_encode_chars_property.toCharArray();

    protected boolean isEscaped(char ch) {
        for (char escapedChar : escapedChars) {
            if (ch == escapedChar) {
                return true;
            }
        }
        return false;
    }

    public boolean handleRequestInFlow(MessageContext messageContext) {

        if (log.isDebugEnabled()) {
            log.debug(this.getClass().getName() + " handleRequestInFlow ");
        }

        org.apache.axis2.context.MessageContext axis2MessageContext
                = ((Axis2MessageContext) messageContext).getAxis2MessageContext();
        String rest_url_postfix = (String) axis2MessageContext.getProperty("REST_URL_POSTFIX");

        int length = rest_url_postfix.length();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {

            char ch = rest_url_postfix.charAt(i);
            if (isEscaped(ch)) {
                try {
                    buffer.append(URLEncoder.encode(String.valueOf(ch), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                buffer.append(ch);
            }

        }

        String encoded_rest_url_postfix = buffer.toString();

        axis2MessageContext.setProperty("TransportInURL", "/" + encoded_rest_url_postfix);

        return true;
    }

    public boolean handleRequestOutFlow(MessageContext messageContext) {
        return true;
    }

    public boolean handleResponseInFlow(MessageContext messageContext) {
        return true;
    }

    public boolean handleResponseOutFlow(MessageContext messageContext) {
        return true;
    }
}
