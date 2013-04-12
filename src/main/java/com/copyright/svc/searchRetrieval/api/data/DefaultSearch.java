/**
 * 
 */
package com.copyright.svc.searchRetrieval.api.data;

import java.util.List;

/**
 * @author lwojtowicz
 *
 */
public abstract class DefaultSearch 
{
	
	// for sorting - generic concepts
	public static final String SORT_RELEVANCE = "relevance";
	public static final String SORT_TITLE = "title";
	public static final String SORT_PUBLISHER = "publisher";
	public static final String SORT_DATE = "date";

	public static interface ORDER
	{
		String asc = ":asc";
		String desc = ":desc";
	}
	
    protected String mWrWrkInst;
	protected List<String> mSortOrder;

	public String getWrWrkInst() {
		return mWrWrkInst;
	}

	public void setWrWrkInst(String wrWrkInst) {
		mWrWrkInst = wrWrkInst;
	}

	/**
	 * @return the sortOrder
	 */
	public List<String> getSortOrder() {
		return mSortOrder;
	}

	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(List<String> sortOrder) {
		mSortOrder = sortOrder;
	}

}
