/*
 * $Id$
 * 
 * Created on Aug 9, 2012
 *
 * Copyright © VoiceRite, Inc. 2011.  All Rights reserved.
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has been
 * deposited with the U.S. Copyright office.
 *
 */
package com.callrite.cbs.event;

/**
 * Invoke the listener remotely through RESTFUL web services
 * Data pass in/back in JSON format
 * @author JLiang
 *
 */
public class VIPEventRemoteListener implements VIPEventListener {
    // web serivce URL
    private String[] webserviceURLs;
    
    public VIPEventRemoteListener(String[] webserviceURLs) {
        super();
        this.webserviceURLs = webserviceURLs;
    }

    /* (non-Javadoc)
     * @see com.callrite.cbs.event.VIPEventListener#handleEvent(com.callrite.cbs.event.VIPEvent)
     */
    public void handleEvent(VIPEvent event) {
        // TODO Auto-generated method stub
        
    }
    
    
}
