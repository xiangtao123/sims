package com.jsrush.security.rbac.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.jsrush.security.rbac.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jsrush.security.rbac.entity.Action;
import com.jsrush.security.rbac.entity.Role;
import com.jsrush.security.rbac.service.ActionService;
import com.jsrush.security.rbac.service.RoleService;
import com.jsrush.security.rbac.service.ShiroManager;
import com.jsrush.util.StringHelper;

/**
 * Created by young on 14-10-8.
 */
@Controller
@RequestMapping(value = "role")
public class RoleController {

    private Logger log = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;
    @Autowired
    private ActionService actionService;
    @Autowired
    UserService userService;

    @RequestMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("/system/biz_role_action");
        int currentRoleLevel = shiroManager.getCurrentRoleLevel();
        log.info("currentRoleLevel = " + currentRoleLevel);
        view.addObject("roleLevel", currentRoleLevel);
        return view;
    }

    @ModelAttribute("role")
    public Role role(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        return roleService.getRole(id);
    }

    @Autowired
    private ShiroManager shiroManager;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String, Object> view(@ModelAttribute("role") Role role) {
        if (role != null) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", role.getId());
            map.put("name", role.getRoleName());
            if (role.getEc() != null)
                map.put("ec", role.getEc().getId());
            if (role.getParentRole() != null)
                map.put("pId", role.getParentRole().getId());
            List<String> list = new ArrayList<String>();
            for (Action action : role.getAction()) {
                list.add(action.getActionText());
            }
            map.put("actions", StringHelper.join(list, ","));
            return map;
        }

        return new HashMap<String, Object>();
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public Long edit(@RequestParam(value = "id", required = false, defaultValue = "-1") Long id,
                     @RequestParam(value = "pId", required = false, defaultValue = "-1") Long pId,
                     @RequestParam(value = "ec", required = false, defaultValue = "-1") Long ecId,
                     @RequestParam(value = "name", required = false, defaultValue = "") String name,
                     @RequestParam(value = "actions", required = false, defaultValue = "") List<Long> aIds) {
        // Set<Long> aboveRoleId = shiroManager.getCurrentAboveRoleId();
        // Long[] permissions = aboveRoleId.toArray(new
        // Long[aboveRoleId.size()]);
        if (shiroManager.getCurrentRoleId() != 1)
            ecId = shiroManager.getCurrentUser().getEcId();
        if (Objects.equals(shiroManager.getCurrentRoleId(), id))
            aIds.clear();
        return roleService.save(id, pId, name, aIds, ecId);
    }

    @RequestMapping(value = "del", method = RequestMethod.POST)
    @ResponseBody
    public int del(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        Role role = roleService.findOne(id);
        Role curRole = roleService.findOne(shiroManager.getCurrentRoleId());
        if (role == null) return 4;
        if (!role.getRoles().contains(curRole))
            return 2;
        if (userService.findByRole(role).size() > 0)
            return 3;
        roleService.del(id);
        return 1;
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public List<Map<String, Object>> list(@ModelAttribute("role") Role pRole) {
        List<Role> roles = new ArrayList<>();
        Role currentRole = roleService.getRole(shiroManager.getCurrentRoleId());
        if (pRole == null) {
            roles.add(currentRole);
        } else {
            if (pRole.getRoles().contains(currentRole))
                roles = roleService.getRoleByPId(pRole.getId());
        }

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (Role role : roles) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", role.getRoleName());
            map.put("id", role.getId());
            if (role.getParentRole() != null)
                map.put("pId", role.getParentRole().getId());
            else
                map.put("pId", 0);
            List<Long> actions = new ArrayList<>();
            for (Action action : role.getAction()) {
                actions.add(action.getId());
            }
            map.put("action", actions);
            if (role.getChildRole().size() > 0) {
                map.put("isParent", true);
                map.put("open", true);
            }
            list.add(map);
        }

        return list;
    }

    @RequestMapping(value = "all")
    @ResponseBody
    public List<Map<String, Object>> all() {
        Long cId = shiroManager.getCurrentRoleId();
        Role current = roleService.findOne(cId);

        Collection<Role> roles = roleService.getAll();
        List<Map<String, Object>> list = new ArrayList<>();
        for (Role role : roles) {
            if (!role.getRoles().contains(current))
                continue;
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", role.getRoleName());
            map.put("id", role.getId());
            map.put("pId", role.getId());
            List<Long> actions = new ArrayList<>();
            for (Action action : role.getAction()) {
                actions.add(action.getId());
            }
            map.put("action", actions);
            if (role.getChildRole().size() > 0) {
                map.put("isParent", true);
                map.put("open", true);
            }
            list.add(map);
        }

        return list;
    }

    @RequestMapping(value = "actions", method = RequestMethod.GET)
    @ResponseBody
    public List<Long> getActions(@RequestParam(value = "role") Long roleId) {
        List<Long> list = new ArrayList<>();

        if (roleService.getRole(roleId) != null) {
            Set<Action> roleActions = roleService.getRole(roleId).getAction();

            for (Action roleAction : roleActions) {
                list.add(roleAction.getId());
            }
        }
        return list;
    }

}
