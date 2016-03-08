package com.jsrush.security.rbac.repository;

import com.jsrush.security.rbac.entity.Ec;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.List;

public class EcDaoImpl implements EcDaoCustom {


	@PersistenceContext
	private EntityManager manager;

    @SuppressWarnings("unchecked")
    @Override
    public List<Ec> findByCorpNameLike(String name, int index, int pageSize) {
        return manager.createNativeQuery( "SELECT ec.* FROM t_jsrush_ec ec WHERE ec.corp_name LIKE ?1", Ec.class)
            .setParameter(1, "%" + name + "%").setFirstResult(index).setMaxResults(pageSize).getResultList();
    }
    @Override
    public int findCountByCorpNameLike(String name) {
        return ((BigInteger) manager.createNativeQuery( "SELECT count(distinct ec.id) FROM t_jsrush_ec ec WHERE ec.corp_name LIKE ?1")
            .setParameter(1, "%" + name + "%").getSingleResult()).intValue();
    }

}
