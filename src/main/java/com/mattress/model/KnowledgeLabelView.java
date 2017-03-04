package com.mattress.model;

import java.util.List;

public class KnowledgeLabelView {
	
	private KnowledgeInfo knowledge;
	
	private List<Label> labelList;

	public KnowledgeLabelView(){
		
	}
	
	public KnowledgeLabelView(KnowledgeInfo knowledge, List<Label> labelList){
		this.setKnowledge(knowledge);
		this.setLabelList(labelList);
	}

	public KnowledgeInfo getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(KnowledgeInfo knowledge) {
		this.knowledge = knowledge;
	}

	public List<Label> getLabelList() {
		return labelList;
	}

	public void setLabelList(List<Label> labelList) {
		this.labelList = labelList;
	}
}
