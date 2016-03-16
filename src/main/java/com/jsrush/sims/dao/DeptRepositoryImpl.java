package com.jsrush.sims.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.jsrush.sims.entity.Dept;
import com.jsrush.util.SystemUtil;

public class DeptRepositoryImpl implements DeptRepositoryCustom {

	private static final String COMMON_SQL = " select t from Dept t ";
	
	private static final String COMMON_COUNT_SQL = " select count(t.id) from Dept t ";
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> findPageList(Dept condition, Integer pageNo, Integer pageSize) {
		String whereSql = makeWhere(condition);
		
		StringBuilder countHql = new StringBuilder(COMMON_COUNT_SQL);
		countHql.append(whereSql);
		Query countQuery = em.createQuery(countHql.toString());
		setQueryParameter(countQuery, condition);
		Long totalCount = (Long) countQuery.getSingleResult();
		List<Dept> resultList = new ArrayList<Dept>();
		if (totalCount != null && totalCount.intValue() > 0) {
			StringBuilder listHql = new StringBuilder(COMMON_SQL);
			listHql.append(whereSql);
			Query listQuery = em.createQuery(listHql.toString());
			setQueryParameter(listQuery, condition);
			listQuery.setFirstResult(SystemUtil.firstNo(pageNo, pageSize));
			listQuery.setMaxResults(pageSize);
			resultList = listQuery.getResultList();
		}
		return SystemUtil.createPageInfoMap(pageNo, pageSize, totalCount, resultList);
	}
	
	private String makeWhere(Dept condition) {
		StringBuilder hql = new StringBuilder();
		if (null != condition.getEcId() && 0 < condition.getEcId()) {
			hql.append(" where t.ecId = :ecId ");
		}
		if (StringUtils.isNotBlank(condition.getType())) {
			hql.append(" and t.type = :type ");
		}
		if (StringUtils.isNotBlank(condition.getDeptName())) {
			hql.append(" and t.deptName like :deptName ");
		}
		return  hql.toString();
	}
	
	private void setQueryParameter(Query query, Dept condition) {
		if (null != condition.getEcId() && 0 < condition.getEcId()) {
			query.setParameter("ecId", condition.getEcId());
		}
		if (StringUtils.isNotBlank(condition.getType())) {
			query.setParameter("deptName", "%" + condition.getDeptName() + "%");
		}
		if (StringUtils.isNotBlank(condition.getDeptName())) {
			query.setParameter("type", condition.getType());
		}
	}
	
}
