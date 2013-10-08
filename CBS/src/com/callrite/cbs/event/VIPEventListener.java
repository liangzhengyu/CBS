/*
 * $Id$
 * 
 * Created on Aug 9, 2012
 *
 * Copyright © VoiceRite, Inc. 2011.  All Rights reserved.
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has been
 * deposited with the U.S. Copyright office.
 *
 */
package com.callrite.cbs.event;

/**
 * VIP event listener interface
 * @author JLiang
 *
 */
public interface VIPEventListener {
    public void handleEvent(VIPEvent event);
}
