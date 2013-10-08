/*
 * $Id: VIPVersion.java 4709 2011-12-16 17:36:43Z jliang $
 * 
 * Created on July 16, 2012
 *
 * Copyright © VoiceRite, Inc. 2012.  All Rights reserved.
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has been
 * deposited with the U.S. Copyright office.
 *
 */
package com.callrite.cbs;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * @author Jliang
 * 
 * This is a stand WebService that all VoiceRite products will expose in order to allow external parties
 * to get the product's version info
 */
public class VIPVersion
{
    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger( VIPVersion.class );
            

    /**
     * Get the product version
     * 
     * @return
     */
    public GetVersionResponse getVersion()
    {
        GetVersionResponse response = new GetVersionResponse() ;
        
        try {
            ResourceBundle properties = ResourceBundle.getBundle("vip-version") ;
            
            String version = properties.getString("Version") ;
            if (version == null || version.length() <= 0) {
                version = "UNKNOWN" ;
            }
            response.setVersion( version );
    
            String buildDate = properties.getString("BuildDate") ;
            if (buildDate == null || buildDate.length() <= 0) {
                buildDate = "UNKNOWN" ;
            }
            response.setBuildDate(buildDate);
            
        } catch (Exception e) {
            logger.error("Version: Exception: " + e.toString()) ;
        }
        
        return response ;
    }
}
