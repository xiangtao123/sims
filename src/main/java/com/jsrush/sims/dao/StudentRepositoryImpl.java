package com.jsrush.sims.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.jsrush.sims.entity.Dept;
import com.jsrush.sims.entity.Student;
import com.jsrush.util.SystemUtil;

public class StudentRepositoryImpl implements StudentRepositoryCustom {

	private static final String COMMON_SQL = " select t from Student t ";
	
	private static final String COMMON_COUNT_SQL = " select count(t.id) from Student t ";
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> findPageList(Student condition, Integer pageNo, Integer pageSize) {
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
	
	private String makeWhere(Student condition) {
		StringBuilder hql = new StringBuilder();
		if (null != condition.getEcId() && 0 < condition.getEcId()) {
			hql.append(" where t.ecId = :ecId ");
		}
		if (null != condition.getSpecialityId() && 0 < condition.getSpecialityId()) {
			hql.append(" and t.specialityId = :specialityId ");
		}
		if (StringUtils.isNotBlank(condition.getStudentNo())) {
			hql.append(" and t.studentNo = :studentNo ");
		}
		if (StringUtils.isNotBlank(condition.getName())) {
			hql.append(" and t.name like :name ");
		}
		if (StringUtils.isNotBlank(condition.getIdNo())) {
			hql.append(" and t.idNo = :idNo ");
		}
		return  hql.toString();
	}
	
	private void setQueryParameter(Query query, Student condition) {
		if (null != condition.getEcId() && 0 < condition.getEcId()) {
			query.setParameter("ecId", condition.getEcId());
		}
		
		if (null != condition.getSpecialityId() && 0 < condition.getSpecialityId()) {
			query.setParameter("specialityId", condition.getSpecialityId());
		}
		
		if (StringUtils.isNotBlank(condition.getStudentNo())) {
			query.setParameter("studentNo", condition.getStudentNo());
		}
		if (StringUtils.isNotBlank(condition.getName())) {
			query.setParameter("name", "%" + condition.getName() + "%");
		}
		if (StringUtils.isNotBlank(condition.getIdNo())) {
			query.setParameter("idNo", condition.getIdNo());
		}
	}

	@Override
	public void updateStudent(Student dto, Set<Long> studentIds) {
		StringBuilder hql = new StringBuilder(" update Student t set t.auditState = :auditState, t.auditTime = :auditTime, t.auditRemark = :auditRemark ");
		hql.append(" where t.id in (:studentIds) ");
		
		Query query = em.createQuery(hql.toString());
		query.setParameter("auditState", dto.getAuditState());
		query.setParameter("auditTime", dto.getAuditTime());
		query.setParameter("auditRemark", dto.getAuditRemark());
		query.setParameter("studentIds", studentIds);
		
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Student findByUserId(Long userId) {
		StringBuilder hql = new StringBuilder(COMMON_SQL);
		hql.append(" where t.userId = :userId ");
		Query query = em.createQuery(hql.toString());
		query.setParameter("userId", userId);
		Student student = null;
		List<Student> resultList = query.getResultList();
		if (CollectionUtils.isNotEmpty(resultList)) {
			student = resultList.get(0);
		}
		return student;
	}
	
	
}
