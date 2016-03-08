package com.jsrush.security.rbac.web;

import com.jsrush.security.rbac.entity.Role;
import com.jsrush.security.rbac.entity.User;
import com.jsrush.security.rbac.service.RoleService;
import com.jsrush.security.rbac.service.ShiroManager;
import com.jsrush.security.rbac.service.UserService;
import com.jsrush.util.SystemUtil;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    ShiroManager shiroManager;

    @RequestMapping(value = "/onLineList", method = RequestMethod.GET)
    @ResponseBody
    public Integer getOnlineUser(HttpServletRequest request) {
    	return shiroManager.getOnlineUser(request);
     }
     
    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(@RequestParam(value = "params", required = false, defaultValue = "{}") String params,
                                    @RequestParam(defaultValue = "1", value = "page") int pageNo,
                                    @RequestParam(defaultValue = "10", value = "rows") int pageSize) {
        Long cId = shiroManager.getCurrentRoleId();
        int index = SystemUtil.firstNo(pageNo, pageSize);
        Role cRole = roleService.findOne(cId);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (User user : userService.findListWithParams(params,index,pageSize,String.valueOf(cId))) {
            if (!user.getRoles().contains(cRole)) continue;
            Map<String, Object> map = new HashMap<>();
            map.put("id", user.getId());
            map.put("name", user.getName());
            map.put("loginName", user.getLoginName());
            map.put("email", user.getEmail());
            map.put("registerDate", user.getRegisterDate().toString());
            map.put("company", user.getCompanyName());
            if (user.getRole() != null)
                map.put("role", user.getRole().getRoleName());
            list.add(map);
        }
        return  SystemUtil.createPageInfoMap(pageNo, pageSize, userService.findCountWithParams(params, String.valueOf(cId)), list);

    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> view(@RequestParam(value = "id") Long id) {
        User user = userService.getUser(id);
        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("name", user.getName());
        map.put("loginName", user.getLoginName());
        map.put("email", user.getEmail());
        map.put("registerDate", user.getRegisterDate());
        map.put("company", user.getCompanyName());
        if (user.getRole() != null)
            map.put("role", user.getRole().getId());

        return map;
    }

    /*@RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String view(@RequestParam(value = "id", required = false, defaultValue = "0") Long id, Model model) {
        User user = userService.getUser(id);
        if (user != null)
            model.addAttribute("user", user);

        return "/system/biz_user_edit";
    }
*/
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public int edit(@RequestParam(value = "id", required = false, defaultValue = "0") Long id, @RequestParam("params") String params) {

        if (id > 0) {
            userService.saveUser(params);
        } else {

            JSONObject jsonObject = JSONObject.fromObject(params);
            String loginName = jsonObject.getString("loginName");//
            String name = jsonObject.getString("name");//
            String password = "";//
            if (jsonObject.containsKey("password"))
                password = jsonObject.getString("password");
            String email = jsonObject.getString("email");//
            Long roleId = jsonObject.getLong("role");//
            Role role = roleService.getRole(roleId);
            if (role == null) return 0;
            userService.registerUser(role, loginName, name, password, email);
        }
        return 1;//"";
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public Boolean delete(@RequestParam(value = "params") String ids) {
        try {
			userService.deleteUser(ids);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
        return false;
    }
}
