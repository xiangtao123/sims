package com.jsrush.security.rbac.service;

import com.jsrush.security.rbac.entity.Ec;
import com.jsrush.security.rbac.entity.Role;
import com.jsrush.security.rbac.entity.User;
import com.jsrush.security.rbac.listener.OnlineUserListener;
import com.jsrush.security.rbac.realm.ShiroDbRealm.ShiroUser;
import com.jsrush.security.rbac.repository.UserDao;
import com.jsrush.util.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * shiro manager
 * 获取当前登陆者的信息
 *
 * @author sunburst
 */
@Component
@Transactional(readOnly = true)
public class ShiroManager {

    @Autowired
    private UserDao userDao;

    public Integer getOnlineUser(HttpServletRequest request) {
        return (Integer) request.getSession().getServletContext().getAttribute(OnlineUserListener.SESSION_USERCOUNT_KEY);
    }

    /**
     * 获取当前登录者角色级别
     *
     * @return 1 : 系统管理员 ,2 ： 企业管理员, 3 ： 企业普通管理员
     */
    public int getCurrentRoleLevel() {
        ShiroUser currentUser = getCurrentUser();
        if (currentUser == null)
            return -1;
        return currentUser.getRoleLevel();
    }


    /**
     * 检查资源权限
     *
     * @param resStr
     * @return
     */
    public boolean hasPermission(String resStr) {
        Set<String> permissions = getPermissions();
        if (CollectionUtils.isNotEmpty(permissions)) {
            return getCurrentUser().getPermissions().contains(resStr);
        }
        return false;
    }

    public Set<String> getPermissions() {
        return getCurrentUser().getPermissions();
    }

    /**
     * 根据登陆名查询系统用户
     *
     * @param loginName
     * @return
     */
    public User findUserByLoginName(String loginName) {
        return userDao.findByLoginName(loginName);
    }

    /**
     * 获取当前登陆者的角色和上级角色的roleId collections.
     *
     * @param roleIds
     * @param role
     */
    public void findAboveRoleIds(Set<Long> roleIds, Role role) {
        if (role == null)
            return;
        roleIds.add(role.getId());
        findAboveRoleIds(roleIds, role.getParentRole());
    }

    /**
     * 获取当前登陆者的角色和上级角色的roleId collections(剔除本企业的角色)
     *
     * @param roleIds
     * @param role
     */
    public void findAboveRoleIds(Set<Long> roleIds, Role role, Long ecId) {
        if (role == null)
            return;
        Ec ec = role.getEc();
        if (ec != null && ec.getId().longValue() == ecId.longValue()) {
            roleIds.add(role.getId());
            findAboveRoleIds(roleIds, role.getParentRole(), ecId);
        }
    }

    /**
     * 获取当前登陆者的角色和下级角色的roleId collections.
     *
     * @param roleIds
     * @param role
     */
    public void findLowerRoleIds(Set<Long> roleIds, Role role) {
        if (role == null)
            return;
        roleIds.add(role.getId());
        Set<Role> childRole = role.getChildRole();
        Role[] roles = childRole.toArray(new Role[childRole.size()]);
        for (int i = 0; i < roles.length; i++)
            findLowerRoleIds(roleIds, roles[i]);
    }

    /**
     * 获取当前登陆者
     *
     * @return
     */
    public ShiroUser getCurrentUser() {
        return (ShiroUser) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获取当前登陆者的角色roleId
     *
     * @return
     */
    public Long getCurrentRoleId() {
        return getCurrentUser().getRoleId();
    }

    public String getCurrentRoleIdStr() {
        Long roleId = getCurrentRoleId();
        return roleId.longValue() + "";
    }

    /**
     * 获取当前登陆者的角色和上级角色的roleId
     *
     * @return
     */
    public Set<Long> getCurrentAboveRoleId() {
        return getCurrentUser().getAboveRoleId();
    }

    public String getCurrentAboveRoleId(String splitor) {
        return StringHelper.joinIds(getCurrentAboveRoleId(), StringHelper.isEmpty(splitor) ? "," : splitor);
    }

    public Long[] getCurrentAboveRoleIdArray() {
        Set<Long> aboveRoleId = getCurrentAboveRoleId();
        Long[] roleId = aboveRoleId.toArray(new Long[aboveRoleId.size()]);
        return roleId;
    }

    public List<Long> getCurrentAboveRoleIdList() {
        Set<Long> set = getCurrentAboveRoleId();
        List<Long> list = new ArrayList<Long>();
        for (Long id : set) {
            list.add(id);
        }
        return list;
    }


    /**
     * 获取当前登陆者的角色和上级角色的roleId(同企业角色)
     *
     * @return
     */
    public Set<Long> getCurrentAboveRoleIdSameEc() {
        return getCurrentUser().getAboveRoleIdSameEc();
    }

    public String getCurrentAboveRoleIdSameEc(String splitor) {
        return StringHelper.joinIds(getCurrentAboveRoleIdSameEc(), StringHelper.isEmpty(splitor) ? "," : splitor);
    }

    public Long[] getCurrentAboveRoleIdArraySameEc() {
        Set<Long> aboveRoleIdSameEc = getCurrentAboveRoleIdSameEc();
        Long[] roleId = aboveRoleIdSameEc.toArray(new Long[aboveRoleIdSameEc.size()]);
        return roleId;
    }

    /**
     * 获取当前登陆者的角色和下级角色的roleId
     *
     * @return
     */
    public Set<Long> getCurrentLowerRoleIds() {
        return getCurrentUser().getLowerRoleId();
    }

    public String getCurrentLowerRoleIds(String splitor) {
        return StringHelper.joinIds(getCurrentLowerRoleIds(), StringHelper.isEmpty(splitor) ? "," : splitor);
    }

    public Long[] getCurrentLowerRoleIdsArray() {
        Set<Long> lowerRoleId = getCurrentLowerRoleIds();
        Long[] roleId = lowerRoleId.toArray(new Long[lowerRoleId.size()]);
        return roleId;
    }

    /**
     * 获取当前登陆者的企业的ecId
     *
     * @return
     */
    public Long getCurrentUserEcId() {
        return getCurrentUser().getEcId();
    }

    /**
     * 获取当前登陆者的企业名称ecName
     *
     * @return
     */
    public String getCurrentUserEcName() {
        return getCurrentUser().getCorpName();
    }

    /**
     * 获取当前登陆者的id.
     */
    public Long getCurrentUserId() {
        return getCurrentUser().id;
    }

    /**
     * 获取当前登陆者的name.
     */
    public String getCurrentUserName() {
        return getCurrentUser().name;
    }

}
