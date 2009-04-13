/**
 * Copyright 2005-2009 Noelios Technologies.
 * 
 * The contents of this file are subject to the terms of one of the following
 * open source licenses: LGPL 3.0 or LGPL 2.1 or CDDL 1.0 or EPL 1.0 (the
 * "Licenses"). You can select the license that you prefer but you may not use
 * this file except in compliance with one of these Licenses.
 * 
 * You can obtain a copy of the LGPL 3.0 license at
 * http://www.opensource.org/licenses/lgpl-3.0.html
 * 
 * You can obtain a copy of the LGPL 2.1 license at
 * http://www.opensource.org/licenses/lgpl-2.1.php
 * 
 * You can obtain a copy of the CDDL 1.0 license at
 * http://www.opensource.org/licenses/cddl1.php
 * 
 * You can obtain a copy of the EPL 1.0 license at
 * http://www.opensource.org/licenses/eclipse-1.0.php
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

package org.restlet.gae.engine.http;

import org.restlet.gae.data.Parameter;
import org.restlet.gae.data.Request;
import org.restlet.gae.data.Response;
import org.restlet.gae.data.ServerInfo;
import org.restlet.gae.data.Status;
import org.restlet.gae.engine.Engine;
import org.restlet.gae.util.Series;

/**
 * Response wrapper for server HTTP calls.
 * 
 * @author Jerome Louvel
 */
public class HttpResponse extends Response {
    /**
     * Adds a new header to the given request.
     * 
     * @param response
     *            The response to update.
     * @param headerName
     *            The header name to add.
     * @param headerValue
     *            The header value to add.
     */
    public static void addHeader(Response response, String headerName,
            String headerValue) {
        if (response instanceof HttpResponse) {
            ((HttpResponse) response).getHeaders().add(headerName, headerValue);
        }
    }

    /** The low-level HTTP call. */
    private volatile HttpServerCall httpCall;

    /** Indicates if the server data was parsed and added. */
    private volatile boolean serverAdded;

    /**
     * Constructor.
     * 
     * @param httpCall
     *            The low-level HTTP server call.
     * @param request
     *            The associated high-level request.
     */
    public HttpResponse(HttpServerCall httpCall, Request request) {
        super(request);
        this.serverAdded = false;
        this.httpCall = httpCall;

        // Set the properties
        setStatus(Status.SUCCESS_OK);
    }

    /**
     * Returns the HTTP headers.
     * 
     * @return The HTTP headers.
     */
    @SuppressWarnings("unchecked")
    public Series<Parameter> getHeaders() {
        return (Series<Parameter>) getAttributes().get(
                HttpConstants.ATTRIBUTE_HEADERS);
    }

    /**
     * Returns the low-level HTTP call.
     * 
     * @return The low-level HTTP call.
     */
    public HttpServerCall getHttpCall() {
        return this.httpCall;
    }

    /**
     * Returns the server-specific information.
     * 
     * @return The server-specific information.
     */
    @Override
    public ServerInfo getServerInfo() {
        final ServerInfo result = super.getServerInfo();

        if (!this.serverAdded) {
            result.setAddress(this.httpCall.getServerAddress());
            result.setAgent(Engine.VERSION_HEADER);
            result.setPort(this.httpCall.getServerPort());
            this.serverAdded = true;
        }

        return result;
    }

}