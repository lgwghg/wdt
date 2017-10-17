/*******************************************************************************
 * All rights reserved. 
 * 
 * @author tianguifang
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.up.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.up.model.UpPhoto;

/**
 * 人物图册数据访问接口
 *
 * @author tianguifang
 * @date 2017-06-06 11:42:41
 */
@Repository
public interface IUpPhotoMapper extends BaseMapper<UpPhoto, String> 
{
	List<UpPhoto> queryPhotoByUpIdAndSecondUpId(Map<String, Object> param);
}
