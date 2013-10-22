package com.callrite.cbs.telephony;

import org.apache.commons.lang.StringUtils;

/**
 * This class contains the request from telephony devices 
 * @author JLiang
 *
 */
public class TelephonyRequest {
    /**
     * The type 
     */
    private int type = HTTP;
            
    public final static int HTTP = 1;
    public final static int WS_SOAP = 2;
    public final static int WS_REST = 3;
    public final static int SOCKET =  4;
    
    /**
     * service protocol
     */
    private String protocol;
    
    /** 
     * Source and Address
     */
    private String source;
    private String ipAddress;
    
    /**
     * Default is incoming
     */
    private String action = INCOMING;
    public final static String INCOMING = "Incoming";
    public final static String OUTCOMING = "Outgoing";
    
    /**
     * Call flow, default is router
     */
    private String callFlow = "router";
    /**
     * Call flow version, default is 1
     */
    private String version = "1";
    
    /** Original request data **/
    private Object originalRequest;

    /**
     * Constructor
     * @param type
     * @param protocol
     * @param source
     * @param ipAddress
     * @param originalRequest
     */
    public TelephonyRequest(int type, String protocol, String source, String ipAddress, 
            String action, String callFlow, String version, Object originalRequest) {
        super();
        this.type = type;
        this.protocol = protocol;
        this.source = source;
        this.ipAddress = ipAddress;
        if (! StringUtils.isEmpty(action) ) {
            this.action = action;
        }
        if (! StringUtils.isEmpty(callFlow) ) {
            this.callFlow = callFlow;
        }
        if (! StringUtils.isEmpty(version) ) {
            this.version = version;
        }
        this.originalRequest = originalRequest;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the protocol
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * @param protocol the protocol to set
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return the ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * @param ipAddress the ipAddress to set
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * @return the originalRequest
     */
    public Object getOriginalRequest() {
        return originalRequest;
    }

    /**
     * @param originalRequest the originalRequest to set
     */
    public void setOriginalRequest(Object originalRequest) {
        this.originalRequest = originalRequest;
    }

    /**
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return the callFlow
     */
    public String getCallFlow() {
        return callFlow;
    }

    /**
     * @param callFlow the callFlow to set
     */
    public void setCallFlow(String callFlow) {
        this.callFlow = callFlow;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }
    
    /**
     * Generate next action URL
     * @param nextAction
     * @return
     */
    public String generateNextActionPath(String nextAction) {
        return String.format("/%s/%s/%s/%s/%s", source, protocol, nextAction, callFlow, version);
    }
}
