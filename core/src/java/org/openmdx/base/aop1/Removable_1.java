/*
 * ====================================================================
 * Project:     openMDX, http://www.openmdx.org/
 * Name:        $Id: Removable_1.java,v 1.4 2009/05/16 22:17:47 wfro Exp $
 * Description: Removable_1 
 * Revision:    $Revision: 1.4 $
 * Owner:       OMEX AG, Switzerland, http://www.omex.ch
 * Date:        $Date: 2009/05/16 22:17:47 $
 * ====================================================================
 *
 * This software is published under the BSD license as listed below.
 * 
 * Copyright (c) 2009, OMEX AG, Switzerland
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

package org.openmdx.base.aop1;

import static org.openmdx.base.accessor.cci.SystemAttributes.REMOVED_AT;
import static org.openmdx.base.accessor.cci.SystemAttributes.REMOVED_BY;

import java.util.Date;

import org.openmdx.base.accessor.cci.DataObject_1_0;
import org.openmdx.base.accessor.cci.SystemAttributes;
import org.openmdx.base.accessor.view.ObjectView_1_0;
import org.openmdx.base.accessor.view.PlugIn_1;
import org.openmdx.base.exception.ServiceException;
import org.openmdx.kernel.exception.BasicException;

/**
 * Removable
 */
public class Removable_1
    extends PlugIn_1
{

    /**
     * Constructor 
     *
     * @param self
     * @param next
     */
    public Removable_1(
        ObjectView_1_0 self, 
        PlugIn_1 next
    ) {
        super(self, next);
    }

    /**
     * The future place holder has actually the value <code>10000-01-01T00:00:00.000Z</code>.
     */
    public static final Date IN_THE_FUTURE = new Date(253402300800000l);

    /* (non-Javadoc)
     * @see org.openmdx.base.accessor.view.PlugIn_1#objDelete()
     */
    @Override
    public void objDelete(
    ) throws ServiceException {
        if(super.jdoIsNew()){
            super.objDelete();
        } else {
            super.objSetValue(
                REMOVED_AT,
                IN_THE_FUTURE
            );
        }
    }

    /* (non-Javadoc)
     * @see org.openmdx.base.accessor.spi.DelegatingObject_1#objSetValue(java.lang.String, java.lang.Object)
     */
    @Override
    public void objSetValue(
        String feature, 
        Object to
    ) throws ServiceException {
        if(REMOVED_AT.equals(feature) || REMOVED_BY.equals(feature)) {
            throw new ServiceException(
                BasicException.Code.DEFAULT_DOMAIN,
                BasicException.Code.NOT_SUPPORTED,
                "A Removables's removal attributes are not modifiable",
                new BasicException.Parameter("id", jdoGetObjectId()),
                new BasicException.Parameter("feature", feature),
                new BasicException.Parameter("to", to)
            );
        }
        super.objSetValue(feature, to);
    } 
    
    @Override
    public boolean objIsRemoved(
    ) throws ServiceException { 
        DataObject_1_0 dataObject = this.self.objGetDelegate();
        return !dataObject.jdoIsDeleted() && IN_THE_FUTURE.equals(
            dataObject.objGetValue(SystemAttributes.REMOVED_AT)
         );   
    }
    
}
