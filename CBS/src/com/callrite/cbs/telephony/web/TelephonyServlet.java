package com.callrite.cbs.telephony.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.callrite.cbs.CBSHelper;
import com.callrite.cbs.ReturnCode;
import com.callrite.cbs.exception.CBSException;
import com.callrite.cbs.exception.NotFoundException;
import com.callrite.cbs.telephony.TelephonyRequest;
import com.callrite.cbs.telephony.TelephonyResponse;
import com.callrite.cbs.telephony.TelephonyService;
import com.callrite.cbs.telephony.TelephonyServiceFactory;

/**
 * 
 * Handle the call requests via HTTP requests and return the responses.  The end point would be as follows:
 * 
 *     http://<host>/CBS/telephony/[source]/[protocol]/[action]/[callflow]/[version]
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
            CBSHelper cbsService = new CBSHelper();
            ReturnCode returnCode = cbsService.locateTelephonyService(source, request.getRemoteAddr(), "", "");
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
                    TelephonyRequest telephonyRequest = new TelephonyRequest( TelephonyRequest.HTTP, protocol, source, request.getRemoteAddr(),
                            args.length>2?args[2]:null, args.length>3?args[3]:null, args.length>4?args[4]:null, 
                            request);
                    TelephonyResponse telephonyResponse = service.process(telephonyRequest);
                    
                    //call CBSHelper to handle the call request
                    cbsService.handleCallActivity(telephonyRequest, telephonyResponse);
                    
                    if ( telephonyResponse.getResult() == TelephonyResponse.SUCCESS ) {
                        //send back response
                        logger.debug(String.format("Send back result [%s]", telephonyResponse.getResultData().toString()));
                        response.getOutputStream().println(telephonyResponse.getResultData().toString());
                    } else if ( telephonyResponse.getResult() == TelephonyResponse.SUCCESS_NO_ACTION ) {
                        logger.debug("No action required");
                    } else {
                        logger.error(String.format("Failed to process with error [%d] and description [%s]", telephonyResponse.getResult(), telephonyResponse.getResultText()));
                        internalError(response) ;
                    }
                    
                } catch (NotFoundException exp) {
                    logger.warn(exp.getMessage()) ;            
                    notFound(response) ;
                    return ;
                } catch (CBSException exp) {
                    logger.warn(exp.getMessage()) ;            
                    internalError(response) ;
                    return ;
                }
            }
        } 
    }    
}
