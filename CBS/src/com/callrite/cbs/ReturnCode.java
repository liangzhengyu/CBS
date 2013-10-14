package com.callrite.cbs;

import com.callrite.cbs.util.JSONHelper;



/**
 * @author jliang
 *
 */
public class ReturnCode 
{
    private int result ;
    private String resultText ;
    private String resultData;
    
    /** available result **/
    public static final int SUCCESS                             = 0 ;
    public static final int PARTIAL_SUCCESS                     = 1 ;
        
    
    public static final int FAILED_DUE_TO_LOAD                  = -9990 ;
    public static final int NOT_IMPLEMENTED_YET                 = -9991 ;
    public static final int AUTHENTICATION_FAILED               = -9997 ;
    public static final int MISSING_DATA                        = -9998 ;
    public static final int FAILURE                             = -9999 ;
    
    public ReturnCode() {   
    }
    
    /**
     * @return Returns the result.
     */
    public int getResult() 
    {
        return result;
    }
    
    /**
     * @param result The result to set.
     */
    public void setResult(int result) 
    {
        this.result = result;
    }
    
    /**
     * @return Returns the resultText.
     */
    public String getResultText() 
    {
        return resultText;
    }
    
    /**
     * @param resultText The resultText to set.
     */
    public void setResultText(String resultText) 
    {
        this.resultText = resultText;
    }
    
    /**
     * @return the resultData
     */
    public String getResultData() {
        return resultData;
    }

    /**
     * @param resultData the resultData to set
     */
    public void setResultData(String resultData) {
        this.resultData = resultData;
    }

    protected String toJSON()
    {
        return 
        "{" +
            "\"result\": " + result + "," +
            "\"resultText\": \"" + JSONHelper.JSONEscape(resultText) + "\"," +
            "\"resultData\": \"" + JSONHelper.JSONEscape(resultData) + "\"" +
        "}" ;        
    }
    
}