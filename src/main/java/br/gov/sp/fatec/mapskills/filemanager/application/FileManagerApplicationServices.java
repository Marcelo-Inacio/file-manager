/*
 * @(#)FileHandleApplicationServices.java 1.0 1 02/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.filemanager.application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

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
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final List<String> EXTENSIONS_ALLOWED = Arrays.asList(".png", ".jpg", ".pdf");
	
	private static final String FILE_NOT_FOUND = "File: %s not found from server";

	/**
	 * Template para diretorio dos arquivos.
	 * 
	 * Ex.
	 * 	linux: /home/filemanager/source/scene00.jpg
	 *  windows: D:/filemanager/source/scene00.jpg
	 */
	private final String pathTemplate;
	
	public FileManagerApplicationServices(@Value("${file.path.template}") final String pathTemplate) {
		this.pathTemplate = pathTemplate;
	}
	
	/**
	 * Resonsavel por salvar um recurso na aplicacao.
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
			validateFileExtension(filename);
			Files.write(getPath(filename), file);
			return filename;
		} catch (final IOException exception) {
			logger.error("Failed to write file " + filename + " to disk", exception);
			throw new FileManagerException("Failed to write file with name: " + filename, exception);
		}
	}

	/**
	 * Responsavel por remover recurso da aplicacao.
	 * 
	 * @param filename
	 * 		Nome arquivo com extensao.
	 */
	public void delete(final String filename) {
		try {
			Files.delete(getPath(filename));
		} catch (final IOException exception) {
			logger.info(String.format(FILE_NOT_FOUND, filename));
			throw new FileNotFoundException(String.format(FILE_NOT_FOUND, filename), exception);
		}
	}

	/**
	 * Responsavel por realizar a atualizacao de um recurso da aplicacao,
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
	public String updateFile(final String oldFileName, final byte[] fileByteArray, final String filename) {
		try {
			delete(oldFileName);
		} catch(final FileNotFoundException exception) {
			logger.info(String.format(FILE_NOT_FOUND, filename), exception);
		}
		return save(fileByteArray, filename);
	}

	/**
	 * Responsavel por recuperar um arquivo salvo na aplicacao.
	 * 
	 * @param filename
	 * 		Nome do arquivo a ser recuperado.
	 * @return
	 * 		Array de bytes do arquivo encontrado.
	 */
	public byte[] getFile(final String filename) {
		try {
			final Path path = getPath(filename);
			return Files.readAllBytes(path);
		} catch (final IOException exception) {
			logger.info(String.format(FILE_NOT_FOUND, filename), exception);
			throw new FileNotFoundException(String.format(FILE_NOT_FOUND, filename), exception);
		}
	}
	
	/**
	 * Recuperar <i>Path</i> do arquivo.
	 * 
	 * @param filename
	 * 		Nome do arquivo.
	 * @return
	 */
	private Path getPath(final String filename) {
		return Paths.get(String.format(pathTemplate, filename));
	}	
	
	/**
	 * Responsavel por validar a extensão do arquivo.
	 * 
	 * @param filename
	 * 		Nome do arquivo.
	 */
	private void validateFileExtension(final String filename) {		
		if (EXTENSIONS_ALLOWED.stream().noneMatch(extension -> filename.endsWith(extension))) {
			throw new FileNotAllowedException(String.format("File: %s not allowed.", filename)
					.concat(" Only file with .png, .jpg or .pdf extensions"));
		}
	}
}