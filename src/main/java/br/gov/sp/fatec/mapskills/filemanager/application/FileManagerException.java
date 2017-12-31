/*
 * @(#)FileManagerException.java 1.0 1 03/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.filemanager.application;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * A classe {@link FileManagerException} representa as
 * excecoes geradas pela aplicacao.
 *
 * @author Marcelo
 * @version 1.0 03/09/2017
 */

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FileManagerException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public FileManagerException(final String message) {
		super(message);
	}
	
	public FileManagerException(final String message, final Throwable throwable) {
		super(message, throwable);
	}
}