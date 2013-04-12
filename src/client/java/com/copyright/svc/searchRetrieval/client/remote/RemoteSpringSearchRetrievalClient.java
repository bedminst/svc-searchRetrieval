/*
 * RemoteSpringSearchRetrievalClient.java
 * Copyright (c) 2009, Copyright Clearance Center, Inc. All rights reserved.
 * ----------------------------------------------------------------------------
 * Revision History
 * 2009-12-21   lwojtowicz    Created.
 * 2011-07-18   dfahey		  Changes to support of the Consolidated Repository
 *                            effort - removal of the workRetirever service and
 *                            the work external oracle table
 * ----------------------------------------------------------------------------
 */

package com.copyright.svc.searchRetrieval.client.remote;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.copyright.domain.data.WorkExternal;
import com.copyright.domain.data.WorkTagExternal;
import com.copyright.svc.searchRetrieval.api.SearchRetrievalService;
import com.copyright.svc.searchRetrieval.api.data.ArticleSearch;
import com.copyright.svc.searchRetrieval.api.data.PublicationSearch;
import com.copyright.svc.searchRetrieval.api.data.SearchRetrievalConsumerContext;
import com.copyright.svc.searchRetrieval.api.data.SearchRetrievalResult;
import com.copyright.svc.searchRetrieval.api.data.SrsLimiter;


/**
 * @author lwojtowicz
 *
 */
public class RemoteSpringSearchRetrievalClient {
	
	private SearchRetrievalService mSearchRetrievalService;
	private SearchRetrievalResult result;
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:com/copyright/svc/searchRetrieval/client/remote/RemoteSpringSearchRetrievalClientContext.xml");

		// Instantiate the search client bean and retrieve client bean
		RemoteSpringSearchRetrievalClient remoteSrsClient = (RemoteSpringSearchRetrievalClient) ctx.getBean("srs_client");

		// Get instance of WorksQueryBuilder and construct your query
		SearchRetrievalConsumerContext consumerCtx = new SearchRetrievalConsumerContext();
		
		pubSearch(remoteSrsClient, consumerCtx);

		artSearch(remoteSrsClient, consumerCtx);

		getAllCountries(remoteSrsClient, consumerCtx);
		
		pubArtSearch(remoteSrsClient, consumerCtx);

