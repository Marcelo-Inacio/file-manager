/*
 * @(#)FileNotFoundException.java 1.0 1 13/10/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.filemanager.application;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * A classe {@link FileNotFoundException} e lancada
 * quando ao tentar recuperar um arquivo em disco,
 * o mesmo nao e encontrado.
 *
 * @author Marcelo
 * @version 1.0 13/10/2017
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileNotFoundException extends FileManagerException {

	private static final long serialVersionUID = 1L;

	public FileNotFoundException(final String message, final Exception exception) {
		super(message, exception);
	}
}