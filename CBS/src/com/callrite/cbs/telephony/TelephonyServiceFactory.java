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

import org.apache.commons.lang.StringUtils;

import com.callrite.cbs.exception.NotFoundException;
import com.callrite.cbs.exception.VIPException;
import com.callrite.cbs.telephony.plivo.PlivoService;

/**
 * Telephony service factory
 * @author JLiang
 *
 */
public class TelephonyServiceFactory {
    private static TelephonyService plivoService = new PlivoService();
    
    public static TelephonyService createTelephonyService(String protocol) throws VIPException {
        if (StringUtils.isEmpty(protocol) ) {
            throw new NotFoundException("protocol is missing");
        }
        
        if ( protocol.equalsIgnoreCase("plivo") ) {
            return plivoService;
        }
        
        throw new NotFoundException("Service [" + protocol + "] not implemented");
    }
}
