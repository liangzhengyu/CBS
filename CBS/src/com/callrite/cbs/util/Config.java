package com.callrite.cbs.util;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * Helper class for getting settings from the itaivr.properties files.  This uses SPring to make the properties
 * file reloadable so that changes made during runtime are detected.  They are not detected
 * immediately though and it could take up to 5 mins.  If this is not sufficient, you can adjust
 * the MAX_TIME_BETWEEN_RELOADS_SECS setting but keep in mind that the smaller this
 * value, the more frequently the properties file file is loaded.
 * 
 * @author JLiang
 * 
 */
public class Config {
    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger("com.cbs.VIP.util.Config") ;

    /**
     * The singleton instance
     */
    protected static Hashtable<String,Config> instances = new Hashtable<String,Config>() ;

    /**
     * The resource bundle
     */
    private ReloadableResourceBundleMessageSource properties = null ;
    
    /**
     * If the last time we reloaded is more than this time, then we will
     * reload
     */
    private static final int MAX_TIME_BETWEEN_RELOADS_SECS = 300 ;
    private long lastReloadTime ;
    
    /** Property Base Name **/
    private String baseName = VIP;
    
    /** available base names **/
    public final static String VIP = "CBSHelper";
    public final static String TELEPHONY = "Telephony";
    
    /**
     * constructor
     */
    protected Config(String baseName) {
        this.baseName = baseName;
        loadProperties() ;
    }

    /**
     * Get default instance
     * @return
     */
    public static Config getInstance() {
        return getInstance(VIP);
    }
    
    /**
     * Get instance
     * @return
     */
    public static Config getInstance(String baseName) {
        if ( instances.containsKey(baseName) ) {
            return instances.get(baseName);
        } else {
            Config newConfig = new Config(baseName);
            instances.put(baseName, newConfig);
            return newConfig;
        }
    }
    
    
    /**
     * Load the properties
     */
    private void loadProperties() {
        synchronized(this) {
            try {
                properties = new ReloadableResourceBundleMessageSource();
                properties.setBasename("classpath:" + baseName) ;
                
                logger.debug(baseName + ".properties loaded") ;
                lastReloadTime = System.currentTimeMillis() ;
                
            } catch (MissingResourceException e) {
                logger.error("Couldn't find CBSHelper.properties") ;
            } 
        }
    }
    
    private void checkIfReloadNecessary() {
        if (System.currentTimeMillis() - lastReloadTime > (MAX_TIME_BETWEEN_RELOADS_SECS * 1000)) {
            refresh() ;
        }
    }
    
    /**
     * Refresh properties
     *
     */
    public void refresh() {
        synchronized(this) {
            if ( properties == null ) {
                //if no properties, load the properties
                loadProperties();
            } else {
                //clear cache which results to refresh properties
                properties.clearCache();
            }
        }
    }
    
    /**
     * Force the properties file to be reloaded.  This can be used if you make a 
     * change and want the change to be picked up without restarting the application
     * server.  Please note that while this will reload the properties file, the
     * change you made may or may not necessarily take effect immediately.  Many
     * settings are loaded only at startup
     *
     */
    public static void reload() {
        for ( Config instance:instances.values() ) {
            instance.refresh();
        }
    }
    

    /**
     * Get a setting from the properties file
     * 
     * @param settingName
     * @param defaultValue
     * @return
     */
    public String getSetting(String settingName, String defaultValue) {       
        if ( properties != null) {
            try {
                checkIfReloadNecessary() ;
                
                String value =  properties.getMessage(settingName, new Object[]{}, Locale.getDefault() ) ;
                if (value == null) {
                    return defaultValue ;
                } else {
                    return value ;
                }
            } catch ( MissingResourceException exp ) {
                return defaultValue;
            } catch ( NoSuchMessageException exp ) {
                return defaultValue;
            }
        } else {
            return defaultValue ;
        }
    }

    /**
     * Get the value of a numeric setting
     * 
     * @param settingName
     * @param defaultValue
     * @return
     */
    public int getSetting(String settingName, int defaultValue)
    {
        String valueStr = getSetting(settingName, "" + defaultValue) ;
        
        try {
            return Integer.parseInt(valueStr) ;
        } catch (NumberFormatException e) {
            return defaultValue ;
        }
    }
    
    /**
     * Get the value of a boolean setting
     * 
     * @param settingName
     * @param defaultValue
     * @return
     */
    public boolean getSetting(String settingName, boolean defaultValue)
    {
        String valueStr = getSetting(settingName, defaultValue?"true":"false") ;
        
        if (valueStr.compareToIgnoreCase("true") == 0 || valueStr.compareToIgnoreCase("yes") == 0) {
            return true ;
        } else {
            return false ;
        }
    }
    

    /**
     * This will take a prefix string and return all the properties that start with that prefix.
     * For a property to match, it must start with the prefix specified followed by a '.'.
     * 
     * For example, suppose the properties file contains the following:
     * 
     *      service.username=
     *      service.password=
     *      service.url=
     *      serviceName.user=
     *
     * and you specify a prefix of "service", then only the first 3 properties would be returned.  Also,
     * the prefix is stripped off so the keys of the returned Properties object would be "username",
     * "password", and "url"
     * 
     * @param prefix
     * @return
     */
    public Properties getSubProperties(String prefix)
    {
        String searchPrefix = prefix + "." ;
        Properties foundProperties = new Properties() ;
        
        ResourceBundle rs = ResourceBundle.getBundle( "VCONS" );
        Enumeration<String> e = rs.getKeys() ;
        while (e.hasMoreElements()) {
            String key = e.nextElement() ;
            if (key.startsWith(searchPrefix)) {
                String subkey = key.substring(searchPrefix.length()) ;
                String value = getSetting(key, "") ;
                
                foundProperties.setProperty(subkey, value) ;
            }
            
        }
        
        return foundProperties ;
    }    
    
    /**
     * Get all the properties.
     * 
     * @param useDb if this is true, then we would include the database settings
     * @return
     */
    public Properties getAllSettings(boolean useDb)
    {
        Properties props = new Properties() ;
        
        // First get all the settings in the properties file
        ResourceBundle rs = ResourceBundle.getBundle( "VCONS" );
        Enumeration<String> e = rs.getKeys() ;
        while (e.hasMoreElements()) {
            String key = e.nextElement() ;
            String value = getSetting(key, "") ;
            props.setProperty(key, value) ;
        }
        
        return props ;
    }
        
}
