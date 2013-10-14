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
