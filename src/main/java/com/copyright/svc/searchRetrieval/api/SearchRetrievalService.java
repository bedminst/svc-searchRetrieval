/*
 * SearchRetrievalService.java
 * Copyright (c) 2009, Copyright Clearance Center, Inc. All rights reserved.
 * ----------------------------------------------------------------------------
 * Revision History
 * 2009-12-21   lwojtowicz    Created.
* 2011-07-18   dfahey		  Changes to support of the Consolidated Repository
 *                            effort - removal of the workRetirever service and
 *                            the work external oracle table
 * ----------------------------------------------------------------------------
 */

package com.copyright.svc.searchRetrieval.api;

import java.util.List;

import com.copyright.domain.data.RightExternal;
import com.copyright.domain.data.WorkExternal;
import com.copyright.svc.common.introspector.Service;
import com.copyright.svc.common.introspector.ServiceArguments;
import com.copyright.svc.searchRetrieval.api.data.ArticleSearch;
import com.copyright.svc.searchRetrieval.api.data.SrsLimiter;
import com.copyright.svc.searchRetrieval.api.data.PublicationSearch;
import com.copyright.svc.searchRetrieval.api.data.SearchRetrievalConsumerContext;
import com.copyright.svc.searchRetrieval.api.data.SearchRetrievalResult;

/**
 * Main interface for querying data from the Works catalog
 * 
 */
@Service
public interface SearchRetrievalService
{

	/**
	 * Main method for performing a query against the rights catalog
	 * 
	 * @param consumerContext A context for this service
	 * @param rgtInsts A list of right insts to search for
	 * @param rghInst A rightsholder inst to search for
	 * 
	 * @return A list of RightsExternal objects containing a logical page of hits for the query.
	 */
	@ServiceArguments(names={"consumerContext",
			"rgtInsts", 
			"rghInst",
			"page",
			"pageSize"})			
	public List<RightExternal> searchRights(SearchRetrievalConsumerContext consumerContext, List<String> rgtInsts, String rghInst, int page, int pageSize);

	/**
	 * Main method for performing a query against the catalog: local or remote.
	 * 
	 * @param consumerContext A context for this service
	 * @param pubSearch A structure with query elements
	 * @param sortList A list of sort fields (each must be an existing solr index field)
	 * @param page The logical page to fetch
	 * @param pageSize The size of the page to fetch
	 * in addition to the basic work attributes.
	 * 
	 * @return A SearchRetrievalResult object containing a logical page of hits for the query.
	 */
	@ServiceArguments(names={"consumerContext",
			"pubSearch", 
			"sortList",
			"page",
			"pageSize"})
	public SearchRetrievalResult searchPublication(SearchRetrievalConsumerContext consumerContext, PublicationSearch pubSearch, List<String> sortList, int page, int pageSize );

	/**
	 * Main method for performing a query against the catalog: local or remote.
	 * 
	 * @param consumerContext A context for this service
	 * @param artSearch A structure with query elements to search articles
	 * @param sortList A list of sort fields (each must be an existing solr index field)
	 * @param page The logical page to fetch
	 * @param pageSize The size of the page to fetch
	 * in addition to the basic work attributes.
	 * 
	 * @return A SearchRetrievalResult object containing a logical page of hits for the query.
	 */
	@ServiceArguments(names={"consumerContext",
			"artSearch",
			"publication",
			"sortList",
			"page",
			"pageSize"})
	public SearchRetrievalResult searchArticle(SearchRetrievalConsumerContext consumerContext, ArticleSearch artSearch, WorkExternal publication, List<String> sortList, int page, int pageSize);

	/**
	 * Method for obtaining all values of limiters in the requested field.
	 * 
	 * @param consumerContext A context for this service
	 * @param fieldName Name of the field to obtain limiters for.
	 * 
	 * @return A list of items (limiters values) for the requested field.
	 */
	@ServiceArguments(names={"consumerContext", "fieldName"})
	public List<SrsLimiter> itemsForField(SearchRetrievalConsumerContext consumerContext, String fieldName);

	@ServiceArguments(names={"consumerContext", "query", "numberOfItems", "count"})
	public List<String> fuzzyTitlesForQuery(SearchRetrievalConsumerContext consumerContext, String query, int numberOfItems, int count);
}
