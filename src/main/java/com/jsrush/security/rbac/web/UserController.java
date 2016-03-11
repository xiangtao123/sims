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

/**
 * 用户管理
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
    
	@Autowired
    private UserService userService;
    
	@Autowired
	private RoleService roleService;
    
	@Autowired
	private ShiroManager shiroManager;

	/**
	 * 查看当前系统活动的用户个数
	 * 
	 * @param request
	 * @return
	 */
    @RequestMapping(value = "/onLineList", method = RequestMethod.GET)
    @ResponseBody
    public Integer getOnlineUser(HttpServletRequest request) {
    	return shiroManager.getOnlineUser(request);
    }
     
    
    /**
     * 查询界面用户列表
     * 
     * @param params
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(@RequestParam(value = "params", required = false, defaultValue = "{}") String params,
                                    @RequestParam(defaultValue = "1", value = "page") int pageNo,
                                    @RequestParam(defaultValue = "10", value = "rows") int pageSize) {
        Long cId = shiroManager.getCurrentRoleId();
        int index = SystemUtil.firstNo(pageNo, pageSize);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        
        List<User> users = userService.findListWithParams(params,index,pageSize,String.valueOf(cId));
        for (User user : users) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", user.getId());
            map.put("name", user.getName());
            map.put("loginName", user.getLoginName());
            map.put("email", user.getEmail());
            map.put("registerDate", user.getRegisterDate().toString());
            if (user.getRole() != null)
                map.put("role", user.getRole().getRoleName());
            list.add(map);
        }
        return  SystemUtil.createPageInfoMap(pageNo, pageSize, userService.findCountWithParams(params, String.valueOf(cId)), list);

    }

    /**
     * 根据用户ID查询用户数据
     *  
     * @param id
     * @return
     */
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findById(@RequestParam(value = "id") Long id) {
        User user = userService.getUser(id);
        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("name", user.getName());
        map.put("loginName", user.getLoginName());
        map.put("email", user.getEmail());
        map.put("registerDate", user.getRegisterDate());
        if (user.getRole() != null)
            map.put("role", user.getRole().getId());

        return map;
    }

    /**
     * 新增或修改用户信息
     * 
     * @param id
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public int saveOrUpdate(@RequestParam(value = "id", required = false, defaultValue = "0") Long id, @RequestParam("params") String params) {
        if (id > 0) {
            userService.saveUser(params);
        } else {
            JSONObject jsonObject = JSONObject.fromObject(params);
            String loginName = jsonObject.getString("loginName");
            String name = jsonObject.getString("name");//
            String password = "";
            if (jsonObject.containsKey("password"))
                password = jsonObject.getString("password");
            String email = jsonObject.getString("email");
            Long roleId = jsonObject.getLong("role");
            Role role = roleService.getRole(roleId);
            if (role == null) return 0;
            userService.registerUser(role, loginName, name, password, email);
        }
        return 1;
    }

    /**
     * 删除用户
     * 
     * @param ids
     * @return
     */
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
