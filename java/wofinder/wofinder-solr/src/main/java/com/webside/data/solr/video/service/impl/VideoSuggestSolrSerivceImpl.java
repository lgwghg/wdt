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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.stereotype.Service;

import com.webside.data.solr.common.Page;
import com.webside.data.solr.common.SolrUtil;
import com.webside.data.solr.video.model.VideoSuggest;
import com.webside.data.solr.video.searchable.VideoSuggestSearchable;
import com.webside.data.solr.video.service.VideoSuggestSolrSerivce;
import com.webside.util.StringUtils;

@Service("videoSuggestSolrSerivce")
public class VideoSuggestSolrSerivceImpl implements VideoSuggestSolrSerivce {
	private static final Logger logger = Logger.getLogger(VideoSuggestSolrSerivceImpl.class);

	@Resource(name = "videoSuggestSolrTemplate")
	private SolrOperations solrTemplate;

	public VideoSuggestSolrSerivceImpl() {
		super();
	}

	public VideoSuggestSolrSerivceImpl(SolrOperations solrTemplate) {
		super();
		this.solrTemplate = solrTemplate;
	}

	/**
	 * 线程池定义
	 */
	private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

	/**
	 * 更新solr中的videoSuggest信息
	 * 
	 * @author zengxn
	 */
	class UpdateSolrVideoSuggestRunner implements Runnable {
		private VideoSuggest entity;

		public UpdateSolrVideoSuggestRunner(VideoSuggest entity) {
			this.entity = entity;
		}

		@Override
		public void run() {
			insertOrUpdateToThread(entity);
		}
	}

	/**
	 * 删除solr中的videoSuggest信息
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

	@Override
	public void insertOrUpdate(String id, String title) {
		if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(title)) {
			cachedThreadPool.execute(new UpdateSolrVideoSuggestRunner(new VideoSuggest(id, title)));
		}
	}

	/**
	 * 添加或修改视频suggest实现方法
	 * 
	 * @author zengxn
	 */
	private void insertOrUpdateToThread(VideoSuggest videoSuggest) {
		try {
			UpdateResponse saveBean = solrTemplate.saveBean(videoSuggest);
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
			logger.error("solr:添加或修改视频videoSuggest出错：", e);
		}
	}

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
			logger.error("solr:根据ID删除视频suggest出错：", e);
		}
	}

	/**
	 * 根据条件分页查询视频suggest
	 * 
	 * @author zengxn
	 */
	@Override
	public void queryListByPage(Page<VideoSuggest> page) {
		try {
			if (page.getConditions() != null)
				if (page.getConditions().containsKey("search")) {
					SolrQuery query = new SolrQuery();
					String search = StringUtils.toString(page.getConditions().get("search"));
					// 设置查询条件
					query.setQuery(VideoSuggestSearchable.SUGGEST_FIELD + ":(" + search + "*)");

					// 设置分页参数
					query.setStart((int) page.getStart());
					query.setRows(page.getSize());

					// 设置空格分割用
					query.set("q.op", "AND");
					// 调试输出执行语句
					if (SolrUtil.DEBUG) {
						System.out.println("/select?" + query);
					}

					QueryResponse response = solrTemplate.getSolrClient().query(query);
					SolrDocumentList docs = response.getResults();

					// 得到实体对象
					List<VideoSuggest> tmpLists = response.getBeans(VideoSuggest.class);

					page.setDatas(tmpLists);
					page.setCount(docs.getNumFound());
				}
		} catch (Exception e) {
			logger.error("solr:根据条件查询视频suggest出错：", e);
		}
	}
}
