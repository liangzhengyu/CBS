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
 * VIP Event base class
 * @author JLiang
 *
 */
public abstract class VIPEventBase implements VIPEvent {
    private Object eventData;
    private String eventSource;
    private int eventType;
    private Object eventResponseData;
    private int eventPriority;
    
    /* (non-Javadoc)
     * @see com.callrite.cbs.event.VIPEvent#getVIPEventData()
     */
    public Object getVIPEventData() {
        return eventData;
    }
    
    /**
     * Set event data
     * @param eventData
     */
    public void setVIPEventData(Object eventData) {
        this.eventData = eventData;
    }

    /* (non-Javadoc)
     * @see com.callrite.cbs.event.VIPEvent#getEventSource()
     */
    public String getEventSource() {
        return eventSource;
    }

    /**
     * Set event source
     * @param eventSource
     */
    public void setEventSource(String eventSource) {
        this.eventSource = eventSource;
    }
    
    /* (non-Javadoc)
     * @see com.callrite.cbs.event.VIPEvent#getEventType()
     */
    public int getEventType() {
        return eventType;
    }

    /**
     * Set event type
     * @param eventType
     */
    public void setEventType(int eventType) {
        this.eventType = eventType;
    }
    
    /* (non-Javadoc)
     * @see com.callrite.cbs.event.VIPEvent#getVIPEventResponseData()
     */
    public Object getVIPEventResponseData() {
        return eventResponseData;
    }
    
    /**
     * Set event response data
     * @param eventResponseData
     */
    public void setVIPEventResponseData(Object eventResponseData) {
        this.eventResponseData =  eventResponseData;
    }
    
    /* (non-Javadoc)
     * @see com.callrite.cbs.event.VIPEvent#getEventPriority()
     */
    public int getEventPriority() {
        return eventPriority;
    }
    
    /**
     * Event Priority
     * @param eventPriority
     */
    public void setEventPriority(int eventPriority) {
        this.eventPriority = eventPriority;
    }
}
