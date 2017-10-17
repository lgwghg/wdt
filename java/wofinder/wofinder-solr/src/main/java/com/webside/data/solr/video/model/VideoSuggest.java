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

import com.webside.data.solr.video.searchable.VideoSuggestSearchable;

/**
 * 视频 model
 *
 * @author zengxn
 * @date 2017-05-25 14:17:39
 */

public class VideoSuggest implements VideoSuggestSearchable {

	public VideoSuggest() {
	}

	public VideoSuggest(String id, String title) {
		this.id = id;
		this.title = title;
	}

	@Field(ID_FIELD)
	private String id;

	@Field(TITLE_FIELD)
	private String title;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "VideoSuggest [id=" + id + ", title=" + title + "]";
	}

}
