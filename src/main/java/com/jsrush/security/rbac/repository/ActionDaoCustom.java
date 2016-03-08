package com.jsrush.security.rbac.repository;

import java.util.List;

import com.jsrush.security.rbac.entity.Action;


public interface ActionDaoCustom {

	List<Action> loadTree(String permission);
	
	/**
	 *  根据当前登录者权限加载菜单数据
	 * @param permission
	 * @return
	 */
	List<Action> loadMenuTree(String permission);

	/**
	 * 根据KeyName查询系统资源
	 * @param keyName
	 * @return
	 */
	List<Action> findListByKeyName(String keyName);
	
	/**
	 * 查询系统资源根节点
	 * @return
	 */
	List<Action> findRootAction();
	
	/**
	 * 查询子节点 按 顺序 排序
	 * @param parentId
	 * @return
	 */
	List<Action> loadChildNodeByParent(Long parentId);
	
}
