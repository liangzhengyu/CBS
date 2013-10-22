package com.callrite.cbs.telephony;

import com.callrite.cbs.exception.CBSException;

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
     * @throws CBSException
     */
    public TelephonyResponse process(TelephonyRequest request) throws CBSException;
}
