/* 
 * SearchRetrievalDAO.java
 * Copyright (c) 2008, Copyright Clearance Center, Inc. All rights reserved.
 * ----------------------------------------------------------------------------
 * Revision History
 * 2008-10-21   lwojtowicz    Created.
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

import java.util.List;

import com.copyright.domain.data.RightExternal;
import com.copyright.domain.data.WorkExternal;
import com.copyright.mediadelivery.framework.ICoreMediaContext;
import com.copyright.mediadelivery.framework.internal.search.SearchParameters;
import com.copyright.svc.searchRetrieval.api.data.SrsLimiter;

public interface SearchRetrievalDAO
{
	public int search(ICoreMediaContext context, SearchParameters sp, List<WorkExternal> allWorks);
	public List<RightExternal> searchRights(List<String> rgtInst, String rghInst, int page, int pageSize);
	public List<SrsLimiter> getItemsForField(String fieldName);
}