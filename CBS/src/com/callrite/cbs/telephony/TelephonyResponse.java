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
package com.callrite.cbs.telephony;

/**
 * This class contains the response to telephony request
 * @author JLiang
 *
 */
public class TelephonyResponse {
    private int result = SUCCESS;
    
    public final static int SUCCESS = 0;
    
    public final static int OTHER_FAILURE = -9999;
    
    /** Result text/description **/
    public String resultText;
    /** Result Data **/
    public Object resultData;
    
    /** Original response data **/
    private Object originalResponse;

    /**
     * Constructor
     * @param originalResponse
     */
    public TelephonyResponse(Object originalResponse) {
        super();
        this.originalResponse = originalResponse;
    }

    /**
     * @return the result
     */
    public int getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(int result) {
        this.result = result;
    }

    /**
     * @return the resultText
     */
    public String getResultText() {
        return resultText;
    }

    /**
     * @param resultText the resultText to set
     */
    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    /**
     * @return the resultData
     */
    public Object getResultData() {
        return resultData;
    }

    /**
     * @param resultData the resultData to set
     */
    public void setResultData(Object resultData) {
        this.resultData = resultData;
    }

    /**
     * @return the originalResponse
     */
    public Object getOriginalResponse() {
        return originalResponse;
    }

    /**
     * @param originalResponse the originalResponse to set
     */
    public void setOriginalResponse(Object originalResponse) {
        this.originalResponse = originalResponse;
    }
    
    
}
