package com.webside.crawler.pageprocessor.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.utils.HttpConstant;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.webside.common.GlobalConstant;
import com.webside.crawler.pageprocessor.VideoPageProcessor;
import com.webside.dict.model.DictEntity;
import com.webside.sys.model.AttributeEntity;
import com.webside.sys.model.AttributeValueEntity;
import com.webside.sys.model.ValueEntity;
import com.webside.up.model.UpEntity;
import com.webside.up.model.UpStationEntity;
import com.webside.up.model.UpValueEntity;
import com.webside.util.DateUtils;
import com.webside.util.IdGen;
import com.webside.util.StringUtils;
import com.webside.video.model.VideoCommentEntity;
import com.webside.video.model.VideoEntity;
import com.webside.video.model.VideoStationEntity;
import com.webside.video.model.VideoValueEntity;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Component
public class BilibiliVideoPageProcessor extends VideoPageProcessor{
	
	public static final Logger logger = Logger.getLogger(BilibiliVideoPageProcessor.class);
	
    /**
     * tid
     * 17：单机联机
     * 171：电子竞技
     * 172：手机游戏
     * 65：网络游戏
     * 173：桌游棋牌
     * 121：GMV
     * 136：音游
     * 19：Mugen
     */
    private final String list_url = "http://api.bilibili.com/archive_rank/getarchiverankbypartion\\?type=json&tid=(17|171|172|65|173|121|136|19)&pn=[0-9]\\d*&_=[0-9]\\d*";
    private final String page_url = "http://api.bilibili.com/archive_rank/getarchiverankbypartion\\?callback=jQuery&type=json&tid=(17|171|172|65|173|121|136|19)&pn=[0-9]\\d*&_=[0-9]\\d*";
    private final String space_url="http://space.bilibili.com/ajax/member/GetInfo.*";
    private final String uptags_url="http://space.bilibili.com/ajax/member/getTags\\?mids=[0-9]\\d*&_=[0-9]\\d*";
    private final String upvideonum_url="http://space.bilibili.com/ajax/member/getNavNum\\?mid=[0-9]\\d*&_=[0-9]\\d*";
    private final String comment_url="http://api.bilibili.com/x/v2/reply\\?jsonp=jsonp&pn=[0-9]\\d*&type=[0-9]\\d*&oid=[0-9]\\d*&sort=[0-9]\\d*&_=[0-9]\\d*";
    private final String commentload_url="http://api.bilibili.com/x/v2/reply\\?jsonp=jsonp&type=[0-9]\\d*&oid=[0-9]\\d*&sort=[0-9]\\d*&_=[0-9]\\d*";
    private final String commentpsload_url="http://api.bilibili.com/x/v2/reply/reply\\?jsonp=jsonp&type=1&oid=[0-9]\\d*&ps=[0-9]\\d*&root=[0-9]\\d*&_=[0-9]\\d*";
    private final String commentps_url = "http://api.bilibili.com/x/v2/reply/reply\\?jsonp=jsonp&pn=[0-9]\\d*&type=1&oid=[0-9]\\d*&ps=[0-9]\\d*&root=[0-9]\\d*&_=[0-9]\\d*";
    private final String tags_url="http://api.bilibili.com/x/tag/archive/tags\\?aid=[0-9]\\d*&jsonp=jsonp&_=[0-9]\\d*";
    
