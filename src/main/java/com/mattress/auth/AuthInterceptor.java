package com.mattress.auth;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mattress.common.CookieTool;
import com.mattress.model.UserManagerInfo;
import com.mattress.service.IManagerService;

public class AuthInterceptor extends HandlerInterceptorAdapter {
	@Qualifier("managerServiceImpl")
	private IManagerService managerService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		// 设置不拦截的对象
		String[] noFilters = new String[] { "manager/login" }; // 对登录本身的页面以及业务不拦截
		boolean beFilter = true;
		for (String s : noFilters) {
			if (uri.indexOf(s) != -1) {
				beFilter = false;
				break;
			}
		}
		if (beFilter == true) {// 除了不拦截的对象以外
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			Cookie cokLoginName = CookieTool.getCookieByName(request, "vcName");
			Cookie cokLoginPwd = CookieTool.getCookieByName(request, "vcPassword");
			// 如果前面的人登录勾选了记住密码,cookie中存在上一个人的信息
			if (cokLoginName != null && cokLoginPwd != null && cokLoginName.getValue() != ""
					&& cokLoginPwd.getValue() != "") {
				UserManagerInfo managerInfo = new UserManagerInfo();
				managerInfo.setVcName(cokLoginName.getValue());
				managerInfo.setVcPassword(cokLoginPwd.getValue());
				// 检查到客户端保存了用户的密码，进行该账户的验证
				List<UserManagerInfo> managerInfoList = managerService.checkPassword(managerInfo);
				if (managerInfoList == null || managerInfoList.isEmpty()) {
					CookieTool.addCookie(response, "vcName", null, 0); // 清除Cookie
					CookieTool.addCookie(response, "vcPassword", null, 0); // 清除Cookie
					try {
						response.sendRedirect("manager/login");
						return false;
					} catch (IOException e) {
						e.printStackTrace();
					}
					request.getSession().setAttribute("errorInfo", "请登录！");
				}
				// 如果存在此人
				else {
					UserManagerInfo manager = (UserManagerInfo) request.getSession().getAttribute("manager");
					if (manager == null) {// 如果未登录而直接拷贝地址栏进入页面
						request.getSession().setAttribute("manager", managerInfoList.get(0));
					} else {// 用户登录后
						if (manager.getVcName().equals(managerInfoList.get(0).getVcName())) {// 如果当前登录人与cookie中信息一致
							request.getSession().setAttribute("manager", managerInfoList.get(0));
						} else {// 如果当前登录人与cookie中信息不一致
							request.getSession().setAttribute("manager", manager);
						}
					}
				}
			} // 如果cookie中没有内容，即未勾选记住密码，或者是退出后清除了cookie
			else {
				UserManagerInfo u = (UserManagerInfo) request.getSession().getAttribute("manager");
				if (u == null) {// 如果未登录
					response.sendRedirect("manager/login");
					return false;
				} else {// 如果已经登录
						// 执行下一步
				}
			}
		}
		return true;
	}
}
