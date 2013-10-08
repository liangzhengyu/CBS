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


import java.util.Date;

/**
 * @author JLiang
 *
 * This is Java bean that holds the current statistics when request from the StatisticsTracker
 */
public class Statistics
{
    private int dbPoolConnections ;
    private int numDbConnections  ;
    private int peakNumDbConnections  ;
    private Date peakNumDbConnectionsTime;
    private int dbConnectionsWarning ;
    
    private Date webServiceRequestsStartTime ;
    private int totalWebServiceRequests ;
    private int numWebServiceRequestsLastMinute ;
    private int numWebServiceRequestsCurrentPartialMinute ;
    private RequestCount [] requestCounts ;
    
    private Date rejectedWebServiceRequestsStartTime ;
    private int totalRejectedWebServiceRequests ;
    private int numRejectedWebServiceRequestsLastMinute ;
    private int numRejectedWebServiceRequestsCurrentPartialMinute ;
    
    private Date callServiceRequestsStartTime ;
    private int totalCallServiceRequests ;
    private int numCallServiceRequestsLastMinute ;
    private int numCallServiceRequestsCurrentPartialMinute ;
    private RequestCount [] callRequestCounts ;
    
    private Date rejectedCallServiceRequestsStartTime ;
    private int totalRejectedCallServiceRequests ;
    private int numRejectedCallServiceRequestsLastMinute ;
    private int numRejectedCallServiceRequestsCurrentPartialMinute ;
    

    /**
     * Default constructor
     */
    public Statistics()
    {
    }

    public int getDbPoolConnections()
    {
        return dbPoolConnections;
    }

    public void setDbPoolConnections(int dbPoolConnections)
    {
        this.dbPoolConnections = dbPoolConnections;
    }

    public int getNumDbConnections()
    {
        return numDbConnections;
    }

    public void setNumDbConnections(int numDbConnections)
    {
        this.numDbConnections = numDbConnections;
    }

    public int getPeakNumDbConnections()
    {
        return peakNumDbConnections;
    }

    public void setPeakNumDbConnections(int peakNumDbConnections)
    {
        this.peakNumDbConnections = peakNumDbConnections;
    }

    public Date getPeakNumDbConnectionsTime()
    {
        return peakNumDbConnectionsTime;
    }

    public void setPeakNumDbConnectionsTime(Date peakNumDbConnectionsTime)
    {
        this.peakNumDbConnectionsTime = peakNumDbConnectionsTime;
    }

    public int getDbConnectionsWarning()
    {
        return dbConnectionsWarning;
    }

    public void setDbConnectionsWarning(int dbConnectionsWarning)
    {
        this.dbConnectionsWarning = dbConnectionsWarning;
    }

    /**
     * @return the rejectedWebServiceRequestsStartTime
     */
    public Date getRejectedWebServiceRequestsStartTime()
    {
        return rejectedWebServiceRequestsStartTime;
    }

    /**
     * @param rejectedWebServiceRequestsStartTime the rejectedWebServiceRequestsStartTime to set
     */
    public void setRejectedWebServiceRequestsStartTime(
            Date rejectedWebServiceRequestsStartTime)
    {
        this.rejectedWebServiceRequestsStartTime = rejectedWebServiceRequestsStartTime;
    }

    /**
     * @return the totalRejectedWebServiceRequests
     */
    public int getTotalRejectedWebServiceRequests()
    {
        return totalRejectedWebServiceRequests;
    }

    /**
     * @param totalRejectedWebServiceRequests the totalRejectedWebServiceRequests to set
     */
    public void setTotalRejectedWebServiceRequests(
            int totalRejectedWebServiceRequests)
    {
        this.totalRejectedWebServiceRequests = totalRejectedWebServiceRequests;
    }

    /**
     * @return the numRejectedWebServiceRequestsLastMinute
     */
    public int getNumRejectedWebServiceRequestsLastMinute()
    {
        return numRejectedWebServiceRequestsLastMinute;
    }

