package com.callrite.cbs.callactivity;

import org.apache.log4j.Logger;

import com.callrite.cbs.event.CBSEvent;
import com.callrite.cbs.event.CBSEventListener;

/**
 * This is the default listener to call activity
 * @author JLiang
 *
 */
public class CBSCallActivityDefaultListener implements CBSEventListener {
    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger(CBSCallActivityDefaultListener.class);

    /* (non-Javadoc)
     * @see com.callrite.cbs.event.CBSEventListener#handleEvent(com.callrite.cbs.event.CBSEvent)
     */
    public void handleEvent(CBSEvent event) {
        logger.debug("Process call event [" + event + "]" );
    }

}
