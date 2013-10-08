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

import com.callrite.cbs.CallRequest;
import com.callrite.cbs.CallResponse;
import com.callrite.cbs.exception.VIPException;

/**
 * This interface defines the methods for Telephony service
 * @author JLiang
 *
 */
public interface TelephonyService  {
    /**
     * Process telephony request
     * @param request request
     * @param response response
     * @throws VIPException
     */
    public CallRequest processRequest(TelephonyRequest request, TelephonyResponse response ) throws VIPException;
    
    /**
     * Process the reponse back to telephony devices
     * @param request
     * @param response
     * @param callRequest
     * @param callResponse
     * @throws VIPException
     */
    public void processResponse(TelephonyRequest request, TelephonyResponse response, CallRequest callRequest, CallResponse callResponse) throws VIPException;
}
