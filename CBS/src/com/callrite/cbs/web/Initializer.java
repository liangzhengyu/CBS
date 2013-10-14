package com.callrite.cbs.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.callrite.cbs.event.VIPEventManager;
import com.callrite.cbs.stats.StatisticsTracker;

/**
 * Application life cycle listener implementation class Initializer
 */
public class Initializer  implements ServletContextListener {
    private static Logger logger = Logger.getLogger(Initializer.class);

    /**
     * Default constructor. 
     */
    public Initializer() {
    }
    
    /* (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        logger.debug("At end") ;

        //destroy event queues
        VIPEventManager.getInstance().terminateQueues();

        StatisticsTracker.getInstance().terminate() ;
        StatisticsTracker.getInstance().interrupt() ;
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
        logger.debug("At start") ;
        
        // Call getInstance() to force the singleton to be initialized
        StatisticsTracker.getInstance() ;
        
        //Initialize event queues
        VIPEventManager.getInstance().initQueues();

    }

}
