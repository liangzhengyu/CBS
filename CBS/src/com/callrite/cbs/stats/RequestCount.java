package com.callrite.cbs.stats;

/**
 * Request Counts
 * @author JLiang
 *
 */
public class RequestCount implements Comparable<RequestCount> {
    private String methodName ;
    private int numRequests ;
    
    public RequestCount(String methodName)
    {
        this.methodName = methodName ;
        numRequests = 0 ;
    }

    public RequestCount(String methodName, int numRequests)
    {
        this.methodName = methodName ;
        this.numRequests = numRequests ;
    }
    
    /**
     * Increment the number of requests by one
     */
    public void increment()
    {
        numRequests++ ;
    }
    
    /**
     * @return the methodName
     */
    public String getMethodName()
    {
        return methodName;
    }

    /**
     * @param methodName the methodName to set
     */
    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }

    /**
     * @return the numRequests
     */
    public int getNumRequests()
    {
        return numRequests;
    }

    /**
     * @param numRequests the numRequests to set
     */
    public void setNumRequests(int numRequests)
    {
        this.numRequests = numRequests;
    }

    public int compareTo(RequestCount rhs)
    {
        return this.methodName.compareTo(rhs.methodName) ;
    }
}
