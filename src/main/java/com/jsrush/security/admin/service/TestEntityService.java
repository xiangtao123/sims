package com.jsrush.security.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsrush.security.admin.dao.TestEntityDao;
import com.jsrush.security.admin.entity.TestEntity;
import com.jsrush.security.admin.util.PageInfo;

@Service("testEntityService")
@Transactional(readOnly=true)
public class TestEntityService {

	@Autowired
	private TestEntityDao testEntityDao;
	
	@Transactional(readOnly=false)
	public void save(String text) {
		testEntityDao.save(text);
	}
	
	public List<TestEntity> findByTextLike(String text) {
		return testEntityDao.findByTextLike(text);
	}
	
	public TestEntity get(Long id) {
		return testEntityDao.get(id);
	}
	
	public List<TestEntity> findPageList(PageInfo pageInfo, TestEntity testEntity) {
		return testEntityDao.findPageList(testEntity, pageInfo);
	}
}
