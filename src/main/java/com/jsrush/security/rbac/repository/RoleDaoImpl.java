package com.jsrush.security.rbac.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;

import net.sf.json.JSONObject;

import com.jsrush.security.rbac.entity.Role;
import com.jsrush.util.StringHelper;
import com.jsrush.util.SystemUtil;

public class RoleDaoImpl implements RoleDaoCustom {

	
	private static final String COMMON_SQL = " select distinct r from Role as r ";

	private static final String COMMON_COUNT_SQL = " select count(distinct r.id) from Role as r  ";

	private static final String COMM_LOADTREE_SQL = " select distinct r from Role r  inner join r.roles as pr  ";

	private static final String COMM_GETBYPID_SQL = " select distinct r from Role r where r.parentRole.id = ";
	
	private static final String COMM_GETNULLPID_SQL = " select distinct r from Role r where r.parentRole.id  ";
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Role> getListWithParams(String params, int pageNo, int pageSize, String permissions) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(makeWhere(params, permissions));
		hql.append(" order by r.id desc ");
		Query query = em.createQuery(hql.toString());
		query.setFirstResult((pageNo - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	public Long getCountWithParams(String params, String permissions) {
		StringBuilder hql = new StringBuilder(COMMON_COUNT_SQL);
		hql.append(makeWhere(params, permissions));
		Query query = em.createQuery(hql.toString());
		return (Long) query.getSingleResult();
	}

	private StringBuilder makeWhere(String params, String permissions) {
		StringBuilder hql = new StringBuilder();
		hql.append(" inner join r.roles as pr  where pr.id in (").append(permissions).append(") ");
		
		JSONObject param = SystemUtil.fromObject(params);
		if (param == null)
			return hql;
		if (StringHelper.isNotEmpty(param.getString("roleName"))) {
			hql.append(" where r.roleName like '%");
			hql.append(param.getString("roleName"));
			hql.append("%' ");
		}
		return hql;
	}
	
	@SuppressWarnings("unchecked")
	public List<Role> loadTree(String permissions) {
		if (StringHelper.isEmpty(permissions))
			return new ArrayList<Role>();
		StringBuilder hql = new StringBuilder(COMM_LOADTREE_SQL);
		hql.append(" where pr.id in (").append(permissions).append(") ");
		Query query = em.createQuery(hql.toString());
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Role> getRoleByPId(Long pId) {
		StringBuilder hql = new StringBuilder(COMM_GETBYPID_SQL);
		hql.append(pId);
		Query query = em.createQuery(hql.toString());
		return query.getResultList();
	}
	
	/*
	 * 查找数据库中最大role节点，即pId == null
	 * 暂定数据库中只有一个pId为null的记录
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Role getNullpId(){
		Role role = null;
		StringBuilder hql = new StringBuilder(COMM_GETNULLPID_SQL);
		hql.append(" is null ");
		Query query = em.createQuery(hql.toString());
		List<Role> list = query.getResultList();
		if (CollectionUtils.isNotEmpty(list))
			role = list.get(0);
		return role;
		
	}

}
