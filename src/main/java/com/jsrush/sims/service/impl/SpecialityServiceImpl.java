package com.jsrush.sims.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsrush.sims.dao.DeptRepository;
import com.jsrush.sims.dao.SpecialityRepository;
import com.jsrush.sims.entity.Dept;
import com.jsrush.sims.entity.Speciality;
import com.jsrush.sims.service.SpecialityService;

@Service("specialityService")
@Transactional
public class SpecialityServiceImpl implements SpecialityService {

	@Autowired
	private SpecialityRepository specialityRepository;
	
	@Autowired
	private DeptRepository deptRepository;
	
	@Override
	public Map<String, Object> findPageList(Speciality condition, Integer pageNo, Integer pageSize) {
		Map<String, Object> pageMap = specialityRepository.findPageList(condition, pageNo, pageSize);
		return pageMap;
	}

	@Override
	public void saveOrUpdate(Speciality dto) {
		Speciality entity = null;
		Long dtoId = dto.getId();
		if (dtoId != null && dtoId > 0) {
			entity = specialityRepository.findOne(dtoId);
		}
		if (entity == null) {
			entity = new Speciality();
		}
		entity.setCode(dto.getCode());
		entity.setDegreeType(dto.getDegreeType());
		
		Long deptId = dto.getDeptId();
		if (deptId != null && deptId.longValue() > 0) {
			Dept dept=deptRepository.findOne(deptId);
			if (dept != null) {
				entity.setDept(dept);
			}
		}
		entity.setEcId(dto.getEcId());
		entity.setLength(dto.getLength());
		entity.setName(dto.getName());
		entity.setOptionCredit(dto.getOptionCredit());
		entity.setRequireCredit(dto.getRequireCredit());

		specialityRepository.save(entity);
	}

	@Override
	public void delete(List<Long> ids) {
		for (Long id : ids) {
			Speciality speciality = specialityRepository.findOne(id);
			if (speciality != null) {
				specialityRepository.delete(speciality);
			}
		}
	}
	
	@Override
	public List<Map<Long, String>> findListByEcId(Long ecId) {
		return specialityRepository.findListByEcId(ecId);
	}
	
}
