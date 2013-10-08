/*
 * $Id$
 * 
 * Created on Nov 10, 2011
 *
 * Copyright © VoiceRite, Inc. 2011.  All Rights reserved.
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has been
 * deposited with the U.S. Copyright office.
 *
 */
package com.callrite.cbs.stats;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.callrite.cbs.util.Config;
import com.callrite.cbs.util.VRThread;

/**
 * @author JLiang
 * 
 * This is used to track statistics about the web application and call requests.
 */
public class StatisticsTracker extends VRThread
{
    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger("com.callrite.cbs.stats.StatisticsTracker") ;
    
 
    private static StatisticsTracker instance = new StatisticsTracker() ;
       
    /**
     * This is a percentage value.  If our connections goes above this percentage usage then
     * a warning message is logged.
     */
    public static final int CONNECTION_POOL_USAGE_PERCENT_WARNING = 75 ;
    
    private int dbPoolConnections = 0;
    private int numDbConnections = 0 ;
    private int peakNumDbConnections = 0 ;
    private Date peakNumDbConnectionsTime = null ;
    private int dbConnectionsWarning = 0 ;
    private Object dbPoolConnectionsAccess = new Object() ;
    
    private Date startTimeWebServiceRequestsTracking ;
    private int totalWebServiceRequests = 0 ;
    private int lastMinuteWebServiceRequests = 0 ;
    private int currentMinuteWebServiceRequests = 0 ;   
    private Hashtable<String,RequestCount> methodRequestCounts = new Hashtable<String,RequestCount>() ;
    private Object numWebServiceRequestsAccess = new Object() ;
    
    private Date startTimeRejectedWebServiceRequestsTracking ;
    private int totalRejectedWebServiceRequests = 0 ;
    private int lastMinuteRejectedWebServiceRequests = 0 ;
    private int currentMinuteRejectedWebServiceRequests = 0 ;   
    private Object numRejectedWebServiceRequestsAccess = new Object() ;
    
    private Date startTimeCallServiceRequestsTracking ;
    private int totalCallServiceRequests = 0 ;
    private int lastMinuteCallServiceRequests = 0 ;
    private int currentMinuteCallServiceRequests = 0 ;   
    private Hashtable<String,RequestCount> callRequestCounts = new Hashtable<String,RequestCount>() ;
    private Object numCallServiceRequestsAccess = new Object() ;
    
    private Date startTimeRejectedCallServiceRequestsTracking ;
    private int totalRejectedCallServiceRequests = 0 ;
    private int lastMinuteRejectedCallServiceRequests = 0 ;
    private int currentMinuteRejectedCallServiceRequests = 0 ;   
    private Object numRejectedCallServiceRequestsAccess = new Object() ;
    
    private long currentMinStartTime = 0 ;
    private double rejectionWebServiceThreshold = 0.0;
    private double rejectionCallServiceThrehold = 0.0;
    
    /**
     * Hidden singleton constructor
     */
    private StatisticsTracker()
    {
        startTimeWebServiceRequestsTracking = new Date() ;
        startTimeRejectedWebServiceRequestsTracking = new Date() ;
        
        try {
            String rejectionThreasholdStr = Config.getInstance().getSetting("RequestRejectionThreshold", "0.0") ;
            rejectionWebServiceThreshold = Double.parseDouble(rejectionThreasholdStr) ;
        } catch (NumberFormatException e) {
            rejectionWebServiceThreshold = 0.0;
        }
        
        try {
            String rejectionCallServiceThreholdStr = Config.getInstance(Config.TELEPHONY).getSetting("RequestRejectionThreshold", "0.0") ;
            rejectionCallServiceThrehold = Double.parseDouble(rejectionCallServiceThreholdStr) ;
        } catch (NumberFormatException e) {
            rejectionCallServiceThrehold = 0.0;
        }
        
        DecimalFormat df = new DecimalFormat("#.##");
        logger.info("Web Service rejection theshold is [" + df.format(rejectionWebServiceThreshold) + "]") ;
        logger.info("Call Service rejection theshold is [" + df.format(rejectionCallServiceThrehold) + "]") ;
        
        start() ;
    }
    
    /**
     * Get the singleton instance
     * 
     * @return
     */
    public static StatisticsTracker getInstance() {
        return instance ;
    }
    
