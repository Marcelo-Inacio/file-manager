/*
 * @(#)FileManagerController.java 1.0 1 02/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.filemanager.restapi;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.mapskills.filemanager.application.FileManagerApplicationServices;
import br.gov.sp.fatec.mapskills.filemanager.application.FileManagerException;
import br.gov.sp.fatec.mapskills.filemanager.restapi.wrapper.FileContextWrapper;
import lombok.AllArgsConstructor;

/**
 * A classe {@link FileManagerController}
 *
 * @author Marcelo
 * @version 1.0 02/09/2017
 */
@RestController
@AllArgsConstructor
public class FileManagerController {
	
	private final FileManagerApplicationServices applicationServices;
	
	/**
	 * Endpoint para servico de criacao de arquivo. Para acessar o
	 * arquivo criado, basta recuperar a localizacao indicada no
	 * cabecalho <b>resource-location</b> encontrada no <i>response</i>.
	 * 
	 * @param contextWrapper Contexto para criacao do arquivo.
	 * @param request Requisicao para o recurso.
	 * @param response Resposta contendo a localizacao do arquivo.
	 */
	@PostMapping("/file")
	public void saveFile(@RequestBody final FileContextWrapper contextWrapper,
			final HttpServletRequest request, final HttpServletResponse response) {
		final String location = applicationServices.save(contextWrapper.getFileByteArray(), contextWrapper.getFileName());
		response.addHeader("resource-location", getRosourceLocation(location, request));
	}
	
	/**
	 * Endpoint
	 * 
	 * @param fileName
	 */
	@DeleteMapping("/file/{fileName:.+}")
	public void deleteFile(@PathVariable("fileName") final String fileName) {
		applicationServices.delete(fileName);
	}
	
	/**
	 * 
	 * @param resource
	 * @param request
	 * @return
	 */
	private String getRosourceLocation(final String resource, final HttpServletRequest request) {
		try {
			final String serverIP = InetAddress.getLocalHost().getHostAddress();
			final int serverPort = request.getLocalPort();
			return String.format("%s:%s%s", serverIP, serverPort, resource);
		} catch (final UnknownHostException exception) {
			throw new FileManagerException("did not possible get local host", exception);
		}
	}

}