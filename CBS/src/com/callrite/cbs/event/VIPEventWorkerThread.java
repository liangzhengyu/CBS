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

import org.apache.log4j.Logger;

import com.callrite.cbs.util.VRThread;

/**
 * VIP Event worker Thread
 * @author JLiang
 *
 */
public class VIPEventWorkerThread extends VRThread {
    /** The event type that the worker thread is working on **/
    private int eventType;
    /** thread identifier **/
    private int threadID;
    private VIPEventListener[] vipEventListeners;
    
    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger(VIPEventWorkerThread.class);
    
    /**
     * Consturctor
     */
    public VIPEventWorkerThread(int eventType, int threadID, VIPEventListener[] vipEventListeners) {
        super();
        this.eventType = eventType;
        this.threadID = threadID;
        this.vipEventListeners = vipEventListeners;
    }

    /* (non-Javadoc)
     * @see java.lang.Thread#run()
     */
    @Override
    public void run() {
        logger.info("Event worker [" + threadID + "] for event type [" + eventType + "] started" );
        
        while ( !terminated() ) {
            VIPEvent event = VIPEventManager.getInstance().consumeVIPEvent(eventType);
            logger.debug("Event worker [" + threadID + "] for event type [" + eventType + "] handling event [" + event + "]");
            for ( VIPEventListener listener : getVIPEventListner() ) {
                listener.handleEvent(event);
                if ( event.getVIPEventResponseData() != null ) {
                    //notify event is handled
                    VIPEventManager.getInstance().notifyVIPEventHandled(event);
                }
            }
            logger.debug("Event worker [" + threadID + "] for event type [" + eventType + "] event [" + event + "] handled");
        }
        
        logger.info("Event worker [" + threadID + "] for event type [" + eventType + "] stopped" );
    }
    
    /**
     * Set VIP Event Listener for this thread
     * @param vipEventListeners
     */
    public synchronized void setVipEventListeners(VIPEventListener[] vipEventListeners) {
        this.vipEventListeners = vipEventListeners;
    }
    
    /**
     * Get VIP event listener
     * @return
     */
    public synchronized VIPEventListener[] getVIPEventListner() {
        return vipEventListeners;
    }
}
