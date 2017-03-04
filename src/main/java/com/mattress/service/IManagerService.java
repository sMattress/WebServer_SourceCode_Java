package com.mattress.service;

import java.io.Serializable;
import java.util.List;

import com.mattress.model.UserManagerInfo;

public interface IManagerService extends IBaseService<UserManagerInfo, Serializable>{
	/**
	 * 查询账号是否存在
	 * @param manager
	 * @return
	 */
	List<UserManagerInfo> checkName(UserManagerInfo manager);
	
	/**
	 * 查询密码是否正确
	 * @param manager
	 * @return
	 */
	List<UserManagerInfo> checkPassword(UserManagerInfo manager);
	
}
