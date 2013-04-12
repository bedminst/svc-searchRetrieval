/*
 * SearchRetrievalConsumerContext.java
 * Copyright (c) 2009, Copyright Clearance Center, Inc. All rights reserved.
 * ----------------------------------------------------------------------------
 * Revision History
 * 2009-10-15   lwojtowicz    Created.
 * ----------------------------------------------------------------------------
 */
package com.copyright.svc.searchRetrieval.api.data;

import com.copyright.svc.common.consumer.ConsumerContextBase;

/**
 * The client-side Search Retrieval Service execution context.
 *   
 * @author lwojtowicz
 */
public class SearchRetrievalConsumerContext extends ConsumerContextBase
{
	private static final long serialVersionUID = 1L;
	
	public SearchRetrievalConsumerContext()
	{
		super();
	}
	
	public SearchRetrievalConsumerContext( String appCode, String auid )
	{
		super( appCode, auid );
	}
}
