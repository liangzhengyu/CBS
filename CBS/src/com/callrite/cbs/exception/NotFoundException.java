package com.callrite.cbs.exception;

public class NotFoundException extends CBSException {
    
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