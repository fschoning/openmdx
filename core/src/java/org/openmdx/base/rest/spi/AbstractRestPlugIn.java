/*
 * ====================================================================
 * Project:     openMDX/Core, http://www.openmdx.org/
 * Name:        $Id: AbstractRestPlugIn.java,v 1.1 2009/05/20 15:13:42 hburger Exp $
 * Description: Abstract REST Plug-In
 * Revision:    $Revision: 1.1 $
 * Owner:       OMEX AG, Switzerland, http://www.omex.ch
 * Date:        $Date: 2009/05/20 15:13:42 $
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
package org.openmdx.base.rest.spi;

import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionMetaData;
import javax.resource.cci.IndexedRecord;
import javax.resource.cci.InteractionSpec;
import javax.resource.cci.Record;

import org.openmdx.base.mof.cci.Multiplicities;
import org.openmdx.base.resource.Records;


/**
 * Abstract REST Plug-In
 */
public abstract class AbstractRestPlugIn
    implements RestPlugIn 
{
    
    /**
     * The connection to the next layer
     */
    private Connection next = null;

   /**
    * The connection to the same layer
    */
   private Connection same = null;

    /** 
     * There is no need for a plug-in to provide meta-data.
     * 
     * @return <code>null</code>
     */
    public ConnectionMetaData getMetaData(
    ) throws ResourceException{
        return null;
    }

    /* (non-Javadoc)
     * @see org.openmdx.base.rest.spi.RestPlugIn#setNext(javax.resource.cci.Connection)
     */
    public void setNext(Connection next) {
        this.next = next;
    }
    
    /**
     * Retrieve next.
     *
     * @return Returns the next.
     */
    protected Connection getNext() {
        return this.next;
    }

    /* (non-Javadoc)
     * @see org.openmdx.base.rest.spi.RestPlugIn#setSame(javax.resource.cci.Connection)
     */
    public void setSame(
        Connection same
    ) {
        this.same = same;
    }

    /**
     * Retrieve same.
     *
     * @return Returns the same.
     */
    protected Connection getSame() {
        return this.same;
    }

    /* (non-Javadoc)
     * @see org.openmdx.base.accessor.rest.RestConnection#execute(javax.resource.cci.InteractionSpec, javax.resource.cci.Record)
     */
    public Record execute(
            InteractionSpec ispec, 
            Record input
    ) throws ResourceException {
        IndexedRecord result = Records.getRecordFactory().createIndexedRecord(Multiplicities.LIST);
        execute(ispec, input, result);
        return result;
    }

}
