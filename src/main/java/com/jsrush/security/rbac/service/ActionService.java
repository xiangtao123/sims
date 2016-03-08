package com.jsrush.security.rbac.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsrush.exception.BizException;
import com.jsrush.security.rbac.entity.Action;
import com.jsrush.security.rbac.entity.Role;
import com.jsrush.security.rbac.repository.ActionDao;
import com.jsrush.security.rbac.repository.RoleDao;
import com.jsrush.security.rbac.vo.ActionDTO;
import com.jsrush.util.StringHelper;

@Service
@Transactional(readOnly = true)
public class ActionService {

    @Autowired
    private ActionDao actionDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleService roleService;
    
    public List<Map<String, Object>> loadTree(Long currentRoleId) {
        List<Action> actions = actionDao.loadTree(currentRoleId+"");
        List<Map<String, Object>> voList = new ArrayList<Map<String, Object>>();
        for (Action ac : actions) {
            Map<String, Object> acMap = new HashMap<String, Object>();
            acMap.put("name", ac.getActionText());
            acMap.put("id", ac.getId());
            Action parentAc = ac.getAction();
            acMap.put("pId", parentAc == null ? null : parentAc.getId());
            acMap.put("idx", ac.getIdx());
            voList.add(acMap);
        }
        return voList;
    }
    
    /**
     * 加载菜单树
     * @param permissions
     * @return
     */
    public List<Map<String, Object>> loadMenuTree(String permissions) {
        List<Action> actions = actionDao.loadMenuTree(permissions);
        List<Map<String, Object>> voList = new ArrayList<Map<String, Object>>();
        for (Action ac : actions) {
            Map<String, Object> acMap = new HashMap<String, Object>();
            acMap.put("name", ac.getActionText());
            acMap.put("id", ac.getId());
            Action parentAc = ac.getAction();
            if (parentAc == null) {
            	acMap.put("pId", null);
            } else {
            	acMap.put("pId", parentAc.getId());
            	}
            acMap.put("targetUrl", ac.getTargetUrl());
            acMap.put("title", ac.getRemark());
            	
            voList.add(acMap);
           }
        return voList;
    }
    

    public Action findAction(Long id) {
        return actionDao.findOne(id);
    }

    public Action findRootAction() { 
    	List<Action> rootAction = actionDao.findRootAction();
    	if (CollectionUtils.isNotEmpty(rootAction)) {
    		return rootAction.get(0);
    	}
    	return null;
    }
    public boolean checkName(String name) {
        return actionDao.findByActionName(name) == null;
    }

    public boolean checkTitle(String title) {
        return actionDao.findByActionText(title) == null;
    }

    @Transactional(readOnly = false)
    public Long add(Action parent, String name, String title) {
        Action newAction = new Action();
        newAction.setActionName(name);
        newAction.setAction(parent);
        newAction.setActionText(title);
        return actionDao.save(newAction).getId();
    }

    @Transactional(readOnly = false)
    public Long save(Long id, String name, String title, Long pId) {
        Action action = null;
        if (id > 0L) {
            action = actionDao.findOne(id);
        }
        if (action == null) {
            action = new Action();
        }

        if (StringHelper.isNotEmpty(name)) {
            action.setActionName(name);
        }
        if (StringHelper.isNotEmpty(title)) {
            action.setActionText(title);
        }
        if (pId > 0) {
            action.setAction(actionDao.findOne(pId));
        }

        Long rid = actionDao.save(action).getId();
        if (id <= 0) {
            Role admin = roleDao.findOne(1L);
            admin.getAction().add(action);
            roleDao.save(admin);
        }

        return rid;
    }

    public Iterable<Action> findAll() {
        return actionDao.findAll();
    }

    public List<Action> loadChildren(Action parent) {
        return actionDao.loadChildNodeByParent(parent.getId());
    }


    public void del(Long id) {
        actionDao.delete(id);
    }

    @Transactional(readOnly = false)
    public void del(Action action) {
        if (action == null) return;

        for (Action action1 : actionDao.findByAction(action)) {
            action1.setAction(action.getAction());
            actionDao.save(action1);
        }


        actionDao.delete(action);
    }

   @Transactional(readOnly = false)
	public Long saveOrUpdate(ActionDTO dto, List<Long> roleIds) throws BizException {
		Action entity = null;
		Long voId = dto.getId();
		if (voId != null && voId > 0) {
			entity = actionDao.findOne(dto.getId());
		}
		String keyName = dto.getKey();
		if (StringHelper.isEmpty(keyName)) {
			throw new BizException("系统资源名称不能为空",-101);
		}
		List<Action> sameKeyNameEntity = actionDao.findListByKeyName(keyName);
		if (CollectionUtils.isNotEmpty(sameKeyNameEntity)) {
			if (entity == null) {
				throw new BizException("该系统资源名称["+keyName+"]已经存在", -102);
			} else {
				if (voId.longValue() != sameKeyNameEntity.get(0).getId().longValue()) {
					throw new BizException("该系统资源名称["+keyName+"]已经存在", -102);
				}
			}
		}
		if (entity == null) {
			entity = new Action();
		}
		Action parentEntity = null;
		Long voPid = dto.getpId();
		if (voPid != null && voPid > 0) {
			parentEntity = actionDao.findOne(voPid);
		}
		entity.setAction(parentEntity);
		entity.setActionName(keyName);
		entity.setActionText(dto.getName());
		entity.setRemark(dto.getTitle());
		entity.setTargetUrl(dto.getTargetUrl());
		entity.setType(dto.getType());
		entity.setIdx(dto.getIdx());
		actionDao.save(entity);
		
		Set<Role> roles = roleService.getRolesToOther(roleIds);
		for (Role r : roles) {
			r.getAction().add(entity);
			roleDao.save(r);
		}

		return entity.getId();
	}
}
