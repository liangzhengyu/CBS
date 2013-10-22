package com.callrite.cbs.callactivity;

import com.callrite.cbs.event.CBSEventAdaptor;
import com.callrite.cbs.telephony.TelephonyRequest;
import com.callrite.cbs.telephony.TelephonyResponse;

/**
 * CBSHelper call activity event
 * @author JLiang
 *
 */
public class CBCCallActivityEvent extends CBSEventAdaptor {
    /**
     * Construct call activity event
     * @param callRequest
     */
    public CBCCallActivityEvent(TelephonyRequest request, TelephonyResponse response) {
        super(CALL_ACTIVITY, request, response, 10);
    }
}
