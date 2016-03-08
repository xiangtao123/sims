package com.jsrush.security.rbac.vo;

import org.apache.shiro.authc.UsernamePasswordToken;


public class CaptchaUsernamePasswordToken extends UsernamePasswordToken  {
	
	private static final long serialVersionUID = 8805945094057221038L;
	
	private String captcha;  
	  
    public CaptchaUsernamePasswordToken(String username, char[] password, boolean rememberMe, String host, String captcha) {  
        super(username, password, rememberMe, host);  
        this.captcha = captcha;  
    }  
  
    public String getCaptcha() {  
        return captcha;  
    }  
  
    public void setCaptcha(String captcha) {  
        this.captcha = captcha;  
    }  
      
}  

