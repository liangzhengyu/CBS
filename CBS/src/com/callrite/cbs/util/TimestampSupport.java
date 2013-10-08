/*
 * $Id$
 * 
 * Created on Dec 19, 2011
 *
 * Copyright © VoiceRite, Inc. 2011.  All Rights reserved.
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has been
 * deposited with the U.S. Copyright office.
 *
 */
package com.callrite.cbs.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


/**
 * @author Derek
 *
 */
public class TimestampSupport
{
    /**
     * Get a timesamp string based on the specified Date object.
     * 
     * @param date
     * @return A timestamp string of the form "YYYYMMDDHHmmss" in
     * UTC time.  If the date is null, then an empty string is
     * returned.
     */
    public static String getTimestamp(Date date) {
        return getTimestamp( date, TimeZone.getTimeZone("UTC") );
    }
    
    public static String getTimestamp(Date date, TimeZone tz ) {
        if (date == null) {
            return "" ;
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss") ;
            formatter.setTimeZone(tz) ;
            String toReturn = formatter.format(date) ;
            return toReturn ;  
        }   
    }
    
    public static String getTimestampNoTzConversions(Date date) {
        if (date == null) {
            return "" ;
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss") ;
           return formatter.format(date) ;  
        }   
    }    
    
    /**
     * Get a timestamp string, but you can specify the format.  The format must be a
     * suitable input to the SimpleDateFormat() constructor.
     * 
     * @param date
     * @param format
     * @return
     */
    public static String getTimestamp(Date date, String format) {
        if (date == null) {
            return "" ;
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat(format) ;
           formatter.setTimeZone(TimeZone.getTimeZone("UTC")) ;
           return formatter.format(date) ;  
        }   
    }    
    
    /**
     * Get a timestamp with just the date component, e.g, "YYYYMMDD"
     * 
     * @param date
     * @return
     */
    public static String getDateOnlyTimestamp(Date date) {
        if (date == null)
            return "" ;
            
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd") ;
        formatter.setTimeZone(TimeZone.getTimeZone("UTC")) ;
        return formatter.format(date) ;     
    }   
    
    /**
     * Get a timestamp string based on the current date and time
     * 
     * @return A string representing the current date and time
     * in UTC.  The format is "yymmddhhmmss"
     */
    public static String getTimestamp() {
        return getTimestamp(new Date()) ;
    }

    /**
     * Get a timestamp string based on the current date and time
     * 
     * @return A string representing the current date and time
     * in UTC.  The format is "yymmddhhmmss"
     */
    public static String getTimestamp( TimeZone tz ) {
        return getTimestamp(new Date(), tz ) ;
    }

    /**
     * Take a timestamp string of the form "YYYYMMDDhhmmss" and in UTC
     * time and return a Date object.
     * 
     * @param timestamp
     * @return A Date object.  If for some reason, the timestamp cannot
     * be parsed, then the current date and time is returned.
     */
    public static Date getDate(String timestamp ) {
        return getDate( timestamp, TimeZone.getTimeZone("UTC") );
    }
    
    public static Date getDate(String timestamp, TimeZone tz) {
        if (timestamp == null || timestamp.length() <= 0)
            return new Date() ;
                        
        try {
            SimpleDateFormat sdf = null ;
            if (timestamp.length() == 8) {
                sdf = new SimpleDateFormat("yyyyMMdd") ;
            } else if (timestamp.length() == 4) {
                sdf = new SimpleDateFormat("HHmm") ;
            } else { 
                sdf = new SimpleDateFormat("yyyyMMddHHmmss") ;
            }
            sdf.setTimeZone(tz) ;
            Date t = sdf.parse(timestamp) ;
            if (t != null)
                return t ;
        } catch (ParseException e) {
        }

        // If we get here it's because we had a problem parsing the 
        // timestamp.  If so, we'll use the current date and time
        return new Date() ;     
    }
    
    public static Date getDateNoTzConversions(String timestamp) {
        if (timestamp == null || timestamp.length() <= 0)
            return new Date() ;
                        
        try {
            SimpleDateFormat sdf = null ;
            if (timestamp.length() == 8) {
                sdf = new SimpleDateFormat("yyyyMMdd") ;
            } else if (timestamp.length() == 4) {
                sdf = new SimpleDateFormat("HHmm") ;
            } else { 
                sdf = new SimpleDateFormat("yyyyMMddHHmmss") ;
            }
            
            Date t = sdf.parse(timestamp) ;
            if (t != null)
                return t ;
        } catch (ParseException e) {
        }

        // If we get here it's because we had a problem parsing the 
        // timestamp.  If so, we'll use the current date and time
        return new Date() ;     
    }    

    /**
     * Get the current date and time in UTC
     * 
     * @return
     */
    public static Date getUtcDate()
    {
        // Default to the current date and time in UTC
        String currentTimeUtc = TimestampSupport.getTimestamp() ;
        SimpleDateFormat dateParser = new SimpleDateFormat("yyyyMMddHHmmss") ;
        try {
            return dateParser.parse(currentTimeUtc) ;
        } catch (ParseException e) {
            return null ;
        } 
    }
    
    /*
     * Get the current date and time in the timezone 
     */
    public static Date getDate( TimeZone tz ) {
        //Default to the current date and time in UTC
        String currentTimeUtc = TimestampSupport.getTimestamp(tz) ;
        SimpleDateFormat dateParser = new SimpleDateFormat("yyyyMMddHHmmss") ;
        try {
            return dateParser.parse(currentTimeUtc) ;
        } catch (ParseException e) {
            return null ;
        } 
    }
    
    /**
     * Determine if the supplied year is a leap year
     * 
     * @param y
     * @return
     */
    public static boolean isLeapYear(int y)
    {
        return ( ((y % 400) == 0) || ( ((y % 4) == 0) && ((y % 100) != 0) ) );
    }
    
    public static Date getUtcDate(String timestamp) {
        if (timestamp == null || timestamp.length() <= 0)
            return new Date() ;
                        
        try {
            SimpleDateFormat sdf = null ;
            if (timestamp.length() == 8) {
                sdf = new SimpleDateFormat("yyyyMMdd") ;
            } else if (timestamp.length() == 4) {
                //add year and month so it could handle day saving time correctly
                Calendar cal = Calendar.getInstance();
                sdf = new SimpleDateFormat("yyyyMMdd");
                timestamp = sdf.format( cal.getTime() ) + timestamp;
                sdf = new SimpleDateFormat("yyyyMMddHHmm") ;
            } else { 
                sdf = new SimpleDateFormat("yyyyMMddHHmmss") ;
            }
            sdf.setTimeZone(TimeZone.getTimeZone("UTC")) ;
            Date t = sdf.parse(timestamp) ;
            if (t != null)
                return t ;
        } catch (ParseException e) {
        }

        // If we get here it's because we had a problem parsing the 
        // timestamp.  If so, we'll use the current date and time
        return new Date() ;     
    }
}

