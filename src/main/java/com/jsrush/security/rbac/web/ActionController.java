package com.jsrush.security.rbac.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsrush.security.rbac.entity.Action;
import com.jsrush.security.rbac.entity.Role;
import com.jsrush.security.rbac.service.ActionService;
import com.jsrush.security.rbac.service.RoleService;
import com.jsrush.security.rbac.service.ShiroManager;
import com.jsrush.security.rbac.vo.ActionDTO;
import com.jsrush.util.LogUtil;
import com.jsrush.util.StringHelper;

/**
 * Created by young on 14-10-8.
 */
@Controller
@RequestMapping(value = "action")
public class ActionController {

	private static final Logger log = Logger.getLogger(ActionController.class);

	@Autowired
	private ActionService actionService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private ShiroManager shiroManager;

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> view(@RequestParam(value = "id") Long id) {
		Action action = actionService.findAction(id);
		if (action != null) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", action.getId());
			if (action.getAction() != null)
				map.put("pId", action.getAction().getId());
			map.put("name", action.getActionName());
			map.put("text", action.getActionText());
			map.put("type", action.getType());
			map.put("idx", action.getIdx());
			map.put("targetUrl", action.getTargetUrl());
			map.put("remark", action.getRemark());
			return map;

		}
		return new HashMap<String, Object>();
	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> list(
			@RequestParam(value = "parent", required = false, defaultValue = "0") Long pid,
			@RequestParam(value = "role", required = false, defaultValue = "0") Long roleId) {
		Collection<Action> curUserActions = new ArrayList<>();// 显示的Action
		Collection<Action> curListActions = new ArrayList<>();// 选中的Action
		Collection<Action> actions = new ArrayList<>();

		if (pid == 0) {
			actions = Collections.singletonList(actionService.findRootAction());
		} else {
			Action action = actionService.findAction(pid);
			actions = actionService.loadChildren(action);
		}

		Long currentRoleId = shiroManager.getCurrentRoleId();
		if (roleId == 0) {
			roleId = currentRoleId;
		}
		if (Objects.equals(roleId, currentRoleId)) {// 如果选择的是当前登陆用户，显示当前用户actions
			curUserActions = roleService.getRole(roleId).getAction();
			curListActions = actions;
		} else {// 低于当前登陆用户，显示parent的actions
			Role role = roleService.getRole(roleId);
			Role cRole = roleService.getRole(shiroManager.getCurrentRoleId());
			if (role != null && cRole != null && role.getParentRole() != null
					&& role.getRoles().contains(cRole)) {
				curUserActions = role.getParentRole().getAction();
				curListActions = role.getAction();
			}
		}

		/*
		 * if (roleService.getRole(roleId) != null) roleActions =
		 * roleService.getRole(roleId).getAction();
		 */

		List<Map<String, Object>> list = new ArrayList<>();
		for (Action ac : actions) {
			if (!curUserActions.contains(ac))
				continue;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", ac.getActionText());
			map.put("id", ac.getId());
			map.put("pId", ac.getAction() == null ? 0 : ac.getAction().getId());
			if (actionService.loadChildren(ac).size() > 0) {
				map.put("isParent", true);
				map.put("open", true);
			}
			if (curListActions.contains(ac))
				map.put("checked", true);
			map.put("remark", ac.getRemark());
			
			list.add(map);
		}

		return list;
	}

	/*
	 * @RequestMapping(value = "add", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public Long add(@RequestParam(value = "name") String name,
	 * 
	 * @RequestParam(value = "title") String title,
	 * 
	 * @RequestParam(value = "id", required = false, defaultValue = "-1") Long
	 * pId) { Action action = actionService.findAction(pId); Action newAction =
	 * new Action(); newAction.setActionName(name); newAction.setAction(action);
	 * newAction.setActionText(title); return actionService.add(action, name,
	 * title); }
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public Long edit(
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "title", required = false, defaultValue = "") String title,
			@RequestParam(value = "id", required = false, defaultValue = "-1") Long id,
			@RequestParam(value = "pId", required = false, defaultValue = "-1") Long pId) {
		return actionService.save(id, name, title, pId);
	}
	
	@RequestMapping(value="saveOrUpdate")
	@ResponseBody
	public int saveOrUpdate(@RequestParam(value = "params") String params){
		if (StringHelper.isEmpty(params))
			return -1;
		try {
			JSONObject jsonObject = JSONObject.fromObject(params);
			ActionDTO dto = new ActionDTO();
			if (StringHelper.isNotEmpty(jsonObject.getString("id")) && StringUtils.isNumeric(jsonObject.getString("id"))) {
				dto.setId(jsonObject.getLong("id"));
			}
			if (StringHelper.isNotEmpty(jsonObject.getString("pId")) && StringUtils.isNumeric(jsonObject.getString("pId"))) {
				dto.setpId(jsonObject.getLong("pId"));
			}
			dto.setKey(jsonObject.getString("name"));
			dto.setName(jsonObject.getString("text"));
			dto.setTargetUrl(jsonObject.getString("targetUrl"));
			dto.setTitle(jsonObject.getString("remark"));
			dto.setType(jsonObject.getString("type"));
			dto.setIdx(jsonObject.getInt("idx"));
			
			List<Long> roleIdList = shiroManager.getCurrentAboveRoleIdList();
			actionService.saveOrUpdate(dto, roleIdList);
			return 1;
		} catch (Exception e) {
			log.error(LogUtil.stackTraceToString(e));
			e.printStackTrace();
		}
		return -1;
	}

	@RequestMapping(value = "del", method = RequestMethod.POST)
	@ResponseBody
	public int del(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
		Action action = actionService.findAction(id);
		
		actionService.del(action);
		return 1;
	}

	@RequestMapping(value = "loadMenuTree")
	@ResponseBody
	public List<Map<String, Object>> loadMenuTree() {
		List<Map<String, Object>> menuTree = null;
		try {
			String lowerRoleIds = shiroManager.getCurrentLowerRoleIds(",");
			menuTree = actionService.loadMenuTree(lowerRoleIds);
		} catch (Exception e) {
			log.error(LogUtil.stackTraceToString(e));
			e.printStackTrace();
			menuTree = new ArrayList<Map<String, Object>>();
		}
		return menuTree;
	}

}
