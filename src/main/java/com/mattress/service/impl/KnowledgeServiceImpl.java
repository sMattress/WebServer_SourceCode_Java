package com.mattress.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mattress.common.ComputeTool;
import com.mattress.dao.IKnowledgeDao;
import com.mattress.model.KnowledgeInfo;
import com.mattress.model.KnowledgeLabelView;
import com.mattress.model.Label;
import com.mattress.model.Page;
import com.mattress.service.IKnowledgeService;

@Service
public class KnowledgeServiceImpl extends BaseServiceImpl<KnowledgeInfo, Serializable> implements IKnowledgeService {
	@Autowired
	@Qualifier("knowledgeDaoImpl")
	private IKnowledgeDao knowledgeDao;

	@Override
	public List<Label> queryLabelByType(int labelType) {
		return knowledgeDao.queryLabelByType(labelType);
	}

	@Override
	public Page<KnowledgeLabelView> queryForPage(int currentPage, int pageSize, int labelId) {

		Page<KnowledgeLabelView> page = new Page<>();

		int allRow = knowledgeDao.queryKnowledges(labelId).size();
		int offset = page.countOffset(currentPage, pageSize);
		List<KnowledgeInfo> knowledgeList = knowledgeDao.queryByPage(offset, pageSize, labelId);
		if (knowledgeList != null && !knowledgeList.isEmpty()) {
			List<KnowledgeLabelView> knowledgeViewList = new ArrayList<>();

			for (int i = 0; i < knowledgeList.size(); i++) {
				KnowledgeInfo knowledge = knowledgeList.get(i);
				List<Label> labelList = new ArrayList<>();
				String vcLabelId = knowledge.getVcLabelId();
				if (vcLabelId != null && !vcLabelId.isEmpty()) {
					int[] labelIdArr = ComputeTool.convertStr2Array(vcLabelId);			
					for (int j = 0; j < labelIdArr.length; j++) {
						List<Label> labels = knowledgeDao.queryLabelById(labelIdArr[j]);
						if (labels != null && !labels.isEmpty()) {
							Label label = labels.get(0);
							labelList.add(label);
						}
					}
				}
				KnowledgeLabelView knowledgeView = new KnowledgeLabelView(knowledge, labelList);
				knowledgeViewList.add(knowledgeView);
			}
			page.setPageNo(currentPage);
			page.setPageSize(pageSize);
			page.setTotalRecords(allRow);
			page.setList(knowledgeViewList);
		}
		return page;
	}

	@Override
	public List<KnowledgeInfo> queryKnowledge(int knowledgeId) {
		return knowledgeDao.queryKnowledge(knowledgeId);
	}

}
