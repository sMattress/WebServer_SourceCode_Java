package com.mattress.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mattress.model.DeviceInfo;
import com.mattress.model.Page;
import com.mattress.service.IDeviceService;

@Controller
@RequestMapping(value = "/deviceManager")
public class DeviceController extends BaseController {
	@Autowired
	@Qualifier("deviceServiceImpl")
	private IDeviceService deviceService;

	/**
	 * 设备列表
	 * 
	 * @param model
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value = "/devicesList", method = { RequestMethod.GET })
	public String devicesQuer(Model model, @RequestParam(value = "pageNo", required = false) String pageNo) {
		if (pageNo == null) {
			pageNo = "1";
		}
		Page<DeviceInfo> devicesPage = deviceService.queryForPage(Integer.parseInt(pageNo), 8);
		model.addAttribute("page", devicesPage);
		model.addAttribute("deviceslist", devicesPage.getList());
		return "device/DeviceList";
	}

	/**
	 * 出厂设备
	 * 
	 * @param model
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value = "/releaseDeviceList", method = { RequestMethod.GET })
	public String releaseDeviceQuer(Model model, @RequestParam(value = "pageNo", required = false) String pageNo) {
		if (pageNo == null) {
			pageNo = "1";
		}
		System.out.println("releaseDeviceQuer:  1");

		Page<DeviceInfo> releaseDevicesPage = deviceService.queryByStatus(Integer.parseInt(pageNo), 8, 0);
		model.addAttribute("page", releaseDevicesPage);
		model.addAttribute("releasedeviceslist", releaseDevicesPage.getList());
		return "device/ReleaseDeviceList";
	}

	/**
	 * 工作设备
	 * 
	 * @param model
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value = "/validatedeviceList", method = { RequestMethod.GET })
	public String validateDeviceList(Model model, @RequestParam(value = "pageNo", required = false) String pageNo) {
		if (pageNo == null) {
			pageNo = "1";
		}
		Page<DeviceInfo> validateDevicesPage = deviceService.queryByStatus(Integer.parseInt(pageNo), 8, 1);
		model.addAttribute("page", validateDevicesPage);
		model.addAttribute("validatedeviceslist", validateDevicesPage.getList());
		return "device/ValidateDeviceList";
	}

	/**
	 * 工作设备状态切换
	 * 
	 * @param model
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value = "/changevalidatedevice", method = { RequestMethod.GET })
	public String changeValidateDevice(Model model, @RequestParam(value = "IId", required = false) int IId,
			@RequestParam(value = "IStatus", required = false) int IStatus) {
		System.out.println("IStatus:" + IStatus);
		System.out.println("IId:" + IId);
		DeviceInfo deviceinfo = new DeviceInfo();
		deviceinfo.setIId(IId);
		if (IStatus == 1) {
			deviceinfo.setIStatus(IStatus + 1);
			deviceService.chageDeviceStatus(deviceinfo);
			return "redirect:validatedeviceList";
		} else {
			deviceinfo.setIStatus(IStatus - 1);
			deviceService.chageDeviceStatus(deviceinfo);
			return "redirect:invalidatedeviceList";
		}

	}

	/**
	 * 报废设备
	 * 
	 * @param model
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value = "/invalidatedeviceList", method = { RequestMethod.GET })
	public String invalidateDeviceList(Model model, @RequestParam(value = "pageNo", required = false) String pageNo) {
		if (pageNo == null) {
			pageNo = "1";
		}
		Page<DeviceInfo> invalidateDevicesPage = deviceService.queryByStatus(Integer.parseInt(pageNo), 8, 2);
		model.addAttribute("page", invalidateDevicesPage);
		model.addAttribute("invalidatedeviceslist", invalidateDevicesPage.getList());
		return "device/InvalidateDeviceList";
	}

	@RequestMapping(value = "/enterdevice", method = { RequestMethod.GET })
	public String enterDevice(HttpServletRequest request, Model model) {
		return "device/EnterDevice";
	}

	@RequestMapping(value = "/enterdevice", method = { RequestMethod.POST })
	public String enterDevice(HttpServletRequest request, Model model, DeviceInfo deviceinfo) {
		deviceinfo.setIStatus(0);
		deviceinfo.setTTime(new Date());
		deviceService.saveDevice(deviceinfo);

		return "device/EnterDevice";
	}

}
