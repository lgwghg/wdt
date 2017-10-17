package com.webside.crawler.pageprocessor;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.MapUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import com.webside.common.GlobalConstant;
import com.webside.crawler.util.UserAgentUtil;
import com.webside.dict.model.DictEntity;
import com.webside.dict.service.DictService;
import com.webside.sys.model.AttributeEntity;
import com.webside.sys.model.AttributeValueEntity;
import com.webside.sys.model.ValueEntity;
import com.webside.sys.service.AttributeService;
import com.webside.sys.service.AttributeValueService;
import com.webside.sys.service.GameService;
import com.webside.sys.service.ValueService;
import com.webside.up.model.UpEntity;
import com.webside.up.service.UpService;
import com.webside.up.service.UpStationService;
import com.webside.up.service.UpValueService;
import com.webside.util.SpringBeanUtil;
import com.webside.util.StringUtils;
import com.webside.video.model.VideoEntity;
import com.webside.video.model.VideoStationEntity;
import com.webside.video.model.VideoValueEntity;
import com.webside.video.service.VideoAlbumService;
import com.webside.video.service.VideoAlbumValueService;
import com.webside.video.service.VideoCommentService;
import com.webside.video.service.VideoService;
import com.webside.video.service.VideoStationService;
import com.webside.video.service.VideoValueService;

public abstract class VideoPageProcessor implements PageProcessor {
	protected VideoService videoService = null;
	protected VideoStationService videoStationService = null;
	protected VideoValueService videoValueService = null;
	protected UpService upService = null;
	protected UpStationService upStationService = null;
	protected UpValueService upValueService = null;
	protected AttributeService attributeService = null;
	protected AttributeValueService attributeValueService = null;
	protected ValueService valueService = null;
	protected VideoCommentService videoCommentService = null;
	protected GameService gameService = null;
	protected DictService dictService = null;
	protected VideoAlbumService videoAlbumSerivce = null;
	protected VideoAlbumValueService videoAlbumValueSerivce = null;

