package com.callrite.cbs.event;

/**
 * Invoke the listener remotely through RESTFUL web services
 * Data pass in/back in JSON format
 * @author JLiang
 *
 */
public class VIPEventRemoteListener implements VIPEventListener {
    // web serivce URL
    private String[] webserviceURLs;
    
    public VIPEventRemoteListener(String[] webserviceURLs) {
        super();
        this.webserviceURLs = webserviceURLs;
    }

    /* (non-Javadoc)
     * @see com.callrite.cbs.event.VIPEventListener#handleEvent(com.callrite.cbs.event.VIPEvent)
     */
    public void handleEvent(VIPEvent event) {
        // TODO Auto-generated method stub
        
    }
    
    
}
