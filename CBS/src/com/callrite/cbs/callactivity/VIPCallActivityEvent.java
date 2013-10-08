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
package com.callrite.cbs.callactivity;

import com.callrite.cbs.CallRequest;
import com.callrite.cbs.event.VIPEventBase;
import com.callrite.cbs.util.Config;

/**
 * VIP call activity event
 * @author JLiang
 *
 */
public class VIPCallActivityEvent extends VIPEventBase {
    /** wait how long for the response **/
    private int waitMillSeconds = 0;
    
    /**
     * Construct call activity event
     * @param callRequest
     */
    public VIPCallActivityEvent(CallRequest callRequest) {
        setEventSource(callRequest.getSource());
        setEventType(CALL_ACTIVITY);
        setVIPEventData(callRequest);
        if ( callRequest.getStatus() == CallRequest.STATUS_STARTED)  {
            waitMillSeconds = Config.getInstance().getSetting("TimeoutForCallStartedResponse", 500);
        }
    }

    /* (non-Javadoc)
     * @see com.callrite.cbs.event.VIPEvent#getVIPEventJSONData()
     */
    public String getVIPEventJSONData() {
        
        return "";
    }

    /* (non-Javadoc)
     * @see com.callrite.cbs.event.VIPEvent#getVIPResponseEventJSONData()
     */
    public String getVIPResponseEventJSONData() {
        // TODO Auto-generated method stub
        return "";
    }

    /* (non-Javadoc)
     * @see com.callrite.cbs.event.VIPEvent#waitingForEventResponse()
     */
    public int waitingForEventResponse() {
        return waitMillSeconds;
    }
}