    /**
     * @param numRejectedWebServiceRequestsLastMinute the numRejectedWebServiceRequestsLastMinute to set
     */
    public void setNumRejectedWebServiceRequestsLastMinute(
            int numRejectedWebServiceRequestsLastMinute)
    {
        this.numRejectedWebServiceRequestsLastMinute = numRejectedWebServiceRequestsLastMinute;
    }

    /**
     * @return the numRejectedWebServiceRequestsCurrentPartialMinute
     */
    public int getNumRejectedWebServiceRequestsCurrentPartialMinute()
    {
        return numRejectedWebServiceRequestsCurrentPartialMinute;
    }

    /**
     * @param numRejectedWebServiceRequestsCurrentPartialMinute the numRejectedWebServiceRequestsCurrentPartialMinute to set
     */
    public void setNumRejectedWebServiceRequestsCurrentPartialMinute(
            int numRejectedWebServiceRequestsCurrentPartialMinute)
    {
        this.numRejectedWebServiceRequestsCurrentPartialMinute = numRejectedWebServiceRequestsCurrentPartialMinute;
    }


    /**
     * @return the webServiceRequestsStartTime
     */
    public Date getWebServiceRequestsStartTime()
    {
        return webServiceRequestsStartTime;
    }

    /**
     * @param webServiceRequestsStartTime the webServiceRequestsStartTime to set
     */
    public void setWebServiceRequestsStartTime(Date webServiceRequestsStartTime)
    {
        this.webServiceRequestsStartTime = webServiceRequestsStartTime;
    }

    /**
     * @return the totalWebServiceRequests
     */
    public int getTotalWebServiceRequests()
    {
        return totalWebServiceRequests;
    }

    /**
     * @param totalWebServiceRequests the totalWebServiceRequests to set
     */
    public void setTotalWebServiceRequests(int totalWebServiceRequests)
    {
        this.totalWebServiceRequests = totalWebServiceRequests;
    }

    /**
     * @return the numWebServiceRequestsLastMinute
     */
    public int getNumWebServiceRequestsLastMinute()
    {
        return numWebServiceRequestsLastMinute;
    }

    /**
     * @param numWebServiceRequestsLastMinute the numWebServiceRequestsLastMinute to set
     */
    public void setNumWebServiceRequestsLastMinute(
            int numWebServiceRequestsLastMinute)
    {
        this.numWebServiceRequestsLastMinute = numWebServiceRequestsLastMinute;
    }

    /**
     * @return the numWebServiceRequestsCurrentPartialMinute
     */
    public int getNumWebServiceRequestsCurrentPartialMinute()
    {
        return numWebServiceRequestsCurrentPartialMinute;
    }

    /**
     * @param numWebServiceRequestsCurrentPartialMinute the numWebServiceRequestsCurrentPartialMinute to set
     */
    public void setNumWebServiceRequestsCurrentPartialMinute(
            int numWebServiceRequestsCurrentPartialMinute)
    {
        this.numWebServiceRequestsCurrentPartialMinute = numWebServiceRequestsCurrentPartialMinute;
    }

    /**
     * @return the requestCounts
     */
    public RequestCount[] getRequestCounts()
    {
        return requestCounts;
    }

    /**
     * @param requestCounts the requestCounts to set
     */
    public void setRequestCounts(RequestCount[] requestCounts)
    {
        this.requestCounts = requestCounts;
    }

    /**
     * @return the callServiceRequestsStartTime
     */
    public Date getCallServiceRequestsStartTime() {
        return callServiceRequestsStartTime;
    }

    /**
     * @param callServiceRequestsStartTime the callServiceRequestsStartTime to set
     */
    public void setCallServiceRequestsStartTime(Date callServiceRequestsStartTime) {
        this.callServiceRequestsStartTime = callServiceRequestsStartTime;
    }

    /**
     * @return the totalCallServiceRequests
     */
    public int getTotalCallServiceRequests() {
        return totalCallServiceRequests;
    }

