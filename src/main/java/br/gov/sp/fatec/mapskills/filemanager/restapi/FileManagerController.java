/*
 * @(#)FileManagerController.java 1.0 1 02/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.filemanager.restapi;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.mapskills.filemanager.application.FileManagerApplicationServices;
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
	
	@PostMapping(value = "/file")
	public void saveImage(@RequestBody final FileContextWrapper contextWrapper) {
		
		applicationServices.save(contextWrapper.getFileByteArray(), contextWrapper.getFileName());
		
	}

}