/*
 * @(#)RestExceptionHandler.java 1.0 1 02/11/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.filemanager.restapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.gov.sp.fatec.mapskills.filemanager.application.FileManagerException;

/**
 * A classe {@link RestExceptionHandler} e responsavel
 * por interceptar uma excecao lancada pela aplicacao,
 * retornando-a formatada no corpo da resposta.
 *
 * @author Marcelo
 * @version 1.0 28/10/2017
 */
@RestControllerAdvice
public class RestExceptionHandler {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@ExceptionHandler(FileManagerException.class)
    protected ResponseEntity<ErrorResponse> mapSkillsExceptionHandle(final FileManagerException exception) {
		final ResponseStatus status = exception.getClass().getAnnotation(ResponseStatus.class);
        final ErrorResponse body = new ErrorResponse(status.value(), exception.getMessage());
        logger.error("Excecao: ", exception);
		return new ResponseEntity<>(body, status.value());
    }
}