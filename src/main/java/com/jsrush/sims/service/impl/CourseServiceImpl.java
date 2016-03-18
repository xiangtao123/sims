package com.jsrush.sims.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsrush.sims.dao.CourseRepository;
import com.jsrush.sims.entity.Course;
import com.jsrush.sims.entity.Speciality;
import com.jsrush.sims.service.CourseService;

@Service("courseService")
@Transactional(readOnly=true)
public class CourseServiceImpl implements CourseService {
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Override
	public Map<String, Object> findPageList(Course condition, Integer pageNo, Integer pageSize) {
		Map<String, Object> pageMap = courseRepository.findPageList(condition, pageNo, pageSize);
		return pageMap;
	}

	@Override
	public void saveOrUpdate(Course dto) {
		Course entity = null;
		Long dtoId = dto.getId();
		if (dtoId != null && dtoId > 0) {
			entity = courseRepository.findOne(dtoId);
		}
		if (entity == null) {
			entity = new Course();
		}
		entity.setCredit(dto.getCredit());
		entity.setEcId(dto.getEcId());
		entity.setMaterial(dto.getMaterial());
		entity.setName(dto.getName());
		
		Set<Speciality> specialitys = entity.getSpecialitys();
		specialitys.clear();
		if (StringUtils.isNotBlank(dto.getSpecialityIds())) {
			String[] ids = dto.getSpecialityIds().split(",");
			for (String id : ids) {
				Speciality s = new Speciality();
				s.setId(Long.parseLong(id));
				specialitys.add(s);
			}
		}
		courseRepository.save(entity);
	}

	@Override
	public void delete(List<Long> ids) {
		for (Long id : ids) {
			Course entity = courseRepository.findOne(id);
			if (entity != null) {
				courseRepository.delete(entity);
			}
		}
	}

	@Override
	public List<Map<Long, String>> findListByEcId(Long ecId) {
		return courseRepository.findListByEcId(ecId);
	}
	
}
