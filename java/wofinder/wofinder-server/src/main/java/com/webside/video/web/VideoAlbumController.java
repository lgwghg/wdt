/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.video.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webside.base.basecontroller.BaseController;
import com.webside.sys.model.ValueEntity;
import com.webside.sys.service.ValueService;
import com.webside.sys.vo.ValueVo;
import com.webside.video.model.VideoAlbumEntity;
import com.webside.video.model.VideoAlbumValueEntity;
import com.webside.video.service.VideoAlbumService;
import com.webside.video.service.VideoAlbumValueService;

/**
 * 视频评论控制管理层
 * 
 * @author zengxn
 * @date 2017-04-20 21:13:58
 */

@Controller
@RequestMapping("/videoAlbum/")
public class VideoAlbumController extends BaseController {
	/**
	 * 视频合集 Service定义
	 */
	@Autowired
	private VideoAlbumService videoAlbumService;

	/**
	 * 视频合集标签 Service定义
	 */
	@Autowired
	private VideoAlbumValueService videoAlbumValueService;

	/**
	 * 属性值 Service定义
	 */
	@Autowired
	private ValueService valueService;

	/**
	 * 获取视频合辑及相关列表
	 * 
	 * @param videoAlbumId
	 * @return
	 */
	@RequestMapping(value = "getVideoAlbumList", method = RequestMethod.POST)
	@ResponseBody
	public Object getVideoAlbumList(String albumId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<VideoAlbumEntity> list = videoAlbumService.queryListByIdForAll(albumId);
			map.put("data", list);
			map.put("success", Boolean.TRUE);
		} catch (Exception e) {
			map.put("success", Boolean.FALSE);
			map.put("message", "获取视频合辑及相关列表失败：" + e.getMessage());
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 查询视频合集标签列表
	 * 
	 * @param albumId
	 * @return
	 */
	@RequestMapping(value = "getValueList", method = RequestMethod.POST)
	@ResponseBody
	public Object queryAlbumValue(String albumId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<VideoAlbumValueEntity> list = videoAlbumValueService.queryListByVideoAlbumId(albumId);
			if (!CollectionUtils.isEmpty(list)) {
				List<ValueVo> valueVos = new ArrayList<ValueVo>(list.size());
				ValueEntity valueEntity = null;
				for (VideoAlbumValueEntity albumValue : list) {
					valueEntity = valueService.findById(albumValue.getValue().getId());
					if (valueEntity != null) {
						valueVos.add(new ValueVo(valueEntity.getId(), valueEntity.getName()));
					}
				}
				map.put("data", valueVos);
			}
			map.put("success", Boolean.TRUE);
		} catch (Exception e) {
			map.put("success", Boolean.FALSE);
			map.put("message", "查询视频合集标签列表失败：" + e.getMessage());
			e.printStackTrace();
		}
		return map;
	}
}
