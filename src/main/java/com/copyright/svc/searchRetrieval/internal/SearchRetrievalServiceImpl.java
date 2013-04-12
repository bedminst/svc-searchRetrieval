/*
 * SearchRetrievalServiceImpl.java
 * Copyright (c) 2008, Copyright Clearance Center, Inc. All rights reserved.
 * ----------------------------------------------------------------------------
 * Revision History
 * 2009-12-21   lwojtowicz    Created.
 * 2011-07-18   dfahey		  Changes to support of the Consolidated Repository
 *                            effort - removal of the workRetirever service and
 *                            the work external oracle table
 * ----------------------------------------------------------------------------
 */

package com.copyright.svc.searchRetrieval.internal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.copyright.domain.data.RightExternal;
import com.copyright.domain.data.WorkExternal;
import com.copyright.domain.data.WorkTagExternal;
import com.copyright.mediadelivery.framework.ICoreMediaContext;
import com.copyright.mediadelivery.framework.api.ISearchParameter;
import com.copyright.mediadelivery.framework.internal.search.SearchParameters;
import com.copyright.mediadelivery.framework.internal.value.SourceValue;
import com.copyright.mediadelivery.framework.internal.value.TextSearchParameter;
import com.copyright.srs.core.utils.SearcherFields;
import com.copyright.srs.core.parser.CC2QueryParser;
import com.copyright.svc.indexSearcher.api.data.IndexEnum;
import com.copyright.svc.searchRetrieval.api.SearchRetrievalService;
import com.copyright.svc.searchRetrieval.api.data.ArticleSearch;
import com.copyright.svc.searchRetrieval.api.data.SrsLimiter;
import com.copyright.svc.searchRetrieval.api.data.PublicationSearch;
import com.copyright.svc.searchRetrieval.api.data.SearchRetrievalConsumerContext;
import com.copyright.svc.searchRetrieval.api.data.SearchRetrievalResult;
import com.copyright.svc.searchRetrieval.internal.persist.SearchRetrievalDAO;
import com.copyright.svc.searchRetrieval.internal.persist.TitleAutosuggestIndexDAO;

/**
 * Standard implementation of CatalogClient that uses the apache SOLR HTTP client
 * for accessing a SOLR/Lucene index.
 *
 */
public class SearchRetrievalServiceImpl implements SearchRetrievalService
{
	private SearchRetrievalDAO searchRetrievalDao;
	private TitleAutosuggestIndexDAO titleAutosuggestIndexDao;

	private Logger log = Logger.getLogger(getClass());
    private ICoreMediaContext context;
    private boolean updateIndex = true;
    
    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
	public List<RightExternal> searchRights(SearchRetrievalConsumerContext consumerContext, List<String> rgtInsts, String rghInst, int page, int pageSize)
	{
		validateContext( consumerContext );
			
		return searchRetrievalDao.searchRights(rgtInsts, rghInst, page, pageSize);
	}
	
	public SearchRetrievalResult searchPublication(SearchRetrievalConsumerContext consumerContext, PublicationSearch pubSearch, List<String> sortList, int page, int pageSize)
	{
		validateContext( consumerContext );

		SearchParameters params = loadSearchParameters(pubSearch, page, pageSize);
		
		// attach sort order
		for(String sort: sortList)
		{
			params.addSortOrder(sort);
		}
		
		List<WorkExternal> allWorks = new ArrayList<WorkExternal>();

		int totalCount = searchRetrievalDao.search(context, params, allWorks);
		
		if(totalCount > 0 && updateIndex)
		{
			String title = pubSearch.getPubTitleOrStdNumber();
			/*
			 * Only try to add to the autosuggest index if the title is present...
			 */
			if(title != null && !title.isEmpty())
			{
				titleAutosuggestIndexDao.addTitleToIndex(title);
			}
		}
		
		return fetchPublicationsForPage(allWorks, totalCount, page, pageSize);
	}	
	
