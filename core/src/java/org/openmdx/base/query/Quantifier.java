/*
 * ====================================================================
 * Project:     openMDX/Core, http://www.openmdx.org/
 * Name:        $Id: Quantifier.java,v 1.2 2009/05/07 14:56:38 hburger Exp $
 * Description: Quantifier 
 * Revision:    $Revision: 1.2 $
 * Owner:       OMEX AG, Switzerland, http://www.omex.ch
 * Date:        $Date: 2009/05/07 14:56:38 $
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
package org.openmdx.base.query;


/**
 * Quantifier
 */
public enum Quantifier {
    
    /**
     * Universal Quantifier (&#x2200;) 
     */
    FOR_ALL(
        (short)1, // code
        '\u2200' // symbol
    ),
    
    /**
     * Existential Quantifier (&#x2203;)
     */
    THERE_EXISTS(
        (short)2, // code
        '\u2203' // symbol
    );

    /**
     * Constructor 
     *
     * @param code
     * @param symbol
     */
    private Quantifier(
        short code,
        char symbol
    ){
        this.code = code;
        this.symbol = symbol;
    }
    
    /**
     * The <code>Quantifiers</code> legacy code
     */
    private final short code;
    
    /**
     * The <code>Quantifiers</code> UNICODE symbol
     */
    private final char symbol;
    
    /**
     * Retrieve the <code>Quantifiers</code>'s code
     * 
     * @return the <code>Quantifiers</code>'s code
     */
    public short code(){
        return this.code;
    }

    /**
     * Retrieve the <code>Quantifiers</code>'s unicode symbol
     * 
     * @return the <code>Quantifiers</code>'s unicode symbol
     */
    public char symbol() {
        return this.symbol;
    }

    /**
     * Retrieve the <code>Quantifiers</code>'s code
     * 
     * @param quantifier
     * 
     * @return the <code>Quantifiers</code>'s code
     */
    public static short codeOf(
        Quantifier quantifier
    ){
        return quantifier == null ? 0 : quantifier.code;
    }
    
    /**
     * Retrieve the <code>Quantifiers</code> represented by the given code
     * 
     * @param code
     * 
     * @return the <code>Quantifiers</code> represented by the given code
     * 
     * @exception IllegalArgumentException if the code does not represent a <code>Quantifiers</code>
     */
    public static Quantifier valueOf(
        short code
    ){
        switch(code){
            case 0: return null;
            case 1: return FOR_ALL;
            case 2: return THERE_EXISTS;
            default: throw new IllegalArgumentException(
                "Invalid quantifier code: " + code
            );
        }
    }

    /**
     * Retrieve the <code>Quantifiers</code> represented by the given symbol
     * 
     * @param code
     * 
     * @return the <code>Quantifiers</code> represented by the given symbol
     * 
     * @exception IllegalArgumentException if the symbol does not represent a <code>Quantifiers</code>
     */
    public static Quantifier valueOf(
        char symbol
    ){
        switch(symbol){
            case '\u2200': return FOR_ALL;
            case '\u2203': return THERE_EXISTS;
            default: throw new IllegalArgumentException(
                "Invalid quantifier symbol: u" + Integer.toHexString(symbol)
            );
        }
    }
    
}
