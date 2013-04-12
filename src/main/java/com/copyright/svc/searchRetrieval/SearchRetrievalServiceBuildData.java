package com.copyright.svc.searchRetrieval;

import java.io.IOException;

import com.copyright.base.config.BuildData;
import com.copyright.base.svc.ServiceRuntimeException;

/**
 * Build data for Search/Retrieval Service module.
 * 
 * @author lwojtowicz
 */
public class SearchRetrievalServiceBuildData extends BuildData
{
	private static final long serialVersionUID = 1L;

	private static String FILE_NAME = "builddata.txt";
	
	private static SearchRetrievalServiceBuildData sInstance;

	protected SearchRetrievalServiceBuildData()
			throws IOException
	{
		super( SearchRetrievalServiceBuildData.class, FILE_NAME );
	}

	public static SearchRetrievalServiceBuildData getInstance()
	{
		if ( sInstance == null )
		{
			try
			{
				sInstance = new SearchRetrievalServiceBuildData();
			}
			catch ( IOException ioe )
			{
				throw new ServiceRuntimeException( ioe );
			}
		}
		
		return sInstance;
	}
}
