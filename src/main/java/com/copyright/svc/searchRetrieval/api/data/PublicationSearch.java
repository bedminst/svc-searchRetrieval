/*
 * PublicationSearch.java
 * Copyright (c) 2009, Copyright Clearance Center, Inc. All rights reserved.
 * ----------------------------------------------------------------------------
 * Revision History
 * 2009-12-29   lwojtowicz    Created.
 * ----------------------------------------------------------------------------
 */

package com.copyright.svc.searchRetrieval.api.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Data object for sending a publication level search
 *
 */
public class PublicationSearch extends DefaultSearch implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String mPubTitleOrStdNumber; 
	protected String mPublisherOrRightsholder;
	protected String mAuthorOrEditor;
	protected String mSeriesName;
	protected String mCountry;
	protected String mLanguage;
	protected String mPubType;
	protected String mHasRights;
	protected List<String> mSourceNames = new ArrayList<String>();
    protected String mTfWrkInst;
	
	public PublicationSearch()
	{
		// publication search will only be using Works Repository (CCCWR)
		mSourceNames.add("CCCWR");
	}
	
	/**
	 * @return the pubTitleOrStdNumber
	 */
	public String getPubTitleOrStdNumber() {
		return mPubTitleOrStdNumber;
	}
	/**
	 * @param pubTitleOrStdNumber the pubTitleOrStdNumber to set
	 */
	public void setPubTitleOrStdNumber(String pubTitleOrStdNumber) {
		mPubTitleOrStdNumber = pubTitleOrStdNumber;
	}
	/**
	 * @return the publisherOrRightsholder
	 */
	public String getPublisherOrRightsholder() {
		return mPublisherOrRightsholder;
	}
	/**
	 * @param publisherOrRightsholder the publisherOrRightsholder to set
	 */
	public void setPublisherOrRightsholder(String publisherOrRightsholder) {
		mPublisherOrRightsholder = publisherOrRightsholder;
	}
	/**
	 * @return the authorOrEditor
	 */
	public String getAuthorOrEditor() {
		return mAuthorOrEditor;
	}
	/**
	 * @param authorOrEditor the authorOrEditor to set
	 */
	public void setAuthorOrEditor(String authorOrEditor) {
		mAuthorOrEditor = authorOrEditor;
	}
	/**
	 * @return the seriesName
	 */
	public String getSeriesName() {
		return mSeriesName;
	}
	/**
	 * @param seriesName the seriesName to set
	 */
	public void setSeriesName(String seriesName) {
		mSeriesName = seriesName;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return mCountry;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		mCountry = country;
	}
	/**
	 * @return the language
	 */
	public String getLanguage() {
		return mLanguage;
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		mLanguage = language;
	}
	/**
	 * @return the pubType
	 */
	public String getPubType() {
		return mPubType;
	}
	/**
	 * @param pubType the pubType to set
	 */
	public void setPubType(String pubType) {
		mPubType = pubType;
	}
	/**
	 * @return the hasRights
	 */
	public String getHasRights() {
		return mHasRights;
	}
	/**
	 * @param hasRights the hasRights to set
	 */
	public void setHasRights(String hasRights) {
		mHasRights = hasRights;
	}

	public List<String> getSourceNames() {
		return mSourceNames;
	}

	public void setSourceNames(List<String> sourceNames) {
		mSourceNames = sourceNames;
	}

	/**
	 * @return the mTfWrkInst
	 */
	public String getTfWrkInst() {
		return mTfWrkInst;
	}

	/**
	 * @param mTfWrkInst the mTfWrkInst to set
	 */
	public void setTfWrkInst(String mTfWrkInst) {
		this.mTfWrkInst = mTfWrkInst;
	}

}
