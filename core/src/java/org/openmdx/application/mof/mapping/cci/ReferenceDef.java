/*
 * ====================================================================
 * Project:     openmdx, http://www.openmdx.org/
 * Name:        $Id: ReferenceDef.java,v 1.4 2009/06/09 12:45:18 hburger Exp $
 * Description: VelocityReferenceDef class
 * Revision:    $Revision: 1.4 $
 * Owner:       OMEX AG, Switzerland, http://www.omex.ch
 * Date:        $Date: 2009/06/09 12:45:18 $
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
package org.openmdx.application.mof.mapping.cci;

import java.util.HashSet;
import java.util.Set;

import org.openmdx.base.exception.ServiceException;
import org.openmdx.base.mof.cci.AggregationKind;
import org.openmdx.base.mof.cci.ModelElement_1_0;
import org.openmdx.base.mof.cci.Model_1_0;

@SuppressWarnings("unchecked")
public class ReferenceDef 
  extends StructuralFeatureDef {

    /**
     * Constructor 
     *
     * @param referenceDef
     * @param model
     * @param openmdx1
     * @throws ServiceException
     */
  public ReferenceDef(
    ModelElement_1_0 referenceDef,
    Model_1_0 model, 
    boolean openmdx1
  ) throws ServiceException {
      this(
          referenceDef,
          model,
          openmdx1,
          getAssociationDef(referenceDef, model)
      );
  }

  /**
   * Constructor 
   *
   * @param referenceDef
   * @param model
   * @param openmdx1
   * @param associationDef
   * @throws ServiceException
   */
  private ReferenceDef(
      ModelElement_1_0 referenceDef,
      Model_1_0 model, 
      boolean openmdx1,
      ModelElement_1_0 associationDef
  ) throws ServiceException {
      this(
          (String)referenceDef.objGetValue("name"),
          (String)referenceDef.objGetValue("qualifiedName"),
          (String)referenceDef.objGetValue("annotation"),
          new HashSet(referenceDef.objGetList("stereotype")),
          (String)referenceDef.objGetValue("visibility"),
          (String)model.getElementType(referenceDef).objGetValue("qualifiedName"),
          (String)referenceDef.objGetValue("multiplicity"),
          getQualifierName(referenceDef, model, "referencedEnd"),
          getQualifierTypeName(referenceDef, model, "referencedEnd"),
          (Boolean)referenceDef.objGetValue("isChangeable"),
          model.referenceIsDerived(referenceDef),
          true,
          getExposedEndName(referenceDef, model),
          getExposedEndQualifiedTypeName(referenceDef, model),
          getQualifierName(referenceDef, model, "exposedEnd"),
          getQualifierTypeName(referenceDef,  model, "exposedEnd"),
          getReferencedEndQualifiedTypeName(referenceDef, model),
          (String) associationDef.objGetValue("name"),
          (String) associationDef.objGetValue("qualifiedName"),
          isComposition(referenceDef, model),
          isShared(referenceDef, model) 
        );
  }
  
  /**
   * Constructor 
   *
   * @param name
   * @param qualifiedName
   * @param annotation
   * @param stereotype
   * @param visibility
   * @param qualifiedTypeName
   * @param multiplicity
   * @param qualifierName
   * @param qualifiedQualifierTypeName
   * @param isChangeable
   * @param isDerived
   * @param openmdx1
   * @param exposedEndName
   * @param exposedEndQualifiedTypeName
   * @param exposedEndQualifierName
   * @param exposedEndQualifiedQualifierTypeName
   * @param referencedEndQualifiedTypeName
   * @param associationName
   * @param qualifiedAssociationName
   * @param composite
   * @param shared
   */
  protected ReferenceDef(
      String name,
      String qualifiedName,
      String annotation,
      Set stereotype,
      String visibility,
      String qualifiedTypeName,
      String multiplicity,
      String qualifierName,
      String qualifiedQualifierTypeName,
      Boolean isChangeable,
      Boolean isDerived, 
      boolean openmdx1,
      String exposedEndName,
      String exposedEndQualifiedTypeName, 
      String exposedEndQualifierName, 
      String exposedEndQualifiedQualifierTypeName, 
      String referencedEndQualifiedTypeName,
      String associationName,
      String qualifiedAssociationName,
      boolean composite,
      boolean shared
  ) {
    super(
      name, 
      qualifiedName, 
      annotation, 
      stereotype,
      visibility,
      qualifiedTypeName, 
      multiplicity,
      isChangeable,
      isDerived 
    );
    this.qualifierName = qualifierName;
    this.qualifiedQualifierTypeName = qualifiedQualifierTypeName;
    this.exposedEndName = exposedEndName;
    this.exposedEndQualifiedTypeName = exposedEndQualifiedTypeName;
    this.exposedEndQualifierName = exposedEndQualifierName;
    this.exposedEndQualifiedQualifierTypeName = exposedEndQualifiedQualifierTypeName;
    this.referencedEndQualifiedTypeName = referencedEndQualifiedTypeName;
    this.associationName = associationName;
    this.qualifiedAssociationName = qualifiedAssociationName;
    this.composition = composite;
    this.shared = shared;
  }

  //-------------------------------------------------------------------------
  private static String getQualifierName(
    ModelElement_1_0 referenceDef,
    Model_1_0 model, 
    String end
  ) throws ServiceException {
    ModelElement_1_0 referencedEnd = model.getElement(
      referenceDef.objGetValue(end)
    );
    boolean hasQualifiers = !referencedEnd.objGetList("qualifierName").isEmpty();
    return hasQualifiers 
      ? (String)referencedEnd.objGetValue("qualifierName") 
      : null;
  }
  
  //-------------------------------------------------------------------------
  private static ModelElement_1_0 getAssociationDef(
      ModelElement_1_0 referenceDef,
      Model_1_0 model
    ) throws ServiceException {
      ModelElement_1_0 associationEndDef = model.getElement(
        referenceDef.objGetValue("referencedEnd")
      );
      return model.getElement(
          associationEndDef.objGetValue("container")
      );
    }

  //-------------------------------------------------------------------------
  private static String getQualifierTypeName(
    ModelElement_1_0 referenceDef,
    Model_1_0 model, 
    String end
  ) throws ServiceException {
    ModelElement_1_0 referencedEnd = model.getElement(
      referenceDef.objGetValue(end)
    );
    boolean hasQualifiers = !referencedEnd.objGetList("qualifierName").isEmpty();
    return hasQualifiers 
      ? (String)model.getDereferencedType(
          referencedEnd.objGetValue("qualifierType")
      ).objGetValue("qualifiedName") 
      : null;
  }

  //-------------------------------------------------------------------------
  private static String getExposedEndQualifiedTypeName(
    ModelElement_1_0 referenceDef,
    Model_1_0 model 
  ) throws ServiceException {
    ModelElement_1_0 exposedEnd = model.getElement(
      referenceDef.objGetValue("exposedEnd")
    );
    return (String)model.getElementType(
        exposedEnd
    ).objGetValue("qualifiedName");
  }
  
  //-------------------------------------------------------------------------
  private static String getReferencedEndQualifiedTypeName(
    ModelElement_1_0 referenceDef,
    Model_1_0 model 
  ) throws ServiceException {
    ModelElement_1_0 referencedEnd = model.getElement(
      referenceDef.objGetValue("referencedEnd")
    );
    return (String)model.getElementType(
        referencedEnd
    ).objGetValue("qualifiedName");
  }
  
  //-------------------------------------------------------------------------
  private static String getExposedEndName(
    ModelElement_1_0 referenceDef,
    Model_1_0 model
  ) throws ServiceException {
    ModelElement_1_0 exposedEnd = model.getElement(
      referenceDef.objGetValue("exposedEnd")
    );
    return (String)exposedEnd.objGetValue("name");
  }
  
  //-------------------------------------------------------------------------
  private static boolean isComposition(
    ModelElement_1_0 referenceDef,
    Model_1_0 model
  ) throws ServiceException {
    ModelElement_1_0 exposedEnd = model.getElement(
      referenceDef.objGetValue("referencedEnd")
    );
    return AggregationKind.COMPOSITE.equals(exposedEnd.objGetValue("aggregation"));
  }
  
  //-------------------------------------------------------------------------
  private static boolean isShared(
      ModelElement_1_0 referenceDef,
      Model_1_0 model
    ) throws ServiceException {
      ModelElement_1_0 exposedEnd = model.getElement(
        referenceDef.objGetValue("referencedEnd")
      );
      return AggregationKind.SHARED.equals(exposedEnd.objGetValue("aggregation"));
    }
  
  //-------------------------------------------------------------------------
  public String getQualifierName(
  ) {
    return this.qualifierName;  
  }

  //-------------------------------------------------------------------------
  public String getQualifiedQualifierTypeName(
  ) {
    return this.qualifiedQualifierTypeName;  
  }

  //-------------------------------------------------------------------------
  public String getExposedEndName(
  ) {
    return this.exposedEndName;  
  }
  
  //-------------------------------------------------------------------------
  public String getExposedEndQualifierName(
  ) {
    return this.exposedEndQualifierName;  
  }
  
  //-------------------------------------------------------------------------
  public String getExposedEndQualifiedTypeName(
  ) {
    return this.exposedEndQualifiedTypeName;  
  }
  
  //-------------------------------------------------------------------------
  public String getReferencedEndQualifiedTypeName(
  ) {
    return this.referencedEndQualifiedTypeName;  
  }
  
  //-------------------------------------------------------------------------
  public String getExposedEndQualifiedQualifierTypeName(
  ) {
    return this.exposedEndQualifiedQualifierTypeName;  
  }
  
  //-------------------------------------------------------------------------
  public String getAssociationName(
  ) {
    return this.associationName;  
  }

  //-------------------------------------------------------------------------
  public String getQualifiedAssociationName(
  ) {
    return this.qualifiedAssociationName;  
  }

  //-------------------------------------------------------------------------
  public boolean isComposition(
  ) {
    return this.composition;  
  }

  //-------------------------------------------------------------------------
  public boolean isShared(
  ) {
    return this.shared;  
  }

  //-------------------------------------------------------------------------
  // Variables
  //-------------------------------------------------------------------------
  private final String qualifierName;
  private final String qualifiedQualifierTypeName;
  private final String exposedEndName;
  private final String exposedEndQualifiedTypeName;
  private final String referencedEndQualifiedTypeName;
  private final String exposedEndQualifierName;
  private final String exposedEndQualifiedQualifierTypeName;
  private final String associationName;
  private final String qualifiedAssociationName;
  private final boolean composition;
  private final boolean shared;
  
}