	@Override
	public void processData(Page page) {
		
		VideoEntity videoEntity;
		VideoEntity videoDataEntity;
		VideoStationEntity videoStationEntity;
		VideoStationEntity videoStationDataEntity;
		VideoStationEntity videoStationSameDataEntity=null;
		UpEntity upEntity;
		UpStationEntity upStationEntity;
		UpStationEntity upStationDataEntity;
		UpValueEntity upValueEntity;
		VideoValueEntity videoValueEntity;
		ValueEntity valueEntity;
		AttributeValueEntity attributeValueEntity;
		AttributeEntity attributeEntity;
		VideoCommentEntity videoCommentEntity;
		
//		VideoService videoService = getVideoService();
//		VideoStationService videoStationService = getVideoStationService();
//		UpStationService upStationService = getUpStationService();
//		ValueService valueService = getValueService();
//		AttributeValueService attributeValueService = getAttributeValueService();
//		VideoValueService videoValueService = getVideoValueService();
//		UpValueService upValueService = getUpValueService();
//		VideoCommentService videoCommentService = getVideoCommentService();
//		UpService upService = getUpService();
		
		//String uurl=page.getUrl().get();
		
		//logger.info("爬虫info查看信息=========url:"+uurl);

			//如果是列表页
			if(page.getUrl().regex(page_url).match()){
//				logger.info("爬虫info查看信息=========进入计算列表页码页码");
				String datalist = page.getRawText();
				JSONObject json = JSONObject.parseObject(datalist).getJSONObject("data");
				Map<String, String> mapPage = JSONObject.parseObject(json.getString("page"), Map.class);
				JSONArray jsonArray = json.getJSONArray("archives");
	        	List<Map> archivesList = JSON.parseArray(jsonArray.toJSONString(), Map.class);
				long videonum = videoStationService.queryCountByReInvalid(GlobalConstant.STATION_TYPE_2, StringUtils.toString(archivesList.get(0).get("tname")));
				long total = StringUtils.toLong(mapPage.get("count"));
				int size = StringUtils.toInteger(mapPage.get("size"));
				int pagenum = StringUtils.toInteger((total-videonum)%size)==0?StringUtils.toInteger((total-videonum)/size):StringUtils.toInteger((total-videonum)/size+1);
				if(pagenum!=0){
					String pageurl=page.getUrl().get().replaceAll("pn=[0-9]\\d*", "pn="+pagenum).replaceAll("callback=jQuery&", "").replaceAll("&_=[0-9]\\d*", "&_="+new Date().getTime());
//					logger.info("首次爬虫info待爬取列表========="+pageurl);
	    			page.addTargetRequest(pageurl);
				}
			}else if(page.getUrl().regex(list_url).match()){	//如果是视频列表
				logger.info("爬虫info查看信息====进入视频列表页："+page.getUrl().get());
				String datalist = page.getRawText();
				if(datalist.indexOf("archives is empty")==-1){
					JSONObject json = JSONObject.parseObject(datalist).getJSONObject("data");
					Map<String, String> mapPage = JSONObject.parseObject(json.getString("page"), Map.class);
					//JSONArray.parse(StringUtils.toString(json.get("archives")));
		        	JSONArray jsonArray = json.getJSONArray("archives");
		        	List<Map> archivesList = JSON.parseArray(jsonArray.toJSONString(), Map.class); 
		        	long videonum = videoStationService.queryCountByReInvalid(GlobalConstant.STATION_TYPE_2, StringUtils.toString(archivesList.get(0).get("tname")));
					long total = StringUtils.toLong(mapPage.get("count"));
					int currentNum = StringUtils.toInteger(mapPage.get("num"));
					int size = StringUtils.toInteger(mapPage.get("size"));
					int rest = StringUtils.toInteger((total-videonum)%size);
					int pagenum = StringUtils.toInteger(rest)==0?StringUtils.toInteger((total-videonum)/size):StringUtils.toInteger((total-videonum)/size+1);
					int lastpagenum = StringUtils.toInteger(total%size==0?total/size:total/size+1);
		        	
		        	if(!archivesList.isEmpty()&&archivesList.size()>=rest&&currentNum==pagenum){
		        		if(lastpagenum!=currentNum&&archivesList.size()<size&&rest==1){
		        			videoStationEntity = new VideoStationEntity();
		        			videoStationEntity.setId(IdGen.uuid());
		        			videoStationEntity.setVid(IdGen.uuid());
		        			videoStationEntity.setCategory(StringUtils.toString(archivesList.get(0).get("tname")));
		        			videoStationEntity.setStation(new DictEntity(GlobalConstant.STATION_TYPE_2));
		        			videoStationEntity.setStatus(new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_9));
		        			videoStationEntity.setCreateTime(StringUtils.toString((System.currentTimeMillis())));
		        			videoStationService.insert(videoStationEntity);
		        		}else{
		        			if(rest==0){
		        				rest=size;
		        			}
		        			if(rest>archivesList.size()){
		            			rest = archivesList.size();
		            		}
		        			for(int i=rest;i>0;i--){
		            			Map map = archivesList.get(i-1);
		            			videoEntity = new VideoEntity();
		            			videoStationEntity = new VideoStationEntity();
		            			upEntity = new UpEntity();
		            			upStationEntity = new UpStationEntity();
		            			videoDataEntity = videoService.findByTitle(quoteReplacement(StringUtils.toString(map.get("title"))));
		            			videoStationDataEntity = videoStationService.findByVideoStationId(null, GlobalConstant.STATION_TYPE_2, null,StringUtils.toString(map.get("aid")));
		            			upStationDataEntity = upStationService.findByStationAndHomeId(GlobalConstant.STATION_TYPE_2, map.get("mid").toString());
		            			
		            			JSONObject statJson = JSONObject.parseObject(StringUtils.toString(map.get("stat")));
		            			
		            			//先处理up主。
		            			if(upStationDataEntity==null){	//如果为空，先新增UP主，再新增站点UP主，这里暂时不做UP主的合并逻辑判断。
		            				//UP主
		                			upEntity.setId(IdGen.uuid()); 
		                			upEntity.setName(quoteReplacement(StringUtils.toString(map.get("author"))));
		                			upEntity.setAvatar(StringUtils.toString(map.get("face")));
		                			upEntity.setStatus(new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1));
		                			upEntity.setCreateTime(StringUtils.toString((System.currentTimeMillis())));
		                			upService.insert(upEntity);
		                			
		                			//up主关联站点
		                			upStationEntity.setUp(upEntity);
		                			upStationEntity.setId(IdGen.uuid());
		                			upStationEntity.setHomeUrl("http://space.bilibili.com/"+map.get("mid"));
		                			upStationEntity.setName(quoteReplacement(StringUtils.toString(map.get("author"))));
		                			upStationEntity.setHomeId(StringUtils.toString(map.get("mid")));
		                			upStationEntity.setUpAvatar(StringUtils.toString(map.get("face")));;
		                			upStationEntity.setStation(new DictEntity(GlobalConstant.STATION_TYPE_2));
		                			upStationEntity.setStatus(new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1));
		                			upStationEntity.setCreateTime(StringUtils.toString((System.currentTimeMillis())));
		                			upStationService.insert(upStationEntity);
		                			
		                			//List<UpNameEntity> upNameEntities = upNameService.queryListByUpId(upEntity.getId());
		                			//因为此处只有新增没做更新操作，所以也跟着不去做多名判断了。
//		                			upNameEntity = new UpNameEntity();
//		                			upNameEntity.setName(StringUtils.toString(map.get("author")));
//		                			upNameEntity.setType(new DictEntity(GlobalConstant.UP_NAME_TYPE_1));
//		                			upNameEntity.setStatus(new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1));
//		                			upNameEntity.setCreateTime(StringUtils.toString((System.currentTimeMillis())));
//		                			upNameEntity.setUp(upEntity);
//		                			upNameService.insert(upNameEntity);
		                			
		            			}else{
		            				upEntity = upStationDataEntity.getUp();
		            				upStationEntity = upStationDataEntity;
		            			}
		            			
		            			if(videoDataEntity!=null){
		            				videoStationSameDataEntity = videoStationService.findByVideoStationId(videoDataEntity.getId(), GlobalConstant.STATION_TYPE_2, null,null);
		            			}
		            			
		            			if(videoStationDataEntity==null){
		            				if(videoDataEntity!=null&&videoStationSameDataEntity==null){
		            					videoEntity = videoDataEntity;
		            				}else{
		            					//视频
		                    			videoEntity.setStatus(new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1));
		                    			videoEntity.setCreateTime(StringUtils.toString(System.currentTimeMillis()));
		                    			if(StringUtils.toLong(map.get("play"))>100000){
		                    				videoEntity.setScore(8.4d);
		                    			}else if(StringUtils.toLong(map.get("play"))>50000&&StringUtils.toLong(map.get("play"))<=100000){
		                    				videoEntity.setScore(7.8d);
		                    			}else if(StringUtils.toLong(map.get("play"))<=50000&&StringUtils.toLong(map.get("play"))>10000){
		                    				videoEntity.setScore(7.3d);
		                    			}else{
		                    				videoEntity.setScore(6.9d);
		                    			}
		                    			if(map.get("title")!=null){
		                    				videoEntity.setTitle(quoteReplacement(StringUtils.toString(map.get("title"))));
		                    			}
		                    			if(map.get("pic")!=null){
		                    				videoEntity.setCover(StringUtils.toString(map.get("pic")));
		                    			}
		                    			if(map.get("duration")!=null){
		                    				videoEntity.setDuration(Double.parseDouble(StringUtils.toString(map.get("duration"))));
		                    			}
		                    			videoEntity.setId(IdGen.uuid());
		                    			videoService.insert(videoEntity);
		            				}
		            				//视频站点
		            				if(map.get("title")!=null){
		                				videoStationEntity.setTitle(quoteReplacement(StringUtils.toString(map.get("title"))));
		                			}
		                			if(map.get("description")!=null){
		                				videoStationEntity.setIntroduction(quoteReplacement(StringUtils.toString(map.get("description"))));
		                			}
		                			if(map.get("pic")!=null){
		                				videoStationEntity.setCover(StringUtils.toString(map.get("pic")));
		                			}
		                			if(map.get("duration")!=null){
		                				videoStationEntity.setDuration(Double.parseDouble(StringUtils.toString(map.get("duration"))));
		                			}
		                			if(videoEntity!=null&&videoEntity.getId()!=null){
		                				
		                			}else{
		                				videoEntity.setId(IdGen.uuid());
		                			}
		                			videoStationEntity.setId(IdGen.uuid());
		                			videoStationEntity.setVideo(videoEntity);
		                			videoStationEntity.setVid(map.get("aid").toString());
		                			videoStationEntity.setUrl("http://www.bilibili.com/video/av"+map.get("aid")+"/");
		                			videoStationEntity.setFlashUrl("http://static.hdslb.com/miniloader.swf?aid="+map.get("aid")+"&page=1");
		                			videoStationEntity.setCategory(StringUtils.toString(map.get("tname")));
		                			videoStationEntity.setPublished(String.valueOf(DateUtils.getStringDate(StringUtils.toString(map.get("create")),DateUtils._DEFAULT1).getTime()));	//2017-06-06 15:48
		                			videoStationEntity.setViewCount(StringUtils.toLong(map.get("play")));
		                			videoStationEntity.setCommentCount(StringUtils.toLong(statJson.get("reply")));
		                			videoStationEntity.setFavoriteCount(StringUtils.toLong(map.get("favorites")));
		                			videoStationEntity.setUp(upEntity);
		                			videoStationEntity.setStation(new DictEntity(GlobalConstant.STATION_TYPE_2));
		                			videoStationEntity.setStatus(new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1));
		                			videoStationEntity.setCreateTime(StringUtils.toString((System.currentTimeMillis())));
		                			videoStationService.insert(videoStationEntity);
		            			}else{
		            				//视频站点
		            				if(videoDataEntity!=null&&videoDataEntity.getId()!=null&&videoStationDataEntity.getVideo().getId()!=videoDataEntity.getId()){		//已存在站点视频并且已存在同标题视频，更新视频
		            					//&&videoDataEntity.getDuration()==videoStationDataEntity.getDuration()
		            					// 视频在该站点为空，说明是其他站点视频，进行合并
		            					if (videoStationSameDataEntity == null || StringUtils.isBlank(videoStationSameDataEntity.getId())) {
		            						Double num = Math.abs(videoDataEntity.getDuration() - videoStationDataEntity.getDuration());
		            						if (num <= 3) {// 视频时长误差不超过3秒
		            							VideoEntity oldVideo = videoStationDataEntity.getVideo();
		            							oldVideo.setStatus(new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_0));
		            							videoService.updateById(oldVideo);
		            							videoCommentService.updateVideoIdByVid(videoDataEntity.getId(), oldVideo.getId());	//将视频评论移动到新评论下
		            							videoValueService.updateVideoIdByTargetForDif(videoDataEntity.getId(), oldVideo.getId());// 将标签移到原视频下
		            							
		            							if(videoDataEntity!=null&&videoDataEntity.getId()!=null){
		            								videoStationDataEntity.setVideo(videoDataEntity);
		            							}
		            						}
		            					}
		            					
		            				}
		            				if(map.get("title")!=null){
		            					videoStationDataEntity.setTitle(quoteReplacement(StringUtils.toString(map.get("title"))));
		                			}
		                			if(map.get("description")!=null){
		                				videoStationDataEntity.setIntroduction(quoteReplacement(StringUtils.toString(map.get("description"))));
		                			}
		                			if(map.get("pic")!=null){
		                				videoStationDataEntity.setCover(StringUtils.toString(map.get("pic")));
		                			}
		                			if(map.get("duration")!=null){
		                				videoStationDataEntity.setDuration(Double.parseDouble(StringUtils.toString(map.get("duration"))));
		                			}
		                			videoStationDataEntity.setFlashUrl("http://static.hdslb.com/miniloader.swf?aid="+map.get("aid")+"&page=1");
		                			videoStationDataEntity.setCategory(StringUtils.toString(map.get("tname")));
		                			videoStationDataEntity.setPublished(String.valueOf(DateUtils.getStringDate(StringUtils.toString(map.get("create")),DateUtils._DEFAULT1).getTime()));	//2017-06-06 15:48
		                			videoStationDataEntity.setViewCount(StringUtils.toLong(map.get("play")));
		                			videoStationDataEntity.setCommentCount(StringUtils.toLong(statJson.get("reply")));
		                			videoStationDataEntity.setFavoriteCount(StringUtils.toLong(map.get("favorites")));
		        					videoStationService.updateById(videoStationDataEntity);
		            			}
		            			if(map.get("mid")!=null&&!StringUtils.toString(map.get("mid")).equals("0")){
		            				String strurl = "http://space.bilibili.com/ajax/member/getTags?mids="+map.get("mid")+"&_="+new Date().getTime();
		                			page.addTargetRequest(strurl);
		                			String strurl2= "http://space.bilibili.com/ajax/member/getNavNum?mid="+map.get("mid")+"&_="+new Date().getTime();
		                			page.addTargetRequest(strurl2);
		                			page.addTargetRequest(createUpPostRequest(StringUtils.toString(map.get("mid"))));
		            			}
		            			if(StringUtils.toLong(statJson.get("reply"))!=0){
		            				String strurl3="http://api.bilibili.com/x/v2/reply?jsonp=jsonp&type=1&oid="+map.get("aid")+"&sort=0"+"&_="+new Date().getTime();
		            				page.addTargetRequest(strurl3);
		            			}
		            			String strurl4= "http://api.bilibili.com/x/tag/archive/tags?aid="+StringUtils.toString(map.get("aid"))+"&jsonp=jsonp"+"&_="+new Date().getTime();
		            			page.addTargetRequest(strurl4);
		            		}
		        		}
		        	}
		        	//计算下一个列表页码
		    		if(archivesList!=null&&archivesList.size()>0){
		    			videonum = videoStationService.queryCountByReInvalid(GlobalConstant.STATION_TYPE_2, StringUtils.toString(archivesList.get(0).get("tname")));
		    			rest = StringUtils.toInteger((total-videonum)%size);
		    			pagenum = StringUtils.toInteger((total-videonum)%size)==0?StringUtils.toInteger((total-videonum)/size):StringUtils.toInteger((total-videonum)/size+1);
		    			if(pagenum!=0&&rest>=0){
		    				String pageurl=page.getUrl().get().replaceAll("pn=[0-9]\\d*", "pn="+pagenum).replaceAll("&_=[0-9]\\d*", "&_="+new Date().getTime());
		        			page.addTargetRequest(pageurl);
//		        			logger.info("爬虫查询列表页面下一个页面====进入视频列表页： :"+pageurl);
		    			}else{
		    				logger.info("爬虫查询列表页面下一个页面====页面为0： :"+page.getUrl().get()+",videonum:"+videonum+",pagenum:"+pagenum+",total:"+total);
		    			}
		    		}else{
		    			String pageurl=page.getUrl().get().replaceAll("pn=[0-9]\\d*", "pn=1").replaceAll("&_=[0-9]\\d*", "&_="+new Date().getTime());
		    			page.addTargetRequest(pageurl);
		    			logger.error("爬虫查询列表页面没数据====进入视频列表页：archives is empty :"+pageurl);
		    		}
				}else{
					String pageurl=page.getUrl().get().replaceAll("pn=[0-9]\\d*", "pn=1").replaceAll("&_=[0-9]\\d*", "&_="+new Date().getTime());
	    			page.addTargetRequest(pageurl);
					logger.error("爬虫查询列表页面出错====进入视频列表页：archives is empty :"+page.getUrl().get()+",pageurl:"+pageurl);
				}
				
			}else if(page.getUrl().regex(commentload_url).match()){
//				logger.info("爬虫info查看信息=========进入评论预加载计算加载页面");
				String datalist = page.getRawText();
				if(!JSONObject.parseObject(datalist).get("message").equals("禁止评论")){
					JSONObject json = JSONObject.parseObject(datalist).getJSONObject("data");
					Map<String, String> mapPage = JSONObject.parseObject(json.getString("page"), Map.class);
					//Map<String, String> upper = JSONObject.parseObject(json.getString("upper"), Map.class);
//		        	List<Map> replies = JSON.parseArray(json.getJSONArray("replies").toJSONString(), Map.class);  
		        	String oid = getDataFromUrl(page.getUrl().get(),"oid");//page.getUrl().get().substring(page.getUrl().get().indexOf("oid")+4, page.getUrl().get().lastIndexOf("&"));
					videoStationEntity =  videoStationService.findByVideoStationId(null, GlobalConstant.STATION_TYPE_2, null,oid);
					
					long total = StringUtils.toLong(mapPage.get("count"));
					JSONObject upper = json.getJSONObject("upper");
					if(StringUtils.isNotBlank(upper.getString("top"))){	//存在置顶数据
						if(total>0){
							total = total - 1;
						}
						logger.info("我是置顶消息！！！vid:"+videoStationEntity.getVid());
						JSONObject topjson = JSONObject.parseObject(upper.getString("top"));
						videoCommentEntity = videoCommentService.findByStationCid(videoStationEntity.getVideo().getId(),GlobalConstant.STATION_TYPE_2,topjson.getString("rpid"));
						if(videoCommentEntity==null){
							videoCommentEntity = new VideoCommentEntity();
							videoCommentEntity.setId(IdGen.uuid());
							videoCommentEntity.setVideo(videoStationEntity.getVideo());
							videoCommentEntity.setCommentId(topjson.getString("rpid"));
							Map<String, String> content = JSONObject.parseObject(topjson.getString("content"), Map.class);
							if(StringUtils.isNotBlank(content.get("message"))&&StringUtils.toString(content.get("message")).indexOf("\\xF0\\x9F\\x90\\xB8")==-1){
								videoCommentEntity.setCommentContent(quoteReplacement((StringUtils.toString(content.get("message")).replaceAll("[\\x{10000}-\\x{10FFFF}]", "*"))));
							}else{
								videoCommentEntity.setCommentContent("空值");
							}
							videoCommentEntity.setCommentCreatetime(topjson.getString("ctime")+"000");
							Map<String, String> member = JSONObject.parseObject(topjson.getString("member"), Map.class);
							videoCommentEntity.setCommentUserId(member.get("mid"));
							videoCommentEntity.setCommentUserName(quoteReplacement(member.get("uname")));
							if(!topjson.getString("root").equals("0")){
								VideoCommentEntity commentParent =  videoCommentService.findByStationCid(videoStationEntity.getVideo().getId(),GlobalConstant.STATION_TYPE_2,topjson.getString("root"));
								if(commentParent==null){
									commentParent = new VideoCommentEntity();
									commentParent.setId(IdGen.uuid());
									commentParent.setVideo(videoStationEntity.getVideo());
									commentParent.setStation(new DictEntity(GlobalConstant.STATION_TYPE_2));
									commentParent.setStatus(new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1));
									commentParent.setCreateTime(StringUtils.toString((System.currentTimeMillis())));
									commentParent.setCommentId(topjson.getString("root"));
									commentParent.setLikeNum(0l);
									videoCommentService.insert(commentParent);
								}
								videoCommentEntity.setCommentParent(commentParent);
							}
							videoCommentEntity.setStation(new DictEntity(GlobalConstant.STATION_TYPE_2));
							videoCommentEntity.setStatus(new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1));
							videoCommentEntity.setCreateTime(StringUtils.toString((System.currentTimeMillis())));
							videoCommentEntity.setLikeNum(topjson.getLong("like"));
							videoCommentService.insert(videoCommentEntity);
						}else{
							Map<String, String> content = JSONObject.parseObject(topjson.getString("content"), Map.class);
							if(StringUtils.isNotBlank(content.get("message"))&&StringUtils.toString(content.get("message")).indexOf("\\xF0\\x9F\\x90\\xB8")==-1){
								videoCommentEntity.setCommentContent(quoteReplacement(StringUtils.toString(content.get("message")).replaceAll("[\\x{10000}-\\x{10FFFF}]", "*").replaceAll("\\xF0\\x9F\\x90\\xB8", "*")));
							}else{
								videoCommentEntity.setCommentContent("空值");
							}
							videoCommentEntity.setCommentCreatetime(topjson.getString("ctime")+"000");
							Map<String, String> member = JSONObject.parseObject(topjson.getString("member"), Map.class);
							videoCommentEntity.setCommentUserId(member.get("mid"));
							videoCommentEntity.setCommentUserName(quoteReplacement(member.get("uname")));
							if(!topjson.getString("root").equals("0")){
								VideoCommentEntity commentParent =  videoCommentService.findByStationCid(videoStationEntity.getVideo().getId(),GlobalConstant.STATION_TYPE_2,topjson.getString("root"));
								if(commentParent==null){
									commentParent = new VideoCommentEntity();
									commentParent.setId(IdGen.uuid());
									commentParent.setCommentId(topjson.getString("root"));
									commentParent.setVideo(videoStationEntity.getVideo());
									commentParent.setStation(new DictEntity(GlobalConstant.STATION_TYPE_2));
									commentParent.setStatus(new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1));
									commentParent.setCreateTime(StringUtils.toString((System.currentTimeMillis())));
									commentParent.setLikeNum(0l);
									videoCommentService.insert(commentParent);
								}
								videoCommentEntity.setCommentParent(commentParent);
							}
							videoCommentEntity.setStation(new DictEntity(GlobalConstant.STATION_TYPE_2));
							videoCommentEntity.setStatus(new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1));
							videoCommentEntity.setUpdateTime(StringUtils.toString((System.currentTimeMillis())));
							videoCommentEntity.setLikeNum(topjson.getLong("like"));
							videoCommentService.updateById(videoCommentEntity);
						}
						if(topjson.getLong("rcount")>0){
							String pageurl = "http://api.bilibili.com/x/v2/reply/reply?jsonp=jsonp&type=1&oid="+oid+"&ps=10&root="+topjson.getString("rpid")+"&_="+new Date().getTime();
							page.addTargetRequest(pageurl);
//							logger.info("爬虫info待爬取列表========="+pageurl);
						}
					}
					if(total!=0){
						long commentnum = videoCommentService.queryCountByReInvalid(GlobalConstant.STATION_TYPE_2, videoStationEntity.getVideo().getId(), null, "yes");
						int size = StringUtils.toInteger(mapPage.get("size"));
						int pagenum = StringUtils.toInteger((total-commentnum)%size)==0?StringUtils.toInteger((total-commentnum)/size):StringUtils.toInteger((total-commentnum)/size+1);
						if(pagenum!=0&&total>commentnum){
							String pageurl="http://api.bilibili.com/x/v2/reply?jsonp=jsonp&pn="+pagenum+"&type=1&oid="+oid+"&sort=0"+"&_="+new Date().getTime();
							page.addTargetRequest(pageurl);
						}
					}
				}
			}else if(page.getUrl().regex(comment_url).match()){
//				logger.info("爬虫info查看信息=========进入评论预页面");
				String datalist = page.getRawText();
				JSONObject json = JSONObject.parseObject(datalist).getJSONObject("data");
				Map<String, String> mapPage = JSONObject.parseObject(json.getString("page"), Map.class);
				List<Map> replies = JSON.parseArray(json.getJSONArray("replies").toJSONString(), Map.class);
				String oid = getDataFromUrl(page.getUrl().get(),"oid");//page.getUrl().get().substring(page.getUrl().get().indexOf("oid")+4, page.getUrl().get().lastIndexOf("&"));
				videoStationEntity =  videoStationService.findByVideoStationId(null, GlobalConstant.STATION_TYPE_2, null,oid);
				long commentnum = videoCommentService.queryCountByReInvalid(GlobalConstant.STATION_TYPE_2, videoStationEntity.getVideo().getId(), null, "yes");
				long total = StringUtils.toLong(mapPage.get("count"));
				JSONObject upper = json.getJSONObject("upper");
				if(StringUtils.isNotBlank(upper.getString("top"))){	//存在置顶数据
					total = total - 1;
					commentnum = commentnum - 1;
				}
				int currentNum = StringUtils.toInteger(mapPage.get("num"));
				int size = StringUtils.toInteger(mapPage.get("size"));
				int rest = StringUtils.toInteger((total-commentnum)%size);
				int pagenum = StringUtils.toInteger(rest)==0?StringUtils.toInteger((total-commentnum)/size):StringUtils.toInteger((total-commentnum)/size+1);
				int lastpagenum = StringUtils.toInteger(total%size==0?total/size:total/size+1);
				
				if(replies!=null&&replies.size()>=rest&&currentNum==pagenum){
					if(lastpagenum!=currentNum&&replies.size()<size&&rest==1){
						videoCommentEntity = new VideoCommentEntity();
						videoCommentEntity.setId(IdGen.uuid());
						videoCommentEntity.setVideo(videoStationEntity.getVideo());
						videoCommentEntity.setCommentId(IdGen.uuid());
						videoCommentEntity.setCommentContent("空值");
						videoCommentEntity.setStation(new DictEntity(GlobalConstant.STATION_TYPE_2));
						videoCommentEntity.setStatus(new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_9));
						videoCommentEntity.setCreateTime(StringUtils.toString((System.currentTimeMillis())));
						videoCommentService.insert(videoCommentEntity);
					}else{
						if(rest==0){
	        				rest=size;
	        			}
	        			if(rest>replies.size()){
	            			rest = replies.size();
	            		}
	        			for(int i=rest;i>0;i--){
	            			Map map = replies.get(i-1);
	    					videoCommentEntity = videoCommentService.findByStationCid(videoStationEntity.getVideo().getId(),GlobalConstant.STATION_TYPE_2,StringUtils.toString(map.get("rpid")));
	    					if(videoCommentEntity==null){
	    						videoCommentEntity = new VideoCommentEntity();
	    						videoCommentEntity.setId(IdGen.uuid());
	    						videoCommentEntity.setVideo(videoStationEntity.getVideo());
	    						videoCommentEntity.setCommentId(StringUtils.toString(map.get("rpid")));
	    						Map<String, String> content = JSONObject.parseObject(StringUtils.toString(map.get("content")), Map.class);
	    						if(StringUtils.isNotBlank(content.get("message"))&&StringUtils.toString(content.get("message")).indexOf("\\xF0\\x9F\\x90\\xB8")==-1){
	    							videoCommentEntity.setCommentContent(quoteReplacement(StringUtils.toString(content.get("message")).replaceAll("[\\x{10000}-\\x{10FFFF}]", "*").replaceAll("\\xF0\\x9F\\x90\\xB8", "*")));
	    						}else{
	    							videoCommentEntity.setCommentContent("空值");
	    						}
	    						videoCommentEntity.setCommentCreatetime(StringUtils.toString(map.get("ctime"))+"000");
	    						Map<String, String> member = JSONObject.parseObject(StringUtils.toString(map.get("member")), Map.class);
	    						videoCommentEntity.setCommentUserId(member.get("mid"));
	    						videoCommentEntity.setCommentUserName(quoteReplacement(member.get("uname")));
	    						if(!StringUtils.toString(map.get("root")).equals("0")){
	    							VideoCommentEntity commentParent = videoCommentService.findByStationCid(videoStationEntity.getVideo().getId(),GlobalConstant.STATION_TYPE_2,StringUtils.toString(map.get("root")));
	    							if(commentParent==null){
	    								commentParent = new VideoCommentEntity();
	    								commentParent.setId(IdGen.uuid());
	    								commentParent.setVideo(videoStationEntity.getVideo());
	    								commentParent.setStation(new DictEntity(GlobalConstant.STATION_TYPE_2));
	    								commentParent.setStatus(new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1));
	    								commentParent.setCreateTime(StringUtils.toString((System.currentTimeMillis())));
	    								commentParent.setCommentId(StringUtils.toString(map.get("root")));
	    								commentParent.setLikeNum(0l);
	    								videoCommentService.insert(commentParent);
	    							}
	    							videoCommentEntity.setCommentParent(commentParent);
	    						}
	    						videoCommentEntity.setStation(new DictEntity(GlobalConstant.STATION_TYPE_2));
	    						videoCommentEntity.setStatus(new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1));
	    						videoCommentEntity.setCreateTime(StringUtils.toString((System.currentTimeMillis())));
	    						videoCommentEntity.setLikeNum(StringUtils.toLong(map.get("like")));
	    						videoCommentService.insert(videoCommentEntity);
	    					}else{
	    						Map<String, String> content = JSONObject.parseObject(StringUtils.toString(map.get("content")), Map.class);
	    						if(StringUtils.isNotBlank(content.get("message"))&&StringUtils.toString(content.get("message")).indexOf("\\xF0\\x9F\\x90\\xB8")==-1){
	    							videoCommentEntity.setCommentContent(quoteReplacement(StringUtils.toString(content.get("message")).replaceAll("[\\x{10000}-\\x{10FFFF}]", "*").replaceAll("\\xF0\\x9F\\x90\\xB8", "*")));
	    						}else{
	    							videoCommentEntity.setCommentContent("空值");
	    						}
	    						videoCommentEntity.setCommentCreatetime(StringUtils.toString(map.get("ctime"))+"000");
	    						Map<String, String> member = JSONObject.parseObject(StringUtils.toString(map.get("member")), Map.class);
	    						videoCommentEntity.setCommentUserId(member.get("mid"));
	    						videoCommentEntity.setCommentUserName(quoteReplacement(member.get("uname")));
	    						if(!StringUtils.toString(map.get("root")).equals("0")){
	    							VideoCommentEntity commentParent =  videoCommentService.findByStationCid(videoStationEntity.getVideo().getId(),GlobalConstant.STATION_TYPE_2,StringUtils.toString(map.get("root")));
	    							if(commentParent==null){
	    								commentParent = new VideoCommentEntity();
	    								commentParent.setId(IdGen.uuid());
	    								commentParent.setCommentId(StringUtils.toString(map.get("root")));
	    								commentParent.setVideo(videoStationEntity.getVideo());
	    								commentParent.setStation(new DictEntity(GlobalConstant.STATION_TYPE_2));
	    								commentParent.setStatus(new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1));
	    								commentParent.setCreateTime(StringUtils.toString((System.currentTimeMillis())));
	    								commentParent.setLikeNum(0l);
	    								videoCommentService.insert(commentParent);
	    							}
	    							videoCommentEntity.setCommentParent(commentParent);
	    						}
	    						videoCommentEntity.setCommentId(StringUtils.toString(map.get("rpid")));
	    						videoCommentEntity.setStation(new DictEntity(GlobalConstant.STATION_TYPE_2));
	    						videoCommentEntity.setStatus(new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1));
	    						videoCommentEntity.setUpdateTime(StringUtils.toString((System.currentTimeMillis())));
	    						videoCommentEntity.setLikeNum(StringUtils.toLong(map.get("like")));
	    						videoCommentService.updateById(videoCommentEntity);
	    					}
	    					if(StringUtils.toLong(map.get("rcount"))>0){
	    						String pageurl = "http://api.bilibili.com/x/v2/reply/reply?jsonp=jsonp&type=1&oid="+oid+"&ps=10&root="+StringUtils.toString(map.get("rpid"))+"&_="+new Date().getTime();
	    						page.addTargetRequest(pageurl);
//	    						logger.info("爬虫info待爬取列表========="+pageurl);
	    					}
	    				}
					}
					
					
				}
				if(replies!=null&&replies.size()>0){
					commentnum = videoCommentService.queryCountByReInvalid(GlobalConstant.STATION_TYPE_2, videoStationEntity.getVideo().getId(), null, "yes");
					if(StringUtils.isNotBlank(upper.getString("top"))){	//存在置顶数据
						commentnum = commentnum - 1;
					}
					pagenum = StringUtils.toInteger((total-commentnum)%size)==0?StringUtils.toInteger((total-commentnum)/size):StringUtils.toInteger((total-commentnum)/size+1);
					if(pagenum!=0&&total>commentnum){
						String pageurl="http://api.bilibili.com/x/v2/reply?jsonp=jsonp&pn="+pagenum+"&type=1&oid="+oid+"&sort=0"+"&_="+new Date().getTime();
						page.addTargetRequest(pageurl);
					}
	    		}
			}else if(page.getUrl().regex(commentpsload_url).match()){
//				logger.info("爬虫info查看信息=========评论回复预加载页面");
				String datalist = page.getRawText();
				JSONObject json = JSONObject.parseObject(datalist).getJSONObject("data");
				Map<String, String> root = JSONObject.parseObject(json.getString("root"), Map.class);
//				Map<String, String> upper = JSONObject.parseObject(json.getString("upper"), Map.class);
				Map<String, String> mapPage = JSONObject.parseObject(json.getString("page"), Map.class);
	        	//List<Map> replies = JSON.parseArray(json.getJSONArray("replies").toJSONString(), Map.class);  
				String oid = getDataFromUrl(page.getUrl().get(),"oid");
				videoStationEntity =  videoStationService.findByVideoStationId(null, GlobalConstant.STATION_TYPE_2, null,oid);
//				VideoCommentEntity parentComment = videoCommentService.findByStationCid(GlobalConstant.STATION_TYPE_2, StringUtils.toString(root.get("rpid")));
				long commentnum = videoCommentService.queryCountByReInvalid(GlobalConstant.STATION_TYPE_2, videoStationEntity.getVideo().getId(), StringUtils.toString(root.get("rpid")), null);
				long total = StringUtils.toLong(mapPage.get("count"));
				int size = StringUtils.toInteger(mapPage.get("size"));
				int pagenum = StringUtils.toInteger((commentnum/size))+1;
				if(pagenum!=0&&total!=0&&total>commentnum){
					String pageurl="http://api.bilibili.com/x/v2/reply/reply?jsonp=jsonp&pn="+pagenum+"&type=1&oid="+oid+"&ps="+size+"&root="+StringUtils.toString(root.get("rpid"))+"&_="+new Date().getTime();
					page.addTargetRequest(pageurl);
				}
			}else if(page.getUrl().regex(commentps_url).match()){
//				logger.info("爬虫info查看信息=========评论回复页面");
				String datalist = page.getRawText();
				JSONObject json = JSONObject.parseObject(datalist).getJSONObject("data");
				Map<String, String> mapPage = JSONObject.parseObject(json.getString("page"), Map.class);
				Map<String, String> root = JSONObject.parseObject(json.getString("root"), Map.class);
	        	List<Map> replies = JSON.parseArray(json.getJSONArray("replies").toJSONString(), Map.class);  
	        	String oid = getDataFromUrl(page.getUrl().get(),"oid");
				videoStationEntity =  videoStationService.findByVideoStationId(null, GlobalConstant.STATION_TYPE_2, null,oid);
				VideoCommentEntity parentComment = videoCommentService.findByStationCid(videoStationEntity.getVideo().getId(),GlobalConstant.STATION_TYPE_2, StringUtils.toString(root.get("rpid")));
				long commentnum = videoCommentService.queryCountByReInvalid(GlobalConstant.STATION_TYPE_2, videoStationEntity.getVideo().getId(), StringUtils.toString(root.get("rpid")), null);
				long total = StringUtils.toLong(mapPage.get("count"));
				int size = StringUtils.toInteger(mapPage.get("size"));
				int pagenum = StringUtils.toInteger((commentnum/size))+1;
				int rest = StringUtils.toInteger(commentnum%size);
				if(replies!=null&&replies.size()>0&&StringUtils.toInteger(mapPage.get("num"))==pagenum){
					for(int i=rest;i<replies.size();i++){
	        			Map map = replies.get(i);
						videoCommentEntity = videoCommentService.findByStationCid(videoStationEntity.getVideo().getId(),GlobalConstant.STATION_TYPE_2,StringUtils.toString(map.get("rpid")));
						if(videoCommentEntity==null){
							videoCommentEntity = new VideoCommentEntity();
							videoCommentEntity.setId(IdGen.uuid());
							videoCommentEntity.setVideo(videoStationEntity.getVideo());
							videoCommentEntity.setCommentId(StringUtils.toString(map.get("rpid")));
							Map<String, String> content = JSONObject.parseObject(StringUtils.toString(map.get("content")), Map.class);
							if(StringUtils.isNotBlank(content.get("message"))&&StringUtils.toString(content.get("message")).indexOf("\\xF0\\x9F\\x90\\xB8")==-1){
								videoCommentEntity.setCommentContent(quoteReplacement(StringUtils.toString(content.get("message")).replaceAll("[\\x{10000}-\\x{10FFFF}]", "*").replaceAll("\\xF0\\x9F\\x90\\xB8", "*")));
							}else{
								videoCommentEntity.setCommentContent("空值");
							}
							videoCommentEntity.setCommentCreatetime(StringUtils.toString(map.get("ctime"))+"000");
							Map<String, String> member = JSONObject.parseObject(StringUtils.toString(map.get("member")), Map.class);
							videoCommentEntity.setCommentUserId(member.get("mid"));
							videoCommentEntity.setCommentUserName(quoteReplacement(member.get("uname")));
							videoCommentEntity.setCommentParent(parentComment);
							videoCommentEntity.setStation(new DictEntity(GlobalConstant.STATION_TYPE_2));
							videoCommentEntity.setStatus(new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1));
							videoCommentEntity.setCreateTime(StringUtils.toString((System.currentTimeMillis())));
							videoCommentEntity.setLikeNum(StringUtils.toLong(map.get("like")));
							videoCommentService.insert(videoCommentEntity);
						}else{
							Map<String, String> content = JSONObject.parseObject(StringUtils.toString(map.get("content")), Map.class);
							if(StringUtils.isNotBlank(content.get("message"))&&StringUtils.toString(content.get("message")).indexOf("\\xF0\\x9F\\x90\\xB8")==-1){
								videoCommentEntity.setCommentContent(quoteReplacement(StringUtils.toString(content.get("message")).replaceAll("[\\x{10000}-\\x{10FFFF}]", "*").replaceAll("\\xF0\\x9F\\x90\\xB8", "*")));
							}else{
								videoCommentEntity.setCommentContent("空值");
							}
							videoCommentEntity.setCommentCreatetime(StringUtils.toString(map.get("ctime"))+"000");
							Map<String, String> member = JSONObject.parseObject(StringUtils.toString(map.get("member")), Map.class);
							videoCommentEntity.setCommentUserId(member.get("mid"));
							videoCommentEntity.setCommentUserName(quoteReplacement(member.get("uname")));
							videoCommentEntity.setCommentParent(parentComment);
							videoCommentEntity.setCommentId(StringUtils.toString(map.get("rpid")));
							videoCommentEntity.setStation(new DictEntity(GlobalConstant.STATION_TYPE_2));
							videoCommentEntity.setStatus(new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1));
							videoCommentEntity.setUpdateTime(StringUtils.toString((System.currentTimeMillis())));
							videoCommentEntity.setLikeNum(StringUtils.toLong(map.get("like")));
							videoCommentService.updateById(videoCommentEntity);
						}
						
					}
				}
				if(replies!=null&&replies.size()>0){
					//计算页面
					commentnum = videoCommentService.queryCountByReInvalid(GlobalConstant.STATION_TYPE_2, videoStationEntity.getVideo().getId(), StringUtils.toString(root.get("rpid")), null);
					pagenum = StringUtils.toInteger((commentnum/size))+1;
					if(pagenum!=0&&total!=0&&total>commentnum&&pagenum!=StringUtils.toInteger(mapPage.get("num"))){
						String pageurl="http://api.bilibili.com/x/v2/reply/reply?jsonp=jsonp&pn="+pagenum+"&type=1&oid="+oid+"&ps="+size+"&root="+StringUtils.toString(root.get("rpid"))+"&_="+new Date().getTime();
						page.addTargetRequest(pageurl);
					}
				}
				
			}else if(page.getUrl().regex(space_url).match()){	//获取UP主站点相关信息
//				logger.info("爬虫info查看信息=========进入UP主个人空间");
	    		String datalist = page.getRawText();
	    		if(datalist.indexOf("遇到了一些问题")==-1){
	    			JSONObject json = JSONObject.parseObject(datalist).getJSONObject("data");
	        		String mid = json.getString("mid");
	        		upStationEntity = upStationService.findByStationAndHomeId(GlobalConstant.STATION_TYPE_2, mid);
	        		if(upStationEntity!=null){
	        			upEntity = upStationEntity.getUp();
	        			if(json.containsKey("rank")&&json.get("rank")!=null&&json.get("rank")!=""){
	        				upEntity.setRank(json.getLong("rank"));
	        			}
	        			upEntity.setIntroduction(json.getString("sign"));
	        			upService.updateById(upEntity);
	        			upStationEntity.setUp(upEntity);
	        			upStationEntity.setName(quoteReplacement(json.getString("name")));
	        			upStationEntity.setUpIntroduction(quoteReplacement(json.getString("sign")));
	        			upStationEntity.setUpAvatar(json.getString("face"));
	        			upStationEntity.setHomeId(json.getString("mid"));
	        			upStationEntity.setUpFansCount(json.getLong("fans"));
	        			upStationEntity.setUpFriendCount(json.getLong("friend"));
	        			upStationEntity.setUpPlayCount(json.getLong("playNum"));
	        			upStationService.updateById(upStationEntity);
	        		}
	    		}
	    		
			}else if(page.getUrl().regex(upvideonum_url).match()){	//获取UP主视频数量
//				logger.info("爬虫info查看信息=========进入UP主个人视频数量空间");
				String datalist = page.getRawText();
	    		JSONObject json = JSONObject.parseObject(datalist).getJSONObject("data");
	    		//String str = "http://space.bilibili.com/ajax/member/getNavNum?mid=4162287&_=1496857052309";
	   		 	String mid = getDataFromUrl(page.getUrl().get(),"mid");// page.getUrl().get().substring(page.getUrl().get().indexOf("mid")+4,page.getUrl().get().lastIndexOf("&"));
	    		upStationEntity = upStationService.findByStationAndHomeId(GlobalConstant.STATION_TYPE_2, mid);
	    		if(upStationEntity!=null){
	    			upStationEntity.setUpVideoCount(json.getLong("video"));
	    			upStationEntity.setUp(upStationEntity.getUp());
	    			upStationService.updateById(upStationEntity);
	    		}
	    		
	    	}else if(page.getUrl().regex(uptags_url).match()){	//获取UP主相关标签
//	    		logger.info("爬虫info查看信息=========进入UP的tags");
	    		String datalist = page.getRawText();
	    		//System.out.println(datalist);
	    		if(!datalist.matches(".*\\[\\].*")){
	    			JSONObject json = JSONObject.parseObject(datalist);
	    			String data = json.getString("data").substring(1, json.getString("data").length()-1);
	    			if(data.trim().indexOf("数错:数错")==-1&&data.trim().indexOf("取失")==-1){
	    				json = JSONObject.parseObject(data);
	                	JSONArray jsonTagArray = json.getJSONArray("tags");
	                	List<String> taglist = jsonTagArray.toJavaList(String.class);
	                	String mid = json.getString("mid");
	                	if(taglist!=null&&taglist.size()>0){
	                		attributeEntity = new AttributeEntity();
	        				attributeEntity.setId("386e0fea98e343b8b459c406a2d66208");	//人物标签属性
	                		for(String tagStr:taglist){
	                			upStationEntity = upStationService.findByStationAndHomeId(GlobalConstant.STATION_TYPE_2, mid);
//	                			upEntity = new UpEntity();
//	                			upEntity.setId(mid);
	        					valueEntity = valueService.findByNameId(quoteReplacement(tagStr), null);
	        					if(valueEntity==null){
	        						valueEntity = new ValueEntity();
	        						valueEntity.setId(IdGen.uuid());
	        						valueEntity.setName(quoteReplacement(tagStr));
	        						valueEntity.setStatus(new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1));
	        						valueEntity.setCreateTime(StringUtils.toString((System.currentTimeMillis())));
	        						valueService.insert(valueEntity);
	        					}
	        					attributeValueEntity = attributeValueService.findByAttributeValueId(attributeEntity, valueEntity, null);
	        					if(attributeValueEntity==null){
	        						attributeValueEntity = new AttributeValueEntity();
	        						attributeValueEntity.setId(IdGen.uuid());
	        						attributeValueEntity.setAttribute(attributeEntity);
	        						attributeValueEntity.setValue(valueEntity);
	        						attributeValueEntity.setStatus(new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1));
	        						attributeValueEntity.setCreateTime(StringUtils.toString((System.currentTimeMillis())));
	        						attributeValueService.insert(attributeValueEntity);
	        					}
	        					upValueEntity = upValueService.findByUpValueId(upStationEntity.getUp(), valueEntity, null);
	        					
	        					if(upValueEntity==null){
	        						upValueEntity = new UpValueEntity();
	        						upValueEntity.setId(IdGen.uuid());
	        						upValueEntity.setValue(valueEntity);
	        						upValueEntity.setUp(upStationEntity.getUp());
	        						upValueEntity.setStatus(new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1));
	        						upValueEntity.setCreateTime(StringUtils.toString((System.currentTimeMillis())));
	        						upValueService.insert(upValueEntity);
	        					}
	                		}
	                	}
	    			}
	    		}
	    	}else if(page.getUrl().regex(tags_url).match()){
//	    		logger.info("爬虫info查看信息=========进入视频标签页面");
				String datalist = page.getRawText();
				JSONArray jsonarray = JSONObject.parseObject(datalist).getJSONArray("data");
				if(jsonarray!=null&&jsonarray.size()>0){
					List<Map> tags = JSON.parseArray(jsonarray.toJSONString(), Map.class);
					if(tags!=null&&tags.size()>0){
						attributeEntity = new AttributeEntity();
						attributeEntity.setId("1d3268c804ae42b88de004732d88b5b0");	//视频标签属性
						String aid =  getDataFromUrl(page.getUrl().get(),"aid");//page.getUrl().get().substring(page.getUrl().get().indexOf("aid")+4, page.getUrl().get().lastIndexOf("&"));
						videoStationDataEntity = videoStationService.findByVideoStationId(null, GlobalConstant.STATION_TYPE_2, null,aid);
						for(Map map:tags){
							if(videoStationDataEntity!=null){
								valueEntity = valueService.findByNameId(quoteReplacement(StringUtils.toString(map.get("tag_name"))), null);
								if(valueEntity==null){
									valueEntity = new ValueEntity();
									valueEntity.setId(IdGen.uuid());
									valueEntity.setName(quoteReplacement(StringUtils.toString(map.get("tag_name"))));
									valueEntity.setStatus(new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1));
									valueEntity.setCreateTime(StringUtils.toString((System.currentTimeMillis())));
									valueService.insert(valueEntity);
								}
								attributeValueEntity = attributeValueService.findByAttributeValueId(attributeEntity, valueEntity, null);
								if(attributeValueEntity==null){
									attributeValueEntity = new AttributeValueEntity();
									attributeValueEntity.setId(IdGen.uuid());
									attributeValueEntity.setAttribute(attributeEntity);
									attributeValueEntity.setValue(valueEntity);
									attributeValueEntity.setStatus(new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1));
									attributeValueEntity.setCreateTime(StringUtils.toString((System.currentTimeMillis())));
									attributeValueService.insert(attributeValueEntity);
								}
								videoValueEntity = videoValueService.findByVideoValueId(videoStationDataEntity.getVideo().getId(), valueEntity.getId(), null);
								
								if(videoValueEntity==null){
									videoValueEntity = new VideoValueEntity();
									videoValueEntity.setId(IdGen.uuid());
									videoValueEntity.setValue(valueEntity);
									videoValueEntity.setVideo(videoStationDataEntity.getVideo());
									videoValueEntity.setStatus(new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1));
									videoValueEntity.setCreateTime(StringUtils.toString((System.currentTimeMillis())));
									videoValueService.insert(videoValueEntity);
								}
							}
			    		}
					}
				}
	    	}
	}
	
	//创建面向用户主页POST请求（http://space.bilibili.com/ajax/member/GetInfo）的Request对象
    private static Request createUpPostRequest(String mid){
        Request request = new Request("http://space.bilibili.com/ajax/member/GetInfo?_="+new Date().getTime());
        request.setMethod(HttpConstant.Method.POST);
        try {
        	Map<String, Object> paramMap = new HashMap<String, Object>();
        	paramMap.put("mid", mid);
        	request.setExtras(paramMap);
        	request.addHeader("Host", "space.bilibili.com");
        	request.addHeader("Origin", "http://space.bilibili.com");
        	request.addHeader("Referer", "http://space.bilibili.com/"+mid);
        	request.addHeader("X-Requested-With", "XMLHttpRequest");
        	request.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			request.setRequestBody(HttpRequestBody.form(paramMap,"utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return request;
    }
    
    /**
     * tid
     * 17：单机游戏
     * 171：电子竞技
     * 172：手机游戏
     * 65：网络游戏
     * 173：桌游棋牌
     * 121：GMV
     * 136：音游
     * 19：Mugen
     */
    public void crawl(Map<String, Object> paramMap){
    	try{
    		
    		List<String> list17 = new ArrayList<String>();
    		list17.add("http://api.bilibili.com/archive_rank/getarchiverankbypartion?callback=jQuery&type=json&tid=17&pn=1");
    		Spider.create(new BilibiliVideoPageProcessor())
    		.setModeType(GlobalConstant.MODE_TYPE_ALWAYS)
    		.setInitUrls(list17)
    		//.setDownloader(new Downloader())
    		//.setScheduler(new QueueScheduler().setDuplicateRemover(new HashSetDuplicateRemover()))
    		.addUrl(list17.get(0)+"&_="+new Date().getTime())
            .thread(6)
            .start();
    		
    		List<String> list171 = new ArrayList<String>();
    		list171.add("http://api.bilibili.com/archive_rank/getarchiverankbypartion?callback=jQuery&type=json&tid=171&pn=1");
    		Spider.create(new BilibiliVideoPageProcessor())
    		.setModeType(GlobalConstant.MODE_TYPE_ALWAYS)
    		.setInitUrls(list171)
    		.addUrl(list171.get(0)+"&_="+new Date().getTime())
            .thread(6)
            .start();
    		
    		List<String> list65 = new ArrayList<String>();
    		list65.add("http://api.bilibili.com/archive_rank/getarchiverankbypartion?callback=jQuery&type=json&tid=65&pn=1");
    		Spider.create(new BilibiliVideoPageProcessor())
    		.setModeType(GlobalConstant.MODE_TYPE_ALWAYS)
    		.setInitUrls(list65)
    		.addUrl(list65.get(0)+"&_="+new Date().getTime())
            .thread(6)
            .start();
    		
    		List<String> list172 = new ArrayList<String>();
    		list172.add("http://api.bilibili.com/archive_rank/getarchiverankbypartion?callback=jQuery&type=json&tid=172&pn=1");
    		Spider.create(new BilibiliVideoPageProcessor())
    		.setModeType(GlobalConstant.MODE_TYPE_ALWAYS)
    		.setInitUrls(list172)
    		.addUrl(list172.get(0)+"&_="+new Date().getTime())
            .thread(6)
            .start();
    		
    		List<String> list173 = new ArrayList<String>();
    		list173.add("http://api.bilibili.com/archive_rank/getarchiverankbypartion?callback=jQuery&type=json&tid=173&pn=1");
    		Spider.create(new BilibiliVideoPageProcessor())
    		.setModeType(GlobalConstant.MODE_TYPE_ALWAYS)
    		.setInitUrls(list173)
    		.addUrl(list173.get(0)+"&_="+new Date().getTime())
            .thread(1)
            .start();
    		
    		List<String> list121 = new ArrayList<String>();
    		list121.add("http://api.bilibili.com/archive_rank/getarchiverankbypartion?callback=jQuery&type=json&tid=121&pn=1");
    		Spider.create(new BilibiliVideoPageProcessor())
    		.setModeType(GlobalConstant.MODE_TYPE_ALWAYS)
    		.setInitUrls(list121)
    		.addUrl(list121.get(0)+"&_="+new Date().getTime())
            .thread(1)
            .start();
    		
    		List<String> list136 = new ArrayList<String>();
    		list136.add("http://api.bilibili.com/archive_rank/getarchiverankbypartion?callback=jQuery&type=json&tid=136&pn=1");
    		Spider.create(new BilibiliVideoPageProcessor())
    		.setModeType(GlobalConstant.MODE_TYPE_ALWAYS)
    		.setInitUrls(list136)
    		.addUrl(list136.get(0)+"&_="+new Date().getTime())
            .thread(1)
            .start();
    		
    		
    		List<String> list19 = new ArrayList<String>();
    		list19.add("http://api.bilibili.com/archive_rank/getarchiverankbypartion?callback=jQuery&type=json&tid=19&pn=1");
    		Spider.create(new BilibiliVideoPageProcessor())
    		.setModeType(GlobalConstant.MODE_TYPE_ALWAYS)
    		.setInitUrls(list19)
    		.addUrl(list19.get(0)+"&_="+new Date().getTime())
            .thread(1)
            .start();
    		
    	}catch (Exception e){
    		logger.error("爬虫出错===========================爬虫出错：", e);
    	}
    }
    
	
	public static void main(String[] args) {
		long videonum = 72832;
		long total = 177616;
		// int currentNum = 3079;
		int size = 20;
		int rest = StringUtils.toInteger((total - videonum) % size);
		int pagenum = StringUtils.toInteger(rest) == 0 ? StringUtils.toInteger((total - videonum) / size) : StringUtils.toInteger((total - videonum) / size + 1);
		System.out.println("pagenum:" + pagenum);
		System.out.println("rest:" + rest);
	}
	
	
	
}
