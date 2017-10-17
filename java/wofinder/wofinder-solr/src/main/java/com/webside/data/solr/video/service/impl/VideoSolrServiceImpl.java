/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Woda Corporation - WODOTA Team
 *******************************************************************************/
package com.webside.data.solr.video.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.stereotype.Service;

import com.webside.data.solr.common.Page;
import com.webside.data.solr.common.SolrUtil;
import com.webside.data.solr.video.model.Video;
import com.webside.data.solr.video.searchable.VideoSearchable;
import com.webside.data.solr.video.service.VideoSolrSerivce;
import com.webside.util.StringUtils;

/**
 * @author zengxn
 */
@Service("videoSolrSerivce")
public class VideoSolrServiceImpl implements VideoSolrSerivce {

	private static final Logger logger = Logger.getLogger(VideoSolrServiceImpl.class);

	@Resource(name = "videoSolrTemplate")
	private SolrOperations solrTemplate;

	public VideoSolrServiceImpl() {
		super();
	}

	public VideoSolrServiceImpl(SolrOperations solrTemplate) {
		super();
		this.solrTemplate = solrTemplate;
	}

	/**
	 * 线程池定义
	 */
	private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

	/**
	 * 更新solr中的video信息
	 * 
	 * @author zengxn
	 */
	class UpdateSolrVideoRunner implements Runnable {
		private Video entity;

		public UpdateSolrVideoRunner(Video entity) {
			this.entity = entity;
		}

		@Override
		public void run() {
			insertOrUpdateToThread(entity);
		}
	}

	/**
	 * 删除solr中的video信息
	 * 
	 * @author zengxn
	 */
	class deleteSolrVideoRunner implements Runnable {
		private String id;

		public deleteSolrVideoRunner(String id) {
			this.id = id;
		}

		@Override
		public void run() {
			deleteByIdToThread(id);
		}
	}

	/**
	 * 添加或修改视频
	 * 
	 * @author zengxn
	 */
	@Override
	public void insertOrUpdate(String id, String title) {
		if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(title)) {
			cachedThreadPool.execute(new UpdateSolrVideoRunner(new Video(id, title)));
		}
	}

	/**
	 * 添加或修改视频实现方法
	 * 
	 * @author zengxn
	 */
	private void insertOrUpdateToThread(Video video) {
		try {
			UpdateResponse saveBean = solrTemplate.saveBean(video);
			if (SolrUtil.DEBUG) {
				System.out.println("insertOrUpdate-saveBean:" + saveBean);
			}
			if (saveBean.getStatus() == 0) {
				UpdateResponse commit = solrTemplate.getSolrClient().commit(false, false, true);
				if (SolrUtil.DEBUG) {
					System.out.println("insertOrUpdate-commit:" + commit);
				}
			}
		} catch (Exception e) {
			logger.error("solr:添加或修改视频出错：", e);
		}
	}

	/**
	 * 根据ID删除视频
	 * 
	 * @author zengxn
	 */
	@Override
	public void deleteById(String id) {
		if (StringUtils.isNotBlank(id)) {
			cachedThreadPool.execute(new deleteSolrVideoRunner(id));
		}
	}

	/**
	 * 根据ID删除视频实现方法
	 * 
	 * @author zengxn
	 */
	private void deleteByIdToThread(String id) {
		try {
			UpdateResponse deleteById = solrTemplate.deleteById(id);
			if (SolrUtil.DEBUG) {
				System.out.println("insertOrUpdate-deleteById:" + deleteById);
			}
			if (deleteById.getStatus() == 0) {
				UpdateResponse commit = solrTemplate.getSolrClient().commit(false, false, true);
				if (SolrUtil.DEBUG) {
					System.out.println("insertOrUpdate-commit:" + commit);
				}
			}
		} catch (Exception e) {
			logger.error("solr:根据ID删除视频出错：", e);
		}
	}

	/**
	 * 根据条件分页查询视频
	 * 
	 * @author zengxn
	 */
	@Override
	public void queryListByPage(Page<Video> page) {
		try {
			if (page.getConditions() != null)
				if (page.getConditions().containsKey("search")) {
					SolrQuery query = new SolrQuery();
					String search = ClientUtils.escapeQueryChars(StringUtils.toString(page.getConditions().get("search")));
					// 设置查询条件
					query.setQuery(VideoSearchable.TITLE_FIELD + ":(" + search + ")");

					// 设置分页参数
					query.setStart((int) page.getStart());
					query.setRows(page.getSize());

					// 设置高亮
					query.setHighlight(true);// 开启高亮组件
					query.addHighlightField(VideoSearchable.TITLE_FIELD);// 高亮字段
					// 标记，高亮关键字
					query.setHighlightSimplePre(SolrUtil.HIGHLIGHT_SIMPLE_PRE);// 前缀
					query.setHighlightSimplePost(SolrUtil.HIGHLIGHT_SIMPLE_POST);// 后缀
					// 获取高亮分片数，默认为1
					query.setHighlightSnippets(1);
					// 每个分片的最大长度，默认为100
					query.setHighlightFragsize(150);
					// 调试输出执行语句
					if (SolrUtil.DEBUG) {
						System.out.println("/select?" + query);
					}
					QueryResponse response = solrTemplate.getSolrClient().query(query);
					SolrDocumentList docs = response.getResults();

					// 获取所有高亮的字段
					Map<String, Map<String, List<String>>> highlightMap = response.getHighlighting();
					// 调试输出高亮信息
					if (SolrUtil.DEBUG) {
						System.out.println(response.getHighlighting());
					}

					// 得到实体对象
					List<Video> tmpLists = response.getBeans(Video.class);
					// 遍历实体集合，替换高亮字段
					for (Video video : tmpLists) {
						if (highlightMap.containsKey(video.getId())) {
							List<String> titleList = highlightMap.get(video.getId()).get(VideoSearchable.TITLE_FIELD);
							// 获取并设置高亮的字段title
							if (titleList != null && titleList.size() > 0) {
								video.setTitle(titleList.get(0));
							}
						}
					}

					page.setDatas(tmpLists);
					page.setCount(docs.getNumFound());
				}
		} catch (Exception e) {
			logger.error("solr:根据条件查询视频出错：", e);
		}
	}

}
