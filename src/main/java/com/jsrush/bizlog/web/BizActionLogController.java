package com.jsrush.bizlog.web;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsrush.bizlog.entity.BizActionLog;
import com.jsrush.bizlog.service.BizActionLogService;
import com.jsrush.security.rbac.service.ShiroManager;
import com.jsrush.util.SystemUtil;

@Controller
@RequestMapping("/bizActionLog")
public class BizActionLogController {

	@Autowired
	private ShiroManager shiroManager;
	
	@Autowired
	private BizActionLogService bizActionLogService;
	
	@RequiresPermissions("bizlog:open")
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "systemmanagement/bizactionlog";
	}
	
	@RequestMapping(value="/list")
	@ResponseBody
	public Map<String,Object> list(@RequestParam(required = false, value = "params") String params,
					   @RequestParam(defaultValue = "1", value = "page") int pageNo, 
					   @RequestParam(defaultValue = "10", value = "rows") int pageSize){
		try {
			String permissions = shiroManager.getCurrentRoleIdStr();
			pageNo = SystemUtil.firstNo(pageNo, pageSize);
			List<BizActionLog> list = bizActionLogService.findListWithParams(params, pageNo, pageSize,permissions);
			return SystemUtil.createPageInfoMap(pageNo, pageSize, bizActionLogService.findCountWithParams(params,permissions ), list);
		} catch (Exception e) {
			e.printStackTrace();
			return SystemUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
	}
	
}