	public SearchRetrievalResult searchArticle(SearchRetrievalConsumerContext consumerContext, ArticleSearch artSearch, WorkExternal publication, List<String> sortList, int page, int pageSize)
	{
		validateContext( consumerContext );
		
		SearchParameters params = loadSearchParameters(artSearch, publication, page, pageSize);

		// attach sort order
		for(String sort: sortList)
		{
			params.addSortOrder(sort);
		}

		// this will hold the result of the query
		List<WorkExternal> allWorks = new ArrayList<WorkExternal>();

		int totalCount = searchRetrievalDao.search(context, params, allWorks);
		
		return fetchArticlesForPage(allWorks, publication, totalCount, page, pageSize);
	}	
	
	public List<SrsLimiter> itemsForField( SearchRetrievalConsumerContext consumerContext, String fieldName)
	{
		validateContext( consumerContext );
		
		return searchRetrievalDao.getItemsForField(fieldName);
	}

	public List<String> fuzzyTitlesForQuery(SearchRetrievalConsumerContext consumerContext, String query, int numberOfItems, int count)
	{
		validateContext( consumerContext );

		return titleAutosuggestIndexDao.queryTitleAutosuggestIndex(query, numberOfItems, count);
	}

	protected void validateContext( SearchRetrievalConsumerContext consumerContext )
	{
		if ( consumerContext == null ) 
		{
			throw new IllegalArgumentException( "SearchRetrievalConsumerContext must not be null" );
		}
	}
	
	/**
	 * @return the log
	 */
	public Logger getLog() {
		return log;
	}

	/**
	 * @param log the log to set
	 */
	public void setLog(Logger log) {
		this.log = log;
	}

	/**
	 * @return the dao
	 */
	public SearchRetrievalDAO getSearchRetrievalDao() {
		return searchRetrievalDao;
	}

