package com.copyright.svc.searchRetrieval.api;
/**
 * SearchRetrievalServiceTest.java 
 */

import static junit.framework.Assert.fail;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.copyright.domain.data.RightExternal;
import com.copyright.domain.data.WorkExternal;
import com.copyright.domain.data.WorkRightslink;
import com.copyright.domain.data.WorkTagExternal;
import com.copyright.svc.searchRetrieval.api.data.ArticleSearch;
import com.copyright.svc.searchRetrieval.api.data.PublicationSearch;
import com.copyright.svc.searchRetrieval.api.data.SearchRetrievalConsumerContext;
import com.copyright.svc.searchRetrieval.api.data.SearchRetrievalResult;
import com.copyright.svc.searchRetrieval.api.data.SrsLimiter;


/**
 * @author lwojtowicz
 *
 */
public class SearchRetrievalServiceTest
{
	private ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:com/copyright/svc/searchRetrieval/internal/config/context/SearchRetrievalServiceContext.xml");
    private SearchRetrievalService svc = (SearchRetrievalService)ctx.getBean("searchRetrievalServiceBean");
	private SearchRetrievalConsumerContext consumerCtx = new SearchRetrievalConsumerContext();
	
	@Test
	public void testSearchPublication()
	{
		try
		{
	        // these are the inputs to our service call
	        PublicationSearch pubSearch = new PublicationSearch();
	        pubSearch.setPubTitleOrStdNumber("nature");
	        pubSearch.setHasRights("Y");		// this is how CC2 will be requesting it...
	        List<String> sortList = new ArrayList<String>();
	        sortList.add(PublicationSearch.SORT_RELEVANCE);
	        int page = 1;
	        int pageSize = 10;

	        SearchRetrievalResult result = svc.searchPublication(consumerCtx, pubSearch, sortList, page, pageSize);
	        assertTrue(result.getResultCount() > 1);
	        
	        int count = 0;
	        for (WorkExternal work : result.getWorks())
	        {
				System.out.println("====================== Record: " + count++ + " ======================");
				System.out.println("AuthorName: " + ((work.getAuthorName() == null) ? "Null value returned" : work.getAuthorName()));		  
				System.out.println("CatalogRank: " + ((work.getIsFrequentlyRequested()== null) ? "Null value returned" : work.getIsFrequentlyRequested()));		  
				System.out.println("Country: " + ((work.getCountry() == null) ? "Null value returned" : work.getCountry()));
				System.out.println("Edition: " + ((work.getEdition() == null) ? "Null value returned" : work.getEdition()));		  
				System.out.println("EditorName: " + ((work.getEditorName() == null) ? "Null value returned" : work.getEditorName()));		  
				System.out.println("HostIdno: " + ((work.getHostIdno() == null) ? "Null value returned" : work.getHostIdno()));		  
				System.out.println("Idno: " + ((work.getIdno() == null) ? "Null value returned" : work.getIdno()));		  
				System.out.println("IdnoType: " + ((work.getIdnoTypeCode() == null) ? "Null value returned" : work.getIdnoTypeCode()));
				System.out.println("IdnoWop: " + ((work.getIdnoWop() == null) ? "Null value returned" : work.getIdnoWop()));
				System.out.println("ItemEndPage: " + ((work.getItemEndPage() == null) ? "Null value returned" : work.getItemEndPage()));
				System.out.println("ItemIssue: " + ((work.getItemIssue() == null) ? "Null value returned" : work.getItemIssue()));
				System.out.println("ItemNumber: " + ((work.getItemNumber() == null) ? "Null value returned" : work.getItemNumber()));
				System.out.println("ItemPageRange: " + ((work.getItemPageRange() == null) ? "Null value returned" : work.getItemPageRange()));
				System.out.println("ItemQuarter: " + ((work.getItemQuarter() == null) ? "Null value returned" : work.getItemQuarter()));
				System.out.println("ItemSeason: " + ((work.getItemSeason() == null) ? "Null value returned" : work.getItemSeason()));
				System.out.println("ItemSection: " + ((work.getItemSection() == null) ? "Null value returned" : work.getItemSection()));
				System.out.println("ItemStartPage: " + ((work.getItemStartPage() == null) ? "Null value returned" : work.getItemStartPage()));
				System.out.println("ItemUrl: " + ((work.getItemUrl() == null) ? "Null value returned" : work.getItemUrl()));
				System.out.println("ItemVolume: " + ((work.getItemVolume() == null) ? "Null value returned" : work.getItemVolume()));
				System.out.println("ItemWeek: " + ((work.getItemWeek() == null) ? "Null value returned" : work.getItemWeek()));
				System.out.println("Language: " + ((work.getLanguage() == null) ? "Null value returned" : work.getLanguage()));
				System.out.println("LbTags: " + ((work.getLbTags() == null) ? "Null value returned" : work.getLbTags()));				
				System.out.println("OclcNum: " + ((work.getOclcNum() == null) ? "Null value returned" : work.getOclcNum()));
				System.out.println("OtherFormatIdnos: " + ((work.getOtherFormatIdnos() == null) ? "Null value returned" : work.getOtherFormatIdnos()));
				System.out.println("Pages: " + ((work.getPages() == null) ? "Null value returned" : work.getPages()));				
				System.out.println("PublicationType: " + ((work.getPublicationType() == null) ? "Null value returned" : work.getPublicationType()));
				System.out.println("PublisherName: " + ((work.getPublisherName() == null) ? "Null value returned" : work.getPublisherName()));
				System.out.println("RrTags: " + ((work.getRrTags() == null) ? "Null value returned" : work.getRrTags()));				
				System.out.println("RunPubEndDate: " + ((work.getRunPubEndDate() == null) ? "Null value returned" : work.getRunPubEndDate()));		  
				System.out.println("RunPubStartDate: " + ((work.getRunPubStartDate() == null) ? "Null value returned" : work.getRunPubStartDate()));
				System.out.println("Series: " + ((work.getSeries() == null) ? "Null value returned" : work.getSeries()));
				System.out.println("SeriesNumber: " + ((work.getSeriesNumber() == null) ? "Null value returned" : work.getSeriesNumber()));
				System.out.println("SrsTags: " + ((work.getSrsTags() == null) ? "Null value returned" : work.getSrsTags()));				
				System.out.println("TfWksInst: " + ((work.getTfWksInst() == null) ? "Null value returned" : work.getTfWksInst()));		  
				System.out.println("TfWrkInst: " + ((work.getTfWrkInst() == null) ? "Null value returned" : work.getTfWrkInst()));		  
				//System.out.println("Title: " + ((work.getTitle() == null) ? "Null value returned" : work.getTitle()));
				System.out.println("Volume: " + ((work.getVolume() == null) ? "Null value returned" : work.getVolume()));
				System.out.println("WrWrkInst: " + ((work.getPrimaryKey() == null) ? "Null value returned" : work.getPrimaryKey()));		  
				
				if (work.getTfWksInstList() != null)
				{
					System.out.println("TfWksInstList: " );
					for (Long tfWrkInst : work.getTfWksInstList()) 
					{
						System.out.println("               " + tfWrkInst);
					}
				}
				else
				{
					System.out.println("TfWksInst: Null value returned");
				}
				
				System.out.println("TfWrkInst: " + ((work.getTfWrkInst() == null) ? "Null value returned" : work.getTfWrkInst()));
				System.out.println("IsFrequentlyRequested: " + ((work.getIsFrequentlyRequested() == null) ? "Null value returned" : work.getIsFrequentlyRequested()));
				System.out.println("Language: " + ((work.getLanguage() == null) ? "Null value returned" : work.getLanguage()));

				System.out.println("FullTitle: " + ((work.getFullTitle() == null) ? "Null value returned" : work.getFullTitle()));		  
				System.out.println("MainTitle: " + ((work.getMainTitle() == null) ? "Null value returned" : work.getMainTitle()));		  
				System.out.println("SubTitle: " + ((work.getSubTitle() == null) ? "Null value returned" : work.getSubTitle()));		  

				if (work.getRghInsts() != null)
				{
					System.out.println("RghInsts: " );
					for (Long rghInst : work.getRghInsts()) 
					{
						System.out.println("               " + rghInst);
					}
				}
				else
				{
					System.out.println("RghInsts: Null value returned");
				}
				
				if (work.getRghInsts() != null)
				{
					System.out.println("RgtInsts: " );
					for (Long rgtInst : work.getRgtInsts()) 
					{
						System.out.println("               " + rgtInst);
					}
				}
				else
				{
					System.out.println("RgtInsts: Null value returned");
				}

				if (work.getRights() != null)
				{
					System.out.println("Rights: " );
					for (String right : work.getRights()) 
					{
						System.out.println("               " + right);
					}
				}
				else
				{
					System.out.println("Rights: Null value returned");
				}	  
				
				if (work.getRgh2names() != null)
				{
					System.out.println("rgh2name: " );
					for (String rgh2name : work.getRgh2names()) 
					{
						System.out.println("               " + rgh2name);
					}
				}
				else
				{
					System.out.println("rgh2name: Null value returned");
				}
				
				if (work.getRgt2tpus() != null)
				{
					System.out.println("rgt2tpu: " );
					for (String rgt2tpu : work.getRgt2tpus()) 
					{
						System.out.println("               " + rgt2tpu);
					}
				}
				else
				{
					System.out.println("rgt2tpu: Null value returned");
				}

				System.out.println("RlnkPub2PubInfo: " + ((StringUtils.isEmpty(work.getRlnkPub2PubInfo()) ? "Null value returned" : work.getRlnkPub2PubInfo())));		  
				if (work.getRlnkPub2PubList() != null)
				{
					System.out.println("RlnkPub2PubList: " );
					for (WorkRightslink workRightslink : work.getRlnkPub2PubList()) 
					{
						System.out.println("               " + workRightslink.getPublicationCode());
						System.out.println("               " + workRightslink.getPublisherCode());
					}
				}
				else
				{
					System.out.println("RlnkPub2PubList: Null value returned");
				}		
	        }
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			fail("Unit Test SearchRetrievalServiceTest.testSearchPublication() Failure");
		}
	}
	
