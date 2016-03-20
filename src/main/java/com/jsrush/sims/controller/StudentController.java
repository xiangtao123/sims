package com.jsrush.sims.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.alibaba.fastjson.JSONObject;
import com.jsrush.security.rbac.service.ShiroManager;
import com.jsrush.sims.entity.Student;
import com.jsrush.sims.service.SpecialityService;
import com.jsrush.sims.service.StudentService;
import com.jsrush.util.SystemUtil;

/**
 * 学生
 * @author sunburst
 *
 */
@Controller
@RequestMapping("/student")
public class StudentController {

	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
	
	@Autowired
	public ShiroManager shiroManager;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private SpecialityService specialityService;
	
	
	@RequestMapping("/index")
	public String index(Model model) {
		Long currentUserEcId = shiroManager.getCurrentUserEcId();
		List<Map<Long, String>> deptList = specialityService.findListByEcId(currentUserEcId);
		model.addAttribute("specialityList", deptList);
		return "sims/student_index";
	}
	
	@RequestMapping(value="/list")
	@ResponseBody
	public Map<String,Object> list(Student condtion,
					   @RequestParam(defaultValue = "1", value = "page") int pageNo, 
					   @RequestParam(defaultValue = "10", value = "rows") int pageSize){
		try {
			Long currentUserEcId = shiroManager.getCurrentUserEcId();
			condtion.setEcId(currentUserEcId);
			return studentService.findPageList(condtion, pageNo, pageSize);
		} catch (Exception e) {
			logger.error("学生-查询列表失败", e);
			return SystemUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
	}
	
	@RequestMapping(value="/saveOrUpdate")
	@ResponseBody
	public int saveOrUpdate(Student dto) {
		try {
			Long currentUserEcId = shiroManager.getCurrentUserEcId();
			if (currentUserEcId == null || 1 > currentUserEcId) {
				return -1;
			}
			dto.setEcId(currentUserEcId);
			studentService.saveOrUpdate(dto);
			return 1;
		} catch (Exception e) {
			logger.error("学生-新增编辑失败", e);
			return -2;
		}
	}
	
	@RequestMapping(value="/updateStudent")
	@ResponseBody
	public int updateStudent(Student dto, String studentIds) {
		try {
			if (StringUtils.isNotBlank(studentIds)) {
				return -1;
			}
			Set<Long> ids = new HashSet<Long>();
			String[] idsStrArray = studentIds.split(",");
			for (String id : idsStrArray) {
				if (StringUtils.isNotBlank(id)) {
					ids.add(Long.parseLong(id));
				}
			}
			if (CollectionUtils.isEmpty(ids)) {
				return -1;
			}
			studentService.updateStudent(dto, ids);
			return 1;
		} catch (Exception e) {
			logger.error("学生-更新失败", e);
			return -2;
		}
	}
	
	@RequestMapping(value="/findOne")
	@ResponseBody
	public String findOne(Long studentId) {
		try {
			if (studentId == null || 1 > studentId.longValue()) {
				return null;
			}
			Student vo = studentService.findOne(studentId);
			if (vo != null) {
				return JSONObject.toJSONString(vo);
			}
			return null;
		} catch (Exception e) {
			logger.error("学生-查询失败", e);
			return null;
		}
	}
	
	@RequestMapping(value="/findByUserId")
	@ResponseBody
	public String findByUserId(Long userId) {
		try {
			if (userId == null || 1 > userId.longValue()) {
				return null;
			}
			Student vo = studentService.findByUserId(userId);
			if (vo != null) {
				return JSONObject.toJSONString(vo);
			}
			return null;
		} catch (Exception e) {
			logger.error("学生-查询失败", e);
			return null;
		}
	}
	
}
