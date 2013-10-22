package com.callrite.cbs.event;

/**
 * 
 * CBSHelper event interface
 * @author JLiang
 *
 */
public interface CBSEvent {
    /** Available Event Type **/
    public final static int CALL_ACTIVITY = 1;
    
    public int getEventType();
    public Object getEventSource();
    public Object getEventResult();
    public int getEventPriority();
}
