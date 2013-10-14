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
