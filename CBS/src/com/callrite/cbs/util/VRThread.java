package com.callrite.cbs.util;

/**
 * @author Derek
 *
 */
public class VRThread extends Thread 
{
    private static int numThreads = 0 ;
    
    private boolean isTerminated = false ;
    
    public VRThread() {
        numThreads++ ;
    }
    
    public void terminate() {
        isTerminated = true ;
        numThreads-- ;
    }
    
    public boolean terminated() {
        return isTerminated ;
    }
    
    public static int getNumThreads() {
        return numThreads ;
    }
}
