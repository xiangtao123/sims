package com.jsrush.common.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/welcome")
public class WelcomeController {

	@RequestMapping(value="index")
	public String index(){
		return "welcome/index";
	}
	
}
