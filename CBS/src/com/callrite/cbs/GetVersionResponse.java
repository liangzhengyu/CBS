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
