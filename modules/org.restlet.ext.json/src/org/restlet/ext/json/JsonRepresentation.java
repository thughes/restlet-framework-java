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

package org.restlet.ext.json;

import java.io.IOException;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;
import org.restlet.data.CharacterSet;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

/**
 * Representation based on a JSON document. JSON stands for JavaScript Object
 * Notation and is a lightweight data-interchange format.
 * 
 * @author Jerome Louvel
 * @see <a href="http://www.json.org">JSON home</a>
 */
public class JsonRepresentation extends StringRepresentation {
    /**
     * Constructor from a JSON array.
     * 
     * @param jsonArray
     *            The JSON array.
     */
    public JsonRepresentation(JSONArray jsonArray) {
        this(jsonArray.toString());
    }

    /**
     * Constructor from a JSON object.
     * 
     * @param jsonObject
     *            The JSON object.
     */
    public JsonRepresentation(JSONObject jsonObject) {
        super(jsonObject.toString(), MediaType.APPLICATION_JSON, null,
                CharacterSet.UTF_8);
    }

    /**
     * Constructor from a JSON stringer.
     * 
     * @param jsonStringer
     *            The JSON stringer.
     */
    public JsonRepresentation(JSONStringer jsonStringer) {
        this(jsonStringer.toString());
    }

    /**
     * Constructor from a JSON tokener.
     * 
     * @param jsonTokener
     *            The JSON tokener.
     */
    public JsonRepresentation(JSONTokener jsonTokener) {
        this(jsonTokener.toString());
    }

    /**
     * Constructor from a map object.
     * 
     * @param map
     *            The map to convert to JSON.
     * @see org.json.JSONObject#JSONObject(Map)
     */
    public JsonRepresentation(Map<Object, Object> map) {
        this(new JSONObject(map));
    }

    /**
     * Constructor from a bean using reflection to generate JSON names.
     * 
     * @param bean
     *            The bean to convert to JSON.
     * @see org.json.JSONObject#JSONObject(Object)
     */
    public JsonRepresentation(Object bean) {
        this(new JSONObject(bean));
    }

    /**
     * Constructor.
     * 
     * @param jsonRepresentation
     *            A source JSON representation to parse.
     */
    public JsonRepresentation(Representation jsonRepresentation)
            throws IOException {
        super(jsonRepresentation.getText(), MediaType.APPLICATION_JSON, null,
                CharacterSet.UTF_8);
    }

    /**
     * Constructor from a JSON string.
     * 
     * @param jsonString
     *            The JSON string.
     */
    public JsonRepresentation(String jsonString) {
        super(jsonString, MediaType.APPLICATION_JSON, null, CharacterSet.UTF_8);
    }

    /**
     * Converts the representation to a JSON array.
     * 
     * @return The converted JSON array.
     * @throws JSONException
     */
    public JSONArray toJsonArray() throws JSONException {
        return new JSONArray(getText());
    }

    /**
     * Converts the representation to a JSON object.
     * 
     * @return The converted JSON object.
     * @throws JSONException
     */
    public JSONObject toJsonObject() throws JSONException {
        return new JSONObject(getText());
    }

}