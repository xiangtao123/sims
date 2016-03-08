package com.jsrush.security.admin.test;

import java.util.List;

import net.sf.json.JSONArray;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.jsrush.security.admin.entity.TestEntity;
import com.jsrush.security.admin.service.TestEntityService;
import com.jsrush.security.admin.util.PageInfo;
import com.jsrush.security.base.BaseTest;

@TransactionConfiguration(defaultRollback=true)
public class TestEntityServiceTest extends BaseTest {

	@Autowired
	private TestEntityService testEntityService;
	
	@Test
	public void testSave(){
		testEntityService.save("mybatis save");
		
		for (int i=0; i < 20; i++) {
			testEntityService.save("mybatis-"+i);
		}
	}
	
	@Test
	public void find(){
		List<TestEntity> testEntitys = testEntityService.findByTextLike("0");
		Assert.assertNotNull(testEntitys);
		logger.info("find : "+JSONArray.fromObject(testEntitys).toString());
	}
	
	@Test
	public void findPageList(){
		PageInfo pageInfo=new PageInfo();
		pageInfo.setPageNo(1);
		pageInfo.setPageSize(20);

		TestEntity testEntity = new TestEntity();
		testEntity.setText(null);
		List<TestEntity> testEntitys = testEntityService.findPageList(pageInfo, testEntity);
		Assert.assertNotNull(testEntitys);
		logger.info("find page : total="+pageInfo.getTotal()+", rows = "+JSONArray.fromObject(testEntitys).toString());
	}
}
