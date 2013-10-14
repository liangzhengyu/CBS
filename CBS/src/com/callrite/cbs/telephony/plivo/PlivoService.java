package com.callrite.cbs.telephony.plivo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.callrite.cbs.CallRequest;
import com.callrite.cbs.CallResponse;
import com.callrite.cbs.exception.VIPException;
import com.callrite.cbs.telephony.TelephonyRequest;
import com.callrite.cbs.telephony.TelephonyResponse;
import com.callrite.cbs.telephony.TelephonyService;
import com.callrite.cbs.util.Config;

public class PlivoService implements TelephonyService {
    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger(PlivoService.class);
    
    /**
     * For POC only, save today's telephony request
     */
    private static Hashtable<String,Vector<CallRequest>> todayCalls = new Hashtable<String,Vector<CallRequest>>();
    private static String todayTimestamp = null;
    
    public CallRequest processRequest(TelephonyRequest request,
            TelephonyResponse response) throws VIPException {
        HttpServletRequest httpRequest = (HttpServletRequest) request.getOriginalRequest();
        logger.debug("Process Request");
        
        //print out parameters
        CallRequest callRequest = new CallRequest();
        Enumeration parameterNames = httpRequest.getParameterNames();
        Hashtable<String, String> legIDs = new Hashtable<String, String>();
        String associatedLegName = null;
        while ( parameterNames.hasMoreElements() ) {
            String name = (String) parameterNames.nextElement();
            String value = httpRequest.getParameter(name);
            logger.debug("Parameter [" + name + "] value is [" + httpRequest.getParameter(name) + "]");
            if ( name == null ) {
                continue;
            }
            if ( name.equalsIgnoreCase("CallUUID") ) {
                callRequest.setCallID(value);
            } else if ( name.equalsIgnoreCase("CallerName") ) {
                callRequest.setCallerName(value);
            } else if ( name.equalsIgnoreCase("To") ) {
                callRequest.setDNIS(value);
            } else if ( name.equalsIgnoreCase("From") ) {
                callRequest.setANI(value);
            } else if ( name.equalsIgnoreCase("Direction") ) {
                if ( value != null && value.equalsIgnoreCase("outbound") ) {
                    callRequest.setDirection(CallRequest.DIRECTION_OUTBOUND);
                } else {
                    callRequest.setDirection(CallRequest.DIRECTION_INBOUND);
                }
            } else if ( name.equalsIgnoreCase("CallStatus") ) {
                if ( value.equalsIgnoreCase("ringing") ) {
                    callRequest.setStatus(CallRequest.STATUS_STARTED);
                    callRequest.setWaitingForResponse(true);
                } else if ( value.equalsIgnoreCase("in-progress") ) {
                    callRequest.setStatus(CallRequest.STATUS_ACTIVE );
                }  else if ( value.equalsIgnoreCase("completed") ) {
                    callRequest.setStatus(CallRequest.STATUS_COMPLETED );
                }  
            } else if ( Pattern.matches("Dial[a-zA-Z]LegUUID", name) ) {
                legIDs.put(name, value);
            } else if ( Pattern.matches("Dial[a-zA-Z]LegStatus", name) ) {
                associatedLegName = name;
                if ( value.equalsIgnoreCase("hangup") ) {
                    callRequest.setStatus(CallRequest.STATUS_HANGUP);
                    if ( name.equalsIgnoreCase("DialALegStatus")) {
                        callRequest.setDisposition(CallRequest.DISPOSITION_CALLER_HANGUP);
                    } else {
                        callRequest.setDisposition(CallRequest.DISPOSITION_CALLEE_HANGUP);
                    }
                } else if ( value.equalsIgnoreCase("answer") ) {
                    callRequest.setStatus(CallRequest.STATUS_ACTIVE );
                }  
            }
        }
        if ( legIDs.size() > 0 ) {
            Collection<String> ids = legIDs.values();
            String[] idsArr = ids.toArray(new String[ids.size()]);
            Arrays.sort(idsArr);
            callRequest.setLegIDs(idsArr);
            if ( StringUtils.isEmpty(associatedLegName) == false && legIDs.containsKey(associatedLegName) ) {
                callRequest.setStatusAssociatedLegID(legIDs.get(associatedLegName));
            }
        }
        
        if ( callRequest.getCallID() != null ) {
            synchronized(todayCalls) {
                if ( todayCalls.containsKey(callRequest.getCallID() ) ) {
                    todayCalls.get(callRequest.getCallID()).add(callRequest);
                } else {
                    SimpleDateFormat sm = new SimpleDateFormat("MMddyy");
                    if ( todayTimestamp == null || todayTimestamp.equals(sm.format(new Date(callRequest.getTimestamp()))) == false ) {
                        todayTimestamp = sm.format(new Date(callRequest.getTimestamp()));
                        todayCalls.clear();
                    }   
                    Vector<CallRequest> callList = new Vector<CallRequest>();
                    callList.add(callRequest);
                    todayCalls.put(callRequest.getCallID(), callList);
                }    
            }    
        }
        
        return callRequest;
    }

    /**
     * Send out Plivo RESTXML response
     */
    public void processResponse(TelephonyRequest request, TelephonyResponse response, CallRequest callRequest, CallResponse callResponse)
            throws VIPException {
        logger.debug("Process Response");
        HttpServletResponse httpResponse = (HttpServletResponse) response.getOriginalResponse();
        
        try {
            StringBuffer responseBuffer = new StringBuffer();
            responseBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
            responseBuffer.append("<Response>\n");
            if ( callRequest.getStatus() == CallRequest.STATUS_STARTED ) {
                responseBuffer.append(Config.getInstance(Config.TELEPHONY).getSetting("PlivoPOCResponse", "")+"\n") ;
            }
            responseBuffer.append("</Response>");
            logger.debug("Response: " + responseBuffer.toString());
            httpResponse.getOutputStream().println(responseBuffer.toString());
        } catch (IOException e) {
            throw new VIPException(e.toString());
        }
        
    }

    /**
     * Get today call requests
     * @return
     */
    public static Vector<CallRequest[]> getTodayCalls() {
        Vector<CallRequest[]> calls = new Vector<CallRequest[]>();
        
        synchronized(todayCalls) {
            for ( Vector<CallRequest> requests: todayCalls.values() ) {
                CallRequest[] retRequests = requests.toArray(new CallRequest[requests.size()]);
                calls.add(retRequests);
            }
        }
        
        //sort by time
        Collections.sort(calls, new Comparator<CallRequest[]>() {
            public int compare(CallRequest[] request1, CallRequest[] request2) {
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
