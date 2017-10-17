package com.webside.sms.mapper;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.sms.model.SmsLog;

@Repository
public interface SmsLogMapper extends BaseMapper<SmsLog, String>{
	
}
