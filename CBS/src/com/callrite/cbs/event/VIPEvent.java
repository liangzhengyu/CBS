package com.callrite.cbs.event;

/**
 * 
 * VIP event interface
 * @author JLiang
 *
 */
public interface VIPEvent {
    /** Available Event Type **/
    public final static int CALL_ACTIVITY = 1;
    
    /** VIP Event Data **/
    public Object getVIPEventData();
    /** VIP Event Source **/
    public String getEventSource();
    /** VIP Event Type **/
    public int getEventType();
    /** VIP Event Response Data **/
    public Object getVIPEventResponseData();
    /** VIP Event Response Data **/
    public void setVIPEventResponseData(Object eventResponseData);
    /** VIP Event Data in JSON Format **/
    public String getVIPEventJSONData();
    /** VIP Event Response Data in JSON Format **/
    public String getVIPResponseEventJSONData();
    /** Get Event Priority **/
    public int getEventPriority();
    /**
     * Waiting for event response in milliseconds
     * @return 0 means no waiting
     */
    public int waitingForEventResponse();
}
