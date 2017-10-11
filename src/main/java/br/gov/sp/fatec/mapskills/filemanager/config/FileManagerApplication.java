/*
 * @(#)FileManagerApplication.java 1.0 1 02/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.filemanager.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * A classe {@link FileManagerApplication}
 *
 * @author Marcelo
 * @version 1.0 02/09/2017
 */
@SpringBootApplication(scanBasePackages = { "br.gov.sp.fatec.mapskills.filemanager" })
public class FileManagerApplication {
	
	public static void main(final String[] args) {
		SpringApplication.run(FileManagerApplication.class, args);
	}

}