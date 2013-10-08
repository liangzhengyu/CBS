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
