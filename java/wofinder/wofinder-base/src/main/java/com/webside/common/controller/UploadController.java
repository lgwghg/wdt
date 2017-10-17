/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.webside.common.controller;

import java.io.File;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.webside.base.basecontroller.BaseController;
import com.webside.util.DateUtils;

/**
 * @author sunhw
 */

@Controller
@RequestMapping(value = "/upload")
public class UploadController extends BaseController 
{
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 查询学生信息列表
	 * 
	 * @param name
	 * @return
	 * @throws NamingException
	 * @throws SQLException
	 */
	@ResponseBody
	@RequestMapping(value = "/fileUpload")
	@SuppressWarnings("deprecation")
	public String fileUpload(@RequestParam Map<String, Object> map,
							 HttpServletRequest request, HttpServletResponse response,
							 @RequestParam(value = "file", required = false) MultipartFile file) 
	{
		String uploader = null;
		String uploadDir = null;
		
		try
		{
			if(null == file || null == file.getOriginalFilename()) return null;
			
			String type = request.getParameter("type");//判断是图片还是视频
			String sep = "/";
			String six = file.getOriginalFilename().indexOf(".") > -1 ? file.getOriginalFilename().trim().split("\\.")[1] : "png";
			six = (null == six || "".equals(six.trim())) ? "png" : six.trim();
			String fileName = UUID.randomUUID().toString().replaceAll("-", "");
			
			byte[] bytes = file.getBytes();
			uploadDir = request.getRealPath("/") + "upload".replace('\\', '/').replace("//", "/");
			uploader = request.getContextPath() + "/upload".replace('\\', '/').replace("//", "/");
			
			
			File dirPath = new File(uploadDir);
			
			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}
			
			uploadDir = uploadDir + sep + ((type == null || "".equals(type.trim()) || "0".equals(type.trim())) ? "photo" : "videos");
			uploader  = uploader  + sep + ((type == null || "".equals(type.trim()) || "0".equals(type.trim())) ? "photo" : "videos");
			
			dirPath = new File(uploadDir);
			
			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}
			
			uploadDir = uploadDir + sep + DateUtils.getDate(DateUtils._DEFAULT3);
			uploader  = uploader  + sep + DateUtils.getDate(DateUtils._DEFAULT3);
			dirPath = new File(uploadDir);
			
			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}
			
			uploader = uploader  + sep + fileName + "." +six;
			fileName = uploadDir + sep + fileName + "." +six;
			File uploadedFile = new File(fileName);
			
			FileCopyUtils.copy(bytes, uploadedFile);
		}
		catch (MaxUploadSizeExceededException e) 
		{
			logger.error(e.getMessage(), e);
			return "文件大小超出限制...";
		} 
		catch (Exception e) 
		{
			logger.error(e.getMessage(), e);
			return "文件上传异常...";
		}
		
		return uploader;
	}
}