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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.mapskills.filemanager.application.FileManagerApplicationServices;
import br.gov.sp.fatec.mapskills.filemanager.application.FileManagerException;
import br.gov.sp.fatec.mapskills.filemanager.restapi.wrapper.FileContextWrapper;
import lombok.AllArgsConstructor;

/**
 * A classe {@link FileManagerController} contem os endpoints
 * referentes ao gerenciamento de arquivos disponiveis na aplicacao.
 *
 * @author Marcelo
 * @version 1.0 02/09/2017
 */
@RestController
@AllArgsConstructor
public class FileManagerController {
	
	/** Objeto de servico da aplicacao. */
	private final FileManagerApplicationServices applicationServices;
	
	/**
	 * Endpoint para servico de criacao de arquivo. Para acessar o
	 * arquivo criado, basta recuperar a localizacao indicada no
	 * cabecalho <b>resource-location</b> encontrada no <i>response</i>.
	 * 
	 * @param contextWrapper
	 * 		Contexto para criacao do arquivo.
	 * @param request
	 * 		Requisicao para o recurso.
	 * @param response
	 * 		Resposta contendo a localizacao do arquivo.
	 */
	@PostMapping("/file")
	public void saveFile(@RequestBody final FileContextWrapper contextWrapper,
			final HttpServletRequest request, final HttpServletResponse response) {
		final String location = applicationServices.save(contextWrapper.getFileByteArray(), contextWrapper.getFileName());
		response.addHeader("resource-location", getRosourceLocation(location, request));
	}
	
	/**
	 * Endpoint reponsavel por realizar uma atualizacao de arquivo no gerenciador,
	 * removendo o arquivo antigo e salvando o novo na aplicacao.
	 * 
	 * @param oldFileName
	 * 		Nome do arquivo com extensao a ser removido.
	 * @param contextWrapper
	 * 		Wrapper que encapsula o texto base64 do arquivo a ser salvo.
	 * @param request
	 * 		Request do servlet para que possa ser montado o caminho do recurso.
	 * @param response
	 * 		Response do servlet, onde sera adicionado no header atraves da chave
	 * 		<i>resource-location</i> o caminho do recurso criado.
	 */
	@PutMapping("/file/{filename:.+}")
	public void updateFile(@PathVariable("filename") final String oldFileName,
			@RequestBody final FileContextWrapper contextWrapper,
			final HttpServletRequest request, final HttpServletResponse response) {
		final String location = applicationServices.updateFile(oldFileName, contextWrapper.getFileByteArray(), contextWrapper.getFileName());
		response.addHeader("resource-location", getRosourceLocation(location, request));
	}
	
	/**
	 * Endpoint responsavel por realizar a exclusao de um arquivo no gerenciador.
	 * 
	 * @param fileName
	 * 		Nome do arquivo com extensao a ser removido. ex.: imagem0001.png
	 */
	@DeleteMapping("/file/{filename:.+}")
	public void deleteFile(@PathVariable("filename") final String fileName) {
		applicationServices.delete(fileName);
	}
	
	/**
	 * Metodo responsavel por montar o caminho de onde o recurso foi
	 * salvo na aplicacao.
	 * 
	 * @param resource
	 * 		Local no diretorio onde se encontra o recurso.
	 * @param request
	 * 		Requisicao para recuperar a porta do servidor.
	 * @return
	 * 		O caminho completo no servidor de onde se encontra o recurso.
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