	@Test
	public void testSearchPublicationRlnkPub2Pub()
	{
		try
		{
	        // these are the inputs to our service call
	        PublicationSearch pubSearch = new PublicationSearch();
	        pubSearch.setWrWrkInst("122804315");
	        List<String> sortList = new ArrayList<String>();
	        int page = 1;
	        int pageSize = 10;

	        SearchRetrievalResult result = svc.searchPublication(consumerCtx, pubSearch, sortList, page, pageSize);
	        assertTrue(result.getResultCount() > 0);
	        
	        int count = 0;
	        for (WorkExternal work : result.getWorks())
	        {
				System.out.println("====================== Record: " + count++ + " ======================");
				System.out.println("AuthorName: " + ((work.getAuthorName() == null) ? "Null value returned" : work.getAuthorName()));		  
				System.out.println("CatalogRank: " + ((work.getIsFrequentlyRequested()== null) ? "Null value returned" : work.getIsFrequentlyRequested()));		  
				System.out.println("Country: " + ((work.getCountry() == null) ? "Null value returned" : work.getCountry()));
				System.out.println("Edition: " + ((work.getEdition() == null) ? "Null value returned" : work.getEdition()));		  
				System.out.println("EditorName: " + ((work.getEditorName() == null) ? "Null value returned" : work.getEditorName()));		  
				System.out.println("HostIdno: " + ((work.getHostIdno() == null) ? "Null value returned" : work.getHostIdno()));		  
				System.out.println("Idno: " + ((work.getIdno() == null) ? "Null value returned" : work.getIdno()));		  
				System.out.println("IdnoType: " + ((work.getIdnoTypeCode() == null) ? "Null value returned" : work.getIdnoTypeCode()));
				System.out.println("IdnoWop: " + ((work.getIdnoWop() == null) ? "Null value returned" : work.getIdnoWop()));
				System.out.println("ItemEndPage: " + ((work.getItemEndPage() == null) ? "Null value returned" : work.getItemEndPage()));
				System.out.println("ItemIssue: " + ((work.getItemIssue() == null) ? "Null value returned" : work.getItemIssue()));
				System.out.println("ItemNumber: " + ((work.getItemNumber() == null) ? "Null value returned" : work.getItemNumber()));
				System.out.println("ItemPageRange: " + ((work.getItemPageRange() == null) ? "Null value returned" : work.getItemPageRange()));
				System.out.println("ItemQuarter: " + ((work.getItemQuarter() == null) ? "Null value returned" : work.getItemQuarter()));
				System.out.println("ItemSeason: " + ((work.getItemSeason() == null) ? "Null value returned" : work.getItemSeason()));
				System.out.println("ItemSection: " + ((work.getItemSection() == null) ? "Null value returned" : work.getItemSection()));
				System.out.println("ItemStartPage: " + ((work.getItemStartPage() == null) ? "Null value returned" : work.getItemStartPage()));
				System.out.println("ItemUrl: " + ((work.getItemUrl() == null) ? "Null value returned" : work.getItemUrl()));
				System.out.println("ItemVolume: " + ((work.getItemVolume() == null) ? "Null value returned" : work.getItemVolume()));
				System.out.println("ItemWeek: " + ((work.getItemWeek() == null) ? "Null value returned" : work.getItemWeek()));
				System.out.println("Language: " + ((work.getLanguage() == null) ? "Null value returned" : work.getLanguage()));
				System.out.println("LbTags: " + ((work.getLbTags() == null) ? "Null value returned" : work.getLbTags()));				
				System.out.println("OclcNum: " + ((work.getOclcNum() == null) ? "Null value returned" : work.getOclcNum()));
				System.out.println("OtherFormatIdnos: " + ((work.getOtherFormatIdnos() == null) ? "Null value returned" : work.getOtherFormatIdnos()));
				System.out.println("Pages: " + ((work.getPages() == null) ? "Null value returned" : work.getPages()));				
				System.out.println("PublicationType: " + ((work.getPublicationType() == null) ? "Null value returned" : work.getPublicationType()));
				System.out.println("PublisherName: " + ((work.getPublisherName() == null) ? "Null value returned" : work.getPublisherName()));
				System.out.println("RrTags: " + ((work.getRrTags() == null) ? "Null value returned" : work.getRrTags()));				
				System.out.println("RunPubEndDate: " + ((work.getRunPubEndDate() == null) ? "Null value returned" : work.getRunPubEndDate()));		  
				System.out.println("RunPubStartDate: " + ((work.getRunPubStartDate() == null) ? "Null value returned" : work.getRunPubStartDate()));
				System.out.println("Series: " + ((work.getSeries() == null) ? "Null value returned" : work.getSeries()));
				System.out.println("SeriesNumber: " + ((work.getSeriesNumber() == null) ? "Null value returned" : work.getSeriesNumber()));
				System.out.println("SrsTags: " + ((work.getSrsTags() == null) ? "Null value returned" : work.getSrsTags()));				
				System.out.println("TfWksInst: " + ((work.getTfWksInst() == null) ? "Null value returned" : work.getTfWksInst()));		  
				System.out.println("TfWrkInst: " + ((work.getTfWrkInst() == null) ? "Null value returned" : work.getTfWrkInst()));		  
				//System.out.println("Title: " + ((work.getTitle() == null) ? "Null value returned" : work.getTitle()));
				System.out.println("Volume: " + ((work.getVolume() == null) ? "Null value returned" : work.getVolume()));
				System.out.println("WrWrkInst: " + ((work.getPrimaryKey() == null) ? "Null value returned" : work.getPrimaryKey()));		  
				
				if (work.getTfWksInstList() != null)
				{
					System.out.println("TfWksInstList: " );
					for (Long tfWrkInst : work.getTfWksInstList()) 
					{
						System.out.println("               " + tfWrkInst);
					}
				}
				else
				{
					System.out.println("TfWksInst: Null value returned");
				}
				
				System.out.println("TfWrkInst: " + ((work.getTfWrkInst() == null) ? "Null value returned" : work.getTfWrkInst()));
				System.out.println("IsFrequentlyRequested: " + ((work.getIsFrequentlyRequested() == null) ? "Null value returned" : work.getIsFrequentlyRequested()));
				System.out.println("Language: " + ((work.getLanguage() == null) ? "Null value returned" : work.getLanguage()));

				System.out.println("FullTitle: " + ((work.getFullTitle() == null) ? "Null value returned" : work.getFullTitle()));		  
				System.out.println("MainTitle: " + ((work.getMainTitle() == null) ? "Null value returned" : work.getMainTitle()));		  
				System.out.println("SubTitle: " + ((work.getSubTitle() == null) ? "Null value returned" : work.getSubTitle()));		  

				if (work.getRghInsts() != null)
				{
					System.out.println("RghInsts: " );
					for (Long rghInst : work.getRghInsts()) 
					{
						System.out.println("               " + rghInst);
					}
				}
				else
				{
					System.out.println("RghInsts: Null value returned");
				}
				
				if (work.getRghInsts() != null)
				{
					System.out.println("RgtInsts: " );
					for (Long rgtInst : work.getRgtInsts()) 
					{
						System.out.println("               " + rgtInst);
					}
				}
				else
				{
					System.out.println("RgtInsts: Null value returned");
				}

				if (work.getRights() != null)
				{
					System.out.println("Rights: " );
					for (String right : work.getRights()) 
					{
						System.out.println("               " + right);
					}
				}
				else
				{
					System.out.println("Rights: Null value returned");
				}	  
				
				if (work.getRgh2names() != null)
				{
					System.out.println("rgh2name: " );
					for (String rgh2name : work.getRgh2names()) 
					{
						System.out.println("               " + rgh2name);
					}
				}
				else
				{
					System.out.println("rgh2name: Null value returned");
				}
				
				if (work.getRgt2tpus() != null)
				{
					System.out.println("rgt2tpu: " );
					for (String rgt2tpu : work.getRgt2tpus()) 
					{
						System.out.println("               " + rgt2tpu);
					}
				}
				else
				{
					System.out.println("rgt2tpu: Null value returned");
				}

				System.out.println("RlnkPub2PubInfo: " + ((StringUtils.isEmpty(work.getRlnkPub2PubInfo()) ? "Null value returned" : work.getRlnkPub2PubInfo())));		  
				if (work.getRlnkPub2PubList() != null)
				{
					System.out.println("RlnkPub2PubList: " );
					for (WorkRightslink workRightslink : work.getRlnkPub2PubList()) 
					{
						System.out.println("               " + workRightslink.getPublicationCode());
						System.out.println("               " + workRightslink.getPublisherCode());
					}
				}
				else
				{
					System.out.println("RlnkPub2PubList: Null value returned");
				}		
	        }
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			fail("Unit Test SearchRetrievalServiceTest.testSearchPublication() Failure");
		}
	}
	
