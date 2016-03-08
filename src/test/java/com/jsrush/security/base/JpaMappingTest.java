package com.jsrush.security.base;

import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.junit.Test;

import com.jsrush.util.LogUtil;

public class JpaMappingTest extends BaseTest {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("rawtypes")
	@Test
	public void allClassMapping() throws Exception {
		Metamodel model = em.getEntityManagerFactory().getMetamodel();

		assertTrue("No entity mapping found", model.getEntities().size() > 0);

		for (EntityType entityType : model.getEntities()) {
			String entityName = entityType.getName();
			logger.info("xxxxxxxxxxxxxxxxxxxxx start: " + entityName);
			try {
				em.createQuery("select o from " + entityName + " o").getResultList();
			} catch (Exception e) {
				logger.error(e.getMessage()+" entityName is :" + entityName);
				logger.error(LogUtil.stackTraceToString(e));
				throw e;
			}
			logger.info("xxxxxxxxxxxxxxxxxxxxx finish: " + entityName);
		}
		
	}
}
