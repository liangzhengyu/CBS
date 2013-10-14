package com.callrite.cbs;

import java.io.Serializable;

/**
 * This class defines the incoming call request from Verizon
 * @author JLiang
 *
 */
public class CallRequest implements Serializable {
    /**
     * Default serial version UID
     */
    private static final long serialVersionUID = 1L;

    /** The unix/POSIX timestamp that when the call request generated **/
    private long timestamp;
    
    /** Unique identifier for this call **/
    private String callID; 
    
    /** Automatic Number identification, the phone number of the party that initiated the call. */
    private String ANI;
    
    /** The caller name if the carrier can pass this information*/
    private String callerName;
    
    /** Dialed Number Identifier Service, the called number **/
    private String DNIS;
    
    /** The value is only valid if the call is a forwarded call. Not all carriers pass this information. */
    private String forwardFrom;
    
    /** The source that identifier how the call is started, for example, "phone", "vip" */
    private String source = SOURCE_PHONE;
    /** available sources **/
    public final static String SOURCE_PHONE = "phone";
    public final static String SOURCE_VIP = "VIP";
    
    /** Call Status **/
    private int status = STATUS_NOT_STARTED;
    
    /** available call status **/
    public final static int STATUS_NOT_STARTED = 0;
    public final static int STATUS_INIT = 1;
    public final static int STATUS_STARTED = 2;
    public final static int STATUS_ACTIVE = 3;
    public final static int STATUS_HOLD = 4;
    public final static int STATUS_RESUME = 5;
    public final static int STATUS_ANSWERED_BY_SOMENE = 6;
    public final static int STATUS_HANGUP = 7;
    public final static int STATUS_COMPLETED = 10;
    public final static int STATUS_BUSY = 20;
    public final static int STATUS_ERROR = 100;
    
    /** Direction of the call **/
    private int direction = DIRECTION_UNKNOWN;
    
    /** available call direction **/
    public final static int DIRECTION_UNKNOWN = 0;
    public final static int DIRECTION_INBOUND = 1;
    public final static int DIRECTION_OUTBOUND = 2;
    
    /** the cause of the call completion **/
    private int disposition = DISPOSITION_UNKNOWN;
    
    /** available dispositions **/
    public final static int DISPOSITION_UNKNOWN = 0;
    public final static int DISPOSITION_CALLER_HANGUP = 1;
    public final static int DISPOSITION_CALLEE_HANGUP = 2;
    public final static int DISPOSITION_SYSTEM_HANGUP = 3;
    public final static int DISPOSITION_ERROR_HANGUP = 4;
    
    /** Unique identifiers which identify the active call legs. The legs are sorted by the time the leg initialed. */
    private String[] legIDs;

    /** Status associated Leg ID **/
    private String statusAssociatedLegID;
    
    /** The unique identifier which identify the outbound request by VIP **/
    private String outboundRequestID;
    
    /** supplemental data, for implementation specific data. For example, plivo "schedulehangupid" **/
    private String[] supplementalData;

    /** true if need to wait for response **/
    private boolean waitingForResponse = false;
    
    /**
     * Constructor
     */
    public CallRequest() {
        super();
        timestamp = System.currentTimeMillis();
    }

    /**
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the callID
     */
    public String getCallID() {
        return callID;
    }

    /**
     * @param callID the callID to set
     */
    public void setCallID(String callID) {
        this.callID = callID;
    }

    /**
     * @return the aNI
     */
    public String getANI() {
        return ANI;
    }

    /**
     * @param aNI the aNI to set
     */
    public void setANI(String aNI) {
        ANI = aNI;
    }

    /**
     * @return the callerName
     */
    public String getCallerName() {
        return callerName;
    }

    /**
     * @param callerName the callerName to set
     */
    public void setCallerName(String callerName) {
        this.callerName = callerName;
    }

    /**
     * @return the dNIS
     */
    public String getDNIS() {
        return DNIS;
    }

    /**
     * @param dNIS the dNIS to set
     */
    public void setDNIS(String dNIS) {
        DNIS = dNIS;
    }

    /**
     * @return the forwardFrom
     */
    public String getForwardFrom() {
        return forwardFrom;
    }

    /**
     * @param forwardFrom the forwardFrom to set
     */
    public void setForwardFrom(String forwardFrom) {
        this.forwardFrom = forwardFrom;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * @return the disposition
     */
    public int getDisposition() {
        return disposition;
    }

    /**
     * @param disposition the disposition to set
     */
    public void setDisposition(int disposition) {
        this.disposition = disposition;
    }

    /**
     * @return the legIDs
     */
    public String[] getLegIDs() {
        return legIDs;
    }

    /**
     * @param legIDs the legIDs to set
     */
    public void setLegIDs(String[] legIDs) {
        this.legIDs = legIDs;
    }

    /**
     * @return the statusAssociatedLegID
     */
    public String getStatusAssociatedLegID() {
        return statusAssociatedLegID;
    }

    /**
     * @param statusAssociatedLegID the statusAssociatedLegID to set
     */
    public void setStatusAssociatedLegID(String statusAssociatedLegID) {
        this.statusAssociatedLegID = statusAssociatedLegID;
    }

    /**
     * @return the outboundRequestID
     */
    public String getOutboundRequestID() {
        return outboundRequestID;
    }

    /**
     * @param outboundRequestID the outboundRequestID to set
     */
    public void setOutboundRequestID(String outboundRequestID) {
        this.outboundRequestID = outboundRequestID;
    }

    /**
     * @return the waitingForResponse
     */
    public boolean isWaitingForResponse() {
        return waitingForResponse;
    }

    /**
     * @param waitingForResponse the waitingForResponse to set
     */
    public void setWaitingForResponse(boolean waitingForResponse) {
        this.waitingForResponse = waitingForResponse;
    }

    /**
     * @return the supplementalData
     */
    public String[] getSupplementalData() {
        return supplementalData;
    }

    /**
     * @param supplementalData the supplementalData to set
     */
    public void setSupplementalData(String[] supplementalData) {
        this.supplementalData = supplementalData;
    }
    
  
}
