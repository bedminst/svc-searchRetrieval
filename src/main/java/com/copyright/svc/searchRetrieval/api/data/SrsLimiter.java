/*
 * Limiter.java
 * Copyright (c) 2009, Copyright Clearance Center, Inc. All rights reserved.
 * ----------------------------------------------------------------------------
 * Revision History
 * 2009-11-24   lwojtowicz    Created.
 * ----------------------------------------------------------------------------
 */

package com.copyright.svc.searchRetrieval.api.data;

import java.io.Serializable;

import com.copyright.base.svc.data.AbstractPersistentDTO;

/**
 * The data object for search limiters.
 * <p>
 *
 * @author lwojtowicz
 *
 */
public class SrsLimiter extends AbstractPersistentDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected String item;
	
	/**
	 * @return the item
	 */
	public String getItem() {
		return item;
	}
	/**
	 * @param item the item to set
	 */
	public void setItem(String item) {
		this.item = item;
	}
	
}