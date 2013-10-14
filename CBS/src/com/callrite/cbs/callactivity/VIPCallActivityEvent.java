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
