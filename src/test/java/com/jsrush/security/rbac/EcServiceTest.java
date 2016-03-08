package com.jsrush.security.rbac;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.jsrush.security.base.BaseTest;
import com.jsrush.security.rbac.entity.Ec;
import com.jsrush.security.rbac.service.EcService;

@TransactionConfiguration(defaultRollback=true)
public class EcServiceTest extends BaseTest {

	@Autowired
	private EcService ecService;
	
	@Test
	public void testListPage() {
		String name = "";
		int pageNo = 0;
		int pageSize = 10;
		List<Ec> list = ecService.findByCorpNameLike(name, pageNo, pageSize);
		int count = ecService.findCountByCorpNameLike(name);

		logger.info(" total count is :" + count);
		for (Ec s : list) {
			logger.info(" entry is: \n" + s);
		}
	}
	
}
