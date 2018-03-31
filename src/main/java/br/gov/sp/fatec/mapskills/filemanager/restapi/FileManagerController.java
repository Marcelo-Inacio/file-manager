/*
 * @(#)FileManagerController.java 1.0 1 02/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.filemanager.restapi;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.mapskills.filemanager.application.FileManagerApplicationServices;
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
	 * Endpoint para servico de salvar arquivo. Para acessar o
	 * arquivo salvo, basta recuperar a localizacao indicada no
	 * cabecalho <b>resource-location</b> encontrada no <i>response</i>.
	 * 
	 * @param contextWrapper
	 * 		Contexto para criacao do arquivo.
	 * @param response
	 * 		Resposta contendo a localizacao do arquivo.
	 */
	@PostMapping("/file")
	public void saveFile(@RequestBody final FileContextWrapper contextWrapper, final HttpServletResponse response) {
		final String location = applicationServices.save(contextWrapper.getFileByteArray(), contextWrapper.getFileName());
		response.addHeader("resource-location", getRosourceLocation(location));
	}
	
	/**
	 * Endpoint reponsavel por realizar uma atualizacao de arquivo no gerenciador,
	 * removendo o arquivo antigo e salvando o novo na aplicacao.
	 * 
	 * @param oldFileName
	 * 		Nome do arquivo com extensao a ser removido.
	 * @param contextWrapper
	 * 		Wrapper que encapsula o texto base64 do arquivo a ser salvo.
	 * @param response
	 * 		Response do servlet, onde sera adicionado no header atraves da chave
	 * 		<i>resource-location</i> o caminho do recurso criado.
	 */
	@PutMapping("/file/{filename:.*}")
	public void updateFile(@PathVariable("filename") final String oldFileName,
			@RequestBody final FileContextWrapper contextWrapper, final HttpServletResponse response) {
		final String location = applicationServices.updateFile(oldFileName, contextWrapper.getFileByteArray(), contextWrapper.getFileName());
		response.addHeader("resource-location", getRosourceLocation(location));
	}
	
	/**
	 * Endpoint responsavel por realizar a exclusao de um arquivo no gerenciador.
	 * 
	 * @param fileName
	 * 		Nome do arquivo com extensao a ser removido. ex.: imagem0001.png
	 */
	@DeleteMapping("/file/{filename:.*}")
	public void deleteFile(@PathVariable("filename") final String filename) {
		applicationServices.delete(filename);
	}
	
	@GetMapping(value = "/file/{filename:.*}")
	public HttpEntity<byte[]> getFile(@PathVariable("filename") final String filename) {
		final byte[] file = applicationServices.getFile(filename);
		return ResponseEntity.ok().contentType(getMediaType(filename)).body(file);
	}
	
	/**
	 * Metodo responsavel por montar o caminho de onde o recurso foi
	 * salvo na aplicacao.
	 * 
	 * @return
	 * 		O caminho para acesso do arquivo no servidor.
	 */
	private String getRosourceLocation(final String resource) {
		return String.format("/file/%s", resource);
	}
	
	private MediaType getMediaType(final String filename) {
		final Map<String, MediaType> mediaTypes = new HashMap<>(3);
		mediaTypes.put("png", MediaType.IMAGE_PNG);
		mediaTypes.put("jpg", MediaType.IMAGE_JPEG);
		mediaTypes.put("pdf", MediaType.APPLICATION_PDF);
		
		if (mediaTypes.containsKey(getExtension(filename))) {
			return mediaTypes.get(getExtension(filename));
		}
		return MediaType.APPLICATION_OCTET_STREAM;
	}
	
	private String getExtension(final String filename) {
		final int index = filename.lastIndexOf('.');
		return filename.substring(index + 1);
	}
}