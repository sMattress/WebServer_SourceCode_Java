package com.mattress.controller.user;

import com.alibaba.druid.util.StringUtils;
import com.mattress.model.UserCustomerInfo;
import com.mattress.utils.CacheUtils;
import com.mattress.utils.ErrCodeMap;
import com.mattress.utils.UrlMap;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import com.mattress.service.IUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@RequestMapping("/v1/user")
public class UserController {
	
	@Autowired
	@Qualifier("userServiceImpl")
	private IUserService userService;

	@GetMapping("/code")
	public AppResponse code(@RequestParam final String account) {

		final UserCustomerInfo user = userService.getUser(account);
		if (user == null) {
			return AppResponse.failure(ErrCodeMap.ACCOUNT_INVALID);
		}

		final String code =  CacheUtils.makeCode(account);
		return AppResponse.success().addParam(new HashMap<String, String>() {{
			put("code", code);
		}});
	}

	@PostMapping("/register")
	public AppResponse register(HttpServletRequest request) {

		final String account = request.getParameter("account");
		final String password = request.getParameter("password");

		System.out.println(String.format(
				"new user: {\"account\" : \"%s\", \"password\" : \"%s\"}",
				account,
				password));

		final UserCustomerInfo newUser = userService.makeUser(account, password);
		if (newUser == null) {
			return AppResponse.failure(ErrCodeMap.ACCOUNT_TOKEN);
		}

		return AppResponse.success();
	}

	@GetMapping("/login")
	public AppResponse login(@RequestParam final String account, @RequestParam final String password) {

		final UserCustomerInfo user = userService.getUser(account);
		if (user == null) {
			return AppResponse.failure(ErrCodeMap.ACCOUNT_INVALID);
		}

		final String realPassword = user.getVcPassword();
		final String code = CacheUtils.getCode(account);
		if (code != null && !code.isEmpty()) {
			CacheUtils.removeCode(account);
		}else {
			CacheUtils.removeCode(account);
		}
		final String securePassword = DigestUtils.md5Hex(realPassword + code);
		if (StringUtils.equals(securePassword, password)) {
			userService.setUserState(user, 1);
			return AppResponse.success().addParam(new HashMap<String, String>() {{
				put("token", CacheUtils.makeToken(account));
				put("expires_in", CacheUtils.getTokenExpiresIn(account));
			}});
		}else {
			return AppResponse.failure(ErrCodeMap.PASSWORD_INVALID);
		}
	}

	@GetMapping("/get/base_info")
	public AppResponse getBaseInfo(@RequestParam String account, @RequestParam String timestamp, @RequestParam String sign) {

		final UserCustomerInfo user = userService.getUser(account);
		if (user == null) {
			return AppResponse.failure(ErrCodeMap.ACCOUNT_INVALID);
		}

		final AppResponse res = userService.validatesSign(account, UrlMap.GET_BASE_INFO, timestamp, sign);
		if (res.getFlag() == 1) {
			user.setVcPassword(null);
			user.setVcSalt(null);
			res.addParam(userService.extractBaseInfo(user));
		}

		return res;
	}

	@PostMapping("/update/base_info")
	public AppResponse updateBaseInfo(HttpServletRequest request, @RequestParam String account, @RequestParam String timestamp,  @RequestParam String sign) {

		final UserCustomerInfo user = userService.getUser(account);
		if (user == null) {
			return AppResponse.failure(ErrCodeMap.ACCOUNT_INVALID);
		}

		final AppResponse res = userService.validatesSign(account, UrlMap.UPDATE_BASE_INFO, timestamp, sign);
		if (res.getFlag() == 1) {
			userService.updateBaseInfo(user, request.getParameterMap());
		}

		return res;
	}

	@PostMapping("/update/secure_info")
	public AppResponse updateSecureInfo(HttpServletRequest request, @RequestParam String account, @RequestParam String timestamp,  @RequestParam String sign) {

		final UserCustomerInfo user = userService.getUser(account);
		if (user == null) {
			return AppResponse.failure(ErrCodeMap.ACCOUNT_INVALID);
		}

		AppResponse res = userService.validatesSign(account, UrlMap.UPDATE_SECURE_INFO, timestamp, sign);
		if (res.getFlag() != 1) {
			return res;
		}

		return userService.updateSecureInfoWithPassword(user, request.getParameterMap());
	}

	@PostMapping("/forget/secure_info")
	public AppResponse updateSecureInfo(HttpServletRequest request, @RequestParam String account, @RequestParam String platform) {

		final UserCustomerInfo user = userService.getUser(account);
		if (user == null) {
			return AppResponse.failure(ErrCodeMap.ACCOUNT_INVALID);
		}

		return userService.updateSecureInfoWithSms(user, request.getParameterMap(), platform);
	}

	@PostMapping("/device/bind")
	public AppResponse deviceBind(HttpServletRequest request, @RequestParam String account, @RequestParam String timestamp,  @RequestParam String sign) {

		final UserCustomerInfo user = userService.getUser(account);
		if (user == null) {
			return AppResponse.failure(ErrCodeMap.ACCOUNT_INVALID);
		}

		AppResponse res = userService.validatesSign(account, UrlMap.DEVICE_BIND, timestamp, sign);
		if (res.getFlag() != 1) {
			return res;
		}

		return userService.updateUserDeviceBindship(user, request.getParameterMap(), true);
	}

	@PostMapping("/device/unbind")
	public AppResponse deviceUnbind(HttpServletRequest request, @RequestParam String account, @RequestParam String timestamp,  @RequestParam String sign) {

		final UserCustomerInfo user = userService.getUser(account);
		if (user == null) {
			return AppResponse.failure(ErrCodeMap.ACCOUNT_INVALID);
		}

		AppResponse res = userService.validatesSign(account, UrlMap.DEVICE_UNBIND, timestamp, sign);
		if (res.getFlag() != 1) {
			return res;
		}

		return userService.updateUserDeviceBindship(user, request.getParameterMap(), false);
	}

	@PostMapping("/device/update")
	public AppResponse deviceUpdate(HttpServletRequest request, @RequestParam String account, @RequestParam String timestamp,  @RequestParam String sign) {

		final UserCustomerInfo user = userService.getUser(account);
		if (user == null) {
			return AppResponse.failure(ErrCodeMap.ACCOUNT_INVALID);
		}

		AppResponse res = userService.validatesSign(account, UrlMap.DEVICE_UPDATE, timestamp, sign);
		if (res.getFlag() != 1) {
			return res;
		}

		return userService.updateUserDeviceInfo(user, request.getParameterMap());
	}

	@GetMapping("/device/list")
	public AppResponse deviceList(HttpServletRequest request, @RequestParam String account, @RequestParam String timestamp,  @RequestParam String sign) {

		final UserCustomerInfo user = userService.getUser(account);
		if (user == null) {
			return AppResponse.failure(ErrCodeMap.ACCOUNT_INVALID);
		}

		AppResponse res = userService.validatesSign(account, UrlMap.DEVICE_LIST, timestamp, sign);
		if (res.getFlag() != 1) {
			return res;
		}

		Object[] res1 = userService.getDeviceList(user);
		return AppResponse.success().setParams(res1);
	}
}
