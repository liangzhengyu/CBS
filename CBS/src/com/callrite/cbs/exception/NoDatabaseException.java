/*
 * $Id$
 * 
 * Created on Dec 19, 2011
 *
 * Copyright © VoiceRite, Inc. 2011.  All Rights reserved.
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has been
 * deposited with the U.S. Copyright office.
 *
 */
package com.callrite.cbs.exception;

public class NoDatabaseException extends VIPException {
    
    /**
     * 
     */
    private static final long serialVersionUID = -2195639491039851614L;

    public NoDatabaseException()
    {
        super() ;
    }
    
    public NoDatabaseException(String msg)
    {
        super(msg) ;
    }
}
