/*
 * $Id$
 * 
 * Created on Jul 23, 2012
 *
 * Copyright © VoiceRite, Inc. 2011.  All Rights reserved.
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has been
 * deposited with the U.S. Copyright office.
 *
 */
package com.callrite.cbs.telephony;

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
    public TelephonyRequest(int type, String protocol, String source,
            String ipAddress, Object originalRequest) {
        super();
        this.type = type;
        this.protocol = protocol;
        this.source = source;
        this.ipAddress = ipAddress;
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
}