	/**
	 * @param dao the dao to set
	 */
	public void setSearchRetrievalDao(SearchRetrievalDAO dao) {
		this.searchRetrievalDao = dao;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(ICoreMediaContext context) {
		this.context = context;
	}

	/**
	 * @return the context
	 */
	public ICoreMediaContext getContext() {
		return context;
	}

	public TitleAutosuggestIndexDAO getTitleAutosuggestIndexDao() {
		return titleAutosuggestIndexDao;
	}

	public void setTitleAutosuggestIndexDao(
			TitleAutosuggestIndexDAO titleAutosuggestIndexDao) {
		this.titleAutosuggestIndexDao = titleAutosuggestIndexDao;
	}

	/*
	 * Private methods...
	 */
	
	private SearchParameters loadSearchParameters(PublicationSearch pubSearch, int page, int pageSize)
	{
		SearchParameters searchParams = new SearchParameters();
		List<ISearchParameter> parameterList = new LinkedList<ISearchParameter>();
		
		// publication search is also allowing wr_wrk_inst searching...
		if(pubSearch.getWrWrkInst() != null && !pubSearch.getWrWrkInst().isEmpty())
		{
			ISearchParameter param = new TextSearchParameter(pubSearch.getWrWrkInst(), ISearchParameter.Operator.AND_JOINED, SearcherFields.WR_WRK_INST);
			parameterList.add(param);
		}
		
		// publication search is also allowing tf_wrk_inst searching...
		if(pubSearch.getTfWrkInst() != null && !pubSearch.getTfWrkInst().isEmpty())
		{
			ISearchParameter param = new TextSearchParameter(pubSearch.getTfWrkInst(), ISearchParameter.Operator.AND_JOINED, SearcherFields.TF_WRK_INST);
			parameterList.add(param);
		}
		
		if(pubSearch.getHasRights() != null && !pubSearch.getHasRights().isEmpty())
		{
			// set up a limiter for processing only works with rights.
			ISearchParameter param = new TextSearchParameter(pubSearch.getHasRights(), ISearchParameter.Operator.AND_JOINED, SearcherFields.PUB_HAS_RIGHTS);
			parameterList.add(param);
		}
		
		// set search parameters
		CC2QueryParser cc2QP = new CC2QueryParser(parameterList);
		
		cc2QP.processField(pubSearch.getAuthorOrEditor(), SearcherFields.PUB_AUTHOR_OR_EDITOR);
		cc2QP.processField(pubSearch.getPubTitleOrStdNumber(), SearcherFields.PUB_PUBTITLE_OR_STDNUMBER);
		cc2QP.processField(pubSearch.getPublisherOrRightsholder(), SearcherFields.PUB_PUBLISHER_OR_RIGHTSHOLDER);
		cc2QP.processField(pubSearch.getSeriesName(), SearcherFields.PUB_SERIESNAME);
		cc2QP.processField(pubSearch.getCountry(), SearcherFields.PUB_COUNTRY);
		cc2QP.processField(pubSearch.getLanguage(), SearcherFields.PUB_LANGUAGE);
		cc2QP.processField(pubSearch.getPubType(), SearcherFields.PUB_PUBTYPE);
		
		// hook up the parameter list...
		searchParams.setParameters(parameterList);
		
		// sources - only WR, initialized in the constructor of PublicationSearch
		for(String sourceName : pubSearch.getSourceNames())
		{
			searchParams.getSelectedSources().add(new SourceValue(sourceName));
		}
		
		// always ask MDS for all records up to and including the requested page
		int targetResultCount = page * pageSize;
		int startingIndex = 1;
		log.info("loadSearchParameters(): Requesting page size " + targetResultCount + " starting from page " + startingIndex);

		searchParams.setTargetResultCount(targetResultCount);
		searchParams.setStartingIndex(startingIndex);

		searchParams.setMode("SEARCH");
		
		searchParams.setAttribute("index", IndexEnum.WORKS.getSourceName());
		
		return searchParams;
	}
	
	private SearchParameters loadSearchParameters(ArticleSearch artSearch, WorkExternal publication, int page, int pageSize)
	{
		SearchParameters searchParams = new SearchParameters();
		List<ISearchParameter> parameterList = new LinkedList<ISearchParameter>();
		
		// article search is also allowing wr_wrk_inst searching...
		if(artSearch.getWrWrkInst() != null && !artSearch.getWrWrkInst().isEmpty())
		{
			ISearchParameter param = new TextSearchParameter(artSearch.getWrWrkInst(), ISearchParameter.Operator.AND_JOINED, SearcherFields.WR_WRK_INST);
			parameterList.add(param);
		}
		
		// set search parameters
		CC2QueryParser cc2QP = new CC2QueryParser(parameterList);

		cc2QP.processField(artSearch.getAuthor(), SearcherFields.ART_AUTHOR);
		cc2QP.processField(artSearch.getArtTitle(), SearcherFields.ART_ARTTITLE);
		cc2QP.processField(artSearch.getArtIdno(), SearcherFields.ART_IDNO);
		cc2QP.processField(artSearch.getDate(), SearcherFields.ART_DATE);
		cc2QP.processField(artSearch.getPubType(), SearcherFields.ART_PUBTYPE);
		// new added in Aug 2010
		cc2QP.processField(artSearch.getItemVolume(), SearcherFields.ART_ITEM_VOLUME);
		cc2QP.processField(artSearch.getItemIssue(), SearcherFields.ART_ITEM_ISSUE);
		cc2QP.processField(artSearch.getItemStartPage(), SearcherFields.ART_ITEM_START_PAGE);
		
		addHostIdnos(publication, cc2QP);
		
		// hook up the parameter list...
		searchParams.setParameters(parameterList);
		
		// sources
		if(publication.getSrsTagList() != null)
		{
			for(WorkTagExternal sourceName : publication.getSrsTagList())
			{
				// TODO add source for searching only if dates are matching - ask Amber!
				searchParams.getSelectedSources().add(new SourceValue(sourceName.getTagName()));
			}
		}

		// always ask MDS for all records up to and including the requested page
		int targetResultCount = page * pageSize;
		int startingIndex = 1;
		log.info("loadSearchParameters(): Requesting page size " + targetResultCount + " starting from page " + startingIndex);

		searchParams.setTargetResultCount(targetResultCount);
		searchParams.setStartingIndex(startingIndex);

		searchParams.setMode("SEARCH");
		
		searchParams.setAttribute("index", IndexEnum.WORKS.getSourceName());

		return searchParams;
	}

	
	/**
	 * Retrieve the list of ids for the works that belong on the specified logical page
	 * 
	 * @param allWorks List of all works
	 * @param totalCount The total count of matching works
	 * @param page The logical page
	 * @param pageSize The page size
	 * @param result SearchRetrievalResult class
	 * 
	 * @return A list of work identifiers
	 * 
	 * @throws Exception
	 */
	private SearchRetrievalResult fetchPublicationsForPage( List<WorkExternal> allWorks, int totalCount, int page, int pageSize)
	{
		// this will hold the result of the query
		SearchRetrievalResult searchResult = new SearchRetrievalResult();

		List<WorkExternal> requestedPage = new ArrayList<WorkExternal>();
		
		int firstRecord = ((page - 1) * pageSize) + 1;
		int lastRecord = page * pageSize;			// there may not be a full page, but that's max
		int index = 0;
		for(WorkExternal workValue : allWorks)
		{
			++index;
			if(index > lastRecord)
			{
				break;
			}
			if(index < firstRecord)
			{
				continue;
			}
			requestedPage.add(workValue);
		}
		
		searchResult.setWorks(requestedPage);
		searchResult.setResultCount(totalCount);
		
		searchResult.setPage(page);
		searchResult.setPageSize(pageSize);
		searchResult.setLastPage((page * pageSize >= totalCount) ? true : false);
		
		return searchResult;
	}

	private SearchRetrievalResult fetchArticlesForPage( List<WorkExternal> allWorks, WorkExternal publication, int totalCount, int page, int pageSize)
	{
		// this will hold the result of the query
		SearchRetrievalResult searchResult = new SearchRetrievalResult();

		List<WorkExternal> requestedPage = new ArrayList<WorkExternal>();
		
		int firstRecord = ((page - 1) * pageSize) + 1;
		int lastRecord = page * pageSize;			// there may not be a full page, but that's max
		int index = 0;
		for(WorkExternal workValue : allWorks)
		{
			++index;
			if(index > lastRecord)
			{
				break;
			}
			if(index < firstRecord)
			{
				continue;
			}
			requestedPage.add(workValue);
		}
		
		/*
		 * In case of article level search, the response is a single work - publication, the same one that was passed by the client in request,
		 * populated with subWorks which are a result of the article level search.
		 */
		
		List<WorkExternal> publications = new ArrayList<WorkExternal>();
		publication.setSubWorks(requestedPage);
		publications.add(publication);
		
		searchResult.setWorks(publications);
		searchResult.setResultCount(totalCount);
		
		searchResult.setPage(page);
		searchResult.setPageSize(pageSize);
		searchResult.setLastPage((page * pageSize >= totalCount) ? true : false);
		
		return searchResult;
	}

	/*
	 * NOTE: Idno from publication becomes hostIdno for article search!
	 * Now there are also idnos coming from other_format_idno element
	 * and they all need to be reflected when searching articles (logically ORed).
	 */
	private void addHostIdnos(WorkExternal publication, CC2QueryParser cc2QP)
	{
		Set<String> uniqueHostIdnos = new HashSet<String>();

		if(publication.getIdnoWop() != null)
		{
			String[] list1 = publication.getIdnoWop().split("\\|");
			for(String hostIdno : list1)
			{
				if(!hostIdno.isEmpty())
				{
					uniqueHostIdnos.add(hostIdno);
				}
			}
		}
		if(publication.getOtherFormatIdnos() != null)
		{
			String[] list2 = publication.getOtherFormatIdnos().split("\\|");
			for(String hostIdno : list2)
			{
				if(!hostIdno.isEmpty())
				{
					uniqueHostIdnos.add(hostIdno);
				}
			}
		}
		
		if(uniqueHostIdnos.size() == 0)
		{
			return;
		}
		
		String orListOfHostIdnos = "";
		for(String hostIdno : uniqueHostIdnos)
		{
			if(orListOfHostIdnos.length() > 0)
			{
				orListOfHostIdnos += " OR ";
			}
			orListOfHostIdnos += hostIdno;
		}
		
		// finally, pass this for searching
		cc2QP.processField(orListOfHostIdnos, SearcherFields.ART_HOSTIDNO);
		log.debug("addHostIdnos(): orListOfHostIdnos: " + orListOfHostIdnos);
	}
	
	public boolean isUpdateIndex() {
		return updateIndex;
	}

	public void setUpdateIndex(boolean updateIndex) {
		this.updateIndex = updateIndex;
	}

}