    /**
     * Set the current number of connectiosn being used
     * 
     * @param numConnections
     */
    public void setNumDbConnections(int numConnections) {       
        synchronized(dbPoolConnectionsAccess) {
            numDbConnections = numConnections ;
            if (numDbConnections > peakNumDbConnections) {
                peakNumDbConnections = numDbConnections ;
                peakNumDbConnectionsTime = new Date() ;
            }

            if (numDbConnections >= dbConnectionsWarning) {
                logger.warn("DB connection pool has [" + dbPoolConnections + "] and [" + numDbConnections + "] are in use") ;
            }
        }
        
    }
    
    public synchronized void setConnectionPoolSize(int poolSize) {
        synchronized(dbPoolConnectionsAccess) {
            dbPoolConnections = poolSize ;
            dbConnectionsWarning = (int) Math.ceil(((double) dbPoolConnections * (double) CONNECTION_POOL_USAGE_PERCENT_WARNING) / 100.0) ;
    
            logger.debug("Connection pool has [" + dbPoolConnections + "] connections.  Will warn when are are using [" + dbConnectionsWarning + "] or more");
        }
    }    
    
    /**
     * Track WebService calls
     *
     */
    private void internalTrackWebServiceRequest(String methodName) {       
        synchronized(numWebServiceRequestsAccess) {
            totalWebServiceRequests++ ;
            
            // Ensure we don't overflow.  If we are near the max, then restart the start time and set to 1
            if (totalWebServiceRequests > Integer.MAX_VALUE - 5) {
                startTimeWebServiceRequestsTracking = new Date() ;
                methodRequestCounts.clear() ;
                totalWebServiceRequests = 1 ;
            }
            
            currentMinuteWebServiceRequests++ ;
            
            // Update the count for the method
            RequestCount r = methodRequestCounts.get(methodName) ;
            if (r == null) {
                r = new RequestCount(methodName) ;
                methodRequestCounts.put(methodName, r) ;
            }
            r.increment() ;
        }
    }
    
    /**
     * Track Call service requests
     *
     */
    private void internalTrackCallServiceRequest(String source) {       
        synchronized(numCallServiceRequestsAccess) {
            totalCallServiceRequests++ ;
            
            // Ensure we don't overflow.  If we are near the max, then restart the start time and set to 1
            if (totalCallServiceRequests > Integer.MAX_VALUE - 5) {
                startTimeCallServiceRequestsTracking = new Date() ;
                callRequestCounts.clear();
                totalCallServiceRequests = 1 ;
            }
            
            currentMinuteCallServiceRequests++ ;
            
            // Update the count for the method
            RequestCount r = callRequestCounts.get(source) ;
            if (r == null) {
                r = new RequestCount(source) ;
                callRequestCounts.put(source, r) ;
            }
            r.increment() ;
        }
    }
    
    /**
     * Track rejected web services
     */
    private void internalTrackRejectedWebServiceRequest() {       
        synchronized(numRejectedWebServiceRequestsAccess) {
            totalRejectedWebServiceRequests++ ;
            
            // Ensure we don't overflow.  If we are near the max, then restart the start time and set to 1
            if (totalRejectedWebServiceRequests > Integer.MAX_VALUE - 5) {
                startTimeRejectedWebServiceRequestsTracking = new Date() ;
                totalRejectedWebServiceRequests = 1 ;
            }
            
            currentMinuteRejectedWebServiceRequests++ ;            
        }
    }    
    
    
    /**
     * Track rejected call services
     */
    private void internalTrackRejectedCallServiceRequest() {       
        synchronized(numCallServiceRequestsAccess) {
            totalRejectedCallServiceRequests++ ;
            
            // Ensure we don't overflow.  If we are near the max, then restart the start time and set to 1
            if (totalRejectedCallServiceRequests > Integer.MAX_VALUE - 5) {
                startTimeRejectedCallServiceRequestsTracking = new Date() ;
                totalRejectedCallServiceRequests = 1 ;
            }
            
            currentMinuteRejectedCallServiceRequests++ ;            
        }
    }    
    
