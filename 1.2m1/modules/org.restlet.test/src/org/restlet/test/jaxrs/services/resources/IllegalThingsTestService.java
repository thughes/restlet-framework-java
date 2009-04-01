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
package org.restlet.test.jaxrs.services.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.restlet.test.jaxrs.services.tests.IllegalThingsTest;

/**
 * This class contains only data for one media type
 * 
 * @author Stephan Koops
 * @see IllegalThingsTest
 */
@Path("/illegalThingsInternal")
public class IllegalThingsTestService {

    @GET
    @Path("package")
    String getPackageVisible() {
        return "this method is package visible. Is there a warning?";
    }

    /**
     * This sub resource locator returns null; that is not allowed
     * 
     * @return
     */
    @Path("nullSubResource")
    public Object getPlainText() {
        return null;
    }

    @GET
    @Path("private")
    String getPrivateVisible() {
        return "this method is private visible. Is there a warning?";
    }

    @GET
    @Path("protected")
    protected String getProtected() {
        return "this method is protected. Is there a warning?";
    }
}