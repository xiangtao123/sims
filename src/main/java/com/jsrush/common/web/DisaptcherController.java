package com.jsrush.common.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jsrush.util.StringHelper;

/**
 * 通用跳转页面
 * @author sunburst
 *
 */
@Controller
@RequestMapping(value="/dispatcher")
public class DisaptcherController {

	private Logger log = LoggerFactory.getLogger(DisaptcherController.class);
	
	/**
	 *  跳转页面
	 *  eg.
	 *  ${ctx }/dispatcher/welcome/index
	 *  ${ctx }/dispatcher/login
	 * @param namespace
	 * @param action
	 * @return
	 */
	@RequestMapping(value="{namespace}/{action}")
	public String dispatch(@PathVariable("namespace") String namespace, @PathVariable("action") String action){
		String targetUrl =  StringHelper.isEmpty(action) ? namespace : namespace + "/" +action ;
		log.info("dispatcher to : " + targetUrl);
		return targetUrl;
	}
	
}
