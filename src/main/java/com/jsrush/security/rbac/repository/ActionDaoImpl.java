package com.jsrush.security.rbac.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.jsrush.security.rbac.entity.Action;

public class ActionDaoImpl implements ActionDaoCustom {

	private static final String COMM_LOADTREE_SQL = " select distinct a from Action a ";
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Action> loadTree(String permission) {
		StringBuilder hql = new StringBuilder(COMM_LOADTREE_SQL);
		hql.append(" inner join a.roles as r where r.id in ( ").append(permission).append(" ) order by a.idx asc ");
		Query query = em.createQuery(hql.toString());
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Action> loadChildNodeByParent(Long parentId) {
		StringBuilder hql = new StringBuilder(COMM_LOADTREE_SQL);
		hql.append(" where a.action.id = ").append(parentId).append(" order by a.idx asc ");
		Query query = em.createQuery(hql.toString());
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Action> loadMenuTree(String permission) {
		StringBuilder hql = new StringBuilder(COMM_LOADTREE_SQL);
		hql.append(" inner join a.roles as r where r.id in ( ").append(permission).append(" ) ");
		hql.append(" and (a.type = 'page' or a.type='module') order by a.idx asc ");
		Query query = em.createQuery(hql.toString());
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Action> findListByKeyName(String keyName) {
		StringBuilder hql = new StringBuilder(COMM_LOADTREE_SQL);
		hql.append(" where a.actionName = '").append(keyName).append("' ");
		Query query = em.createQuery(hql.toString());
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Action> findRootAction() {
		StringBuilder hql = new StringBuilder(COMM_LOADTREE_SQL);
		hql.append(" where a.action.id is null ");
		Query query = em.createQuery(hql.toString());
		return query.getResultList();
	}
	
}
