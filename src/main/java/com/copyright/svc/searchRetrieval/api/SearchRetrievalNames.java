/*
 * SearchRetrievalNames.java
 * Copyright (c) 2008, Copyright Clearance Center, Inc. All rights reserved.
 * ----------------------------------------------------------------------------
 * Revision History
 * 2008-10-21   lwojtowicz    Created.
 * ----------------------------------------------------------------------------
 */

package com.copyright.svc.searchRetrieval.api;

/**
 * A collection of constants where each represents the name of a service. 
 * Ideally a service is set on it's dependent object by Spring. In some cases this
 * is not possible and instead we want to tell Spring to give us an instance of a
 * particular service. This describes using Spring as an "on-demand" factory.
 * 
 * @author lwojtowicz
 * @see com.copyright.svc.api.SearchRetrievalServiceLocator
 */
public class SearchRetrievalNames {
	public static final String 	SEARCHRETRIEVAL_SERVICE_BEAN = "searchRetrievalServiceBean";

}
