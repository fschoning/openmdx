/*
 * ====================================================================
 * Project:     openMDX, http://www.openmdx.org/
 * Name:        $Id: LenientPathComparator.java,v 1.4 2009/05/29 17:04:09 hburger Exp $
 * Description: Abstract Filter Class
 * Revision:    $Revision: 1.4 $
 * Owner:       OMEX AG, Switzerland, http://www.omex.ch
 * Date:        $Date: 2009/05/29 17:04:09 $
 * ====================================================================
 *
 * This software is published under the BSD license as listed below.
 * 
 * Copyright (c) 2004-2008, OMEX AG, Switzerland
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 * 
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * 
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.
 * 
 * * Neither the name of the openMDX team nor the names of its
 *   contributors may be used to endorse or promote products derived
 *   from this software without specific prior written permission.
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
 * This product includes software developed by other organizations as
 * listed in the NOTICE file.
 */
package org.openmdx.base.query;

import java.util.Comparator;

import org.openmdx.base.naming.Path;

/**
 * Allows comparison of not directly comparable classes
 */
public class LenientPathComparator extends LenientDatatypeComparator {

    /**
     * Factory for a LenientComparator using default CharSequence comparator
     * 
     * @return  a leinient comparator using the default CharSequence comparator
     */
    public static Comparator<Object> getInstance(
    ){
        return LenientPathComparator.instance;
    }

    /**
     * Use specific CharSequence camparator
     */
    public LenientPathComparator(
        Comparator<Object> charSequenceComparator
    ) {
        super(charSequenceComparator);
    }

    /**
     * 
     */
    private static final Comparator<Object> instance = new LenientPathComparator(null);
            
    
    //------------------------------------------------------------------------
    // Implements Comparator
    //------------------------------------------------------------------------

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(
        Object first, 
        Object second
    ) {
        if(first instanceof Path || second instanceof Path) try {
            return toPath(first).compareTo(toPath(second)); 
        } catch (Exception exception){
            return toUri(first).compareTo(toUri(second));
        } else {
            return super.compare(first, second);
        }
    }


    //------------------------------------------------------------------------
    // Type conversions
    //------------------------------------------------------------------------

    /**
     * 
     * @param number
     */
    private static Path toPath(
        Object value
    ){
        return value instanceof Path ? 
            (Path)value :
            new Path(value.toString());
    }
     
    /**
     * 
     * @param number
     */
    @SuppressWarnings("deprecation")
    private static String toUri(
        Object value
    ){
        return value instanceof Path ?
            ((Path)value).toUri() :
            value.toString();
    }

}
