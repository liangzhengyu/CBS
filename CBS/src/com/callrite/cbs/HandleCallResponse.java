/*
 * $Id$
 * 
 * Created on Jul 31, 2012
 *
 * Copyright © VoiceRite, Inc. 2011.  All Rights reserved.
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has been
 * deposited with the U.S. Copyright office.
 *
 */
package com.callrite.cbs;

/**
 * Response when handling call request
 * @author JLiang
 *
 */
public class HandleCallResponse {
    private ReturnCode returnCode;
    private CallResponse callResponse;
    
    /**
     * Constructor
     */
    public HandleCallResponse() {
        super();
    }

    /**
     * @return the returnCode
     */
    public ReturnCode getReturnCode() {
        return returnCode;
    }

    /**
     * @param returnCode the returnCode to set
     */
    public void setReturnCode(ReturnCode returnCode) {
        this.returnCode = returnCode;
    }

    /**
     * @return the callResponse
     */
    public CallResponse getCallResponse() {
        return callResponse;
    }

    /**
     * @param callResponse the callResponse to set
     */
    public void setCallResponse(CallResponse callResponse) {
        this.callResponse = callResponse;
    }
    
    
}
