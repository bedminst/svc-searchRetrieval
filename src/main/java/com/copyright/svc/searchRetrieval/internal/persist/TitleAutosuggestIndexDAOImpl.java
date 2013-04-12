/*
 * TitleAutosuggestIndexDAOImpl.java
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.core.task.TaskExecutor;

public class TitleAutosuggestIndexDAOImpl implements TitleAutosuggestIndexDAO
{
	private CommonsHttpSolrServer mAutosuggestMasterServer = null;
	private CommonsHttpSolrServer mAutosuggestSlaveServer = null;
    private String similarity = "0.5";
    
    private TaskExecutor mTaskExecutor;

    private Logger logger = Logger.getLogger( TitleAutosuggestIndexDAOImpl.class );

	public void addTitleToIndex(String pubTitleOrStdNumber)
	{
		String title = determineTitle(pubTitleOrStdNumber);
		
		if(title != null)
		{
			/*
			 * Note: this title is already "normalized", i.e. it has common punctuation replaced 
			 *       with spaces (if applicable) and multiple spaces replaced with a single space
			 */
			logger.debug("Adding '" + title + "' to the autosuggest index.");
			mTaskExecutor.execute(new TitleInsertTask(title));
		}
	}
	
	public List<String> queryTitleAutosuggestIndex(String titleQuery, int numberOfItems, int count)
	{
		// set up the query object
		SolrQuery query = new SolrQuery();
		
		query.setQuery(makeTitleFuzzy(titleQuery, count));
		query.setStart(0);
		query.setRows(numberOfItems);
		// we're only interested in the work id field being returned
		query.addField("title");
		query.addSortField("count", ORDER.desc);

		// perform the query without spellchecking
		QueryResponse results = null;
		try 
		{
			results = mAutosuggestSlaveServer.query( query );
		}
		catch (SolrServerException e)
		{
			logger.error("SolrServerException: " + e.getMessage());
		}
		
		// generate titles objects for the returned titles
		List<String> titles = new ArrayList<String>();
		if(results == null)
		{
			return titles;
		}
		
		for( SolrDocument doc : results.getResults() ) 
		{
			String title = (String) doc.getFieldValue("title");
			titles.add(title);
		}
		return titles;
	}

	public void setSimilarity(String similarity) {
		this.similarity = similarity;
	}

	public String getSimilarity() {
		return similarity;
	}

	private String makeTitleFuzzy(String titleOrStdNumber, int count)
	{
		String fuzzyQuery = "{!lucene q.op=AND}title:(";
		
		if(titleOrStdNumber != null && !titleOrStdNumber.isEmpty())
		{
			String[] titleOrStdNumberWords = titleOrStdNumber.split(" ");
			
			boolean inLoop = false;
			for(String token : titleOrStdNumberWords)
			{
				if(inLoop)
				{
					fuzzyQuery += " ";
				}
				fuzzyQuery += token + "~" + similarity;
				inLoop = true;
			}
		}
		
		return fuzzyQuery + ") AND count:[" + count + " TO *]";
	}

	private String determineTitle(String pubTitleOrStdNumber)
	{
		if(pubTitleOrStdNumber == null || pubTitleOrStdNumber.isEmpty())
		{
			return null;
		}

		/*
		 *  Strip all whitespace, digits, capital X and dashes.
		 *  If nothing is left, this was ISSN or ISBN
		 *  so we shouldn't add it to the autosuggest index
		 */
		String titleNoSpace = pubTitleOrStdNumber.replaceAll("[\\s]+", "");
		String titleNoDigits = titleNoSpace.replaceAll("([0-9]+)", "");
		String titleNoDigitsAndX = titleNoDigits.replaceAll("X", "");
		String title = titleNoDigitsAndX.replaceAll("(\\-)+", "");

		if(title == null || title.isEmpty())
		{
			return null;
		}

		/*
		 *  Now final normalization: remove common punctuation and multiple spaces
		 */
		title = pubTitleOrStdNumber.replaceAll("[\"~;:,.-]+", "").replaceAll("[\\s]+", " ").toLowerCase().trim();
		logger.debug("Left '" + title + "'");

		return title;
	}

	public void setTaskExecutor(TaskExecutor taskExecutor) {
		mTaskExecutor = taskExecutor;
	}

	public TaskExecutor getTaskExecutor() {
		return mTaskExecutor;
	}
	
	private class TitleInsertTask implements Runnable
	{
		private String mTitle;

		public TitleInsertTask(String pubTitleOrStdNumber)
		{
			mTitle = pubTitleOrStdNumber;
		}

		public void run()
		{
			// set up the query object
			SolrQuery query = new SolrQuery();
			String token = mTitle.replaceAll("[\\s]+", "");
			logger.debug("Querying for: " + token);
			query.setQuery("title_phrase:(" + token + ")");

			// perform the query without spellchecking
			QueryResponse results = null;
			try 
			{
				results = mAutosuggestMasterServer.query( query );
			}
			catch (SolrServerException e)
			{
				logger.error("SolrServerException: " + e.getMessage());
			}

			String identifier = null;
			Integer count = 0;
			if(results.getResults().size() >= 1)
			{
				SolrDocument sd = results.getResults().get(0);
				
				identifier = (String)sd.getFieldValue("id");
				count = (Integer)sd.getFieldValue("count");

				if(results.getResults().size() > 1)
				{
					logger.error("More than one response for: " + token);
				}
			}

			SolrInputDocument sid = new SolrInputDocument();
			if(identifier != null)
			{
				sid.addField("id", identifier);
			}
			sid.addField("title", mTitle);
			sid.addField("count", count + 1);
			
			try 
			{
				mAutosuggestMasterServer.add(sid);
				mAutosuggestMasterServer.commit(false, false);
			} 
			catch (SolrServerException e)
			{
				logger.error("SolrServerException: " + e.getMessage());
			}
			catch (IOException e)
			{
				logger.error("IOException: " + e.getMessage());
			}
		}
	}

	public CommonsHttpSolrServer getAutosuggestMasterServer() {
		return mAutosuggestMasterServer;
	}

	public void setAutosuggestMasterServer(
			CommonsHttpSolrServer autosuggestMasterServer) {
		mAutosuggestMasterServer = autosuggestMasterServer;
	}

	public CommonsHttpSolrServer getAutosuggestSlaveServer() {
		return mAutosuggestSlaveServer;
	}

	public void setAutosuggestSlaveServer(
			CommonsHttpSolrServer autosuggestSlaveServer) {
		mAutosuggestSlaveServer = autosuggestSlaveServer;
	}

}