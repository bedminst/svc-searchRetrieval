/*
 * SearchRetrievalDAOImpl.java
 * Copyright (c) 2009, Copyright Clearance Center, Inc. All rights reserved.
 * ----------------------------------------------------------------------------
 * Revision History
 * 2009-10-21   lwojtowicz    Created.
 * 2011-07-18   dfahey		  Changes to support of the Consolidated Repository
 *                            effort - removal of the workRetirever service and
 *                            the work external oracle table
 * ----------------------------------------------------------------------------
 */

package com.copyright.svc.searchRetrieval.internal.persist;

/**
 * 
 * @author lwojtowicz
 * 
 */

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.copyright.workbench.logging.LogMessageHelper;
import com.copyright.base.svc.ServiceRuntimeException;
import com.copyright.domain.data.RightExternal;
import com.copyright.domain.data.WorkExternal;
import com.copyright.srs.connectors.cccwr.searcherclient.IndexSearcherClient;
import com.copyright.srs.core.index.InMemoryWork;
import com.copyright.srs.core.index.WorkMerger;
import com.copyright.svc.indexSearcher.api.data.Limiter;
import com.copyright.svc.searchRetrieval.api.data.SrsLimiter;
import com.copyright.svc.searchRetrieval.mock.MockSummitMediaContext.ProviderKeys;
import com.copyright.mediadelivery.framework.ICoreMediaContext;
import com.copyright.mediadelivery.framework.api.ISearchResults;
import com.copyright.mediadelivery.framework.api.IWorkValue;
import com.copyright.mediadelivery.framework.internal.search.SearchParameters;
import com.copyright.mediadelivery.svc.api.ISearchServices;

public class SearchRetrievalDAOImpl implements SearchRetrievalDAO 
{
	protected ISearchServices mSearchServices;
	protected IndexSearcherClient mSearcherClient;

	private Logger logger = Logger.getLogger( SearchRetrievalDAOImpl.class );

	/****************************************************************************
	 * Search/Retrieval related methods
	 **************************************************************************** */	

	public List<RightExternal> searchRights(List<String> rgtInsts, String rghInst, int page, int pageSize) 
	{
		List<RightExternal> rightExternals = mSearcherClient.searchRights(rgtInsts, rghInst, page, pageSize);
		
		return rightExternals;
	}
	
	public int search(ICoreMediaContext context, SearchParameters params, List<WorkExternal> allWorks)
	{
		long transactionStartTime = System.currentTimeMillis();
		if (logger.isDebugEnabled())
		{
			logger.debug(">>> search");
		}

		LogMessageHelper.logSuccessMessage(logger, "search", transactionStartTime);
		
		int totalCount = 0;
		
		if (params != null)
		{
			try
			{
					totalCount = getWorks(context, params, allWorks);
			}
			catch (ServiceRuntimeException ex)
			{
				throw new ServiceRuntimeException("Exception thrown during processing query: " + params.toString(), ex);
			}
		}

		return totalCount;
	}

	public List<SrsLimiter> getItemsForField(String fieldName) 
	{
		List<Limiter> fromWorkService = mSearcherClient.getItemsForField(fieldName);
		
		List<SrsLimiter> srsLimiterList = new ArrayList<SrsLimiter>();
		for(Limiter limiter : fromWorkService)
		{
			SrsLimiter srsLimiter = new SrsLimiter();
			srsLimiter.setItem(limiter.getItem());
			srsLimiterList.add(srsLimiter);
		}
		
		return srsLimiterList;
	}

	/**
	 * Retrieve the list of works from the back end services
	 * 
	 * @param context media context
	 * @param searchParams query, sort options, number of results to pull, etc. packaged into SearchParameters class
	 * @param searchResults all search results returned with this query
	 * 
	 * @return total count of works for this query
	 * 
	 */
	private int getWorks( ICoreMediaContext context, SearchParameters searchParams, List<WorkExternal> searchResults) 
	{
		/*
		 *  Perform the query
		 */
		ISearchResults results = mSearchServices.search(context, searchParams);
		
		int resultsCount = results.getTotalResultsAvailable();
		
		/*
		 *  There is no reason to merge/sort if results came from WR - they are ready for display
		 */
		if(results.getHandlerResults().size() == 1 && results.getHandlerResults().get(0).getProviderKey().compareToIgnoreCase(ProviderKeys.CCCWR) == 0)
		{
			if (results.getHandlerResults().get(0).getWorks() != null)
			{
				for(IWorkValue work : results.getHandlerResults().get(0).getWorks())
				{
					WorkExternal workExt = (WorkExternal)work.getAttribute("work_object");
					workExt.setProviderKey(work.getProviderKey());
					searchResults.add(workExt);
				}
			}
		}
		else
		{
			/*
			 *  Send results to the merger class and obtain the top N records according to the requested criteria:
			 *  SORT_RELEVANCE, SORT_DATE or SORT_TITLE.
			 *  This would only happen for articles - publication search is only WR.
			 */
			WorkMerger workMerger = new WorkMerger();
			List<InMemoryWork> topResults = workMerger.mergeWorks(results, searchParams);
			if (topResults != null)
			{
				for(InMemoryWork work : topResults)
				{
					WorkExternal workExt = work.getWorkExt();
					workExt.setProviderKey(work.getProviderKey());
					searchResults.add(workExt);
				}
			}
			resultsCount -= workMerger.getDuplicatesCount();
		}
		
		return resultsCount;
	}
	
	public ISearchServices getSearchServices() {
		return mSearchServices;
	}

	public void setSearchServices(ISearchServices searchServices) {
		mSearchServices = searchServices;
	}

	public IndexSearcherClient getSearcherClient() {
		return mSearcherClient;
	}

	public void setSearcherClient(IndexSearcherClient searcherClient) {
		mSearcherClient = searcherClient;
	}

}