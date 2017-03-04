package com.mattress.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mattress.dao.IManagerDao;
import com.mattress.model.UserManagerInfo;
import com.mattress.service.IManagerService;

@Service
public class ManagerServiceImpl extends BaseServiceImpl<UserManagerInfo, Serializable> implements IManagerService{
	@Autowired
	@Qualifier("managerDaoImpl")
	private IManagerDao managerDao;

	@Override
	public List<UserManagerInfo> checkName(UserManagerInfo manager) {
		return managerDao.checkName(manager);
	}
	
	@Override
	public List<UserManagerInfo> checkPassword(UserManagerInfo manager) {
		return managerDao.checkPassword(manager);
	}

}
