package com.jsrush.sims.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.jsrush.sims.entity.Speciality;
import com.jsrush.util.SystemUtil;

public class SpecialityRepositoryImpl implements SpecialityRepositoryCustom {

	private static final String COMMON_SQL = " select t from Speciality t ";
	
	private static final String COMMON_COUNT_SQL = " select count(t.id) from Speciality t ";
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> findPageList(Speciality condition, Integer pageNo, Integer pageSize) {
		String whereSql = makeWhere(condition);
		
		StringBuilder countHql = new StringBuilder(COMMON_COUNT_SQL);
		countHql.append(whereSql);
		Query countQuery = em.createQuery(countHql.toString());
		setQueryParameter(countQuery, condition);
		Long totalCount = (Long) countQuery.getSingleResult();
		List<Object> resultList = new ArrayList<Object>();
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
	
	private String makeWhere(Speciality condition) {
		StringBuilder hql = new StringBuilder();
		if (null != condition.getEcId() && 0 < condition.getEcId()) {
			hql.append(" where t.ecId = :ecId ");
		}
		if (null != condition.getDeptId() && 0 < condition.getDeptId()) {
			hql.append(" and t.dept.id = :deptId ");
		}
		if (StringUtils.isNotBlank(condition.getCode())) {
			hql.append(" and t.code = :code ");
		}
		if (StringUtils.isNotBlank(condition.getName())) {
			hql.append(" and t.name like :name ");
		}
		return  hql.toString();
	}
	
	private void setQueryParameter(Query query, Speciality condition) {
		if (null != condition.getEcId() && 0 < condition.getEcId()) {
			query.setParameter("ecId", condition.getEcId());
		}
		if (null != condition.getDeptId() && 0 < condition.getDeptId()) {
			query.setParameter("deptId", condition.getDeptId());
		}
		if (StringUtils.isNotBlank(condition.getCode())) {
			query.setParameter("code", condition.getCode());
		}
		if (StringUtils.isNotBlank(condition.getName())) {
			query.setParameter("name", "%" + condition.getName() + "%");
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<Long, String>> findListByEcId(Long ecId) {
		StringBuilder hql = new StringBuilder("  select t.id, t.name from Speciality t ");
		if (ecId != null) {
			hql.append(" where t.ecId = :ecId ");
		}
		Query listQuery = em.createQuery(hql.toString());
		if (ecId != null) {
			listQuery.setParameter("ecId", ecId);
		}
		List<Map<Long, String>> resultList = listQuery.getResultList();
		return resultList;
	}
	
}
