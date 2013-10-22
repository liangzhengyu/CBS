package com.callrite.cbs.exception;

/**
 * The base exception for CBSHelper project
 * @author JLiang
 *
 */
public class CBSException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1;

    public CBSException()
    {
        super() ;
    }
    
    public CBSException(String msg)
    {
        super(msg) ;
    }
        
}
