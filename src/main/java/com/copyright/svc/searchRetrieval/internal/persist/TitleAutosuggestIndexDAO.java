/* 
 * TitleAutosuggestIndexDAO.java
 * Copyright (c) 2011, Copyright Clearance Center, Inc. All rights reserved.
 * ----------------------------------------------------------------------------
 * Revision History
 * 2011-07-08   lwojtowicz    Created.
 * ----------------------------------------------------------------------------
 */

package com.copyright.svc.searchRetrieval.internal.persist;

/**
 * 
 * @author lwojtowicz
 * 
 */

import java.util.List;


public interface TitleAutosuggestIndexDAO
{
	public List<String> queryTitleAutosuggestIndex(String query, int numberOfItems, int count);
	public void addTitleToIndex(String pubTitleOrStdNumber);
}