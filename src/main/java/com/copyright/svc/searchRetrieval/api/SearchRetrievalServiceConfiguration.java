/*
 * SearchRetrievalServiceConfiguration.java
 * Copyright (c) 2009, Copyright Clearance Center, Inc. All rights reserved.
 * ----------------------------------------------------------------------------
 * Revision History
 * 2009-10-12   lwojtowicz    Created.
 * ----------------------------------------------------------------------------
 */
package com.copyright.svc.searchRetrieval.api;

import org.apache.commons.lang.SystemUtils;

import com.copyright.base.config.ClasspathConfiguration;

/**
 * Configuration properties for SearchRetrieval Service.
 * 
 * @author lwojtowicz
 *
 */
public class SearchRetrievalServiceConfiguration extends ClasspathConfiguration
{
	private static final long serialVersionUID = 1L;
	
	public static final String CONFIG_FILE_NAME = "SearchRetrievalService.properties";

	// Config file is not in top-level config directory.  So build up the 
	// relative path within the proscribed MDS config hierarchy.
	private static final String CONFIG_FILE_PATH = 
			System.getProperty( "mds.configKey" ) + SystemUtils.FILE_SEPARATOR +
			"properties" + SystemUtils.FILE_SEPARATOR +
			CONFIG_FILE_NAME;
	
	private static SearchRetrievalServiceConfiguration sInstance;

	private SearchRetrievalServiceConfiguration()
	{
		super( CONFIG_FILE_PATH );
	}
	
	public static SearchRetrievalServiceConfiguration getInstance()
	{
		String s = System.getProperty( "config.dir" ) + SystemUtils.FILE_SEPARATOR + 
			System.getProperty( "mds.configKey" ) + SystemUtils.FILE_SEPARATOR +
			"properties" + SystemUtils.FILE_SEPARATOR +
			CONFIG_FILE_NAME;
		
		if ( sInstance == null ) sInstance = new SearchRetrievalServiceConfiguration();
		
		return sInstance;
	}
	
	public String getSolrWorksUrl()
	{
		return getString( "solr.works.url" );
	}
}
