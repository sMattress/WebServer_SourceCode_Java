package com.mattress.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mattress.common.CookieTool;
import com.mattress.model.UserManagerInfo;
import com.mattress.service.IManagerService;

@Controller
@RequestMapping(value = "/managers")
public class ManagerController extends BaseController {
	@Autowired
	@Qualifier("managerServiceImpl")
	private IManagerService managerService;
	private String vcName = "";
	private String vcPassword = "";

	@RequestMapping(value = "/login", method = { RequestMethod.GET })
	public String login(HttpServletRequest request, Model model, @ModelAttribute("message") String message) {
		getCookie(request);
		model.addAttribute("vcName", vcName);
		model.addAttribute("vcPassword", vcPassword);
		model.addAttribute("message", message);
		return "managers/Login";
	}

	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	public String login(HttpServletRequest request, HttpServletResponse response, Model model,
			UserManagerInfo managerInfo, RedirectAttributes attr) {
		// 登录验证
		List<UserManagerInfo> managerInfoList = managerService.checkName(managerInfo);
		if (managerInfoList == null || managerInfoList.isEmpty()) {
			attr.addFlashAttribute("message", "登录失败，账号不存在!");
			return "redirect:login";
		} else {
			managerInfoList = managerService.checkPassword(managerInfo);
			if (managerInfoList != null && !managerInfoList.isEmpty()) {
				int loginMaxAge = 30 * 24 * 60 * 60;
				CookieTool.addCookie(response, "vcName", managerInfo.getVcName(), loginMaxAge);
				CookieTool.addCookie(response, "vcPassword", managerInfo.getVcPassword(), loginMaxAge);
				model.addAttribute("manager", managerInfoList);
				return "redirect:../customer/customersOnlineList";
			} else {
				attr.addFlashAttribute("message", "登录失败，密码错误!");
				return "redirect:login";
			}
		}
	}

	@RequestMapping(value = "/logout", method = { RequestMethod.GET })
	public String logout(HttpServletResponse response) {
		CookieTool.addCookie(response, "vcName", null, 0);
		CookieTool.addCookie(response, "vcPassword", null, 0);
		return "redirect:login";
	}

	private void getCookie(HttpServletRequest request) {
		Cookie cokLoginName = CookieTool.getCookieByName(request, "vcName");
		Cookie cokLoginPwd = CookieTool.getCookieByName(request, "vcPassword");
		if (cokLoginName != null) {
			vcName = cokLoginName.getValue();
			if (cokLoginPwd != null) {
				vcPassword = cokLoginPwd.getValue();
			}
		}
	}

}
