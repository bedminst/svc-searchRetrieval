/*
 * SearchRetrievalServiceLocator.java
 * Copyright (c) 2008, Copyright Clearance Center, Inc. All rights reserved.
 * ----------------------------------------------------------------------------
 * Revision History
 * 2008-10-21   lwojtowicz    Created.
 * ----------------------------------------------------------------------------
 */

package com.copyright.svc.searchRetrieval.api;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @author lwojtowicz
 *
 */
public class SearchRetrievalServiceLocator implements ApplicationContextAware {

	private static Logger sLogger = Logger.getLogger( SearchRetrievalServiceLocator.class );
	private ApplicationContext appCtx;
	private static SearchRetrievalServiceLocator sLocator;
	
	/* (non-JavaDoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext ctx) {
		sLogger.debug("received callback from Spring, setting app ctx=" + ctx);
		appCtx = ctx;
	}

    private SearchRetrievalServiceLocator() {}
    
    /**
     * Singleton access to the service locator instance.
     * @return SearchRetrievalServiceLocator
     */
	public static SearchRetrievalServiceLocator getInstance() {
	    if (sLocator == null) {
	        sLocator = new SearchRetrievalServiceLocator();
	    }
	    return sLocator;
	}
	
	/**
	 * Returns a handle to the searchRetrievalServiceService bean.
	 * <p>
	 * Initializes the spring app context if needed, then returns the service bean.
	 * 
	 * @return An searchRetrievalServiceBean bean.
	 */
	public static SearchRetrievalService getSearchRetrievalService(){
		
	    if( null == SearchRetrievalServiceLocator.getInstance().getAppCtx() ){
			sLogger.info("SearchRetrievalServiceLocator loading application context.");
			SearchRetrievalServiceLocator.getInstance().setApplicationContext( loadContext() );
		}
	    sLogger.info("SearchRetrievalServiceLocator returning application context.");
		return (SearchRetrievalService)SearchRetrievalServiceLocator.getInstance().getAppCtx().getBean(SearchRetrievalNames.SEARCHRETRIEVAL_SERVICE_BEAN);
	}
	
	/*
	 * Common task of initializing the Spring context.
	 */
	private static ApplicationContext loadContext()
	{
		return new ClassPathXmlApplicationContext( "com/copyright/svc/searchRetrieval/api/svc-searchRetrieval-context.xml" );
	}

    private ApplicationContext getAppCtx()
    {
        return appCtx;
    }
}
