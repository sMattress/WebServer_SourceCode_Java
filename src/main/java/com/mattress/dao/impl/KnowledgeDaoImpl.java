package com.mattress.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mattress.dao.IKnowledgeDao;
import com.mattress.model.KnowledgeInfo;
import com.mattress.model.Label;
import com.mattress.model.UserCustomerInfo;

@Repository
public class KnowledgeDaoImpl extends BaseDaoImpl<KnowledgeInfo, Serializable> implements IKnowledgeDao {

	@Override
	public List<KnowledgeInfo> queryKnowledges(int labelId) {
		String hql = "FROM KnowledgeInfo knowledge where knowledge.vcLabelId like '%" + labelId + "%'";
		if (labelId == 0) {
			hql = "FROM KnowledgeInfo knowledge";
		}
		return queryList(hql);
	}

	@Override
	public List<KnowledgeInfo> queryByPage(int offset, int length, int labelId) {
		String hql = "FROM KnowledgeInfo knowledge WHERE knowledge.vcLabelId like '%" + labelId + "%' order by knowledge.IId DESC";
		if (labelId == 0) {
			hql = "FROM KnowledgeInfo knowledge order by knowledge.IId DESC";
		}
		return queryForPage(hql, offset, length);
	}

	@Override
	public List<Label> queryLabelByType(int labelType) {
		String hql = "FROM Label label WHERE label.tiType=" + labelType;
		List list = queryList(hql);
		return list;
	}

	@Override
	public List<Label> queryLabelById(int labelId) {
		String hql = "FROM Label label WHERE label.IId=" + labelId;
		List list = queryList(hql);
		return list;
	}

	@Override
	public List<KnowledgeInfo> queryKnowledge(int knowledgeId) {
		String hql = "FROM KnowledgeInfo knowledge where knowledge.IId="+knowledgeId;
		return queryList(hql);
	}

}
