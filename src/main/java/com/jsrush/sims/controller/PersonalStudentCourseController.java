package com.jsrush.sims.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsrush.security.rbac.service.ShiroManager;
import com.jsrush.sims.dto.StudentCourseCondition;
import com.jsrush.sims.service.CourseService;
import com.jsrush.sims.service.StudentCourseService;
import com.jsrush.sims.service.StudentService;
import com.jsrush.util.SystemUtil;

/**
 * 学生个人选课
 * 
 * @author sunburst
 *
 */
@Controller
@RequestMapping("/student_take_course")
public class PersonalStudentCourseController {

	private static final Logger logger = LoggerFactory.getLogger(PersonalStudentCourseController.class);
	
	@Autowired
	public ShiroManager shiroManager;
	
	@Autowired
	private StudentCourseService studentCourseService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private StudentService studentService;
	
	
	@RequestMapping("/index")
	public String index(Model model) {
		Long currentUserEcId = shiroManager.getCurrentUserEcId();
		List<Map<Long, String>> courseList = courseService.findListByEcId(currentUserEcId);
		
		model.addAttribute("courseList", courseList);
		return "sims/student_takecourse_index";
	}
	
	@RequestMapping(value="/list")
	@ResponseBody
	public Map<String,Object> list(StudentCourseCondition condtion,
					   @RequestParam(defaultValue = "1", value = "page") int pageNo, 
					   @RequestParam(defaultValue = "10", value = "rows") int pageSize){
		try {
			Long currentUserEcId = shiroManager.getCurrentUserEcId();
			Long currentStudentId = shiroManager.getCurrentStudentId();
			if (null == currentStudentId) {
				return SystemUtil.createPageInfoMap(pageNo, pageSize, 0, new ArrayList<Object>());
			}
			condtion.setEcId(currentUserEcId);
			condtion.setStudentId(currentStudentId);
			return studentCourseService.findPageList(condtion, pageNo, pageSize);
		} catch (Exception e) {
			logger.error("学生选课-查询列表失败", e);
			return SystemUtil.createPageInfoMap(pageNo, pageSize, 0, new ArrayList<Object>());
		}
	}
	
	@RequestMapping(value="/takeCourse")
	@ResponseBody
	public int takeCourse(Long courseId) {
		try {
			Long currentUserEcId = shiroManager.getCurrentUserEcId();
			if (currentUserEcId == null || 1 > currentUserEcId) {
				return -1;
			}
			Long currentStudentId = shiroManager.getCurrentStudentId();
			if (currentStudentId == null || 1 > currentStudentId) {
				return -3;
			}
			studentCourseService.takeCourse(currentStudentId, courseId, currentUserEcId);
			return 1;
		} catch (Exception e) {
			logger.error("学生选课-新增编辑失败", e);
			return -2;
		}
	}
	
}
