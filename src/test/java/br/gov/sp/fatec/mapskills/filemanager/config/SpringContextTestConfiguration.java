/*
 * @(#)SpringContextTestConfiguration.java 1.0 1 04/09/2017
 *
 * Copyright (c) 2017, Fatec-Jessen Vidal. All rights reserved.
 * Fatec-Jessen Vidal proprietary/confidential. Use is subject to license terms.
 */

package br.gov.sp.fatec.mapskills.filemanager.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * A classe {@link SpringContextTestConfiguration}
 *
 * @author Marcelo Inacio
 * @version 1.0 04/09/2017
 */
@Configuration
@PropertySource({"classpath:application.properties"})
@ComponentScan(basePackages  = "br.gov.sp.fatec.mapskills.filemanager")
public class SpringContextTestConfiguration {
}