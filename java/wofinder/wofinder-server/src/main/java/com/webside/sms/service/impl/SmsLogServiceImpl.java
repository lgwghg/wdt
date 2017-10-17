package com.webside.sms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webside.base.baseservice.impl.AbstractService;
import com.webside.sms.mapper.SmsLogMapper;
import com.webside.sms.model.SmsLog;
import com.webside.sms.service.SmsLogService;
@Service("smsLogService")
public class SmsLogServiceImpl extends AbstractService<SmsLog, String> implements SmsLogService {
	@Autowired
	private SmsLogMapper smsLogMapper;
	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(smsLogMapper);
	}
	@Override
	public int insert(SmsLog smsLog) {
		return smsLogMapper.insert(smsLog);
	}

}
