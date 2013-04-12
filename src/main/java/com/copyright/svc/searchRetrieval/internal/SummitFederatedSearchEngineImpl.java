/**
 * 
 */

package com.copyright.svc.searchRetrieval.internal;

import java.util.List;

import com.copyright.mediadelivery.framework.ICoreMediaContext;
import com.copyright.mediadelivery.framework.api.ISearchHandlerInstance;
import com.copyright.mediadelivery.framework.api.SearchSpecification;
import com.copyright.mediadelivery.framework.internal.search.FederatedSearchEngineImpl;
import com.copyright.mediadelivery.framework.internal.search.SearchParameters;

/**
 * @author lwojtowicz
 * 
 */
public class SummitFederatedSearchEngineImpl extends FederatedSearchEngineImpl
{
	
	protected SearchSpecification getSearchSpecification(ICoreMediaContext context, List<ISearchHandlerInstance> allHandlers, ISearchHandlerInstance handler, SearchParameters params)
	{
		return new SearchSpecification((params.getStartingIndex() - 1) + params.getTargetResultCount());
	}
	
}
