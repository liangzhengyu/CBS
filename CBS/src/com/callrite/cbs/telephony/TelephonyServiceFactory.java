package com.callrite.cbs.telephony;

import org.apache.commons.lang.StringUtils;

import com.callrite.cbs.exception.NotFoundException;
import com.callrite.cbs.exception.CBSException;
import com.callrite.cbs.telephony.plivo.PlivoService;

/**
 * Telephony service factory
 * @author JLiang
 *
 */
public class TelephonyServiceFactory {
    private static TelephonyService plivoService = new PlivoService();
    
    public static TelephonyService createTelephonyService(String protocol) throws CBSException {
        if (StringUtils.isEmpty(protocol) ) {
            throw new NotFoundException("protocol is missing");
        }
        
        if ( protocol.equalsIgnoreCase("plivo") ) {
            return plivoService;
        }
        
        throw new NotFoundException("Service [" + protocol + "] not implemented");
    }
}
