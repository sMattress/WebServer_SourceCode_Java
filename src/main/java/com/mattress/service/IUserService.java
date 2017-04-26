package com.mattress.service;

import com.mattress.controller.user.AppResponse;
import com.mattress.model.DeviceInfo;
import com.mattress.model.UserBindDevice;
import com.mattress.model.UserCustomerInfo;

import java.util.List;
import java.util.Map;

public interface IUserService {


	/**
	 * 获取用户实体
	 * @param account 账号
	 * @return
	 */
	UserCustomerInfo getUser(String account);

	/**
	 * 创建新用户
	 * @param account 账号
	 * @param password 密码
	 * @return
	 */
	UserCustomerInfo makeUser(String account, String password);

	/**
	 * 设置用户状态
	 * @param user 用户实例
	 * @param state 状态
	 */
	void setUserState(UserCustomerInfo user, int state);

	/**
	 * 验证签名
	 * @param account 账号
	 * @param url 连接地址
	 * @param timestamp 时间戳
	 * @param sign 签名
	 * @return
	 */
	AppResponse validatesSign(String account, String url, String timestamp, String sign);

	/**
	 * 验证密码
	 * @param account 账号
	 * @param password 密码
	 * @return
	 */
	AppResponse validatesPassword(String account, String password);

	/**
	 * 验证安全密码
	 * @param account 账号
	 * @param password 安全密码
	 * @return
	 */
	AppResponse validatesSecurePassword(String account, String password);

	/**
	 * 验证短信
	 * @param account 账号
	 * @param sms 短信验证码
	 * @param platform 平台
	 * @return
	 */
	AppResponse validatesSms(String account, String sms, String platform);

	/**
	 * 抽取用户基本信息
	 * 用于兼容手机
	 * @param user 用户实例
	 * @return
	 */
	Map<String, Object> extractBaseInfo(UserCustomerInfo user);

	/**
	 * 抽取用户安全信息
	 * 用于兼容手机
	 * @param user 用户实例
	 * @return
	 */
	Map<String, Object> extractSecureInfo(UserCustomerInfo user);

	/**
	 * 更新用户基本信息
	 * @param user 用户实例
	 * @param params 参数列表
	 */
	void updateBaseInfo(UserCustomerInfo user, Map<String, String[]> params);

	/**
	 * 通过密码更新用户安全信息
	 * @param user 用户实例
	 * @param params 参数列表
	 */
	AppResponse updateSecureInfoWithPassword(UserCustomerInfo user, Map<String, String[]> params);

	/**
	 * 通过短信更新用户安全信息
	 * @param user 用户实例
	 * @param params 参数列表
	 */
	AppResponse updateSecureInfoWithSms(UserCustomerInfo user, Map<String, String[]> params, String platform);

	/**
	 * 获取设备实例
	 * @param name 设备名
	 * @return
	 */
	DeviceInfo getDevice(String name);

	/**
	 * 获取设备列表
	 * @param user 用户实例
	 * @return
	 */
	Object[] getDeviceList(UserCustomerInfo user);

	/**
	 * 获取绑定
	 * @param user 用户实例
	 * @param device 设备实例
	 * @return
	 */
	UserBindDevice getBindShip(UserCustomerInfo user, DeviceInfo device);

	/**
	 * 更新用户与设备的绑定关系
	 * @param user 用户实例
	 * @param params 参数列表
	 * @param bind true为绑定，false为解绑
	 * @return
	 */
	AppResponse updateUserDeviceBindship(UserCustomerInfo user, Map<String, String[]> params, boolean bind);

	/**
	 * 修改用户绑定的设备的信息
	 * @param user 用户实例
	 * @param params 参数列表
	 * @return
	 */
	AppResponse updateUserDeviceInfo(UserCustomerInfo user, Map<String, String[]> params);

}
