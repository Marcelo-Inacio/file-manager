/*
 * @(#)FileNotAllowedException.java 1.0 1 31/03/2018
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.filemanager.application;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * A classe {@link FileNotAllowedException} eh lancada quando e tentado
 * salvar um arquivo com extensao invalida na aplicacao.
 *
 * @author Marcelo
 * @version 1.0 31/03/2018
 */
@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class FileNotAllowedException extends FileManagerException {

	/****/
	private static final long serialVersionUID = 1L;
	
	public FileNotAllowedException(final String message) {
		super(message);
	}

}