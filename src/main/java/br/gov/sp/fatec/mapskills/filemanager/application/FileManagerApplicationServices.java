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
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * A classe {@link ApplicationServices} contem os servicos
 * para gerenciamento de arquivos manipulados pela aplicacao.
 *
 * @author Marcelo Inacio
 * @version 1.0 02/09/2017
 */
@ApplicationServices
public class FileManagerApplicationServices {
	
	private static final Logger logger = LoggerFactory.getLogger(FileManagerApplicationServices.class);
	
	/** Diretorio de onde serao salvos os arquivos. */
	private static final String RESOURCE = "/drive/";

	/**
	 * Template para diretorio dos arquivos.
	 * Ex.
	 * 	windows: /drive\scene00.jpg
	 *  linux: /drive/scene00.jpg
	 */
	private final String pathTemplate;
	
	private final ServletContext context;
	
	public FileManagerApplicationServices(@Value("${file.path.template}") final String pathTemplate,
			final ServletContext context) {
		this.pathTemplate = pathTemplate;
		this.context = context;
	}
	
	/**
	 * Metodo resonsavel por salvar um recurso na aplicacao.
	 * 
	 * @param file
	 * 		Byte array do arquivo a ser salvo.
	 * @param fileName
	 * 		Nome do arquivo com extensao a ser salvo.
	 * @return
	 * 		O caminho em nivel de diretorio do arquivo salvo.
	 */
	public String save(final byte[] file, final String fileName) {
		try {
			final String realPath = context.getRealPath(RESOURCE);
			final String resourcePath = RESOURCE.concat(fileName);
			final String filePath = String.format(pathTemplate, realPath, fileName);
			final OutputStream stream = new FileOutputStream(filePath);
		    stream.write(file);
		    stream.close();
		    return resourcePath;
		} catch (final IOException exception) {
			logger.error("Falha ao tentar gravar o arquivo no disco", exception);
			throw new FileManagerException("Fail to write file with name: " + fileName, exception);
		}
	}
	
	/**
	 * Metodo responsavel por remover recurso da aplicacao.
	 * 
	 * @param fileName
	 * 		Nome arquivo com extensao.
	 */
	public void delete(final String fileName) {
		final File file = new File(context.getRealPath(RESOURCE + fileName));
		if(!file.exists()) {
			throw new FileNotFoundException("File with name: " + fileName + " not found from server");
		}
		file.delete();
	}

	/**
	 * Metodo responsavel por realizar a atualizacao de um recurso da aplicacao,
	 * removendo o recurso antigo, e salvando o novo na aplicacao.
	 * 
	 * @param oldFileName
	 * 		Nome do arquivo com extensao a ser removido.
	 * @param fileByteArray
	 * 		Array de bytes do arquivo a ser salvo.
	 * @param fileName
	 * 		Nome do arquivo com extensao a ser salvo.
	 * @return
	 * 		O caminho em nivel de diretorio de onde o recurso foi salvo.
	 */
	public String updateFile(final String oldFileName, final byte[] fileByteArray, final String fileName) {
		delete(oldFileName);
		return save(fileByteArray, fileName);
	}

	/**
	 * Metodo responsavel por recuperar um arquivo salvo na aplicacao.
	 * 
	 * @param filename
	 * 		Nome do arquivo a ser recuperado.
	 * @return
	 * 		Array de bytes do arquivo encontrado.
	 */
	public byte[] getFile(final String filename) {
		try {
			return Files.readAllBytes(Paths.get(context.getRealPath(RESOURCE + filename)));
		} catch (final IOException exception) {
			throw new FileNotFoundException("File with name: " + filename + " not found from server", exception);
		}
	}
}