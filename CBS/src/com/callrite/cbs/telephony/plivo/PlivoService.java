package com.callrite.cbs.telephony.plivo;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.LRUMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.callrite.cbs.exception.CBSException;
import com.callrite.cbs.telephony.TelephonyRequest;
import com.callrite.cbs.telephony.TelephonyResponse;
import com.callrite.cbs.telephony.TelephonyService;
import com.callrite.cbs.telephony.plivo.callflow.ICallFlowHandler;
import com.callrite.cbs.util.Config;

public class PlivoService implements TelephonyService {
    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger(PlivoService.class);
    
    /**
     * For POC only, save today's telephony request
     */
    private static Hashtable<String,Vector<PlivoCall>> todayCalls = new Hashtable<String,Vector<PlivoCall>>();
    private static String todayTimestamp = null;
    
    /**
     * Call Map
     */
    private static LRUMap callMap = new LRUMap(Config.getInstance().getSetting("Maximum.Calls", 100));
    
    /* (non-Javadoc)
     * @see com.callrite.cbs.telephony.TelephonyService#process(com.callrite.cbs.telephony.TelephonyRequest)
     */
    public TelephonyResponse process(TelephonyRequest request)
            throws CBSException {
        String callID = getCallID(request);
        if ( StringUtils.isEmpty(callID) ) {
            logger.error("Call ID is empty");
            return new PlivoCallResponse(PlivoCallResponse.CALL_ID_MISSING, "Call ID is empty");
        }
        
        PlivoCall call = null;
        if ( request.getAction().compareTo(TelephonyRequest.INCOMING) != 0 ) {
            if (! callMap.containsKey(callID) ) {
                logger.error(String.format("Call ID [%s] not valid", callID));
                return new PlivoCallResponse(PlivoCallResponse.CALL_ID_INVALID, String.format("Call ID [%s] not valid", callID));
            }
        } else {
            processCallFlow(request); //determine call flow
        }
        
        ICallFlowHandler handler = getCallFlowHandler(request);
        if ( handler == null ) {
            logger.error(String.format("No handler for Call flow [%s] action [%s]", request.getCallFlow(), request.getAction()));
            return new PlivoCallResponse(PlivoCallResponse.CALL_FLOW_INVALID, String.format("No handler for Call flow [%s] action [%s]", request.getCallFlow(), request.getAction()));
        }
        PlivoCallResponse response = handler.process(call, request);
                
        if ( response.getCall() != null ) {
            synchronized(todayCalls) {
                if ( todayCalls.containsKey(response.getCall().getCallID()) ) {
                    todayCalls.get(response.getCall().getCallID()).add(response.getCall());
                } else {
                    SimpleDateFormat sm = new SimpleDateFormat("MMddyy");
                    if ( todayTimestamp == null || todayTimestamp.equals(sm.format(new Date(response.getCall().getTimestamp()))) == false ) {
                        todayTimestamp = sm.format(new Date(response.getCall().getTimestamp()));
                        todayCalls.clear();
                    }   
                    Vector<PlivoCall> callList = new Vector<PlivoCall>();
                    callList.add(response.getCall());
                    todayCalls.put(response.getCall().getCallID(), callList);
                }    
            }    
        }
        
        return response;
        
    }

    /**
     * Determine call flow here
     * @param request
     */
    private void processCallFlow(TelephonyRequest request) {
        // TODO: code to process call flow
    }

    /**
     * Get call flow handler
     * @param request
     * @return
     */
    private ICallFlowHandler getCallFlowHandler(TelephonyRequest request) {
        String handlerClassName = String.format("com.callrite.cbs.telephony.plivo.callflow.%s%s.%sHandler", request.getCallFlow(), request.getVersion(), request.getAction());
        
        Object obj;
        try {
            obj = Class.forName(handlerClassName).newInstance();
            if ( obj instanceof ICallFlowHandler) {
                return (ICallFlowHandler) obj;
            }
            logger.error(String.format("Class [%s] is not a handler", handlerClassName));

        } catch (InstantiationException e) {
            logger.error(String.format("Failed to create call flow handler class [%s]", handlerClassName), e);
        } catch (IllegalAccessException e) {
            logger.error(String.format("Failed to create call flow handler class [%s]", handlerClassName), e);
        } catch (ClassNotFoundException e) {
            logger.error(String.format("Failed to create call flow handler class [%s]", handlerClassName), e);
        }
        return null;
    }

    /**
     * Get call ID
     * @param request
     * @return
     * @throws CBSException
     */
    private String getCallID(TelephonyRequest request) throws CBSException {
        HttpServletRequest httpRequest = (HttpServletRequest) request.getOriginalRequest();
        if ( httpRequest.getParameterMap().containsKey(PlivoCall.CALL_ID) ) {
            return httpRequest.getParameter(PlivoCall.CALL_ID);
        }
        return null;
    }
    
    /**
     * Get today call requests
     * @return
     */
    public static Vector<PlivoCall[]> getTodayCalls() {
        Vector<PlivoCall[]> calls = new Vector<PlivoCall[]>();
        
        synchronized(todayCalls) {
            for ( Vector<PlivoCall> requests: todayCalls.values() ) {
                PlivoCall[] retRequests = requests.toArray(new PlivoCall[requests.size()]);
                calls.add(retRequests);
            }
        }
        
        //sort by time
        Collections.sort(calls, new Comparator<PlivoCall[]>() {
            public int compare(PlivoCall[] request1, PlivoCall[] request2) {
                if ( request1[0].getTimestamp() > request2[0].getTimestamp() ) {
                    return -1;
                } else if ( request1[0].getTimestamp() < request2[0].getTimestamp() ) {
                    return 1;
                }
                return 0;
            }
        });
        
        return calls;
    }
}
