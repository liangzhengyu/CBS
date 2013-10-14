package com.callrite.cbs.util;


import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author JLiang
 *
 * Some helper routines for dealing with JSON output
 */
public class JSONHelper
{
    /**
     * Escape certain characters within a string that must be escaped when added to a JSON structure
     * 
     * @param str
     * @return
     */
    public static String JSONEscape(String str)
    {
        if (str == null) {
            return null;
        }

        StringBuffer sb = new StringBuffer() ;
        
        for (int x=0; x<str.length(); x++) {
            char c = str.charAt(x) ;
            if (c == '"') {
                sb.append("\\\"") ;
            } else if (c == '\r') {
                sb.append("\\r") ;
            } else if (c == '\n') {
                sb.append("\\n") ;
            } else if (c == '\t') {
                sb.append("\\t") ;
            } else if (c == '\\') {
                sb.append("\\\\") ;
            } else {
                sb.append(c) ;
            }
        }
        
        return sb.toString();
    }
    
    /**
     * Convert an array of string to JSON
     * 
     * @param strings
     * @return
     */
    public static String toJSON(String [] strings)
    {
        StringBuffer sb = new StringBuffer() ;
        
        sb.append("[") ;
        
        if (strings != null) {
            for (int x=0; x<strings.length; x++) {
                if (x >0) {
                    sb.append(",") ;
                }
                sb.append("\"") ;
                sb.append(JSONEscape(strings[x])) ;
                sb.append("\"") ;
            }
        }
        
        sb.append("]") ;
        
        return sb.toString() ;
    }
    
    public static String toJSON(int [] ints)
    {
        StringBuffer sb = new StringBuffer() ;
        
        sb.append("[") ;
        
        if (ints != null) {
            for (int x=0; x<ints.length; x++) {
                if (x >0) {
                    sb.append(",") ;
                }
                sb.append("" + ints[x]) ;
            }
        }
        
        sb.append("]") ;
        
        return sb.toString() ;
    }    
    
    /**
     * Get a string field from the specified JSON object.  If the field doesn't exist, this will 
     * gracefully return a default value
     * 
     * @param o
     * @param fieldName
     * @param defaultValue Default value to return if the field doesn't exist or if there is a JSON problem
     * @return
     */
    public static String getField(JSONObject o, String fieldName, String defaultValue)
    {
        try {
            if (o.has(fieldName)) {
                return o.getString(fieldName) ;
            } else {
                return defaultValue ;
            }
        } catch (JSONException e) {
            return defaultValue ;
        }
    }
    
    /**
     * Get a string field from the specified JSON object.  If the field doesn't exist, this will 
     * gracefully return a default value
     * 
     * @param o
     * @param fieldName
     * @param defaultValue Default value to return if the field doesn't exist or if there is a JSON problem
     * @return
     */    
    public static int getField(JSONObject o, String fieldName, int defaultValue)
    {
        try {
            if (o.has(fieldName)) {
                return o.getInt(fieldName) ;
            } else {
                return defaultValue ;
            }
        } catch (JSONException e) {
            return defaultValue ;
        }
    }    
    
    public static boolean getField(JSONObject o, String fieldName, boolean defaultValue)
    {
        try {
            if (o.has(fieldName)) {
                return o.getBoolean(fieldName) ;
            } else {
                return defaultValue ;
            }
        } catch (JSONException e) {
            return defaultValue ;
        }
    }   
    
    public static byte [] getField(JSONObject o, String fieldName, byte [] defaultValue)
    {
        try {
            if (o.has(fieldName)) {
                String base64EncodedData = o.getString(fieldName) ;
                
                return Base64.decodeBase64(base64EncodedData.getBytes()) ;
                
            } else {
                return defaultValue ;
            }
        } catch (JSONException e) {
            return defaultValue ;
        }        
    }
    
    public static String [] getField(JSONObject o, String fieldName, String [] defaultValue)
    {
        try {
            if (o.has(fieldName)) {
                JSONArray array = o.getJSONArray(fieldName) ;
                
                String [] toReturn = new String[array.length()] ;
                
                for (int x=0; x<array.length(); x++) {
                    toReturn[x] = array.getString(x) ;
                }
                
                return toReturn ;
            } else {
                return defaultValue ;
            }
        } catch (JSONException e) {
            return defaultValue ;
        }        
    }
    
    public static void main(String [] args)
    {
        String s = "\"Derek Lee-Wo\"\r\nLine 2\r\n\tLine3" ;
        System.out.println(JSONEscape(s)) ;
        
    }
}

