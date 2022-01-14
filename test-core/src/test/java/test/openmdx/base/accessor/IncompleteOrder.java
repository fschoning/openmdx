/*
 * ====================================================================
 * Project:     openMDX/Core, http://www.openmdx.org/
 * Description: Flush Order 
 * Owner:       the original authors.
 * ====================================================================
 *
 * This software is published under the BSD license as listed below.
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
package test.openmdx.base.accessor;

import java.util.Comparator;

import org.openmdx.base.accessor.cci.DataObject_1_0;
import org.openmdx.base.naming.Path;


/**
 * Flush Order
 */
public class IncompleteOrder implements Comparator<DataObject_1_0> {

    /**
     * Constructor 
     */
    protected IncompleteOrder(){
        super();
    }

    /**
     * A  a <code>FlushOrder</code> instance
     */
    private static final Comparator<DataObject_1_0> instance = new IncompleteOrder();
    
    /**
     * Retrieve a <code>FlushOrder</code> instance
     * 
     * @return a <code>FlushOrder</code> instance
     */
    public static Comparator<DataObject_1_0> getInstance(){
        return IncompleteOrder.instance;
    }
    
//  @Override
    public int compare(
        DataObject_1_0 o1, 
        DataObject_1_0 o2
    ) {
        boolean new1 = o1.jdoIsNew();
        boolean new2 = o2.jdoIsNew();
        boolean del1 = o1.jdoIsDeleted();
        boolean del2 = o2.jdoIsDeleted();
        if(new1 != del1 && new2 != del2) {
            Path xri1 = o1.jdoGetObjectId();
            Path xri2 = o2.jdoGetObjectId();
            if(new1 && new2) {
                return xri1.startsWith(xri2) ? +1 : xri2.startsWith(xri1) ? -1 : 0;
            } else { // del1 == del2
                return xri1.startsWith(xri2) ? -1 : xri2.startsWith(xri1) ? +1 : 0;
            }
        } else {
            return 0;
        }
    }
    
}
