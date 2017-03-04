package com.mattress.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mattress.dao.IManagerDao;
import com.mattress.model.UserCustomerInfo;
import com.mattress.model.UserManagerInfo;

@Repository
public class ManagerDaoImpl extends BaseDaoImpl<UserManagerInfo, Serializable> implements IManagerDao {

	private List<UserManagerInfo> managerList = new ArrayList<UserManagerInfo>();

	@Override
	public List<UserManagerInfo> checkName(UserManagerInfo manager) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("vcName", manager.getVcName());
		String hql = "FROM UserManagerInfo manager WHERE manager.vcName = :vcName";
		managerList = find(hql, params);
		return managerList;
	}

	@Override
	public List<UserManagerInfo> checkPassword(UserManagerInfo manager) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("vcName", manager.getVcName());
		params.put("vcPassword", manager.getVcPassword());
		String hql = "FROM UserManagerInfo manager WHERE manager.vcName = :vcName and manager.vcPassword = :vcPassword";
		managerList = find(hql, params);
		return managerList;
	}

}