	// 视频标签，在数据库对应的key - 游戏视频
	private AttributeEntity attributeEntity = new AttributeEntity("1d3268c804ae42b88de004732d88b5b0");
	private DictEntity status = new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_1);

	protected VideoService getVideoService() {
		if (videoService == null) {
			videoService = (VideoService) SpringBeanUtil.getBean(VideoService.BEANNAME);
		}
		return videoService;
	}

	protected VideoStationService getVideoStationService() {
		if (videoStationService == null) {
			videoStationService = (VideoStationService) SpringBeanUtil.getBean(VideoStationService.BEANNAME);
		}
		return videoStationService;
	}

	protected VideoValueService getVideoValueService() {
		if (videoValueService == null) {
			videoValueService = (VideoValueService) SpringBeanUtil.getBean(VideoValueService.BEANNAME);
		}
		return videoValueService;
	}

	protected UpService getUpService() {
		if (upService == null) {
			upService = (UpService) SpringBeanUtil.getBean(UpService.BEANNAME);
		}
		return upService;
	}

	protected UpStationService getUpStationService() {
		if (upStationService == null) {
			upStationService = (UpStationService) SpringBeanUtil.getBean(UpStationService.BEANNAME);
		}
		return upStationService;
	}

	protected UpValueService getUpValueService() {
		if (upValueService == null) {
			upValueService = (UpValueService) SpringBeanUtil.getBean(UpValueService.BEANNAME);
		}
		return upValueService;
	}

	protected AttributeService getAttributeService() {
		if (attributeService == null) {
			attributeService = (AttributeService) SpringBeanUtil.getBean(AttributeService.BEANNAME);
		}
		return attributeService;
	}

	protected AttributeValueService getAttributeValueService() {
		if (attributeValueService == null) {
			attributeValueService = (AttributeValueService) SpringBeanUtil.getBean(AttributeValueService.BEANNAME);
		}
		return attributeValueService;
	}

	protected ValueService getValueService() {
		if (valueService == null) {
			valueService = (ValueService) SpringBeanUtil.getBean(ValueService.BEANNAME);
		}
		return valueService;
	}

	protected VideoCommentService getVideoCommentService() {
		if (videoCommentService == null) {
			videoCommentService = (VideoCommentService) SpringBeanUtil.getBean(VideoCommentService.BEANNAME);
		}
		return videoCommentService;
	}

	protected GameService getGameService() {
		if (gameService == null) {
			gameService = (GameService) SpringBeanUtil.getBean(GameService.BEANNAME);
		}
		return gameService;
	}

	protected DictService getDictService() {
		if (dictService == null) {
			dictService = (DictService) SpringBeanUtil.getBean(DictService.BEANNAME);
		}
		return dictService;
	}

	protected VideoAlbumService getVideoAlbumSerivce() {
		if (videoAlbumSerivce == null) {
			videoAlbumSerivce = (VideoAlbumService) SpringBeanUtil.getBean(VideoAlbumService.BEANNAME);
		}
		return videoAlbumSerivce;
	}

	protected VideoAlbumValueService getVideoAlbumValueSerivce() {
		if (videoAlbumValueSerivce == null) {
			videoAlbumValueSerivce = (VideoAlbumValueService) SpringBeanUtil.getBean(VideoAlbumValueService.BEANNAME);
		}
		return videoAlbumValueSerivce;
	}

	// 构建Site对象，指定请求头键值字段
	private Site site = Site.me().setRetryTimes(3).setTimeOut(30000).setSleepTime(1800)
			// 接口有IP接入限制，估计是60s内上限150次
			.setCycleRetryTimes(3).setUseGzip(true).addHeader("User-Agent", UserAgentUtil.randomUserAgent()).addHeader("Accept", "application/json, text/plain, */*").addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
			.addHeader("Accept-Encoding", "gzip, deflate, sdch").addHeader("Connection", "keep-alive");

	public Site getSite() {

		return site;
	}

	public void process(Page page) {
		videoService = getVideoService();
		videoStationService = getVideoStationService();
		upStationService = getUpStationService();
		valueService = getValueService();
		attributeValueService = getAttributeValueService();
		videoValueService = getVideoValueService();
		upValueService = getUpValueService();
		videoCommentService = getVideoCommentService();
		upService = getUpService();
		gameService = getGameService();
		dictService = getDictService();
		videoAlbumSerivce = getVideoAlbumSerivce();
		videoAlbumValueSerivce = getVideoAlbumValueSerivce();

		processData(page);
	};

	/**
	 * 爬虫执行代码
	 * 
	 * @param page
	 */
	public abstract void processData(Page page);

	/**
	 * 爬虫启动代码
	 * 
	 * @param paramMap
	 */
	public abstract void crawl(Map<String, Object> paramMap);

	/**
	 * 对反斜杠和$做处理
	 * 
	 * @param s
	 * @return
	 */
	public static String quoteReplacement(String s) {
		s = s.replaceAll("[\\x{10000}-\\x{10FFFF}]", "*");
		if ((s.indexOf('\\') == -1) && (s.indexOf('$') == -1))
			return s;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '\\') {
				sb.append('\\');
				sb.append('\\');
			} else if (c == '$') {
				sb.append('\\');
				sb.append('$');
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 获取URL中的参数值
	 * 
	 * @param url
	 * @param dataName
	 * @return
	 */
	public static String getDataFromUrl(String url, String dataName) {
		Pattern p = Pattern.compile(dataName + "=[0-9]\\d*&");
		Matcher m = p.matcher(url);
		m.find();
		return url.substring(m.start() + dataName.length() + 1, m.end() - 1);
	}

	protected VideoEntity saveOrUpdateVideo(Map<String, String> map, VideoEntity entity) {

		return null;
	}

	protected VideoStationEntity saveVideoStation(Map<String, String> map, VideoStationEntity videoEntity) {

		return null;
	}
	
	protected void setUpdateCount() {
		
	}
	
	protected VideoEntity saveVideo(Map<String, String> map, UpEntity upEntity, String stationValue) {
		// 站点
		String vid = MapUtils.getString(map, "vid");
		String title = quoteReplacement(MapUtils.getString(map, "title"));
		VideoEntity videoDataEntity = videoService.findByTitle(title);
		VideoStationEntity videoStationSameDataEntity = null;
		if(videoDataEntity != null && StringUtils.isNotBlank(videoDataEntity.getId())) {
			videoStationSameDataEntity = videoStationService.findByVideoStationId(videoDataEntity.getId(), stationValue, null, null);
		}
		VideoStationEntity videoStationDataEntity = videoStationService.findByVideoStationId(null, stationValue, null, vid);
		if (videoStationDataEntity == null || StringUtils.isBlank(videoStationDataEntity.getId())) {
			// 视频为空
			if (videoDataEntity == null || StringUtils.isBlank(videoDataEntity.getId())) {
				videoDataEntity = saveOrUpdateVideo(map, null);
			} else {
				// 视频归属同一个站点，则新增另一个视频
				if(videoStationSameDataEntity != null && StringUtils.isNotBlank(videoStationSameDataEntity.getId())) {
					videoDataEntity = saveOrUpdateVideo(map, null);
				} else {
					Double duration = MapUtils.getDouble(map, "duration");
					Double num = Math.abs(videoDataEntity.getDuration() - duration);
					if (num > 3) {// 视频时长误差超过3秒
						videoDataEntity = saveOrUpdateVideo(map, null);
					}
				}
			}
			videoStationDataEntity = new VideoStationEntity();
			videoStationDataEntity.setVideo(videoDataEntity);
			videoStationDataEntity.setUp(upEntity);
			saveVideoStation(map, videoStationDataEntity);
		} else {
			setUpdateCount();
			// 站点视频 和 视频ID 不一致，更新视频
			if (videoDataEntity != null && StringUtils.isNotBlank(videoDataEntity.getId()) && videoStationDataEntity.getVideo().getId() != videoDataEntity.getId()) {
				// 视频在该站点为空，说明是其他站点视频，进行合并
				if (videoStationSameDataEntity == null || StringUtils.isBlank(videoStationSameDataEntity.getId())) {
					Double num = Math.abs(videoDataEntity.getDuration() - videoStationDataEntity.getDuration());
					if (num <= 3) {// 视频时长误差不超过3秒
						VideoEntity oldVideo = videoStationDataEntity.getVideo();
						oldVideo.setStatus(new DictEntity(GlobalConstant.DICTTYPE_EFFECTIVE_STATUS_0));
						
						videoService.updateById(oldVideo);
						videoCommentService.updateVideoIdByVid(videoDataEntity.getId(), oldVideo.getId()); // 将视频评论移动到原视频下
						videoValueService.updateVideoIdByTargetForDif(videoDataEntity.getId(), oldVideo.getId());// 将标签移到原视频下
						
						videoStationDataEntity.setVideo(videoDataEntity);
					}
				}
				
			}
			 
			// 更新视频
			videoDataEntity = saveOrUpdateVideo(map, videoStationDataEntity.getVideo());
			// 更新站点
			saveVideoStation(map, videoStationDataEntity);
		}

		return videoDataEntity;
	}

	// 保存视频标签
	protected void saveVideoTags(List<String> taglist, VideoEntity videoEntity) {
		if (taglist != null && !taglist.isEmpty()) {
			for (String tagName : taglist) {
				ValueEntity valueEntity = saveSysValue(tagName);

				VideoValueEntity videoValueEntity = videoValueService.findByVideoValueId(videoEntity.getId(), valueEntity.getId(), null);
				if (videoValueEntity == null) {
					videoValueEntity = new VideoValueEntity();
					videoValueEntity.setValue(valueEntity);
					videoValueEntity.setVideo(videoEntity);
					videoValueEntity.setStatus(status);
					videoValueEntity.setCreateTime(StringUtils.toString((System.currentTimeMillis())));
					videoValueService.insert(videoValueEntity);
				}
			}
		}
	}

	protected ValueEntity saveSysValue(String tagName) {
		ValueEntity valueEntity = valueService.findByNameId(tagName, null);
		if (valueEntity == null) {
			valueEntity = new ValueEntity();
			valueEntity.setName(tagName);
			valueEntity.setStatus(status);
			valueEntity.setCreateTime(StringUtils.toString((System.currentTimeMillis())));
			valueService.insert(valueEntity);
		}

		AttributeValueEntity attributeValueEntity = attributeValueService.findByAttributeValueId(attributeEntity, valueEntity, null);
		if (attributeValueEntity == null) {
			attributeValueEntity = new AttributeValueEntity();
			attributeValueEntity.setAttribute(attributeEntity);
			attributeValueEntity.setValue(valueEntity);
			attributeValueEntity.setStatus(status);
			attributeValueEntity.setCreateTime(StringUtils.toString((System.currentTimeMillis())));
			attributeValueService.insert(attributeValueEntity);
		}

		return valueEntity;
	}

}
