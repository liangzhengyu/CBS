package com.callrite.cbs;

import org.apache.log4j.Logger;

import com.callrite.cbs.callactivity.VIPCallActivityEvent;
import com.callrite.cbs.event.VIPEventManager;
import com.callrite.cbs.stats.StatisticsTracker;

/**
 * 
 * Provides VIP core functions
 * @author JLiang
 *
 */
public class VIP {
    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger("com.callrite.cbs.VIP") ;
    
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
        // TODO Auto-generated method stub
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
            return generateReturnCode(ReturnCode.SUCCESS, "" );
        }  finally {
           logger.debug("Request took [" + (System.currentTimeMillis() - startTime) + " ms]") ;            
        }
    }

    /**
     * Handle incoming call request
     * @param request
     * @return
     */
    public HandleCallResponse handleCall(CallRequest request) {
        //send call to events
        VIPCallActivityEvent event = new VIPCallActivityEvent(request);
        VIPEventManager.getInstance().fireVIPEvent(event);
        
        HandleCallResponse response = new HandleCallResponse();
        response.setReturnCode(generateSucessfulReturnCode());
        if ( event.getVIPEventResponseData() != null ) {
            response.setCallResponse((CallResponse)event.getVIPEventResponseData());
        }
        return response;
    }
}
