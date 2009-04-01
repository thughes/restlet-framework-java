/**
 * Copyright 2005-2009 Noelios Technologies.
 * 
 * The contents of this file are subject to the terms of the following open
 * source licenses: LGPL 3.0 or LGPL 2.1 or CDDL 1.0 (the "Licenses"). You can
 * select the license that you prefer but you may not use this file except in
 * compliance with one of these Licenses.
 * 
 * You can obtain a copy of the LGPL 3.0 license at
 * http://www.gnu.org/licenses/lgpl-3.0.html
 * 
 * You can obtain a copy of the LGPL 2.1 license at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 * 
 * You can obtain a copy of the CDDL 1.0 license at
 * http://www.sun.com/cddl/cddl.html
 * 
 * See the Licenses for the specific language governing permissions and
 * limitations under the Licenses.
 * 
 * Alternatively, you can obtain a royalty free commercial license with less
 * limitations, transferable or non-transferable, directly at
 * http://www.noelios.com/products/restlet-engine
 * 
 * Restlet is a registered trademark of Noelios Technologies.
 */

package org.restlet.resource;

import java.io.IOException;
import java.io.OutputStream;

import org.restlet.data.LinkSet;

/**
 * Base of all RDF representation classes. It knows how to serialize and
 * deserialize a {@link LinkSet}.
 * 
 * @author Jerome Louvel
 */
public abstract class RdfRepresentation extends OutputRepresentation {

    private LinkSet linkSet;

    public RdfRepresentation(LinkSet linkSet) {
        super(null);
        this.linkSet = linkSet;
    }

    /**
     * Constructor that parsed a given RDF representation into a link set.
     * 
     * @param rdfRepresentation
     *            The RDF representation to parse.
     * @param linkSet
     *            The link set to update.
     */
    public RdfRepresentation(Representation rdfRepresentation, LinkSet linkSet) {
        // Parsing goes here.
        this(linkSet);
    }

    public LinkSet getLinkSet() {
        return linkSet;
    }

    public void setLinkSet(LinkSet linkSet) {
        this.linkSet = linkSet;
    }

    @Override
    public void write(OutputStream outputStream) throws IOException {
    }

}