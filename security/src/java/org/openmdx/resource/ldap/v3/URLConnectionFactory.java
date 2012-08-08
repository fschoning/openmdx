/*
 * ====================================================================
 * Project:     openMDX, http://www.openmdx.org/
 * Name:        $Id: URLConnectionFactory.java,v 1.5 2009/03/12 17:12:30 hburger Exp $
 * Description: Managed LDAP Connection Factory
 * Revision:    $Revision: 1.5 $
 * Owner:       OMEX AG, Switzerland, http://www.omex.ch
 * Date:        $Date: 2009/03/12 17:12:30 $
 * ====================================================================
 *
 * This software is published under the BSD license
 * as listed below.
 * 
 * Copyright (c) 2007, OMEX AG, Switzerland
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

package org.openmdx.resource.ldap.v3;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.resource.ResourceException;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.InvalidPropertyException;
import javax.security.auth.Subject;

import netscape.ldap.LDAPv3;

import org.openmdx.resource.ldap.spi.ManagedConnection;

/**
 * Managed LDAP Connection Factory
 */
public class URLConnectionFactory
    extends AbstractConnectionFactory {

	/**
	 * Implements <code>Serializable</code>
	 */
	private static final long serialVersionUID = -9174010615999827478L;

	/**
	 * The regular expression's groups are<ol>
	 * <li>the LDAP entry's distinguished name
	 * </ol>
	 */
	private String distinguishedNamePattern = "^dn: (.*)$";

	/**
	 * The regular expression's groups are<ol>
	 * <li>the attribute's name
	 * <li>the attribute's value
	 * </ol>
	 */
	private String attributePattern = "^([^:]+): (.*)$";

	/**
	 * The regular expression's groups are<ol>
	 * <li>the attribute's name
	 * <li>the attribute's value
	 * </ol>
	 */
	private String binaryAttributePattern = "^([^:]+):: (.*)$";

	/**
	 * Tells whether string comparison is case sensitive or not
	 */
	private boolean caseSensitive = false;

	/**
	 * The regular expression's groups are<ol>
	 * <li>the attribute's name
	 * <li>the attribute's value
	 * </ol>
	 */
	private String commentPattern = "^#(.*)$";
	
	/**
	 * The LDAP cache
	 */
	private LDAPv3 physicalConnection = null;
	
    public synchronized javax.resource.spi.ManagedConnection createManagedConnection(
        Subject subject,
        ConnectionRequestInfo connectionRequestInfo
    ) throws ResourceException {
    	if(this.physicalConnection == null) {
			this.physicalConnection = new URLConnection(
				this.toURL("ConnectionURL", this.getConnectionURL()),
				this.toPattern("DistinguishedNamePattern", this.getDistinguishedNamePattern()),
				this.toPattern("AttributePattern", this.getAttributePattern()),
				this.toPattern("BinaryAttributePattern", this.getBinaryAttributePattern()), 
				this.toPattern("CommentPattern", this.getCommentPattern()), 
				this.caseSensitive
			);
    	}
        return new ManagedConnection(
            this.physicalConnection,
            null
         );
    }

    private Pattern toPattern(
    	String property,
    	String pattern
    ) throws InvalidPropertyException {
    	if(pattern == null) throw new InvalidPropertyException(
    		"Missing " + property
    	);
    	try {
	    	return Pattern.compile(pattern);
    	} 
    	catch (PatternSyntaxException exception) {
			throw new InvalidPropertyException(
				"Invalid " + property + ": " + pattern,
				exception
			);
    	}
    }

    private URL toURL(
    	String property,
    	String value
    ) throws InvalidPropertyException {    	
    	if(value == null) throw new InvalidPropertyException(
    		"Missing " + property
    	);
    	try {
	    	return new URL(value);
		} 
    	catch (MalformedURLException exception) {
			throw new InvalidPropertyException(
				"Invalid " + property + ": " + value,
				exception
			);
    	}
    }
    
	/**
	 * @return the distinguishedNamePattern
	 */
	public String getDistinguishedNamePattern() {
		return this.distinguishedNamePattern;
	}


	/**
	 * @param distinguishedNamePattern the distinguishedNamePattern to set
	 */
	public void setDistinguishedNamePattern(String distinguishedNamePattern) {
		this.distinguishedNamePattern = distinguishedNamePattern;
	}


	/**
	 * @return the attributePattern
	 */
	public String getAttributePattern() {
		return this.attributePattern;
	}


	/**
	 * @param attributePattern the attributePattern to set
	 */
	public void setAttributePattern(String attributePattern) {
		this.attributePattern = attributePattern;
	}

	/**
	 * @return the attributePattern
	 */
	public String getBinaryAttributePattern() {
		return this.binaryAttributePattern;
	}


	/**
	 * @param attributePattern the attributePattern to set
	 */
	public void setBinaryAttributePattern(String binaryAttributePattern) {
		this.binaryAttributePattern = binaryAttributePattern;
	}

	/**
	 * @return the commentPattern
	 */
	public String getCommentPattern() {
		return this.commentPattern;
	}

	/**
	 * @param commentPattern the commentPattern to set
	 */
	public void setCommentPattern(String commentPattern) {
		this.commentPattern = commentPattern;
	}

	/**
	 * @return the caseSensitive
	 */
	public Boolean getCaseSensitive() {
		return this.caseSensitive;
	}

	/**
	 * @param caseSensitive the caseSensitive to set
	 */
	public void setCaseSensitive(Boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

}
