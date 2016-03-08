package com.jsrush.security.rbac;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.jsrush.exception.BizException;
import com.jsrush.security.base.BaseTest;
import com.jsrush.security.rbac.entity.Action;
import com.jsrush.security.rbac.entity.Role;
import com.jsrush.security.rbac.repository.ActionDao;
import com.jsrush.security.rbac.repository.RoleDao;
import com.jsrush.security.rbac.service.ActionService;
import com.jsrush.security.rbac.vo.ActionDTO;
import com.jsrush.util.FileUtil;

@TransactionConfiguration(defaultRollback=true)
public class ActionServiceTest extends BaseTest {

	@Autowired
	private ActionService actionService;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private ActionDao actionDao;
	
	@Test
	public void testLoadMenuTree(){
		String permissions = "1L";
		List<Map<String,Object>> menuTree = actionService.loadMenuTree(permissions);
		logger.info(menuTree);
	}
	
	
	@Test
	public void testInitMenuTree() {
		String dataFilePath = "/src/test/resources/datafile/menu_data.json";
		String dataStr = FileUtil.readFileText(dataFilePath);
		logger.info("dataStr is :\n"+dataStr);
		Assert.assertNotEquals("", dataStr);
		
		List<Long> roleIds = new ArrayList<Long>();
		Role rootRole = roleDao.getNullpId();
		
		roleIds.add(rootRole.getId());
		
		Long rootActionId = null;
		List<Action> rootActions = actionDao.findRootAction();
		if (CollectionUtils.isEmpty(rootActions)) {
			ActionDTO rootDTO = new ActionDTO();
			rootDTO.setKey("page:welcome");
			rootDTO.setName("登录");
			rootDTO.setpId(null);
			rootDTO.setTitle("登录系统");
			rootDTO.setType("gateway");
			try {
				rootActionId = actionService.saveOrUpdate(rootDTO, roleIds);
			} catch (BizException e) {
				e.printStackTrace();
			}
		}
		
		HashMap<Long, Long> idsMap = new HashMap<Long, Long>();
		JSONArray jsonArray = JSONArray.fromObject(dataStr);
		for (int i=0, len=jsonArray.size(); i < len; i++) {
			JSONObject jsonObj = jsonArray.getJSONObject(i);
			logger.info("convert to dto is : \n"+jsonObj.toString());
			ActionDTO dto = (ActionDTO) JSONObject.toBean(jsonObj, ActionDTO.class);
			
			Long dtoId = dto.getId();
			Long dtopId = dto.getpId();
			dto.setId(null);
			if (dtopId.longValue() == 0) {
				dto.setpId(rootActionId);
			} else {
				if (idsMap.containsKey(dtopId)) {
					dto.setpId(idsMap.get(dtopId));
				}
			}
			logger.info("save dtos is : \n"+JSONArray.fromObject(dto).toString());
			try {
				Long entityId = actionService.saveOrUpdate(dto, roleIds);
				idsMap.put(dtoId, entityId);
			} catch (BizException e) {
				e.printStackTrace();
			}
			
		}
		
		testLoadMenuTree();
	}
	
}