    /**
     * Get the current statistics
     * 
     * @return
     */
    private Statistics internalGetStatistics()
    {
        Statistics stats = new Statistics() ;
        
        ArrayList<RequestCount> counts = new ArrayList<RequestCount>() ;
        ArrayList<RequestCount> callCounts = new ArrayList<RequestCount>() ;
                
        synchronized(dbPoolConnectionsAccess) {
            stats.setDbPoolConnections(dbPoolConnections) ;
            stats.setDbConnectionsWarning(dbConnectionsWarning) ;
            stats.setNumDbConnections(numDbConnections) ;
            stats.setPeakNumDbConnections(peakNumDbConnections) ;
            stats.setPeakNumDbConnectionsTime(peakNumDbConnectionsTime) ;
        }
        
        synchronized(numWebServiceRequestsAccess) {
            stats.setWebServiceRequestsStartTime(startTimeWebServiceRequestsTracking) ;
            stats.setTotalWebServiceRequests(totalWebServiceRequests) ;
            stats.setNumWebServiceRequestsLastMinute(lastMinuteWebServiceRequests) ;
            stats.setNumWebServiceRequestsCurrentPartialMinute(currentMinuteWebServiceRequests) ;
            
            Collection<RequestCount> c = methodRequestCounts.values() ;
            Iterator<RequestCount> i = c.iterator() ;
            while (i.hasNext()) {
                RequestCount r = i.next() ;
                counts.add(new RequestCount(r.getMethodName(), r.getNumRequests())) ;
            }
        }
        
        synchronized(numRejectedWebServiceRequestsAccess) {
            stats.setRejectedWebServiceRequestsStartTime(startTimeRejectedWebServiceRequestsTracking) ;
            stats.setTotalRejectedWebServiceRequests(totalRejectedWebServiceRequests) ;
            stats.setNumRejectedWebServiceRequestsLastMinute(lastMinuteRejectedWebServiceRequests) ;
            stats.setNumRejectedWebServiceRequestsCurrentPartialMinute(currentMinuteRejectedWebServiceRequests) ;
        }        
        
        // Now sort the method requests
        Collections.sort(counts) ;

        stats.setRequestCounts(counts.toArray(new RequestCount[counts.size()])) ;
        
        synchronized(numCallServiceRequestsAccess) {
            stats.setCallServiceRequestsStartTime(startTimeCallServiceRequestsTracking) ;
            stats.setTotalCallServiceRequests(totalCallServiceRequests) ;
            stats.setNumCallServiceRequestsLastMinute(lastMinuteCallServiceRequests) ;
            stats.setNumCallServiceRequestsCurrentPartialMinute(currentMinuteCallServiceRequests) ;
            
            Collection<RequestCount> c = callRequestCounts.values() ;
            Iterator<RequestCount> i = c.iterator() ;
            while (i.hasNext()) {
                RequestCount r = i.next() ;
                callCounts.add(new RequestCount(r.getMethodName(), r.getNumRequests())) ;
            }
        }
        
        synchronized(numRejectedCallServiceRequestsAccess) {
            stats.setRejectedCallServiceRequestsStartTime(startTimeRejectedCallServiceRequestsTracking) ;
            stats.setTotalRejectedCallServiceRequests(totalRejectedCallServiceRequests) ;
            stats.setNumRejectedCallServiceRequestsLastMinute(lastMinuteRejectedCallServiceRequests) ;
            stats.setNumRejectedCallServiceRequestsCurrentPartialMinute(currentMinuteRejectedCallServiceRequests) ;
        }        
        
        // Now sort the call requests
        Collections.sort(callCounts) ;

        stats.setCallRequestCounts(callCounts.toArray(new RequestCount[callCounts.size()])) ;
        
        return stats ;
    }
    
    /**
     * Get the instantaneous requests/sec rate.  If the current min is at least 15 seconds long,
     * then that is used.  If it's less, then the previous minute is used.
     * 
     * @return
     */
    private double internalGetCurrentRequestsPerSecond()
    {
        int currentDurationSecs = (int) ((System.currentTimeMillis() - currentMinStartTime) / 1000) ;
        
        if (currentDurationSecs >= 15) {
            return ((double) (currentMinuteWebServiceRequests - currentMinuteRejectedWebServiceRequests)) / ((double) currentDurationSecs) ;
        } else {
            return ((double) (lastMinuteWebServiceRequests - lastMinuteRejectedWebServiceRequests)) / 60.0 ;
        }
    }
    
    /**
     * Get the instantaneous requests/sec rate.  If the current min is at least 15 seconds long,
     * then that is used.  If it's less, then the previous minute is used.
     * 
     * @return
     */
    private double internalGetCurrentCallRequestsPerSecond()
    {
        int currentDurationSecs = (int) ((System.currentTimeMillis() - currentMinStartTime) / 1000) ;
        
        if (currentDurationSecs >= 15) {
            return ((double) (currentMinuteCallServiceRequests - currentMinuteRejectedCallServiceRequests)) / ((double) currentDurationSecs) ;
        } else {
            return ((double) (lastMinuteCallServiceRequests - lastMinuteRejectedCallServiceRequests)) / 60.0 ;
        }
    }
    