	@Test
	public void testSearchRightsRghInst()
	{
		try
		{
	        List<RightExternal>  rightExternals = svc.searchRights(consumerCtx, null, "105600", 1, 20);
	        assertNotNull(rightExternals);
	        assertTrue(rightExternals.size() > 0);	        
			int count = 0;
			
			for (RightExternal right : rightExternals)
			{
				System.out.println("====================== Record: " + count++ + " ======================");
				System.out.println("BeginDate: " + ((right.getBeginDate() == null) ? "Null value returned" : right.getBeginDate()));		  
				System.out.println("EndDate: " + ((right.getEndDate()== null) ? "Null value returned" : right.getEndDate()));		  
				System.out.println("Fees: " + ((right.getFees() == null) ? "Null value returned" : right.getFees()));
				System.out.println("Id: " + ((right.getId() == null) ? "Null value returned" : right.getId()));		  
				System.out.println("LicClass: " + ((right.getLicClass() == null) ? "Null value returned" : right.getLicClass()));		  
				System.out.println("Limits: " + ((right.getLimits() == null) ? "Null value returned" : right.getLimits()));		  
				System.out.println("Permission: " + ((right.getPermission() == null) ? "Null value returned" : right.getPermission()));		  
				System.out.println("Product: " + ((right.getProduct() == null) ? "Null value returned" : right.getProduct()));		  
				System.out.println("RghInst: " + ((right.getRghInst() == null) ? "Null value returned" : right.getRghInst()));		  
				System.out.println("RghName: " + ((right.getRghName() == null) ? "Null value returned" : right.getRghName()));		  
				System.out.println("RgtInst: " + ((right.getRgtInst() == null) ? "Null value returned" : right.getRgtInst()));		  
				System.out.println("TfWksInst: " + ((right.getTfWksInst() == null) ? "Null value returned" : right.getTfWksInst()));		  
				System.out.println("TfWrkInst: " + ((right.getTfWrkInst() == null) ? "Null value returned" : right.getTfWrkInst()));		  
				System.out.println("UseType: " + ((right.getUseType() == null) ? "Null value returned" : right.getUseType()));		  
				System.out.println("WorksCount: " + ((right.getWorkCount() == null) ? "Null value returned" : right.getWorkCount()));
				System.out.println("AgrStatusCd: " + ((right.getAgrStatusCd() == null) ? "Null value returned" : right.getAgrStatusCd()));
				System.out.println("DirectDeal: " + ((right.getDirectDeal() == null) ? "Null value returned" : right.getDirectDeal()));
				System.out.println("RroMember: " + ((right.getRroMember() == null) ? "Null value returned" : right.getRroMember()));
			}			

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			fail("Unit Test SearchRetrievalServiceTest.testSearchPublication() Failure");
		}
	}
	
