package com.callrite.cbs.event;

/**
 * CBSHelper Event base class
 * @author JLiang
 *
 */
public class CBSEventAdaptor implements CBSEvent {
    private int eventType = CBSEvent.CALL_ACTIVITY;
    private Object eventSource;
    private Object eventResult;
    private int eventPriority;
    
    /**
     * Default
     */
    public CBSEventAdaptor() {
        super();
    }

    
    /**
     * @param eventType
     * @param eventSource
     * @param eventResult
     * @param eventPriority
     */
    public CBSEventAdaptor(int eventType, Object eventSource,
            Object eventResult, int eventPriority) {
        super();
        this.eventType = eventType;
        this.eventSource = eventSource;
        this.eventResult = eventResult;
        this.eventPriority = eventPriority;
    }


    /* (non-Javadoc)
     * @see com.callrite.cbs.event.CBSEvent#getEventType()
     */
    public int getEventType() {
        return eventType;
    }

    /**
     * @param eventType the eventType to set
     */
    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    /* (non-Javadoc)
     * @see com.callrite.cbs.event.CBSEvent#getEventSource()
     */
    public Object getEventSource() {
        return eventSource;
    }

    /**
     * @param eventSource the eventSource to set
     */
    public void setEventSource(Object eventSource) {
        this.eventSource = eventSource;
    }

    /* (non-Javadoc)
     * @see com.callrite.cbs.event.CBSEvent#getEventResult()
     */
    public Object getEventResult() {
        return eventResult;
    }

    /* (non-Javadoc)
     * @see com.callrite.cbs.event.CBSEvent#getEventPriority()
     */
    public int getEventPriority() {
        return eventPriority;
    }

    /**
     * @param eventResult the eventResult to set
     */
    public void setEventResult(Object eventResult) {
        this.eventResult = eventResult;
    }

    /**
     * @param eventPriority the eventPriority to set
     */
    public void setEventPriority(int eventPriority) {
        this.eventPriority = eventPriority;
    }

}
