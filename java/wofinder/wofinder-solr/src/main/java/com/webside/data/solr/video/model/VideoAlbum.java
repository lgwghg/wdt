/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.data.solr.video.model;

import org.apache.solr.client.solrj.beans.Field;

import com.webside.data.solr.video.searchable.VideoAlbumSearchable;

/**
 * 专辑 model
 *
 * @author zengxn
 * @date 2017-05-25 14:17:39
 */

public class VideoAlbum implements VideoAlbumSearchable {

	public VideoAlbum() {
	}

	public VideoAlbum(String id, String name) {
		this.id = id;
		this.name = name;
	}

	@Field(ID_FIELD)
	private String id;

	@Field(NAME_FIELD)
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "VideoAlbum [id=" + id + ", name=" + name + "]";
	}

}