	@Test
	public void testSearchRightsRgtInsts()
	{
		List<String> rgtInsts = new ArrayList<String>();
		rgtInsts.add("50699");
		rgtInsts.add("74296");
		
		try
		{
	        List<RightExternal>  rightExternals = svc.searchRights(consumerCtx, rgtInsts, null, 1, 20);
	        assertNotNull(rightExternals);
	        assertTrue(rightExternals.size() > 0);
			int count = 0;
			
			for (RightExternal right : rightExternals)
			{
				System.out.println("====================== Record: " + count++ + " ======================");
				System.out.println("BeginDate: " + ((right.getBeginDate() == null) ? "Null value returned" : right.getBeginDate()));		  
				System.out.println("EndDate: " + ((right.getEndDate()== null) ? "Null value returned" : right.getEndDate()));		  
				System.out.println("Fees: " + ((right.getFees() == null) ? "Null value returned" : right.getFees()));
				System.out.println("Id: " + ((right.getId() == null) ? "Null value returned" : right.getId()));		  
				System.out.println("LicClass: " + ((right.getLicClass() == null) ? "Null value returned" : right.getLicClass()));		  
				System.out.println("Limits: " + ((right.getLimits() == null) ? "Null value returned" : right.getLimits()));		  
				System.out.println("Permission: " + ((right.getPermission() == null) ? "Null value returned" : right.getPermission()));		  
				System.out.println("Product: " + ((right.getProduct() == null) ? "Null value returned" : right.getProduct()));		  
				System.out.println("RghInst: " + ((right.getRghInst() == null) ? "Null value returned" : right.getRghInst()));		  
				System.out.println("RghName: " + ((right.getRghName() == null) ? "Null value returned" : right.getRghName()));		  
				System.out.println("RgtInst: " + ((right.getRgtInst() == null) ? "Null value returned" : right.getRgtInst()));		  
				System.out.println("TfWksInst: " + ((right.getTfWksInst() == null) ? "Null value returned" : right.getTfWksInst()));		  
				System.out.println("TfWrkInst: " + ((right.getTfWrkInst() == null) ? "Null value returned" : right.getTfWrkInst()));		  
				System.out.println("UseType: " + ((right.getUseType() == null) ? "Null value returned" : right.getUseType()));		  
				System.out.println("WorksCount: " + ((right.getWorkCount() == null) ? "Null value returned" : right.getWorkCount()));
				System.out.println("AgrStatusCd: " + ((right.getAgrStatusCd() == null) ? "Null value returned" : right.getAgrStatusCd()));
				System.out.println("DirectDeal: " + ((right.getDirectDeal() == null) ? "Null value returned" : right.getDirectDeal()));
				System.out.println("RroMember: " + ((right.getRroMember() == null) ? "Null value returned" : right.getRroMember()));
			}			

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			fail("Unit Test SearchRetrievalServiceTest.testSearchPublication() Failure");
		}
	}
	
