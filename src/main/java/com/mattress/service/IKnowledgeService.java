package com.mattress.service;

import java.io.Serializable;
import java.util.List;

import com.mattress.model.KnowledgeInfo;
import com.mattress.model.KnowledgeLabelView;
import com.mattress.model.Label;
import com.mattress.model.Page;
import com.mattress.model.UserCustomerView;

public interface IKnowledgeService extends IBaseService<KnowledgeInfo, Serializable>{

	List<Label> queryLabelByType(int labelType);
	Page<KnowledgeLabelView> queryForPage(int currentPage, int pageSize, int labelId);
	List<KnowledgeInfo> queryKnowledge(int knowledgeId);
	
}
