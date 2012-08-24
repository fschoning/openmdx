/*
 * ====================================================================
 * Project:     openMDX/Security, http://www.openmdx.org/
 * Description: Managed Key Store Connection Factory
 * Owner:       OMEX AG, Switzerland, http://www.omex.ch
 * ====================================================================
 *
 * This software is published under the BSD license as listed below.
 * 
 * Copyright (c) 2006-2010, OMEX AG, Switzerland
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
package org.openmdx.resource.pki.keystore;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.PKIXParameters;
import java.util.ArrayList;
import java.util.List;

import javax.resource.ResourceException;
import javax.resource.spi.CommException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.EISSystemException;
import javax.resource.spi.InvalidPropertyException;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.security.PasswordCredential;
import javax.security.auth.Subject;

import org.openmdx.resource.spi.AbstractManagedConnectionFactory;

/**
 * Managed Key Store Connection Factory
 */
public class ManagedConnectionFactory extends AbstractManagedConnectionFactory {

	/**
     * Constructor
     */
    public ManagedConnectionFactory() {
	    super();
    }

	/**
     * Implements <code>Serializable</code>
     */
    private static final long serialVersionUID = 8198549417001170970L;

    /**
     * 
     */
    private static final char[][] NO_PASS_PHRASES = {};

    /**
     * 'KeyStore' property
     */
    private String keyStoreType;
    
    /**
     * 'PassPhraseSeparator' property
     */
    private String passPhraseSeparator;

    /**
     * 
     */
    private String algorithm;
    
    /**
     * Overrides a credential's user name
     */
    private String alias;
    
    /**
     * 
     */
    private boolean revocationEnabled = false;
    
    /**
     * 'ConnectionType' property
     */
    private ConnectionType connectionType = ConnectionType.CERTIFICATE_PROVIDER;
    
    /* (non-Javadoc)
     * @see org.openmdx.resource.spi.AbstractManagedConnectionFactory#isManagedConnectionShareable()
     */
    @Override
    protected boolean isManagedConnectionShareable() {
	    return this.connectionType != ConnectionType.CERTIFICATE_VALIDATOR;
    }

	/* (non-Javadoc)
     * @see javax.resource.spi.ManagedConnectionFactory#createConnectionFactory(javax.resource.spi.ConnectionManager)
     */
    public Object createConnectionFactory(
        ConnectionManager connectionManager
    ) throws ResourceException {
        return new ConnectionFactory(
        	this,
        	connectionManager
        );
    }

    /**
     * Retrieve the first pass phrase encoded in the credential's password field
     * 
     * @param credential
     * 
     * @return the first pass phrase encoded in the credential's password field
     * 
     * @throws ResourceException
     */
    protected final char[][] getPassPhrases(
        PasswordCredential credential
    ) throws ResourceException {
        char[] password = credential.getPassword();
        if(this.passPhraseSeparator == null) {
            return new char[][]{password};
        } else switch (getPassPhraseSeparator().length()){
            case 0: 
                return new char[][]{password};
            case 1:
                char passPhraseSeparator = getPassPhraseSeparator().charAt(0);
                List<char[]> passPhrases = new ArrayList<char[]>();
                for(
                    int i = 0, begin = 0;
                    i <= password.length;
                    i++
                ) if (
                    i == password.length ||
                    password[i] == passPhraseSeparator
                ) {                    
                    char[] passPhrase = new char[i - begin];
                    System.arraycopy(password, begin, passPhrase, 0, passPhrase.length);
                    passPhrases.add(passPhrase);
                    begin = i + 1;
                }
                return passPhrases.toArray(new char[passPhrases.size()][]);
            default: 
                throw log(
                    new InvalidPropertyException(
                        "A pass phrase separator must be one character long: '" + getPassPhraseSeparator() + "'"
                    )
                );
        }
    }
    
    /**
     * Retrieve keyStoreType.
     *
     * @return Returns the keyStoreType.
     */
    public final String getKeyStoreType() {
        return this.keyStoreType;
    }
    
    /**
     * Set keyStoreType.
     * 
     * @param keyStoreType The keyStoreType to set.
     */
    public final void setKeyStoreType(
        String keyStoreType
    ) {
        this.keyStoreType = keyStoreType;
    }
    
    /**
     * Retrieve passPhraseSeparator.
     *
     * @return Returns the passPhraseSeparator.
     */
    public final String getPassPhraseSeparator() {
        return this.passPhraseSeparator;
    }

    /**
     * Set passPhraseSeparator.
     * 
     * @param passPhraseSeparator The passPhraseSeparator to set.
     */
    public final void setPassPhraseSeparator(
        String passPhraseSeparator
    ) {
        this.passPhraseSeparator = passPhraseSeparator;
    }

    /**
     * Retrieve the 'ConnectionType' property
     * 
     * @return the connection type
     */
    public String getConnectionType() {
    	return this.connectionType == null ? null : this.connectionType.name();
    }

