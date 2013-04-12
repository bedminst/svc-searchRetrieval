/*
 * SearchRetrievalResult.java
 * Copyright (c) 2009, Copyright Clearance Center, Inc. All rights reserved.
 * ----------------------------------------------------------------------------
 * Revision History
 * 2009-12-21   lwojtowicz    Created.
 * ----------------------------------------------------------------------------
 */

package com.copyright.svc.searchRetrieval.api.data;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

import com.copyright.domain.data.WorkExternal;

/**
 * Data object for returning a result of a catalog query
 *
 */
public class SearchRetrievalResult implements Serializable {

	private static final long serialVersionUID = 1L;

	protected List<WorkExternal> mWorks = new ArrayList<WorkExternal>(); // page of work objects
	protected int mResultCount = 0;	// the number of hits
	protected int mPage;			 	// the requested page
	protected int mPageSize;		 	// the requested page size
	protected boolean mLastPage;		// the last page given page size
	
	public List<WorkExternal> getWorks()
	{
		return mWorks;
	}
	
	public void setWorks(List<WorkExternal> works)
	{
		this.mWorks = works;
	}
		
	public int getResultCount()
	{
		return mResultCount;
	}
	
	public void setResultCount(int resultCount)
	{
		this.mResultCount = resultCount;
	}
	
	public int getPage()
	{
		return mPage;
	}
	
	public void setPage(int page)
	{
		this.mPage = page;
	}
	
	public int getPageSize()
	{
		return mPageSize;
	}
	
	public void setPageSize(int pageSize)
	{
		this.mPageSize = pageSize;
	}
	
	public boolean getLastPage()
	{
		return mLastPage;
	}
	
	public void setLastPage(boolean lastPage)
	{
		this.mLastPage = lastPage;
	}
}
