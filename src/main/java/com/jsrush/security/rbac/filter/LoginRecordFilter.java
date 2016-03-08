package com.jsrush.security.rbac.filter;

import com.jsrush.bizlog.entity.BizActionLog;
import com.jsrush.bizlog.event.BizActionLogAddEventPublisher;
import com.jsrush.bizlog.service.BizActionLogService;
import com.jsrush.common.service.SpringContextUtil;
import com.jsrush.security.rbac.vo.CaptchaUsernamePasswordToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@Component
public class LoginRecordFilter extends FormAuthenticationFilter {

	private static final Logger LOG = LoggerFactory.getLogger(LoginRecordFilter.class);

	public LoginRecordFilter() {
	}

	private static final String TEXT[] = {
				"登陆",
				"用户",
				"远程地址：",
				"远程主机：",
				"远程用户："
		};

	@Override
	protected CaptchaUsernamePasswordToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		String captcha = getCaptcha(request);
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);
		CaptchaUsernamePasswordToken token = new CaptchaUsernamePasswordToken(username, password != null ? password.toCharArray() : null, rememberMe, host, captcha);
		return token;
	}

	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		CaptchaUsernamePasswordToken token = createToken(request, response);
		try {
			/* 图形验证码验证 */
			doCaptchaValidate((HttpServletRequest) request, token);
			Subject subject = getSubject(request, response);
			doLoginValidate(subject, token);
			saveLoginRecord(request);
			return onLoginSuccess(token, subject, request, response);
		} catch (LockedAccountException e) {
			LOG.info(token.getUsername() + "  登录失败--" + e);
			e.printStackTrace();
			AuthenticationException ex = new AuthenticationException("用户锁定，无登录权限");
			return onLoginFailure(token, ex, request, response);
		} catch (IncorrectCredentialsException e) {
			LOG.info(token.getUsername() + "  登录失败--" + e);
			e.printStackTrace();
			AuthenticationException ex = new AuthenticationException("用户名/密码不匹配");
			return onLoginFailure(token, ex, request, response);
		} catch (AuthenticationException e) {
			LOG.info(token.getUsername() + "  登录失败--" + e);
			e.printStackTrace();

			return onLoginFailure(token, e, request, response);
		}
	}

	/**
	 * 登录名密码验证，登录权限验证
	 * @param subject
	 * @param token
	 */
	protected void doLoginValidate(Subject subject, CaptchaUsernamePasswordToken token)  throws AuthenticationException  {
		subject.login(token);// 登录验证
		boolean permitted = subject.isPermitted("page:welcome");
		if (!permitted) {
			subject.logout();
			throw new LockedAccountException("无登录权限");
		}
		LOG.info(token.getUsername() + "  登录成功");
	}

	// 验证码校验
	protected void doCaptchaValidate(HttpServletRequest request, CaptchaUsernamePasswordToken token) {
		// session中的图形码字符串
		String captcha = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		// 比对
		if (captcha != null && !captcha.equalsIgnoreCase(token.getCaptcha())) {
			throw new AuthenticationException("验证码错误！");
		}
	}

	private void saveLoginRecord(ServletRequest request) {
		HttpServletRequest req = (HttpServletRequest) request;
		StringBuilder bizContent = new StringBuilder();
		bizContent.append(TEXT[2]).append(request.getRemoteAddr()).append(TEXT[3]).append(request.getRemoteHost()).append(TEXT[4]).append(req.getRemoteUser());
		BizActionLog bizActionLog = new BizActionLog();
		bizActionLog.setActionType(TEXT[0]);
		bizActionLog.setBizContent(bizContent.toString());
		bizActionLog.setBizType(TEXT[1]);

		BizActionLogService bizActionLogService = SpringContextUtil.getBean("bizActionLogService");
		BizActionLogAddEventPublisher publisher = SpringContextUtil.getBean("bizActionLogAddEventPublisher");

		bizActionLogService.setEntityUserInfo(bizActionLog);
		publisher.publishEvent(bizActionLog);
	}

	public static final String DEFAULT_CAPTCHA_PARAM = "captcha";

	private String captchaParam = DEFAULT_CAPTCHA_PARAM;

	public String getCaptchaParam() {
		return captchaParam;
	}

	public void setCaptchaParam(String captchaParam) {
		this.captchaParam = captchaParam;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	@Override
	protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
		request.setAttribute(getFailureKeyAttribute(), ae);
	}

}
