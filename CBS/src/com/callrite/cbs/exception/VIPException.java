/*
 * $Id$
 * 
 * Created on June 10, 2012
 *
 * Copyright © VoiceRite, Inc. 2011.  All Rights reserved.
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has been
 * deposited with the U.S. Copyright office.
 *
 */
package com.callrite.cbs.exception;

/**
 * The base exception for VIP project
 * @author JLiang
 *
 */
public class VIPException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1;

    public VIPException()
    {
        super() ;
    }
    
    public VIPException(String msg)
    {
        super(msg) ;
    }
        
}
