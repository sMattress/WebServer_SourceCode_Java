package com.mattress.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mattress.model.KnowledgeInfo;
import com.mattress.model.KnowledgeLabelView;
import com.mattress.model.Label;
import com.mattress.model.Page;
import com.mattress.model.UserManagerInfo;
import com.mattress.service.IKnowledgeService;

@Controller
@RequestMapping(value = "/knowledge")
public class KnowledgeController extends BaseController {
	@Autowired
	@Qualifier("knowledgeServiceImpl")
	private IKnowledgeService knowledgeService;

	@RequestMapping(value = "/knowledgeList", method = { RequestMethod.GET })
	public String knowledgeList(Model model, @RequestParam(value = "pageNo", required = false) String pageNo,
			@RequestParam(value = "labelId", required = false) String labelId) {
		if (pageNo == null) {
			pageNo = "1";
		}
		if (labelId == null) {
			labelId = "0";
		}
		System.out.println("pageNo........." + pageNo);
		System.out.println("labelId........." + labelId);
		List<Label> lableList = knowledgeService.queryLabelByType(0);
		Page<KnowledgeLabelView> knowledgePage = knowledgeService.queryForPage(Integer.parseInt(pageNo), 4,
				Integer.parseInt(labelId));
		model.addAttribute("labelId", labelId);
		model.addAttribute("lablelist", lableList);
		model.addAttribute("page", knowledgePage);
		model.addAttribute("knowledgelist", knowledgePage.getList());
		return "knowledge/KnowledgeList";
	}

	@RequestMapping(value = "/saveKnowledge", method = { RequestMethod.POST })
	public String saveKnowledge(Model model, KnowledgeInfo knowledgeInfo) {
		System.out.println("saveKnowledge....." + knowledgeInfo.getIId());
		if (knowledgeInfo.getIId() == null) {
			knowledgeService.save(knowledgeInfo);
		} else {
			knowledgeService.update(knowledgeInfo);
		}
		return "redirect:knowledgeList";
	}

	@RequestMapping(value = "/deleteKnowledge", method = { RequestMethod.POST })
	public @ResponseBody KnowledgeInfo deleteKnowledge(HttpServletRequest request, KnowledgeInfo knowledgeInfo) {
		String knowledgeId = request.getParameter("knowledgeId");
		knowledgeInfo = knowledgeService.queryKnowledge(Integer.parseInt(knowledgeId)).get(0);
		System.out.println("getIId......." + knowledgeInfo.getIId());
		knowledgeService.delete(knowledgeInfo);
		return knowledgeInfo;
	}
}
