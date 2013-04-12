/**
 * 
 */
package com.copyright.svc.searchRetrieval.mock;

import java.util.HashSet;
import java.util.Set;

import com.copyright.mediadelivery.framework.context.DefaultCoreMediaContext;
import com.copyright.mediadelivery.framework.internal.control.DefaultAccessControlModel;
import com.copyright.mediadelivery.framework.internal.metadata.MediaTypeManager;
import com.copyright.mediadelivery.framework.internal.value.CategoryValue;
import com.copyright.mediadelivery.framework.internal.value.ContentNodeValue;
import com.copyright.mediadelivery.framework.internal.value.CustomerValue;
import com.copyright.mediadelivery.framework.internal.value.LicenseValue;
import com.copyright.mediadelivery.framework.internal.value.MediaCollectionValue;
import com.copyright.mediadelivery.framework.internal.value.ProviderValue;
import com.copyright.mediadelivery.framework.internal.value.SourceValue;
import com.copyright.mediadelivery.framework.internal.value.UserValue;
import com.copyright.mediadelivery.svc.api.IMetaDataServices;
import com.copyright.mediadelivery.svc.api.MediaDeliveryServiceFinder;

/**
 * @author lwojtowicz, based on MockMediaContext by fgracely
 *
 */
public class NatureMediaContext extends DefaultCoreMediaContext
{
	public static interface ProviderKeys
	{
		public static final String NATURE = "NATURE";
	}

	// Provider Values
	public static final ProviderValue PROV_WR = 	  new ProviderValue(ProviderKeys.NATURE);

	// Providers and Collections
	public static final Set<ProviderValue> PROVIDERS = 		  new HashSet<ProviderValue>();
	public static final Set<MediaCollectionValue> COLLECTIONS = new HashSet<MediaCollectionValue>();	

	// Source Values
	public static final SourceValue SOURCE_GENERAL = new SourceValue("GENERAL");
	
	// Collection Values
	public static final MediaCollectionValue COL_STD = new MediaCollectionValue("STANDARD");

	// License Values
	public static final LicenseValue LIC_STD = new LicenseValue(0, "LIC-STANDARD", COL_STD);

	// Category Values
	public static final CategoryValue CATEGORY_GENERAL = new CategoryValue("GENERAL");

	// ContentNode Values
	public static final ContentNodeValue NODE_JOURNAL =		new ContentNodeValue(SOURCE_GENERAL, CATEGORY_GENERAL, MediaTypeManager.JOURNAL);
//	public static final ContentNodeValue NODE_ARTICLE =		new ContentNodeValue(SOURCE_GENERAL, CATEGORY_GENERAL, MediaTypeManager.ARTICLE);
//	public static final ContentNodeValue NODE_BOOK =			new ContentNodeValue(SOURCE_GENERAL, CATEGORY_GENERAL, MediaTypeManager.BOOK);
//	public static final ContentNodeValue NODE_BOOK_CHAPTER =	new ContentNodeValue(SOURCE_GENERAL, CATEGORY_GENERAL, MediaTypeManager.BOOK_CHAPTER);

	// Binding...
	static
	{
		// Adding ContentNodes to CollectionValues
		COL_STD.addContentNode(NODE_JOURNAL);
//		COL_STD.addContentNode(NODE_ARTICLE);		
//		COL_STD.addContentNode(NODE_BOOK);		
//		COL_STD.addContentNode(NODE_BOOK_CHAPTER);	

		// Adding ContentNodes to ProviderValues
		PROV_WR.addNode(NODE_JOURNAL);
//		PROV_WR.addNode(NODE_ARTICLE);
//		PROV_WR.addNode(NODE_BOOK);
//		PROV_WR.addNode(NODE_BOOK_CHAPTER);
		
		// Adding ProviderValues to Providers
		PROVIDERS.add(PROV_WR);		
		
		// Adding CollectionValues to Collections
		COLLECTIONS.add(COL_STD);
	}
	
	public NatureMediaContext()
	{
		DefaultAccessControlModel model = new DefaultAccessControlModel(this);
		
		IMetaDataServices metaServices = MediaDeliveryServiceFinder.getMetaDataServices();

		ReadyContentMockMetaDataManager manager = (ReadyContentMockMetaDataManager)metaServices.getMetaDataManager(); 
			
		manager.init(PROVIDERS, COLLECTIONS);
		setMetaDataManager(manager);
		
		setAccessControlModel(model);

		setUser(new UserValue());
		setCustomer(new CustomerValue("NOVARTIS"));
		setDebugMode(true);		
		init();
		model.init();
	}

	protected void init()
	{
		getAccessControlModel().addLicense(LIC_STD);
	}
}
