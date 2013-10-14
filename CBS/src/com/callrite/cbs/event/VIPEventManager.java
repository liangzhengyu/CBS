package com.callrite.cbs.event;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.callrite.cbs.callactivity.VIPCallActivityDefaultListener;
import com.callrite.cbs.callactivity.VIPCallActivityEvent;
import com.callrite.cbs.util.Config;

/**
 * This is the manager class that manage the event in the queue
 * @author JLiang
 *
 */
public class VIPEventManager {
    /** Singletone Instance **/
    private static VIPEventManager instance = new VIPEventManager();
    
    /** event queues **/
    private BlockingQueue<VIPEvent> callActivityEventQueue; 
    private VIPEventWorkerThread[] callActivityWorkers;
    
    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger(VIPEventManager.class);
    
    /**
     * private constructor
     */
    private void VIPEventManager() {
    }
    
    /**
     * 
     * @return
     */
    public static VIPEventManager getInstance() {
        return instance;
    }
    
    /**
     * Initialize the event queues
     */
    public void initQueues() {
        terminateQueues();
        callActivityEventQueue = new LinkedBlockingQueue<VIPEvent>( Config.getInstance().getSetting( "CallActivityQueueCapacity", 5000 ) );
        //start worker thread for call activity 
        int numberOfCallActivityWorker = Config.getInstance().getSetting( "CallActivityWorkerNum", 5 );
        callActivityWorkers = new VIPEventWorkerThread[numberOfCallActivityWorker];
        logger.info("Starting [" + numberOfCallActivityWorker + "] worker threads to handle call activity event");
        VIPEventListener[] callActivityListeners = new VIPEventListener[1];
        callActivityListeners[0] = new VIPCallActivityDefaultListener();
        for ( int i=0; i<numberOfCallActivityWorker; i++) {
            callActivityWorkers[i] = new VIPEventWorkerThread(VIPEvent.CALL_ACTIVITY, i+1, callActivityListeners);
            callActivityWorkers[i].start();
        }
        
    }
    
    /**
     * Terminate queues
     */
    public void terminateQueues() {
        //terminate call activity workers
        if ( callActivityWorkers != null ) {
            for ( VIPEventWorkerThread worker: callActivityWorkers ) {
                worker.terminate();
            }
        }
    }
    
    /**
     * fire VIP event
     * @param event
     */
    public void fireVIPEvent(VIPEvent event) {
        //add to the queue
        logger.debug( "Add event to queue [" + event.getEventType() + "] ");
        boolean eventAdded = false;
        if ( event.getEventType() == VIPEvent.CALL_ACTIVITY ) {
            try {
                eventAdded = callActivityEventQueue.offer(event, (long)event.waitingForEventResponse(), TimeUnit.MILLISECONDS );
            } catch (InterruptedException e) {
            }
                    
            if (! eventAdded ) {
                logger.warn("It seems your event queue for [" + event.getEventType() + "]  is full" );
            }
        } else {
            logger.error("No worker for this type of event [" + event.getEventType() + "] ");
        }
        if (eventAdded && event.waitingForEventResponse() > 0 ) {
            synchronized(event) {
                try {
                    event.wait(event.waitingForEventResponse());
                } catch (InterruptedException e) {
                }
            }
        }
    }
    
    /**
     * Consume VIP event
     * @param event
     */
    public VIPEvent consumeVIPEvent(int eventType) {
        if ( eventType == VIPEvent.CALL_ACTIVITY ) {
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
    public void notifyVIPEventHandled(VIPEvent event) {
        synchronized(event) {
            event.notifyAll();
        }
    }
    
}
