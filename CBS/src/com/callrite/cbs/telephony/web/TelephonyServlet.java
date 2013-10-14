package com.callrite.cbs.telephony.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.callrite.cbs.CallRequest;
import com.callrite.cbs.HandleCallResponse;
import com.callrite.cbs.ReturnCode;
import com.callrite.cbs.VIP;
import com.callrite.cbs.exception.NotFoundException;
import com.callrite.cbs.exception.VIPException;
import com.callrite.cbs.telephony.TelephonyRequest;
import com.callrite.cbs.telephony.TelephonyResponse;
import com.callrite.cbs.telephony.TelephonyService;
import com.callrite.cbs.telephony.TelephonyServiceFactory;

/**
 * 
 * Handle the call requests via HTTP requests and return the responses.  The end point would be as follows:
 * 
 *     http://<host>/VIP/telephony/demo/plivo
 *     
 * The above example shows a call request from demo system with plivo service.
 * 
 * @author JLiang
 * 
 */
public class TelephonyServlet extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger("com.callrite.cbs.telephony.web.TelephonyServlet") ;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TelephonyServlet() {
        super();
    }
    
    /**
     * 
     * @param request
     * @param response
     * @throws ServeltException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
        doWork(request, response) ;
    }

    /**
     * 
     * @param request
     * @param response
     * @throws ServeltException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
        doWork(request, response) ;
    }

    /**
     * Not found response
     * @param response
     */
    private void notFound(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND) ;
    }
    
    /**
     * Not authorized response
     * @param response
     */
    private void notAuthorized(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED) ;
    }
    
    /**
     * Overloaded response
     * @param response
     */
    private void overload(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_REQUEST_TIMEOUT) ;
    }
    
    /**
     * Internal error, general errors
     * @param response
     */
    private void internalError(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR) ;
    }
    
    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    private void doWork(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
         * Get and parse the file info
         */
        String extraPath = request.getPathInfo() ;
        if (extraPath == null) {
            logger.warn("Received a call request without the required path info.  Programmer error or hacking attempt") ;            
            notFound(response) ;
            return ;
        } else {
            logger.debug("Path info=[" + extraPath + "]") ;
        }
        
        String [] args = extraPath.substring(1, extraPath.length()).split("/") ;
        if (args.length > 0 ) {
            String source = args[0] ;
            if ( StringUtils.isEmpty(source) ) {
                logger.warn("Received a call request without the source name.  Programmer error or hacking attempt") ;            
                notFound(response) ;
                return ;
            }
            
            logger.debug("SOURCE: " + source) ;
            
            //authenticate telephony request
            VIP vipService = new VIP();
            ReturnCode returnCode = vipService.locateTelephonyService(source, request.getRemoteAddr(), "", "");
            //handle return code 
            switch (returnCode.getResult()) {
                case ReturnCode.SUCCESS:
                    break;
                case ReturnCode.FAILED_DUE_TO_LOAD:
                    if (StringUtils.isEmpty(returnCode.getResultData()) ) {
                        overload(response) ;
                        return ;
                    } else {
                        logger.debug("Call service overloaded, redirect to alternative server [" + returnCode.getResultData() + "]") ;  
                        response.sendRedirect(returnCode.getResultData());
                        return;
                    }
                case ReturnCode.AUTHENTICATION_FAILED:
                    logger.warn("Call service request not authenthorized");
                    notAuthorized(response);
                    return;
                default:
                    logger.warn("Server internal error [" + returnCode.getResultText() + "]");   
                    internalError(response);
                    return;
            }
            
            String protocol = "plive" ; //default use plivo protocol
            if (args.length > 1 ) {
                protocol = args[1] ;
                if ( StringUtils.isEmpty(protocol) ) {
                    logger.warn("Received a call request without the protocol specified.  Programmer error or hacking attempt") ;            
                    notFound(response) ;
                    return ;
                }
                
                try {
                    //get call service handler
                    TelephonyService service = TelephonyServiceFactory.createTelephonyService(protocol);
                    TelephonyRequest telephonyRequest = new TelephonyRequest( TelephonyRequest.HTTP, protocol, source, request.getRemoteAddr(), request);
                    TelephonyResponse telephonyResponse = new TelephonyResponse(response);
                    CallRequest callRequest = service.processRequest( telephonyRequest, telephonyResponse );
                    
                    VIP vip = new VIP();
                    //call VIP to handle the call request
                    HandleCallResponse callResponse = vip.handleCall(callRequest);
                    
                    service.processResponse(telephonyRequest, telephonyResponse, callRequest, callResponse.getCallResponse());
                    
                } catch (NotFoundException exp) {
                    logger.warn(exp.getMessage()) ;            
                    notFound(response) ;
                    return ;
                } catch (VIPException exp) {
                    logger.warn(exp.getMessage()) ;            
                    internalError(response) ;
                    return ;
                }
            }
        } 
    }    
}
