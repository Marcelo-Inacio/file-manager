/*
 * @(#)FileHandleApplicationServices.java 1.0 1 02/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.filemanager.application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AllArgsConstructor;

/**
 * A classe {@link ApplicationServices}
 *
 * @author Marcelo Inacio
 * @version 1.0 02/09/2017
 */
@ApplicationServices
@AllArgsConstructor
public class FileManagerApplicationServices {
	
	private static final Logger logger = LoggerFactory.getLogger(FileManagerApplicationServices.class);
	private static final String RESOURCE = "/drive/";
	
	private final ServletContext context;
	
	public String save(final byte[] file, final String fileName) {
		try {
			final String realPath = context.getRealPath(RESOURCE);
			final String resourcePath = RESOURCE.concat(fileName);
			final String filePath = String.format("%s\\%s", realPath, fileName);
			final OutputStream stream = new FileOutputStream(filePath);
		    stream.write(file);
		    stream.close();
		    return resourcePath;
		} catch (final IOException exception) {
			logger.error("Falha ao tentar gravar o arquivo no disco", exception);
			throw new FileManagerException("Fail to write file with name: " + fileName, exception);
		}
	}
	
	public void delete(final String fileName) {
		final File file = new File(context.getRealPath(RESOURCE + fileName));
		if(!file.exists()) {
			throw new FileNotFoundException("File with name: " + fileName + " not found from server");
		}
		file.delete();
	}

}