package com.callrite.cbs.event;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.callrite.cbs.callactivity.CBSCallActivityDefaultListener;
import com.callrite.cbs.util.Config;

/**
 * This is the manager class that manage the event in the queue
 * @author JLiang
 *
 */
public class CBSEventManager {
    /** Singletone Instance **/
    private static CBSEventManager instance = new CBSEventManager();
    
    /** event queues **/
    private BlockingQueue<CBSEvent> callActivityEventQueue; 
    private CBSEventWorkerThread[] callActivityWorkers;
    
    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger(CBSEventManager.class);
    
    /**
     * private constructor
     */
    private void CBSEventManager() {
    }
    
    /**
     * 
     * @return
     */
    public static CBSEventManager getInstance() {
        return instance;
    }
    
    /**
     * Initialize the event queues
     */
    public void initQueues() {
        terminateQueues();
        callActivityEventQueue = new LinkedBlockingQueue<CBSEvent>( Config.getInstance().getSetting( "CallActivityQueueCapacity", 5000 ) );
        //start worker thread for call activity 
        int numberOfCallActivityWorker = Config.getInstance().getSetting( "CallActivityWorkerNum", 5 );
        callActivityWorkers = new CBSEventWorkerThread[numberOfCallActivityWorker];
        logger.info("Starting [" + numberOfCallActivityWorker + "] worker threads to handle call activity event");
        CBSEventListener[] callActivityListeners = new CBSEventListener[1];
        callActivityListeners[0] = new CBSCallActivityDefaultListener();
        for ( int i=0; i<numberOfCallActivityWorker; i++) {
            callActivityWorkers[i] = new CBSEventWorkerThread(CBSEvent.CALL_ACTIVITY, i+1, callActivityListeners);
            callActivityWorkers[i].start();
        }
        
    }
    
    /**
     * Terminate queues
     */
    public void terminateQueues() {
        //terminate call activity workers
        if ( callActivityWorkers != null ) {
            for ( CBSEventWorkerThread worker: callActivityWorkers ) {
                worker.terminate();
            }
        }
    }
    
    /**
     * fire CBSHelper event
     * @param event
     */
    public void fireCBSEvent(CBSEvent event) {
        //add to the queue
        logger.debug( "Add event to queue [" + event.getEventType() + "] ");
        boolean eventAdded = false;
        if ( event.getEventType() == CBSEvent.CALL_ACTIVITY ) {
            try {
                eventAdded = callActivityEventQueue.offer(event, 1000, TimeUnit.MILLISECONDS );
            } catch (InterruptedException e) {
            }
                    
            if (! eventAdded ) {
                logger.warn("It seems your event queue for [" + event.getEventType() + "]  is full" );
            }
        } else {
            logger.error("No worker for this type of event [" + event.getEventType() + "] ");
        }
    }
    
    /**
     * Consume CBSHelper event
     * @param event
     */
    public CBSEvent consumeCBSEvent(int eventType) {
        if ( eventType == CBSEvent.CALL_ACTIVITY ) {
            try {
                return callActivityEventQueue.take();
            } catch (InterruptedException e) {
            }
        } else {
            logger.error("No event queue for event [" + eventType + "], hold the thread ... ");
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        return null;
    }
    
    /**
     * Notify when the event is handled
     * @param event
     */
    public void notifyCBSEventHandled(CBSEvent event) {
        synchronized(event) {
            event.notifyAll();
        }
    }
    
}
