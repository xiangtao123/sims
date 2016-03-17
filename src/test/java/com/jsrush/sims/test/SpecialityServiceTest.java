package com.jsrush.sims.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.alibaba.fastjson.JSONObject;
import com.jsrush.security.base.BaseTest;
import com.jsrush.sims.entity.Speciality;
import com.jsrush.sims.service.SpecialityService;

/**
 * 专业
 * 
 * @author sunburst
 */
@TransactionConfiguration(defaultRollback=true)
public class SpecialityServiceTest extends BaseTest {

	@Autowired
	private SpecialityService specialityService;
	
	@Test
	public void testFindPageList() {
		Speciality condtion=new Speciality();
		condtion.setEcId(1L);
		Integer pageNo=1;
		Integer pageSize=10;
		Map<String, Object> pageMap = specialityService.findPageList(condtion, pageNo, pageSize);
		Assert.assertNotNull(pageMap);
		logger.info(JSONObject.toJSONString(pageMap));
	}
	
	@Test
	public void testSaveOrUpdate() {
		Speciality dto = new Speciality();
		dto.setName("计算机科学与技术");
		dto.setCode("0010011001");
		dto.setDegreeType("工学");
		dto.setLength(4);
		dto.setOptionCredit(23);
		dto.setRequireCredit(57);
		dto.setEcId(1L);
		dto.setDeptId(3L);
		specialityService.saveOrUpdate(dto);
	}
	
	@Test
	public void testDelete() {
		List<Long> ids = new ArrayList<Long>();
		ids.add(1L);
		specialityService.delete(ids);
	}
	
	
}