	@Test
	public void testSearchNYT()
	{
		try
		{
	        // these are the inputs to our service call
	        ArticleSearch artSearch = new ArticleSearch();
	        artSearch.setArtTitle("cart not horse");
	        List<String> sortList = new ArrayList<String>();
	        sortList.add(ArticleSearch.SORT_RELEVANCE);
	        
	        // prepare sources...
	        List<WorkTagExternal> sources = new ArrayList<WorkTagExternal>();
	        WorkTagExternal wt = new WorkTagExternal();
	        wt.setTagName("NYT");
	        sources.add(wt);
	        
	        int page = 1;
	        int pageSize = 10;
	        
	        WorkExternal publication = new WorkExternal();
	        publication.setSrsTagList(sources);

	        SearchRetrievalResult result = svc.searchArticle(consumerCtx, artSearch, publication, sortList, page, pageSize);

	        assertTrue(result.getResultCount() > 1);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			fail("Unit Test SearchRetrievalServiceTest.testSearchArticle() Failure");
		}
	}

	@Test
	public void testSearchCCCWR()
	{
		try
		{
	        // these are the inputs to our service call
	        ArticleSearch artSearch = new ArticleSearch();
	        artSearch.setArtTitle("cart not horse");
	        List<String> sortList = new ArrayList<String>();
	        sortList.add(ArticleSearch.SORT_RELEVANCE);
	        
	        // prepare sources...
	        List<WorkTagExternal> sources = new ArrayList<WorkTagExternal>();
	        WorkTagExternal wt = new WorkTagExternal();
	        wt.setTagName("CCCWR");
	        sources.add(wt);
	        
	        int page = 1;
	        int pageSize = 10;
	        
	        WorkExternal publication = new WorkExternal();
	        publication.setSrsTagList(sources);

	        SearchRetrievalResult result = svc.searchArticle(consumerCtx, artSearch, publication, sortList, page, pageSize);

	        assertTrue(result.getResultCount() > 1);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			fail("Unit Test SearchRetrievalServiceTest.testSearchArticle() Failure");
		}
	}

