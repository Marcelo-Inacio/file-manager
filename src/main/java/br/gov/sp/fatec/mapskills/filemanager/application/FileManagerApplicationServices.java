/*
 * @(#)FileHandleApplicationServices.java 1.0 1 02/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.filemanager.application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

	/**
	 * Template para diretorio dos arquivos.
	 * Ex.
	 * 	windows: /home/filemanager/source/scene00.jpg
	 *  linux: D:/filemanager/source/scene00.jpg
	 */
	private final String pathTemplate;
	
	public FileManagerApplicationServices(@Value("${file.path.template}") final String pathTemplate) {
		this.pathTemplate = pathTemplate;
	}
	
	/**
	 * Metodo resonsavel por salvar um recurso na aplicacao.
	 * 
	 * @param file
	 * 		Byte array do arquivo a ser salvo.
	 * @param filename
	 * 		Nome do arquivo com extensao a ser salvo.
	 * @return
	 * 		O caminho em nivel de diretorio do arquivo salvo.
	 */
	public String save(final byte[] file, final String filename) {
		try {
			Files.write(getPath(filename), file);
			return filename;
		} catch (final IOException exception) {
			logger.error("Falha ao tentar gravar o arquivo no disco", exception);
			throw new FileManagerException("Fail to write file with name: " + filename, exception);
		}
	}
	
	/**
	 * Metodo responsavel por remover recurso da aplicacao.
	 * 
	 * @param filename
	 * 		Nome arquivo com extensao.
	 */
	public void delete(final String filename) {
		final File file = getPath(filename).toFile();
		if(!file.exists()) {
			throw new FileNotFoundException("File with name: " + filename + " not found from server");
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
			final Path path = getPath(filename);
			logger.info(path.toString());
			return Files.readAllBytes(path);
		} catch (final IOException exception) {
			logger.warn("File: " + filename + " not found", exception);
			throw new FileNotFoundException("File with name: " + filename + " not found from server", exception);
		}
	}
	
	private Path getPath(final String filename) {
		return Paths.get(String.format(pathTemplate, filename));
	}
}