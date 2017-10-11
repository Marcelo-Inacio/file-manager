/*
 * @(#)SceneImageContextWrapper.java 1.0 1 03/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.filemanager.restapi.wrapper;

import java.util.Base64;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.gov.sp.fatec.mapskills.filemanager.restapi.serializer.FileContextDeserializer;

/**
 * A classe {@link FileContextWrapper}
 *
 * @author Marcelo Inacio
 * @version 1.0 03/09/2017
 */
@JsonDeserialize(using = FileContextDeserializer.class)
public class FileContextWrapper {
	
	private final String fileBase64;
	private final String fileName;
	
	public FileContextWrapper(final String base64Image,
			final String fileName) {
		this.fileBase64 = base64Image;
		this.fileName = fileName;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public byte[] getFileByteArray() {
		return Base64.getDecoder().decode(fileBase64);
	}

}