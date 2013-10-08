/*
 * $Id$
 * 
 * Created on Jul 23, 2012
 *
 * Copyright © VoiceRite, Inc. 2011.  All Rights reserved.
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has been
 * deposited with the U.S. Copyright office.
 *
 */
package com.callrite.cbs.exception;

public class NotFoundException extends VIPException {
    
    /**
     * 
     */
    private static final long serialVersionUID = 8975886181501106485L;

    public NotFoundException()
    {
        super() ;
    }
    
    public NotFoundException(String msg)
    {
        super(msg) ;
    }
}