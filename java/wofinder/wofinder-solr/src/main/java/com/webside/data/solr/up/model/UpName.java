/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.data.solr.up.model;

import org.apache.solr.client.solrj.beans.Field;

import com.webside.data.solr.up.searchable.UpNameSearchable;

/**
 * 视频作者名称 model
 *
 * @author zengxn
 * @date 2017-05-25 14:17:39
 */

public class UpName implements UpNameSearchable {

	public UpName() {
	}

	public UpName(String id, String name, String type) {
		this.id = id;
		this.name = name;
		this.type = type;
	}

	@Field(ID_FIELD)
	private String id;

	@Field(NAME_FIELD)
	private String name;

	@Field(TYPE_FIELD)
	private String type;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "UpName [id=" + id + ", name=" + name + ", type=" + type + "]";
	}

}
