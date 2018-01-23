/*
 * @(#)SceneImageContextDeserializer.java 1.0 1 03/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.filemanager.restapi.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import br.gov.sp.fatec.mapskills.filemanager.restapi.wrapper.FileContextWrapper;

/**
 * A classe {@link FileContextDeserializer}
 *
 * @author Marcelo Inacio
 * @version 1.0 03/09/2017
 */
public class FileContextDeserializer extends JsonDeserializer<FileContextWrapper> {

	@Override
	public FileContextWrapper deserialize(final JsonParser arg0, final DeserializationContext arg1)
			throws IOException {
		
		final ObjectCodec oc = arg0.getCodec();
        final JsonNode node = oc.readTree(arg0);
		
		return new FileContextWrapper(node.get("base64").textValue(),
				node.get("filename").textValue());
	}

}