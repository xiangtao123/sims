package com.jsrush.sims.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsrush.security.rbac.service.ShiroManager;
import com.jsrush.sims.entity.Course;
import com.jsrush.sims.service.CourseService;
import com.jsrush.sims.service.SpecialityService;
import com.jsrush.util.SystemUtil;

/**
 * 部门
 * @author sunburst
 *
 */
@Controller
@RequestMapping("/course")
public class CourseController {

	private static final Logger logger = LoggerFactory.getLogger(CourseController.class);
	
	@Autowired
	public ShiroManager shiroManager;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private SpecialityService specialityService;
	
	@RequestMapping("/index")
	public String index(Model model) {
		Long currentUserEcId = shiroManager.getCurrentUserEcId();
		List<Map<Long, String>> deptList = specialityService.findListByEcId(currentUserEcId);
		model.addAttribute("specialityList", deptList);
		return "sims/course_index";
	}
	
	@RequestMapping(value="/list")
	@ResponseBody
	public Map<String,Object> list(Course condtion,
					   @RequestParam(defaultValue = "1", value = "page") int pageNo, 
					   @RequestParam(defaultValue = "10", value = "rows") int pageSize){
		try {
			Long currentUserEcId = shiroManager.getCurrentUserEcId();
			condtion.setEcId(currentUserEcId);
			return courseService.findPageList(condtion, pageNo, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return SystemUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
	}
	
	@RequestMapping(value="/saveOrUpdate")
	@ResponseBody
	public int saveOrUpdate(Course dto) {
		try {
			Long currentUserEcId = shiroManager.getCurrentUserEcId();
			if (currentUserEcId == null || 1 > currentUserEcId) {
				return -1;
			}
			dto.setEcId(currentUserEcId);
			courseService.saveOrUpdate(dto);
			return 1;
		} catch (Exception e) {
			logger.error("部门院系-新增编辑失败", e);
			return -2;
		}
	}
	
	@RequestMapping(value="/delete")
	@ResponseBody
	public int delete(String deptIds) {
		try {
			if (StringUtils.isNotBlank(deptIds)) {
				return -1;
			}
			List<Long> ids = new ArrayList<Long>();
			String[] idsStrArray = deptIds.split(",");
			for (String id : idsStrArray) {
				if (StringUtils.isNotBlank(id)) {
					ids.add(Long.parseLong(id));
				}
			}
			if (CollectionUtils.isEmpty(ids)) {
				return -1;
			}
			courseService.delete(ids);
			return 1;
		} catch (Exception e) {
			logger.error("部门院系-删除失败", e);
			return -2;
		}
	}
	
}
