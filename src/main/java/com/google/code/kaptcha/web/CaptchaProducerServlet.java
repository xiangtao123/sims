package com.google.code.kaptcha.web;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jsrush.util.LogUtil;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

@SuppressWarnings("serial")
public class CaptchaProducerServlet  extends HttpServlet{

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info(request.getRemoteHost());
		Producer captchaProducer = (Producer)WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext()).getBean("captchaProducer") ;
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control","no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");
		String capText = captchaProducer.createText();
		request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY,capText);
		BufferedImage bi = captchaProducer.createImage(capText);
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			// write the data out
			ImageIO.write(bi, "jpg", out);
			out.flush();
		} catch (IOException e) {
			logger.error(LogUtil.stackTraceToString(e));
		} finally {
			try {
				if (out != null){
					out.close();
				}
			} catch (IOException e) {
				logger.error(LogUtil.stackTraceToString(e));
			}
		}
	}
}
