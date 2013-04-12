/**
 * 
 */
package com.copyright.svc.searchRetrieval.mock;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.copyright.mediadelivery.framework.api.IMetaDataManager;
import com.copyright.mediadelivery.framework.exception.InvalidDataException;
import com.copyright.mediadelivery.framework.internal.metadata.MediaTypeManager;
import com.copyright.mediadelivery.framework.internal.value.CategoryValue;
import com.copyright.mediadelivery.framework.internal.value.ContentNodeValue;
import com.copyright.mediadelivery.framework.internal.value.MediaCollectionValue;
import com.copyright.mediadelivery.framework.internal.value.MediaTypeValue;
import com.copyright.mediadelivery.framework.internal.value.ProviderValue;
import com.copyright.mediadelivery.framework.internal.value.SourceValue;
import com.copyright.mediadelivery.framework.internal.value.TypeOfUseValue;

/**
 * @author fgracely
 *
 */
public class ReadyContentMockMetaDataManager implements IMetaDataManager
{
	protected Map<String, CategoryValue> mCatNameToCatValueMap = new HashMap<String, CategoryValue>();	
	protected Map<String, SourceValue> mSourceNameToSourceValueMap = new HashMap<String, SourceValue>();	
	protected Map<String, ProviderValue> mProviderKeyToProviderValueMap = new HashMap<String, ProviderValue>();	
	protected Set<ProviderValue> mProviders;
	protected Set<MediaCollectionValue> mCollections;

	
	public ReadyContentMockMetaDataManager()
	{
		super();
	}

	public Set<MediaCollectionValue> getCollections()
	{
		return mCollections;
	}

	public CategoryValue getCategoryByName(String categoryName)
	{
		CategoryValue value = mCatNameToCatValueMap.get(categoryName.toUpperCase());
		if (value == null)
		{
			throw new InvalidDataException("Unrecognized category name:" + categoryName);
		}
		return value;
	}
	
	public SourceValue getSourceByName(String sourceName)
	{
		SourceValue value = mSourceNameToSourceValueMap.get(sourceName.toUpperCase());
		if (value == null)
		{
			throw new InvalidDataException("Unrecognized source name:" + sourceName);
		}

		return value;
	}

	public Set<ProviderValue> getProviders()
	{
		return mProviders;
	}
	public void init(Set<ProviderValue> providers, Set<MediaCollectionValue> collections)
	{
		mCollections = collections;
		mProviders = providers;
		
		for (MediaCollectionValue col: mCollections)
		{
			for (ContentNodeValue node: col.getContentNodes())
			{
				mCatNameToCatValueMap.put(node.getCategory().getName().toUpperCase(), node.getCategory());
				mSourceNameToSourceValueMap.put(node.getSource().getName().toUpperCase(), node.getSource());

			}
		}
		for (ProviderValue provider: mProviders)
		{
			mProviderKeyToProviderValueMap.put(provider.getKey().toUpperCase(), provider);
		}
		
	}	
	
	public ProviderValue getProviderByKey(String key)
	{
		return mProviderKeyToProviderValueMap.get(key.toUpperCase());
	}

	public MediaCollectionValue getCollectionByName(String name)
	{
		MediaCollectionValue col = null;
		for (MediaCollectionValue currCol: mCollections)
		{
			if (currCol.getName().equalsIgnoreCase(name))
			{
				col = currCol;
				break;
			}
		}
		if (col == null)
		{
			throw new InvalidDataException("Unrecognized collection name:" + name);
		}
		return col;
	}

	public MediaTypeValue getMediaTypeValueByName(String name)
	{
		return MediaTypeManager.getMediaTypeByName(name);
	}

	public String getNodeGroupByCollectionName(String collectionName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Set<TypeOfUseValue> getTypeOfUses()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<CategoryValue> getAllCategories() {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<MediaCollectionValue> getAllCollections() {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<ContentNodeValue> getAllContentNodes() {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<SourceValue> getAllSources() {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<ContentNodeValue> getContentNodes(SourceValue source,
			CategoryValue category, MediaTypeValue mediaType) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
