package com.jsrush.bizlog.service;

import java.util.List;
import java.util.Set;

import ognl.Ognl;
import ognl.OgnlException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jsrush.bizlog.annotation.WithColumn;
import com.jsrush.bizlog.dto.BizActionLogDTO;
import com.jsrush.bizlog.entity.BizActionLog;
import com.jsrush.bizlog.repository.BizActionLogDao;
import com.jsrush.security.rbac.entity.Role;
import com.jsrush.security.rbac.repository.RoleDao;
import com.jsrush.security.rbac.service.ShiroManager;
import com.jsrush.util.DateUtil;
import com.jsrush.util.StringHelper;

@Component
@Transactional(readOnly=true)
public class BizActionLogService {

	private static Logger log = LoggerFactory.getLogger(BizActionLogService.class);
	
	@Autowired
	private BizActionLogDao bizActionLogDao;
	
	@Autowired
	private ShiroManager shiroManager;
	
	@Autowired
	private RoleDao roleDao;
	
	@Transactional(readOnly=false)
	public void save(BizActionLog entity) {
		entity.setCreateTime(DateUtil.getNowTimestamp());
		log.info("saving bizActionLong info ... " + entity.toString());
		bizActionLogDao.save(entity);
	}
	
	/**
	 * 设置当前登陆者的信息及权限信息
	 * @param entity
	 */
	public void setEntityUserInfo(BizActionLog entity){
		Set<Long> aboveRoleId = shiroManager.getCurrentAboveRoleId();
		Long[] permissions = aboveRoleId.toArray(new Long[aboveRoleId.size()]);
		entity.getRoles().clear();
		for (Long rId : permissions) {
			Role rl = roleDao.findOne(rId);
			if (rl == null)
				continue;
			entity.getRoles().add(rl);
		}
		
		entity.setCreatorId(shiroManager.getCurrentUserId());
		entity.setCreatorName(shiroManager.getCurrentUserName());
	}
	
	public String findFieldByHql(String hql, Object[] params){
		List<String> list = bizActionLogDao.findFieldByHql(hql, params); 
		return  StringHelper.join(list, ",");
	}

	public String formatBizContent(BizActionLogDTO bizActionLogDTO) throws OgnlException{
		StringBuilder bizInfo = new StringBuilder();
		StringBuilder bizInfoHql = bizActionLogDTO.getFindBizInfoHql();
		WithColumn[] withColumns = bizActionLogDTO.getWithColumns();
		List<Object> list = bizActionLogDao.findFieldByHql(bizInfoHql.toString());
		for (Object entity : list){
			for (int i = 0; i < withColumns.length; i++){
				WithColumn wc = withColumns[i];
				Object value = Ognl.getValue(wc.name(), entity);
				bizInfo.append(wc.text()).append(wc.joinor()).append(value);
				if (i < withColumns.length -1)
					bizInfo.append(wc.splitor());
				else
					bizInfo.append(bizActionLogDTO.getTableSplitor());
			}
		}
		return bizInfo.toString();
	}
	
	public List<BizActionLog> findListWithParams(String params, int pageNo, int pageSize, String permissions){
		return bizActionLogDao.getListWithParams(params, pageNo, pageSize, permissions);
	}
	
	public Long	findCountWithParams(String params, String permissions){
		return bizActionLogDao.getCountWithParams(params, permissions);
	}
	
}