    /**
     * Determine if the system is currently overloaded
     * 
     * @return
     */
    private boolean internalIsOverloaded()
    {
        if (rejectionWebServiceThreshold > 0.0 && StatisticsTracker.getCurrentRequestsPerSecond() > rejectionWebServiceThreshold) {
            return true ;
        } else {
            return false ;
        }
    }
    
    /**
     * Determine if the system is currently overloaded
     * 
     * @return
     */
    private boolean internalIsCallServiceOverloaded()
    {
        if (rejectionCallServiceThrehold > 0.0 && StatisticsTracker.getCurrentCallRequestsPerSecond() > rejectionCallServiceThrehold) {
            return true;
        } else {
            return false ;
        }
    }
    
    /**
     * Get the instantaneous requests/sec rate.  If the current min is at least 15 seconds long,
     * then that is used.  If it's less, then the previous minute is used.
     * 
     * @return
     */    
    public static double getCurrentRequestsPerSecond()
    {
        return instance.internalGetCurrentRequestsPerSecond() ;
    }
    
    /**
     * Get the instantaneous requests/sec rate.  If the current min is at least 15 seconds long,
     * then that is used.  If it's less, then the previous minute is used.
     * 
     * @return
     */    
    public static double getCurrentCallRequestsPerSecond()
    {
        return instance.internalGetCurrentCallRequestsPerSecond() ;
    }
    
    /**
     * Determine if the system is currently overloaded
     * 
     * @return
     */    
    public static boolean isOverloaded() {
        return instance.internalIsOverloaded() ;
    }
    
    /**
     * Determine if the system is currently overloaded
     * 
     * @return
     */    
    public static boolean isCallServiceOverloaded() {
        return instance.internalIsCallServiceOverloaded() ;
    }

    /**
     * This is called for each WebService method that is called.  This is used to help gather 
     * statistics about the usage
     * 
     * @param methodName
     */
    public static void trackWebService(String methodName)
    {
        instance.internalTrackWebServiceRequest(methodName) ;
    }
    
    public static void trackRejectedWebService()
    {
        instance.internalTrackRejectedWebServiceRequest() ;
    }
    
    /**
     * This is called for each call service source that is from.  This is used to help gather 
     * statistics about the usage
     * 
     * @param methodName
     */
    public static void trackCallService(String source)
    {
        instance.internalTrackCallServiceRequest(source) ;
    }
    
    public static void trackRejectedCallService()
    {
        instance.internalTrackRejectedCallServiceRequest() ;
    }
    
    /**
     * This is used to get the current statistics
     * 
     * @return
     */
    public static Statistics getStatistics()
    {
        return instance.internalGetStatistics() ;
    }
    
    /**
     * Thread's run method.  This is used to update the current and last minute counts.
     */
    public void run()
    {
        logger.debug("Thread started") ;
        
        while (!terminated()) {
            try {
                try {
                    currentMinStartTime = System.currentTimeMillis() ;                    
                    Thread.sleep(60000) ;
                    
                    synchronized(numWebServiceRequestsAccess) {
                        lastMinuteWebServiceRequests = currentMinuteWebServiceRequests ;
                        currentMinuteWebServiceRequests = 0 ;
                    }
                    
                    synchronized(numRejectedWebServiceRequestsAccess) {
                        lastMinuteRejectedWebServiceRequests = currentMinuteRejectedWebServiceRequests ;
                        currentMinuteRejectedWebServiceRequests = 0 ;
                    }
                    
                    synchronized(numCallServiceRequestsAccess) {
                        lastMinuteCallServiceRequests = currentMinuteCallServiceRequests ;
                        currentMinuteCallServiceRequests = 0 ;
                    }
                    
                    synchronized(numRejectedCallServiceRequestsAccess) {
                        lastMinuteRejectedCallServiceRequests = currentMinuteRejectedCallServiceRequests ;
                        currentMinuteRejectedCallServiceRequests = 0 ;
                    }
                    
                } catch (InterruptedException e) {
                    break ;
                }
                
            } catch (Exception e) {
            }
        }
        
        logger.debug("Thread ending") ;
    }
    
}