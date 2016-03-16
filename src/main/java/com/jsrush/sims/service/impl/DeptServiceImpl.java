package com.jsrush.sims.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsrush.sims.dao.DeptRepository;
import com.jsrush.sims.entity.Dept;
import com.jsrush.sims.service.DeptService;

@Service("deptService")
@Transactional(readOnly=true)
public class DeptServiceImpl implements DeptService {
	
	@Autowired
	private DeptRepository deptRepository;
	
	@Override
	public Map<String, Object> findPageList(Dept condition, Integer pageNo, Integer pageSize) {
		Map<String, Object> pageMap = deptRepository.findPageList(condition, pageNo, pageSize);
		return pageMap;
	}

	@Override
	public void saveOrUpdate(Dept dto) {
		Dept dept = null;
		Long dtoId = dto.getId();
		if (dtoId != null && dtoId > 0) {
			dept = deptRepository.findOne(dtoId);
		}
		if (dept == null) {
			dept = new Dept();
		}
		dept.setEcId(dto.getEcId());
		dept.setDeptName(dto.getDeptName());
		dept.setType(dto.getType());
		dept.setRemark(dto.getRemark());
		dept.setParentId(dto.getParentId());
		deptRepository.save(dept);
	}

	@Override
	public void delete(List<Long> ids) {
		for (Long id : ids) {
			Dept dept = deptRepository.findOne(id);
			if (dept != null) {
				deptRepository.delete(dept);
			}
		}
	}
	
}
