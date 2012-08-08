/*
 * ====================================================================
 * Project:     openMDX, http://www.openmdx.org/
 * Name:        $Id: Dataprovider_1Bean.java,v 1.5 2009/05/26 13:59:43 wfro Exp $
 * Description: A Dataprovider Service
 * Revision:    $Revision: 1.5 $
 * Owner:       OMEX AG, Switzerland, http://www.omex.ch
 * Date:        $Date: 2009/05/26 13:59:43 $
 * ====================================================================
 *
 * This software is published under the BSD license as listed below.
 * 
 * Copyright (c) 2004-2009, OMEX AG, Switzerland
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
package org.openmdx.application.dataprovider.transport.ejb.server;

import org.openmdx.application.configuration.Configuration;
import org.openmdx.application.dataprovider.cci.Dataprovider_1_0;
import org.openmdx.application.dataprovider.cci.ServiceHeader;
import org.openmdx.application.dataprovider.cci.UnitOfWorkReply;
import org.openmdx.application.dataprovider.cci.UnitOfWorkRequest;
import org.openmdx.application.dataprovider.kernel.Dataprovider_1;
import org.openmdx.application.dataprovider.transport.cci.Dataprovider_1_1Connection;
import org.openmdx.base.exception.ServiceException;

/**
 * A Dataprovider Service
 */
public class Dataprovider_1Bean 
    extends AbstractDataprovider_1Bean 
    implements Dataprovider_1_0
{

    /**
     * The delegate
     */    
    protected Dataprovider_1_1Connection kernel;
  
    /**
     * Implements <code>Serializable</code>
     */
    private static final long serialVersionUID = -8210685129844478326L;

    
    //------------------------------------------------------------------------
    // Extends AbstractDataprovider_1Bean
    // -----------------------------------------------------------------------

    /* (non-Javadoc)
     * @see org.openmdx.application.dataprovider.transport.ejb.spi.AbstractDataprovider_1Bean#getDelegate()
     */
    @Override
    protected Dataprovider_1_0 getDelegate() {
        return this.kernel;
    }

    
    //------------------------------------------------------------------------
    // Extends SessionBean_1
    // -----------------------------------------------------------------------
  
    /**
     * Activates the EJB
     * 
     * @param configuration
     */
    protected void activate(
        Configuration configuration
    ) throws Exception {
        super.activate(configuration);
        //
        // Acquire kernel
        //
        this.kernel = new Dataprovider_1(
          configuration,
          this
        );
    }
    
    /**
     * Deactivates the dataprovider Java server.
     */
    public void deactivate(
    ) throws Exception {
      this.kernel.close();
      super.deactivate();      
    }


    //------------------------------------------------------------------------
    // Implements Dataprovider_1_0
    //------------------------------------------------------------------------

    /**
     * Process a set of working units
     *
     * @param   header          the service header
     * @param   workingUnits    a collection of working units
     *
     * @return  a collection of working unit replies
     */
    public UnitOfWorkReply[] process(
        ServiceHeader header,
        UnitOfWorkRequest... workingUnits
    ) {      
        try {
            UnitOfWorkReply[] replies = this.kernel.process(
                header,
                workingUnits
            );
            return replies;
       } 
       catch (RuntimeException exception) {
            new ServiceException(exception).log();
            throw exception;    
       }
    }

}  
