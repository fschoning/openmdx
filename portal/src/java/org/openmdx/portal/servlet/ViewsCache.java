/*
 * ====================================================================
 * Project:     openMDX/Portal, http://www.openmdx.org/
 * Description: ViewsCache 
 * Owner:       OMEX AG, Switzerland, http://www.omex.ch
 * ====================================================================
 *
 * This software is published under the BSD license
 * as listed below.
 * 
 * Copyright (c) 2004-2008, OMEX AG, Switzerland
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 * 
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in
 * the documentation and/or other materials provided with the
 * distribution.
 * 
 * * Neither the name of the openMDX team nor the names of its
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * ------------------
 * 
 * This product includes software developed by the Apache Software
 * Foundation (http://www.apache.org/).
 *
 * This product includes yui, the Yahoo! UI Library
 * (License - based on BSD).
 *
 */
package org.openmdx.portal.servlet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.openmdx.portal.servlet.view.ObjectView;

/**
 * Holds the cached views stored per session. transient prevents serializing
 * of cached views.
 */
public class ViewsCache {

    //-----------------------------------------------------------------------
	private static class ExpiringView {
		
		public ExpiringView(
			ObjectView view
		) {
			this.objectView = view;
			this.lastAccessedAt = System.currentTimeMillis();
		}
		
		public final ObjectView objectView;
		public long lastAccessedAt;
	}
	
    //-----------------------------------------------------------------------
    public ViewsCache(
    	int timeoutInMinutes
    ) {
    	this.viewsTimeoutMillis = 60000L * (long)timeoutInMinutes;
    }

    //-----------------------------------------------------------------------
    public void clearViews(
        HttpSession session,
        long cachedSince          
    ) {
    	List<ExpiringView> views = new ArrayList<ExpiringView>(this.cache.values());
        this.cache.clear();
    	for(ExpiringView view: views) {
    		if(view.objectView != null) {
    			view.objectView.close();
    		}
    	}
        session.setAttribute(
            WebKeys.VIEW_CACHE_CACHED_SINCE, 
            new Long(cachedSince)
        );
    }

    //-----------------------------------------------------------------------
    public void removeDirtyViews(
    ) {
    	try {
    		long now = System.currentTimeMillis();
            for(
            	Iterator<Entry<String,ExpiringView>> i = this.cache.entrySet().iterator(); 
            	i.hasNext(); 
            ) {
            	Entry<String,ExpiringView> entry = i.next();
            	ExpiringView expiringView = entry.getValue();
                if(
                	(expiringView.objectView.getObjectReference().getObject() == null) ||
                	(expiringView.lastAccessedAt + this.viewsTimeoutMillis < now)
                ) {
                	if(expiringView.objectView != null) {
                		expiringView.objectView.close();
                	}
                    i.remove();
                }
            }
    	}
    	catch(Exception e) {} // ignore
    }

    //-----------------------------------------------------------------------
    public ObjectView getView(
        String requestId
    ) {          
    	if(requestId == null) return null;
    	ExpiringView view = this.cache.get(requestId);
    	if(view == null) {
    		return null;
    	}
    	view.lastAccessedAt = System.currentTimeMillis();
    	return view.objectView;
    }

    //-----------------------------------------------------------------------
    public void addView(
        String requestId,
        ObjectView view
    ) {
        this.cache.put(
            requestId, 
            new ExpiringView(view)
        );
    }

    //-----------------------------------------------------------------------
    public void removeView(
        String requestId
    ) {
        ExpiringView view = this.cache.remove(requestId);
        if(view != null && view.objectView != null) {
        	view.objectView.close();
        }
    }

    //-----------------------------------------------------------------------
    public boolean containsView(
        String requestId
    ) {
        return this.cache.containsKey(requestId);
    }

    //-----------------------------------------------------------------------
    // Members
    //-----------------------------------------------------------------------
    private final transient Map<String,ExpiringView> cache = new ConcurrentHashMap<String,ExpiringView>();
    private final long viewsTimeoutMillis;

}
