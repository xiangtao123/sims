package com.jsrush.security.rbac.repository;

import com.jsrush.security.rbac.entity.User;
import com.jsrush.util.StringHelper;
import com.jsrush.util.SystemUtil;
import net.sf.json.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class UserDaoImpl implements UserDaoCustom {


	private static final String COMMON_SQL = " select distinct u from User as u ";

	private static final String COMMON_COUNT_SQL = " select count(distinct u.id) from User as u  ";

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<User> getListWithParams(String params, int index, int pageSize, String permissions) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(makeWhere(params, permissions));
		hql.append(" order by u.id desc ");
		Query query = em.createQuery(hql.toString());
		query.setFirstResult(index);
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
		hql.append(" inner join u.roles as pr where pr.id in (").append(permissions).append(") ");

		JSONObject param = SystemUtil.fromObject(params);
		if (param == null)
			return hql;
		if (param.containsKey("name") && StringHelper.isNotEmpty(param.getString("name"))) {
			hql.append(" and u.name like '%");
			hql.append(param.getString("name"));
			hql.append("%' ");
		}
		if (param.containsKey("loginName") && StringHelper.isNotEmpty(param.getString("loginName"))) {
			hql.append(" and u.loginName like '%");
			hql.append(param.getString("loginName"));
			hql.append("%' ");
		}if (param.containsKey("role") && StringHelper.isNotEmpty(param.getString("role"))) {
			hql.append(" and u.role = ");
			hql.append(param.getString("role"));
			hql.append(" ");
		}
		if (param.containsKey("mail") && StringHelper.isNotEmpty(param.getString("mail"))) {
			hql.append(" and u.mail like '%");
			hql.append(param.getString("mail"));
			hql.append("%' ");
		}

		return hql;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> findListByLoginId(String loginIds) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" where u.loginName in (").append(loginIds).append(") ");
		Query query = em.createQuery(hql.toString());
		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> loadList(String permissions) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" inner join u.roles as pr where pr.id in (").append(permissions).append(") ");
		Query query = em.createQuery(hql.toString());
		return query.getResultList();
	}


}
