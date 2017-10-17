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
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.webside.base.basecontroller.BaseController;
import com.webside.common.CacheConstant;
import com.webside.common.GlobalConstant;
import com.webside.shiro.ShiroAuthenticationManager;
import com.webside.user.model.UserEntity;
import com.webside.user.model.UserPhoto;
import com.webside.user.service.UserService;
import com.webside.util.CookieUtil;
import com.webside.util.DateUtils;
import com.webside.util.IdGen;
import com.webside.util.ImageUtil;
import com.webside.util.StringUtils;

/**
 * @author sunhw
 */

@Controller
@RequestMapping(value = "/avatar")
public class AvatarController extends BaseController 
{
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserService userService;
	
	/**
	 * 查询学生信息列表
	 * 
	 * @param name
	 * @return
	 * @throws NamingException
	 * @throws SQLException
	 */
	@ResponseBody
	@RequestMapping(value = "/upload")
	@SuppressWarnings("deprecation")
	public Map<String, Object> fileUpload(@RequestParam Map<String, Object> map,
							 HttpServletRequest request, HttpServletResponse response,
							 @RequestParam(value = "file", required = false) MultipartFile file) 
	{
		Map<String, Object> data = new HashMap<String, Object>();
		String uploader = null;
		String uploadDir = null;
		data.put("code", "0");
		try
		{
			String originalFilename = file.getOriginalFilename();
			if(null == file || !StringUtils.isNotEmpty(originalFilename)) return null;
			
			String six = originalFilename.lastIndexOf(".") > -1 ? originalFilename.substring(originalFilename.lastIndexOf(".") + 1, originalFilename.length()) : "png";
			six = (StringUtils.isNotEmpty(six)) ? six.trim() :  "png" ;
			String fileName = UUID.randomUUID().toString().replaceAll("-", "");
			
			byte[] bytes = file.getBytes();
			
			uploadDir = (request.getRealPath("/") + "upload").replace('\\', '/').replace("//", "/");
			uploader  = (request.getContextPath() + "/upload").replace('\\', '/').replace("//", "/");
			
			File dirPath = new File(uploadDir);
			
			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}
			
			uploadDir = uploadDir + "/avatar";
			uploader  = uploader  + "/avatar";
			
			dirPath = new File(uploadDir);
			
			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}
			
			uploadDir = uploadDir + "/" + DateUtils.getDate(DateUtils._DEFAULT3);
			uploader  = uploader  + "/" + DateUtils.getDate(DateUtils._DEFAULT3);
			dirPath = new File(uploadDir);
			
			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}
			
			uploader = uploader  + "/" + fileName + "." +six;
			fileName = uploadDir + "/" + fileName + "." +six;
			File uploadedFile = new File(fileName);
			
			//文件拷贝
			FileCopyUtils.copy(bytes, uploadedFile);
			//文件裁剪
			int resizeCut = ImageUtil.resizeCut(fileName,map);
			
			if(resizeCut==1){
				UserEntity userEntity = new UserEntity();
				userEntity.setId(ShiroAuthenticationManager.getUserId());
				userEntity.setPhoto(uploader);			
				userService.updateUserOnly(userEntity,true);
				UserPhoto photo = new UserPhoto();
				String photoId = IdGen.uuid();
				photo.setId(photoId);
				photo.setUserId(ShiroAuthenticationManager.getUserId());
				photo.setCreateTime(System.currentTimeMillis());
				photo.setStatus(1);
				photo.setIsCurrent(1);
				photo.setPhoto(uploader);
				userService.insertUserPhoto(photo);
				// 设置进cookie
				CookieUtil.addCookie(response, GlobalConstant.COOKIE_USER_PHOTO, URLEncoder.encode(userEntity.getPhoto(), "utf-8").replaceAll("%2F", "/"), CacheConstant.SEVEN_DAY);
				data.put("code", "1");
				data.put("src", userEntity.getPhoto_133());
				data.put("id", photoId);
			}else{
				if(resizeCut==2){
					data.put("msg", "图片非法裁剪");
				}else{
					data.put("msg", "错误图片");
				}
			}
		}
		catch (MaxUploadSizeExceededException e) 
		{
			logger.error(e.getMessage(), e);
			data.put("msg", "文件大小超出限制...");
		} 
		catch (Exception e) 
		{
			logger.error(e.getMessage(), e);
			data.put("msg", "文件上传异常...");
		}
		return data;
	}
}