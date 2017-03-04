package com.mattress.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.mattress.dao.IUserDao;
import com.mattress.model.UserCustomerInfo;

@Repository
public class UserDaoImpl extends BaseDaoImpl<UserCustomerInfo, Serializable> implements IUserDao{

}
