package com.callrite.cbs.event;

/**
 * Invoke the listener remotely through RESTFUL web services
 * Data pass in/back in JSON format
 * @author JLiang
 *
 */
public class CBSEventRemoteListener implements CBSEventListener {
    // web serivce URL
    private String[] webserviceURLs;
    
    public CBSEventRemoteListener(String[] webserviceURLs) {
        super();
        this.webserviceURLs = webserviceURLs;
    }

    /* (non-Javadoc)
     * @see com.callrite.cbs.event.CBSEventListener#handleEvent(com.callrite.cbs.event.CBSEvent)
     */
    public void handleEvent(CBSEvent event) {
        // TODO Auto-generated method stub
        
    }
    
    
}
