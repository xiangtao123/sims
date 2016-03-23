package com.jsrush.sims.controller;

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
 * 选修
 * @author sunburst
 *
 */
@Controller
@RequestMapping("/take_course")
public class StudentCourseController {

	private static final Logger logger = LoggerFactory.getLogger(StudentCourseController.class);
	
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
		List<Map<String, Object>> studentList = studentService.findListByEcId(currentUserEcId);
		List<Map<Long, String>> courseList = courseService.findListByEcId(currentUserEcId);
		
		model.addAttribute("studentList", studentList);
		model.addAttribute("courseList", courseList);
		return "sims/takecourse_index";
	}
	
	@RequestMapping(value="/list")
	@ResponseBody
	public Map<String,Object> list(StudentCourseCondition condtion,
					   @RequestParam(defaultValue = "1", value = "page") int pageNo, 
					   @RequestParam(defaultValue = "10", value = "rows") int pageSize){
		try {
			Long currentUserEcId = shiroManager.getCurrentUserEcId();
			condtion.setEcId(currentUserEcId);
			return studentCourseService.findPageList(condtion, pageNo, pageSize);
		} catch (Exception e) {
			logger.error("学生选课-查询列表失败", e);
			return SystemUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
	}
	
	@RequestMapping(value="/takeCourse")
	@ResponseBody
	public int takeCourse(Long studentId, Long courseId) {
		try {
			Long currentUserEcId = shiroManager.getCurrentUserEcId();
			if (currentUserEcId == null || 1 > currentUserEcId) {
				return -1;
			}
			studentCourseService.takeCourse(studentId, courseId, currentUserEcId);
			return 1;
		} catch (Exception e) {
			logger.error("学生选课-新增编辑失败", e);
			return -2;
		}
	}
	
	@RequestMapping(value="/saveGrade")
	@ResponseBody
	public int saveGrade(Long studentCourseId, String grade) {
		try {
			Long currentUserEcId = shiroManager.getCurrentUserEcId();
			if (currentUserEcId == null || 1 > currentUserEcId) {
				return -1;
			}
			studentCourseService.saveGrade(studentCourseId, grade);
			return 1;
		} catch (Exception e) {
			logger.error("学生选课-注册失败", e);
			return -2;
		}
	}
	
}
