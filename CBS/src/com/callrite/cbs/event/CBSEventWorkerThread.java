package com.callrite.cbs.event;

import org.apache.log4j.Logger;

import com.callrite.cbs.util.VRThread;

/**
 * CBSHelper Event worker Thread
 * @author JLiang
 *
 */
public class CBSEventWorkerThread extends VRThread {
    /** The event type that the worker thread is working on **/
    private int eventType;
    /** thread identifier **/
    private int threadID;
    private CBSEventListener[] cbsEventListeners;
    
    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger(CBSEventWorkerThread.class);
    
    /**
     * Consturctor
     */
    public CBSEventWorkerThread(int eventType, int threadID, CBSEventListener[] cbsEventListeners) {
        super();
        this.eventType = eventType;
        this.threadID = threadID;
        this.cbsEventListeners = cbsEventListeners;
    }

    /* (non-Javadoc)
     * @see java.lang.Thread#run()
     */
    @Override
    public void run() {
        logger.info("Event worker [" + threadID + "] for event type [" + eventType + "] started" );
        
        while ( !terminated() ) {
            CBSEvent event = CBSEventManager.getInstance().consumeCBSEvent(eventType);
            logger.debug("Event worker [" + threadID + "] for event type [" + eventType + "] handling event [" + event + "]");
            for ( CBSEventListener listener : getCBSEventListner() ) {
                listener.handleEvent(event);
            }
            //notify event is handled
            CBSEventManager.getInstance().notifyCBSEventHandled(event);
            logger.debug("Event worker [" + threadID + "] for event type [" + eventType + "] event [" + event + "] handled");
        }
        
        logger.info("Event worker [" + threadID + "] for event type [" + eventType + "] stopped" );
    }
    
    /**
     * Set CBSHelper Event Listener for this thread
     * @param cbsEventListeners
     */
    public synchronized void setCBSEventListeners(CBSEventListener[] cbsEventListeners) {
        this.cbsEventListeners = cbsEventListeners;
    }
    
    /**
     * Get CBSHelper event listener
     * @return
     */
    public synchronized CBSEventListener[] getCBSEventListner() {
        return cbsEventListeners;
    }
}
