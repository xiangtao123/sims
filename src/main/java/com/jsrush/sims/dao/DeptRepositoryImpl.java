package com.jsrush.sims.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.jsrush.sims.entity.Dept;
import com.jsrush.util.SystemUtil;

public class DeptRepositoryImpl implements DeptRepositoryCustom {

	private static final String COMMON_SQL = " select t from Dept t ";
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Dept> findPageList(Dept condition, Integer pageNo, Integer pageSize) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" where t.ecId = :ecId ");
		if (StringUtils.isNotBlank(condition.getType())) {
			hql.append(" and t.type = :type ");
		}
		if (StringUtils.isNotBlank(condition.getDeptName())) {
			hql.append(" and t.deptName like :deptName ");
		}
		Query query = em.createQuery(hql.toString());
		query.setParameter("ecId", condition.getEcId());
		if (StringUtils.isNotBlank(condition.getType())) {
			query.setParameter("deptName", "%" + condition.getDeptName() + "%");
		}
		if (StringUtils.isNotBlank(condition.getDeptName())) {
			query.setParameter("type", condition.getType());
		}
		query.setFirstResult(SystemUtil.firstNo(pageNo, pageSize));
		query.setMaxResults(pageSize);
		return query.getResultList();
	}
	
	
}
