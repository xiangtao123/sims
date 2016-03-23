package com.jsrush.sims.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.jsrush.sims.dto.StudentCourseCondition;
import com.jsrush.sims.entity.StudentCourse;
import com.jsrush.util.SystemUtil;

public class StudentCourseRepositoryImpl implements StudentCourseRepositoryCustom {

	private static final String COMMON_LIST_SQL = " select t from StudentCourse t  join t.student student  join t.course course ";
	private static final String COMMON_COUNT_SQL = " select count(t.id) from StudentCourse t  join t.student student  join t.course course ";
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> findPageList(StudentCourseCondition condition, Integer pageNo, Integer pageSize) {
		String whereSql = makeWhere(condition);
		
		StringBuilder countHql = new StringBuilder(COMMON_COUNT_SQL);
		countHql.append(whereSql);
		Query countQuery = em.createQuery(countHql.toString());
		setQueryParameter(countQuery, condition);
		Long totalCount = (Long) countQuery.getSingleResult();
		List<Object> resultList = new ArrayList<Object>();
		if (totalCount != null && totalCount.intValue() > 0) {
			StringBuilder listHql = new StringBuilder(COMMON_LIST_SQL);
			listHql.append(whereSql);
			Query listQuery = em.createQuery(listHql.toString());
			setQueryParameter(listQuery, condition);
			listQuery.setFirstResult(SystemUtil.firstNo(pageNo, pageSize));
			listQuery.setMaxResults(pageSize);
			resultList = listQuery.getResultList();
		}
		return SystemUtil.createPageInfoMap(pageNo, pageSize, totalCount, resultList);
	}
	
	private String makeWhere(StudentCourseCondition condition) {
		StringBuilder hql = new StringBuilder();
		hql.append(" where t.ecId = :ecId ");
		if (null != condition.getStudentId()) {
			hql.append(" and student.id = :studentId ");
		}
		if (StringUtils.isNotBlank(condition.getStudentName())) {
			hql.append(" and student.name like :studentName ");
		}
		if (StringUtils.isNotBlank(condition.getStudentNo())) {
			hql.append(" and student.studentNo = :studentNo ");
		}
		if (null != condition.getCourseId()) {
			hql.append(" and course.id = :courseId ");
		}
		if (StringUtils.isNotBlank(condition.getGrade()) ) {
			hql.append(" and t.grade = :grade ");
		}
		
		return  hql.toString();
	}
	
	private void setQueryParameter(Query query, StudentCourseCondition condition) {
		query.setParameter("ecId", condition.getEcId());
		if (null != condition.getStudentId()) {
			query.setParameter("studentId", condition.getStudentId());
		}
		if (StringUtils.isNotBlank(condition.getStudentName())) {
			query.setParameter("studentName", condition.getStudentName());
		}
		if (StringUtils.isNotBlank(condition.getStudentNo())) {
			query.setParameter("studentNo", condition.getStudentNo());
		}
		if (null != condition.getCourseId()) {
			query.setParameter("courseId", condition.getCourseId());
		}
		if (StringUtils.isNotBlank(condition.getGrade()) ) {
			query.setParameter("grade", condition.getGrade());
		}
	}

	@Override
	public List<StudentCourse> findListByStudentIdAndCourseId(Long studentId, Long courseId) {
		StringBuilder hql = new StringBuilder("select  t from StudentCourse t  left join t.student student  left join t.course course ");
		hql.append(" where course.id = :courseId ");
		hql.append(" and student.id = :studentId ");
		
		Query query = em.createQuery(hql.toString());
		query.setParameter("courseId", courseId);
		query.setParameter("studentId", studentId);
		@SuppressWarnings("unchecked")
		List<StudentCourse> resultList = query.getResultList();
		return resultList;
	}
	
}
