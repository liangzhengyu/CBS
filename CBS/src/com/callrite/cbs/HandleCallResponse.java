package com.callrite.cbs;

/**
 * Response when handling call request
 * @author JLiang
 *
 */
public class HandleCallResponse {
    private ReturnCode returnCode;
    private CallResponse callResponse;
    
    /**
     * Constructor
     */
    public HandleCallResponse() {
        super();
    }

    /**
     * @return the returnCode
     */
    public ReturnCode getReturnCode() {
        return returnCode;
    }

    /**
     * @param returnCode the returnCode to set
     */
    public void setReturnCode(ReturnCode returnCode) {
        this.returnCode = returnCode;
    }

    /**
     * @return the callResponse
     */
    public CallResponse getCallResponse() {
        return callResponse;
    }

    /**
     * @param callResponse the callResponse to set
     */
    public void setCallResponse(CallResponse callResponse) {
        this.callResponse = callResponse;
    }
    
    
}
