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
