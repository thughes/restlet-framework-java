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

package org.restlet.ext.script.internal;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.concurrent.ConcurrentMap;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.restlet.data.Language;
import org.restlet.ext.script.ScriptedTextResource;
import org.restlet.representation.WriterRepresentation;

import com.threecrickets.scripturian.EmbeddedScript;
import com.threecrickets.scripturian.ScriptContextController;

/**
 * Representation used in streaming mode of {@link ScriptedTextResource}.
 * 
 * @author Tal Liron
 * @ScriptedTextResource
 */
class ScriptedTextStreamingRepresentation extends WriterRepresentation {

    /**
     * The resource.
     */
    private final ScriptedTextResource resource;

    /**
     * The container.
     */
    private final ScriptedTextResourceContainer container;

    /**
     * The embedded script instance.
     */
    private final EmbeddedScript script;

    /**
     * The script context controller.
     */
    private final ScriptContextController scriptContextController;

    /**
     * A cache of script engines used by {@link EmbeddedScript}.
     */
    private final ConcurrentMap<String, ScriptEngine> scriptEngines;

    /**
     * Whether to flush the writers after every line.
     */
    private final boolean flushLines;

    /**
     * Constructor.
     * 
     * @param resource
     *            The resource
     * @param container
     *            The container
     * @param scriptEngines
     *            A cache of script engines used by {@link EmbeddedScript}.
     * @param scriptContextController
     *            The script context controller
     * @param script
     *            The embedded script instance
     * @param flushLines
     *            Whether to flush the writers after every line
     */
    public ScriptedTextStreamingRepresentation(ScriptedTextResource resource,
            ScriptedTextResourceContainer container,
            ConcurrentMap<String, ScriptEngine> scriptEngines,
            ScriptContextController scriptContextController,
            EmbeddedScript script, boolean flushLines) {
        // Note that we are setting representation characteristics
        // before we actually run the script
        super(container.getMediaType());
        this.resource = resource;
        this.container = container;
        this.scriptEngines = scriptEngines;
        this.scriptContextController = scriptContextController;
        this.flushLines = flushLines;
        setCharacterSet(container.getCharacterSet());
        if (container.getLanguage() != null) {
            setLanguages(Arrays
                    .asList(new Language[] { container.getLanguage() }));
        }
        this.script = script;
    }

    @Override
    public void write(Writer writer) throws IOException {
        // writer = new OutputStreamWriter(System.out);
        this.container.isStreaming = true;
        this.resource.setWriter(writer);
        try {
            this.script.run(writer, this.resource.getErrorWriter(),
                    this.flushLines, this.scriptEngines,
                    this.scriptContextController, false);
        } catch (ScriptException e) {
            IOException ioe = new IOException("Script exception");
            ioe.initCause(e);
            throw ioe;
        } finally {
            // The script may have set its cacheDuration, so we must
            // make sure to disable it!
            this.script.setCacheDuration(0);
        }
    }
}