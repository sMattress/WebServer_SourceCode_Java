package com.mattress.dao;

import java.io.Serializable;
import java.util.List;

import com.mattress.model.UserCustomerInfo;
import com.mattress.model.UserManagerInfo;

public interface IManagerDao extends IBaseDao<UserManagerInfo, Serializable> {
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
