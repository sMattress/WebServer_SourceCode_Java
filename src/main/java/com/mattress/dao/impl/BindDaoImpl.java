package com.mattress.dao.impl;

import com.mattress.dao.IBindDao;
import com.mattress.model.UserBindDevice;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public class BindDaoImpl extends BaseDaoImpl<UserBindDevice, Serializable> implements IBindDao {
}
