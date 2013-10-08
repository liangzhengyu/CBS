/*
 * $Id: GetVersionResponse.java 3733 2011-11-11 19:35:37Z jliang $
 * 
 * Created on July 11, 2012
 *
 * Copyright © VoiceRite, Inc. 2012.  All Rights reserved.
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has been
 * deposited with the U.S. Copyright office.
 *
 */
package com.callrite.cbs;

/**
 * @author jliang
 * 
 */
public class GetVersionResponse
{
    private String version ;
    private String buildDate ;
    
    /**
     * Default constructor
     *
     */
    public GetVersionResponse()
    {
    }
    
    /**
     * @return Returns the buildDate.
     */
    public String getBuildDate()
    {
        return buildDate;
    }
    /**
     * @param buildDate The buildDate to set.
     */
    public void setBuildDate(String buildDate)
    {
        this.buildDate = buildDate;
    }
    /**
     * @return Returns the version.
     */
    public String getVersion()
    {
        return version;
    }
    /**
     * @param version The version to set.
     */
    public void setVersion(String version)
    {
        this.version = version;
    }
}