    /**
     * @param totalCallServiceRequests the totalCallServiceRequests to set
     */
    public void setTotalCallServiceRequests(int totalCallServiceRequests) {
        this.totalCallServiceRequests = totalCallServiceRequests;
    }

    /**
     * @return the numCallServiceRequestsLastMinute
     */
    public int getNumCallServiceRequestsLastMinute() {
        return numCallServiceRequestsLastMinute;
    }

    /**
     * @param numCallServiceRequestsLastMinute the numCallServiceRequestsLastMinute to set
     */
    public void setNumCallServiceRequestsLastMinute(
            int numCallServiceRequestsLastMinute) {
        this.numCallServiceRequestsLastMinute = numCallServiceRequestsLastMinute;
    }

    /**
     * @return the numCallServiceRequestsCurrentPartialMinute
     */
    public int getNumCallServiceRequestsCurrentPartialMinute() {
        return numCallServiceRequestsCurrentPartialMinute;
    }

    /**
     * @param numCallServiceRequestsCurrentPartialMinute the numCallServiceRequestsCurrentPartialMinute to set
     */
    public void setNumCallServiceRequestsCurrentPartialMinute(
            int numCallServiceRequestsCurrentPartialMinute) {
        this.numCallServiceRequestsCurrentPartialMinute = numCallServiceRequestsCurrentPartialMinute;
    }

    /**
     * @return the callRequestCounts
     */
    public RequestCount[] getCallRequestCounts() {
        return callRequestCounts;
    }

    /**
     * @param callRequestCounts the callRequestCounts to set
     */
    public void setCallRequestCounts(RequestCount[] callRequestCounts) {
        this.callRequestCounts = callRequestCounts;
    }

    /**
     * @return the rejectedCallServiceRequestsStartTime
     */
    public Date getRejectedCallServiceRequestsStartTime() {
        return rejectedCallServiceRequestsStartTime;
    }

    /**
     * @param rejectedCallServiceRequestsStartTime the rejectedCallServiceRequestsStartTime to set
     */
    public void setRejectedCallServiceRequestsStartTime(
            Date rejectedCallServiceRequestsStartTime) {
        this.rejectedCallServiceRequestsStartTime = rejectedCallServiceRequestsStartTime;
    }

    /**
     * @return the totalRejectedCallServiceRequests
     */
    public int getTotalRejectedCallServiceRequests() {
        return totalRejectedCallServiceRequests;
    }

    /**
     * @param totalRejectedCallServiceRequests the totalRejectedCallServiceRequests to set
     */
    public void setTotalRejectedCallServiceRequests(int totalRejectedCallServiceRequests) {
        this.totalRejectedCallServiceRequests = totalRejectedCallServiceRequests;
    }

    /**
     * @return the numRejectedCallServiceRequestsLastMinute
     */
    public int getNumRejectedCallServiceRequestsLastMinute() {
        return numRejectedCallServiceRequestsLastMinute;
    }

    /**
     * @param numRejectedCallServiceRequestsLastMinute the numRejectedCallServiceRequestsLastMinute to set
     */
    public void setNumRejectedCallServiceRequestsLastMinute(
            int numRejectedCallServiceRequestsLastMinute) {
        this.numRejectedCallServiceRequestsLastMinute = numRejectedCallServiceRequestsLastMinute;
    }

    /**
     * @return the numRejectedCallServiceRequestsCurrentPartialMinute
     */
    public int getNumRejectedCallServiceRequestsCurrentPartialMinute() {
        return numRejectedCallServiceRequestsCurrentPartialMinute;
    }

    /**
     * @param numRejectedCallServiceRequestsCurrentPartialMinute the numRejectedCallServiceRequestsCurrentPartialMinute to set
     */
    public void setNumRejectedCallServiceRequestsCurrentPartialMinute(
            int numRejectedCallServiceRequestsCurrentPartialMinute) {
        this.numRejectedCallServiceRequestsCurrentPartialMinute = numRejectedCallServiceRequestsCurrentPartialMinute;
    }
    
}