	/**
	 * Set the 'ConnectionType' property.
	 * 
     * @param validator the validator to set
     */
    public void setConnectionType(
        String connectionType
    ) {
    	this.connectionType = connectionType == null ? null : ConnectionType.valueOf(connectionType.toUpperCase());
    }

	/**
	 * Retrieve the 'Algorithm' property
	 * 
     * @return the algorithm
     */
    public String getAlgorithm() {
    	return algorithm;
    }

	/**
	 * Set the 'Algorithm' property
	 * 
     * @param algorithm the algorithm to set
     */
    public void setAlgorithm(String algorithm) {
    	this.algorithm = algorithm;
    }

	/**
	 * Retrieve the 'RevocationEnabled' property
	 * 
     * @return the revocationEnabled
     */
    public boolean isRevocationEnabled() {
    	return this.revocationEnabled;
    }

	/**
	 * Set the 'RevocationEnabled' property
	 * 
     * @param revocationEnabled the revocationEnabled to set
     */
    public void setRevocationEnabled(boolean revocationEnabled) {
    	this.revocationEnabled = revocationEnabled;
    }

	/* (non-Javadoc)
     * @see javax.resource.spi.ManagedConnectionFactory#createManagedConnection(javax.security.auth.Subject, javax.resource.spi.ConnectionRequestInfo)
     */
    public ManagedConnection createManagedConnection(
        Subject subject,
        ConnectionRequestInfo connectionRequestInfo
    ) throws ResourceException {
    	String alias;
    	char[][] passPhrases;
        PasswordCredential credential = getCredential(subject);
        if(credential == null) {
            alias = this.alias;
            passPhrases = NO_PASS_PHRASES;
        } else {
            alias = this.alias == null ? credential.getUserName() : this.alias;
            passPhrases = getPassPhrases(credential);
        }
        String keyStoreType = getKeyStoreType();
        String connectionURL = getConnectionURL();
        try {
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);   
            keyStore.load(
                new URL(connectionURL).openStream(),
                passPhrases.length == 0 || passPhrases[0].length == 0 ? null : passPhrases[0]
            );
            if(this.connectionType != null) {
	            switch(this.connectionType) {
		            case CERTIFICATE_VALIDATOR:
		            	PKIXParameters parameters = new PKIXParameters(keyStore);
		            	parameters.setRevocationEnabled(this.isRevocationEnabled());
		            	return new ManagedKeyStoreConnection(
		            		this.connectionType,
		                	credential,
		                	parameters, 
		                	getAlgorithm()
		                );
		            case CERTIFICATE_PROVIDER:
		            case SIGNATURE_VERIFIER:
		            	if(alias == null) {
		                    throw new ResourceException(
		                    	"Missing 'UserName' in UserName/Password credential, which is used as certificate alias"
		                    );
		            	}
		            	return new ManagedKeyStoreConnection(
		            		this.connectionType,
		                    credential,
		                    alias,
		                    keyStore.getCertificate(alias), 
		                    null, 
		                    getAlgorithm()
		                 ); 
		            case SIGNATURE_PROVIDER:		            	
		            	return new ManagedKeyStoreConnection(
		            		this.connectionType,
		                    credential,
		                    alias,
		                    keyStore.getCertificate(alias), 
		                    keyStore.getKey(alias, passPhrases[1]), 
		                    getAlgorithm()
		                 );
	            }
            }
            throw new ResourceException(
            	"Missing 'ConnectionType'"
            );
        } catch (NoSuchAlgorithmException exception) {
            throw log(
                (ResourceException) new EISSystemException(
                    "Unable to to retrieve a " + alias + " validator"
                ).initCause(
                    exception
                )
            );
        } catch (GeneralSecurityException exception) {
            throw log(
                (ResourceException) new EISSystemException(
                    "Unable to load " + keyStoreType + " key store from " + connectionURL
                ).initCause(
                    exception
                )
            );
        } catch (MalformedURLException exception) {
            throw log(
                (ResourceException) new InvalidPropertyException(
                    "Invalid key store URL: " + connectionURL
                ).initCause(
                    exception
                )
            );
        } catch (IOException exception) {
            throw log(
                (ResourceException) new CommException(
                    "Unable to load key store from " + connectionURL
                ).initCause(
                    exception
                )
            );
        }
    }

    
    //------------------------------------------------------------------------
    // Extends AbstractManagedConnectionFactory
    //------------------------------------------------------------------------
    
    /**
     * Overriding required for Oracle WebLogic
     * 
     * @see org.openmdx.resource.spi.AbstractManagedConnectionFactory#equals(java.lang.Object)
     */
    @Override
    public boolean equals(
    	Object that
    ) {
        return super.equals(that);
    }

	/**
     * Overriding required for Oracle WebLogic
     * 
     * @see org.openmdx.resource.spi.AbstractManagedConnectionFactory#hashCode()
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
}