	@Test
	public void testSearchNature()
	{
		try
		{
	        // these are the inputs to our service call
	        ArticleSearch artSearch = new ArticleSearch();
	        artSearch.setArtTitle("cart not horse");
	        List<String> sortList = new ArrayList<String>();
	        sortList.add(ArticleSearch.SORT_RELEVANCE);
	        
	        // prepare sources...
	        List<WorkTagExternal> sources = new ArrayList<WorkTagExternal>();
	        WorkTagExternal wt = new WorkTagExternal();
	        wt.setTagName("NATURE");
	        sources.add(wt);
	        
	        int page = 1;
	        int pageSize = 10;
	        
	        WorkExternal publication = new WorkExternal();
	        publication.setSrsTagList(sources);

	        SearchRetrievalResult result = svc.searchArticle(consumerCtx, artSearch, publication, sortList, page, pageSize);

	        assertTrue(result.getResultCount() > 1);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			fail("Unit Test SearchRetrievalServiceTest.testSearchArticle() Failure");
		}
	}

	@Test
	public void testSearchPubget()
	{
		try
		{
	        // these are the inputs to our service call
	        ArticleSearch artSearch = new ArticleSearch();
	        artSearch.setArtTitle("cart not horse");
	        List<String> sortList = new ArrayList<String>();
	        sortList.add(ArticleSearch.SORT_RELEVANCE);
	        
	        // prepare sources...
	        List<WorkTagExternal> sources = new ArrayList<WorkTagExternal>();
	        WorkTagExternal wt = new WorkTagExternal();
	        wt.setTagName("PUBGET");
	        sources.add(wt);
	        
	        int page = 1;
	        int pageSize = 10;
	        
	        WorkExternal publication = new WorkExternal();
	        publication.setSrsTagList(sources);

	        SearchRetrievalResult result = svc.searchArticle(consumerCtx, artSearch, publication, sortList, page, pageSize);

	        assertTrue(result.getResultCount() > 1);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			fail("Unit Test SearchRetrievalServiceTest.testSearchArticle() Failure");
		}
	}

	@Test
	public void testSearchArticles()
	{
		try
		{
	        // these are the inputs to our service call
	        ArticleSearch artSearch = new ArticleSearch();
	        artSearch.setArtTitle("cart not horse");
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
	        publication.setSrsTagList(sources);

	        SearchRetrievalResult result = svc.searchArticle(consumerCtx, artSearch, publication, sortList, page, pageSize);

	        assertTrue(result.getResultCount() > 1);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			fail("Unit Test SearchRetrievalServiceTest.testSearchArticle() Failure");
		}
	}

	@Test
	public void testItemsForField()
	{
		try
		{
			List<SrsLimiter> limiters = svc.itemsForField(consumerCtx, "lang");
			
	        assertNotNull(limiters);
	        System.out.println("The number of languages returned is: " + limiters.size());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			fail("Unit Test SearchRetrievalServiceTest.testItemsForField() Failure");
		}
	}
}
