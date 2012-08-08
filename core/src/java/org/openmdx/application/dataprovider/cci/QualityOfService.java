/*
 * ====================================================================
 * Project:     openmdx, http://www.openmdx.org/
 * Name:        $Id: QualityOfService.java,v 1.3 2009/06/09 15:39:58 hburger Exp $
 * Description: Quality of Service
 * Revision:    $Revision: 1.3 $
 * Owner:       OMEX AG, Switzerland, http://www.omex.ch
 * Date:        $Date: 2009/06/09 15:39:58 $
 * ====================================================================
 *
 * This software is published under the BSD license
 * as listed below.
 * 
 * Copyright (c) 2004, OMEX AG, Switzerland
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
 */
package org.openmdx.application.dataprovider.cci;

import java.io.Serializable;

/**
 * Quality of Service Descriptor
 */
public class QualityOfService
    implements Serializable
{

    /**
     * With the default qaulity of service the client waits until the server
     * has completed the requests in the given order.
     */
    public QualityOfService(
    ){
        super();
    }
    
    /**
     * Implements <code>Serializable</code>
     */
    private static final long serialVersionUID = 3257844389827981365L;

    /**
     * TODO Replace by constant
     */
    final private short synchronizationScope = -1;
    
    /**
     * TODO Replace by constant
     */
    final private short routingType = -1; 
    
    /**
     * TODO Replace by constant
     */
    final private short orderingType = -1; 

    /**
     * The standard quality of service
     */
    public static final QualityOfService STANDARD = new QualityOfService();
    
    /**
     *
     */
    public short synchronizationScope(
    ){
        return this.synchronizationScope;
    }
    
    /**
     *
     */
    public short routingType(
    ){
        return this.routingType;
    }

    /**
     *
     */
    public int orderingType(
    ){
        return this.orderingType;
    }

}
