package com.webside.user.message.model;

import java.io.Serializable;

public class MessageVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8387122589337714638L;
	//null videoCover,null videoId, null id,h.ID businessId,h.CONTENT,0 count, 0 type 
	private String videoCover;
	private String videoId;
	private Integer count;
	private String id;
	private String businessId;
	private String content;
	private Integer type;
	public String getVideoCover() {
		return videoCover;
	}
	public void setVideoCover(String videoCover) {
		this.videoCover = videoCover;
	}
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
}
