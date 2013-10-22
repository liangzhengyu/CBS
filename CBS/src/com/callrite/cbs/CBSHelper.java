package com.callrite.cbs;

import org.apache.log4j.Logger;

import com.callrite.cbs.callactivity.CBCCallActivityEvent;
import com.callrite.cbs.event.CBSEventManager;
import com.callrite.cbs.stats.StatisticsTracker;
import com.callrite.cbs.telephony.TelephonyRequest;
import com.callrite.cbs.telephony.TelephonyResponse;

/**
 * 
 * Provides CBSHelper core functions
 * @author JLiang
 *
 */
public class CBSHelper {
    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger("com.callrite.cbs.CBSHelper") ;
    
    /**
     * Geneate return code
     * @param resultCode
     * @param resultText
     * @param resultData
     * @return
     */
    private ReturnCode generateReturnCode( int resultCode, String resultText, String resultData ) {
        ReturnCode returnCode = new ReturnCode();
        returnCode.setResult(resultCode);
        returnCode.setResultText(resultText);
        returnCode.setResultData(resultData);
        return returnCode;
    }
    
    /**
     * Generate return code without return data
     * @param resultCode
     * @param resultText
     * @return
     */
    private ReturnCode generateReturnCode( int resultCode, String resultText) {
        return generateReturnCode(resultCode, resultText, "");
    }
    
    /**
     * Generate return code without return data
     * @return
     */
    private ReturnCode generateSucessfulReturnCode( ) {
        return generateReturnCode(ReturnCode.SUCCESS, "");
    }
    
    /**
     * Get alternative server to handle overloaded requests
     * @return null if no alternative server is available
     */
    private String getTelephonyAlternativeServer() {
       return null;
    }
    
    /**
     * Locate telephony service
     * @param sourceName telephony source name
     * @param ipAddress IP Address
     * @param username user name
     * @param password password
     * @return
     */
    public ReturnCode locateTelephonyService( String sourceName, String ipAddress, String username, String password ) {
        StatisticsTracker.trackCallService(sourceName) ;
        
        if (StatisticsTracker.isCallServiceOverloaded() ) {
            StatisticsTracker.trackRejectedCallService();
            return generateReturnCode(ReturnCode.FAILED_DUE_TO_LOAD, 
                    "Call service is overloaded right now.", 
                    getTelephonyAlternativeServer()) ;
        }
        
        long startTime = System.currentTimeMillis() ;
        
        try {
            return generateSucessfulReturnCode();
        }  finally {
           logger.debug("Request took [" + (System.currentTimeMillis() - startTime) + " ms]") ;            
        }
    }

    /**
     * Handle incoming call request
     * @param request
     * @return
     */
    public void handleCallActivity(TelephonyRequest request, TelephonyResponse response) {
        //send call to events
        CBCCallActivityEvent event = new CBCCallActivityEvent(request, response);
        CBSEventManager.getInstance().fireCBSEvent(event);
    }
}
