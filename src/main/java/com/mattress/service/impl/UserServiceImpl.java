package com.mattress.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.mattress.dao.IBindDao;
import com.mattress.dao.IDeviceDao;
import com.mattress.model.DeviceInfo;
import com.mattress.model.UserBindDevice;
import com.mattress.utils.CacheUtils;
import com.mattress.utils.ErrCodeMap;
import com.mattress.utils.HttpsUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.mattress.controller.user.AppResponse;
import com.mattress.dao.IUserDao;
import com.mattress.model.UserCustomerInfo;
import com.mattress.service.IUserService;

@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	@Qualifier("userDaoImpl")
	private IUserDao userDao;

	@Autowired
	@Qualifier("bindDaoImpl")
	private IBindDao bindDao;

	@Autowired
	@Qualifier("deviceDaoImpl")
	private IDeviceDao deviceDao;

	@Override
	public UserCustomerInfo getUser(final String account) {

		final List<UserCustomerInfo> users = userDao.find("FROM UserCustomerInfo user WHERE user.CPhoneNumber = :CPhoneNumber", new HashMap<String, Object>() {{
			put("CPhoneNumber", account);
		}});

		return users == null || users.isEmpty() ? null : users.get(0);
	}

	@Override
	public DeviceInfo getDevice(final String name) {

		final List<DeviceInfo> devices = deviceDao.find("FROM DeviceInfo device WHERE device.vcName = :name", new HashMap<String, Object>() {{
			put("name", name);
		}});

		return devices == null || devices.isEmpty() ? null : devices.get(0);
	}

	@Override
	public Object[] getDeviceList(final UserCustomerInfo user) {

		final List<UserBindDevice> bindShips = bindDao.find("FROM UserBindDevice bindShip WHERE bindShip.fkCustomerId = :userId", new HashMap<String, Object>() {{
			put("userId", user.getIId());
		}});

		if (bindShips == null || bindShips.isEmpty()) {
			return null;
		}

		final List<Integer> deviceIds = new ArrayList<>();
		bindShips.forEach(bindShip -> {
			deviceIds.add(bindShip.getFkDeviceId());
		});


		final List<DeviceInfo> devices = deviceDao.find("FROM DeviceInfo device WHERE device.IId IN (:deviceIds)", new HashMap<String, Object>() {{
			put("deviceIds", deviceIds);
		}});

		if (devices == null || devices.isEmpty()) {
			return null;
		}

		final Map<String, Object> res = new HashMap<>();

		bindShips.forEach(bindShip -> {
			res.put(String.valueOf(bindShip.getFkDeviceId()), bindShip.getVcName());
		});

		devices.forEach(device -> {
			final String key = String.valueOf(device.getIId());
			final String alias = (String) res.get(key);
			final Map<String, String> value = new HashMap<String, String>() {{
				put("device_name", device.getVcName());
				put("alias", alias);
			}};
			res.put(key, value);
		});

		return res.values().toArray();
	}

	@Override
	public UserBindDevice getBindShip(final UserCustomerInfo user, final DeviceInfo device) {

		final List<UserBindDevice> bindShips = bindDao.find("FROM UserBindDevice bindShip WHERE bindShip.fkCustomerId = :userId AND bindShip.fkDeviceId = :deviceId", new HashMap<String, Object>() {{
			put("userId", user.getIId());
			put("deviceId", device.getIId());
		}});

		return bindShips == null || bindShips.isEmpty() ? null : bindShips.get(0);
	}

	@Override
	public UserCustomerInfo makeUser(String account, String password) {

		final UserCustomerInfo user = getUser(account);
		if (user != null) {
			return null;
		}

		final UserCustomerInfo newUser = new UserCustomerInfo();
		newUser.setCPhoneNumber(account);
		newUser.setVcPassword(password);
		userDao.save(newUser);

		return newUser;
	}

	@Override
	public void setUserState(UserCustomerInfo user, int state) {
		user.setTiStatus((byte) state);
		userDao.update(user);
	}

	@Override
	public AppResponse validatesSign(String account, String url, String timestamp, String sign) {

		final String token = CacheUtils.getToken(account);
		if (token == null || token.isEmpty()) {
			return AppResponse.failure(ErrCodeMap.SIGN_INVALID);
		}

		final String rightUrl = String.format("%s?account=%s&timestamp=%s&token=%s", url, account, timestamp, token);
		final String rightSign = DigestUtils.md5Hex(rightUrl);

		System.out.println("url: " + rightUrl);
		System.out.println("sign: " + sign + " <=> " + rightSign);

		return StringUtils.equals(rightSign, sign) ? AppResponse.success() : AppResponse.failure(ErrCodeMap.SIGN_INVALID);
	}

	@Override
	public AppResponse validatesPassword(String account, String password) {

		final UserCustomerInfo user = getUser(account);
		if (user == null) {
			return AppResponse.failure(ErrCodeMap.ACCOUNT_INVALID);
		}

		return StringUtils.equals(user.getVcPassword(), password) ? AppResponse.success() : AppResponse.failure(ErrCodeMap.PASSWORD_INVALID);
	}

	@Override
	public AppResponse validatesSecurePassword(String account, String password) {

		final UserCustomerInfo user = getUser(account);
		if (user == null) {
			return AppResponse.failure(ErrCodeMap.ACCOUNT_INVALID);
		}

		final String code = CacheUtils.getCode(account);
		if (code == null || code.isEmpty()) {
			return AppResponse.failure(ErrCodeMap.PLATFORM_INVALID);
		}

		final String securePassword = DigestUtils.md5Hex(user.getVcPassword() + code);
		return StringUtils.equals(securePassword, password) ? AppResponse.success() : AppResponse.failure(ErrCodeMap.PASSWORD_INVALID);
	}

	@Override
	public AppResponse validatesSms(final String account, final String sms, final String platform) {

		final String url = "https://webapi.sms.mob.com/sms/verify";
		final String iosKey = "1b7e634a3263c";
		final String androidKey = "1b728173e3684";

		final String content = String.format("appkey=%s&phone=%s&zone=86&code=%s",
				StringUtils.equals("ios", platform) ? iosKey : androidKey,
				account,
				sms
		);

		JSONObject res = null;
		try {
			res = HttpsUtil.post(url, content, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}


		if (res == null) {
			return AppResponse.failure(ErrCodeMap.UNREALIZED_FUNCTION);
		}else {
			return (int) res.get("status") == 200 ? AppResponse.success() : AppResponse.failure((int) res.get("status"));
		}
	}

	@Override
	public Map<String, Object> extractBaseInfo(final UserCustomerInfo user) {
		return new HashMap<String, Object>() {{
			put("name", user.getVcName());
			put("birthday", user.getTBirthday());
			put("sex", user.getTiGender());
			put("img_url", user.getVcImageUrl());
		}};
	}

	@Override
	public Map<String, Object> extractSecureInfo(final UserCustomerInfo user) {
		return new HashMap<String, Object>() {{
			put("account", user.getCPhoneNumber());
			put("password", user.getVcPassword());
		}};
	}

	@Override
	public void updateBaseInfo(UserCustomerInfo user, Map<String, String[]> params) {

		if (params.containsKey("name")) {
			user.setVcName(params.get("name")[0]);
		}
		if (params.containsKey("img_url")) {
			user.setVcImageUrl(params.get("img_url")[0]);
		}
		if (params.containsKey("birthday")) {
			final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date birthday = new Date();
			try {
				birthday = formatter.parse(params.get("birthday")[0]);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			user.setTBirthday(birthday);
		}
		if (params.containsKey("sex")) {
			user.setTiGender(Byte.valueOf(params.get("sex")[0]));
		}
		userDao.update(user);
	}

	@Override
	public AppResponse updateSecureInfoWithPassword(UserCustomerInfo user, Map<String, String[]> params) {

		final String oldPassword = params.get("old_password")[0];

		if (!StringUtils.equals(user.getVcPassword(), oldPassword)) {
			return AppResponse.failure(ErrCodeMap.PASSWORD_INVALID);
		}

		if (params.containsKey("new_password")) {
			user.setVcPassword(params.get("new_password")[0]);
		}
		userDao.update(user);

		return AppResponse.success();
	}

	@Override
	public AppResponse updateSecureInfoWithSms(UserCustomerInfo user, Map<String, String[]> params, String platform) {

		final String sms = params.get("sms")[0];
		final AppResponse res = validatesSms(user.getCPhoneNumber(), sms, platform);

		if (res.getFlag() == 1 && params.containsKey("new_password")) {
			user.setVcPassword(params.get("new_password")[0]);
			userDao.update(user);
		}

		return res;
	}

	@Override
	public AppResponse updateUserDeviceBindship(UserCustomerInfo user, Map<String, String[]> params, boolean bind) {

		final String deviceName = params.get("device_name")[0];
		final DeviceInfo device = getDevice(deviceName);
		if (device == null) {
			return AppResponse.failure(ErrCodeMap.DEVICE_INVALID);
		}

		final UserBindDevice bindShip = getBindShip(user, device);
		if (bind) {
			if (bindShip != null) {
				return AppResponse.failure(ErrCodeMap.DEVICE_TOKEN);
			}

			final UserBindDevice newBindShip = new UserBindDevice();
			newBindShip.setFkCustomerId(user.getIId());
			newBindShip.setFkDeviceId(device.getIId());
			bindDao.save(newBindShip);
			device.setIStatus(3);
			deviceDao.update(device);
		}else {
			if (bindShip == null) {
				return AppResponse.failure(ErrCodeMap.DEVICE_INVALID);
			}

			bindDao.delete(bindShip);
			device.setIStatus(0);
			deviceDao.update(device);
		}

		return AppResponse.success();
	}

	@Override
	public AppResponse updateUserDeviceInfo(UserCustomerInfo user, Map<String, String[]> params) {

		final String deviceName = params.get("device_name")[0];
		final DeviceInfo device = getDevice(deviceName);
		if (device == null) {
			return AppResponse.failure(ErrCodeMap.DEVICE_INVALID);
		}

		if (params.containsKey("alias")) {
			final String alias = params.get("alias")[0];
			final UserBindDevice bindShip = getBindShip(user, device);
			if (bindShip == null) {
				return AppResponse.failure(ErrCodeMap.DEVICE_INVALID);
			}

			bindShip.setVcName(alias);
			bindDao.update(bindShip);
		}

		return AppResponse.success();
	}

}
