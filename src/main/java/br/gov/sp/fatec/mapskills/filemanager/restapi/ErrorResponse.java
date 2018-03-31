/*
 * @(#)ErrorResponse.java 1.0 1 28/10/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.filemanager.restapi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;

/**
 * A classe {@link ErrorResponse} e responsavel
 * por interceptar excecoes lancadas pela aplicacao,
 * trata-las e retorna-las formatadas no corpo da resposta.
 *
 * @author Marcelo
 * @version 1.0 28/10/2017
 */
public class ErrorResponse {
	
	private final HttpStatus status;
	private final String message;
	
	public ErrorResponse(final HttpStatus status, final String message) {
		this.status = status;
		this.message = message;
	}
	
	public int getStatusCode() {
		return status.value();
	}
	
	public String getStatusError() {
		return status.name();
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getTimestamp() {
		final LocalDateTime timestamp = LocalDateTime.now();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return timestamp.format(formatter);
	}
}