/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper.common;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.sys.entity.common.ComSmsLog;

/**
 * 验证码MAPPER接口
 * @author laiyanxin
 * @version 2018-02-26
 */
@MyBatisMapper
public interface ComSmsLogMapper extends BaseMapper<ComSmsLog> {

    //查找手机验证玛
	ComSmsLog findCodeByMobile(ComSmsLog comSmsLog);
    //查找邮箱验证玛
	ComSmsLog findCodeByEmail(ComSmsLog comSmsLog);

	ComSmsLog checkEmail(ComSmsLog comSmsLog);
	
}