package com.callrite.cbs.telephony;

import java.util.Hashtable;

/**
 * This class contains the response to telephony request
 * @author JLiang
 *
 */
public class TelephonyResponse {
    private int result = SUCCESS;
    
    public final static int SUCCESS = 0;
    public final static int SUCCESS_NO_ACTION = 1;
    public final static int SUCCESS_NEXT_ACTION = 2;
    
    public final static int OTHER_FAILURE = -9999;
    
    /** Result text/description **/
    public String resultText;
    
    /** Result Data **/
    public Object resultData;
    
    /** response data **/
    private Object responseData;

    /** response options **/
    Hashtable<String, String> responseOptions = new Hashtable<String,String>();
    
    /**
     * Constructor
     */
    public TelephonyResponse() {
        super();
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
     * @return the responseData
     */
    public Object getResponseData() {
        return responseData;
    }

    /**
     * @param responseData the responseData to set
     */
    public void setResponseData(Object responseData) {
        this.responseData = responseData;
    }

    /**
     * @return the responseOptions
     */
    public Hashtable<String, String> getResponseOptions() {
        return responseOptions;
    }

    /**
     * @param responseOptions the responseOptions to set
     */
    public void setResponseOptions(Hashtable<String, String> responseOptions) {
        this.responseOptions = responseOptions;
    }
}
