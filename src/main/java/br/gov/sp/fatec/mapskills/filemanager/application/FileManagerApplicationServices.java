/*
 * @(#)FileHandleApplicationServices.java 1.0 1 02/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.filemanager.application;

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
	
	private final ServletContext context;
	
	public void save(final byte[] file, final String fileName) {
		final String path = context.getRealPath("/drive");
		try {
			final OutputStream stream = new FileOutputStream(path.concat("/" + fileName));
		    stream.write(file);
		    stream.close();
		} catch (final IOException exception) {
			logger.error("Falha ao tentar gravar o arquivo no disco", exception);
			throw new FileManagerException("Falha ao gravar " + fileName, exception);
		}
	}

}