		System.out.println("Search completed.");
	}
	
	private static void pubSearch(RemoteSpringSearchRetrievalClient remoteSrsClient, SearchRetrievalConsumerContext consumerCtx)
	{
        // these are the inputs to our service call
        PublicationSearch pubSearch = new PublicationSearch();
        pubSearch.setPubTitleOrStdNumber("woody plants");
        pubSearch.setHasRights("Y");		// this is how CC2 will be requesting it...
        List<String> sortList = new ArrayList<String>();
        sortList.add(PublicationSearch.SORT_RELEVANCE);
        int page = 1;
        int pageSize = 10;

        // invoke the service
		System.out.println("Searching for '" + pubSearch.toString() + "', sorting by " + sortList.toString() + "...");
        SearchRetrievalResult result = remoteSrsClient.getSearchRetrievalService().searchPublication(consumerCtx, pubSearch, sortList, page, pageSize);

        // Process the result set
        System.out.println("Result count: " + result.getResultCount());
        System.out.println("Last Page: " + result.getLastPage());
        System.out.println("Number of records returned: " + result.getWorks().size());
        
		List<WorkExternal> works = result.getWorks();
		
		dumpWorks(works);
	}
	
	private static void artSearch(RemoteSpringSearchRetrievalClient remoteSrsClient, SearchRetrievalConsumerContext consumerCtx)
	{
        // these are the inputs to our service call
        ArticleSearch artSearch = new ArticleSearch();
        artSearch.setArtTitle("fertilizers");
        artSearch.setPubType("article");
        List<String> sortList = new ArrayList<String>();
        sortList.add(ArticleSearch.SORT_RELEVANCE);
        
        // prepare sources...
        List<WorkTagExternal> sources = new ArrayList<WorkTagExternal>();
        WorkTagExternal wt1 = new WorkTagExternal();
        WorkTagExternal wt2 = new WorkTagExternal();
        WorkTagExternal wt3 = new WorkTagExternal();
        WorkTagExternal wt4 = new WorkTagExternal();
        wt1.setTagName("NYT");
        wt2.setTagName("CCCWR");
        wt3.setTagName("NATURE");
        wt4.setTagName("PUBGET");
        sources.add(wt1);
        sources.add(wt2);
        sources.add(wt3);
        sources.add(wt4);
        
        int page = 1;
        int pageSize = 10;
        
        WorkExternal publication = new WorkExternal();
// this is a place to set up hostIdno if needed
//        publication.setHostIdno(hostIdno);
//        publication.setSrsTagList(sources);

        // invoke the service
		System.out.println("Searching for '" + artSearch.toString() + "', sorting by " + sortList.toString() + "...");
        SearchRetrievalResult result = remoteSrsClient.getSearchRetrievalService().searchArticle(consumerCtx, artSearch, publication, sortList, page, pageSize);

        // Process the result set
        System.out.println("Result count: " + result.getResultCount());
        System.out.println("Last Page: " + result.getLastPage());
        System.out.println("Number of records returned: " + result.getWorks().get(0).getSubWorks().size());
        
		List<WorkExternal> works = result.getWorks().get(0).getSubWorks();
		
		dumpWorks(works);
	}
	
	private static void pubArtSearch(RemoteSpringSearchRetrievalClient remoteSrsClient, SearchRetrievalConsumerContext consumerCtx)
	{
        // these are the inputs to our service call
        PublicationSearch pubSearch = new PublicationSearch();
        pubSearch.setPubTitleOrStdNumber("science education");
        pubSearch.setHasRights("Y");		// this is how CC2 will be requesting it...
        List<String> sortList = new ArrayList<String>();
        sortList.add(PublicationSearch.SORT_RELEVANCE);
        int page = 1;
        int pageSize = 1;

        // invoke the service
		System.out.println("Searching for '" + pubSearch.toString() + "', sorting by " + sortList.toString() + "...");
        SearchRetrievalResult result = remoteSrsClient.getSearchRetrievalService().searchPublication(consumerCtx, pubSearch, sortList, page, pageSize);

        // Process the result set
        System.out.println("Result count: " + result.getResultCount());
        System.out.println("Last Page: " + result.getLastPage());
        System.out.println("Number of records returned: " + result.getWorks().size());
        
		WorkExternal publication = result.getWorks().get(0);
		
		/*
		 * We have publication... now the article part!
		 */
        ArticleSearch artSearch = new ArticleSearch();
        artSearch.setArtTitle("impact of the mars curriculum");
        List<String> sortList2 = new ArrayList<String>();
        sortList2.add(ArticleSearch.SORT_TITLE);
        
        // invoke the service
		System.out.println("Searching for '" + artSearch.toString() + "', sorting by " + sortList2.toString() + "...");
        SearchRetrievalResult result2 = remoteSrsClient.getSearchRetrievalService().searchArticle(consumerCtx, artSearch, publication, sortList2, 1, 150);

        // Process the result set
        System.out.println("Result count: " + result2.getResultCount());
        System.out.println("Last Page: " + result2.getLastPage());
        System.out.println("Number of records returned: " + result2.getWorks().get(0).getSubWorks().size());
        
		List<WorkExternal> works = result2.getWorks().get(0).getSubWorks();
		
		dumpWorks(works);
	}
	
	
	private static void getAllCountries(RemoteSpringSearchRetrievalClient remoteSrsClient, SearchRetrievalConsumerContext consumerCtx)
	{
		List<SrsLimiter> limiters = remoteSrsClient.getSearchRetrievalService().itemsForField(consumerCtx, "language");
		for(SrsLimiter srsLim : limiters)
		{
			System.out.println( srsLim.getItem() );
		}
	}
	
	private static void dumpWorks(List<WorkExternal> works)
	{
		for(WorkExternal work : works) 
		{
			System.out.println( "**************************************************" );
			System.out.println( "PROVIDER: " + work.getProviderKey() );
			printIfNotEmpty( "WR_WRK_INST: " , work.getPrimaryKey());                                        
			printIfNotEmpty( "TF_WRK_INST: " , work.getTfWksInst());
			printIfNotEmpty( "HOST_IDNO: " , work.getHostIdno());
			printIfNotEmpty( "IDNO: " , work.getIdno());
			printIfNotEmpty( "IDNO_TYPE_CD: " , work.getIdnoTypeCd());
			//printIfNotEmpty( "TITLE: " , work.getTitle());
			printIfNotEmpty( "VOLUME: " , work.getVolume());
			printIfNotEmpty( "EDITION: " , work.getEdition());
			printIfNotEmpty( "PUBLISHER_NAME: " , work.getPublisherName());
			printIfNotEmpty( "AUTHOR_NAME: " , work.getAuthorName());
			printIfNotEmpty( "EDITOR_NAME: " , work.getEditorName()); 
			printIfNotEmpty( "RUN_PUB_START_DATE: " , work.getRunPubStartDate());
			printIfNotEmpty( "RUN_PUB_END_DATE: " , work.getRunPubEndDate());
			printIfNotEmpty( "PUBLICATION_TYPE: " , work.getPublicationType());
			printIfNotEmpty( "CATALOG_RANK: " , work.getIsFrequentlyRequested());
			printIfNotEmpty( "TF_WKS_INST: " , work.getTfWksInst());
			printIfNotEmpty( "LANGUAGE: " , work.getLanguage());
			printIfNotEmpty( "OCLCNUM: " , work.getOclcNum());
			printIfNotEmpty( "IDNO_WOP: " , work.getIdnoWop());
			printIfNotEmpty( "PAGES: " , work.getPages());
			printIfNotEmpty( "COUNTRY: " , work.getCountry());
			printIfNotEmpty( "SERIES: " , work.getSeries());
			printIfNotEmpty( "SERIES_NUMBER: " , work.getSeriesNumber());
			// article level related fields
			printIfNotEmpty( "ITEM_START_PAGE: " , work.getItemStartPage()); 
			printIfNotEmpty( "ITEM_END_PAGE: " , work.getItemEndPage()); 
			printIfNotEmpty( "ITEM_VOLUME: " , work.getItemVolume()); 
			printIfNotEmpty( "ITEM_ISSUE: " , work.getItemIssue()); 
			printIfNotEmpty( "ITEM_NUMBER: " , work.getItemNumber()); 
			printIfNotEmpty( "ITEM_PAGE_RANGE: " , work.getItemPageRange()); 
			printIfNotEmpty( "ITEM_SEASON: " , work.getItemSeason()); 
			printIfNotEmpty( "ITEM_QUARTER: " , work.getItemQuarter()); 
			printIfNotEmpty( "ITEM_WEEK: " , work.getItemWeek()); 
			printIfNotEmpty( "ITEM_SECTION: " , work.getItemSection()); 
			printIfNotEmpty( "ITEM_URL: " , work.getItemUrl()); 
			
			printTagIfNotEmpty( "RR_TAGS: ", work.getRrTagList());
			printTagIfNotEmpty( "LB_TAGS: " , work.getLbTagList()); 
			printTagIfNotEmpty( "SRS_TAGS: " , work.getSrsTagList()); 
		}
	}

	private static void printIfNotEmpty(String label, Object content)
	{
		if(content != null)
		{
			System.out.println( label + content.toString() );
		}
	}

	private static void printTagIfNotEmpty(String label, List<WorkTagExternal> content)
	{
		if(content != null)
		{
			System.out.println( label);
			System.out.println( "****" );
			for(WorkTagExternal wt : content)
			{
				System.out.println( "TagName:       " + wt.getTagName());
				System.out.println( "BiactiveStart: " + wt.getBiactiveStart());
				System.out.println( "BiactiveEnd:   " + wt.getBiactiveEnd());
				System.out.println( "TagValidStart: " + wt.getTagValidStart());
				System.out.println( "TagValidEnd:   " + wt.getTagValidEnd());
				System.out.println( "****" );
			}
		}
	}

	/**
	 * @return the result
	 */
	public SearchRetrievalResult getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(SearchRetrievalResult result) {
		this.result = result;
	}

	/**
	 * @return the SearchRetrievalService
	 */
	public SearchRetrievalService getSearchRetrievalService() {
		return mSearchRetrievalService;
	}

	/**
	 * @param SearchRetrievalService the SearchRetrievalService to set
	 */
	public void setSearchRetrievalService(SearchRetrievalService searchRetrievalService) {
		this.mSearchRetrievalService = searchRetrievalService;
	}

}
