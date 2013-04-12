/*
 * ArticleSearch.java
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
 * Data object for sending a article level search
 *
 */
public class ArticleSearch extends DefaultSearch implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String mArtTitle;
	protected String mAuthor;
	protected String mArtIdno;
	protected String mDate;
	protected String mPubType;
	protected String mItemVolume;
	protected String mItemIssue;
	protected String mItemStartPage;
	
	/**
	 * @return the mArtTitle
	 */
	public String getArtTitle() {
		return mArtTitle;
	}
	/**
	 * @param artTitle the mArtTitle to set
	 */
	public void setArtTitle(String artTitle) {
		mArtTitle = artTitle;
	}
	/**
	 * @return the author
	 */
	public String getAuthor() {
		return mAuthor;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		mAuthor = author;
	}
	/**
	 * @return the mArtIdno
	 */
	public String getArtIdno() {
		return mArtIdno;
	}
	/**
	 * @param artIdno the mArtIdno to set
	 */
	public void setArtIdno(String artIdno) {
		mArtIdno = artIdno;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return mDate;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		mDate = date;
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
	public String getItemVolume() {
		return mItemVolume;
	}
	public void setItemVolume(String itemVolume) {
		mItemVolume = itemVolume;
	}
	public String getItemIssue() {
		return mItemIssue;
	}
	public void setItemIssue(String itemIssue) {
		mItemIssue = itemIssue;
	}
	public String getItemStartPage() {
		return mItemStartPage;
	}
	public void setItemStartPage(String itemStartPage) {
		mItemStartPage = itemStartPage;
	}

}
