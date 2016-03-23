package com.jsrush.sims.controller;

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
import com.jsrush.sims.service.StudentCourseService;
import com.jsrush.util.SystemUtil;

/**
 * 查询成绩
 * 
 * @author sunburst
 */
@Controller
@RequestMapping(value="/query_score")
public class QueryScoreController {

	private static final Logger logger = LoggerFactory.getLogger(QueryScoreController.class);
	
	@Autowired
	public ShiroManager shiroManager;
	
	@Autowired
	private StudentCourseService studentCourseService;
	
	@RequestMapping("/index")
	public String index(Model model) {
		return "sims/queryscore_index";
	}
	
	@RequestMapping(value="/list")
	@ResponseBody
	public Map<String,Object> list(StudentCourseCondition condtion,
					   @RequestParam(defaultValue = "1", value = "page") int pageNo, 
					   @RequestParam(defaultValue = "10", value = "rows") int pageSize){
		try {
			Long currentUserEcId = shiroManager.getCurrentUserEcId();
			if (null == currentUserEcId) {
				return SystemUtil.createPageInfoMap(pageNo, pageSize, 0, null);
			}
			Long currentStudentId = shiroManager.getCurrentStudentId();
			if (null == currentStudentId) {
				return SystemUtil.createPageInfoMap(pageNo, pageSize, 0, null);
			}
			
			condtion.setEcId(currentUserEcId);
			condtion.setStudentId(currentStudentId);
			return studentCourseService.findPageList(condtion, pageNo, pageSize);
		} catch (Exception e) {
			logger.error("学生成绩-查询列表失败", e);
			return SystemUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
	}
	
	
}
