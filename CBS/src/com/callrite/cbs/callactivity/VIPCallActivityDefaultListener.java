package com.callrite.cbs.callactivity;

import org.apache.log4j.Logger;

import com.callrite.cbs.CallRequest;
import com.callrite.cbs.CallResponse;
import com.callrite.cbs.event.VIPEvent;
import com.callrite.cbs.event.VIPEventListener;

/**
 * This is the default listener to call activity
 * @author JLiang
 *
 */
public class VIPCallActivityDefaultListener implements VIPEventListener {
    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger(VIPCallActivityDefaultListener.class);

    /* (non-Javadoc)
     * @see com.callrite.cbs.event.VIPEventListener#handleEvent(com.callrite.cbs.event.VIPEvent)
     */
    public void handleEvent(VIPEvent event) {
        // TODO Auto-generated method stub
        logger.debug("Process call event [" + event + "]" );
        CallRequest callRequest = (CallRequest) event.getVIPEventData();
        if ( callRequest.isWaitingForResponse() ) {
            //send back call response
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            CallResponse callResponse = new CallResponse();
            event.setVIPEventResponseData(callResponse);
        }
    }

}
