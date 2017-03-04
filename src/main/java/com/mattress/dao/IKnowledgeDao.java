package com.mattress.dao;

import java.io.Serializable;
import java.util.List;

import com.mattress.model.KnowledgeInfo;
import com.mattress.model.Label;
import com.mattress.model.UserCustomerInfo;

public interface IKnowledgeDao extends IBaseDao<KnowledgeInfo, Serializable>{
	List<KnowledgeInfo> queryKnowledges(int labelId);
	List<Label> queryLabelByType(int labelType);
	List<Label> queryLabelById(int labelId);
	List<KnowledgeInfo>	queryByPage(int offset, int length,int labelId);
	List<KnowledgeInfo> queryKnowledge(int knowledgeId);
}
