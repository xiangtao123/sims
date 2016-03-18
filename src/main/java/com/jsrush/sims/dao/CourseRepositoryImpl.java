package com.jsrush.sims.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.jsrush.sims.entity.Course;
import com.jsrush.sims.entity.Dept;
import com.jsrush.util.SystemUtil;


public class CourseRepositoryImpl implements CourseRepositoryCustom {

private static final String COMMON_SQL = " select t from Course t ";
	
	private static final String COMMON_COUNT_SQL = " select count(t.id) from Course t ";
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> findPageList(Course condition, Integer pageNo, Integer pageSize) {
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
	
	private String makeWhere(Course condition) {
		StringBuilder hql = new StringBuilder();
		if (null != condition.getEcId() && 0 < condition.getEcId()) {
			hql.append(" where t.ecId = :ecId ");
		}
		if (StringUtils.isNotBlank(condition.getName())) {
			hql.append(" and t.name like :name ");
		}
		if (StringUtils.isNotBlank(condition.getMaterial())) {
			hql.append(" and t.material = :material ");
		}
		return  hql.toString();
	}
	
	private void setQueryParameter(Query query, Course condition) {
		if (null != condition.getEcId() && 0 < condition.getEcId()) {
			query.setParameter("ecId", condition.getEcId());
		}
		if (StringUtils.isNotBlank(condition.getName())) {
			query.setParameter("name", condition.getName());
		}
		if (StringUtils.isNotBlank(condition.getMaterial())) {
			query.setParameter("material", condition.getMaterial());
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<Long, String>> findListByEcId(Long ecId) {
		StringBuilder hql = new StringBuilder("  select t.id, t.name from Course t ");
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
