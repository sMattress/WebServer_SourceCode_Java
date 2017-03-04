package com.mattress.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mattress.model.Page;
import com.mattress.model.UserCustomerInfo;
import com.mattress.model.UserCustomerView;
import com.mattress.service.ICustomerService;

@Controller
@RequestMapping(value = "customer")
public class CustomerController extends BaseController {
	@Autowired
	@Qualifier("customerServiceImpl")
	private ICustomerService customerService;

	@RequestMapping(value = "/customersOnlineList", method = { RequestMethod.GET })
	public String customerOnlineList(Model model, @RequestParam(value = "pageNo", required = false) String pageNo) {
		if (pageNo == null) {
			pageNo = "1";
		}		
		
		Page<UserCustomerView> customersPage = customerService.queryForPage(Integer.parseInt(pageNo), 8, 1);
		model.addAttribute("page", customersPage);
		model.addAttribute("customersview", customersPage.getList());
		
		return "customer/CustomerOnlineList";
	}

	@RequestMapping(value = "/customersList", method = { RequestMethod.GET })
	public String customerQuer(Model model, @RequestParam(value = "pageNo", required = false) String pageNo) {
		if (pageNo == null) {
			pageNo = "1";
		}
		Page<UserCustomerView> customersPage = customerService.queryForPage(Integer.parseInt(pageNo), 8, 0);
		model.addAttribute("page", customersPage);
		model.addAttribute("customersview", customersPage.getList());
		return "customer/CustomerList";
	}

}
